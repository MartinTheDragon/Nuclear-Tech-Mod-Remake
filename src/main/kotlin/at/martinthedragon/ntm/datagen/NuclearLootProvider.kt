package at.martinthedragon.ntm.datagen

import at.martinthedragon.ntm.datagen.loot.BlockLoots
import com.mojang.datafixers.util.Pair
import net.minecraft.data.DataGenerator
import net.minecraft.data.LootTableProvider
import net.minecraft.loot.LootParameterSet
import net.minecraft.loot.LootParameterSets
import net.minecraft.loot.LootTable
import net.minecraft.loot.ValidationTracker
import net.minecraft.util.ResourceLocation
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

class NuclearLootProvider(generator: DataGenerator) : LootTableProvider(generator) {
    override fun getName(): String = "Nuclear Tech Mod Loot Tables"

    private val subProviders = listOf<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>>(
        Pair.of(Supplier { BlockLoots() }, LootParameterSets.BLOCK)
    )

    override fun getTables() = subProviders

    override fun validate(map: MutableMap<ResourceLocation, LootTable>, validationtracker: ValidationTracker) {
        // maybe TODO
    }
}
