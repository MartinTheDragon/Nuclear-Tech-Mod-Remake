package at.martinthedragon.nucleartech

import net.minecraft.world.damagesource.DamageSource

object DamageSources {
    val radiation: DamageSource = DamageSource("${NuclearTech.MODID}.radiation").bypassArmor()
    val nuclearBlast: DamageSource = DamageSource("${NuclearTech.MODID}.nuclearBlast").setExplosion()
}
