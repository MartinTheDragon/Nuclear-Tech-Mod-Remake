package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.api.fluid.trait.FluidTraitManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mojang.logging.LogUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.GsonHelper
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.conditions.ICondition.IContext
import net.minecraftforge.fluids.FluidStack

object FluidTraitManager : SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "${NuclearTech.MODID}_fluid_traits"), FluidTraitManager {
    @JvmStatic private val LOGGER = LogUtils.getLogger()
    internal var context: IContext = IContext.EMPTY
    private var byTarget = emptyMap<AttachedFluidTrait.FluidTarget, List<AttachedFluidTrait<*>>>()

    override fun apply(definitions: MutableMap<ResourceLocation, JsonElement>, resourceManager: ResourceManager, profiler: ProfilerFiller) {
        byTarget = buildMap<_, MutableList<AttachedFluidTrait<*>>> {
            for ((id, json) in definitions) {
                if (id.path.startsWith('_')) continue

                try {
                    if (json.isJsonObject && !CraftingHelper.processConditions(json.asJsonObject, "conditions", context)) {
                        LOGGER.debug("Skipping loading fluid trait $id as its conditions were not met")
                        continue
                    }
                    val attachedTrait = AttachedFluidTraitImpl.fromJson(id, GsonHelper.convertToJsonObject(json, "top element"))
                    getOrPut(attachedTrait.target, ::mutableListOf) += attachedTrait
                } catch (ex: JsonParseException) {
                    LOGGER.error("Couldn't parse fluid trait $id", ex)
                }
            }
        }.mapValues { (_, attachedList) -> attachedList.toList() }
    }

    // TODO support overrides
    override fun getTraitsForFluidStack(fluidStack: FluidStack) = byTarget.filterKeys { it.test(fluidStack) }.values.flatten()
}
