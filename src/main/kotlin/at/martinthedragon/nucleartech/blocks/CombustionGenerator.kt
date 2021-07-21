package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.tileentities.CombustionGeneratorTileEntity
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
import net.minecraft.particles.ParticleTypes
import net.minecraft.state.BooleanProperty
import net.minecraft.state.DirectionProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.fml.network.NetworkHooks
import java.util.*

class CombustionGenerator(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(LIT, false))
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING, LIT)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
        defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)

    override fun setPlacedBy(world: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        setTileEntityCustomName<CombustionGeneratorTileEntity>(world, pos, stack)
    }

    override fun onRemove(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        p_196243_5_: Boolean
    ) {
        dropTileEntityContents<CombustionGeneratorTileEntity>(state, world, pos, newState)
        @Suppress("DEPRECATION")
        super.onRemove(state, world, pos, newState, p_196243_5_)
    }

    override fun use(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!world.isClientSide) {
            if (!FluidUtil.interactWithFluidHandler(player, hand, world, pos, hit.direction)) {
                val tileEntity = world.getBlockEntity(pos)
                if (tileEntity is CombustionGeneratorTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
            }
        }
        return ActionResultType.sidedSuccess(world.isClientSide)
    }

    override fun animateTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state.getValue(LIT)) {
            val posX = pos.x + 0.5
            val posY = pos.y.toDouble()
            val posZ = pos.z + 0.5
            if (random.nextDouble() < 0.1) {
                world.playLocalSound(
                    posX, posY, posZ,
                    SoundEvents.FURNACE_FIRE_CRACKLE,
                    SoundCategory.BLOCKS,
                    1.0f, 1.0f, false
                )
            }
            val direction = state.getValue(FACING)
            val axis = direction.axis
            val d4 = random.nextDouble() * 0.6 - 0.3
            val d5 = if (axis == Direction.Axis.X) direction.stepX.toDouble() * 0.52 else d4
            val d6 = random.nextDouble() * 6.0 / 16.0
            val d7 = if (axis == Direction.Axis.Z) direction.stepZ.toDouble() * 0.52 else d4
            world.addParticle(ParticleTypes.SMOKE, posX + d5, posY + d6, posZ + d7, 0.0, 0.0, 0.0)
            world.addParticle(ParticleTypes.FLAME, posX + d5, posY + d6, posZ + d7, 0.0, 0.0, 0.0)
        }
    }

    override fun hasAnalogOutputSignal(state: BlockState) = true

    override fun getAnalogOutputSignal(state: BlockState, world: World, pos: BlockPos) =
        Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos))

    override fun getLightValue(state: BlockState, world: IBlockReader, pos: BlockPos) =
        if (state.getValue(LIT)) 13 else 0

    override fun rotate(state: BlockState, direction: Rotation): BlockState =
        state.setValue(FACING, direction.rotate(state.getValue(FACING)))

    @Suppress("DEPRECATION")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState =
        state.rotate(mirror.getRotation(state.getValue(FACING)))

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = CombustionGeneratorTileEntity()

    companion object {
        val FACING: DirectionProperty = HorizontalBlock.FACING
        val LIT: BooleanProperty = BlockStateProperties.LIT
    }
}
