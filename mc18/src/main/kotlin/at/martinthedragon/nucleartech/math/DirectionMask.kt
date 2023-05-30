package at.martinthedragon.nucleartech.math

import net.minecraft.core.Direction
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

/**
 * Bit order: 0 0 EAST WEST SOUTH NORTH UP DOWN
 */
@JvmInline
value class DirectionMask(val mask: Byte) : Iterable<Direction> {
    constructor(mask: Int) : this(mask.toByte())
    constructor(direction: Direction) : this(direction.ordinal)
    constructor(vararg direction: Direction) : this(combineDirections(direction))

    val all: Boolean get() = mask and ALL_MASK == ALL_MASK
    val down: Boolean get() = mask and DOWN_MASK == DOWN_MASK
    val up: Boolean get() = mask and UP_MASK == UP_MASK
    val north: Boolean get() = mask and NORTH_MASK == NORTH_MASK
    val south: Boolean get() = mask and SOUTH_MASK == SOUTH_MASK
    val west: Boolean get() = mask and WEST_MASK == WEST_MASK
    val east: Boolean get() = mask and EAST_MASK == EAST_MASK

    operator fun plus(other: DirectionMask) = DirectionMask(mask or other.mask)
    operator fun minus(other: DirectionMask) = DirectionMask(mask and other.mask.inv())

    operator fun plus(byte: Byte) = DirectionMask(mask or byte)
    operator fun minus(byte: Byte) = DirectionMask(mask and byte.inv())

    operator fun plus(direction: Direction) = DirectionMask(mask or getBitForDirection(direction))
    operator fun minus(direction: Direction) = DirectionMask(mask and getBitForDirection(direction).inv())

    operator fun plus(directions: Collection<Direction>) = plus(combineDirections(directions))
    operator fun minus(directions: Collection<Direction>) = minus(combineDirections(directions))

    operator fun plus(directions: Array<out Direction>) = plus(combineDirections(directions))
    operator fun minus(directions: Array<out Direction>) = minus(combineDirections(directions))

    operator fun contains(direction: Direction): Boolean {
        val bit = getBitForDirection(direction)
        return mask and bit == bit
    }

    override operator fun iterator() = object : Iterator<Direction> {
        val maskInt = mask.toInt()
        var shiftedMask = maskInt
        var currentBit = 0

        override fun hasNext() = (shiftedMask).countOneBits() > 0

        override fun next(): Direction {
            if (!hasNext()) throw NoSuchElementException()
            var direction: Direction
            do {
                direction = Direction.values()[currentBit]
                val zeros = (maskInt shr currentBit).countTrailingZeroBits()
                currentBit++
                shiftedMask = maskInt shr currentBit
            } while (zeros > 0)
            return direction
        }
    }

    operator fun get(direction: Direction): Boolean = contains(direction)
    fun set(direction: Direction, value: Boolean) = if (value) plus(direction) else minus(direction)

    companion object {
        const val ALL_MASK: Byte    = 0b00111111
        const val DOWN_MASK: Byte   = 0b00000001
        const val UP_MASK: Byte     = 0b00000010
        const val NORTH_MASK: Byte  = 0b00000100
        const val SOUTH_MASK: Byte  = 0b00001000
        const val WEST_MASK: Byte   = 0b00010000
        const val EAST_MASK: Byte   = 0b00100000

        const val NULL_MASK: Byte   = 0b00000000

        val NULL = DirectionMask(NULL_MASK)

        fun getBitForDirection(direction: Direction) = (1 shl direction.ordinal).toByte()
        fun combineDirections(directions: Collection<Direction>) = directions.map(DirectionMask::getBitForDirection).fold(NULL_MASK) { acc, byte -> acc or byte }
        fun combineDirections(directions: Array<out Direction>) = directions.map(DirectionMask::getBitForDirection).fold(NULL_MASK) { acc, byte -> acc or byte }
    }
}
