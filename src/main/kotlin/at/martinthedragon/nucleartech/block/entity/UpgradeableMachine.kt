package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.item.upgrades.MachineUpgradeItem
import net.minecraft.network.chat.MutableComponent
import net.minecraft.sounds.SoundSource

interface UpgradeableMachine : BlockEntityWrapper {
    fun getUpgradeInfoForEffect(effect: MachineUpgradeItem.UpgradeEffect<*>): List<MutableComponent>

    fun getSupportedUpgrades(): List<MachineUpgradeItem.UpgradeEffect<*>>
    fun resetUpgrades()

    val upgradeSlots: IntRange

    fun checkChangedUpgradeSlot(slot: Int) {
        val level = levelWrapped
        if (level == null || !hasInventory || slot !in upgradeSlots) return
        if (getInventory().getStackInSlot(slot).item is MachineUpgradeItem) {
            level.playSound(null, blockPosWrapped, SoundEvents.installUpgrade.get(), SoundSource.BLOCKS, 1F, 1F)
        }
    }
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
