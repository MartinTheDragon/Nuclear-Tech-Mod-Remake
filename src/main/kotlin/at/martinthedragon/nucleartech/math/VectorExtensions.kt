package at.martinthedragon.nucleartech.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.math.vector.Vector3f
import net.minecraft.util.math.vector.Vector3i

/** Converts this [BlockPos] to a [Vector3d] with 0.5 added to each component */
fun BlockPos.toVector3dMiddle() = Vector3d(x + .5, y + .5, z + .5)

fun Vector3i.toVector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
