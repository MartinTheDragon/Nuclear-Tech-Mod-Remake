package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class RadiationConfig : ConfigBase {
    override val fileName = "radiation"
    override val configSpec: ForgeConfigSpec
    override val configType: ModConfig.Type = ModConfig.Type.SERVER

    val enableEntityIrradiation: ForgeConfigSpec.BooleanValue
    val netherRadiation: ForgeConfigSpec.DoubleValue
    val worldRadiationEffects: ForgeConfigSpec.BooleanValue
    val worldRadiationThreshold: ForgeConfigSpec.DoubleValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Radiation Config. Synced from server to client.").push(fileName)

            enableEntityIrradiation = comment("Whether entities can get irradiated").define("enableEntityIrradiation", true)
            netherRadiation = comment("RAD/s in the nether (or other warm dimensions, if applicable)").defineInRange("netherRadiation", .1, 0.0, 1_000.0)
            worldRadiationEffects = comment("Whether high radiation levels should perform changes to the world").define("worldRadiationEffects", true)
            worldRadiationThreshold = comment("The least amount of RADs required in a chunk for block modifications to happen").defineInRange("worldRadiationThreshold", 20.0, .5, 1_000_000.0)

            pop()
            configSpec = build()
        }
    }
}
