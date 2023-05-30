package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.RegistriesAndLifecycle
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.api.fluid.trait.FluidTrait
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.GsonHelper
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.ForgeRegistries

class AttachedFluidTraitImpl<out T : FluidTrait> private constructor(
    override val trait: T,
    override val target: AttachedFluidTrait.FluidTarget,
    override val tag: CompoundTag
) : AttachedFluidTrait<T> {
    companion object {
        @JvmStatic fun fromJson(id: ResourceLocation, json: JsonObject): AttachedFluidTrait<*> {
            val trait = GsonHelper.getAsString(json, "trait", null) ?: throw JsonParseException("Fluid trait attachment $id doesn't define a fluid trait to attach")
            val parsedTrait = RegistriesAndLifecycle.FLUID_TRAIT_REGISTRY.get().getValue(ResourceLocation(trait)) ?: throw JsonParseException("Couldn't find fluid trait with id $trait")

            val fluid = GsonHelper.getAsString(json, "fluid", null)
            val tag = GsonHelper.getAsString(json, "tag", null)
            if (fluid == null && tag == null)
                throw JsonParseException("Fluid trait attachment $id doesn't have a fluid id or tag defined")

            val tagTarget = tag?.let { TagTarget(TagKey.create(ForgeRegistries.FLUIDS.registryKey, ResourceLocation(tag.removePrefix("#")))) }
            val idTarget = fluid?.let { IdTarget(ForgeRegistries.FLUIDS.getValue(ResourceLocation(fluid)) ?: throw JsonParseException("Couldn't find fluid with id $fluid")) }
            val targets = CompoundTarget(buildSet {
                if (tagTarget != null) add(tagTarget)
                if (idTarget != null) add(idTarget)
            })

            val data = GsonHelper.getAsJsonObject(json, "data", JsonObject())!!
            val parsedData = parsedTrait.loadAdditionalData(data)

            return AttachedFluidTraitImpl(parsedTrait, targets, parsedData)
        }
    }

    private data class IdTarget(private val fluid: Fluid) : AttachedFluidTrait.FluidTarget {
        override fun test(fluid: FluidStack) = fluid.fluid == this.fluid
    }

    private data class TagTarget(private val tag: TagKey<Fluid>) : AttachedFluidTrait.FluidTarget {
        @Suppress("DEPRECATION")
        override fun test(fluid: FluidStack) = fluid.fluid.`is`(tag)
    }

    private data class CompoundTarget(private val targets: Collection<AttachedFluidTrait.FluidTarget>) : AttachedFluidTrait.FluidTarget {
        override fun test(fluid: FluidStack) = targets.any { it.test(fluid) }
    }
}
