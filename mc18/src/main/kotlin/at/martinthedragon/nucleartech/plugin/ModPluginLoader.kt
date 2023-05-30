package at.martinthedragon.nucleartech.plugin

import at.martinthedragon.nucleartech.api.ModPlugin
import at.martinthedragon.nucleartech.api.NuclearTechPlugin
import com.mojang.logging.LogUtils
import net.minecraftforge.fml.ModList
import org.objectweb.asm.Type

object ModPluginLoader {
    private val LOGGER = LogUtils.getLogger()

    fun getModPlugins(): List<ModPlugin> {
        val annotationType = Type.getType(NuclearTechPlugin::class.java)
        val pluginClassNames = buildList {
            ModList.get().allScanData.forEach { scanData ->
                scanData.annotations.forEach {
                    if (it.annotationType == annotationType) add(it.memberName)
                }
            }
        }

        return pluginClassNames.mapNotNull {
            try {
                val plugin = Class.forName(it).asSubclass(ModPlugin::class.java).getDeclaredConstructor().newInstance()
                LOGGER.debug("Found NTM plugin with ID '${plugin.id}' at $it")
                plugin
            } catch (ex: ReflectiveOperationException) {
                LOGGER.error("Failed to load: $it\n${ex.stackTraceToString()}")
                null
            } catch (ex: LinkageError) { // TODO change this when kotlin supports union types
                LOGGER.error("Failed to load: $it\n${ex.stackTraceToString()}")
                null
            }
        }
    }
}
