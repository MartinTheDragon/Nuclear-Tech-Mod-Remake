package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.api.item.AttackHandler
import at.martinthedragon.nucleartech.api.item.DamageHandler
import at.martinthedragon.nucleartech.api.item.FallHandler
import at.martinthedragon.nucleartech.api.item.TickingArmor
import at.martinthedragon.nucleartech.capability.contamination.EntityContaminationHandler
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.extensions.*
import at.martinthedragon.nucleartech.fallout.FalloutTransformationManager
import at.martinthedragon.nucleartech.hazard.EntityContaminationEffects
import at.martinthedragon.nucleartech.hazard.HazardSystem
import at.martinthedragon.nucleartech.hazard.HazmatValues
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.*
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.item.ItemEntity
import net.minecraftforge.event.AddReloadListenerEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.ItemAttributeModifierEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.EntityLeaveWorldEvent
import net.minecraftforge.event.entity.living.*
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.VersionChecker
import net.minecraftforge.fml.common.Mod
import kotlin.math.roundToInt

@Suppress("unused")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent @JvmStatic
    fun addServerResources(event: AddReloadListenerEvent) {
        event.addListener(FalloutTransformationManager)
    }

    @SubscribeEvent @JvmStatic
    fun attachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is LivingEntity)
            event.addCapability(ntm("contamination"), EntityContaminationHandler())
    }

    @SubscribeEvent @JvmStatic
    fun clientVersionCheckChatMessage(event: EntityJoinWorldEvent) {
        if (!event.world.isClientSide) return
        val entity: Entity = event.entity
        if (entity === Minecraft.getInstance().player) {
            val message = createVersionUpdateChatMessage()
            message?.let {
                entity.displayClientMessage(LangKeys.VERSION_CHECKER_ANNOUNCEMENT.gold(), false)
                entity.displayClientMessage(it, false)
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    @JvmStatic
    fun trackItemEntityHazardSystem(event: EntityJoinWorldEvent) {
        val entity = event.entity
        if (entity is ItemEntity) HazardSystem.trackItemEntity(entity)
    }

    @SubscribeEvent @JvmStatic
    fun stopTrackingItemEntityHazardSystem(event: EntityLeaveWorldEvent) {
        val entity = event.entity
        if (entity is ItemEntity) HazardSystem.stopTrackingItemEntity(entity)
    }

    @SubscribeEvent @JvmStatic
    fun tickHazardSystem(event: TickEvent.WorldTickEvent) {
        if (event.phase == TickEvent.Phase.END) HazardSystem.tickWorldHazards(event.world)
    }

    @SubscribeEvent @JvmStatic
    fun tickHazardSystemClient(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) Minecraft.getInstance().level?.let { HazardSystem.tickWorldHazards(it) }
    }

    @SubscribeEvent @JvmStatic
    fun onLivingUpdate(event: LivingEvent.LivingUpdateEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is TickingArmor) item.handleTick(event, it) }
        EntityContaminationEffects.update(event.entityLiving)
        HazardSystem.applyHazardsInInventory(event.entityLiving)
    }

    @SubscribeEvent @JvmStatic
    fun onLivingSpawn(event: LivingSpawnEvent.CheckSpawn) {
        if (EntityContaminationEffects.isRadiationImmune(event.entityLiving) || event.spawnReason != MobSpawnType.NATURAL) return
        val world = event.world
        val pos = BlockPos(event.x.roundToInt(), event.y.roundToInt(), event.z.roundToInt())
        if (ChunkRadiation.getRadiation(world, pos) > 2F) event.result = Event.Result.DENY
    }

    @SubscribeEvent @JvmStatic
    fun onLivingAttack(event: LivingAttackEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is AttackHandler) item.handleAttack(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun onLivingHurt(event: LivingHurtEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is DamageHandler) item.handleDamage(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun onLivingFall(event: LivingFallEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is FallHandler) item.handleFall(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun onCreativeFall(event: PlayerFlyableFallEvent) {
        event.entityLiving.armorSlots.forEach { val item = it.item; if (item is FallHandler) item.handleFall(event, it) }
    }

    @SubscribeEvent @JvmStatic
    fun modifyItemAttributes(event: ItemAttributeModifierEvent) {
        HazmatValues.addItemStackAttributes(event)
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
