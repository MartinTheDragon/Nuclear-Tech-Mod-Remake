package at.martinthedragon.nucleartech.coremodules.minecraft.world.item

import at.martinthedragon.nucleartech.coremodules.InjectionFactories
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.sorcerer.Injected
import java.util.function.Consumer

@Injected
interface ItemStack {
    val item: Item
    val isEmpty: Boolean

    val isDamaged: Boolean
    var damageValue: Int

    fun <T> hurtAndBreak(damage: Int, owner: T, onBreak: Consumer<T>) // TODO limit T to LivingEntity

    val descriptionId: String
    val hasCustomHoverName: Boolean

    fun hasTag(): Boolean
    fun getTag(): CompoundTag?
    fun getOrCreateTag(): CompoundTag

    interface Factory {
        fun create(item: Item, count: Int): ItemStack
    }
}

fun ItemStack(item: Item, count: Int = 1) = InjectionFactories.itemStack.create(item, count)
