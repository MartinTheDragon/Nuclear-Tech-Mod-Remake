package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.LargeCoolingTowerBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class LargeCoolingTowerRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<LargeCoolingTowerBlockEntity>(context) {
    override fun getModel(blockEntity: LargeCoolingTowerBlockEntity) = SpecialModels.COOLING_TOWER_LARGE.get()
}
