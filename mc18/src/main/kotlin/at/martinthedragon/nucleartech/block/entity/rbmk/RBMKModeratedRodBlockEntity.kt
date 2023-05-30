package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RBMKModeratedRodBlockEntity(pos: BlockPos, state: BlockState) : RBMKRodBlockEntity(BlockEntityTypes.rbmkModeratedRodBlockEntityType.get(), pos, state) {
    override fun isModerated() = true
}
