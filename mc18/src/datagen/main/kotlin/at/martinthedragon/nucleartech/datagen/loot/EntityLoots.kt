package at.martinthedragon.nucleartech.datagen.loot

import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.RegistriesAndLifecycle
import at.martinthedragon.nucleartech.entity.EntityTypes
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.data.loot.EntityLoot
import net.minecraft.tags.EntityTypeTags
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import net.minecraftforge.registries.RegistryObject

class EntityLoots : EntityLoot() {
    override fun addTables() {
        add(EntityTypes.nuclearCreeper.get(), LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1F))
                .add(LootItem.lootTableItem(Items.TNT).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
            )
            .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1F))
                .add(LootItem.lootTableItem(NTechItems.u233Nugget.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
                .add(LootItem.lootTableItem(NTechItems.pu238Nugget.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
                .add(LootItem.lootTableItem(NTechItems.pu239Nugget.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
                .add(LootItem.lootTableItem(NTechItems.neptuniumNugget.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 2F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
                // TODO Fat Man Core
                .add(LootItem.lootTableItem(NTechItems.sulfur.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
                .add(LootItem.lootTableItem(NTechItems.niter.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1F, 4F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))
                // TODO AWESOME, Fusion Core, Stimpak, T45 Armor, Nuke Ammo
                .`when`(AlternativeLootItemCondition.alternative(
                    LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)),
                    LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.DIRECT_KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.ARROWS))),
                )
                // this pool cannot drop from player kills
                .`when`(InvertedLootItemCondition.invert(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityType.PLAYER))))
            )
        )
    }

    override fun getKnownEntities(): MutableIterable<EntityType<*>> =
        RegistriesAndLifecycle.ENTITIES.entries.map(RegistryObject<EntityType<*>>::get).toMutableList()
}
