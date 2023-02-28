package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.block.BlockTints
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.renderer.*
import at.martinthedragon.nucleartech.block.entity.renderer.rbmk.*
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.entity.renderer.*
import at.martinthedragon.nucleartech.extensions.getAverageColor
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.item.*
import at.martinthedragon.nucleartech.menu.MenuTypes
import at.martinthedragon.nucleartech.model.RandomModelLoader
import at.martinthedragon.nucleartech.particle.*
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.rendering.NTechCapes
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import at.martinthedragon.nucleartech.rendering.NuclearRenderTypes
import at.martinthedragon.nucleartech.rendering.SpecialModels
import at.martinthedragon.nucleartech.screen.*
import at.martinthedragon.nucleartech.screen.rbmk.*
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.entity.ItemEntityRenderer
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.client.renderer.item.ItemPropertyFunction
import net.minecraft.client.searchtree.ReloadableSearchTree
import net.minecraft.util.Mth
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.material.Fluids
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
        MenuScreens.register(MenuTypes.anvilMenu.get(), ::AnvilScreen)
        MenuScreens.register(MenuTypes.assemblerMenu.get(), ::AssemblerScreen)
        MenuScreens.register(MenuTypes.blastFurnaceMenu.get(), ::BlastFurnaceScreen)
        MenuScreens.register(MenuTypes.chemPlantMenu.get(), ::ChemPlantScreen)
        MenuScreens.register(MenuTypes.centrifugeMenu.get(), ::CentrifugeScreen)
        MenuScreens.register(MenuTypes.combustionGeneratorMenu.get(), ::CombustionGeneratorScreen)
        MenuScreens.register(MenuTypes.electricFurnaceMenu.get(), ::ElectricFurnaceScreen)
        MenuScreens.register(MenuTypes.fatManMenu.get(), ::FatManScreen)
        MenuScreens.register(MenuTypes.launchPadMenu.get(), ::LaunchPadScreen)
        MenuScreens.register(MenuTypes.littleBoyMenu.get(), ::LittleBoyScreen)
        MenuScreens.register(MenuTypes.oilWellMenu.get(), ::OilWellScreen)
        MenuScreens.register(MenuTypes.rbmkAutoControlMenu.get(), ::RBMKAutoControlScreen)
        MenuScreens.register(MenuTypes.rbmkBoilerMenu.get(), ::RBMKBoilerScreen)
        MenuScreens.register(MenuTypes.rbmkConsoleMenu.get(), ::RBMKConsoleScreen)
        MenuScreens.register(MenuTypes.rbmkManualControlMenu.get(), ::RBMKManualControlScreen)
        MenuScreens.register(MenuTypes.rbmkRodMenu.get(), ::RBMKRodScreen)
        MenuScreens.register(MenuTypes.safeMenu.get(), ::SafeScreen)
        MenuScreens.register(MenuTypes.shredderMenu.get(), ::ShredderScreen)
        MenuScreens.register(MenuTypes.sirenMenu.get(), ::SirenScreen)
        MenuScreens.register(MenuTypes.steamPressMenu.get(), ::SteamPressScreen)
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
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.steelGrate.get(), RenderType.cutoutMipped())
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.glowingMushroom.get(), RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(NTechBlocks.coatedUniversalFluidDuct.get(), RenderType.cutoutMipped())
        for ((source, flowing, _, _) in NTechFluids.getFluidsList()) {
            ItemBlockRenderTypes.setRenderLayer(source.get(), RenderType.translucent())
            ItemBlockRenderTypes.setRenderLayer(flowing.get(), RenderType.translucent())
        }

        NuclearTech.LOGGER.debug("Registering item properties")
        event.enqueueWork {
            run {
                @Suppress("DEPRECATION") val pelletDepletion = ItemPropertyFunction { stack, _, _, _ -> val item = stack.item; if (item is RBMKPelletItem) item.getDepletion(stack).toFloat() else 0F }
                @Suppress("DEPRECATION") val pelletXenon = ItemPropertyFunction { stack, _, _, _ -> val item = stack.item; if (item is RBMKPelletItem && item.canHaveXenon && item.hasXenon(stack)) 1F else 0F }

                for (itemRegistryObject in RegistriesAndLifecycle.ITEMS.entries) {
                    val item = itemRegistryObject.get()
                    if (item is RBMKPelletItem) {
                        ItemProperties.register(item, ntm("depletion"), pelletDepletion)
                        if (item.canHaveXenon)
                            ItemProperties.register(item, ntm("xenon"), pelletXenon)
                    }
                }
            }
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
            registerBlockEntityRenderer(BlockEntityTypes.centrifugeBlockEntityType.get(), ::CentrifugeRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.fatManBlockEntityType.get(), ::FatManRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.launchPadBlockEntityType.get(), ::LaunchPadRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.littleBoyBlockEntityType.get(), ::LittleBoyRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.oilDerrickBlockEntityType.get(), ::OilDerrickRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.pumpjackBlockEntityType.get(), ::PumpjackRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkAbsorberBlockEntityType.get(), ::RBMKAbsorberRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkAutoControlBlockEntityType.get(), ::RBMKAutoControlRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkBlankBlockEntityType.get(), ::RBMKBlankRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkBoilerBlockEntityType.get(), ::RBMKBoilerRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkConsoleBlockEntityType.get(), ::RBMKConsoleRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkManualControlBlockEntityType.get(), ::RBMKManualControlRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkModeratedControlBlockEntityType.get(), ::RBMKModeratedControlRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkModeratedReaSimRodBlockEntityType.get(), ::RBMKModeratedReaSimRodRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkModeratedRodBlockEntityType.get(), ::RBMKModeratedRodRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkModeratorBlockEntityType.get(), ::RBMKModeratorRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkReaSimRodBlockEntityType.get(), ::RBMKReaSimRodRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkReflectorBlockEntityType.get(), ::RBMKReflectorRenderer)
            registerBlockEntityRenderer(BlockEntityTypes.rbmkRodBlockEntityType.get(), ::RBMKRodRenderer)
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
            registerEntityRenderer(EntityTypes.missileTectonic.get(), ::SimpleMissileRenderer)

            registerEntityRenderer(EntityTypes.oilSpill.get(), ::NoopRenderer)

            registerEntityRenderer(EntityTypes.clusterFragment.get(), ::ThrownItemRenderer)
            registerEntityRenderer(EntityTypes.shrapnel.get(), ::ShrapnelRenderer)
            registerEntityRenderer(EntityTypes.volcanicShrapnel.get(), ::VolcanicShrapnelRenderer)

            registerEntityRenderer(EntityTypes.rbmkDebris.get(), ::RBMKDebrisRenderer)

            registerEntityRenderer(EntityTypes.meteor.get(), ::MeteorRenderer)

            registerEntityRenderer(EntityTypes.wasteItemEntity.get(), ::ItemEntityRenderer)
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerLayerDefinitions(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        with(event) {
            registerLayerDefinition(NuclearModelLayers.METEOR, MeteorRenderer.MeteorModel::createLayerDefinition)
            registerLayerDefinition(NuclearModelLayers.RUBBLE, RubbleParticle.RubbleModel::createLayerDefinition)
            registerLayerDefinition(NuclearModelLayers.SHRAPNEL, ShrapnelRenderer.ShrapnelModel::createLayerDefinition)
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
        if (event is ColorHandlerEvent.Block) {
            val blockColors = event.blockColors
            blockColors.register(
                { _, level, pos, _ ->
                    if (level == null || pos == null) -1
                    else level.getBlockTint(pos, BlockTints.FLUID_DUCT_COLOR_RESOLVER)
                }, NTechBlocks.coatedUniversalFluidDuct.get()
            )
        } else if (event is ColorHandlerEvent.Item) {
            val itemColors = event.itemColors
            itemColors.register({ stack, layer ->
                if (layer == 0) -1 else {
                    val fluid = FluidIdentifierItem.getFluid(stack)
                    if (fluid.isSame(Fluids.EMPTY)) return@register -1
                    val texture = fluid.attributes.stillTexture
                    try {
                        val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture)
                        val baseColor = sprite.getAverageColor(0, 0, 0, 15, 15) and 0xFFFFFF
                        Mth.colorMultiply(baseColor, fluid.attributes.color)
                    } catch (ex: Exception) {
                        NuclearTech.LOGGER.error("Couldn't sample fluid texture $texture for tinting fluid identifier", ex)
                        -1
                    }
                }
            }, NTechItems.fluidIdentifier.get())
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
            register(ModParticles.MINI_NUKE_CLOUD.get(), MiniNukeCloudParticle::Provider)
            register(ModParticles.MINI_NUKE_CLOUD_BALEFIRE.get(), MiniNukeCloudParticle::Provider)
            register(ModParticles.MINI_NUKE_FLASH.get(), MiniNukeFlashParticle::Provider)
            register(ModParticles.OIL_SPILL.get(), OilSpillParticle::Provider)
            register(ModParticles.RBMK_FIRE.get(), RBMKFireParticle::Provider)
            register(ModParticles.RBMK_MUSH.get(), RBMKMushParticle::Provider)
            register(ModParticles.ROCKET_FLAME.get(), RocketFlameParticle::Provider)
            register(ModParticles.RUBBLE.get(), RubbleParticle.Provider())
            register(ModParticles.SHOCKWAVE.get(), ShockwaveParticle::Provider)
            register(ModParticles.SMOKE.get(), SmokeParticle::Provider)
            register(ModParticles.VOLCANO_SMOKE.get(), VolcanoSmokeParticle::Provider)
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
