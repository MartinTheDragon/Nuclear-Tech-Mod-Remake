package at.martinthedragon.nucleartech.block.multi

import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.extensions.ifPresentInline
import at.martinthedragon.nucleartech.item.transferItemsBetweenItemHandlers
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.items.CapabilityItemHandler
import java.util.function.Supplier

open class MultiBlockPort(
    blockEntityGetter: (pos: BlockPos, state: BlockState) -> MultiBlockPortBlockEntity,
    private val blockEntityType: Supplier<out BlockEntityType<out MultiBlockPortBlockEntity>>
) : MultiBlockPart(blockEntityGetter) {
    constructor() : this(::GenericMultiBlockPortBlockEntity, BlockEntityTypes.genericMultiBlockPortBlockEntityType)

    init { registerDefaultState(stateDefinition.any().setValue(INVENTORY_MODE, PortMode.NONE).setValue(ENERGY_MODE, PortMode.NONE).setValue(FLUID_MODE, PortMode.NONE).setValue(INPUT_SIDE, Direction.UP).setValue(OUTPUT_SIDE, Direction.DOWN).setValue(PUSH_TRANSFER, true)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(INVENTORY_MODE, ENERGY_MODE, FLUID_MODE, INPUT_SIDE, OUTPUT_SIDE, PUSH_TRANSFER)
    }

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide || !state.getValue(PUSH_TRANSFER)) null else createServerTickerChecked(type, blockEntityType.get())

    enum class PortMode(private val serializedName: String) : StringRepresentable {
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

    companion object {
        val INVENTORY_MODE: EnumProperty<PortMode> = EnumProperty.create("inventory_mode", PortMode::class.java)
        val ENERGY_MODE: EnumProperty<PortMode> = EnumProperty.create("energy_mode", PortMode::class.java)
        val FLUID_MODE: EnumProperty<PortMode> = EnumProperty.create("fluid_mode", PortMode::class.java)
        val INPUT_SIDE: DirectionProperty = DirectionProperty.create("input_side")
        val OUTPUT_SIDE: DirectionProperty = DirectionProperty.create("output_side")
        val PUSH_TRANSFER: BooleanProperty = BooleanProperty.create("push_transfer") // TODO make this more variable with each transfer type, maybe defining transfer handlers?
    }

    open class MultiBlockPortBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : MultiBlockPartBlockEntity(type, pos, state), TickingServerBlockEntity {
        override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
            if (!state.getValue(PUSH_TRANSFER)) return

            val coreEntity = level.getBlockEntity(core) ?: return

            val inputSide = state.getValue(INPUT_SIDE)
            val outputSide = state.getValue(OUTPUT_SIDE)

            val inputBlockEntity = level.getBlockEntity(pos.relative(inputSide))
            val outputBlockEntity = level.getBlockEntity(pos.relative(outputSide))

            if (inputBlockEntity != null && inputBlockEntity !is MultiBlockPartBlockEntity && inputBlockEntity !== coreEntity) {
                if (state.getValue(INVENTORY_MODE).isInput()) {
                    coreEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSide).ifPresentInline { core ->
                        inputBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSide.opposite).ifPresentInline { other ->
                            transferItemsBetweenItemHandlers(other, core, 64)
                        }
                    }
                }
                if (state.getValue(ENERGY_MODE).isInput()) {
                    coreEntity.getCapability(CapabilityEnergy.ENERGY, inputSide).ifPresentInline { core ->
                        inputBlockEntity.getCapability(CapabilityEnergy.ENERGY, inputSide.opposite).ifPresentInline { other ->
                            transferEnergy(other, core)
                        }
                    }
                }
                if (state.getValue(FLUID_MODE).isInput()) {
                    coreEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, inputSide).ifPresentInline { core ->
                        inputBlockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, inputSide.opposite).ifPresentInline { other ->
                            // TODO
                        }
                    }
                }
            }

            if (outputBlockEntity != null && outputBlockEntity !is MultiBlockPartBlockEntity && outputBlockEntity !== coreEntity) {
                if (state.getValue(INVENTORY_MODE).isOutput()) {
                    coreEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide).ifPresentInline { core ->
                        outputBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide.opposite).ifPresentInline { other ->
                            transferItemsBetweenItemHandlers(core, other, 64)
                        }
                    }
                }
                if (state.getValue(ENERGY_MODE).isOutput()) {
                    coreEntity.getCapability(CapabilityEnergy.ENERGY, outputSide).ifPresentInline { core ->
                        outputBlockEntity.getCapability(CapabilityEnergy.ENERGY, outputSide.opposite).ifPresentInline { other ->
                            transferEnergy(core, other)
                        }
                    }
                }
                if (state.getValue(FLUID_MODE).isOutput()) {
                    coreEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, outputSide).ifPresentInline { core ->
                        outputBlockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, outputSide.opposite).ifPresentInline { other ->
                            // TODO
                        }
                    }
                }
            }
        }

        override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            if (!isRemoved && hasLevel() && side != null) {
                val blockState = level!!.getBlockState(blockPos)
                val coreEntity = level!!.getBlockEntity(core)
                if (coreEntity != null && (side == blockState.getValue(INPUT_SIDE) || side == blockState.getValue(OUTPUT_SIDE))) {
                    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && blockState.getValue(INVENTORY_MODE).isNotNone() ||
                        cap == CapabilityEnergy.ENERGY && blockState.getValue(ENERGY_MODE).isNotNone() ||
                        cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && blockState.getValue(FLUID_MODE).isNotNone()
                    ) {
                        return coreEntity.getCapability(cap, side).cast()
                    }
                }
            }

            return super.getCapability(cap, side)
        }
    }

    class GenericMultiBlockPortBlockEntity(pos: BlockPos, state: BlockState) : MultiBlockPortBlockEntity(BlockEntityTypes.genericMultiBlockPortBlockEntityType.get(), pos, state)
}
