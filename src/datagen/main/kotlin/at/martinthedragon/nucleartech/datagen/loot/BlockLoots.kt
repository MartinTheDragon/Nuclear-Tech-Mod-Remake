package at.martinthedragon.nucleartech.datagen.loot

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.advancements.critereon.StatePropertiesPredicate
import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.IntRange
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.EntryGroup
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.LimitCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import net.minecraftforge.registries.ForgeRegistries

class BlockLoots : BlockLoot() {
    override fun addTables() {
        add(NTechBlocks.uraniumOre.get()) { createOreDrop(it, NTechItems.rawUranium.get()) }
        add(NTechBlocks.scorchedUraniumOre.get()) { createOreDrop(it, NTechItems.rawUranium.get()) }
        add(NTechBlocks.thoriumOre.get()) { createOreDrop(it, NTechItems.rawThorium.get()) }
        add(NTechBlocks.titaniumOre.get()) { createOreDrop(it, NTechItems.rawTitanium.get()) }
        add(NTechBlocks.sulfurOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.niterOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.niter.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.tungstenOre.get()) { createOreDrop(it, NTechItems.rawTungsten.get()) }
        add(NTechBlocks.aluminiumOre.get()) { createOreDrop(it, NTechItems.rawAluminium.get()) }
        add(NTechBlocks.fluoriteOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.fluorite.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.berylliumOre.get()) { createOreDrop(it, NTechItems.rawBeryllium.get()) }
        add(NTechBlocks.leadOre.get()) { createOreDrop(it, NTechItems.rawLead.get()) }
        dropSelf(NTechBlocks.oilDeposit.get())
        dropSelf(NTechBlocks.emptyOilDeposit.get())
        dropSelf(NTechBlocks.oilSand.get())
        add(NTechBlocks.ligniteOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.lignite.get()).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.asbestosOre.get()) { createOreDrop(it, NTechItems.asbestosSheet.get()) }
        add(NTechBlocks.schrabidiumOre.get()) { createOreDrop(it, NTechItems.rawSchrabidium.get()) }
        add(NTechBlocks.australianOre.get()) { createOreDrop(it, NTechItems.rawAustralium.get()) }
        dropSelf(NTechBlocks.weidite.get())
        dropSelf(NTechBlocks.reiite.get())
        dropSelf(NTechBlocks.brightblendeOre.get())
        dropSelf(NTechBlocks.dellite.get())
        dropSelf(NTechBlocks.dollarGreenMineral.get())
        add(NTechBlocks.rareEarthOre.get()) { createOreDrop(it, NTechItems.rawRareEarth.get()) }
        add(NTechBlocks.cobaltOre.get()) { createOreDrop(it, NTechItems.rawCobalt.get()) }
        add(NTechBlocks.deepslateUraniumOre.get()) { createOreDrop(it, NTechItems.rawUranium.get()) }
        add(NTechBlocks.scorchedDeepslateUraniumOre.get()) { createOreDrop(it, NTechItems.rawUranium.get()) }
        add(NTechBlocks.deepslateThoriumOre.get()) { createOreDrop(it, NTechItems.rawThorium.get()) }
        add(NTechBlocks.deepslateTitaniumOre.get()) { createOreDrop(it, NTechItems.rawTitanium.get()) }
        add(NTechBlocks.deepslateSulfurOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.deepslateNiterOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.niter.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.deepslateTungstenOre.get()) { createOreDrop(it, NTechItems.rawTungsten.get()) }
        add(NTechBlocks.deepslateAluminiumOre.get()) { createOreDrop(it, NTechItems.rawAluminium.get()) }
        add(NTechBlocks.deepslateFluoriteOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.fluorite.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.deepslateBerylliumOre.get()) { createOreDrop(it, NTechItems.rawBeryllium.get()) }
        add(NTechBlocks.deepslateLeadOre.get()) { createOreDrop(it, NTechItems.rawLead.get()) }
        dropSelf(NTechBlocks.deepslateOilDeposit.get())
        dropSelf(NTechBlocks.emptyDeepslateOilDeposit.get())
        add(NTechBlocks.deepslateAsbestosOre.get()) { createOreDrop(it, NTechItems.asbestosSheet.get()) }
        add(NTechBlocks.deepslateSchrabidiumOre.get()) { createOreDrop(it, NTechItems.rawSchrabidium.get()) }
        add(NTechBlocks.deepslateAustralianOre.get()) { createOreDrop(it, NTechItems.rawAustralium.get()) }
        add(NTechBlocks.deepslateRareEarthOre.get()) { createOreDrop(it, NTechItems.rawRareEarth.get()) }
        add(NTechBlocks.deepslateCobaltOre.get()) { createOreDrop(it, NTechItems.rawCobalt.get()) }

