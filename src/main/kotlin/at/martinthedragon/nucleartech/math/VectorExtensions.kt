package at.martinthedragon.nucleartech.math

import com.mojang.math.Vector3d
import com.mojang.math.Vector3f
import net.minecraft.core.BlockPos
import net.minecraft.core.Vec3i
import net.minecraft.world.phys.Vec3

/** Converts this [BlockPos] to a [Vector3d] with 0.5 added to each component */
fun BlockPos.toVec3Middle() = Vec3(x + .5, y + .5, z + .5)

fun Vec3i.toVector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
