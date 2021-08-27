package at.martinthedragon.nucleartech.datagen.loot

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.RegistriesAndLifecycle
import at.martinthedragon.nucleartech.entities.EntityTypes
import net.minecraft.advancements.criterion.EntityPredicate
import net.minecraft.data.loot.EntityLootTables
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.loot.*
import net.minecraft.loot.conditions.Alternative
import net.minecraft.loot.conditions.EntityHasProperty
import net.minecraft.loot.conditions.Inverted
import net.minecraft.loot.functions.LootingEnchantBonus
import net.minecraft.loot.functions.SetCount
import net.minecraft.tags.EntityTypeTags
import net.minecraftforge.fml.RegistryObject

class EntityLoots : EntityLootTables() {
    override fun addTables() {
        add(EntityTypes.nuclearCreeperEntity.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(Items.TNT).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
            )
            .withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(ModItems.u233Nugget.get()).apply(SetCount.setCount(RandomValueRange.between(1F, 2F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
                .add(ItemLootEntry.lootTableItem(ModItems.pu238Nugget.get()).apply(SetCount.setCount(RandomValueRange.between(1F, 2F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
                .add(ItemLootEntry.lootTableItem(ModItems.pu239Nugget.get()).apply(SetCount.setCount(RandomValueRange.between(1F, 2F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
                .add(ItemLootEntry.lootTableItem(ModItems.neptuniumNugget.get()).apply(SetCount.setCount(RandomValueRange.between(1F, 2F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
                // TODO Fat Man Core
                .add(ItemLootEntry.lootTableItem(ModItems.sulfur.get()).apply(SetCount.setCount(RandomValueRange.between(1F, 4F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
                .add(ItemLootEntry.lootTableItem(ModItems.niter.get()).apply(SetCount.setCount(RandomValueRange.between(1F, 4F))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F))))
                // TODO AWESOME, Fusion Core, Stimpak, T45 Armor, Nuke Ammo
                .`when`(Alternative.alternative(
                    EntityHasProperty.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)),
                    EntityHasProperty.hasProperties(LootContext.EntityTarget.DIRECT_KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.ARROWS))),
                )
                // this pool cannot drop from player kills
                .`when`(Inverted.invert(EntityHasProperty.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityType.PLAYER))))
            )
        )
    }

    override fun getKnownEntities(): MutableIterable<EntityType<*>> =
        RegistriesAndLifecycle.ENTITIES.entries.map(RegistryObject<EntityType<*>>::get).toMutableList()
}