        add(NTechBlocks.netherUraniumOre.get()) { createOreDrop(it, NTechItems.rawUranium.get()) }
        add(NTechBlocks.scorchedNetherUraniumOre.get()) { createOreDrop(it, NTechItems.rawUranium.get()) }
        add(NTechBlocks.netherPlutoniumOre.get()) { createOreDrop(it, NTechItems.rawPlutonium.get()) }
        add(NTechBlocks.netherTungstenOre.get()) { createOreDrop(it, NTechItems.rawTungsten.get()) }
        add(NTechBlocks.netherSulfurOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }

        val hasSilkTouchCondition = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))
        ))
        val hasNoSilkTouchCondition = hasSilkTouchCondition.invert()

        add(NTechBlocks.netherPhosphorusOre.get()) {
            LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1F))
                .add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(NTechBlocks.netherPhosphorusOre.get()).`when`(hasSilkTouchCondition),
                    LootItem.lootTableItem(NTechItems.redPhosphorus.get()).`when`(LootItemRandomChanceCondition.randomChance(.4F)),
                    LootItem.lootTableItem(Items.BLAZE_POWDER).`when`(LootItemRandomChanceCondition.randomChance(.4F)),
                    LootItem.lootTableItem(NTechItems.whitePhosphorusIngot.get()).`when`(LootItemRandomChanceCondition.randomChance(.2F))
                ))
            )
        }

        add(NTechBlocks.netherSchrabidiumOre.get()) { createOreDrop(it, NTechItems.rawSchrabidium.get()) }
        dropSelf(NTechBlocks.meteorUraniumOre.get())
        dropSelf(NTechBlocks.meteorThoriumOre.get())
        dropSelf(NTechBlocks.meteorTitaniumOre.get())
        add(NTechBlocks.meteorSulfurOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(6F, 12F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        dropSelf(NTechBlocks.meteorCopperOre.get())
        dropSelf(NTechBlocks.meteorTungstenOre.get())
        dropSelf(NTechBlocks.meteorAluminiumOre.get())
        dropSelf(NTechBlocks.meteorLeadOre.get())
        add(NTechBlocks.meteorLithiumOre.get()) { createOreDrop(it, NTechItems.rawLithium.get()) }
        add(NTechBlocks.starmetalOre.get()) { createOreDrop(it, NTechItems.rawStarmetal.get()) }
        add(NTechBlocks.trixite.get()) { createOreDrop(it, NTechItems.rawTrixite.get()) }
        add(NTechBlocks.basaltSulfurOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.basaltFluoriteOre.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(NTechItems.fluorite.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))) }
        add(NTechBlocks.basaltAsbestosOre.get()) { createOreDrop(it, NTechItems.asbestosSheet.get()) }
        add(NTechBlocks.basaltVolcanicGemOre.get()) { createOreDrop(it, NTechItems.volcanicGem.get()) }

        dropSelf(NTechBlocks.uraniumBlock.get())
        dropSelf(NTechBlocks.u233Block.get())
        dropSelf(NTechBlocks.u235Block.get())
        dropSelf(NTechBlocks.u238Block.get())
        dropSelf(NTechBlocks.uraniumFuelBlock.get())
        dropSelf(NTechBlocks.neptuniumBlock.get())
        dropSelf(NTechBlocks.moxFuelBlock.get())
        dropSelf(NTechBlocks.plutoniumBlock.get())
        dropSelf(NTechBlocks.pu238Block.get())
        dropSelf(NTechBlocks.pu239Block.get())
        dropSelf(NTechBlocks.pu240Block.get())
        dropSelf(NTechBlocks.plutoniumFuelBlock.get())
        dropSelf(NTechBlocks.thoriumBlock.get())
        dropSelf(NTechBlocks.thoriumFuelBlock.get())
        dropSelf(NTechBlocks.titaniumBlock.get())
        dropSelf(NTechBlocks.sulfurBlock.get())
        dropSelf(NTechBlocks.niterBlock.get())
        dropSelf(NTechBlocks.redCopperBlock.get())
        dropSelf(NTechBlocks.advancedAlloyBlock.get())
        dropSelf(NTechBlocks.tungstenBlock.get())
        dropSelf(NTechBlocks.aluminiumBlock.get())
        dropSelf(NTechBlocks.fluoriteBlock.get())
        dropSelf(NTechBlocks.berylliumBlock.get())
        dropSelf(NTechBlocks.cobaltBlock.get())
        dropSelf(NTechBlocks.steelBlock.get())
        dropSelf(NTechBlocks.leadBlock.get())
        dropSelf(NTechBlocks.lithiumBlock.get())
        dropSelf(NTechBlocks.whitePhosphorusBlock.get())
        dropSelf(NTechBlocks.redPhosphorusBlock.get())
        dropSelf(NTechBlocks.yellowcakeBlock.get())
        dropSelf(NTechBlocks.scrapBlock.get())
        dropSelf(NTechBlocks.electricalScrapBlock.get())
        dropSelf(NTechBlocks.insulatorRoll.get())
        dropSelf(NTechBlocks.fiberglassRoll.get())
        dropSelf(NTechBlocks.asbestosBlock.get())
        dropSelf(NTechBlocks.trinititeBlock.get())
        dropSelf(NTechBlocks.nuclearWasteBlock.get())
        dropSelf(NTechBlocks.schrabidiumBlock.get())
        dropSelf(NTechBlocks.soliniumBlock.get())
        dropSelf(NTechBlocks.schrabidiumFuelBlock.get())
        dropSelf(NTechBlocks.euphemiumBlock.get())
        dropSelf(NTechBlocks.schrabidiumCluster.get())
        dropSelf(NTechBlocks.euphemiumEtchedSchrabidiumCluster.get())
        dropSelf(NTechBlocks.magnetizedTungstenBlock.get())
        dropSelf(NTechBlocks.combineSteelBlock.get())
        dropSelf(NTechBlocks.deshReinforcedBlock.get())
        dropSelf(NTechBlocks.starmetalBlock.get())
        dropSelf(NTechBlocks.australiumBlock.get())
        dropSelf(NTechBlocks.weidaniumBlock.get())
        dropSelf(NTechBlocks.reiiumBlock.get())
        dropSelf(NTechBlocks.unobtainiumBlock.get())
        dropSelf(NTechBlocks.daffergonBlock.get())
        dropSelf(NTechBlocks.verticiumBlock.get())
        dropSelf(NTechBlocks.titaniumDecoBlock.get())
        dropSelf(NTechBlocks.redCopperDecoBlock.get())
        dropSelf(NTechBlocks.tungstenDecoBlock.get())
        dropSelf(NTechBlocks.aluminiumDecoBlock.get())
        dropSelf(NTechBlocks.steelDecoBlock.get())
        dropSelf(NTechBlocks.leadDecoBlock.get())
        dropSelf(NTechBlocks.berylliumDecoBlock.get())
        dropSelf(NTechBlocks.asbestosRoof.get())
        dropSelf(NTechBlocks.hazmatBlock.get())
        add(NTechBlocks.meteorite.get()) { createSilkTouchDispatchTable(it, EntryGroup.list(LootItem.lootTableItem(NTechItems.dalekaniumPlate.get()), LootItem.lootTableItem(NTechBlockItems.meteorite.get()).setWeight(9))) }
        add(NTechBlocks.meteoriteCobblestone.get()) { createSingleItemTableWithSilkTouch(it, NTechItems.meteoriteFragment.get()) }
        add(NTechBlocks.brokenMeteorite.get()) { createSingleItemTableWithSilkTouch(it, NTechItems.meteoriteFragment.get()) }
        dropWhenSilkTouch(NTechBlocks.hotMeteoriteCobblestone.get())
        add(NTechBlocks.meteoriteTreasure.get()) { createSilkTouchDispatchTable(it, EntryGroup.list(
            LootItem.lootTableItem(NTechItems.superConductingCoil.get()),
            LootItem.lootTableItem(NTechItems.advancedAlloyPlate.get()),
            LootItem.lootTableItem(NTechItems.deshMix.get()),
            LootItem.lootTableItem(NTechItems.deshIngot.get()),
            LootItem.lootTableItem(NTechItems.advancedBattery.get()),
            LootItem.lootTableItem(NTechItems.advancedBattery.get()),
            LootItem.lootTableItem(NTechItems.lithiumPowerCell.get()),
            LootItem.lootTableItem(NTechItems.advancedPowerCell.get()),
            LootItem.lootTableItem(NTechItems.schrabidiumNugget.get()),
            LootItem.lootTableItem(NTechItems.plutoniumIngot.get()),
            LootItem.lootTableItem(NTechItems.thoriumFuelIngot.get()),
            LootItem.lootTableItem(NTechItems.u233Ingot.get()),
            LootItem.lootTableItem(NTechItems.highSpeedSteelIngot.get()), // TODO tungsten turbine
            LootItem.lootTableItem(NTechItems.polymerIngot.get()),
            LootItem.lootTableItem(NTechItems.tungstenIngot.get()),
            LootItem.lootTableItem(NTechItems.combineSteelIngot.get()),
            LootItem.lootTableItem(NTechItems.lanthanumIngot.get()),
            LootItem.lootTableItem(NTechItems.actiniumIngot.get()),
            LootItem.lootTableItem(NTechBlockItems.meteorite.get()), // TODO fusion heater
            LootItem.lootTableItem(NTechItems.advancedCircuit.get()),
            LootItem.lootTableItem(NTechBlockItems.rareEarthOre.get()),
            // TODO
        )) }
        dropSelf(NTechBlocks.steelBeam.get())
        dropSelf(NTechBlocks.steelScaffold.get())
        dropSelf(NTechBlocks.steelGrate.get())

        dropSelf(NTechBlocks.decoRbmkBlock.get())
        dropSelf(NTechBlocks.decoRbmkSmoothBlock.get())

        dropSelf(NTechBlocks.glowingMushroom.get())
        add(NTechBlocks.glowingMushroomBlock.get()) { createMushroomBlockDrop(it, NTechBlocks.glowingMushroom.get()) }
        dropWhenSilkTouch(NTechBlocks.glowingMushroomStem.get())
        add(NTechBlocks.deadGrass.get()) { createSingleItemTableWithSilkTouch(it, Blocks.DIRT) }
        add(NTechBlocks.glowingMycelium.get()) { createSingleItemTableWithSilkTouch(it, Blocks.DIRT) }
        add(NTechBlocks.trinitite.get()) { createSilkTouchDispatchTable(it, LootItem.lootTableItem(NTechItems.trinitite.get())) }
        add(NTechBlocks.redTrinitite.get()) { createSilkTouchDispatchTable(it, LootItem.lootTableItem(NTechItems.trinitite.get())) }
        add(NTechBlocks.charredLog.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(
            NTechItems.burntBark.get()).`when`(hasNoSilkTouchCondition)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-999F, 1F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))) }
        add(NTechBlocks.charredPlanks.get()) { createOreDrop(it, Items.COAL) }
        dropSelf(NTechBlocks.slakedSellafite.get())
        dropSelf(NTechBlocks.sellafite.get())
        dropSelf(NTechBlocks.hotSellafite.get())
        dropSelf(NTechBlocks.boilingSellafite.get())
        dropSelf(NTechBlocks.blazingSellafite.get())
        dropSelf(NTechBlocks.infernalSellafite.get())
        dropSelf(NTechBlocks.sellafiteCorium.get())

        dropSelf(NTechBlocks.corium.get())
        dropSelf(NTechBlocks.corebblestone.get())

        dropSelf(NTechBlocks.siren.get())
        dropSelf(NTechBlocks.safe.get())

        dropSelf(NTechBlocks.steamPressBase.get())
        add(NTechBlocks.steamPressFrame.get(), noDrop())
        add(NTechBlocks.steamPressTop.get(), noDrop())
        dropSelf(NTechBlocks.blastFurnace.get())
        dropSelf(NTechBlocks.combustionGenerator.get())
        dropSelf(NTechBlocks.electricFurnace.get())
        dropSelf(NTechBlocks.shredder.get())
        dropSelf(NTechBlocks.ironAnvil.get())
        dropSelf(NTechBlocks.leadAnvil.get())
        dropSelf(NTechBlocks.steelAnvil.get())
        dropSelf(NTechBlocks.meteoriteAnvil.get())
        dropSelf(NTechBlocks.starmetalAnvil.get())
        dropSelf(NTechBlocks.ferrouraniumAnvil.get())
        dropSelf(NTechBlocks.bismuthAnvil.get())
        dropSelf(NTechBlocks.schrabidateAnvil.get())
        dropSelf(NTechBlocks.dineutroniumAnvil.get())
        dropSelf(NTechBlocks.murkyAnvil.get())
        dropSelf(NTechBlocks.coatedCable.get())
        dropSelf(NTechBlocks.coatedUniversalFluidDuct.get())
        dropSelf(NTechBlocks.assembler.get())
        dropSelf(NTechBlocks.chemPlant.get())
        dropSelf(NTechBlocks.oilDerrick.get())
        dropSelf(NTechBlocks.pumpjack.get())
        dropSelf(NTechBlocks.centrifuge.get())

        add(NTechBlocks.rbmkColumn.get(), noDrop())
        rbmkDrops(NTechBlocks.rbmkRod.get())
        rbmkDrops(NTechBlocks.rbmkModeratedRod.get())
        rbmkDrops(NTechBlocks.rbmkReaSimRod.get())
        rbmkDrops(NTechBlocks.rbmkModeratedReaSimRod.get())
        rbmkDrops(NTechBlocks.rbmkReflector.get())
        rbmkDrops(NTechBlocks.rbmkModerator.get())
        rbmkDrops(NTechBlocks.rbmkAbsorber.get())
        add(NTechBlocks.rbmkBoilerColumn.get(), noDrop())
        rbmkDrops(NTechBlocks.rbmkBoiler.get())
        rbmkDrops(NTechBlocks.rbmkBlank.get())
        rbmkDrops(NTechBlocks.rbmkManualControlRod.get())
        rbmkDrops(NTechBlocks.rbmkModeratedControlRod.get())
        rbmkDrops(NTechBlocks.rbmkAutoControlRod.get())
        dropSelf(NTechBlocks.rbmkSteamConnector.get())
        dropSelf(NTechBlocks.rbmkInlet.get())
        dropSelf(NTechBlocks.rbmkOutlet.get())
        dropSelf(NTechBlocks.rbmkConsole.get())
        dropSelf(NTechBlocks.rbmkDebris.get())
        dropSelf(NTechBlocks.rbmkBurningDebris.get())
        dropSelf(NTechBlocks.rbmkRadioactiveDebris.get())

        dropSelf(NTechBlocks.littleBoy.get())
        dropSelf(NTechBlocks.fatMan.get())

        dropSelf(NTechBlocks.launchPad.get())
        add(NTechBlocks.launchPadPart.get(), noDrop())

        add(NTechBlocks.genericMultiBlockPart.get(), noDrop())
        add(NTechBlocks.genericMultiBlockPort.get(), noDrop())
        add(NTechBlocks.oilPipe.get(), noDrop())
    }

    private fun rbmkDrops(block: RBMKBaseBlock) {
        add(block, LootTable.lootTable()
            .withPool(applyExplosionCondition(block, LootPool.lootPool().add(LootItem.lootTableItem(block))))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NTechItems.rbmkLid.get())).`when`(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RBMKBaseBlock.LID_TYPE, RBMKBaseBlock.LidType.CONCRETE))))
            .withPool(LootPool.lootPool().add(LootItem.lootTableItem(NTechItems.rbmkGlassLid.get())).`when`(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RBMKBaseBlock.LID_TYPE, RBMKBaseBlock.LidType.LEAD_GLASS))))
        )
    }

    // automatically await a loot table for all blocks registered by this mod
    override fun getKnownBlocks(): MutableIterable<Block> = ForgeRegistries.BLOCKS.entries
        .filter { it.key.location().namespace == NuclearTech.MODID }
        .map { it.value }
        .toMutableList()
}
