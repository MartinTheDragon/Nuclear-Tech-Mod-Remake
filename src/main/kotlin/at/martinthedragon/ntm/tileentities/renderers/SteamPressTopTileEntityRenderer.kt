package at.martinthedragon.ntm.tileentities.renderers

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.tileentities.SteamPressTopTileEntity
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.client.renderer.model.RenderMaterial
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.inventory.container.PlayerContainer
import net.minecraft.util.ResourceLocation

class SteamPressTopTileEntityRenderer(tileEntityRendererDispatcher: TileEntityRendererDispatcher) :
    TileEntityRenderer<SteamPressTopTileEntity>(tileEntityRendererDispatcher) {

    private val pressHead = ModelRenderer(64, 64, 0, 0)

    init {
        pressHead.addBox(-3.5F, -15F, -3.5F, 7F, 15F, 7F)
        pressHead.setPos(8F, 15F, 8F)
        val pressPlate = ModelRenderer(64, 64, 0, 22)
        pressPlate.addBox(-5.5F, -2F, -5.5F, 11F, 2F, 11F)
        pressPlate.setPos(0F, -15F, 0F)
        pressHead.addChild(pressPlate)
    }

    override fun render(
        tileEntity: SteamPressTopTileEntity,
        partialTicks: Float,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        light: Int,
        otherLight: Int
    ) {
        // TODO animation
        val position = 15F
        pressHead.y = position
        val vertexBuilder = PRESS_HEAD_RESOURCE_LOCATION.buffer(buffer, RenderType::entitySolid)
        pressHead.render(matrixStack, vertexBuilder, light, otherLight)
    }

    companion object {
        val PRESS_HEAD_RESOURCE_LOCATION = RenderMaterial(PlayerContainer.BLOCK_ATLAS, ResourceLocation(Main.MODID, "block/steam_press/steam_press_head"))
    }
}
