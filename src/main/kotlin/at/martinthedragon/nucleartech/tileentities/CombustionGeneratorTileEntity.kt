package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.blocks.CombustionGenerator
import at.martinthedragon.nucleartech.containers.CombustionGeneratorContainer
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.fluid.Fluids
import net.minecraft.inventory.InventoryHelper
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.LockableTileEntity
import net.minecraft.util.Direction
import net.minecraft.util.IIntArray
import net.minecraft.util.NonNullList
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
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

class CombustionGeneratorTileEntity : LockableTileEntity(TileEntityTypes.combustionGeneratorTileEntityType.get()), ITickableTileEntity {
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

    private val dataAccess = object : IIntArray {
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
            0 -> ForgeHooks.getBurnTime(stack) > 0
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

    override fun load(state: BlockState, nbt: CompoundNBT) {
        super.load(state, nbt)
        items.clear()
        ItemStackHelper.loadAllItems(nbt, items)
        litTime = nbt.getInt("BurnTime")
        litDuration = ForgeHooks.getBurnTime(items[0])
        tank.readFromNBT(nbt.getCompound("WaterTank"))
        energy = nbt.getInt("Energy")
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        super.save(nbt)
        ItemStackHelper.saveAllItems(nbt, items)
        nbt.putInt("BurnTime", litTime)
        val fluidTankNbt = CompoundNBT()
        tank.writeToNBT(fluidTankNbt)
        nbt.put("WaterTank", fluidTankNbt)
        nbt.putInt("Energy", energy)
        return nbt
    }

    override fun clearContent() {
        items.clear()
    }

    override fun getContainerSize() = 4

    override fun isEmpty() = items.all { it.isEmpty } && tank.isEmpty && energyStorage.energyStored == 0

    override fun getItem(slot: Int): ItemStack = items[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack = ItemStackHelper.removeItem(items, slot, amount)

    override fun removeItemNoUpdate(slot: Int): ItemStack = ItemStackHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        items[slot] = itemStack
        if (itemStack.count > maxStackSize)
            itemStack.count = maxStackSize
    }

    override fun stillValid(player: PlayerEntity): Boolean {
        return if (level!!.getBlockEntity(worldPosition) != this) false
        else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64
    }

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory) =
        CombustionGeneratorContainer(windowId, playerInventory, this, dataAccess)

    override fun getDefaultName(): ITextComponent = TranslationTextComponent("container.${NuclearTech.MODID}.combustion_generator")

    override fun tick() {
        if (level!!.isClientSide) return

        val wasLit = isLit
        var contentsChanged = false

        if (isLit)
            litTime--

        val fuel = items[0]
        if (!fuel.isEmpty && !isLit) {
            litTime = ForgeHooks.getBurnTime(fuel) / 2
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
            ) { // on success, transfer items if possible
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
                            InventoryHelper.dropContents(level!!, worldPosition, NonNullList.of(waterInput.copy().apply { count-- }))
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
                val tileEntity = level?.getBlockEntity(blockPos.relative(it)) ?: return@associateWith null
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
            level!!.setBlockAndUpdate(worldPosition, level!!.getBlockState(worldPosition).setValue(CombustionGenerator.LIT, isLit))
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
