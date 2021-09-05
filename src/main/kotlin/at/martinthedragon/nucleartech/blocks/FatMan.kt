package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.explosions.IgnitableExplosive
import at.martinthedragon.nucleartech.tileentities.FatManTileEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateContainer
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class FatMan(properties: Properties) : Block(properties), IgnitableExplosive {
    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HorizontalBlock.FACING)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
        defaultBlockState().setValue(HorizontalBlock.FACING, context.horizontalDirection.counterClockWise)

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK
    override fun setPlacedBy(world: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        setTileEntityCustomName<FatManTileEntity>(world, pos, stack)
    }
    override fun onRemove(state: BlockState, world: World, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropTileEntityContents<FatManTileEntity>(state, world, pos, newState)
        @Suppress("DEPRECATION")
        super.onRemove(state, world, pos, newState, p_196243_5_)
    }
    override fun use(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!world.isClientSide) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is FatManTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
        }
        return ActionResultType.sidedSuccess(world.isClientSide)
    }

    override fun hasTileEntity(state: BlockState?) = true
    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = FatManTileEntity()

    override fun rotate(state: BlockState, rotation: Rotation): BlockState =
        state.setValue(HorizontalBlock.FACING, rotation.rotate(state.getValue(HorizontalBlock.FACING)))

    @Suppress("DEPRECATION")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState =
        state.rotate(mirror.getRotation(state.getValue(HorizontalBlock.FACING)))

    companion object {
        val requiredComponents = mapOf(
            ModItems.bundleOfImplosionPropellant to 4,
            ModItems.bombIgniter to 1,
            ModItems.plutoniumCore to 1
        )
    }
}
