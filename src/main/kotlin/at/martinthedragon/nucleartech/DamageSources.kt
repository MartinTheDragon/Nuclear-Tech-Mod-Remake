package at.martinthedragon.nucleartech

import net.minecraft.util.DamageSource

object DamageSources {
    val radiation: DamageSource = DamageSource("${NuclearTech.MODID}.radiation").bypassArmor()
    val nuclearBlast: DamageSource = DamageSource("${NuclearTech.MODID}.nuclearBlast").setExplosion()
}
