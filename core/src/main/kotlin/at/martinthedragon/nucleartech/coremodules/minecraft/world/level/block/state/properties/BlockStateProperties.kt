package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties

import at.martinthedragon.nucleartech.coremodules.InjectionStatic

interface BlockStateProperties {
    val HORIZONTAL_FACING: DirectionProperty

    companion object {
        val HORIZONTAL_FACING get() = InjectionStatic.blockStateProperties.HORIZONTAL_FACING
    }
}
