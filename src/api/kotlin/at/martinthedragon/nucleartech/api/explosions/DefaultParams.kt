package at.martinthedragon.nucleartech.api.explosions

public data class NuclearExplosionMk4Params(
    val hasFallout: Boolean = true,
    val extraFallout: Int = 0,
    val muted: Boolean = false,
    val withVfx: Boolean = true,
) : ExplosionFactory.ExplosionParams

public data class ExplosionLargeParams(
    val fire: Boolean = false,
    val cloud: Boolean = false,
    val rubble: Boolean = false,
    val shrapnel: Boolean = false,
) : ExplosionFactory.ExplosionParams
