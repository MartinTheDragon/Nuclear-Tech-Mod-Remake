package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.capability.item.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.capability.item.AccessLimitedOutputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.extensions.acceptFluids
import at.martinthedragon.nucleartech.extensions.subView
import at.martinthedragon.nucleartech.extensions.subViewWithFluids
import at.martinthedragon.nucleartech.fluid.*
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.insertAllItemsStacked
import at.martinthedragon.nucleartech.menu.ChemPlantMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.BooleanDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.FluidStackDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.recipe.ChemRecipe
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.CapabilityItemHandler

class ChemPlantBlockEntity(pos: BlockPos, state: BlockState) : RecipeMachineBlockEntity<ChemRecipe>(BlockEntityTypes.chemPlantBlockEntityType.get(), pos, state), ContainerFluidHandler {
    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(21, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
        0 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
        in 1..3 -> false // TODO
        4 -> stack.item is ChemPlantTemplateItem
        in 5..8 -> true
        9, 10 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        11, 12 -> true
        in 13..16 -> true
        17, 18 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent
        19, 20 -> true
        else -> false
    }

    override fun inventoryChanged(slot: Int) {
        super.inventoryChanged(slot)
        if (slot == 4) {
            checkCanProgress()
            setupTanks()
        }
    }

    private val energyStorage = EnergyStorageExposed(MAX_ENERGY)

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    val inputTank1 = NTechFluidTank(24_000)
    val inputTank2 = NTechFluidTank(24_000)
    val outputTank1 = NTechFluidTank(24_000)
    val outputTank2 = NTechFluidTank(24_000)

    private val tanks = arrayOf(inputTank1, inputTank2, outputTank1, outputTank2)

    override fun getTanks() = 4
    override fun getFluidInTank(tank: Int): FluidStack = if (tank > 3) FluidStack.EMPTY else tanks[tank].fluid
    override fun getTankCapacity(tank: Int) = if (tank > 3) 0 else tanks[tank].capacity
    override fun isFluidValid(tank: Int, stack: FluidStack) = true
    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (resource.isEmpty) return 0

        var filled = inputTank1.fill(resource, action)
        filled += inputTank2.fill(FluidStack(resource, resource.amount - filled), action)

