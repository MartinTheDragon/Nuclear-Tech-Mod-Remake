package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class ExplosionsConfig : ConfigBase {
    override val fileName = "explosions"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.SERVER

    val detonateUnloadedBombs: ForgeConfigSpec.BooleanValue

    val littleBoyStrength: ForgeConfigSpec.IntValue
    val fatManStrength: ForgeConfigSpec.IntValue
    val missileStrength: ForgeConfigSpec.IntValue

    val explosionSpeed: ForgeConfigSpec.IntValue
    val falloutRange: ForgeConfigSpec.DoubleValue
    val falloutSpeed: ForgeConfigSpec.IntValue
    val phasedExplosions: ForgeConfigSpec.BooleanValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Explosions Config. Synced from server to client.").push(fileName)

            detonateUnloadedBombs = comment("Whether bombs not loaded in the world can be detonated").define("detonateUnloadedBombs", false)

            comment("Bomb explosion settings").push("bombs")
            littleBoyStrength = comment("Strength value of the Little Boy").defineInRange("littleBoyStrength", 240, 0, 1_000)
            fatManStrength = comment("Strength value of the Fat Man").defineInRange("fatManStrength", 350, 0, 1_000)
            missileStrength = comment("Strength value of nuclear missiles").defineInRange("missileStrength", 200, 0, 1_000)
            pop()

            comment("Explosion behaviour settings").push("behaviour")
            explosionSpeed = comment("The maximum amount of blocks destroyed by an explosion per tick").defineInRange("explosionSpeed", 3072, 1, 1_000_000_000)
            falloutRange = comment("Modifies the range of the fallout area (value in percent)").defineInRange("falloutRange", 100.0, 0.0, 100_000.0)
            falloutSpeed = comment("The amount of block columns changed per tick by fallout after explosions").defineInRange("falloutSpeed", 256, 1, 1_000_000_000)
            phasedExplosions = comment("Splits the nuclear explosion algorithm into multiple phases, sending blocks and updating lighting afterwards. Goes easier on TPS, FPS and client connection. Disabling this will get the old \"animation\" back.").define("phasedExplosion", true)
            pop()

            pop()
            configSpec = build()
        }
    }
}
