package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.blocks.entities.BlockEntityTypes
import at.martinthedragon.nucleartech.blocks.entities.CombustionGeneratorBlockEntity
import at.martinthedragon.nucleartech.blocks.entities.createServerTickerChecked
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.network.NetworkHooks
import java.util.*

class CombustionGenerator(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH).setValue(BlockStateProperties.LIT, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(HorizontalDirectionalBlock.FACING, BlockStateProperties.LIT) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.horizontalDirection.opposite)
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setBlockEntityCustomName<CombustionGeneratorBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropBlockEntityContents<CombustionGeneratorBlockEntity>(state, level, pos, newState)
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        if (!level.isClientSide) {
            if (!FluidUtil.interactWithFluidHandler(player, hand, level, pos, hit.direction)) {
                val blockEntity = level.getBlockEntity(pos)
                if (blockEntity is CombustionGeneratorBlockEntity) NetworkHooks.openGui(player as ServerPlayer, blockEntity, pos)
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: Random) {
        if (state.getValue(BlockStateProperties.LIT)) {
            val posX = pos.x + 0.5
            val posY = pos.y.toDouble()
            val posZ = pos.z + 0.5
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(
                    posX, posY, posZ,
                    SoundEvents.FURNACE_FIRE_CRACKLE,
                    SoundSource.BLOCKS,
                    1.0f, 1.0f, false
                )
            }
            val direction = state.getValue(HorizontalDirectionalBlock.FACING)
            val axis = direction.axis
            val d4 = random.nextDouble() * 0.6 - 0.3
            val d5 = if (axis == Direction.Axis.X) direction.stepX.toDouble() * 0.52 else d4
            val d6 = random.nextDouble() * 6.0 / 16.0
            val d7 = if (axis == Direction.Axis.Z) direction.stepZ.toDouble() * 0.52 else d4
            level.addParticle(ParticleTypes.SMOKE, posX + d5, posY + d6, posZ + d7, 0.0, 0.0, 0.0)
            level.addParticle(ParticleTypes.FLAME, posX + d5, posY + d6, posZ + d7, 0.0, 0.0, 0.0)
        }
    }

    override fun hasAnalogOutputSignal(state: BlockState) = true
    override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos) = AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos))

    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)))
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = @Suppress("DEPRECATION") state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)))

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = CombustionGeneratorBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.combustionGeneratorBlockEntityType.get())
}
