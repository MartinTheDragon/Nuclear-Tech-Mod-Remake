package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.menu.AnvilMenu
import at.martinthedragon.nucleartech.world.DamageSources
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.network.NetworkHooks

class AnvilBlock(val tier: Int, properties: Properties) : FallingBlock(properties), SimpleWaterloggedBlock {
    init { registerDefaultState(stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder.add(HorizontalDirectionalBlock.FACING).add(BlockStateProperties.WATERLOGGED) }
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.horizontalDirection.counterClockWise).setValue(BlockStateProperties.WATERLOGGED, context.level.getFluidState(context.clickedPos).type == Fluids.WATER)

    override fun getShape(state: BlockState, blocks: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = when (state.getValue(HorizontalDirectionalBlock.FACING)) {
        Direction.NORTH -> NORTH_SHAPE
        Direction.SOUTH -> SOUTH_SHAPE
        Direction.EAST -> EAST_SHAPE
        Direction.WEST -> WEST_SHAPE
        else -> NORTH_SHAPE
    }

    override fun getInteractionShape(state: BlockState, blocks: BlockGetter, pos: BlockPos): VoxelShape = when (state.getValue(HorizontalDirectionalBlock.FACING)) {
        Direction.NORTH -> NORTH_SHAPE
        Direction.SOUTH -> SOUTH_SHAPE
        Direction.EAST -> EAST_SHAPE
        Direction.WEST -> WEST_SHAPE
        else -> NORTH_SHAPE
    }

    @Suppress("DEPRECATION")
    override fun updateShape(state: BlockState, direction: Direction, newState: BlockState, level: LevelAccessor, pos: BlockPos, newPos: BlockPos): BlockState {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) level.scheduleTick(newPos, Fluids.WATER, Fluids.WATER.getTickDelay(level))
        return super.updateShape(state, direction, newState, level, pos, newPos)
    }

    override fun getFluidState(state: BlockState): FluidState = if (state.getValue(BlockStateProperties.WATERLOGGED)) Fluids.WATER.getSource(false) else Fluids.EMPTY.defaultFluidState()

    override fun isPathfindable(state: BlockState, blocks: BlockGetter, pos: BlockPos, type: PathComputationType) = false

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        if (!level.isClientSide) NetworkHooks.openGui(player as ServerPlayer, state.getMenuProvider(level, pos)) { it.writeInt(tier) }
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun getDustColor(state: BlockState, blocks: BlockGetter, pos: BlockPos) = state.getMapColor(blocks, pos).col

