package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.entity.missile.AbstractMissile
import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.BlockModelRotation
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener
import net.minecraftforge.client.model.ForgeModelBakery
import net.minecraftforge.client.model.SimpleModelState
import net.minecraftforge.client.model.StandaloneModelConfiguration
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.client.model.obj.OBJModel
import net.minecraftforge.client.model.renderable.SimpleRenderable
import net.minecraftforge.client.textures.UnitSprite
import java.util.function.Supplier

@Suppress("unused")
object SpecialModels : ResourceManagerReloadListener {
    private val modelFunctions = mutableMapOf<ResourceLocation, (ResourceLocation) -> SimpleRenderable>()
    private val bakedModelFunctions = mutableMapOf<ResourceLocation, (ResourceLocation) -> BakedModel>()

    val ASSEMBLER_ARM = registerModel(modelLoc("assembler/arm"), simpleModel())
    val ASSEMBLER_BODY = registerModel(modelLoc("assembler/body"), simpleModel())
    val ASSEMBLER_COG = registerModel(modelLoc("assembler/cog"), simpleModel())
    val ASSEMBLER_SLIDER = registerModel(modelLoc("assembler/slider"), simpleModel())
    val CHEM_PLANT_BODY = registerModel(modelLoc("chem_plant/body"), simpleModel())
    val CHEM_PLANT_FLUID = registerBakedModel(modelLoc("chem_plant/fluid"), bakedModel())
    val CHEM_PLANT_FLUID_CAP = registerBakedModel(modelLoc("chem_plant/fluid_cap"), bakedModel())
    val CHEM_PLANT_PISTON = registerModel(modelLoc("chem_plant/piston"), simpleModel())
    val CHEM_PLANT_SPINNER = registerModel(modelLoc("chem_plant/spinner"), simpleModel())
    val FAT_MAN = registerModel(modelLoc("fat_man/fat_man"), simpleModel())
    val LAUNCH_PAD = registerModel(modelLoc("launch_pad/launch_pad"), simpleModel())
    val LITTLE_BOY = registerModel(modelLoc("little_boy/little_boy"), simpleModel())
    val MISSILE_HUGE = registerBakedModel(AbstractMissile.MODEL_MISSILE_HUGE, bakedModel())
    val MISSILE_NUCLEAR = registerBakedModel(modelLoc("missiles/missile_nuclear"), bakedModel())
    val MISSILE_STRONG = registerBakedModel(AbstractMissile.MODEL_MISSILE_STRONG, bakedModel())
    val MISSILE_V2 = registerBakedModel(AbstractMissile.MODEL_MISSILE_V2, bakedModel())
    val MUSHROOM_CLOUD = registerBakedModel(modelLoc("mushroom_cloud/mush"), bakedModel())

    private fun modelLoc(path: String) = ntm("models/other/$path.obj")

    private fun simpleModel(detectCullableFaces: Boolean = false, diffuseLighting: Boolean = false, flipV: Boolean = true, ambientToFullbright: Boolean = true, materialLibraryOverrideLocation: String? = null): (ResourceLocation) -> SimpleRenderable =
        { id -> OBJLoader.INSTANCE.loadModel(OBJModel.ModelSettings(id, detectCullableFaces, diffuseLighting, flipV, ambientToFullbright, materialLibraryOverrideLocation)).bakeRenderable(StandaloneModelConfiguration.create(id, mapOf("#texture" to ResourceLocation(id.namespace, id.path.removeSuffix(".obj").removePrefix("models/"))))) }

    private fun bakedModel(detectCullableFaces: Boolean = false, diffuseLighting: Boolean = false, flipV: Boolean = true, ambientToFullbright: Boolean = true, materialLibraryOverrideLocation: String? = null): (ResourceLocation) -> BakedModel =
        { id -> OBJLoader.INSTANCE.loadModel(OBJModel.ModelSettings(id, detectCullableFaces, diffuseLighting, flipV, ambientToFullbright, materialLibraryOverrideLocation)).bake(StandaloneModelConfiguration.INSTANCE, ForgeModelBakery.instance(), UnitSprite.GETTER, SimpleModelState.IDENTITY, ItemOverrides.EMPTY, id) }

    private val modelCache = mutableMapOf<ResourceLocation, SimpleRenderable>()
    private val bakedModelCache = mutableMapOf<ResourceLocation, BakedModel>()

    fun registerModel(id: ResourceLocation, modelFunction: (ResourceLocation) -> SimpleRenderable): SimpleModelReference {
        modelFunctions[id] = modelFunction
        return SimpleModelReference(id)
    }

    fun getModel(id: ResourceLocation) = modelCache.computeIfAbsent(id, modelFunctions.getValue(id))
    fun hasModel(id: ResourceLocation) = modelFunctions.containsKey(id)
    fun getOrRegisterModel(id: ResourceLocation, modelFunction: (ResourceLocation) -> SimpleRenderable): SimpleRenderable {
        if (!hasModel(id)) registerModel(id, modelFunction)
        return getModel(id)
    }

    fun registerBakedModel(id: ResourceLocation, bakedModelFunction: (ResourceLocation) -> BakedModel): BakedModelReference {
        bakedModelFunctions[id] = bakedModelFunction
        return BakedModelReference(id)
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
        modelCache.putAll(modelFunctions.mapValues { (id, function) -> function(id) })
        bakedModelCache.putAll(bakedModelFunctions.mapValues { (id, function) -> function(id) })
    }

    // must be called at right time, e.g. ModelRegistryEvent or TextureStitchEvent.Post
    fun injectIntoModelBakery(modelBakery: ModelBakery, key: ResourceLocation, unbakedModel: UnbakedModel, override: Boolean = false, bakeNow: Boolean = true) {
        if (override) {
            modelBakery.unbakedCache[key] = unbakedModel
            modelBakery.topLevelModels[key] = unbakedModel
            if (bakeNow) {
                unbakedModel.getMaterials(modelBakery::getModel, mutableSetOf()) // so the BlockModel's parent gets set
                modelBakery.bakedTopLevelModels[key] = modelBakery.bake(key, BlockModelRotation.X0_Y0, modelBakery.spriteMap::getSprite)
            }
        } else {
            val existed = modelBakery.unbakedCache.putIfAbsent(key, unbakedModel) != null
            modelBakery.topLevelModels.putIfAbsent(key, unbakedModel)
            if (bakeNow && !existed) {
                unbakedModel.getMaterials(modelBakery::getModel, mutableSetOf())
                modelBakery.bakedTopLevelModels.putIfAbsent(key, modelBakery.bake(key, BlockModelRotation.X0_Y0, modelBakery.spriteMap::getSprite))
            }
        }
    }

    class SimpleModelReference(val id: ResourceLocation) : Supplier<SimpleRenderable> {
        override fun get() = getModel(id)
    }

    class BakedModelReference(val id: ResourceLocation) : Supplier<BakedModel> {
        override fun get() = getBakedModel(id)
    }
}
