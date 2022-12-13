package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.math.ResourceUnit

enum class FluidUnit(override val ratio: Double, override val leniency: Int, override val decimals: Int, override val unitPrefix: String, override val unitType: UnitType) : ResourceUnit<FluidUnit, FluidUnit.UnitType> {
    MB(ResourceUnit.ONE, 1, 0, ResourceUnit.PREFIX_MILLI, UnitType.MINECRAFT), // milli buckets
    B(ResourceUnit.KILO, 1000, 1, ResourceUnit.PREFIX_ONE, UnitType.MINECRAFT), // buckets
    ;

    enum class UnitType(override val unitString: String, override val defaultGetter: () -> FluidUnit): ResourceUnit.UnitType<FluidUnit, UnitType> {
        MINECRAFT("B", { MB })
    }
}
