package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.BombBlockEntity
import at.martinthedragon.nucleartech.api.explosion.NuclearExplosionMk4Params
import at.martinthedragon.nucleartech.block.LittleBoyBlock
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.explosion.Explosions
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.menu.LittleBoyMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class LittleBoyBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.littleBoyBlockEntityType.get(), pos, state), BombBlockEntity<LittleBoyBlockEntity> {
    // Bits in following order: Is Ready?, Neutron Shielding, Target, Projectile, Propellant, Igniter
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

    private val items = NonNullList.withSize(5, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
            0 -> stack.item == NTechItems.neutronShieldingLittleBoy.get()
            1 -> stack.item == NTechItems.subcriticalUraniumTarget.get()
            2 -> stack.item == NTechItems.uraniumProjectile.get()
            3 -> stack.item == NTechItems.propellantLittleBoy.get()
            4 -> stack.item == NTechItems.bombIgniterLittleBoy.get()
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

    override fun createMenu(windowID: Int, playerInventory: Inventory) =
        LittleBoyMenu(windowID, playerInventory, this, dataAccess)

    override fun getDefaultName() = LangKeys.CONTAINER_LITTLE_BOY.get()

    override fun setChanged() {
        updateBombCompletion()
        super.setChanged()
    }

    private fun updateBombCompletion() {
        var result = 0
        if (itemInSlotIs(0, NTechItems.neutronShieldingLittleBoy.get())) result = result or (1 shl 4)
        if (itemInSlotIs(1, NTechItems.subcriticalUraniumTarget.get())) result = result or (1 shl 3)
        if (itemInSlotIs(2, NTechItems.uraniumProjectile.get())) result = result or (1 shl 2)
        if (itemInSlotIs(3, NTechItems.propellantLittleBoy.get())) result = result or (1 shl 1)
        if (itemInSlotIs(4, NTechItems.bombIgniterLittleBoy.get())) result = result or 1
        if (result == 0b11111) result = result or (1 shl 5)
        bombCompletion = result
    }

    private fun itemInSlotIs(slot: Int, item: Item) : Boolean =
        getItem(slot).let { it.item == item && !it.isEmpty }

    override fun getRequiredDetonationComponents() = LittleBoyBlock.requiredComponents

    override fun detonate(): Boolean = if (level != null && !level!!.isClientSide) {
        setRemoved()
        level!!.removeBlock(worldPosition, false)
        level!!.playSound(null, worldPosition, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1F, level!!.random.nextFloat() * .1F + .9F)
        Explosions.getBuiltinDefault().createAndStart(level!!, blockPos.toVec3Middle(), NuclearConfig.explosions.littleBoyStrength.get().toFloat(), NuclearExplosionMk4Params())
    } else false

    private val inventoryCapability = LazyOptional.of(this::inventory)

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
