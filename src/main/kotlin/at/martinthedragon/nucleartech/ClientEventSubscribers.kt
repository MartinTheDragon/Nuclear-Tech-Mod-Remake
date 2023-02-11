package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.extensions.*
import at.martinthedragon.nucleartech.hazard.HazardSystem
import at.martinthedragon.nucleartech.rendering.NTechCapes
import at.martinthedragon.nucleartech.screen.AnvilScreen
import at.martinthedragon.nucleartech.screen.UseTemplateFolderScreen
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.*
import net.minecraft.world.entity.Entity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RecipesUpdatedEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.event.TagsUpdatedEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.VersionChecker
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

    @SubscribeEvent @JvmStatic
    fun onRenderPlayer(event: RenderPlayerEvent.Pre) {
        NTechCapes.renderCape(event.player, event.renderer)
    }

    @SubscribeEvent @JvmStatic
    fun onRenderPlayerPost(event: RenderPlayerEvent.Post) {
        NTechCapes.renderCapePost(event.renderer)
    }

    @SubscribeEvent @JvmStatic
    fun clientVersionCheckChatMessage(event: EntityJoinWorldEvent) {
        val entity: Entity = event.entity
        if (entity === Minecraft.getInstance().player) {
            val message = createVersionUpdateChatMessage()
            message?.let {
                entity.displayClientMessage(LangKeys.VERSION_CHECKER_ANNOUNCEMENT.gold(), false)
                entity.displayClientMessage(it, false)
            }
        }
    }

    private fun createVersionUpdateChatMessage(): Component? {
        val currentVersion = NuclearTech.currentVersion ?: return null
        if (!NuclearConfig.client.displayUpdateMessage.get() || currentVersion == "0.0NONE") return null
        val versionCheckResult = NuclearTech.versionCheckResult ?: return null
        return when (versionCheckResult.status) {
            VersionChecker.Status.PENDING, VersionChecker.Status.FAILED, VersionChecker.Status.UP_TO_DATE, null -> null
            VersionChecker.Status.BETA, VersionChecker.Status.AHEAD -> {
                val cuttingEdgeMessage = if (NuclearTech.isSnapshot) LangKeys.VERSION_CHECKER_BLEEDING_EDGE.red() else LangKeys.VERSION_CHECKER_CUTTING_EDGE.gold()
                cuttingEdgeMessage.append(TextComponent(" ($currentVersion)").white())
            }
            VersionChecker.Status.OUTDATED, VersionChecker.Status.BETA_OUTDATED -> {
                LangKeys.VERSION_CHECKER_UPDATE.yellow()
                    .append(TextComponent(" ($currentVersion -> ").white())
                    .append(TextComponent("${versionCheckResult.target}").blue().underline().withStyle(Style.EMPTY
                        .withHoverEvent(HoverEvent(HoverEvent.Action.SHOW_TEXT, LangKeys.VERSION_CHECKER_VIEW_RELEASES.gray()))
                        .withClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, versionCheckResult.url))))
                    .append(TextComponent(")").white())
                    .append("\n")
                    .run { if (versionCheckResult.changes.isNotEmpty()) append(LangKeys.VERSION_CHECKER_CHANGES_LIST.yellow()) else this }
                    .run { var next = this; for (change in versionCheckResult.changes.values.flatMap { it.split("\r\n", "\n", "\r") }) next = next.append(TextComponent('\n' + change.prependIndent()).white()); next }
            }
        }
    }
}
