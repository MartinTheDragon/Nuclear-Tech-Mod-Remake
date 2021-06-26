package at.martinthedragon.nucleartech

import net.minecraft.util.DamageSource

object DamageSources {
    val radiation: DamageSource = DamageSource("radiation").bypassArmor()
}
