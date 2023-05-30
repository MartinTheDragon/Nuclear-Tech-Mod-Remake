package at.martinthedragon.nucleartech.menu

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.MENUS
import at.martinthedragon.nucleartech.menu.rbmk.*
import at.martinthedragon.nucleartech.registerK
import net.minecraftforge.common.extensions.IForgeMenuType

object MenuTypes {
    val safeMenu = MENUS.registerK("safe") { IForgeMenuType.create(SafeMenu::fromNetwork) }
    val sirenMenu = MENUS.registerK("siren") { IForgeMenuType.create(SirenMenu::fromNetwork) }
    val anvilMenu = MENUS.registerK("anvil") { IForgeMenuType.create(AnvilMenu::fromNetwork) }
    val steamPressMenu = MENUS.registerK("steam_press") { IForgeMenuType.create(PressMenu::fromNetwork) }
    val blastFurnaceMenu = MENUS.registerK("blast_furnace") { IForgeMenuType.create(BlastFurnaceMenu::fromNetwork) }
    val combustionGeneratorMenu = MENUS.registerK("combustion_generator") { IForgeMenuType.create(CombustionGeneratorMenu::fromNetwork) }
    val electricFurnaceMenu = MENUS.registerK("electric_furnace") { IForgeMenuType.create(ElectricFurnaceMenu::fromNetwork) }
    val shredderMenu = MENUS.registerK("shredder") { IForgeMenuType.create(ShredderMenu::fromNetwork) }
    val assemblerMenu = MENUS.registerK("assembler") { IForgeMenuType.create(AssemblerMenu::fromNetwork) }
    val chemPlantMenu = MENUS.registerK("chem_plant") { IForgeMenuType.create(ChemPlantMenu::fromNetwork) }
    val turbineMenu = MENUS.registerK("turbine") { IForgeMenuType.create(TurbineMenu::fromNetwork) }
    val oilWellMenu = MENUS.registerK("oil_well") { IForgeMenuType.create(OilWellMenu::fromNetwork) }
    val centrifugeMenu = MENUS.registerK("centrifuge") { IForgeMenuType.create(CentrifugeMenu::fromNetwork) }
    val rbmkRodMenu = MENUS.registerK("rbmk_rod") { IForgeMenuType.create(RBMKRodMenu::fromNetwork) }
    val rbmkBoilerMenu = MENUS.registerK("rbmk_boiler") { IForgeMenuType.create(RBMKBoilerMenu::fromNetwork) }
    val rbmkManualControlMenu = MENUS.registerK("rbmk_manual_control") { IForgeMenuType.create(RBMKManualControlMenu::fromNetwork) }
    val rbmkAutoControlMenu = MENUS.registerK("rbmk_auto_control") { IForgeMenuType.create(RBMKAutoControlMenu::fromNetwork) }
    val rbmkConsoleMenu = MENUS.registerK("rbmk_console") { IForgeMenuType.create(RBMKConsoleMenu::fromNetwork) }
    val littleBoyMenu = MENUS.registerK("little_boy") { IForgeMenuType.create(LittleBoyMenu::fromNetwork) }
    val fatManMenu = MENUS.registerK("fat_man") { IForgeMenuType.create(FatManMenu::fromNetwork) }
    val launchPadMenu = MENUS.registerK("launch_pad") { IForgeMenuType.create(LaunchPadMenu::fromNetwork) }
}
