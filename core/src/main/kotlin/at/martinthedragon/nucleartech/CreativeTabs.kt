package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.coremodules.forge.registries.RegistryObject
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.CreativeModeTab
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.item.NTechItemsCore
import java.util.function.Supplier

enum class CreativeTabs(val tab: CreativeModeTab) {
    Parts(createTab("parts", NTechItemsCore::uraniumIngot)),
    Items(createTab("items", NTechItemsCore::stoneFlatStamp)), // TODO
    Templates(createTab("templates", NTechItemsCore::machineTemplateFolder)),
    Blocks(createTab("blocks", NTechBlockItemsCore::uraniumOre)),
    Machines(createTab("machines", NTechBlockItemsCore::safe)), // TODO
    Bombs(createTab("bombs", NTechBlockItemsCore::fatMan).setBackgroundImage(ntm("textures/gui/creative_inventory/tab_nuke.png"))),
    Rocketry(createTab("rocketry", NTechItemsCore::nuclearMissile)),
    Consumables(createTab("consumables", NTechItemsCore::oilDetector)),
    Miscellaneous(createTab("miscellaneous", NTechItemsCore::nuclearCreeperSpawnEgg))
}

private fun createTab(name: String, iconItem: Supplier<out RegistryObject<out Item>>) = CreativeModeTab("${MOD_ID}_$name") { ItemStack(iconItem.get().get()) }
