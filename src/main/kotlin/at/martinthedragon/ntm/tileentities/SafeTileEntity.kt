package at.martinthedragon.ntm.tileentities

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.containers.SafeContainer
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import java.util.function.Supplier

class SafeTileEntity : TileEntity(TileEntityTypes.safeTileEntityType), INamedContainerProvider {
    var customName: ITextComponent? = null
    private val inventory = object : ItemStackHandler(15) {
        override fun onContentsChanged(slot: Int) {
            markDirty()
        }
    }

    private val inventoryCapability: LazyOptional<IItemHandler> = LazyOptional.of { inventory }

    override fun remove() {
        super.remove()
        inventoryCapability.invalidate()
    }

    override fun getUpdateTag(): CompoundNBT = write(CompoundNBT())

    override fun write(nbt: CompoundNBT): CompoundNBT {
        val nbt2 = super.write(nbt)
        // probably due to some kind of bug that persisted in kotlin 1.3.72
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        if (customName != null)
            nbt2.putString("CustomName", ITextComponent.Serializer.toJson(customName))
        nbt2.merge(inventory.serializeNBT())
        return nbt2
    }

    override fun func_230337_a_(state: BlockState, nbt: CompoundNBT) {
        super.func_230337_a_(state, nbt)
        if (nbt.contains("CustomName", 8)) {
            customName = ITextComponent.Serializer.func_240643_a_(nbt.getString("CustomName"))
        }
        inventory.deserializeNBT(nbt)
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(this::createHandler).cast()
        }
        return super.getCapability(cap, side)
    }

    private fun createHandler() = inventory

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
            SafeContainer(windowId, playerInventory, this)

    override fun getDisplayName() = customName ?: TranslationTextComponent(ModBlocks.safe.block.translationKey)
}
