package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.items.AssemblyTemplate
import at.martinthedragon.nucleartech.recipes.StackedIngredient
import at.martinthedragon.nucleartech.recipes.containerSatisfiesRequirements
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.common.Tags
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class CraftMachineTemplateMessage(val result: ItemStack) : NetworkMessage<CraftMachineTemplateMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeItem(result)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer)
            context.get().enqueueWork {
                val sender = context.get().sender ?: return@enqueueWork

                val folderResults = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name)
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

                    val pressStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.FOLDER_STAMPS.name)
                    val sirenTracks = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.SIREN_TRACKS.name)
                    when (result.item) {
                        is AssemblyTemplate -> {
                            if (!AssemblyTemplate.isValidTemplate(result, sender.level.recipeManager)) return@enqueueWork

                            // h
                            val ingredients = listOf(StackedIngredient.of(1, Items.PAPER), StackedIngredient.of(1, Tags.Items.DYES))
                            if (ingredients.containerSatisfiesRequirements(sender.inventory))
                                ingredients.containerSatisfiesRequirements(sender.inventory, true)
                            else return@enqueueWork
                        }
                        in pressStamps -> {
                            val stoneStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.STONE_STAMPS.name)
                            val ironStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.IRON_STAMPS.name)
                            val steelStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.STEEL_STAMPS.name)
                            val titaniumStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.TITANIUM_STAMPS.name)
                            val obsidianStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.OBSIDIAN_STAMPS.name)
                            val schrabidiumStamps = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.SCHRABIDIUM_STAMPS.name)
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
                                sender.inventory.items.firstOrNull { it.item in NuclearTags.Items.PLATES_INSULATOR }?.item,
                                sender.inventory.items.firstOrNull { it.item in NuclearTags.Items.PLATES_STEEL }?.item
                            )) { return@enqueueWork }
                        else -> if (!removeIfPossible(sender,
                                Items.PAPER,
                                sender.inventory.items.firstOrNull { it.item in Tags.Items.DYES }?.item
                            )) { return@enqueueWork }
                    }
                }

                val stack = result.copy()
                val successful = sender.inventory.add(stack)
                if (successful && stack.isEmpty) {
                    stack.count = 1
                    sender.drop(stack, false)?.makeFakeItem()
                    sender.level.playSound(
                        null, sender.x, sender.y, sender.z,
                        net.minecraft.sounds.SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
                        .2F, ((sender.random.nextFloat() - sender.random.nextFloat()) * .7F + 1F) * 2F
                    )
                    sender.inventoryMenu.broadcastChanges()
                } else {
                    val droppedStack = sender.drop(stack, false)
                    if (droppedStack != null) {
                        droppedStack.setNoPickUpDelay()
                        droppedStack.owner = sender.uuid
                    }
                }
            }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = CraftMachineTemplateMessage(packetBuffer.readItem())
    }
}
