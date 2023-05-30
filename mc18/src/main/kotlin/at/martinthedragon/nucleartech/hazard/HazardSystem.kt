package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipe.getItems
import com.google.common.collect.ImmutableMap
import io.netty.channel.ChannelHandler
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import net.minecraft.client.Minecraft
import net.minecraft.network.Connection
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.network.filters.VanillaPacketFilter
import java.util.function.BiConsumer
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

    private val itemEntities = IntOpenHashSet()

    fun trackItemEntity(itemEntity: ItemEntity) {
        val stack = itemEntity.item
        if (itemEntity.level.isClientSide && stack.isEmpty) return

        trackItemEntity(itemEntity.id, stack)
    }

    private fun trackItemEntity(entityId: Int, stack: ItemStack) {
        if (getHazardsForStack(stack).isNotEmpty()) {
            itemEntities += entityId
        }
    }

    fun stopTrackingItemEntity(itemEntity: ItemEntity) {
        itemEntities -= itemEntity.id
    }

    fun tickWorldHazards(level: Level) {
        itemEntities.map(level::getEntity).filterIsInstance<ItemEntity>().forEach(this::applyWorldHazards)
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

    @ChannelHandler.Sharable
    class ItemDataPacketDetector : VanillaPacketFilter(buildHandlers()) {
        override fun isNecessary(manager: Connection?) = FMLEnvironment.dist == Dist.CLIENT

        companion object {
            val NAME = ntm("hazard_system_item_data_detector")

            private fun onEntityData(packet: ClientboundSetEntityDataPacket): ClientboundSetEntityDataPacket {
                if (packet.unpackedData?.any { it.accessor.serializer == EntityDataSerializers.ITEM_STACK } == true)
                    Minecraft.getInstance().execute {
                        val itemStack = packet.unpackedData?.find { it.accessor.serializer == EntityDataSerializers.ITEM_STACK }?.value as? ItemStack?
                        if (itemStack != null && !itemStack.isEmpty) {
                            trackItemEntity(packet.id, itemStack)
                        }
                    }

                return packet
            }

            fun buildHandlers(): Map<Class<out Packet<*>>, BiConsumer<Packet<*>, MutableList<in Packet<*>>>> =
                ImmutableMap.builder<Class<out Packet<*>>, BiConsumer<Packet<*>, MutableList<in Packet<*>>>>()
                    .put(handler(ClientboundSetEntityDataPacket::class.java, ItemDataPacketDetector::onEntityData))
                    .build()
        }
    }
}
