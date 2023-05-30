package at.martinthedragon.nucleartech.block.entity.renderer.rbmk

import at.martinthedragon.nucleartech.ntm
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

open class RBMKReaSimRodRenderer(context: BlockEntityRendererProvider.Context) : RBMKRodRenderer(context) {
    override val texture = ntm("textures/other/rbmk/reasim_rods.png")
}
