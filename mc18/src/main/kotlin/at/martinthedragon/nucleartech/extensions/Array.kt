package at.martinthedragon.nucleartech.extensions

// more performant implementation of range check
@Suppress("ConvertTwoComparisonsToRangeCheck")
fun Array<*>.isIndexInRange(index: Int): Boolean = index >= 0 && index < size
