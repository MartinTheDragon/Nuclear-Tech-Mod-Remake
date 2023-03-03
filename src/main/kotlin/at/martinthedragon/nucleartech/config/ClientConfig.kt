package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class ClientConfig : ConfigBase {
    override val fileName = "client"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.CLIENT

    val displayUpdateMessage: ForgeConfigSpec.BooleanValue

    val displayCustomCapes: ForgeConfigSpec.BooleanValue

    val meteorTrailsPerTick: ForgeConfigSpec.IntValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Client config. Only present on client, duh.").push(fileName)

            displayUpdateMessage = comment("Will show a chat message with update info if a newer version of NTM is available").define("displayUpdateMessage", true)

            comment("Render settings").push("rendering")
            displayCustomCapes = comment("Disable this if there are any rendering problems with other mods. Please leave it on otherwise for *respect*.").define("displayCustomCapes", true)
            pop()

            comment("Particle settings").push("particles")
            meteorTrailsPerTick = comment("How many meteor trail particle emitters are spawned per tick. One emitter renders ten particles.").defineInRange("meteorTrailsPerTick", 6, 0, 20)
            pop()

            pop()
            configSpec = build()
        }
    }
}
