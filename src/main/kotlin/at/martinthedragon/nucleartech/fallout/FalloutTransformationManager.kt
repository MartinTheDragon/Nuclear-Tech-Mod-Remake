package at.martinthedragon.nucleartech.fallout

import at.martinthedragon.nucleartech.NuclearTech
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mojang.logging.LogUtils
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.GsonHelper
import net.minecraft.util.profiling.ProfilerFiller

object FalloutTransformationManager : SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "${NuclearTech.MODID}_fallouttf") {
    @JvmStatic private val LOGGER = LogUtils.getLogger()

    private var byName = emptyMap<ResourceLocation, FalloutTransformation>()

    override fun apply(definitions: MutableMap<ResourceLocation, JsonElement>, resourceManager: ResourceManager, profiler: ProfilerFiller) {
        byName = buildMap {
            for ((id, json) in definitions) {
                if (id.path.startsWith('_')) continue

                try {
                    put(id, FalloutTransformation.fromJson(id, GsonHelper.convertToJsonObject(json, "top element")))
                } catch (ex: JsonParseException) {
                    LOGGER.error("Couldn't parse fallout transformation $id", ex)
                }
            }
        }
    }

    fun byKey(key: ResourceLocation) = byName[key]
    fun getAllTransformations() = byName.values.toList()
}
