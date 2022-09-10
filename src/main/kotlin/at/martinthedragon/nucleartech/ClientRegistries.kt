package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.renderer.*
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.entity.renderer.MushroomCloudRenderer
import at.martinthedragon.nucleartech.entity.renderer.NoopRenderer
import at.martinthedragon.nucleartech.entity.renderer.NuclearCreeperRenderer
import at.martinthedragon.nucleartech.entity.renderer.SimpleMissileRenderer
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.item.BombKitItem
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.item.setIdSupplier
import at.martinthedragon.nucleartech.menu.MenuTypes
import at.martinthedragon.nucleartech.model.RandomModelLoader
import at.martinthedragon.nucleartech.particle.ContrailParticle
import at.martinthedragon.nucleartech.particle.ModParticles
import at.martinthedragon.nucleartech.particle.RubbleParticle
import at.martinthedragon.nucleartech.particle.SmokeParticle
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.rendering.NTechCapes
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import at.martinthedragon.nucleartech.rendering.NuclearRenderTypes
import at.martinthedragon.nucleartech.rendering.SpecialModels
import at.martinthedragon.nucleartech.screen.*
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.client.searchtree.ReloadableSearchTree
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.*
import net.minecraftforge.client.model.ModelLoaderRegistry
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
        MenuScreens.register(MenuTypes.anvilMenu.get(), ::AnvilScreen)
        MenuScreens.register(MenuTypes.assemblerMenu.get(), ::AssemblerScreen)
        MenuScreens.register(MenuTypes.chemPlantMenu.get(), ::ChemPlantScreen)
        MenuScreens.register(MenuTypes.littleBoyMenu.get(), ::LittleBoyScreen)
        MenuScreens.register(MenuTypes.fatManMenu.get(), ::FatManScreen)
        MenuScreens.register(MenuTypes.launchPadMenu.get(), ::LaunchPadScreen)
    }

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        NuclearTech.LOGGER.debug("Creating search trees")
        val templateFolderSearchTree = ReloadableSearchTree<ItemStack>({
            it.getTooltipLines(null, TooltipFlag.Default.NORMAL).map { tooltip ->
                ChatFormatting.stripFormatting(tooltip.string)!!.trim()
            }.stream()
        }) { Stream.of(ForgeRegistries.ITEMS.getKey(it.item)) }
        val anvilConstructingRecipeSearchTree = ReloadableSearchTree<AnvilConstructingRecipe>({
            val results = it.results.map(AnvilConstructingRecipe.ConstructingResult::stack).flatMap { stack -> stack.getTooltipLines(null, TooltipFlag.Default.NORMAL).map { tooltip -> ChatFormatting.stripFormatting(tooltip.string)!!.trim() }}

            if (it.results.size > 1 && it.ingredientsList.size == 1) {
                val recyclingSearch = results + it.ingredientsList.single().items.flatMap { stack -> stack.getTooltipLines(null, TooltipFlag.Default.NORMAL).map { tooltip -> ChatFormatting.stripFormatting(tooltip.string)!!.trim() }}
                recyclingSearch.stream()
            } else results.stream()
        }) { it.results.map(AnvilConstructingRecipe.ConstructingResult::stack).map { stack -> ForgeRegistries.ITEMS.getKey(stack.item) }.stream() }

        Minecraft.getInstance().searchTreeManager.apply {
            register(UseTemplateFolderScreen.searchTree, templateFolderSearchTree)
            register(AnvilScreen.searchTree, anvilConstructingRecipeSearchTree)
        }

        NuclearTech.LOGGER.debug("Setting rendering layers")
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.glowingMushroom.get(), RenderType.cutout())
        for ((source, flowing, _, _) in NTechFluids.getFluidsList()) {
            ItemBlockRenderTypes.setRenderLayer(source.get(), RenderType.translucent())
            ItemBlockRenderTypes.setRenderLayer(flowing.get(), RenderType.translucent())
        }

        NuclearTech.LOGGER.debug("Registering item properties")
        event.enqueueWork {
            ItemProperties.register(NTechItems.assemblyTemplate.get(), ntm("shift")) { _, _, _, _ -> if (Screen.hasShiftDown()) 1F else 0F }
            ItemProperties.register(NTechItems.chemTemplate.get(), ntm("shift")) { _, _, _, _ -> if (Screen.hasShiftDown()) 1F else 0F }
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerEntityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        NuclearTech.LOGGER.debug("Registering BERs")
        with(event) {
            registerBlockEntityRenderer(BlockEntityTypes.assemblerBlockEntityType.get(), ::AssemblerRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.chemPlantBlockEntityType.get(), ::ChemPlantRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.fatManBlockEntityType.get(), ::FatManRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.launchPadBlockEntityType.get(), ::LaunchPadRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.littleBoyBlockEntityType.get(), ::LittleBoyRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.steamPressHeadBlockEntityType.get(), ::SteamPressRenderer)
        }

        NuclearTech.LOGGER.debug("Registering Entity Renderers")
        with(event) {
            registerEntityRenderer(EntityTypes.nuclearExplosion.get(), ::NoopRenderer)
            registerEntityRenderer(EntityTypes.mushroomCloud.get(), ::MushroomCloudRenderer)
            registerEntityRenderer(EntityTypes.falloutRain.get(), ::NoopRenderer)
            registerEntityRenderer(EntityTypes.nuclearCreeper.get(), ::NuclearCreeperRenderer)

            registerEntityRenderer(EntityTypes.missileHE.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileIncendiary.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileCluster.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileBunkerBuster.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileHEStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileIncendiaryStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileClusterStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileBunkerBusterStrong.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileBurst.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileInferno.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileRain.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileDrill.get(), ::SimpleMissileRenderer)
            registerEntityRenderer(EntityTypes.missileNuclear.get(), ::SimpleMissileRenderer)

            registerEntityRenderer(EntityTypes.clusterFragment.get(), ::ThrownItemRenderer)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        with(event) {
            registerLayerDefinition(NuclearModelLayers.RUBBLE, RubbleParticle.RubbleModel::createLayerDefinition)
            registerLayerDefinition(NuclearModelLayers.STEAM_PRESS, SteamPressRenderer::createLayerDefinition)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerTextureInAtlas(event: TextureStitchEvent.Pre) {
        if (event.atlas.location() == InventoryMenu.BLOCK_ATLAS) {
            NuclearTech.LOGGER.debug("Adding atlas textures")
            event.addSprite(ntm("block/steam_press/steam_press_head"))
            ChemPlantTemplateItem.getChemistrySpriteLocations(Minecraft.getInstance().resourceManager).forEach(event::addSprite)
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
        NuclearTech.LOGGER.debug("Registering custom model loaders")
        ModelLoaderRegistry.registerLoader(ntm("random"), RandomModelLoader)

        NuclearTech.LOGGER.debug("Registering random model id suppliers")
        RandomModelLoader.setIdSupplier(NTechItems.polaroid)

//        ForgeModelBakery.addSpecialModel(ntm("other/assembler"))
//        ForgeModelBakery.addSpecialModel(ntm("other/mushroom_cloud"))
    }

    @SubscribeEvent @JvmStatic
    fun bakeExtraModels(event: ModelBakeEvent) {
        NuclearTech.LOGGER.debug("Baking additional models")
        ChemPlantTemplateItem.generateTemplateIcons(event.modelLoader)
    }

    @SubscribeEvent @JvmStatic
    fun registerResourceReloadListeners(event: RegisterClientReloadListenersEvent) {
        event.registerReloadListener(SpecialModels)

        val capeTextures = NTechCapes.CapeTextureManager(Minecraft.getInstance().textureManager)
        NTechCapes.capeTextures = capeTextures
        event.registerReloadListener(capeTextures)
    }

    @SubscribeEvent @JvmStatic
    fun registerParticleProviders(event: ParticleFactoryRegisterEvent) {
        with(Minecraft.getInstance().particleEngine) {
            register(ModParticles.CONTRAIL.get(), ContrailParticle::Provider)
            register(ModParticles.RUBBLE.get(), RubbleParticle.Provider())
            register(ModParticles.SMOKE.get(), SmokeParticle::Provider)
        }
    }

    // uncomment, compile a snapshot, put it in the mods folder of your logged-in minecraft instance, start it and see the console log
//    @SubscribeEvent @JvmStatic
//    fun grabAuthToken(event: FMLClientSetupEvent) {
//        println("AUTH_TOKEN ${Minecraft.getInstance().user.accessToken}")
//        println("UUID ${Minecraft.getInstance().user.uuid}")
//    }

    @Mod.EventBusSubscriber(modid = NuclearTech.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
    object Shaders {
        lateinit var rendertypeMushroomCloudShader: ShaderInstance private set

        @SubscribeEvent @JvmStatic
        fun registerShaders(event: RegisterShadersEvent) {
            event.registerShader(ShaderInstance(event.resourceManager, ntm("rendertype_mushroom_cloud"), NuclearRenderTypes.VertexFormats.POSITION_COLOR_TEX_NORMAL)) { rendertypeMushroomCloudShader = it }
        }
    }
}
