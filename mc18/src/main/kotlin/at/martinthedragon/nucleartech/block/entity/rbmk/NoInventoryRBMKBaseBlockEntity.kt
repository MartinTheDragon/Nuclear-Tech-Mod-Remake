package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.block.entity.SyncedBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class NoInventoryRBMKBaseBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : SyncedBlockEntity(type, pos, state), RBMKBase {
    override var steam = 0
    override var heat = 0.0
    override var water = 0
    override val heatCache = arrayOfNulls<RBMKBase?>(4)
    override val valid = !remove

    private var cachedHeight = -1

    override fun getColumnHeight(): Int {
        if (cachedHeight == -1) {
            cachedHeight = super.getColumnHeight()
        }
        return cachedHeight
    }

    override fun getRenderBoundingBox() = super<RBMKBase>.getRenderBoundingBox()

    override fun saveAdditional(tag: CompoundTag) {
        super<SyncedBlockEntity>.saveAdditional(tag)
        super<RBMKBase>.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        super<SyncedBlockEntity>.load(tag)
        super<RBMKBase>.load(tag)
    }
}
