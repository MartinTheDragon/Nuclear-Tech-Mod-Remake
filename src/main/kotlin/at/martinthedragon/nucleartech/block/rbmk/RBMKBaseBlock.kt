package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.block.dropBlockEntityContents
import at.martinthedragon.nucleartech.block.entity.rbmk.InventoryRBMKBaseBlockEntity
import at.martinthedragon.nucleartech.block.openMenu
import at.martinthedragon.nucleartech.item.RBMKLidItem
import at.martinthedragon.nucleartech.item.ScrewdriverItem
import net.minecraft.core.BlockPos
import net.minecraft.util.StringRepresentable
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import java.util.function.Supplier

// TODO more accurate collision box
abstract class RBMKBaseBlock(val column: Supplier<out RBMKColumnBlock>, properties: Properties) : BaseEntityBlock(properties), RBMKPart {
    init { registerDefaultState(stateDefinition.any().setValue(LID_TYPE, LidType.NONE)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(LID_TYPE) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(LID_TYPE, LidType.NONE)

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    abstract val hasMenu: Boolean

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        if (!hasMenu) return InteractionResult.PASS

        val itemInHand = player.getItemInHand(hand).item
        val rbmk = getTopRBMKBase(level, pos, state) ?: return InteractionResult.FAIL

        if (itemInHand is RBMKLidItem && !rbmk.hasLid())
            return InteractionResult.PASS

        if (itemInHand is ScrewdriverItem && rbmk.hasLid())
            return InteractionResult.PASS

        return openMenu<InventoryRBMKBaseBlockEntity>(level, pos, player)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, piston: Boolean) {
        if (!newState.`is`(state.block)) {
            dropBlockEntityContents<InventoryRBMKBaseBlockEntity>(state, level, pos, newState)
            if (level.getBlockState(pos.below()).block is RBMKColumnBlock) level.destroyBlock(pos.below(), false)
        }
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, piston)
    }

    abstract override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity
    abstract override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>?

    override fun getTopBlockPos(level: Level, queryPos: BlockPos, state: BlockState) = queryPos

    enum class LidType(private val serializedName: String) : StringRepresentable {
        NONE("none"),
        CONCRETE("concrete"),
        LEAD_GLASS("lead_glass");

        fun isLid() = this != NONE
        fun seeThrough() = this == NONE || this == LEAD_GLASS

        override fun getSerializedName() = serializedName
    }

    companion object {
        val LID_TYPE: EnumProperty<LidType> = EnumProperty.create("lid_type", LidType::class.java)
    }
}
