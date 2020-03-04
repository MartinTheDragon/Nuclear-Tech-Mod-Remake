package at.martinthedragon.ntm.blocks.advancedblocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.RedstoneTorchBlock
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class ReinforcedRedstoneLampBlock(registryName: String, customProperties: CustomizedProperties) : CustomizedBlock(registryName, customProperties) {
    init {
        defaultState = defaultState.with(LIT, false)
    }

    companion object {
        val LIT: BooleanProperty = RedstoneTorchBlock.LIT
    }

    @Suppress("DEPRECATION")
    override fun getLightValue(state: BlockState): Int = if(state.get(LIT)) super.getLightValue(state) else 0

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? =
            defaultState.with(LIT, context.world.isBlockPowered(context.pos))

    override fun neighborChanged(state: BlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos, isMoving: Boolean) {
        if (!worldIn.isRemote && state.get(LIT) != worldIn.isBlockPowered(pos))
            if (state.get(LIT))
                worldIn.pendingBlockTicks.scheduleTick(pos, this, 4)
            else
                worldIn.setBlockState(pos, state.cycle(LIT), 2)
    }

    override fun tick(state: BlockState, worldIn: World, pos: BlockPos, random: Random) {
        if (!worldIn.isRemote && state.get(LIT) && !worldIn.isBlockPowered(pos))
            worldIn.setBlockState(pos, state.cycle(LIT), 2)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(LIT)
    }
}