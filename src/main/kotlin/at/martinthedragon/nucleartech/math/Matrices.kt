package at.martinthedragon.nucleartech.math

import kotlin.math.sqrt

// ROTATION

fun BooleanArray.rotate1DSquareMatrixInPlaceClockwise() {
    val squareLength = sqrt(size.toFloat()).run {
        if (rem(1) != 0F) throw IllegalArgumentException("Array is not a square matrix!")
        toInt()
    }
    for (x in 0 until squareLength / 2) for (y in x until squareLength - x - 1) {
        val index1 = x * squareLength + y
        val index2 = (squareLength - 1 - y) * squareLength + x
        val index3 = (squareLength - 1 - x) * squareLength + squareLength - 1 - y
        val index4 = y * squareLength + squareLength - 1 - x
        val temp = get(index1)
        set(index1, get(index2))
        set(index2, get(index3))
        set(index3, get(index4))
        set(index4, temp)
    }
}

fun BooleanArray.rotate1DSquareMatrixInPlaceCounterClockwise() {
    val squareLength = sqrt(size.toFloat()).run {
        if (rem(1) != 0F) throw IllegalArgumentException("Array is not a square matrix!")
        toInt()
    }
    for (x in 0 until squareLength / 2) for (y in x until squareLength - x - 1) {
        val index1 = x * squareLength + y
        val index2 = y * squareLength + squareLength - 1 - x
        val index3 = (squareLength - 1 - x) * squareLength + squareLength - 1 - y
        val index4 = (squareLength - 1 - y) * squareLength + x
        val temp = get(index1)
        set(index1, get(index2))
        set(index2, get(index3))
        set(index3, get(index4))
        set(index4, temp)
    }
}

fun <T> Array<T>.rotate1DSquareMatrixInPlaceClockwise() {
    val squareLength = sqrt(size.toFloat()).run {
        if (rem(1) != 0F) throw IllegalArgumentException("Array is not a square matrix!")
        toInt()
    }
    for (x in 0 until squareLength / 2) for (y in x until squareLength - x - 1) {
        val index1 = x * squareLength + y
        val index2 = (squareLength - 1 - y) * squareLength + x
        val index3 = (squareLength - 1 - x) * squareLength + squareLength - 1 - y
        val index4 = y * squareLength + squareLength - 1 - x
        val temp = get(index1)
        set(index1, get(index2))
        set(index2, get(index3))
        set(index3, get(index4))
        set(index4, temp)
    }
}

fun <T> Array<T>.rotate1DSquareMatrixInPlaceCounterClockwise() {
    val squareLength = sqrt(size.toFloat()).run {
        if (rem(1) != 0F) throw IllegalArgumentException("Array is not a square matrix!")
        toInt()
    }
    for (x in 0 until squareLength / 2) for (y in x until squareLength - x - 1) {
        val index1 = x * squareLength + y
        val index2 = y * squareLength + squareLength - 1 - x
        val index3 = (squareLength - 1 - x) * squareLength + squareLength - 1 - y
        val index4 = (squareLength - 1 - y) * squareLength + x
        val temp = get(index1)
        set(index1, get(index2))
        set(index2, get(index3))
        set(index3, get(index4))
        set(index4, temp)
    }
}
