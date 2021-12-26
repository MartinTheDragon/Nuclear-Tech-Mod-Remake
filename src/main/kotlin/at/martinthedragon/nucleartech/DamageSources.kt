package at.martinthedragon.nucleartech

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import kotlin.random.Random

object DamageSources {
    val radiation: DamageSource = DamageSource("${NuclearTech.MODID}.radiation").bypassArmor()
    val nuclearBlast: DamageSource = DamageSource("${NuclearTech.MODID}.nuclearBlast").setExplosion()
    val murkyAnvil: DamageSource = MurkyAnvilDamageSource("${NuclearTech.MODID}.murkyAnvil")
}

private class MurkyAnvilDamageSource(msgID: String) : DamageSource(msgID) {
    override fun getLocalizedDeathMessage(entity: LivingEntity): Component {
        val message = super.getLocalizedDeathMessage(entity).string
        val obfuscatedMessage = StringBuilder()
        var currentlyObfuscating = false
        for (i in message.indices) {
            if (!currentlyObfuscating && Random.nextInt(3) == 0) {
                obfuscatedMessage.append("§k")
                currentlyObfuscating = true
            }
            obfuscatedMessage.append(message[i])
            if (currentlyObfuscating && Random.nextInt(2) == 0) {
                obfuscatedMessage.append("§r")
                currentlyObfuscating = false
            }
        }
        obfuscatedMessage.append("§r")
        return TextComponent(obfuscatedMessage.toString())
    }
}
