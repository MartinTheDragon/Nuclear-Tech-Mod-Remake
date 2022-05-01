package at.martinthedragon.nucleartech.api.blocks.entities

import at.martinthedragon.nucleartech.api.world.dropExperience
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.Recipe

public interface ExperienceRecipeResultBlockEntity {
    public fun getExperienceToDrop(player: Player?): Float
    public fun getRecipesToAward(player: Player): List<Recipe<*>>
    public fun clearUsedRecipes()
}

public fun ExperienceRecipeResultBlockEntity.dropExperienceAndAwardRecipes(player: Player) {
    player.awardRecipes(getRecipesToAward(player))
    player.level.dropExperience(player.position(), getExperienceToDrop(player))
    clearUsedRecipes()
}
