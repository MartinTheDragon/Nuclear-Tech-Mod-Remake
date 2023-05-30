package at.martinthedragon.nucleartech.coremodules

import org.koin.core.component.KoinComponent

interface NTechKoinComponent : KoinComponent {
    override fun getKoin() = koinApp.koin
}
