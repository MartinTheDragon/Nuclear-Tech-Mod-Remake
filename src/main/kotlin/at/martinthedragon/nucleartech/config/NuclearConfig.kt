package at.martinthedragon.nucleartech.config

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.loading.FMLPaths

object NuclearConfig {
    val configDirectory = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(NuclearTech.MODID), NuclearTech.MODID)
    val general = GeneralConfig()
    val explosions = ExplosionsConfig()
    val fallout = FalloutConfig()
    val radiation = RadiationConfig()
    val world = WorldConfig()

    fun registerConfigs(container: ModContainer) {
        container.apply {
            addConfig(NuclearModConfig(this, general))
            addConfig(NuclearModConfig(this, explosions))
            addConfig(NuclearModConfig(this, fallout))
            addConfig(NuclearModConfig(this, radiation))
            addConfig(NuclearModConfig(this, world))
        }

        // TODO let players override configs for specific worlds
    }
}
