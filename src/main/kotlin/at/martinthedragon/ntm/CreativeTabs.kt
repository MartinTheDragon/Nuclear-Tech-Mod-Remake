package at.martinthedragon.ntm

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

enum class CreativeTabs(val itemGroup: ItemGroup) {
    Parts(
            object : ItemGroup("ntm_parts") {
                override fun createIcon() = ItemStack(I.uraniumIngot)
            }
    ),
    Blocks(
            object : ItemGroup("ntm_blocks") {
                override fun createIcon() = ItemStack(BI.uraniumOre)
            }
    ),
    Templates(
            object : ItemGroup("ntm_templates") {
                override fun createIcon() = ItemStack(I.sirenTrackAirRaidSiren) // TODO
            }
    ),
    Machines(
            object : ItemGroup("ntm_machines") {
                override fun createIcon() = ItemStack(BI.safe) // TODO
            }
    )
}
