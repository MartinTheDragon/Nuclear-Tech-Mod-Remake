package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKModeratorBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKModeratorRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKModeratorBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKModeratorBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/moderator.png")
    override val glassLidTexture = ntm("textures/other/rbmk/moderator_glass.png")
}
