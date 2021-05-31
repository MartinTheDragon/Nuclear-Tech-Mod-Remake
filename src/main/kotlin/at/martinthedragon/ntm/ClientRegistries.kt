package at.martinthedragon.ntm

import at.martinthedragon.ntm.containers.ContainerTypes
import at.martinthedragon.ntm.screens.SafeScreen
import at.martinthedragon.ntm.screens.SirenScreen
import net.minecraft.client.gui.ScreenManager
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = Main.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegistries {
    @SubscribeEvent
    @JvmStatic
    fun registerScreens(event: RegistryEvent.Register<ContainerType<*>>) {
        ScreenManager.register(ContainerTypes.safeContainer, ::SafeScreen)
        ScreenManager.register(ContainerTypes.sirenContainer, ::SirenScreen)
    }

    @SubscribeEvent
    @JvmStatic
    fun clientSetup(event: FMLClientSetupEvent) {
//        TODO
//        @Suppress("UNCHECKED_CAST")
//        ClientRegistry.bindTileEntityRenderer(TileEntityTypes.steamPressHeadTileEntityType as TileEntityType<SteamPressHeadTileEntity>) {
//            object : TileEntityRenderer<SteamPressHeadTileEntity>(it) {
//                override fun render(tileEntityIn: SteamPressHeadTileEntity, partialTicks: Float, matrixStackIn: MatrixStack, bufferIn: IRenderTypeBuffer, combinedLightIn: Int, combinedOverlayIn: Int) {
//                }
//            }
//        }
    }
}
