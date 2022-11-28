package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKAbsorberBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

class RBMKAbsorberBlock(column: Supplier<out RBMKColumnBlock>, properties: Properties) : RBMKBaseBlock(column, properties) {
    override val hasMenu = false
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKAbsorberBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.rbmkAbsorberBlockEntityType.get())
}
