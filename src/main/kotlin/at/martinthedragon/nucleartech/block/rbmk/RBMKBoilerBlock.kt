package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBoilerBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

class RBMKBoilerBlock(column: Supplier<out RBMKColumnBlock>, properties: Properties) : RBMKBaseBlock(column, properties) {
    override val hasMenu = true
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = RBMKBoilerBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.rbmkBoilerBlockEntityType.get())
}
