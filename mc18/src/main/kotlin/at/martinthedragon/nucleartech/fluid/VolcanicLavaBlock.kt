package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.block.NTechBlocks
import net.minecraft.core.BlockPos
import net.minecraft.tags.BlockTags
import net.minecraft.tags.FluidTags
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FlowingFluid
import net.minecraftforge.event.ForgeEventFactory
import java.util.function.Supplier

class VolcanicLavaBlock(fluidSupplier: Supplier<out FlowingFluid>, properties: Properties) : LiquidBlock(fluidSupplier, properties) {
    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, previousState: BlockState, whatever: Boolean) {
        doBlockReactions(level, pos)
        level.scheduleTick(pos, state.fluidState.type, fluid.getTickDelay(level))
    }

    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, piston: Boolean) {
        doBlockReactions(level, pos)
        level.scheduleTick(pos, state.fluidState.type, fluid.getTickDelay(level))
    }

    private fun doBlockReactions(level: Level, pos: BlockPos) {
        for (direction in POSSIBLE_FLOW_DIRECTIONS) {
            val relativePos = pos.relative(direction)
            val otherFluidState = level.getFluidState(relativePos)
            val otherBlockState = level.getBlockState(relativePos)
            val reaction = when {
                otherFluidState.`is`(FluidTags.WATER) -> Blocks.STONE.defaultBlockState()
                otherBlockState.`is`(BlockTags.LOGS_THAT_BURN) -> NTechBlocks.charredLog.get().defaultBlockState()
                otherBlockState.`is`(BlockTags.PLANKS) -> NTechBlocks.charredPlanks.get().defaultBlockState() // FIXME also includes fireproof planks
                otherBlockState.`is`(BlockTags.LEAVES) -> BaseFireBlock.getState(level, pos)
                else -> null
            }

            if (reaction != null) {
                level.setBlockAndUpdate(relativePos, ForgeEventFactory.fireFluidPlaceBlockEvent(level, relativePos, pos, reaction))
                fizz(level, pos)
            }
        }
    }

    private fun fizz(level: Level, pos: BlockPos) = level.levelEvent(LevelEvent.LAVA_FIZZ, pos, 0)
}
