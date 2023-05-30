package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKAutoControlBlockEntity
import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKAutoControlRenderer(context: BlockEntityRendererProvider.Context) : RBMKControlRenderer<RBMKAutoControlBlockEntity>(context) {
    override val texture = ntm("textures/other/rbmk/auto_control.png")
}
