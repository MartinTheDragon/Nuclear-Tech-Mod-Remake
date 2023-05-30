package at.martinthedragon.nucleartech.coremodules.minecraft.world.item

import at.martinthedragon.nucleartech.coremodules.InjectionFactories
import at.martinthedragon.nucleartech.sorcerer.Injected

@Injected
interface CreativeModeTab {
    fun makeIcon(): ItemStack

    interface Factory {
        fun create(id: String, iconGetter: () -> ItemStack): CreativeModeTab
    }
}

fun CreativeModeTab(id: String, iconGetter: () -> ItemStack) = InjectionFactories.creativeModeTab.create(id, iconGetter)
