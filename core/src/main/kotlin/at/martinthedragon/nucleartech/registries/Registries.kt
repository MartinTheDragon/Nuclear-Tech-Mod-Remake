package at.martinthedragon.nucleartech.registries

import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.coremodules.NTechKoinComponent
import at.martinthedragon.nucleartech.item.NTechItemsCore
import org.koin.core.component.inject

object Registries : NTechKoinComponent {
    val itemRegistry: ItemRegistry by inject()
    val soundRegistry: SoundRegistry by inject()

    fun initRegistries() {
        NTechItemsCore
        NTechSoundsCore
    }
}
