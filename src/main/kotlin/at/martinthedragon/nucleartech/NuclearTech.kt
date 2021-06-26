package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(NuclearTech.MODID)
class NuclearTech {
    init {
        MinecraftForge.EVENT_BUS.register(this)
        NuclearPacketHandler.initialize()
    }

    companion object {
        const val MODID = "nucleartech"
        val LOGGER: Logger = LogManager.getLogger()
    }
}
