package at.martinthedragon.nucleartech

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.function.Supplier

enum class CreativeTabs(val itemGroup: CreativeModeTab) {
    Parts(createItemGroup("parts", ModItems.uraniumIngot)),
    Items(createItemGroup("items", ModItems.stoneFlatStamp)), // TODO
    Templates(createItemGroup("templates", ModItems.machineTemplateFolder)),
    Blocks(createItemGroup("blocks", ModBlockItems.uraniumOre)),
    Machines(createItemGroup("machines", ModBlockItems.safe)), // TODO
    Bombs(createItemGroup("bombs", ModBlockItems.fatMan).setBackgroundImage(ntm("textures/gui/creative_inventory/tab_nuke.png"))),
    Consumables(createItemGroup("consumables", ModItems.oilDetector)),
    Miscellaneous(createItemGroup("miscellaneous", ModItems.nuclearCreeperSpawnEgg))
}

private fun createItemGroup(name: String, iconItem: Supplier<out Item>) = object : CreativeModeTab("${NuclearTech.MODID}_$name") {
    override fun makeIcon() = ItemStack(iconItem.get()) // do not cache, because Forge plans on making registries reloadable
}
