package at.martinthedragon.nucleartech.blocks.multi

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

abstract class MultiBlockPart(val core: Block) : BaseEntityBlock(Properties.of(Material.METAL).strength(10F).sound(SoundType.METAL)) {
    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getCloneItemStack(state: BlockState, target: HitResult, world: BlockGetter, pos: BlockPos, player: Player) = ItemStack(core)

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, p_60519_: Boolean) {
        if (!state.`is`(newState.block)) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is MultiBlockPartBlockEntity && level.getBlockState(blockEntity.core).`is`(core))
                level.destroyBlock(blockEntity.core, true)
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

    override fun getOcclusionShape(state: BlockState, level: BlockGetter, pos: BlockPos): VoxelShape = Shapes.empty()
    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos) = true
    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 1F

    open class MultiBlockPartBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state) {
        var core: BlockPos = BlockPos.ZERO

        override fun saveAdditional(tag: CompoundTag) {
            tag.putInt("coreX", core.x)
            tag.putInt("coreY", core.y)
            tag.putInt("coreZ", core.z)
        }

        override fun load(tag: CompoundTag) {
            super.load(tag)
            core = BlockPos(tag.getInt("coreX"), tag.getInt("coreY"), tag.getInt("coreZ"))
        }
    }
}
