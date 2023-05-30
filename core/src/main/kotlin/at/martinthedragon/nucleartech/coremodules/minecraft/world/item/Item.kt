package at.martinthedragon.nucleartech.coremodules.minecraft.world.item

import at.martinthedragon.nucleartech.coremodules.STUBS
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.ICapabilityProvider
import at.martinthedragon.nucleartech.coremodules.forge.registries.IForgeRegistryEntry
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionResult
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.UseOnContext
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.crafting.RecipeType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.enchantment.Enchantment
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.sorcerer.Linkage
import at.martinthedragon.nucleartech.sorcerer.Linkage.CompatibleVersions.MC12
import at.martinthedragon.nucleartech.sorcerer.Linkage.CompatibleVersions.MC18
import at.martinthedragon.nucleartech.sorcerer.registries.RegistryCandidate

@Linkage(MC12, "net.minecraft.item.Item")
@Linkage(MC18, "net.minecraft.world.item.Item")
open class Item(val properties: Properties) : IForgeRegistryEntry, RegistryCandidate<Item> {
    @get:[JvmSynthetic JvmName("registryName_")]
    var registryName: ResourceLocation? = null

    override fun getRegistryName() = registryName

    @Linkage.Link(MC18, "%i|>%e")
    open fun useOn(context: UseOnContext): InteractionResult = with(STUBS) { useOnStub(context) }

    @Linkage.Link(MC18, ">%%")
    open fun canBeDepleted(): Boolean = with(STUBS) { canBeDepletedStub() }

    @Linkage.Link(MC18, "%i|>%%")
    open fun isBarVisible(itemStack: ItemStack): Boolean = with(STUBS) { isBarVisibleStub(itemStack) }

    @Linkage.Link(MC18, "%i|>%%")
    open fun getBarColor(itemStack: ItemStack): Int = with(STUBS) { getBarColorStub(itemStack) }

    @Linkage.Link(MC18, "%i|%iL")
    open fun fillItemCategory(tab: CreativeModeTab, items: MutableList<ItemStack>) = with(STUBS) { fillItemCategoryStub(tab, items) }

    @Linkage.Link(MC18, "%i|>%%")
    protected open fun allowedIn(tab: CreativeModeTab): Boolean = with(STUBS) { allowedInStub(tab) }

    @Linkage.Link(MC12, "%i|%?|%iL.formattedText|%%.isAdvanced", "addInformation")
    @Linkage.Link(MC18, "%i|%?|%iL|%%.isAdvanced")
    open fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean) = with(STUBS) { appendHoverTextStub(itemStack, level, tooltip, extended) }

    @Linkage.Link(MC18, "%i|>%%")
    open fun getDamage(itemStack: ItemStack): Int = with(STUBS) { getDamageStub(itemStack) }

    @Linkage.Link(MC18, "%i|>%%")
    open fun getMaxDamage(itemStack: ItemStack): Int = with(STUBS) { getMaxDamageStub(itemStack) }

    @Linkage.Link(MC18, "%i|%?|>%i")
    open fun initCapabilities(itemStack: ItemStack, nbt: CompoundTag?): ICapabilityProvider? = with(STUBS) { initCapabilitiesStub(itemStack, nbt) }

    @Linkage.Link(MC18, "%i|%?|>%%")
    open fun getBurnTime(itemStack: ItemStack, recipeType: RecipeType<*>?): Int = with(STUBS) { getBurnTimeStub(itemStack, recipeType) }

    @Linkage.Link(MC18, "%i|%i|>%%")
    open fun canApplyAtEnchantingTable(itemStack: ItemStack, enchantment: Enchantment): Boolean = with(STUBS) { canApplyAtEnchantingTableStub(itemStack, enchantment) }

    data class Properties(
        private val stacksTo: Int = 64,
        val maxDamage: Int = 0,
        val tab: CreativeModeTab? = null,
        val rarity: Rarity = Rarity.COMMON,
        val canRepair: Boolean = true,
    ) {
        val maxStackSize get() = if (maxDamage != 0) 1 else stacksTo
    }
}
