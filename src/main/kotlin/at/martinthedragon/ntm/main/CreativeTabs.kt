package at.martinthedragon.ntm.main

import at.martinthedragon.ntm.blocks.ModBlocks
import at.martinthedragon.ntm.items.ModItems
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

enum class CreativeTabs(val itemGroup: ItemGroup) {
    PARTS_TAB(object : ItemGroup("ntm_parts") {
        override fun createIcon(): ItemStack = ItemStack(ModItems.URANIUM_INGOT)
    }),
    BLOCKS_TAB(object : ItemGroup("ntm_blocks") {
        override fun createIcon(): ItemStack = ItemStack(ModBlocks.URANIUM_ORE)
    })
}
