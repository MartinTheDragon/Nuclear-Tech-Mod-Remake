package at.martinthedragon.nucleartech

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

enum class CreativeTabs(val itemGroup: ItemGroup) {
    Parts(
        object : ItemGroup("nucleartech_parts") {
            override fun makeIcon() = ItemStack(ModItems.uraniumIngot.get())
        }
    ),
    Items(
        object : ItemGroup("nucleartech_items") {
            override fun makeIcon() = ItemStack(ModItems.stoneFlatStamp.get()) // TODO
        }
    ),
    Templates(
        object : ItemGroup("nucleartech_templates") {
            override fun makeIcon() = ItemStack(ModItems.machineTemplateFolder.get())
        }
    ),
    Blocks(
        object : ItemGroup("nucleartech_blocks") {
            override fun makeIcon() = ItemStack(ModBlockItems.uraniumOre.get())
        }
    ),
    Machines(
        object : ItemGroup("nucleartech_machines") {
            override fun makeIcon() = ItemStack(ModBlockItems.safe.get()) // TODO
        }
    )
}
