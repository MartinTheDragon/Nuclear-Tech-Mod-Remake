package at.martinthedragon.nucleartech.api.explosion

public object EmptyParams : ExplosionFactory.ExplosionParams

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

public data class SmallNukeExplosionParams(
    val damageRadius: Int = 45,
    val radiationModifier: Float = 1F,
    val shrapnel: Boolean = true,
    val actualExplosion: ExplosionFactory<EmptyParams>? = null
) : ExplosionFactory.ExplosionParams
