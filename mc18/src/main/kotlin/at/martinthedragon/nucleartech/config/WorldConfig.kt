package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class WorldConfig : ConfigBase {
    override val fileName = "world"
    override val configSpec: ForgeConfigSpec
    override val configType: ModConfig.Type = ModConfig.Type.SERVER

    val enableGlowingMyceliumSpread: ForgeConfigSpec.BooleanValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Config for world generation and world changes. Synced from server to client.").push(fileName)

            comment("World modification settings").push("modification")
            enableGlowingMyceliumSpread = comment("Whether Glowing Mycelium can assimilate other dirt-like blocks").define("enableGlowingMyceliumSpread", false)
            pop()

            pop()
            configSpec = build()
        }
    }
}
