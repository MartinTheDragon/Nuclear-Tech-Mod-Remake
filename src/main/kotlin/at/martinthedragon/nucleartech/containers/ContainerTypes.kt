package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.CONTAINERS
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.fml.RegistryObject

object ContainerTypes {
    val safeContainer: RegistryObject<ContainerType<SafeContainer>> = CONTAINERS.register("safe") { IForgeContainerType.create(SafeContainer::fromNetwork) }
    val sirenContainer: RegistryObject<ContainerType<SirenContainer>> = CONTAINERS.register("siren") { IForgeContainerType.create(SirenContainer::fromNetwork) }
    val steamPressContainer: RegistryObject<ContainerType<PressContainer>> = CONTAINERS.register("steam_press") { IForgeContainerType.create(PressContainer::fromNetwork) }
    val blastFurnaceContainer: RegistryObject<ContainerType<BlastFurnaceContainer>> = CONTAINERS.register("blast_furnace") { IForgeContainerType.create(BlastFurnaceContainer::fromNetwork) }
    val combustionGeneratorContainer: RegistryObject<ContainerType<CombustionGeneratorContainer>> = CONTAINERS.register("combustion_generator") { IForgeContainerType.create(CombustionGeneratorContainer::fromNetwork) }
    val electricFurnaceContainer: RegistryObject<ContainerType<ElectricFurnaceContainer>> = CONTAINERS.register("electric_furnace") { IForgeContainerType.create(ElectricFurnaceContainer::fromNetwork) }
    val shredderContainer: RegistryObject<ContainerType<ShredderContainer>> = CONTAINERS.register("shredder") { IForgeContainerType.create(ShredderContainer::fromNetwork) }
    val littleBoyContainer: RegistryObject<ContainerType<LittleBoyContainer>> = CONTAINERS.register("little_boy") { IForgeContainerType.create(LittleBoyContainer::fromNetwork) }
    val fatManContainer: RegistryObject<ContainerType<FatManContainer>> = CONTAINERS.register("fat_man") { IForgeContainerType.create(FatManContainer::fromNetwork) }
}
