package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.api.item.TargetDesignator
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.extensions.darkRed
import at.martinthedragon.nucleartech.extensions.gray
import at.martinthedragon.nucleartech.extensions.green
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class DesignatorItem(properties: Properties) : Item(properties), TargetDesignator {
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val player = context.player
        val pos = context.clickedPos
        val block = level.getBlockState(pos)

        if (block.`is`(NTechBlocks.launchPad.get()) || block.`is`(NTechBlocks.launchPadPart.get())) return InteractionResult.PASS

        if (!level.isClientSide) with(context.itemInHand.orCreateTag) {
            val (x, y, z) = pos
            putInt("TargetX", x)
            putInt("TargetY", y)
            putInt("TargetZ", z)
        } else player?.displayClientMessage(LangKeys.DEVICE_POSITION_SET.green(), true)
        player?.playSound(NTechSoundsCore.randomBleep.get(), 1F, 1F)

        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun hasValidTarget(level: Level, tag: CompoundTag, launchPos: BlockPos) = hasTags(tag)
    override fun getTargetPos(level: Level, tag: CompoundTag, launchPos: BlockPos) = getTarget(tag)

    private fun hasTags(tag: CompoundTag) = tag.contains("TargetX", 3) && tag.contains("TargetY", 3) && tag.contains("TargetZ", 3)
    private fun getTarget(tag: CompoundTag) = BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"))

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip += if (!stack.hasTag() || !hasTags(stack.tag!!)) LangKeys.DEVICE_POSITION_NOT_SET.darkRed()
        else {
            val (x, y, z) = getTarget(stack.tag!!)
            LangKeys.INFO_POSITION.format(x, y, z).gray()
        }
    }
}
