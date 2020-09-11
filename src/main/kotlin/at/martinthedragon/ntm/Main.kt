package at.martinthedragon.ntm

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

typealias CT = CreativeTabs
typealias B = ModBlocks
typealias BI = ModBlockItems
typealias I = ModItems

@Mod(Main.MODID)
class Main {
    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    companion object {
        const val MODID = "ntm"

        val logger: Logger = LogManager.getLogger()
    }
}
