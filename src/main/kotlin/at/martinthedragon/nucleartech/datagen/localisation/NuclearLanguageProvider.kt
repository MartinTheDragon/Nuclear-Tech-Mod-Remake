package at.martinthedragon.nucleartech.datagen.localisation

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.datagen.NuclearLanguageProviders
import net.minecraft.data.DataGenerator
import net.minecraft.data.HashCache
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
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

    override fun getName() = "Nuclear Tech ${super.getName()}"

    override fun run(cache: HashCache) {
        super.run(cache)
        validate()
    }

    protected open fun validate() {
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

    protected fun addBlockDesc(supplier: Supplier<out Block>, desc: String) {
        add("${supplier.get().descriptionId}.desc", desc)
    }

    protected fun addItemDesc(supplier: Supplier<out Item>, desc: String) {
        add("${supplier.get().descriptionId}.desc", desc)
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

    /** What to append to the spawn egg name */
    abstract val spawnEggSuffix: String
    /** If `true`, prefixes the suffix instead */
    open val spawnEggSuffixIsPrefix = false
    /** What to replace spaces with in the name of the entity for the spawn egg */
    open val spawnEggEntityStringWordSeparator = ' '

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
