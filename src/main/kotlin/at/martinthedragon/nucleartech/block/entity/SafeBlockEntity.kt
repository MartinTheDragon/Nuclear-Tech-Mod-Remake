package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.menu.SafeMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

class SafeBlockEntity(pos: BlockPos, state: BlockState) : RandomizableContainerBlockEntity(BlockEntityTypes.safeBlockEntityType.get(), pos, state) {
    private var items = NonNullList.withSize(15, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            setChanged()
        }
    }

    override fun getContainerSize(): Int = 15

    private var inventoryCapability: LazyOptional<IItemHandlerModifiable>? = null

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        nbt.merge(inventory.serializeNBT())
    }

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        inventory.deserializeNBT(nbt)
    }

    override fun createMenu(windowId: Int, playerInventory: Inventory): AbstractContainerMenu =
        SafeMenu(windowId, playerInventory, this)

    override fun getDefaultName() = TranslatableComponent("container.nucleartech.safe")

    override fun getItems(): NonNullList<ItemStack> = items

    override fun setItems(newItems: NonNullList<ItemStack>) {
        items = newItems
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (inventoryCapability == null)
                inventoryCapability = LazyOptional.of(this::createHandler)
            return inventoryCapability!!.cast()
        }
        return super.getCapability(cap, side)
    }

    private fun createHandler(): IItemHandlerModifiable = inventory

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability?.invalidate()
    }
}
