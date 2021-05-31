package at.martinthedragon.ntm.datagen

import at.martinthedragon.ntm.*
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper

class ItemTagProvider(
    generator: DataGenerator,
    blockTagProvider: BlockTagProvider,
    existingFileHelper: ExistingFileHelper
) : ItemTagsProvider(generator, blockTagProvider, Main.MODID, existingFileHelper) {

    override fun getName(): String = "Nuclear Tech Mod Item Tags"

    override fun addTags() {
        copyFromBlocks()
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
        modifyVanillaTags()
    }

    private fun copyFromBlocks() {
        copy(NuclearTags.Blocks.ORES_URANIUM, NuclearTags.Items.ORES_URANIUM)
        copy(NuclearTags.Blocks.ORES_THORIUM, NuclearTags.Items.ORES_THORIUM)
        copy(NuclearTags.Blocks.ORES_TITANIUM, NuclearTags.Items.ORES_TITANIUM)
        copy(NuclearTags.Blocks.ORES_SULFUR, NuclearTags.Items.ORES_SULFUR)
        copy(NuclearTags.Blocks.ORES_NITER, NuclearTags.Items.ORES_NITER)
        copy(NuclearTags.Blocks.ORES_COPPER, NuclearTags.Items.ORES_COPPER)
        copy(NuclearTags.Blocks.ORES_TUNGSTEN, NuclearTags.Items.ORES_TUNGSTEN)
        copy(NuclearTags.Blocks.ORES_ALUMINIUM, NuclearTags.Items.ORES_ALUMINIUM)
        copy(NuclearTags.Blocks.ORES_FLUORITE, NuclearTags.Items.ORES_FLUORITE)
        copy(NuclearTags.Blocks.ORES_BERYLLIUM, NuclearTags.Items.ORES_BERYLLIUM)
        copy(NuclearTags.Blocks.ORES_LEAD, NuclearTags.Items.ORES_LEAD)
        copy(NuclearTags.Blocks.ORES_OIL, NuclearTags.Items.ORES_OIL)
        copy(NuclearTags.Blocks.ORES_LIGNITE, NuclearTags.Items.ORES_LIGNITE)
        copy(NuclearTags.Blocks.ORES_ASBESTOS, NuclearTags.Items.ORES_ASBESTOS)
        copy(NuclearTags.Blocks.ORES_RARE_EARTH, NuclearTags.Items.ORES_RARE_EARTH)
        copy(Tags.Blocks.ORES, Tags.Items.ORES)

        copy(NuclearTags.Blocks.STORAGE_BLOCKS_URANIUM, NuclearTags.Items.STORAGE_BLOCKS_URANIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_NEPTUNIUM, NuclearTags.Items.STORAGE_BLOCKS_NEPTUNIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_MOX, NuclearTags.Items.STORAGE_BLOCKS_MOX)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_PLUTONIUM, NuclearTags.Items.STORAGE_BLOCKS_PLUTONIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_THORIUM, NuclearTags.Items.STORAGE_BLOCKS_THORIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_TITANIUM, NuclearTags.Items.STORAGE_BLOCKS_TITANIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SULFUR, NuclearTags.Items.STORAGE_BLOCKS_SULFUR)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_NITER, NuclearTags.Items.STORAGE_BLOCKS_NITER)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_COPPER, NuclearTags.Items.STORAGE_BLOCKS_COPPER)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_TUNGSTEN, NuclearTags.Items.STORAGE_BLOCKS_TUNGSTEN)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_ALUMINIUM, NuclearTags.Items.STORAGE_BLOCKS_ALUMINIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_FLUORITE, NuclearTags.Items.STORAGE_BLOCKS_FLUORITE)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_BERYLLIUM, NuclearTags.Items.STORAGE_BLOCKS_BERYLLIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_COBALT, NuclearTags.Items.STORAGE_BLOCKS_COBALT)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_STEEL, NuclearTags.Items.STORAGE_BLOCKS_STEEL)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_LEAD, NuclearTags.Items.STORAGE_BLOCKS_LEAD)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_LITHIUM, NuclearTags.Items.STORAGE_BLOCKS_LITHIUM)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_PHOSPHORUS, NuclearTags.Items.STORAGE_BLOCKS_PHOSPHORUS)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_YELLOWCAKE, NuclearTags.Items.STORAGE_BLOCKS_YELLOWCAKE)
        copy(NuclearTags.Blocks.STORAGE_BLOCKS_SCRAP, NuclearTags.Items.STORAGE_BLOCKS_SCRAP)
        copy(NuclearTags.Blocks.STORAGE_BLOCK_ASBESTOS, NuclearTags.Items.STORAGE_BLOCK_ASBESTOS)
        copy(NuclearTags.Blocks.STORAGE_BLOCK_NUCLEAR_WASTE, NuclearTags.Items.STORAGE_BLOCK_NUCLEAR_WASTE)
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS)

        copy(NuclearTags.Blocks.SAND_OIL, NuclearTags.Items.SAND_OIL)
        copy(Tags.Blocks.SAND, Tags.Items.SAND)
    }

    private fun ingotTags() {
        tag(Tags.Items.INGOTS)
            .addTag(NuclearTags.Items.INGOTS_URANIUM)
            .addTag(NuclearTags.Items.INGOTS_THORIUM)
            .addTag(NuclearTags.Items.INGOTS_PLUTONIUM)
            .addTag(NuclearTags.Items.INGOTS_NEPTUNIUM)
            .addTag(NuclearTags.Items.INGOTS_POLONIUM)
            .addTag(NuclearTags.Items.INGOTS_TITANIUM)
            .addTag(NuclearTags.Items.INGOTS_COPPER)
            .addTag(NuclearTags.Items.INGOTS_TUNGSTEN)
            .addTag(NuclearTags.Items.INGOTS_ALUMINIUM)
            .addTag(NuclearTags.Items.INGOTS_STEEL)
            .addTag(NuclearTags.Items.INGOTS_LEAD)
            .addTag(NuclearTags.Items.INGOTS_BERYLLIUM)
            .addTag(NuclearTags.Items.INGOTS_COBALT)
            .addTag(NuclearTags.Items.INGOTS_POLYMER)
            .addTag(NuclearTags.Items.INGOTS_LANTHANUM)
            .addTag(NuclearTags.Items.INGOTS_ACTINIUM)
            .addTag(NuclearTags.Items.INGOTS_STARMETAL)
            .addTag(NuclearTags.Items.INGOTS_PHOSPHORUS)
            .addTag(NuclearTags.Items.INGOTS_LITHIUM)
            .add(
                ModItems.redCopperIngot, ModItems.advancedAlloyIngot,
                ModItems.highSpeedSteelIngot, ModItems.schraraniumIngot,
                ModItems.schrabidiumIngot, ModItems.magnetizedTungstenIngot,
                ModItems.combineSteelIngot, ModItems.soliniumIngot,
                ModItems.moxFuelIngot, ModItems.schrabidiumFuelIngot,
                ModItems.highEnrichedSchrabidiumFuelIngot,
                ModItems.lowEnrichedSchrabidiumFuelIngot,
                ModItems.australiumIngot, ModItems.weidaniumIngot,
                ModItems.reiiumIngot, ModItems.unobtainiumIngot,
                ModItems.daffergonIngot, ModItems.verticiumIngot,
                ModItems.deshIngot, ModItems.saturniteIngot,
                ModItems.euphemiumIngot, ModItems.dineutroniumIngot,
                ModItems.electroniumIngot
            )

        tag(NuclearTags.Items.INGOTS_URANIUM).add(
            ModItems.uraniumIngot, ModItems.u233Ingot,
            ModItems.u235Ingot, ModItems.u238Ingot,
            ModItems.uraniumFuelIngot
        )
        tag(NuclearTags.Items.INGOTS_THORIUM).add(ModItems.th232Ingot, ModItems.thoriumFuelIngot)
        tag(NuclearTags.Items.INGOTS_PLUTONIUM).add(
            ModItems.plutoniumIngot, ModItems.pu238Ingot,
            ModItems.pu239Ingot, ModItems.pu240Ingot,
            ModItems.plutoniumFuelIngot
        )
        tag(NuclearTags.Items.INGOTS_NEPTUNIUM).add(ModItems.neptuniumIngot)
        tag(NuclearTags.Items.INGOTS_POLONIUM).add(ModItems.poloniumIngot)
        tag(NuclearTags.Items.INGOTS_TITANIUM).add(ModItems.titaniumIngot)
        tag(NuclearTags.Items.INGOTS_COPPER).add(ModItems.copperIngot)
        tag(NuclearTags.Items.INGOTS_TUNGSTEN).add(ModItems.tungstenIngot)
        tag(NuclearTags.Items.INGOTS_ALUMINIUM).add(ModItems.aluminiumIngot)
        tag(NuclearTags.Items.INGOTS_STEEL).add(ModItems.steelIngot)
        tag(NuclearTags.Items.INGOTS_LEAD).add(ModItems.leadIngot)
        tag(NuclearTags.Items.INGOTS_BERYLLIUM).add(ModItems.berylliumIngot)
        tag(NuclearTags.Items.INGOTS_COBALT).add(ModItems.cobaltIngot)
        tag(NuclearTags.Items.INGOTS_POLYMER).add(ModItems.polymerIngot)
        tag(NuclearTags.Items.INGOTS_LANTHANUM).add(ModItems.lanthanumIngot)
        tag(NuclearTags.Items.INGOTS_ACTINIUM).add(ModItems.actiniumIngot)
        tag(NuclearTags.Items.INGOTS_STARMETAL).add(ModItems.starmetalIngot)
        tag(NuclearTags.Items.INGOTS_PHOSPHORUS).add(ModItems.whitePhosphorusIngot)
        tag(NuclearTags.Items.INGOTS_LITHIUM).add(ModItems.lithiumCube)
    }

    private fun dustTags() {
        tag(Tags.Items.DUSTS)
            .addTag(NuclearTags.Items.DUSTS_SULFUR)
            .addTag(NuclearTags.Items.DUSTS_NITER)
            .addTag(NuclearTags.Items.DUSTS_FLUORITE)
            .addTag(NuclearTags.Items.DUSTS_COAL)
            .addTag(NuclearTags.Items.DUSTS_IRON)
            .addTag(NuclearTags.Items.DUSTS_GOLD)
            .addTag(NuclearTags.Items.DUSTS_LAPIS)
            .addTag(NuclearTags.Items.DUSTS_QUARTZ)
            .addTag(NuclearTags.Items.DUSTS_DIAMOND)
            .addTag(NuclearTags.Items.DUSTS_EMERALD)
            .addTag(NuclearTags.Items.DUSTS_URANIUM)
            .addTag(NuclearTags.Items.DUSTS_THORIUM)
            .addTag(NuclearTags.Items.DUSTS_PLUTONIUM)
            .addTag(NuclearTags.Items.DUSTS_NEPTUNIUM)
            .addTag(NuclearTags.Items.DUSTS_POLONIUM)
            .addTag(NuclearTags.Items.DUSTS_TITANIUM)
            .addTag(NuclearTags.Items.DUSTS_COPPER)
            .addTag(NuclearTags.Items.DUSTS_TUNGSTEN)
            .addTag(NuclearTags.Items.DUSTS_ALUMINIUM)
            .addTag(NuclearTags.Items.DUSTS_STEEL)
            .addTag(NuclearTags.Items.DUSTS_LEAD)
            .addTag(NuclearTags.Items.DUSTS_BERYLLIUM)
            .addTag(NuclearTags.Items.DUSTS_POLYMER)
            .addTag(NuclearTags.Items.DUSTS_LITHIUM)
            .addTag(NuclearTags.Items.DUSTS_NEODYMIUM)
            .addTag(NuclearTags.Items.DUSTS_COBALT)
            .addTag(NuclearTags.Items.DUSTS_NIOBIUM)
            .addTag(NuclearTags.Items.DUSTS_CERIUM)
            .addTag(NuclearTags.Items.DUSTS_LANTHANUM)
            .addTag(NuclearTags.Items.DUSTS_ACTINIUM)
            .addTag(NuclearTags.Items.DUSTS_ASBESTOS)
            .addTag(NuclearTags.Items.DUSTS_ENCHANTMENT)
            .addTag(NuclearTags.Items.DUSTS_METEORITE)
            .addTag(NuclearTags.Items.DUSTS_DUST)
            .addTag(NuclearTags.Items.DUSTS_PHOSPHORUS)
            .addTag(NuclearTags.Items.DUSTS_COLD)
            .addTag(NuclearTags.Items.DUSTS_POISON)
            .addTag(NuclearTags.Items.DUSTS_THERMITE)
            .addTag(NuclearTags.Items.DUSTS_ENERGY)
            .addTag(NuclearTags.Items.DUSTS_CORDITE)
            .addTag(NuclearTags.Items.DUSTS_BALLISTITE)
            .add(
                ModItems.redCopperPowder, ModItems.advancedAlloyPowder,
                ModItems.schrabidiumPowder, ModItems.magnetizedTungstenPowder,
                ModItems.chlorophytePowder, ModItems.combineSteelPowder,
                ModItems.lignitePowder, ModItems.australiumPowder,
                ModItems.weidaniumPowder, ModItems.reiiumPowder,
                ModItems.unobtainiumPowder, ModItems.daffergonPowder,
                ModItems.verticiumPowder, ModItems.cloudResidue,
                ModItems.thermonuclearAshes, ModItems.semtexMix,
                ModItems.deshMix, ModItems.deshReadyMix,
                ModItems.deshPowder, ModItems.nitaniumMix,
                ModItems.sparkMix, ModItems.euphemiumPowder,
                ModItems.dineutroniumPowder, ModItems.desaturatedRedstone
            )

        tag(NuclearTags.Items.DUSTS_SULFUR).add(ModItems.sulfur)
        tag(NuclearTags.Items.DUSTS_NITER).add(ModItems.niter)
        tag(NuclearTags.Items.DUSTS_FLUORITE).add(ModItems.fluorite)
        tag(NuclearTags.Items.DUSTS_COAL).add(ModItems.coalPowder)
        tag(NuclearTags.Items.DUSTS_IRON).add(ModItems.ironPowder)
        tag(NuclearTags.Items.DUSTS_GOLD).add(ModItems.goldPowder)
        tag(NuclearTags.Items.DUSTS_LAPIS).add(ModItems.lapisLazuliPowder)
        tag(NuclearTags.Items.DUSTS_QUARTZ).add(ModItems.quartzPowder)
        tag(NuclearTags.Items.DUSTS_DIAMOND).add(ModItems.diamondPowder)
        tag(NuclearTags.Items.DUSTS_EMERALD).add(ModItems.emeraldPowder)
        tag(NuclearTags.Items.DUSTS_URANIUM).add(ModItems.uraniumPowder)
        tag(NuclearTags.Items.DUSTS_THORIUM).add(ModItems.thoriumPowder)
        tag(NuclearTags.Items.DUSTS_PLUTONIUM).add(ModItems.plutoniumPowder)
        tag(NuclearTags.Items.DUSTS_NEPTUNIUM).add(ModItems.neptuniumPowder)
        tag(NuclearTags.Items.DUSTS_POLONIUM).add(ModItems.poloniumPowder)
        tag(NuclearTags.Items.DUSTS_TITANIUM).add(ModItems.titaniumPowder)
        tag(NuclearTags.Items.DUSTS_COPPER).add(ModItems.copperPowder)
        tag(NuclearTags.Items.DUSTS_TUNGSTEN).add(ModItems.tungstenPowder)
        tag(NuclearTags.Items.DUSTS_ALUMINIUM).add(ModItems.aluminiumPowder)
        tag(NuclearTags.Items.DUSTS_STEEL).add(ModItems.steelPowder)
        tag(NuclearTags.Items.DUSTS_LEAD).add(ModItems.leadPowder)
        tag(NuclearTags.Items.DUSTS_BERYLLIUM).add(ModItems.berylliumPowder)
        tag(NuclearTags.Items.DUSTS_POLYMER).add(ModItems.polymerPowder)
        tag(NuclearTags.Items.DUSTS_LITHIUM).add(ModItems.lithiumPowder)
        tag(NuclearTags.Items.DUSTS_NEODYMIUM).add(ModItems.neodymiumPowder)
        tag(NuclearTags.Items.DUSTS_COBALT).add(ModItems.cobaltPowder)
        tag(NuclearTags.Items.DUSTS_NIOBIUM).add(ModItems.niobiumPowder)
        tag(NuclearTags.Items.DUSTS_CERIUM).add(ModItems.ceriumPowder)
        tag(NuclearTags.Items.DUSTS_LANTHANUM).add(ModItems.lanthanumPowder)
        tag(NuclearTags.Items.DUSTS_ACTINIUM).add(ModItems.actiniumPowder)
        tag(NuclearTags.Items.DUSTS_ASBESTOS).add(ModItems.asbestosPowder)
        tag(NuclearTags.Items.DUSTS_ENCHANTMENT).add(ModItems.enchantmentPowder)
        tag(NuclearTags.Items.DUSTS_METEORITE).add(ModItems.meteoritePowder)
        tag(NuclearTags.Items.DUSTS_DUST).add(ModItems.dust)
        tag(NuclearTags.Items.DUSTS_PHOSPHORUS).add(ModItems.redPhosphorus)
        tag(NuclearTags.Items.DUSTS_COLD).add(ModItems.cryoPowder)
        tag(NuclearTags.Items.DUSTS_POISON).add(ModItems.poisonPowder)
        tag(NuclearTags.Items.DUSTS_THERMITE).add(ModItems.thermite)
        tag(NuclearTags.Items.DUSTS_ENERGY).add(ModItems.energyPowder)
        tag(NuclearTags.Items.DUSTS_CORDITE).add(ModItems.cordite)
        tag(NuclearTags.Items.DUSTS_BALLISTITE).add(ModItems.ballistite)
    }

    private fun crystalTags() {
        tag(Tags.Items.GEMS)
            .addTag(NuclearTags.Items.GEMS_IRON)
            .addTag(NuclearTags.Items.GEMS_GOLD)
            .addTag(NuclearTags.Items.GEMS_REDSTONE)
            .addTag(NuclearTags.Items.GEMS_DIAMOND)
            .addTag(NuclearTags.Items.GEMS_URANIUM)
            .addTag(NuclearTags.Items.GEMS_THORIUM)
            .addTag(NuclearTags.Items.GEMS_PLUTONIUM)
            .addTag(NuclearTags.Items.GEMS_TITANIUM)
            .addTag(NuclearTags.Items.GEMS_SULFUR)
            .addTag(NuclearTags.Items.GEMS_NITER)
            .addTag(NuclearTags.Items.GEMS_COPPER)
            .addTag(NuclearTags.Items.GEMS_TUNGSTEN)
            .addTag(NuclearTags.Items.GEMS_ALUMINIUM)
            .addTag(NuclearTags.Items.GEMS_FLUORITE)
            .addTag(NuclearTags.Items.GEMS_FLUORITE)
            .addTag(NuclearTags.Items.GEMS_BERYLLIUM)
            .addTag(NuclearTags.Items.GEMS_LEAD)
            .addTag(NuclearTags.Items.GEMS_RARE_EARTH)
            .addTag(NuclearTags.Items.GEMS_PHOSPHORUS)
            .addTag(NuclearTags.Items.GEMS_LITHIUM)
            .addTag(NuclearTags.Items.GEMS_STARMETAL)
            .addTag(NuclearTags.Items.GEMS_TRIXITE)
            .addTag(NuclearTags.Items.GEMS_NEODYMIUM)
            .addTag(NuclearTags.Items.GEMS_COBALT)
            .addTag(NuclearTags.Items.GEMS_NIOBIUM)
            .addTag(NuclearTags.Items.GEMS_CERIUM)
            .addTag(NuclearTags.Items.GEMS_LANTHANUM)
            .addTag(NuclearTags.Items.GEMS_ACTINIUM)
            .add(ModItems.schraraniumCrystals, ModItems.schrabidiumCrystals)

        tag(NuclearTags.Items.GEMS_IRON).add(ModItems.ironCrystals)
        tag(NuclearTags.Items.GEMS_GOLD).add(ModItems.goldCrystals)
        tag(NuclearTags.Items.GEMS_REDSTONE).add(ModItems.redstoneCrystals)
        tag(NuclearTags.Items.GEMS_DIAMOND).add(ModItems.diamondCrystals)
        tag(NuclearTags.Items.GEMS_URANIUM).add(ModItems.uraniumCrystals)
        tag(NuclearTags.Items.GEMS_THORIUM).add(ModItems.thoriumCrystals)
        tag(NuclearTags.Items.GEMS_PLUTONIUM).add(ModItems.plutoniumCrystals)
        tag(NuclearTags.Items.GEMS_TITANIUM).add(ModItems.titaniumCrystals)
        tag(NuclearTags.Items.GEMS_SULFUR).add(ModItems.sulfurCrystals)
        tag(NuclearTags.Items.GEMS_NITER).add(ModItems.niterCrystals)
        tag(NuclearTags.Items.GEMS_COPPER).add(ModItems.copperCrystals)
        tag(NuclearTags.Items.GEMS_TUNGSTEN).add(ModItems.tungstenCrystals)
        tag(NuclearTags.Items.GEMS_ALUMINIUM).add(ModItems.aluminiumCrystals)
        tag(NuclearTags.Items.GEMS_FLUORITE).add(ModItems.fluoriteCrystals)
        tag(NuclearTags.Items.GEMS_BERYLLIUM).add(ModItems.berylliumCrystals)
        tag(NuclearTags.Items.GEMS_LEAD).add(ModItems.leadCrystals)
        tag(NuclearTags.Items.GEMS_RARE_EARTH).add(ModItems.rareEarthCrystals)
        tag(NuclearTags.Items.GEMS_PHOSPHORUS).add(ModItems.redPhosphorusCrystals)
        tag(NuclearTags.Items.GEMS_LITHIUM).add(ModItems.lithiumCrystals)
        tag(NuclearTags.Items.GEMS_STARMETAL).add(ModItems.starmetalCrystals)
        tag(NuclearTags.Items.GEMS_TRIXITE).add(ModItems.trixiteCrystals)

        tag(NuclearTags.Items.GEMS_NEODYMIUM).add(ModItems.neodymiumFragment)
        tag(NuclearTags.Items.GEMS_COBALT).add(ModItems.cobaltFragment)
        tag(NuclearTags.Items.GEMS_NIOBIUM).add(ModItems.niobiumFragment)
        tag(NuclearTags.Items.GEMS_CERIUM).add(ModItems.ceriumFragment)
        tag(NuclearTags.Items.GEMS_LANTHANUM).add(ModItems.lanthanumFragment)
        tag(NuclearTags.Items.GEMS_ACTINIUM).add(ModItems.actiniumFragment)
    }

    private fun nuggetTags() {
        tag(Tags.Items.NUGGETS)
            .addTag(NuclearTags.Items.NUGGETS_URANIUM)
            .addTag(NuclearTags.Items.NUGGETS_THORIUM)
            .addTag(NuclearTags.Items.NUGGETS_PLUTONIUM)
            .addTag(NuclearTags.Items.NUGGETS_NEPTUNIUM)
            .addTag(NuclearTags.Items.NUGGETS_POLONIUM)
            .addTag(NuclearTags.Items.NUGGETS_LEAD)
            .addTag(NuclearTags.Items.NUGGETS_BERYLLIUM)
            .addTag(NuclearTags.Items.NUGGETS_MERCURY)
            .add(
                ModItems.schrabidiumNugget, ModItems.soliniumNugget,
                ModItems.moxFuelNugget, ModItems.schrabidiumFuelNugget,
                ModItems.highEnrichedSchrabidiumFuelNugget,
                ModItems.lowEnrichedSchrabidiumFuelNugget,
                ModItems.australiumNugget, ModItems.weidaniumNugget,
                ModItems.reiiumNugget, ModItems.unobtainiumNugget,
                ModItems.daffergonNugget, ModItems.verticiumNugget,
                ModItems.deshNugget, ModItems.euphemiumNugget,
                ModItems.dineutroniumNugget
            )

        tag(NuclearTags.Items.NUGGETS_URANIUM).add(
            ModItems.uraniumNugget, ModItems.u233Nugget,
            ModItems.u235Nugget, ModItems.u238Nugget,
            ModItems.uraniumFuelNugget
        )
        tag(NuclearTags.Items.NUGGETS_THORIUM).add(ModItems.th232Nugget, ModItems.thoriumFuelNugget)
        tag(NuclearTags.Items.NUGGETS_PLUTONIUM).add(
            ModItems.plutoniumNugget, ModItems.pu238Nugget,
            ModItems.pu239Nugget, ModItems.pu240Nugget,
            ModItems.plutoniumFuelNugget
        )
        tag(NuclearTags.Items.NUGGETS_NEPTUNIUM).add(ModItems.neptuniumNugget)
        tag(NuclearTags.Items.NUGGETS_POLONIUM).add(ModItems.poloniumNugget)
        tag(NuclearTags.Items.NUGGETS_LEAD).add(ModItems.leadNugget)
        tag(NuclearTags.Items.NUGGETS_BERYLLIUM).add(ModItems.berylliumNugget)
        tag(NuclearTags.Items.NUGGETS_MERCURY).add(ModItems.mercuryDroplet)
    }

    private fun plateTags() {
        tag(NuclearTags.Items.PLATES)
            .addTag(NuclearTags.Items.PLATES_IRON)
            .addTag(NuclearTags.Items.PLATES_GOLD)
            .addTag(NuclearTags.Items.PLATES_TITANIUM)
            .addTag(NuclearTags.Items.PLATES_ALUMINIUM)
            .addTag(NuclearTags.Items.PLATES_STEEL)
            .addTag(NuclearTags.Items.PLATES_LEAD)
            .addTag(NuclearTags.Items.PLATES_COPPER)
            .addTag(NuclearTags.Items.PLATES_NEUTRON_REFLECTOR)
            .addTag(NuclearTags.Items.PLATES_INSULATOR)
            .addTag(NuclearTags.Items.PLATES_KEVLAR)
            .add(
                ModItems.advancedAlloyPlate, ModItems.schrabidiumPlate,
                ModItems.combineSteelPlate, ModItems.mixedPlate,
                ModItems.saturnitePlate, ModItems.paAAlloyPlate,
                ModItems.dalekaniumPlate, ModItems.deshCompoundPlate,
                ModItems.euphemiumCompoundPlate, ModItems.dineutroniumCompoundPlate
            )

        tag(NuclearTags.Items.PLATES_IRON).add(ModItems.ironPlate)
        tag(NuclearTags.Items.PLATES_GOLD).add(ModItems.goldPlate)
        tag(NuclearTags.Items.PLATES_TITANIUM).add(ModItems.titaniumPlate)
        tag(NuclearTags.Items.PLATES_ALUMINIUM).add(ModItems.aluminiumPlate)
        tag(NuclearTags.Items.PLATES_STEEL).add(ModItems.steelPlate)
        tag(NuclearTags.Items.PLATES_LEAD).add(ModItems.leadPlate)
        tag(NuclearTags.Items.PLATES_COPPER).add(ModItems.copperPlate, ModItems.copperPanel)
        tag(NuclearTags.Items.PLATES_NEUTRON_REFLECTOR).add(ModItems.neutronReflector)
        tag(NuclearTags.Items.PLATES_INSULATOR).add(ModItems.insulator)
        tag(NuclearTags.Items.PLATES_KEVLAR).add(ModItems.kevlarCeramicCompound)
    }

    private fun wireTags() {
        tag(NuclearTags.Items.WIRES)
            .addTag(NuclearTags.Items.WIRES_ALUMINIUM)
            .addTag(NuclearTags.Items.WIRES_COPPER)
            .addTag(NuclearTags.Items.WIRES_TUNGSTEN)
            .addTag(NuclearTags.Items.WIRES_SUPER_CONDUCTOR)
            .addTag(NuclearTags.Items.WIRES_GOLD)
            .add(ModItems.redCopperWire)

        tag(NuclearTags.Items.WIRES_ALUMINIUM).add(ModItems.aluminiumWire)
        tag(NuclearTags.Items.WIRES_COPPER).add(ModItems.copperWire)
        tag(NuclearTags.Items.WIRES_TUNGSTEN).add(ModItems.tungstenWire)
        tag(NuclearTags.Items.WIRES_SUPER_CONDUCTOR).add(ModItems.superConductor, ModItems.highTemperatureSuperConductor)
        tag(NuclearTags.Items.WIRES_GOLD).add(ModItems.goldWire)
    }

    private fun coilTags() {
        tag(NuclearTags.Items.COILS)
            .addTag(NuclearTags.Items.COILS_COPPER)
            .addTag(NuclearTags.Items.COILS_SUPER_CONDUCTOR)
            .addTag(NuclearTags.Items.COILS_GOLD)

        tag(NuclearTags.Items.COILS_COPPER).add(ModItems.copperCoil, ModItems.ringCoil)
        tag(NuclearTags.Items.COILS_SUPER_CONDUCTOR).add(
            ModItems.superConductingCoil, ModItems.superConductingRingCoil,
            ModItems.heatingCoil, ModItems.highTemperatureSuperConductingCoil
        )
        tag(NuclearTags.Items.COILS_GOLD).add(ModItems.goldCoil, ModItems.goldRingCoil)
    }

    private fun fissileFuelTags() {
        tag(NuclearTags.Items.FISSILE_FUELS)
            .addTag(NuclearTags.Items.FISSILE_FUELS_URANIUM)
            .addTag(NuclearTags.Items.FISSILE_FUELS_THORIUM)
            .addTag(NuclearTags.Items.FISSILE_FUELS_PLUTONIUM)
            .addTag(NuclearTags.Items.FISSILE_FUELS_MOX)
            .add(
                ModItems.schrabidiumFuelIngot,
                ModItems.highEnrichedSchrabidiumFuelIngot,
                ModItems.lowEnrichedSchrabidiumFuelIngot
            )

        tag(NuclearTags.Items.FISSILE_FUELS_URANIUM).add(ModItems.uraniumFuelIngot, ModItems.uraniumFuelNugget)
        tag(NuclearTags.Items.FISSILE_FUELS_THORIUM).add(ModItems.thoriumFuelIngot, ModItems.thoriumFuelNugget)
        tag(NuclearTags.Items.FISSILE_FUELS_PLUTONIUM).add(ModItems.plutoniumFuelIngot, ModItems.plutoniumFuelNugget)
        tag(NuclearTags.Items.FISSILE_FUELS_MOX).add(ModItems.moxFuelIngot, ModItems.moxFuelNugget)
    }

    private fun wasteTags() {
        tag(NuclearTags.Items.NUCLEAR_WASTE).add(
            ModItems.nuclearWaste, ModItems.depletedUraniumFuel,
            ModItems.depletedThoriumFuel, ModItems.depletedPlutoniumFuel,
            ModItems.depletedMOXFuel, ModItems.depletedSchrabidiumFuel,
            ModItems.hotDepletedUraniumFuel, ModItems.hotDepletedThoriumFuel,
            ModItems.hotDepletedPlutoniumFuel, ModItems.hotDepletedMOXFuel,
            ModItems.hotDepletedSchrabidiumFuel
        )

        tag(NuclearTags.Items.WASTES)
            .addTag(NuclearTags.Items.WASTES_SCRAP)
            .addTag(NuclearTags.Items.WASTES_NUCLEAR_WASTE)

        tag(NuclearTags.Items.WASTES_SCRAP).add(ModItems.scrap)
        tag(NuclearTags.Items.WASTES_NUCLEAR_WASTE)
            .addTag(NuclearTags.Items.WASTES_HOT_URANIUM)
            .addTag(NuclearTags.Items.WASTES_HOT_THORIUM)
            .addTag(NuclearTags.Items.WASTES_HOT_PLUTONIUM)
            .addTag(NuclearTags.Items.WASTES_HOT_MOX)
            .addTag(NuclearTags.Items.WASTES_URANIUM)
            .addTag(NuclearTags.Items.WASTES_THORIUM)
            .addTag(NuclearTags.Items.WASTES_PLUTONIUM)
            .addTag(NuclearTags.Items.WASTES_MOX)
            .add(ModItems.nuclearWaste)

        tag(NuclearTags.Items.WASTES_HOT_URANIUM).add(ModItems.hotDepletedUraniumFuel)
        tag(NuclearTags.Items.WASTES_HOT_THORIUM).add(ModItems.hotDepletedThoriumFuel)
        tag(NuclearTags.Items.WASTES_HOT_PLUTONIUM).add(ModItems.hotDepletedPlutoniumFuel)
        tag(NuclearTags.Items.WASTES_HOT_MOX).add(ModItems.hotDepletedMOXFuel)
        tag(NuclearTags.Items.WASTES_URANIUM).add(ModItems.depletedUraniumFuel)
        tag(NuclearTags.Items.WASTES_THORIUM).add(ModItems.depletedThoriumFuel)
        tag(NuclearTags.Items.WASTES_PLUTONIUM).add(ModItems.depletedPlutoniumFuel)
        tag(NuclearTags.Items.WASTES_MOX).add(ModItems.depletedMOXFuel)

        tag(NuclearTags.Items.HOT_WASTES).add(
            ModItems.hotDepletedUraniumFuel, ModItems.hotDepletedThoriumFuel,
            ModItems.hotDepletedPlutoniumFuel, ModItems.hotDepletedMOXFuel,
            ModItems.hotDepletedSchrabidiumFuel
        )
        tag(NuclearTags.Items.COLD_WASTES).add(
            ModItems.depletedUraniumFuel,
            ModItems.depletedThoriumFuel, ModItems.depletedPlutoniumFuel,
            ModItems.depletedMOXFuel, ModItems.depletedSchrabidiumFuel,
        )
    }

    private fun miscTags() {
        tag(NuclearTags.Items.COKE).add(ModItems.coke)

        tag(NuclearTags.Items.YELLOWCAKE_URANIUM).add(ModItems.yellowcake)

        tag(NuclearTags.Items.ORE_CRYSTALS).add(
            ModItems.ironCrystals, ModItems.goldCrystals,
            ModItems.redstoneCrystals, ModItems.diamondCrystals,
            ModItems.uraniumCrystals, ModItems.thoriumCrystals,
            ModItems.plutoniumCrystals, ModItems.titaniumCrystals,
            ModItems.sulfurCrystals, ModItems.niterCrystals,
            ModItems.copperCrystals, ModItems.tungstenCrystals,
            ModItems.aluminiumCrystals, ModItems.fluoriteCrystals,
            ModItems.berylliumCrystals, ModItems.leadCrystals,
            ModItems.schraraniumCrystals, ModItems.schrabidiumCrystals,
            ModItems.rareEarthCrystals, ModItems.redPhosphorusCrystals,
            ModItems.lithiumCrystals, ModItems.starmetalCrystals,
            ModItems.trixiteCrystals
        )

        tag(NuclearTags.Items.RARE_EARTH_FRAGMENTS).add(
            ModItems.neodymiumFragment, ModItems.cobaltFragment,
            ModItems.niobiumFragment, ModItems.ceriumFragment,
            ModItems.lanthanumFragment, ModItems.actiniumFragment,
        )

        tag(NuclearTags.Items.BIOMASS).add(ModItems.biomass).addTag(NuclearTags.Items.COMPRESSED_BIOMASS)
        tag(NuclearTags.Items.COMPRESSED_BIOMASS).add(ModItems.compressedBiomass)

        tag(NuclearTags.Items.BOLTS).addTag(NuclearTags.Items.BOLTS_TUNGSTEN)
        tag(NuclearTags.Items.BOLTS_TUNGSTEN).add(ModItems.tungstenBolt)

        tag(NuclearTags.Items.FABRIC_HAZMAT).add(ModItems.hazmatCloth, ModItems.advancedHazmatCloth, ModItems.leadReinforcedHazmatCloth)

        tag(NuclearTags.Items.FILTERS).addTag(NuclearTags.Items.FILTERS_CARBON)
        tag(NuclearTags.Items.FILTERS_CARBON).add(ModItems.activatedCarbonFilter)

        tag(NuclearTags.Items.SCRAP).add(ModItems.scrap)

        tag(NuclearTags.Items.SIREN_TRACKS).add(
            ModItems.sirenTrackHatchSiren, ModItems.sirenTrackAutopilotDisconnected,
            ModItems.sirenTrackAMSSiren, ModItems.sirenTrackBlastDoorAlarm,
            ModItems.sirenTrackAPCSiren, ModItems.sirenTrackKlaxon,
            ModItems.sirenTrackVaultDoorAlarm, ModItems.sirenTrackSecurityAlert,
            ModItems.sirenTrackStandardSiren, ModItems.sirenTrackClassicSiren,
            ModItems.sirenTrackBankAlarm, ModItems.sirenTrackBeepSiren,
            ModItems.sirenTrackContainerAlarm, ModItems.sirenTrackSweepSiren,
            ModItems.sirenTrackMissileSiloSiren, ModItems.sirenTrackAirRaidSiren,
            ModItems.sirenTrackNostromoSelfDestruct, ModItems.sirenTrackEASAlarmScreech,
            ModItems.sirenTrackAPCPass, ModItems.sirenTrackRazortrainHorn,
        )
    }

    private fun modifyVanillaTags() {
        tag(ItemTags.PIGLIN_LOVED)
            .addTag(NuclearTags.Items.DUSTS_GOLD)
            .addTag(NuclearTags.Items.GEMS_GOLD)
            .addTag(NuclearTags.Items.PLATES_GOLD)
            .addTag(NuclearTags.Items.WIRES_GOLD)
            .addTag(NuclearTags.Items.COILS_GOLD)
            .add(ModItems.goldBulletAssembly)
    }
}
