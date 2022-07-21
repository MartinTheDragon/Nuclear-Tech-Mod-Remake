package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.blocks.FatManBlock
import at.martinthedragon.nucleartech.blocks.LittleBoyBlock
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.entities.missiles.*
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
import net.minecraftforge.registries.RegistryObject

@Suppress("unused")
object ModItems {
    private val PARTS_TAB_PROP = Properties().tab(CreativeTabs.Parts)
    private val PARTS_TAB_PROP_UNCOMMON = Properties().tab(CreativeTabs.Parts).rarity(Rarity.UNCOMMON)
    private val PARTS_TAB_PROP_RARE = Properties().tab(CreativeTabs.Parts).rarity(Rarity.RARE)
    private val PARTS_TAB_PROP_EPIC = Properties().tab(CreativeTabs.Parts).rarity(Rarity.EPIC)

    val rawUranium = ITEMS.registerK("raw_uranium") { Item(PARTS_TAB_PROP) }
    val rawThorium = ITEMS.registerK("raw_thorium") { Item(PARTS_TAB_PROP) }
    val rawPlutonium = ITEMS.registerK("raw_plutonium") { Item(PARTS_TAB_PROP) }
    val rawTitanium = ITEMS.registerK("raw_titanium") { Item(PARTS_TAB_PROP) }
    val rawTungsten = ITEMS.registerK("raw_tungsten") { Item(PARTS_TAB_PROP) }
    val rawAluminium = ITEMS.registerK("raw_aluminium") { Item(PARTS_TAB_PROP) }
    val rawBeryllium = ITEMS.registerK("raw_beryllium") { Item(PARTS_TAB_PROP) }
    val rawLead = ITEMS.registerK("raw_lead") { Item(PARTS_TAB_PROP) }
    val rawSchrabidium = ITEMS.registerK("raw_schrabidium") { Item(PARTS_TAB_PROP_RARE) }
    val rawAustralium = ITEMS.registerK("raw_australium") { Item(PARTS_TAB_PROP) }
    val rawRareEarth = ITEMS.registerK("raw_rare_earth") { Item(PARTS_TAB_PROP) }
    val rawCobalt = ITEMS.registerK("raw_cobalt") { Item(PARTS_TAB_PROP) }
    val rawLithium = ITEMS.registerK("raw_lithium") { Item(PARTS_TAB_PROP) }
    val rawStarmetal = ITEMS.registerK("raw_starmetal") { Item(PARTS_TAB_PROP) }
    val rawTrixite = ITEMS.registerK("raw_trixite") { Item(PARTS_TAB_PROP) }
    val uraniumIngot = ITEMS.registerK("uranium_ingot") { Item(PARTS_TAB_PROP) }
    val u233Ingot = ITEMS.registerK("uranium233_ingot") { Item(PARTS_TAB_PROP) }
    val u235Ingot = ITEMS.registerK("uranium235_ingot") { Item(PARTS_TAB_PROP) }
    val u238Ingot = ITEMS.registerK("uranium238_ingot") { Item(PARTS_TAB_PROP) }
    val th232Ingot = ITEMS.registerK("thorium232_ingot") { Item(PARTS_TAB_PROP) }
    val plutoniumIngot = ITEMS.registerK("plutonium_ingot") { Item(PARTS_TAB_PROP) }
    val pu238Ingot = ITEMS.registerK("plutonium238_ingot") { Item(PARTS_TAB_PROP) }
    val pu239Ingot = ITEMS.registerK("plutonium239_ingot") { Item(PARTS_TAB_PROP) }
    val pu240Ingot = ITEMS.registerK("plutonium240_ingot") { Item(PARTS_TAB_PROP) }
    val pu241Ingot = ITEMS.registerK("plutonium241_ingot") { Item(PARTS_TAB_PROP) }
    val reactorGradePlutoniumIngot = ITEMS.registerK("reactor_grade_plutonium_ingot") { Item(PARTS_TAB_PROP) }
    val americium241Ingot = ITEMS.registerK("americium241_ingot") { Item(PARTS_TAB_PROP) }
    val americium242Ingot = ITEMS.registerK("americium242_ingot") { Item(PARTS_TAB_PROP) }
    val reactorGradeAmericiumIngot = ITEMS.registerK("reactor_grade_americium_ingot") { Item(PARTS_TAB_PROP) }
    val neptuniumIngot = ITEMS.registerK("neptunium_ingot") { Item(PARTS_TAB_PROP) }
    val polonium210Ingot = ITEMS.registerK("polonium210_ingot") { Item(PARTS_TAB_PROP) }
    val technetium99Ingot = ITEMS.registerK("technetium99_ingot") { Item(PARTS_TAB_PROP) }
    val cobalt60Ingot = ITEMS.registerK("cobalt60_ingot") { Item(PARTS_TAB_PROP) }
    val strontium90Ingot = ITEMS.registerK("strontium90_ingot") { Item(PARTS_TAB_PROP) }
    val gold198Ingot = ITEMS.registerK("gold198_ingot") { Item(PARTS_TAB_PROP) }
    val lead209Ingot = ITEMS.registerK("lead209_ingot") { Item(PARTS_TAB_PROP) }
    val radium226Ingot = ITEMS.registerK("radium226_ingot") { Item(PARTS_TAB_PROP) }
    val titaniumIngot = ITEMS.registerK("titanium_ingot") { Item(PARTS_TAB_PROP) }
    val redCopperIngot = ITEMS.registerK("red_copper_ingot") { Item(PARTS_TAB_PROP) }
    val advancedAlloyIngot = ITEMS.registerK("advanced_alloy_ingot") { Item(PARTS_TAB_PROP) }
    val tungstenIngot = ITEMS.registerK("tungsten_ingot") { Item(PARTS_TAB_PROP) }
    val aluminiumIngot = ITEMS.registerK("aluminium_ingot") { Item(PARTS_TAB_PROP) }
    val steelIngot = ITEMS.registerK("steel_ingot") { Item(PARTS_TAB_PROP) }
    val technetiumSteelIngot = ITEMS.registerK("technetium_steel_ingot") { Item(PARTS_TAB_PROP) }
    val leadIngot = ITEMS.registerK("lead_ingot") { Item(PARTS_TAB_PROP) }
    val bismuthIngot = ITEMS.registerK("bismuth_ingot") { Item(PARTS_TAB_PROP) }
    val arsenicIngot = ITEMS.registerK("arsenic_ingot") { Item(PARTS_TAB_PROP) }
    val tantaliumIngot = ITEMS.registerK("tantalium_ingot") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val niobiumIngot = ITEMS.registerK("niobium_ingot") { Item(PARTS_TAB_PROP) }
    val berylliumIngot = ITEMS.registerK("beryllium_ingot") { Item(PARTS_TAB_PROP) }
    val cobaltIngot = ITEMS.registerK("cobalt_ingot") { Item(PARTS_TAB_PROP) }
    val boronIngot = ITEMS.registerK("boron_ingot") { Item(PARTS_TAB_PROP) }
    val graphiteIngot = ITEMS.registerK("graphite_ingot") { Item(PARTS_TAB_PROP) }
    val highSpeedSteelIngot = ITEMS.registerK("high_speed_steel_ingot") { Item(PARTS_TAB_PROP) }
    val polymerIngot = ITEMS.registerK("polymer_ingot") { Item(PARTS_TAB_PROP) }
    val bakeliteIngot = ITEMS.registerK("bakelite_ingot") { Item(PARTS_TAB_PROP) }
    val rubberIngot = ITEMS.registerK("rubber_ingot") { Item(PARTS_TAB_PROP) }
    val schraraniumIngot = ITEMS.registerK("schraranium_ingot") { Item(PARTS_TAB_PROP) }
    val schrabidiumIngot = ITEMS.registerK("schrabidium_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val ferricSchrabidateIngot = ITEMS.registerK("ferric_schrabidate_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val magnetizedTungstenIngot = ITEMS.registerK("magnetized_tungsten_ingot") { Item(PARTS_TAB_PROP) }
    val combineSteelIngot = ITEMS.registerK("combine_steel_ingot") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val soliniumIngot = ITEMS.registerK("solinium_ingot") { Item(PARTS_TAB_PROP) }
    val ghiorsium336Ingot = ITEMS.registerK("ghiorsium336_ingot") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val uraniumFuelIngot = ITEMS.registerK("uranium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val thoriumFuelIngot = ITEMS.registerK("thorium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val plutoniumFuelIngot = ITEMS.registerK("plutonium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val neptuniumFuelIngot = ITEMS.registerK("neptunium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val moxFuelIngot = ITEMS.registerK("mox_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val americiumFuelIngot = ITEMS.registerK("americium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val schrabidiumFuelIngot = ITEMS.registerK("schrabidium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val highEnrichedSchrabidiumFuelIngot = ITEMS.registerK("high_enriched_schrabidium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val lowEnrichedSchrabidiumFuelIngot = ITEMS.registerK("low_enriched_schrabidium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val australiumIngot = ITEMS.registerK("australium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val weidaniumIngot = ITEMS.registerK("weidanium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val reiiumIngot = ITEMS.registerK("reiium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val unobtainiumIngot = ITEMS.registerK("unobtainium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val daffergonIngot = ITEMS.registerK("daffergon_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val verticiumIngot = ITEMS.registerK("verticium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val lanthanumIngot = ITEMS.registerK("lanthanum_ingot") { Item(PARTS_TAB_PROP) }
    val actiniumIngot = ITEMS.registerK("actinium227_ingot") { Item(PARTS_TAB_PROP) }
    val deshIngot = ITEMS.registerK("desh_ingot") { Item(PARTS_TAB_PROP) }
    val starmetalIngot = ITEMS.registerK("starmetal_ingot") { Item(PARTS_TAB_PROP) }
    val saturniteIngot = ITEMS.registerK("saturnite_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val euphemiumIngot = ITEMS.registerK("euphemium_ingot") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val dineutroniumIngot = ITEMS.registerK("dineutronium_ingot") { Item(PARTS_TAB_PROP) }
    val electroniumIngot = ITEMS.registerK("electronium_ingot") { Item(PARTS_TAB_PROP) }
    val osmiridiumIngot = ITEMS.registerK("osmiridium_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val whitePhosphorusIngot = ITEMS.registerK("white_phosphorus_ingot") { Item(PARTS_TAB_PROP) }
    val semtexBar = ITEMS.registerK("semtex_bar") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).food(FoodProperties.Builder().nutrition(4).saturationMod(0.5f).build())) }
    val lithiumCube = ITEMS.registerK("lithium_cube") { Item(PARTS_TAB_PROP) }
    val solidFuelCube = ITEMS.registerK("solid_fuel_cube") { Item(PARTS_TAB_PROP) }
    val solidRocketFuelCube = ITEMS.registerK("solid_rocket_fuel_cube") { Item(PARTS_TAB_PROP) }
    val fiberglassSheet = ITEMS.registerK("fiberglass_sheet") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val asbestosSheet = ITEMS.registerK("asbestos_sheet") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val mercuryDroplet = ITEMS.registerK("mercury_droplet") { Item(PARTS_TAB_PROP) }
    val mercuryBottle = ITEMS.registerK("mercury_bottle") { Item(PARTS_TAB_PROP) }
    val coke: RegistryObject<Item> = ITEMS.registerK("coke") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 3200
    }}
    val lignite: RegistryObject<Item> = ITEMS.registerK("lignite") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 1200
    }}
    val ligniteBriquette: RegistryObject<Item> = ITEMS.registerK("lignite_briquette") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 1600
    }}
    val sulfur = ITEMS.registerK("sulfur") { Item(PARTS_TAB_PROP) }
    val niter = ITEMS.registerK("niter") { Item(PARTS_TAB_PROP) }
    val fluorite = ITEMS.registerK("fluorite") { Item(PARTS_TAB_PROP) }
    val coalPowder = ITEMS.registerK("coal_powder") { Item(PARTS_TAB_PROP) }
    val ironPowder = ITEMS.registerK("iron_powder") { Item(PARTS_TAB_PROP) }
    val goldPowder = ITEMS.registerK("gold_powder") { Item(PARTS_TAB_PROP) }
    val lapisLazuliPowder = ITEMS.registerK("lapis_lazuli_powder") { Item(PARTS_TAB_PROP) }
    val quartzPowder = ITEMS.registerK("quartz_powder") { Item(PARTS_TAB_PROP) }
    val diamondPowder = ITEMS.registerK("diamond_powder") { Item(PARTS_TAB_PROP) }
    val emeraldPowder = ITEMS.registerK("emerald_powder") { Item(PARTS_TAB_PROP) }
    val uraniumPowder = ITEMS.registerK("uranium_powder") { Item(PARTS_TAB_PROP) }
    val thoriumPowder = ITEMS.registerK("thorium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val plutoniumPowder = ITEMS.registerK("plutonium_powder") { Item(PARTS_TAB_PROP) }
    val neptuniumPowder = ITEMS.registerK("neptunium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val poloniumPowder = ITEMS.registerK("polonium_powder") { Item(PARTS_TAB_PROP) }
    val titaniumPowder = ITEMS.registerK("titanium_powder") { Item(PARTS_TAB_PROP) }
    val copperPowder = ITEMS.registerK("copper_powder") { Item(PARTS_TAB_PROP) }
    val redCopperPowder = ITEMS.registerK("red_copper_powder") { Item(PARTS_TAB_PROP) }
    val advancedAlloyPowder = ITEMS.registerK("advanced_alloy_powder") { Item(PARTS_TAB_PROP) }
    val tungstenPowder = ITEMS.registerK("tungsten_powder") { Item(PARTS_TAB_PROP) }
    val aluminiumPowder = ITEMS.registerK("aluminium_powder") { Item(PARTS_TAB_PROP) }
    val steelPowder = ITEMS.registerK("steel_powder") { Item(PARTS_TAB_PROP) }
    val leadPowder = ITEMS.registerK("lead_powder") { Item(PARTS_TAB_PROP) }
    val yellowcake = ITEMS.registerK("yellowcake") { Item(PARTS_TAB_PROP) }
    val berylliumPowder = ITEMS.registerK("beryllium_powder") { Item(PARTS_TAB_PROP) }
    val highSpeedSteelPowder = ITEMS.registerK("high_speed_steel_powder") { Item(PARTS_TAB_PROP) }
    val polymerPowder = ITEMS.registerK("polymer_powder") { Item(PARTS_TAB_PROP) }
    val schrabidiumPowder = ITEMS.registerK("schrabidium_powder") { Item(PARTS_TAB_PROP_RARE) }
    val magnetizedTungstenPowder = ITEMS.registerK("magnetized_tungsten_powder") { Item(PARTS_TAB_PROP) }
    val chlorophytePowder = ITEMS.registerK("chlorophyte_powder") { Item(PARTS_TAB_PROP) }
    val combineSteelPowder = ITEMS.registerK("combine_steel_powder") { Item(PARTS_TAB_PROP) }
    val lithiumPowder = ITEMS.registerK("lithium_powder") { Item(PARTS_TAB_PROP) }
    val lignitePowder = ITEMS.registerK("lignite_powder") { Item(PARTS_TAB_PROP) }
    val neodymiumPowder = ITEMS.registerK("neodymium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val australiumPowder = ITEMS.registerK("australium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val weidaniumPowder = ITEMS.registerK("weidanium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val reiiumPowder = ITEMS.registerK("reiium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val unobtainiumPowder = ITEMS.registerK("unobtainium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val daffergonPowder = ITEMS.registerK("daffergon_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val verticiumPowder = ITEMS.registerK("verticium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val cobaltPowder = ITEMS.registerK("cobalt_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val niobiumPowder = ITEMS.registerK("niobium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val ceriumPowder = ITEMS.registerK("cerium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val lanthanumPowder = ITEMS.registerK("lanthanum_powder") { Item(PARTS_TAB_PROP) }
    val actiniumPowder = ITEMS.registerK("actinium227_powder") { Item(PARTS_TAB_PROP) }
    val asbestosPowder = ITEMS.registerK("asbestos_powder") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val enchantmentPowder = ITEMS.registerK("enchantment_powder") { Item(PARTS_TAB_PROP) }
    val cloudResidue = ITEMS.registerK("cloud_residue") { Item(PARTS_TAB_PROP) }
    val thermonuclearAshes = ITEMS.registerK("thermonuclear_ashes") { Item(PARTS_TAB_PROP) }
    val semtexMix = ITEMS.registerK("semtex_mix") { Item(PARTS_TAB_PROP) }
    val deshMix = ITEMS.registerK("desh_mix") { Item(PARTS_TAB_PROP) }
    val deshReadyMix = ITEMS.registerK("desh_ready_mix") { Item(PARTS_TAB_PROP) }
    val deshPowder = ITEMS.registerK("desh_powder") { Item(PARTS_TAB_PROP) }
    val nitaniumMix = ITEMS.registerK("nitanium_mix") { Item(PARTS_TAB_PROP) }
    val sparkMix = ITEMS.registerK("spark_mix") { Item(PARTS_TAB_PROP) }
    val meteoritePowder = ITEMS.registerK("meteorite_powder") { Item(PARTS_TAB_PROP) }
    val euphemiumPowder = ITEMS.registerK("euphemium_powder") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val dineutroniumPowder = ITEMS.registerK("dineutronium_powder") { Item(PARTS_TAB_PROP) }
    val desaturatedRedstone = ITEMS.registerK("desaturated_redstone") { Item(PARTS_TAB_PROP) }
    val dust: RegistryObject<Item> = ITEMS.registerK("dust") { object : AutoTooltippedItem(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 400
    }}
    val tinyLithiumPowder = ITEMS.registerK("tiny_lithium_powder") { Item(PARTS_TAB_PROP) }
    val tinyNeodymiumPowder = ITEMS.registerK("tiny_neodymium_powder") { Item(PARTS_TAB_PROP) }
    val tinyCobaltPowder = ITEMS.registerK("tiny_cobalt_powder") { Item(PARTS_TAB_PROP) }
    val tinyNiobiumPowder = ITEMS.registerK("tiny_niobium_powder") { Item(PARTS_TAB_PROP) }
    val tinyCeriumPowder = ITEMS.registerK("tiny_cerium_powder") { Item(PARTS_TAB_PROP) }
    val tinyLanthanumPowder = ITEMS.registerK("tiny_lanthanum_powder") { Item(PARTS_TAB_PROP) }
    val tinyActiniumPowder = ITEMS.registerK("tiny_actinium_powder") { Item(PARTS_TAB_PROP) }
    val tinyMeteoritePowder = ITEMS.registerK("tiny_meteorite_powder") { Item(PARTS_TAB_PROP) }
    val redPhosphorus = ITEMS.registerK("red_phosphorus") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val cryoPowder = ITEMS.registerK("cryo_powder") { Item(PARTS_TAB_PROP) }
    val poisonPowder = ITEMS.registerK("poison_powder") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val thermite = ITEMS.registerK("thermite") { Item(PARTS_TAB_PROP) }
    val energyPowder = ITEMS.registerK("energy_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val cordite = ITEMS.registerK("cordite") { Item(PARTS_TAB_PROP) }
    val ballistite = ITEMS.registerK("ballistite") { Item(PARTS_TAB_PROP) }
    val coalCrystals = ITEMS.registerK("coal_crystals") { Item(PARTS_TAB_PROP) }
    val ironCrystals = ITEMS.registerK("iron_crystals") { Item(PARTS_TAB_PROP) }
    val goldCrystals = ITEMS.registerK("gold_crystals") { Item(PARTS_TAB_PROP) }
    val redstoneCrystals = ITEMS.registerK("redstone_crystals") { Item(PARTS_TAB_PROP) }
    val lapisCrystals = ITEMS.registerK("lapis_crystals") { Item(PARTS_TAB_PROP) }
    val diamondCrystals = ITEMS.registerK("diamond_crystals") { Item(PARTS_TAB_PROP) }
    val uraniumCrystals = ITEMS.registerK("uranium_crystals") { Item(PARTS_TAB_PROP) }
    val thoriumCrystals = ITEMS.registerK("thorium_crystals") { Item(PARTS_TAB_PROP) }
    val plutoniumCrystals = ITEMS.registerK("plutonium_crystals") { Item(PARTS_TAB_PROP) }
    val titaniumCrystals = ITEMS.registerK("titanium_crystals") { Item(PARTS_TAB_PROP) }
    val sulfurCrystals = ITEMS.registerK("sulfur_crystals") { Item(PARTS_TAB_PROP) }
    val niterCrystals = ITEMS.registerK("niter_crystals") { Item(PARTS_TAB_PROP) }
    val copperCrystals = ITEMS.registerK("copper_crystals") { Item(PARTS_TAB_PROP) }
    val tungstenCrystals = ITEMS.registerK("tungsten_crystals") { Item(PARTS_TAB_PROP) }
    val aluminiumCrystals = ITEMS.registerK("aluminium_crystals") { Item(PARTS_TAB_PROP) }
    val fluoriteCrystals = ITEMS.registerK("fluorite_crystals") { Item(PARTS_TAB_PROP) }
    val berylliumCrystals = ITEMS.registerK("beryllium_crystals") { Item(PARTS_TAB_PROP) }
    val leadCrystals = ITEMS.registerK("lead_crystals") { Item(PARTS_TAB_PROP) }
    val schraraniumCrystals = ITEMS.registerK("schraranium_crystals") { Item(PARTS_TAB_PROP) }
    val schrabidiumCrystals = ITEMS.registerK("schrabidium_crystals") { Item(PARTS_TAB_PROP) }
    val rareEarthCrystals = ITEMS.registerK("rare_earth_crystals") { Item(PARTS_TAB_PROP) }
    val redPhosphorusCrystals = ITEMS.registerK("red_phosphorus_crystals") { Item(PARTS_TAB_PROP) }
    val lithiumCrystals = ITEMS.registerK("lithium_crystals") { Item(PARTS_TAB_PROP) }
    val cobaltCrystals = ITEMS.registerK("cobalt_crystals") { Item(PARTS_TAB_PROP) }
    val starmetalCrystals = ITEMS.registerK("starmetal_crystals") { Item(PARTS_TAB_PROP) }
    val trixiteCrystals = ITEMS.registerK("trixite_crystals") { Item(PARTS_TAB_PROP) }
    val osmiridiumCrystals = ITEMS.registerK("osmiridium_crystals") { Item(PARTS_TAB_PROP_RARE) }
    val neodymiumFragment = ITEMS.registerK("neodymium_fragment") { Item(PARTS_TAB_PROP) }
    val cobaltFragment = ITEMS.registerK("cobalt_fragment") { Item(PARTS_TAB_PROP) }
    val niobiumFragment = ITEMS.registerK("niobium_fragment") { Item(PARTS_TAB_PROP) }
    val ceriumFragment = ITEMS.registerK("cerium_fragment") { Item(PARTS_TAB_PROP) }
    val lanthanumFragment = ITEMS.registerK("lanthanum_fragment") { Item(PARTS_TAB_PROP) }
    val actiniumFragment = ITEMS.registerK("actinium227_fragment") { Item(PARTS_TAB_PROP) }
    val meteoriteFragment = ITEMS.registerK("meteorite_fragment") { Item(PARTS_TAB_PROP) }
    val biomass = ITEMS.registerK("biomass") { Item(PARTS_TAB_PROP) }
    val compressedBiomass = ITEMS.registerK("compressed_biomass") { Item(PARTS_TAB_PROP) }
    val uraniumNugget = ITEMS.registerK("uranium_nugget") { Item(PARTS_TAB_PROP) }
    val u233Nugget = ITEMS.registerK("uranium233_nugget") { Item(PARTS_TAB_PROP) }
    val u235Nugget = ITEMS.registerK("uranium235_nugget") { Item(PARTS_TAB_PROP) }
    val u238Nugget = ITEMS.registerK("uranium238_nugget") { Item(PARTS_TAB_PROP) }
    val th232Nugget = ITEMS.registerK("thorium232_nugget") { Item(PARTS_TAB_PROP) }
    val plutoniumNugget = ITEMS.registerK("plutonium_nugget") { Item(PARTS_TAB_PROP) }
    val pu238Nugget = ITEMS.registerK("plutonium238_nugget") { Item(PARTS_TAB_PROP) }
    val pu239Nugget = ITEMS.registerK("plutonium239_nugget") { Item(PARTS_TAB_PROP) }
    val pu240Nugget = ITEMS.registerK("plutonium240_nugget") { Item(PARTS_TAB_PROP) }
    val pu241Nugget = ITEMS.registerK("plutonium241_nugget") { Item(PARTS_TAB_PROP) }
    val reactorGradePlutoniumNugget = ITEMS.registerK("reactor_grade_plutonium_nugget") { Item(PARTS_TAB_PROP) }
    val americium241Nugget = ITEMS.registerK("americium241_nugget") { Item(PARTS_TAB_PROP) }
    val americium242Nugget = ITEMS.registerK("americium242_nugget") { Item(PARTS_TAB_PROP) }
    val reactorGradeAmericiumNugget = ITEMS.registerK("reactor_grade_americium_nugget") { Item(PARTS_TAB_PROP) }
    val neptuniumNugget = ITEMS.registerK("neptunium_nugget") { Item(PARTS_TAB_PROP) }
    val poloniumNugget = ITEMS.registerK("polonium210_nugget") { Item(PARTS_TAB_PROP) }
    val cobaltNugget = ITEMS.registerK("cobalt_nugget") { Item(PARTS_TAB_PROP) }
    val cobalt60Nugget = ITEMS.registerK("cobalt60_nugget") { Item(PARTS_TAB_PROP) }
    val strontium90Nugget = ITEMS.registerK("strontium90_nugget") { Item(PARTS_TAB_PROP) }
    val technetium99Nugget = ITEMS.registerK("technetium99_nugget") { Item(PARTS_TAB_PROP) }
    val gold198Nugget = ITEMS.registerK("gold198_nugget") { Item(PARTS_TAB_PROP) }
    val lead209Nugget = ITEMS.registerK("lead209_nugget") { Item(PARTS_TAB_PROP) }
    val radium226Nugget = ITEMS.registerK("radium226_nugget") { Item(PARTS_TAB_PROP) }
    val actinium227Nugget = ITEMS.registerK("actinium227_nugget") { Item(PARTS_TAB_PROP) }
    val leadNugget = ITEMS.registerK("lead_nugget") { Item(PARTS_TAB_PROP) }
    val bismuthNugget = ITEMS.registerK("bismuth_nugget") { Item(PARTS_TAB_PROP) }
    val arsenicNugget = ITEMS.registerK("arsenic_nugget") { Item(PARTS_TAB_PROP) }
    val tantaliumNugget = ITEMS.registerK("tantalium_nugget") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val berylliumNugget = ITEMS.registerK("beryllium_nugget") { Item(PARTS_TAB_PROP) }
    val schrabidiumNugget = ITEMS.registerK("schrabidium_nugget") { Item(PARTS_TAB_PROP_RARE) }
    val soliniumNugget = ITEMS.registerK("solinium_nugget") { Item(PARTS_TAB_PROP) }
    val ghiorsium336Nugget = ITEMS.registerK("ghiorsium336_nugget") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val uraniumFuelNugget = ITEMS.registerK("uranium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val thoriumFuelNugget = ITEMS.registerK("thorium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val plutoniumFuelNugget = ITEMS.registerK("plutonium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val neptuniumFuelNugget = ITEMS.registerK("neptunium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val moxFuelNugget = ITEMS.registerK("mox_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val americiumFuelNugget = ITEMS.registerK("americium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val schrabidiumFuelNugget = ITEMS.registerK("schrabidium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val highEnrichedSchrabidiumFuelNugget = ITEMS.registerK("high_enriched_schrabidium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val lowEnrichedSchrabidiumFuelNugget = ITEMS.registerK("low_enriched_schrabidium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val australiumNugget = ITEMS.registerK("australium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val weidaniumNugget = ITEMS.registerK("weidanium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val reiiumNugget = ITEMS.registerK("reiium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val unobtainiumNugget = ITEMS.registerK("unobtainium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val daffergonNugget = ITEMS.registerK("daffergon_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val verticiumNugget = ITEMS.registerK("verticium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val deshNugget = ITEMS.registerK("desh_nugget") { Item(PARTS_TAB_PROP) }
    val euphemiumNugget = ITEMS.registerK("euphemium_nugget") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val dineutroniumNugget = ITEMS.registerK("dineutronium_nugget") { Item(PARTS_TAB_PROP) }
    val osmiridiumNugget = ITEMS.registerK("osmiridium_nugget") { Item(PARTS_TAB_PROP_RARE) }
    val ironPlate = ITEMS.registerK("iron_plate") { Item(PARTS_TAB_PROP) }
    val goldPlate = ITEMS.registerK("gold_plate") { Item(PARTS_TAB_PROP) }
    val titaniumPlate = ITEMS.registerK("titanium_plate") { Item(PARTS_TAB_PROP) }
    val aluminiumPlate = ITEMS.registerK("aluminium_plate") { Item(PARTS_TAB_PROP) }
    val steelPlate = ITEMS.registerK("steel_plate") { Item(PARTS_TAB_PROP) }
    val leadPlate = ITEMS.registerK("lead_plate") { Item(PARTS_TAB_PROP) }
    val copperPlate = ITEMS.registerK("copper_plate") { Item(PARTS_TAB_PROP) }
    val advancedAlloyPlate = ITEMS.registerK("advanced_alloy_plate") { Item(PARTS_TAB_PROP) }
    val neutronReflector = ITEMS.registerK("neutron_reflector") { Item(PARTS_TAB_PROP) }
    val schrabidiumPlate = ITEMS.registerK("schrabidium_plate") { Item(PARTS_TAB_PROP) }
    val combineSteelPlate = ITEMS.registerK("combine_steel_plate") { Item(PARTS_TAB_PROP) }
    val mixedPlate = ITEMS.registerK("mixed_plate") { Item(PARTS_TAB_PROP) }
    val saturnitePlate = ITEMS.registerK("saturnite_plate") { Item(PARTS_TAB_PROP) }
    val paAAlloyPlate = ITEMS.registerK("paa_alloy_plate") { Item(PARTS_TAB_PROP) }
    val insulator = ITEMS.registerK("insulator") { Item(PARTS_TAB_PROP) }
    val kevlarCeramicCompound = ITEMS.registerK("kevlar_ceramic_compound") { Item(PARTS_TAB_PROP) }
    val dalekaniumPlate = ITEMS.registerK("angry_metal") { Item(PARTS_TAB_PROP) }
    val deshCompoundPlate = ITEMS.registerK("desh_compound_plate") { Item(PARTS_TAB_PROP) }
    val euphemiumCompoundPlate = ITEMS.registerK("euphemium_compound_plate") { Item(PARTS_TAB_PROP) }
    val dineutroniumCompoundPlate = ITEMS.registerK("dineutronium_compound_plate") { Item(PARTS_TAB_PROP) }
    val copperPanel = ITEMS.registerK("copper_panel") { Item(PARTS_TAB_PROP) }
    val highSpeedSteelBolt = ITEMS.registerK("high_speed_steel_bolt") { Item(PARTS_TAB_PROP) }
    val tungstenBolt = ITEMS.registerK("tungsten_bolt") { Item(PARTS_TAB_PROP) }
    val reinforcedTurbineShaft = ITEMS.registerK("reinforced_turbine_shaft") { Item(PARTS_TAB_PROP) }
    val hazmatCloth = ITEMS.registerK("hazmat_cloth") { Item(PARTS_TAB_PROP) }
    val advancedHazmatCloth = ITEMS.registerK("advanced_hazmat_cloth") { Item(PARTS_TAB_PROP) }
    val leadReinforcedHazmatCloth = ITEMS.registerK("lead_reinforced_hazmat_cloth") { Item(PARTS_TAB_PROP) }
    val fireProximityCloth = ITEMS.registerK("fire_proximity_cloth") { Item(PARTS_TAB_PROP) }
    val activatedCarbonFilter = ITEMS.registerK("activated_carbon_filter") { Item(PARTS_TAB_PROP) }
    val aluminiumWire = ITEMS.registerK("aluminium_wire") { Item(PARTS_TAB_PROP) }
    val copperWire = ITEMS.registerK("copper_wire") { Item(PARTS_TAB_PROP) }
    val tungstenWire = ITEMS.registerK("tungsten_wire") { Item(PARTS_TAB_PROP) }
    val redCopperWire = ITEMS.registerK("red_copper_wire") { Item(PARTS_TAB_PROP) }
    val superConductor = ITEMS.registerK("super_conductor") { Item(PARTS_TAB_PROP) }
    val goldWire = ITEMS.registerK("gold_wire") { Item(PARTS_TAB_PROP) }
    val schrabidiumWire = ITEMS.registerK("schrabidium_wire") { Item(PARTS_TAB_PROP) }
    val highTemperatureSuperConductor = ITEMS.registerK("high_temperature_super_conductor") { Item(PARTS_TAB_PROP) }
    val copperCoil = ITEMS.registerK("copper_coil") { Item(PARTS_TAB_PROP) }
    val ringCoil = ITEMS.registerK("ring_coil") { Item(PARTS_TAB_PROP) }
    val superConductingCoil = ITEMS.registerK("super_conducting_coil") { Item(PARTS_TAB_PROP) }
    val superConductingRingCoil = ITEMS.registerK("super_conducting_ring_coil") { Item(PARTS_TAB_PROP) }
    val goldCoil = ITEMS.registerK("gold_coil") { Item(PARTS_TAB_PROP) }
    val goldRingCoil = ITEMS.registerK("gold_ring_coil") { Item(PARTS_TAB_PROP) }
    val heatingCoil = ITEMS.registerK("heating_coil") { Item(PARTS_TAB_PROP) }
    val highTemperatureSuperConductingCoil = ITEMS.registerK("high_temperature_super_conducting_coil") { Item(PARTS_TAB_PROP) }
    val steelTank = ITEMS.registerK("steel_tank") { Item(PARTS_TAB_PROP) }
    val motor = ITEMS.registerK("motor") { Item(PARTS_TAB_PROP) }
    val centrifugeElement = ITEMS.registerK("centrifuge_element") { Item(PARTS_TAB_PROP) }
    val centrifugeTower = ITEMS.registerK("centrifuge_tower") { Item(PARTS_TAB_PROP) }
    val deeMagnets = ITEMS.registerK("dee_magnets") { Item(PARTS_TAB_PROP) }
    val flatMagnet = ITEMS.registerK("flat_magnet") { Item(PARTS_TAB_PROP) }
    val cyclotronTower = ITEMS.registerK("cyclotron_tower") { Item(PARTS_TAB_PROP) }
    val breedingReactorCore = ITEMS.registerK("breeding_reactor_core") { Item(PARTS_TAB_PROP) }
    val rtgUnit = ITEMS.registerK("rtg_unit") { Item(PARTS_TAB_PROP) }
    val thermalDistributionUnit = ITEMS.registerK("thermal_distribution_unit") { Item(PARTS_TAB_PROP) }
    val endothermicDistributionUnit = ITEMS.registerK("endothermic_distribution_unit") { Item(PARTS_TAB_PROP) }
    val exothermicDistributionUnit = ITEMS.registerK("exothermic_distribution_unit") { Item(PARTS_TAB_PROP) }
    val gravityManipulator = ITEMS.registerK("gravity_manipulator") { Item(PARTS_TAB_PROP) }
    val steelPipes = ITEMS.registerK("steel_pipes") { Item(PARTS_TAB_PROP) }
    val titaniumDrill = ITEMS.registerK("titanium_drill") { Item(PARTS_TAB_PROP) }
    val photovoltaicPanel = ITEMS.registerK("photovoltaic_panel") { Item(PARTS_TAB_PROP) }
    val chlorinePinwheel = ITEMS.registerK("chlorine_pinwheel") { Item(PARTS_TAB_PROP) }
    val telepad = ITEMS.registerK("telepad") { Item(PARTS_TAB_PROP) }
    val entanglementKit = ITEMS.registerK("entanglement_kit") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val stabilizerComponent = ITEMS.registerK("stabilizer_component") { Item(PARTS_TAB_PROP) }
    val emitterComponent = ITEMS.registerK("emitter_component") { Item(PARTS_TAB_PROP) }
    val aluminiumCap = ITEMS.registerK("aluminium_cap") { Item(PARTS_TAB_PROP) }
    val smallSteelShell = ITEMS.registerK("small_steel_shell") { Item(PARTS_TAB_PROP) }
    val smallAluminiumShell = ITEMS.registerK("small_aluminium_shell") { Item(PARTS_TAB_PROP) }
    val bigSteelShell = ITEMS.registerK("big_steel_shell") { Item(PARTS_TAB_PROP) }
    val bigAluminiumShell = ITEMS.registerK("big_aluminium_shell") { Item(PARTS_TAB_PROP) }
    val bigTitaniumShell = ITEMS.registerK("big_titanium_shell") { Item(PARTS_TAB_PROP) }
    val flatSteelCasing = ITEMS.registerK("flat_steel_casing") { Item(PARTS_TAB_PROP) }
    val smallSteelGridFins = ITEMS.registerK("small_steel_grid_fins") { Item(PARTS_TAB_PROP) }
    val bigSteelGridFins = ITEMS.registerK("big_steel_grid_fins") { Item(PARTS_TAB_PROP) }
    val largeSteelFins = ITEMS.registerK("large_steel_fins") { Item(PARTS_TAB_PROP) }
    val smallTitaniumFins = ITEMS.registerK("small_titanium_fins") { Item(PARTS_TAB_PROP) }
    val steelSphere = ITEMS.registerK("steel_sphere") { Item(PARTS_TAB_PROP) }
    val steelPedestal = ITEMS.registerK("steel_pedestal") { Item(PARTS_TAB_PROP) }
    val dysfunctionalNuclearReactor = ITEMS.registerK("dysfunctional_nuclear_reactor") { Item(PARTS_TAB_PROP) }
    val largeSteelRotor = ITEMS.registerK("large_steel_rotor") { Item(PARTS_TAB_PROP) }
    val generatorBody = ITEMS.registerK("generator_body") { Item(PARTS_TAB_PROP) }
    val titaniumBlade = ITEMS.registerK("titanium_blade") { Item(PARTS_TAB_PROP) }
    val tungstenReinforcedBlade = ITEMS.registerK("tungsten_reinforced_blade") { Item(PARTS_TAB_PROP) }
    val titaniumSteamTurbine = ITEMS.registerK("titanium_steam_turbine") { Item(PARTS_TAB_PROP) }
    val reinforcedTurbofanBlades = ITEMS.registerK("reinforced_turbofan_blades") { Item(PARTS_TAB_PROP) }
    val generatorFront = ITEMS.registerK("generator_front") { Item(PARTS_TAB_PROP) }
    val toothpicks = ITEMS.registerK("toothpicks") { Item(PARTS_TAB_PROP) }
    val ductTape = ITEMS.registerK("duct_tape") { Item(PARTS_TAB_PROP) }
    val clayCatalyst = ITEMS.registerK("clay_catalyst") { Item(PARTS_TAB_PROP) }
    val smallMissileAssembly = ITEMS.registerK("small_missile_assembly") { Item(PARTS_TAB_PROP) }
    val smallWarhead = ITEMS.registerK("small_warhead") { Item(PARTS_TAB_PROP) }
    val mediumWarhead = ITEMS.registerK("medium_warhead") { Item(PARTS_TAB_PROP) }
    val largeWarhead = ITEMS.registerK("large_warhead") { Item(PARTS_TAB_PROP) }
    val smallIncendiaryWarhead = ITEMS.registerK("small_incendiary_warhead") { Item(PARTS_TAB_PROP) }
    val mediumIncendiaryWarhead = ITEMS.registerK("medium_incendiary_warhead") { Item(PARTS_TAB_PROP) }
    val largeIncendiaryWarhead = ITEMS.registerK("large_incendiary_warhead") { Item(PARTS_TAB_PROP) }
    val smallClusterWarhead = ITEMS.registerK("small_cluster_warhead") { Item(PARTS_TAB_PROP) }
    val mediumClusterWarhead = ITEMS.registerK("medium_cluster_warhead") { Item(PARTS_TAB_PROP) }
    val largeClusterWarhead = ITEMS.registerK("large_cluster_warhead") { Item(PARTS_TAB_PROP) }
    val smallBunkerBusterWarhead = ITEMS.registerK("small_bunker_busting_warhead") { Item(PARTS_TAB_PROP) }
    val mediumBunkerBusterWarhead = ITEMS.registerK("medium_bunker_busting_warhead") { Item(PARTS_TAB_PROP) }
    val largeBunkerBusterWarhead = ITEMS.registerK("large_bunker_busting_warhead") { Item(PARTS_TAB_PROP) }
    val nuclearWarhead = ITEMS.registerK("nuclear_warhead") { Item(PARTS_TAB_PROP) }
    val thermonuclearWarhead = ITEMS.registerK("thermonuclear_warhead") { Item(PARTS_TAB_PROP) }
    val endothermicWarhead = ITEMS.registerK("endothermic_warhead") { Item(PARTS_TAB_PROP) }
    val exothermicWarhead = ITEMS.registerK("exothermic_warhead") { Item(PARTS_TAB_PROP) }
    val smallFuelTank = ITEMS.registerK("small_fuel_tank") { Item(PARTS_TAB_PROP) }
    val mediumFuelTank = ITEMS.registerK("medium_fuel_tank") { Item(PARTS_TAB_PROP) }
    val largeFuelTank = ITEMS.registerK("large_fuel_tank") { Item(PARTS_TAB_PROP) }
    val smallThruster = ITEMS.registerK("small_thruster") { Item(PARTS_TAB_PROP) }
    val mediumThruster = ITEMS.registerK("medium_thruster") { Item(PARTS_TAB_PROP) }
    val largeThruster = ITEMS.registerK("large_thruster") { Item(PARTS_TAB_PROP) }
    val lvnNuclearRocketEngine = ITEMS.registerK("lv_n_nuclear_rocket_engine") { Item(PARTS_TAB_PROP) }
    val satelliteBase = ITEMS.registerK("satellite_base") { Item(PARTS_TAB_PROP) }
    val highGainOpticalCamera = ITEMS.registerK("high_gain_optical_camera") { Item(PARTS_TAB_PROP) }
    val m700SurveyScanner = ITEMS.registerK("m700_survey_scanner") { Item(PARTS_TAB_PROP) }
    val radarDish = ITEMS.registerK("radar_dish") { Item(PARTS_TAB_PROP) }
    val deathRay = ITEMS.registerK("death_ray") { Item(PARTS_TAB_PROP) }
    val xeniumResonator = ITEMS.registerK("xenium_resonator") { Item(PARTS_TAB_PROP) }
    val size10Connector = ITEMS.registerK("size_10_connector") { Item(PARTS_TAB_PROP) }
    val size15Connector = ITEMS.registerK("size_15_connector") { Item(PARTS_TAB_PROP) }
    val size20Connector = ITEMS.registerK("size_20_connector") { Item(PARTS_TAB_PROP) }
    val hunterChopperCockpit = ITEMS.registerK("hunter_chopper_cockpit") { Item(PARTS_TAB_PROP) }
    val emplacementGun = ITEMS.registerK("emplacement_gun") { Item(PARTS_TAB_PROP) }
    val hunterChopperBody = ITEMS.registerK("hunter_chopper_body") { Item(PARTS_TAB_PROP) }
    val hunterChopperTail = ITEMS.registerK("hunter_chopper_tail") { Item(PARTS_TAB_PROP) }
    val hunterChopperWing = ITEMS.registerK("hunter_chopper_wing") { Item(PARTS_TAB_PROP) }
    val hunterChopperRotorBlades = ITEMS.registerK("hunter_chopper_rotor_blades") { Item(PARTS_TAB_PROP) }
    val combineScrapMetal = ITEMS.registerK("combine_scrap_metal") { Item(PARTS_TAB_PROP) }
    val heavyHammerHead = ITEMS.registerK("heavy_hammer_head") { Item(PARTS_TAB_PROP) }
    val heavyAxeHead = ITEMS.registerK("heavy_axe_head") { Item(PARTS_TAB_PROP) }
    val reinforcedPolymerHandle = ITEMS.registerK("reinforced_polymer_handle") { Item(PARTS_TAB_PROP) }
    val basicCircuitAssembly = ITEMS.registerK("basic_circuit_assembly") { Item(PARTS_TAB_PROP) }
    val basicCircuit = ITEMS.registerK("basic_circuit") { Item(PARTS_TAB_PROP) }
    val enhancedCircuit = ITEMS.registerK("enhanced_circuit") { Item(PARTS_TAB_PROP) }
    val advancedCircuit = ITEMS.registerK("advanced_circuit") { Item(PARTS_TAB_PROP) }
    val overclockedCircuit = ITEMS.registerK("overclocked_circuit") { Item(PARTS_TAB_PROP) }
    val highPerformanceCircuit = ITEMS.registerK("high_performance_circuit") { Item(PARTS_TAB_PROP_RARE) }
    val militaryGradeCircuitBoardTier1 = ITEMS.registerK("military_grade_circuit_board_tier_1") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier2 = ITEMS.registerK("military_grade_circuit_board_tier_2") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier3 = ITEMS.registerK("military_grade_circuit_board_tier_3") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier4 = ITEMS.registerK("military_grade_circuit_board_tier_4") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier5 = ITEMS.registerK("military_grade_circuit_board_tier_5") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier6 = ITEMS.registerK("military_grade_circuit_board_tier_6") { Item(PARTS_TAB_PROP) }
    val revolverMechanism = ITEMS.registerK("revolver_mechanism") { Item(PARTS_TAB_PROP) }
    val advancedRevolverMechanism = ITEMS.registerK("advanced_revolver_mechanism") { Item(PARTS_TAB_PROP) }
    val rifleMechanism = ITEMS.registerK("rifle_mechanism") { Item(PARTS_TAB_PROP) }
    val advancedRifleMechanism = ITEMS.registerK("advanced_rifle_mechanism") { Item(PARTS_TAB_PROP) }
    val launcherMechanism = ITEMS.registerK("launcher_mechanism") { Item(PARTS_TAB_PROP) }
    val advancedLauncherMechanism = ITEMS.registerK("advanced_launcher_mechanism") { Item(PARTS_TAB_PROP) }
    val highTechWeaponMechanism = ITEMS.registerK("high_tech_weapon_mechanism") { Item(PARTS_TAB_PROP) }
    val point357MagnumPrimer = ITEMS.registerK("point_357_magnum_primer") { Item(PARTS_TAB_PROP) }
    val point44MagnumPrimer = ITEMS.registerK("point_44_magnum_primer") { Item(PARTS_TAB_PROP) }
    val smallCaliberPrimer = ITEMS.registerK("small_caliber_primer") { Item(PARTS_TAB_PROP) }
    val largeCaliberPrimer = ITEMS.registerK("large_caliber_primer") { Item(PARTS_TAB_PROP) }
    val buckshotPrimer = ITEMS.registerK("buckshot_primer") { Item(PARTS_TAB_PROP) }
    val point357MagnumCasing = ITEMS.registerK("point_357_magnum_casing") { Item(PARTS_TAB_PROP) }
    val point44MagnumCasing = ITEMS.registerK("point_44_magnum_casing") { Item(PARTS_TAB_PROP) }
    val smallCaliberCasing = ITEMS.registerK("small_caliber_casing") { Item(PARTS_TAB_PROP) }
    val largeCaliberCasing = ITEMS.registerK("large_caliber_casing") { Item(PARTS_TAB_PROP) }
    val buckshotCasing = ITEMS.registerK("buckshot_casing") { Item(PARTS_TAB_PROP) }
    val ironBulletAssembly = ITEMS.registerK("iron_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val leadBulletAssembly = ITEMS.registerK("lead_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val glassBulletAssembly = ITEMS.registerK("glass_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val goldBulletAssembly = ITEMS.registerK("gold_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val schrabidiumBulletAssembly = ITEMS.registerK("schrabidium_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val nightmareBulletAssembly = ITEMS.registerK("nightmare_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val deshBulletAssembly = ITEMS.registerK("desh_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val point44MagnumAssembly = ITEMS.registerK("point_44_magnum_assembly") { Item(PARTS_TAB_PROP) }
    val nineMmAssembly = ITEMS.registerK("9_mm_assembly") { Item(PARTS_TAB_PROP) }
    val fivePoint56mmAssembly = ITEMS.registerK("5_point_56_mm_assembly") { Item(PARTS_TAB_PROP) }
    val point22LRAssembly = ITEMS.registerK("point_22_lr_assembly") { Item(PARTS_TAB_PROP) }
    val point5mmAssembly = ITEMS.registerK("point_5_mm_assembly") { Item(PARTS_TAB_PROP) }
    val point50AEAssembly = ITEMS.registerK("point_50_ae_assembly") { Item(PARTS_TAB_PROP) }
    val point50BMGAssembly = ITEMS.registerK("point_50_bmg_assembly") { Item(PARTS_TAB_PROP) }
    val silverBulletCasing = ITEMS.registerK("silver_bullet_casing") { Item(PARTS_TAB_PROP) }
    val twelvePoint8cmStarmetalHighEnergyShell = ITEMS.registerK("12_point_8_cm_starmetal_high_energy_shell") { Item(PARTS_TAB_PROP) }
    val twelvePoint8cmNuclearShell = ITEMS.registerK("12_point_8_cm_nuclear_shell") { Item(PARTS_TAB_PROP) }
    val twelvePoint8cmDUShell = ITEMS.registerK("12_point_8_cm_du_shell") { Item(PARTS_TAB_PROP) }
    val cableDrum = ITEMS.registerK("cable_drum") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val paintingOfACartoonPony = ITEMS.registerK("painting_of_a_cartoon_pony") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val conspiracyTheory = ITEMS.registerK("conspiracy_theory") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val politicalTopic = ITEMS.registerK("political_topic") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val ownOpinion = ITEMS.registerK("own_opinion") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val explosivePellets = ITEMS.registerK("explosive_pellets") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val leadPellets = ITEMS.registerK("lead_pellets") { Item(PARTS_TAB_PROP) }
    val flechettes = ITEMS.registerK("flechettes") { Item(PARTS_TAB_PROP) }
    val poisonGasCartridge = ITEMS.registerK("poison_gas_cartridge") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val magnetron = ITEMS.registerK("magnetron") { Item(PARTS_TAB_PROP) }
    val denseCoalCluster = ITEMS.registerK("dense_coal_cluster") { Item(PARTS_TAB_PROP) }
    val burntBark = ITEMS.registerK("burnt_bark") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val machineUpgradeTemplate = ITEMS.registerK("machine_upgrade_template") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val blankRune = ITEMS.registerK("blank_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val isaRune = ITEMS.registerK("isa_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val dagazRune = ITEMS.registerK("dagaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val hagalazRune = ITEMS.registerK("hagalaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val jeraRune = ITEMS.registerK("jera_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val thurisazRune = ITEMS.registerK("thurisaz_rune") { Item(Properties().tab(CreativeTabs.Parts).stacksTo(1)) }
    val burnedOutQuadSchrabidiumFuelRod = ITEMS.registerK("burned_out_quad_schrabidium_rod") { AutoTooltippedItem(Properties().tab(CreativeTabs.Parts).stacksTo(1).rarity(Rarity.EPIC)) }
    val scrap: RegistryObject<Item> = ITEMS.registerK("scrap") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 800
    }}
    val hotDepletedUraniumFuel = ITEMS.registerK("hot_depleted_uranium_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedThoriumFuel = ITEMS.registerK("hot_depleted_thorium_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedPlutoniumFuel = ITEMS.registerK("hot_depleted_plutonium_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedMOXFuel = ITEMS.registerK("hot_depleted_mox_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedSchrabidiumFuel = ITEMS.registerK("hot_depleted_schrabidium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedUraniumFuel = ITEMS.registerK("depleted_uranium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedThoriumFuel = ITEMS.registerK("depleted_thorium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedPlutoniumFuel = ITEMS.registerK("depleted_plutonium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedMOXFuel = ITEMS.registerK("depleted_mox_fuel") { Item(PARTS_TAB_PROP) }
    val depletedSchrabidiumFuel = ITEMS.registerK("depleted_schrabidium_fuel") { Item(PARTS_TAB_PROP) }
    val trinitite = ITEMS.registerK("trinitite") { Item(PARTS_TAB_PROP) }
    val nuclearWaste = ITEMS.registerK("nuclear_waste") { Item(PARTS_TAB_PROP) }
    val tinyNuclearWaste = ITEMS.registerK("tiny_nuclear_waste") { Item(PARTS_TAB_PROP) }
    val crystalHorn = ITEMS.registerK("crystal_horn") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val charredCrystal = ITEMS.registerK("charred_crystal") { AutoTooltippedItem(PARTS_TAB_PROP) }

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
    val infiniteBattery = ITEMS.registerK("infinite_battery") { BatteryOfInfinityItem(Properties().tab(CreativeTabs.Items)) }
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
    val aluminiumShredderBlade = ITEMS.registerK("aluminium_shredder_blade") { ShredderBladeItem(Properties().durability(20).tab(CreativeTabs.Items)) }
    val goldShredderBlade = ITEMS.registerK("gold_shredder_blade") { ShredderBladeItem(Properties().durability(30).tab(CreativeTabs.Items)) }
    val ironShredderBlade = ITEMS.registerK("iron_shredder_blade") { ShredderBladeItem(Properties().durability(100).tab(CreativeTabs.Items)) }
    val steelShredderBlade = ITEMS.registerK("steel_shredder_blade") { ShredderBladeItem(Properties().durability(200).tab(CreativeTabs.Items)) }
    val titaniumShredderBlade = ITEMS.registerK("titanium_shredder_blade") { ShredderBladeItem(Properties().durability(350).tab(CreativeTabs.Items)) }
    val advancedAlloyShredderBlade = ITEMS.registerK("advanced_alloy_shredder_blade") { ShredderBladeItem(Properties().durability(700).tab(CreativeTabs.Items)) }
    val combineSteelShredderBlade = ITEMS.registerK("combine_steel_shredder_blade") { ShredderBladeItem(Properties().durability(1500).tab(CreativeTabs.Items)) }
    val schrabidiumShredderBlade = ITEMS.registerK("schrabidium_shredder_blade") { ShredderBladeItem(Properties().durability(2000).tab(CreativeTabs.Items)) }
    val deshShredderBlade = ITEMS.registerK("desh_shredder_blade") { ShredderBladeItem(Properties().stacksTo(1).tab(CreativeTabs.Items)) }

    // Templates

    val machineTemplateFolder = ITEMS.registerK("machine_template_folder") { TemplateFolderItem() }

    // Siren Tracks
    val sirenTrackHatchSiren = ITEMS.registerK("siren_track_hatch_siren") { SirenTrackItem(SoundEvents.sirenTrackHatchSiren, 250, true, 0x334077) }
    val sirenTrackAutopilotDisconnected = ITEMS.registerK("siren_track_autopilot_disconnected") { SirenTrackItem(SoundEvents.sirenTrackAutopilotDisconnected, 50, true, 0xB5B5B5) }
    val sirenTrackAMSSiren = ITEMS.registerK("siren_track_ams_siren") { SirenTrackItem(SoundEvents.sirenTrackAMSSiren, 100, true, 0xE5BB52) }
    val sirenTrackBlastDoorAlarm = ITEMS.registerK("siren_track_blast_door_alarm") { SirenTrackItem(SoundEvents.sirenTrackBlastDoorAlarm, 50, true, 0xB20000) }
    val sirenTrackAPCSiren = ITEMS.registerK("siren_track_apc_siren") { SirenTrackItem(SoundEvents.sirenTrackAPCSiren, 100, true, 0x3666A0) }
    val sirenTrackKlaxon = ITEMS.registerK("siren_track_klaxon") { SirenTrackItem(SoundEvents.sirenTrackKlaxon, 50, true, 0x808080) }
    val sirenTrackVaultDoorAlarm = ITEMS.registerK("siren_track_vault_door_alarm") { SirenTrackItem(SoundEvents.sirenTrackVaultDoorAlarm, 50, true, 0x8C810B) }
    val sirenTrackSecurityAlert = ITEMS.registerK("siren_track_security_alert") { SirenTrackItem(SoundEvents.sirenTrackSecurityAlert, 50, true, 0x76818E) }
    val sirenTrackStandardSiren = ITEMS.registerK("siren_track_standard_siren") { SirenTrackItem(SoundEvents.sirenTrackStandardSiren, 250, true, 0x660000) }
    val sirenTrackClassicSiren = ITEMS.registerK("siren_track_classic_siren") { SirenTrackItem(SoundEvents.sirenTrackClassicSiren, 250, true, 0xC0CFE8) }
    val sirenTrackBankAlarm = ITEMS.registerK("siren_track_bank_alarm") { SirenTrackItem(SoundEvents.sirenTrackBankAlarm, 100, true, 0x3684E2) }
    val sirenTrackBeepSiren = ITEMS.registerK("siren_track_beep_siren") { SirenTrackItem(SoundEvents.sirenTrackBeepSiren, 100, true, 0xD3D3D3) }
    val sirenTrackContainerAlarm = ITEMS.registerK("siren_track_container_alarm") { SirenTrackItem(SoundEvents.sirenTrackContainerAlarm, 100, true, 0xE0BA9F) }
    val sirenTrackSweepSiren = ITEMS.registerK("siren_track_sweep_siren") { SirenTrackItem(SoundEvents.sirenTrackSweepSiren, 500, true, 0xEDEA5A) }
    val sirenTrackMissileSiloSiren = ITEMS.registerK("siren_track_missile_silo_siren") { SirenTrackItem(SoundEvents.sirenTrackMissileSiloSiren, 500, true, 0xABAB9A) }
    val sirenTrackAirRaidSiren = ITEMS.registerK("siren_track_air_raid_siren") { SirenTrackItem(SoundEvents.sirenTrackAirRaidSiren, 1000, false, 0xDF3795) }
    val sirenTrackNostromoSelfDestruct = ITEMS.registerK("siren_track_nostromo_self_destruct") { SirenTrackItem(SoundEvents.sirenTrackNostromoSelfDestruct, 100, true, 0x5DD800) }
    val sirenTrackEASAlarmScreech = ITEMS.registerK("siren_track_eas_alarm_screech") { SirenTrackItem(SoundEvents.sirenTrackEASAlarmScreech, 50, true, 0xB3A8C1) }
    val sirenTrackAPCPass = ITEMS.registerK("siren_track_apc_pass") { SirenTrackItem(SoundEvents.sirenTrackAPCPass, 50, false, 0x3437D3) }
    val sirenTrackRazortrainHorn = ITEMS.registerK("siren_track_razortrain_horn") { SirenTrackItem(SoundEvents.sirenTrackRazortrainHorn, 250, false, 0x7750ED) }

    val assemblyTemplate = ITEMS.registerK("assembly_template") { AssemblyTemplateItem(Properties().tab(CreativeTabs.Templates)) }
    val chemTemplate = ITEMS.registerK("chem_template") { ChemPlantTemplateItem(Properties().tab(CreativeTabs.Templates)) }

    // Bomb Items

    val neutronShieldingLittleBoy = ITEMS.registerK("neutron_shielding_little_boy") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val subcriticalUraniumTarget = ITEMS.registerK("subcritical_uranium235_target") { Item(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val uraniumProjectile = ITEMS.registerK("uranium235_projectile") { Item(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val propellantLittleBoy = ITEMS.registerK("propellant_little_boy") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bombIgniterLittleBoy = ITEMS.registerK("bomb_igniter_little_boy") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bundleOfImplosionPropellant = ITEMS.registerK("bundle_of_implosion_propellant") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val bombIgniterFatMan = ITEMS.registerK("bomb_igniter_fat_man") { AutoTooltippedItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val plutoniumCore = ITEMS.registerK("plutonium_core") { Item(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val detonator = ITEMS.registerK("detonator") { DetonatorItem(Properties().tab(CreativeTabs.Bombs).stacksTo(1)) }
    val littleBoyKit = ITEMS.registerK("little_boy_kit") { BombKitItem(mapOf(ModBlockItems.littleBoy to 1) + LittleBoyBlock.requiredComponents, 0, 0x0026FF, Properties().tab(CreativeTabs.Bombs)) }
    val fatManKit = ITEMS.registerK("fat_man_kit") { BombKitItem(mapOf(ModBlockItems.fatMan to 1) + FatManBlock.requiredComponents, 0, 0xFFD800, Properties().tab(CreativeTabs.Bombs)) }

    // Rocketry: Missiles and Satellites

    val designator = ITEMS.registerK("designator") { DesignatorItem(Properties().tab(CreativeTabs.Rocketry).stacksTo(1)) }
    val heMissile = ITEMS.registerK("he_missile") { MissileItem(::HEMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val incendiaryMissile = ITEMS.registerK("incendiary_missile") { MissileItem(::IncendiaryMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val clusterMissile = ITEMS.registerK("cluster_missile") { MissileItem(::ClusterMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val bunkerBusterMissile = ITEMS.registerK("bunker_buster_missile") { MissileItem(::BunkerBusterMissile, Properties().tab(CreativeTabs.Rocketry)) }
    val strongMissile = ITEMS.registerK("strong_missile") { MissileItem(::StrongHEMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val strongIncendiaryMissile = ITEMS.registerK("strong_incendiary_missile") { MissileItem(::StrongIncendiaryMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val strongClusterMissile = ITEMS.registerK("strong_cluster_missile") { MissileItem(::StrongClusterMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val strongBunkerBusterMissile = ITEMS.registerK("strong_bunker_buster_missile") { MissileItem(::StrongBunkerBusterMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_STRONG, renderScale = 1.5F) }
    val burstMissile = ITEMS.registerK("burst_missile") { MissileItem(::BurstMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val infernoMissile = ITEMS.registerK("inferno_missile") { MissileItem(::InfernoMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val rainMissile = ITEMS.registerK("rain_missile") { MissileItem(::RainMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val drillMissile = ITEMS.registerK("drill_missile") { MissileItem(::DrillMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.MODEL_MISSILE_HUGE, renderScale = 2F) }
    val nuclearMissile = ITEMS.registerK("nuclear_missile") { MissileItem(::NuclearMissile, Properties().tab(CreativeTabs.Rocketry), renderModel = AbstractMissile.missileModel("missile_nuclear"), renderScale = 1.5F) }

    // Consumables and Gear

    val oilDetector = ITEMS.registerK("oil_detector") { OilDetectorItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val geigerCounter = ITEMS.registerK("handheld_geiger_counter") { GeigerCounterItem(Properties().tab(CreativeTabs.Consumables).stacksTo(1)) }
    val ivBag = ITEMS.registerK("iv_bag") { EmptyIVBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val bloodBag = ITEMS.registerK("blood_bag") { BloodBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val emptyExperienceBag = ITEMS.registerK("empty_experience_bag") { EmptyExperienceBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val experienceBag = ITEMS.registerK("experience_bag") { ExperienceBagItem(Properties().tab(CreativeTabs.Consumables)) }
    val radAway = ITEMS.registerK("radaway") { RadAwayItem(140F, 5 * 20, Properties().tab(CreativeTabs.Consumables)) }
    val strongRadAway = ITEMS.registerK("strong_radaway") { RadAwayItem(350F, 4 * 20, Properties().tab(CreativeTabs.Consumables)) }
    val eliteRadAway = ITEMS.registerK("elite_radaway") { RadAwayItem(1000F, 3 * 20, Properties().tab(CreativeTabs.Consumables)) }

    val hazmatHelmet = ITEMS.registerK("hazmat_helmet") { HazmatMaskItem(NuclearArmorMaterials.hazmat, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val hazmatChestplate = ITEMS.registerK("hazmat_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.hazmat, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val hazmatLeggings = ITEMS.registerK("hazmat_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.hazmat, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val hazmatBoots = ITEMS.registerK("hazmat_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.hazmat, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatHelmet = ITEMS.registerK("advanced_hazmat_helmet") { HazmatMaskItem(NuclearArmorMaterials.advancedHazmat, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatChestplate = ITEMS.registerK("advanced_hazmat_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedHazmat, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatLeggings = ITEMS.registerK("advanced_hazmat_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedHazmat, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val advancedHazmatBoots = ITEMS.registerK("advanced_hazmat_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.advancedHazmat, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.EMPTY, Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatHelmet = ITEMS.registerK("reinforced_hazmat_helmet") { HazmatMaskItem(NuclearArmorMaterials.reinforcedHazmat, EquipmentSlot.HEAD, FullSetBonusArmorItem.FullSetBonus(fireproof = true), Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatChestplate = ITEMS.registerK("reinforced_hazmat_chestplate") { FullSetBonusArmorItem(NuclearArmorMaterials.reinforcedHazmat, EquipmentSlot.CHEST, FullSetBonusArmorItem.FullSetBonus.copyFrom(reinforcedHazmatHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatLeggings = ITEMS.registerK("reinforced_hazmat_leggings") { FullSetBonusArmorItem(NuclearArmorMaterials.reinforcedHazmat, EquipmentSlot.LEGS, FullSetBonusArmorItem.FullSetBonus.copyFrom(reinforcedHazmatHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
    val reinforcedHazmatBoots = ITEMS.registerK("reinforced_hazmat_boots") { FullSetBonusArmorItem(NuclearArmorMaterials.reinforcedHazmat, EquipmentSlot.FEET, FullSetBonusArmorItem.FullSetBonus.copyFrom(reinforcedHazmatHelmet.get()), Properties().tab(CreativeTabs.Consumables)) }
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

    val polaroid = ITEMS.registerK("polaroid") { PolaroidItem(Properties().tab(CreativeTabs.Consumables)) }

    // Miscellaneous

    val nuclearCreeperSpawnEgg = ITEMS.registerK("nuclear_creeper_spawn_egg") { ForgeSpawnEggItem(EntityTypes.nuclearCreeper, 0x1E3E2E, 0x66B300, Properties().tab(CreativeTabs.Miscellaneous)) }

    // Hidden

    val creativeNuclearExplosionSpawner = ITEMS.registerK("creative_nuclear_explosion_spawner") { CreativeNuclearExplosionSpawnerItem(Properties().stacksTo(1)) }

    private fun Properties.tab(tab: CreativeTabs): Properties = tab(tab.itemGroup)

    // waaay faster code analysis
}
