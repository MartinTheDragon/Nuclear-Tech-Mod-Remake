package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.capabilites.contamination.ContaminationCapabilityProvider
import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.living.LivingSpawnEvent
import net.minecraftforge.event.server.ServerStoppedEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import kotlin.math.roundToInt

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent @JvmStatic
    fun onWorldTick(event: TickEvent.WorldTickEvent) {
        if (event.phase == TickEvent.Phase.START)
            Radiation.applyRadiationEffects(event.world)
    }

    @SubscribeEvent @JvmStatic
    fun onServerStopped(event: ServerStoppedEvent) {
        Radiation.irradiatedEntityList = emptyList()
    }

    @SubscribeEvent @JvmStatic
    fun attachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is LivingEntity)
            event.addCapability(ntm("contamination"), ContaminationCapabilityProvider())
    }

    @SubscribeEvent @JvmStatic
    fun onLivingUpdate(event: LivingEvent.LivingUpdateEvent) {
        EntityContaminationEffects.update(event.entityLiving)
    }

    @SubscribeEvent @JvmStatic
    fun onLivingSpawn(event: LivingSpawnEvent.CheckSpawn) {
        if (EntityContaminationEffects.isRadiationImmune(event.entityLiving) || event.spawnReason != MobSpawnType.NATURAL) return
        val world = event.world
        val pos = BlockPos(event.x.roundToInt(), event.y.roundToInt(), event.z.roundToInt())
        if (ChunkRadiation.getRadiation(world, pos) > 2F) event.result = Event.Result.DENY
    }
}
