package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.CentrifugeBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class CentrifugeRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<CentrifugeBlockEntity>(context) {
    override fun getModel(blockEntity: CentrifugeBlockEntity) = SpecialModels.CENTRIFUGE.get()
}
