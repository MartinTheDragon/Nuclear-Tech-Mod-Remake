package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.MaterialGroup
import at.martinthedragon.nucleartech.datagen.localisation.DeDeLanguageProvider
import at.martinthedragon.nucleartech.datagen.localisation.EnUsLanguageProvider
import at.martinthedragon.nucleartech.datagen.localisation.KoKrLanguageProvider
import at.martinthedragon.nucleartech.datagen.localisation.PlPlLanguageProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

object NuclearLanguageProviders {
    lateinit var keys: Set<String>
    lateinit var materialTranslations: Map<MaterialGroup, String>

    fun getLanguageProviders(dataGenerator: DataGenerator) = listOf<LanguageProvider>(
        EnUsLanguageProvider(dataGenerator), // ensure this runs first for data validation
        DeDeLanguageProvider(dataGenerator),
        PlPlLanguageProvider(dataGenerator),
        KoKrLanguageProvider(dataGenerator),
    )

    const val EN_US = "en_us"
    const val DE_DE = "de_de"
    const val PL_PL = "pl_pl"
    const val KO_KR = "ko_kr"
}
