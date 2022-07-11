package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.recipes.getItems
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.server.TickTask
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraftforge.common.util.LogicalSidedProvider
import net.minecraftforge.fml.LogicalSide
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

    fun applyWorldHazards(itemEntity: ItemEntity) {
        for (hazard in getHazardsForStack(itemEntity.item))
            hazard.applyWorldHazard(itemEntity)
    }

    private val itemEntities = mutableListOf<ItemEntity>()

    fun trackItemEntity(itemEntity: ItemEntity) {
        val stack = itemEntity.item
        if (itemEntity.level.isClientSide && stack.`is`(Items.AIR)) { // basically loops for each tick until the item gets synced
            val executor = LogicalSidedProvider.WORKQUEUE.get(LogicalSide.CLIENT)
            executor.tell(TickTask(0) { trackItemEntity(itemEntity) })
            return
        }

        if (getHazardsForStack(stack).isNotEmpty()) {
            itemEntities += itemEntity
        }
    }

    fun stopTrackingItemEntity(itemEntity: ItemEntity) {
        itemEntities -= itemEntity
    }

    fun tickWorldHazards() {
        itemEntities.toList().forEach(this::applyWorldHazards)
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
        val tagData = stack.tags.map { tagMap.getValue(it) }.collect(Collectors.toList())
        val itemData = itemMap.getValue(stack.item)
        return (tagData + itemData).filterNot { it === HazardData.EMPTY }.flatMap(HazardData::getEntries)
    }
}
