package at.martinthedragon.nucleartech.screen

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.item.AssemblyTemplateItem
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.networking.CraftMachineTemplateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.searchtree.SearchRegistry
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.ForgeRegistries
import kotlin.math.ceil
import kotlin.math.min

class UseTemplateFolderScreen : Screen(NTechItems.machineTemplateFolder.get().description) {
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var templateButtons: List<Button>
    private lateinit var searchBox: EditBox
    private val itemList = getAllItems()
    private val searchResults: MutableList<ItemStack> = mutableListOf()
    private var pagesCount = ceil(itemList.size.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
    private var currentPage = 1

    override fun init() {
        backButton = addRenderableWidget(ChangePageButton(width / 2 - GUI_WIDTH / 2 + 7, height / 2 - 7, false) {
            pageBack()
        })
        nextButton = addRenderableWidget(ChangePageButton(width / 2 + GUI_WIDTH / 2 - 25, height / 2 - 7, true) {
            pageForward()
        })

        val buttonsStartX = (width - GUI_WIDTH) / 2 + 25
        val buttonsStartY = (height - GUI_HEIGHT) / 2 + 26
        val buttonOffset = 27

        val newButtons = mutableListOf<Button>()
        for (buttonNumberY in 0 until 7) for (buttonNumberX in 0 until 5) {
            newButtons +=
                addRenderableWidget(CraftButton(
                    buttonsStartX + buttonNumberX * buttonOffset,
                    buttonsStartY + buttonNumberY * buttonOffset,
                    buttonNumberY * 5 + buttonNumberX
                ))
        }
        templateButtons = newButtons.toList()

        minecraft!!.keyboardHandler.setSendRepeatsToGui(true)

        val left = (width - GUI_WIDTH) / 2
        val top = (height - GUI_HEIGHT) / 2

        searchBox = addRenderableWidget(EditBox(font, left + 61, top + 213, 54, 10, TranslatableComponent("itemGroup.search")))
        with(searchBox) {
            setMaxLength(50)
            setBordered(false)
            isVisible = true

            setTextColor(0xFFFFFF)
        }

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
            results[(currentPage - 1) * RECIPES_PER_PAGE + index]
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

    override fun render(matrixStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrixStack)
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, TEMPLATE_FOLDER_GUI_LOCATION)
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

        // page number
        GuiComponent.drawCenteredString(matrixStack, font, TextComponent("$currentPage / $pagesCount"), width / 2, (height - GUI_HEIGHT) / 2 + 10, 0xFFFFFF)

        // search bar
        if (searchBox.isFocused) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            RenderSystem.setShaderTexture(0, TEMPLATE_FOLDER_GUI_LOCATION)
            blit(matrixStack, x + 45, y + 211, 176, 54, 72, 12)
        }

        searchBox.render(matrixStack, mouseX, mouseY, partialTicks)

        // item tooltips
        for (i in templateButtons.indices) {
            val button = templateButtons[i]
            if (button.isHoveredOrFocused && button.visible) {
                val itemStack = itemsToShow[(currentPage - 1) * RECIPES_PER_PAGE + i]
                renderComponentTooltip(matrixStack, getTooltipFromItem(itemStack), mouseX, mouseY, font)
            }
        }
    }

    private fun renderButtonItem(item: ItemStack, x: Int, y: Int) {
        blitOffset = 100
        itemRenderer.blitOffset = 100F
        itemRenderer.renderAndDecorateItem(item, x, y)
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
        val searchTree = minecraft!!.getSearchTree(searchTree)
        searchResults.addAll(searchTree.search(searchString.lowercase(getMinecraft().languageManager.selected.javaLocale)))
        currentPage = 1
        val itemsCount = if (searchResults.isEmpty()) itemList.size else searchResults.size
        pagesCount = ceil(itemsCount.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
        updateButtonVisibility()
    }

    class ChangePageButton(x: Int, y: Int, val isForward: Boolean, onPress: OnPress) :
        Button(x, y, 18, 18, TextComponent.EMPTY, onPress) {

        override fun renderButton(matrixStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            RenderSystem.setShaderTexture(0, TEMPLATE_FOLDER_GUI_LOCATION)

            val x = if (isHoveredOrFocused) GUI_WIDTH + 18 else GUI_WIDTH
            val y = if (isForward) 36 else 18

            blit(matrixStack, this.x, this.y, x, y, 18, 18)
        }
    }

    override fun isPauseScreen(): Boolean = false

    inner class CraftButton(x: Int, y: Int, val index: Int) : Button(x, y, 18, 18, TextComponent.EMPTY, { craftRecipe(index) }) {
        override fun renderButton(matrixStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            RenderSystem.setShaderTexture(0, TEMPLATE_FOLDER_GUI_LOCATION)

            val x = if (isHoveredOrFocused) GUI_WIDTH + 18 else GUI_WIDTH

            blit(matrixStack, this.x, this.y, x, 0, 18, 18)
        }
    }

    companion object {
        const val GUI_WIDTH = 176
        const val GUI_HEIGHT = 229
        const val RECIPES_PER_PAGE = 35
        val TEMPLATE_FOLDER_GUI_LOCATION = ntm("textures/gui/machine_template_folder.png")
        val searchTree = SearchRegistry.Key<ItemStack>()

        fun getAllItems(): List<ItemStack> = ForgeRegistries.ITEMS.getTagManager().getTag(NTechTags.Items.FOLDER_STAMPS).map(::ItemStack) +
            ForgeRegistries.ITEMS.getTagManager().getTag(NTechTags.Items.SIREN_TRACKS).map(::ItemStack) +
            AssemblyTemplateItem.getAllTemplates((Minecraft.getInstance().level ?: throw IllegalStateException("Openend template folder without loaded level")).recipeManager).sortedBy { it.displayName.string } +
            ChemPlantTemplateItem.getAllChemTemplates(Minecraft.getInstance().level!!.recipeManager).sortedBy { it.displayName.string }

        fun reloadSearchTree() {
            if (Minecraft.getInstance().level == null) return // outside of session
            NuclearTech.LOGGER.debug("Reloading Machine Template Folder search tree")
            val templateFolderSearchTree = Minecraft.getInstance().getSearchTree(searchTree)
            templateFolderSearchTree.clear()
            getAllItems().forEach(templateFolderSearchTree::add)
            templateFolderSearchTree.refresh()
        }
    }
}
