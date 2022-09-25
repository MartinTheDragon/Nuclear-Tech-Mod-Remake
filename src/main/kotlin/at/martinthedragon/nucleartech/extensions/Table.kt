package at.martinthedragon.nucleartech.extensions

import com.google.common.collect.Table

operator fun <R, C, V> Table.Cell<R, C, V>.component1(): R = rowKey
operator fun <R, C, V> Table.Cell<R, C, V>.component2(): C = columnKey
operator fun <R, C, V> Table.Cell<R, C, V>.component3(): V = value
