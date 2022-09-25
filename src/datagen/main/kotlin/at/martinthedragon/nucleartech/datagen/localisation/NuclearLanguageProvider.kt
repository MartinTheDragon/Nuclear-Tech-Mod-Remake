package at.martinthedragon.nucleartech.datagen.localisation

import at.martinthedragon.nucleartech.MaterialGroup
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.TranslationKey
import at.martinthedragon.nucleartech.datagen.NuclearLanguageProviders
import at.martinthedragon.nucleartech.isVanilla
import net.minecraft.data.DataGenerator
import net.minecraft.data.HashCache
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.LanguageProvider
import java.util.function.Supplier

abstract class NuclearLanguageProvider(
    dataGenerator: DataGenerator,
    private val locale: String,
    private val exceptionOnMissing: Boolean = true
) : LanguageProvider(dataGenerator, NuclearTech.MODID, locale) {
    protected val translations = mutableMapOf<String, String>()
    protected val materialTranslations = mutableMapOf<MaterialGroup, String>()
    private val redundantTranslations = mutableMapOf<String, String>()

    override fun getName() = "Nuclear Tech ${super.getName()}"

    override fun run(cache: HashCache) {
        super.run(cache)
        validate()
    }

    final override fun addTranslations() {
        translate()

        for ((material, materialName) in materialTranslations) {
            if (!material.isVanilla()) {
                val ore = material.ore()
                val deepOre = material.deepOre()
                val block = material.block()
                val raw = material.raw()
                val ingot = material.ingot()
                val nugget = material.nugget()

                if (ore != null) addIfAbsent(ore.descriptionId, oreFormat.format(materialName))
                if (deepOre != null) addIfAbsent(deepOre.descriptionId, deepOreFormat.format(materialName))
                if (block != null) addIfAbsent(block.descriptionId, blockFormat.format(materialName))
                if (raw != null) addIfAbsent(raw.descriptionId, rawFormat.format(materialName))
                if (ingot != null) addIfAbsent(ingot.descriptionId, ingotFormat.format(materialName))
                if (nugget != null) addIfAbsent(nugget.descriptionId, nuggetFormat.format(materialName))
            }

            val crystals = material.crystals()
            val powder = material.powder()
            val plate = material.plate()
            val wire = material.wire()

            if (crystals != null) addIfAbsent(crystals.descriptionId, crystalsFormat.format(materialName))
            if (powder != null) addIfAbsent(powder.descriptionId, powderFormat.format(materialName))
            if (plate != null) addIfAbsent(plate.descriptionId, plateFormat.format(materialName))
            if (wire != null) addIfAbsent(wire.descriptionId, wireFormat.format(materialName))
        }
    }

    abstract fun translate()

    protected open fun validate() {
        if (redundantTranslations.isNotEmpty()) {
            val warningMessage = StringBuilder().appendLine("Redundant override translations for following keys in locale $locale:")
            for ((redundantKey, redundantTranslation) in redundantTranslations) warningMessage.appendLine("$redundantKey=$redundantTranslation")
            NuclearTech.LOGGER.warn(warningMessage.toString())
        }

        if (!materialTranslations.keys.containsAll(NuclearLanguageProviders.materialTranslations.keys)) {
            val missingTranslations = (NuclearLanguageProviders.materialTranslations - materialTranslations.keys).values
            val errorMessage = StringBuilder().appendLine("Missing material translations in locale $locale for the following groups:")
            for (missing in missingTranslations) errorMessage.appendLine(missing)
            if (exceptionOnMissing) throw IllegalStateException(errorMessage.toString())
            else NuclearTech.LOGGER.error(errorMessage.toString())
        }

        if (!translations.keys.containsAll(NuclearLanguageProviders.keys)) {
            val missingTranslations = NuclearLanguageProviders.keys subtract translations.keys
            val errorMessage = StringBuilder().appendLine("Missing translations in locale $locale for following keys:")
            for (missing in missingTranslations) errorMessage.appendLine(missing.prependIndent())
            if (exceptionOnMissing) throw IllegalStateException(errorMessage.toString())
            else NuclearTech.LOGGER.error(errorMessage.toString())
        }

        if (!NuclearLanguageProviders.keys.containsAll(translations.keys)) {
            val extraTranslations = translations.keys subtract NuclearLanguageProviders.keys
            val errorMessage = StringBuilder().appendLine("Extra translations in locale $locale that are non-existent in default locale:")
            for (extra in extraTranslations) errorMessage.appendLine(extra.prependIndent())
            NuclearTech.LOGGER.error(errorMessage.toString())
        }
    }

    override fun add(key: String, value: String) {
        translations[key] = value
        super.add(key, value)
    }

    protected fun add(key: TranslationKey, value: String) {
        add(key.key, value)
    }

    protected fun addIfAbsent(key: String, value: String) {
        val existing = translations[key]
        if (existing == value) redundantTranslations[key] = value
        if (existing == null) add(key, value)
    }

    protected fun ignore(key: String) {
        translations[key] = ""
    }

    protected fun ignore(key: TranslationKey) {
        ignore(key.key)
    }

    protected fun addMaterial(materialGroup: MaterialGroup, name: String) {
        materialTranslations += materialGroup to name
    }

    protected fun addBlockDesc(supplier: Supplier<out Block>, desc: String) {
        add("${supplier.get().descriptionId}.desc", desc)
    }

    protected fun addBlockDesc11(supplier: Supplier<out Block>, desc: String) {
        add("${supplier.get().descriptionId}.desc11", desc)
    }

    protected fun addItemDesc(supplier: Supplier<out Item>, desc: String) {
        add("${supplier.get().descriptionId}.desc", desc)
    }

    protected fun addItemDesc11(supplier: Supplier<out Item>, desc: String) {
        add("${supplier.get().descriptionId}.desc11", desc)
    }

    protected fun addMenuType(key: Supplier<out MenuType<*>>, name: String) {
        add("container.${key.get().registryName!!.namespace}.${key.get().registryName!!.path}", name)
    }

    protected fun addSound(key: Supplier<out SoundEvent>, name: String) {
        add("subtitle.${key.get().registryName!!.namespace}.${key.get().registryName!!.path}", name)
    }

    protected fun addDamageSource(key: DamageSource, message: String, killCreditMessage: String? = null) {
        add("death.attack.${key.msgId}", message)
        if (killCreditMessage != null) add("death.attack.${key.msgId}.player", killCreditMessage)
    }

    protected fun addAttribute(key: Supplier<out Attribute>, name: String) {
        add("${key.get().registryName!!.namespace}.attribute.name.${key.get().registryName!!.path}", name)
    }

    /** What to append to the spawn egg name */
    protected abstract val spawnEggSuffix: String
    /** If `true`, prefixes the suffix instead */
    protected open val spawnEggSuffixIsPrefix = false
    /** What to replace spaces with in the name of the entity for the spawn egg */
    protected open val spawnEggEntityStringWordSeparator = ' '

    protected abstract val oreFormat: String
    protected abstract val deepOreFormat: String
    protected abstract val blockFormat: String
    protected abstract val rawFormat: String
    protected abstract val ingotFormat: String
    protected abstract val nuggetFormat: String
    protected abstract val crystalsFormat: String
    protected abstract val powderFormat: String
    protected abstract val plateFormat: String
    protected abstract val wireFormat: String

    protected fun addEntityTypeWithSpawnEgg(key: Supplier<out EntityType<*>>, name: String) {
//        val spawnEgg = ForgeSpawnEggItem.fromEntityType(key.get()) ?: throw IllegalStateException("No spawn egg registered for entity $name")
        addEntityType(key, name)
//        val formatted = name.replace(' ', spawnEggEntityStringWordSeparator)
//        add(spawnEgg, if (spawnEggSuffixIsPrefix) spawnEggSuffix + formatted else formatted + spawnEggSuffix)
    }

    companion object {
        @JvmStatic
        protected val MODID = NuclearTech.MODID
    }
}
