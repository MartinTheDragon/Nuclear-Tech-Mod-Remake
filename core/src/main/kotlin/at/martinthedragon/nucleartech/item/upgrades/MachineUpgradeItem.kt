package at.martinthedragon.nucleartech.item.upgrades

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.coremodules.minecraft.client.Minecraft
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level

class MachineUpgradeItem(properties: Properties, val effect: UpgradeEffect<UpgradeableMachine>) : Item(properties.stacksTo(1)) {
    /**
     * An interface that uses unsafe variance trickery to bypass type checks for simpler implementations.
     *
     * Check [isCompatibleWith] first before calling [apply], otherwise a [ClassCastException] will be thrown!
     */
    interface UpgradeEffect<out M : UpgradeableMachine> {
        /**
         * Returns a formatted name of the upgrade for display in tooltips.
         */
        fun getName(): Component

        /**
         * Represents the tier of this upgrade.
         *
         * Upgrades with only one tier should return `0`.
         */
        val tier: Int

        /**
         * Checks if [apply] can be called with the supplied [machine].
         *
         * Implementation: Perform an `is` check on the [machine] with the actual type of [M].
         */
        fun isCompatibleWith(machine: UpgradeableMachine): Boolean

        /**
         * Applies this effect on the [machine].
         *
         * Bypasses laws of type variance using [UnsafeVariance] to make implementations and uses simpler.
         * Calling this is unsafe and will cause a [ClassCastException] if [isCompatibleWith] returns `false`.
         */
        fun apply(machine: @UnsafeVariance M) // in the future, we may be able to use a type-safe non-hacky way using compiler contracts
    }

    override fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        if (level == null || !level.isClientSide) return

        // retrieve the BlockEntity we're currently viewing (only works with our custom menu implementation)
        val screen = Minecraft.screen

        if (screen is InventoryScreen || screen is CreativeModeInventoryScreen) {
            tooltip += LangKeys.UPGRADE_WARN_VIEW_MACHINE.yellow()
            return
        }

        if (screen !is AbstractContainerScreen<*>) {
            tooltip += LangKeys.UPGRADE_WARN_NOT_A_MACHINE.red()
            return
        }

        val menu = screen.menu

        if (menu !is NTechContainerMenu<*>) {
            tooltip += LangKeys.UPGRADE_WARN_UNKNOWN_COMPATIBILITY.darkGray()
            return
        }

        val blockEntity = menu.blockEntity

        if (blockEntity !is UpgradeableMachine) {
            tooltip += LangKeys.UPGRADE_WARN_MACHINE_NOT_UPGRADEABLE.red()
            return
        }

        if (effect.isCompatibleWith(blockEntity)) {
            // now actually display the upgrade info
            if (blockEntity is Nameable) {
                tooltip += blockEntity.displayName.plainCopy().append(":").green()
            }
            tooltip += blockEntity.getUpgradeInfoForEffect(effect).map(MutableComponent::gray)
        } else {
            tooltip += LangKeys.UPGRADE_WARN_INCOMPATIBLE.red()
        }
    }

    companion object {
        fun isValidForBE(machine: BlockEntity, stack: ItemStack): Boolean {
            if (machine !is UpgradeableMachine) return false
            return isValidFor(machine as UpgradeableMachine, stack)
        }

        fun isValidFor(machine: UpgradeableMachine, stack: ItemStack): Boolean {
            val item = stack.item
            if (item !is MachineUpgradeItem) return false
            return item.effect.isCompatibleWith(machine)
        }

        fun applyUpgrades(machine: UpgradeableMachine, items: List<ItemStack>) {
            machine.resetUpgrades()
            for (item in items.map(ItemStack::getItem)) {
                if (item is MachineUpgradeItem) {
                    val effect = item.effect
                    if (effect.isCompatibleWith(machine))
                        effect.apply(machine)
                }
            }
        }
    }
}
