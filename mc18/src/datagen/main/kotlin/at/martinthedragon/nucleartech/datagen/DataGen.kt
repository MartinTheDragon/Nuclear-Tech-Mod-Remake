package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.datagen.tag.BlockTagProvider
import at.martinthedragon.nucleartech.datagen.tag.FluidTagProvider
import at.martinthedragon.nucleartech.datagen.tag.ItemTagProvider
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@Suppress("unused")
object DataGen {
    @SubscribeEvent
    @JvmStatic
    fun generateData(event: GatherDataEvent) {
        val dataGenerator = event.generator
        val existingFileHelper = event.existingFileHelper
        if (event.includeServer()) {
            val blockTagProvider = BlockTagProvider(dataGenerator, existingFileHelper)
            dataGenerator.addProvider(blockTagProvider)
            dataGenerator.addProvider(ItemTagProvider(dataGenerator, blockTagProvider, existingFileHelper))
            dataGenerator.addProvider(FluidTagProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(FluidTraitProvider(dataGenerator))
            dataGenerator.addProvider(HarvestabilityProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearRecipeProvider(dataGenerator))
            dataGenerator.addProvider(NuclearLootProvider(dataGenerator))
            dataGenerator.addProvider(FalloutTransformationsProvider(dataGenerator))
        }

        if (event.includeClient()) {
            dataGenerator.addProvider(NuclearBlockStateProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearItemModelProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearModelProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearSoundsProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearParticleProvider(dataGenerator, existingFileHelper))

            for (translation in NuclearLanguageProviders.getLanguageProviders(dataGenerator))
                dataGenerator.addProvider(translation)
        }
    }
}
