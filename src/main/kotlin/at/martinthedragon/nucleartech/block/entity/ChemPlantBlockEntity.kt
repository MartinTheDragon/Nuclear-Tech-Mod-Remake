package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingBlockEntity
import at.martinthedragon.nucleartech.block.multi.RotatedMultiBlockPlacer
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
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.menu.ChemPlantMenu
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.slots.data.BooleanDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.FluidStackDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.IntDataSlot
import at.martinthedragon.nucleartech.networking.BlockEntityUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.recipe.ChemRecipe
import at.martinthedragon.nucleartech.recipe.containerSatisfiesRequirements
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.Connection
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.StackedContentsCompatible
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.network.PacketDistributor
import kotlin.jvm.optionals.getOrNull

class ChemPlantBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.chemPlantBlockEntityType.get(), pos, state),
    TickingBlockEntity, StackedContentsCompatible, SoundLoopBlockEntity, ContainerFluidHandler, ContainerSyncableBlockEntity
{
    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }
    var progress = 0
    var maxProgress = 100
    val isProgressing: Boolean get() = progress > 0
    var canProgress = false
        private set

    var recipeID: ResourceLocation? = null

    private var consumption = 100
    private var speed = 100

    private val items = NonNullList.withSize(21, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
            if (slot == 4) setupTanks()
        }

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
    }
    private val energyStorage = EnergyStorageExposed(MAX_ENERGY)

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
        if (outputTank2.fluid.fluid == stack.fluid) {
            val output2 = outputTank2.drain(maxDrain - stack.amount, action)
            if (stack.isEmpty) stack = output2
            else stack.grow(output2.amount)
        }
        return stack
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun setupTanks() {
        val level = level ?: return
        val recipeID = recipeID ?: ChemPlantTemplateItem.getRecipeIDFromStack(items[4]) ?: return
        val recipe = level.recipeManager.byKey(recipeID).getOrNull() as? ChemRecipe ?: return
        if (inputTank1.isEmpty) inputTank1.fluid = FluidStack(recipe.inputFluid1.fluid, 0)
        if (inputTank2.isEmpty) inputTank2.fluid = FluidStack(recipe.inputFluid2.fluid, 0)
        if (outputTank1.isEmpty) outputTank1.fluid = FluidStack(recipe.outputFluid1.fluid, 0)
        if (outputTank2.isEmpty) outputTank2.fluid = FluidStack(recipe.outputFluid2.fluid, 0)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        items.clear()
        ContainerHelper.loadAllItems(tag, items)
        energy = tag.getInt("Energy")
        progress = tag.getInt("Progress")
        maxProgress = tag.getInt("MaxProgress")

        val tanks = tag.getCompound("Tanks")
        inputTank1.readFromNBT(tanks.getCompound("InputTank1"))
        inputTank2.readFromNBT(tanks.getCompound("InputTank2"))
        outputTank1.readFromNBT(tanks.getCompound("OutputTank1"))
        outputTank2.readFromNBT(tanks.getCompound("OutputTank2"))

        setupTanks()
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        ContainerHelper.saveAllItems(tag, items)
        tag.putInt("Energy", energy)
        tag.putInt("Progress", progress)
        tag.putInt("MaxProgress", maxProgress)

        val tanks = CompoundTag()
        tanks.put("InputTank1", inputTank1.writeToNBT(CompoundTag()))
        tanks.put("InputTank2", inputTank2.writeToNBT(CompoundTag()))
        tanks.put("OutputTank1", outputTank1.writeToNBT(CompoundTag()))
        tanks.put("OutputTank2", outputTank2.writeToNBT(CompoundTag()))
        tag.put("Tanks", tanks)
    }

    override fun getUpdateTag() = CompoundTag().apply {
        putInt("Progress", progress)
        putBoolean("CanProgress", canProgress)
        recipeID?.let { putString("Recipe", it.toString()) }
        put("Tank1", inputTank1.writeToNBT(CompoundTag()))
        put("Tank2", inputTank2.writeToNBT(CompoundTag()))
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        progress = tag.getInt("Progress")
        canProgress = tag.getBoolean("CanProgress")
        recipeID = if (tag.contains("Recipe", 8)) ResourceLocation(tag.getString("Recipe")) else null
        inputTank1.readFromNBT(tag.getCompound("Tank1"))
        inputTank2.readFromNBT(tag.getCompound("Tank2"))
        setupTanks()
    }

    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket = ClientboundBlockEntityDataPacket.create(this)

    override fun onDataPacket(net: Connection, pkt: ClientboundBlockEntityDataPacket) {
        if (level!!.isClientSide && net.direction == PacketFlow.CLIENTBOUND) {
            pkt.tag?.let { handleUpdateTag(it) }
        }
    }

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(IntDataSlot.create(this::energy) { energy = it })
        menu.track(IntDataSlot.create(this::progress) { progress = it })
        menu.track(IntDataSlot.create(this::maxProgress) { maxProgress = it })
        menu.track(BooleanDataSlot.create(this::canProgress) { canProgress = it })

        val isClient = level!!.isClientSide
        menu.track(FluidStackDataSlot.create(inputTank1, isClient))
        menu.track(FluidStackDataSlot.create(inputTank2, isClient))
        menu.track(FluidStackDataSlot.create(outputTank1, isClient))
        menu.track(FluidStackDataSlot.create(outputTank2, isClient))
    }

    override fun clearContent() { items.clear() }
    override fun getContainerSize() = items.size
    override fun isEmpty() = items.all { it.isEmpty } && energy == 0
    override fun getItem(slot: Int): ItemStack = items[slot]
    override fun removeItem(slot: Int, amount: Int): ItemStack = ContainerHelper.removeItem(items, slot, amount)
    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        items[slot] = itemStack
        itemStack.count = itemStack.count.coerceAtMost(maxStackSize)
        setChanged()
    }

    override fun stillValid(player: Player): Boolean = if (level!!.getBlockEntity(worldPosition) != this) false else player.distanceToSqr(worldPosition.toVec3Middle()) <= 64
    override fun createMenu(windowID: Int, playerInventory: Inventory) = ChemPlantMenu(windowID, playerInventory, this)
    override fun getDefaultName() = TranslatableComponent("container.${NuclearTech.MODID}.chem_plant")
    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 3, 3))

    override fun fillStackedContents(stacks: StackedContents) {
        for (itemStack in items) stacks.accountStack(itemStack)
    }

    var renderTick: Int = 0
        private set

    override val soundPos: BlockPos get() = blockPos
    override val shouldPlaySoundLoop get() = (isProgressing || canProgress) && !isRemoved
    override val soundLoopEvent = SoundEvents.chemPlantOperate.get()
    override val soundLoopStateMachine = SoundLoopBlockEntity.SoundStateMachine(this)

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        super.clientTick(level, pos, state)

        if ((isProgressing || canProgress) && !isRemoved) {
            renderTick++
            if (renderTick >= 360) renderTick = 0
        } else renderTick = 0
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val wasProcessing = isProgressing || canProgress
        val previousRecipe = recipeID

        val energyItem = items[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)

        var fluidTransferResult = tryEmptyFluidContainerAndMove(items[17], inputTank1, AccessLimitedInputItemHandler(inventory, 19), inputTank1.space, true)
        if (fluidTransferResult.success) items[17] = fluidTransferResult.result

        fluidTransferResult = tryEmptyFluidContainerAndMove(items[18], inputTank2, AccessLimitedInputItemHandler(inventory, 20), inputTank2.space, true)
        if (fluidTransferResult.success) items[18] = fluidTransferResult.result

        fluidTransferResult = tryFillFluidContainerAndMove(items[9], outputTank1, AccessLimitedInputItemHandler(inventory, 11), outputTank1.fluidAmount, true)
        if (fluidTransferResult.success) items[9] = fluidTransferResult.result

        fluidTransferResult = tryFillFluidContainerAndMove(items[10], outputTank2, AccessLimitedInputItemHandler(inventory, 12), outputTank2.fluidAmount, true)
        if (fluidTransferResult.success) items[10] = fluidTransferResult.result

        val id = ChemPlantTemplateItem.getRecipeIDFromStack(items[4])
        recipeID = id
        val recipe = ChemPlantTemplateItem.getRecipeFromStack(items[4], level.recipeManager)
        if (recipe != null) {
            maxProgress = recipe.duration * speed / 100
            canProgress = energy >= consumption && canProcess(recipe)
            if (canProgress) {
                progress++
                if (progress >= maxProgress) {
                    process(recipe)
                    syncToClient()
                    progress = 0
                }
                energy -= consumption
            } else progress = 0
        } else {
            progress = 0
            canProgress = false
        }

        if (wasProcessing != (isProgressing || canProgress) || previousRecipe != recipeID) {
            setChanged()
            syncToClient()
        }
    }

    // this method is quite computationally expensive
    private fun canProcess(recipe: ChemRecipe) =
        recipe.matches(subViewWithFluids(13..16, 0..1), level!!)
            && insertAllItemsStacked(AccessLimitedInputItemHandler(inventory, 5..8), recipe.resultsList, true).isEmpty()
            && SimpleFluidHandler(outputTank1, outputTank2).acceptFluids(listOf(recipe.outputFluid1, recipe.outputFluid2), true).isEmpty()

    private fun process(recipe: ChemRecipe) {
        recipe.ingredientsList.containerSatisfiesRequirements(subView(13..16), true)
        val handler = SimpleFluidHandler(inputTank1, inputTank2)
        handler.drain(recipe.inputFluid1, IFluidHandler.FluidAction.EXECUTE)
        handler.drain(recipe.inputFluid2, IFluidHandler.FluidAction.EXECUTE)
        insertAllItemsStacked(AccessLimitedInputItemHandler(inventory, 5..8), recipe.resultsList, false)
        SimpleFluidHandler(outputTank1, outputTank2).acceptFluids(listOf(recipe.outputFluid1, recipe.outputFluid2), false)
    }

    private fun syncToClient() {
        NuclearPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with { level!!.getChunkAt(blockPos) }, BlockEntityUpdateMessage(blockPos, updateTag))
    }

    override fun setRemoved() {
        super.setRemoved()
        soundLoopStateMachine.tick()
    }

    private val relativeRotation by lazy { RotatedMultiBlockPlacer.invert(RotatedMultiBlockPlacer.getRotationFor(level!!.getBlockState(blockPos).getValue(BlockStateProperties.HORIZONTAL_FACING))) }

    private val inputInventory = AccessLimitedInputItemHandler(inventory, 13..16)
    private val outputInventory = AccessLimitedOutputItemHandler(inventory, 5..8)

    private val inventoryCapability = LazyOptional.of(this::inventory)
    private val inventoryInputCapability = LazyOptional.of(this::inputInventory)
    private val inventoryOutputCapability = LazyOptional.of(this::outputInventory)
    private val energyCapability = LazyOptional.of(this::energyStorage)
    private val fluidCapability: LazyOptional<IFluidHandler> = LazyOptional.of { this }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && side != Direction.DOWN) when (cap) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> {
                return if (side == null) inventoryCapability.cast()
                else when (relativeRotation.rotate(side)) {
                    Direction.WEST -> inventoryInputCapability.cast()
                    Direction.EAST -> inventoryOutputCapability.cast()
                    else -> LazyOptional.empty()
                }
            }
            CapabilityEnergy.ENERGY -> return energyCapability.cast()
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> return fluidCapability.cast()
        }
        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability.invalidate()
        inventoryInputCapability.invalidate()
        inventoryOutputCapability.invalidate()
        energyCapability.invalidate()
        fluidCapability.invalidate()
    }

    companion object {
        const val MAX_ENERGY = 100_000
    }
}
