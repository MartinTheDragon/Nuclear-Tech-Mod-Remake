package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKManualControlBlockEntity
import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

open class RBMKManualControlRenderer(context: BlockEntityRendererProvider.Context) : RBMKControlRenderer<RBMKManualControlBlockEntity>(context) {
    override val texture = ntm("textures/other/rbmk/manual_control.png")
}
