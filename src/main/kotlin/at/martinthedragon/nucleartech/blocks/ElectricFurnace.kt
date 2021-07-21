package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.tileentities.ElectricFurnaceTileEntity
import at.martinthedragon.nucleartech.world.dropExperience
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.container.Container
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class ElectricFurnace(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.LIT, false))
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HorizontalBlock.FACING, BlockStateProperties.LIT)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
        defaultBlockState().setValue(HorizontalBlock.FACING, context.horizontalDirection.opposite)

    override fun setPlacedBy(world: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        setTileEntityCustomName<ElectricFurnaceTileEntity>(world, pos, stack)
    }

    override fun onRemove(state: BlockState, world: World, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropTileEntityContents<ElectricFurnaceTileEntity>(state, world, pos, newState) {
            world.dropExperience(Vector3d.atCenterOf(pos), it.getExperienceToDrop(null))
        }
        @Suppress("DEPRECATION")
        super.onRemove(state, world, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!world.isClientSide) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is ElectricFurnaceTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
        }
        return ActionResultType.sidedSuccess(world.isClientSide)
    }

    override fun getLightValue(state: BlockState, world: IBlockReader, pos: BlockPos) =
        if (state.getValue(BlastFurnace.LIT)) 13 else 0

    override fun hasAnalogOutputSignal(state: BlockState) = true

    override fun getAnalogOutputSignal(state: BlockState, world: World, pos: BlockPos) =
        Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos))

    override fun rotate(state: BlockState, direction: Rotation): BlockState =
        state.setValue(HorizontalBlock.FACING, direction.rotate(state.getValue(HorizontalBlock.FACING)))

    @Suppress("DEPRECATION")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState =
        state.rotate(mirror.getRotation(state.getValue(HorizontalBlock.FACING)))

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = ElectricFurnaceTileEntity()
}
