package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.containers.PressContainer
import at.martinthedragon.nucleartech.recipes.PressRecipe
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.block.BlockState
import net.minecraft.entity.item.ExperienceOrbEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.IRecipeHelperPopulator
import net.minecraft.inventory.IRecipeHolder
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.RecipeItemHelper
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tags.ItemTags
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.LockableTileEntity
import net.minecraft.util.*
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import kotlin.math.floor
import kotlin.random.Random

class SteamPressTopTileEntity : LockableTileEntity(TileEntityTypes.steamPressHeadTileEntityType.get()), IInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
    private var litDuration = 0
    private var litTime = 0
    val isLit: Boolean
        get() = litTime > 0
    var power = 0
        private set
    var pressProgress = 0
        private set

    private val dataAccess = object : IIntArray {
        override fun get(index: Int): Int = when (index) {
            0 -> litTime
            1 -> litDuration
            2 -> pressProgress
            3 -> power
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> litTime = value
                1 -> litDuration = value
                2 -> pressProgress = value
                3 -> power = value
            }
        }

        override fun getCount() = 4
    }

    private val items = NonNullList.withSize(4, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
            if (slot == 1) return stack.item in ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.STAMPS.name)
            if (slot == 2) return ForgeHooks.getBurnTime(stack) > 0
            return true
        }
    }

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun load(blockState: BlockState, nbt: CompoundNBT) {
        super.load(blockState, nbt)
        items.clear()
        ItemStackHelper.loadAllItems(nbt, items)
        litTime = nbt.getInt("BurnTime")
        val fuel = items[2]
        litDuration = if (fuel.isEmpty) 0 else ForgeHooks.getBurnTime(fuel)
        pressProgress = nbt.getInt("PressTime")
        power = nbt.getInt("Power")
        val recipesNbt = nbt.getCompound("RecipesUsed")
        for (string in recipesNbt.allKeys)
            recipesUsed[ResourceLocation(string)] = recipesNbt.getInt(string)
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        super.save(nbt)
        ItemStackHelper.saveAllItems(nbt, items)
        nbt.putInt("BurnTime", litTime)
        nbt.putInt("PressTime", pressProgress)
        nbt.putInt("Power", power)
        val recipesNbt = CompoundNBT()
        for ((recipeID, amount) in recipesUsed)
            recipesNbt.putInt(recipeID.toString(), amount)
        nbt.put("RecipesUsed", recipesNbt)
        return nbt
    }

    override fun clearContent() {
        items.clear()
    }

    override fun getContainerSize() = items.size

    override fun isEmpty() = items.all { it.isEmpty }

    override fun getItem(slot: Int): ItemStack = items[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack = ItemStackHelper.removeItem(items, slot, amount)

    override fun removeItemNoUpdate(slot: Int): ItemStack = ItemStackHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        val item = items[slot]
        val itemEqualToPrevious = !itemStack.isEmpty && itemStack.sameItem(item) && ItemStack.tagMatches(itemStack, item)
        items[slot] = itemStack
        if (itemStack.count > maxStackSize)
            itemStack.count = maxStackSize

        if (slot == 0 && !itemEqualToPrevious) {
            pressProgress = 0
            setChanged()
        }
    }

    override fun stillValid(player: PlayerEntity): Boolean {
        return if (level!!.getBlockEntity(worldPosition) != this) false
        else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64
    }

    override fun createMenu(windowId: Int, playerInventory: PlayerInventory): Container =
        PressContainer(windowId, playerInventory, this, dataAccess)

    override fun getDefaultName(): ITextComponent = TranslationTextComponent("container.${NuclearTech.MODID}.steam_press")

    override fun setRecipeUsed(recipe: IRecipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun getRecipeUsed(): IRecipe<*>? = null

    override fun awardUsedRecipes(player: PlayerEntity) {}

    fun awardUsedRecipesAndPopExperience(player: PlayerEntity) {
        val recipeList = getRecipesToAwardAndPopExperience(player.level, player.position())
        player.awardRecipes(recipeList)
        recipesUsed.clear()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getRecipesToAwardAndPopExperience(world: World, pos: Vector3d): List<IRecipe<*>> {
        return buildList<IRecipe<*>> {
            for ((recipeID, amount) in recipesUsed.object2IntEntrySet()) {
                world.recipeManager.byKey(recipeID).ifPresent {
                    add(it)
                    createExperience(world, pos, amount, (it as PressRecipe).experience)
                }
            }
        }
    }

    private fun createExperience(world: World, pos: Vector3d, times: Int, xpAmount: Float) {
        var i = floor(times * xpAmount).toInt()
        val f = (times * xpAmount) % 1
        if (f != 0F && Random.nextFloat() < f) i++

        while (i > 0) {
            val j = ExperienceOrbEntity.getExperienceValue(i)
            i -= j
            world.addFreshEntity(ExperienceOrbEntity(world, pos.x, pos.y, pos.z, j))
        }
    }

    override fun fillStackedContents(recipeItemHelper: RecipeItemHelper) {
        for (itemStack in items) recipeItemHelper.accountStack(itemStack)
    }

    private fun canPress(recipe: IRecipe<*>?): Boolean {
        if (items[0].isEmpty || recipe == null) return false
        val recipeResult = recipe.resultItem
        if (recipeResult.isEmpty) return false

        val resultStack = items[3]
        return when {
            resultStack.isEmpty -> true
            !resultStack.sameItem(recipeResult) -> false
            resultStack.count + recipeResult.count <= maxStackSize && resultStack.count + recipeResult.count <= resultStack.maxStackSize -> true
            else -> resultStack.count + recipeResult.count <= recipeResult.maxStackSize
        }
    }

    private fun getBurnDuration(fuel: ItemStack): Int = if (fuel.isEmpty) 0 else ForgeHooks.getBurnTime(fuel)

    @Suppress("UsePropertyAccessSyntax")
    private fun press(recipe: IRecipe<*>?): Boolean {
        if (recipe == null || !canPress(recipe)) return false
        val recipeResult = recipe.resultItem
        val resultStack = items[3]
        if (resultStack.isEmpty) items[3] = recipeResult.copy()
        else if (resultStack.sameItem(recipeResult)) resultStack.grow(recipeResult.count)
        if (!level!!.isClientSide) setRecipeUsed(recipe)
        items[0].shrink(1)
        val stamp = items[1]
        if (stamp.hurt(1, level!!.random, null)) {
            if (stamp.damageValue >= stamp.maxDamage)
                stamp.shrink(1)
        }
        level!!.playSound(null, blockPos, SoundEvents.pressOperate, SoundCategory.BLOCKS, 1.5F, 1F)
        return true
    }

    override fun tick() {
        if (isLit)
            litTime--

        if (level!!.isClientSide) return

        val wasLit = isLit
        var contentsChanged = false

        val fuel = items[2]
        val recipe = level!!.recipeManager.getRecipeFor(RecipeTypes.PRESSING, this, level!!).orElse(null)

        if (!fuel.isEmpty && !isLit && canPress(recipe)) {
            litTime = getBurnDuration(fuel) / 8
            litDuration = litTime
            if (isLit) {
                contentsChanged = true
                if (fuel.hasContainerItem()) items[2] = fuel.containerItem
                else if (!fuel.isEmpty) {
                    fuel.shrink(1)
                    if (fuel.isEmpty) items[2] = fuel.containerItem
                }
            }
        }
        if (isLit && power < maxPower) power++ else if (!isLit && power > 0) power--
        if (power >= powerNeeded && pressProgress >= pressTotalTime) if (press(recipe)) {
            pressProgress = 0
            contentsChanged = true
        }
        if (power >= powerNeeded && canPress(recipe)) pressProgress += power * 25 / maxPower else pressProgress = 0

        if (wasLit != isLit) contentsChanged = true
        if (contentsChanged) setChanged()
    }

    private var inventoryCapability: LazyOptional<IItemHandler>? = null

    private fun createHandler(): IItemHandler = inventory

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (inventoryCapability == null)
                inventoryCapability = LazyOptional.of(this::createHandler)
            return inventoryCapability!!.cast()
        }
        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        inventoryCapability?.invalidate()
    }

    companion object {
        const val maxPower = 700
        const val pressTotalTime = 200
        val powerNeeded: Int
            get() = maxPower / 3
    }
}
