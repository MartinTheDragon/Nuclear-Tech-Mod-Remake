package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state

import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties.Property
import java.util.Optional

interface StateHolder<O, S> {
    fun <T : Comparable<T>> hasProperty(property: Property<T>): Boolean
    fun <T : Comparable<T>> getValue(property: Property<T>): T
    fun <T : Comparable<T>> getOptionalValue(property: Property<T>): Optional<T>
    fun <T : Comparable<T>, V : T> setValue(property: Property<T>, value: V): S
}
