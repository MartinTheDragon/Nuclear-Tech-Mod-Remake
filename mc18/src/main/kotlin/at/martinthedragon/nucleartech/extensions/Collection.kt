package at.martinthedragon.nucleartech.extensions

// more performant implementation of the range check
@Suppress("ConvertTwoComparisonsToRangeCheck")
fun Collection<*>.isIndexInRange(index: Int): Boolean = index >= 0 && index < size
