package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.api.items.AttackHandler
import at.martinthedragon.nucleartech.api.items.DamageHandler
import at.martinthedragon.nucleartech.api.items.FallHandler
import at.martinthedragon.nucleartech.api.items.TickingArmor
import at.martinthedragon.nucleartech.capability.contamination.ContaminationCapabilityProvider
import at.martinthedragon.nucleartech.hazard.EntityContaminationEffects
import at.martinthedragon.nucleartech.hazard.HazardSystem
import at.martinthedragon.nucleartech.hazard.HazmatValues
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.ItemAttributeModifierEvent
import net.minecraftforge.event.entity.living.*
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.math.roundToInt

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent @JvmStatic
    fun attachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is LivingEntity)
            event.addCapability(ntm("contamination"), ContaminationCapabilityProvider())
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
}
