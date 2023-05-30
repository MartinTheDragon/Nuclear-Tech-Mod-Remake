package at.martinthedragon.nucleartech.datagen.recipe

import at.martinthedragon.nucleartech.MaterialGroup
import at.martinthedragon.nucleartech.Materials
import at.martinthedragon.nucleartech.TagGroup
import at.martinthedragon.nucleartech.TagGroups
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.resources.ResourceLocation
import java.util.function.Consumer
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class RuleBasedRecipeBuilder {
    private val rules = mutableListOf<Rule>()
    private val materials = mutableMapOf<MaterialGroup, TagGroup>()

    fun addRule(rule: Rule): RuleBasedRecipeBuilder {
        rules += rule
        return this
    }

    fun forMaterial(materialGroup: MaterialGroup): RuleBasedRecipeBuilder {
        materials[materialGroup] = TagGroup(materialGroup)
        return this
    }

    fun forMaterial(tagGroup: TagGroup): RuleBasedRecipeBuilder {
        materials[tagGroup.materialGroup] = tagGroup
        return this
    }

    fun forMaterial(vararg materialGroups: MaterialGroup): RuleBasedRecipeBuilder {
        materialGroups.forEach(this::forMaterial)
        return this
    }

    fun forMaterial(vararg tagGroups: TagGroup): RuleBasedRecipeBuilder {
        tagGroups.forEach(this::forMaterial)
        return this
    }

    fun excluding(materialGroup: MaterialGroup): RuleBasedRecipeBuilder {
        materials -= materialGroup
        return this
    }

    fun excluding(tagGroup: TagGroup): RuleBasedRecipeBuilder {
        materials -= tagGroup.materialGroup
        return this
    }

    fun excluding(vararg materialGroups: MaterialGroup): RuleBasedRecipeBuilder {
        materialGroups.forEach(this::excluding)
        return this
    }

    fun excluding(vararg tagGroups: TagGroup): RuleBasedRecipeBuilder {
        tagGroups.forEach(this::excluding)
        return this
    }

    fun build(consumer: Consumer<FinishedRecipe>) {
        for (rule in rules) for (tagGroup in materials.values) {
            val (recipeBuilder, name) = rule(tagGroup) ?: continue
            recipeBuilder.save(consumer, name)
        }
    }

    fun interface Rule : (TagGroup) -> Pair<RecipeBuilder, ResourceLocation>?

    companion object {
        @OptIn(ExperimentalContracts::class)
        inline fun create(builderFunction: RuleBasedRecipeBuilder.() -> Unit): RuleBasedRecipeBuilder {
            contract {
                callsInPlace(builderFunction, InvocationKind.EXACTLY_ONCE)
            }

            val builder = RuleBasedRecipeBuilder()
            builder.builderFunction()
            return builder
        }
    }
}

fun RuleBasedRecipeBuilder.forAllTagsAndMaterials(): RuleBasedRecipeBuilder {
    Materials.getAllMaterials().forEach(this::forMaterial)
    TagGroups.getAllTagGroups().forEach(this::forMaterial)
    return this
}

fun RuleBasedRecipeBuilder.forModTagsAndMaterials(): RuleBasedRecipeBuilder {
    Materials.getModMaterials().forEach(this::forMaterial)
    TagGroups.getModTagGroups().forEach(this::forMaterial)
    return this
}
