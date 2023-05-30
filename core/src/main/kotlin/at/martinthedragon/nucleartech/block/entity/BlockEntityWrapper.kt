package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.coremodules.forge.items.IItemHandlerModifiable
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

interface BlockEntityWrapper {
    val blockPosWrapped: BlockPos
    val levelWrapped: Level?
    val levelUnchecked: Level

    val hasInventory: Boolean
    fun getInventory(): IItemHandlerModifiable
}
