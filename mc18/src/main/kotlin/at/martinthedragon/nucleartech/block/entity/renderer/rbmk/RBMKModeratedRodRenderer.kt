package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKModeratedRodRenderer(context: BlockEntityRendererProvider.Context) : RBMKRodRenderer(context) {
    override val texture = ntm("textures/other/rbmk/moderated_rods.png")
}
