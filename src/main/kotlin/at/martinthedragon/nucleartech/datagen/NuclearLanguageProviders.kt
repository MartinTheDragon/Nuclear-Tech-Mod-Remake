package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.datagen.localisation.DeDeLanguageProvider
import at.martinthedragon.nucleartech.datagen.localisation.EnUsLanguageProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

object NuclearLanguageProviders {
    lateinit var keys: Set<String>

    fun getLanguageProviders(dataGenerator: DataGenerator) = listOf<LanguageProvider>(
        EnUsLanguageProvider(dataGenerator), // ensure this runs first for data validation
        DeDeLanguageProvider(dataGenerator),
    )

    const val EN_US = "en_us"
    const val DE_DE = "de_de"
}
