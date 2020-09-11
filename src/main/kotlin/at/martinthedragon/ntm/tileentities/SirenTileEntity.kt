package at.martinthedragon.ntm.tileentities

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.blocks.Siren
import at.martinthedragon.ntm.containers.SirenContainer
import at.martinthedragon.ntm.items.SirenTrack
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.SimpleSound
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryHelper
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler

class SirenTileEntity : TileEntity(TileEntityTypes.sirenTileEntityType), INamedContainerProvider {
    private val inventory = object : ItemStackHandler(1) {
        override fun isItemValid(slot: Int, stack: ItemStack) = stack.item is SirenTrack

        override fun onContentsChanged(slot: Int) {
            if (blockState == ModBlocks.siren.defaultState.with(Siren.powered, true)) {
                startPlaying()
            } else
                stopPlaying()
            markDirty()
        }
    }

    private val inventoryCapability: LazyOptional<IItemHandler> = LazyOptional.of { inventory }

    fun startPlaying() {
        if (!world!!.isRemote) {
            stopPlaying()

            val item = inventory.getStackInSlot(0).item

            if (item is SirenTrack) {
                val sound = SimpleSound(
                        item.sound.name,
                        SoundCategory.BLOCKS,
                        item.volume,
                        1f,
                        item.loop,
                        0,
                        ISound.AttenuationType.LINEAR,
                        pos.x + .5,
                        pos.y + .5,
                        pos.z + .5,
                        false
                )
                mapSoundPositions[pos] = sound
                Minecraft.getInstance().soundHandler.play(sound)
            }
        }
    }

    fun stopPlaying() {
        if (!world!!.isRemote) {
            val sound = mapSoundPositions[pos]
            if (sound != null) {
                Minecraft.getInstance().soundHandler.stop(sound)
                mapSoundPositions.remove(pos)
            }
        }
    }

    fun dropSirenTrack() {
        InventoryHelper.spawnItemStack(world!!, pos.x + .5, pos.y + .5, pos.z +.5, inventory.extractItem(0, 1, true))
    }

    override fun remove() {
        super.remove()
        inventoryCapability.invalidate()
    }

    override fun getUpdateTag(): CompoundNBT = write(CompoundNBT())

    override fun write(compound: CompoundNBT): CompoundNBT {
        val nbt = super.write(compound)
        nbt.merge(inventory.serializeNBT())
        return nbt
    }

    override fun func_230337_a_(blockState: BlockState, compound: CompoundNBT) {
        super.func_230337_a_(blockState, compound)

        inventory.deserializeNBT(compound)
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(this::createHandler).cast()
        }
        return super.getCapability(cap, side)
    }

    private fun createHandler() = inventory

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
            SirenContainer(windowId, playerInventory, this)

    override fun getDisplayName(): ITextComponent = TranslationTextComponent(ModBlocks.siren.block.translationKey)

    companion object {
        private val mapSoundPositions = mutableMapOf<BlockPos, ISound>()
    }
}
