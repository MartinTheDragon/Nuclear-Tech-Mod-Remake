package at.martinthedragon.nucleartech.block.multi

import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.openMultiBlockMenu
import net.minecraft.client.particle.ParticleEngine
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.client.IBlockRenderProperties
import java.util.function.Consumer

open class MultiBlockPart(val blockEntityGetter: (pos: BlockPos, state: BlockState) -> MultiBlockPartBlockEntity? = ::GenericMultiBlockPartBlockEntity) : BaseEntityBlock(Properties.of(Material.METAL).strength(10F).sound(SoundType.METAL)) {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false

    override fun getCloneItemStack(state: BlockState, target: HitResult, world: BlockGetter, pos: BlockPos, player: Player): ItemStack {
        val blockEntity = world.getBlockEntity(pos)
        if (blockEntity !is MultiBlockPartBlockEntity) return ItemStack.EMPTY
        val blockState = world.getBlockState(blockEntity.core)
        return ItemStack(blockState.block)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_60519_: Boolean) {
        if (!state.`is`(newState.block)) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is MultiBlockPartBlockEntity) level.destroyBlock(blockEntity.core, true)
        }
        @Suppress("DEPRECATION") super.onRemove(state, level, pos, newState, p_60519_)
    }

    override fun playerWillDestroy(level: Level, pos: BlockPos, state: BlockState, player: Player) {
        if (!level.isClientSide && player.isCreative) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is MultiBlockPartBlockEntity) level.destroyBlock(blockEntity.core, false)
        }
        super.playerWillDestroy(level, pos, state, player)
    }

    override fun use(state: BlockState, level: Level, pos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult) = openMultiBlockMenu<BaseContainerBlockEntity, MultiBlockPartBlockEntity>(level, pos, player)

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape = Shapes.empty()
    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos) = true
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = blockEntityGetter(pos, state)

    override fun addRunningEffects(state: BlockState, level: Level, pos: BlockPos, entity: Entity) = true
    override fun addLandingEffects(state1: BlockState, level: ServerLevel, pos: BlockPos, state2: BlockState, entity: LivingEntity, numberOfParticles: Int) = true

    override fun initializeClient(consumer: Consumer<IBlockRenderProperties>) {
        consumer.accept(object : IBlockRenderProperties {
            override fun addDestroyEffects(state: BlockState?, Level: Level?, pos: BlockPos?, manager: ParticleEngine?): Boolean {
                return true // prevent particles
            }
        })
    }

    open class MultiBlockPartBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state) {
        var core: BlockPos = BlockPos.ZERO

        override fun saveAdditional(tag: CompoundTag) {
            super.saveAdditional(tag)
            tag.putInt("coreX", core.x)
            tag.putInt("coreY", core.y)
            tag.putInt("coreZ", core.z)
        }

        override fun load(tag: CompoundTag) {
            super.load(tag)
            core = BlockPos(tag.getInt("coreX"), tag.getInt("coreY"), tag.getInt("coreZ"))
        }

        override fun getUpdatePacket(): ClientboundBlockEntityDataPacket = ClientboundBlockEntityDataPacket.create(this)
        override fun getUpdateTag(): CompoundTag = saveWithoutMetadata()
    }

    class GenericMultiBlockPartBlockEntity(pos: BlockPos, state: BlockState) : MultiBlockPartBlockEntity(BlockEntityTypes.genericMultiBlockPartBlockEntityType.get(), pos, state)
}
