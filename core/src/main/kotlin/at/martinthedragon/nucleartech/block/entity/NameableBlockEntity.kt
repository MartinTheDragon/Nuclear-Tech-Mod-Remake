package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.Nameable
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntityType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState
import at.martinthedragon.nucleartech.sorcerer.Mirrored

// TODO remove later
@Mirrored
class NameableBlockEntity(type: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(type, blockPos, blockState), Nameable {
    override val name: Component
        get() = TODO("Not yet implemented")
}
