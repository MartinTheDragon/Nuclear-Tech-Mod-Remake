package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.blocks.AssemblerBlock
import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPart
import at.martinthedragon.nucleartech.blocks.multi.RotatedMultiBlockPlacer
import at.martinthedragon.nucleartech.capabilites.items.AccessLimitedInputItemHandler
import at.martinthedragon.nucleartech.capabilites.items.AccessLimitedOutputItemHandler
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.items.AssemblyTemplateItem
import at.martinthedragon.nucleartech.items.canTransferItem
import at.martinthedragon.nucleartech.items.transferItemsBetweenItemHandlers
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.menus.AssemblerMenu
import at.martinthedragon.nucleartech.networking.AssemblerSyncMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.recipes.AssemblyRecipe
import at.martinthedragon.nucleartech.recipes.containerSatisfiesRequirements
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.ContainerHelper
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.StackedContentsCompatible
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.network.PacketDistributor

class AssemblerBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.assemblerBlockEntityType.get(), pos, state),
    TickingServerBlockEntity, TickingClientBlockEntity, StackedContentsCompatible
{
    private val relativeRotation by lazy { RotatedMultiBlockPlacer.invert(RotatedMultiBlockPlacer.getRotationFor(level!!.getBlockState(blockPos).getValue(HorizontalDirectionalBlock.FACING))) }

    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }
    var progress = 0
    var maxProgress = 100
    val isProgressing: Boolean get() = progress > 0
    private var canProgress = false

    var recipeID: ResourceLocation? = null

    private var consumption = 100
    private var speed = 100

    private val dataAccess = object : ContainerData {
        override fun get(index: Int): Int = when (index) {
            0 -> energy
            1 -> progress
            2 -> maxProgress
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> energy = value
                1 -> progress = value
                2 -> maxProgress = value
            }
        }

        override fun getCount() = 3
    }

    private val items = NonNullList.withSize(18, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
            0 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
            in 1..3 -> false // TODO
            4 -> stack.item is AssemblyTemplateItem
            else -> true
        }
    }
    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, 1000, 0)

    override fun load(tag: CompoundTag) {
        super.load(tag)
        items.clear()
        ContainerHelper.loadAllItems(tag, items)
        energy = tag.getInt("Energy")
        progress = tag.getInt("Progress")
        maxProgress = tag.getInt("MaxProgress")
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        ContainerHelper.saveAllItems(tag, items)
        tag.putInt("Energy", energy)
        tag.putInt("Progress", progress)
        tag.putInt("MaxProgress", maxProgress)
    }

    override fun getUpdateTag() = CompoundTag().apply {
        putInt("Progress", progress)
        recipeID?.let { putString("Recipe", it.toString()) }
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        progress = tag.getInt("Progress")
        recipeID = if (tag.contains("Recipe", 8)) ResourceLocation(tag.getString("Recipe")) else null
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
    override fun createMenu(windowID: Int, playerInventory: Inventory) = AssemblerMenu(windowID, playerInventory, this, dataAccess)
    override fun getDefaultName() = TranslatableComponent("container.${NuclearTech.MODID}.assembler")
    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 2, 3))

    override fun fillStackedContents(stacks: StackedContents) {
        for (itemStack in items) stacks.accountStack(itemStack)
    }

    var renderTick: Int = 0
        private set

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        if (isProgressing) {
            renderTick++
            if (renderTick >= 1800) renderTick = 0
        } else renderTick = 0
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val wasAssembling = isProgressing || canProgress
        val previousRecipe = recipeID

        val energyItem = items[0]
        if (!energyItem.isEmpty) transferEnergy(energyItem, energyStorage)

        val id = AssemblyTemplateItem.getRecipeIDFromStack(items[4])
        recipeID = id
        val recipe = id?.let { level.recipeManager.byKey(it).orElse(null) as? AssemblyRecipe }
        if (recipe != null) {
            maxProgress = recipe.duration * speed / 100
            canProgress = energy >= consumption && canAssemble(recipe)
            if (canProgress) {
                progress++
                if (progress >= maxProgress) {
                    progress = 0
                    assemble(recipe)
                }
                energy -= consumption
            } else progress = 0
        } else {
            progress = 0
            canProgress = false
        }

        if (wasAssembling != (isProgressing || canProgress) || previousRecipe != recipeID) setChanged(true)
    }

    private fun canAssemble(recipe: AssemblyRecipe) = recipe.matches(SimpleContainer(*items.subList(5, 17).toTypedArray()), level!!) && canTransferItem(recipe.resultItem, items[17], this)

    private fun assemble(recipe: AssemblyRecipe) {
        recipe.ingredientsList.containerSatisfiesRequirements(SimpleContainer(*items.subList(5, 17).toTypedArray()), true)
        val recipeResult = recipe.resultItem
        val resultStack = items[17]
        if (resultStack.isEmpty) items[17] = recipeResult.copy()
        else if (resultStack.sameItem(recipeResult)) resultStack.grow(recipeResult.count)
        // else ???
    }

    fun setChanged(sync: Boolean = false) {
        super.setChanged()
        if (sync && level != null && !level!!.isClientSide) {
            val message = AssemblerSyncMessage(blockPos, recipeID, isProgressing || canProgress)
            NuclearPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with { level!!.getChunkAt(blockPos) }, message)
        }
    }

    private val inputInventory = AccessLimitedInputItemHandler(inventory, 5..16)
    private val outputInventory = AccessLimitedOutputItemHandler(inventory, 17)

    private val inventoryCapability = LazyOptional.of(this::inventory)
    private val inventoryInputCapability = LazyOptional.of(this::inputInventory)
    private val inventoryOutputCapability = LazyOptional.of(this::outputInventory)
    private val energyCapability = LazyOptional.of(this::energyStorage)

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && side != Direction.DOWN) when (cap) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> {
                if (side == null) return inventoryCapability.cast()
                when (relativeRotation.rotate(side)) {
                    in inventoryInputPorts.values.flatMap(Set<Direction>::asIterable) -> return inventoryInputCapability.cast()
                    in inventoryOutputPorts.values.flatMap(Set<Direction>::asIterable) -> return inventoryOutputCapability.cast()
                    else -> {}
                }
            }
            CapabilityEnergy.ENERGY -> return energyCapability.cast()
        }
        return super.getCapability(cap)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability.invalidate()
        energyCapability.invalidate()
    }

    class AssemblerPartBlockEntity(pos: BlockPos, state: BlockState) : MultiBlockPart.MultiBlockPartBlockEntity(BlockEntityTypes.assemblerPartBlockEntityType.get(), pos, state), TickingServerBlockEntity {
        private val relativeRotation by lazy { RotatedMultiBlockPlacer.invert(RotatedMultiBlockPlacer.getRotationFor(level!!.getBlockState(core).getValue(HorizontalDirectionalBlock.FACING))) }
        private val relativePos by lazy { blockPos.subtract(core).rotate(relativeRotation) }

        // only has a ticker if it's in port position
        override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
            val mode = state.getValue(AssemblerBlock.AssemblerPart.PORT_MODE)
            if (mode.isNone()) return
            val portMap = if (mode.isInput()) inventoryInputPorts else inventoryOutputPorts
            val otherBlockEntityPos = pos.relative(RotatedMultiBlockPlacer.invert(relativeRotation).rotate(portMap.getValue(relativePos).first()))
            val direction = Direction.fromNormal(otherBlockEntityPos.subtract(pos))
            level.getBlockEntity(core)?.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction)?.ifPresent { assembler ->
                val otherBlockEntity = level.getBlockEntity(otherBlockEntityPos) ?: return@ifPresent
                otherBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).ifPresent { other ->
                    transferItemsBetweenItemHandlers(other, assembler, 64, mode.isOutput())
                }
            }
        }

        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            if (hasLevel() && side != null) {
                val coreEntity = level!!.getBlockEntity(core)
                if (coreEntity != null) {
                    val relativeDirection = relativeRotation.rotate(side)
                    if (cap == CapabilityEnergy.ENERGY && relativePos in energyPorts.keys) {
                        if (relativeDirection in energyPorts.getValue(relativePos)) return coreEntity.getCapability(cap, side).cast()
                    } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                        if (relativePos in inventoryInputPorts.keys && relativeDirection in inventoryInputPorts.getValue(relativePos)) return coreEntity.getCapability(cap, side).cast()
                        if (relativePos in inventoryOutputPorts.keys && relativeDirection in inventoryOutputPorts.getValue(relativePos)) return coreEntity.getCapability(cap, side).cast()
                    }
                }
            }

            return super.getCapability(cap, side)
        }
    }

    companion object {
        const val MAX_ENERGY = 100_000

        val energyPorts = mapOf(
            BlockPos(0, 0, 1) to setOf(Direction.SOUTH),
            BlockPos(-1, 0, 1) to setOf(Direction.SOUTH),
            BlockPos(0, 0, -2) to setOf(Direction.NORTH),
            BlockPos(-1, 0, -2) to setOf(Direction.NORTH)
        )

        val inventoryInputPorts = mapOf(
            BlockPos(1, 0, -1) to setOf(Direction.EAST)
        )

        val inventoryOutputPorts = mapOf(
            BlockPos(-2, 0, 0) to setOf(Direction.WEST)
        )
    }
}
