package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.networking.CraftMachineTemplateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.IBidiRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.client.util.SearchTreeManager
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fml.client.gui.GuiUtils
import java.util.*
import kotlin.math.ceil
import kotlin.math.min

class UseTemplateFolderScreen : Screen(ModItems.machineTemplateFolder.get().description) {
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var templateButtons: List<Button>
    private lateinit var searchBox: TextFieldWidget
    private val itemList = ItemTags.getAllTags().getTagOrEmpty(NuclearTags.Items.MACHINE_TEMPLATE_FOLDER_RESULTS.name).values.toList<Item>()
    private val searchResults: MutableList<Item> = mutableListOf()
    private var pagesCount = ceil(itemList.size.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
    private var currentPage = 1

    override fun init() {
        backButton = addButton(ChangePageButton(width / 2 - GUI_WIDTH / 2 + 7, height / 2 - 7, false) {
            pageBack()
        })
        nextButton = addButton(ChangePageButton(width / 2 + GUI_WIDTH / 2 - 25, height / 2 - 7, true) {
            pageForward()
        })

        children.add(backButton)
        children.add(nextButton)

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
        templateButtons = newButtons.toList()
        children.addAll(newButtons)

        minecraft!!.keyboardHandler.setSendRepeatsToGui(true)

        val left = (width - GUI_WIDTH) / 2
        val top = (height - GUI_HEIGHT) / 2

        searchBox = TextFieldWidget(font, left + 61, top + 213, 54, 10, TranslationTextComponent("itemGroup.search"))
        with(searchBox) {
            setMaxLength(50)
            setBordered(false)
            isVisible = true

            setTextColor(0xFFFFFF)
        }

        children.add(searchBox)

        updateButtonVisibility()
    }

    override fun resize(minecraft: Minecraft, sizeX: Int, sizeY: Int) {
        val searchString = searchBox.value
        super.resize(minecraft, sizeX, sizeY)
        searchBox.value = searchString
        if (searchBox.value.isNotBlank())
            refreshSearchResults()
    }

    override fun removed() {
        super.removed()
        minecraft!!.keyboardHandler.setSendRepeatsToGui(false)
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
        val results = if (searchResults.isEmpty()) itemList else searchResults
        NuclearPacketHandler.INSTANCE.sendToServer(CraftMachineTemplateMessage(
            results[(currentPage - 1) * RECIPES_PER_PAGE + index].defaultInstance
        ))
    }

    private fun updateButtonVisibility() {
        backButton.visible = currentPage > 1
        nextButton.visible = currentPage < pagesCount
        if (currentPage == pagesCount) {
            val itemCount = (if (searchResults.isEmpty()) itemList.size else searchResults.size) - (currentPage - 1) * RECIPES_PER_PAGE
            for (i in 0 until itemCount)
                templateButtons[i].visible = true
            for (i in itemCount until RECIPES_PER_PAGE)
                templateButtons[i].visible = false
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

        val itemsToShow = if (searchResults.isEmpty()) itemList else searchResults

        // item icons

        val itemsCount = min(itemsToShow.size - (currentPage - 1) * RECIPES_PER_PAGE, RECIPES_PER_PAGE)
        val itemsStartX = (width - GUI_WIDTH) / 2 + 26
        val itemsStartY = (height - GUI_HEIGHT) / 2 + 27
        val itemOffset = 27

        for (itemNumberY in 0 until min(itemsCount / 5, 7)) for (itemNumberX in 0 until 5)
            renderButtonItem(
                itemsToShow[(currentPage - 1) * RECIPES_PER_PAGE + itemNumberY * 5 + itemNumberX],
                itemsStartX + itemNumberX * itemOffset,
                itemsStartY + itemNumberY * itemOffset
            )
        val lastRowItemsAmount = if (itemsCount == RECIPES_PER_PAGE) 0 else itemsCount.rem(5)
        for (lastRowEntry in 0 until lastRowItemsAmount)
            renderButtonItem(
                itemsToShow[(currentPage - 1) * RECIPES_PER_PAGE + itemsCount - lastRowItemsAmount + lastRowEntry],
                itemsStartX + lastRowEntry * itemOffset,
                itemsStartY + itemsCount / 5 * itemOffset
            )

        // item tooltips

        for (i in templateButtons.indices) {
            val button = templateButtons[i]
            if (button.isHovered && button.visible) {
                val itemStack = itemsToShow[(currentPage - 1) * RECIPES_PER_PAGE + i].defaultInstance
                val font = itemStack.item.getFontRenderer(itemStack) ?: font
                GuiUtils.preItemToolTip(itemStack)
                renderWrappedToolTip(matrixStack, getTooltipFromItem(itemStack), mouseX, mouseY, font)
                GuiUtils.postItemToolTip()
            }
        }

        // page number
        IBidiRenderer.create(font, StringTextComponent("$currentPage / $pagesCount"))
            .renderCentered(matrixStack, width / 2, (height - GUI_HEIGHT) / 2 + 10)

        // search bar
        if (searchBox.isFocused) {
            minecraft!!.textureManager.bind(TEMPLATE_FOLDER_GUI_LOCATION)
            blit(matrixStack, x + 45, y + 211, 176, 54, 72, 12)
        }

        searchBox.render(matrixStack, mouseX, mouseY, partialTicks)
    }

    private fun renderButtonItem(item: Item, x: Int, y: Int) {
        blitOffset = 100
        itemRenderer.blitOffset = 100F
        itemRenderer.renderAndDecorateItem(minecraft!!.player!!, item.defaultInstance, x, y)
        itemRenderer.blitOffset = 0F
        blitOffset = 0
    }

    override fun keyPressed(p_231046_1_: Int, p_231046_2_: Int, p_231046_3_: Int): Boolean {
        searchBox.setFocus(true)
        val searchString = searchBox.value
        if (searchBox.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
            if (searchString != searchBox.value)
                refreshSearchResults()
            return true
        }

        return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)
    }

    override fun charTyped(char: Char, p_231042_2_: Int): Boolean {
        searchBox.setFocus(true)
        val searchString = searchBox.value
        return if (searchBox.charTyped(char, p_231042_2_)) {
            if (searchString != searchBox.value)
                refreshSearchResults()
            true
        } else false
    }

    private fun refreshSearchResults() {
        searchResults.clear()

        val searchString = searchBox.value
        if (searchString.isBlank()) return
        val searchTree = minecraft!!.getSearchTree(SEARCH_TREE)
        searchResults.addAll(searchTree.search(searchString.lowercase(Locale.ROOT)).map(ItemStack::getItem))
        currentPage = 1
        val itemsCount = if (searchResults.isEmpty()) itemList.size else searchResults.size
        pagesCount = ceil(itemsCount.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
        updateButtonVisibility()
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
        val TEMPLATE_FOLDER_GUI_LOCATION = ResourceLocation(NuclearTech.MODID, "textures/gui/machine_template_folder.png")
        val SEARCH_TREE = SearchTreeManager.Key<ItemStack>()
    }
}
