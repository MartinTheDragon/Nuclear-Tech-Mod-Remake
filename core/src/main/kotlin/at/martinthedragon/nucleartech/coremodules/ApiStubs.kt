package at.martinthedragon.nucleartech.coremodules

import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.ICapabilityProvider
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionResult
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.CreativeModeTab
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.UseOnContext
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.crafting.RecipeType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.enchantment.Enchantment
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

interface ApiStubs {
    fun Item.useOnStub(context: UseOnContext): InteractionResult
    fun Item.canBeDepletedStub(): Boolean
    fun Item.isBarVisibleStub(itemStack: ItemStack): Boolean
    fun Item.getBarColorStub(itemStack: ItemStack): Int
    fun Item.fillItemCategoryStub(tab: CreativeModeTab, items: MutableList<ItemStack>)
    fun Item.allowedInStub(tab: CreativeModeTab): Boolean
    fun Item.appendHoverTextStub(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean)
    fun Item.getDamageStub(itemStack: ItemStack): Int
    fun Item.getMaxDamageStub(itemStack: ItemStack): Int
    fun Item.initCapabilitiesStub(itemStack: ItemStack, nbt: CompoundTag?): ICapabilityProvider?
    fun Item.getBurnTimeStub(itemStack: ItemStack, recipeType: RecipeType<*>?): Int
    fun Item.canApplyAtEnchantingTableStub(itemStack: ItemStack, enchantment: Enchantment): Boolean
}
