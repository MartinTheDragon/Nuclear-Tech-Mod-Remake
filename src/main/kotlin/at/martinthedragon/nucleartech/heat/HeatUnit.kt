package at.martinthedragon.nucleartech.heat

import at.martinthedragon.nucleartech.math.ResourceUnit

@Suppress("EnumEntryName")
enum class HeatUnit(override val ratio: Double, override val leniency: Int, override val decimals: Int, override val unitPrefix: String, override val unitType: UnitType) : ResourceUnit<HeatUnit, HeatUnit.UnitType> {
    TU(ResourceUnit.ONE, 1, 0, ResourceUnit.PREFIX_ONE, UnitType.HBM),
    kTU(ResourceUnit.KILO, 1, 2, ResourceUnit.PREFIX_KILO, UnitType.HBM),
    MTU(ResourceUnit.MEGA, 1, 2, ResourceUnit.PREFIX_MEGA, UnitType.HBM),
    GTU(ResourceUnit.GIGA, 1, 2, ResourceUnit.PREFIX_GIGA, UnitType.HBM),
    TTU(ResourceUnit.TERA, 1, 2, ResourceUnit.PREFIX_TERA, UnitType.HBM),
    PTU(ResourceUnit.PETA, 1, 2, ResourceUnit.PREFIX_PETA, UnitType.HBM),
    ETU(ResourceUnit.EXA, 1, 2, ResourceUnit.PREFIX_EXA, UnitType.HBM),
    ;
    enum class UnitType(override val unitString: String, override val defaultGetter: () -> HeatUnit) : ResourceUnit.UnitType<HeatUnit, UnitType> {
        HBM("TU", { TU }),
    }
}
