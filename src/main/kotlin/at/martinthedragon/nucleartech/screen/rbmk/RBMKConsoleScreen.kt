package at.martinthedragon.nucleartech.screen.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKConsoleBlockEntity
import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKManualControlBlockEntity
import at.martinthedragon.nucleartech.extensions.contains
import at.martinthedragon.nucleartech.extensions.yellow
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.math.rotate1DSquareMatrixInPlaceClockwise
import at.martinthedragon.nucleartech.menu.rbmk.RBMKConsoleMenu
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.networking.SetRBMKConsoleControlRodLevelMessage
import at.martinthedragon.nucleartech.networking.SetRBMKConsoleScreenAssignedColumnsMessage
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import it.unimi.dsi.fastutil.bytes.ByteArrayList
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.level.block.Rotation
import net.minecraftforge.registries.ForgeRegistries
import kotlin.math.ceil
import kotlin.math.min
import net.minecraft.sounds.SoundEvents as VanillaSoundEvents

class RBMKConsoleScreen(
    menu: RBMKConsoleMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKConsoleMenu>(menu, playerInventory, title) { // TODO consider making it not a *container* screen
    private val texture = ntm("textures/gui/rbmk/console.png")

    init {
        imageWidth = 244
        imageHeight = 172
        inventoryLabelY = imageHeight - 94
    }

    private val selection = BooleanArray(15 * 15)
    private var az5Lid = true
    private var lastEmergyPress = 0L
    private lateinit var editBox: EditBox

    override fun init() {
        super.init()

        minecraft!!.keyboardHandler.setSendRepeatsToGui(true)

        editBox = EditBox(font, guiLeft + 9, guiTop + 84, 35, 9, TextComponent.EMPTY).apply {
            setTextColor(0x00FF00)
            setTextColorUneditable(0x008000)
            setBordered(false)
            setMaxLength(5)
            setFilter { string -> string != null && string.all { char -> char.isDigit() || char == '.' }}
            this@RBMKConsoleScreen.addRenderableWidget(this)
        }
    }

    override fun resize(minecraft: Minecraft, x: Int, y: Int) {
        val value = editBox.value
        super.resize(minecraft, x, y)
        editBox.value = value
    }

    override fun removed() {
        super.removed()
        minecraft!!.keyboardHandler.setSendRepeatsToGui(false)
    }

    override fun containerTick() {
        super.containerTick()
        editBox.tick()
    }

    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        val console = menu.blockEntity
        val mouseX = x.toInt()
        val mouseY = y.toInt()

        val bx = 86
        val by = 11
        val size = 10

        // toggle selection
        if (mouseX in guiLeft + bx .. guiLeft + bx + 150 && mouseY in guiTop + by .. guiTop + by + 150) {
            val index = (mouseX - bx - guiLeft) / size + (mouseY - by - guiTop) / size * 15
            if (index in selection.indices && console.columns[index] != null) {
                selection[index] = !selection[index]
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, if (selection[index]) 1F else 0.75F))
                return true
            }
        }

        // clear selection
        if (mouseX in guiLeft + 72 .. guiLeft + 81 && mouseY in guiTop + 70 .. guiTop + 79) {
            selection.fill(false)
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.5F))
            return true
        }

        // select all control rods
        if (mouseX in guiLeft + 61 .. guiLeft + 70 && mouseY in guiTop + 70 .. guiTop + 79) {
            selection.fill(false)
            for ((index, column) in console.columns.withIndex()) {
                if (column != null && column.type == RBMKConsoleBlockEntity.Column.Type.CONTROL)
                    selection[index] = true
            }
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 1.5F))
            return true
        }

        // select color groups
        for (i in 0..4) {
            if (mouseX in guiLeft + 6 + i * 11 .. guiLeft + 15 + i * 11 && mouseY in guiTop + 70 .. guiTop + 79) {
                selection.fill(false)
                for ((index, column) in console.columns.withIndex()) {
                    if (column != null && column.type == RBMKConsoleBlockEntity.Column.Type.CONTROL && column.data.getByte("Color").toInt() == i)
                        selection[index] = true
                }
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.8F + i * 0.1F))
                return true
            }
        }

        // AZ-5
        if (mouseX in guiLeft + 30 .. guiLeft + 57 && mouseY in guiTop + 138 .. guiTop + 165) {
            if (az5Lid) {
                az5Lid = false
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.rbmkAz5Cover.get(), 0.5F))
            } else if (lastEmergyPress + 3000L < System.currentTimeMillis()) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.rbmkShutdown.get(), 1F))
                lastEmergyPress = System.currentTimeMillis()

                val rotatedColumns = console.columns.copyOf()
                repeat(console.getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
                    rotatedColumns.rotate1DSquareMatrixInPlaceClockwise()
                }

                val controlRods = ByteArrayList(rotatedColumns.size / 2)
                for ((index, column) in rotatedColumns.withIndex()) {
                    if (column != null && column.type == RBMKConsoleBlockEntity.Column.Type.CONTROL)
                        controlRods += index.toByte()
                }
                NuclearPacketHandler.INSTANCE.sendToServer(SetRBMKConsoleControlRodLevelMessage(0.0, controlRods.toByteArray()))
            }
            return true
        }

        // save control rod setting
        if (mouseX in guiLeft + 48 .. guiLeft + 59 && mouseY in guiTop + 82 .. guiTop + 93) {
            var level = (editBox.value.toDoubleOrNull() ?: 0.0).coerceIn(0.0, 100.0)
            level = (level * 10).toInt() / 10.0
            editBox.value = level.toString()
            level *= 0.01

            val selectedRodsCount = selection.count { it }
            val selectedRodsArray = ByteArray(selectedRodsCount)
            val rotatedSelection = selection.copyOf()
            repeat(console.getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
                rotatedSelection.rotate1DSquareMatrixInPlaceClockwise()
            }
            var nextIndex = 0
            for (i in rotatedSelection.indices) if (rotatedSelection[i])
                selectedRodsArray[nextIndex++] = i.toByte()

            NuclearPacketHandler.INSTANCE.sendToServer(SetRBMKConsoleControlRodLevelMessage(level, selectedRodsArray))
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 1F))
            return true
        }

        for (i in 0..2) for (j in 0..1) {
            val id = i * 2 + j

            // toggle screen display type
            if (mouseX in guiLeft + 6 + 40 * j..guiLeft + 6 + 40 * j + 17 && mouseY in guiTop + 8 + 21 * i..guiTop + 8 + 21 * i + 17 && menu.clickMenuButton(minecraft!!.player!!, id)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.5F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, id)
                return true
            }

            // submit selected columns for screen display
            if (mouseX in guiLeft + 24 + 40 * j..guiLeft + 24 + 40 * j + 17 && mouseY in guiTop + 8 + 21 * i..guiTop + 8 + 21 * i + 17) {
                val selectedRodsCount = selection.count { it }
                val selectedRodsArray = ByteArray(selectedRodsCount)
                val rotatedSelection = selection.copyOf()
                repeat(console.getHorizontalBlockRotation().getRotated(Rotation.CLOCKWISE_180).ordinal) {
                    rotatedSelection.rotate1DSquareMatrixInPlaceClockwise()
                }
                var nextIndex = 0
                for (k in rotatedSelection.indices) if (rotatedSelection[k])
                    selectedRodsArray[nextIndex++] = k.toByte()

                NuclearPacketHandler.INSTANCE.sendToServer(SetRBMKConsoleScreenAssignedColumnsMessage(id, selectedRodsArray))
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(VanillaSoundEvents.UI_BUTTON_CLICK, 0.75F))
            }
        }

        return super.mouseClicked(x, y, button)
    }

    override fun render(stack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(stack)
        super.render(stack, mouseX, mouseY, partialTicks)
        renderTooltip(stack, mouseX, mouseY)
    }

    override fun renderBg(stack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, texture)
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize)

        val console = menu.blockEntity

        if (az5Lid) {
            blit(stack, guiLeft + 30, guiTop + 138, 228, 172, 28, 28)
        }

        for (i in 0..2) for (j in 0..1) {
            val id = i * 2 + j
            blit(stack, guiLeft + 6 + 40 * j, guiTop + 8 + 21 * i, console.screens[id].type.ordinal * 18, 238, 18, 18)
        }

        val bx = 86
        val by = 11
        val size = 10

        for ((index, column) in console.columns.withIndex()) {
            if (column == null) continue

            val x = bx + size * (index % 15)
            val y = by + size * (index / 15)

            val tx = column.type.ordinal * 10
            val ty = 172

            blit(stack, guiLeft + x, guiTop + y, tx, ty, size, size)

            val h = min(ceil((column.data.getDouble("Heat") - 20.0) * 10.0 / column.data.getDouble("MaxHeat")).toInt(), 10)
            blit(stack, guiLeft + x, guiTop + y + size - h, 0, 192 - h, 10, h)

            when (column.type) {
                RBMKConsoleBlockEntity.Column.Type.CONTROL -> {
                    val color = column.data.getByte("Color")
                    if (color > -1) blit(stack, guiLeft + x, guiTop + y, color * size, 202, 10, 10)
                    val fr = 8 - ceil(column.data.getDouble("RodLevel") * 8.0).toInt()
                    blit(stack, guiLeft + x + 4, guiTop + y + 1, 24, 183, 2, fr)
                }
                RBMKConsoleBlockEntity.Column.Type.CONTROL_AUTO -> {
                    val fr = 8 - ceil(column.data.getDouble("RodLevel") * 8.0).toInt()
                    blit(stack, guiLeft + x + 4, guiTop + y + 1, 24, 183, 2, fr)
                }
                RBMKConsoleBlockEntity.Column.Type.FUEL, RBMKConsoleBlockEntity.Column.Type.FUEL_REASIM -> {
                    if (column.data.contains("FuelHeat", Tag.TAG_DOUBLE)) {
                        val fh = ceil((column.data.getDouble("FuelHeat") - 20.0) * 8.0 / column.data.getDouble("FuelMaxHeat")).toInt()
                        blit(stack, guiLeft + x + 1, guiTop + y + size - fh - 1, 11, 191 - fh, 2, fh)

                        val fe = ceil(column.data.getDouble("Enrichment") * 8.0).toInt()
                        blit(stack, guiLeft + x + 4, guiTop + y + size - fe - 1, 14, 191 - fe, 2, fe)
                    }
                }
                RBMKConsoleBlockEntity.Column.Type.BOILER -> {
                    val fw = column.data.getInt("Water") * 8 / column.data.getInt("MaxWater")
                    blit(stack, guiLeft + x + 1, guiTop + y + size - fw - 1, 41, 191 - fw, 3, fw)
                    val fs = column.data.getInt("Steam") * 8 / column.data.getInt("MaxSteam")
                    blit(stack, guiLeft + x + 6, guiTop + y + size - fs - 1, 46, 191 - fs, 3, fs)

                    val fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation(column.data.getString("SteamType")))
                    if (fluid != null) when {
                        fluid.isSame(NTechFluids.steam.source.get()) -> blit(stack, guiLeft + x + 4, guiTop + y + 1, 44, 183, 2, 2)
                        fluid.isSame(NTechFluids.steamHot.source.get()) -> blit(stack, guiLeft + x + 4, guiTop + y + 3, 44, 185, 2, 2)
                        fluid.isSame(NTechFluids.steamSuperHot.source.get()) -> blit(stack, guiLeft + x + 4, guiTop + y + 5, 44, 187, 2, 2)
                        fluid.isSame(NTechFluids.steamUltraHot.source.get()) -> blit(stack, guiLeft + x + 4, guiTop + y + 7, 44, 189, 2, 2)
                    }
                }
                RBMKConsoleBlockEntity.Column.Type.HEATEX -> TODO()
                else -> {}
            }

            if (selection[index]) blit(stack, guiLeft + x, guiTop + y, 0, 192, 10, 10)
        }
    }

    override fun renderLabels(stack: PoseStack, mouseX: Int, mouseY: Int) {}

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        val console = menu.blockEntity

        val bx = 86
        val by = 11
        val size = 10

        if (mouseX in guiLeft + bx .. guiLeft + bx + 150 && mouseY in guiTop + by .. guiTop + by + 11 + 150) {
            val index = (mouseX - bx - guiLeft) / size + (mouseY - by - guiTop) / size * 15

            if (index in console.columns.indices) {
                val column = console.columns[index]
                if (column != null) {
                    renderComponentTooltip(matrix, listOf(TextComponent(column.type.name)) + column.getFormattedStats(), mouseX, mouseY, font)
                }
            }
        }

        if (isHovering(62, 71, 8, 8, mouseX.toDouble(), mouseY.toDouble()))
            renderTooltip(matrix, LangKeys.RBMK_CONSOLE_SELECT_CONTROL_RODS.get(), mouseX, mouseY)
        if (isHovering(73, 71, 8, 8, mouseX.toDouble(), mouseY.toDouble()))
            renderTooltip(matrix, LangKeys.RBMK_CONSOLE_DESELECT_CONTROL_RODS.get(), mouseX, mouseY)

        for (i in 0..2) for (j in 0..1) {
            val id = i * 2 + j + 1
            if (isHovering(7 + 40 * j, 9 + 21 * i, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
                renderTooltip(matrix, console.screens[id - 1].type.translationKey.format(id).yellow(), mouseX, mouseY)
            if (isHovering(25 + 40 * j, 9 + 21 * i, 16, 16, mouseX.toDouble(), mouseY.toDouble()))
                renderTooltip(matrix, LangKeys.RBMK_CONSOLE_ASSIGN.format(id), mouseX, mouseY)
        }

        for ((index, color) in RBMKManualControlBlockEntity.Color.values().withIndex()) {
            val offset = index * 11 + 7
            if (isHovering(offset, 71, 8, 8, mouseX.toDouble(), mouseY.toDouble()))
                renderTooltip(matrix, LangKeys.RBMK_CONSOLE_SELECT_GROUP.format(color.name.lowercase()), mouseX, mouseY)
        }
    }
}
