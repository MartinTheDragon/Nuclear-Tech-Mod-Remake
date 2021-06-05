package at.martinthedragon.ntm

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

enum class CreativeTabs(val itemGroup: ItemGroup) {
    Parts(
            object : ItemGroup("ntm_parts") {
                override fun makeIcon() = ItemStack(I.uraniumIngot.get())
            }
    ),
    Blocks(
            object : ItemGroup("ntm_blocks") {
                override fun makeIcon() = ItemStack(BI.uraniumOre.get())
            }
    ),
    Templates(
            object : ItemGroup("ntm_templates") {
                override fun makeIcon() = ItemStack(I.sirenTrackAirRaidSiren.get()) // TODO
            }
    ),
    Machines(
            object : ItemGroup("ntm_machines") {
                override fun makeIcon() = ItemStack(BI.safe.get()) // TODO
            }
    )
}
