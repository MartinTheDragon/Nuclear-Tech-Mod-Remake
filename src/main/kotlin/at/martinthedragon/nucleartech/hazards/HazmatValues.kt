package at.martinthedragon.nucleartech.hazards

import at.martinthedragon.nucleartech.Attributes
import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraftforge.event.ItemAttributeModifierEvent
import net.minecraftforge.fml.util.thread.SidedThreadGroups
import java.util.*
import java.util.function.Supplier

// Attribute driven
object HazmatValues {
    private val HEAD_RADIATION_RESISTANCE_UUID = UUID.fromString("8ff5f9c5-b7e9-46a9-8dc7-f04d50119447")
    private val CHEST_RADIATION_RESISTANCE_UUID = UUID.fromString("9c0b6672-b841-4a4b-ad38-75d9bdd153f4")
    private val LEGS_RADIATION_RESISTANCE_UUID = UUID.fromString("ee0392ec-1eeb-4742-b1d1-1a1d298c6623")
    private val FEET_RADIATION_RESISTANCE_UUID = UUID.fromString("cdf5fcbe-af7e-4a74-8f1a-5dcb012d500a")
    private val SLOT_UUIDS = arrayOf(HEAD_RADIATION_RESISTANCE_UUID, CHEST_RADIATION_RESISTANCE_UUID, LEGS_RADIATION_RESISTANCE_UUID, FEET_RADIATION_RESISTANCE_UUID)

    private val defaultResistanceValues = mutableMapOf<Supplier<out Item>, Float>() // these are the repair items
    private val specialResistanceValues = mutableMapOf<Supplier<out Item>, Float>() // these are for specific items

    private val RESISTANCE_FRACTIONS = floatArrayOf(.2F, .4F, .3F, .1F)

    // Called for each ItemStack to calculate the values on the fly
    fun addItemStackAttributes(event: ItemAttributeModifierEvent) = with(event) {
        if (event.slotType.type == EquipmentSlot.Type.HAND) return

        val item = itemStack.item
        if (item is ArmorItem) {
            val slot = item.slot
            if (event.slotType != slot) return

            var resistance = specialResistanceValues[item.delegate] ?: defaultResistanceValues.firstNotNullOfOrNull { (repairItem, value) -> if (item.material.repairIngredient.test(repairItem.get().defaultInstance)) value else null } ?: return
            resistance *= RESISTANCE_FRACTIONS[slot.index]

            if (Thread.currentThread().threadGroup != SidedThreadGroups.SERVER) resistance *= 100 // For nicer display

            event.addModifier(Attributes.RADIATION_RESISTANCE.get(), AttributeModifier(SLOT_UUIDS[slot.index], "Armor radiation resistance", resistance.toDouble(), AttributeModifier.Operation.ADDITION))
        }
    }

    fun getPlayerResistance(player: Player): Float {
        var resistance = 0F
        resistance += player.getAttributeValue(Attributes.RADIATION_RESISTANCE.get()).toFloat()
        if (player.stringUUID == NuclearTech.SpecialUsers.Pu_238) resistance += .4F
        return resistance
    }

    init {
        defaultResistanceValues.putAll(mapOf(
            Supplier(Items::IRON_INGOT) to .0225F,
            Supplier(Items::GOLD_INGOT) to .0225F,
            Supplier(Items::NETHERITE_INGOT) to .08F,
            ModItems.steelIngot to .045F,
            ModItems.titaniumIngot to .045F,
            ModItems.advancedAlloyIngot to .07F,
            ModItems.cobaltIngot to .125F,
            ModItems.hazmatCloth to .6F,
            ModItems.advancedHazmatCloth to 1F,
            ModItems.leadReinforcedHazmatCloth to 2F,
            ModItems.paAAlloyPlate to 1.7F,
            ModItems.starmetalIngot to 1F,
            ModItems.combineSteelIngot to 1.3F,
            ModItems.schrabidiumIngot to 3F,
        ))
    }
}
