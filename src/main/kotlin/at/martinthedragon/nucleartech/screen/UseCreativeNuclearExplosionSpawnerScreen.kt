package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.networking.SpawnNuclearExplosionMessage
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

class UseCreativeNuclearExplosionSpawnerScreen : Screen(NTechItems.creativeNuclearExplosionSpawner.get().description) {
    private lateinit var strengthEdit: EditBox
    private lateinit var isMutedButton: BooleanSwitchButton
    private lateinit var hasFalloutButton: BooleanSwitchButton
    private lateinit var extraFalloutEdit: EditBox
    private lateinit var positionEdit: EditBox
    private lateinit var cancelButton: Button
    private lateinit var startButton: Button

    override fun init() {
        val startX = width / 2 - GUI_WIDTH / 2
        val startY = height / 2 - GUI_HEIGHT / 2
        minecraft!!.keyboardHandler.setSendRepeatsToGui(true)
        strengthEdit = object : EditBox(font, startX + 116, startY + 18, 52, 16, STRENGTH_LABEL) {
            override fun charTyped(char: Char, p_231042_2_: Int): Boolean = if (!char.isDigit()) false else super.charTyped(char, p_231042_2_)
        }
        strengthEdit.setMaxLength(3)
        strengthEdit.value = "200"
        addRenderableWidget(strengthEdit)
        isMutedButton = BooleanSwitchButton(startX + 152, startY + 36, false, MUTED_LABEL) {}
        addRenderableWidget(isMutedButton)
        hasFalloutButton = BooleanSwitchButton(startX + 152, startY + 54, true, HAS_FALLOUT_LABEL) {}
        addRenderableWidget(hasFalloutButton)
        extraFalloutEdit = object : EditBox(font, startX + 116, startY + 72, 52, 16, EXTRA_FALLOUT_LABEL) {
            override fun charTyped(char: Char, p_231042_2_: Int): Boolean = if (!char.isDigit()) false else super.charTyped(char, p_231042_2_)
        }
        extraFalloutEdit.setMaxLength(4)
        extraFalloutEdit.value = "0"
        addRenderableWidget(extraFalloutEdit)
        positionEdit = object : EditBox(font, startX + 116, startY + 90, 52, 16, POSITION_LABEL) {
            override fun charTyped(char: Char, p_231042_2_: Int): Boolean = if (!char.isDigit() && char !in listOf('~', '-', '.', '^')) false else super.charTyped(char, p_231042_2_)
        }
        positionEdit.setMaxLength(64)
        positionEdit.value = "~ ~ ~"
        addRenderableWidget(positionEdit)

        cancelButton = Button(startX + 8, startY + 112, 76, 20, CANCEL_LABEL) { onCancel() }
        addRenderableWidget(cancelButton)
        startButton = Button(startX + 92, startY + 112, 76, 20, START_LABEL) { onStart() }
        addRenderableWidget(startButton)

        setInitialFocus(strengthEdit)
    }

    override fun resize(p_231152_1_: Minecraft, p_231152_2_: Int, p_231152_3_: Int) {
        val strength = strengthEdit.value
        super.resize(p_231152_1_, p_231152_2_, p_231152_3_)
        strengthEdit.value = strength
    }

    override fun removed() {
        super.removed()
        minecraft!!.keyboardHandler.setSendRepeatsToGui(false)
    }

    override fun tick() {
        strengthEdit.tick()
        extraFalloutEdit.tick()
        positionEdit.tick()
    }

    private var error: Component? = null

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, GUI_LOCATION)
        val startX = (width - GUI_WIDTH) / 2
        val startY = (height - GUI_HEIGHT) / 2
        blit(matrix, startX, startY, 0, 0, GUI_WIDTH, GUI_HEIGHT)

        val title = error ?: NTechItems.creativeNuclearExplosionSpawner.get().description
        val titleColor = if (error == null) 0x404040 else 0xFF5555
        font.draw(matrix, title, (width - font.width(title)) / 2F, startY + 6F, titleColor)

        val labelColor = 0x303030
        font.draw(matrix, STRENGTH_LABEL, startX + 8F, startY + 22F, labelColor)
        font.draw(matrix, MUTED_LABEL, startX + 8F, startY + 40F, labelColor)
        font.draw(matrix, HAS_FALLOUT_LABEL, startX + 8F, startY + 58F, labelColor)
        font.draw(matrix, EXTRA_FALLOUT_LABEL, startX + 8F, startY + 76F, labelColor)
        font.draw(matrix, POSITION_LABEL, startX + 8F, startY + 94F, labelColor)

        strengthEdit.render(matrix, mouseX, mouseY, partialTicks)
        extraFalloutEdit.render(matrix, mouseX, mouseY, partialTicks)
        positionEdit.render(matrix, mouseX, mouseY, partialTicks)

        super.render(matrix, mouseX, mouseY, partialTicks)
    }

    private fun onStart() {
        val strength = strengthEdit.value.toIntOrNull()
        if (strength == null) {
            error = ERR_STRENGTH
            return
        }
        val muted = isMutedButton.on
        val fallout = hasFalloutButton.on
        val extraFallout = extraFalloutEdit.value.toIntOrNull()
        if (extraFallout == null) {
            error = ERR_EXTRA_FALLOUT
            return
        }
        val commandSource = minecraft?.player?.createCommandSourceStack() ?: return
        val position = try {
            Vec3Argument.vec3().parse(StringReader(positionEdit.value)).getPosition(commandSource)
        } catch (e: CommandSyntaxException) {
            error = TextComponent(e.localizedMessage)
            return
        }
        NuclearPacketHandler.INSTANCE.sendToServer(SpawnNuclearExplosionMessage(strength, muted, fallout, extraFallout, position))
        minecraft?.setScreen(null)
    }

    private fun onCancel() {
        minecraft?.setScreen(null)
    }

    override fun isPauseScreen() = false

    class BooleanSwitchButton(x: Int, y: Int, startValue: Boolean, label: Component, onPress: OnPress) :
        Button(x, y, 16, 16, label, onPress)
    {
        var on = startValue
            private set

        override fun renderButton(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            RenderSystem.setShaderTexture(0, GUI_LOCATION)
            val x = if (on) GUI_WIDTH + 16 else GUI_WIDTH
            val y = if (isHoveredOrFocused) 16 else 0
            blit(matrix, this.x, this.y, x, y, width, height)
        }

        override fun onPress() {
            on = !on
            super.onPress()
        }
    }

    companion object {
        const val GUI_WIDTH = 176
        const val GUI_HEIGHT = 141
        val GUI_LOCATION = ntm("textures/gui/creative_nuclear_explosion_spawner.png")
        private val STRENGTH_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.strength")
        private val MUTED_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.muted")
        private val HAS_FALLOUT_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.has_fallout")
        private val EXTRA_FALLOUT_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.extra_fallout")
        private val POSITION_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.position")
        private val CANCEL_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.cancel")
        private val START_LABEL = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.start")
        private val ERR_STRENGTH = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.error_strength")
        private val ERR_EXTRA_FALLOUT = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.error_extra_fallout")
        val ERR_INSUFFICIENT_PERMISSION: Component = TranslatableComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.error_insufficient_permission").withStyle(ChatFormatting.RED)
    }
}
