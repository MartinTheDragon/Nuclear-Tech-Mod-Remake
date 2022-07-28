package at.martinthedragon.nucleartech.datagen.tags

import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.fluid.NTechFluids
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.FluidTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper

class FluidTagProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) : FluidTagsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Fluid Tags"

    override fun addTags(): Unit = with(NuclearTags.Fluids) {
        tag(URANIUM_HEXAFLUORIDE).add(NTechFluids.uraniumHexafluoride.source, NTechFluids.uraniumHexafluoride.flowing)
    }
}
