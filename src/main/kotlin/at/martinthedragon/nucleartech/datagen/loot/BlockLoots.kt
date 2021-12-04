package at.martinthedragon.nucleartech.datagen.loot

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.advancements.critereon.EnchantmentPredicate
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.advancements.critereon.MinMaxBounds
import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.IntRange
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.TagEntry
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay
import net.minecraft.world.level.storage.loot.functions.LimitCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import net.minecraftforge.registries.ForgeRegistries

class BlockLoots : BlockLoot() {
    override fun addTables() {
        dropSelf(ModBlocks.uraniumOre.get())
        dropSelf(ModBlocks.scorchedUraniumOre.get())
        dropSelf(ModBlocks.thoriumOre.get())
        dropSelf(ModBlocks.titaniumOre.get())
        add(ModBlocks.sulfurOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(ModItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        add(ModBlocks.niterOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(ModItems.niter.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.copperOre.get())
        dropSelf(ModBlocks.tungstenOre.get())
        dropSelf(ModBlocks.aluminiumOre.get())
        add(ModBlocks.fluoriteOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(ModItems.fluorite.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.berylliumOre.get())
        dropSelf(ModBlocks.leadOre.get())
        dropSelf(ModBlocks.oilDeposit.get())
        dropSelf(ModBlocks.emptyOilDeposit.get())
        dropSelf(ModBlocks.oilSand.get())
        add(ModBlocks.ligniteOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(ModItems.lignite.get()).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }
        dropSelf(ModBlocks.asbestosOre.get())
        dropSelf(ModBlocks.schrabidiumOre.get())
        dropSelf(ModBlocks.australianOre.get())
        dropSelf(ModBlocks.weidite.get())
        dropSelf(ModBlocks.reiite.get())
        dropSelf(ModBlocks.brightblendeOre.get())
        dropSelf(ModBlocks.dellite.get())
        dropSelf(ModBlocks.dollarGreenMineral.get())

        val hasSilkTouchCondition = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))
        ))
        val hasNoSilkTouchCondition = hasSilkTouchCondition.invert()

        add(ModBlocks.rareEarthOre.get()) {
            LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1F))
                .add(LootItem.lootTableItem(ModBlocks.rareEarthOre.get()))
                .`when`(hasSilkTouchCondition)
            ).withPool(LootPool.lootPool()
                .setRolls(UniformGenerator.between(6F, 16F))
                .add(TagEntry.expandTag(NuclearTags.Items.RARE_EARTH_FRAGMENTS).apply(ApplyExplosionDecay.explosionDecay()))
                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                .`when`(hasNoSilkTouchCondition))
        }

        dropSelf(ModBlocks.netherUraniumOre.get())
        dropSelf(ModBlocks.scorchedNetherUraniumOre.get())
        dropSelf(ModBlocks.netherPlutoniumOre.get())
        dropSelf(ModBlocks.netherTungstenOre.get())
        add(ModBlocks.netherSulfurOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(ModItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        }

        add(ModBlocks.netherPhosphorusOre.get()) {
            LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1F))
                .add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(ModBlocks.netherPhosphorusOre.get()).`when`(hasSilkTouchCondition),
                    LootItem.lootTableItem(ModItems.redPhosphorus.get()).`when`(LootItemRandomChanceCondition.randomChance(.4F)),
                    LootItem.lootTableItem(Items.BLAZE_POWDER).`when`(LootItemRandomChanceCondition.randomChance(.4F)),
                    LootItem.lootTableItem(ModItems.whitePhosphorusIngot.get()).`when`(LootItemRandomChanceCondition.randomChance(.2F))
                ))
            )
        }

        dropSelf(ModBlocks.netherSchrabidiumOre.get())
        dropSelf(ModBlocks.meteorUraniumOre.get())
        dropSelf(ModBlocks.meteorThoriumOre.get())
        dropSelf(ModBlocks.meteorTitaniumOre.get())
        add(ModBlocks.meteorSulfurOre.get()) {
            createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(ModItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(6F, 12F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
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
        add(ModBlocks.trinitite.get()) { createSilkTouchDispatchTable(it, LootItem.lootTableItem(ModItems.trinitite.get())) }
        add(ModBlocks.redTrinitite.get()) { createSilkTouchDispatchTable(it, LootItem.lootTableItem(ModItems.trinitite.get())) }
        add(ModBlocks.charredLog.get()) { createSilkTouchDispatchTable(it, applyExplosionDecay(it, LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(2F, 4F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(ModItems.burntBark.get()).`when`(hasNoSilkTouchCondition)).apply(SetItemCountFunction.setCount(UniformGenerator.between(-999F, 1F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))) }
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
