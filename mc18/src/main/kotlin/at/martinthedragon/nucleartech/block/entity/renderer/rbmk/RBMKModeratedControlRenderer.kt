package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKModeratedControlRenderer(context: BlockEntityRendererProvider.Context) : RBMKManualControlRenderer(context) {
    override val texture = ntm("textures/other/rbmk/moderated_control.png")
}
