package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBoilerBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

class RBMKBoilerColumnBlock(properties: Properties) : RBMKColumnBlock(properties), EntityBlock {
    init { registerDefaultState(stateDefinition.any().setValue(LEVEL, 1).setValue(IO, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(IO)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = if (state.getValue(IO)) RBMKBoilerBlockEntity.WaterInputBlockEntity(pos, state) else null
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? =
        if (!state.getValue(IO)) null else if (level.isClientSide) null
        else createServerTickerChecked(type, BlockEntityTypes.rbmkBoilerWaterInputBlockEntityType.get())

    companion object {
        val IO: BooleanProperty = BooleanProperty.create("io")
    }
}
