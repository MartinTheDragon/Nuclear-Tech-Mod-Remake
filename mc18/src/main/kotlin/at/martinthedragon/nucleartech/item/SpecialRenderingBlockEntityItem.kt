package at.martinthedragon.nucleartech.item

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

interface SpecialRenderingBlockEntityItem {
    val blockStateForRendering: BlockState
    val blockEntityFunc: (pos: BlockPos, state: BlockState) -> BlockEntity
}
