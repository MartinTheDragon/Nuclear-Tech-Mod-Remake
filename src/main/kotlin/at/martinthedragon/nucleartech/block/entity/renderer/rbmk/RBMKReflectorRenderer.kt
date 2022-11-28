package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKReflectorBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKReflectorRenderer(context: BlockEntityRendererProvider.Context) : RBMKElementRenderer<RBMKReflectorBlockEntity>(context) {
    override fun getModel(blockEntity: RBMKReflectorBlockEntity) = SpecialModels.RBMK_COMMON_COLUMN.get()
    override val texture = ntm("textures/other/rbmk/reflector.png")
    override val glassLidTexture = ntm("textures/other/rbmk/reflector_glass.png")
}
