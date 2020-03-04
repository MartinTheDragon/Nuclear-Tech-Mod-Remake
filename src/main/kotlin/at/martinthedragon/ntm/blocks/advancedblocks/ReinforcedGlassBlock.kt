package at.martinthedragon.ntm.blocks.advancedblocks

import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader

class ReinforcedGlassBlock(registryName: String, customProperties: CustomizedProperties) : CustomizedBlock(registryName, customProperties) {
    @Suppress("DEPRECATION")
    override fun isSideInvisible(state: BlockState, adjacentBlockState: BlockState, side: Direction): Boolean =
            if (adjacentBlockState.block == this) true else super.isSideInvisible(state, adjacentBlockState, side)

    override fun propagatesSkylightDown(state: BlockState, reader: IBlockReader, pos: BlockPos): Boolean = true
    override fun causesSuffocation(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean = false
    override fun isNormalCube(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean = false
    override fun canEntitySpawn(state: BlockState, worldIn: IBlockReader, pos: BlockPos, type: EntityType<*>): Boolean = false
    override fun getRenderLayer(): BlockRenderLayer = BlockRenderLayer.CUTOUT

    override fun func_220080_a(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Float = 1.toFloat()
}