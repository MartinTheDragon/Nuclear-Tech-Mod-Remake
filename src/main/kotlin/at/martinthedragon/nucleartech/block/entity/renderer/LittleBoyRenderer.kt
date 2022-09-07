package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.LittleBoyBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class LittleBoyRenderer(context: BlockEntityRendererProvider.Context) : RotatedBlockEntityRenderer<LittleBoyBlockEntity>(context) {
    override fun getModel(blockEntity: LittleBoyBlockEntity) = SpecialModels.LITTLE_BOY.get()
}
