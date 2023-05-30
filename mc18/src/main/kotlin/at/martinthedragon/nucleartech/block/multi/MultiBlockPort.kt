package at.martinthedragon.nucleartech.block.multi

import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.IODelegatedBlockEntity
import at.martinthedragon.nucleartech.capability.CapabilityCache
import at.martinthedragon.nucleartech.capability.WorldlyCapabilityProvider
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import java.util.*
import java.util.function.Supplier

open class MultiBlockPort(
    blockEntityGetter: (pos: BlockPos, state: BlockState) -> MultiBlockPortBlockEntity,
    private val blockEntityType: Supplier<out BlockEntityType<out MultiBlockPortBlockEntity>>
) : MultiBlockPart(blockEntityGetter) {
    constructor() : this(::GenericMultiBlockPortBlockEntity, BlockEntityTypes.genericMultiBlockPortBlockEntityType)

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, blockEntityType.get())

    open class MultiBlockPortBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : MultiBlockPartBlockEntity(type, pos, state), TickingServerBlockEntity {
        private val capabilityCache = CapabilityCache()
        private val otherCapabilityCaches = EnumMap<_, CapabilityCache>(Direction::class.java)

        override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
            val coreEntity = level.getBlockEntity(core) ?: return
            if (coreEntity !is IODelegatedBlockEntity) return

            val configTable = coreEntity.ioConfigurations
            val row = configTable.row(pos) ?: return

            for ((side, configs) in row) {
                val otherBlockEntity = level.getBlockEntity(pos.relative(side)) ?: continue
                if (coreEntity === otherBlockEntity) continue
                for (config in configs) {
                    if (config.mode.isNone()) continue
                    config.action.tickIO(coreEntity, otherBlockEntity, side, config.mode, capabilityCache, otherCapabilityCaches.getOrPut(side, ::CapabilityCache))
                }
            }
        }

        override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            if (!isRemoved && hasLevel() && side != null) {
                val coreEntity = level!!.getBlockEntity(core)
                if (coreEntity != null) {
                    return if (coreEntity is WorldlyCapabilityProvider)
                        coreEntity.getCapability(cap, blockPos, side).cast()
                    else
                        coreEntity.getCapability(cap, side).cast()
                }
            }

            return super.getCapability(cap, side)
        }
    }

    class GenericMultiBlockPortBlockEntity(pos: BlockPos, state: BlockState) : MultiBlockPortBlockEntity(BlockEntityTypes.genericMultiBlockPortBlockEntityType.get(), pos, state)
}
