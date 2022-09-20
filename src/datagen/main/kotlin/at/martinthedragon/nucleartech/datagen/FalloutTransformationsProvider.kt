package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.NTechTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.extensions.appendToPath
import at.martinthedragon.nucleartech.fallout.FalloutTransformation
import at.martinthedragon.nucleartech.ntm
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.save
import net.minecraft.data.HashCache
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.common.Tags
import java.util.function.Consumer

class FalloutTransformationsProvider(private val generator: DataGenerator) : DataProvider {
    override fun getName() = "Nuclear Tech Mod Fallout Transformations"

    private fun createTransformations(consumer: Consumer<Builder>) {
        builder()
            .from(BlockTags.LEAVES)
            .from(Blocks.MOSS_BLOCK) // TODO lush caves decay
            .from(Blocks.MOSS_CARPET)
            .from(Blocks.MELON)
            .from(Blocks.PUMPKIN)
            .from(BlockTags.SNOW)
            .from(Blocks.POWDER_SNOW)
            .from(Blocks.ICE)
            .from(Blocks.FROSTED_ICE)
            .from(Blocks.BEEHIVE)
            .from(Blocks.BEE_NEST)
            .from(Blocks.RED_MUSHROOM_BLOCK)
            .from(Blocks.BROWN_MUSHROOM_BLOCK)
            .results(Blocks.AIR).depth(-1).save(consumer)
        builder().from(Blocks.WATER_CAULDRON).from(Blocks.POWDER_SNOW_CAULDRON).results(Blocks.CAULDRON).save(consumer)
        builder().from(BlockTags.LOGS_THAT_BURN).from(Blocks.MUSHROOM_STEM).results(NTechBlocks.charredLog.get()).rotations().depth(-1).save(consumer)
        builder().from(BlockTags.PLANKS).results(NTechBlocks.charredPlanks.get()).rotations().depth(-1).save(consumer)
        builder().from(Tags.Blocks.STONE).from(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).from(Tags.Blocks.GRAVEL).from(NTechBlocks.slakedSellafite.get()).from(NTechBlocks.sellafite.get()).results(NTechBlocks.hotSellafite.get()).max(.05F).depth(3).save(consumer)
        builder().from(Tags.Blocks.STONE).from(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).from(Tags.Blocks.GRAVEL).from(NTechBlocks.slakedSellafite.get()).results(NTechBlocks.sellafite.get()).min(.05F).max(.15F).depth(3).save(consumer)
        builder().from(Tags.Blocks.STONE).from(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).from(Tags.Blocks.GRAVEL).results(NTechBlocks.slakedSellafite.get()).min(.15F).max(.50F).depth(3).save(consumer)
        builder().from(Blocks.GRASS_BLOCK).from(Blocks.PODZOL).results(NTechBlocks.deadGrass.get()).save(consumer)
        builder().from(Blocks.MYCELIUM).results(NTechBlocks.glowingMycelium.get()).save(consumer)
        builder().from(Tags.Blocks.SAND_COLORLESS).results(NTechBlocks.trinitite.get()).chance(.05F).save(consumer)
        builder().from(Tags.Blocks.SAND_RED).results(NTechBlocks.redTrinitite.get()).chance(.05F).save(consumer)
        builder().from(Blocks.CLAY).results(Blocks.TERRACOTTA).save(consumer)
        builder().from(Blocks.PACKED_ICE).from(Blocks.BLUE_ICE).results(Blocks.WATER).depth(3).save(consumer)
        builder().from(Tags.Blocks.COBBLESTONE_MOSSY).results(Blocks.COAL_ORE).save(consumer)
        builder().from(Tags.Blocks.ORES_COAL).results(Blocks.DIAMOND_ORE).chance(.30F).save(consumer) // TODO perhaps make the alternative mechanism be a map of chances?
        builder().from(Tags.Blocks.ORES_COAL).results(Blocks.EMERALD_ORE).chance(.20F).save(consumer)
        builder().from(NTechTags.Blocks.ORES_LIGNITE).results(Blocks.DIAMOND_ORE).chance(.08F).setId(ntm("diamond_ore_from_lignite_ore")).save(consumer)
        builder().from(NTechTags.Blocks.ORES_URANIUM, NTechBlocks.deepslateUraniumOre.get(), NTechBlocks.netherUraniumOre.get(), NTechBlocks.scorchedUraniumOre.get(), NTechBlocks.scorchedDeepslateUraniumOre.get(), NTechBlocks.scorchedNetherUraniumOre.get()).results(NTechBlocks.schrabidiumOre.get()).chance(.01F).alternative(NTechBlocks.scorchedUraniumOre.get()).save(consumer)
        builder().from(NTechBlocks.deepslateUraniumOre.get()).results(NTechBlocks.deepslateSchrabidiumOre.get()).chance(.01F).alternative(NTechBlocks.scorchedDeepslateUraniumOre.get()).save(consumer)
        builder().from(NTechBlocks.netherUraniumOre.get()).results(NTechBlocks.netherSchrabidiumOre.get()).chance(.01F).alternative(NTechBlocks.scorchedNetherUraniumOre.get()).save(consumer)
    }

