package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.items.AssemblyTemplateItem
import at.martinthedragon.nucleartech.items.giveItemToInventory
import at.martinthedragon.nucleartech.recipes.StackedIngredient
import at.martinthedragon.nucleartech.recipes.containerSatisfiesRequirements
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.common.Tags
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier

class CraftMachineTemplateMessage(val result: ItemStack) : NetworkMessage<CraftMachineTemplateMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeItem(result)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer)
            context.get().enqueueWork {
                val sender = context.get().sender ?: return@enqueueWork

                val tagManager = ForgeRegistries.ITEMS.tags() ?: throw IllegalStateException("No tag manager bound to items")
                val folderResults = tagManager.getTag(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS)
                if (result.item !in folderResults) return@enqueueWork

                if (!sender.inventory.getSelected().sameItem(ModItems.machineTemplateFolder.get().defaultInstance) &&
                    !sender.inventory.offhand.first().sameItem(ModItems.machineTemplateFolder.get().defaultInstance)) {
                    return@enqueueWork // h4xx0r detected
                }

                // check whether the player is creative or has the random necessary
                if (!sender.isCreative) {
                    fun removeIfPossible(player: ServerPlayer, vararg items: Item?): Boolean {
                        if (items.any { it == null }) return false

                        val slots = IntArray(items.size)
                        for (i in items.indices) slots[i] = player.inventory.findSlotMatchingUnusedItem(items[i]!!.defaultInstance)
                        if (slots.any { it == -1 }) return false
                        for (i in slots.indices) player.inventory.removeItem(slots[i], 1)
                        return true
                    }

                    val pressStamps = tagManager.getTag(NuclearTags.Items.FOLDER_STAMPS)
                    val sirenTracks = tagManager.getTag(NuclearTags.Items.SIREN_TRACKS)
                    when (result.item) {
                        is AssemblyTemplateItem -> {
                            if (!AssemblyTemplateItem.isValidTemplate(result, sender.level.recipeManager)) return@enqueueWork

                            // h
                            val ingredients = listOf(StackedIngredient.of(1, Items.PAPER), StackedIngredient.of(1, Tags.Items.DYES))
                            if (ingredients.containerSatisfiesRequirements(sender.inventory))
                                ingredients.containerSatisfiesRequirements(sender.inventory, true)
                            else return@enqueueWork
                        }
                        in pressStamps -> {
                            val stoneStamps = tagManager.getTag(NuclearTags.Items.STONE_STAMPS)
                            val ironStamps = tagManager.getTag(NuclearTags.Items.IRON_STAMPS)
                            val steelStamps = tagManager.getTag(NuclearTags.Items.STEEL_STAMPS)
                            val titaniumStamps = tagManager.getTag(NuclearTags.Items.TITANIUM_STAMPS)
                            val obsidianStamps = tagManager.getTag(NuclearTags.Items.OBSIDIAN_STAMPS)
                            val schrabidiumStamps = tagManager.getTag(NuclearTags.Items.SCHRABIDIUM_STAMPS)
                            when (result.item) {
                                in stoneStamps -> if (!removeIfPossible(sender, ModItems.stoneFlatStamp.get())) return@enqueueWork
                                in ironStamps -> if (!removeIfPossible(sender, ModItems.ironFlatStamp.get())) return@enqueueWork
                                in steelStamps -> if (!removeIfPossible(sender, ModItems.steelFlatStamp.get())) return@enqueueWork
                                in titaniumStamps -> if (!removeIfPossible(sender, ModItems.titaniumFlatStamp.get())) return@enqueueWork
                                in obsidianStamps -> if (!removeIfPossible(sender, ModItems.obsidianFlatStamp.get())) return@enqueueWork
                                in schrabidiumStamps -> if (!removeIfPossible(sender, ModItems.schrabidiumFlatStamp.get())) return@enqueueWork
                                else -> return@enqueueWork
                            }
                        }
                        in sirenTracks -> if (!removeIfPossible(sender,
                                sender.inventory.items.firstOrNull { it.`is`(NuclearTags.Items.PLATES_INSULATOR) }?.item,
                                sender.inventory.items.firstOrNull { it.`is`(NuclearTags.Items.PLATES_STEEL) }?.item
                            )) { return@enqueueWork }
                        else -> if (!removeIfPossible(sender,
                                Items.PAPER,
                                sender.inventory.items.firstOrNull { it.`is`(Tags.Items.DYES) }?.item
                            )) { return@enqueueWork }
                    }
                }

                giveItemToInventory(sender, result.copy())
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = CraftMachineTemplateMessage(packetBuffer.readItem())
    }
}
