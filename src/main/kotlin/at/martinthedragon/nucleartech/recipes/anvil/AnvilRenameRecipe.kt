package at.martinthedragon.nucleartech.recipes.anvil

import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient

class AnvilRenameRecipe(recipeID: ResourceLocation) : SpecialAnvilRecipe(recipeID, 1) {
    private val nameTagIngredient = Ingredient.of(Items.NAME_TAG)

    override fun matches(container: Container): Boolean {
        return if (container.containerSize < 2) false
        else nameTagIngredient.test(container.getItem(1))
    }

    override fun assemble(container: Container): ItemStack {
        val output = container.getItem(0).copy()
        val name = container.getItem(1).hoverName.string.replace("\\&", "§")
        output.hoverName = TextComponent("§r$name")
        return output
    }

    override fun getSerializer() = RecipeSerializers.SMITHING_RENAMING.get()
}
