package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.*
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
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

    private fun copyFromBlocks() {
        copy(NuclearTags.Blocks.ORES_URANIUM, NuclearTags.Items.ORES_URANIUM)
        copy(NuclearTags.Blocks.ORES_THORIUM, NuclearTags.Items.ORES_THORIUM)
        copy(NuclearTags.Blocks.ORES_PLUTONIUM, NuclearTags.Items.ORES_PLUTONIUM)
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
        copy(NuclearTags.Blocks.ORES_TRINITITE, NuclearTags.Items.ORES_TRINITITE)
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
        copy(NuclearTags.Blocks.GLOWING_MUSHROOM_GROW_BLOCK, NuclearTags.Items.GLOWING_MUSHROOM_GROW_BLOCK)
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
                ModItems.redCopperIngot.get(), ModItems.advancedAlloyIngot.get(),
                ModItems.highSpeedSteelIngot.get(), ModItems.schraraniumIngot.get(),
                ModItems.schrabidiumIngot.get(), ModItems.magnetizedTungstenIngot.get(),
                ModItems.combineSteelIngot.get(), ModItems.soliniumIngot.get(),
                ModItems.moxFuelIngot.get(), ModItems.schrabidiumFuelIngot.get(),
                ModItems.highEnrichedSchrabidiumFuelIngot.get(),
                ModItems.lowEnrichedSchrabidiumFuelIngot.get(),
                ModItems.australiumIngot.get(), ModItems.weidaniumIngot.get(),
                ModItems.reiiumIngot.get(), ModItems.unobtainiumIngot.get(),
                ModItems.daffergonIngot.get(), ModItems.verticiumIngot.get(),
                ModItems.deshIngot.get(), ModItems.saturniteIngot.get(),
                ModItems.euphemiumIngot.get(), ModItems.dineutroniumIngot.get(),
                ModItems.electroniumIngot.get()
            )

        tag(NuclearTags.Items.INGOTS_URANIUM).add(
            ModItems.uraniumIngot.get(), ModItems.u233Ingot.get(),
            ModItems.u235Ingot.get(), ModItems.u238Ingot.get(),
            ModItems.uraniumFuelIngot.get()
        )
        tag(NuclearTags.Items.INGOTS_THORIUM).add(ModItems.th232Ingot.get(), ModItems.thoriumFuelIngot.get())
        tag(NuclearTags.Items.INGOTS_PLUTONIUM).add(
            ModItems.plutoniumIngot.get(), ModItems.pu238Ingot.get(),
            ModItems.pu239Ingot.get(), ModItems.pu240Ingot.get(),
            ModItems.plutoniumFuelIngot.get()
        )
        tag(NuclearTags.Items.INGOTS_NEPTUNIUM).add(ModItems.neptuniumIngot.get())
        tag(NuclearTags.Items.INGOTS_POLONIUM).add(ModItems.poloniumIngot.get())
        tag(NuclearTags.Items.INGOTS_TITANIUM).add(ModItems.titaniumIngot.get())
        tag(NuclearTags.Items.INGOTS_COPPER).add(ModItems.copperIngot.get())
        tag(NuclearTags.Items.INGOTS_TUNGSTEN).add(ModItems.tungstenIngot.get())
        tag(NuclearTags.Items.INGOTS_ALUMINIUM).add(ModItems.aluminiumIngot.get())
        tag(NuclearTags.Items.INGOTS_STEEL).add(ModItems.steelIngot.get())
        tag(NuclearTags.Items.INGOTS_LEAD).add(ModItems.leadIngot.get())
        tag(NuclearTags.Items.INGOTS_BERYLLIUM).add(ModItems.berylliumIngot.get())
        tag(NuclearTags.Items.INGOTS_COBALT).add(ModItems.cobaltIngot.get())
        tag(NuclearTags.Items.INGOTS_POLYMER).add(ModItems.polymerIngot.get())
        tag(NuclearTags.Items.INGOTS_LANTHANUM).add(ModItems.lanthanumIngot.get())
        tag(NuclearTags.Items.INGOTS_ACTINIUM).add(ModItems.actiniumIngot.get())
        tag(NuclearTags.Items.INGOTS_STARMETAL).add(ModItems.starmetalIngot.get())
        tag(NuclearTags.Items.INGOTS_PHOSPHORUS).add(ModItems.whitePhosphorusIngot.get())
        tag(NuclearTags.Items.INGOTS_LITHIUM).add(ModItems.lithiumCube.get())
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
                ModItems.redCopperPowder.get(), ModItems.advancedAlloyPowder.get(),
                ModItems.schrabidiumPowder.get(), ModItems.magnetizedTungstenPowder.get(),
                ModItems.chlorophytePowder.get(), ModItems.combineSteelPowder.get(),
                ModItems.lignitePowder.get(), ModItems.australiumPowder.get(),
                ModItems.weidaniumPowder.get(), ModItems.reiiumPowder.get(),
                ModItems.unobtainiumPowder.get(), ModItems.daffergonPowder.get(),
                ModItems.verticiumPowder.get(), ModItems.cloudResidue.get(),
                ModItems.thermonuclearAshes.get(), ModItems.semtexMix.get(),
                ModItems.deshMix.get(), ModItems.deshReadyMix.get(),
                ModItems.deshPowder.get(), ModItems.nitaniumMix.get(),
                ModItems.sparkMix.get(), ModItems.euphemiumPowder.get(),
                ModItems.dineutroniumPowder.get(), ModItems.desaturatedRedstone.get()
            )

        tag(NuclearTags.Items.DUSTS_SULFUR).add(ModItems.sulfur.get())
        tag(NuclearTags.Items.DUSTS_NITER).add(ModItems.niter.get())
        tag(NuclearTags.Items.DUSTS_FLUORITE).add(ModItems.fluorite.get())
        tag(NuclearTags.Items.DUSTS_COAL).add(ModItems.coalPowder.get())
        tag(NuclearTags.Items.DUSTS_IRON).add(ModItems.ironPowder.get())
        tag(NuclearTags.Items.DUSTS_GOLD).add(ModItems.goldPowder.get())
        tag(NuclearTags.Items.DUSTS_LAPIS).add(ModItems.lapisLazuliPowder.get())
        tag(NuclearTags.Items.DUSTS_QUARTZ).add(ModItems.quartzPowder.get())
        tag(NuclearTags.Items.DUSTS_DIAMOND).add(ModItems.diamondPowder.get())
        tag(NuclearTags.Items.DUSTS_EMERALD).add(ModItems.emeraldPowder.get())
        tag(NuclearTags.Items.DUSTS_URANIUM).add(ModItems.uraniumPowder.get())
        tag(NuclearTags.Items.DUSTS_THORIUM).add(ModItems.thoriumPowder.get())
        tag(NuclearTags.Items.DUSTS_PLUTONIUM).add(ModItems.plutoniumPowder.get())
        tag(NuclearTags.Items.DUSTS_NEPTUNIUM).add(ModItems.neptuniumPowder.get())
        tag(NuclearTags.Items.DUSTS_POLONIUM).add(ModItems.poloniumPowder.get())
        tag(NuclearTags.Items.DUSTS_TITANIUM).add(ModItems.titaniumPowder.get())
        tag(NuclearTags.Items.DUSTS_COPPER).add(ModItems.copperPowder.get())
        tag(NuclearTags.Items.DUSTS_TUNGSTEN).add(ModItems.tungstenPowder.get())
        tag(NuclearTags.Items.DUSTS_ALUMINIUM).add(ModItems.aluminiumPowder.get())
        tag(NuclearTags.Items.DUSTS_STEEL).add(ModItems.steelPowder.get())
        tag(NuclearTags.Items.DUSTS_LEAD).add(ModItems.leadPowder.get())
        tag(NuclearTags.Items.DUSTS_BERYLLIUM).add(ModItems.berylliumPowder.get())
        tag(NuclearTags.Items.DUSTS_POLYMER).add(ModItems.polymerPowder.get())
        tag(NuclearTags.Items.DUSTS_LITHIUM).add(ModItems.lithiumPowder.get())
        tag(NuclearTags.Items.DUSTS_NEODYMIUM).add(ModItems.neodymiumPowder.get())
        tag(NuclearTags.Items.DUSTS_COBALT).add(ModItems.cobaltPowder.get())
        tag(NuclearTags.Items.DUSTS_NIOBIUM).add(ModItems.niobiumPowder.get())
        tag(NuclearTags.Items.DUSTS_CERIUM).add(ModItems.ceriumPowder.get())
        tag(NuclearTags.Items.DUSTS_LANTHANUM).add(ModItems.lanthanumPowder.get())
        tag(NuclearTags.Items.DUSTS_ACTINIUM).add(ModItems.actiniumPowder.get())
        tag(NuclearTags.Items.DUSTS_ASBESTOS).add(ModItems.asbestosPowder.get())
        tag(NuclearTags.Items.DUSTS_ENCHANTMENT).add(ModItems.enchantmentPowder.get())
        tag(NuclearTags.Items.DUSTS_METEORITE).add(ModItems.meteoritePowder.get())
        tag(NuclearTags.Items.DUSTS_DUST).add(ModItems.dust.get())
        tag(NuclearTags.Items.DUSTS_PHOSPHORUS).add(ModItems.redPhosphorus.get())
        tag(NuclearTags.Items.DUSTS_COLD).add(ModItems.cryoPowder.get())
        tag(NuclearTags.Items.DUSTS_POISON).add(ModItems.poisonPowder.get())
        tag(NuclearTags.Items.DUSTS_THERMITE).add(ModItems.thermite.get())
        tag(NuclearTags.Items.DUSTS_ENERGY).add(ModItems.energyPowder.get())
        tag(NuclearTags.Items.DUSTS_CORDITE).add(ModItems.cordite.get())
        tag(NuclearTags.Items.DUSTS_BALLISTITE).add(ModItems.ballistite.get())
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
            .add(ModItems.schraraniumCrystals.get(), ModItems.schrabidiumCrystals.get())

        tag(NuclearTags.Items.GEMS_IRON).add(ModItems.ironCrystals.get())
        tag(NuclearTags.Items.GEMS_GOLD).add(ModItems.goldCrystals.get())
        tag(NuclearTags.Items.GEMS_REDSTONE).add(ModItems.redstoneCrystals.get())
        tag(NuclearTags.Items.GEMS_DIAMOND).add(ModItems.diamondCrystals.get())
        tag(NuclearTags.Items.GEMS_URANIUM).add(ModItems.uraniumCrystals.get())
        tag(NuclearTags.Items.GEMS_THORIUM).add(ModItems.thoriumCrystals.get())
        tag(NuclearTags.Items.GEMS_PLUTONIUM).add(ModItems.plutoniumCrystals.get())
        tag(NuclearTags.Items.GEMS_TITANIUM).add(ModItems.titaniumCrystals.get())
        tag(NuclearTags.Items.GEMS_SULFUR).add(ModItems.sulfurCrystals.get())
        tag(NuclearTags.Items.GEMS_NITER).add(ModItems.niterCrystals.get())
        tag(NuclearTags.Items.GEMS_COPPER).add(ModItems.copperCrystals.get())
        tag(NuclearTags.Items.GEMS_TUNGSTEN).add(ModItems.tungstenCrystals.get())
        tag(NuclearTags.Items.GEMS_ALUMINIUM).add(ModItems.aluminiumCrystals.get())
        tag(NuclearTags.Items.GEMS_FLUORITE).add(ModItems.fluoriteCrystals.get())
        tag(NuclearTags.Items.GEMS_BERYLLIUM).add(ModItems.berylliumCrystals.get())
        tag(NuclearTags.Items.GEMS_LEAD).add(ModItems.leadCrystals.get())
        tag(NuclearTags.Items.GEMS_RARE_EARTH).add(ModItems.rareEarthCrystals.get())
        tag(NuclearTags.Items.GEMS_PHOSPHORUS).add(ModItems.redPhosphorusCrystals.get())
        tag(NuclearTags.Items.GEMS_LITHIUM).add(ModItems.lithiumCrystals.get())
        tag(NuclearTags.Items.GEMS_STARMETAL).add(ModItems.starmetalCrystals.get())
        tag(NuclearTags.Items.GEMS_TRIXITE).add(ModItems.trixiteCrystals.get())

        tag(NuclearTags.Items.GEMS_NEODYMIUM).add(ModItems.neodymiumFragment.get())
        tag(NuclearTags.Items.GEMS_COBALT).add(ModItems.cobaltFragment.get())
        tag(NuclearTags.Items.GEMS_NIOBIUM).add(ModItems.niobiumFragment.get())
        tag(NuclearTags.Items.GEMS_CERIUM).add(ModItems.ceriumFragment.get())
        tag(NuclearTags.Items.GEMS_LANTHANUM).add(ModItems.lanthanumFragment.get())
        tag(NuclearTags.Items.GEMS_ACTINIUM).add(ModItems.actiniumFragment.get())
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
                ModItems.schrabidiumNugget.get(), ModItems.soliniumNugget.get(),
                ModItems.moxFuelNugget.get(), ModItems.schrabidiumFuelNugget.get(),
                ModItems.highEnrichedSchrabidiumFuelNugget.get(),
                ModItems.lowEnrichedSchrabidiumFuelNugget.get(),
                ModItems.australiumNugget.get(), ModItems.weidaniumNugget.get(),
                ModItems.reiiumNugget.get(), ModItems.unobtainiumNugget.get(),
                ModItems.daffergonNugget.get(), ModItems.verticiumNugget.get(),
                ModItems.deshNugget.get(), ModItems.euphemiumNugget.get(),
                ModItems.dineutroniumNugget.get()
            )

        tag(NuclearTags.Items.NUGGETS_URANIUM).add(
            ModItems.uraniumNugget.get(), ModItems.u233Nugget.get(),
            ModItems.u235Nugget.get(), ModItems.u238Nugget.get(),
            ModItems.uraniumFuelNugget.get()
        )
        tag(NuclearTags.Items.NUGGETS_THORIUM).add(ModItems.th232Nugget.get(), ModItems.thoriumFuelNugget.get())
        tag(NuclearTags.Items.NUGGETS_PLUTONIUM).add(
            ModItems.plutoniumNugget.get(), ModItems.pu238Nugget.get(),
            ModItems.pu239Nugget.get(), ModItems.pu240Nugget.get(),
            ModItems.plutoniumFuelNugget.get()
        )
        tag(NuclearTags.Items.NUGGETS_NEPTUNIUM).add(ModItems.neptuniumNugget.get())
        tag(NuclearTags.Items.NUGGETS_POLONIUM).add(ModItems.poloniumNugget.get())
        tag(NuclearTags.Items.NUGGETS_LEAD).add(ModItems.leadNugget.get())
        tag(NuclearTags.Items.NUGGETS_BERYLLIUM).add(ModItems.berylliumNugget.get())
        tag(NuclearTags.Items.NUGGETS_MERCURY).add(ModItems.mercuryDroplet.get())
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
                ModItems.advancedAlloyPlate.get(), ModItems.schrabidiumPlate.get(),
                ModItems.combineSteelPlate.get(), ModItems.mixedPlate.get(),
                ModItems.saturnitePlate.get(), ModItems.paAAlloyPlate.get(),
                ModItems.dalekaniumPlate.get(), ModItems.deshCompoundPlate.get(),
                ModItems.euphemiumCompoundPlate.get(), ModItems.dineutroniumCompoundPlate.get()
            )

        tag(NuclearTags.Items.PLATES_IRON).add(ModItems.ironPlate.get())
        tag(NuclearTags.Items.PLATES_GOLD).add(ModItems.goldPlate.get())
        tag(NuclearTags.Items.PLATES_TITANIUM).add(ModItems.titaniumPlate.get())
        tag(NuclearTags.Items.PLATES_ALUMINIUM).add(ModItems.aluminiumPlate.get())
        tag(NuclearTags.Items.PLATES_STEEL).add(ModItems.steelPlate.get())
        tag(NuclearTags.Items.PLATES_LEAD).add(ModItems.leadPlate.get())
        tag(NuclearTags.Items.PLATES_COPPER).add(ModItems.copperPlate.get())
        tag(NuclearTags.Items.PLATES_NEUTRON_REFLECTOR).add(ModItems.neutronReflector.get())
        tag(NuclearTags.Items.PLATES_INSULATOR).add(ModItems.insulator.get())
        tag(NuclearTags.Items.PLATES_KEVLAR).add(ModItems.kevlarCeramicCompound.get())
    }

    private fun wireTags() {
        tag(NuclearTags.Items.WIRES)
            .addTag(NuclearTags.Items.WIRES_ALUMINIUM)
            .addTag(NuclearTags.Items.WIRES_COPPER)
            .addTag(NuclearTags.Items.WIRES_TUNGSTEN)
            .addTag(NuclearTags.Items.WIRES_SUPER_CONDUCTOR)
            .addTag(NuclearTags.Items.WIRES_GOLD)
            .add(ModItems.redCopperWire.get())

        tag(NuclearTags.Items.WIRES_ALUMINIUM).add(ModItems.aluminiumWire.get())
        tag(NuclearTags.Items.WIRES_COPPER).add(ModItems.copperWire.get())
        tag(NuclearTags.Items.WIRES_TUNGSTEN).add(ModItems.tungstenWire.get())
        tag(NuclearTags.Items.WIRES_SUPER_CONDUCTOR).add(ModItems.superConductor.get(), ModItems.highTemperatureSuperConductor.get())
        tag(NuclearTags.Items.WIRES_GOLD).add(ModItems.goldWire.get())
    }

    private fun coilTags() {
        tag(NuclearTags.Items.COILS)
            .addTag(NuclearTags.Items.COILS_COPPER)
            .addTag(NuclearTags.Items.COILS_SUPER_CONDUCTOR)
            .addTag(NuclearTags.Items.COILS_GOLD)

        tag(NuclearTags.Items.COILS_COPPER).add(ModItems.copperCoil.get(), ModItems.ringCoil.get())
        tag(NuclearTags.Items.COILS_SUPER_CONDUCTOR).add(
            ModItems.superConductingCoil.get(), ModItems.superConductingRingCoil.get(),
            ModItems.heatingCoil.get(), ModItems.highTemperatureSuperConductingCoil.get()
        )
        tag(NuclearTags.Items.COILS_GOLD).add(ModItems.goldCoil.get(), ModItems.goldRingCoil.get())
    }

    private fun fissileFuelTags() {
        tag(NuclearTags.Items.FISSILE_FUELS)
            .addTag(NuclearTags.Items.FISSILE_FUELS_URANIUM)
            .addTag(NuclearTags.Items.FISSILE_FUELS_THORIUM)
            .addTag(NuclearTags.Items.FISSILE_FUELS_PLUTONIUM)
            .addTag(NuclearTags.Items.FISSILE_FUELS_MOX)
            .add(
                ModItems.schrabidiumFuelIngot.get(),
                ModItems.highEnrichedSchrabidiumFuelIngot.get(),
                ModItems.lowEnrichedSchrabidiumFuelIngot.get()
            )

        tag(NuclearTags.Items.FISSILE_FUELS_URANIUM).add(ModItems.uraniumFuelIngot.get(), ModItems.uraniumFuelNugget.get())
        tag(NuclearTags.Items.FISSILE_FUELS_THORIUM).add(ModItems.thoriumFuelIngot.get(), ModItems.thoriumFuelNugget.get())
        tag(NuclearTags.Items.FISSILE_FUELS_PLUTONIUM).add(ModItems.plutoniumFuelIngot.get(), ModItems.plutoniumFuelNugget.get())
        tag(NuclearTags.Items.FISSILE_FUELS_MOX).add(ModItems.moxFuelIngot.get(), ModItems.moxFuelNugget.get())
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

        tag(NuclearTags.Items.ORE_CRYSTALS).add(
            ModItems.ironCrystals.get(), ModItems.goldCrystals.get(),
            ModItems.redstoneCrystals.get(), ModItems.diamondCrystals.get(),
            ModItems.uraniumCrystals.get(), ModItems.thoriumCrystals.get(),
            ModItems.plutoniumCrystals.get(), ModItems.titaniumCrystals.get(),
            ModItems.sulfurCrystals.get(), ModItems.niterCrystals.get(),
            ModItems.copperCrystals.get(), ModItems.tungstenCrystals.get(),
            ModItems.aluminiumCrystals.get(), ModItems.fluoriteCrystals.get(),
            ModItems.berylliumCrystals.get(), ModItems.leadCrystals.get(),
            ModItems.schraraniumCrystals.get(), ModItems.schrabidiumCrystals.get(),
            ModItems.rareEarthCrystals.get(), ModItems.redPhosphorusCrystals.get(),
            ModItems.lithiumCrystals.get(), ModItems.starmetalCrystals.get(),
            ModItems.trixiteCrystals.get()
        )

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
