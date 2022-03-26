package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class GeneralConfig : ConfigBase {
    override val fileName = "general"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.SERVER

    val mobsFullSetBonus: ForgeConfigSpec.BooleanValue

    val emptyIVBagCooldown: ForgeConfigSpec.IntValue
    val bloodBagCooldown: ForgeConfigSpec.IntValue
    val emptyExperienceBagCooldown: ForgeConfigSpec.IntValue
    val experienceBagCooldown: ForgeConfigSpec.IntValue
    val radAwayCooldown: ForgeConfigSpec.IntValue

    val enableBabyMode: ForgeConfigSpec.BooleanValue
    val enable528Mode: ForgeConfigSpec.BooleanValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("General Config. Synced from server to client.").push(fileName)

            mobsFullSetBonus = comment("Mobs get the full set bonus from armor").define("mobsFullSetBonus", true)

            comment("IV Bag Cooldown Settings. All values in ticks.").push("iv_bags")
            emptyIVBagCooldown = defineInRange("emptyIVBagCooldown", 20, 0, Int.MAX_VALUE)
            bloodBagCooldown = defineInRange("bloodBagCooldown", 40, 0, Int.MAX_VALUE)
            emptyExperienceBagCooldown = defineInRange("emptyExperienceBagCooldown", 0, 0, Int.MAX_VALUE)
            experienceBagCooldown = defineInRange("experienceBagCooldown", 0, 0, Int.MAX_VALUE)
            radAwayCooldown = defineInRange("radAwayCooldown", 100, 0, Int.MAX_VALUE)
            pop()

            enableBabyMode = comment("Hello peer!").define("enableBabyMode", false)

            comment("528 Mode settings").push("528")
            enable528Mode = comment("The central toggle for 528 mode").define("enable528Mode", false)
            pop()

            pop()
            configSpec = build()
        }
    }
}
