package at.martinthedragon.ntm

import at.martinthedragon.ntm.capabilites.IrradiationCapabilityProvider
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent(priority = EventPriority.HIGH)
    @JvmStatic
    fun biomeLoading(event: BiomeLoadingEvent) {
        WorldGeneration.generateOres(event)
    }

    @SubscribeEvent
    @JvmStatic
    fun onWorldTick(event: TickEvent.WorldTickEvent) {
        if (event.phase == TickEvent.Phase.START)
            Radiation.applyRadiationEffects(event.world)
    }

    @SubscribeEvent
    @JvmStatic
    fun onServerStopped(event: FMLServerStoppedEvent) {
        Radiation.irradiatedEntityList = emptyList()
    }

    @SubscribeEvent
    @JvmStatic
    fun attachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) { // What the hell this is literal sorcery!!!
        if (event.`object` is LivingEntity)
            event.addCapability(ResourceLocation(Main.MODID, "irradiation_cap"), IrradiationCapabilityProvider())
    }
}