    private fun builder() = Builder()

    override fun run(cache: HashCache) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val path = generator.outputFolder
        val ids = mutableSetOf<ResourceLocation>()
        createTransformations {
            val id = it.getId()
            if (!ids.add(id)) {
                throw IllegalStateException("Duplicate fallout transformation $id")
            } else {
                save(gson, cache, it.toJson(), path.resolve("data/${id.namespace}/${NuclearTech.MODID}_fallouttf/${id.path}.json"))
            }
        }
    }

    private class Builder {
        private var id: ResourceLocation? = null
        private val targets = mutableListOf<FalloutTransformation.Target>()
        private var chance = 1F
        private var minDistance = 0F
        private var maxDistance = 1F
        private var depth = 0
        private lateinit var result: BlockState
        private var alternative: BlockState? = null
        private var keepRotationData: Boolean = false

        fun from(block: Block): Builder {
            targets += FalloutTransformation.BlockTarget(block)
            return this
        }

        fun from(state: BlockState): Builder {
            targets += FalloutTransformation.ExactBlockStateTarget(state)
            return this
        }

        fun from(state: BlockState, vararg properties: Property<*>): Builder {
            targets += FalloutTransformation.BlockStateTarget(state, properties.associateWith { state.getValue(it) })
            return this
        }

        fun from(tag: TagKey<Block>, excluding: List<FalloutTransformation.Target> = emptyList()): Builder {
            targets += FalloutTransformation.TagTarget(tag, excluding)
            return this
        }

        fun from(tag: TagKey<Block>, vararg excluding: Block) = from(tag, excluding.map(FalloutTransformation::BlockTarget))
        fun from(tag: TagKey<Block>, vararg excluding: BlockState) = from(tag, excluding.map(FalloutTransformation::ExactBlockStateTarget))

        fun results(block: Block): Builder {
            result = block.defaultBlockState()
            return this
        }

        fun results(blockState: BlockState): Builder {
            result = blockState
            return this
        }

        fun alternative(block: Block): Builder {
            alternative = block.defaultBlockState()
            return this
        }

        fun alternative(blockState: BlockState): Builder {
            alternative = blockState
            return this
        }

        fun setId(id: ResourceLocation): Builder {
            this.id = id
            return this
        }

        fun getId(): ResourceLocation {
            if (id != null) return id!!
            val autoId = ntm(result.block.registryName!!.path)
            return if (alternative != null) autoId.appendToPath("_or_${alternative!!.block.registryName!!.path}") else autoId
        }

        fun chance(chance: Float): Builder {
            this.chance = chance
            return this
        }

        fun min(distance: Float): Builder {
            minDistance = distance
            return this
        }

        fun max(distance: Float): Builder {
            maxDistance = distance
            return this
        }

        fun depth(depth: Int): Builder {
            this.depth = depth
            return this
        }

        fun rotations(shouldKeep: Boolean = true): Builder {
            keepRotationData = shouldKeep
            return this
        }

        fun toJson(): JsonObject {
            val output = JsonObject()
            targets.forEach { it.saveToJson(output) }
            if (chance != 1F) output.addProperty("chance", chance)
            if (minDistance != 0F) output.addProperty("min_distance", minDistance)
            if (maxDistance != 1F) output.addProperty("max_distance", maxDistance)
            if (depth != 0) output.addProperty("max_depth", depth)
            output.addProperty("result", FalloutTransformation.encodeBlockState(result))
            if (alternative != null) output.addProperty("alternative", FalloutTransformation.encodeBlockState(alternative!!))
            if (keepRotationData) output.addProperty("keep_rotations", true)
            return output
        }

        fun save(consumer: Consumer<Builder>) {
            if (targets.isEmpty()) throw IllegalStateException("No targets specified!")
            if (!this::result.isInitialized) throw IllegalStateException("No result specified")

            consumer.accept(this)
        }
    }
}
