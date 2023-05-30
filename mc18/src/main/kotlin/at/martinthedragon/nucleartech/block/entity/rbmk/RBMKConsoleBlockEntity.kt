package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.TranslationKey
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.entity.BaseMachineBlockEntity
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.extensions.*
import at.martinthedragon.nucleartech.math.rotate1DSquareMatrixInPlaceCounterClockwise
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.rbmk.RBMKConsoleMenu
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.registries.ForgeRegistries

class RBMKConsoleBlockEntity(pos: BlockPos, state: BlockState) : BaseMachineBlockEntity(BlockEntityTypes.rbmkConsoleBlockEntityType.get(), pos, state), TickingServerBlockEntity {
    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("No sound loop for RBMK console")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(this)

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(0, ItemStack.EMPTY)
    override fun isItemValid(slot: Int, stack: ItemStack) = false

    override val defaultName = LangKeys.CONTAINER_RBMK_CONSOLE.get()
    override fun createMenu(windowID: Int, inventory: Inventory) = RBMKConsoleMenu(windowID, inventory, this)

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {}

    override fun getRenderBoundingBox() = AABB(blockPos.offset(-2, 0, -2), blockPos.offset(3, 4, 3))

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (level.gameTime % 10L == 0L) {
            level.profiler.push("rbmkConsole_rescan")
            rescan(level)
            level.profiler.pop()
            prepareScreenInfo()
            sendContinuousUpdatePacket()
        }
    }

    var scanTarget = blockPos
    val columns = arrayOfNulls<Column>(15 * 15)
    val screens = Array(6) { Screen() }
    val recentFluxValues = IntArray(20)

    private fun rescan(level: Level) {
        var flux = 0.0

        for (i in -7..7) for (j in -7..7) {
            val blockEntity = level.getBlockEntity(scanTarget.offset(i, 0, j))
            val index = (i + 7) + (j + 7) * 15
            if (blockEntity is RBMKBase) {
                columns[index] = Column(blockEntity.consoleType, blockEntity.getConsoleData()).apply {
                    data.putDouble("Heat", blockEntity.heat)
                    data.putDouble("MaxHeat", blockEntity.maxHeat())
                    data.putBoolean("Moderated", blockEntity.isModerated())
                }

                if (blockEntity is RBMKRodBlockEntity) {
                    flux += blockEntity.fluxFast + blockEntity.fluxSlow
                }
            } else columns[index] = null
        }

        for (i in 0 until recentFluxValues.lastIndex - 1)
            recentFluxValues[i] = recentFluxValues[i + 1]
        recentFluxValues[recentFluxValues.lastIndex] = flux.toInt()
    }

    private fun prepareScreenInfo() {
        for (screen in screens) {
            val screenType = screen.type
            if (screenType == Screen.Type.NONE) {
                screen.displayValue = 0.0
                continue
            }

            val availableColumns = screen.columns.map(columns::get).filterNotNull()
            var count = 0
            val total = availableColumns.fold(0.0) { acc, column ->
                acc + when {
                    screenType == Screen.Type.COLUMN_TEMPERATURE && column.data.contains("Heat", Tag.TAG_DOUBLE) -> { count++; column.data.getDouble("Heat") }
                    screenType == Screen.Type.FUEL_DEPLETION && column.data.contains("Enrichment", Tag.TAG_DOUBLE) -> { count++; (100.0 - column.data.getDouble("Enrichment") * 100.0) }
                    screenType == Screen.Type.FUEL_POISON && column.data.contains("Xenon", Tag.TAG_DOUBLE) -> { count++; column.data.getDouble("Xenon") }
                    screenType == Screen.Type.FUEL_TEMPERATURE && column.data.contains("FuelHeat", Tag.TAG_DOUBLE) -> { count++; column.data.getDouble("FuelHeat") }
                    screenType == Screen.Type.ROD_EXTRACTION && column.data.contains("RodLevel", Tag.TAG_DOUBLE) -> { count++; column.data.getDouble("RodLevel") * 100.0 }
                    else -> 0.0
                }
            }
            screen.displayValue = (total / count.toDouble() * 10.0).toInt() / 10.0
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun setControlRodLevelForSelection(rodLevel: Double, selection: UByteArray) {
        val maxColumn = columns.lastIndex.toUByte()
        if (selection.any { it > maxColumn }) {
            NuclearTech.LOGGER.error("RBMK Console at $blockPos received invalid control rod ids")
            return
        }

        for (rod in selection) {
            val x = rod % 15u - 7u
            val z = rod / 15u - 7u
            val blockEntity = levelUnchecked.getBlockEntity(scanTarget.offset(x.toInt(), 0, z.toInt()))
            if (blockEntity is RBMKManualControlBlockEntity) {
                blockEntity.startingLevel = blockEntity.rodLevel
                blockEntity.targetLevel = rodLevel.coerceIn(0.0, 1.0)
                blockEntity.markDirty()
            }
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun assignColumnsToScreen(screen: Int, selection: UByteArray) {
        val maxColumn = columns.lastIndex.toUByte()
        if (selection.any { it > maxColumn }) {
            NuclearTech.LOGGER.error("RBMK Console at $blockPos received invalid column ids")
            return
        }
        if (screen > screens.lastIndex) {
            NuclearTech.LOGGER.error("RBMK Console at $blockPos received in valid screen id for assigning columns")
            return
        }

        screens[screen].columns = IntArray(selection.size) { selection[it].toInt() }
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        val columnList = ListTag()
        columnList.addAll(columns.map {
            CompoundTag().apply {
                if (it != null) {
                    putByte("Type", it.type.ordinal.toByte())
                    put("Data", it.data)
                }
            }
        })
        put("Columns", columnList)

        val screenList = ListTag()
        screenList.addAll(screens.map {
            CompoundTag().apply {
                putByte("Type", it.type.ordinal.toByte())
                putDouble("Value", it.displayValue)
            }
        })
        put("Screens", screenList)

        putIntArray("Flux", recentFluxValues)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        columns.fill(null)
        // just as a side note: if you think this destructuring declaration with boxing of an iterator to make it indexed is inefficient,
        // the kotlin compiler completely optimises it out! check the bytecode
        for ((index, columnTag) in tag.getList("Columns", Tag.TAG_COMPOUND).withIndex()) {
            columnTag as CompoundTag
            if (columnTag.contains("Type"))
                columns[index] = Column(Column.Type.values()[columnTag.getByte("Type").toInt()], columnTag.getCompound("Data"))
        }
        repeat(getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
            columns.rotate1DSquareMatrixInPlaceCounterClockwise()
        }

        for ((index, screenTag) in tag.getList("Screens", Tag.TAG_COMPOUND).withIndex()) {
            screenTag as CompoundTag
            if (screenTag.contains("Type"))
                screens[index].apply {
                    type = Screen.Type.values()[screenTag.getByte("Type").toInt()]
                    displayValue = screenTag.getDouble("Value")
                }
        }
        tag.getIntArray("Flux").copyInto(recentFluxValues)
    }

    class Column(val type: Type, val data: CompoundTag) {
        fun getFormattedStats(): List<Component> = if (data.isEmpty) emptyList() else buildList {
            add(LangKeys.RBMK_HEAT.format((data.getDouble("Heat") * 10.0).toInt() / 10.0).yellow())

            when (type) {
                Type.FUEL, Type.FUEL_REASIM -> {
                    add(LangKeys.RBMK_ROD_DEPLETION.format(((1.0 - data.getDouble("Enrichment")) * 100_000.0).toInt() / 1000.0).green())
                    add(LangKeys.RBMK_ROD_XENON.format((data.getDouble("Xenon") * 1000.0).toInt() / 1000.0).darkPurple())
                    add(LangKeys.RBMK_ROD_HEAT_CORE.format((data.getDouble("FuelCoreHeat") * 10.0).toInt() / 10.0).darkRed())
                    add(LangKeys.RBMK_ROD_HEAT_HULL_TO_MELTING_POINT.format((data.getDouble("FuelHeat") * 10.0).toInt() / 10.0, (data.getDouble("FuelMaxHeat") * 10.0).toInt() / 10.0).red())
                }
                Type.BOILER -> {
                    add(LangKeys.RBMK_BOILER_WATER.format(data.getDouble("Water"), data.getDouble("MaxHeat")).blue())
                    add(LangKeys.RBMK_BOILER_STEAM.format(data.getDouble("Steam"), data.getDouble("MaxSteam")).white())
                    add(LangKeys.RBMK_BOILER_TYPE.format(ForgeRegistries.FLUIDS.getValue(ResourceLocation(data.getString("SteamType")))?.attributes?.translationKey?.let { TranslatableComponent(it) } ?: "N/A").yellow())
                }
                Type.CONTROL -> {
                    add(LangKeys.RBMK_CONTROL_ROD_LEVEL.format((data.getDouble("RodLevel") * 1000.0).toInt() / 10.0).yellow())
                    val colorOrdinal = data.getByte("Color").toInt()
                    if (colorOrdinal in RBMKManualControlBlockEntity.Color.values().indices)
                        add(RBMKManualControlBlockEntity.Color.values()[colorOrdinal].groupNameTranslationKey.yellow())
                }
                Type.CONTROL_AUTO -> {
                    add(LangKeys.RBMK_CONTROL_ROD_LEVEL.format((data.getDouble("RodLevel") * 1000.0).toInt() / 10.0).yellow())
                }
                Type.HEATEX -> {
                    // TODO
                }
                else -> {}
            }

            if (data.getBoolean("Moderated"))
                add(LangKeys.RBMK_MODERATED.yellow())
        }

        enum class Type { BLANK, FUEL, CONTROL, CONTROL_AUTO, BOILER, MODERATOR, ABSORBER, REFLECTOR, OUTGASSER, FUEL_REASIM, BREEDER, STORAGE, COOLER, HEATEX }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)

        tag.putInt("TargetX", scanTarget.x)
        tag.putInt("TargetY", scanTarget.y)
        tag.putInt("TargetZ", scanTarget.z)

        val screenList = ListTag()
        screenList.addAll(screens.map {
            CompoundTag().apply {
                putByte("Type", it.type.ordinal.toByte())
                putByteArray("Assigned", ByteArray(it.columns.size) { index -> it.columns[index].toByte() })
            }
        })
        tag.put("Screens", screenList)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        scanTarget = BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"))

        for ((index, screenTag) in tag.getList("Screens", Tag.TAG_COMPOUND).withIndex()) {
            screenTag as CompoundTag
            if (screenTag.contains("Type"))
                screens[index].apply {
                    type = Screen.Type.values()[screenTag.getByte("Type").toInt()]
                    val columnByteArray = screenTag.getByteArray("Assigned")
                    columns = IntArray(columnByteArray.size) { columnByteArray[it].toUByte().toInt() }
                }
        }
    }

    class Screen(var type: Type = Type.NONE, var columns: IntArray = intArrayOf(), var displayValue: Double = 0.0) {
        fun getDisplayText(): Component = when (type) {
            Type.COLUMN_TEMPERATURE -> LangKeys.RBMK_SCREEN_TEMPERATURE.format(displayValue)
            Type.FUEL_DEPLETION -> LangKeys.RBMK_SCREEN_DEPLETION.format(displayValue)
            Type.FUEL_POISON -> LangKeys.RBMK_SCREEN_POISON.format(displayValue)
            Type.FUEL_TEMPERATURE -> LangKeys.RBMK_SCREEN_FUEL_TEMPERATURE.format(displayValue)
            Type.ROD_EXTRACTION -> LangKeys.RBMK_SCREEN_ROD_EXTRACTION.format(displayValue)
            else -> TextComponent.EMPTY
        }

        enum class Type(val translationKey: TranslationKey) {
            NONE(LangKeys.RBMK_SCREEN_TYPE_NONE),
            COLUMN_TEMPERATURE(LangKeys.RBMK_SCREEN_TYPE_COLUMN_TEMPERATURE),
            ROD_EXTRACTION(LangKeys.RBMK_SCREEN_TYPE_ROD_EXTRACTION),
            FUEL_DEPLETION(LangKeys.RBMK_SCREEN_TYPE_FUEL_DEPLETION),
            FUEL_POISON(LangKeys.RBMK_SCREEN_TYPE_FUEL_POISON),
            FUEL_TEMPERATURE(LangKeys.RBMK_SCREEN_TYPE_FUEL_TEMPERATURE)
        }
    }
}
