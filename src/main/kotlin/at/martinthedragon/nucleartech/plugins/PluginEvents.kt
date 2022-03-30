package at.martinthedragon.nucleartech.plugins

import at.martinthedragon.nucleartech.api.ModPlugin
import at.martinthedragon.nucleartech.api.NuclearTechRuntime
import at.martinthedragon.nucleartech.explosions.Explosions
import at.martinthedragon.nucleartech.hazards.HazmatValues
import com.mojang.logging.LogUtils

object PluginEvents {
    private val LOGGER = LogUtils.getLogger()
    private lateinit var plugins: List<ModPlugin>

    fun init() {
        LOGGER.debug("Getting NTM plugins list...")
        plugins = ModPluginLoader.getModPlugins()
        registerExplosionAlgorithms()
        registerHazmatProtectionValues()
        sendRuntime(NtmRuntimeImpl())
    }

    private fun registerExplosionAlgorithms() {
        LOGGER.debug("Registering explosion algorithms...")
        for (plugin in plugins) plugin.registerExplosions(Explosions)
    }

    private fun registerHazmatProtectionValues() {
        LOGGER.debug("Registering hazmat protection values...")
        for (plugin in plugins) plugin.registerHazmatValues(HazmatValues)
    }

    private fun sendRuntime(runtime: NuclearTechRuntime) {
        LOGGER.debug("Sending runtime instance to plugins...")
        for (plugin in plugins) plugin.onRuntime(runtime)
    }
}
