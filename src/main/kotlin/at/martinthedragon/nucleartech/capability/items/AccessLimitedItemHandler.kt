package at.martinthedragon.nucleartech.capability.items

import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler

open class AccessLimitedItemHandler(private val backer: IItemHandler, val slots: IntArray) : IItemHandler {
    constructor(backer: IItemHandler, slot: Int) : this(backer, intArrayOf(slot))
    constructor(backer: IItemHandler, slotRange: IntRange) : this(backer, slotRange.toList().toIntArray())
    constructor(backer: IItemHandler, vararg slotRanges: IntRange) : this(backer, slotRanges.flatMap(IntRange::asIterable).toIntArray())

    init {
        check(backer.slots >= slots.size && backer.slots >= slots.maxOf { it }) { "There are slots in the slots array that don't exist" }
    }

    override fun getSlots() = slots.size
    override fun getStackInSlot(slot: Int) = backer.getStackInSlot(slots[slot])
    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = backer.insertItem(slots[slot], stack, simulate)
    override fun extractItem(slot: Int, amount: Int, simulate: Boolean) = backer.extractItem(slots[slot], amount, simulate)
    override fun getSlotLimit(slot: Int) = backer.getSlotLimit(slots[slot])
    override fun isItemValid(slot: Int, stack: ItemStack) = backer.isItemValid(slots[slot], stack)
}

class AccessLimitedInputItemHandler(backer: IItemHandler, slots: IntArray) : AccessLimitedItemHandler(backer, slots) {
    constructor(backer: IItemHandler, slot: Int) : this(backer, intArrayOf(slot))
    constructor(backer: IItemHandler, slotRange: IntRange) : this(backer, slotRange.toList().toIntArray())
    constructor(backer: IItemHandler, vararg slotRanges: IntRange) : this(backer, slotRanges.flatMap(IntRange::asIterable).toIntArray())

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack = ItemStack.EMPTY
}

class AccessLimitedOutputItemHandler(backer: IItemHandler, slots: IntArray) : AccessLimitedItemHandler(backer, slots) {
    constructor(backer: IItemHandler, slot: Int) : this(backer, intArrayOf(slot))
    constructor(backer: IItemHandler, slotRange: IntRange) : this(backer, slotRange.toList().toIntArray())
    constructor(backer: IItemHandler, vararg slotRanges: IntRange) : this(backer, slotRanges.flatMap(IntRange::asIterable).toIntArray())

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean) = stack
    override fun isItemValid(slot: Int, stack: ItemStack) = false
}
