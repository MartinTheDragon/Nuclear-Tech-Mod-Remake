package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.blocks.entities.BlockEntityTypes
import at.martinthedragon.nucleartech.blocks.entities.ShredderBlockEntity
import at.martinthedragon.nucleartech.blocks.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.world.dropExperience
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3

class Shredder(properties: Properties) : BaseEntityBlock(properties) {
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<ShredderBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setBlockEntityCustomName<ShredderBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropBlockEntityContents<ShredderBlockEntity>(state, level, pos, newState) { level.dropExperience(Vec3.atCenterOf(pos), it.getExperienceToDrop(null)) }
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun hasAnalogOutputSignal(state: BlockState) = true
    override fun getAnalogOutputSignal(state: BlockState, world: Level, pos: BlockPos) = AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos))

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = ShredderBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.shredderBlockEntityType.get())
}
