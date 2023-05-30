package at.martinthedragon.nucleartech.coremodules.minecraft.world.item.enchantment

import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.sorcerer.Linkage
import at.martinthedragon.nucleartech.sorcerer.Linkage.Companion.MC18

// TODO enum is extensible, dynamically create enum entries
@Linkage(MC18, "net.minecraft.world.item.enchantment.EnchantmentCategory")
enum class EnchantmentCategory {
    ARMOR,
    ARMOR_FEET,
    ARMOR_LEGS,
    ARMOR_CHEST,
    ARMOR_HEAD,
    WEAPON,
    DIGGER,
    FISHING_ROD,
    TRIDENT,
    BOW,
    WEARABLE,
    CROSSBOW,
    VANISHABLE;

    fun canEnchant(item: Item): Boolean = TODO()
}
