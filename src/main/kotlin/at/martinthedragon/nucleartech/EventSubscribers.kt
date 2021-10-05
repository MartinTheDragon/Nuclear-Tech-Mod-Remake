package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.capabilites.contamination.ContaminationCapabilityProvider
import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent

@Suppress("unused", "UNUSED_PARAMETER")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent @JvmStatic
    fun onWorldTick(event: TickEvent.WorldTickEvent) {
        if (event.phase == TickEvent.Phase.START)
            Radiation.applyRadiationEffects(event.world)
    }

    @SubscribeEvent @JvmStatic
    fun onServerStopped(event: FMLServerStoppedEvent) {
        Radiation.irradiatedEntityList = emptyList()
    }

    @SubscribeEvent @JvmStatic
    fun attachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
        if (event.`object` is LivingEntity)
            event.addCapability(ResourceLocation(NuclearTech.MODID, "irradiation_cap"), ContaminationCapabilityProvider())
    }

    @SubscribeEvent @JvmStatic
    fun onLivingUpdate(event: LivingEvent.LivingUpdateEvent) {
        EntityContaminationEffects.update(event.entityLiving)
    }
}
