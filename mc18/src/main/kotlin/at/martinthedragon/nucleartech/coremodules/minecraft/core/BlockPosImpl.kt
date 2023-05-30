package at.martinthedragon.nucleartech.coremodules.minecraft.core

@JvmInline
value class BlockPosImpl(private val delegate: net.minecraft.core.BlockPos) : BlockPos {
    override val x: Int get() = delegate.x
    override val y: Int get() = delegate.y
    override val z: Int get() = delegate.z

    override fun offset(x: Int, y: Int, z: Int) = BlockPosImpl(delegate.offset(x, y, z))
    override fun subtract(vec3i: Vec3i) = BlockPosImpl(delegate.offset(-vec3i.x, -vec3i.y, -vec3i.z))
    override fun multiply(amount: Int) = BlockPosImpl(delegate.multiply(amount))
    override fun relative(direction: Direction, amount: Int) = BlockPosImpl(delegate.relative(net.minecraft.core.Direction.values()[direction.ordinal], amount))

    override fun toString() = delegate.toString()

    class Factory : BlockPos.Factory {
        override fun create(x: Int, y: Int, z: Int) = BlockPosImpl(net.minecraft.core.BlockPos(x, y, z))
    }
}
