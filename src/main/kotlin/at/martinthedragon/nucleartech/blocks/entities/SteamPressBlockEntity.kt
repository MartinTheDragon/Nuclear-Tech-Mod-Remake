package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.api.blocks.entities.ExperienceRecipeResultBlockEntity
import at.martinthedragon.nucleartech.api.blocks.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.items.canTransferItem
import at.martinthedragon.nucleartech.menus.PressMenu
import at.martinthedragon.nucleartech.networking.BlockEntityUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.recipes.PressingRecipe
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.RecipeHolder
import net.minecraft.world.inventory.StackedContentsCompatible
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.network.PacketDistributor

class SteamPressBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.steamPressHeadBlockEntityType.get(), pos, state),
    TickingServerBlockEntity, RecipeHolder, StackedContentsCompatible, ExperienceRecipeResultBlockEntity
{
    private var litDuration = 0
    private var litTime = 0
    private val isLit: Boolean
        get() = litTime > 0
    var power = 0
        private set
    var pressProgress = 0
        private set

    var pressedItem: ItemStack = ItemStack.EMPTY
        private set

    private val dataAccess = object : ContainerData {
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
            if (slot == 1) return stack.`is`(NuclearTags.Items.STAMPS)
            if (slot == 2) return ForgeHooks.getBurnTime(stack, null) > 0
            return true
        }
    }

    private val recipesUsed = Object2IntOpenHashMap<ResourceLocation>()

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        items.clear()
        ContainerHelper.loadAllItems(nbt, items)
        litTime = nbt.getInt("BurnTime")
        val fuel = items[2]
        litDuration = if (fuel.isEmpty) 0 else ForgeHooks.getBurnTime(fuel, null)
        pressProgress = nbt.getInt("PressTime")
        power = nbt.getInt("Power")
        isRetracting = nbt.getBoolean("IsRetracting")
        val recipesNbt = nbt.getCompound("RecipesUsed")
        for (string in recipesNbt.allKeys)
            recipesUsed[ResourceLocation(string)] = recipesNbt.getInt(string)
    }

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        ContainerHelper.saveAllItems(nbt, items)
        nbt.putInt("BurnTime", litTime)
        nbt.putInt("PressTime", pressProgress)
        nbt.putInt("Power", power)
        nbt.putBoolean("IsRetracting", isRetracting)
        val recipesNbt = CompoundTag()
        for ((recipeID, amount) in recipesUsed)
            recipesNbt.putInt(recipeID.toString(), amount)
        nbt.put("RecipesUsed", recipesNbt)
    }

    override fun getUpdateTag() = CompoundTag().apply {
        putInt("Progress", pressProgress)
        val item = CompoundTag()
        items[0].save(item)
        put("PressedItem", item)
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        pressProgress = tag.getInt("Progress")
        if (tag.contains("PressedItem", Tag.TAG_COMPOUND.toInt()))
            pressedItem = ItemStack.of(tag.getCompound("PressedItem"))
    }

    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket = ClientboundBlockEntityDataPacket.create(this)

    override fun clearContent() {
        items.clear()
    }

    override fun getContainerSize() = items.size

    override fun isEmpty() = items.all { it.isEmpty }

    override fun getItem(slot: Int): ItemStack = items[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack = ContainerHelper.removeItem(items, slot, amount)

    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)

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

    override fun stillValid(player: Player): Boolean {
        return if (level!!.getBlockEntity(worldPosition) != this) false
        else player.distanceToSqr(worldPosition.x + .5, worldPosition.y + .5, worldPosition.z + .5) <= 64
    }

    override fun createMenu(windowId: Int, playerInventory: Inventory): AbstractContainerMenu =
        PressMenu(windowId, playerInventory, this, dataAccess)

    override fun getDefaultName() = TranslatableComponent("container.${NuclearTech.MODID}.steam_press")

    override fun getRenderBoundingBox() = AABB(blockPos.offset(.0, -1.0, .0), blockPos.offset(1.0, 1.0, 1.0))

    override fun setRecipeUsed(recipe: Recipe<*>?) {
        if (recipe == null) return
        recipesUsed.addTo(recipe.id, 1)
    }

    override fun getRecipeUsed(): Recipe<*>? = null

    override fun awardUsedRecipes(player: Player) {}

    override fun clearUsedRecipes() = recipesUsed.clear()

    override fun getExperienceToDrop(player: Player?): Float =
        recipesUsed.object2IntEntrySet().mapNotNull { (recipeID, amount) ->
            (level!!.recipeManager.byKey(recipeID).orElse(null) as? PressingRecipe)?.experience?.times(amount)
        }.sum()

    override fun getRecipesToAward(player: Player): List<Recipe<*>> =
        recipesUsed.keys.mapNotNull { player.level.recipeManager.byKey(it).orElse(null) }

    override fun fillStackedContents(recipeItemHelper: StackedContents) {
        for (itemStack in items) recipeItemHelper.accountStack(itemStack)
    }

    private fun canPress(recipe: Recipe<*>?): Boolean =
        if (items[0].isEmpty || items[1].isEmpty || recipe == null) false
        else canTransferItem(recipe.resultItem, items[3], this)

    private fun getBurnDuration(fuel: ItemStack): Int = if (fuel.isEmpty) 0 else ForgeHooks.getBurnTime(fuel, null)

    @Suppress("UsePropertyAccessSyntax")
    private fun press(recipe: Recipe<*>?): Boolean {
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
        level!!.playSound(null, blockPos, SoundEvents.pressOperate.get(), SoundSource.BLOCKS, 1.5F, 1F)
        return true
    }

    private var isRetracting = false

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (isLit)
            litTime--

        if (level.isClientSide) return

        val wasLit = isLit
        var contentsChanged = false

        val fuel = items[2]
        val recipe = level.recipeManager.getRecipeFor(RecipeTypes.PRESSING, this, level).orElse(null)

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

        if (power < powerNeeded) isRetracting = true

        if (!isRetracting && power >= powerNeeded && pressProgress >= pressTotalTime) if (press(recipe)) {
            isRetracting = true
            contentsChanged = true
        }

        val  speed = power * 25 / maxPower
        if (power >= powerNeeded) {
            if (canPress(recipe)) {
                if (!isRetracting) pressProgress += speed
            } else isRetracting = true
            if (isRetracting) pressProgress -= speed
        } else isRetracting = true

        if (pressProgress <= 0) {
            isRetracting = false
            pressProgress = 0
        }

        syncToClient(false)

        if (wasLit != isLit) contentsChanged = true
        if (contentsChanged) setChanged()
    }

    override fun setChanged() {
        super.setChanged()
        syncToClient(true)
    }

    private fun syncToClient(withItems: Boolean) {
        if (!level!!.isClientSide)
            NuclearPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with { (level!!.getChunkAt(blockPos)) }, BlockEntityUpdateMessage(blockPos, if (withItems) updateTag else CompoundTag().apply { putInt("Progress", pressProgress) }))
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
