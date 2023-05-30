package at.martinthedragon.nucleartech.api.hazard.radiation

import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Supplier

/**
 * Register the radiation protection values for armor here.
 *
 * The resistance values registered by [registerMaterial] or [registerArmorRepairItem] will be split across the different armor parts like this:
 *
 * | Slot  | Amount |
 * |-------|-------:|
 * | Head  | 20%    |
 * | Chest | 40%    |
 * | Legs  | 30%    |
 * | Feet  | 10%    |
 *
 * Values registered with [registerItem] will also get modified depending on the applicable slot.
 *
 * The actual radiation *modifier* is calculated this way: `10^(-coefficient)`
 *
 * Here's a listing of default values:
 *
 * | Material               | Coefficient | Resistance (approx) |
 * |------------------------|------------:|--------------------:|
 * | Iron                   | 0.0225      | 5%                  |
 * | Gold                   | 0.0225      | 5%                  |
 * | Netherite              | 0.08        | 17%                 |
 * | Steel                  | 0.045       | 10%                 |
 * | Titanium               | 0.045       | 10%                 |
 * | Advanced Alloy         | 0.07        | 15%                 |
 * | Cobalt                 | 0.125       | 25%                 |
 * | Hazmat                 | 0.6         | 50%                 |
 * | Advanced Hazmat        | 1           | 90%                 |
 * | Lead Reinforced Hazmat | 2           | 99%                 |
 * | PaA                    | 1.7         | 97%                 |
 * | Starmetal              | 1           | 90%                 |
 * | Combine Steel          | 1.3         | 95%                 |
 * | Schrabidium            | 3           | 99.9%               |
 * | Euphemium              | 10          | <100%               |
 *
 * For display on armor, coefficient values will be multiplied by 100 client-side.
 */
public interface HazmatRegistry {
    /**
     * When an armor's [ArmorMaterial.getName] matches the [material] name, [fullSetProtection] will be pulled.
     *
     * @return Whether the [material] was registered successfully
     */
    public fun registerMaterial(material: ArmorMaterial, fullSetProtection: Float): Boolean

    /**
     * When an armor's repair [Ingredient.test] returns true for [ingredient], [fullSetProtection] will be pulled if no matching [ArmorMaterial] registered via [registerMaterial] was found.
     *
     * @return Whether the [ingredient] was registered successfully
     */
    public fun registerArmorRepairItem(ingredient: Supplier<out Item>, fullSetProtection: Float): Boolean

    /**
     * Protection values registered here will take precedence over [registerMaterial] and [registerArmorRepairItem].
     *
     * @return Whether the [item] was registered successfully
     */
    public fun registerItem(item: Supplier<out Item>, protection: Float): Boolean
}
