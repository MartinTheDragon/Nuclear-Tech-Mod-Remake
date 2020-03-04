package at.martinthedragon.ntm.main

import at.martinthedragon.ntm.lib.Config
import at.martinthedragon.ntm.lib.MODID
import at.martinthedragon.ntm.worldgen.WorldGeneration
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object NTM {
    val logger: Logger = LogManager.getLogger()

    @JvmStatic
    @SubscribeEvent
    @Suppress("unused", "UNUSED_PARAMETER")
    fun commonSetup(event: FMLCommonSetupEvent) {
        logger.info("Hello world!")
        Config.generateWorldGenerationConfigs()
        WorldGeneration.generateOres()
    }
}