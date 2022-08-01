package at.martinthedragon.nucleartech.api.menu.slot

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler
import kotlin.math.min

public open class ResultSlot(protected val player: Player, inventory: IItemHandler, index: Int, xPosition: Int, yPosition: Int) : OutputSlot(inventory, index, xPosition, yPosition) {
    protected var removeCount: Int = 0

    override fun remove(amount: Int): ItemStack {
        if (hasItem()) removeCount += min(amount, item.count)
        return super.remove(amount)
    }

    override fun onTake(player: Player, itemStack: ItemStack) {
        checkTakeAchievements(itemStack)
        super.onTake(player, itemStack)
    }

    override fun onQuickCraft(oldStackIn: ItemStack, newStackIn: ItemStack) {
        val count = newStackIn.count - oldStackIn.count
        if (count > 0) onQuickCraft(newStackIn, count)
    }

    override fun onQuickCraft(itemStack: ItemStack, amount: Int) {
        removeCount += amount
        checkTakeAchievements(itemStack)
    }

    override fun onSwapCraft(amount: Int) {
        removeCount += amount
    }

    override fun checkTakeAchievements(stack: ItemStack) {
        if (removeCount > 0) {
            stack.onCraftedBy(player.level, player, removeCount)
        }
        removeCount = 0
    }
}
