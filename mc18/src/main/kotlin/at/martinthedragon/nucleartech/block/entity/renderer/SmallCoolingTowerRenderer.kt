package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.SmallCoolingTowerBlockEntity
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class SmallCoolingTowerRenderer(context: BlockEntityRendererProvider.Context) : GenericBlockEntityRenderer<SmallCoolingTowerBlockEntity>(context) {
    override fun getModel(blockEntity: SmallCoolingTowerBlockEntity) = SpecialModels.COOLING_TOWER_SMALL.get()
}
