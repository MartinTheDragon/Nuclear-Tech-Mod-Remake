package at.martinthedragon.nucleartech.coremodules.minecraft.core

import at.martinthedragon.nucleartech.coremodules.InjectionFactories
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Rotation

interface BlockPos : Vec3i {
    override fun offset(x: Int, y: Int, z: Int): BlockPos
    override fun subtract(vec3i: Vec3i): BlockPos
    override fun multiply(amount: Int): BlockPos
    override fun above(amount: Int): BlockPos = relative(Direction.UP, amount)
    override fun below(amount: Int): BlockPos = relative(Direction.DOWN, amount)
    override fun north(amount: Int): BlockPos = relative(Direction.NORTH, amount)
    override fun south(amount: Int): BlockPos = relative(Direction.SOUTH, amount)
    override fun west(amount: Int): BlockPos = relative(Direction.WEST, amount)
    override fun east(amount: Int): BlockPos = relative(Direction.EAST, amount)
    override fun relative(direction: Direction, amount: Int): BlockPos

    fun rotate(rotation: Rotation): BlockPos

    interface Factory {
        fun create(x: Int, y: Int, z: Int): BlockPos
    }

    companion object {
        val ZERO = BlockPos(0, 0, 0)
    }
}

fun BlockPos(x: Int, y: Int, z: Int) = InjectionFactories.blockPos.create(x, y, z)
