package at.martinthedragon.nucleartech.coremodules.minecraft.world.entity

import at.martinthedragon.nucleartech.sorcerer.Linkage
import at.martinthedragon.nucleartech.sorcerer.Linkage.Companion.MC18

@Linkage(MC18, "net.minecraft.world.entity.EquipmentSlot")
enum class EquipmentSlot {
    MAINHAND,
    OFFHAND,
    FEET,
    LEGS,
    CHEST,
    HEAD
}
