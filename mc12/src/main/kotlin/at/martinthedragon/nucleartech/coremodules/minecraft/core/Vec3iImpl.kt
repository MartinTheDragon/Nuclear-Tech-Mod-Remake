package at.martinthedragon.nucleartech.coremodules.minecraft.core

import net.minecraft.util.EnumFacing

@JvmInline
value class Vec3iImpl(val delegate: net.minecraft.util.math.Vec3i) : Vec3i {
    override val x: Int get() = delegate.x
    override val y: Int get() = delegate.y
    override val z: Int get() = delegate.z

    override fun offset(x: Int, y: Int, z: Int) = Vec3iImpl(net.minecraft.util.math.Vec3i(this.x + x, this.y + y, this.z + z))
    override fun subtract(vec3i: Vec3i) = Vec3iImpl(net.minecraft.util.math.Vec3i(this.x - x, this.y - y, this.z - z))
    override fun multiply(amount: Int) = Vec3iImpl(net.minecraft.util.math.Vec3i(this.x * amount, this.y * amount, this.z * amount))
    override fun relative(direction: Direction, amount: Int): Vec3iImpl {
        val mc12Direction = EnumFacing.values()[direction.ordinal]
        return Vec3iImpl(net.minecraft.util.math.Vec3i(
            x + mc12Direction.frontOffsetX * amount,
            y + mc12Direction.frontOffsetY * amount,
            z + mc12Direction.frontOffsetZ * amount
        ))
    }

    override fun toString() = delegate.toString()

    class Factory : Vec3i.Factory {
        override fun create(x: Int, y: Int, z: Int) = Vec3iImpl(net.minecraft.util.math.Vec3i(x, y, z))
    }
}
