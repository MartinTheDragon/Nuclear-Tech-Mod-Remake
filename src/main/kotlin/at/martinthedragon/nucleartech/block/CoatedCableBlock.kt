package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.CableBlockEntity
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class CoatedCableBlock(properties: Properties) : BaseEntityBlock(properties) {
    init {
        registerDefaultState(stateDefinition.any()
            .setValue(BlockStateProperties.UP, false)
            .setValue(BlockStateProperties.DOWN, false)
            .setValue(BlockStateProperties.NORTH, false)
            .setValue(BlockStateProperties.EAST, false)
            .setValue(BlockStateProperties.SOUTH, false)
            .setValue(BlockStateProperties.WEST, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.UP, BlockStateProperties.DOWN, BlockStateProperties.NORTH, BlockStateProperties.EAST, BlockStateProperties.SOUTH, BlockStateProperties.WEST)
    }

    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    // can't use onPlace: happens before BlockEntity world registration
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is CableBlockEntity) blockEntity.placeInWorld()
    }

    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, neighbor: Block, neighborPos: BlockPos, p_60514_: Boolean) {
        handleNeighborChange(level, pos, neighborPos)
    }

    override fun onNeighborChange(state: BlockState, level: LevelReader, pos: BlockPos, neighbor: BlockPos) {
        handleNeighborChange(level, pos, neighbor)
    }

    private fun handleNeighborChange(level: LevelReader, pos: BlockPos, neighbor: BlockPos) {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is CableBlockEntity) {
            val (x, y, z) = pos
            val (nx, ny, nz) = neighbor
            val direction = Direction.getNearest((nx - x).toFloat(), (ny - y).toFloat(), (nz - z).toFloat())
            blockEntity.neighborChanged(direction)
        }
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = CableBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.cableBlockEntity.get())
}
