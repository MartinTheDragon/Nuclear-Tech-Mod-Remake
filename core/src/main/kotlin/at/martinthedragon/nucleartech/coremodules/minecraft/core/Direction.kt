package at.martinthedragon.nucleartech.coremodules.minecraft.core

import at.martinthedragon.nucleartech.coremodules.minecraft.util.StringRepresentable
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.Rotation

enum class Direction(private val oppositeIndex: Int, private val serializedName: String, val axis: Axis) : StringRepresentable {
    DOWN(1, "down", Axis.Y),
    UP(0, "up", Axis.Y),
    NORTH(3, "north", Axis.Z),
    SOUTH(2, "south", Axis.Z),
    WEST(5, "west", Axis.X),
    EAST(4, "east", Axis.X);

    override fun getSerializedName() = serializedName

    val opposite get() = values()[oppositeIndex]

    val clockwise
        get() = when (this) {
            NORTH -> EAST
            SOUTH -> WEST
            WEST -> NORTH
            EAST -> SOUTH
            else -> throw IllegalStateException("Unable to get CW facing of $this")
        }

    val counterclockwise
        get() = when (this) {
            NORTH -> WEST
            SOUTH -> EAST
            WEST -> SOUTH
            EAST -> NORTH
            else -> throw IllegalStateException("Unable to get CCW facing of $this")
        }

    val horizontalRotation
        get() = when (this) {
            NORTH -> Rotation.NONE
            EAST -> Rotation.CLOCKWISE_90
            SOUTH -> Rotation.CLOCKWISE_180
            WEST -> Rotation.COUNTERCLOCKWISE_90
            UP, DOWN -> Rotation.NONE
        }

    enum class Axis(private val serializedName: String) : StringRepresentable {
        X("x"),
        Y("y"),
        Z("z");

        override fun getSerializedName() = serializedName
    }
}
