package at.martinthedragon.ntm.blocks

import at.martinthedragon.ntm.tileentities.SirenTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
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
        defaultState = stateContainer.baseState.with(powered, false)
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!worldIn.isRemote) {
            val tileEntity = worldIn.getTileEntity(pos)
            if (tileEntity is SirenTileEntity) {
                NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
            }
        }
        return ActionResultType.SUCCESS
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(powered)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
            defaultState.with(powered, false)

    override fun neighborChanged(state: BlockState, worldIn: World, pos: BlockPos, blockIn: Block, fromPos: BlockPos, isMoving: Boolean) {
        val flag = worldIn.isBlockPowered(pos)
        if (flag != state.get(powered)) {
            val sirenTileEntity = worldIn.getTileEntity(pos)
            if (sirenTileEntity is SirenTileEntity)
                if (flag)
                    sirenTileEntity.startPlaying()
                else
                    sirenTileEntity.stopPlaying()
            worldIn.setBlockState(pos, state.with(powered, flag), 0b11)
        }
    }

    override fun onBlockHarvested(worldIn: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val sirenTileEntity = worldIn.getTileEntity(pos)
        if (sirenTileEntity is SirenTileEntity)
            sirenTileEntity.stopPlaying()
        super.onBlockHarvested(worldIn, pos, state, player)
    }

    override fun onReplaced(state: BlockState, worldIn: World, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (!state.isIn(newState.block)) {
            val sirenTileEntity = worldIn.getTileEntity(pos)
            if (sirenTileEntity is SirenTileEntity)
                sirenTileEntity.dropSirenTrack()
        }
        @Suppress("DEPRECATION")
        super.onReplaced(state, worldIn, pos, newState, isMoving)
    }

    override fun hasTileEntity(state: BlockState?) = true
    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SirenTileEntity()

    companion object {
        val powered: BooleanProperty = BlockStateProperties.POWERED
    }
}
