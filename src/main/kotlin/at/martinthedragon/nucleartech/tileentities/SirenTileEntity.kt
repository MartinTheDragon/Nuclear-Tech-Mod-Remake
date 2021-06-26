package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.blocks.Siren
import at.martinthedragon.nucleartech.containers.SirenContainer
import at.martinthedragon.nucleartech.items.SirenTrack
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.SimpleSound
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.LockableLootTileEntity
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

class SirenTileEntity : LockableLootTileEntity(TileEntityTypes.sirenTileEntityType.get()) {
    private var items = NonNullList.withSize(1, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is SirenTrack

        override fun onContentsChanged(slot: Int) {
            if (blockState.getValue(Siren.POWERED)) {
                startPlaying()
            } else
                stopPlaying()
            setChanged()
        }
    }

    private var inventoryCapability: LazyOptional<IItemHandlerModifiable>? = null

    fun startPlaying() {
        if (!level!!.isClientSide) {
            stopPlaying()

            val item = inventory.getStackInSlot(0).item

            if (item is SirenTrack) {
                val sound = SimpleSound(
                        item.sound.location,
                        SoundCategory.BLOCKS,
                        item.volume,
                        1f,
                        item.loop,
                        0,
                        ISound.AttenuationType.LINEAR,
                        blockPos.x + .5,
                        blockPos.y + .5,
                        blockPos.z + .5,
                        false
                )
                mapSoundPositions[blockPos] = sound
                Minecraft.getInstance().soundManager.play(sound)
            }
        }
    }

    fun stopPlaying() {
        if (!level!!.isClientSide) {
            val sound = mapSoundPositions[blockPos]
            if (sound != null) {
                Minecraft.getInstance().soundManager.stop(sound)
                mapSoundPositions.remove(blockPos)
            }
        }
    }

    override fun setRemoved() {
        stopPlaying()
        super.setRemoved()
    }

    fun dropSirenTrack() {
        InventoryHelper.dropItemStack(
            level!!,
            blockPos.x + .5,
            blockPos.y + .5,
            blockPos.z +.5,
            inventory.extractItem(0, 1, true))
    }

    override fun clearCache() {
        super.clearCache()
        inventoryCapability?.invalidate()
        inventoryCapability = null
    }

    override fun getContainerSize(): Int = 1

    override fun getUpdateTag(): CompoundNBT = save(CompoundNBT())

    override fun save(nbt: CompoundNBT): CompoundNBT {
        super.save(nbt)
        nbt.merge(inventory.serializeNBT())
        return nbt
    }

    override fun load(blockState: BlockState, compound: CompoundNBT) {
        super.load(blockState, compound)
        inventory.deserializeNBT(compound)
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (inventoryCapability == null)
                inventoryCapability = LazyOptional.of(this::createHandler)
            return inventoryCapability!!.cast()
        }
        return super.getCapability(cap, side)
    }

    private fun createHandler() = inventory

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability?.invalidate()
    }

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory) =
            SirenContainer(windowId, playerInventory, this)

    override fun getDefaultName(): ITextComponent = TranslationTextComponent("container.nucleartech.siren")

    override fun getItems(): NonNullList<ItemStack> = items

    override fun setItems(newItems: NonNullList<ItemStack>) {
        items = newItems
    }

    companion object {
        private val mapSoundPositions = mutableMapOf<BlockPos, ISound>()
    }
}
