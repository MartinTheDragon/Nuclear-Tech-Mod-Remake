package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.api.fluid.trait.FluidTrait
import at.martinthedragon.nucleartech.api.fluid.trait.getTraitForFluidStack
import at.martinthedragon.nucleartech.block.entity.UpgradeableMachine
import at.martinthedragon.nucleartech.capability.CapabilityCache
import at.martinthedragon.nucleartech.extensions.ifPresentInline
import at.martinthedragon.nucleartech.fluid.trait.FluidTraitManager
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.tags.TagKey
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidTank
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.DistExecutor.SafeSupplier

inline fun addPlayerInventory(
    addSlot: (Slot) -> Slot,
    playerInventory: Container,
    xStart: Int,
    yStart: Int,
    slotCreator: (inventory: Container, index: Int, x: Int, y: Int) -> Slot = ::Slot
) {
    val slotSize = 18
    val rows = 3
    val columns = 9

    for (i in 0 until rows)
        for (j in 0 until columns) {
            addSlot(slotCreator(playerInventory, j + i * 9 + 9, xStart + j * slotSize, yStart + i * slotSize))
        }
    val newYStart = yStart + slotSize * rows + 4

    for (i in 0 until columns) {
        addSlot(slotCreator(playerInventory, i, xStart + i * slotSize, newYStart))
    }
}

/** Should not be used outside Container.quickMoveStack */
fun AbstractContainerMenu.tryMoveInPlayerInventory(index: Int, inventoryStart: Int, itemStack: ItemStack): Boolean {
    if (index >= inventoryStart && index < (slots.size - 9).coerceAtLeast(inventoryStart + 1)) {
        if (!moveItemStackTo(itemStack, (slots.size - 9).coerceAtLeast(inventoryStart), slots.size, false))
            return false
    } else if (index >= (slots.size - 9) && index < slots.size && !moveItemStackTo(itemStack, inventoryStart, (slots.size - 9), false))
        return false
    return true
}

inline fun AbstractContainerMenu.quickMoveStackBoilerplate(player: Player, index: Int, playerInventoryStart: Int, outputSlots: IntArray, moveContextBuilder: MenuMoveFunctionContext.() -> IntRange?): ItemStack {
    var returnStack = ItemStack.EMPTY
    val slot = slots[index]
    if (slot.hasItem()) {
        val itemStack = slot.item
        returnStack = itemStack.copy()
        if (index in outputSlots) {
            if (!moveItemStackTo(itemStack, playerInventoryStart, slots.size, true)) return ItemStack.EMPTY
            slot.onQuickCraft(itemStack, returnStack)
        } else if (index !in 0 until playerInventoryStart) {
            val context = MenuMoveFunctionContext(this, itemStack)
            val defaultSlots = context.run(moveContextBuilder)
            val success = context.evaluate()
            if (!success &&
                defaultSlots?.let { !moveItemStackTo(itemStack, defaultSlots.first, defaultSlots.last + 1, false) } == true &&
                !tryMoveInPlayerInventory(index, playerInventoryStart, itemStack)
                ) {
                return ItemStack.EMPTY
            }
        } else if (!moveItemStackTo(itemStack, playerInventoryStart, slots.size, false)) return ItemStack.EMPTY

        if (itemStack.isEmpty) slot.set(ItemStack.EMPTY)
        else slot.setChanged()

        if (itemStack.count == returnStack.count) return ItemStack.EMPTY

        slot.onTake(player, itemStack)
    }
    return returnStack
}

class MenuMoveFunctionContext(private val menu: AbstractContainerMenu, val itemStack: ItemStack) {
    private val conditionMap = Object2ObjectArrayMap<(ItemStack) -> Boolean, IntRange>()

    fun evaluate(): Boolean {
        for ((condition, slots) in conditionMap.object2ObjectEntrySet()) {
            if (condition(itemStack) && menu.moveItemStackTo(itemStack, slots.first, slots.last + 1, false))
                return true
        }
        return false
    }

    infix fun IntRange.check(condition: (ItemStack) -> Boolean) {
        conditionMap += condition to this
    }

    infix fun Int.check(condition: (ItemStack) -> Boolean) {
        this..this check condition
    }

    infix fun ((ItemStack) -> Boolean).and(condition: (ItemStack) -> Boolean) = { stack: ItemStack -> this(stack) && condition(stack) }

    fun itemTagCondition(tag: TagKey<Item>) = { stack: ItemStack -> stack.`is`(tag) }
    inline fun <reified T> itemIsInstanceCondition() = { stack: ItemStack -> stack.item is T }
    fun compatibleMachineUpgradeCondition(machine: UpgradeableMachine) = { stack: ItemStack -> MachineUpgradeItem.isValidFor(machine, stack) }

    private val capabilityCache = CapabilityCache()
    fun <T : Any> getCapability(capability: Capability<T>): LazyOptional<T> = capabilityCache.getOrAddToCache(capability, null, itemStack::getCapability)
    fun hasCapability(capability: Capability<*>) = getCapability(capability).isPresent

    fun supportsEnergyCondition() = { _: ItemStack -> hasCapability(CapabilityEnergy.ENERGY) }
    fun supportsFluidsCondition() = { _: ItemStack -> hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) }

    fun containsFluidCondition(fluid: Fluid) = { _: ItemStack -> getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresentInline { !it.drain(FluidStack(fluid, Int.MAX_VALUE), IFluidHandler.FluidAction.SIMULATE).isEmpty } == true }
    fun canDrainTankCondition(outputTank: FluidTank) = { _: ItemStack -> getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresentInline { it.fill(FluidStack(outputTank.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE) > 0 || it.fill(FluidStack(outputTank.fluid.rawFluid, outputTank.capacity), IFluidHandler.FluidAction.SIMULATE) > 0 } == true }
    fun canFillTankCondition(inputTank: FluidTank) = { _: ItemStack -> getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresentInline { !it.drain(FluidStack(inputTank.fluid.rawFluid, 1), IFluidHandler.FluidAction.SIMULATE).isEmpty || !it.drain(FluidStack(inputTank.fluid.rawFluid, inputTank.capacity), IFluidHandler.FluidAction.SIMULATE).isEmpty } == true }

    inline fun <reified T : FluidTrait> fluidTraitCondition(crossinline fluidGetter: (ItemStack) -> Fluid, crossinline condition: (AttachedFluidTrait<T>) -> Boolean) = { stack: ItemStack -> FluidTraitManager.getTraitForFluidStack<T>(FluidStack(fluidGetter(stack), 1000))?.let(condition) == true }
}

fun <T : BlockEntity> getBlockEntityForContainer(buffer: FriendlyByteBuf): T = DistExecutor.safeRunForDist({ ClientBlockEntityGetter(buffer) }, ::ServerBlockEntityGetter)

private class ServerBlockEntityGetter : SafeSupplier<Nothing> {
    override fun get(): Nothing {
        throw IllegalAccessException("Cannot call function on server")
    }
}

private class ClientBlockEntityGetter<out T : BlockEntity>(private val buffer: FriendlyByteBuf) : SafeSupplier<@UnsafeVariance T> {
    @Suppress("UNCHECKED_CAST")
    override fun get(): T {
        val pos = buffer.readBlockPos()
        return (Minecraft.getInstance().level?.getBlockEntity(pos) ?: throw IllegalStateException("Invalid block entity position sent from server: $pos"))
            as? T ?: throw IllegalStateException("Cannot open container on wrong block entity")
    }
}
