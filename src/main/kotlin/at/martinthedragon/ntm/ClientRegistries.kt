package at.martinthedragon.ntm

import at.martinthedragon.ntm.containers.ContainerTypes
import at.martinthedragon.ntm.screens.SafeScreen
import at.martinthedragon.ntm.screens.SirenScreen
import at.martinthedragon.ntm.screens.UseTemplateFolderScreen
import at.martinthedragon.ntm.tileentities.TileEntityTypes
import at.martinthedragon.ntm.tileentities.renderers.SteamPressTopTileEntityRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.client.util.SearchTree
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.PlayerContainer
import net.minecraft.item.ItemStack
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import java.util.stream.Stream

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = Main.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {
    @SubscribeEvent(priority = EventPriority.LOWEST) // so that the actual container types get registered before this
    @JvmStatic
    fun registerScreens(event: RegistryEvent.Register<ContainerType<*>>) {
        Main.LOGGER.debug("Registering screens")
        ScreenManager.register(ContainerTypes.safeContainer.get(), ::SafeScreen)
        ScreenManager.register(ContainerTypes.sirenContainer.get(), ::SirenScreen)
    }

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        Main.LOGGER.debug("Binding TERs")
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.steamPressHeadTileEntityType.get(), ::SteamPressTopTileEntityRenderer)

        Main.LOGGER.debug("Creating search trees")
        val templateFolderSearchTree = SearchTree<ItemStack>({
            it.getTooltipLines(null, ITooltipFlag.TooltipFlags.NORMAL).stream().map { tooltip ->
                TextFormatting.stripFormatting(tooltip.string)!!.trim()
            }
        }) { Stream.of(ForgeRegistries.ITEMS.getKey(it.item)) }
        Minecraft.getInstance().searchTreeManager.register(UseTemplateFolderScreen.SEARCH_TREE, templateFolderSearchTree)
    }

    @SubscribeEvent @JvmStatic
    fun registerTextureInAtlas(event: TextureStitchEvent.Pre) {
        if (event.map.location() == PlayerContainer.BLOCK_ATLAS) {
            Main.LOGGER.debug("Adding atlas textures")
            event.addSprite(ResourceLocation(Main.MODID, "block/steam_press/steam_press_head"))
        }
    }
}
