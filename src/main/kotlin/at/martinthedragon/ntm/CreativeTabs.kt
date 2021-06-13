package at.martinthedragon.ntm

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

enum class CreativeTabs(val itemGroup: ItemGroup) {
    Parts(
        object : ItemGroup("ntm_parts") {
            override fun makeIcon() = ItemStack(ModItems.uraniumIngot.get())
        }
    ),
    Items(
        object : ItemGroup("ntm_items") {
            override fun makeIcon() = ItemStack(ModItems.stoneFlatStamp.get()) // TODO
        }
    ),
    Templates(
        object : ItemGroup("ntm_templates") {
            override fun makeIcon() = ItemStack(ModItems.machineTemplateFolder.get())
        }
    ),
    Blocks(
        object : ItemGroup("ntm_blocks") {
            override fun makeIcon() = ItemStack(ModBlockItems.uraniumOre.get())
        }
    ),
    Machines(
        object : ItemGroup("ntm_machines") {
            override fun makeIcon() = ItemStack(ModBlockItems.safe.get()) // TODO
        }
    )
}
