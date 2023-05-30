package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.capability.CapabilityCache
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.extensions.component1
import at.martinthedragon.nucleartech.extensions.component2
import at.martinthedragon.nucleartech.extensions.component3
import at.martinthedragon.nucleartech.extensions.ifPresentInline
import at.martinthedragon.nucleartech.item.transferItemsBetweenItemHandlers
import com.google.common.collect.HashBasedTable
import com.google.common.collect.ImmutableTable
import com.google.common.collect.Table
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.items.CapabilityItemHandler

interface IODelegatedBlockEntity {
    val ioConfigurations: Table<BlockPos, Direction, List<IOConfiguration>>

    companion object {
        fun fromRelativeTable(pos: BlockPos, rotation: Rotation, table: Table<BlockPos, Direction, List<IOConfiguration>>): ImmutableTable<BlockPos, Direction, List<IOConfiguration>> {
            return ImmutableTable.copyOf(HashBasedTable.create<BlockPos, Direction, List<IOConfiguration>>(table.rowKeySet().size, table.columnKeySet().size).apply {
                for ((relativePos, relativeDirection, configs) in table.cellSet()) {
                    put(pos.offset(relativePos.rotate(rotation)), rotation.rotate(relativeDirection), configs)
                }
            })
        }

        fun fromTriples(pos: BlockPos, rotation: Rotation, vararg triples: Triple<BlockPos, Direction, List<IOConfiguration>>): ImmutableTable<BlockPos, Direction, List<IOConfiguration>> {
            return ImmutableTable.copyOf(HashBasedTable.create<BlockPos, Direction, List<IOConfiguration>>(triples.size, triples.size).apply {
                for ((relativePos, relativeDirection, configs) in triples) {
                    put(pos.offset(relativePos.rotate(rotation)), rotation.rotate(relativeDirection), configs)
                }
            })
        }

        val DEFAULT_ITEM_ACTION = IOConfiguration.IOAction { self: BlockEntity, other: BlockEntity, side: Direction, mode: IOConfiguration.Mode, selfCache: CapabilityCache, otherCache: CapabilityCache  ->
            selfCache.getOrAddToCache(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side, self::getCapability).ifPresentInline { selfHandler ->
                otherCache.getOrAddToCache(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.opposite, other::getCapability).ifPresentInline { otherHandler ->
                    if (mode.isInput()) {
                        transferItemsBetweenItemHandlers(otherHandler, selfHandler, 64)
                    } else if (mode.isOutput()) {
                        transferItemsBetweenItemHandlers(selfHandler, otherHandler, 64)
                    }
                }
            }
        }

        val DEFAULT_ENERGY_ACTION = IOConfiguration.IOAction { self: BlockEntity, other: BlockEntity, side: Direction, mode: IOConfiguration.Mode, selfCache: CapabilityCache, otherCache: CapabilityCache ->
            selfCache.getOrAddToCache(CapabilityEnergy.ENERGY, side, self::getCapability).ifPresentInline { selfHandler ->
                otherCache.getOrAddToCache(CapabilityEnergy.ENERGY, side.opposite, other::getCapability).ifPresentInline { otherHandler ->
                    if (mode.isInput()) {
                        transferEnergy(otherHandler, selfHandler)
                    } else if (mode.isOutput()) {
                        transferEnergy(selfHandler, otherHandler)
                    }
                    Unit
                }
            }
        }

        val DEFAULT_FLUID_ACTION = IOConfiguration.IOAction { self: BlockEntity, other: BlockEntity, side: Direction, mode: IOConfiguration.Mode, selfCache: CapabilityCache, otherCache: CapabilityCache ->
            selfCache.getOrAddToCache(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side, self::getCapability).ifPresentInline { selfHandler ->
                otherCache.getOrAddToCache(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.opposite, other::getCapability).ifPresentInline { otherHandler ->
                    // TODO
                }
            }
        }
    }
}

data class IOConfiguration(var mode: Mode, val action: IOAction<BlockEntity>) {
    enum class Mode(private val serializedName: String) : StringRepresentable {
        NONE("none"),
        IN("in"),
        OUT("out"),
        BOTH("both");

        fun isNone() = this == NONE
        fun isNotNone() = this != NONE
        fun isInput() = this == IN || this == BOTH
        fun isOutput() = this == OUT || this == BOTH

        override fun getSerializedName() = serializedName
    }

    fun interface IOAction<in T : BlockEntity> {
        fun tickIO(self: T, other: BlockEntity, side: Direction, mode: Mode, selfCache: CapabilityCache, otherCache: CapabilityCache)
    }
}
