package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RBMKModeratedReaSimRodBlockEntity(pos: BlockPos, state: BlockState) : RBMKReaSimRodBlockEntity(BlockEntityTypes.rbmkModeratedReaSimRodBlockEntityType.get(), pos, state) {
    override fun isModerated() = true
}
