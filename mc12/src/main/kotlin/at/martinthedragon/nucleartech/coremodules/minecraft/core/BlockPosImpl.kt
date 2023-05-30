package at.martinthedragon.nucleartech.coremodules.minecraft.core

import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Rotation
import net.minecraft.util.EnumFacing

@JvmInline
value class BlockPosImpl(val delegate: net.minecraft.util.math.BlockPos) : BlockPos {
    override val x: Int get() = delegate.x
    override val y: Int get() = delegate.y
    override val z: Int get() = delegate.z

    override fun offset(x: Int, y: Int, z: Int) = BlockPosImpl(delegate.add(x, y, z))
    override fun subtract(vec3i: Vec3i) = BlockPosImpl(delegate.add(-vec3i.x, -vec3i.y, -vec3i.z))
    override fun multiply(amount: Int) = BlockPosImpl(net.minecraft.util.math.BlockPos(x * amount, y * amount, z * amount))
    override fun relative(direction: Direction, amount: Int) = BlockPosImpl(delegate.offset(EnumFacing.values()[direction.ordinal], amount))

    override fun rotate(rotation: Rotation) = BlockPosImpl(delegate.rotate(net.minecraft.util.Rotation.values()[rotation.ordinal]))

    override fun toString() = delegate.toString()

    class Factory : BlockPos.Factory {
        override fun create(x: Int, y: Int, z: Int) = BlockPosImpl(net.minecraft.util.math.BlockPos(x, y, z))
    }
}
