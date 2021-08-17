package at.martinthedragon.nucleartech.datagen.localisation

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.datagen.NuclearLanguageProviders
import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.data.DirectoryCache
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.data.LanguageProvider
import java.util.function.Supplier

abstract class NuclearLanguageProvider(
    dataGenerator: DataGenerator,
    private val locale: String,
    private val exceptionOnMissing: Boolean = true
) : LanguageProvider(dataGenerator, NuclearTech.MODID, locale) {
    override fun getName() = "Nuclear Tech ${super.getName()}"

    override fun run(cache: DirectoryCache) {
        super.run(cache)
        validate()
    }

    protected open fun validate() {
        if (!data.keys.containsAll(NuclearLanguageProviders.keys)) {
            val missingTranslations = NuclearLanguageProviders.keys subtract data.keys
            val errorMessage = StringBuilder().appendLine("Missing translations in locale $locale for following keys:")
            for (missing in missingTranslations) errorMessage.appendLine(missing.prependIndent())
            if (exceptionOnMissing) throw IllegalStateException(errorMessage.toString())
            else NuclearTech.LOGGER.error(errorMessage.toString())
        }
    }

    protected fun addBlockDesc(supplier: Supplier<out Block>, desc: String, count: Int = 0) {
        add("${supplier.get().descriptionId}.desc$count", desc)
    }

    protected fun addItemDesc(supplier: Supplier<out Item>, desc: String, count: Int = 0) {
        add("${supplier.get().descriptionId}.desc$count", desc)
    }

    protected fun addContainerType(key: Supplier<out ContainerType<*>>, name: String) {
        add("container.${key.get().registryName!!.namespace}.${key.get().registryName!!.path}", name)
    }

    protected fun addSound(key: Supplier<out SoundEvent>, name: String) {
        add("subtitle.${key.get().registryName!!.namespace}.${key.get().registryName!!.path}", name)
    }

    protected fun addDamageSource(key: DamageSource, name: String) {
        add("death.attack.${key.msgId}", name)
    }

    // TODO replace with formatted strings
    protected open fun addSirenTrack(supplier: Supplier<out Item>, name: String, loop: Boolean, range: Int) {
        val baseID = supplier.get().descriptionId
        add(baseID, "Siren Track - $name")
        add("$baseID.name", name)
        add("$baseID.type", if (loop) "Type: Loop" else "Type: Play Once")
        add("$baseID.range", "Range: $range Meters")
    }

    companion object {
        @JvmStatic
        protected val MODID = NuclearTech.MODID
    }
}
