package at.martinthedragon.ntm.blocks.advancedblocks

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.item.FallingBlockEntity
import net.minecraft.particles.BlockParticleData
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import java.util.*

class CrushedObsidianBlock(registryName: String, customProperties: CustomizedProperties) : CustomizedBlock(registryName, customProperties) {
    override fun onBlockAdded(state: BlockState, worldIn: World, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        worldIn.pendingBlockTicks.scheduleTick(pos, this, tickRate(worldIn))
    }

    override fun updatePostPlacement(stateIn: BlockState, facing: Direction, facingState: BlockState, worldIn: IWorld, currentPos: BlockPos, facingPos: BlockPos): BlockState {
        worldIn.pendingBlockTicks.scheduleTick(currentPos, this, tickRate(worldIn))
        return stateIn
    }

    override fun tick(state: BlockState, worldIn: World, pos: BlockPos, random: Random) {
        if (!worldIn.isRemote)
            checkFallable(worldIn, pos)
    }

    fun checkFallable(worldIn: World, pos: BlockPos) {
        if (canFallThrough(worldIn.getBlockState(pos.down())) && pos.y >= 0 && !worldIn.isRemote)
            worldIn.addEntity(FallingBlockEntity(worldIn, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, worldIn.getBlockState(pos)))
    }

    override fun tickRate(worldIn: IWorldReader): Int = 2

    @Suppress("UsePropertyAccessSyntax", "DEPRECATION")
    fun canFallThrough(state: BlockState): Boolean = state.isAir() || state.block == Blocks.FIRE || state.material.isLiquid || state.material.isReplaceable

    override fun animateTick(stateIn: BlockState, worldIn: World, pos: BlockPos, rand: Random) {
        if (rand.nextInt(16) == 0 && worldIn.isRemote && (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))))
            worldIn.addParticle(BlockParticleData(ParticleTypes.FALLING_DUST, stateIn),
                    pos.x + rand.nextDouble(),
                    pos.y - 0.5,
                    pos.z + rand.nextDouble(),
                    0.0, 0.0, 0.0)
    }
}