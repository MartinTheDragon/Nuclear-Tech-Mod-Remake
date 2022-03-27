package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.items.SirenTrackItem
import at.martinthedragon.nucleartech.menus.SirenMenu
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

class SirenBlockEntity(pos: BlockPos, state: BlockState) : RandomizableContainerBlockEntity(BlockEntityTypes.sirenBlockEntityType.get(), pos, state) {
    private var items = NonNullList.withSize(1, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is SirenTrackItem

        override fun onContentsChanged(slot: Int) {
            if (blockState.getValue(BlockStateProperties.POWERED)) {
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

            if (item is SirenTrackItem) {
                val sound = SimpleSoundInstance(
                        item.soundSupplier.get().location,
                        SoundSource.BLOCKS,
                        item.range.toFloat(),
                        1f,
                        item.loop,
                        0,
                        SoundInstance.Attenuation.LINEAR,
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

    override fun getContainerSize(): Int = 1

    override fun getUpdateTag(): CompoundTag = saveWithoutMetadata()

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        nbt.merge(inventory.serializeNBT())
    }

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        inventory.deserializeNBT(nbt)
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

    override fun createMenu(windowId: Int, playerInventory: Inventory) = SirenMenu(windowId, playerInventory, this)

    override fun getDefaultName(): Component = TranslatableComponent("container.nucleartech.siren")

    override fun getItems(): NonNullList<ItemStack> = items

    override fun setItems(newItems: NonNullList<ItemStack>) {
        items = newItems
    }

    companion object {
        private val mapSoundPositions = mutableMapOf<BlockPos, SoundInstance>()
    }
}
