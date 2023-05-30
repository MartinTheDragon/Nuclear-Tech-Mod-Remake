package at.martinthedragon.nucleartech.extensions

import net.minecraft.world.level.block.Rotation

val Rotation.inverted
    get() = when (this) {
        Rotation.NONE -> Rotation.NONE
        Rotation.CLOCKWISE_90 -> Rotation.COUNTERCLOCKWISE_90
        Rotation.CLOCKWISE_180 -> Rotation.CLOCKWISE_180
        Rotation.COUNTERCLOCKWISE_90 -> Rotation.CLOCKWISE_90
    }
