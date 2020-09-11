package at.martinthedragon.ntm.screens

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.containers.SirenContainer
import at.martinthedragon.ntm.items.SirenTrack
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.items.CapabilityItemHandler

class SirenScreen(
        container: SirenContainer,
        playerInventory: PlayerInventory,
        title: ITextComponent
) : ContainerScreen<SirenContainer>(container, playerInventory, title) {
    private var trackName: ITextComponent = StringTextComponent("")
    private var trackType: ITextComponent = StringTextComponent("")
    private var trackRange: ITextComponent = StringTextComponent("")
    private val texture = ResourceLocation(Main.MODID, "textures/gui/siren.png")

    init {
        xSize = 175
        ySize = 165
    }

    override fun func_230450_a_(matrixStack: MatrixStack, param1: Float, param2: Int, param3: Int) {
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        Minecraft.getInstance().textureManager.bindTexture(texture)
        func_238474_b_(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    override fun func_230430_a_(p_230430_1_: MatrixStack, p_230430_2_: Int, p_230430_3_: Int, p_230430_4_: Float) {
        super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_)
        func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_)

        val inv = container.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(::Error)
        val sirenTrack = inv.getStackInSlot(0).item
        if (sirenTrack is SirenTrack) {
            trackName = sirenTrack.trackName
            trackType = sirenTrack.trackType
            trackRange = sirenTrack.trackRange
        } else {
            trackName = StringTextComponent("")
            trackType = StringTextComponent("")
            trackRange = StringTextComponent("")
        }

        // display track info
        func_238472_a_(p_230430_1_, field_230712_o_, trackName, field_230708_k_ / 2 + 17, field_230709_l_ / 2 - 54, 0xFFCCCC)
        func_238472_a_(p_230430_1_, field_230712_o_, trackType, field_230708_k_ / 2 + 17, field_230709_l_ / 2 - 44, 0xFFCCCC)
        func_238472_a_(p_230430_1_, field_230712_o_, trackRange, field_230708_k_ / 2 + 17, field_230709_l_ / 2 - 34, 0xFFCCCC)
    }
}
