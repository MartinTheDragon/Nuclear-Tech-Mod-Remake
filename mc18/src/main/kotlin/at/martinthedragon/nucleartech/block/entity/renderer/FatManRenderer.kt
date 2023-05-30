package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.FatManBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class FatManRenderer(context: BlockEntityRendererProvider.Context) : RotatedBlockEntityRenderer<FatManBlockEntity>(context) {
    override fun getModel(blockEntity: FatManBlockEntity) = SpecialModels.FAT_MAN.get()
}
