package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block

import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction

enum class Rotation {
    NONE,
    CLOCKWISE_90,
    CLOCKWISE_180,
    COUNTERCLOCKWISE_90;

    fun rotate(rotation: Rotation): Rotation = values()[(ordinal + rotation.ordinal) % 4]

    fun rotate(direction: Direction): Direction = if (direction.axis == Direction.Axis.Y) direction else when (this) {
        CLOCKWISE_90 -> direction.clockwise
        CLOCKWISE_180 -> direction.opposite
        COUNTERCLOCKWISE_90 -> direction.counterclockwise
        NONE -> direction
    }

    val inverted
        get() = when (this) {
            NONE -> NONE
            CLOCKWISE_90 -> COUNTERCLOCKWISE_90
            CLOCKWISE_180 -> CLOCKWISE_180
            COUNTERCLOCKWISE_90 -> CLOCKWISE_90
        }
}
