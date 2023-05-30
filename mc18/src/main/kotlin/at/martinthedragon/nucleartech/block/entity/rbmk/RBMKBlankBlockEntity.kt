package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.entity.RBMKDebris
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.state.BlockState

class RBMKBlankBlockEntity(pos: BlockPos, state: BlockState) : NoInventoryRBMKBaseBlockEntity(BlockEntityTypes.rbmkBlankBlockEntityType.get(), pos, state) {
    override fun onMelt(reduce: Int) {
        super.onMelt(reduce)

        val count = 1 + levelUnchecked.random.nextInt(2)
        for (i in 0 until count) spawnDebris(RBMKDebris.DebrisType.BLANK)
    }

    override val consoleType = RBMKConsoleBlockEntity.Column.Type.BLANK
    override fun getConsoleData() = CompoundTag()
}
