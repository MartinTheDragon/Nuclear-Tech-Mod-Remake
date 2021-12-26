package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.screens.AnvilScreen
import at.martinthedragon.nucleartech.screens.UseTemplateFolderScreen
import net.minecraft.client.Minecraft
import net.minecraft.core.Registry
import net.minecraft.world.item.Item
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RecipesUpdatedEvent
import net.minecraftforge.event.TagsUpdatedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(Dist.CLIENT, modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ClientEventSubscribers {
    @SubscribeEvent @JvmStatic
    fun onTagsUpdated(event: TagsUpdatedEvent) {
        NuclearTech.LOGGER.debug("Reloading Machine Template Folder search tree")
        val templateFolderSearchTree = Minecraft.getInstance().getSearchTree(UseTemplateFolderScreen.searchTree)
        templateFolderSearchTree.clear()
        event.tagManager.getOrEmpty(Registry.ITEM_REGISTRY).getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name)
            .values
            .map(Item::getDefaultInstance)
            .forEach(templateFolderSearchTree::add)
        templateFolderSearchTree.refresh()
    }

    @SubscribeEvent @JvmStatic
    fun onRecipesUpdated(event: RecipesUpdatedEvent) {
        NuclearTech.LOGGER.debug("Reloading Anvil Constructing Recipes search tree")
        val anvilConstructingRecipeSearchTree = Minecraft.getInstance().getSearchTree(AnvilScreen.searchTree)
        anvilConstructingRecipeSearchTree.clear()
        event.recipeManager.getAllRecipesFor(RecipeTypes.CONSTRUCTING).forEach(anvilConstructingRecipeSearchTree::add)
        anvilConstructingRecipeSearchTree.refresh()
    }
}
