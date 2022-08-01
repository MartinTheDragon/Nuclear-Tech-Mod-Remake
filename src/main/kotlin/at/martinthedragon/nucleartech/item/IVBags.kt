package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.world.DamageSources
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.capability.contamination.addEffect
import at.martinthedragon.nucleartech.capability.contamination.effect.RadiationEffect
import at.martinthedragon.nucleartech.config.NuclearConfig
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import java.util.function.Supplier
import net.minecraft.sounds.SoundEvents as VanillaSoundEvents

abstract class IVBagItem(properties: Properties) : Item(properties) {
    protected abstract val bagUseSound: Supplier<out SoundEvent>
    protected abstract val cooldownItems: Set<Supplier<out Item>>
    protected abstract val cooldown: Int

    final override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        val result = doBagUseAction(level, player, stack.copy(), hand, stack.count == 1)
        if (result.result.consumesAction()) {
            player.playSound(bagUseSound.get(), 1F, 1F)
            if (cooldown > 0) cooldownItems.forEach { player.cooldowns.addCooldown(it.get(), cooldown) }
        }
        return result
    }

    protected abstract fun doBagUseAction(level: Level, player: Player, originalStack: ItemStack, hand: InteractionHand, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack>

    // A bit of a mess, but does everything correctly
    protected fun addOrDropItem(level: Level, player: Player, originalStack: ItemStack, newStack: ItemStack, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack> =
        if (!player.abilities.instabuild && originalStackEmptied) InteractionResultHolder.sidedSuccess(newStack, level.isClientSide) else {
            if (!level.isClientSide && !player.addItem(newStack.copy())) player.drop(newStack, false)
            InteractionResultHolder.sidedSuccess(originalStack.copy().apply { if (!player.abilities.instabuild) shrink(1) }, level.isClientSide)
        }
}

class EmptyIVBagItem(properties: Properties) : IVBagItem(properties) {
    override val bagUseSound = SoundEvents.inject
    override val cooldownItems = setOf(NTechItems.ivBag)
    override val cooldown: Int get() = NuclearConfig.general.emptyIVBagCooldown.get()

    override fun doBagUseAction(level: Level, player: Player, originalStack: ItemStack, hand: InteractionHand, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack> {
        if (!player.abilities.instabuild) {
            if (player.health > 5F) {
                if (!player.hurt(DamageSources.extractBlood, 5F) && !level.isClientSide) return InteractionResultHolder.fail(originalStack)
            } else {
                player.hurt(DamageSources.extractBlood, 5F)
                return InteractionResultHolder.fail(originalStack)
            }
        }
        if (player.isDeadOrDying) return InteractionResultHolder.fail(originalStack)
        return addOrDropItem(level, player, originalStack, ItemStack(NTechItems.bloodBag.get()), originalStackEmptied)
    }
}

class BloodBagItem(properties: Properties) : IVBagItem(properties) {
    override val bagUseSound = SoundEvents.emptyIVBag
    override val cooldownItems = setOf(NTechItems.bloodBag)
    override val cooldown: Int get() = NuclearConfig.general.bloodBagCooldown.get()

    override fun doBagUseAction(level: Level, player: Player, originalStack: ItemStack, hand: InteractionHand, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack> {
        player.heal(5F)
        return addOrDropItem(level, player, originalStack, ItemStack(NTechItems.ivBag.get()), originalStackEmptied)
    }
}

class EmptyExperienceBagItem(properties: Properties) : IVBagItem(properties) {
    override val bagUseSound = SoundEvents.inject
    override val cooldownItems = setOf(NTechItems.emptyExperienceBag)
    override val cooldown: Int get() = NuclearConfig.general.emptyExperienceBagCooldown.get()

    override fun doBagUseAction(level: Level, player: Player, originalStack: ItemStack, hand: InteractionHand, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack> {
        if (player.totalExperience >= 100) player.giveExperiencePoints(-100) else return InteractionResultHolder.fail(originalStack)
        return addOrDropItem(level, player, originalStack, ItemStack(NTechItems.experienceBag.get()), originalStackEmptied)
    }
}

class ExperienceBagItem(properties: Properties) : IVBagItem(properties) {
    override val bagUseSound = Supplier(VanillaSoundEvents::EXPERIENCE_ORB_PICKUP)
    override val cooldownItems = setOf(NTechItems.experienceBag)
    override val cooldown: Int get() = NuclearConfig.general.experienceBagCooldown.get()

    override fun doBagUseAction(level: Level, player: Player, originalStack: ItemStack, hand: InteractionHand, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack> {
        player.giveExperiencePoints(100)
        return addOrDropItem(level, player, originalStack, ItemStack(NTechItems.emptyExperienceBag.get()), originalStackEmptied)
    }
}

class RadAwayItem(private val amount: Float, private val duration: Int, properties: Properties) : IVBagItem(properties) {
    override val bagUseSound = SoundEvents.emptyIVBag
    override val cooldownItems = setOf(NTechItems.radAway, NTechItems.strongRadAway, NTechItems.eliteRadAway)
    override val cooldown: Int get() = NuclearConfig.general.radAwayCooldown.get()

    override fun doBagUseAction(level: Level, player: Player, originalStack: ItemStack, hand: InteractionHand, originalStackEmptied: Boolean): InteractionResultHolder<ItemStack> {
        if (!level.isClientSide) {
            val capability = Capabilities.getContamination(player) ?: return InteractionResultHolder.fail(originalStack)
            capability.addEffect(RadiationEffect.createWithTotalRads(-amount, duration, true, "radaway"))
        }
        return if (player.abilities.instabuild) InteractionResultHolder.sidedSuccess(originalStack, level.isClientSide)
        else addOrDropItem(level, player, originalStack, ItemStack(NTechItems.ivBag.get()), originalStackEmptied)
    }
}
