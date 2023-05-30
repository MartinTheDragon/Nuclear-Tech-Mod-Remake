package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKConsoleBlockEntity
import at.martinthedragon.nucleartech.block.multi.MultiBlockPart
import at.martinthedragon.nucleartech.block.rbmk.RBMKConsoleBlock
import at.martinthedragon.nucleartech.block.rbmk.RBMKPart
import at.martinthedragon.nucleartech.extensions.*
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class RBMKLinkerItem(properties: Properties) : AutoTooltippedItem(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        if (!context.isSecondaryUseActive) return InteractionResult.PASS
        val player = context.player ?: return InteractionResult.FAIL
        val level = context.level
        val clickedPos = context.clickedPos
        val blockState = level.getBlockState(clickedPos)
        val block = blockState.block
        val item = context.itemInHand
        val tags = item.tag

        if (block is RBMKPart) {
            setTarget(item.orCreateTag, block.getTopBlockPos(level, clickedPos, blockState))
            if (level.isClientSide) player.displayClientMessage(LangKeys.DEVICE_POSITION_SET.yellow(), true)
            return InteractionResult.sidedSuccess(level.isClientSide)
        } else {
            val consolePos = when (block) {
                is RBMKConsoleBlock -> clickedPos
                is MultiBlockPart -> {
                    val part = level.getBlockEntity(clickedPos) as? MultiBlockPart.MultiBlockPartBlockEntity ?: return InteractionResult.FAIL
                    part.core
                }
                else -> return InteractionResult.PASS
            }

            if (tags != null && hasTags(tags)) {
                val targetPos = getTarget(tags)
                if (!level.isLoaded(targetPos)) {
                    if (level.isClientSide) player.displayClientMessage(LangKeys.DEVICE_POSITION_NOT_LOADED.red(), true)
                    return InteractionResult.FAIL
                }

                val console = level.getBlockEntity(consolePos) as? RBMKConsoleBlockEntity ?: return InteractionResult.FAIL
                console.scanTarget = targetPos

                if (level.isClientSide) player.displayClientMessage(LangKeys.RBMK_CONSOLE_LINK.green(), true)
                return InteractionResult.sidedSuccess(level.isClientSide)
            }

            if (level.isClientSide) player.displayClientMessage(LangKeys.DEVICE_POSITION_NOT_SET.red(), true)
            return InteractionResult.FAIL
        }
    }

    private fun hasTags(tag: CompoundTag) = tag.contains("TargetX", 3) && tag.contains("TargetY", 3) && tag.contains("TargetZ", 3)
    private fun getTarget(tag: CompoundTag) = BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"))
    private fun setTarget(tag: CompoundTag, pos: BlockPos) {
        tag.putInt("TargetX", pos.x)
        tag.putInt("TargetY", pos.y)
        tag.putInt("TargetZ", pos.z)
    }

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(stack, level, tooltip, flag)

        tooltip += if (!stack.hasTag() || !hasTags(stack.tag!!))
            LangKeys.DEVICE_POSITION_NOT_SET.darkRed()
        else {
            val (x, y, z) = getTarget(stack.tag!!)
            LangKeys.INFO_POSITION.format(x, y, z).gray()
        }
    }
}
