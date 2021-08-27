package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.containers.ContainerTypes
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.entities.renderers.FalloutRainRenderer
import at.martinthedragon.nucleartech.entities.renderers.NuclearCreeperRenderer
import at.martinthedragon.nucleartech.entities.renderers.NukeExplosionRenderer
import at.martinthedragon.nucleartech.items.NuclearSpawnEggItem
import at.martinthedragon.nucleartech.screens.*
import at.martinthedragon.nucleartech.tileentities.TileEntityTypes
import at.martinthedragon.nucleartech.tileentities.renderers.SteamPressTopTileEntityRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.client.util.SearchTree
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.PlayerContainer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import java.util.stream.Stream

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {
    @SubscribeEvent(priority = EventPriority.LOWEST) // so that the actual container types get registered before this
    @JvmStatic
    fun registerScreens(event: RegistryEvent.Register<ContainerType<*>>) {
        NuclearTech.LOGGER.debug("Registering screens")
        ScreenManager.register(ContainerTypes.safeContainer.get(), ::SafeScreen)
        ScreenManager.register(ContainerTypes.sirenContainer.get(), ::SirenScreen)
        ScreenManager.register(ContainerTypes.steamPressContainer.get(), ::SteamPressScreen)
        ScreenManager.register(ContainerTypes.blastFurnaceContainer.get(), ::BlastFurnaceScreen)
        ScreenManager.register(ContainerTypes.combustionGeneratorContainer.get(), ::CombustionGeneratorScreen)
        ScreenManager.register(ContainerTypes.electricFurnaceContainer.get(), ::ElectricFurnaceScreen)
        ScreenManager.register(ContainerTypes.shredderContainer.get(), ::ShredderScreen)
    }

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        NuclearTech.LOGGER.debug("Binding TERs")
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.steamPressHeadTileEntityType.get(), ::SteamPressTopTileEntityRenderer)

        NuclearTech.LOGGER.debug("Registering Entity Renderers")
        RenderingRegistry.registerEntityRenderingHandler(EntityTypes.nukeExplosionEntity.get(), ::NukeExplosionRenderer)
        RenderingRegistry.registerEntityRenderingHandler(EntityTypes.falloutRainEntity.get(), ::FalloutRainRenderer)
        RenderingRegistry.registerEntityRenderingHandler(EntityTypes.nuclearCreeperEntity.get(), ::NuclearCreeperRenderer)

        NuclearTech.LOGGER.debug("Creating search trees")
        val templateFolderSearchTree = SearchTree<ItemStack>({
            it.getTooltipLines(null, ITooltipFlag.TooltipFlags.NORMAL).stream().map { tooltip ->
                TextFormatting.stripFormatting(tooltip.string)!!.trim()
            }
        }) { Stream.of(ForgeRegistries.ITEMS.getKey(it.item)) }
        Minecraft.getInstance().searchTreeManager.register(UseTemplateFolderScreen.SEARCH_TREE, templateFolderSearchTree)

        NuclearTech.LOGGER.debug("Setting rendering layers")
        RenderTypeLookup.setRenderLayer(ModBlocks.glowingMushroom.get(), RenderType.cutout())
    }

    @SubscribeEvent @JvmStatic
    fun registerTextureInAtlas(event: TextureStitchEvent.Pre) {
        if (event.map.location() == PlayerContainer.BLOCK_ATLAS) {
            NuclearTech.LOGGER.debug("Adding atlas textures")
            event.addSprite(ResourceLocation(NuclearTech.MODID, "block/steam_press/steam_press_head"))
        }
    }

    @SubscribeEvent @JvmStatic
    fun registerColors(event: ColorHandlerEvent) {
        if (event is ColorHandlerEvent.Item) {
            val itemColors = event.itemColors
            for (spawnEgg in NuclearSpawnEggItem.resolvedMap.values) {
                itemColors.register({ _, color -> spawnEgg.getColor(color) }, spawnEgg)
            }
        }
    }
}
