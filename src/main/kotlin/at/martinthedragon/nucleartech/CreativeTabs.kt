package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

enum class CreativeTabs(val tab: CreativeModeTab) {
    Parts(createTab("parts", NTechItems::uraniumIngot)),
    Items(createTab("items", NTechItems::stoneFlatStamp)), // TODO
    Templates(createTab("templates", NTechItems::machineTemplateFolder)),
    Blocks(createTab("blocks", NTechBlockItems::uraniumOre)),
    Machines(createTab("machines", NTechBlockItems::safe)), // TODO
    Bombs(createTab("bombs", NTechBlockItems::fatMan).setBackgroundImage(ntm("textures/gui/creative_inventory/tab_nuke.png"))),
    Rocketry(createTab("rocketry", NTechItems::nuclearMissile)),
    Consumables(createTab("consumables", NTechItems::oilDetector)),
    Miscellaneous(createTab("miscellaneous", NTechItems::nuclearCreeperSpawnEgg))
}

private fun createTab(name: String, iconItem: Supplier<out RegistryObject<out Item>>) = object : CreativeModeTab("${NuclearTech.MODID}_$name") {
    override fun makeIcon() = ItemStack(iconItem.get().get()) // do not cache, because Forge plans on making registries reloadable
}
