package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.menu.CombustionGeneratorMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.ContainerHelper
import net.minecraft.world.Containers
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.fluids.capability.templates.FluidTank
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class CombustionGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.combustionGeneratorBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    private var litDuration = 0
    private var litTime = 0
    val isLit: Boolean
        get() = litTime > 0
    private var water: Int
        get() = tank.fluidAmount
        set(value) { tank.fluid.amount = value }
    private var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    private val dataAccess = object : ContainerData {
        override fun get(index: Int): Int = when (index) {
            0 -> litTime
            1 -> litDuration
            2 -> water
            3 -> energy
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> litTime = value
                1 -> litDuration = value
                2 -> water
                3 -> energy
            }
        }

        override fun getCount() = 4
    }

    private val items = NonNullList.withSize(4, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
            0 -> ForgeHooks.getBurnTime(stack, null) > 0
            1 -> {
                val fluid = FluidUtil.getFluidContained(stack).let { if (!it.isPresent) return false else it.get() }
                tank.isFluidValid(fluid)
            }
            2 -> FluidUtil.getFluidContained(stack).let { if (!it.isPresent) return false else it.get() }.isEmpty
            3 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
            else -> true
        }
    }
    private val tank = object : FluidTank(MAX_WATER, { it.fluid == Fluids.WATER }) {
        override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction?) = FluidStack.EMPTY
        override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction?) = FluidStack.EMPTY
    }
    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, 0, ENERGY_TRANSFER_RATE)

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        items.clear()
        ContainerHelper.loadAllItems(nbt, items)
        litTime = nbt.getInt("BurnTime")
        litDuration = ForgeHooks.getBurnTime(items[0], RecipeType.SMELTING)
        tank.readFromNBT(nbt.getCompound("WaterTank"))
        energy = nbt.getInt("Energy")
    }

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        ContainerHelper.saveAllItems(nbt, items)
        nbt.putInt("BurnTime", litTime)
        val fluidTankNbt = CompoundTag()
        tank.writeToNBT(fluidTankNbt)
        nbt.put("WaterTank", fluidTankNbt)
        nbt.putInt("Energy", energy)
    }

    override fun clearContent() {
        items.clear()
    }

    override fun getContainerSize() = 4

    override fun isEmpty() = items.all { it.isEmpty } && tank.isEmpty && energyStorage.energyStored == 0

    override fun getItem(slot: Int): ItemStack = items[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack = ContainerHelper.removeItem(items, slot, amount)

    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        items[slot] = itemStack
        if (itemStack.count > maxStackSize)
            itemStack.count = maxStackSize
    }

    override fun stillValid(player: Player): Boolean {
        return if (level!!.getBlockEntity(worldPosition) != this) false
        else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64
    }

    override fun createMenu(windowId: Int, playerInventory: Inventory) =
        CombustionGeneratorMenu(windowId, playerInventory, this, dataAccess)

    override fun getDefaultName(): Component = TranslatableComponent("container.${NuclearTech.MODID}.combustion_generator")

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val wasLit = isLit
        var contentsChanged = false

        if (isLit)
            litTime--

        val fuel = items[0]
        if (!fuel.isEmpty && !isLit) {
            litTime = ForgeHooks.getBurnTime(fuel, null) / 2
            litDuration = litTime
            if (isLit) {
                contentsChanged = true
                if (fuel.hasContainerItem()) items[0] = fuel.containerItem
                else if (!fuel.isEmpty) {
                    fuel.shrink(1)
                    if (fuel.isEmpty) items[0] = fuel.containerItem
                }
            }
        }

        val waterInput = items[1]
        val outputSlot = items[2]
        if (!waterInput.isEmpty) {
            val result = FluidUtil.tryEmptyContainer(waterInput, tank, Int.MAX_VALUE, null, true)
            val resultItem = result.getResult()
            if (result.isSuccess) if (FluidUtil.getFluidHandler(resultItem)
                    .let { // check whether the resulting container has no water left
                        if (!it.isPresent) true
                        else it.resolve().get()
                            .drain(FluidStack(Fluids.WATER, Int.MAX_VALUE), IFluidHandler.FluidAction.SIMULATE)
                            .isEmpty
                    }
            ) { // on success, transfer random if possible
                when {
                    outputSlot.isEmpty -> {
                        waterInput.shrink(1)
                        items[2] = resultItem.copy()
                    }
                    ItemStack.isSame(outputSlot, resultItem) &&
                            ItemStack.tagMatches(outputSlot, resultItem) &&
                            outputSlot.count + resultItem.count <= maxStackSize &&
                            outputSlot.count + resultItem.count <= outputSlot.maxStackSize -> {
                        waterInput.shrink(1)
                        outputSlot.grow(1)
                    }
                    else -> {
                        if (waterInput.count > 1) // if somehow there is more than one item in the input slot, drop the rest
                            Containers.dropContents(level, pos, NonNullList.of(ItemStack.EMPTY, waterInput.copy().apply { count-- }))
                        items[1] = resultItem.copy()
                    }
                }
            } else items[1] = resultItem.copy() // if the item still has fluid, update it
        }

        if (energy > 0) {
            val energyItemSlot = items[3]
            if (!energyItemSlot.isEmpty) transferEnergy(energyStorage, energyItemSlot)

            // TODO cache somehow TODO improve distribution
            val hungryConsumers = Direction.values().associateWith {
                val tileEntity = level.getBlockEntity(blockPos.relative(it)) ?: return@associateWith null
                tileEntity.getCapability(CapabilityEnergy.ENERGY, it.opposite)
            }.filterValues { it != null && it.isPresent }.map { (_, consumer) -> consumer!!.resolve().get() }

            if (hungryConsumers.isNotEmpty()) {
                val energyForEach = energy / hungryConsumers.size
                for (consumer in hungryConsumers) {
                    transferEnergy(energyStorage, consumer, energyForEach)
                }
            }
        }

        if (isLit && water - WATER_CONSUMPTION_RATE >= 0) {
            tank.fluid.shrink(WATER_CONSUMPTION_RATE)
            energy = (energy + ENERGY_PRODUCTION_RATE).coerceIn(0, MAX_ENERGY)
            contentsChanged = true
        }

        if (wasLit != isLit) {
            contentsChanged = true
            level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(BlockStateProperties.LIT, isLit))
        }
        if (contentsChanged) setChanged()
    }

    private val itemHolder = LazyOptional.of { inventory }
    private val tankHolder = LazyOptional.of { tank }
    private val energyHolder = LazyOptional.of { energyStorage }

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove) when (cap) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> return itemHolder.cast()
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> return tankHolder.cast()
            CapabilityEnergy.ENERGY -> return energyHolder.cast()
        }
        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        itemHolder.invalidate()
        tankHolder.invalidate()
        energyHolder.invalidate()
    }

    companion object {
        const val MAX_ENERGY = 400_000
        const val MAX_WATER = FluidAttributes.BUCKET_VOLUME * 10
        const val WATER_CONSUMPTION_RATE = 2
        const val ENERGY_PRODUCTION_RATE = 50
        const val ENERGY_TRANSFER_RATE = ENERGY_PRODUCTION_RATE * 10
    }
}
