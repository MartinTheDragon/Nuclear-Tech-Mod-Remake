package at.martinthedragon.ntm.screens

import at.martinthedragon.ntm.Main
import at.martinthedragon.ntm.NuclearTags
import at.martinthedragon.ntm.networking.CraftMachineTemplateMessage
import at.martinthedragon.ntm.networking.NuclearPacketHandler
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.IBidiRenderer
import net.minecraft.client.gui.chat.NarratorChatListener
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraft.util.Hand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.fml.client.gui.GuiUtils
import kotlin.math.ceil
import kotlin.math.min

// TODO search bar
class UseTemplateFolderScreen : Screen(NarratorChatListener.NO_TITLE) {
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var templateButtons: List<Button>
    private var itemList = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name).values.toList<Item>()
    private val pagesCount = ceil(itemList.size.toFloat() / RECIPES_PER_PAGE).toInt()
    private var currentPage = 1

    override fun init() {
        backButton = addButton(ChangePageButton(width / 2 - GUI_WIDTH / 2 + 7, height / 2 - 7, false) {
            pageBack()
        })
        nextButton = addButton(ChangePageButton(width / 2 + GUI_WIDTH / 2 - 25, height / 2 - 7, true) {
            pageForward()
        })

        val buttonsStartX = (width - GUI_WIDTH) / 2 + 25
        val buttonsStartY = (height - GUI_HEIGHT) / 2 + 26
        val buttonOffset = 27

        val newButtons = mutableListOf<Button>()
        for (buttonNumberY in 0 until 7) for (buttonNumberX in 0 until 5) {
            newButtons +=
                addButton(CraftButton(
                    buttonsStartX + buttonNumberX * buttonOffset,
                    buttonsStartY + buttonNumberY * buttonOffset,
                    buttonNumberY * 5 + buttonNumberX
                ))
        }
        templateButtons = newButtons

        updateButtonVisibility()
    }

    private fun pageBack() {
        if (currentPage > 1)
            currentPage--
        else currentPage = pagesCount

        updateButtonVisibility()
    }

    private fun pageForward() {
        if (currentPage < pagesCount)
            currentPage++
        else currentPage = 1

        updateButtonVisibility()
    }

    // the index is the button's number
    private fun craftRecipe(index: Int) {
        NuclearPacketHandler.INSTANCE.sendToServer(CraftMachineTemplateMessage(
            itemList[(currentPage - 1) * RECIPES_PER_PAGE + index].defaultInstance
        ))
    }

    private fun updateButtonVisibility() {
        backButton.visible = currentPage > 1
        nextButton.visible = currentPage < pagesCount
        if (currentPage == pagesCount) {
            val itemCount = itemList.size - (currentPage - 1) * RECIPES_PER_PAGE
            if (itemCount != 0) {
                for (i in 0 until itemCount)
                    templateButtons[i].visible = true
                for (i in itemCount until RECIPES_PER_PAGE)
                    templateButtons[i].visible = false
            }
        } else templateButtons.forEach { it.visible = true }
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        @Suppress("DEPRECATION")
        RenderSystem.color4f(1f, 1f, 1f, 1f)
        minecraft!!.textureManager.bind(TEMPLATE_FOLDER_GUI_LOCATION)
        val x = (width - GUI_WIDTH) / 2
        val y = (height - GUI_HEIGHT) / 2
        blit(matrixStack, x, y, 0, 0, GUI_WIDTH, GUI_HEIGHT)

        super.render(matrixStack, mouseX, mouseY, partialTicks)

        // item icons

        val itemsCount = min(itemList.size - (currentPage - 1) * RECIPES_PER_PAGE, RECIPES_PER_PAGE)
        val itemsStartX = (width - GUI_WIDTH) / 2 + 26
        val itemsStartY = (height - GUI_HEIGHT) / 2 + 27
        val itemOffset = 27

        for (itemNumberY in 0 until min(itemsCount / 5, 7)) for (itemNumberX in 0 until 5)
            renderButtonItem(
                itemList[(currentPage - 1) * RECIPES_PER_PAGE + itemNumberY * 5 + itemNumberX],
                itemsStartX + itemNumberX * itemOffset,
                itemsStartY + itemNumberY * itemOffset
            )
        val lastRowItemsAmount = if (itemsCount == RECIPES_PER_PAGE) 0 else itemsCount.rem(5)
        for (lastRowEntry in 0 until lastRowItemsAmount)
            renderButtonItem(
                itemList[(currentPage - 1) * RECIPES_PER_PAGE + itemsCount - lastRowItemsAmount + lastRowEntry],
                itemsStartX + lastRowEntry * itemOffset,
                itemsStartY + itemsCount / 5 * itemOffset
            )

        // item tooltips

        for (i in templateButtons.indices) {
            val button = templateButtons[i]
            if (button.isHovered) {
                val itemStack = itemList[(currentPage - 1) * RECIPES_PER_PAGE + i].defaultInstance
                val font = itemStack.item.getFontRenderer(itemStack) ?: font
                GuiUtils.preItemToolTip(itemStack)
                renderWrappedToolTip(matrixStack, getTooltipFromItem(itemStack), mouseX, mouseY, font)
                GuiUtils.postItemToolTip()
            }
        }

        // page number
        IBidiRenderer.create(font, StringTextComponent("$currentPage / $pagesCount"))
            .renderCentered(matrixStack, width / 2, (height - GUI_HEIGHT) / 2 + 10)
    }

    private fun renderButtonItem(item: Item, x: Int, y: Int) {
        blitOffset = 100
        itemRenderer.blitOffset = 100F
        itemRenderer.renderAndDecorateItem(minecraft!!.player!!, item.defaultInstance, x, y)
        itemRenderer.blitOffset = 0F
        blitOffset = 0
    }

    class ChangePageButton(x: Int, y: Int, val isForward: Boolean, onPress: IPressable) :
        Button(x, y, 18, 18, StringTextComponent.EMPTY, onPress) {

        override fun renderButton(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            @Suppress("DEPRECATION")
            RenderSystem.color4f(1f, 1f, 1f, 1f)
            Minecraft.getInstance().textureManager.bind(TEMPLATE_FOLDER_GUI_LOCATION)

            val x = if (isHovered()) GUI_WIDTH + 18 else GUI_WIDTH
            val y = if (isForward) 36 else 18

            blit(matrixStack, this.x, this.y, x, y, 18, 18)
        }
    }

    override fun isPauseScreen(): Boolean = false

    inner class CraftButton(x: Int, y: Int, val index: Int) : Button(x, y, 18, 18, StringTextComponent.EMPTY, { craftRecipe(index) }) {
        override fun renderButton(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            @Suppress("DEPRECATION")
            RenderSystem.color4f(1f, 1f, 1f, 1f)
            Minecraft.getInstance().textureManager.bind(TEMPLATE_FOLDER_GUI_LOCATION)

            val x = if (isHovered()) GUI_WIDTH + 18 else GUI_WIDTH

            blit(matrixStack, this.x, this.y, x, 0, 18, 18)
        }
    }

    companion object {
        const val GUI_WIDTH = 176
        const val GUI_HEIGHT = 229
        const val RECIPES_PER_PAGE = 35
        val TEMPLATE_FOLDER_GUI_LOCATION = ResourceLocation(Main.MODID, "textures/gui/machine_template_folder.png")
    }
}
