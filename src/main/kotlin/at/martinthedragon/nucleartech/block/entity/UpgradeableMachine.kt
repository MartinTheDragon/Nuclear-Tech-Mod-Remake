package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import net.minecraft.network.chat.MutableComponent

interface UpgradeableMachine {
    fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>): List<MutableComponent>
    fun getSupportedUpgrades(): List<MachineUpgradeItem.UpgradeEffect<*>>

    fun resetUpgrades()
}

interface SpeedUpgradeableMachine : UpgradeableMachine {
    val maxSpeedUpgradeLevel: Int
    var speedUpgradeLevel: Int

    override fun resetUpgrades() {
        speedUpgradeLevel = 0
    }
}

interface PowerSavingUpgradeableMachine : UpgradeableMachine {
    val maxPowerSavingUpgradeLevel: Int
    var powerSavingUpgradeLevel: Int

    override fun resetUpgrades() {
        powerSavingUpgradeLevel = 0
    }
}

interface OverdriveUpgradeableMachine : UpgradeableMachine {
    val maxOverdriveUpgradeLevel: Int
    var overdriveUpgradeLevel: Int

    override fun resetUpgrades() {
        overdriveUpgradeLevel = 0
    }
}
