package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.recipes.getItems
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import java.util.stream.Collectors

object HazardSystem {
    fun applyHazardsInInventory(target: LivingEntity) {
        if (target is Player) {
            for (stack in target.inventory.getItems(false)) applyHazards(stack, target)
        } else {
            for (stack in target.allSlots) applyHazards(stack, target)
        }
    }

    fun applyHazards(stack: ItemStack, target: LivingEntity) {
        for (hazard in getHazardsForStack(stack))
            hazard.applyHazard(stack, target)
    }

    fun addHoverText(stack: ItemStack, player: Player?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        val hazards = getHazardsForStack(stack)
        if (hazards.isEmpty()) return

        val tooltipScan = mutableListOf<Component>()
        tooltipScan += TextComponent("NTM Hazard Tooltip Scan")
        stack.item.appendHoverText(stack, player?.level, tooltipScan, flag)
        val tooltipSkip = tooltipScan.size

        val extraInfo = tooltip.size - tooltipSkip
        val hazardTooltipView = tooltip.subList(0, tooltipSkip)

        if (tooltipScan.size > 1) hazardTooltipView += TextComponent.EMPTY

        for (hazardEntry in getHazardsForStack(stack)) {
            hazardEntry.hazard.appendHoverText(stack, hazardEntry.level, hazardEntry.getMods(), player, hazardTooltipView, flag)
        }

        if (extraInfo > 0) hazardTooltipView += TextComponent.EMPTY
    }

    fun register(tag: TagKey<Item>, data: HazardData) {
        tagMap[tag] = data
    }

    fun register(item: Item, data: HazardData) {
        itemMap[item] = data
    }

    private val tagMap = mutableMapOf<TagKey<Item>, HazardData>().withDefault { HazardData.EMPTY }
    private val itemMap = mutableMapOf<Item, HazardData>().withDefault { HazardData.EMPTY }

    fun getHazardsForStack(stack: ItemStack): List<HazardData.HazardEntry> {
        val tagData = stack.tags.map { tagMap.getValue(it) }.collect(Collectors.toList()).filterNot { it == HazardData.EMPTY }
        val itemData = itemMap.getValue(stack.item)
        return (tagData + itemData).filterNot { it === HazardData.EMPTY }.flatMap(HazardData::getEntries)
    }
}
