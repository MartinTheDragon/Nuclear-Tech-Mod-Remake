package at.martinthedragon.nucleartech.config

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.fml.config.ModConfig

class RBMKConfig : ConfigBase {
    override val fileName = "rbmk"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.SERVER

    val passiveCooling: ForgeConfigSpec.DoubleValue
    val columnHeatFlow: ForgeConfigSpec.DoubleValue
    val fuelDiffusionMod: ForgeConfigSpec.DoubleValue
    val heatProvision: ForgeConfigSpec.DoubleValue
    val columnHeight: ForgeConfigSpec.IntValue
    val permanentScrap: ForgeConfigSpec.BooleanValue
    val boilerHeatConsumption: ForgeConfigSpec.DoubleValue
    val controlSpeedMod: ForgeConfigSpec.DoubleValue
    val reactivityMod: ForgeConfigSpec.DoubleValue
    val outgasserMod: ForgeConfigSpec.DoubleValue
    val surgeMod: ForgeConfigSpec.DoubleValue
    val fluxRange: ForgeConfigSpec.IntValue
    val disableMeltdowns: ForgeConfigSpec.BooleanValue

    val reasimRange: ForgeConfigSpec.IntValue
    val reasimCount: ForgeConfigSpec.IntValue
    val reasimMod: ForgeConfigSpec.DoubleValue
    val reasimBoilers: ForgeConfigSpec.BooleanValue
    val reasimBoilerSpeed: ForgeConfigSpec.DoubleValue

    init {
        ForgeConfigSpec.Builder().apply {
            comment("RBMK config. Changes should reload automatically.").push(fileName)

            passiveCooling = comment("The amount of heat per tick removed from components passively").defineInRange("passiveCooling", 1.0, 0.0, Double.POSITIVE_INFINITY)
            columnHeatFlow = comment("The percentage step size how quickly neighboring component heat equalizes, 1 is instant, 0.5 is in 50% steps, etc.").defineInRange("passiveCooling", 0.2, 0.0, 1.0)
            fuelDiffusionMod = comment("Modifies the fuel rod diffusion, i.e. how quickly the core and hull temperatures equalize").defineInRange("fuelDiffusionMod", 1.0, 0.0, Double.POSITIVE_INFINITY)
            heatProvision = comment("The percentage step size how quickly the fuel hull heat and the component heat equalize").defineInRange("heatProvision", 0.2, 0.0, 1.0)
            columnHeight = comment("The columns' placement height, doesn't affect previously placed columns").defineInRange("columnHeight", 4, 1, 15)
            permanentScrap = comment("Toggles whether scrap entities despawn on their own or remain alive until picked up").define("permanentScrap", true)
            boilerHeatConsumption = comment("How many heat units are consumed per mB water in steam channels").defineInRange("boilerHeatConsumption", 0.1, 0.0, Double.POSITIVE_INFINITY)
            controlSpeedMod = comment("Modifies how quickly the control rods move").defineInRange("controlSpeedMod", 1.0, 0.0, Double.POSITIVE_INFINITY)
            reactivityMod = comment("Modifies how much flux the rods give out").defineInRange("reactivityMod", 1.0, 0.0, Double.POSITIVE_INFINITY)
            outgasserMod = comment("Modifies how quickly outgasser recipes progress").defineInRange("outgasserMod", 1.0, 0.0, Double.POSITIVE_INFINITY)
            surgeMod = comment("Modifies the power surge when inserting control rods").defineInRange("surgeMod", 1.0, 0.0, Double.POSITIVE_INFINITY)
            fluxRange = comment("How far the flux of a normal fuel rod reaches").defineInRange("fluxRange", 5, 1, 100)
            disableMeltdowns = comment("Toggles whether fuel columns should initiate a meltdown when overheating").define("disableMeltdowns", false)

            comment("ReaSim specific settings").push("reasim")
            reasimRange = comment("How far the flux of a ReaSim fuel rod reaches").defineInRange("reasimRange", 10, 1, 100)
            reasimCount = comment("How many neutrons are created from ReaSim fuel rods").defineInRange("reasimCount", 6, 1, 24)
            reasimMod = comment("Modifies the outgoing flux of individual streams from the ReaSim fuel rod to compensate for the potentially increased stream count").defineInRange("reasimMod", 1.0, 0.0, Double.POSITIVE_INFINITY)
            reasimBoilers = comment("Whether all components should act like boilers with dedicated in/outlet columns (true when 528 specific option is set)").define("reasimBoilers", false)
            reasimBoilerSpeed = comment("The percentage of possible steam ending up being produced per tick").defineInRange("reasimBoilerSpeed", 0.05, 0.0, 1.0)
            pop()

            pop()
            configSpec = build()
        }
    }
}
