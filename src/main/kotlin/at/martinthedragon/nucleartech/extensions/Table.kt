package at.martinthedragon.nucleartech.extensions

import com.google.common.collect.Table

fun <R, C, V> Table.Cell<R, C, V>.component1(): R = rowKey
fun <R, C, V> Table.Cell<R, C, V>.component2(): C = columnKey
fun <R, C, V> Table.Cell<R, C, V>.component3(): V = value
