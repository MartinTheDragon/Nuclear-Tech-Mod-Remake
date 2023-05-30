package at.martinthedragon.nucleartech.coremodules.forge.registries

import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import java.util.function.Supplier

interface RegistryObject<T> : Supplier<T> {
    val id: ResourceLocation
}
