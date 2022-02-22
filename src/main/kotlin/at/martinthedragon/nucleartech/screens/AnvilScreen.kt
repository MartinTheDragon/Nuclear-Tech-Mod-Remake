package at.martinthedragon.nucleartech.screens

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.menus.AnvilMenu
import at.martinthedragon.nucleartech.networking.AnvilConstructMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.recipes.StackedIngredient
import at.martinthedragon.nucleartech.recipes.anvil.AnvilConstructingRecipe
import at.martinthedragon.nucleartech.recipes.containerSatisfiesRequirements
import at.martinthedragon.nucleartech.recipes.getItems
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.searchtree.SearchRegistry
import net.minecraft.client.sounds.SoundManager
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.entity.player.Inventory
import kotlin.math.ceil
import kotlin.math.min

class AnvilScreen(anvilMenu: AnvilMenu, playerInventory: Inventory, title: Component) : AbstractContainerScreen<AnvilMenu>(anvilMenu, playerInventory, title) {
    init {
        imageWidth = GUI_WIDTH
        imageHeight = GUI_HEIGHT
        inventoryLabelY = imageHeight - 94
    }

    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var constructButton: Button
    private lateinit var recipeButtons: List<Button>
    private lateinit var searchBox: EditBox
    private val tier = menu.tier
    private val originalRecipeList = menu.playerInventory.player.level.recipeManager.getAllRecipesFor(RecipeTypes.CONSTRUCTING).filter { it.isTierWithinBounds(tier) }
    private val searchResults = mutableListOf<AnvilConstructingRecipe>()
    private var pagesCount = ceil(originalRecipeList.size.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
    private var currentPage = 1

    override fun init() {
        super.init()

        backButton = addRenderableWidget(ChangePageButton(guiLeft + 7, guiTop + 71, false) { pageBack() })
        nextButton = addRenderableWidget(ChangePageButton(guiLeft + 106, guiTop + 71, true) { pageForward() })
        constructButton = addRenderableWidget(ConstructButton(guiLeft + 52, guiTop + 53))

        val buttonsStartX = guiLeft + 16
        val buttonsStartY = guiTop + 71
        val buttonOffset = 18

        val newButtons = mutableListOf<Button>()
        for (buttonNumberY in 0 until 2) for (buttonNumberX in 0 until 5) {
            newButtons += addRenderableWidget(RecipeButton(
                buttonsStartX + buttonNumberX * buttonOffset,
                buttonsStartY + buttonNumberY * buttonOffset,
                buttonNumberY * 5 + buttonNumberX
            ))
        }
        recipeButtons = newButtons.toList()

        getMinecraft().keyboardHandler.setSendRepeatsToGui(true)
        searchBox = addRenderableWidget(EditBox(font, guiLeft + 10, guiTop + 111, 106, 12, TranslatableComponent("itemGroup.search")))
        with(searchBox) {
            setTextColor(0xFFFFFF)
            setTextColorUneditable(0xFFFFFF)
            setResponder(this@AnvilScreen::refreshSearchResults)
            setBordered(false)
            setEditable(true)
            setMaxLength(50)
            value = ""
        }

        updateButtonVisibility()
    }

    override fun removed() {
        super.removed()
        getMinecraft().keyboardHandler.setSendRepeatsToGui(false)
    }

    private fun pageBack() {
        if (currentPage > 1) currentPage--
        else currentPage = pagesCount
        updateButtonVisibility()
    }

    private fun pageForward() {
        if (currentPage < pagesCount) currentPage++
        else currentPage = 1
        updateButtonVisibility()
    }

    private fun updateButtonVisibility() {
        backButton.visible = currentPage > 1
        nextButton.visible = currentPage < pagesCount
        if (currentPage == pagesCount) {
            val recipeCount = (if (searchResults.isEmpty()) originalRecipeList.size else searchResults.size) - (currentPage - 1) * RECIPES_PER_PAGE
            for (i in 0 until recipeCount) recipeButtons[i].visible = true
            for (i in recipeCount until RECIPES_PER_PAGE) recipeButtons[i].visible = false
        } else recipeButtons.forEach { it.visible = true }
        setSelection(-1, -1, -1)
    }

    private fun refreshSearchResults(string: String) {
        searchResults.clear()
        currentPage = 1
        if (string.isBlank()) {
            pagesCount = ceil(originalRecipeList.size.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
            updateButtonVisibility()
            return
        }
        val searchTree = getMinecraft().getSearchTree(searchTree)
        searchResults.addAll(searchTree.search(string.lowercase(getMinecraft().languageManager.selected.javaLocale)).filter { it.isTierWithinBounds(tier) })
        val recipesCount = if (searchResults.isEmpty()) originalRecipeList.size else searchResults.size
        pagesCount = ceil(recipesCount.toFloat() / RECIPES_PER_PAGE).toInt().coerceAtLeast(1)
        updateButtonVisibility()
    }

    private var selected = -1
    private var selectionX = -1
    private var selectionY = -1

    private fun setSelection(index: Int, x: Int, y: Int) {
        selected = index
        selectionX = x
        selectionY = y
    }

    private fun getOverlayTypeForButton(index: Int): AnvilConstructingRecipe.OverlayType {
        val results = if (searchResults.isEmpty()) originalRecipeList else searchResults
        return results[(currentPage - 1) * RECIPES_PER_PAGE + index].overlay
    }

    private fun getSelectedRecipe(): AnvilConstructingRecipe? {
        if (selected == -1) return null
        val results = if (searchResults.isEmpty()) originalRecipeList else searchResults
        return results[(currentPage - 1) * RECIPES_PER_PAGE + selected]
    }

    private fun canConstructSelectedRecipe(): Boolean {
        val selectedRecipe = getSelectedRecipe() ?: return false
        return selectedRecipe.ingredientsList.containerSatisfiesRequirements(menu.playerInventory)
    }

    private fun sendConstructPacket() {
        val recipe = getSelectedRecipe() ?: return
        NuclearPacketHandler.INSTANCE.sendToServer(AnvilConstructMessage(recipe.recipeID, Screen.hasShiftDown()))
    }

    override fun containerTick() {
        super.containerTick()
        searchBox.tick()
    }

    override fun render(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBackground(matrix)
        super.render(matrix, mouseX, mouseY, partialTicks)
        RenderSystem.disableBlend()
        renderFg(matrix, mouseX, mouseY, partialTicks)
        renderTooltip(matrix, mouseX, mouseY)
    }

    private var slideSize = 1

    override fun renderBg(matrix: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, TEXTURE)
        blit(matrix, guiLeft, guiTop, 0, 0, xSize, ySize)

        val slide = (slideSize - 42).coerceIn(0, 1000)
        var multiplier = 1
        while (true) {
            if (slide >= 51 * multiplier) {
                blit(matrix, guiLeft + 125 + 51 * multiplier, guiTop + 17, 125, 17, 54, 108)
                multiplier++
            } else break
        }
        blit(matrix, guiLeft + 125 + slide, guiTop + 17, 125, 17, 54, 108)

        if (searchBox.isFocused) blit(matrix, guiLeft + 8, guiTop + 108, 150, 222, 106, 16)

        val recipeList = if (searchResults.isEmpty()) originalRecipeList else searchResults
        val itemsCount = min(recipeList.size - (currentPage - 1) * RECIPES_PER_PAGE, RECIPES_PER_PAGE)
        val itemsStartX = guiLeft + 17
        val itemsStartY = guiTop + 72
        val itemOffset = 18
        blitOffset = 100
        itemRenderer.blitOffset = 100F
        for (itemNumberY in 0 until min(itemsCount / 5, 2)) for (itemNumberX in 0 until 5) itemRenderer.renderAndDecorateItem(
            recipeList[(currentPage - 1) * RECIPES_PER_PAGE + itemNumberY * 5 + itemNumberX].getDisplay(),
            itemsStartX + itemNumberX * itemOffset,
            itemsStartY + itemNumberY * itemOffset
        )
        val lastRowItemsAmount = if (itemsCount == RECIPES_PER_PAGE) 0 else itemsCount.rem(5)
        for (lastRowEntry in 0 until lastRowItemsAmount) itemRenderer.renderAndDecorateItem(
            recipeList[(currentPage - 1) * RECIPES_PER_PAGE + itemsCount - lastRowItemsAmount + lastRowEntry].getDisplay(),
            itemsStartX + lastRowEntry * itemOffset,
            itemsStartY + itemsCount / 5 * itemOffset
        )
        itemRenderer.blitOffset = 0F
        blitOffset = 0
    }

    private fun renderFg(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (selectionX != -1 && selectionY != -1) {
            RenderSystem.disableDepthTest()
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            RenderSystem.setShaderTexture(0, TEXTURE)
            blit(matrix, selectionX, selectionY, 0, GUI_HEIGHT, 18, 18)
            RenderSystem.enableDepthTest()
        }
        searchBox.render(matrix, mouseX, mouseY, partialTicks)

        val texts = getSelectedRecipeToListFormatted()
        if (texts.isEmpty()) {
            slideSize = 0
            return
        }

        val longest = texts.maxOf { font.width(it) }
        val scale = .5F
        matrix.pushPose()
        matrix.scale(scale, scale, scale)
        var offsetY = 0
        for (component in texts) {
            font.draw(matrix, component, guiLeft * 2 + 260F, guiTop * 2 + 50F + offsetY, 0xFFFFFF)
            offsetY += font.lineHeight
        }
        slideSize = (longest * scale).toInt()
        matrix.popPose()
    }

    override fun renderTooltip(matrix: PoseStack, mouseX: Int, mouseY: Int) {
        super.renderTooltip(matrix, mouseX, mouseY)

        if (!menu.carried.isEmpty) return
        val recipeList = if (searchResults.isEmpty()) originalRecipeList else searchResults
        for (i in recipeButtons.indices) {
            val button = recipeButtons[i]
            if (button.isHoveredOrFocused && button.visible) {
                val itemStack = recipeList[(currentPage - 1) * RECIPES_PER_PAGE + i].getDisplay()
                renderTooltip(matrix, itemStack, mouseX, mouseY)
            }
        }
    }

    override fun keyPressed(key: Int, p_97766_: Int, p_97767_: Int): Boolean {
        if (key == 256) getMinecraft().player!!.closeContainer()
        return if (!searchBox.keyPressed(key, p_97766_, p_97767_) && !searchBox.canConsumeInput()) super.keyPressed(key, p_97766_, p_97767_) else true
    }

    private fun getSelectedRecipeToListFormatted(): List<Component> {
        val selectedRecipe = getSelectedRecipe() ?: return emptyList()
        val (available, missing) = partitionIngredients(selectedRecipe.ingredientsList)
        return buildList {
            add(TranslatableComponent("$CONTAINER_TRANSLATION.inputs").withStyle(ChatFormatting.YELLOW))
            addAll(missing.map { TextComponent("> ").append(it.withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.RED) })
            addAll(available.map { TextComponent("> ").append(it.withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.GREEN) })
            add(TextComponent.EMPTY)
            add(TranslatableComponent("$CONTAINER_TRANSLATION.outputs").withStyle(ChatFormatting.YELLOW))
            addAll(selectedRecipe.results.map { TextComponent("> ${it.stack.count}x ").append(it.stack.hoverName).apply { if (it.chance != 1F) append(" (${it.chance * 100}%)") }})
        }
    }

    private fun partitionIngredients(ingredients: List<StackedIngredient>): Pair<List<MutableComponent>, List<MutableComponent>> {
        val (available, missing) = ingredients.filterNot(StackedIngredient::isEmpty).partition {
            var requiredAmountLeft = it.requiredAmount
            for (stack in menu.playerInventory.getItems()) {
                if (it.test(stack)) {
                    val removeCount = min(stack.count, requiredAmountLeft)
                    stack.count -= removeCount
                    requiredAmountLeft -= removeCount
                }
            }
            requiredAmountLeft <= 0
        }
        return available.map { TextComponent("${it.requiredAmount}x ").append(it.items[0].hoverName) } to
                missing.map { TextComponent("${it.requiredAmount}x ").append(it.items[0].hoverName) }
    }

    private class ChangePageButton(x: Int, y: Int, val isForward: Boolean, onPress: OnPress) : Button(x, y, 9, 36, TextComponent.EMPTY, onPress) {
        override fun renderButton(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            if (!isHoveredOrFocused) return
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            RenderSystem.setShaderTexture(0, TEXTURE)
            val x = if (isForward) GUI_WIDTH + 9 else GUI_WIDTH
            blit(matrix, this.x, this.y, x, 186, width, height)
        }
    }

    private inner class RecipeButton(x: Int, y: Int, val index: Int) : Button(x, y, 18, 18, TextComponent.EMPTY, { setSelection(index, x, y) }) {
        override fun renderButton(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            RenderSystem.disableDepthTest()
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            RenderSystem.setShaderTexture(0, TEXTURE)
            val x = 18 + getOverlayTypeForButton(index).ordinal * 18
            blit(matrix, this.x, this.y, x, GUI_HEIGHT, width, height)
            RenderSystem.enableDepthTest()
            if (isHoveredOrFocused) renderSlotHighlight(matrix, this.x + 1, this.y + 1, blitOffset, slotColor)
        }

        override fun playDownSound(manager: SoundManager) {
            if (selected == index) return
            super.playDownSound(manager)
        }
    }

    private inner class ConstructButton(x: Int, y: Int) : Button(x, y, 18, 18, TextComponent.EMPTY, { sendConstructPacket() }){
        override fun renderButton(matrix: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
            if (!isHoveredOrFocused || !canConstructSelectedRecipe()) return
            RenderSystem.setShader(GameRenderer::getPositionTexShader)
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
            RenderSystem.setShaderTexture(0, TEXTURE)
            blit(matrix, this.x, this.y, GUI_WIDTH, 150, 18, 18)
        }

        override fun playDownSound(manager: SoundManager) {
            if (!canConstructSelectedRecipe()) return
            super.playDownSound(manager)
        }
    }

    companion object {
        const val CONTAINER_TRANSLATION = "container.${NuclearTech.MODID}.anvil"
        private const val GUI_WIDTH = 176
        private const val GUI_HEIGHT = 222
        private const val RECIPES_PER_PAGE = 10
        private val TEXTURE = ntm("textures/gui/anvil.png")
        val searchTree = SearchRegistry.Key<AnvilConstructingRecipe>()

        fun reloadSearchTree() {
            NuclearTech.LOGGER.debug("Reloading Anvil Constructing Recipes search tree")
            val anvilConstructingRecipeSearchTree = Minecraft.getInstance().getSearchTree(searchTree)
            anvilConstructingRecipeSearchTree.clear()
            val level = Minecraft.getInstance().level
            if (level != null) {
                level.recipeManager.getAllRecipesFor(RecipeTypes.CONSTRUCTING).forEach(anvilConstructingRecipeSearchTree::add)
            } else NuclearTech.LOGGER.error("Anvil Constructing Recipes search tree reload failed")
            anvilConstructingRecipeSearchTree.refresh()
        }
    }
}
