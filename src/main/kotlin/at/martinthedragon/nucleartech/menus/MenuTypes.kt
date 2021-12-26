package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.MENUS
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.registries.RegistryObject

object MenuTypes {
    val safeMenu: RegistryObject<MenuType<SafeMenu>> = MENUS.register("safe") { IForgeMenuType.create(SafeMenu::fromNetwork) }
    val sirenMenu: RegistryObject<MenuType<SirenMenu>> = MENUS.register("siren") { IForgeMenuType.create(SirenMenu::fromNetwork) }
    val anvilMenu: RegistryObject<MenuType<AnvilMenu>> = MENUS.register("anvil") { IForgeMenuType.create(AnvilMenu::fromNetwork) }
    val steamPressMenu: RegistryObject<MenuType<PressMenu>> = MENUS.register("steam_press") { IForgeMenuType.create(PressMenu::fromNetwork) }
    val blastFurnaceMenu: RegistryObject<MenuType<BlastFurnaceMenu>> = MENUS.register("blast_furnace") { IForgeMenuType.create(BlastFurnaceMenu::fromNetwork) }
    val combustionGeneratorMenu: RegistryObject<MenuType<CombustionGeneratorMenu>> = MENUS.register("combustion_generator") { IForgeMenuType.create(CombustionGeneratorMenu::fromNetwork) }
    val electricFurnaceMenu: RegistryObject<MenuType<ElectricFurnaceMenu>> = MENUS.register("electric_furnace") { IForgeMenuType.create(ElectricFurnaceMenu::fromNetwork) }
    val shredderMenu: RegistryObject<MenuType<ShredderMenu>> = MENUS.register("shredder") { IForgeMenuType.create(ShredderMenu::fromNetwork) }
    val littleBoyMenu: RegistryObject<MenuType<LittleBoyMenu>> = MENUS.register("little_boy") { IForgeMenuType.create(LittleBoyMenu::fromNetwork) }
    val fatManMenu: RegistryObject<MenuType<FatManMenu>> = MENUS.register("fat_man") { IForgeMenuType.create(FatManMenu::fromNetwork) }
}
