package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient

object NuclearArmorMaterials {
    val titanium: ArmorMaterial = Material(name("titanium"), 25, intArrayOf(3, 8, 6, 3), 9, SoundEvents.ARMOR_EQUIP_IRON, .5F, 0F, lazy { Ingredient.of(NuclearTags.Items.INGOTS_TITANIUM) })
    val steel: ArmorMaterial = Material(name("steel"), 20, intArrayOf(2, 6, 5, 2), 5, SoundEvents.ARMOR_EQUIP_IRON, .25F, 0F, lazy { Ingredient.of(NuclearTags.Items.INGOTS_STEEL) })
    val advancedAlloy: ArmorMaterial = Material(name("advanced_alloy"), 40, intArrayOf(3, 8, 6, 3), 9, SoundEvents.ARMOR_EQUIP_IRON, 1F, 0F, lazy { Ingredient.of(ModItems.advancedAlloyIngot.get()) })
    val combineSteel: ArmorMaterial = Material(name("combine_steel"), 60, intArrayOf(3, 8, 6, 3), 50, SoundEvents.ARMOR_EQUIP_IRON, 1.5F, .1F, lazy { Ingredient.of(ModItems.combineSteelIngot.get()) })
    val paAAlloy: ArmorMaterial = Material(name("paa_alloy"), 75, intArrayOf(3, 8, 6, 3), 25, SoundEvents.ARMOR_EQUIP_IRON, 2F, 0F, lazy { Ingredient.of(ModItems.paAAlloyPlate.get()) })
    val asbestos: ArmorMaterial = Material(name("asbestos"), 20, intArrayOf(1, 4, 3, 1), 5, SoundEvents.ARMOR_EQUIP_LEATHER, 0F, 0F, lazy { Ingredient.of(ModItems.fireProximityCloth.get()) })
    val schrabidium: ArmorMaterial = Material(name("schrabidium"), 100, intArrayOf(3, 8, 6, 3), 50, SoundEvents.ARMOR_EQUIP_IRON, 7F, .4F, lazy { Ingredient.of(ModItems.schrabidiumIngot.get()) })

    private fun name(name: String) = "${NuclearTech.MODID}:$name"

    private class Material(
        private val name: String,
        private val durabilityMultiplier: Int,
        private val slotProtections: IntArray,
        private val enchantmentValue: Int,
        private val sound: SoundEvent,
        private val toughness: Float,
        private val knockbackResistance: Float,
        private val repairIngredient: Lazy<Ingredient>
    ) : ArmorMaterial {
        override fun getName() = name
        override fun getEnchantmentValue() = enchantmentValue
        override fun getDurabilityForSlot(slot: EquipmentSlot) = slotHealths[slot.index] * durabilityMultiplier
        override fun getDefenseForSlot(slot: EquipmentSlot) = slotProtections[slot.index]
        override fun getEquipSound() = sound
        override fun getToughness() = toughness
        override fun getKnockbackResistance() = knockbackResistance
        override fun getRepairIngredient() = repairIngredient.value
    }

    private val slotHealths = intArrayOf(13, 15, 16, 11)
}
