package at.martinthedragon.nucleartech.datagen.tags

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
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

    private fun copyFromBlocks() = with(NuclearTags.Items) {
        copy(NuclearTags.Blocks.ORES_ALUMINIUM, ORES_ALUMINIUM)
        copy(NuclearTags.Blocks.ORES_ASBESTOS, ORES_ASBESTOS)
        copy(NuclearTags.Blocks.ORES_AUSTRALIUM, ORES_AUSTRALIUM)
        copy(NuclearTags.Blocks.ORES_BERYLLIUM, ORES_BERYLLIUM)
        copy(NuclearTags.Blocks.ORES_COBALT, ORES_COBALT)
        copy(NuclearTags.Blocks.ORES_DAFFERGON, ORES_DAFFERGON)
        copy(NuclearTags.Blocks.ORES_FLUORITE, ORES_FLUORITE)
        copy(NuclearTags.Blocks.ORES_LEAD, ORES_LEAD)
        copy(NuclearTags.Blocks.ORES_LIGNITE, ORES_LIGNITE)
        copy(NuclearTags.Blocks.ORES_NITER, ORES_NITER)
        copy(NuclearTags.Blocks.ORES_OIL, ORES_OIL)
        copy(NuclearTags.Blocks.ORES_PHOSPHORUS, ORES_PHOSPHORUS)
        copy(NuclearTags.Blocks.ORES_PLUTONIUM, ORES_PLUTONIUM)
        copy(NuclearTags.Blocks.ORES_RARE_EARTH, ORES_RARE_EARTH)
        copy(NuclearTags.Blocks.ORES_REIIUM, ORES_REIIUM)
        copy(NuclearTags.Blocks.ORES_SCHRABIDIUM, ORES_SCHRABIDIUM)
        copy(NuclearTags.Blocks.ORES_STARMETAL, ORES_STARMETAL)
        copy(NuclearTags.Blocks.ORES_SULFUR, ORES_SULFUR)
        copy(NuclearTags.Blocks.ORES_THORIUM, ORES_THORIUM)
        copy(NuclearTags.Blocks.ORES_TITANIUM, ORES_TITANIUM)
        copy(NuclearTags.Blocks.ORES_TRINITITE, ORES_TRINITITE)
        copy(NuclearTags.Blocks.ORES_TRIXITE, ORES_TRIXITE)
        copy(NuclearTags.Blocks.ORES_TUNGSTEN, ORES_TUNGSTEN)
        copy(NuclearTags.Blocks.ORES_UNOBTAINIUM, ORES_UNOBTAINIUM)
        copy(NuclearTags.Blocks.ORES_URANIUM, ORES_URANIUM)
        copy(NuclearTags.Blocks.ORES_VERTICIUM, ORES_VERTICIUM)
        copy(NuclearTags.Blocks.ORES_WEIDANIUM, ORES_WEIDANIUM)
        copy(Tags.Blocks.ORES, Tags.Items.ORES)
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE)
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE)
        copy(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK)
        copy(NuclearTags.Blocks.ORES_IN_GROUND_END_STONE, ORES_IN_GROUND_END_STONE)

        copy(NuclearTags.Blocks.STORAGE_BLOCKS_ALUMINIUM, STORAGE_BLOCKS_ALUMINIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_ASBESTOS, STORAGE_BLOCKS_ASBESTOS)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_AUSTRALIUM, STORAGE_BLOCKS_AUSTRALIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_BERYLLIUM, STORAGE_BLOCKS_BERYLLIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_COBALT, STORAGE_BLOCKS_COBALT)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_COMBINE_STEEL, STORAGE_BLOCKS_COMBINE_STEEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_DAFFERGON, STORAGE_BLOCKS_DAFFERGON)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_DESH, STORAGE_BLOCKS_DESH)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_ELECTRICAL_SCRAP, STORAGE_BLOCKS_ELECTRICAL_SCRAP)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_EUPHEMIUM, STORAGE_BLOCKS_EUPHEMIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_FIBERGLASS, STORAGE_BLOCKS_FIBERGLASS)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_FLUORITE, STORAGE_BLOCKS_FLUORITE)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_HAZMAT, STORAGE_BLOCKS_HAZMAT)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_INSULATOR, STORAGE_BLOCKS_INSULATOR)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_LEAD, STORAGE_BLOCKS_LEAD)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_LITHIUM, STORAGE_BLOCKS_LITHIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN, STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_MOX, STORAGE_BLOCKS_MOX)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM, STORAGE_BLOCKS_NEPTUNIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_NITER, STORAGE_BLOCKS_NITER)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_NUCLEAR_WASTE, STORAGE_BLOCKS_NUCLEAR_WASTE)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS, STORAGE_BLOCKS_PHOSPHORUS)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_PLUTONIUM, STORAGE_BLOCKS_PLUTONIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_PLUTONIUM_FUEL, STORAGE_BLOCKS_PLUTONIUM_FUEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_RED_COPPER, STORAGE_BLOCKS_RED_COPPER)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_REIIUM, STORAGE_BLOCKS_REIIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SCHRABIDIUM, STORAGE_BLOCKS_SCHRABIDIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SCHRABIDIUM_FUEL, STORAGE_BLOCKS_SCHRABIDIUM_FUEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SCRAP, STORAGE_BLOCKS_SCRAP)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SOLINIUM, STORAGE_BLOCKS_SOLINIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_STARMETAL, STORAGE_BLOCKS_STARMETAL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_STEEL, STORAGE_BLOCKS_STEEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SULFUR, STORAGE_BLOCKS_SULFUR)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_THORIUM, STORAGE_BLOCKS_THORIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_THORIUM_FUEL, STORAGE_BLOCKS_THORIUM_FUEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_TITANIUM, STORAGE_BLOCKS_TITANIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_TRINITITE, STORAGE_BLOCKS_TRINITITE)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_TUNGSTEN, STORAGE_BLOCKS_TUNGSTEN)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_UNOBTAINIUM, STORAGE_BLOCKS_UNOBTAINIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_URANIUM, STORAGE_BLOCKS_URANIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_URANIUM_FUEL, STORAGE_BLOCKS_URANIUM_FUEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_VERTICIUM, STORAGE_BLOCKS_VERTICIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_WEIDANIUM, STORAGE_BLOCKS_WEIDANIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_YELLOWCAKE, STORAGE_BLOCKS_YELLOWCAKE)
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS)

        copy(NuclearTags.Blocks.SAND_OIL, SAND_OIL)
        copy(Tags.Blocks.SAND, Tags.Items.SAND)
        copy(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK, GLOWING_MUSHROOM_GROW_BLOCK)
        copy(NuclearTags.Blocks.ANVIL, ANVIL)
        return@with
    }

    private fun rawMaterialTags() = with(NuclearTags.Items) {
        tag(Tags.Items.RAW_MATERIALS).addTags(RAW_MATERIALS_ALUMINIUM, RAW_MATERIALS_AUSTRALIUM, RAW_MATERIALS_BERYLLIUM, RAW_MATERIALS_COBALT, RAW_MATERIALS_LEAD, RAW_MATERIALS_LITHIUM, RAW_MATERIALS_PLUTONIUM, RAW_MATERIALS_RARE_EARTH, RAW_MATERIALS_SCHRABIDIUM, RAW_MATERIALS_STARMETAL, RAW_MATERIALS_THORIUM, RAW_MATERIALS_TITANIUM, RAW_MATERIALS_TRIXITE, RAW_MATERIALS_TUNGSTEN, RAW_MATERIALS_URANIUM)
        tag(RAW_MATERIALS_ALUMINIUM).add(ModItems.rawAluminium)
        tag(RAW_MATERIALS_AUSTRALIUM).add(ModItems.rawAustralium)
        tag(RAW_MATERIALS_BERYLLIUM).add(ModItems.rawBeryllium)
        tag(RAW_MATERIALS_COBALT).add(ModItems.rawCobalt)
        tag(RAW_MATERIALS_LEAD).add(ModItems.rawLead)
        tag(RAW_MATERIALS_LITHIUM).add(ModItems.rawLithium)
        tag(RAW_MATERIALS_PLUTONIUM).add(ModItems.rawPlutonium)
        tag(RAW_MATERIALS_RARE_EARTH).add(ModItems.rawRareEarth)
        tag(RAW_MATERIALS_SCHRABIDIUM).add(ModItems.rawSchrabidium)
        tag(RAW_MATERIALS_STARMETAL).add(ModItems.rawStarmetal)
        tag(RAW_MATERIALS_THORIUM).add(ModItems.rawThorium)
        tag(RAW_MATERIALS_TITANIUM).add(ModItems.rawTitanium)
        tag(RAW_MATERIALS_TRIXITE).add(ModItems.rawTrixite)
        tag(RAW_MATERIALS_TUNGSTEN).add(ModItems.rawTungsten)
        tag(RAW_MATERIALS_URANIUM).add(ModItems.rawUranium)
        return@with
    }

    private fun ingotTags() = with(NuclearTags.Items) {
        tag(Tags.Items.INGOTS)
            .addTags(INGOTS_ACTINIUM, INGOTS_ALUMINIUM, INGOTS_AMERICIUM, INGOTS_ASBESTOS, INGOTS_AUSTRALIUM, INGOTS_BERYLLIUM, INGOTS_COBALT, INGOTS_COMBINE_STEEL, INGOTS_DAFFERGON, INGOTS_DESH, INGOTS_DINEUTRONIUM, INGOTS_ELECTRONIUM, INGOTS_EUPHEMIUM, INGOTS_FIBERGLASS, INGOTS_HIGH_SPEED_STEEL, INGOTS_LANTHANUM, INGOTS_LEAD, INGOTS_LITHIUM, INGOTS_MAGNETIZED_TUNGSTEN, INGOTS_MOX, INGOTS_NEPTUNIUM, INGOTS_PHOSPHORUS, INGOTS_PLUTONIUM, INGOTS_PLUTONIUM_FUEL, INGOTS_POLONIUM, INGOTS_POLYMER, INGOTS_RED_COPPER, INGOTS_REIIUM, INGOTS_SATURNITE, INGOTS_SCHRABIDIUM, INGOTS_SCHRABIDIUM_FUEL, INGOTS_SCHRARANIUM, INGOTS_SEMTEX, INGOTS_SOLINIUM, INGOTS_STARMETAL, INGOTS_STEEL, INGOTS_TEFLON, INGOTS_THORIUM, INGOTS_THORIUM_FUEL, INGOTS_TITANIUM, INGOTS_TUNGSTEN, INGOTS_UNOBTAINIUM, INGOTS_URANIUM, INGOTS_URANIUM_FUEL, INGOTS_VERTICIUM, INGOTS_WEIDANIUM)
            .add(ModItems.advancedAlloyIngot.get(), ModItems.highEnrichedSchrabidiumFuelIngot.get(), ModItems.lowEnrichedSchrabidiumFuelIngot.get())
        tag(INGOTS_ACTINIUM).add(ModItems.actiniumIngot)
        tag(INGOTS_ALUMINIUM).add(ModItems.aluminiumIngot)
        tag(INGOTS_AMERICIUM).add(ModItems.americium241Ingot, ModItems.americium242Ingot)
        tag(INGOTS_ASBESTOS).add(ModItems.asbestosSheet)
        tag(INGOTS_AUSTRALIUM).add(ModItems.australiumIngot)
        tag(INGOTS_BERYLLIUM).add(ModItems.berylliumIngot)
        tag(INGOTS_COBALT).add(ModItems.cobaltIngot)
        tag(INGOTS_COMBINE_STEEL).add(ModItems.combineSteelIngot)
        tag(INGOTS_DAFFERGON).add(ModItems.daffergonIngot)
        tag(INGOTS_DESH).add(ModItems.deshIngot)
        tag(INGOTS_DINEUTRONIUM).add(ModItems.dineutroniumIngot)
        tag(INGOTS_ELECTRONIUM).add(ModItems.electroniumIngot)
        tag(INGOTS_EUPHEMIUM).add(ModItems.euphemiumIngot)
        tag(INGOTS_FIBERGLASS).add(ModItems.fiberglassSheet)
        tag(INGOTS_HIGH_SPEED_STEEL).add(ModItems.highSpeedSteelIngot)
        tag(INGOTS_LANTHANUM).add(ModItems.lanthanumIngot)
        tag(INGOTS_LEAD).add(ModItems.leadIngot)
        tag(INGOTS_LITHIUM).add(ModItems.lithiumCube)
        tag(INGOTS_MAGNETIZED_TUNGSTEN).add(ModItems.magnetizedTungstenIngot)
        tag(INGOTS_MOX).add(ModItems.moxFuelIngot)
        tag(INGOTS_NEPTUNIUM).add(ModItems.neptuniumIngot)
        tag(INGOTS_PHOSPHORUS).add(ModItems.whitePhosphorusIngot)
        tag(INGOTS_PLUTONIUM).add(ModItems.plutoniumIngot)
        tag(INGOTS_PLUTONIUM_FUEL).add(ModItems.plutoniumFuelIngot)
        tag(INGOTS_POLONIUM).add(ModItems.polonium210Ingot)
        tag(INGOTS_POLYMER).addTag(INGOTS_TEFLON)
        tag(INGOTS_RED_COPPER).add(ModItems.redCopperIngot)
        tag(INGOTS_REIIUM).add(ModItems.reiiumIngot)
        tag(INGOTS_SATURNITE).add(ModItems.saturniteIngot)
        tag(INGOTS_SCHRABIDIUM).add(ModItems.schrabidiumIngot)
        tag(INGOTS_SCHRABIDIUM_FUEL).add(ModItems.schrabidiumFuelIngot)
        tag(INGOTS_SCHRARANIUM).add(ModItems.schraraniumIngot)
        tag(INGOTS_SEMTEX).add(ModItems.semtexBar)
        tag(INGOTS_SOLINIUM).add(ModItems.soliniumIngot)
        tag(INGOTS_STARMETAL).add(ModItems.starmetalIngot)
        tag(INGOTS_STEEL).add(ModItems.steelIngot)
        tag(INGOTS_TEFLON).add(ModItems.polymerIngot)
        tag(INGOTS_THORIUM).add(ModItems.th232Ingot)
        tag(INGOTS_THORIUM_FUEL).add(ModItems.thoriumFuelIngot)
        tag(INGOTS_TITANIUM).add(ModItems.titaniumIngot)
        tag(INGOTS_TUNGSTEN).add(ModItems.tungstenIngot)
        tag(INGOTS_UNOBTAINIUM).add(ModItems.unobtainiumIngot)
        tag(INGOTS_URANIUM).add(ModItems.uraniumIngot)
        tag(INGOTS_URANIUM_FUEL).add(ModItems.uraniumFuelIngot)
        tag(INGOTS_VERTICIUM).add(ModItems.verticiumIngot)
        tag(INGOTS_WEIDANIUM).add(ModItems.weidaniumIngot)
        return@with
    }

    private fun dustTags() = with(NuclearTags.Items) {
        tag(Tags.Items.DUSTS)
            .addTags(DUSTS_ACTINIUM, DUSTS_ALUMINIUM, DUSTS_ASBESTOS, DUSTS_AUSTRALIUM, DUSTS_BALLISTITE, DUSTS_BERYLLIUM, DUSTS_CERIUM, DUSTS_CHLOROPHYTE, DUSTS_CLOUD, DUSTS_COAL, DUSTS_COBALT, DUSTS_COLD, DUSTS_COMBINE_STEEL, DUSTS_COPPER, DUSTS_CORDITE, DUSTS_DAFFERGON, DUSTS_DESH, DUSTS_DIAMOND, DUSTS_DINEUTRONIUM, DUSTS_DUST, DUSTS_EMERALD, DUSTS_ENCHANTMENT, DUSTS_ENERGY, DUSTS_EUPHEMIUM, DUSTS_FLUORITE, DUSTS_GOLD, DUSTS_IRON, DUSTS_LANTHANUM, DUSTS_LAPIS, DUSTS_LEAD, DUSTS_LIGNITE, DUSTS_LITHIUM, DUSTS_MAGNETIZED_TUNGSTEN, DUSTS_METEORITE, DUSTS_NEODYMIUM, DUSTS_NEPTUNIUM, DUSTS_NIOBIUM, DUSTS_NITER, DUSTS_PHOSPHORUS, DUSTS_PLUTONIUM, DUSTS_POISON, DUSTS_POLONIUM, DUSTS_POLYMER, DUSTS_QUARTZ, DUSTS_RED_COPPER, DUSTS_REIIUM, DUSTS_SCHRABIDIUM, DUSTS_SEMTEX, DUSTS_STEEL, DUSTS_SULFUR, DUSTS_TEFLON, DUSTS_THERMITE, DUSTS_THERMONUCLEAR_ASHES, DUSTS_THORIUM, DUSTS_TITANIUM, DUSTS_TUNGSTEN, DUSTS_UNOBTAINIUM, DUSTS_URANIUM, DUSTS_VERTICIUM, DUSTS_WEIDANIUM)
            .add(ModItems.advancedAlloyPowder, ModItems.deshMix, ModItems.deshReadyMix, ModItems.nitaniumMix, ModItems.dust, ModItems.sparkMix, ModItems.desaturatedRedstone)
        tag(DUSTS_ACTINIUM).add(ModItems.actiniumPowder)
        tag(DUSTS_ALUMINIUM).add(ModItems.aluminiumPowder)
        tag(DUSTS_ASBESTOS).add(ModItems.asbestosPowder)
        tag(DUSTS_AUSTRALIUM).add(ModItems.australiumPowder)
        tag(DUSTS_BALLISTITE).add(ModItems.ballistite)
        tag(DUSTS_BERYLLIUM).add(ModItems.berylliumPowder)
        tag(DUSTS_CERIUM).add(ModItems.ceriumPowder)
        tag(DUSTS_CHLOROPHYTE).add(ModItems.chlorophytePowder)
        tag(DUSTS_CLOUD).add(ModItems.cloudResidue)
        tag(DUSTS_COAL).add(ModItems.coalPowder)
        tag(DUSTS_COBALT).add(ModItems.cobaltPowder)
        tag(DUSTS_COLD).add(ModItems.cryoPowder)
        tag(DUSTS_COMBINE_STEEL).add(ModItems.combineSteelPowder)
        tag(DUSTS_COPPER).add(ModItems.copperPowder)
        tag(DUSTS_CORDITE).add(ModItems.cordite)
        tag(DUSTS_DAFFERGON).add(ModItems.daffergonPowder)
        tag(DUSTS_DESH).add(ModItems.deshPowder)
        tag(DUSTS_DIAMOND).add(ModItems.diamondPowder)
        tag(DUSTS_DINEUTRONIUM).add(ModItems.dineutroniumPowder)
        tag(DUSTS_DUST).add(ModItems.dust)
        tag(DUSTS_EMERALD).add(ModItems.emeraldPowder)
        tag(DUSTS_ENCHANTMENT).add(ModItems.enchantmentPowder)
        tag(DUSTS_ENERGY).add(ModItems.energyPowder)
        tag(DUSTS_EUPHEMIUM).add(ModItems.euphemiumPowder)
        tag(DUSTS_FLUORITE).add(ModItems.fluorite)
        tag(DUSTS_GOLD).add(ModItems.goldPowder)
        tag(DUSTS_IRON).add(ModItems.ironPowder)
        tag(DUSTS_LANTHANUM).add(ModItems.lanthanumPowder)
        tag(DUSTS_LAPIS).add(ModItems.lapisLazuliPowder)
        tag(DUSTS_LEAD).add(ModItems.leadPowder)
        tag(DUSTS_LIGNITE).add(ModItems.lignitePowder)
        tag(DUSTS_LITHIUM).add(ModItems.lithiumPowder)
        tag(DUSTS_MAGNETIZED_TUNGSTEN).add(ModItems.magnetizedTungstenPowder)
        tag(DUSTS_METEORITE).add(ModItems.meteoritePowder)
        tag(DUSTS_NEODYMIUM).add(ModItems.neodymiumPowder)
        tag(DUSTS_NEPTUNIUM).add(ModItems.neptuniumPowder)
        tag(DUSTS_NIOBIUM).add(ModItems.niobiumPowder)
        tag(DUSTS_NITER).add(ModItems.niter)
        tag(DUSTS_PHOSPHORUS).add(ModItems.redPhosphorus)
        tag(DUSTS_PLUTONIUM).add(ModItems.plutoniumPowder)
        tag(DUSTS_POISON).add(ModItems.poisonPowder)
        tag(DUSTS_POLONIUM).add(ModItems.poloniumPowder)
        tag(DUSTS_POLYMER).addTag(DUSTS_TEFLON)
        tag(DUSTS_QUARTZ).add(ModItems.quartzPowder)
        tag(DUSTS_RED_COPPER).add(ModItems.redCopperPowder)
        tag(DUSTS_REIIUM).add(ModItems.reiiumPowder)
        tag(DUSTS_SCHRABIDIUM).add(ModItems.schrabidiumPowder)
        tag(DUSTS_SEMTEX).add(ModItems.semtexMix)
        tag(DUSTS_STEEL).add(ModItems.steelIngot)
        tag(DUSTS_SULFUR).add(ModItems.sulfur)
        tag(DUSTS_TEFLON).add(ModItems.polymerPowder)
        tag(DUSTS_THERMITE).add(ModItems.thermite)
        tag(DUSTS_THERMONUCLEAR_ASHES).add(ModItems.thermonuclearAshes)
        tag(DUSTS_THORIUM).add(ModItems.thoriumPowder)
        tag(DUSTS_TITANIUM).add(ModItems.titaniumPowder)
        tag(DUSTS_TUNGSTEN).add(ModItems.tungstenPowder)
        tag(DUSTS_UNOBTAINIUM).add(ModItems.unobtainiumPowder)
        tag(DUSTS_URANIUM).add(ModItems.uraniumPowder)
        tag(DUSTS_VERTICIUM).add(ModItems.verticiumPowder)
        tag(DUSTS_WEIDANIUM).add(ModItems.weidaniumPowder)
        return@with
    }

    private fun crystalTags() = with(NuclearTags.Items) {
        tag(CRYSTALS).addTags(CRYSTALS_ALUMINIUM, CRYSTALS_BERYLLIUM, CRYSTALS_COAL, CRYSTALS_COPPER, CRYSTALS_DIAMOND, CRYSTALS_FLUORITE, CRYSTALS_GOLD, CRYSTALS_IRON, CRYSTALS_LEAD, CRYSTALS_LITHIUM, CRYSTALS_NITER, CRYSTALS_PHOSPHORUS, CRYSTALS_PLUTONIUM, CRYSTALS_RARE_EARTH, CRYSTALS_REDSTONE, CRYSTALS_SCHRABIDIUM, CRYSTALS_SCHRARANIUM, CRYSTALS_STARMETAL, CRYSTALS_SULFUR, CRYSTALS_THORIUM, CRYSTALS_TITANIUM, CRYSTALS_TRIXITE, CRYSTALS_TUNGSTEN, CRYSTALS_URANIUM)
        tag(CRYSTALS_ALUMINIUM).add(ModItems.aluminiumCrystals)
        tag(CRYSTALS_BERYLLIUM).add(ModItems.berylliumCrystals)
        tag(CRYSTALS_COAL).add(ModItems.coalCrystals)
        tag(CRYSTALS_COPPER).add(ModItems.copperCrystals)
        tag(CRYSTALS_DIAMOND).add(ModItems.diamondCrystals)
        tag(CRYSTALS_FLUORITE).add(ModItems.fluoriteCrystals)
        tag(CRYSTALS_GOLD).add(ModItems.goldCrystals)
        tag(CRYSTALS_IRON).add(ModItems.ironCrystals)
        tag(CRYSTALS_LEAD).add(ModItems.leadCrystals)
        tag(CRYSTALS_LITHIUM).add(ModItems.lithiumCrystals)
        tag(CRYSTALS_NITER).add(ModItems.niterCrystals)
        tag(CRYSTALS_PHOSPHORUS).add(ModItems.redPhosphorusCrystals)
        tag(CRYSTALS_PLUTONIUM).add(ModItems.plutoniumCrystals)
        tag(CRYSTALS_RARE_EARTH).add(ModItems.rareEarthCrystals)
        tag(CRYSTALS_REDSTONE).add(ModItems.redstoneCrystals)
        tag(CRYSTALS_SCHRABIDIUM).add(ModItems.schrabidiumCrystals)
        tag(CRYSTALS_SCHRARANIUM).add(ModItems.schraraniumCrystals)
        tag(CRYSTALS_STARMETAL).add(ModItems.starmetalCrystals)
        tag(CRYSTALS_SULFUR).add(ModItems.sulfurCrystals)
        tag(CRYSTALS_THORIUM).add(ModItems.thoriumCrystals)
        tag(CRYSTALS_TITANIUM).add(ModItems.titaniumCrystals)
        tag(CRYSTALS_TRIXITE).add(ModItems.trixiteCrystals)
        tag(CRYSTALS_TUNGSTEN).add(ModItems.tungstenCrystals)
        tag(CRYSTALS_URANIUM).add(ModItems.uraniumCrystals)

        tag(Tags.Items.GEMS).addTags(CRYSTALS, GEMS_ACTINIUM, GEMS_CERIUM, GEMS_COBALT, GEMS_LANTHANUM, GEMS_NEODYMIUM, GEMS_NIOBIUM)
        tag(GEMS_ACTINIUM).add(ModItems.actiniumFragment)
        tag(GEMS_ALUMINIUM).addTag(CRYSTALS_ALUMINIUM)
        tag(GEMS_BERYLLIUM).addTag(CRYSTALS_BERYLLIUM)
        tag(GEMS_CERIUM).add(ModItems.ceriumFragment)
        tag(GEMS_COAL).addTag(CRYSTALS_COAL)
        tag(GEMS_COBALT).add(ModItems.cobaltFragment)
        tag(GEMS_COPPER).addTag(CRYSTALS_COPPER)
        tag(GEMS_DIAMOND).addTag(CRYSTALS_DIAMOND)
        tag(GEMS_FLUORITE).addTag(CRYSTALS_FLUORITE)
        tag(GEMS_GOLD).addTag(CRYSTALS_GOLD)
        tag(GEMS_IRON).addTag(CRYSTALS_IRON)
        tag(GEMS_LANTHANUM).add(ModItems.lanthanumFragment)
        tag(GEMS_LEAD).addTag(CRYSTALS_LEAD)
        tag(GEMS_LITHIUM).addTag(CRYSTALS_LITHIUM)
        tag(GEMS_NEODYMIUM).add(ModItems.neodymiumFragment)
        tag(GEMS_NIOBIUM).add(ModItems.niobiumFragment)
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

    private fun nuggetTags() = with(NuclearTags.Items) {
        tag(Tags.Items.NUGGETS)
            .addTags(NUGGETS_AUSTRALIUM, NUGGETS_BERYLLIUM, NUGGETS_DAFFERGON, NUGGETS_DESH, NUGGETS_DINEUTRONIUM, NUGGETS_EUPHEMIUM, NUGGETS_LEAD, NUGGETS_MERCURY, NUGGETS_MOX, NUGGETS_NEPTUNIUM, NUGGETS_PLUTONIUM, NUGGETS_PLUTONIUM_FUEL, NUGGETS_POLONIUM, NUGGETS_REIIUM, NUGGETS_SCHRABIDIUM, NUGGETS_SCHRABIDIUM_FUEL, NUGGETS_SOLINIUM, NUGGETS_THORIUM, NUGGETS_THORIUM_FUEL, NUGGETS_UNOBTAINIUM, NUGGETS_URANIUM, NUGGETS_URANIUM_FUEL, NUGGETS_VERTICIUM, NUGGETS_WEIDANIUM)
            .add(ModItems.highEnrichedSchrabidiumFuelNugget, ModItems.lowEnrichedSchrabidiumFuelNugget)
        tag(NUGGETS_AUSTRALIUM).add(ModItems.australiumNugget)
        tag(NUGGETS_BERYLLIUM).add(ModItems.berylliumNugget)
        tag(NUGGETS_DAFFERGON).add(ModItems.daffergonNugget)
        tag(NUGGETS_DESH).add(ModItems.deshNugget)
        tag(NUGGETS_DINEUTRONIUM).add(ModItems.dineutroniumNugget)
        tag(NUGGETS_EUPHEMIUM).add(ModItems.euphemiumNugget)
        tag(NUGGETS_LEAD).add(ModItems.leadNugget)
        tag(NUGGETS_MERCURY).add(ModItems.mercuryDroplet)
        tag(NUGGETS_MOX).add(ModItems.moxFuelNugget)
        tag(NUGGETS_NEPTUNIUM).add(ModItems.neptuniumNugget)
        tag(NUGGETS_PLUTONIUM).add(ModItems.plutoniumNugget)
        tag(NUGGETS_PLUTONIUM_FUEL).add(ModItems.plutoniumFuelNugget)
        tag(NUGGETS_POLONIUM).add(ModItems.poloniumNugget)
        tag(NUGGETS_REIIUM).add(ModItems.reiiumNugget)
        tag(NUGGETS_SCHRABIDIUM).add(ModItems.schrabidiumNugget)
        tag(NUGGETS_SCHRABIDIUM_FUEL).add(ModItems.schrabidiumFuelNugget)
        tag(NUGGETS_SOLINIUM).add(ModItems.soliniumNugget)
        tag(NUGGETS_THORIUM).add(ModItems.th232Nugget)
        tag(NUGGETS_THORIUM_FUEL).add(ModItems.thoriumFuelNugget)
        tag(NUGGETS_UNOBTAINIUM).add(ModItems.unobtainiumNugget)
        tag(NUGGETS_URANIUM).add(ModItems.uraniumNugget)
        tag(NUGGETS_URANIUM_FUEL).add(ModItems.uraniumFuelNugget)
        tag(NUGGETS_VERTICIUM).add(ModItems.verticiumNugget)
        tag(NUGGETS_WEIDANIUM).add(ModItems.weidaniumNugget)
        return@with
    }

    private fun plateTags() = with(NuclearTags.Items) {
        tag(PLATES)
            .addTags(PLATES_ALUMINIUM, PLATES_COMBINE_STEEL, PLATES_COPPER, PLATES_GOLD, PLATES_INSULATOR, PLATES_IRON, PLATES_KEVLAR, PLATES_LEAD, PLATES_NEUTRON_REFLECTOR, PLATES_SATURNITE, PLATES_SCHRABIDIUM, PLATES_STEEL, PLATES_TITANIUM)
            .add(ModItems.advancedAlloyPlate, ModItems.mixedPlate, ModItems.paAAlloyPlate, ModItems.dalekaniumPlate, ModItems.deshCompoundPlate, ModItems.euphemiumCompoundPlate, ModItems.dineutroniumCompoundPlate)
        tag(PLATES_ALUMINIUM).add(ModItems.aluminiumPlate)
        tag(PLATES_COMBINE_STEEL).add(ModItems.combineSteelPlate)
        tag(PLATES_COPPER).add(ModItems.copperPlate)
        tag(PLATES_GOLD).add(ModItems.goldPlate)
        tag(PLATES_INSULATOR).add(ModItems.insulator)
        tag(PLATES_IRON).add(ModItems.ironPlate)
        tag(PLATES_KEVLAR).add(ModItems.kevlarCeramicCompound)
        tag(PLATES_LEAD).add(ModItems.leadPlate)
        tag(PLATES_NEUTRON_REFLECTOR).add(ModItems.neutronReflector)
        tag(PLATES_SATURNITE).add(ModItems.saturnitePlate)
        tag(PLATES_SCHRABIDIUM).add(ModItems.schrabidiumPlate)
        tag(PLATES_STEEL).add(ModItems.steelPlate)
        tag(PLATES_TITANIUM).add(ModItems.titaniumPlate)
        return@with
    }

    private fun wireTags() = with(NuclearTags.Items) {
        tag(WIRES).addTags(WIRES_ALUMINIUM, WIRES_COPPER, WIRES_GOLD, WIRES_MAGNETIZED_TUNGSTEN, WIRES_RED_COPPER, WIRES_SCHRABIDIUM, WIRES_SUPER_CONDUCTOR, WIRES_TUNGSTEN)
        tag(WIRES_ALUMINIUM).add(ModItems.aluminiumWire)
        tag(WIRES_COPPER).add(ModItems.copperWire)
        tag(WIRES_GOLD).add(ModItems.goldWire)
        tag(WIRES_MAGNETIZED_TUNGSTEN).add(ModItems.highTemperatureSuperConductor)
        tag(WIRES_RED_COPPER).add(ModItems.redCopperWire)
        tag(WIRES_SCHRABIDIUM).add(ModItems.schrabidiumWire)
        tag(WIRES_SUPER_CONDUCTOR).add(ModItems.superConductor)
        tag(WIRES_TUNGSTEN).add(ModItems.tungstenWire)
        return@with
    }

    private fun coilTags() = with(NuclearTags.Items) {
        tag(COILS).addTag(COILS_COPPER).addTag(COILS_GOLD).addTag(COILS_SUPER_CONDUCTOR).addTag(COILS_TUNGSTEN)
        tag(COILS_COPPER).add(ModItems.copperCoil, ModItems.ringCoil)
        tag(COILS_GOLD).add(ModItems.goldCoil, ModItems.goldRingCoil)
        tag(COILS_SUPER_CONDUCTOR).add(ModItems.superConductingCoil, ModItems.superConductingRingCoil, ModItems.heatingCoil, ModItems.highTemperatureSuperConductingCoil)
        tag(COILS_TUNGSTEN).add(ModItems.heatingCoil)
    }

    private fun fissileFuelTags() = with(NuclearTags.Items) {
        tag(FISSILE_FUELS)
            .addTags(FISSILE_FUELS_MOX, FISSILE_FUELS_PLUTONIUM, FISSILE_FUELS_SCHRABIDIUM, FISSILE_FUELS_THORIUM, FISSILE_FUELS_URANIUM)
            .add(ModItems.highEnrichedSchrabidiumFuelIngot, ModItems.lowEnrichedSchrabidiumFuelIngot)
        tag(FISSILE_FUELS_MOX).add(ModItems.moxFuelIngot, ModItems.moxFuelNugget)
        tag(FISSILE_FUELS_PLUTONIUM).add(ModItems.plutoniumFuelIngot, ModItems.plutoniumFuelNugget)
        tag(FISSILE_FUELS_SCHRABIDIUM).add(ModItems.schrabidiumFuelIngot, ModItems.schrabidiumFuelNugget)
        tag(FISSILE_FUELS_THORIUM).add(ModItems.thoriumFuelIngot, ModItems.thoriumFuelNugget)
        tag(FISSILE_FUELS_URANIUM).add(ModItems.uraniumFuelIngot, ModItems.uraniumFuelNugget)
        return@with
    }

    private fun wasteTags() {
        tag(NuclearTags.Items.NUCLEAR_WASTE).add(
            ModItems.nuclearWaste.get(), ModItems.depletedUraniumFuel.get(),
            ModItems.depletedThoriumFuel.get(), ModItems.depletedPlutoniumFuel.get(),
            ModItems.depletedMOXFuel.get(), ModItems.depletedSchrabidiumFuel.get(),
            ModItems.hotDepletedUraniumFuel.get(), ModItems.hotDepletedThoriumFuel.get(),
            ModItems.hotDepletedPlutoniumFuel.get(), ModItems.hotDepletedMOXFuel.get(),
            ModItems.hotDepletedSchrabidiumFuel.get()
        )

        tag(NuclearTags.Items.WASTES)
            .addTag(NuclearTags.Items.WASTES_SCRAP)
            .addTag(NuclearTags.Items.WASTES_NUCLEAR_WASTE)

        tag(NuclearTags.Items.WASTES_SCRAP).add(ModItems.scrap.get())
        tag(NuclearTags.Items.WASTES_NUCLEAR_WASTE)
            .addTag(NuclearTags.Items.WASTES_HOT_URANIUM)
            .addTag(NuclearTags.Items.WASTES_HOT_THORIUM)
            .addTag(NuclearTags.Items.WASTES_HOT_PLUTONIUM)
            .addTag(NuclearTags.Items.WASTES_HOT_MOX)
            .addTag(NuclearTags.Items.WASTES_URANIUM)
            .addTag(NuclearTags.Items.WASTES_THORIUM)
            .addTag(NuclearTags.Items.WASTES_PLUTONIUM)
            .addTag(NuclearTags.Items.WASTES_MOX)
            .add(ModItems.nuclearWaste.get())

        tag(NuclearTags.Items.WASTES_HOT_URANIUM).add(ModItems.hotDepletedUraniumFuel.get())
        tag(NuclearTags.Items.WASTES_HOT_THORIUM).add(ModItems.hotDepletedThoriumFuel.get())
        tag(NuclearTags.Items.WASTES_HOT_PLUTONIUM).add(ModItems.hotDepletedPlutoniumFuel.get())
        tag(NuclearTags.Items.WASTES_HOT_MOX).add(ModItems.hotDepletedMOXFuel.get())
        tag(NuclearTags.Items.WASTES_URANIUM).add(ModItems.depletedUraniumFuel.get())
        tag(NuclearTags.Items.WASTES_THORIUM).add(ModItems.depletedThoriumFuel.get())
        tag(NuclearTags.Items.WASTES_PLUTONIUM).add(ModItems.depletedPlutoniumFuel.get())
        tag(NuclearTags.Items.WASTES_MOX).add(ModItems.depletedMOXFuel.get())

        tag(NuclearTags.Items.HOT_WASTES).add(
            ModItems.hotDepletedUraniumFuel.get(), ModItems.hotDepletedThoriumFuel.get(),
            ModItems.hotDepletedPlutoniumFuel.get(), ModItems.hotDepletedMOXFuel.get(),
            ModItems.hotDepletedSchrabidiumFuel.get()
        )
        tag(NuclearTags.Items.COLD_WASTES).add(
            ModItems.depletedUraniumFuel.get(), ModItems.depletedThoriumFuel.get(),
            ModItems.depletedPlutoniumFuel.get(), ModItems.depletedMOXFuel.get(),
            ModItems.depletedSchrabidiumFuel.get(),
        )
    }

    private fun miscTags() {
        tag(NuclearTags.Items.COKE).add(ModItems.coke.get())

        tag(NuclearTags.Items.YELLOWCAKE_URANIUM).add(ModItems.yellowcake.get())

        tag(NuclearTags.Items.RARE_EARTH_FRAGMENTS).add(
            ModItems.neodymiumFragment.get(), ModItems.cobaltFragment.get(),
            ModItems.niobiumFragment.get(), ModItems.ceriumFragment.get(),
            ModItems.lanthanumFragment.get(), ModItems.actiniumFragment.get(),
        )

        tag(NuclearTags.Items.BIOMASS).add(ModItems.biomass.get()).addTag(NuclearTags.Items.COMPRESSED_BIOMASS)
        tag(NuclearTags.Items.COMPRESSED_BIOMASS).add(ModItems.compressedBiomass.get())

        tag(NuclearTags.Items.BOLTS).addTag(NuclearTags.Items.BOLTS_TUNGSTEN)
        tag(NuclearTags.Items.BOLTS_TUNGSTEN).add(ModItems.tungstenBolt.get())

        tag(NuclearTags.Items.FABRIC_HAZMAT).add(ModItems.hazmatCloth.get(), ModItems.advancedHazmatCloth.get(), ModItems.leadReinforcedHazmatCloth.get())

        tag(NuclearTags.Items.FILTERS).addTag(NuclearTags.Items.FILTERS_CARBON)
        tag(NuclearTags.Items.FILTERS_CARBON).add(ModItems.activatedCarbonFilter.get())

        tag(NuclearTags.Items.SCRAP).add(ModItems.scrap.get())

        tag(NuclearTags.Items.SHREDDER_BLADES).add(
            ModItems.aluminiumShredderBlade.get(), ModItems.goldShredderBlade.get(),
            ModItems.ironShredderBlade.get(), ModItems.steelShredderBlade.get(),
            ModItems.titaniumShredderBlade.get(), ModItems.advancedAlloyShredderBlade.get(),
            ModItems.combineSteelShredderBlade.get(), ModItems.schrabidiumShredderBlade.get(),
            ModItems.deshShredderBlade.get()
        )
    }

    private fun templateFolders() {
        tag(NuclearTags.Items.SIREN_TRACKS).add(
            ModItems.sirenTrackHatchSiren.get(), ModItems.sirenTrackAutopilotDisconnected.get(),
            ModItems.sirenTrackAMSSiren.get(), ModItems.sirenTrackBlastDoorAlarm.get(),
            ModItems.sirenTrackAPCSiren.get(), ModItems.sirenTrackKlaxon.get(),
            ModItems.sirenTrackVaultDoorAlarm.get(), ModItems.sirenTrackSecurityAlert.get(),
            ModItems.sirenTrackStandardSiren.get(), ModItems.sirenTrackClassicSiren.get(),
            ModItems.sirenTrackBankAlarm.get(), ModItems.sirenTrackBeepSiren.get(),
            ModItems.sirenTrackContainerAlarm.get(), ModItems.sirenTrackSweepSiren.get(),
            ModItems.sirenTrackMissileSiloSiren.get(), ModItems.sirenTrackAirRaidSiren.get(),
            ModItems.sirenTrackNostromoSelfDestruct.get(), ModItems.sirenTrackEASAlarmScreech.get(),
            ModItems.sirenTrackAPCPass.get(), ModItems.sirenTrackRazortrainHorn.get(),
        )
        tag(NuclearTags.Items.FLAT_STAMPS).add(
            ModItems.stoneFlatStamp.get(),
            ModItems.ironFlatStamp.get(),
            ModItems.steelFlatStamp.get(),
            ModItems.titaniumFlatStamp.get(),
            ModItems.obsidianFlatStamp.get(),
            ModItems.schrabidiumFlatStamp.get()
        )
        tag(NuclearTags.Items.PLATE_STAMPS).add(
            ModItems.stonePlateStamp.get(),
            ModItems.ironPlateStamp.get(),
            ModItems.steelPlateStamp.get(),
            ModItems.titaniumPlateStamp.get(),
            ModItems.obsidianPlateStamp.get(),
            ModItems.schrabidiumPlateStamp.get()
        )
        tag(NuclearTags.Items.WIRE_STAMPS).add(
            ModItems.stoneWireStamp.get(),
            ModItems.ironWireStamp.get(),
            ModItems.steelWireStamp.get(),
            ModItems.titaniumWireStamp.get(),
            ModItems.obsidianWireStamp.get(),
            ModItems.schrabidiumWireStamp.get()
        )
        tag(NuclearTags.Items.CIRCUIT_STAMPS).add(
            ModItems.stoneCircuitStamp.get(),
            ModItems.ironCircuitStamp.get(),
            ModItems.steelCircuitStamp.get(),
            ModItems.titaniumCircuitStamp.get(),
            ModItems.obsidianCircuitStamp.get(),
            ModItems.schrabidiumCircuitStamp.get()
        )
        tag(NuclearTags.Items.FOLDER_STAMPS)
            .addTag(NuclearTags.Items.PLATE_STAMPS)
            .addTag(NuclearTags.Items.WIRE_STAMPS)
            .addTag(NuclearTags.Items.CIRCUIT_STAMPS)
        tag(NuclearTags.Items.STONE_STAMPS).add(
            ModItems.stoneFlatStamp.get(),
            ModItems.stonePlateStamp.get(),
            ModItems.stoneWireStamp.get(),
            ModItems.stoneCircuitStamp.get()
        )
        tag(NuclearTags.Items.IRON_STAMPS).add(
            ModItems.ironFlatStamp.get(),
            ModItems.ironPlateStamp.get(),
            ModItems.ironWireStamp.get(),
            ModItems.ironCircuitStamp.get()
        )
        tag(NuclearTags.Items.STEEL_STAMPS).add(
            ModItems.steelFlatStamp.get(),
            ModItems.steelPlateStamp.get(),
            ModItems.steelWireStamp.get(),
            ModItems.steelCircuitStamp.get()
        )
        tag(NuclearTags.Items.TITANIUM_STAMPS).add(
            ModItems.titaniumFlatStamp.get(),
            ModItems.titaniumPlateStamp.get(),
            ModItems.titaniumWireStamp.get(),
            ModItems.titaniumCircuitStamp.get()
        )
        tag(NuclearTags.Items.OBSIDIAN_STAMPS).add(
            ModItems.obsidianFlatStamp.get(),
            ModItems.obsidianPlateStamp.get(),
            ModItems.obsidianWireStamp.get(),
            ModItems.obsidianCircuitStamp.get()
        )
        tag(NuclearTags.Items.SCHRABIDIUM_STAMPS).add(
            ModItems.schrabidiumFlatStamp.get(),
            ModItems.schrabidiumPlateStamp.get(),
            ModItems.schrabidiumWireStamp.get(),
            ModItems.schrabidiumCircuitStamp.get()
        )
        tag(NuclearTags.Items.STAMPS)
            .addTag(NuclearTags.Items.STONE_STAMPS)
            .addTag(NuclearTags.Items.IRON_STAMPS)
            .addTag(NuclearTags.Items.STEEL_STAMPS)
            .addTag(NuclearTags.Items.TITANIUM_STAMPS)
            .addTag(NuclearTags.Items.OBSIDIAN_STAMPS)
            .addTag(NuclearTags.Items.SCHRABIDIUM_STAMPS)
        tag(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS)
            .addTag(NuclearTags.Items.FOLDER_STAMPS)
            .addTag(NuclearTags.Items.SIREN_TRACKS)
            .add(ModItems.assemblyTemplate.get())
            .add(ModItems.chemTemplate.get())
    }

    private fun modifyVanillaTags() {
        tag(ItemTags.PIGLIN_LOVED)
            .addTag(NuclearTags.Items.DUSTS_GOLD)
            .addTag(NuclearTags.Items.GEMS_GOLD)
            .addTag(NuclearTags.Items.PLATES_GOLD)
            .addTag(NuclearTags.Items.WIRES_GOLD)
            .addTag(NuclearTags.Items.COILS_GOLD)
            .add(ModItems.goldBulletAssembly.get())
    }
}
