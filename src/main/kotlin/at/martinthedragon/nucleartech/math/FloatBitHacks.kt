package at.martinthedragon.nucleartech.math

private const val SQRT_BIAS = -0x4B0D2

fun hackedSqrt(x: Float) = Float.fromBits((1 shl 29) + (x.toRawBits() shr 1 ) - (1 shl 22) + SQRT_BIAS)

fun hackedHypot(x: Float, y: Float): Float = hackedSqrt(x * x + y * y)
