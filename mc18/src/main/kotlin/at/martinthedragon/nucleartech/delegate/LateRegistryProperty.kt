package at.martinthedragon.nucleartech.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LateRegistryProperty<T : Any> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Registry property ${property.name} wasn't initialized before get.")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == null) this.value = value else throw IllegalStateException("Can't set the same property ${property.name} twice.")
    }
}
