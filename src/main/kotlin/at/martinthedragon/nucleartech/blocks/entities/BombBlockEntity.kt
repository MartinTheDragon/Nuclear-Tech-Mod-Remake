package at.martinthedragon.nucleartech.blocks.entities

import net.minecraft.world.Container
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.entity.BlockEntity
import java.util.function.Supplier

// Type-argument T is just used here to make sure the interface gets implemented by an actual TileEntity
interface BombBlockEntity<T> : Container
        where T : BlockEntity,
              T : Container,
              T : BombBlockEntity<T>
{
    val requiresComponentsToDetonate: Boolean get() = getRequiredDetonationComponents().isNotEmpty()

    /** Returns a Map of all required components */
    fun getRequiredDetonationComponents(): Map<out Supplier<Item>, Int>

    /**
     * Returns `true` when all items are contained in the inventory.
     *
     * @exception[NullPointerException] When called before items are registered.
     */
    fun isComplete(): Boolean {
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
    fun canDetonate() = isComplete()

    /** Detonates the explosive and returns whether it was successful */
    fun detonate(): Boolean
}
