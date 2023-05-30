package at.martinthedragon.nucleartech.world

import com.mojang.logging.LogUtils
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.ChunkPos
import net.minecraftforge.common.world.ForgeChunkManager
import net.minecraftforge.common.world.ForgeChunkManager.LoadingValidationCallback

object ChunkLoadingValidationCallback : LoadingValidationCallback {
    private val LOGGER = LogUtils.getLogger()

    override fun validateTickets(level: ServerLevel, ticketHelper: ForgeChunkManager.TicketHelper) {
        val levelName = level.dimension().location()
        LOGGER.debug("Validating tickets in '$levelName' - ${ticketHelper.blockTickets.size} Blocks - ${ticketHelper.entityTickets.size} Entities")
        for ((uuid, tickets) in ticketHelper.entityTickets) {
            LOGGER.debug("Considering entity '$uuid' - ${tickets.first.size} Forced chunks - ${tickets.second.size} Ticking forced chunks")
            if (tickets.first.isEmpty() || tickets.second.isEmpty()) LOGGER.debug("No tickets found")
            val entity = level.getEntity(uuid)
            if (entity == null) {
                LOGGER.warn("Entity with UUID '$uuid' stored tickets, but doesn't exist anymore. Removing tickets...")
                ticketHelper.removeAllTickets(uuid)
                continue
            }
            if (entity !is ChunkLoader) {
                LOGGER.warn("Entity with UUID '$uuid' of class ${entity::class.qualifiedName} does not implement '${ChunkLoader::class.simpleName}' but has chunk force tickets. Removing tickets...")
                ticketHelper.removeAllTickets(uuid)
                continue
            }

            val entityForcedChunks = entity.forcedChunks.map(ChunkPos::toLong).toSet()
            if (!tickets.first.containsAll(entityForcedChunks)) {
                LOGGER.warn("Entity with UUID '$uuid' of class ${entity::class.qualifiedName} appears to have unregistered tickets in its ticket store. Correcting...")
                entity.unForceChunks()
                entity.forceChunks(tickets.first.map(::ChunkPos).toSet())
            }
            val entityForcedTickingChunks = entity.tickingForcedChunks.map(ChunkPos::toLong).toSet()
            if (!tickets.second.containsAll(entityForcedTickingChunks)) {
                LOGGER.warn("Entity with UUID '$uuid' of class ${entity::class.qualifiedName} appears to have unregistered ticking tickets in its ticket store. Correcting...")
                entity.unForceTickingChunks()
                entity.forceTickingChunks(tickets.second.map(::ChunkPos).toSet())
            }
        }
    }
}
