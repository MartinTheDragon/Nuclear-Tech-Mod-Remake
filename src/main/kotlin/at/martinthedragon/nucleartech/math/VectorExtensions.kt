package at.martinthedragon.nucleartech.math

import com.mojang.math.Vector3d
import com.mojang.math.Vector3f
import com.mojang.math.Vector4f
import net.minecraft.core.BlockPos
import net.minecraft.core.Vec3i
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.phys.Vec2
import net.minecraft.world.phys.Vec3

/** Converts this [BlockPos] to a [Vector3d] with 0.5 added to each component */
fun BlockPos.toVec3Middle() = Vec3(x + .5, y + .5, z + .5)

fun Vec3.toBlockPos() = BlockPos(x.toInt(), y.toInt(), z.toInt())

fun Vec3i.toVec3() = Vec3(x.toDouble(), y.toDouble(), z.toDouble())

fun Vec3i.toVector3f() = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())

fun Vector3f.toVec3i() = Vec3i(x().toInt(), y().toInt(), z().toInt())

fun Vec3.rotate(rotation: Rotation) = when (rotation) {
    Rotation.NONE -> this
    Rotation.CLOCKWISE_90 -> Vec3(-z, y, x)
    Rotation.CLOCKWISE_180 -> Vec3(-x, y, -z)
    Rotation.COUNTERCLOCKWISE_90 -> Vec3(z, y, -x)
}

operator fun Vec2.component1() = x
operator fun Vec2.component2() = y

operator fun Vec3i.component1() = x
operator fun Vec3i.component2() = y
operator fun Vec3i.component3() = z

operator fun Vec3.component1() = x
operator fun Vec3.component2() = y
operator fun Vec3.component3() = z

operator fun Vector3f.component1() = x()
operator fun Vector3f.component2() = y()
operator fun Vector3f.component3() = z()

operator fun Vector3d.component1() = x
operator fun Vector3d.component2() = y
operator fun Vector3d.component3() = z

operator fun Vector4f.component1() = x()
operator fun Vector4f.component2() = y()
operator fun Vector4f.component3() = z()
operator fun Vector4f.component4() = w()
