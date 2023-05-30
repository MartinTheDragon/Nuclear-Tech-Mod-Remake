package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.extensions.*
import at.martinthedragon.nucleartech.getRecipeManagerClient
import at.martinthedragon.nucleartech.getRecipeManagerServer
import at.martinthedragon.nucleartech.recipe.ChemRecipe
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.rendering.CustomBEWLR
import at.martinthedragon.nucleartech.rendering.SpecialModels
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraft.world.level.Level
import net.minecraftforge.client.IItemRenderProperties
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.DistExecutor.SafeSupplier
import org.intellij.lang.annotations.Language
import java.util.function.Consumer
import kotlin.math.floor

class ChemPlantTemplateItem(properties: Properties) : Item(properties) {
    override fun getName(stack: ItemStack): Component = TranslatableComponent(getDescriptionId(stack), Minecraft.getInstance().level?.use { getRecipeFromStack(stack, it.recipeManager)?.recipeID }?.let { TranslatableComponent("${LangKeys.CAT_CHEMISTRY}.${it.namespace}.${it.path.removePrefix("chem/")}") } ?: "N/A")

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        val recipeManager = DistExecutor.safeRunForDist({ SafeSupplier(::getRecipeManagerClient) }) { SafeSupplier(::getRecipeManagerServer) } ?: return
        if (allowdedIn(tab)) {
            items.addAll(getAllChemTemplates(recipeManager))
        }
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        if (level == null) return
        val recipe = getRecipeFromStack(stack, level.recipeManager) ?: return

        fun formatFluid(fluidStack: FluidStack) = TextComponent("  ${fluidStack.amount}mb ").append(fluidStack.displayName).gray()

        with(tooltip) {
            add(LangKeys.INFO_OUTPUTS.bold().gray())
            for (output in recipe.resultsList) add(TextComponent("  ${output.count}x ").append(output.hoverName).gray())
            if (!recipe.outputFluid1.isEmpty) add(formatFluid(recipe.outputFluid1))
            if (!recipe.outputFluid2.isEmpty) add(formatFluid(recipe.outputFluid2))
            add(LangKeys.INFO_INPUTS.bold().gray())
            for (input in recipe.ingredientsList) add(TextComponent("  ${input.requiredAmount}x ").append(input.items.first().hoverName).gray())
            if (!recipe.inputFluid1.isEmpty) add(formatFluid(recipe.inputFluid1))
            if (!recipe.inputFluid2.isEmpty) add(formatFluid(recipe.inputFluid2))
            add(LangKeys.INFO_PRODUCTION_TIME.bold().gray())
            add(TextComponent("  ${floor(recipe.duration / 20F * 100F) / 100F} ").append(LangKeys.WORD_SECONDS.get()).gray())
            if (flag.isAdvanced) add(TextComponent(recipe.id.toString()).italic().blue())
        }
    }

    override fun initializeClient(consumer: Consumer<IItemRenderProperties>) {
        consumer.accept(object : IItemRenderProperties {
            override fun getItemStackRenderer() = CustomBEWLR
        })
    }

    companion object {
        fun getRecipeIDFromStack(stack: ItemStack) = if (stack.item is ChemPlantTemplateItem) stack.tag?.getString("recipe")?.let { ResourceLocation(it) } else null
        fun getRecipeFromStack(stack: ItemStack, recipeManager: RecipeManager) = getRecipeIDFromStack(stack)?.let { recipeManager.byKey(it).orElse(null) as? ChemRecipe }
        fun getAllChemTemplates(recipeManager: RecipeManager): List<ItemStack> = recipeManager.getAllRecipesFor(RecipeTypes.CHEM).map { ItemStack(
            NTechItems.chemTemplate.get()).apply { orCreateTag.putString("recipe", it.id.toString()) } }
        fun isValidTemplate(stack: ItemStack, recipeManager: RecipeManager) = getRecipeFromStack(stack, recipeManager) != null
        fun createWithID(id: ResourceLocation) = ItemStack(NTechItems.chemTemplate.get(), 1).apply { orCreateTag.putString("recipe", id.toString()) }

        fun generateTemplateIcons(modelBakery: ModelBakery) {
            val sprites = getChemistrySpriteLocations(modelBakery.resourceManager)
            for (sprite in sprites) {
                @Language("JSON")
                val model = BlockModel.fromString("""
                    {
                      "parent": "minecraft:item/generated",
                      "textures": {
                        "layer0": "$sprite"
                      }
                    }
                """.trimIndent())
                SpecialModels.injectIntoModelBakery(modelBakery, sprite, model)
            }
        }

        fun getChemistrySpriteLocations(resourceManager: ResourceManager) = resourceManager.listResources("textures/chemistry_icons") { it.endsWith(".png") }.map { ResourceLocation(it.namespace, it.path.removePrefix("textures/").removeSuffix(".png")) }
    }
}
