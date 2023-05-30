package at.martinthedragon.nucleartech.config

import at.martinthedragon.nucleartech.NuclearTech
import com.electronwill.nightconfig.core.file.CommentedFileConfig
import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.config.ConfigFileTypeHandler
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.loading.FMLPaths
import java.nio.file.Path
import java.util.function.Function

class NuclearModConfig(container: ModContainer, config: ConfigBase) :
    ModConfig(config.configType, config.configSpec, container, "${NuclearTech.MODID}/${config.fileName}.toml")
{
    override fun getHandler(): ConfigFileTypeHandler = NuclearConfigFileHandler

    private object NuclearConfigFileHandler : ConfigFileTypeHandler() {
        private fun redirectPath(path: Path) = if (path.endsWith("serverconfig"))
            FMLPaths.CONFIGDIR.get()
        else path

        override fun reader(configBasePath: Path): Function<ModConfig, CommentedFileConfig> =
            super.reader(redirectPath(configBasePath))

        override fun unload(configBasePath: Path, config: ModConfig) =
            super.unload(redirectPath(configBasePath), config)
    }
}
