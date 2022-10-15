package at.martinthedragon.nucleartech.datagen.tag

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.item.NTechItems
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class ItemTagProvider(
    generator: DataGenerator,
    blockTagProvider: BlockTagProvider,
    existingFileHelper: ExistingFileHelper
) : ItemTagsProvider(generator, blockTagProvider, NuclearTech.MODID, existingFileHelper) {

    override fun getName(): String = "Nuclear Tech Mod Item Tags"

    override fun addTags() {
        copyFromBlocks()
        rawMaterialTags()
        ingotTags()
        dustTags()
        crystalTags()
        nuggetTags()
        plateTags()
        wireTags()
        coilTags()
        fissileFuelTags()
        wasteTags()
        miscTags()
        templateFolders()
        modifyVanillaTags()
    }

    private fun copyFromBlocks() = with(NTechTags.Items) {
        copy(NTechTags.Blocks.ORES_ALUMINIUM, ORES_ALUMINIUM)
        copy(NTechTags.Blocks.ORES_ASBESTOS, ORES_ASBESTOS)
        copy(NTechTags.Blocks.ORES_AUSTRALIUM, ORES_AUSTRALIUM)
        copy(NTechTags.Blocks.ORES_BERYLLIUM, ORES_BERYLLIUM)
        copy(NTechTags.Blocks.ORES_COBALT, ORES_COBALT)
        copy(NTechTags.Blocks.ORES_DAFFERGON, ORES_DAFFERGON)
        copy(NTechTags.Blocks.ORES_FLUORITE, ORES_FLUORITE)
        copy(NTechTags.Blocks.ORES_LEAD, ORES_LEAD)
        copy(NTechTags.Blocks.ORES_LIGNITE, ORES_LIGNITE)
        copy(NTechTags.Blocks.ORES_NITER, ORES_NITER)
        copy(NTechTags.Blocks.ORES_OIL, ORES_OIL)
        copy(NTechTags.Blocks.ORES_PHOSPHORUS, ORES_PHOSPHORUS)
        copy(NTechTags.Blocks.ORES_PLUTONIUM, ORES_PLUTONIUM)
        copy(NTechTags.Blocks.ORES_RARE_EARTH, ORES_RARE_EARTH)
        copy(NTechTags.Blocks.ORES_REIIUM, ORES_REIIUM)
        copy(NTechTags.Blocks.ORES_SCHRABIDIUM, ORES_SCHRABIDIUM)
        copy(NTechTags.Blocks.ORES_STARMETAL, ORES_STARMETAL)
        copy(NTechTags.Blocks.ORES_SULFUR, ORES_SULFUR)
        copy(NTechTags.Blocks.ORES_THORIUM, ORES_THORIUM)
        copy(NTechTags.Blocks.ORES_TITANIUM, ORES_TITANIUM)
        copy(NTechTags.Blocks.ORES_TRINITITE, ORES_TRINITITE)
        copy(NTechTags.Blocks.ORES_TRIXITE, ORES_TRIXITE)
        copy(NTechTags.Blocks.ORES_TUNGSTEN, ORES_TUNGSTEN)
        copy(NTechTags.Blocks.ORES_UNOBTAINIUM, ORES_UNOBTAINIUM)
        copy(NTechTags.Blocks.ORES_URANIUM, ORES_URANIUM)
        copy(NTechTags.Blocks.ORES_VERTICIUM, ORES_VERTICIUM)
        copy(NTechTags.Blocks.ORES_WEIDANIUM, ORES_WEIDANIUM)
        copy(Tags.Blocks.ORES, Tags.Items.ORES)
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE)
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE)
        copy(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK)
        copy(NTechTags.Blocks.ORES_IN_GROUND_END_STONE, ORES_IN_GROUND_END_STONE)

        copy(NTechTags.Blocks.STORAGE_BLOCKS_ALUMINIUM, STORAGE_BLOCKS_ALUMINIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_ASBESTOS, STORAGE_BLOCKS_ASBESTOS)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_AUSTRALIUM, STORAGE_BLOCKS_AUSTRALIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_BERYLLIUM, STORAGE_BLOCKS_BERYLLIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_COBALT, STORAGE_BLOCKS_COBALT)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_COMBINE_STEEL, STORAGE_BLOCKS_COMBINE_STEEL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_DAFFERGON, STORAGE_BLOCKS_DAFFERGON)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_DESH, STORAGE_BLOCKS_DESH)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_ELECTRICAL_SCRAP, STORAGE_BLOCKS_ELECTRICAL_SCRAP)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_EUPHEMIUM, STORAGE_BLOCKS_EUPHEMIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_FIBERGLASS, STORAGE_BLOCKS_FIBERGLASS)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_FLUORITE, STORAGE_BLOCKS_FLUORITE)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_HAZMAT, STORAGE_BLOCKS_HAZMAT)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_INSULATOR, STORAGE_BLOCKS_INSULATOR)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_LEAD, STORAGE_BLOCKS_LEAD)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_LITHIUM, STORAGE_BLOCKS_LITHIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN, STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_MOX, STORAGE_BLOCKS_MOX)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM, STORAGE_BLOCKS_NEPTUNIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_NITER, STORAGE_BLOCKS_NITER)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_NUCLEAR_WASTE, STORAGE_BLOCKS_NUCLEAR_WASTE)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS, STORAGE_BLOCKS_PHOSPHORUS)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_PLUTONIUM, STORAGE_BLOCKS_PLUTONIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_PLUTONIUM_FUEL, STORAGE_BLOCKS_PLUTONIUM_FUEL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_RED_COPPER, STORAGE_BLOCKS_RED_COPPER)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_REIIUM, STORAGE_BLOCKS_REIIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_SCHRABIDIUM, STORAGE_BLOCKS_SCHRABIDIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_SCHRABIDIUM_FUEL, STORAGE_BLOCKS_SCHRABIDIUM_FUEL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_SCRAP, STORAGE_BLOCKS_SCRAP)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_SOLINIUM, STORAGE_BLOCKS_SOLINIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_STARMETAL, STORAGE_BLOCKS_STARMETAL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_STEEL, STORAGE_BLOCKS_STEEL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_SULFUR, STORAGE_BLOCKS_SULFUR)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_THORIUM, STORAGE_BLOCKS_THORIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_THORIUM_FUEL, STORAGE_BLOCKS_THORIUM_FUEL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_TITANIUM, STORAGE_BLOCKS_TITANIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_TRINITITE, STORAGE_BLOCKS_TRINITITE)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_TUNGSTEN, STORAGE_BLOCKS_TUNGSTEN)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_UNOBTAINIUM, STORAGE_BLOCKS_UNOBTAINIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_URANIUM, STORAGE_BLOCKS_URANIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_URANIUM_FUEL, STORAGE_BLOCKS_URANIUM_FUEL)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_VERTICIUM, STORAGE_BLOCKS_VERTICIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_WEIDANIUM, STORAGE_BLOCKS_WEIDANIUM)
        copy(NTechTags.Blocks.STORAGE_BLOCKS_YELLOWCAKE, STORAGE_BLOCKS_YELLOWCAKE)
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS)

        copy(NTechTags.Blocks.SAND_OIL, SAND_OIL)
        copy(Tags.Blocks.SAND, Tags.Items.SAND)
        copy(NTechTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK, GLOWING_MUSHROOM_GROW_BLOCK)
        copy(NTechTags.Blocks.ANVIL, ANVIL)
        return@with
    }

    private fun rawMaterialTags() = with(NTechTags.Items) {
        tag(Tags.Items.RAW_MATERIALS).addTags(RAW_MATERIALS_ALUMINIUM, RAW_MATERIALS_AUSTRALIUM, RAW_MATERIALS_BERYLLIUM, RAW_MATERIALS_COBALT, RAW_MATERIALS_LEAD, RAW_MATERIALS_LITHIUM, RAW_MATERIALS_PLUTONIUM, RAW_MATERIALS_RARE_EARTH, RAW_MATERIALS_SCHRABIDIUM, RAW_MATERIALS_STARMETAL, RAW_MATERIALS_THORIUM, RAW_MATERIALS_TITANIUM, RAW_MATERIALS_TRIXITE, RAW_MATERIALS_TUNGSTEN, RAW_MATERIALS_URANIUM)
        tag(RAW_MATERIALS_ALUMINIUM).add(NTechItems.rawAluminium)
        tag(RAW_MATERIALS_AUSTRALIUM).add(NTechItems.rawAustralium)
        tag(RAW_MATERIALS_BERYLLIUM).add(NTechItems.rawBeryllium)
        tag(RAW_MATERIALS_COBALT).add(NTechItems.rawCobalt)
        tag(RAW_MATERIALS_LEAD).add(NTechItems.rawLead)
        tag(RAW_MATERIALS_LITHIUM).add(NTechItems.rawLithium)
        tag(RAW_MATERIALS_PLUTONIUM).add(NTechItems.rawPlutonium)
        tag(RAW_MATERIALS_RARE_EARTH).add(NTechItems.rawRareEarth)
        tag(RAW_MATERIALS_SCHRABIDIUM).add(NTechItems.rawSchrabidium)
        tag(RAW_MATERIALS_STARMETAL).add(NTechItems.rawStarmetal)
        tag(RAW_MATERIALS_THORIUM).add(NTechItems.rawThorium)
        tag(RAW_MATERIALS_TITANIUM).add(NTechItems.rawTitanium)
        tag(RAW_MATERIALS_TRIXITE).add(NTechItems.rawTrixite)
        tag(RAW_MATERIALS_TUNGSTEN).add(NTechItems.rawTungsten)
        tag(RAW_MATERIALS_URANIUM).add(NTechItems.rawUranium)
        return@with
    }

    private fun ingotTags() = with(NTechTags.Items) {
        tag(Tags.Items.INGOTS)
            .addTags(INGOTS_ACTINIUM, INGOTS_ALUMINIUM, INGOTS_AMERICIUM, INGOTS_ASBESTOS, INGOTS_AUSTRALIUM, INGOTS_BERYLLIUM, INGOTS_BISMUTH, INGOTS_COBALT, INGOTS_COMBINE_STEEL, INGOTS_DAFFERGON, INGOTS_DESH, INGOTS_DINEUTRONIUM, INGOTS_ELECTRONIUM, INGOTS_EUPHEMIUM, INGOTS_FIBERGLASS, INGOTS_HIGH_SPEED_STEEL, INGOTS_LANTHANUM, INGOTS_LEAD, INGOTS_LITHIUM, INGOTS_MAGNETIZED_TUNGSTEN, INGOTS_MOX, INGOTS_NEPTUNIUM, INGOTS_PHOSPHORUS, INGOTS_PLASTIC, INGOTS_PLUTONIUM, INGOTS_PLUTONIUM_FUEL, INGOTS_POLONIUM, INGOTS_POLYMER, INGOTS_RED_COPPER, INGOTS_REIIUM, INGOTS_SATURNITE, INGOTS_SCHRABIDATE, INGOTS_SCHRABIDIUM, INGOTS_SCHRABIDIUM_FUEL, INGOTS_SCHRARANIUM, INGOTS_SEMTEX, INGOTS_SOLINIUM, INGOTS_STARMETAL, INGOTS_STEEL, INGOTS_TEFLON, INGOTS_THORIUM, INGOTS_THORIUM_FUEL, INGOTS_TITANIUM, INGOTS_TUNGSTEN, INGOTS_UNOBTAINIUM, INGOTS_URANIUM, INGOTS_URANIUM_FUEL, INGOTS_VERTICIUM, INGOTS_WEIDANIUM)
            .add(NTechItems.advancedAlloyIngot.get(), NTechItems.highEnrichedSchrabidiumFuelIngot.get(), NTechItems.lowEnrichedSchrabidiumFuelIngot.get())
        tag(INGOTS_ACTINIUM).add(NTechItems.actiniumIngot)
        tag(INGOTS_ALUMINIUM).add(NTechItems.aluminiumIngot)
        tag(INGOTS_AMERICIUM).add(NTechItems.americium241Ingot, NTechItems.americium242Ingot)
        tag(INGOTS_ASBESTOS).add(NTechItems.asbestosSheet)
        tag(INGOTS_AUSTRALIUM).add(NTechItems.australiumIngot)
        tag(INGOTS_BERYLLIUM).add(NTechItems.berylliumIngot)
        tag(INGOTS_BISMUTH).add(NTechItems.bismuthIngot)
        tag(INGOTS_COBALT).add(NTechItems.cobaltIngot)
        tag(INGOTS_COMBINE_STEEL).add(NTechItems.combineSteelIngot)
        tag(INGOTS_DAFFERGON).add(NTechItems.daffergonIngot)
        tag(INGOTS_DESH).add(NTechItems.deshIngot)
        tag(INGOTS_DINEUTRONIUM).add(NTechItems.dineutroniumIngot)
        tag(INGOTS_ELECTRONIUM).add(NTechItems.electroniumIngot)
        tag(INGOTS_EUPHEMIUM).add(NTechItems.euphemiumIngot)
        tag(INGOTS_FIBERGLASS).add(NTechItems.fiberglassSheet)
        tag(INGOTS_HIGH_SPEED_STEEL).add(NTechItems.highSpeedSteelIngot)
        tag(INGOTS_LANTHANUM).add(NTechItems.lanthanumIngot)
        tag(INGOTS_LEAD).add(NTechItems.leadIngot)
        tag(INGOTS_LITHIUM).add(NTechItems.lithiumCube)
        tag(INGOTS_MAGNETIZED_TUNGSTEN).add(NTechItems.magnetizedTungstenIngot)
        tag(INGOTS_MOX).add(NTechItems.moxFuelIngot)
        tag(INGOTS_NEPTUNIUM).add(NTechItems.neptuniumIngot)
        tag(INGOTS_PHOSPHORUS).add(NTechItems.whitePhosphorusIngot)
        tag(INGOTS_PLASTIC).add(NTechItems.bakeliteIngot).addTag(INGOTS_TEFLON)
        tag(INGOTS_PLUTONIUM).add(NTechItems.plutoniumIngot)
        tag(INGOTS_PLUTONIUM_FUEL).add(NTechItems.plutoniumFuelIngot)
        tag(INGOTS_POLONIUM).add(NTechItems.polonium210Ingot)
        tag(INGOTS_POLYMER).addTag(INGOTS_TEFLON).addTag(INGOTS_PLASTIC)
        tag(INGOTS_RED_COPPER).add(NTechItems.redCopperIngot)
        tag(INGOTS_REIIUM).add(NTechItems.reiiumIngot)
        tag(INGOTS_SATURNITE).add(NTechItems.saturniteIngot)
        tag(INGOTS_SCHRABIDATE).add(NTechItems.ferricSchrabidateIngot)
        tag(INGOTS_SCHRABIDIUM).add(NTechItems.schrabidiumIngot)
        tag(INGOTS_SCHRABIDIUM_FUEL).add(NTechItems.schrabidiumFuelIngot)
        tag(INGOTS_SCHRARANIUM).add(NTechItems.schraraniumIngot)
        tag(INGOTS_SEMTEX).add(NTechItems.semtexBar)
        tag(INGOTS_SOLINIUM).add(NTechItems.soliniumIngot)
        tag(INGOTS_STARMETAL).add(NTechItems.starmetalIngot)
        tag(INGOTS_STEEL).add(NTechItems.steelIngot)
        tag(INGOTS_TEFLON).add(NTechItems.polymerIngot)
        tag(INGOTS_THORIUM).add(NTechItems.th232Ingot)
        tag(INGOTS_THORIUM_FUEL).add(NTechItems.thoriumFuelIngot)
        tag(INGOTS_TITANIUM).add(NTechItems.titaniumIngot)
        tag(INGOTS_TUNGSTEN).add(NTechItems.tungstenIngot)
        tag(INGOTS_UNOBTAINIUM).add(NTechItems.unobtainiumIngot)
        tag(INGOTS_URANIUM).add(NTechItems.uraniumIngot)
        tag(INGOTS_URANIUM_FUEL).add(NTechItems.uraniumFuelIngot)
        tag(INGOTS_VERTICIUM).add(NTechItems.verticiumIngot)
        tag(INGOTS_WEIDANIUM).add(NTechItems.weidaniumIngot)
        return@with
    }

    private fun dustTags() = with(NTechTags.Items) {
        tag(Tags.Items.DUSTS)
            .addTags(DUSTS_ACTINIUM, DUSTS_ALUMINIUM, DUSTS_ASBESTOS, DUSTS_AUSTRALIUM, DUSTS_BALLISTITE, DUSTS_BERYLLIUM, DUSTS_CERIUM, DUSTS_CHLOROPHYTE, DUSTS_CLOUD, DUSTS_COAL, DUSTS_COBALT, DUSTS_COLD, DUSTS_COMBINE_STEEL, DUSTS_COPPER, DUSTS_CORDITE, DUSTS_DAFFERGON, DUSTS_DESH, DUSTS_DIAMOND, DUSTS_DINEUTRONIUM, DUSTS_DUST, DUSTS_EMERALD, DUSTS_ENCHANTMENT, DUSTS_ENERGY, DUSTS_EUPHEMIUM, DUSTS_FLUORITE, DUSTS_GOLD, DUSTS_IRON, DUSTS_LANTHANUM, DUSTS_LAPIS, DUSTS_LEAD, DUSTS_LIGNITE, DUSTS_LITHIUM, DUSTS_MAGNETIZED_TUNGSTEN, DUSTS_METEORITE, DUSTS_NEODYMIUM, DUSTS_NEPTUNIUM, DUSTS_NIOBIUM, DUSTS_NITER, DUSTS_PHOSPHORUS, DUSTS_PLUTONIUM, DUSTS_POISON, DUSTS_POLONIUM, DUSTS_POLYMER, DUSTS_QUARTZ, DUSTS_RED_COPPER, DUSTS_REIIUM, DUSTS_SCHRABIDIUM, DUSTS_SEMTEX, DUSTS_STEEL, DUSTS_SULFUR, DUSTS_TEFLON, DUSTS_THERMITE, DUSTS_THERMONUCLEAR_ASHES, DUSTS_THORIUM, DUSTS_TITANIUM, DUSTS_TUNGSTEN, DUSTS_UNOBTAINIUM, DUSTS_URANIUM, DUSTS_VERTICIUM, DUSTS_WEIDANIUM)
            .add(NTechItems.advancedAlloyPowder, NTechItems.deshMix, NTechItems.deshReadyMix, NTechItems.nitaniumMix, NTechItems.dust, NTechItems.sparkMix, NTechItems.desaturatedRedstone)
        tag(DUSTS_ACTINIUM).add(NTechItems.actiniumPowder)
        tag(DUSTS_ALUMINIUM).add(NTechItems.aluminiumPowder)
        tag(DUSTS_ASBESTOS).add(NTechItems.asbestosPowder)
        tag(DUSTS_AUSTRALIUM).add(NTechItems.australiumPowder)
        tag(DUSTS_BALLISTITE).add(NTechItems.ballistite)
        tag(DUSTS_BERYLLIUM).add(NTechItems.berylliumPowder)
        tag(DUSTS_CERIUM).add(NTechItems.ceriumPowder)
        tag(DUSTS_CHLOROPHYTE).add(NTechItems.chlorophytePowder)
        tag(DUSTS_CLOUD).add(NTechItems.cloudResidue)
        tag(DUSTS_COAL).add(NTechItems.coalPowder)
        tag(DUSTS_COBALT).add(NTechItems.cobaltPowder)
        tag(DUSTS_COLD).add(NTechItems.cryoPowder)
        tag(DUSTS_COMBINE_STEEL).add(NTechItems.combineSteelPowder)
        tag(DUSTS_COPPER).add(NTechItems.copperPowder)
        tag(DUSTS_CORDITE).add(NTechItems.cordite)
        tag(DUSTS_DAFFERGON).add(NTechItems.daffergonPowder)
        tag(DUSTS_DESH).add(NTechItems.deshPowder)
        tag(DUSTS_DIAMOND).add(NTechItems.diamondPowder)
        tag(DUSTS_DINEUTRONIUM).add(NTechItems.dineutroniumPowder)
        tag(DUSTS_DUST).add(NTechItems.dust)
        tag(DUSTS_EMERALD).add(NTechItems.emeraldPowder)
        tag(DUSTS_ENCHANTMENT).add(NTechItems.enchantmentPowder)
        tag(DUSTS_ENERGY).add(NTechItems.energyPowder)
        tag(DUSTS_EUPHEMIUM).add(NTechItems.euphemiumPowder)
        tag(DUSTS_FLUORITE).add(NTechItems.fluorite)
        tag(DUSTS_GOLD).add(NTechItems.goldPowder)
        tag(DUSTS_IRON).add(NTechItems.ironPowder)
        tag(DUSTS_LANTHANUM).add(NTechItems.lanthanumPowder)
        tag(DUSTS_LAPIS).add(NTechItems.lapisLazuliPowder)
        tag(DUSTS_LEAD).add(NTechItems.leadPowder)
        tag(DUSTS_LIGNITE).add(NTechItems.lignitePowder)
        tag(DUSTS_LITHIUM).add(NTechItems.lithiumPowder)
        tag(DUSTS_MAGNETIZED_TUNGSTEN).add(NTechItems.magnetizedTungstenPowder)
        tag(DUSTS_METEORITE).add(NTechItems.meteoritePowder)
        tag(DUSTS_NEODYMIUM).add(NTechItems.neodymiumPowder)
        tag(DUSTS_NEPTUNIUM).add(NTechItems.neptuniumPowder)
        tag(DUSTS_NIOBIUM).add(NTechItems.niobiumPowder)
        tag(DUSTS_NITER).add(NTechItems.niter)
        tag(DUSTS_PHOSPHORUS).add(NTechItems.redPhosphorus)
        tag(DUSTS_PLUTONIUM).add(NTechItems.plutoniumPowder)
        tag(DUSTS_POISON).add(NTechItems.poisonPowder)
        tag(DUSTS_POLONIUM).add(NTechItems.poloniumPowder)
        tag(DUSTS_POLYMER).addTag(DUSTS_TEFLON)
        tag(DUSTS_QUARTZ).add(NTechItems.quartzPowder)
        tag(DUSTS_RED_COPPER).add(NTechItems.redCopperPowder)
        tag(DUSTS_REIIUM).add(NTechItems.reiiumPowder)
        tag(DUSTS_SCHRABIDIUM).add(NTechItems.schrabidiumPowder)
        tag(DUSTS_SEMTEX).add(NTechItems.semtexMix)
        tag(DUSTS_STEEL).add(NTechItems.steelIngot)
        tag(DUSTS_SULFUR).add(NTechItems.sulfur)
        tag(DUSTS_TEFLON).add(NTechItems.polymerPowder)
        tag(DUSTS_THERMITE).add(NTechItems.thermite)
        tag(DUSTS_THERMONUCLEAR_ASHES).add(NTechItems.thermonuclearAshes)
        tag(DUSTS_THORIUM).add(NTechItems.thoriumPowder)
        tag(DUSTS_TITANIUM).add(NTechItems.titaniumPowder)
        tag(DUSTS_TUNGSTEN).add(NTechItems.tungstenPowder)
        tag(DUSTS_UNOBTAINIUM).add(NTechItems.unobtainiumPowder)
        tag(DUSTS_URANIUM).add(NTechItems.uraniumPowder)
        tag(DUSTS_VERTICIUM).add(NTechItems.verticiumPowder)
        tag(DUSTS_WEIDANIUM).add(NTechItems.weidaniumPowder)
        return@with
    }

    private fun crystalTags() = with(NTechTags.Items) {
        tag(CRYSTALS).addTags(CRYSTALS_ALUMINIUM, CRYSTALS_BERYLLIUM, CRYSTALS_COAL, CRYSTALS_COPPER, CRYSTALS_DIAMOND, CRYSTALS_FLUORITE, CRYSTALS_GOLD, CRYSTALS_IRON, CRYSTALS_LEAD, CRYSTALS_LITHIUM, CRYSTALS_NITER, CRYSTALS_PHOSPHORUS, CRYSTALS_PLUTONIUM, CRYSTALS_RARE_EARTH, CRYSTALS_REDSTONE, CRYSTALS_SCHRABIDIUM, CRYSTALS_SCHRARANIUM, CRYSTALS_STARMETAL, CRYSTALS_SULFUR, CRYSTALS_THORIUM, CRYSTALS_TITANIUM, CRYSTALS_TRIXITE, CRYSTALS_TUNGSTEN, CRYSTALS_URANIUM)
        tag(CRYSTALS_ALUMINIUM).add(NTechItems.aluminiumCrystals)
        tag(CRYSTALS_BERYLLIUM).add(NTechItems.berylliumCrystals)
        tag(CRYSTALS_COAL).add(NTechItems.coalCrystals)
        tag(CRYSTALS_COPPER).add(NTechItems.copperCrystals)
        tag(CRYSTALS_DIAMOND).add(NTechItems.diamondCrystals)
        tag(CRYSTALS_FLUORITE).add(NTechItems.fluoriteCrystals)
        tag(CRYSTALS_GOLD).add(NTechItems.goldCrystals)
        tag(CRYSTALS_IRON).add(NTechItems.ironCrystals)
        tag(CRYSTALS_LEAD).add(NTechItems.leadCrystals)
        tag(CRYSTALS_LITHIUM).add(NTechItems.lithiumCrystals)
        tag(CRYSTALS_NITER).add(NTechItems.niterCrystals)
        tag(CRYSTALS_PHOSPHORUS).add(NTechItems.redPhosphorusCrystals)
        tag(CRYSTALS_PLUTONIUM).add(NTechItems.plutoniumCrystals)
        tag(CRYSTALS_RARE_EARTH).add(NTechItems.rareEarthCrystals)
        tag(CRYSTALS_REDSTONE).add(NTechItems.redstoneCrystals)
        tag(CRYSTALS_SCHRABIDIUM).add(NTechItems.schrabidiumCrystals)
        tag(CRYSTALS_SCHRARANIUM).add(NTechItems.schraraniumCrystals)
        tag(CRYSTALS_STARMETAL).add(NTechItems.starmetalCrystals)
        tag(CRYSTALS_SULFUR).add(NTechItems.sulfurCrystals)
        tag(CRYSTALS_THORIUM).add(NTechItems.thoriumCrystals)
        tag(CRYSTALS_TITANIUM).add(NTechItems.titaniumCrystals)
        tag(CRYSTALS_TRIXITE).add(NTechItems.trixiteCrystals)
        tag(CRYSTALS_TUNGSTEN).add(NTechItems.tungstenCrystals)
        tag(CRYSTALS_URANIUM).add(NTechItems.uraniumCrystals)

        tag(Tags.Items.GEMS).addTags(CRYSTALS, GEMS_ACTINIUM, GEMS_CERIUM, GEMS_COBALT, GEMS_LANTHANUM, GEMS_NEODYMIUM, GEMS_NIOBIUM)
        tag(GEMS_ACTINIUM).add(NTechItems.actiniumFragment)
        tag(GEMS_ALUMINIUM).addTag(CRYSTALS_ALUMINIUM)
        tag(GEMS_BERYLLIUM).addTag(CRYSTALS_BERYLLIUM)
        tag(GEMS_CERIUM).add(NTechItems.ceriumFragment)
        tag(GEMS_COAL).addTag(CRYSTALS_COAL)
        tag(GEMS_COBALT).add(NTechItems.cobaltFragment)
        tag(GEMS_COPPER).addTag(CRYSTALS_COPPER)
        tag(GEMS_DIAMOND).addTag(CRYSTALS_DIAMOND)
        tag(GEMS_FLUORITE).addTag(CRYSTALS_FLUORITE)
        tag(GEMS_GOLD).addTag(CRYSTALS_GOLD)
        tag(GEMS_IRON).addTag(CRYSTALS_IRON)
        tag(GEMS_LANTHANUM).add(NTechItems.lanthanumFragment)
        tag(GEMS_LEAD).addTag(CRYSTALS_LEAD)
        tag(GEMS_LITHIUM).addTag(CRYSTALS_LITHIUM)
        tag(GEMS_NEODYMIUM).add(NTechItems.neodymiumFragment)
        tag(GEMS_NIOBIUM).add(NTechItems.niobiumFragment)
        tag(GEMS_NITER).addTag(CRYSTALS_NITER)
        tag(GEMS_PHOSPHORUS).addTag(CRYSTALS_PHOSPHORUS)
        tag(GEMS_PLUTONIUM).addTag(CRYSTALS_PLUTONIUM)
        tag(GEMS_RARE_EARTH).addTag(CRYSTALS_RARE_EARTH)
        tag(GEMS_REDSTONE).addTag(CRYSTALS_REDSTONE)
        tag(GEMS_SCHRABIDIUM).addTag(CRYSTALS_SCHRABIDIUM)
        tag(GEMS_SCHRARANIUM).addTag(CRYSTALS_SCHRARANIUM)
        tag(GEMS_STARMETAL).addTag(CRYSTALS_STARMETAL)
        tag(GEMS_SULFUR).addTag(CRYSTALS_SULFUR)
        tag(GEMS_THORIUM).addTag(CRYSTALS_THORIUM)
        tag(GEMS_TITANIUM).addTag(CRYSTALS_TITANIUM)
        tag(GEMS_TRIXITE).addTag(CRYSTALS_TRIXITE)
        tag(GEMS_TUNGSTEN).addTag(CRYSTALS_TUNGSTEN)
        tag(GEMS_URANIUM).addTag(CRYSTALS_URANIUM)
        return@with
    }

    private fun nuggetTags() = with(NTechTags.Items) {
        tag(Tags.Items.NUGGETS)
            .addTags(NUGGETS_AUSTRALIUM, NUGGETS_BERYLLIUM, NUGGETS_BISMUTH, NUGGETS_DAFFERGON, NUGGETS_DESH, NUGGETS_DINEUTRONIUM, NUGGETS_EUPHEMIUM, NUGGETS_LEAD, NUGGETS_MERCURY, NUGGETS_MOX, NUGGETS_NEPTUNIUM, NUGGETS_PLUTONIUM, NUGGETS_PLUTONIUM_FUEL, NUGGETS_POLONIUM, NUGGETS_REIIUM, NUGGETS_SCHRABIDIUM, NUGGETS_SCHRABIDIUM_FUEL, NUGGETS_SOLINIUM, NUGGETS_THORIUM, NUGGETS_THORIUM_FUEL, NUGGETS_UNOBTAINIUM, NUGGETS_URANIUM, NUGGETS_URANIUM_FUEL, NUGGETS_VERTICIUM, NUGGETS_WEIDANIUM)
            .add(NTechItems.highEnrichedSchrabidiumFuelNugget, NTechItems.lowEnrichedSchrabidiumFuelNugget)
        tag(NUGGETS_AUSTRALIUM).add(NTechItems.australiumNugget)
        tag(NUGGETS_BERYLLIUM).add(NTechItems.berylliumNugget)
        tag(NUGGETS_BISMUTH).add(NTechItems.bismuthNugget)
        tag(NUGGETS_DAFFERGON).add(NTechItems.daffergonNugget)
        tag(NUGGETS_DESH).add(NTechItems.deshNugget)
        tag(NUGGETS_DINEUTRONIUM).add(NTechItems.dineutroniumNugget)
        tag(NUGGETS_EUPHEMIUM).add(NTechItems.euphemiumNugget)
        tag(NUGGETS_LEAD).add(NTechItems.leadNugget)
        tag(NUGGETS_MERCURY).add(NTechItems.mercuryDroplet)
        tag(NUGGETS_MOX).add(NTechItems.moxFuelNugget)
        tag(NUGGETS_NEPTUNIUM).add(NTechItems.neptuniumNugget)
        tag(NUGGETS_PLUTONIUM).add(NTechItems.plutoniumNugget)
        tag(NUGGETS_PLUTONIUM_FUEL).add(NTechItems.plutoniumFuelNugget)
        tag(NUGGETS_POLONIUM).add(NTechItems.poloniumNugget)
        tag(NUGGETS_REIIUM).add(NTechItems.reiiumNugget)
        tag(NUGGETS_SCHRABIDIUM).add(NTechItems.schrabidiumNugget)
        tag(NUGGETS_SCHRABIDIUM_FUEL).add(NTechItems.schrabidiumFuelNugget)
        tag(NUGGETS_SOLINIUM).add(NTechItems.soliniumNugget)
        tag(NUGGETS_THORIUM).add(NTechItems.th232Nugget)
        tag(NUGGETS_THORIUM_FUEL).add(NTechItems.thoriumFuelNugget)
        tag(NUGGETS_UNOBTAINIUM).add(NTechItems.unobtainiumNugget)
        tag(NUGGETS_URANIUM).add(NTechItems.uraniumNugget)
        tag(NUGGETS_URANIUM_FUEL).add(NTechItems.uraniumFuelNugget)
        tag(NUGGETS_VERTICIUM).add(NTechItems.verticiumNugget)
        tag(NUGGETS_WEIDANIUM).add(NTechItems.weidaniumNugget)
        return@with
    }

    private fun plateTags() = with(NTechTags.Items) {
        tag(PLATES)
            .addTags(PLATES_ALUMINIUM, PLATES_COMBINE_STEEL, PLATES_COPPER, PLATES_GOLD, PLATES_INSULATOR, PLATES_IRON, PLATES_KEVLAR, PLATES_LEAD, PLATES_NEUTRON_REFLECTOR, PLATES_SATURNITE, PLATES_SCHRABIDIUM, PLATES_STEEL, PLATES_TITANIUM)
            .add(NTechItems.advancedAlloyPlate, NTechItems.mixedPlate, NTechItems.paAAlloyPlate, NTechItems.dalekaniumPlate, NTechItems.deshCompoundPlate, NTechItems.euphemiumCompoundPlate, NTechItems.dineutroniumCompoundPlate)
        tag(PLATES_ALUMINIUM).add(NTechItems.aluminiumPlate)
        tag(PLATES_COMBINE_STEEL).add(NTechItems.combineSteelPlate)
        tag(PLATES_COPPER).add(NTechItems.copperPlate)
        tag(PLATES_GOLD).add(NTechItems.goldPlate)
        tag(PLATES_INSULATOR).add(NTechItems.insulator)
        tag(PLATES_IRON).add(NTechItems.ironPlate)
        tag(PLATES_KEVLAR).add(NTechItems.kevlarCeramicCompound)
        tag(PLATES_LEAD).add(NTechItems.leadPlate)
        tag(PLATES_NEUTRON_REFLECTOR).add(NTechItems.neutronReflector)
        tag(PLATES_SATURNITE).add(NTechItems.saturnitePlate)
        tag(PLATES_SCHRABIDIUM).add(NTechItems.schrabidiumPlate)
        tag(PLATES_STEEL).add(NTechItems.steelPlate)
        tag(PLATES_TITANIUM).add(NTechItems.titaniumPlate)
        return@with
    }

    private fun wireTags() = with(NTechTags.Items) {
        tag(WIRES).addTags(WIRES_ALUMINIUM, WIRES_COPPER, WIRES_GOLD, WIRES_MAGNETIZED_TUNGSTEN, WIRES_RED_COPPER, WIRES_SCHRABIDIUM, WIRES_SUPER_CONDUCTOR, WIRES_TUNGSTEN)
        tag(WIRES_ALUMINIUM).add(NTechItems.aluminiumWire)
        tag(WIRES_COPPER).add(NTechItems.copperWire)
        tag(WIRES_GOLD).add(NTechItems.goldWire)
        tag(WIRES_MAGNETIZED_TUNGSTEN).add(NTechItems.highTemperatureSuperConductor)
        tag(WIRES_RED_COPPER).add(NTechItems.redCopperWire)
        tag(WIRES_SCHRABIDIUM).add(NTechItems.schrabidiumWire)
        tag(WIRES_SUPER_CONDUCTOR).add(NTechItems.superConductor)
        tag(WIRES_TUNGSTEN).add(NTechItems.tungstenWire)
        return@with
    }

    private fun coilTags() = with(NTechTags.Items) {
        tag(COILS).addTag(COILS_COPPER).addTag(COILS_GOLD).addTag(COILS_SUPER_CONDUCTOR).addTag(COILS_TUNGSTEN)
        tag(COILS_COPPER).add(NTechItems.copperCoil, NTechItems.ringCoil)
        tag(COILS_GOLD).add(NTechItems.goldCoil, NTechItems.goldRingCoil)
        tag(COILS_SUPER_CONDUCTOR).add(NTechItems.superConductingCoil, NTechItems.superConductingRingCoil, NTechItems.heatingCoil, NTechItems.highTemperatureSuperConductingCoil)
        tag(COILS_TUNGSTEN).add(NTechItems.heatingCoil)
    }

    private fun fissileFuelTags() = with(NTechTags.Items) {
        tag(FISSILE_FUELS)
            .addTags(FISSILE_FUELS_MOX, FISSILE_FUELS_PLUTONIUM, FISSILE_FUELS_SCHRABIDIUM, FISSILE_FUELS_THORIUM, FISSILE_FUELS_URANIUM)
            .add(NTechItems.highEnrichedSchrabidiumFuelIngot, NTechItems.lowEnrichedSchrabidiumFuelIngot)
        tag(FISSILE_FUELS_MOX).add(NTechItems.moxFuelIngot, NTechItems.moxFuelNugget)
        tag(FISSILE_FUELS_PLUTONIUM).add(NTechItems.plutoniumFuelIngot, NTechItems.plutoniumFuelNugget)
        tag(FISSILE_FUELS_SCHRABIDIUM).add(NTechItems.schrabidiumFuelIngot, NTechItems.schrabidiumFuelNugget)
        tag(FISSILE_FUELS_THORIUM).add(NTechItems.thoriumFuelIngot, NTechItems.thoriumFuelNugget)
        tag(FISSILE_FUELS_URANIUM).add(NTechItems.uraniumFuelIngot, NTechItems.uraniumFuelNugget)
        return@with
    }

    private fun wasteTags() {
        tag(NTechTags.Items.NUCLEAR_WASTE).add(
            NTechItems.nuclearWaste.get(), NTechItems.depletedUraniumFuel.get(),
            NTechItems.depletedThoriumFuel.get(), NTechItems.depletedPlutoniumFuel.get(),
            NTechItems.depletedMOXFuel.get(), NTechItems.depletedSchrabidiumFuel.get(),
            NTechItems.hotDepletedUraniumFuel.get(), NTechItems.hotDepletedThoriumFuel.get(),
            NTechItems.hotDepletedPlutoniumFuel.get(), NTechItems.hotDepletedMOXFuel.get(),
            NTechItems.hotDepletedSchrabidiumFuel.get()
        )

        tag(NTechTags.Items.WASTES)
            .addTag(NTechTags.Items.WASTES_SCRAP)
            .addTag(NTechTags.Items.WASTES_NUCLEAR_WASTE)

        tag(NTechTags.Items.WASTES_SCRAP).add(NTechItems.scrap.get())
        tag(NTechTags.Items.WASTES_NUCLEAR_WASTE)
            .addTag(NTechTags.Items.WASTES_HOT_URANIUM)
            .addTag(NTechTags.Items.WASTES_HOT_THORIUM)
            .addTag(NTechTags.Items.WASTES_HOT_PLUTONIUM)
            .addTag(NTechTags.Items.WASTES_HOT_MOX)
            .addTag(NTechTags.Items.WASTES_URANIUM)
            .addTag(NTechTags.Items.WASTES_THORIUM)
            .addTag(NTechTags.Items.WASTES_PLUTONIUM)
            .addTag(NTechTags.Items.WASTES_MOX)
            .add(NTechItems.nuclearWaste.get())

        tag(NTechTags.Items.WASTES_HOT_URANIUM).add(NTechItems.hotDepletedUraniumFuel.get())
        tag(NTechTags.Items.WASTES_HOT_THORIUM).add(NTechItems.hotDepletedThoriumFuel.get())
        tag(NTechTags.Items.WASTES_HOT_PLUTONIUM).add(NTechItems.hotDepletedPlutoniumFuel.get())
        tag(NTechTags.Items.WASTES_HOT_MOX).add(NTechItems.hotDepletedMOXFuel.get())
        tag(NTechTags.Items.WASTES_URANIUM).add(NTechItems.depletedUraniumFuel.get())
        tag(NTechTags.Items.WASTES_THORIUM).add(NTechItems.depletedThoriumFuel.get())
        tag(NTechTags.Items.WASTES_PLUTONIUM).add(NTechItems.depletedPlutoniumFuel.get())
        tag(NTechTags.Items.WASTES_MOX).add(NTechItems.depletedMOXFuel.get())

        tag(NTechTags.Items.HOT_WASTES).add(
            NTechItems.hotDepletedUraniumFuel.get(), NTechItems.hotDepletedThoriumFuel.get(),
            NTechItems.hotDepletedPlutoniumFuel.get(), NTechItems.hotDepletedMOXFuel.get(),
            NTechItems.hotDepletedSchrabidiumFuel.get()
        )
        tag(NTechTags.Items.COLD_WASTES).add(
            NTechItems.depletedUraniumFuel.get(), NTechItems.depletedThoriumFuel.get(),
            NTechItems.depletedPlutoniumFuel.get(), NTechItems.depletedMOXFuel.get(),
            NTechItems.depletedSchrabidiumFuel.get(),
        )
    }

    private fun miscTags() {
        tag(NTechTags.Items.COKE).add(NTechItems.coke.get())

        tag(NTechTags.Items.YELLOWCAKE_URANIUM).add(NTechItems.yellowcake.get())

        tag(NTechTags.Items.RARE_EARTH_FRAGMENTS).add(
            NTechItems.neodymiumFragment.get(), NTechItems.cobaltFragment.get(),
            NTechItems.niobiumFragment.get(), NTechItems.ceriumFragment.get(),
            NTechItems.lanthanumFragment.get(), NTechItems.actiniumFragment.get(),
        )

        tag(NTechTags.Items.BIOMASS).add(NTechItems.biomass.get()).addTag(NTechTags.Items.COMPRESSED_BIOMASS)
        tag(NTechTags.Items.COMPRESSED_BIOMASS).add(NTechItems.compressedBiomass.get())

        tag(NTechTags.Items.BOLTS).addTag(NTechTags.Items.BOLTS_TUNGSTEN)
        tag(NTechTags.Items.BOLTS_TUNGSTEN).add(NTechItems.tungstenBolt.get())

        tag(NTechTags.Items.FABRIC_HAZMAT).add(NTechItems.hazmatCloth.get(), NTechItems.advancedHazmatCloth.get(), NTechItems.leadReinforcedHazmatCloth.get())

        tag(NTechTags.Items.FILTERS).addTag(NTechTags.Items.FILTERS_CARBON)
        tag(NTechTags.Items.FILTERS_CARBON).add(NTechItems.activatedCarbonFilter.get())

        tag(NTechTags.Items.SCRAP).add(NTechItems.scrap.get())

        tag(NTechTags.Items.SHREDDER_BLADES).add(
            NTechItems.aluminiumShredderBlade.get(), NTechItems.goldShredderBlade.get(),
            NTechItems.ironShredderBlade.get(), NTechItems.steelShredderBlade.get(),
            NTechItems.titaniumShredderBlade.get(), NTechItems.advancedAlloyShredderBlade.get(),
            NTechItems.combineSteelShredderBlade.get(), NTechItems.schrabidiumShredderBlade.get(),
            NTechItems.deshShredderBlade.get()
        )
    }

    private fun templateFolders() {
        tag(NTechTags.Items.SIREN_TRACKS).add(
            NTechItems.sirenTrackHatchSiren.get(), NTechItems.sirenTrackAutopilotDisconnected.get(),
            NTechItems.sirenTrackAMSSiren.get(), NTechItems.sirenTrackBlastDoorAlarm.get(),
            NTechItems.sirenTrackAPCSiren.get(), NTechItems.sirenTrackKlaxon.get(),
            NTechItems.sirenTrackVaultDoorAlarm.get(), NTechItems.sirenTrackSecurityAlert.get(),
            NTechItems.sirenTrackStandardSiren.get(), NTechItems.sirenTrackClassicSiren.get(),
            NTechItems.sirenTrackBankAlarm.get(), NTechItems.sirenTrackBeepSiren.get(),
            NTechItems.sirenTrackContainerAlarm.get(), NTechItems.sirenTrackSweepSiren.get(),
            NTechItems.sirenTrackMissileSiloSiren.get(), NTechItems.sirenTrackAirRaidSiren.get(),
            NTechItems.sirenTrackNostromoSelfDestruct.get(), NTechItems.sirenTrackEASAlarmScreech.get(),
            NTechItems.sirenTrackAPCPass.get(), NTechItems.sirenTrackRazortrainHorn.get(),
        )
        tag(NTechTags.Items.FLAT_STAMPS).add(
            NTechItems.stoneFlatStamp.get(),
            NTechItems.ironFlatStamp.get(),
            NTechItems.steelFlatStamp.get(),
            NTechItems.titaniumFlatStamp.get(),
            NTechItems.obsidianFlatStamp.get(),
            NTechItems.schrabidiumFlatStamp.get()
        )
        tag(NTechTags.Items.PLATE_STAMPS).add(
            NTechItems.stonePlateStamp.get(),
            NTechItems.ironPlateStamp.get(),
            NTechItems.steelPlateStamp.get(),
            NTechItems.titaniumPlateStamp.get(),
            NTechItems.obsidianPlateStamp.get(),
            NTechItems.schrabidiumPlateStamp.get()
        )
        tag(NTechTags.Items.WIRE_STAMPS).add(
            NTechItems.stoneWireStamp.get(),
            NTechItems.ironWireStamp.get(),
            NTechItems.steelWireStamp.get(),
            NTechItems.titaniumWireStamp.get(),
            NTechItems.obsidianWireStamp.get(),
            NTechItems.schrabidiumWireStamp.get()
        )
        tag(NTechTags.Items.CIRCUIT_STAMPS).add(
            NTechItems.stoneCircuitStamp.get(),
            NTechItems.ironCircuitStamp.get(),
            NTechItems.steelCircuitStamp.get(),
            NTechItems.titaniumCircuitStamp.get(),
            NTechItems.obsidianCircuitStamp.get(),
            NTechItems.schrabidiumCircuitStamp.get()
        )
        tag(NTechTags.Items.FOLDER_STAMPS)
            .addTag(NTechTags.Items.PLATE_STAMPS)
            .addTag(NTechTags.Items.WIRE_STAMPS)
            .addTag(NTechTags.Items.CIRCUIT_STAMPS)
        tag(NTechTags.Items.STONE_STAMPS).add(
            NTechItems.stoneFlatStamp.get(),
            NTechItems.stonePlateStamp.get(),
            NTechItems.stoneWireStamp.get(),
            NTechItems.stoneCircuitStamp.get()
        )
        tag(NTechTags.Items.IRON_STAMPS).add(
            NTechItems.ironFlatStamp.get(),
            NTechItems.ironPlateStamp.get(),
            NTechItems.ironWireStamp.get(),
            NTechItems.ironCircuitStamp.get()
        )
        tag(NTechTags.Items.STEEL_STAMPS).add(
            NTechItems.steelFlatStamp.get(),
            NTechItems.steelPlateStamp.get(),
            NTechItems.steelWireStamp.get(),
            NTechItems.steelCircuitStamp.get()
        )
        tag(NTechTags.Items.TITANIUM_STAMPS).add(
            NTechItems.titaniumFlatStamp.get(),
            NTechItems.titaniumPlateStamp.get(),
            NTechItems.titaniumWireStamp.get(),
            NTechItems.titaniumCircuitStamp.get()
        )
        tag(NTechTags.Items.OBSIDIAN_STAMPS).add(
            NTechItems.obsidianFlatStamp.get(),
            NTechItems.obsidianPlateStamp.get(),
            NTechItems.obsidianWireStamp.get(),
            NTechItems.obsidianCircuitStamp.get()
        )
        tag(NTechTags.Items.SCHRABIDIUM_STAMPS).add(
            NTechItems.schrabidiumFlatStamp.get(),
            NTechItems.schrabidiumPlateStamp.get(),
            NTechItems.schrabidiumWireStamp.get(),
            NTechItems.schrabidiumCircuitStamp.get()
        )
        tag(NTechTags.Items.STAMPS)
            .addTag(NTechTags.Items.STONE_STAMPS)
            .addTag(NTechTags.Items.IRON_STAMPS)
            .addTag(NTechTags.Items.STEEL_STAMPS)
            .addTag(NTechTags.Items.TITANIUM_STAMPS)
            .addTag(NTechTags.Items.OBSIDIAN_STAMPS)
            .addTag(NTechTags.Items.SCHRABIDIUM_STAMPS)
        tag(NTechTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS)
            .addTag(NTechTags.Items.FOLDER_STAMPS)
            .addTag(NTechTags.Items.SIREN_TRACKS)
            .add(NTechItems.fluidIdentifier.get())
            .add(NTechItems.assemblyTemplate.get())
            .add(NTechItems.chemTemplate.get())
    }

    private fun modifyVanillaTags() {
        tag(ItemTags.PIGLIN_LOVED)
            .addTag(NTechTags.Items.DUSTS_GOLD)
            .addTag(NTechTags.Items.GEMS_GOLD)
            .addTag(NTechTags.Items.PLATES_GOLD)
            .addTag(NTechTags.Items.WIRES_GOLD)
            .addTag(NTechTags.Items.COILS_GOLD)
            .add(NTechItems.goldBulletAssembly.get())
    }
}
