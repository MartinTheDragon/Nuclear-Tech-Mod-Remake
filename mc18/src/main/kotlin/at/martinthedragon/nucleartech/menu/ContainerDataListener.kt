package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.menu.slots.data.NTechDataSlot
import net.minecraft.world.level.block.entity.BlockEntity

interface ContainerDataListener<T : BlockEntity> {
    fun dataChanged(menu: NTechContainerMenu<T>, data: NTechDataSlot.Data)
}
