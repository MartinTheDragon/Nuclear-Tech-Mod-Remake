package at.martinthedragon.ntm.main

import at.martinthedragon.ntm.items.ModItems
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import javax.annotation.Nonnull

enum class CreativeTabs(public val itemGroup: ItemGroup) {
    PARTS_TAB(object : ItemGroup("ntm_parts") {
        @OnlyIn(Dist.CLIENT)
        @Nonnull
        override fun createIcon(): ItemStack {
            return ItemStack(ModItems.URANIUM_INGOT)
        }
    })
}
