package at.martinthedragon.nucleartech.rendering

import net.minecraft.client.resources.model.BakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener
import net.minecraftforge.client.model.renderable.SimpleRenderable

object SpecialModels : ResourceManagerReloadListener {
    private val modelFunctions = mutableMapOf<ResourceLocation, (ResourceLocation) -> SimpleRenderable>()
    private val modelCache = mutableMapOf<ResourceLocation, SimpleRenderable>()

    // this is separate from the above
    private val bakedModelFunctions = mutableMapOf<ResourceLocation, (ResourceLocation) -> BakedModel>()
    private val bakedModelCache = mutableMapOf<ResourceLocation, BakedModel>()

    fun registerModel(id: ResourceLocation, modelFunction: (ResourceLocation) -> SimpleRenderable) {
        modelFunctions[id] = modelFunction
    }

    fun getModel(id: ResourceLocation) = modelCache.computeIfAbsent(id, modelFunctions.getValue(id))
    fun hasModel(id: ResourceLocation) = modelFunctions.containsKey(id)
    fun getOrRegisterModel(id: ResourceLocation, modelFunction: (ResourceLocation) -> SimpleRenderable): SimpleRenderable {
        if (!hasModel(id)) registerModel(id, modelFunction)
        return getModel(id)
    }

    fun registerBakedModel(id: ResourceLocation, bakedModelFunction: (ResourceLocation) -> BakedModel) {
        bakedModelFunctions[id] = bakedModelFunction
    }

    fun getBakedModel(id: ResourceLocation) = bakedModelCache.computeIfAbsent(id, bakedModelFunctions.getValue(id))
    fun hasBakedModel(id: ResourceLocation) = bakedModelFunctions.containsKey(id)
    fun getOrRegisterBakedModel(id: ResourceLocation, bakedModelFunction: (ResourceLocation) -> BakedModel): BakedModel {
        if (!hasBakedModel(id)) registerBakedModel(id, bakedModelFunction)
        return getBakedModel(id)
    }

    override fun onResourceManagerReload(manager: ResourceManager) {
        modelCache.clear()
        bakedModelCache.clear()
    }
}
