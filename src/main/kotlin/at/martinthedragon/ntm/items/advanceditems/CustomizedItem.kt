package at.martinthedragon.ntm.items.advanceditems

import at.martinthedragon.ntm.lib.MODID
import at.martinthedragon.ntm.main.CreativeTabs
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

class CustomizedItem(val registryName: String, val customProperties: CustomizedProperties = CustomizedProperties()): Item(customProperties) {

    init {
        setRegistryName(MODID, registryName)
        itemBuffer.add(this)
    }

    override fun getTranslationKey(): String = "item.ntm.$registryName"
    override fun getBurnTime(itemStack: ItemStack?): Int = customProperties.burnTime * 20

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        if (worldIn != null && worldIn.isRemote)
            customProperties.lore.getLoreFromLanguageFile()
        tooltip.addAll(customProperties.lore.lore)
    }

    companion object {
        val itemBuffer = emptyList<CustomizedItem>().toMutableList()
    }

    class CustomizedProperties(
            val burnTime: Int = 0,
            val radiation: Int = 0,
            val group: CreativeTabs? = null,
            val lore: Lore = Lore.empty()
    ) : Item.Properties() {
        init {
            if (group != null) super.group(group.itemGroup)
        }
    }
    class Lore {
        var lore: MutableList<ITextComponent> = emptyList<ITextComponent>().toMutableList()
            private set
        var lines: Int = 0
            private set
        var itemName = ""
            private set

        private var isLoreFetched = false

        constructor(loreList: List<ITextComponent>) {
            lore = loreList.toMutableList()
            lines = loreList.size
            isLoreFetched = true
        }

        constructor(itemName: String) {
            this.itemName = itemName
        }

        private constructor()

        // Using this without any precautions crashes the game
        fun getLoreFromLanguageFile(lines: Int = 0, fetchAgain: Boolean = false) {
            if (itemName.isEmpty()) return
            if (isLoreFetched && !fetchAgain) return

            if (lines > 0) {
                this.lines = lines
                lore = when {
                    lines == 0 -> emptyList<ITextComponent>().toMutableList()
                    lines > 0 -> MutableList(lines - 1) {
                        StringTextComponent(I18n.format("item.ntm.$itemName.lore$it")).setStyle(Style().setColor(TextFormatting.GRAY))
                    }
                    else -> throw IllegalArgumentException("Specified line count is below zero!")
                }
                return
            }

            var lineCount = 0
            val tmpList: MutableList<ITextComponent> = emptyList<ITextComponent>().toMutableList()
            while (true) {
                val tmp = I18n.format("item.ntm.$itemName.lore$lineCount")
                if (tmp == "item.ntm.$itemName.lore$lineCount")
                    break
                else {
                    lineCount++
                    tmpList.add(StringTextComponent(tmp).setStyle(Style().setColor(TextFormatting.GRAY)))
                }
            }
            this.lines = lineCount
            lore = tmpList
        }

        companion object Factory {
            fun empty() = Lore()
        }
    }
}
