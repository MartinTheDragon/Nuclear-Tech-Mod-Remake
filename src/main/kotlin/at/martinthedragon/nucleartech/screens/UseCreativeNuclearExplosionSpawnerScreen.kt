package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.networking.SpawnNuclearExplosionMessage
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.command.arguments.Vec3Argument
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent

class UseCreativeNuclearExplosionSpawnerScreen : Screen(ModItems.creativeNuclearExplosionSpawner.get().description) {
    private lateinit var strengthEdit: TextFieldWidget
    private lateinit var isMutedButton: BooleanSwitchButton
    private lateinit var hasFalloutButton: BooleanSwitchButton
    private lateinit var extraFalloutEdit: TextFieldWidget
    private lateinit var positionEdit: TextFieldWidget
    private lateinit var cancelButton: Button
    private lateinit var startButton: Button

    override fun init() {
        val startX = width / 2 - GUI_WIDTH / 2
        val startY = height / 2 - GUI_HEIGHT / 2
        minecraft!!.keyboardHandler.setSendRepeatsToGui(true)
        strengthEdit = object : TextFieldWidget(font, startX + 116, startY + 18, 52, 16, STRENGTH_LABEL) {
            override fun charTyped(char: Char, p_231042_2_: Int): Boolean = if (!char.isDigit()) false else super.charTyped(char, p_231042_2_)
        }
        strengthEdit.setMaxLength(3)
        strengthEdit.value = "200"
        children += strengthEdit
        isMutedButton = BooleanSwitchButton(startX + 152, startY + 36, false, MUTED_LABEL) {}
        children += isMutedButton
        buttons += isMutedButton
        hasFalloutButton = BooleanSwitchButton(startX + 152, startY + 54, true, HAS_FALLOUT_LABEL) {}
        children += hasFalloutButton
        buttons += hasFalloutButton
        extraFalloutEdit = object : TextFieldWidget(font, startX + 116, startY + 72, 52, 16, EXTRA_FALLOUT_LABEL) {
            override fun charTyped(char: Char, p_231042_2_: Int): Boolean = if (!char.isDigit()) false else super.charTyped(char, p_231042_2_)
        }
        extraFalloutEdit.setMaxLength(4)
        extraFalloutEdit.value = "0"
        children += extraFalloutEdit
        positionEdit = object : TextFieldWidget(font, startX + 116, startY + 90, 52, 16, POSITION_LABEL) {
            override fun charTyped(char: Char, p_231042_2_: Int): Boolean = if (!char.isDigit() && char !in listOf('~', '-', '.', '^')) false else super.charTyped(char, p_231042_2_)
        }
        positionEdit.setMaxLength(64)
        positionEdit.value = "~ ~ ~"
        children += positionEdit

        cancelButton = Button(startX + 8, startY + 112, 76, 20, CANCEL_LABEL) { onCancel() }
        buttons += cancelButton
        children += cancelButton
        startButton = Button(startX + 92, startY + 112, 76, 20, START_LABEL) { onStart() }
        buttons += startButton
        children += startButton

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

    private var error: ITextComponent? = null

    override fun render(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        minecraft!!.textureManager.bind(GUI_LOCATION)
        val startX = (width - GUI_WIDTH) / 2
        val startY = (height - GUI_HEIGHT) / 2
        blit(matrix, startX, startY, 0, 0, GUI_WIDTH, GUI_HEIGHT)

        val title = error ?: ModItems.creativeNuclearExplosionSpawner.get().description
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
            error = StringTextComponent(e.localizedMessage)
            return
        }
        NuclearPacketHandler.INSTANCE.sendToServer(SpawnNuclearExplosionMessage(strength, muted, fallout, extraFallout, position))
        minecraft?.setScreen(null)
    }

    private fun onCancel() {
        minecraft?.setScreen(null)
    }

    override fun isPauseScreen() = false

    class BooleanSwitchButton(x: Int, y: Int, startValue: Boolean, label: ITextComponent, onPress: IPressable) :
        Button(x, y, 16, 16, label, onPress)
    {
        var on = startValue
            private set

        override fun renderButton(matrix: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            Minecraft.getInstance().textureManager.bind(GUI_LOCATION)
            val x = if (on) GUI_WIDTH + 16 else GUI_WIDTH
            val y = if (isHovered()) 16 else 0
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
        val GUI_LOCATION = ResourceLocation(NuclearTech.MODID, "textures/gui/creative_nuclear_explosion_spawner.png")
        private val STRENGTH_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.strength")
        private val MUTED_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.muted")
        private val HAS_FALLOUT_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.has_fallout")
        private val EXTRA_FALLOUT_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.extra_fallout")
        private val POSITION_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.position")
        private val CANCEL_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.cancel")
        private val START_LABEL = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.start")
        private val ERR_STRENGTH = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.error_strength")
        private val ERR_EXTRA_FALLOUT = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.error_extra_fallout")
        val ERR_INSUFFICIENT_PERMISSION: ITextComponent = TranslationTextComponent("creative_nuclear_explosion_spawner.${NuclearTech.MODID}.error_insufficient_permission").withStyle(TextFormatting.RED)
    }
}
