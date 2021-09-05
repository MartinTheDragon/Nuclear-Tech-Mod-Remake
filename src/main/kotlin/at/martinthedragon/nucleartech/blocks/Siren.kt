package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.tileentities.SirenTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class Siren(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(POWERED, false))
    }

    override fun use(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!worldIn.isClientSide) {
            val tileEntity = worldIn.getBlockEntity(pos)
            if (tileEntity is SirenTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
            return ActionResultType.CONSUME
        }
        return ActionResultType.SUCCESS
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(POWERED)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
            defaultBlockState().setValue(POWERED, false)

    override fun neighborChanged(state: BlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos, isMoving: Boolean) {
        val flag = worldIn.hasNeighborSignal(pos)
        if (flag != state.getValue(POWERED)) {
            val sirenTileEntity = worldIn.getBlockEntity(pos)
            if (sirenTileEntity is SirenTileEntity)
                if (flag)
                    sirenTileEntity.startPlaying()
                else
                    sirenTileEntity.stopPlaying()
            worldIn.setBlock(pos, state.setValue(POWERED, flag), 0b11)
        }
    }

    override fun onRemove(oldState: BlockState, world: World, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        if (!oldState.`is`(newState.block)) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is SirenTileEntity)
                tileEntity.dropSirenTrack()
        }

        @Suppress("DEPRECATION")
        super.onRemove(oldState, world, pos, newState, p_196243_5_)
    }

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SirenTileEntity()

    companion object {
        @JvmField val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }
}
