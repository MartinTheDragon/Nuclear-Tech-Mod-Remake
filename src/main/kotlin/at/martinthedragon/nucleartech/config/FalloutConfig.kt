package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class FalloutConfig : ConfigBase {
    override val fileName = "fallout"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.SERVER

    val burnFlammables: ForgeConfigSpec.BooleanValue
    val removePlants: ForgeConfigSpec.BooleanValue

    val emulateSpiral: ForgeConfigSpec.BooleanValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Fallout Config. Synced from server to client.").push(fileName)

            burnFlammables = comment("Set fire to any flammable blocks").define("burnFlammables", true)
            removePlants = comment("Remove any plants (more specifically those that implement IPlantable or IForgeShearable)").define("removePlants", true)
            emulateSpiral = comment("Makes the transformations start from the middle and spiral outwards. Almost entirely cosmetic, slight changes in performance depend on size of fallout").define("emulateSpiral", true)

            pop()
            configSpec = build()
        }
    }
}
