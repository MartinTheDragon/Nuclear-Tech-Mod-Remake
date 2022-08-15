package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.extensions.contains
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import kotlin.jvm.optionals.getOrNull

abstract class RecipeMachineBlockEntity<RECIPE : Recipe<*>>(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : ProgressingMachineBlockEntity(type, pos, state) {
    open var recipe: RECIPE? = null
        protected set
    val recipeID: ResourceLocation? get() = recipe?.id

    protected abstract fun findPossibleRecipe(): RECIPE?
    protected abstract fun matchesRecipe(recipe: RECIPE): Boolean
    protected abstract fun processRecipe(recipe: RECIPE)

    override fun checkCanProgress(): Boolean {
        val previousRecipe = recipe
        val recipe = findPossibleRecipe()
        this.recipe = recipe
        if (previousRecipe != recipe) {
            markDirty()
            sendContinuousUpdatePacket()
        }
        return recipe != null && matchesRecipe(recipe)
    }

    override fun onProgressFinished() {
        val recipe = recipe ?: return
        processRecipe(recipe)
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().also(this::saveRecipe)

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        loadRecipe(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        saveRecipe(tag)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        loadRecipe(tag)
    }

    protected fun saveRecipe(tag: CompoundTag) {
        recipeID?.let { tag.putString("Recipe", it.toString()) }
    }

    @OptIn(ExperimentalStdlibApi::class)
    protected fun loadRecipe(tag: CompoundTag) {
        if (tag.contains("Recipe", Tag.TAG_STRING)) {
            val recipeString = tag.getString("Recipe")
            if (ResourceLocation.isValidResourceLocation(recipeString)) {
                val recipe = Minecraft.getInstance().level?.recipeManager?.byKey(ResourceLocation(recipeString))?.getOrNull()
                @Suppress("UNCHECKED_CAST")
                this.recipe = recipe as? RECIPE ?: return
            }
        }
    }
}
