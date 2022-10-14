package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.entity.transmitters.CableBlockEntity
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.capability.item.AccessLimitedOutputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.fluid.NTechFluidTank
import at.martinthedragon.nucleartech.fluid.tryEmptyFluidContainerAndMove
import at.martinthedragon.nucleartech.menu.CombustionGeneratorMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.FluidStackDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.CapabilityItemHandler

class CombustionGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(BlockEntityTypes.combustionGeneratorBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    var litDuration = 0
        private set
    var litTime = 0
        private set
    private val isLit get() = litTime > 0
    var water: Int
        get() = tank.fluidAmount
        private set(value) { tank.fluid.amount = value }
    var energy: Int
        get() = energyStorage.energyStored
        private set(value) { energyStorage.energy = value }

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(4, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> ForgeHooks.getBurnTime(stack, null) > 0
        1 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        2 -> true
        3 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        else -> false
    }

    private val tank = object : NTechFluidTank(MAX_WATER, { it.fluid == Fluids.WATER }) {
        override fun drain(resource: FluidStack?, action: IFluidHandler.FluidAction?) = FluidStack.EMPTY
        override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction?) = FluidStack.EMPTY
    }
    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, 0, ENERGY_TRANSFER_RATE)

    override fun createMenu(windowID: Int, inventory: Inventory) = CombustionGeneratorMenu(windowID, inventory, this)
    override val defaultName: Component = LangKeys.CONTAINER_COMBUSTION_GENERATOR.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::litTime, this::litTime::set))
        menu.track(IntDataSlot.create(this::litDuration, this::litDuration::set))
        menu.track(FluidStackDataSlot.create(tank, isClientSide()))
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
    }

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent get() = throw IllegalStateException("No sound loop for combustion generator")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val wasLit = isLit
        var contentsChanged = false

        if (isLit)
            litTime--

        val fuel = mainInventory[0]
        if (!fuel.isEmpty && !isLit) {
            litTime = ForgeHooks.getBurnTime(fuel, null) / 2
            litDuration = litTime
            if (isLit) {
                contentsChanged = true
                if (fuel.hasContainerItem()) mainInventory[0] = fuel.containerItem
                else if (!fuel.isEmpty) {
                    fuel.shrink(1)
                    if (fuel.isEmpty) mainInventory[0] = fuel.containerItem
                }
            }
        }

        val fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[1], tank, this, tank.space, true)
        if (fluidTransferResult.success) mainInventory[1] = fluidTransferResult.result

        if (energy > 0) {
            val energyItemSlot = mainInventory[3]
            if (!energyItemSlot.isEmpty) transferEnergy(energyStorage, energyItemSlot)

            // TODO cache somehow TODO improve distribution
            val hungryConsumers = Direction.values().associateWith {
                val tileEntity = level.getBlockEntity(blockPos.relative(it)) ?: return@associateWith null
                if (tileEntity is CableBlockEntity) return@associateWith null
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

    override fun load(tag: CompoundTag) {
        super.load(tag)
        litTime = tag.getInt("BurnTime")
        litDuration = ForgeHooks.getBurnTime(mainInventory[0], RecipeType.SMELTING)
        tank.readFromNBT(tag.getCompound("WaterTank"))
        energy = tag.getInt("Energy")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("BurnTime", litTime)
        tag.put("WaterTank", tank.writeToNBT(CompoundTag()))
        tag.putInt("Energy", energy)
    }

    private val inputInventory = AccessLimitedInputItemHandler(this, 0)
    private val waterInputInventory = AccessLimitedInputItemHandler(this, 1)
    private val outputInventory = AccessLimitedOutputItemHandler(this, 2)

    init {
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, this::tank)
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::inputInventory, Direction.UP)
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::waterInputInventory, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::outputInventory, Direction.DOWN)
    }

    companion object {
        const val MAX_ENERGY = 400_000
        const val MAX_WATER = FluidAttributes.BUCKET_VOLUME * 10
        const val WATER_CONSUMPTION_RATE = 2
        const val ENERGY_PRODUCTION_RATE = 50
        const val ENERGY_TRANSFER_RATE = ENERGY_PRODUCTION_RATE * 10
    }
}
