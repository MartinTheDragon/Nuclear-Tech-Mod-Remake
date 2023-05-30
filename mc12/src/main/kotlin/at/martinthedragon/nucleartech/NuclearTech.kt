package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.coremodules.koinApp
import at.martinthedragon.nucleartech.coremodules.minecraftClientModule
import at.martinthedragon.nucleartech.coremodules.minecraftModule
import at.martinthedragon.nucleartech.coremodules.registriesModule
import at.martinthedragon.nucleartech.registries.Registries
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.relauncher.Side
import org.koin.dsl.koinApplication

@Mod(modid = NuclearTech.MODID)
class NuclearTech : ModClass {
    init {
        koinApp = koinApplication {
            modules(
                minecraftModule,
                registriesModule
            )
            if (FMLCommonHandler.instance().side == Side.CLIENT)
                modules(
                    minecraftClientModule
                )
        }
        entryPoint()

        MinecraftForge.EVENT_BUS.apply {
            register(this)
            register(Registries.itemRegistry)
        }
    }

    override val LOGGER: Logger
        get() = TODO("Not yet implemented")
    override val polaroidBroken: Boolean
        get() = false // TODO

    companion object {
        const val MODID = "nucleartech"
        lateinit var LOGGER: org.apache.logging.log4j.Logger
    }
}
