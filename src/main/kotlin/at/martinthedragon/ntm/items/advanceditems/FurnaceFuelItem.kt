package at.martinthedragon.ntm.items.advanceditems

import net.minecraft.item.ItemStack

class FurnaceFuelItem(registryName: String, private val burnTime: Int, customizedProperties: CustomizedProperties = CustomizedProperties()) : CustomizedItem(registryName, customizedProperties) {
    override fun getBurnTime(itemStack: ItemStack?): Int = burnTime * 20
}
