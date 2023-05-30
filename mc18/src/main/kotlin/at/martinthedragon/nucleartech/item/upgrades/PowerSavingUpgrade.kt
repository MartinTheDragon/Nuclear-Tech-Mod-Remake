package at.martinthedragon.nucleartech.item.upgrades

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.PowerSavingUpgradeableMachine
import at.martinthedragon.nucleartech.block.entity.UpgradeableMachine
import at.martinthedragon.nucleartech.extensions.darkBlue

class PowerSavingUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<PowerSavingUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_POWER_SAVING.darkBlue()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is PowerSavingUpgradeableMachine

    override fun apply(machine: PowerSavingUpgradeableMachine) {
        machine.powerSavingUpgradeLevel = (machine.powerSavingUpgradeLevel + tier).coerceAtMost(machine.maxPowerSavingUpgradeLevel)
    }
}
