package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class RBMKModeratedReaSimRodRenderer(context: BlockEntityRendererProvider.Context) : RBMKReaSimRodRenderer(context) {
    override val texture = ntm("textures/other/rbmk/moderated_reasim_rods.png")
}
