package at.martinthedragon.nucleartech.coremodules.minecraft.world.item.enchantment

import at.martinthedragon.nucleartech.coremodules.minecraft.world.entity.EquipmentSlot
import at.martinthedragon.nucleartech.sorcerer.Linkage
import at.martinthedragon.nucleartech.sorcerer.Linkage.Companion.MC18

open class Enchantment(val rarity: Rarity, val category: EnchantmentCategory, val slots: Array<EquipmentSlot>) {
    @Linkage(MC18, "net.minecraft.world.item.enchantment.Enchantment.Rarity")
    enum class Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        VERY_RARE
    }
}
