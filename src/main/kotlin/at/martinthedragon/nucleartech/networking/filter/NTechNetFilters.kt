package at.martinthedragon.nucleartech.networking.filter

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.hazard.HazardSystem
import com.google.common.collect.ImmutableMap
import net.minecraft.network.Connection
import net.minecraftforge.network.filters.NetworkFilters
import net.minecraftforge.network.filters.VanillaPacketFilter
import sun.misc.Unsafe
import java.lang.reflect.Modifier
import java.util.function.Function

// no it's not censorship...
object NTechNetFilters {
    private val instances = mapOf(
        HazardSystem.ItemDataPacketDetector.NAME.toString() to Function { _: Connection? -> HazardSystem.ItemDataPacketDetector() }
    )

    private val theUnsafe = getUnsafeInstance()

    // the following is the result of over a dozen hours of research, to find out where I could potentially intercept packets.
    // it is absolutely abhorrent and I hate it.
    // please don't ever make me do this again.
    fun performTheFunny() {
        NuclearTech.LOGGER.debug("Injecting network filters...")
        if (theUnsafe == null) {
            NuclearTech.LOGGER.error("Can't inject network filters, couldn't get Unsafe instance")
            return
        }

        try { // a hack to initialize static fields
            NetworkFilters.injectIfNecessary(null)
        } catch (ignored: NullPointerException) {}

        val field = NetworkFilters::class.java.getDeclaredField("instances")
        val base = theUnsafe.staticFieldBase(field)
        val offset = theUnsafe.staticFieldOffset(field)

        val originalMap = theUnsafe.getObject(base, offset) as? ImmutableMap<*, *> ?: run { NuclearTech.LOGGER.error("Couldn't inject network packet filters"); return }
        val typeCheckEntry = originalMap.entries.firstOrNull() ?: run { NuclearTech.LOGGER.error("Original network packet filter map is empty, bailing out..."); return }
        if (typeCheckEntry.key !is String || typeCheckEntry.value !is Function<*, *>) {
            NuclearTech.LOGGER.error("Unexpected types in packet filter map, bailing out...")
            return
        }

        @Suppress("UNCHECKED_CAST")
        originalMap as ImmutableMap<String, Function<Connection?, out VanillaPacketFilter>>
        val newMap = mutableMapOf<String, Function<Connection?, out VanillaPacketFilter>>()
        originalMap.toMap(newMap)
        instances.toMap(newMap)
        theUnsafe.putObject(base, offset, ImmutableMap.copyOf(newMap))
        NuclearTech.LOGGER.debug("Successfully injected network filters")
    }

    private fun getUnsafeInstance(): Unsafe? {
        for (field in Unsafe::class.java.declaredFields) {
            if (field.type != Unsafe::class.java)
                continue

            val modifiers = field.modifiers
            if (!Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers))
                continue

            try {
                field.isAccessible = true
                return field.get(null) as Unsafe
            } catch (ignored: Exception) {}
        }

        return null
    }
}
