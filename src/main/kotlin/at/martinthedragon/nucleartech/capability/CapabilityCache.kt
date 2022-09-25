package at.martinthedragon.nucleartech.capability

import com.google.common.collect.HashBasedTable
import net.minecraft.core.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import java.util.*

class CapabilityCache {
    private val capabilities = HashBasedTable.create<Capability<*>, Optional<Direction>, LazyOptional<*>>()

    fun <T : Any> addToCache(capability: Capability<T>, side: Direction?, handler: LazyOptional<T>): LazyOptional<T> {
        handler.addListener { removeCached(capability, side) }
        return capabilities.put(capability, Optional.ofNullable(side), handler)?.cast() ?: LazyOptional.empty()
    }

    fun <T : Any> get(capability: Capability<T>, side: Direction?): LazyOptional<T> =
        capabilities.get(capability, Optional.ofNullable(side))?.cast() ?: LazyOptional.empty()

    fun <T : Any> getOrAddToCache(capability: Capability<T>, side: Direction?, supplier: (cap: Capability<T>, side: Direction?) -> LazyOptional<T>): LazyOptional<T> {
        val optionalSide = Optional.ofNullable(side)
        return if (capabilities.contains(capability, optionalSide)) get(capability, side)
        else {
            val newHandler = supplier(capability, side)
            addToCache(capability, side, newHandler)
            newHandler
        }
    }

    fun <T : Any> removeCached(capability: Capability<T>, side: Direction?): LazyOptional<T> =
        capabilities.remove(capability, Optional.ofNullable(side))?.cast() ?: LazyOptional.empty()
}
