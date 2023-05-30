package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.coremodules.forge.registries.RegistryObject
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.TextComponent
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Rarity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.crafting.RecipeType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.registries.ItemRegistry
import at.martinthedragon.nucleartech.registries.Registries
import at.martinthedragon.nucleartech.sorcerer.EnchantRegistry

@EnchantRegistry
object NTechItemsCore {
    val ITEMS: ItemRegistry get() = Registries.itemRegistry

    private val PARTS_TAB_PROP = Item.Properties(tab = CreativeTabs.Parts.tab)
    private val PARTS_TAB_PROP_UNCOMMON = PARTS_TAB_PROP.copy(rarity = Rarity.UNCOMMON)
    private val PARTS_TAB_PROP_RARE = PARTS_TAB_PROP.copy(rarity = Rarity.RARE)
    private val PARTS_TAB_PROP_EPIC = PARTS_TAB_PROP.copy(rarity = Rarity.EPIC)
    private val ITEMS_TAB_PROP = Item.Properties(tab = CreativeTabs.Items.tab)
    private val ITEMS_TAB_PROP_RARE = ITEMS_TAB_PROP.copy(rarity = Rarity.RARE)

    val uraniumIngot = ITEMS.register("uranium_ingot") { Item(PARTS_TAB_PROP) }
    val u233Ingot = ITEMS.register("uranium233_ingot") { Item(PARTS_TAB_PROP) }
    val u235Ingot = ITEMS.register("uranium235_ingot") { Item(PARTS_TAB_PROP) }
    val u238Ingot = ITEMS.register("uranium238_ingot") { Item(PARTS_TAB_PROP) }
    val th232Ingot = ITEMS.register("thorium232_ingot") { Item(PARTS_TAB_PROP) }
    val plutoniumIngot = ITEMS.register("plutonium_ingot") { Item(PARTS_TAB_PROP) }
    val pu238Ingot = ITEMS.register("plutonium238_ingot") { Item(PARTS_TAB_PROP) }
    val pu239Ingot = ITEMS.register("plutonium239_ingot") { Item(PARTS_TAB_PROP) }
    val pu240Ingot = ITEMS.register("plutonium240_ingot") { Item(PARTS_TAB_PROP) }
    val pu241Ingot = ITEMS.register("plutonium241_ingot") { Item(PARTS_TAB_PROP) }
    val reactorGradePlutoniumIngot = ITEMS.register("reactor_grade_plutonium_ingot") { Item(PARTS_TAB_PROP) }
    val americium241Ingot = ITEMS.register("americium241_ingot") { Item(PARTS_TAB_PROP) }
    val americium242Ingot = ITEMS.register("americium242_ingot") { Item(PARTS_TAB_PROP) }
    val reactorGradeAmericiumIngot = ITEMS.register("reactor_grade_americium_ingot") { Item(PARTS_TAB_PROP) }
    val neptuniumIngot = ITEMS.register("neptunium_ingot") { Item(PARTS_TAB_PROP) }
    val polonium210Ingot = ITEMS.register("polonium210_ingot") { Item(PARTS_TAB_PROP) }
    val technetium99Ingot = ITEMS.register("technetium99_ingot") { Item(PARTS_TAB_PROP) }
    val cobalt60Ingot = ITEMS.register("cobalt60_ingot") { Item(PARTS_TAB_PROP) }
    val strontium90Ingot = ITEMS.register("strontium90_ingot") { Item(PARTS_TAB_PROP) }
    val gold198Ingot = ITEMS.register("gold198_ingot") { Item(PARTS_TAB_PROP) }
    val lead209Ingot = ITEMS.register("lead209_ingot") { Item(PARTS_TAB_PROP) }
    val radium226Ingot = ITEMS.register("radium226_ingot") { Item(PARTS_TAB_PROP) }
    val titaniumIngot = ITEMS.register("titanium_ingot") { Item(PARTS_TAB_PROP) }
    val redCopperIngot = ITEMS.register("red_copper_ingot") { Item(PARTS_TAB_PROP) }
    val advancedAlloyIngot = ITEMS.register("advanced_alloy_ingot") { Item(PARTS_TAB_PROP) }
    val tungstenIngot = ITEMS.register("tungsten_ingot") { Item(PARTS_TAB_PROP) }
    val aluminiumIngot = ITEMS.register("aluminium_ingot") { Item(PARTS_TAB_PROP) }
    val steelIngot = ITEMS.register("steel_ingot") { Item(PARTS_TAB_PROP) }
    val technetiumSteelIngot = ITEMS.register("technetium_steel_ingot") { Item(PARTS_TAB_PROP) }
    val leadIngot = ITEMS.register("lead_ingot") { Item(PARTS_TAB_PROP) }
    val bismuthIngot = ITEMS.register("bismuth_ingot") { Item(PARTS_TAB_PROP) }
    val arsenicIngot = ITEMS.register("arsenic_ingot") { Item(PARTS_TAB_PROP) }
    val tantaliumIngot = ITEMS.register("tantalium_ingot") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val niobiumIngot = ITEMS.register("niobium_ingot") { Item(PARTS_TAB_PROP) }
    val berylliumIngot = ITEMS.register("beryllium_ingot") { Item(PARTS_TAB_PROP) }
    val cobaltIngot = ITEMS.register("cobalt_ingot") { Item(PARTS_TAB_PROP) }
    val boronIngot = ITEMS.register("boron_ingot") { Item(PARTS_TAB_PROP) }
    val graphiteIngot = ITEMS.register("graphite_ingot") { Item(PARTS_TAB_PROP) }
    val highSpeedSteelIngot = ITEMS.register("high_speed_steel_ingot") { Item(PARTS_TAB_PROP) }
    val polymerIngot = ITEMS.register("polymer_ingot") { Item(PARTS_TAB_PROP) }
    val bakeliteIngot = ITEMS.register("bakelite_ingot") { Item(PARTS_TAB_PROP) }
    val rubberIngot = ITEMS.register("rubber_ingot") { Item(PARTS_TAB_PROP) }
    val schraraniumIngot = ITEMS.register("schraranium_ingot") { Item(PARTS_TAB_PROP) }
    val schrabidiumIngot = ITEMS.register("schrabidium_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val ferricSchrabidateIngot = ITEMS.register("ferric_schrabidate_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val magnetizedTungstenIngot = ITEMS.register("magnetized_tungsten_ingot") { Item(PARTS_TAB_PROP) }
    val combineSteelIngot = ITEMS.register("combine_steel_ingot") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val soliniumIngot = ITEMS.register("solinium_ingot") { Item(PARTS_TAB_PROP) }
    val ghiorsium336Ingot = ITEMS.register("ghiorsium336_ingot") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val uraniumFuelIngot = ITEMS.register("uranium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val thoriumFuelIngot = ITEMS.register("thorium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val plutoniumFuelIngot = ITEMS.register("plutonium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val neptuniumFuelIngot = ITEMS.register("neptunium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val moxFuelIngot = ITEMS.register("mox_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val americiumFuelIngot = ITEMS.register("americium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val schrabidiumFuelIngot = ITEMS.register("schrabidium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val highEnrichedSchrabidiumFuelIngot = ITEMS.register("high_enriched_schrabidium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val lowEnrichedSchrabidiumFuelIngot = ITEMS.register("low_enriched_schrabidium_fuel_ingot") { Item(PARTS_TAB_PROP) }
    val australiumIngot = ITEMS.register("australium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val weidaniumIngot = ITEMS.register("weidanium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val reiiumIngot = ITEMS.register("reiium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val unobtainiumIngot = ITEMS.register("unobtainium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val daffergonIngot = ITEMS.register("daffergon_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val verticiumIngot = ITEMS.register("verticium_ingot") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val lanthanumIngot = ITEMS.register("lanthanum_ingot") { Item(PARTS_TAB_PROP) }
    val actiniumIngot = ITEMS.register("actinium227_ingot") { Item(PARTS_TAB_PROP) }
    val deshIngot = ITEMS.register("desh_ingot") { Item(PARTS_TAB_PROP) }
    val starmetalIngot = ITEMS.register("starmetal_ingot") { Item(PARTS_TAB_PROP) }
    val saturniteIngot = ITEMS.register("saturnite_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val euphemiumIngot = ITEMS.register("euphemium_ingot") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val dineutroniumIngot = ITEMS.register("dineutronium_ingot") { Item(PARTS_TAB_PROP) }
    val electroniumIngot = ITEMS.register("electronium_ingot") { Item(PARTS_TAB_PROP) }
    val osmiridiumIngot = ITEMS.register("osmiridium_ingot") { Item(PARTS_TAB_PROP_RARE) }
    val whitePhosphorusIngot = ITEMS.register("white_phosphorus_ingot") { Item(PARTS_TAB_PROP) }
    val semtexBar = ITEMS.register("semtex_bar") { AutoTooltippedItem(Item.Properties().tab(CreativeTabs.Parts).food(FoodProperties.Builder().nutrition(4).saturationMod(0.5f).build())) }
    val lithiumCube = ITEMS.register("lithium_cube") { Item(PARTS_TAB_PROP) }
    val solidFuelCube = ITEMS.register("solid_fuel_cube") { Item(PARTS_TAB_PROP) }
    val solidRocketFuelCube = ITEMS.register("solid_rocket_fuel_cube") { Item(PARTS_TAB_PROP) }
    val fiberglassSheet = ITEMS.register("fiberglass_sheet") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val asbestosSheet = ITEMS.register("asbestos_sheet") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val uraniumBillet = ITEMS.register("uranium_billet") { Item(PARTS_TAB_PROP) }
    val u233Billet = ITEMS.register("uranium233_billet") { Item(PARTS_TAB_PROP) }
    val u235Billet = ITEMS.register("uranium235_billet") { Item(PARTS_TAB_PROP) }
    val u238Billet = ITEMS.register("uranium238_billet") { Item(PARTS_TAB_PROP) }
    val th232Billet = ITEMS.register("thorium232_billet") { Item(PARTS_TAB_PROP) }
    val plutoniumBillet = ITEMS.register("plutonium_billet") { Item(PARTS_TAB_PROP) }
    val pu238Billet = ITEMS.register("plutonium238_billet") { Item(PARTS_TAB_PROP) }
    val pu239Billet = ITEMS.register("plutonium239_billet") { Item(PARTS_TAB_PROP) }
    val pu240Billet = ITEMS.register("plutonium240_billet") { Item(PARTS_TAB_PROP) }
    val pu241Billet = ITEMS.register("plutonium241_billet") { Item(PARTS_TAB_PROP) }
    val reactorGradePlutoniumBillet = ITEMS.register("reactor_grade_plutonium_billet") { Item(PARTS_TAB_PROP) }
    val americium241Billet = ITEMS.register("americium241_billet") { Item(PARTS_TAB_PROP) }
    val americium242Billet = ITEMS.register("americium242_billet") { Item(PARTS_TAB_PROP) }
    val reactorGradeAmericiumBillet = ITEMS.register("reactor_grade_americium_billet") { Item(PARTS_TAB_PROP) }
    val neptuniumBillet = ITEMS.register("neptunium_billet") { Item(PARTS_TAB_PROP) }
    val poloniumBillet = ITEMS.register("polonium_billet") { Item(PARTS_TAB_PROP) }
    val technetium99Billet = ITEMS.register("technetium99_billet") { Item(PARTS_TAB_PROP) }
    val cobaltBillet = ITEMS.register("cobalt_billet") { Item(PARTS_TAB_PROP) }
    val cobalt60Billet = ITEMS.register("cobalt60_billet") { Item(PARTS_TAB_PROP) }
    val strontium90Billet = ITEMS.register("strontium90_billet") { Item(PARTS_TAB_PROP) }
    val gold198Billet = ITEMS.register("gold198_billet") { Item(PARTS_TAB_PROP) }
    val lead209Billet = ITEMS.register("lead209_billet") { Item(PARTS_TAB_PROP) }
    val radium226Billet = ITEMS.register("radium226_billet") { Item(PARTS_TAB_PROP) }
    val actinium227Billet = ITEMS.register("actinium227_billet") { Item(PARTS_TAB_PROP) }
    val schrabidiumBillet = ITEMS.register("schrabidium_billet") { Item(PARTS_TAB_PROP_RARE) }
    val soliniumBillet = ITEMS.register("solinium_billet") { Item(PARTS_TAB_PROP) }
    val ghiorsium336Billet = ITEMS.register("ghiorsium336_billet") { Item(PARTS_TAB_PROP) }
    val australiumBillet = ITEMS.register("australium_billet") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val lesserAustraliumBillet = ITEMS.register("lesser_australium_billet") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val greaterAustraliumBillet = ITEMS.register("greater_australium_billet") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val uraniumFuelBillet = ITEMS.register("uranium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val thoriumFuelBillet = ITEMS.register("thorium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val plutoniumFuelBillet = ITEMS.register("plutonium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val neptuniumFuelBillet = ITEMS.register("neptunium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val moxFuelBillet = ITEMS.register("mox_fuel_billet") { Item(PARTS_TAB_PROP) }
    val americiumFuelBillet = ITEMS.register("americium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val lowEnrichedSchrabidiumFuelBillet = ITEMS.register("low_enriched_schrabidium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val schrabidiumFuelBillet = ITEMS.register("schrabidium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val highEnrichedSchrabidiumFuelBillet = ITEMS.register("high_enriched_schrabidium_fuel_billet") { Item(PARTS_TAB_PROP) }
    val po210BeBillet = ITEMS.register("polonium210_beryllium_billet") { Item(PARTS_TAB_PROP) }
    val ra226BeBillet = ITEMS.register("radium226_beryllium_billet") { Item(PARTS_TAB_PROP) }
    val pu238BeBillet = ITEMS.register("plutonium238_beryllium_billet") { Item(PARTS_TAB_PROP) }
    val berylliumBillet = ITEMS.register("beryllium_billet") { Item(PARTS_TAB_PROP) }
    val bismuthBillet = ITEMS.register("bismuth_billet") { Item(PARTS_TAB_PROP) }
    val zirconiumBillet = ITEMS.register("zirconium_billet") { Item(PARTS_TAB_PROP) }
    val bismuthZfbBillet = ITEMS.register("bismuth_zfb_billet") { Item(PARTS_TAB_PROP) }
    val pu241ZfbBillet = ITEMS.register("plutonium241_zfb_billet") { Item(PARTS_TAB_PROP) }
    val reactorGradeAmericiumZfbBillet = ITEMS.register("reactor_grade_americium_zfb_billet") { Item(PARTS_TAB_PROP) }
    val yharoniteBillet = ITEMS.register("yharonite_billet") { Item(PARTS_TAB_PROP) }
    val flashgoldBillet = ITEMS.register("flashgold_billet") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val flashleadBillet = ITEMS.register("flashlead_billet") { AutoTooltippedItem(PARTS_TAB_PROP_UNCOMMON) }
    val nuclearWasteBillet = ITEMS.register("nuclear_waste_billet") { Item(PARTS_TAB_PROP) }
    val mercuryDroplet = ITEMS.register("mercury_droplet") { Item(PARTS_TAB_PROP) }
    val mercuryBottle = ITEMS.register("mercury_bottle") { Item(PARTS_TAB_PROP) }
    val coke: RegistryObject<Item> = ITEMS.register("coke") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 3200
    }}
    val lignite: RegistryObject<Item> = ITEMS.register("lignite") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 1200
    }}
    val ligniteBriquette: RegistryObject<Item> = ITEMS.register("lignite_briquette") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 1600
    }}
    val sulfur = ITEMS.register("sulfur") { Item(PARTS_TAB_PROP) }
    val niter = ITEMS.register("niter") { Item(PARTS_TAB_PROP) }
    val fluorite = ITEMS.register("fluorite") { Item(PARTS_TAB_PROP) }
    val coalPowder = ITEMS.register("coal_powder") { Item(PARTS_TAB_PROP) }
    val ironPowder = ITEMS.register("iron_powder") { Item(PARTS_TAB_PROP) }
    val goldPowder = ITEMS.register("gold_powder") { Item(PARTS_TAB_PROP) }
    val lapisLazuliPowder = ITEMS.register("lapis_lazuli_powder") { Item(PARTS_TAB_PROP) }
    val quartzPowder = ITEMS.register("quartz_powder") { Item(PARTS_TAB_PROP) }
    val diamondPowder = ITEMS.register("diamond_powder") { Item(PARTS_TAB_PROP) }
    val emeraldPowder = ITEMS.register("emerald_powder") { Item(PARTS_TAB_PROP) }
    val uraniumPowder = ITEMS.register("uranium_powder") { Item(PARTS_TAB_PROP) }
    val thoriumPowder = ITEMS.register("thorium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val plutoniumPowder = ITEMS.register("plutonium_powder") { Item(PARTS_TAB_PROP) }
    val neptuniumPowder = ITEMS.register("neptunium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val poloniumPowder = ITEMS.register("polonium_powder") { Item(PARTS_TAB_PROP) }
    val titaniumPowder = ITEMS.register("titanium_powder") { Item(PARTS_TAB_PROP) }
    val copperPowder = ITEMS.register("copper_powder") { Item(PARTS_TAB_PROP) }
    val redCopperPowder = ITEMS.register("red_copper_powder") { Item(PARTS_TAB_PROP) }
    val advancedAlloyPowder = ITEMS.register("advanced_alloy_powder") { Item(PARTS_TAB_PROP) }
    val tungstenPowder = ITEMS.register("tungsten_powder") { Item(PARTS_TAB_PROP) }
    val aluminiumPowder = ITEMS.register("aluminium_powder") { Item(PARTS_TAB_PROP) }
    val steelPowder = ITEMS.register("steel_powder") { Item(PARTS_TAB_PROP) }
    val leadPowder = ITEMS.register("lead_powder") { Item(PARTS_TAB_PROP) }
    val yellowcake = ITEMS.register("yellowcake") { Item(PARTS_TAB_PROP) }
    val berylliumPowder = ITEMS.register("beryllium_powder") { Item(PARTS_TAB_PROP) }
    val highSpeedSteelPowder = ITEMS.register("high_speed_steel_powder") { Item(PARTS_TAB_PROP) }
    val polymerPowder = ITEMS.register("polymer_powder") { Item(PARTS_TAB_PROP) }
    val schrabidiumPowder = ITEMS.register("schrabidium_powder") { Item(PARTS_TAB_PROP_RARE) }
    val magnetizedTungstenPowder = ITEMS.register("magnetized_tungsten_powder") { Item(PARTS_TAB_PROP) }
    val chlorophytePowder = ITEMS.register("chlorophyte_powder") { Item(PARTS_TAB_PROP) }
    val combineSteelPowder = ITEMS.register("combine_steel_powder") { Item(PARTS_TAB_PROP) }
    val lithiumPowder = ITEMS.register("lithium_powder") { Item(PARTS_TAB_PROP) }
    val lignitePowder = ITEMS.register("lignite_powder") { Item(PARTS_TAB_PROP) }
    val neodymiumPowder = ITEMS.register("neodymium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val australiumPowder = ITEMS.register("australium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val weidaniumPowder = ITEMS.register("weidanium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val reiiumPowder = ITEMS.register("reiium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val unobtainiumPowder = ITEMS.register("unobtainium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val daffergonPowder = ITEMS.register("daffergon_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val verticiumPowder = ITEMS.register("verticium_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val cobaltPowder = ITEMS.register("cobalt_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val niobiumPowder = ITEMS.register("niobium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val ceriumPowder = ITEMS.register("cerium_powder") { Item(PARTS_TAB_PROP_EPIC) }
    val lanthanumPowder = ITEMS.register("lanthanum_powder") { Item(PARTS_TAB_PROP) }
    val actiniumPowder = ITEMS.register("actinium227_powder") { Item(PARTS_TAB_PROP) }
    val asbestosPowder = ITEMS.register("asbestos_powder") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val enchantmentPowder = ITEMS.register("enchantment_powder") { Item(PARTS_TAB_PROP) }
    val cloudResidue = ITEMS.register("cloud_residue") { Item(PARTS_TAB_PROP) }
    val thermonuclearAshes = ITEMS.register("thermonuclear_ashes") { Item(PARTS_TAB_PROP) }
    val semtexMix = ITEMS.register("semtex_mix") { Item(PARTS_TAB_PROP) }
    val deshMix = ITEMS.register("desh_mix") { Item(PARTS_TAB_PROP) }
    val deshReadyMix = ITEMS.register("desh_ready_mix") { Item(PARTS_TAB_PROP) }
    val deshPowder = ITEMS.register("desh_powder") { Item(PARTS_TAB_PROP) }
    val nitaniumMix = ITEMS.register("nitanium_mix") { Item(PARTS_TAB_PROP) }
    val sparkMix = ITEMS.register("spark_mix") { Item(PARTS_TAB_PROP) }
    val meteoritePowder = ITEMS.register("meteorite_powder") { Item(PARTS_TAB_PROP) }
    val euphemiumPowder = ITEMS.register("euphemium_powder") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val dineutroniumPowder = ITEMS.register("dineutronium_powder") { Item(PARTS_TAB_PROP) }
    val desaturatedRedstone = ITEMS.register("desaturated_redstone") { Item(PARTS_TAB_PROP) }
    val dust: RegistryObject<Item> = ITEMS.register("dust") { object : AutoTooltippedItem(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 400
    }}
    val tinyLithiumPowder = ITEMS.register("tiny_lithium_powder") { Item(PARTS_TAB_PROP) }
    val tinyNeodymiumPowder = ITEMS.register("tiny_neodymium_powder") { Item(PARTS_TAB_PROP) }
    val tinyCobaltPowder = ITEMS.register("tiny_cobalt_powder") { Item(PARTS_TAB_PROP) }
    val tinyNiobiumPowder = ITEMS.register("tiny_niobium_powder") { Item(PARTS_TAB_PROP) }
    val tinyCeriumPowder = ITEMS.register("tiny_cerium_powder") { Item(PARTS_TAB_PROP) }
    val tinyLanthanumPowder = ITEMS.register("tiny_lanthanum_powder") { Item(PARTS_TAB_PROP) }
    val tinyActiniumPowder = ITEMS.register("tiny_actinium_powder") { Item(PARTS_TAB_PROP) }
    val tinyMeteoritePowder = ITEMS.register("tiny_meteorite_powder") { Item(PARTS_TAB_PROP) }
    val redPhosphorus = ITEMS.register("red_phosphorus") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val cryoPowder = ITEMS.register("cryo_powder") { Item(PARTS_TAB_PROP) }
    val poisonPowder = ITEMS.register("poison_powder") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val thermite = ITEMS.register("thermite") { Item(PARTS_TAB_PROP) }
    val energyPowder = ITEMS.register("energy_powder") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val cordite = ITEMS.register("cordite") { Item(PARTS_TAB_PROP) }
    val ballistite = ITEMS.register("ballistite") { Item(PARTS_TAB_PROP) }
    val coalCrystals = ITEMS.register("coal_crystals") { Item(PARTS_TAB_PROP) }
    val ironCrystals = ITEMS.register("iron_crystals") { Item(PARTS_TAB_PROP) }
    val goldCrystals = ITEMS.register("gold_crystals") { Item(PARTS_TAB_PROP) }
    val redstoneCrystals = ITEMS.register("redstone_crystals") { Item(PARTS_TAB_PROP) }
    val lapisCrystals = ITEMS.register("lapis_crystals") { Item(PARTS_TAB_PROP) }
    val diamondCrystals = ITEMS.register("diamond_crystals") { Item(PARTS_TAB_PROP) }
    val uraniumCrystals = ITEMS.register("uranium_crystals") { Item(PARTS_TAB_PROP) }
    val thoriumCrystals = ITEMS.register("thorium_crystals") { Item(PARTS_TAB_PROP) }
    val plutoniumCrystals = ITEMS.register("plutonium_crystals") { Item(PARTS_TAB_PROP) }
    val titaniumCrystals = ITEMS.register("titanium_crystals") { Item(PARTS_TAB_PROP) }
    val sulfurCrystals = ITEMS.register("sulfur_crystals") { Item(PARTS_TAB_PROP) }
    val niterCrystals = ITEMS.register("niter_crystals") { Item(PARTS_TAB_PROP) }
    val copperCrystals = ITEMS.register("copper_crystals") { Item(PARTS_TAB_PROP) }
    val tungstenCrystals = ITEMS.register("tungsten_crystals") { Item(PARTS_TAB_PROP) }
    val aluminiumCrystals = ITEMS.register("aluminium_crystals") { Item(PARTS_TAB_PROP) }
    val fluoriteCrystals = ITEMS.register("fluorite_crystals") { Item(PARTS_TAB_PROP) }
    val berylliumCrystals = ITEMS.register("beryllium_crystals") { Item(PARTS_TAB_PROP) }
    val leadCrystals = ITEMS.register("lead_crystals") { Item(PARTS_TAB_PROP) }
    val schraraniumCrystals = ITEMS.register("schraranium_crystals") { Item(PARTS_TAB_PROP) }
    val schrabidiumCrystals = ITEMS.register("schrabidium_crystals") { Item(PARTS_TAB_PROP) }
    val rareEarthCrystals = ITEMS.register("rare_earth_crystals") { Item(PARTS_TAB_PROP) }
    val redPhosphorusCrystals = ITEMS.register("red_phosphorus_crystals") { Item(PARTS_TAB_PROP) }
    val lithiumCrystals = ITEMS.register("lithium_crystals") { Item(PARTS_TAB_PROP) }
    val cobaltCrystals = ITEMS.register("cobalt_crystals") { Item(PARTS_TAB_PROP) }
    val starmetalCrystals = ITEMS.register("starmetal_crystals") { Item(PARTS_TAB_PROP) }
    val trixiteCrystals = ITEMS.register("trixite_crystals") { Item(PARTS_TAB_PROP) }
    val osmiridiumCrystals = ITEMS.register("osmiridium_crystals") { Item(PARTS_TAB_PROP_RARE) }
    val volcanicGem = ITEMS.register("volcanic_gem") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val neodymiumFragment = ITEMS.register("neodymium_fragment") { Item(PARTS_TAB_PROP) }
    val cobaltFragment = ITEMS.register("cobalt_fragment") { Item(PARTS_TAB_PROP) }
    val niobiumFragment = ITEMS.register("niobium_fragment") { Item(PARTS_TAB_PROP) }
    val ceriumFragment = ITEMS.register("cerium_fragment") { Item(PARTS_TAB_PROP) }
    val lanthanumFragment = ITEMS.register("lanthanum_fragment") { Item(PARTS_TAB_PROP) }
    val actiniumFragment = ITEMS.register("actinium227_fragment") { Item(PARTS_TAB_PROP) }
    val meteoriteFragment = ITEMS.register("meteorite_fragment") { Item(PARTS_TAB_PROP) }
    val biomass = ITEMS.register("biomass") { Item(PARTS_TAB_PROP) }
    val compressedBiomass = ITEMS.register("compressed_biomass") { Item(PARTS_TAB_PROP) }
    val uraniumNugget = ITEMS.register("uranium_nugget") { Item(PARTS_TAB_PROP) }
    val u233Nugget = ITEMS.register("uranium233_nugget") { Item(PARTS_TAB_PROP) }
    val u235Nugget = ITEMS.register("uranium235_nugget") { Item(PARTS_TAB_PROP) }
    val u238Nugget = ITEMS.register("uranium238_nugget") { Item(PARTS_TAB_PROP) }
    val th232Nugget = ITEMS.register("thorium232_nugget") { Item(PARTS_TAB_PROP) }
    val plutoniumNugget = ITEMS.register("plutonium_nugget") { Item(PARTS_TAB_PROP) }
    val pu238Nugget = ITEMS.register("plutonium238_nugget") { Item(PARTS_TAB_PROP) }
    val pu239Nugget = ITEMS.register("plutonium239_nugget") { Item(PARTS_TAB_PROP) }
    val pu240Nugget = ITEMS.register("plutonium240_nugget") { Item(PARTS_TAB_PROP) }
    val pu241Nugget = ITEMS.register("plutonium241_nugget") { Item(PARTS_TAB_PROP) }
    val reactorGradePlutoniumNugget = ITEMS.register("reactor_grade_plutonium_nugget") { Item(PARTS_TAB_PROP) }
    val americium241Nugget = ITEMS.register("americium241_nugget") { Item(PARTS_TAB_PROP) }
    val americium242Nugget = ITEMS.register("americium242_nugget") { Item(PARTS_TAB_PROP) }
    val reactorGradeAmericiumNugget = ITEMS.register("reactor_grade_americium_nugget") { Item(PARTS_TAB_PROP) }
    val neptuniumNugget = ITEMS.register("neptunium_nugget") { Item(PARTS_TAB_PROP) }
    val poloniumNugget = ITEMS.register("polonium210_nugget") { Item(PARTS_TAB_PROP) }
    val cobaltNugget = ITEMS.register("cobalt_nugget") { Item(PARTS_TAB_PROP) }
    val cobalt60Nugget = ITEMS.register("cobalt60_nugget") { Item(PARTS_TAB_PROP) }
    val strontium90Nugget = ITEMS.register("strontium90_nugget") { Item(PARTS_TAB_PROP) }
    val technetium99Nugget = ITEMS.register("technetium99_nugget") { Item(PARTS_TAB_PROP) }
    val gold198Nugget = ITEMS.register("gold198_nugget") { Item(PARTS_TAB_PROP) }
    val lead209Nugget = ITEMS.register("lead209_nugget") { Item(PARTS_TAB_PROP) }
    val radium226Nugget = ITEMS.register("radium226_nugget") { Item(PARTS_TAB_PROP) }
    val actinium227Nugget = ITEMS.register("actinium227_nugget") { Item(PARTS_TAB_PROP) }
    val leadNugget = ITEMS.register("lead_nugget") { Item(PARTS_TAB_PROP) }
    val bismuthNugget = ITEMS.register("bismuth_nugget") { Item(PARTS_TAB_PROP) }
    val arsenicNugget = ITEMS.register("arsenic_nugget") { Item(PARTS_TAB_PROP) }
    val tantaliumNugget = ITEMS.register("tantalium_nugget") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val berylliumNugget = ITEMS.register("beryllium_nugget") { Item(PARTS_TAB_PROP) }
    val schrabidiumNugget = ITEMS.register("schrabidium_nugget") { Item(PARTS_TAB_PROP_RARE) }
    val soliniumNugget = ITEMS.register("solinium_nugget") { Item(PARTS_TAB_PROP) }
    val ghiorsium336Nugget = ITEMS.register("ghiorsium336_nugget") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val uraniumFuelNugget = ITEMS.register("uranium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val thoriumFuelNugget = ITEMS.register("thorium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val plutoniumFuelNugget = ITEMS.register("plutonium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val neptuniumFuelNugget = ITEMS.register("neptunium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val moxFuelNugget = ITEMS.register("mox_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val americiumFuelNugget = ITEMS.register("americium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val schrabidiumFuelNugget = ITEMS.register("schrabidium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val highEnrichedSchrabidiumFuelNugget = ITEMS.register("high_enriched_schrabidium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val lowEnrichedSchrabidiumFuelNugget = ITEMS.register("low_enriched_schrabidium_fuel_nugget") { Item(PARTS_TAB_PROP) }
    val australiumNugget = ITEMS.register("australium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val weidaniumNugget = ITEMS.register("weidanium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val reiiumNugget = ITEMS.register("reiium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val unobtainiumNugget = ITEMS.register("unobtainium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val daffergonNugget = ITEMS.register("daffergon_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val verticiumNugget = ITEMS.register("verticium_nugget") { Item(PARTS_TAB_PROP_UNCOMMON) }
    val deshNugget = ITEMS.register("desh_nugget") { Item(PARTS_TAB_PROP) }
    val euphemiumNugget = ITEMS.register("euphemium_nugget") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC) }
    val dineutroniumNugget = ITEMS.register("dineutronium_nugget") { Item(PARTS_TAB_PROP) }
    val osmiridiumNugget = ITEMS.register("osmiridium_nugget") { Item(PARTS_TAB_PROP_RARE) }
    val ironPlate = ITEMS.register("iron_plate") { Item(PARTS_TAB_PROP) }
    val goldPlate = ITEMS.register("gold_plate") { Item(PARTS_TAB_PROP) }
    val titaniumPlate = ITEMS.register("titanium_plate") { Item(PARTS_TAB_PROP) }
    val aluminiumPlate = ITEMS.register("aluminium_plate") { Item(PARTS_TAB_PROP) }
    val steelPlate = ITEMS.register("steel_plate") { Item(PARTS_TAB_PROP) }
    val leadPlate = ITEMS.register("lead_plate") { Item(PARTS_TAB_PROP) }
    val copperPlate = ITEMS.register("copper_plate") { Item(PARTS_TAB_PROP) }
    val advancedAlloyPlate = ITEMS.register("advanced_alloy_plate") { Item(PARTS_TAB_PROP) }
    val neutronReflector = ITEMS.register("neutron_reflector") { Item(PARTS_TAB_PROP) }
    val schrabidiumPlate = ITEMS.register("schrabidium_plate") { Item(PARTS_TAB_PROP) }
    val combineSteelPlate = ITEMS.register("combine_steel_plate") { Item(PARTS_TAB_PROP) }
    val mixedPlate = ITEMS.register("mixed_plate") { Item(PARTS_TAB_PROP) }
    val saturnitePlate = ITEMS.register("saturnite_plate") { Item(PARTS_TAB_PROP) }
    val paAAlloyPlate = ITEMS.register("paa_alloy_plate") { Item(PARTS_TAB_PROP) }
    val insulator = ITEMS.register("insulator") { Item(PARTS_TAB_PROP) }
    val kevlarCeramicCompound = ITEMS.register("kevlar_ceramic_compound") { Item(PARTS_TAB_PROP) }
    val dalekaniumPlate = ITEMS.register("angry_metal") { Item(PARTS_TAB_PROP) }
    val deshCompoundPlate = ITEMS.register("desh_compound_plate") { Item(PARTS_TAB_PROP) }
    val euphemiumCompoundPlate = ITEMS.register("euphemium_compound_plate") { Item(PARTS_TAB_PROP) }
    val dineutroniumCompoundPlate = ITEMS.register("dineutronium_compound_plate") { Item(PARTS_TAB_PROP) }
    val copperPanel = ITEMS.register("copper_panel") { Item(PARTS_TAB_PROP) }
    val highSpeedSteelBolt = ITEMS.register("high_speed_steel_bolt") { Item(PARTS_TAB_PROP) }
    val tungstenBolt = ITEMS.register("tungsten_bolt") { Item(PARTS_TAB_PROP) }
    val reinforcedTurbineShaft = ITEMS.register("reinforced_turbine_shaft") { Item(PARTS_TAB_PROP) }
    val hazmatCloth = ITEMS.register("hazmat_cloth") { Item(PARTS_TAB_PROP) }
    val advancedHazmatCloth = ITEMS.register("advanced_hazmat_cloth") { Item(PARTS_TAB_PROP) }
    val leadReinforcedHazmatCloth = ITEMS.register("lead_reinforced_hazmat_cloth") { Item(PARTS_TAB_PROP) }
    val fireProximityCloth = ITEMS.register("fire_proximity_cloth") { Item(PARTS_TAB_PROP) }
    val activatedCarbonFilter = ITEMS.register("activated_carbon_filter") { Item(PARTS_TAB_PROP) }
    val aluminiumWire = ITEMS.register("aluminium_wire") { Item(PARTS_TAB_PROP) }
    val copperWire = ITEMS.register("copper_wire") { Item(PARTS_TAB_PROP) }
    val tungstenWire = ITEMS.register("tungsten_wire") { Item(PARTS_TAB_PROP) }
    val redCopperWire = ITEMS.register("red_copper_wire") { Item(PARTS_TAB_PROP) }
    val superConductor = ITEMS.register("super_conductor") { Item(PARTS_TAB_PROP) }
    val goldWire = ITEMS.register("gold_wire") { Item(PARTS_TAB_PROP) }
    val schrabidiumWire = ITEMS.register("schrabidium_wire") { Item(PARTS_TAB_PROP) }
    val highTemperatureSuperConductor = ITEMS.register("high_temperature_super_conductor") { Item(PARTS_TAB_PROP) }
    val copperCoil = ITEMS.register("copper_coil") { Item(PARTS_TAB_PROP) }
    val ringCoil = ITEMS.register("ring_coil") { Item(PARTS_TAB_PROP) }
    val superConductingCoil = ITEMS.register("super_conducting_coil") { Item(PARTS_TAB_PROP) }
    val superConductingRingCoil = ITEMS.register("super_conducting_ring_coil") { Item(PARTS_TAB_PROP) }
    val goldCoil = ITEMS.register("gold_coil") { Item(PARTS_TAB_PROP) }
    val goldRingCoil = ITEMS.register("gold_ring_coil") { Item(PARTS_TAB_PROP) }
    val heatingCoil = ITEMS.register("heating_coil") { Item(PARTS_TAB_PROP) }
    val highTemperatureSuperConductingCoil = ITEMS.register("high_temperature_super_conducting_coil") { Item(PARTS_TAB_PROP) }
    val steelTank = ITEMS.register("steel_tank") { Item(PARTS_TAB_PROP) }
    val motor = ITEMS.register("motor") { Item(PARTS_TAB_PROP) }
    val centrifugeElement = ITEMS.register("centrifuge_element") { Item(PARTS_TAB_PROP) }
    val centrifugeTower = ITEMS.register("centrifuge_tower") { Item(PARTS_TAB_PROP) }
    val deeMagnets = ITEMS.register("dee_magnets") { Item(PARTS_TAB_PROP) }
    val flatMagnet = ITEMS.register("flat_magnet") { Item(PARTS_TAB_PROP) }
    val cyclotronTower = ITEMS.register("cyclotron_tower") { Item(PARTS_TAB_PROP) }
    val breedingReactorCore = ITEMS.register("breeding_reactor_core") { Item(PARTS_TAB_PROP) }
    val rtgUnit = ITEMS.register("rtg_unit") { Item(PARTS_TAB_PROP) }
    val thermalDistributionUnit = ITEMS.register("thermal_distribution_unit") { Item(PARTS_TAB_PROP) }
    val endothermicDistributionUnit = ITEMS.register("endothermic_distribution_unit") { Item(PARTS_TAB_PROP) }
    val exothermicDistributionUnit = ITEMS.register("exothermic_distribution_unit") { Item(PARTS_TAB_PROP) }
    val gravityManipulator = ITEMS.register("gravity_manipulator") { Item(PARTS_TAB_PROP) }
    val steelPipes = ITEMS.register("steel_pipes") { Item(PARTS_TAB_PROP) }
    val titaniumDrill = ITEMS.register("titanium_drill") { Item(PARTS_TAB_PROP) }
    val photovoltaicPanel = ITEMS.register("photovoltaic_panel") { Item(PARTS_TAB_PROP) }
    val chlorinePinwheel = ITEMS.register("chlorine_pinwheel") { Item(PARTS_TAB_PROP) }
    val telepad = ITEMS.register("telepad") { Item(PARTS_TAB_PROP) }
    val entanglementKit = ITEMS.register("entanglement_kit") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val stabilizerComponent = ITEMS.register("stabilizer_component") { Item(PARTS_TAB_PROP) }
    val emitterComponent = ITEMS.register("emitter_component") { Item(PARTS_TAB_PROP) }
    val aluminiumCap = ITEMS.register("aluminium_cap") { Item(PARTS_TAB_PROP) }
    val smallSteelShell = ITEMS.register("small_steel_shell") { Item(PARTS_TAB_PROP) }
    val smallAluminiumShell = ITEMS.register("small_aluminium_shell") { Item(PARTS_TAB_PROP) }
    val bigSteelShell = ITEMS.register("big_steel_shell") { Item(PARTS_TAB_PROP) }
    val bigAluminiumShell = ITEMS.register("big_aluminium_shell") { Item(PARTS_TAB_PROP) }
    val bigTitaniumShell = ITEMS.register("big_titanium_shell") { Item(PARTS_TAB_PROP) }
    val flatSteelCasing = ITEMS.register("flat_steel_casing") { Item(PARTS_TAB_PROP) }
    val smallSteelGridFins = ITEMS.register("small_steel_grid_fins") { Item(PARTS_TAB_PROP) }
    val bigSteelGridFins = ITEMS.register("big_steel_grid_fins") { Item(PARTS_TAB_PROP) }
    val largeSteelFins = ITEMS.register("large_steel_fins") { Item(PARTS_TAB_PROP) }
    val smallTitaniumFins = ITEMS.register("small_titanium_fins") { Item(PARTS_TAB_PROP) }
    val steelSphere = ITEMS.register("steel_sphere") { Item(PARTS_TAB_PROP) }
    val steelPedestal = ITEMS.register("steel_pedestal") { Item(PARTS_TAB_PROP) }
    val dysfunctionalNuclearReactor = ITEMS.register("dysfunctional_nuclear_reactor") { Item(PARTS_TAB_PROP) }
    val largeSteelRotor = ITEMS.register("large_steel_rotor") { Item(PARTS_TAB_PROP) }
    val generatorBody = ITEMS.register("generator_body") { Item(PARTS_TAB_PROP) }
    val titaniumBlade = ITEMS.register("titanium_blade") { Item(PARTS_TAB_PROP) }
    val tungstenReinforcedBlade = ITEMS.register("tungsten_reinforced_blade") { Item(PARTS_TAB_PROP) }
    val titaniumSteamTurbine = ITEMS.register("titanium_steam_turbine") { Item(PARTS_TAB_PROP) }
    val reinforcedTurbofanBlades = ITEMS.register("reinforced_turbofan_blades") { Item(PARTS_TAB_PROP) }
    val generatorFront = ITEMS.register("generator_front") { Item(PARTS_TAB_PROP) }
    val toothpicks = ITEMS.register("toothpicks") { Item(PARTS_TAB_PROP) }
    val ductTape = ITEMS.register("duct_tape") { Item(PARTS_TAB_PROP) }
    val clayCatalyst = ITEMS.register("clay_catalyst") { Item(PARTS_TAB_PROP) }
    val smallMissileAssembly = ITEMS.register("small_missile_assembly") { Item(PARTS_TAB_PROP) }
    val smallWarhead = ITEMS.register("small_warhead") { Item(PARTS_TAB_PROP) }
    val mediumWarhead = ITEMS.register("medium_warhead") { Item(PARTS_TAB_PROP) }
    val largeWarhead = ITEMS.register("large_warhead") { Item(PARTS_TAB_PROP) }
    val smallIncendiaryWarhead = ITEMS.register("small_incendiary_warhead") { Item(PARTS_TAB_PROP) }
    val mediumIncendiaryWarhead = ITEMS.register("medium_incendiary_warhead") { Item(PARTS_TAB_PROP) }
    val largeIncendiaryWarhead = ITEMS.register("large_incendiary_warhead") { Item(PARTS_TAB_PROP) }
    val smallClusterWarhead = ITEMS.register("small_cluster_warhead") { Item(PARTS_TAB_PROP) }
    val mediumClusterWarhead = ITEMS.register("medium_cluster_warhead") { Item(PARTS_TAB_PROP) }
    val largeClusterWarhead = ITEMS.register("large_cluster_warhead") { Item(PARTS_TAB_PROP) }
    val smallBunkerBusterWarhead = ITEMS.register("small_bunker_busting_warhead") { Item(PARTS_TAB_PROP) }
    val mediumBunkerBusterWarhead = ITEMS.register("medium_bunker_busting_warhead") { Item(PARTS_TAB_PROP) }
    val largeBunkerBusterWarhead = ITEMS.register("large_bunker_busting_warhead") { Item(PARTS_TAB_PROP) }
    val nuclearWarhead = ITEMS.register("nuclear_warhead") { Item(PARTS_TAB_PROP) }
    val thermonuclearWarhead = ITEMS.register("thermonuclear_warhead") { Item(PARTS_TAB_PROP) }
    val endothermicWarhead = ITEMS.register("endothermic_warhead") { Item(PARTS_TAB_PROP) }
    val exothermicWarhead = ITEMS.register("exothermic_warhead") { Item(PARTS_TAB_PROP) }
    val smallFuelTank = ITEMS.register("small_fuel_tank") { Item(PARTS_TAB_PROP) }
    val mediumFuelTank = ITEMS.register("medium_fuel_tank") { Item(PARTS_TAB_PROP) }
    val largeFuelTank = ITEMS.register("large_fuel_tank") { Item(PARTS_TAB_PROP) }
    val smallThruster = ITEMS.register("small_thruster") { Item(PARTS_TAB_PROP) }
    val mediumThruster = ITEMS.register("medium_thruster") { Item(PARTS_TAB_PROP) }
    val largeThruster = ITEMS.register("large_thruster") { Item(PARTS_TAB_PROP) }
    val lvnNuclearRocketEngine = ITEMS.register("lv_n_nuclear_rocket_engine") { Item(PARTS_TAB_PROP) }
    val satelliteBase = ITEMS.register("satellite_base") { Item(PARTS_TAB_PROP) }
    val highGainOpticalCamera = ITEMS.register("high_gain_optical_camera") { Item(PARTS_TAB_PROP) }
    val m700SurveyScanner = ITEMS.register("m700_survey_scanner") { Item(PARTS_TAB_PROP) }
    val radarDish = ITEMS.register("radar_dish") { Item(PARTS_TAB_PROP) }
    val deathRay = ITEMS.register("death_ray") { Item(PARTS_TAB_PROP) }
    val xeniumResonator = ITEMS.register("xenium_resonator") { Item(PARTS_TAB_PROP) }
    val size10Connector = ITEMS.register("size_10_connector") { Item(PARTS_TAB_PROP) }
    val size15Connector = ITEMS.register("size_15_connector") { Item(PARTS_TAB_PROP) }
    val size20Connector = ITEMS.register("size_20_connector") { Item(PARTS_TAB_PROP) }
    val hunterChopperCockpit = ITEMS.register("hunter_chopper_cockpit") { Item(PARTS_TAB_PROP) }
    val emplacementGun = ITEMS.register("emplacement_gun") { Item(PARTS_TAB_PROP) }
    val hunterChopperBody = ITEMS.register("hunter_chopper_body") { Item(PARTS_TAB_PROP) }
    val hunterChopperTail = ITEMS.register("hunter_chopper_tail") { Item(PARTS_TAB_PROP) }
    val hunterChopperWing = ITEMS.register("hunter_chopper_wing") { Item(PARTS_TAB_PROP) }
    val hunterChopperRotorBlades = ITEMS.register("hunter_chopper_rotor_blades") { Item(PARTS_TAB_PROP) }
    val combineScrapMetal = ITEMS.register("combine_scrap_metal") { Item(PARTS_TAB_PROP) }
    val heavyHammerHead = ITEMS.register("heavy_hammer_head") { Item(PARTS_TAB_PROP) }
    val heavyAxeHead = ITEMS.register("heavy_axe_head") { Item(PARTS_TAB_PROP) }
    val reinforcedPolymerHandle = ITEMS.register("reinforced_polymer_handle") { Item(PARTS_TAB_PROP) }
    val basicCircuitAssembly = ITEMS.register("basic_circuit_assembly") { Item(PARTS_TAB_PROP) }
    val basicCircuit = ITEMS.register("basic_circuit") { Item(PARTS_TAB_PROP) }
    val enhancedCircuit = ITEMS.register("enhanced_circuit") { Item(PARTS_TAB_PROP) }
    val advancedCircuit = ITEMS.register("advanced_circuit") { Item(PARTS_TAB_PROP) }
    val overclockedCircuit = ITEMS.register("overclocked_circuit") { Item(PARTS_TAB_PROP) }
    val highPerformanceCircuit = ITEMS.register("high_performance_circuit") { Item(PARTS_TAB_PROP_RARE) }
    val militaryGradeCircuitBoardTier1 = ITEMS.register("military_grade_circuit_board_tier_1") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier2 = ITEMS.register("military_grade_circuit_board_tier_2") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier3 = ITEMS.register("military_grade_circuit_board_tier_3") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier4 = ITEMS.register("military_grade_circuit_board_tier_4") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier5 = ITEMS.register("military_grade_circuit_board_tier_5") { Item(PARTS_TAB_PROP) }
    val militaryGradeCircuitBoardTier6 = ITEMS.register("military_grade_circuit_board_tier_6") { Item(PARTS_TAB_PROP) }
    val revolverMechanism = ITEMS.register("revolver_mechanism") { Item(PARTS_TAB_PROP) }
    val advancedRevolverMechanism = ITEMS.register("advanced_revolver_mechanism") { Item(PARTS_TAB_PROP) }
    val rifleMechanism = ITEMS.register("rifle_mechanism") { Item(PARTS_TAB_PROP) }
    val advancedRifleMechanism = ITEMS.register("advanced_rifle_mechanism") { Item(PARTS_TAB_PROP) }
    val launcherMechanism = ITEMS.register("launcher_mechanism") { Item(PARTS_TAB_PROP) }
    val advancedLauncherMechanism = ITEMS.register("advanced_launcher_mechanism") { Item(PARTS_TAB_PROP) }
    val highTechWeaponMechanism = ITEMS.register("high_tech_weapon_mechanism") { Item(PARTS_TAB_PROP) }
    val point357MagnumPrimer = ITEMS.register("point_357_magnum_primer") { Item(PARTS_TAB_PROP) }
    val point44MagnumPrimer = ITEMS.register("point_44_magnum_primer") { Item(PARTS_TAB_PROP) }
    val smallCaliberPrimer = ITEMS.register("small_caliber_primer") { Item(PARTS_TAB_PROP) }
    val largeCaliberPrimer = ITEMS.register("large_caliber_primer") { Item(PARTS_TAB_PROP) }
    val buckshotPrimer = ITEMS.register("buckshot_primer") { Item(PARTS_TAB_PROP) }
    val point357MagnumCasing = ITEMS.register("point_357_magnum_casing") { Item(PARTS_TAB_PROP) }
    val point44MagnumCasing = ITEMS.register("point_44_magnum_casing") { Item(PARTS_TAB_PROP) }
    val smallCaliberCasing = ITEMS.register("small_caliber_casing") { Item(PARTS_TAB_PROP) }
    val largeCaliberCasing = ITEMS.register("large_caliber_casing") { Item(PARTS_TAB_PROP) }
    val buckshotCasing = ITEMS.register("buckshot_casing") { Item(PARTS_TAB_PROP) }
    val ironBulletAssembly = ITEMS.register("iron_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val leadBulletAssembly = ITEMS.register("lead_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val glassBulletAssembly = ITEMS.register("glass_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val goldBulletAssembly = ITEMS.register("gold_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val schrabidiumBulletAssembly = ITEMS.register("schrabidium_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val nightmareBulletAssembly = ITEMS.register("nightmare_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val deshBulletAssembly = ITEMS.register("desh_bullet_assembly") { Item(PARTS_TAB_PROP) }
    val point44MagnumAssembly = ITEMS.register("point_44_magnum_assembly") { Item(PARTS_TAB_PROP) }
    val nineMmAssembly = ITEMS.register("9_mm_assembly") { Item(PARTS_TAB_PROP) }
    val fivePoint56mmAssembly = ITEMS.register("5_point_56_mm_assembly") { Item(PARTS_TAB_PROP) }
    val point22LRAssembly = ITEMS.register("point_22_lr_assembly") { Item(PARTS_TAB_PROP) }
    val point5mmAssembly = ITEMS.register("point_5_mm_assembly") { Item(PARTS_TAB_PROP) }
    val point50AEAssembly = ITEMS.register("point_50_ae_assembly") { Item(PARTS_TAB_PROP) }
    val point50BMGAssembly = ITEMS.register("point_50_bmg_assembly") { Item(PARTS_TAB_PROP) }
    val silverBulletCasing = ITEMS.register("silver_bullet_casing") { Item(PARTS_TAB_PROP) }
    val twelvePoint8cmStarmetalHighEnergyShell = ITEMS.register("12_point_8_cm_starmetal_high_energy_shell") { Item(PARTS_TAB_PROP) }
    val twelvePoint8cmNuclearShell = ITEMS.register("12_point_8_cm_nuclear_shell") { Item(PARTS_TAB_PROP) }
    val twelvePoint8cmDUShell = ITEMS.register("12_point_8_cm_du_shell") { Item(PARTS_TAB_PROP) }
    val cableDrum = ITEMS.register("cable_drum") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val paintingOfACartoonPony = ITEMS.register("painting_of_a_cartoon_pony") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val conspiracyTheory = ITEMS.register("conspiracy_theory") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val politicalTopic = ITEMS.register("political_topic") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val ownOpinion = ITEMS.register("own_opinion") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val explosivePellets = ITEMS.register("explosive_pellets") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val leadPellets = ITEMS.register("lead_pellets") { Item(PARTS_TAB_PROP) }
    val flechettes = ITEMS.register("flechettes") { Item(PARTS_TAB_PROP) }
    val poisonGasCartridge = ITEMS.register("poison_gas_cartridge") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val magnetron = ITEMS.register("magnetron") { Item(PARTS_TAB_PROP) }
    val denseCoalCluster = ITEMS.register("dense_coal_cluster") { Item(PARTS_TAB_PROP) }
    val burntBark = ITEMS.register("burnt_bark") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val machineUpgradeTemplate = ITEMS.register("machine_upgrade_template") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val blankRune = ITEMS.register("blank_rune") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val isaRune = ITEMS.register("isa_rune") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val dagazRune = ITEMS.register("dagaz_rune") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val hagalazRune = ITEMS.register("hagalaz_rune") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val jeraRune = ITEMS.register("jera_rune") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val thurisazRune = ITEMS.register("thurisaz_rune") { Item(PARTS_TAB_PROP.copy(stacksTo = 1)) }
    val burnedOutQuadSchrabidiumFuelRod = ITEMS.register("burned_out_quad_schrabidium_rod") { AutoTooltippedItem(PARTS_TAB_PROP_EPIC.copy(stacksTo = 1)) }
    val scrap: RegistryObject<Item> = ITEMS.register("scrap") { object : Item(PARTS_TAB_PROP) {
        override fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?) = 800
    }}
    val hotDepletedUraniumFuel = ITEMS.register("hot_depleted_uranium_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedThoriumFuel = ITEMS.register("hot_depleted_thorium_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedPlutoniumFuel = ITEMS.register("hot_depleted_plutonium_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedMOXFuel = ITEMS.register("hot_depleted_mox_fuel") { Item(PARTS_TAB_PROP) }
    val hotDepletedSchrabidiumFuel = ITEMS.register("hot_depleted_schrabidium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedUraniumFuel = ITEMS.register("depleted_uranium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedThoriumFuel = ITEMS.register("depleted_thorium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedPlutoniumFuel = ITEMS.register("depleted_plutonium_fuel") { Item(PARTS_TAB_PROP) }
    val depletedMOXFuel = ITEMS.register("depleted_mox_fuel") { Item(PARTS_TAB_PROP) }
    val depletedSchrabidiumFuel = ITEMS.register("depleted_schrabidium_fuel") { Item(PARTS_TAB_PROP) }
    val trinitite = ITEMS.register("trinitite") { Item(PARTS_TAB_PROP) }
    val nuclearWaste = ITEMS.register("nuclear_waste") { Item(PARTS_TAB_PROP) }
    val tinyNuclearWaste = ITEMS.register("tiny_nuclear_waste") { Item(PARTS_TAB_PROP) }
    val crystalHorn = ITEMS.register("crystal_horn") { AutoTooltippedItem(PARTS_TAB_PROP) }
    val charredCrystal = ITEMS.register("charred_crystal") { AutoTooltippedItem(PARTS_TAB_PROP) }

    // Items and Fuel

    val battery = ITEMS.register("battery") { BatteryItem(20_000, 400, 400, ITEMS_TAB_PROP) }
    val redstonePowerCell = ITEMS.register("redstone_power_cell") { BatteryItem(60_000, 400, 400, ITEMS_TAB_PROP) }
    val sixfoldRedstonePowerCell = ITEMS.register("sixfold_redstone_power_cell") { BatteryItem(360_000, 400, 400, ITEMS_TAB_PROP) }
    val twentyFourFoldRedstonePowerCell = ITEMS.register("twenty_four_fold_redstone_power_cell") { BatteryItem(1_440_000, 400, 400, ITEMS_TAB_PROP) }
    val advancedBattery = ITEMS.register("advanced_battery") { BatteryItem(80_000, 2_000, 2_000, ITEMS_TAB_PROP) }
    val advancedPowerCell = ITEMS.register("advanced_power_cell") { BatteryItem(240_000, 2_000, 2_000, ITEMS_TAB_PROP) }
    val quadrupleAdvancedPowerCell = ITEMS.register("quadruple_advanced_power_cell") { BatteryItem(960_000, 2_000, 2_000, ITEMS_TAB_PROP) }
    val twelveFoldAdvancedPowerCell = ITEMS.register("twelvefold_advanced_power_cell") { BatteryItem(2_880_000, 2_000, 2_000, ITEMS_TAB_PROP) }
    val lithiumBattery = ITEMS.register("lithium_battery") { BatteryItem(1_000_000, 4_000, 4_000, ITEMS_TAB_PROP) }
    val lithiumPowerCell = ITEMS.register("lithium_power_cell") { BatteryItem(3_000_000, 4_000, 4_000, ITEMS_TAB_PROP) }
    val tripleLithiumPowerCell = ITEMS.register("triple_lithium_power_cell") { BatteryItem(9_000_000, 4_000, 4_000, ITEMS_TAB_PROP) }
    val sixfoldLithiumPowerCell = ITEMS.register("sixfold_lithium_power_cell") { BatteryItem(18_000_000, 4_000, 4_000, ITEMS_TAB_PROP) }
    val schrabidiumBattery = ITEMS.register("schrabidium_battery") { BatteryItem(4_000_000, 20_000, 20_000, ITEMS_TAB_PROP_RARE) }
    val schrabidiumPowerCell = ITEMS.register("schrabidium_power_cell") { BatteryItem(12_000_000, 20_000, 20_000, ITEMS_TAB_PROP_RARE) }
    val doubleSchrabidiumPowerCell = ITEMS.register("double_schrabidium_power_cell") { BatteryItem(24_000_000, 20_000, 20_000, ITEMS_TAB_PROP_RARE) }
    val quadrupleSchrabidiumPowerCell = ITEMS.register("quadruple_schrabidium_power_cell") { BatteryItem(48_000_000, 20_000, 20_000, ITEMS_TAB_PROP_RARE) }
    val sparkBattery = ITEMS.register("spark_battery") { BatteryItem(400_000_000, 8_000_000, 8_000_000, ITEMS_TAB_PROP) }
    val offBrandSparkBattery = ITEMS.register("off_brand_spark_battery") { BatteryItem(20_000_000, 160_000, 800_000, ITEMS_TAB_PROP) }
    val sparkPowerCell = ITEMS.register("spark_power_cell") { BatteryItem(2_400_000_000L, 8_000_000, 8_000_000, ITEMS_TAB_PROP) }
    val sparkArcaneCarBattery = ITEMS.register("spark_arcane_car_battery") { BatteryItem(10_000_000_000L, 8_000_000, 8_000_000, ITEMS_TAB_PROP) }
    val sparkArcaneEnergyStorageArray = ITEMS.register("spark_arcane_energy_storage_array") { BatteryItem(40_000_000_000L, 8_000_000, 8_000_000, ITEMS_TAB_PROP) }
    val sparkArcaneMassEnergyVoid = ITEMS.register("spark_arcane_mass_energy_void") { BatteryItem(400_000_000_000L, 80_000_000, 80_000_000, ITEMS_TAB_PROP) }
    val sparkArcaneDiracSea = ITEMS.register("spark_arcane_dirac_sea") { BatteryItem(1_000_000_000_000L, 80_000_000, 80_000_000, ITEMS_TAB_PROP) }
    val sparkSolidSpaceTimeCrystal = ITEMS.register("spark_solid_space_time_crystal") { BatteryItem(4_000_000_000_000L, 800_000_000, 800_000_000, ITEMS_TAB_PROP) }
    val sparkLudicrousPhysicsDefyingEnergyStorageUnit = ITEMS.register("spark_ludicrous_physics_defying_energy_storage_unit") { BatteryItem(400_000_000_000_000L, 800_000_000, 800_000_000, ITEMS_TAB_PROP) }
    // TODO electronium cube
    val infiniteBattery = ITEMS.register("infinite_battery") { BatteryOfInfinityItem(ITEMS_TAB_PROP) }
    val singleUseBattery = ITEMS.register("single_use_battery") { BatteryItem(6_000, 0, 400, ITEMS_TAB_PROP) }
    val largeSingleUseBattery = ITEMS.register("large_single_use_battery") { BatteryItem(14_000, 0, 400, ITEMS_TAB_PROP) }
    val potatoBattery = ITEMS.register("potato_battery") { BatteryItem(400, 0, 400, ITEMS_TAB_PROP) }
    // TODO PotatOS
    val steamPoweredEnergyStorageTank = ITEMS.register("steam_powered_energy_storage_tank") { BatteryItem(240_000, 1_200, 24_000, ITEMS_TAB_PROP) }
    val largeSteamPoweredEnergyStorageTank = ITEMS.register("large_steam_powered_energy_storage_tank") { BatteryItem(400_000, 2_000, 40_000, ITEMS_TAB_PROP) }

    val stoneFlatStamp = ITEMS.register("stone_flat_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 10)) }
    val stonePlateStamp = ITEMS.register("stone_plate_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 10)) }
    val stoneWireStamp = ITEMS.register("stone_wire_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 10)) }
    val stoneCircuitStamp = ITEMS.register("stone_circuit_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 10)) }
    val ironFlatStamp = ITEMS.register("iron_flat_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 50)) }
    val ironPlateStamp = ITEMS.register("iron_plate_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 50)) }
    val ironWireStamp = ITEMS.register("iron_wire_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 50)) }
    val ironCircuitStamp = ITEMS.register("iron_circuit_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 50)) }
    val steelFlatStamp = ITEMS.register("steel_flat_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 100)) }
    val steelPlateStamp = ITEMS.register("steel_plate_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 100)) }
    val steelWireStamp = ITEMS.register("steel_wire_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 100)) }
    val steelCircuitStamp = ITEMS.register("steel_circuit_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 100)) }
    val titaniumFlatStamp = ITEMS.register("titanium_flat_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 150)) }
    val titaniumPlateStamp = ITEMS.register("titanium_plate_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 150)) }
    val titaniumWireStamp = ITEMS.register("titanium_wire_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 150)) }
    val titaniumCircuitStamp = ITEMS.register("titanium_circuit_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 150)) }
    val obsidianFlatStamp = ITEMS.register("obsidian_flat_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 170)) }
    val obsidianPlateStamp = ITEMS.register("obsidian_plate_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 170)) }
    val obsidianWireStamp = ITEMS.register("obsidian_wire_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 170)) }
    val obsidianCircuitStamp = ITEMS.register("obsidian_circuit_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 170)) }
    val schrabidiumFlatStamp = ITEMS.register("schrabidium_flat_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 3000)) }
    val schrabidiumPlateStamp = ITEMS.register("schrabidium_plate_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 3000)) }
    val schrabidiumWireStamp = ITEMS.register("schrabidium_wire_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 3000)) }
    val schrabidiumCircuitStamp = ITEMS.register("schrabidium_circuit_stamp") { Item(ITEMS_TAB_PROP.copy(maxDamage = 3000)) }

    val screwdriver = ITEMS.register("screwdriver") { ScrewdriverItem(ITEMS_TAB_PROP.copy(maxDamage = 100)) }
    val deshScrewdriver = ITEMS.register("desh_screwdriver") { ScrewdriverItem(ITEMS_TAB_PROP.copy(stacksTo = 1)) }

}
