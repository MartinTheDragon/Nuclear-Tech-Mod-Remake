package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.api.blocks.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.api.world.dropExperience
import at.martinthedragon.nucleartech.blocks.entities.BlastFurnaceBlockEntity
import at.martinthedragon.nucleartech.blocks.entities.BlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.InteractionHand
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import java.util.*

class BlastFurnaceBlock(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH).setValue(BlockStateProperties.LIT, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(HorizontalDirectionalBlock.FACING, BlockStateProperties.LIT) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.horizontalDirection.opposite)
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMenu<BlastFurnaceBlockEntity>(level, pos, player)
    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) = setBlockEntityCustomName<BlastFurnaceBlockEntity>(level, pos, stack)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropBlockEntityContents<BlastFurnaceBlockEntity>(state, level, pos, newState) { level.dropExperience(Vec3.atCenterOf(pos), it.getExperienceToDrop(null)) }
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_196243_5_)
    }

    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!entity.fireImmune() && level.getBlockState(pos).getValue(BlockStateProperties.LIT) && entity is LivingEntity && !EnchantmentHelper.hasFrostWalker(entity))
            entity.hurt(DamageSource.HOT_FLOOR, 2F)
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: Random) {
        if (state.getValue(BlockStateProperties.LIT)) {
            val posX = pos.x + .5
            val posY = pos.y
            val posZ = pos.z + .5

            // front particles
            val direction: Direction = state.getValue(HorizontalDirectionalBlock.FACING)
            val axis = direction.axis
            val d4: Double = random.nextDouble() * 0.6 - 0.3
            val d5 = if (axis == Direction.Axis.X) direction.stepX.toDouble() * 0.52 else d4
            val d6: Double = random.nextDouble() * .5 // * 8.0 / 16.0
            val d7 = if (axis == Direction.Axis.Z) direction.stepZ.toDouble() * 0.52 else d4
            level.addParticle(ParticleTypes.SMOKE, posX + d5, posY + .25 + d6, posZ + d7, 0.0, 0.0, 0.0)
            level.addParticle(ParticleTypes.FLAME, posX + d5, posY + .25 + d6, posZ + d7, 0.0, 0.0, 0.0)

            // top particles
            val topParticleX = random.nextDouble() * .8 + .1
            val topParticleZ = random.nextDouble() * .8 + .1
            level.addParticle(ParticleTypes.SMOKE, pos.x + topParticleX, posY + 1.0, pos.z + topParticleZ, 0.0, 0.05, 0.0)
            level.addParticle(ParticleTypes.LAVA, pos.x + topParticleX, posY + 1.0, pos.z + topParticleZ, random.nextDouble() * .5, 5.0E-6, random.nextDouble() * .5)
        }
    }

    override fun hasAnalogOutputSignal(state: BlockState) = true
    override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos) = AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos))

    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)))
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = @Suppress("DEPRECATION") state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)))

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = BlastFurnaceBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.blastFurnaceBlockEntityType.get())
}
