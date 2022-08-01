package at.martinthedragon.nucleartech.networking

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.menu.AnvilMenu
import at.martinthedragon.nucleartech.recipe.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier
import kotlin.math.floor

class AnvilConstructMessage(val recipe: ResourceLocation, val repeat: Boolean) : NetworkMessage<AnvilConstructMessage> {
    override fun encode(packetBuffer: FriendlyByteBuf) {
        packetBuffer.writeResourceLocation(recipe)
        packetBuffer.writeBoolean(repeat)
    }

    override fun handle(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction.receptionSide.isServer) context.get().enqueueWork {
            val sender = context.get().sender ?: return@enqueueWork
            val menu = sender.containerMenu
            if (menu !is AnvilMenu) return@enqueueWork
            val recipe = sender.level.recipeManager.byKey(recipe).takeIf { it.isPresent }?.get() as? AnvilConstructingRecipe ?: run {
                NuclearTech.LOGGER.warn("Player with UUID ${sender.stringUUID} sent bad AnvilConstructingRecipe ID '$recipe'")
                return@enqueueWork
            }
            if (!recipe.isTierWithinBounds(menu.tier)) return@enqueueWork

            val inventory = sender.inventory
            val requestedCraftAmount = if (repeat) {
                // If it is a recycling recipe, take the max stack size from the first ingredient, otherwise find the lowest stack size in the outputs
                if (recipe.overlay == AnvilConstructingRecipe.OverlayType.RECYCLING) floor(recipe.ingredientsList.first().items.first().maxStackSize.toFloat() / recipe.ingredientsList.first().requiredAmount).toInt()
                else recipe.results.minOf { floor(it.stack.maxStackSize.toFloat() / it.stack.count).toInt() }
            } else 1
            var amountToGive = 0
            for (i in 0 until requestedCraftAmount) {
                if (recipe.ingredientsList.containerSatisfiesRequirements(inventory)) {
                    recipe.ingredientsList.containerSatisfiesRequirements(inventory, true)
                    amountToGive++
                } else break
            }
            for (i in 0 until amountToGive) for ((output, chance) in recipe.results) if (chance == 1F || sender.random.nextFloat() < chance)
                if (!sender.addItem(output.copy())) sender.drop(output.copy(), false, true)
        }
        context.get().packetHandled = true
    }

    companion object {
        @JvmStatic
        fun decode(packetBuffer: FriendlyByteBuf) = AnvilConstructMessage(packetBuffer.readResourceLocation(), packetBuffer.readBoolean())
    }
}
