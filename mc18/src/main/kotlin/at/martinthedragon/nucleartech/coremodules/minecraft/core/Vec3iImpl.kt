package at.martinthedragon.nucleartech.coremodules.minecraft.core

@JvmInline
value class Vec3iImpl(val delegate: net.minecraft.core.Vec3i) : Vec3i {
    override val x: Int get() = delegate.x
    override val y: Int get() = delegate.y
    override val z: Int get() = delegate.z

    override fun offset(x: Int, y: Int, z: Int) = Vec3iImpl(delegate.offset(x, y, z))
    override fun subtract(vec3i: Vec3i) = Vec3iImpl(delegate.offset(-vec3i.x, -vec3i.y, -vec3i.z))
    override fun multiply(amount: Int) = Vec3iImpl(delegate.multiply(amount))
    override fun relative(direction: Direction, amount: Int) = Vec3iImpl(delegate.relative(net.minecraft.core.Direction.values()[direction.ordinal], amount))

    override fun toString() = delegate.toString()

    class Factory : Vec3i.Factory {
        override fun create(x: Int, y: Int, z: Int) = Vec3iImpl(net.minecraft.core.Vec3i(x, y, z))
    }
}
