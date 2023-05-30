package at.martinthedragon.nucleartech.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.mojang.datafixers.util.Pair
import com.mojang.logging.LogUtils
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraftforge.client.model.IModelConfiguration
import net.minecraftforge.client.model.IModelLoader
import net.minecraftforge.client.model.geometry.IModelGeometry
import java.util.function.Function
import kotlin.random.Random

object RandomModelLoader : IModelLoader<RandomModelLoader.Model> {
    private val LOGGER = LogUtils.getLogger()

    override fun onResourceManagerReload(resourceManager: ResourceManager) {}

    override fun read(deserializationContext: JsonDeserializationContext, modelContents: JsonObject): Model {
        if (!modelContents.has("models")) throw RuntimeException("Random Model Loader requires a 'models' element")
        val models = modelContents.get("models").asJsonArray
        val id = if (modelContents.has("id_supplier")) modelIdMap.getValue(ResourceLocation(modelContents.get("id_supplier").asString)).invoke(models.size()) else Random.nextInt(models.size())
        return Model(id, deserializationContext.deserialize(models[id], BlockModel::class.java))
    }

    private val modelIdMap = mutableMapOf<ResourceLocation, (size: Int) -> Int>().withDefault {
        LOGGER.error("Could not find random model id supplier for '$it'")
        Random::nextInt
    }

    fun setIdSupplier(identifier: ResourceLocation, idSupplier: (size: Int) -> Int) {
        modelIdMap[identifier] = idSupplier
    }

    class Model(val id: Int, val model: BlockModel) : IModelGeometry<Model> {
        override fun bake(owner: IModelConfiguration, bakery: ModelBakery, spriteGetter: Function<Material, TextureAtlasSprite>, modelTransform: ModelState, overrides: ItemOverrides, modelLocation: ResourceLocation): BakedModel {
            return model.bake(bakery, model, spriteGetter, modelTransform, modelLocation, true)
        }

        override fun getTextures(owner: IModelConfiguration, modelGetter: Function<ResourceLocation, UnbakedModel>, missingTextureErrors: MutableSet<Pair<String, String>>): MutableCollection<Material> {
            return model.getMaterials(modelGetter, missingTextureErrors)
        }
    }
}
