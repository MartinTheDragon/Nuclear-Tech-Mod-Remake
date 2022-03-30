package at.martinthedragon.nucleartech.api.blocks.entities

import net.minecraft.world.Container
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.entity.BlockEntity
import java.util.function.Supplier

/**
 * Implement this in a [BlockEntity] and provide it in your [at.martinthedragon.nucleartech.api.explosions.IgnitableExplosive]
 */
// Type-argument T is just used here to make sure the interface gets implemented by an actual BlockEntity
public interface BombBlockEntity<T> : Container
        where T : BlockEntity,
              T : Container,
              T : BombBlockEntity<T>
{
    public val requiresComponentsToDetonate: Boolean get() = getRequiredDetonationComponents().isNotEmpty()

    /** Returns a Map of all required components */
    public fun getRequiredDetonationComponents(): Map<out Supplier<out Item>, Int>

    /**
     * Returns `true` when all items are contained in the inventory.
     *
     * @exception[NullPointerException] When called before items are registered.
     */
    public fun isComplete(): Boolean {
        return when {
            !requiresComponentsToDetonate -> true
            isEmpty -> false
            else -> {
                val componentMap = getRequiredDetonationComponents().mapKeys { (supplier, _) -> supplier.get() }.toMutableMap()
                // for each matching component, subtract the amount contained from the map
                for (i in 0 until containerSize) getItem(i).let { itemStack ->
                    if (!itemStack.isEmpty && componentMap[itemStack.item] != null)
                        componentMap[itemStack.item] = componentMap.getValue(itemStack.item) - itemStack.count
                }
                // if all map values are equal or below 0, then all components are present
                return componentMap.values.all { it <= 0 }
            }
        }
    }

    /**
     * Checks whether the explosive can be detonated
     *
     * Can be used to implement extra checks
     */
    public fun canDetonate(): Boolean = isComplete()

    /**
     * Detonates the explosive and returns whether it was successful
     *
     * Logic for removing the block, playing sounds and spawning the explosions should be handled here.
     * While not strictly necessary, it is recommended to check for server-side.
     */
    public fun detonate(): Boolean
}
