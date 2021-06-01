package at.martinthedragon.ntm.datagen

import at.martinthedragon.ntm.Main
import com.google.gson.GsonBuilder
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.data.IDataProvider
import net.minecraft.resources.ResourcePackType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.data.ExistingFileHelper
import java.io.IOException

abstract class ArmatureProvider<T : ArmatureBuilder>(
    protected val generator: DataGenerator,
    protected val folder: String,
    protected val factory: (ResourceLocation) -> T,
    val existingFileHelper: ExistingFileHelper
) : IDataProvider {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    val generatedArmatures = mutableMapOf<ResourceLocation, T>()

    protected abstract fun registerArmatures()

    override fun run(cache: DirectoryCache) {
        clear()
        registerArmatures()
        generateAll(cache)
    }

    protected fun clear() {
        generatedArmatures.clear()
    }

    protected fun generateAll(cache: DirectoryCache) {
        for (armature in generatedArmatures.values) {
            val target = generator.outputFolder.resolve("assets/${armature.location.namespace}/armatures/${armature.location.path}.json")
            try {
                IDataProvider.save(gson, cache, armature.toJson(), target)
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }
    }

    fun builder(path: String): T {
        val outputLocation = extendWithFolder(if (path.contains(':')) ResourceLocation(path) else ResourceLocation(Main.MODID, path))
        existingFileHelper.trackGenerated(outputLocation, ARMATURE)
        return generatedArmatures.computeIfAbsent(outputLocation, factory)
    }

    private fun extendWithFolder(resourceLocation: ResourceLocation): ResourceLocation =
        if (resourceLocation.path.contains('/')) resourceLocation
        else ResourceLocation(resourceLocation.namespace, "$folder/${resourceLocation.path}")

    companion object {
        const val BLOCK_FOLDER = "block"
        const val ITEM_FOLDER = "item"

        val ARMATURE = ExistingFileHelper.ResourceType(ResourcePackType.CLIENT_RESOURCES, ".json", "armatures")
    }
}
