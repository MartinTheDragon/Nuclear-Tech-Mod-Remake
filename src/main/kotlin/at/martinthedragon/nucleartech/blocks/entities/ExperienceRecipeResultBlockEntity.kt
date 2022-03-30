package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.world.dropExperience
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.Recipe

interface ExperienceRecipeResultBlockEntity {
    fun getExperienceToDrop(player: Player?): Float
    fun getRecipesToAward(player: Player): List<Recipe<*>>
    fun clearUsedRecipes()
}

fun ExperienceRecipeResultBlockEntity.dropExperienceAndAwardRecipes(player: Player) {
    player.awardRecipes(getRecipesToAward(player))
    player.level.dropExperience(player.position(), getExperienceToDrop(player))
    clearUsedRecipes()
}