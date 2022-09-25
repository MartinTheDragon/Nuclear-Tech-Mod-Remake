package at.martinthedragon.nucleartech.item.upgrades

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.AfterBurnUpgradeableMachine
import at.martinthedragon.nucleartech.block.entity.UpgradeableMachine
import at.martinthedragon.nucleartech.extensions.lightPurple

class AfterBurnUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<AfterBurnUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_AFTER_BURNER.lightPurple()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is AfterBurnUpgradeableMachine

    override fun apply(machine: AfterBurnUpgradeableMachine) {
        machine.afterBurnUpgradeLevel = (machine.afterBurnUpgradeLevel + tier).coerceAtMost(machine.maxAfterBurnUpgradeLevel)
    }
}
