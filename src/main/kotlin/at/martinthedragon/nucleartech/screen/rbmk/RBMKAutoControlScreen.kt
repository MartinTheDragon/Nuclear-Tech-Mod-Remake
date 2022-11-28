package at.martinthedragon.nucleartech.screen.rbmk

import at.martinthedragon.nucleartech.block.entity.rbmk.RBMKAutoControlBlockEntity
import at.martinthedragon.nucleartech.menu.ContainerDataListener
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.rbmk.RBMKAutoControlMenu
import at.martinthedragon.nucleartech.menu.slots.data.DoubleDataSlot
import at.martinthedragon.nucleartech.menu.slots.data.NTechDataSlot
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.networking.SetRBMKAutoControlRodValuesMessage
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Inventory
import java.util.function.Predicate

class RBMKAutoControlScreen(
    menu: RBMKAutoControlMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<RBMKAutoControlMenu>(menu, playerInventory, title), ContainerDataListener<RBMKAutoControlBlockEntity> {
    private val texture = ntm("textures/gui/rbmk/auto_control.png")

    init {
        imageWidth = 176
        imageHeight = 186
        inventoryLabelY = imageHeight - 94
    }

    private lateinit var editBoxes: Array<EditBox>

    override fun init() {
        minecraft!!.keyboardHandler.setSendRepeatsToGui(true)
        super.init()

        val editBoxFilter = Predicate<String> { string -> string != null && string.all { char -> char.isDigit() || char == '.' }}
        editBoxes = Array(4) {
            EditBox(font, guiLeft + 30, guiTop + 27 + it * 11, 26, 6, TextComponent.EMPTY).apply {
                setTextColor(-1)
                setTextColorUneditable(-1)
                setBordered(false)
                setMaxLength(if (it < 2) 5 else 6)
                setFilter(editBoxFilter)
                this@RBMKAutoControlScreen.addRenderableWidget(this)
            }
        }

        menu.addDataListener(this)
    }

    override fun dataChanged(menu: NTechContainerMenu<RBMKAutoControlBlockEntity>, data: NTechDataSlot.Data) {
        if (data is DoubleDataSlot.DoubleData) {
            editBoxes[data.slot.toInt()].value = ((data.value * 10.0).toInt() / 10.0).toString()
        }
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == 256) minecraft!!.player!!.closeContainer()

        var inputConsumed = false
        for (editBox in editBoxes) {
            editBox.keyPressed(keyCode, scanCode, modifiers)
            inputConsumed = editBox.canConsumeInput()
            if (inputConsumed) break
        }

        return if (inputConsumed) true else super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun resize(minecraft: Minecraft, x: Int, y: Int) {
        val values = editBoxes.map { it.value }
        super.resize(minecraft, x, y)
        editBoxes.forEachIndexed { index, editBox -> editBox.value = values[index] }
    }

    override fun removed() {
        super.removed()
        minecraft!!.keyboardHandler.setSendRepeatsToGui(false)
        menu.removeDataListener(this)
    }

    override fun containerTick() {
        super.containerTick()
        editBoxes.forEach { it.tick() }
    }

    // TODO actual buttons
    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        if (x.toInt() in guiLeft + 28..guiLeft + 58 && y.toInt() in guiTop + 70..guiTop + 80) {
            Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
            val data = editBoxes.mapIndexed { index, editBox ->
                val clamp = if (index < 2) 100.0 else 9999.0
                var result = (editBox.value.toDoubleOrNull() ?: 0.0).coerceIn(0.0, clamp)
                result = (result * 10).toInt() / 10.0
                editBox.value = result.toString()
                result
            }
            NuclearPacketHandler.INSTANCE.sendToServer(SetRBMKAutoControlRodValuesMessage(data[0], data[1], data[2], data[3]))
        }

        for (i in 0..2) {
            if (x.toInt() in guiLeft + 61..guiLeft + 83 && y.toInt() in guiTop + 48 + i * 11..guiTop + 58 + i * 11 && menu.clickMenuButton(minecraft!!.player!!, i)) {
                Minecraft.getInstance().soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F))
                minecraft!!.gameMode!!.handleInventoryButtonClick(menu.containerId, i)
                return true
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

        val height = (56 * (1.0 - menu.blockEntity.rodLevel)).toInt()
        if (height > 0) blit(stack, guiLeft + 124, guiTop + 29, 176, 56 - height, 8, height)

        val function = menu.blockEntity.function.ordinal
        blit(stack, guiLeft + 59, guiTop + 27, 184, function * 19, 26, 19)
    }
}
