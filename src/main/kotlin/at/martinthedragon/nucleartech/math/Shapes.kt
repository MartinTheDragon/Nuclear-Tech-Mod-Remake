package at.martinthedragon.nucleartech.math

import net.minecraft.core.BlockPos
import kotlin.math.ceil

inline fun placeOctahedral(origin: BlockPos, sphericalRadius: Float, placer: (pos: BlockPos) -> Unit) {
    val boxRadius = ceil(sphericalRadius).toInt()
    val octahedron = octahedronByPlanes.map { it.copy(d = it.d * sphericalRadius) }
    for (x in -boxRadius..boxRadius) for (y in -boxRadius..boxRadius) for (z in -boxRadius..boxRadius) {
        if (pointIsInsideConvexShape(Point(x.toFloat(), y.toFloat(), z.toFloat()), octahedron))
            placer(origin.offset(x, y, z))
    }
}

val octahedronByPlanes = arrayOf(
    Plane(1F, 1F, 1F, -1F),
    Plane(-1F, 1F, 1F, -1F),
    Plane(-1F, -1F, 1F, -1F),
    Plane(1F, -1F, 1F, -1F),
    Plane(1F, 1F, -1F, -1F),
    Plane(-1F, 1F, -1F, -1F),
    Plane(-1F, -1F, -1F, -1F),
    Plane(1F, -1F, -1F, -1F)
)

data class Point(val x: Float, val y: Float, val z: Float)
data class Plane(val a: Float, val b: Float, val c: Float, val d: Float)

fun pointIsInsideConvexShape(point: Point, planes: Collection<Plane>): Boolean = planes.all {
    point.x * it.a + point.y * it.b + point.z * it.c + it.d < 0F
}
