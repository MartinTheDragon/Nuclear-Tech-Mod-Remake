package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.tileentities.BlastFurnaceTileEntity
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.block.material.PushReaction
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.InventoryHelper
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
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks
import java.util.*

class BlastFurnace(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(LIT, false))
    }

    override fun use(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!world.isClientSide) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is BlastFurnaceTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
        }
        return ActionResultType.sidedSuccess(world.isClientSide)
    }

    override fun getPistonPushReaction(p_149656_1_: BlockState) = PushReaction.BLOCK

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING, LIT)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState =
        defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)

    override fun setPlacedBy(world: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomHoverName()) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is BlastFurnaceTileEntity)
                tileEntity.customName = stack.hoverName
        }
    }

    override fun onRemove(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        p_196243_5_: Boolean
    ) {
        if (!state.`is`(newState.block)) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is BlastFurnaceTileEntity) {
                InventoryHelper.dropContents(world, pos, tileEntity)
                tileEntity.getRecipesToAwardAndPopExperience(world, Vector3d.atCenterOf(pos))
            }
        }
        @Suppress("DEPRECATION")
        super.onRemove(state, world, pos, newState, p_196243_5_)
    }

    override fun stepOn(world: World, pos: BlockPos, entity: Entity) {
        if (!entity.fireImmune() && world.getBlockState(pos).getValue(LIT) && entity is LivingEntity && !EnchantmentHelper.hasFrostWalker(entity)) {
            entity.hurt(DamageSource.HOT_FLOOR, 2F)
        }
    }

    override fun animateTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state.getValue(LIT)) {
            val posX = pos.x + .5
            val posY = pos.y
            val posZ = pos.z + .5

            // front particles
            val direction: Direction = state.getValue(AbstractFurnaceBlock.FACING)
            val axis = direction.axis
            val d4: Double = random.nextDouble() * 0.6 - 0.3
            val d5 = if (axis == Direction.Axis.X) direction.stepX.toDouble() * 0.52 else d4
            val d6: Double = random.nextDouble() * .5 // * 8.0 / 16.0
            val d7 = if (axis == Direction.Axis.Z) direction.stepZ.toDouble() * 0.52 else d4
            world.addParticle(ParticleTypes.SMOKE, posX + d5, posY + .25 + d6, posZ + d7, 0.0, 0.0, 0.0)
            world.addParticle(ParticleTypes.FLAME, posX + d5, posY + .25 + d6, posZ + d7, 0.0, 0.0, 0.0)

            // top particles
            val topParticleX = random.nextDouble() * .8 + .1
            val topParticleZ = random.nextDouble() * .8 + .1
            world.addParticle(ParticleTypes.SMOKE, pos.x + topParticleX, posY + 1.0, pos.z + topParticleZ, 0.0, 0.05, 0.0)
            world.addParticle(ParticleTypes.LAVA, pos.x + topParticleX, posY + 1.0, pos.z + topParticleZ, random.nextDouble() * .5, 5.0E-6, random.nextDouble() * .5)
        }
    }

    override fun hasAnalogOutputSignal(state: BlockState) = true

    override fun getAnalogOutputSignal(state: BlockState, world: World, pos: BlockPos): Int =
        Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos))

    override fun rotate(state: BlockState, direction: Rotation): BlockState =
        state.setValue(FACING, direction.rotate(state.getValue(FACING)))

    @Suppress("DEPRECATION")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState =
        state.rotate(mirror.getRotation(state.getValue(FACING)))

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = BlastFurnaceTileEntity()

    companion object {
        val FACING: DirectionProperty = HorizontalBlock.FACING
        val LIT: BooleanProperty = BlockStateProperties.LIT
    }
}
