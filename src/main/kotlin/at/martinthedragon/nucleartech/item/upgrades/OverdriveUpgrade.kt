package at.martinthedragon.nucleartech.item.upgrades

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.OverdriveUpgradeableMachine
import at.martinthedragon.nucleartech.block.entity.UpgradeableMachine
import at.martinthedragon.nucleartech.extensions.darkGray

class OverdriveUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<OverdriveUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_OVERDRIVE.darkGray()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is OverdriveUpgradeableMachine

    override fun apply(machine: OverdriveUpgradeableMachine) {
        machine.overdriveUpgradeLevel = (machine.overdriveUpgradeLevel + tier).coerceAtMost(machine.maxOverdriveUpgradeLevel)
    }
}