        return filled
    }

    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        if (resource.isEmpty) return FluidStack.EMPTY

        var stack = FluidStack.EMPTY
        if (outputTank1.fluid.fluid == resource.fluid) {
            stack = outputTank1.drain(resource, action)
        }
        if (outputTank2.fluid.fluid == resource.fluid) {
            val output2 = outputTank2.drain(FluidStack(resource, resource.amount - stack.amount), action)
            if (stack.isEmpty) stack = output2
            else stack.grow(output2.amount)
        }
        return stack
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        var stack = outputTank1.drain(maxDrain, action)
        if (stack.isEmpty || outputTank2.fluid.fluid == stack.fluid) {
            val output2 = outputTank2.drain(maxDrain - stack.amount, action)
            if (stack.isEmpty) stack = output2
            else stack.grow(output2.amount)
        }
        return stack
    }

    private fun setupTanks() {
        val level = level ?: return
        val recipe = recipe ?: ChemPlantTemplateItem.getRecipeFromStack(mainInventory[4], level.recipeManager) ?: return
        if (inputTank1.isEmpty) inputTank1.fluid = FluidStack(recipe.inputFluid1.fluid, 0)
        if (inputTank2.isEmpty) inputTank2.fluid = FluidStack(recipe.inputFluid2.fluid, 0)
        if (outputTank1.isEmpty) outputTank1.fluid = FluidStack(recipe.outputFluid1.fluid, 0)
        if (outputTank2.isEmpty) outputTank2.fluid = FluidStack(recipe.outputFluid2.fluid, 0)
    }

    override fun createMenu(windowID: Int, inventory: Inventory) = ChemPlantMenu(windowID, inventory, this)
    override val defaultName = LangKeys.CONTAINER_CHEM_PLANT.get()

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy, this::energy::set))
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::maxProgress) { maxProgress = it })
        menu.track(BooleanDataSlot.create(this::canProgress) { canProgress = it })

        val isClient = isClientSide()
        menu.track(FluidStackDataSlot.create(inputTank1, isClient))
        menu.track(FluidStackDataSlot.create(inputTank2, isClient))
        menu.track(FluidStackDataSlot.create(outputTank1, isClient))
        menu.track(FluidStackDataSlot.create(outputTank2, isClient))
    }

    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 3, 3))

    var renderTick: Int = 0
        private set

    override val soundLoopEvent = SoundEvents.chemPlantOperate.get()

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        super.clientTick(level, pos, state)

        if (canProgress && !isRemoved) {
            renderTick++
            if (renderTick >= 360) renderTick = 0
        } else renderTick = 0
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        super.serverTick(level, pos, state)

        val energyItem = mainInventory[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)

        var fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[17], inputTank1, AccessLimitedInputItemHandler(this, 19), inputTank1.space, true)
        if (fluidTransferResult.success) mainInventory[17] = fluidTransferResult.result

        fluidTransferResult = tryEmptyFluidContainerAndMove(mainInventory[18], inputTank2, AccessLimitedInputItemHandler(this, 20), inputTank2.space, true)
        if (fluidTransferResult.success) mainInventory[18] = fluidTransferResult.result

        fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[9], outputTank1, AccessLimitedInputItemHandler(this, 11), outputTank1.fluidAmount, true)
        if (fluidTransferResult.success) mainInventory[9] = fluidTransferResult.result

        fluidTransferResult = tryFillFluidContainerAndMove(mainInventory[10], outputTank2, AccessLimitedInputItemHandler(this, 12), outputTank2.fluidAmount, true)
        if (fluidTransferResult.success) mainInventory[10] = fluidTransferResult.result

        sendContinuousUpdatePacket() // TODO make this happen only when fluids change and not all the time
    }

    override fun checkCanProgress() = energy >= consumption && super.checkCanProgress()

    override var maxProgress = 100
        private set

    private var consumption = 100
    private var speed = 100

    override fun tickProgress() {
        super.tickProgress()
        val recipe = recipe
        if (recipe != null)
            maxProgress = recipe.duration * speed / 100
        energy -= consumption
    }

    override fun findPossibleRecipe() = ChemPlantTemplateItem.getRecipeFromStack(mainInventory[4], getLevelUnchecked().recipeManager)

    // this method is quite computationally expensive
    override fun matchesRecipe(recipe: ChemRecipe) =
        recipe.matches(subViewWithFluids(13..16, 0..1), getLevelUnchecked())
            && insertAllItemsStacked(AccessLimitedInputItemHandler(this, 5..8), recipe.resultsList, true).isEmpty()
            && SimpleFluidHandler(outputTank1, outputTank2).acceptFluids(listOf(recipe.outputFluid1, recipe.outputFluid2), true).isEmpty()

    override fun processRecipe(recipe: ChemRecipe) {
        recipe.ingredientsList.containerSatisfiesRequirements(subView(13..16), true)
        val handler = SimpleFluidHandler(inputTank1, inputTank2)
        handler.drain(recipe.inputFluid1, IFluidHandler.FluidAction.EXECUTE)
        handler.drain(recipe.inputFluid2, IFluidHandler.FluidAction.EXECUTE)
        insertAllItemsStacked(AccessLimitedInputItemHandler(this, 5..8), recipe.resultsList, false)
        SimpleFluidHandler(outputTank1, outputTank2).acceptFluids(listOf(recipe.outputFluid1, recipe.outputFluid2), false)
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        put("Tank1", inputTank1.writeToNBT(CompoundTag()))
        put("Tank2", inputTank2.writeToNBT(CompoundTag()))
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        inputTank1.readFromNBT(tag.getCompound("Tank1"))
        inputTank2.readFromNBT(tag.getCompound("Tank2"))
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putInt("Energy", energy)
        tag.putInt("MaxProgress", maxProgress)

        val tanks = CompoundTag()
        tanks.put("InputTank1", inputTank1.writeToNBT(CompoundTag()))
        tanks.put("InputTank2", inputTank2.writeToNBT(CompoundTag()))
        tanks.put("OutputTank1", outputTank1.writeToNBT(CompoundTag()))
        tanks.put("OutputTank2", outputTank2.writeToNBT(CompoundTag()))
        tag.put("Tanks", tanks)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        energy = tag.getInt("Energy")
        maxProgress = tag.getInt("MaxProgress")

        val tanks = tag.getCompound("Tanks")
        inputTank1.readFromNBT(tanks.getCompound("InputTank1"))
        inputTank2.readFromNBT(tanks.getCompound("InputTank2"))
        outputTank1.readFromNBT(tanks.getCompound("OutputTank1"))
        outputTank2.readFromNBT(tanks.getCompound("OutputTank2"))
        setupTanks()
    }


    private val inputInventory = AccessLimitedInputItemHandler(this, 13..16)
    private val outputInventory = AccessLimitedOutputItemHandler(this, 5..8)

    init {
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::inputInventory, Direction.WEST)
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, this::outputInventory, Direction.EAST)
        registerCapabilityHandler(CapabilityEnergy.ENERGY, this::energyStorage)
        registerCapabilityHandler(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, { this })
    }

    companion object {
        const val MAX_ENERGY = 100_000
    }
}