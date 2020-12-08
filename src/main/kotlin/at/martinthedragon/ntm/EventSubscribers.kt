package at.martinthedragon.ntm

import net.minecraftforge.event.world.BiomeLoadingEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object EventSubscribers {
    @SubscribeEvent(priority = EventPriority.HIGH)
    @JvmStatic
    fun biomeLoading(event: BiomeLoadingEvent) {
        WorldGeneration.generateOres(event)
    }
}
