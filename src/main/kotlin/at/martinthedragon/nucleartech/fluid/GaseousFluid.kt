package at.martinthedragon.nucleartech.fluid

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.fluids.ForgeFlowingFluid

abstract class GaseousFluid(properties: Properties) : ForgeFlowingFluid(properties) {
    override fun canBeReplacedWith(state: FluidState, level: BlockGetter, pos: BlockPos, fluidIn: Fluid, direction: Direction): Boolean {
        return !fluidIn.attributes.isGaseous
    }

    open class Flowing(properties: Properties) : GaseousFluid(properties) {
        init {
            registerDefaultState(stateDefinition.any().setValue(LEVEL, 7))
        }

        override fun createFluidStateDefinition(builder: StateDefinition.Builder<Fluid, FluidState>) {
            super.createFluidStateDefinition(builder)
            builder.add(LEVEL)
        }

        override fun getAmount(state: FluidState): Int = state.getValue(LEVEL)
        override fun isSource(state: FluidState) = false
    }

    open class Source(properties: Properties) : GaseousFluid(properties) {
        override fun getAmount(state: FluidState) = 8
        override fun isSource(state: FluidState) = true
    }
}
