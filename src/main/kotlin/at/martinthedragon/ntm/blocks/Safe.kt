package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.tileentities.SafeTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateContainer
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class Safe(properties: Properties) : Block(properties) {
    init {
        defaultState = stateContainer.baseState.with(facing, Direction.NORTH)
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!worldIn.isRemote) {
            val tileEntity = worldIn.getTileEntity(pos)
            if (tileEntity is SafeTileEntity) {
                NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
            }
        }
        return ActionResultType.SUCCESS
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(facing)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
            defaultState.with(facing, context.placementHorizontalFacing.opposite)

    override fun hasTileEntity(state: BlockState?) = true
    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SafeTileEntity()

    // for custom names
    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasDisplayName()) {
            val tileEntity = worldIn.getTileEntity(pos)
            if (tileEntity is SafeTileEntity) {
                tileEntity.customName = stack.displayName
            }
        }
    }

    companion object {
        val facing = HorizontalBlock.HORIZONTAL_FACING
    }
}
