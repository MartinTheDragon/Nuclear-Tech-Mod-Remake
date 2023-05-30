@file:Suppress("PropertyName")

package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.registries.Registries

const val MOD_ID = "nucleartech"
fun ntm(path: String) = ResourceLocation(MOD_ID, path)

/**
 * To be implemented by the mod class
 */
interface ModClass {
    /**
     * To be called after dependency injection registration when initializing the mod class
     */
    fun entryPoint() = launchMod(this)

    val LOGGER: Logger
    val polaroidBroken: Boolean
}

internal lateinit var NTech: ModClass

internal fun launchMod(modInstance: ModClass) {
    NTech = modInstance
    Registries.initRegistries()
}
