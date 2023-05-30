package at.martinthedragon.nucleartech.net

import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag

interface ByteBuf {
    fun writeBlockPos(pos: BlockPos)
    fun writeNbt(tag: CompoundTag?)

    fun readBlockPos(): BlockPos
    fun readNbt(): CompoundTag?
}
