package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.tileentities.SafeTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.state.DirectionProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

// TODO when adding keys and locks make them work with safes
class Safe(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun use(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!worldIn.isClientSide) {
            val tileEntity = worldIn.getBlockEntity(pos)
            if (tileEntity is SafeTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
            return ActionResultType.CONSUME
        }
        return ActionResultType.SUCCESS
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
            defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SafeTileEntity()

    // for custom names
    override fun setPlacedBy(worldIn: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomHoverName()) {
            val tileEntity = worldIn.getBlockEntity(pos)
            if (tileEntity is SafeTileEntity) {
                tileEntity.customName = stack.hoverName
            }
        }
    }

    override fun onRemove(oldState: BlockState, world: World, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        if (!oldState.`is`(newState.block)) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is SafeTileEntity) {
                InventoryHelper.dropContents(world, pos, tileEntity)
            }
        }

        @Suppress("DEPRECATION")
        super.onRemove(oldState, world, pos, newState, p_196243_5_)
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    override fun rotate(state: BlockState, rotation: Rotation): BlockState {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)))
    }

    override fun mirror(state: BlockState, mirror: Mirror): BlockState {
        @Suppress("DEPRECATION")
        return state.rotate(mirror.getRotation(state.getValue(FACING)))
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    companion object {
        val FACING: DirectionProperty = HorizontalBlock.FACING
    }
}
