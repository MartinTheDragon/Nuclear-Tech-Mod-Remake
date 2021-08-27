package at.martinthedragon.nucleartech

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import java.util.function.Supplier

enum class CreativeTabs(val itemGroup: ItemGroup) {
    Parts(createItemGroup("parts", ModItems.uraniumIngot)),
    Items(createItemGroup("items", ModItems.stoneFlatStamp)), // TODO
    Templates(createItemGroup("templates", ModItems.machineTemplateFolder)),
    Blocks(createItemGroup("blocks", ModBlockItems.uraniumOre)),
    Machines(createItemGroup("machines", ModBlockItems.safe)), // TODO
    Consumables(createItemGroup("consumables", ModItems.oilDetector)),
    Miscellaneous(createItemGroup("miscellaneous", ModItems.nuclearCreeperSpawnEgg))
}

private fun createItemGroup(name: String, iconItem: Supplier<out Item>) = object : ItemGroup("${NuclearTech.MODID}_$name") {
    override fun makeIcon() = ItemStack(iconItem.get()) // do not cache, because Forge plans on making registries reloadable
}
