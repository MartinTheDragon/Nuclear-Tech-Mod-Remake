package at.martinthedragon.nucleartech.fallout

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.Util
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.GsonHelper
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.registries.ForgeRegistries
import java.util.*
import kotlin.jvm.optionals.getOrNull

data class FalloutTransformation(
    val id: ResourceLocation,
    val targets: List<Target>,
    val chance: Float,
    val minDistance: Float,
    val maxDistance: Float,
    val maxDepth: Int,
    val result: BlockState,
    val alternative: BlockState?,
    val keepRotationData: Boolean
) {
    fun process(blockState: BlockState, random: Random, distance: Float, depth: Int): BlockState? {
        if (targets.none { it.matches(blockState) }) return null
        if (distance !in minDistance..maxDistance) return null
        if (maxDepth != -1 && depth > maxDepth) return null
        if (chance == 1F) return transform(blockState, result)
        return if (random.nextFloat() < chance) transform(blockState, result) else alternative?.let { transform(blockState, it) }
    }

    private fun transform(blockState: BlockState, to: BlockState): BlockState {
        if (!keepRotationData) return to
        applyPropertyIfPossible(blockState, to, BlockStateProperties.AXIS)
        applyPropertyIfPossible(blockState, to, BlockStateProperties.HORIZONTAL_FACING)
        applyPropertyIfPossible(blockState, to, BlockStateProperties.ORIENTATION)
        applyPropertyIfPossible(blockState, to, BlockStateProperties.FACING)
        applyPropertyIfPossible(blockState, to, BlockStateProperties.DOUBLE_BLOCK_HALF)
        applyPropertyIfPossible(blockState, to, BlockStateProperties.HALF)
        return to
    }

    private fun <T : Comparable<T>> applyPropertyIfPossible(from: BlockState, to: BlockState, property: Property<T>) {
        if (!to.hasProperty(property) || !from.hasProperty(property)) return
        to.setValue(property, from.getValue(property))
    }

    sealed interface Target {
        fun matches(blockState: BlockState): Boolean
        fun saveToJson(json: JsonObject)
    }

    data class BlockTarget(private val block: Block) : Target {
        override fun matches(blockState: BlockState) = blockState.`is`(block)

        override fun saveToJson(json: JsonObject) {
            getOrCreateBlockTargets(json).add(block.registryName!!.toString())
        }
    }

    data class BlockStateTarget(private val blockState: BlockState, private val requiredProperties: Map<Property<*>, Comparable<*>>) : Target {
        override fun matches(blockState: BlockState): Boolean {
            if (!blockState.`is`(this.blockState.block)) return false
            for ((property, value) in requiredProperties) {
                if (value != blockState.getValue(property))
                    return false
            }
            return true
        }

        override fun saveToJson(json: JsonObject) {
            getOrCreateBlockTargets(json).add(encodeBlockState(blockState, requiredProperties.keys.toList()))
        }
    }

    data class ExactBlockStateTarget(private val blockState: BlockState) : Target {
        override fun matches(blockState: BlockState) = blockState == this.blockState

        override fun saveToJson(json: JsonObject) {
            getOrCreateBlockTargets(json).add(encodeBlockState(blockState))
        }
    }

    data class TagTarget(private val tag: TagKey<Block>, private val excluding: List<Target>) : Target {
        override fun matches(blockState: BlockState): Boolean {
            if (excluding.any { it.matches(blockState) }) return false
            return blockState.`is`(tag)
        }

        override fun saveToJson(json: JsonObject) {
            getOrCreateTagTargets(json).add("#${tag.location}")
            val output = JsonObject()
            for (exclusion in excluding) {
                if (exclusion is TagTarget) throw IllegalStateException("Cannot exclude tags")
                exclusion.saveToJson(output)
            }
            if (excluding.isNotEmpty()) {
                val targets = getOrCreateTargets(json)
                if (!targets.has("excluding")) {
                    targets.add("excluding", JsonArray())
                }
                targets.getAsJsonArray("excluding").addAll(getOrCreateBlockTargets(output))
            }
        }
    }

    companion object {
        @JvmStatic fun fromJson(id: ResourceLocation, json: JsonObject): FalloutTransformation {
            // get JSON
            val targetsJson = GsonHelper.getAsJsonObject(json, "targets")
            val blockList = GsonHelper.getAsJsonArray(targetsJson, "block", null)
            val tagList = GsonHelper.getAsJsonArray(targetsJson, "tag", null)
            if (blockList == null && tagList == null)
                throw JsonParseException("Element 'targets' doesn't contain a valid target definition")

            val excluding = GsonHelper.getAsJsonArray(targetsJson, "excluding", null)

            if (excluding != null && tagList == null)
                throw JsonParseException("Exclusions only work with tags!")

            val chance = GsonHelper.getAsFloat(json, "chance", 1F)
            val minDistance = GsonHelper.getAsFloat(json, "min_distance", 0F)
            val maxDistance = GsonHelper.getAsFloat(json, "max_distance", 1F)
            val depth = GsonHelper.getAsInt(json, "max_depth", 0)

            val result = GsonHelper.getAsString(json, "result")
            val alternative = GsonHelper.getAsString(json, "alternative", null)
            val keepRotationData = GsonHelper.getAsBoolean(json, "keep_rotations", false)

            // parse JSON

            val exclusions = excluding?.map { GsonHelper.convertToString(it, "exclusion element") }?.map { parseBlockOrBlockState(it) } ?: emptyList()

            val targets = buildList {
                if (blockList != null) for (blockElement in blockList) {
                    add(parseBlockOrBlockState(GsonHelper.convertToString(blockElement, "block element")))
                }
                if (tagList != null) for (tagElement in tagList) {
                    add(TagTarget(TagKey.create(ForgeRegistries.BLOCKS.registryKey, ResourceLocation(GsonHelper.convertToString(tagElement, "tag element").removePrefix("#"))), exclusions))
                }
            }
            if (targets.isEmpty())
                throw JsonParseException("Couldn't correctly parse at least one target")

            return FalloutTransformation(id, targets, chance, minDistance, maxDistance, depth, getBlockState(result), alternative?.let { getBlockState(it) }, keepRotationData)
        }

        private fun parseBlockOrBlockState(string: String) =
            if (string.contains('[')) tryParseBlockState(string)
            else tryParseBlock(string)

        private fun tryParseBlock(string: String): Target {
            val block = getBlock(string)
            return BlockTarget(block)
        }

        @OptIn(ExperimentalStdlibApi::class)
        private fun tryParseBlockState(string: String): Target {
            val block = getBlock(string.substringBefore('['))
            val properties = buildMap<Property<*>, Comparable<*>> {
                val stateDefinition = block.stateDefinition
                string.substringAfter('[', "").removeSuffix("]").split(',').forEach {
                    val propertyString = it.substringBefore('=')
                    val property = stateDefinition.getProperty(propertyString) ?: throw JsonParseException("Block '${block.registryName}' has no property named '$propertyString'")
                    val valueString = it.substringAfter('=')
                    val value = property.getValue(valueString).getOrNull() ?: throw JsonParseException("Property '$propertyString' of block '${block.registryName}' can't have value '$valueString'")
                    put(property, value)
                }
            }
            val blockState = properties.entries.fold(block.defaultBlockState()) { acc, (property, value) -> acc.neighbours.get(property, value) ?: acc }

            return if (block.stateDefinition.properties.containsAll(properties.keys))
                ExactBlockStateTarget(blockState)
            else
                BlockStateTarget(blockState, properties)
        }

        private fun getBlock(string: String): Block =
            ForgeRegistries.BLOCKS.getValue(ResourceLocation(string)) ?: throw JsonParseException("Couldn't find block '$string'")

        @OptIn(ExperimentalStdlibApi::class)
        private fun getBlockState(string: String): BlockState {
            val block = getBlock(string.substringBefore('['))
            val properties = buildMap<Property<*>, Comparable<*>> {
                val stateDefinition = block.stateDefinition
                string.substringAfter('[', "").removeSuffix("]").split(',').forEach {
                    if (it.isEmpty()) return@forEach
                    val propertyString = it.substringBefore('=')
                    val property = stateDefinition.getProperty(propertyString) ?: throw JsonParseException("Block '${block.registryName}' has no property named '$propertyString'")
                    val valueString = it.substringAfter('=')
                    val value = property.getValue(valueString).getOrNull() ?: throw JsonParseException("Property '$propertyString' of block '${block.registryName}' can't have value '$valueString'")
                    put(property, value)
                }
            }
            return properties.entries.fold(block.defaultBlockState()) { acc, (property, value) -> acc.neighbours.get(property, value) ?: acc }
        }

        private fun getOrCreateTargets(json: JsonObject): JsonObject {
            if (!json.has("targets")) {
                json.add("targets", JsonObject())
            }
            return json.getAsJsonObject("targets")
        }

        private fun getOrCreateBlockTargets(json: JsonObject): JsonArray {
            val targets = getOrCreateTargets(json)
            if (!targets.has("block")) {
                targets.add("block", JsonArray())
            }
            return targets.getAsJsonArray("block")
        }

        private fun getOrCreateTagTargets(json: JsonObject): JsonArray {
            val targets = getOrCreateTargets(json)
            if (!targets.has("tag")) {
                targets.add("tag", JsonArray())
            }
            return targets.getAsJsonArray("tag")
        }

        fun encodeBlockState(blockState: BlockState, properties: List<Property<*>> = blockState.properties.toList()): String {
            val registryName = blockState.block.registryName!!.toString()
            val encodedProperties = blockState.values.filterKeys(properties::contains).map { (property, value) -> "${property.name}=${Util.getPropertyName(property, value)}" }
            val couldOmitBrackets = encodedProperties.isEmpty() || blockState == blockState.block.defaultBlockState()
            return if (couldOmitBrackets) registryName
            else "$registryName${encodedProperties.joinToString(",", "[", "]")}"
        }
    }
}
