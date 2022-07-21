package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.menus.NTechContainerMenu

interface ContainerSyncableBlockEntity {
    fun trackContainerMenu(menu: NTechContainerMenu<*>)
}
