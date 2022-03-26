package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.blocks.FatMan
import at.martinthedragon.nucleartech.blocks.LittleBoy
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.items.*
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.crafting.RecipeType
import net.minecraftforge.common.ForgeSpawnEggItem
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraftforge.registries.RegistryObject

@Suppress("unused")
object ModItems {
    val rawUranium = ITEMS.registerK("raw_uranium") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .25F) }
    val rawThorium = ITEMS.registerK("raw_thorium") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawPlutonium = ITEMS.registerK("raw_plutonium") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1F) }
    val rawTitanium = ITEMS.registerK("raw_titanium") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawTungsten = ITEMS.registerK("raw_tungsten") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawAluminium = ITEMS.registerK("raw_aluminium") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawBeryllium = ITEMS.registerK("raw_beryllium") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawLead = ITEMS.registerK("raw_lead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawAsbestos = ITEMS.registerK("raw_asbestos") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawSchrabidium = ITEMS.registerK("raw_schrabidium") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE), 4F) }
    val rawAustralium = ITEMS.registerK("raw_australium") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawRareEarth = ITEMS.registerK("raw_rare_earth") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawCobalt = ITEMS.registerK("raw_cobalt") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawLithium = ITEMS.registerK("raw_lithium") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawStarmetal = ITEMS.registerK("raw_starmetal") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rawTrixite = ITEMS.registerK("raw_trixite") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 2F) }
    val uraniumIngot = ITEMS.registerK("uranium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .5f) }
    val u233Ingot = ITEMS.registerK("u233_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 5f) }
    val u235Ingot = ITEMS.registerK("u235_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 5f) }
    val u238Ingot = ITEMS.registerK("u238_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .5f) }
    val th232Ingot = ITEMS.registerK("th232_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val plutoniumIngot = ITEMS.registerK("plutonium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val pu238Ingot = ITEMS.registerK("pu238_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val pu239Ingot = ITEMS.registerK("pu239_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 5f) }
    val pu240Ingot = ITEMS.registerK("pu240_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 5f) }
    val neptuniumIngot = ITEMS.registerK("neptunium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val poloniumIngot = ITEMS.registerK("polonium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 10f) }
    val titaniumIngot = ITEMS.registerK("titanium_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val redCopperIngot = ITEMS.registerK("red_copper_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedAlloyIngot = ITEMS.registerK("advanced_alloy_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tungstenIngot = ITEMS.registerK("tungsten_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val aluminiumIngot = ITEMS.registerK("aluminium_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelIngot = ITEMS.registerK("steel_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val leadIngot = ITEMS.registerK("lead_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val berylliumIngot = ITEMS.registerK("beryllium_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val cobaltIngot = ITEMS.registerK("cobalt_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highSpeedSteelIngot = ITEMS.registerK("high_speed_steel_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val polymerIngot = ITEMS.registerK("polymer_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schraraniumIngot = ITEMS.registerK("schraranium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val schrabidiumIngot = ITEMS.registerK("schrabidium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE), 7.5f) }
    val magnetizedTungstenIngot = ITEMS.registerK("magnetized_tungsten_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val combineSteelIngot = ITEMS.registerK("combine_steel_ingot") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val soliniumIngot = ITEMS.registerK("solinium_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 7.5f) }
    val uraniumFuelIngot = ITEMS.registerK("uranium_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 3.5f) }
    val thoriumFuelIngot = ITEMS.registerK("thorium_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .5f) }
    val plutoniumFuelIngot = ITEMS.registerK("plutonium_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 3.5f) }
    val moxFuelIngot = ITEMS.registerK("mox_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 3.5f) }
    val schrabidiumFuelIngot = ITEMS.registerK("schrabidium_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 5f) }
    val highEnrichedSchrabidiumFuelIngot = ITEMS.registerK("high_enriched_schrabidium_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 7.5f) }
    val lowEnrichedSchrabidiumFuelIngot = ITEMS.registerK("low_enriched_schrabidium_fuel_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val australiumIngot = ITEMS.registerK("australium_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val weidaniumIngot = ITEMS.registerK("weidanium_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val reiiumIngot = ITEMS.registerK("reiium_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val unobtainiumIngot = ITEMS.registerK("unobtainium_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val daffergonIngot = ITEMS.registerK("daffergon_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val verticiumIngot = ITEMS.registerK("verticium_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val lanthanumIngot = ITEMS.registerK("lanthanum_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val actiniumIngot = ITEMS.registerK("actinium_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deshIngot = ITEMS.registerK("desh_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val starmetalIngot = ITEMS.registerK("starmetal_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val saturniteIngot = ITEMS.registerK("saturnite_ingot") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE)) }
    val euphemiumIngot = ITEMS.registerK("euphemium_ingot") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val dineutroniumIngot = ITEMS.registerK("dineutronium_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val electroniumIngot = ITEMS.registerK("electronium_ingot") { Item(Properties().tab(CreativeTabs.Parts)) }
    val whitePhosphorusIngot = ITEMS.registerK("white_phosphorus_ingot") { EffectItem(listOf(EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts)) }
    val semtexBar = ITEMS.registerK("semtex_bar") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).food(FoodProperties.Builder().nutrition(4).saturationMod(0.5f).build())) }
    val lithiumCube = ITEMS.registerK("lithium_cube") { Item(Properties().tab(CreativeTabs.Parts)) }
    val solidFuelCube = ITEMS.registerK("solid_fuel_cube") { Item(Properties().tab(CreativeTabs.Parts)) }
    val solidRocketFuelCube = ITEMS.registerK("solid_rocket_fuel_cube") { Item(Properties().tab(CreativeTabs.Parts)) }
    val fiberglassSheet = ITEMS.registerK("fiberglass_sheet") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val asbestosSheet = ITEMS.registerK("asbestos_sheet") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val mercuryDroplet = ITEMS.registerK("mercury_droplet") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mercuryBottle = ITEMS.registerK("mercury_bottle") { Item(Properties().tab(CreativeTabs.Parts)) }
    val coke: RegistryObject<Item> = ITEMS.registerK("coke") { object : Item(Properties().tab(CreativeTabs.Parts)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 3200
    }}
    val lignite: RegistryObject<Item> = ITEMS.registerK("lignite") { object : Item(Properties().tab(CreativeTabs.Parts)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 1200
    }}
    val ligniteBriquette: RegistryObject<Item> = ITEMS.registerK("lignite_briquette") { object : Item(Properties().tab(CreativeTabs.Parts)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 1600
    }}
    val sulfur = ITEMS.registerK("sulfur") { Item(Properties().tab(CreativeTabs.Parts)) }
    val niter = ITEMS.registerK("niter") { Item(Properties().tab(CreativeTabs.Parts)) }
    val fluorite = ITEMS.registerK("fluorite") { Item(Properties().tab(CreativeTabs.Parts)) }
    val coalPowder = ITEMS.registerK("coal_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ironPowder = ITEMS.registerK("iron_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldPowder = ITEMS.registerK("gold_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val lapisLazuliPowder = ITEMS.registerK("lapis_lazuli_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val quartzPowder = ITEMS.registerK("quartz_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val diamondPowder = ITEMS.registerK("diamond_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val emeraldPowder = ITEMS.registerK("emerald_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val uraniumPowder = ITEMS.registerK("uranium_powder") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), .5f) }
    val thoriumPowder = ITEMS.registerK("thorium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val plutoniumPowder = ITEMS.registerK("plutonium_powder") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val neptuniumPowder = ITEMS.registerK("neptunium_powder") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC), 2.5f) }
    val poloniumPowder = ITEMS.registerK("polonium_powder") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 10f) }
    val titaniumPowder = ITEMS.registerK("titanium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val copperPowder = ITEMS.registerK("copper_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val redCopperPowder = ITEMS.registerK("red_copper_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedAlloyPowder = ITEMS.registerK("advanced_alloy_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tungstenPowder = ITEMS.registerK("tungsten_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val aluminiumPowder = ITEMS.registerK("aluminium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelPowder = ITEMS.registerK("steel_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val leadPowder = ITEMS.registerK("lead_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val yellowcake = ITEMS.registerK("yellowcake") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .5f) }
    val berylliumPowder = ITEMS.registerK("beryllium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highSpeedSteelPowder = ITEMS.registerK("high_speed_steel_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val polymerPowder = ITEMS.registerK("polymer_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schrabidiumPowder = ITEMS.registerK("schrabidium_powder") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE), 7.5f) }
    val magnetizedTungstenPowder = ITEMS.registerK("magnetized_tungsten_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val chlorophytePowder = ITEMS.registerK("chlorophyte_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val combineSteelPowder = ITEMS.registerK("combine_steel_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val lithiumPowder = ITEMS.registerK("lithium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val lignitePowder = ITEMS.registerK("lignite_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val neodymiumPowder = ITEMS.registerK("neodymium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val australiumPowder = ITEMS.registerK("australium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val weidaniumPowder = ITEMS.registerK("weidanium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val reiiumPowder = ITEMS.registerK("reiium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val unobtainiumPowder = ITEMS.registerK("unobtainium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val daffergonPowder = ITEMS.registerK("daffergon_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val verticiumPowder = ITEMS.registerK("verticium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val cobaltPowder = ITEMS.registerK("cobalt_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val niobiumPowder = ITEMS.registerK("niobium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val ceriumPowder = ITEMS.registerK("cerium_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val lanthanumPowder = ITEMS.registerK("lanthanum_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val actiniumPowder = ITEMS.registerK("actinium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val asbestosPowder = ITEMS.registerK("asbestos_powder") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val enchantmentPowder = ITEMS.registerK("enchantment_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val cloudResidue = ITEMS.registerK("cloud_residue") { Item(Properties().tab(CreativeTabs.Parts)) }
    val thermonuclearAshes = ITEMS.registerK("thermonuclear_ashes") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 500f) }
    val semtexMix = ITEMS.registerK("semtex_mix") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deshMix = ITEMS.registerK("desh_mix") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deshReadyMix = ITEMS.registerK("desh_ready_mix") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deshPowder = ITEMS.registerK("desh_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val nitaniumMix = ITEMS.registerK("nitanium_mix") { Item(Properties().tab(CreativeTabs.Parts)) }
    val sparkMix = ITEMS.registerK("spark_mix") { Item(Properties().tab(CreativeTabs.Parts)) }
    val meteoritePowder = ITEMS.registerK("meteorite_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val euphemiumPowder = ITEMS.registerK("euphemium_powder") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val dineutroniumPowder = ITEMS.registerK("dineutronium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val desaturatedRedstone = ITEMS.registerK("desaturated_redstone") { Item(Properties().tab(CreativeTabs.Parts)) }
    val dust: RegistryObject<Item> = ITEMS.registerK("dust") { object : AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 400
    }}
    val tinyLithiumPowder = ITEMS.registerK("tiny_lithium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyNeodymiumPowder = ITEMS.registerK("tiny_neodymium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyCobaltPowder = ITEMS.registerK("tiny_cobalt_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyNiobiumPowder = ITEMS.registerK("tiny_niobium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyCeriumPowder = ITEMS.registerK("tiny_cerium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyLanthanumPowder = ITEMS.registerK("tiny_lanthanum_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyActiniumPowder = ITEMS.registerK("tiny_actinium_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tinyMeteoritePowder = ITEMS.registerK("tiny_meteorite_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val redPhosphorus = ITEMS.registerK("red_phosphorus") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val cryoPowder = ITEMS.registerK("cryo_powder") { Item(Properties().tab(CreativeTabs.Parts)) }
    val poisonPowder = ITEMS.registerK("poison_powder") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val thermite = ITEMS.registerK("thermite") { Item(Properties().tab(CreativeTabs.Parts)) }
    val energyPowder = ITEMS.registerK("energy_powder") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val cordite = ITEMS.registerK("cordite") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ballistite = ITEMS.registerK("ballistite") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ironCrystals = ITEMS.registerK("iron_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldCrystals = ITEMS.registerK("gold_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val redstoneCrystals = ITEMS.registerK("redstone_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val diamondCrystals = ITEMS.registerK("diamond_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val uraniumCrystals = ITEMS.registerK("uranium_crystals") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .75f) }
    val thoriumCrystals = ITEMS.registerK("thorium_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val plutoniumCrystals = ITEMS.registerK("plutonium_crystals") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 3f) }
    val titaniumCrystals = ITEMS.registerK("titanium_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val sulfurCrystals = ITEMS.registerK("sulfur_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val niterCrystals = ITEMS.registerK("niter_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val copperCrystals = ITEMS.registerK("copper_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tungstenCrystals = ITEMS.registerK("tungsten_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val aluminiumCrystals = ITEMS.registerK("aluminium_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val fluoriteCrystals = ITEMS.registerK("fluorite_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val berylliumCrystals = ITEMS.registerK("beryllium_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val leadCrystals = ITEMS.registerK("lead_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schraraniumCrystals = ITEMS.registerK("schraranium_crystals") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 5f) }
    val schrabidiumCrystals = ITEMS.registerK("schrabidium_crystals") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 10f) }
    val rareEarthCrystals = ITEMS.registerK("rare_earth_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val redPhosphorusCrystals = ITEMS.registerK("red_phosphorus_crystals") { EffectItem(listOf(EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts)) }
    val lithiumCrystals = ITEMS.registerK("lithium_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val starmetalCrystals = ITEMS.registerK("starmetal_crystals") { Item(Properties().tab(CreativeTabs.Parts)) }
    val trixiteCrystals = ITEMS.registerK("trixite_crystals") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 10f) }
    val neodymiumFragment = ITEMS.registerK("neodymium_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val cobaltFragment = ITEMS.registerK("cobalt_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val niobiumFragment = ITEMS.registerK("niobium_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ceriumFragment = ITEMS.registerK("cerium_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val lanthanumFragment = ITEMS.registerK("lanthanum_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val actiniumFragment = ITEMS.registerK("actinium_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val meteoriteFragment = ITEMS.registerK("meteorite_fragment") { Item(Properties().tab(CreativeTabs.Parts)) }
    val biomass = ITEMS.registerK("biomass") { Item(Properties().tab(CreativeTabs.Parts)) }
    val compressedBiomass = ITEMS.registerK("compressed_biomass") { Item(Properties().tab(CreativeTabs.Parts)) }
    val uraniumNugget = ITEMS.registerK("uranium_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .25f) }
    val u233Nugget = ITEMS.registerK("u233_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val u235Nugget = ITEMS.registerK("u235_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val u238Nugget = ITEMS.registerK("u238_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .25f) }
    val th232Nugget = ITEMS.registerK("th232_nugget") { Item(Properties().tab(CreativeTabs.Parts)) }
    val plutoniumNugget = ITEMS.registerK("plutonium_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val pu238Nugget = ITEMS.registerK("pu238_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .25f) }
    val pu239Nugget = ITEMS.registerK("pu239_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val pu240Nugget = ITEMS.registerK("pu240_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val neptuniumNugget = ITEMS.registerK("neptunium_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val poloniumNugget = ITEMS.registerK("polonium_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 3.5f) }
    val leadNugget = ITEMS.registerK("lead_nugget") { Item(Properties().tab(CreativeTabs.Parts)) }
    val berylliumNugget = ITEMS.registerK("beryllium_nugget") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schrabidiumNugget = ITEMS.registerK("schrabidium_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE), 2.5f) }
    val soliniumNugget = ITEMS.registerK("solinium_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val uraniumFuelNugget = ITEMS.registerK("uranium_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val thoriumFuelNugget = ITEMS.registerK("thorium_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .025f) }
    val plutoniumFuelNugget = ITEMS.registerK("plutonium_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1f) }
    val moxFuelNugget = ITEMS.registerK("mox_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1f) }
    val schrabidiumFuelNugget = ITEMS.registerK("schrabidium_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 1.5f) }
    val highEnrichedSchrabidiumFuelNugget = ITEMS.registerK("high_enriched_schrabidium_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 2.5f) }
    val lowEnrichedSchrabidiumFuelNugget = ITEMS.registerK("low_enriched_schrabidium_fuel_nugget") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), .5f) }
    val australiumNugget = ITEMS.registerK("australium_nugget") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val weidaniumNugget = ITEMS.registerK("weidanium_nugget") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val reiiumNugget = ITEMS.registerK("reiium_nugget") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val unobtainiumNugget = ITEMS.registerK("unobtainium_nugget") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val daffergonNugget = ITEMS.registerK("daffergon_nugget") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val verticiumNugget = ITEMS.registerK("verticium_nugget") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)) }
    val deshNugget = ITEMS.registerK("desh_nugget") { Item(Properties().tab(CreativeTabs.Parts)) }
    val euphemiumNugget = ITEMS.registerK("euphemium_nugget") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)) }
    val dineutroniumNugget = ITEMS.registerK("dineutronium_nugget") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ironPlate = ITEMS.registerK("iron_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldPlate = ITEMS.registerK("gold_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val titaniumPlate = ITEMS.registerK("titanium_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val aluminiumPlate = ITEMS.registerK("aluminium_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelPlate = ITEMS.registerK("steel_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val leadPlate = ITEMS.registerK("lead_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val copperPlate = ITEMS.registerK("copper_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedAlloyPlate = ITEMS.registerK("advanced_alloy_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val neutronReflector = ITEMS.registerK("neutron_reflector") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schrabidiumPlate = ITEMS.registerK("schrabidium_plate") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 7.5f) }
    val combineSteelPlate = ITEMS.registerK("combine_steel_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mixedPlate = ITEMS.registerK("mixed_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val saturnitePlate = ITEMS.registerK("saturnite_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val paAAlloyPlate = ITEMS.registerK("paa_alloy_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val insulator = ITEMS.registerK("insulator") { Item(Properties().tab(CreativeTabs.Parts)) }
    val kevlarCeramicCompound = ITEMS.registerK("kevlar_ceramic_compound") { Item(Properties().tab(CreativeTabs.Parts)) }
    val dalekaniumPlate = ITEMS.registerK("angry_metal") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deshCompoundPlate = ITEMS.registerK("desh_compound_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val euphemiumCompoundPlate = ITEMS.registerK("euphemium_compound_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val dineutroniumCompoundPlate = ITEMS.registerK("dineutronium_compound_plate") { Item(Properties().tab(CreativeTabs.Parts)) }
    val copperPanel = ITEMS.registerK("copper_panel") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highSpeedSteelBolt = ITEMS.registerK("high_speed_steel_bolt") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tungstenBolt = ITEMS.registerK("tungsten_bolt") { Item(Properties().tab(CreativeTabs.Parts)) }
    val reinforcedTurbineShaft = ITEMS.registerK("reinforced_turbine_shaft") { Item(Properties().tab(CreativeTabs.Parts)) }
    val hazmatCloth = ITEMS.registerK("hazmat_cloth") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedHazmatCloth = ITEMS.registerK("advanced_hazmat_cloth") { Item(Properties().tab(CreativeTabs.Parts)) }
    val leadReinforcedHazmatCloth = ITEMS.registerK("lead_reinforced_hazmat_cloth") { Item(Properties().tab(CreativeTabs.Parts)) }
    val fireProximityCloth = ITEMS.registerK("fire_proximity_cloth") { Item(Properties().tab(CreativeTabs.Parts)) }
    val activatedCarbonFilter = ITEMS.registerK("activated_carbon_filter") { Item(Properties().tab(CreativeTabs.Parts)) }
    val aluminiumWire = ITEMS.registerK("aluminium_wire") { Item(Properties().tab(CreativeTabs.Parts)) }
    val copperWire = ITEMS.registerK("copper_wire") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tungstenWire = ITEMS.registerK("tungsten_wire") { Item(Properties().tab(CreativeTabs.Parts)) }
    val redCopperWire = ITEMS.registerK("red_copper_wire") { Item(Properties().tab(CreativeTabs.Parts)) }
    val superConductor = ITEMS.registerK("super_conductor") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldWire = ITEMS.registerK("gold_wire") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schrabidiumWire = ITEMS.registerK("schrabidium_wire") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), .5f) }
    val highTemperatureSuperConductor = ITEMS.registerK("high_temperature_super_conductor") { Item(Properties().tab(CreativeTabs.Parts)) }
    val copperCoil = ITEMS.registerK("copper_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ringCoil = ITEMS.registerK("ring_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val superConductingCoil = ITEMS.registerK("super_conducting_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val superConductingRingCoil = ITEMS.registerK("super_conducting_ring_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldCoil = ITEMS.registerK("gold_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldRingCoil = ITEMS.registerK("gold_ring_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val heatingCoil = ITEMS.registerK("heating_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highTemperatureSuperConductingCoil = ITEMS.registerK("high_temperature_super_conducting_coil") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelTank = ITEMS.registerK("steel_tank") { Item(Properties().tab(CreativeTabs.Parts)) }
    val motor = ITEMS.registerK("motor") { Item(Properties().tab(CreativeTabs.Parts)) }
    val centrifugeElement = ITEMS.registerK("centrifuge_element") { Item(Properties().tab(CreativeTabs.Parts)) }
    val centrifugeTower = ITEMS.registerK("centrifuge_tower") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deeMagnets = ITEMS.registerK("dee_magnets") { Item(Properties().tab(CreativeTabs.Parts)) }
    val flatMagnet = ITEMS.registerK("flat_magnet") { Item(Properties().tab(CreativeTabs.Parts)) }
    val cyclotronTower = ITEMS.registerK("cyclotron_tower") { Item(Properties().tab(CreativeTabs.Parts)) }
    val breedingReactorCore = ITEMS.registerK("breeding_reactor_core") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rtgUnit = ITEMS.registerK("rtg_unit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val thermalDistributionUnit = ITEMS.registerK("thermal_distribution_unit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val endothermicDistributionUnit = ITEMS.registerK("endothermic_distribution_unit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val exothermicDistributionUnit = ITEMS.registerK("exothermic_distribution_unit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val gravityManipulator = ITEMS.registerK("gravity_manipulator") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelPipes = ITEMS.registerK("steel_pipes") { Item(Properties().tab(CreativeTabs.Parts)) }
    val titaniumDrill = ITEMS.registerK("titanium_drill") { Item(Properties().tab(CreativeTabs.Parts)) }
    val photovoltaicPanel = ITEMS.registerK("photovoltaic_panel") { Item(Properties().tab(CreativeTabs.Parts)) }
    val chlorinePinwheel = ITEMS.registerK("chlorine_pinwheel") { Item(Properties().tab(CreativeTabs.Parts)) }
    val telepad = ITEMS.registerK("telepad") { Item(Properties().tab(CreativeTabs.Parts)) }
    val entanglementKit = ITEMS.registerK("entanglement_kit") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val stabilizerComponent = ITEMS.registerK("stabilizer_component") { Item(Properties().tab(CreativeTabs.Parts)) }
    val emitterComponent = ITEMS.registerK("emitter_component") { Item(Properties().tab(CreativeTabs.Parts)) }
    val aluminiumCap = ITEMS.registerK("aluminium_cap") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallSteelShell = ITEMS.registerK("small_steel_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallAluminiumShell = ITEMS.registerK("small_aluminium_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val bigSteelShell = ITEMS.registerK("big_steel_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val bigAluminiumShell = ITEMS.registerK("big_aluminium_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val bigTitaniumShell = ITEMS.registerK("big_titanium_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val flatSteelCasing = ITEMS.registerK("flat_steel_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallSteelGridFins = ITEMS.registerK("small_steel_grid_fins") { Item(Properties().tab(CreativeTabs.Parts)) }
    val bigSteelGridFins = ITEMS.registerK("big_steel_grid_fins") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeSteelFins = ITEMS.registerK("large_steel_fins") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallTitaniumFins = ITEMS.registerK("small_titanium_fins") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelSphere = ITEMS.registerK("steel_sphere") { Item(Properties().tab(CreativeTabs.Parts)) }
    val steelPedestal = ITEMS.registerK("steel_pedestal") { Item(Properties().tab(CreativeTabs.Parts)) }
    val dysfunctionalNuclearReactor = ITEMS.registerK("dysfunctional_nuclear_reactor") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeSteelRotor = ITEMS.registerK("large_steel_rotor") { Item(Properties().tab(CreativeTabs.Parts)) }
    val generatorBody = ITEMS.registerK("generator_body") { Item(Properties().tab(CreativeTabs.Parts)) }
    val titaniumBlade = ITEMS.registerK("titanium_blade") { Item(Properties().tab(CreativeTabs.Parts)) }
    val tungstenReinforcedBlade = ITEMS.registerK("tungsten_reinforced_blade") { Item(Properties().tab(CreativeTabs.Parts)) }
    val titaniumSteamTurbine = ITEMS.registerK("titanium_steam_turbine") { Item(Properties().tab(CreativeTabs.Parts)) }
    val reinforcedTurbofanBlades = ITEMS.registerK("reinforced_turbofan_blades") { Item(Properties().tab(CreativeTabs.Parts)) }
    val generatorFront = ITEMS.registerK("generator_front") { Item(Properties().tab(CreativeTabs.Parts)) }
    val toothpicks = ITEMS.registerK("toothpicks") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ductTape = ITEMS.registerK("duct_tape") { Item(Properties().tab(CreativeTabs.Parts)) }
    val clayCatalyst = ITEMS.registerK("clay_catalyst") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallMissileAssembly = ITEMS.registerK("small_missile_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallWarhead = ITEMS.registerK("small_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mediumWarhead = ITEMS.registerK("medium_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeWarhead = ITEMS.registerK("large_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallIncendiaryWarhead = ITEMS.registerK("small_incendiary_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mediumIncendiaryWarhead = ITEMS.registerK("medium_incendiary_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeIncendiaryWarhead = ITEMS.registerK("large_incendiary_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallClusterWarhead = ITEMS.registerK("small_cluster_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mediumClusterWarhead = ITEMS.registerK("medium_cluster_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeClusterWarhead = ITEMS.registerK("large_cluster_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallBunkerBusterWarhead = ITEMS.registerK("small_bunker_busting_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mediumBunkerBusterWarhead = ITEMS.registerK("medium_bunker_busting_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeBunkerBusterWarhead = ITEMS.registerK("large_bunker_busting_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val nuclearWarhead = ITEMS.registerK("nuclear_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val thermonuclearWarhead = ITEMS.registerK("thermonuclear_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val endothermicWarhead = ITEMS.registerK("endothermic_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val exothermicWarhead = ITEMS.registerK("exothermic_warhead") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallFuelTank = ITEMS.registerK("small_fuel_tank") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mediumFuelTank = ITEMS.registerK("medium_fuel_tank") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeFuelTank = ITEMS.registerK("large_fuel_tank") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallThruster = ITEMS.registerK("small_thruster") { Item(Properties().tab(CreativeTabs.Parts)) }
    val mediumThruster = ITEMS.registerK("medium_thruster") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeThruster = ITEMS.registerK("large_thruster") { Item(Properties().tab(CreativeTabs.Parts)) }
    val lvnNuclearRocketEngine = ITEMS.registerK("lv_n_nuclear_rocket_engine") { Item(Properties().tab(CreativeTabs.Parts)) }
    val satelliteBase = ITEMS.registerK("satellite_base") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highGainOpticalCamera = ITEMS.registerK("high_gain_optical_camera") { Item(Properties().tab(CreativeTabs.Parts)) }
    val m700SurveyScanner = ITEMS.registerK("m700_survey_scanner") { Item(Properties().tab(CreativeTabs.Parts)) }
    val radarDish = ITEMS.registerK("radar_dish") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deathRay = ITEMS.registerK("death_ray") { Item(Properties().tab(CreativeTabs.Parts)) }
    val xeniumResonator = ITEMS.registerK("xenium_resonator") { Item(Properties().tab(CreativeTabs.Parts)) }
    val size10Connector = ITEMS.registerK("size_10_connector") { Item(Properties().tab(CreativeTabs.Parts)) }
    val size15Connector = ITEMS.registerK("size_15_connector") { Item(Properties().tab(CreativeTabs.Parts)) }
    val size20Connector = ITEMS.registerK("size_20_connector") { Item(Properties().tab(CreativeTabs.Parts)) }
    val hunterChopperCockpit = ITEMS.registerK("hunter_chopper_cockpit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val emplacementGun = ITEMS.registerK("emplacement_gun") { Item(Properties().tab(CreativeTabs.Parts)) }
    val hunterChopperBody = ITEMS.registerK("hunter_chopper_body") { Item(Properties().tab(CreativeTabs.Parts)) }
    val hunterChopperTail = ITEMS.registerK("hunter_chopper_tail") { Item(Properties().tab(CreativeTabs.Parts)) }
    val hunterChopperWing = ITEMS.registerK("hunter_chopper_wing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val hunterChopperRotorBlades = ITEMS.registerK("hunter_chopper_rotor_blades") { Item(Properties().tab(CreativeTabs.Parts)) }
    val combineScrapMetal = ITEMS.registerK("combine_scrap_metal") { Item(Properties().tab(CreativeTabs.Parts)) }
    val heavyHammerHead = ITEMS.registerK("heavy_hammer_head") { Item(Properties().tab(CreativeTabs.Parts)) }
    val heavyAxeHead = ITEMS.registerK("heavy_axe_head") { Item(Properties().tab(CreativeTabs.Parts)) }
    val reinforcedPolymerHandle = ITEMS.registerK("reinforced_polymer_handle") { Item(Properties().tab(CreativeTabs.Parts)) }
    val basicCircuitAssembly = ITEMS.registerK("basic_circuit_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val basicCircuit = ITEMS.registerK("basic_circuit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val enhancedCircuit = ITEMS.registerK("enhanced_circuit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedCircuit = ITEMS.registerK("advanced_circuit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val overclockedCircuit = ITEMS.registerK("overclocked_circuit") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highPerformanceCircuit = ITEMS.registerK("high_performance_circuit") { Item(Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE)) }
    val militaryGradeCircuitBoardTier1 = ITEMS.registerK("military_grade_circuit_board_tier_1") { Item(Properties().tab(CreativeTabs.Parts)) }
    val militaryGradeCircuitBoardTier2 = ITEMS.registerK("military_grade_circuit_board_tier_2") { Item(Properties().tab(CreativeTabs.Parts)) }
    val militaryGradeCircuitBoardTier3 = ITEMS.registerK("military_grade_circuit_board_tier_3") { Item(Properties().tab(CreativeTabs.Parts)) }
    val militaryGradeCircuitBoardTier4 = ITEMS.registerK("military_grade_circuit_board_tier_4") { Item(Properties().tab(CreativeTabs.Parts)) }
    val militaryGradeCircuitBoardTier5 = ITEMS.registerK("military_grade_circuit_board_tier_5") { Item(Properties().tab(CreativeTabs.Parts)) }
    val militaryGradeCircuitBoardTier6 = ITEMS.registerK("military_grade_circuit_board_tier_6") { Item(Properties().tab(CreativeTabs.Parts)) }
    val revolverMechanism = ITEMS.registerK("revolver_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedRevolverMechanism = ITEMS.registerK("advanced_revolver_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val rifleMechanism = ITEMS.registerK("rifle_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedRifleMechanism = ITEMS.registerK("advanced_rifle_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val launcherMechanism = ITEMS.registerK("launcher_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val advancedLauncherMechanism = ITEMS.registerK("advanced_launcher_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val highTechWeaponMechanism = ITEMS.registerK("high_tech_weapon_mechanism") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point357MagnumPrimer = ITEMS.registerK("point_357_magnum_primer") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point44MagnumPrimer = ITEMS.registerK("point_44_magnum_primer") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallCaliberPrimer = ITEMS.registerK("small_caliber_primer") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeCaliberPrimer = ITEMS.registerK("large_caliber_primer") { Item(Properties().tab(CreativeTabs.Parts)) }
    val buckshotPrimer = ITEMS.registerK("buckshot_primer") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point357MagnumCasing = ITEMS.registerK("point_357_magnum_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point44MagnumCasing = ITEMS.registerK("point_44_magnum_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val smallCaliberCasing = ITEMS.registerK("small_caliber_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val largeCaliberCasing = ITEMS.registerK("large_caliber_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val buckshotCasing = ITEMS.registerK("buckshot_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val ironBulletAssembly = ITEMS.registerK("iron_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val leadBulletAssembly = ITEMS.registerK("lead_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val glassBulletAssembly = ITEMS.registerK("glass_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val goldBulletAssembly = ITEMS.registerK("gold_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val schrabidiumBulletAssembly = ITEMS.registerK("schrabidium_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val nightmareBulletAssembly = ITEMS.registerK("nightmare_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val deshBulletAssembly = ITEMS.registerK("desh_bullet_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point44MagnumAssembly = ITEMS.registerK("point_44_magnum_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val nineMmAssembly = ITEMS.registerK("9_mm_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val fivePoint56mmAssembly = ITEMS.registerK("5_point_56_mm_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point22LRAssembly = ITEMS.registerK("point_22_lr_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point5mmAssembly = ITEMS.registerK("point_5_mm_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point50AEAssembly = ITEMS.registerK("point_50_ae_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val point50BMGAssembly = ITEMS.registerK("point_50_bmg_assembly") { Item(Properties().tab(CreativeTabs.Parts)) }
    val silverBulletCasing = ITEMS.registerK("silver_bullet_casing") { Item(Properties().tab(CreativeTabs.Parts)) }
    val twelvePoint8cmStarmetalHighEnergyShell = ITEMS.registerK("12_point_8_cm_starmetal_high_energy_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val twelvePoint8cmNuclearShell = ITEMS.registerK("12_point_8_cm_nuclear_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val twelvePoint8cmDUShell = ITEMS.registerK("12_point_8_cm_du_shell") { Item(Properties().tab(CreativeTabs.Parts)) }
    val cableDrum = ITEMS.registerK("cable_drum") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val paintingOfACartoonPony = ITEMS.registerK("painting_of_a_cartoon_pony") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val conspiracyTheory = ITEMS.registerK("conspiracy_theory") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val politicalTopic = ITEMS.registerK("political_topic") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val ownOpinion = ITEMS.registerK("own_opinion") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val explosivePellets = ITEMS.registerK("explosive_pellets") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val leadPellets = ITEMS.registerK("lead_pellets") { Item(Properties().tab(CreativeTabs.Parts)) }
    val flechettes = ITEMS.registerK("flechettes") { Item(Properties().tab(CreativeTabs.Parts)) }
    val poisonGasCartridge = ITEMS.registerK("poison_gas_cartridge") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val magnetron = ITEMS.registerK("magnetron") { Item(Properties().tab(CreativeTabs.Parts)) }
    val denseCoalCluster = ITEMS.registerK("dense_coal_cluster") { Item(Properties().tab(CreativeTabs.Parts)) }
    val burntBark = ITEMS.registerK("burnt_bark") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val machineUpgradeTemplate = ITEMS.registerK("machine_upgrade_template") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val blankRune = ITEMS.registerK("blank_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val isaRune = ITEMS.registerK("isa_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val dagazRune = ITEMS.registerK("dagaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val hagalazRune = ITEMS.registerK("hagalaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val jeraRune = ITEMS.registerK("jera_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val thurisazRune = ITEMS.registerK("thurisaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val burnedOutQuadSchrabidiumFuelRod = ITEMS.registerK("burned_out_quad_schrabidium_rod") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).stacksTo(1).rarity(Rarity.EPIC)) }
    val scrap: RegistryObject<Item> = ITEMS.registerK("scrap") { object : Item(Properties().tab(CreativeTabs.Parts)) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 800
    }}
    val hotDepletedUraniumFuel = ITEMS.registerK("hot_depleted_uranium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 15f) }
    val hotDepletedThoriumFuel = ITEMS.registerK("hot_depleted_thorium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 10f) }
    val hotDepletedPlutoniumFuel = ITEMS.registerK("hot_depleted_plutonium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 15f) }
    val hotDepletedMOXFuel = ITEMS.registerK("hot_depleted_mox_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 15f) }
    val hotDepletedSchrabidiumFuel = ITEMS.registerK("hot_depleted_schrabidium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding, EffectItem.EffectTypes.Hot), Properties().tab(CreativeTabs.Parts), 40f) }
    val depletedUraniumFuel = ITEMS.registerK("depleted_uranium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 15f) }
    val depletedThoriumFuel = ITEMS.registerK("depleted_thorium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 10f) }
    val depletedPlutoniumFuel = ITEMS.registerK("depleted_plutonium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 15f) }
    val depletedMOXFuel = ITEMS.registerK("depleted_mox_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 15f) }
    val depletedSchrabidiumFuel = ITEMS.registerK("depleted_schrabidium_fuel") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive, EffectItem.EffectTypes.Blinding), Properties().tab(CreativeTabs.Parts), 40f) }
    val trinitite = ITEMS.registerK("trinitite") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), .1f) }
    val nuclearWaste = ITEMS.registerK("nuclear_waste") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 15f) }
    val tinyNuclearWaste = ITEMS.registerK("tiny_nuclear_waste") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Parts), 2f) }
    val crystalHorn = ITEMS.registerK("crystal_horn") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }
    val charredCrystal = ITEMS.registerK("charred_crystal") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts)) }

    // Items and Fuel

    val battery = ITEMS.registerK("battery") { BatteryItem(20_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val redstonePowerCell = ITEMS.registerK("redstone_power_cell") { BatteryItem(60_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val sixfoldRedstonePowerCell = ITEMS.registerK("sixfold_redstone_power_cell") { BatteryItem(360_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val twentyFourFoldRedstonePowerCell = ITEMS.registerK("twenty_four_fold_redstone_power_cell") { BatteryItem(1_440_000, 400, 400, Properties().tab(CreativeTabs.Items)) }
    val advancedBattery = ITEMS.registerK("advanced_battery") { BatteryItem(80_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val advancedPowerCell = ITEMS.registerK("advanced_power_cell") { BatteryItem(240_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val quadrupleAdvancedPowerCell = ITEMS.registerK("quadruple_advanced_power_cell") { BatteryItem(960_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val twelveFoldAdvancedPowerCell = ITEMS.registerK("twelvefold_advanced_power_cell") { BatteryItem(2_880_000, 2_000, 2_000, Properties().tab(CreativeTabs.Items)) }
    val lithiumBattery = ITEMS.registerK("lithium_battery") { BatteryItem(1_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val lithiumPowerCell = ITEMS.registerK("lithium_power_cell") { BatteryItem(3_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val tripleLithiumPowerCell = ITEMS.registerK("triple_lithium_power_cell") { BatteryItem(9_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val sixfoldLithiumPowerCell = ITEMS.registerK("sixfold_lithium_power_cell") { BatteryItem(18_000_000, 4_000, 4_000, Properties().tab(CreativeTabs.Items)) }
    val schrabidiumBattery = ITEMS.registerK("schrabidium_battery") { BatteryItem(4_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val schrabidiumPowerCell = ITEMS.registerK("schrabidium_power_cell") { BatteryItem(12_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val doubleSchrabidiumPowerCell = ITEMS.registerK("double_schrabidium_power_cell") { BatteryItem(24_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val quadrupleSchrabidiumPowerCell = ITEMS.registerK("quadruple_schrabidium_power_cell") { BatteryItem(48_000_000, 20_000, 20_000, Properties().rarity(Rarity.RARE).tab(CreativeTabs.Items)) }
    val sparkBattery = ITEMS.registerK("spark_battery") { BatteryItem(400_000_000, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val offBrandSparkBattery = ITEMS.registerK("off_brand_spark_battery") { BatteryItem(20_000_000, 160_000, 800_000, Properties().tab(CreativeTabs.Items)) }
    val sparkPowerCell = ITEMS.registerK("spark_power_cell") { BatteryItem(2_400_000_000L, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneCarBattery = ITEMS.registerK("spark_arcane_car_battery") { BatteryItem(10_000_000_000L, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneEnergyStorageArray = ITEMS.registerK("spark_arcane_energy_storage_array") { BatteryItem(40_000_000_000L, 8_000_000, 8_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneMassEnergyVoid = ITEMS.registerK("spark_arcane_mass_energy_void") { BatteryItem(400_000_000_000L, 80_000_000, 80_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkArcaneDiracSea = ITEMS.registerK("spark_arcane_dirac_sea") { BatteryItem(1_000_000_000_000L, 80_000_000, 80_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkSolidSpaceTimeCrystal = ITEMS.registerK("spark_solid_space_time_crystal") { BatteryItem(4_000_000_000_000L, 800_000_000, 800_000_000, Properties().tab(CreativeTabs.Items)) }
    val sparkLudicrousPhysicsDefyingEnergyStorageUnit = ITEMS.registerK("spark_ludicrous_physics_defying_energy_storage_unit") { BatteryItem(400_000_000_000_000L, 800_000_000, 800_000_000, Properties().tab(CreativeTabs.Items)) }
    // TODO electronium cube
    val infiniteBattery = ITEMS.registerK("infinite_battery") { BatteryOfInfinity(Properties().tab(CreativeTabs.Items)) }
    val singleUseBattery = ITEMS.registerK("single_use_battery") { BatteryItem(6_000, 0, 400, Properties().tab(CreativeTabs.Items)) }
    val largeSingleUseBattery = ITEMS.registerK("large_single_use_battery") { BatteryItem(14_000, 0, 400, Properties().tab(CreativeTabs.Items)) }
    val potatoBattery = ITEMS.registerK("potato_battery") { BatteryItem(400, 0, 400, Properties().tab(CreativeTabs.Items)) }
    // TODO PotatOS
    val steamPoweredEnergyStorageTank = ITEMS.registerK("steam_powered_energy_storage_tank") { BatteryItem(240_000, 1_200, 24_000, Properties().tab(CreativeTabs.Items)) }
    val largeSteamPoweredEnergyStorageTank = ITEMS.registerK("large_steam_powered_energy_storage_tank") { BatteryItem(400_000, 2_000, 40_000, Properties().tab(CreativeTabs.Items)) }

    val stoneFlatStamp = ITEMS.registerK("stone_flat_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val stonePlateStamp = ITEMS.registerK("stone_plate_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val stoneWireStamp = ITEMS.registerK("stone_wire_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val stoneCircuitStamp = ITEMS.registerK("stone_circuit_stamp") { Item(Properties().durability(10).tab(CreativeTabs.Items)) }
    val ironFlatStamp = ITEMS.registerK("iron_flat_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val ironPlateStamp = ITEMS.registerK("iron_plate_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val ironWireStamp = ITEMS.registerK("iron_wire_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val ironCircuitStamp = ITEMS.registerK("iron_circuit_stamp") { Item(Properties().durability(50).tab(CreativeTabs.Items)) }
    val steelFlatStamp = ITEMS.registerK("steel_flat_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelPlateStamp = ITEMS.registerK("steel_plate_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelWireStamp = ITEMS.registerK("steel_wire_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelCircuitStamp = ITEMS.registerK("steel_circuit_stamp") { Item(Properties().durability(100).tab(CreativeTabs.Items)) }
    val titaniumFlatStamp = ITEMS.registerK("titanium_flat_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val titaniumPlateStamp = ITEMS.registerK("titanium_plate_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val titaniumWireStamp = ITEMS.registerK("titanium_wire_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val titaniumCircuitStamp = ITEMS.registerK("titanium_circuit_stamp") { Item(Properties().durability(150).tab(CreativeTabs.Items)) }
    val obsidianFlatStamp = ITEMS.registerK("obsidian_flat_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val obsidianPlateStamp = ITEMS.registerK("obsidian_plate_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val obsidianWireStamp = ITEMS.registerK("obsidian_wire_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val obsidianCircuitStamp = ITEMS.registerK("obsidian_circuit_stamp") { Item(Properties().durability(170).tab(CreativeTabs.Items)) }
    val schrabidiumFlatStamp = ITEMS.registerK("schrabidium_flat_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val schrabidiumPlateStamp = ITEMS.registerK("schrabidium_plate_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val schrabidiumWireStamp = ITEMS.registerK("schrabidium_wire_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val schrabidiumCircuitStamp = ITEMS.registerK("schrabidium_circuit_stamp") { Item(Properties().durability(3000).tab(CreativeTabs.Items)) }
    val aluminiumShredderBlade = ITEMS.registerK("aluminium_shredder_blade") { ShredderBlade(Properties().durability(20).tab(CreativeTabs.Items)) }
    val goldShredderBlade = ITEMS.registerK("gold_shredder_blade") { ShredderBlade(Properties().durability(30).tab(CreativeTabs.Items)) }
    val ironShredderBlade = ITEMS.registerK("iron_shredder_blade") { ShredderBlade(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelShredderBlade = ITEMS.registerK("steel_shredder_blade") { ShredderBlade(Properties().durability(200).tab(CreativeTabs.Items)) }
    val titaniumShredderBlade = ITEMS.registerK("titanium_shredder_blade") { ShredderBlade(Properties().durability(350).tab(CreativeTabs.Items)) }
    val advancedAlloyShredderBlade = ITEMS.registerK("advanced_alloy_shredder_blade") { ShredderBlade(Properties().durability(700).tab(CreativeTabs.Items)) }
    val combineSteelShredderBlade = ITEMS.registerK("combine_steel_shredder_blade") { ShredderBlade(Properties().durability(1500).tab(CreativeTabs.Items)) }
    val schrabidiumShredderBlade = ITEMS.registerK("schrabidium_shredder_blade") { ShredderBlade(Properties().durability(2000).tab(CreativeTabs.Items)) }
    val deshShredderBlade = ITEMS.registerK("desh_shredder_blade") { ShredderBlade(Properties().stacksTo(1).tab(CreativeTabs.Items)) }

    // Templates

    val machineTemplateFolder = ITEMS.registerK("machine_template_folder") { TemplateFolder() }

    // Siren Tracks
    val sirenTrackHatchSiren = ITEMS.registerK("siren_track_hatch_siren") { SirenTrack(SoundEvents.sirenTrackHatchSiren, 250, true, 0x334077) }
    val sirenTrackAutopilotDisconnected = ITEMS.registerK("siren_track_autopilot_disconnected") { SirenTrack(SoundEvents.sirenTrackAutopilotDisconnected, 50, true, 0xB5B5B5) }
    val sirenTrackAMSSiren = ITEMS.registerK("siren_track_ams_siren") { SirenTrack(SoundEvents.sirenTrackAMSSiren, 100, true, 0xE5BB52) }
    val sirenTrackBlastDoorAlarm = ITEMS.registerK("siren_track_blast_door_alarm") { SirenTrack(SoundEvents.sirenTrackBlastDoorAlarm, 50, true, 0xB20000) }
    val sirenTrackAPCSiren = ITEMS.registerK("siren_track_apc_siren") { SirenTrack(SoundEvents.sirenTrackAPCSiren, 100, true, 0x3666A0) }
    val sirenTrackKlaxon = ITEMS.registerK("siren_track_klaxon") { SirenTrack(SoundEvents.sirenTrackKlaxon, 50, true, 0x808080) }
    val sirenTrackVaultDoorAlarm = ITEMS.registerK("siren_track_vault_door_alarm") { SirenTrack(SoundEvents.sirenTrackVaultDoorAlarm, 50, true, 0x8C810B) }
    val sirenTrackSecurityAlert = ITEMS.registerK("siren_track_security_alert") { SirenTrack(SoundEvents.sirenTrackSecurityAlert, 50, true, 0x76818E) }
    val sirenTrackStandardSiren = ITEMS.registerK("siren_track_standard_siren") { SirenTrack(SoundEvents.sirenTrackStandardSiren, 250, true, 0x660000) }
    val sirenTrackClassicSiren = ITEMS.registerK("siren_track_classic_siren") { SirenTrack(SoundEvents.sirenTrackClassicSiren, 250, true, 0xC0CFE8) }
    val sirenTrackBankAlarm = ITEMS.registerK("siren_track_bank_alarm") { SirenTrack(SoundEvents.sirenTrackBankAlarm, 100, true, 0x3684E2) }
    val sirenTrackBeepSiren = ITEMS.registerK("siren_track_beep_siren") { SirenTrack(SoundEvents.sirenTrackBeepSiren, 100, true, 0xD3D3D3) }
    val sirenTrackContainerAlarm = ITEMS.registerK("siren_track_container_alarm") { SirenTrack(SoundEvents.sirenTrackContainerAlarm, 100, true, 0xE0BA9F) }
    val sirenTrackSweepSiren = ITEMS.registerK("siren_track_sweep_siren") { SirenTrack(SoundEvents.sirenTrackSweepSiren, 500, true, 0xEDEA5A) }
    val sirenTrackMissileSiloSiren = ITEMS.registerK("siren_track_missile_silo_siren") { SirenTrack(SoundEvents.sirenTrackMissileSiloSiren, 500, true, 0xABAB9A) }
    val sirenTrackAirRaidSiren = ITEMS.registerK("siren_track_air_raid_siren") { SirenTrack(SoundEvents.sirenTrackAirRaidSiren, 1000, false, 0xDF3795) }
    val sirenTrackNostromoSelfDestruct = ITEMS.registerK("siren_track_nostromo_self_destruct") { SirenTrack(SoundEvents.sirenTrackNostromoSelfDestruct, 100, true, 0x5DD800) }
    val sirenTrackEASAlarmScreech = ITEMS.registerK("siren_track_eas_alarm_screech") { SirenTrack(SoundEvents.sirenTrackEASAlarmScreech, 50, true, 0xB3A8C1) }
    val sirenTrackAPCPass = ITEMS.registerK("siren_track_apc_pass") { SirenTrack(SoundEvents.sirenTrackAPCPass, 50, false, 0x3437D3) }
    val sirenTrackRazortrainHorn = ITEMS.registerK("siren_track_razortrain_horn") { SirenTrack(SoundEvents.sirenTrackRazortrainHorn, 250, false, 0x7750ED) }

    val assemblyTemplate = ITEMS.registerK("assembly_template") { AssemblyTemplate(Properties().tab(CreativeTabs.Templates)) }

    // Bomb Items

    val neutronShieldingLittleBoy = ITEMS.registerK("neutron_shielding_little_boy") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val subcriticalUraniumTarget = ITEMS.registerK("subcritical_u235_target") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Bombs).stacksTo(1), 5F) }
    val uraniumProjectile = ITEMS.registerK("u235_projectile") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Bombs).stacksTo(1), 1.5F) }
    val propellantLittleBoy = ITEMS.registerK("propellant_little_boy") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bombIgniterLittleBoy = ITEMS.registerK("bomb_igniter_little_boy") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bundleOfImplosionPropellant = ITEMS.registerK("bundle_of_implosion_propellant") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bombIgniterFatMan = ITEMS.registerK("bomb_igniter_fat_man") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val plutoniumCore = ITEMS.registerK("plutonium_core") { EffectItem(listOf(EffectItem.EffectTypes.Radioactive), Properties().tab(CreativeTabs.Bombs).stacksTo(1), 5F) }
    val detonator = ITEMS.registerK("detonator") { Detonator(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val littleBoyKit = ITEMS.registerK("little_boy_kit") { BombKitItem(mapOf(ModBlockItems.littleBoy to 1) + LittleBoy.requiredComponents, 0x0026FF, Properties().tab(CreativeTabs.Bombs)) }
    val fatManKit = ITEMS.registerK("fat_man_kit") { BombKitItem(mapOf(ModBlockItems.fatMan to 1) + FatMan.requiredComponents, 0xFFD800, Properties().tab(CreativeTabs.Bombs)) }

    // Consumables and Gear

    val oilDetector = ITEMS.registerK("oil_detector") { OilDetector(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val geigerCounter = ITEMS.registerK("handheld_geiger_counter") { GeigerCounterItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val ivBag = ITEMS.registerK("iv_bag") { EmptyIVBag(Properties().tab(CreativeTabs.Consumables)) }
    val bloodBag = ITEMS.registerK("blood_bag") { BloodBag(Properties().tab(CreativeTabs.Consumables)) }
    val emptyExperienceBag = ITEMS.registerK("empty_experience_bag") { EmptyExperienceBag(Properties().tab(CreativeTabs.Consumables)) }
    val experienceBag = ITEMS.registerK("experience_bag") { ExperienceBag(Properties().tab(CreativeTabs.Consumables)) }
    val radAway = ITEMS.registerK("radaway") { RadAway(140F, 5 * 20, Properties().tab(CreativeTabs.Consumables)) }
    val strongRadAway = ITEMS.registerK("strong_radaway") { RadAway(350F, 4 * 20, Properties().tab(CreativeTabs.Consumables)) }
    val eliteRadAway = ITEMS.registerK("elite_radaway") { RadAway(1000F, 3 * 20, Properties().tab(CreativeTabs.Consumables)) }

    val titaniumHelmet = ITEMS.registerK("titanium_helmet") { FullSetBonusArmorItem(NuclearArmorMaterials.titanium, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageMod = .85F), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumChestplate = ITEMS.registerK("titanium_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.titanium, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(titaniumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumLeggings = ITEMS.registerK("titanium_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.titanium, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(titaniumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val titaniumBoots = ITEMS.registerK("titanium_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.titanium, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(titaniumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val steelHelmet = ITEMS.registerK("steel_helmet") { FullSetBonusArmorItem(NuclearArmorMaterials.steel, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageMod = .9F), Properties().tab(CreativeTabs.Consumables)) }
    val steelChestplate = ITEMS.registerK("steel_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.steel, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(steelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val steelLeggings = ITEMS.registerK("steel_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.steel, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(steelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val steelBoots = ITEMS.registerK("steel_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.steel, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(steelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyHelmet = ITEMS.registerK("advanced_alloy_helmet") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedAlloy, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageMod = .75F), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyChestplate = ITEMS.registerK("advanced_alloy_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedAlloy, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(advancedAlloyHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyLeggings = ITEMS.registerK("advanced_alloy_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedAlloy, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(advancedAlloyHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val advancedAlloyBoots = ITEMS.registerK("advanced_alloy_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedAlloy, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(advancedAlloyHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelHelmet = ITEMS.registerK("combine_steel_helmet") { FullSetBonusArmorItem(NuclearArmorMaterials.combineSteel, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageCap = 2F, damageMod = .05F, damageThreshold = 2F, fireproof = true, effects = listOf(
        MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, false, false, false),
        MobEffectInstance(MobEffects.DIG_SPEED, 20, 2, false, false, false),
        MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 4, false, false, false),
    )), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelChestplate = ITEMS.registerK("combine_steel_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.combineSteel, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(combineSteelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelLeggings = ITEMS.registerK("combine_steel_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.combineSteel, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(combineSteelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val combineSteelBoots = ITEMS.registerK("combine_steel_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.combineSteel, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(combineSteelHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val paAAlloyChestplate = ITEMS.registerK("paa_alloy_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.paAAlloy, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus(damageCap = 6F, damageMod = .3F, noHelmet = true, effects = listOf(MobEffectInstance(MobEffects.DIG_SPEED, 20, 0, false, false, false))), Properties().tab(CreativeTabs.Consumables)) }
    val paAAlloyLeggings = ITEMS.registerK("paa_alloy_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.paAAlloy, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(paAAlloyChestplate.get()), Properties().tab(CreativeTabs.Consumables)) }
    val paAAlloyBoots = ITEMS.registerK("paa_alloy_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.paAAlloy, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(paAAlloyChestplate.get()), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumHelmet = ITEMS.registerK("schrabidium_helmet") { FullSetBonusArmorItem(NuclearArmorMaterials.schrabidium, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(damageCap = 4F, damageMod = .1F, fireproof = true, effects = listOf(
        MobEffectInstance(MobEffects.DIG_SPEED, 20, 2, false, false, false),
        MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2, false, false, false),
        MobEffectInstance(MobEffects.JUMP, 20, 1, false, false, false),
        MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, false, false, false),
    )), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumChestplate = ITEMS.registerK("schrabidium_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.schrabidium, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(schrabidiumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumLeggings = ITEMS.registerK("schrabidium_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.schrabidium, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(schrabidiumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val schrabidiumBoots = ITEMS.registerK("schrabidium_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.schrabidium, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(schrabidiumHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }

    // Miscellaneous

    val nuclearCreeperSpawnEgg = ITEMS.registerK("nuclear_creeper_spawn_egg") { ForgeSpawnEggItem(EntityTypes.nuclearCreeper, 0x1E3E2E, 0x66B300, Properties().tab(CreativeTabs.Miscellaneous)) }

    // Hidden

    val creativeNuclearExplosionSpawner = ITEMS.registerK("creative_nuclear_explosion_spawner") { CreativeNuclearExplosionSpawner(Properties().stacksTo(1)) }

    private fun Properties.tab(tab: CreativeTabs): Properties = tab(tab.itemGroup)

    // waaay faster code analysis
    private fun <T : IForgeRegistryEntry<T>, R : T> DeferredRegister<T>.registerK(name: String, sup: () -> R): RegistryObject<R> = register(name, sup)
}
