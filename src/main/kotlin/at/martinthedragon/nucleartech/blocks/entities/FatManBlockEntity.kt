package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.blocks.entities.BombBlockEntity
import at.martinthedragon.nucleartech.blocks.FatManBlock
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.explosions.Explosions
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.menus.FatManMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class FatManBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.fatManBlockEntityType.get(), pos, state), BombBlockEntity<FatManBlockEntity> {
    // Bits in following order: Is Ready?, Propellant 1, Propellant 2, Propellant 3, Propellant 4, Bomb Igniter, Plutonium Core
    private var bombCompletion = 0b0

    private val dataAccess = object : ContainerData {
        override fun get(index: Int): Int = when (index) {
            0 -> bombCompletion
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> bombCompletion = value
            }
        }

        override fun getCount() = 1
    }

    private val items = NonNullList.withSize(6, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
            in 0..3 -> stack.item == ModItems.bundleOfImplosionPropellant.get()
            4 -> stack.item == ModItems.bombIgniterFatMan.get()
            5 -> stack.item == ModItems.plutoniumCore.get()
            else -> false
        }
    }

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        items.clear()
        ContainerHelper.loadAllItems(nbt, items)
        updateBombCompletion()
    }

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        ContainerHelper.saveAllItems(nbt, items)
    }

    override fun getRequiredDetonationComponents() = FatManBlock.requiredComponents

    override fun detonate(): Boolean = if (level != null && !level!!.isClientSide) {
        setRemoved()
        level!!.removeBlock(worldPosition, false)
        level!!.playSound(null, worldPosition, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1F, level!!.random.nextFloat() * .1F + .9F)
        Explosions.getBuiltinDefault().create(level!!, blockPos.toVec3Middle(), NuclearConfig.explosions.fatManStrength.get())
    } else false

    override fun setChanged() {
        updateBombCompletion()
        super.setChanged()
    }

    private fun updateBombCompletion() {
        var result = 0
        if (getItem(0).let { it.item == ModItems.bundleOfImplosionPropellant.get() && !it.isEmpty }) result = result or 0b100000
        if (getItem(1).let { it.item == ModItems.bundleOfImplosionPropellant.get() && !it.isEmpty }) result = result or 0b10000
        if (getItem(2).let { it.item == ModItems.bundleOfImplosionPropellant.get() && !it.isEmpty }) result = result or 0b1000
        if (getItem(3).let { it.item == ModItems.bundleOfImplosionPropellant.get() && !it.isEmpty }) result = result or 0b100
        if (getItem(4).let { it.item == ModItems.bombIgniterFatMan.get() && !it.isEmpty }) result = result or 0b10
        if (getItem(5).let { it.item == ModItems.plutoniumCore.get() && !it.isEmpty }) result = result or 0b1
        if (result == 0b111111) result = 0b1111111
        bombCompletion = result
    }

    override fun clearContent() { items.clear() }
    override fun getContainerSize() = items.size
    override fun isEmpty() = items.all { it.isEmpty }
    override fun getItem(slot: Int) = items[slot]
    override fun removeItem(slot: Int, amount: Int): ItemStack = ContainerHelper.removeItem(items, slot, amount)
    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)
    override fun setItem(slot: Int, itemStack: ItemStack) {
        items[slot] = itemStack
        if (itemStack.count > maxStackSize)
            itemStack.count = maxStackSize
    }
    override fun stillValid(player: Player) = if (level!!.getBlockEntity(worldPosition) != this) false
    else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64

    override fun createMenu(windowID: Int, playerInventory: Inventory) = FatManMenu(windowID, playerInventory, this, dataAccess)

    override fun getDefaultName() = TranslatableComponent("container.${NuclearTech.MODID}.fat_man")

    private var inventoryCapability = LazyOptional.of(this::inventory)

    override fun <T : Any?> getCapability(cap: Capability<T>, direction: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryCapability.cast()
        }
        return super.getCapability(cap, direction)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability.invalidate()
    }
}
