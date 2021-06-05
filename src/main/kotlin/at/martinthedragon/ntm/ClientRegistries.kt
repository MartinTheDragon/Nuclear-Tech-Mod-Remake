package at.martinthedragon.ntm

import at.martinthedragon.ntm.containers.ContainerTypes
import at.martinthedragon.ntm.screens.SafeScreen
import at.martinthedragon.ntm.screens.SirenScreen
import at.martinthedragon.ntm.tileentities.TileEntityTypes
import at.martinthedragon.ntm.tileentities.renderers.SteamPressTopTileEntityRenderer
import net.minecraft.client.gui.ScreenManager
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.PlayerContainer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = Main.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {
    @SubscribeEvent(priority = EventPriority.LOWEST) // so that the actual container types get registered before this
    @JvmStatic
    fun registerScreens(event: RegistryEvent.Register<ContainerType<*>>) {
        Main.logger.debug("Registering screens")
        ScreenManager.register(ContainerTypes.safeContainer.get(), ::SafeScreen)
        ScreenManager.register(ContainerTypes.sirenContainer.get(), ::SirenScreen)
    }

    @SubscribeEvent @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
        Main.logger.debug("Binding TERs")
        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.steamPressHeadTileEntityType.get(), ::SteamPressTopTileEntityRenderer)
    }

    @SubscribeEvent @JvmStatic
    fun registerTextureInAtlas(event: TextureStitchEvent.Pre) {
        if (event.map.location() == PlayerContainer.BLOCK_ATLAS) {
            Main.logger.debug("Adding atlas textures")
            event.addSprite(ResourceLocation(Main.MODID, "blocks/machines/steam_press/steam_press_head"))
        }
    }
}
