package at.martinthedragon.ntm

import net.minecraft.util.DamageSource

object DamageSources {
    val radiation: DamageSource = DamageSource("radiation").bypassArmor()
}
