package at.martinthedragon.nucleartech.item.upgrades

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.SpeedUpgradeableMachine
import at.martinthedragon.nucleartech.block.entity.UpgradeableMachine
import at.martinthedragon.nucleartech.extensions.darkRed

class SpeedUpgrade(override val tier: Int) : MachineUpgradeItem.UpgradeEffect<SpeedUpgradeableMachine> {
    override fun getName() = LangKeys.UPGRADE_NAME_SPEED.darkRed()

    override fun isCompatibleWith(machine: UpgradeableMachine) = machine is SpeedUpgradeableMachine

    override fun apply(machine: SpeedUpgradeableMachine) {
        machine.speedUpgradeLevel = (machine.speedUpgradeLevel + tier).coerceAtMost(machine.maxSpeedUpgradeLevel)
    }
}
