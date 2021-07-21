package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.world.dropExperience
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.crafting.IRecipe

interface ExperienceRecipeResultTileEntity {
    fun getExperienceToDrop(player: PlayerEntity?): Float
    fun getRecipesToAward(player: PlayerEntity): List<IRecipe<*>>
    fun clearUsedRecipes()
}

fun ExperienceRecipeResultTileEntity.dropExperienceAndAwardRecipes(player: PlayerEntity) {
    player.awardRecipes(getRecipesToAward(player))
    player.level.dropExperience(player.position(), getExperienceToDrop(player))
    clearUsedRecipes()
}
