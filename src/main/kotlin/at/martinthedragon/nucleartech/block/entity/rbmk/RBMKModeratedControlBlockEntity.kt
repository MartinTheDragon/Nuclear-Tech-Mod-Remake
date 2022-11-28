package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

class RBMKModeratedControlBlockEntity(pos: BlockPos, state: BlockState) : RBMKManualControlBlockEntity(BlockEntityTypes.rbmkModeratedControlBlockEntityType.get(), pos, state) {
    override val defaultName = LangKeys.CONTAINER_RBMK_CONTROL_MODERATED.get()
    override fun isModerated() = true
}
