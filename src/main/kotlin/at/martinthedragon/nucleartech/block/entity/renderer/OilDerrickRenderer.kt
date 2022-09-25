package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.OilDerrickBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class OilDerrickRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<OilDerrickBlockEntity>(context) {
    override fun getModel(blockEntity: OilDerrickBlockEntity) = SpecialModels.OIL_DERRICK.get()
}
