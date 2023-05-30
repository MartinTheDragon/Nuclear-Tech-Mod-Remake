package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.rbmk.RBMKAutoControlMenu
import at.martinthedragon.nucleartech.menu.slots.data.ByteDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.DoubleDataSlot
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class RBMKAutoControlBlockEntity(pos: BlockPos, state: BlockState) : RBMKControlBlockEntity(BlockEntityTypes.rbmkAutoControlBlockEntityType.get(), pos, state) {
    var function = ControlFunction.Linear
    var levelLower = 0.0
    var levelUpper = 0.0
    var heatLower = 0.0
    var heatUpper = 0.0

    override val defaultName = LangKeys.CONTAINER_RBMK_CONTROL_AUTO.get()
    override fun createMenu(windowID: Int, inventory: Inventory) = RBMKAutoControlMenu(windowID, inventory, this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(DoubleDataSlot.create(this::levelUpper, this::levelUpper::set))
        menu.track(DoubleDataSlot.create(this::levelLower, this::levelLower::set))
        menu.track(DoubleDataSlot.create(this::heatUpper, this::heatUpper::set))
        menu.track(DoubleDataSlot.create(this::heatLower, this::heatLower::set))
        menu.track(ByteDataSlot.create({ function.ordinal.toByte() }, { val index = it.toInt(); function = if (index !in ControlFunction.values().indices) ControlFunction.Linear else ControlFunction.values()[index] }))
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val lowerBound = min(heatLower, heatUpper)
        val upperBound = max(heatLower, heatUpper)

        val fauxLevel: Double = when {
            heat < lowerBound -> levelLower
            heat > upperBound -> levelUpper
            else -> when (function) {
                    ControlFunction.Linear -> (heat - heatLower) * ((levelUpper - levelLower) / (heatUpper - heatLower)) + levelLower
                    ControlFunction.QuadUp -> ((heat - heatLower) / (heatUpper - heatLower)).pow(2) * (levelUpper - levelLower) + levelLower
                    ControlFunction.QuadDown -> ((heat - heatUpper) / (heatLower - heatUpper)).pow(2) * (levelLower - levelUpper) + levelUpper
                }
        }

        targetLevel = (fauxLevel * 0.01).coerceIn(0.0, 1.0)

        super.serverTick(level, pos, state)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putDouble("LevelLower", levelLower)
        tag.putDouble("LevelUpper", levelUpper)
        tag.putDouble("HeatLower", heatLower)
        tag.putDouble("HeatUpper", heatUpper)
        tag.putInt("Function", function.ordinal)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        levelLower = tag.getDouble("LevelLower")
        levelUpper = tag.getDouble("LevelUpper")
        heatLower = tag.getDouble("HeatLower")
        heatUpper = tag.getDouble("HeatUpper")
        function = ControlFunction.values()[tag.getInt("Function")]
    }

    override val consoleType = RBMKConsoleBlockEntity.Column.Type.CONTROL_AUTO

    enum class ControlFunction { Linear, QuadUp, QuadDown }
}
