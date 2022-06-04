package at.martinthedragon.nucleartech.config

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.NuclearTags
import com.mojang.logging.LogUtils
import net.minecraft.Util
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.Tags
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.registries.ForgeRegistries
import org.slf4j.Logger
import kotlin.random.Random

class FalloutConfig : ConfigBase {
    override val fileName = "fallout"
    override val configSpec: ForgeConfigSpec
    override val configType = ModConfig.Type.SERVER

    val burnFlammables: ForgeConfigSpec.BooleanValue
    val removePlants: ForgeConfigSpec.BooleanValue
    val transformationsList: ForgeConfigSpec.ConfigValue<List<String>>

    init {
        ForgeConfigSpec.Builder().apply {
            comment("Fallout Config. Synced from server to client.").push(fileName)

            burnFlammables = comment("Set fire to any flammable blocks").define("burnFlammables", true)
            removePlants = comment("Remove any plants (more specifically those that implement IPlantable or IForgeShearable)").define("removePlants", true)
            // TODO
            transformationsList = comment("EXPERIMENTAL: A list/map for transforming specific blocks to other specific blocks (an in-depth description of how the format works will be available later)").defineListAllowEmpty(listOf(("transformationsList")), FalloutTransformation::buildDefaultTransformations, FalloutTransformation::isValidElement)

            pop()
            configSpec = build()
        }
    }

