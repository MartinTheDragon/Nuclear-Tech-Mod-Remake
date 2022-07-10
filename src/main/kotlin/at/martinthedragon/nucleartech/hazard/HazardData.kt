package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.hazard.modifier.HazardModifier
import at.martinthedragon.nucleartech.hazard.modifier.evaluateAllModifiers
import at.martinthedragon.nucleartech.hazard.type.HazardType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack

class HazardData() {
    constructor(hazard: HazardType, level: Float = 1F) : this() {
        addEntry(hazard, level)
    }

    private val entries = mutableListOf<HazardEntry>()
    private var tagOverride = false

    fun overridesTagging(override: Boolean = true) {
        tagOverride = override
    }

    fun addEntry(hazard: HazardType, level: Float = 1F) = addEntry(HazardEntry(hazard, level))

    fun addEntry(entry: HazardEntry): HazardData {
        entries += entry
        return this
    }

    fun addEntries(entries: Collection<HazardEntry>): HazardData {
        for (entry in entries) addEntry(entry)
        return this
    }

    fun getEntries(): List<HazardEntry> = entries

    data class HazardEntry(val hazard: HazardType, val level: Float, private val mods: MutableList<HazardModifier> = mutableListOf()) {
        fun addMod(modifier: HazardModifier): HazardEntry {
            mods += modifier
            return this
        }

        fun applyHazard(itemStack: ItemStack, target: LivingEntity) {
            hazard.tick(target, itemStack, mods.evaluateAllModifiers(itemStack, target, level))
        }

        fun applyWorldHazard(itemEntity: ItemEntity) {
            hazard.tickDropped(itemEntity, mods.evaluateAllModifiers(itemEntity.item, null, level))
        }

        fun getMods(): List<HazardModifier> = mods
    }

    companion object {
        val EMPTY = HazardData()
    }
}
