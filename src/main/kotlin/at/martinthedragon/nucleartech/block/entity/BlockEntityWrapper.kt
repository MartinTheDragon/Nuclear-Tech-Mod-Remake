package at.martinthedragon.nucleartech.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraftforge.items.IItemHandlerModifiable

interface BlockEntityWrapper {
    val blockPosWrapped: BlockPos
    val levelWrapped: Level?
    val levelUnchecked: Level

    val hasInventory: Boolean
    fun getInventory(): IItemHandlerModifiable
}
