package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKAbsorberBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKAbsorberRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKAbsorberBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKAbsorberBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/absorber.png")
    override val glassLidTexture = ntm("textures/other/rbmk/absorber_glass.png")
}
