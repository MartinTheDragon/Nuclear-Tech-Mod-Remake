package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKSteamConnectorBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

class RBMKSteamConnectorBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKSteamConnectorBlockEntity(pos, state)
}
