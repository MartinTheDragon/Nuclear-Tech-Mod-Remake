package at.martinthedragon.nucleartech.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.vector.Vector3d

/** Converts this [BlockPos] to a [Vector3d] with 0.5 added to each component */
fun BlockPos.toVector3dMiddle() = Vector3d(x + .5, y + .5, z + .5)
