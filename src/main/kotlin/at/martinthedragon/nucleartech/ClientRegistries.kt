package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.blocks.entities.BlockEntityTypes
import at.martinthedragon.nucleartech.blocks.entities.renderers.SteamPressRenderer
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.entities.renderers.MushroomCloudRenderer
import at.martinthedragon.nucleartech.entities.renderers.NoopRenderer
import at.martinthedragon.nucleartech.entities.renderers.NuclearCreeperRenderer
import at.martinthedragon.nucleartech.items.BombKitItem
import at.martinthedragon.nucleartech.menus.MenuTypes
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import at.martinthedragon.nucleartech.rendering.NuclearRenderTypes
import at.martinthedragon.nucleartech.screens.*
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.searchtree.ReloadableSearchTree
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.*
import net.minecraftforge.client.model.ForgeModelBakery
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import java.util.stream.Stream

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {
    @SubscribeEvent(priority = EventPriority.LOWEST) // so that the actual container types get registered before this
    @JvmStatic
    fun registerScreens(event: RegistryEvent.Register<MenuType<*>>) {
        NuclearTech.LOGGER.debug("Registering screens")
        MenuScreens.register(MenuTypes.safeMenu.get(), ::SafeScreen)
        MenuScreens.register(MenuTypes.sirenMenu.get(), ::SirenScreen)
        MenuScreens.register(MenuTypes.steamPressMenu.get(), ::SteamPressScreen)
        MenuScreens.register(MenuTypes.blastFurnaceMenu.get(), ::BlastFurnaceScreen)
        MenuScreens.register(MenuTypes.combustionGeneratorMenu.get(), ::CombustionGeneratorScreen)
        MenuScreens.register(MenuTypes.electricFurnaceMenu.get(), ::ElectricFurnaceScreen)
        MenuScreens.register(MenuTypes.shredderMenu.get(), ::ShredderScreen)

        MenuScreens.register(MenuTypes.littleBoyMenu.get(), ::LittleBoyScreen)
        MenuScreens.register(MenuTypes.fatManMenu.get(), ::FatManScreen)
    }

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        NuclearTech.LOGGER.debug("Creating search trees")
        val templateFolderSearchTree = ReloadableSearchTree<ItemStack>({
            it.getTooltipLines(null, TooltipFlag.Default.NORMAL).stream().map { tooltip ->
                ChatFormatting.stripFormatting(tooltip.string)!!.trim()
            }
        }) { Stream.of(ForgeRegistries.ITEMS.getKey(it.item)) }
        Minecraft.getInstance().searchTreeManager.register(UseTemplateFolderScreen.SEARCH_TREE, templateFolderSearchTree)

        NuclearTech.LOGGER.debug("Setting rendering layers")
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.glowingMushroom.get(), RenderType.cutout())
    }

    @SubscribeEvent @JvmStatic
    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        NuclearTech.LOGGER.debug("Registering BERs")
        event.registerBlockEntityRenderer(BlockEntityTypes.steamPressHeadBlockEntityType.get(), ::SteamPressRenderer)

        NuclearTech.LOGGER.debug("Registering Entity Renderers")
        event.registerEntityRenderer(EntityTypes.nukeExplosionEntity.get(), ::NoopRenderer)
        event.registerEntityRenderer(EntityTypes.nukeCloudEntity.get(), ::MushroomCloudRenderer)
        event.registerEntityRenderer(EntityTypes.falloutRainEntity.get(), ::NoopRenderer)
        event.registerEntityRenderer(EntityTypes.nuclearCreeper.get(), ::NuclearCreeperRenderer)
    }

    @SubscribeEvent @JvmStatic
    fun registerLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        event.registerLayerDefinition(NuclearModelLayers.STEAM_PRESS, SteamPressRenderer::createLayerDefinition)
    }

    @SubscribeEvent @JvmStatic
    fun registerTextureInAtlas(event: TextureStitchEvent.Pre) {
        if (event.atlas.location() == InventoryMenu.BLOCK_ATLAS) {
            NuclearTech.LOGGER.debug("Adding atlas textures")
            event.addSprite(ResourceLocation(NuclearTech.MODID, "block/steam_press/steam_press_head"))
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerColors(event: ColorHandlerEvent) {
        if (event is ColorHandlerEvent.Item) {
            val itemColors = event.itemColors
            for (bombKit in BombKitItem.allKits) {
                itemColors.register({ _, layer -> if (layer == 0) -1 else bombKit.color }, bombKit)
            }
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerModels(event: ModelRegistryEvent) {
        ForgeModelBakery.addSpecialModel(ResourceLocation(NuclearTech.MODID, "other/mushroom_cloud"))
    }

    @Mod.EventBusSubscriber(modid = NuclearTech.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
    object Shaders {
        lateinit var rendertypeMushroomCloudShader: ShaderInstance private set

        @SubscribeEvent @JvmStatic
        fun registerShaders(event: RegisterShadersEvent) {
            event.registerShader(ShaderInstance(event.resourceManager, ResourceLocation(NuclearTech.MODID, "rendertype_mushroom_cloud"), NuclearRenderTypes.VertexFormats.POSITION_COLOR_TEX_NORMAL)) { rendertypeMushroomCloudShader = it }
        }
    }
}