    private val containerTitle = LangKeys.CONTAINER_ANVIL.format(tier)

    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos) = SimpleMenuProvider({ windowID, inventory, _ ->
        AnvilMenu(windowID, inventory, ContainerLevelAccess.create(level, pos), tier)
    }, containerTitle)

    override fun appendHoverText(stack: ItemStack, blocks: BlockGetter?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += containerTitle.copy().withStyle(ChatFormatting.GOLD)
    }

    override fun getFallDamageSource(): DamageSource = if (this == NTechBlocks.murkyAnvil.get()) DamageSources.murkyAnvil else DamageSource.ANVIL
    override fun falling(entity: FallingBlockEntity) { entity.setHurtsEntities(2F * tier, (40 * tier).coerceAtMost(250)) }

    override fun onLand(level: Level, pos: BlockPos, state: BlockState, newState: BlockState, entity: FallingBlockEntity) {
        if (!entity.isSilent) level.playSound(null, pos, SoundEvents.anvilFall.get(), SoundSource.BLOCKS, 50F, 1F)
    }

    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)))
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = @Suppress("DEPRECATION") state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)))

    companion object {
        private val FRONT_LEG_X = Block.box(2.0, 0.0, 4.0, 4.0, 1.0, 12.0)
        private val FRONT_LEG_CONNECTOR_X = Block.box(4.0, 0.0, 5.0, 5.0, 1.0, 11.0)
        private val HIND_LEG_X = Block.box(12.0, 0.0, 4.0, 14.0, 1.0, 12.0)
        private val HIND_LEG_CONNECTOR_X = Block.box(11.0, 0.0, 5.0, 12.0, 1.0, 11.0)
        private val FRONT_LEG_Z = Block.box(4.0, 0.0, 2.0, 12.0, 1.0, 4.0)
        private val FRONT_LEG_CONNECTOR_Z = Block.box(5.0, 0.0, 4.0, 11.0, 1.0, 5.0)
        private val HIND_LEG_Z = Block.box(4.0, 0.0, 12.0, 12.0, 1.0, 14.0)
        private val HIND_LEG_CONNECTOR_Z = Block.box(5.0, 0.0, 11.0, 11.0, 1.0, 12.0)
        private val LEG_MIDDLE_PART = Block.box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0)
        private val STEM = Block.box(6.0, 2.0, 6.0, 10.0, 6.0, 10.0)
        private val STEM_CONNECTOR_NORTH = Block.box(6.0, 6.0, 6.0, 10.0, 7.0, 11.0)
        private val MAIN_BODY_0_NORTH = Block.box(6.0, 7.0, 2.0, 10.0, 11.0, 12.0)
        private val MAIN_BODY_1_NORTH = Block.box(6.0, 8.0, 12.0, 10.0, 11.0, 15.0)
        private val MAIN_BODY_2_NORTH = Block.box(6.0, 9.0, 15.0, 10.0, 11.0, 18.0)
        private val STEP_NORTH = Block.box(6.5, 7.5, 0.0, 9.5, 10.5, 2.0)
        private val HORN_0_NORTH = Block.box(7.0, 8.0, -3.0, 9.0, 10.0, 0.0)
        private val HORN_1_NORTH = Block.box(7.0, 9.0, -5.0, 9.0, 10.0, -3.0)
        private val STEM_CONNECTOR_SOUTH = Block.box(6.0, 6.0, 5.0, 10.0, 7.0, 10.0)
        private val MAIN_BODY_0_SOUTH = Block.box(6.0, 7.0, 4.0, 10.0, 11.0, 14.0)
        private val MAIN_BODY_1_SOUTH = Block.box(6.0, 8.0, 1.0, 10.0, 11.0, 4.0)
        private val MAIN_BODY_2_SOUTH = Block.box(6.0, 9.0, -2.0, 10.0, 11.0, 1.0)
        private val STEP_SOUTH = Block.box(6.5, 7.5, 14.0, 9.5, 10.5, 16.0)
        private val HORN_0_SOUTH = Block.box(7.0, 8.0, 16.0, 9.0, 10.0, 19.0)
        private val HORN_1_SOUTH = Block.box(7.0, 9.0, 19.0, 9.0, 10.0, 21.0)
        private val STEM_CONNECTOR_EAST = Block.box(5.0, 6.0, 6.0, 10.0, 7.0, 10.0)
        private val MAIN_BODY_0_EAST = Block.box(4.0, 7.0, 6.0, 14.0, 11.0, 10.0)
        private val MAIN_BODY_1_EAST = Block.box(1.0, 8.0, 6.0, 4.0, 11.0, 10.0)
        private val MAIN_BODY_2_EAST = Block.box(-2.0, 9.0, 6.0, 1.0, 11.0, 10.0)
        private val STEP_EAST = Block.box(14.0, 7.5, 6.5, 16.0, 10.5, 9.5)
        private val HORN_0_EAST = Block.box(16.0, 8.0, 7.0, 19.0, 10.0, 9.0)
        private val HORN_1_EAST = Block.box(19.0, 9.0, 7.0, 21.0, 10.0, 9.0)
        private val STEM_CONNECTOR_WEST = Block.box(6.0, 6.0, 6.0, 11.0, 7.0, 10.0)
        private val MAIN_BODY_0_WEST = Block.box(2.0, 7.0, 6.0, 12.0, 11.0, 10.0)
        private val MAIN_BODY_1_WEST = Block.box(12.0, 8.0, 6.0, 15.0, 11.0, 10.0)
        private val MAIN_BODY_2_WEST = Block.box(15.0, 9.0, 6.0, 18.0, 11.0, 10.0)
        private val STEP_WEST = Block.box(0.0, 7.5, 6.5, 2.0, 10.5, 9.5)
        private val HORN_0_WEST = Block.box(-3.0, 8.0, 7.0, 0.0, 10.0, 9.0)
        private val HORN_1_WEST = Block.box(-5.0, 9.0, 7.0, -3.0, 10.0, 9.0)
        private val LEG_X = Shapes.or(FRONT_LEG_X, FRONT_LEG_CONNECTOR_X, LEG_MIDDLE_PART, HIND_LEG_CONNECTOR_X, HIND_LEG_X, STEM)
        private val LEG_Z = Shapes.or(FRONT_LEG_Z, FRONT_LEG_CONNECTOR_Z, LEG_MIDDLE_PART, HIND_LEG_CONNECTOR_Z, HIND_LEG_Z, STEM)
        private val NORTH_SHAPE = Shapes.or(LEG_Z, STEM_CONNECTOR_NORTH, MAIN_BODY_0_NORTH, MAIN_BODY_1_NORTH, MAIN_BODY_2_NORTH, STEP_NORTH, HORN_0_NORTH, HORN_1_NORTH)
        private val SOUTH_SHAPE = Shapes.or(LEG_Z, STEM_CONNECTOR_SOUTH, MAIN_BODY_0_SOUTH, MAIN_BODY_1_SOUTH, MAIN_BODY_2_SOUTH, STEP_SOUTH, HORN_0_SOUTH, HORN_1_SOUTH)
        private val EAST_SHAPE = Shapes.or(LEG_X, STEM_CONNECTOR_EAST, MAIN_BODY_0_EAST, MAIN_BODY_1_EAST, MAIN_BODY_2_EAST, STEP_EAST, HORN_0_EAST, HORN_1_EAST)
        private val WEST_SHAPE = Shapes.or(LEG_X, STEM_CONNECTOR_WEST, MAIN_BODY_0_WEST, MAIN_BODY_1_WEST, MAIN_BODY_2_WEST, STEP_WEST, HORN_0_WEST, HORN_1_WEST)

        fun getAnvilByTier(tier: Int): AnvilBlock = when (tier) {
            in Int.MIN_VALUE..1 -> NTechBlocks.ironAnvil.get()
            2 -> NTechBlocks.steelAnvil.get()
            3 -> NTechBlocks.meteoriteAnvil.get()
            4 -> NTechBlocks.ferrouraniumAnvil.get()
            5 -> NTechBlocks.bismuthAnvil.get()
            6 -> NTechBlocks.schrabidateAnvil.get()
            7 -> NTechBlocks.dineutroniumAnvil.get()
            in 8..Int.MAX_VALUE -> NTechBlocks.murkyAnvil.get()
            else -> NTechBlocks.ironAnvil.get()
        }
    }
}
