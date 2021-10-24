package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

interface ConfigBase {
    val fileName: String
    val configSpec: ForgeConfigSpec
    val configType: ModConfig.Type
}
