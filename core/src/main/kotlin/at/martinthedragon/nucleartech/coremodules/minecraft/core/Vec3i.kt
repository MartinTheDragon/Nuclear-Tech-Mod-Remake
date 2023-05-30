package at.martinthedragon.nucleartech.coremodules.minecraft.core

import at.martinthedragon.nucleartech.coremodules.InjectionFactories

interface Vec3i {
    val x: Int
    val y: Int
    val z: Int

    fun offset(x: Int, y: Int, z: Int): Vec3i
    fun subtract(vec3i: Vec3i): Vec3i
    fun multiply(amount: Int): Vec3i
    fun above(amount: Int = 1): Vec3i = relative(Direction.UP, amount)
    fun below(amount: Int = 1): Vec3i = relative(Direction.DOWN, amount)
    fun north(amount: Int = 1): Vec3i = relative(Direction.NORTH, amount)
    fun south(amount: Int = 1): Vec3i = relative(Direction.SOUTH, amount)
    fun west(amount: Int = 1): Vec3i = relative(Direction.WEST, amount)
    fun east(amount: Int = 1): Vec3i = relative(Direction.EAST, amount)
    fun relative(direction: Direction, amount: Int = 1): Vec3i

    override fun toString(): String
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int

    interface Factory {
        fun create(x: Int, y: Int, z: Int): Vec3i
    }
}

fun Vec3i(x: Int, y: Int, z: Int) = InjectionFactories.vec3i.create(x, y, z)
