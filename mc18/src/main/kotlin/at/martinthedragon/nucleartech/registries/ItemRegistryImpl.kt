package at.martinthedragon.nucleartech.registries

import at.martinthedragon.nucleartech.RegistriesAndLifecycle
import at.martinthedragon.nucleartech.coremodules.forge.registries.RegistryObjectImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.item.ItemImplItem
import at.martinthedragon.nucleartech.registerK

class ItemRegistryImpl : ItemRegistry {
    override fun <I : Item> register(name: String, sup: () -> I) = RegistryObjectImpl(RegistriesAndLifecycle.ITEMS.registerK(name) { ItemImplItem(sup()) })
}
