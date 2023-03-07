package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.TurbineBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class TurbineBlock(properties: Properties) : BaseEntityBlock(properties) {
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<TurbineBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setMachineCustomName<TurbineBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, piston: Boolean) {
        dropBlockEntityContents<TurbineBlockEntity>(state, level, pos, newState)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, piston)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = TurbineBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.turbineBlockEntityType.get())
}
