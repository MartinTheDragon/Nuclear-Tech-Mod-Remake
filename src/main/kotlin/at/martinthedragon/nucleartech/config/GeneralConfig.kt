package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class GeneralConfig : ConfigBase {
    override val fileName = "general"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.SERVER

    val enable528Mode: ForgeConfigSpec.BooleanValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("General Config. Synced from server to client.").push(fileName)

            comment("528 Mode settings").push("528")
            enable528Mode = comment("The central toggle for 528 mode").define("enable528Mode", false)
            pop()

            pop()
            configSpec = build()
        }
    }
}
