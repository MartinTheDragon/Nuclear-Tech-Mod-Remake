package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.api.block.entities.createSidedTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKModeratedControlBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

class RBMKModeratedControlBlock(column: Supplier<out RBMKColumnBlock>, properties: Properties) : RBMKManualControlBlock(column, properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKModeratedControlBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = createSidedTickerChecked(level.isClientSide, type, BlockEntityTypes.rbmkModeratedControlBlockEntityType.get())
}
