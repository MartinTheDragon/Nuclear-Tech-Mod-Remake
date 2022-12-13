package at.martinthedragon.nucleartech.energy

import at.martinthedragon.nucleartech.math.ResourceUnit

private const val HE_TO_FE_RATIO = 4.0

@Suppress("EnumEntryName")
enum class EnergyUnit(override val ratio: Double, override val leniency: Int, override val decimals: Int, override val unitPrefix: String, override val unitType: UnitType) : ResourceUnit<EnergyUnit, EnergyUnit.UnitType> {
    FE(ResourceUnit.ONE, 1, 0, ResourceUnit.PREFIX_ONE, UnitType.FORGE),
    kFE(ResourceUnit.KILO, 1, 2, ResourceUnit.PREFIX_KILO, UnitType.FORGE),
    MFE(ResourceUnit.MEGA, 1, 2, ResourceUnit.PREFIX_MEGA, UnitType.FORGE),
    GFE(ResourceUnit.GIGA, 1, 2, ResourceUnit.PREFIX_GIGA, UnitType.FORGE),
    TFE(ResourceUnit.TERA, 1, 2, ResourceUnit.PREFIX_TERA, UnitType.FORGE),
    PFE(ResourceUnit.PETA, 1, 2, ResourceUnit.PREFIX_PETA, UnitType.FORGE),
    EFE(ResourceUnit.EXA, 1, 2, ResourceUnit.PREFIX_EXA, UnitType.FORGE),

    HE(ResourceUnit.ONE * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_ONE, UnitType.HBM),
    kHE(ResourceUnit.KILO * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_KILO, UnitType.HBM),
    MHE(ResourceUnit.MEGA * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_MEGA, UnitType.HBM),
    GHE(ResourceUnit.GIGA * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_GIGA, UnitType.HBM),
    THE(ResourceUnit.TERA * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_TERA, UnitType.HBM),
    PHE(ResourceUnit.PETA * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_PETA, UnitType.HBM),
    EHE(ResourceUnit.EXA * HE_TO_FE_RATIO, 1, 2, ResourceUnit.PREFIX_EXA, UnitType.HBM),
    ;

    enum class UnitType(override val unitString: String, override val defaultGetter: () -> EnergyUnit) : ResourceUnit.UnitType<EnergyUnit, UnitType> {
        FORGE("FE", { FE }),
        HBM("HE", { HE }),
    }
}
