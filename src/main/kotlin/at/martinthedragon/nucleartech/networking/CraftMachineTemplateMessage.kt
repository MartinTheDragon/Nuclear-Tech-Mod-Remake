package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.item.AssemblyTemplateItem
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.giveItemToInventory
import at.martinthedragon.nucleartech.recipe.StackedIngredient
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
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
                val folderResults = tagManager.getTag(NTechTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS)
                if (result.item !in folderResults) return@enqueueWork

                if (!sender.inventory.getSelected().sameItem(NTechItems.machineTemplateFolder.get().defaultInstance) &&
                    !sender.inventory.offhand.first().sameItem(NTechItems.machineTemplateFolder.get().defaultInstance)) {
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

                    val pressStamps = tagManager.getTag(NTechTags.Items.FOLDER_STAMPS)
                    val sirenTracks = tagManager.getTag(NTechTags.Items.SIREN_TRACKS)
                    when (result.item) {
                        is AssemblyTemplateItem -> if (!AssemblyTemplateItem.isValidTemplate(result, sender.level.recipeManager) || !removeTemplateIngredients(sender)) return@enqueueWork
                        is ChemPlantTemplateItem -> if (!ChemPlantTemplateItem.isValidTemplate(result, sender.level.recipeManager) || !removeTemplateIngredients(sender)) return@enqueueWork
                        in pressStamps -> {
                            val stoneStamps = tagManager.getTag(NTechTags.Items.STONE_STAMPS)
                            val ironStamps = tagManager.getTag(NTechTags.Items.IRON_STAMPS)
                            val steelStamps = tagManager.getTag(NTechTags.Items.STEEL_STAMPS)
                            val titaniumStamps = tagManager.getTag(NTechTags.Items.TITANIUM_STAMPS)
                            val obsidianStamps = tagManager.getTag(NTechTags.Items.OBSIDIAN_STAMPS)
                            val schrabidiumStamps = tagManager.getTag(NTechTags.Items.SCHRABIDIUM_STAMPS)
                            when (result.item) {
                                in stoneStamps -> if (!removeIfPossible(sender, NTechItems.stoneFlatStamp.get())) return@enqueueWork
                                in ironStamps -> if (!removeIfPossible(sender, NTechItems.ironFlatStamp.get())) return@enqueueWork
                                in steelStamps -> if (!removeIfPossible(sender, NTechItems.steelFlatStamp.get())) return@enqueueWork
                                in titaniumStamps -> if (!removeIfPossible(sender, NTechItems.titaniumFlatStamp.get())) return@enqueueWork
                                in obsidianStamps -> if (!removeIfPossible(sender, NTechItems.obsidianFlatStamp.get())) return@enqueueWork
                                in schrabidiumStamps -> if (!removeIfPossible(sender, NTechItems.schrabidiumFlatStamp.get())) return@enqueueWork
                                else -> return@enqueueWork
                            }
                        }
                        in sirenTracks -> if (!removeIfPossible(sender,
                                sender.inventory.items.firstOrNull { it.`is`(NTechTags.Items.PLATES_INSULATOR) }?.item,
                                sender.inventory.items.firstOrNull { it.`is`(NTechTags.Items.PLATES_STEEL) }?.item
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

    private fun removeTemplateIngredients(sender: ServerPlayer): Boolean {
        val ingredients = listOf(StackedIngredient.of(1, Items.PAPER), StackedIngredient.of(1, Tags.Items.DYES))
        if (ingredients.containerSatisfiesRequirements(sender.inventory))
            ingredients.containerSatisfiesRequirements(sender.inventory, true)
        else return false
        return true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = CraftMachineTemplateMessage(packetBuffer.readItem())
    }
}
