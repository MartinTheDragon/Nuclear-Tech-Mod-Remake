package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.extensions.blue
import at.martinthedragon.nucleartech.extensions.bold
import at.martinthedragon.nucleartech.extensions.gray
import at.martinthedragon.nucleartech.extensions.italic
import at.martinthedragon.nucleartech.recipe.AssemblyRecipe
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.rendering.CustomBEWLR
import net.minecraft.client.Minecraft
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.level.Level
import net.minecraftforge.client.IItemRenderProperties
import java.util.function.Consumer
import kotlin.math.floor

class AssemblyTemplateItem(properties: Properties) : Item(properties) {
    override fun getName(stack: ItemStack): Component = TranslatableComponent(getDescriptionId(stack), Minecraft.getInstance().level?.let { getRecipeFromStack(stack, it.recipeManager)?.resultItem?.hoverName } ?: "N/A")

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        val level = Minecraft.getInstance().level
        if (allowdedIn(tab) && level != null) {
            items.addAll(getAllTemplates(level.recipeManager))
        }
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltips: MutableList<Component>, flag: TooltipFlag) {
        if (level == null) return
        val recipe = getRecipeFromStack(stack, level.recipeManager) ?: return
        val resultItem = recipe.resultItem

        with(tooltips) {
            add(LangKeys.INFO_OUTPUT.bold().gray())
            add(TextComponent("${resultItem.count}x ".prependIndent("  ")).append(resultItem.hoverName).gray())
            add(LangKeys.INFO_INPUTS.bold().gray())
            for (input in recipe.ingredientsList) add(TextComponent("${input.requiredAmount}x ".prependIndent("  ")).append(input.items.first().hoverName).gray())
            add(LangKeys.INFO_PRODUCTION_TIME.bold().gray())
            add(TextComponent("${floor(recipe.duration / 20F * 100F) / 100F} ".prependIndent("  ")).append(LangKeys.WORD_SECONDS.get()).gray())
        }

        if (flag.isAdvanced) tooltips += TextComponent(recipe.id.toString()).italic().blue()
    }

    override fun initializeClient(consumer: Consumer<IItemRenderProperties>) {
        consumer.accept(object : IItemRenderProperties {
            override fun getItemStackRenderer() = CustomBEWLR
        })
    }

    companion object {
        fun getRecipeIDFromStack(stack: ItemStack) = if (stack.item is AssemblyTemplateItem) stack.tag?.getString("recipe")?.let { ResourceLocation(it) } else null
        fun getRecipeFromStack(stack: ItemStack, recipeManager: RecipeManager) = getRecipeIDFromStack(stack)?.let { recipeManager.byKey(it).orElse(null) as? AssemblyRecipe }
        fun getAllTemplates(recipeManager: RecipeManager): List<ItemStack> = recipeManager.getAllRecipesFor(RecipeTypes.ASSEMBLY).map { ItemStack(
            NTechItems.assemblyTemplate.get()).apply { orCreateTag.putString("recipe", it.id.toString()) } }
        fun isValidTemplate(stack: ItemStack, recipeManager: RecipeManager) = getRecipeFromStack(stack, recipeManager) != null
        fun createWithID(id: ResourceLocation) = ItemStack(NTechItems.assemblyTemplate.get(), 1).apply { orCreateTag.putString("recipe", id.toString()) }
    }
}