    class BlockListFalloutTransformation(val from: List<Block>, to: BlockState, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : FalloutTransformation(to, chance, distance, transformationDepth) {
        constructor(from: List<Block>, to: Block, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(from, to.defaultBlockState(), chance, distance, transformationDepth)
        constructor(from: Block, to: BlockState, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(listOf(from), to, chance, distance, transformationDepth)
        constructor(from: Block, to: Block, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(from, to.defaultBlockState(), chance, distance, transformationDepth)

        override fun matches(blockState: BlockState) = from.any { blockState.`is`(it) }

        override fun toString(): String {
            val fromString = if (from.size == 1) from.single().registryName!! else from.map(Block::getRegistryName).joinToString(",", "{", "}")
            return "$fromString|${encodeBlockState(to, true)}|$chance|$distance|$transformationDepth"
        }
    }

    class BlockStateListFalloutTransformation(val from: List<BlockState>, to: BlockState, chance: Float = 1F, distance: Float = 1F, depth: Int = 1) : FalloutTransformation(to, chance, distance, depth) {
        constructor(from: List<Block>, to: Block, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(from.map(Block::defaultBlockState), to.defaultBlockState(), chance, distance, transformationDepth)
        constructor(from: BlockState, to: BlockState, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(listOf(from), to, chance, distance, transformationDepth)
        constructor(from: Block, to: Block, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(from.defaultBlockState(), to.defaultBlockState(), chance, distance, transformationDepth)

        override fun matches(blockState: BlockState) = blockState in from

        override fun toString(): String {
            val fromString = if (from.size == 1) encodeBlockState(from.single()) else from.joinToString(",", "{", "}", transform = FalloutTransformation::encodeBlockState)
            return "$fromString|${encodeBlockState(to, true)}|$chance|$distance|$transformationDepth"
        }
    }

    open class TagFalloutTransformation(val from: TagKey<Block>, to: BlockState, chance: Float = 1F, distance: Float = 1F, depth: Int = 1) : FalloutTransformation(to, chance, distance, depth) {
        constructor(from: TagKey<Block>, to: Block, chance: Float = 1F, distance: Float = 1F, transformationDepth: Int = 1) : this(from, to.defaultBlockState(), chance, distance, transformationDepth)

        override fun matches(blockState: BlockState) = blockState.`is`(from)

        override fun toString(): String {
            return "#${from.location}|${encodeBlockState(to, true)}|$chance|$distance|$transformationDepth"
        }
    }

    class BlacklistedFalloutTransformation(val falloutTransformation: FalloutTransformation, val without: List<BlockState>) : FalloutTransformation(falloutTransformation.to, falloutTransformation.chance, falloutTransformation.distance, falloutTransformation.transformationDepth) {
        override fun matches(blockState: BlockState) = falloutTransformation.matches(blockState) && without.none { it == blockState }
        override fun shouldTransform(blockState: BlockState, distance: Float, depth: Int) = falloutTransformation.shouldTransform(blockState, distance, depth)
        override fun transform(blockState: BlockState) = falloutTransformation.transform(blockState)

        override fun toString(): String {
            val output = falloutTransformation.toString()
            val withoutString = without.joinToString(",", "{", "}") { encodeBlockState(it, true) }
            return "${output.substringBefore('|')}!$withoutString|${output.substringAfter('|')}"
        }
    }

    class TagBlacklistedFalloutTransformation(val falloutTransformation: FalloutTransformation, val without: TagKey<Block>) : FalloutTransformation(falloutTransformation.to, falloutTransformation.chance, falloutTransformation.distance, falloutTransformation.transformationDepth) {
        override fun matches(blockState: BlockState) = falloutTransformation.matches(blockState) && !blockState.`is`(without)
        override fun shouldTransform(blockState: BlockState, distance: Float, depth: Int) = falloutTransformation.shouldTransform(blockState, distance, depth)
        override fun transform(blockState: BlockState) = falloutTransformation.transform(blockState)

        override fun toString(): String {
            val output = falloutTransformation.toString()
            return "${output.substringBefore('|')}!#${without.location}|${output.substringAfter('|')}"
        }
    }

    class AlternativeFalloutTransformation(val falloutTransformation: FalloutTransformation, val alternative: BlockState) : FalloutTransformation(falloutTransformation.to, falloutTransformation.chance, falloutTransformation.distance, falloutTransformation.transformationDepth) {
        private var resultAlternative = false

        override fun shouldTransform(blockState: BlockState, distance: Float, depth: Int): Boolean {
            if (!matches(blockState) || this.distance < distance || this.transformationDepth < depth) return false
            if (Random.nextFloat() >= chance) resultAlternative = true
            return true
        }

        override fun transform(blockState: BlockState) = transform(blockState, if (resultAlternative) alternative else to)

        override fun matches(blockState: BlockState) = falloutTransformation.matches(blockState)

        override fun toString(): String {
            val elements = falloutTransformation.toString().split('|')
            return "${elements[0]}|${elements[1]}?${encodeBlockState(alternative, true)}|${elements.drop(2).joinToString("|")}"
        }
    }

    sealed class FalloutTransformation(val to: BlockState, val chance: Float = 1F, val distance: Float = 1F, val transformationDepth: Int = 1) {
        open fun shouldTransform(blockState: BlockState, distance: Float, depth: Int): Boolean {
            return matches(blockState) && Random.nextFloat() < chance && this.distance >= distance && (this.transformationDepth >= depth || transformationDepth == -1)
        }

        open fun transform(blockState: BlockState) = transform(blockState, to)

        protected fun transform(blockState: BlockState, to: BlockState): BlockState {
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

        abstract fun matches(blockState: BlockState): Boolean
        abstract override fun toString(): String

        companion object {
            val LOGGER: Logger = LogUtils.getLogger()

            fun buildDefaultTransformations() = listOf(
                TagFalloutTransformation(BlockTags.LEAVES, Blocks.AIR, transformationDepth = -1),
                TagFalloutTransformation(BlockTags.SNOW, Blocks.AIR, transformationDepth = -1),
                TagFalloutTransformation(BlockTags.LOGS_THAT_BURN, ModBlocks.charredLog.get(), transformationDepth = -1),
                BlockListFalloutTransformation(Blocks.MUSHROOM_STEM, ModBlocks.charredLog.get(), transformationDepth = -1),
                TagFalloutTransformation(Tags.Blocks.STONE, ModBlocks.hotSellafite.get(), distance = .05F, transformationDepth = 3),
                TagFalloutTransformation(Tags.Blocks.STONE, ModBlocks.sellafite.get(), distance = .15F, transformationDepth = 3),
                TagFalloutTransformation(Tags.Blocks.STONE, ModBlocks.slakedSellafite.get(), distance = .75F, transformationDepth = 3),
                BlockListFalloutTransformation(listOf(Blocks.GRASS_BLOCK, Blocks.PODZOL), ModBlocks.deadGrass.get()),
                BlockListFalloutTransformation(Blocks.MYCELIUM, ModBlocks.glowingMycelium.get()),
                TagFalloutTransformation(Tags.Blocks.SAND_COLORLESS, ModBlocks.trinitite.get(), chance = .05F),
                TagFalloutTransformation(Tags.Blocks.SAND_RED, ModBlocks.trinitite.get(), chance = .05F),
                BlockListFalloutTransformation(Blocks.CLAY, Blocks.TERRACOTTA),
                TagFalloutTransformation(Tags.Blocks.ORES_COAL, Blocks.DIAMOND_ORE, chance = 0.08F),
                TagFalloutTransformation(Tags.Blocks.ORES_COAL, Blocks.EMERALD_ORE, chance = 0.15F),
                BlockListFalloutTransformation(listOf(Blocks.RED_MUSHROOM_BLOCK, Blocks.BROWN_MUSHROOM_BLOCK), Blocks.AIR, transformationDepth = -1),
                BlacklistedFalloutTransformation(AlternativeFalloutTransformation(TagFalloutTransformation(NuclearTags.Blocks.ORES_URANIUM, ModBlocks.schrabidiumOre.get(), chance = 0.01F), ModBlocks.scorchedUraniumOre.get().defaultBlockState()), listOf(ModBlocks.scorchedUraniumOre.get().defaultBlockState()))
            ).map(FalloutTransformation::toString)

            fun isValidElement(whatever: Any) = try {
                whatever as String
                loadFromString(whatever)
                true
            } catch (ex: Exception) {
                false
            }

            fun loadFromString(string: String): FalloutTransformation {
                val elements = string.split('|')

                val chance = elements.getOrNull(2)?.toFloatOrNull() ?: 1F
                val distance = elements.getOrNull(3)?.toFloatOrNull() ?: 1F
                val transformationDepth = elements.getOrNull(4)?.toIntOrNull() ?: 1

                var fromString = elements[0]
                var blackListString: String? = null
                if (fromString.contains('!')) {
                    blackListString = fromString.substringAfter('!')
                    fromString = fromString.substringBefore('!')
                }

                var toString = elements[1]
                var alternativeString: String? = null
                if (toString.contains('?')) {
                    alternativeString = toString.substringAfter('?')
                    toString = toString.substringBefore('?')
                }

                val to = decodeBlockState(toString) ?: throw IllegalArgumentException()

                val fromIsBlockState = fromString.contains('[')

                var transformation = when {
                    fromString.startsWith('#') -> TagFalloutTransformation(parseTag(fromString), to, chance, distance, transformationDepth)
                    fromString.startsWith('{') -> if (fromIsBlockState) BlockStateListFalloutTransformation(parseBlockStateList(fromString), to, chance, distance, transformationDepth) else BlockListFalloutTransformation(parseBlockList(fromString), to, chance, distance, transformationDepth)
                    fromIsBlockState -> BlockStateListFalloutTransformation(listOf(decodeBlockState(fromString) ?: throw IllegalArgumentException()), to, chance, distance, transformationDepth)
                    else -> BlockListFalloutTransformation(ForgeRegistries.BLOCKS.getValue(ResourceLocation(fromString)) ?: throw IllegalArgumentException(), to, chance, distance, transformationDepth)
                }

                if (alternativeString != null && alternativeString.isNotBlank()) transformation = AlternativeFalloutTransformation(transformation, decodeBlockState(alternativeString) ?: throw IllegalArgumentException())

                return if (blackListString != null && blackListString.isNotBlank()) when {
                        blackListString.startsWith('{') -> BlacklistedFalloutTransformation(transformation, parseBlockStateList(blackListString))
                        blackListString.startsWith('#') -> TagBlacklistedFalloutTransformation(transformation, parseTag(blackListString))
                        else -> throw IllegalArgumentException()
                    }
                else transformation
            }

            private fun parseBlockList(string: String) = string.removeSurrounding("{", "}").split(',').map(::ResourceLocation).mapNotNull(ForgeRegistries.BLOCKS::getValue)
            private fun parseBlockStateList(string: String) = string.removeSurrounding("{", "}").split(',').mapNotNull(FalloutTransformation::decodeBlockState)
            private fun parseTag(string: String) = TagKey.create(ForgeRegistries.BLOCKS.registryKey, ResourceLocation(string.removePrefix("#")))

            fun loadFromConfig() = NuclearConfig.fallout.transformationsList.get().map(FalloutTransformation::loadFromString)

            fun encodeBlockState(blockState: BlockState, mayOmitBrackets: Boolean = false): String {
                val registryName = blockState.block.registryName!!.toString()
                val properties = blockState.values.map { (property, value) -> "${property.name}=${Util.getPropertyName(property, value)}" }
                val couldOmitBrackets = properties.isEmpty() || blockState == blockState.block.defaultBlockState()
                return when {
                    mayOmitBrackets && couldOmitBrackets -> registryName
                    couldOmitBrackets -> "$registryName[]"
                    else -> "$registryName${properties.joinToString(",", "[", "]")}"
                }
            }

            fun decodeBlockState(string: String): BlockState? {
                val blockString = string.substringBefore('[')
                val block = ForgeRegistries.BLOCKS.getValue(ResourceLocation(blockString)) ?: return null // TODO better error messages
                val propertiesString = string.substringAfter('[', "").removeSuffix("]")
                val properties: Map<Property<*>, Comparable<*>> = if (propertiesString.isNotEmpty()) {
                    val stateDefinition = block.stateDefinition
                    propertiesString.split(',').map {
                        val property = stateDefinition.getProperty(it.substringBefore('=')) ?: return null
                        val value = property.getValue(it.substringAfter('=')).orElseGet { throw IllegalArgumentException("'$it' is an invalid property") }
                        property to value
                    }.toMap()
                } else emptyMap()
                return properties.entries.fold(block.defaultBlockState()) { acc, (property, value) -> acc.neighbours.get(property, value) ?: acc }
            }
        }
    }
}
