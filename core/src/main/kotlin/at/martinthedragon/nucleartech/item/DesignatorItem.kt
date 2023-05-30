package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.darkRed
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.gray
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.green
import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionResult
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.UseOnContext
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

class DesignatorItem(properties: Properties) : Item(properties), TargetDesignator {
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val player = context.player
        val pos = context.clickedPos
        val blockState = level.getBlockState(pos)

        // TODO pass launch pads & co

        if (!level.isClientSide) with(context.itemInHand.getOrCreateTag()) {
            putInt("TargetX", pos.x)
            putInt("TargetY", pos.y)
            putInt("TargetZ", pos.z)
        } else player?.displayClientMessage(LangKeys.DEVICE_POSITION_SET.green(), true)
        player?.playSound(NTechSoundsCore.randomBleep.get(), 1F, 1F)

        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun hasValidTarget(level: Level, tag: CompoundTag, launchPos: BlockPos) = hasTags(tag)
    override fun getTargetPos(level: Level, tag: CompoundTag, launchPos: BlockPos) = getTarget(tag)

    private fun hasTags(tag: CompoundTag) = tag.contains("TargetX", 3) && tag.contains("TargetY", 3) && tag.contains("TargetZ", 3)
    private fun getTarget(tag: CompoundTag) = BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"))

    override fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean) {
        tooltip += if (!itemStack.hasTag() || !hasTags(itemStack.getTag()!!)) LangKeys.DEVICE_POSITION_NOT_SET.darkRed()
        else {
            val target = getTarget(itemStack.getTag()!!)
            LangKeys.INFO_POSITION.format(target.x, target.y, target.z).gray()
        }
    }
}
