package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.TranslationKey
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.rbmk.RBMKManualControlMenu
import at.martinthedragon.nucleartech.menu.slots.data.ByteDataSlot
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sin

open class RBMKManualControlBlockEntity(type: BlockEntityType<out RBMKManualControlBlockEntity>, pos: BlockPos, state: BlockState) : RBMKControlBlockEntity(type, pos, state) {
    constructor(pos: BlockPos, state: BlockState) : this(BlockEntityTypes.rbmkManualControlBlockEntityType.get(), pos, state)

    var color: Color? = null
    var startingLevel = 0.0

    override var targetLevel: Double
        get() = super.targetLevel
        set(value) { super.targetLevel = value; startingLevel = value }

    override val fluxMultiplier: Double
        get() {
            var surge = 0.0
            if (targetLevel < startingLevel && abs(rodLevel - targetLevel) > 0.01)
                surge = sin((1.0 - rodLevel).pow(15) * PI) * (startingLevel - targetLevel) * NuclearConfig.rbmk.surgeMod.get()
            return rodLevel + surge
        }

    override val defaultName = LangKeys.CONTAINER_RBMK_CONTROL_MANUAL.get()
    override fun createMenu(windowID: Int, inventory: Inventory) = RBMKManualControlMenu(windowID, inventory, this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {
        menu.track(ByteDataSlot.create({ val color = color; color?.ordinal?.toByte() ?: -1 }, { val index = it.toInt(); color = if (index !in Color.values().indices) null else Color.values()[index] }))
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putDouble("StartingLevel", startingLevel)
        color?.let { tag.putInt("Color", it.ordinal) }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        startingLevel = tag.getDouble("StartingLevel")
        if (tag.contains("Color", Tag.TAG_INT.toInt()))
            color = Color.values()[tag.getInt("Color")]
    }

    override val consoleType = RBMKConsoleBlockEntity.Column.Type.CONTROL
    override fun getConsoleData() = super.getConsoleData().apply {
        putByte("Color", color?.ordinal?.toByte() ?: -1)
    }

    enum class Color(val groupNameTranslationKey: TranslationKey) {
        Red(LangKeys.RBMK_CONTROL_GROUP_RED),
        Yellow(LangKeys.RBMK_CONTROL_GROUP_YELLOW),
        Green(LangKeys.RBMK_CONTROL_GROUP_GREEN),
        Blue(LangKeys.RBMK_CONTROL_GROUP_BLUE),
        Purple(LangKeys.RBMK_CONTROL_GROUP_PURPLE)
    }
}
