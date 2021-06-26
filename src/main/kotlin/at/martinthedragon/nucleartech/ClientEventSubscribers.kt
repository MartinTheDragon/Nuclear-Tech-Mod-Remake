package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.screens.UseTemplateFolderScreen
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.TagsUpdatedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(Dist.CLIENT, modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ClientEventSubscribers {
    @SubscribeEvent @JvmStatic
    fun onTagsUpdated(event: TagsUpdatedEvent) {
        NuclearTech.LOGGER.debug("Reloading Machine Template Folder search tree")
        val templateFolderSearchTree = Minecraft.getInstance().getSearchTree(UseTemplateFolderScreen.SEARCH_TREE)
        templateFolderSearchTree.clear()
        ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name)
            .values
            .map(Item::getDefaultInstance)
            .forEach(templateFolderSearchTree::add)
        templateFolderSearchTree.refresh()
    }
}
