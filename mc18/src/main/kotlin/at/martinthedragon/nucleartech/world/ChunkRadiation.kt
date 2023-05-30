package at.martinthedragon.nucleartech.world

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.world.ChunkRadiationHandler
import at.martinthedragon.nucleartech.config.NuclearConfig
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraftforge.common.IPlantable
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.world.ChunkDataEvent
import net.minecraftforge.event.world.ChunkEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.LogicalSide
import net.minecraftforge.fml.common.Mod
import kotlin.math.abs
import kotlin.math.max

@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ChunkRadiation : ChunkRadiationHandler {
    private val perWorldRadiation = mutableMapOf<LevelAccessor, MutableMap<ChunkPos, Float>>()

    @SubscribeEvent @JvmStatic
    fun onWorldLoad(event: WorldEvent.Load) {
        if (!event.world.isClientSide) perWorldRadiation[event.world] = mutableMapOf()
    }

    @SubscribeEvent @JvmStatic
    fun onWorldUnload(event: WorldEvent.Unload) {
        if (!event.world.isClientSide) perWorldRadiation.remove(event.world)
    }

    private const val RADIATION_KEY = NuclearTech.MODID + "_radiation"

    @SubscribeEvent @JvmStatic
    fun onChunkLoad(event: ChunkDataEvent.Load) {
        if (event.world == null || event.world.isClientSide) return
        val worldRadiation = perWorldRadiation[event.world] ?: return
        worldRadiation[event.chunk.pos] = event.data.getFloat(RADIATION_KEY)
    }

    @SubscribeEvent @JvmStatic
    fun onChunkSave(event: ChunkDataEvent.Save) {
        if (event.world.isClientSide) return
        val worldRadiation = perWorldRadiation[event.world] ?: return
        val radiation = worldRadiation[event.chunk.pos] ?: 0F
        event.data.putFloat(RADIATION_KEY, radiation)
    }

    @SubscribeEvent @JvmStatic
    fun onChunkUnload(event: ChunkEvent.Unload) {
        if (event.world.isClientSide) return
        perWorldRadiation[event.world]?.remove(event.chunk.pos)
    }

    override fun getRadiation(world: LevelAccessor, pos: BlockPos): Float {
        val worldRadiation = perWorldRadiation[world] ?: return 0F
        return worldRadiation[ChunkPos(pos)] ?: 0F
    }

    override fun setRadiation(world: Level, pos: BlockPos, radiation: Float) {
        if (!world.worldBorder.isWithinBounds(pos)) return
        val worldRadiation = perWorldRadiation[world] ?: return
        worldRadiation[ChunkPos(pos)] = radiation
        world.getChunkAt(pos).isUnsaved = true
    }

    private var eggTimer = 0

    @SubscribeEvent @JvmStatic
    fun updateTimer(event: TickEvent.ServerTickEvent) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            eggTimer++
            if (eggTimer >= 20) {
                update()
                eggTimer = 0
            }

            if (NuclearConfig.radiation.worldRadiationEffects.get())
                irradiateEnvironment()
        }
    }

    private fun update() {
        for ((_, worldRadiation) in perWorldRadiation) {
            val temp = worldRadiation.toMap()
            worldRadiation.clear()

            for ((pos, radiation) in temp) {
                if (radiation == 0F) continue

                for (i in -1..1) for (j in -1..1) {
                    val type = abs(i) + abs(j)
                    val percent = if (type == 0) .6F else if (type == 1) .075F else 0.025F
                    val newPos = ChunkPos(pos.x + i, pos.z + j)

                    if (temp.containsKey(newPos)) {
                        val oldRadiation = worldRadiation[newPos] ?: 0F
                        val newRadiation = max(oldRadiation + radiation * percent * .99F - .05F, 0F)
                        worldRadiation[newPos] = newRadiation
                    } else worldRadiation[newPos] = radiation * percent

                    // TODO fog
                }
            }
        }
    }

    private fun irradiateEnvironment() {
        for ((world, radiationMap) in perWorldRadiation) {
            if (world !is ServerLevel) continue
            if (radiationMap.isEmpty()) continue

            for (chunk in 0..4) {
                val (pos, radiation) = radiationMap.entries.random()
                val chunkProvider = world.chunkSource

                if (radiation < NuclearConfig.radiation.worldRadiationThreshold.get() || !chunkProvider.hasChunk(pos.x, pos.z)) continue

                for (i in 0..4) for (a in 0..15) for (b in 0..15) {
                    if (world.random.nextInt(5) != 0) continue

                    val x = pos.minBlockX + a
                    val z = pos.minBlockZ + b
                    val y = world.getHeight(Heightmap.Types.WORLD_SURFACE, x, z) - world.random.nextInt(3)
                    val blockPos = BlockPos(x, y, z)
                    val block = world.getBlockState(blockPos)

                    // TODO config
                    when {
                        block.`is`(BlockTags.LEAVES) -> world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState())
                        block.`is`(Blocks.GRASS_BLOCK) || block.`is`(Blocks.PODZOL) -> world.setBlockAndUpdate(blockPos, NTechBlocks.deadGrass.get().defaultBlockState())
                        block.block is IPlantable || block.block is BonemealableBlock -> world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState())
                    }
                }
            }
        }
    }
}
