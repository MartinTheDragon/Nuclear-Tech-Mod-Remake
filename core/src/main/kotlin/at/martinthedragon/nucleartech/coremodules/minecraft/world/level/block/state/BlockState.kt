package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state

import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Block

interface BlockState : StateHolder<Block, BlockState> {
    val isAir: Boolean
    val block: Block

    fun isBlock(block: Block): Boolean
}
