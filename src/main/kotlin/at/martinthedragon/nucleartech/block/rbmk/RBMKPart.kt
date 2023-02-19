package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.api.item.ScrewdriverInteractable
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKBase
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.math.toVec3Middle
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

/**
 * Implemented by any RBMK-related block. Useful for `is` checks.
 */
interface RBMKPart : ScrewdriverInteractable {
    fun getTopBlockPos(level: Level, queryPos: BlockPos, state: BlockState): BlockPos

    fun getTopBlock(level: Level, queryPos: BlockPos, state: BlockState): BlockState =
        level.getBlockState(getTopBlockPos(level, queryPos, state))

    fun getTopBlockEntity(level: Level, queryPos: BlockPos, state: BlockState): BlockEntity? =
        level.getBlockEntity(getTopBlockPos(level, queryPos, state))

    fun getTopRBMKBase(level: Level, queryPos: BlockPos, state: BlockState): RBMKBase? =
        getTopBlockEntity(level, queryPos, state) as? RBMKBase

    override fun onScrew(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos
        val blockState = level.getBlockState(clickedPos)
        val rbmk = getTopRBMKBase(level, clickedPos, blockState) ?: return InteractionResult.FAIL

        if (rbmk.hasLid() && rbmk.isLidRemovable) {
            val topBlockPos = getTopBlockPos(level, clickedPos, blockState)
            if (!level.isClientSide && context.player?.abilities?.instabuild != true) {
                val itemSpawnPos = topBlockPos.above().toVec3Middle()
                val item = when (rbmk.getLid()) {
                    RBMKBaseBlock.LidType.CONCRETE -> NTechItems.rbmkLid.get()
                    RBMKBaseBlock.LidType.LEAD_GLASS -> NTechItems.rbmkGlassLid.get()
                    else -> return InteractionResult.FAIL
                }
                level.addFreshEntity(ItemEntity(level, itemSpawnPos.x, itemSpawnPos.y, itemSpawnPos.z, item.defaultInstance))
            }
            level.setBlockAndUpdate(topBlockPos, level.getBlockState(topBlockPos).setValue(RBMKBaseBlock.LID_TYPE, RBMKBaseBlock.LidType.NONE))
            return InteractionResult.sidedSuccess(level.isClientSide)
        }
        return InteractionResult.FAIL
    }
}
