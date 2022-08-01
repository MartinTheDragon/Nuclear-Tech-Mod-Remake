package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.hazard.HazardSystem
import at.martinthedragon.nucleartech.screen.AnvilScreen
import at.martinthedragon.nucleartech.screen.UseTemplateFolderScreen
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RecipesUpdatedEvent
import net.minecraftforge.event.TagsUpdatedEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(Dist.CLIENT, modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ClientEventSubscribers {
    @SubscribeEvent @JvmStatic
    fun onItemTooltip(event: ItemTooltipEvent) {
        HazardSystem.addHoverText(event.itemStack, event.player, event.toolTip, event.flags)
    }

    @SubscribeEvent @JvmStatic
    fun onTagsUpdated(event: TagsUpdatedEvent) {
        UseTemplateFolderScreen.reloadSearchTree()
    }

    @SubscribeEvent @JvmStatic
    fun onRecipesUpdated(event: RecipesUpdatedEvent) {
        UseTemplateFolderScreen.reloadSearchTree()
        AnvilScreen.reloadSearchTree()
    }
}
