package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.containers.SafeContainer
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.LockableLootTileEntity
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

class SafeTileEntity : LockableLootTileEntity(TileEntityTypes.safeTileEntityType.get()) {
    private var items = NonNullList.withSize(15, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            setChanged()
        }
    }

    override fun getContainerSize(): Int = 15

    private var inventoryCapability: LazyOptional<IItemHandlerModifiable>? = null

    override fun clearCache() {
        super.clearCache()
        inventoryCapability?.invalidate()
        inventoryCapability = null
    }

    override fun getUpdateTag(): CompoundNBT = save(CompoundNBT())

    override fun save(nbt: CompoundNBT): CompoundNBT {
        super.save(nbt)
        nbt.merge(inventory.serializeNBT())
        return nbt
    }

    override fun load(state: BlockState, nbt: CompoundNBT) {
        super.load(state, nbt)
        inventory.deserializeNBT(nbt)
    }

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory): Container =
        SafeContainer(windowId, playerInventory, this)

    override fun getDefaultName(): ITextComponent = TranslationTextComponent("container.nucleartech.safe")

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
