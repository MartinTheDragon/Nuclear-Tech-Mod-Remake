package at.martinthedragon.ntm.items.advanceditems

import at.martinthedragon.ntm.items.ModItems
import at.martinthedragon.ntm.lib.MODID
import at.martinthedragon.ntm.main.CreativeTabs
import at.martinthedragon.ntm.main.Main
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import java.util.concurrent.Callable

class CustomizedItem(val registryName: String, val customProperties: CustomizedProperties = CustomizedProperties()): Item(customProperties) {
    init {
        setRegistryName(MODID, registryName)
        ModItems.itemBuffer.add(this)
    }

    override fun getTranslationKey(): String = "item.ntm.$registryName"
    override fun getBurnTime(itemStack: ItemStack?): Int = customProperties.burnTime * 20
    override fun hasEffect(itemStack: ItemStack): Boolean = customProperties.glint || itemStack.isEnchanted

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        if (worldIn != null && worldIn.isRemote) {
            var i = 0
            val list = emptyList<ITextComponent>().toMutableList()
            while (true) {
                val translationKey = "${this.translationKey}.lore$i"
                val tmp = I18n.format(translationKey)
                if (tmp == translationKey) break
                else {
                    list.add(StringTextComponent(tmp).setStyle(Style().setColor(TextFormatting.GRAY)))
                    i++
                }
            }
            tooltip.addAll(list)
        }
    }

    class CustomizedProperties(
            val burnTime: Int = 0,
            val radiation: Int = 0,
            val group: CreativeTabs? = null,
            val glint: Boolean = false,
            val rarity: Rarity = Rarity.COMMON,
            val maxStackSize: Int = 64
    ) : Item.Properties() {
        init {
            if (group != null) {
                super.group(group.itemGroup)
            }
            if (rarity != Rarity.COMMON) {
                super.rarity(rarity)
            }
            if (maxStackSize != 64) {
                super.maxStackSize(maxStackSize)
            }
        }
    }
}
