package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBoilerBlockEntity
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKBoilerRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKBoilerBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKBoilerBlockEntity) = SpecialModels.RBMK_CONTROL_ROD_COLUMN.get()
    override fun getLidModel(blockEntity: RBMKBoilerBlockEntity) = if (blockEntity.blockState.getValue(RBMKBaseBlock.LID_TYPE).seeThrough()) SpecialModels.RBMK_LID.get() else SpecialModels.RBMK_CONTROL_ROD_LID.get()
    override val texture = ntm("textures/other/rbmk/boiler.png")
    override val glassLidTexture = ntm("textures/other/rbmk/boiler_glass.png")
}
