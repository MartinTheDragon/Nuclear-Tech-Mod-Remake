package at.martinthedragon.nucleartech.world

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import kotlin.random.Random

object DamageSources {
    val extractBlood: DamageSource = DamageSource(name("extractBlood")).bypassArmor().bypassMagic().setNoAggro()
    val meteorite: DamageSource = DamageSource(name("meteorite")).bypassMagic().bypassArmor().setNoAggro()
    val murkyAnvil: DamageSource = MurkyAnvilDamageSource(name("murkyAnvil"))
    val nuclearBlast: DamageSource = DamageSource(name("nuclearBlast")).setExplosion()
    val radiation: DamageSource = DamageSource(name("radiation")).bypassArmor()
    val shrapnel: DamageSource = DamageSource(name("shrapnel")).setProjectile()

    private fun name(string: String) = "${NuclearTech.MODID}.$string"
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
