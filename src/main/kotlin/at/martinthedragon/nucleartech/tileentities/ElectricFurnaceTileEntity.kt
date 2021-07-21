package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.containers.ElectricFurnaceContainer
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.items.canTransferItem
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.IRecipeHelperPopulator
import net.minecraft.inventory.IRecipeHolder
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.AbstractCookingRecipe
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.IRecipeType
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.nbt.CompoundNBT
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.LockableTileEntity
import net.minecraft.util.Direction
import net.minecraft.util.IIntArray
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class ElectricFurnaceTileEntity : LockableTileEntity(TileEntityTypes.electricFurnaceTileEntityType.get()),
    IRecipeHolder, IRecipeHelperPopulator, ExperienceRecipeResultTileEntity, ITickableTileEntity
{
    private var cookingProgress = 0
    private val isCooking: Boolean
        get() = cookingProgress > 0
    private var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    private val dataAccess = object : IIntArray {
        override fun get(index: Int): Int = when (index) {
            0 -> cookingProgress
            1 -> energy
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> cookingProgress = value
                1 -> energy = value
            }
        }

        override fun getCount() = 2
    }

    private val items = NonNullList.withSize(3, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean =
            if (slot == 1) stack.getCapability(CapabilityEnergy.ENERGY).isPresent
            else true
    }
    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, ENERGY_TRANSFER_RATE, 0)

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun load(state: BlockState, nbt: CompoundNBT) {
        super.load(state, nbt)
        items.clear()
        ItemStackHelper.loadAllItems(nbt, items)
        cookingProgress = nbt.getInt("CookingTime")
        energy = nbt.getInt("Energy")
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        super.save(nbt)
        ItemStackHelper.saveAllItems(nbt, items)
        nbt.putInt("CookingTime", cookingProgress)
        nbt.putInt("Energy", energy)
        return nbt
    }

    override fun clearContent() {
        items.clear()
    }

    override fun getContainerSize() = 3

    override fun isEmpty() = items.all { it.isEmpty } && energyStorage.energyStored == 0

    override fun getItem(slot: Int): ItemStack = items[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack = ItemStackHelper.removeItem(items, slot, amount)

    override fun removeItemNoUpdate(slot: Int): ItemStack = ItemStackHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        items[slot] = itemStack
        if (itemStack.count > maxStackSize)
            itemStack.count = maxStackSize
    }

    override fun stillValid(player: PlayerEntity): Boolean =
        if (level!!.getBlockEntity(worldPosition) != this) false
        else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory) =
        ElectricFurnaceContainer(windowId, playerInventory, this, dataAccess)

    override fun getDefaultName(): ITextComponent = TranslationTextComponent("container.${NuclearTech.MODID}.electric_furnace")

    override fun tick() {
        if (level!!.isClientSide) return

        val wasCooking = isCooking
        var contentChanged = false

        if (isCooking) energy = (energy - ENERGY_CONSUMPTION_RATE).coerceAtLeast(0)

        val energyItemSlot = items[1]
        if (!energyItemSlot.isEmpty) transferEnergy(energyItemSlot, energyStorage)

        val recipe: IRecipe<*>? = level!!.recipeManager.getRecipeFor(IRecipeType.SMELTING, this, level!!).orElse(null)

        if (canCook(recipe)) {
            if (cookingProgress >= COOKING_TIME) {
                cook(recipe)
                cookingProgress = 0
                contentChanged = true
            }

            // to slow down and prevent lag when not enough energy gets supplied, we check with a threshold
            if (!isCooking && energy >= MIN_ENERGY_THRESHOLD || isCooking && energy > 0)
                cookingProgress++
        }

        if (!canCook(recipe) || isCooking && energy <= 0) cookingProgress = (cookingProgress - 2).coerceAtLeast(0)

        if (wasCooking != isCooking) {
            contentChanged = true
            level!!.setBlockAndUpdate(worldPosition, level!!.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, isCooking))
        }

        if (contentChanged) setChanged()
    }

    @OptIn(ExperimentalContracts::class)
    private fun canCook(recipe: IRecipe<*>?): Boolean {
        contract {
            returns(true) implies (recipe != null)
        }

        return if (items[0].isEmpty || recipe == null) false
        else canTransferItem(recipe.resultItem, items[2], this)
    }

    @Suppress("UsePropertyAccessSyntax")
    private fun cook(recipe: IRecipe<*>) {
        val result = recipe.resultItem
        val resultsStack = items[2]
        if (resultsStack.isEmpty) items[2] = result.copy()
        else resultsStack.grow(result.count)
        setRecipeUsed(recipe)
        items[0].shrink(1)
    }

    override fun fillStackedContents(recipeItemHelper: RecipeItemHelper) {
        for (itemStack in items) recipeItemHelper.accountStack(itemStack)
    }

    override fun getRecipeUsed(): IRecipe<*>? = null

    override fun setRecipeUsed(recipe: IRecipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun awardUsedRecipes(player: PlayerEntity) {}

    override fun clearUsedRecipes() = recipesUsed.clear()

    override fun getExperienceToDrop(player: PlayerEntity?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? AbstractCookingRecipe)?.experience?.times(amount)
        }.sum() / 2F

    override fun getRecipesToAward(player: PlayerEntity): List<IRecipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

    private val inventoryCapability = LazyOptional.of(this::inventory)
    private val energyCapability = LazyOptional.of(this::energyStorage)

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove) when (cap) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> return inventoryCapability.cast()
            CapabilityEnergy.ENERGY -> return energyCapability.cast()
        }

        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability.invalidate()
        energyCapability.invalidate()
    }

    companion object {
        const val MAX_ENERGY = 400_000
        const val COOKING_TIME = 100
        const val ENERGY_CONSUMPTION_RATE = 50
        const val MIN_ENERGY_THRESHOLD = COOKING_TIME * ENERGY_CONSUMPTION_RATE / 10
        const val ENERGY_TRANSFER_RATE = MAX_ENERGY / 100
    }
}
