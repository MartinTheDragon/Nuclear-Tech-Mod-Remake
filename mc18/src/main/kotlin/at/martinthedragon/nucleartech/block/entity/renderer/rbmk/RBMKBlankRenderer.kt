package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBlankBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKBlankRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKBlankBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKBlankBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/blank.png")
}
