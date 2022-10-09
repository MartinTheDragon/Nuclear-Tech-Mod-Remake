package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.entity.transmitters.FluidPipeBlockEntity
import at.martinthedragon.nucleartech.extensions.getAverageColor
import com.mojang.logging.LogUtils
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import net.minecraft.client.Minecraft
import net.minecraft.client.color.block.BlockTintCache
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.ColorResolver
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import java.util.function.ToIntFunction

@Mod.EventBusSubscriber(modid = NuclearTech.MODID, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.FORGE)
object BlockTints {
    private val LOGGER = LogUtils.getLogger()

    val FLUID_DUCT_COLOR_RESOLVER = ColorResolver { _, _, _ -> -1 }

    // TODO might have issues with mods that try to render a different dimension to the world
    private val fluidDuctTintCache = NonThreadLocalBlockTintCache sourceFunc@{
        val level = Minecraft.getInstance().level ?: return@sourceFunc -1
        val blockEntity = level.getBlockEntity(it) as? FluidPipeBlockEntity ?: return@sourceFunc -1
        val texture = blockEntity.fluid.attributes.stillTexture ?: return@sourceFunc -1
        try {
            val sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture)
            sprite.getAverageColor(0, 0, 0, 15, 15) and 0xFFFFFF
        } catch (ex: Exception) {
            LOGGER.error("Couldn't sample fluid texture $texture for tinting fluid duct at $it", ex)
            -1
        }
    }

    // because mojang made every tint cache have a copy on each thread that never gets invalidated...
    // TODO make a custom implementation that still caches thread locally but invalidates correctly
    private class NonThreadLocalBlockTintCache(source: ToIntFunction<BlockPos>) : BlockTintCache(source) {
        override fun getColor(pos: BlockPos): Int {
            val i = SectionPos.blockToSectionCoord(pos.x)
            val j = SectionPos.blockToSectionCoord(pos.z)
            val values = findOrCreateChunkCache(i, j).getLayer(pos.y)
            val k = pos.x and 15
            val l = pos.z and 15
            val i1 = l shl 4 or k
            val j1 = values[i1]
            return if (j1 != -1) {
                j1
            } else {
                val k1 = source.applyAsInt(pos)
                values[i1] = k1
                k1
            }
        }
    }

    @JvmStatic @SubscribeEvent
    fun injectTintCaches(event: WorldEvent.Load) {
        val level = event.world
        if (level is ClientLevel) {
            level.tintCaches = Object2ObjectArrayMap(level.tintCaches.toMutableMap().apply {
                put(FLUID_DUCT_COLOR_RESOLVER, fluidDuctTintCache)
            })
        }
    }

    fun invalidate(level: ClientLevel, pos: BlockPos) {
        for (cache in level.tintCaches.values) {
            cache.invalidateForChunk(SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z))
        }
    }

    fun invalidate(level: ClientLevel, colorResolver: ColorResolver, pos: BlockPos) {
        level.tintCaches[colorResolver]?.invalidateForChunk(SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z))
    }
}
