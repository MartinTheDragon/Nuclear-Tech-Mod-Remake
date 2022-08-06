package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.item.AttackHandler
import at.martinthedragon.nucleartech.api.item.DamageHandler
import at.martinthedragon.nucleartech.api.item.FallHandler
import at.martinthedragon.nucleartech.api.item.TickingArmor
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.extensions.*
import at.martinthedragon.nucleartech.ntmTranslationString
import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import kotlin.math.max
import kotlin.math.min

open class FullSetBonusArmorItem(material: ArmorMaterial, slot: EquipmentSlot, val bonus: FullSetBonus, properties: Properties) : ArmorItem(material, slot, properties),
    AttackHandler, DamageHandler, TickingArmor, FallHandler
{
    open fun isEnabled(stack: ItemStack) = true

    override fun appendHoverText(stack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        tooltip.apply {
            val originalCount = tooltip.size
            with(bonus) {
                for (effect in effects) add(TranslatableComponent(effect.descriptionId).aqua().prependIntent())
                for (resistance in resistances)
                    if (resistance.value != 0F) add(LangKeys.ARMOR_DAMAGE_RESISTANCE_MODIFIER.format(I18n.get(ntmTranslationString("armor.resistance.${resistance.key.msgId}"))).yellow().prependIntent())
                    else add(LangKeys.ARMOR_NULLIFIES_DAMAGE.format(I18n.get(ntmTranslationString("armor.resistance.${resistance.key.msgId}"))).red().prependIntent())
                if (blastProtection != 1F) add(LangKeys.ARMOR_BLAST_PROTECTION.format(bonus.blastProtection).yellow().prependIntent())
                if (projectileProtection != 1F) add(LangKeys.ARMOR_PROJECTILE_PROTECTION.format(bonus.projectileProtection).yellow().prependIntent())
                if (damageCap != -1F) add(LangKeys.ARMOR_DAMAGE_CAP.format(bonus.damageCap).yellow().prependIntent())
                if (damageMod != 1F) add(LangKeys.ARMOR_DAMAGE_MODIFIER.format(bonus.damageMod).yellow().prependIntent())
                if (damageThreshold > 0) add(LangKeys.ARMOR_DAMAGE_THRESHOLD.format(bonus.damageThreshold).yellow().prependIntent())
                if (fireproof) add(LangKeys.ARMOR_FIREPROOF.red().prependIntent())
                if (geigerSound) add(LangKeys.ARMOR_GEIGER_SOUND.gold().prependIntent())
                if (customGeiger) add(LangKeys.ARMOR_CUSTOM_GEIGER.gold().prependIntent())
                if (vats) add(LangKeys.ARMOR_VATS.red().prependIntent())
                if (thermal) add(LangKeys.ARMOR_THERMAL.red().prependIntent())
                if (hardLanding) add(LangKeys.ARMOR_HARD_LANDING.red().prependIntent())
                if (gravity != 0.0) add(LangKeys.ARMOR_GRAVITY.format(gravity).blue().prependIntent())
                if (dashCount > 0) add(LangKeys.ARMOR_DASH_COUNT.format(dashCount).aqua().prependIntent())
                if (protectionYield != 100F) add(LangKeys.ARMOR_PROTECTION_YIELD.format(protectionYield).blue().prependIntent())
            }
            if (tooltip.size > originalCount) add(originalCount, LangKeys.ARMOR_FULL_SET_BONUS.gold())
        }
    }

    override fun handleAttack(event: LivingAttackEvent, stack: ItemStack) {
        if (getSlot().index != 2) return

        val entity = event.entityLiving
        if (!hasFSB(entity)) return

        with(bonus) {
            if (damageThreshold >= event.amount && !event.source.isBypassInvul) event.isCanceled = true
            if (fireproof && event.source.isFire) {
                entity.clearFire()
                event.isCanceled = true
            }
            val resistance = resistances[event.source]
            if (resistance != null && resistance <= 0) event.isCanceled = true
        }
    }

    override fun handleDamage(event: LivingHurtEvent, stack: ItemStack) {
        if (getSlot().index != 2) return

        val entity = event.entityLiving
        if (event.source.isBypassInvul || !hasFSB(entity)) return

        with(bonus) {
            val source = event.source
            val overflow = max(0F, event.amount - protectionYield)

            event.amount = min(event.amount, protectionYield)
            if (!source.isBypassInvul) event.amount -= damageThreshold
            event.amount *= damageMod
            if (resistances.contains(source)) event.amount *= resistances.getValue(source)
            if (source.isExplosion) event.amount *= blastProtection
            if (source.isProjectile) event.amount *= projectileProtection
            if (damageCap != -1F) event.amount = min(event.amount, damageCap)

            event.amount += overflow
        }
    }

    override fun handleTick(event: LivingEvent.LivingUpdateEvent, stack: ItemStack) {
        if (getSlot().index != 2) return

        val entity = event.entityLiving
        if (!hasFSB(entity)) return

        with(bonus) {
            for (effect in effects) entity.addEffect(MobEffectInstance(effect.effect, effect.duration, effect.amplifier, effect.isAmbient, effect.isVisible, effect.showIcon()))
            if (entity is Player && !entity.abilities.flying && !entity.isInWater) entity.deltaMovement = entity.deltaMovement.add(0.0, -gravity, 0.0)
        }
    }

    override fun handleFall(entity: LivingEntity, distance: Float, multiplier: Float, stack: ItemStack) {
        if (getSlot().index != 2 || !hasFSB(entity)) return

        if (bonus.hardLanding && distance >= 10F) {
            for (surroundingEntity in entity.level.getEntities(entity, entity.boundingBox.inflate(3.0, 0.0, 3.0))) {
                val towardsEntity = Vec3(entity.x - surroundingEntity.x, 0.0, entity.z - surroundingEntity.z)
                val distanceToEntity = towardsEntity.length()
                if (distanceToEntity < 3) { // so it's circular
                    val intensity = 3 - distanceToEntity
                    surroundingEntity.push(towardsEntity.x * intensity * -2, 0.1 * intensity, towardsEntity.z * intensity * -2)
                    surroundingEntity.hurt(if (entity is Player) DamageSource.playerAttack(entity) else DamageSource.mobAttack(entity), intensity.toFloat() * 10F) // TODO custom damage source?
                }
            }
        }

        if (bonus.fallSound != null) entity.playSound(bonus.fallSound, 1F, 1F)
    }

    data class FullSetBonus(
        val blastProtection: Float = 1F,
        val projectileProtection: Float = 1F,
        val damageCap: Float = -1F,
        val damageMod: Float = 1F,
        val damageThreshold: Float = 0F,
        val protectionYield: Float = 100F,
        val fireproof: Boolean = false,
        val noHelmet: Boolean = false,
        val vats: Boolean = false,
        val thermal: Boolean = false,
        val geigerSound: Boolean = false,
        val customGeiger: Boolean = false,
        val hardLanding: Boolean = false,
        val gravity: Double = 0.0,
        val dashCount: Int = 0,
        val stepSound: SoundEvent? = null,
        val jumpSound: SoundEvent? = null,
        val fallSound: SoundEvent? = null,
        val resistances: Map<DamageSource, Float> = emptyMap(),
        val effects: Collection<MobEffectInstance> = emptyList()
    ) {
        companion object {
            val EMPTY = FullSetBonus()

            fun copyFrom(armorFSB: FullSetBonusArmorItem) = armorFSB.bonus.copy()
        }
    }

    companion object {
        fun hasFSB(entity: LivingEntity): Boolean {
            if (!NuclearConfig.general.mobsFullSetBonus.get() && entity !is Player) return false

            val chestplate = entity.armorSlots.find { val item = it.item; item is FullSetBonusArmorItem && item.slot == EquipmentSlot.CHEST }?.item as? FullSetBonusArmorItem ?: return false

            for ((index, armor) in entity.armorSlots.withIndex()) {
                val armorItem = armor.item
                if (armor.isEmpty || armorItem !is FullSetBonusArmorItem) if (index != EquipmentSlot.HEAD.index || !chestplate.bonus.noHelmet) return false else continue
                if (armorItem.material != chestplate.material || !armorItem.isEnabled(armor)) return false
            }

            return true
        }
    }
}
