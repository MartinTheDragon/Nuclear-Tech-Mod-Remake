package at.martinthedragon.nucleartech.datagen.loot

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.advancements.criterion.EnchantmentPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.loot.BlockLootTables
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.conditions.MatchTool
import net.minecraft.loot.conditions.RandomChance
import net.minecraft.loot.functions.ApplyBonus
import net.minecraft.loot.functions.ExplosionDecay
import net.minecraft.loot.functions.LimitCount
import net.minecraft.loot.functions.SetCount
import net.minecraftforge.registries.ForgeRegistries

class BlockLoots : BlockLootTables() {
    override fun addTables() {
        dropSelf(ModBlocks.uraniumOre.get())
        dropSelf(ModBlocks.scorchedUraniumOre.get())
        dropSelf(ModBlocks.thoriumOre.get())
        dropSelf(ModBlocks.titaniumOre.get())
        add(ModBlocks.sulfurOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(ModItems.sulfur.get()).apply(SetCount.setCount(RandomValueRange.between(2F, 4F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        add(ModBlocks.niterOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(ModItems.niter.get()).apply(SetCount.setCount(RandomValueRange.between(2F, 4F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.copperOre.get())
        dropSelf(ModBlocks.tungstenOre.get())
        dropSelf(ModBlocks.aluminiumOre.get())
        add(ModBlocks.fluoriteOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(ModItems.fluorite.get()).apply(SetCount.setCount(RandomValueRange.between(2F, 4F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.berylliumOre.get())
        dropSelf(ModBlocks.leadOre.get())
        dropSelf(ModBlocks.oilDeposit.get())
        dropSelf(ModBlocks.emptyOilDeposit.get())
        dropSelf(ModBlocks.oilSand.get())
        add(ModBlocks.ligniteOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(ModItems.lignite.get()).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.asbestosOre.get())
        dropSelf(ModBlocks.schrabidiumOre.get())
        dropSelf(ModBlocks.australianOre.get())
        dropSelf(ModBlocks.weidite.get())
        dropSelf(ModBlocks.reiite.get())
        dropSelf(ModBlocks.brightblendeOre.get())
        dropSelf(ModBlocks.dellite.get())
        dropSelf(ModBlocks.dollarGreenMineral.get())

        val hasSilkTouchCondition = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
        val hasNoSilkTouchCondition = hasSilkTouchCondition.invert()

        add(ModBlocks.rareEarthOre.get()) {
            LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(ModBlocks.rareEarthOre.get()))
                .`when`(hasSilkTouchCondition)
            ).withPool(LootPool.lootPool()
                .setRolls(RandomValueRange.between(6F, 16F))
                .add(TagLootEntry.expandTag(NuclearTags.Items.RARE_EARTH_FRAGMENTS).apply(ExplosionDecay.explosionDecay()))
                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                .`when`(hasNoSilkTouchCondition))
        }

        dropSelf(ModBlocks.netherUraniumOre.get())
        dropSelf(ModBlocks.scorchedNetherUraniumOre.get())
        dropSelf(ModBlocks.netherPlutoniumOre.get())
        dropSelf(ModBlocks.netherTungstenOre.get())
        add(ModBlocks.netherSulfurOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(ModItems.sulfur.get()).apply(SetCount.setCount(RandomValueRange.between(2F, 4F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }

        add(ModBlocks.netherPhosphorusOre.get()) {
            LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(AlternativesLootEntry.alternatives(
                    ItemLootEntry.lootTableItem(ModBlocks.netherPhosphorusOre.get()).`when`(hasSilkTouchCondition),
                    ItemLootEntry.lootTableItem(ModItems.redPhosphorus.get()).`when`(RandomChance.randomChance(.4F)),
                    ItemLootEntry.lootTableItem(Items.BLAZE_POWDER).`when`(RandomChance.randomChance(.4F)),
                    ItemLootEntry.lootTableItem(ModItems.whitePhosphorusIngot.get()).`when`(RandomChance.randomChance(.2F))
                ))
            )
        }

        dropSelf(ModBlocks.netherSchrabidiumOre.get())
        dropSelf(ModBlocks.meteorUraniumOre.get())
        dropSelf(ModBlocks.meteorThoriumOre.get())
        dropSelf(ModBlocks.meteorTitaniumOre.get())
        add(ModBlocks.meteorSulfurOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(ModItems.sulfur.get()).apply(SetCount.setCount(RandomValueRange.between(6F, 12F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.meteorCopperOre.get())
        dropSelf(ModBlocks.meteorTungstenOre.get())
        dropSelf(ModBlocks.meteorAluminiumOre.get())
        dropSelf(ModBlocks.meteorLeadOre.get())
        dropSelf(ModBlocks.meteorLithiumOre.get())
        dropSelf(ModBlocks.starmetalOre.get())
        dropSelf(ModBlocks.trixite.get())

        dropSelf(ModBlocks.uraniumBlock.get())
        dropSelf(ModBlocks.u233Block.get())
        dropSelf(ModBlocks.u235Block.get())
        dropSelf(ModBlocks.u238Block.get())
        dropSelf(ModBlocks.uraniumFuelBlock.get())
        dropSelf(ModBlocks.neptuniumBlock.get())
        dropSelf(ModBlocks.moxFuelBlock.get())
        dropSelf(ModBlocks.plutoniumBlock.get())
        dropSelf(ModBlocks.pu238Block.get())
        dropSelf(ModBlocks.pu239Block.get())
        dropSelf(ModBlocks.pu240Block.get())
        dropSelf(ModBlocks.plutoniumFuelBlock.get())
        dropSelf(ModBlocks.thoriumBlock.get())
        dropSelf(ModBlocks.thoriumFuelBlock.get())
        dropSelf(ModBlocks.titaniumBlock.get())
        dropSelf(ModBlocks.sulfurBlock.get())
        dropSelf(ModBlocks.niterBlock.get())
        dropSelf(ModBlocks.copperBlock.get())
        dropSelf(ModBlocks.redCopperBlock.get())
        dropSelf(ModBlocks.advancedAlloyBlock.get())
        dropSelf(ModBlocks.tungstenBlock.get())
        dropSelf(ModBlocks.aluminiumBlock.get())
        dropSelf(ModBlocks.fluoriteBlock.get())
        dropSelf(ModBlocks.berylliumBlock.get())
        dropSelf(ModBlocks.cobaltBlock.get())
        dropSelf(ModBlocks.steelBlock.get())
        dropSelf(ModBlocks.leadBlock.get())
        dropSelf(ModBlocks.lithiumBlock.get())
        dropSelf(ModBlocks.whitePhosphorusBlock.get())
        dropSelf(ModBlocks.redPhosphorusBlock.get())
        dropSelf(ModBlocks.yellowcakeBlock.get())
        dropSelf(ModBlocks.scrapBlock.get())
        dropSelf(ModBlocks.electricalScrapBlock.get())
        dropSelf(ModBlocks.insulatorRoll.get())
        dropSelf(ModBlocks.fiberglassRoll.get())
        dropSelf(ModBlocks.asbestosBlock.get())
        dropSelf(ModBlocks.trinititeBlock.get())
        dropSelf(ModBlocks.nuclearWasteBlock.get())
        dropSelf(ModBlocks.schrabidiumBlock.get())
        dropSelf(ModBlocks.soliniumBlock.get())
        dropSelf(ModBlocks.schrabidiumFuelBlock.get())
        dropSelf(ModBlocks.euphemiumBlock.get())
        dropSelf(ModBlocks.schrabidiumCluster.get())
        dropSelf(ModBlocks.euphemiumEtchedSchrabidiumCluster.get())
        dropSelf(ModBlocks.magnetizedTungstenBlock.get())
        dropSelf(ModBlocks.combineSteelBlock.get())
        dropSelf(ModBlocks.deshReinforcedBlock.get())
        dropSelf(ModBlocks.starmetalBlock.get())
        dropSelf(ModBlocks.australiumBlock.get())
        dropSelf(ModBlocks.weidaniumBlock.get())
        dropSelf(ModBlocks.reiiumBlock.get())
        dropSelf(ModBlocks.unobtainiumBlock.get())
        dropSelf(ModBlocks.daffergonBlock.get())
        dropSelf(ModBlocks.verticiumBlock.get())
        dropSelf(ModBlocks.titaniumDecoBlock.get())
        dropSelf(ModBlocks.redCopperDecoBlock.get())
        dropSelf(ModBlocks.tungstenDecoBlock.get())
        dropSelf(ModBlocks.aluminiumDecoBlock.get())
        dropSelf(ModBlocks.steelDecoBlock.get())
        dropSelf(ModBlocks.leadDecoBlock.get())
        dropSelf(ModBlocks.berylliumDecoBlock.get())
        dropSelf(ModBlocks.asbestosRoof.get())
        dropSelf(ModBlocks.hazmatBlock.get())

        dropSelf(ModBlocks.glowingMushroom.get())
        add(ModBlocks.glowingMushroomBlock.get()) { createMushroomBlockDrop(it, ModBlocks.glowingMushroom.get()) }
        dropWhenSilkTouch(ModBlocks.glowingMushroomStem.get())
        add(ModBlocks.deadGrass.get()) { createSingleItemTableWithSilkTouch(it, Blocks.DIRT) }
        add(ModBlocks.glowingMycelium.get()) { createSingleItemTableWithSilkTouch(it, Blocks.DIRT) }
        add(ModBlocks.trinitite.get()) { createSilkTouchDispatchTable(it, ItemLootEntry.lootTableItem(ModItems.trinitite.get())) }
        add(ModBlocks.redTrinitite.get()) { createSilkTouchDispatchTable(it, ItemLootEntry.lootTableItem(ModItems.trinitite.get())) }
        add(ModBlocks.charredLog.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, ItemLootEntry.lootTableItem(Items.COAL).apply(SetCount.setCount(RandomValueRange.between(2F, 4F))).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(ModItems.burntBark.get()).`when`(hasNoSilkTouchCondition)).apply(SetCount.setCount(RandomValueRange.between(-999F, 1F))).apply(LimitCount.limitCount(IntClamper.lowerBound(0)))) }
        add(ModBlocks.charredPlanks.get()) { createOreDrop(it, Items.COAL) }
        dropSelf(ModBlocks.slakedSellafite.get())
        dropSelf(ModBlocks.sellafite.get())
        dropSelf(ModBlocks.hotSellafite.get())
        dropSelf(ModBlocks.boilingSellafite.get())
        dropSelf(ModBlocks.blazingSellafite.get())
        dropSelf(ModBlocks.infernalSellafite.get())
        dropSelf(ModBlocks.sellafiteCorium.get())

        dropSelf(ModBlocks.siren.get())
        dropSelf(ModBlocks.safe.get())

        dropSelf(ModBlocks.steamPressBase.get())
        add(ModBlocks.steamPressFrame.get()) { noDrop() }
        add(ModBlocks.steamPressTop.get()) { noDrop() }
        dropSelf(ModBlocks.blastFurnace.get())
        dropSelf(ModBlocks.combustionGenerator.get())
        dropSelf(ModBlocks.electricFurnace.get())
        dropSelf(ModBlocks.shredder.get())

        dropSelf(ModBlocks.littleBoy.get())
        dropSelf(ModBlocks.fatMan.get())
    }

    // automatically await a loot table for all blocks registered by this mod
    override fun getKnownBlocks(): MutableIterable<Block> = ForgeRegistries.BLOCKS.entries
        .filter { it.key.location().namespace == NuclearTech.MODID }
        .map { it.value }
        .toMutableList()
}
