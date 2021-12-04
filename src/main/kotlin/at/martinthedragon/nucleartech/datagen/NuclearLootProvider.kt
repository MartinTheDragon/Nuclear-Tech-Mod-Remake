package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.datagen.loot.BlockLoots
import at.martinthedragon.nucleartech.datagen.loot.EntityLoots
import com.mojang.datafixers.util.Pair
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.LootTables
import net.minecraft.world.level.storage.loot.ValidationContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

class NuclearLootProvider(generator: DataGenerator) : LootTableProvider(generator) {
    override fun getName(): String = "Nuclear Tech Mod Loot Tables"

    private val subProviders = listOf<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>>(
        Pair.of(Supplier(::EntityLoots), LootContextParamSets.ENTITY),
        Pair.of(Supplier(::BlockLoots), LootContextParamSets.BLOCK),
    )

    override fun getTables() = subProviders

    override fun validate(map: MutableMap<ResourceLocation, LootTable>, validationtracker: ValidationContext) {
        for ((name, table) in map) LootTables.validate(validationtracker, name, table)
    }
}
