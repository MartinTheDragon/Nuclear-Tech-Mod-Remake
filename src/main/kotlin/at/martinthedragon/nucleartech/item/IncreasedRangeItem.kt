package at.martinthedragon.nucleartech.item

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.extensions.IForgeItem
import net.minecraftforge.event.ItemAttributeModifierEvent
import java.util.*

interface IncreasedRangeItem : IForgeItem {
    val additionalRange: Float

    override fun onBlockStartBreak(itemstack: ItemStack, pos: BlockPos, player: Player) = additionalRange > 0F // prevent mining blocks if the range is increased

    companion object {
        private val INCREASED_RANGE_UUID = UUID.fromString("ded1e5f7-4d8a-4a03-92ec-4f2e08df1742")

        fun addItemStackAttributes(event: ItemAttributeModifierEvent) {
            val item = event.itemStack.item as? IncreasedRangeItem ?: return
            if (item.additionalRange > 0F && event.slotType == EquipmentSlot.MAINHAND) {
                event.addModifier(ForgeMod.REACH_DISTANCE.get(), AttributeModifier(INCREASED_RANGE_UUID, "Increased range multi-block placement", item.additionalRange.toDouble(), AttributeModifier.Operation.ADDITION))
            }
        }

        fun checkCanBreakWithItem(stack: ItemStack, hand: InteractionHand): Boolean {
            if (hand == InteractionHand.OFF_HAND) return true
            val item = stack.item as? IncreasedRangeItem ?: return true
            return item.additionalRange <= 0F
        }
    }
}
