package at.martinthedragon.nucleartech.math

// Adapted from a yet unpublished project of mine and modified

// IMPORTANT: Most of these functions all only return approximations. They are a bit less accurate in favor of speed.

private const val FLOAT_ONE_AS_INT = 0x3F80_0000
private const val SCALE_UP = 1.17549435082228750796873653722E-38.toFloat() // = 0x0080_0000
private const val SCALE_DOWN = 1f / SCALE_UP

/**
 * An approximation of log2 using a magic constant.
 *
 * It's the opposite of [fastExp2].
 *
 * WARNING: No error checking will be performed; e.g. negative values for x will give wrong answers
 */
fun fastLog2(x: Float): Float = (x.toRawBits() - FLOAT_ONE_AS_INT).toFloat() * SCALE_DOWN

/**
 * An approximation of 2^x using a magic constant.
 *
 * It's the opposite of [fastLog2].
 *
 * Negative numbers work.
 */
fun fastExp2(x: Float): Float = Float.fromBits((x * SCALE_UP).toInt() + FLOAT_ONE_AS_INT)

/**
 * Very loose approximation of pow.
 *
 * Uses concepts from [fastLog2] and [fastExp2] together with identity x^p = 2^(p log2 x)
 */
fun fastPow(x: Float, p: Float): Float = Float.fromBits((p * (x.toRawBits() - FLOAT_ONE_AS_INT)).toInt() + FLOAT_ONE_AS_INT)

fun fastSqrt(x: Float): Float = Float.fromBits((x.toRawBits() shr 1) + (FLOAT_ONE_AS_INT shr 1))

fun fastSqrtNewRap(x: Float): Float {
    val y = fastSqrt(x)
    return (y * y + x) / (2 * y)
}
