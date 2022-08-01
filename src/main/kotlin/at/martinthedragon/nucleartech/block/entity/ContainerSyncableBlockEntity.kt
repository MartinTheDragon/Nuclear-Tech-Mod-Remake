package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.menu.NTechContainerMenu

interface ContainerSyncableBlockEntity {
    fun trackContainerMenu(menu: NTechContainerMenu<*>)
}
