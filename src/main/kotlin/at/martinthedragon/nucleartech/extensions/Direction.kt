package at.martinthedragon.nucleartech.extensions

import net.minecraft.core.Direction
import net.minecraft.world.level.block.Rotation

val Direction.horizontalRotation
    get() = when (this) {
        Direction.NORTH -> Rotation.NONE
        Direction.EAST -> Rotation.CLOCKWISE_90
        Direction.SOUTH -> Rotation.CLOCKWISE_180
        Direction.WEST -> Rotation.COUNTERCLOCKWISE_90
        Direction.UP, Direction.DOWN -> Rotation.NONE
    }
