package at.martinthedragon.nucleartech.containers

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.CONTAINERS
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.fml.RegistryObject

object ContainerTypes {
    val safeContainer: RegistryObject<ContainerType<SafeContainer>> = CONTAINERS.register("safe") {
        IForgeContainerType.create(SafeContainer.Companion::fromNetwork)
    }
    val sirenContainer: RegistryObject<ContainerType<SirenContainer>> = CONTAINERS.register("siren") {
        IForgeContainerType.create(SirenContainer.Companion::fromNetwork)
    }
    val pressContainer: RegistryObject<ContainerType<PressContainer>> = CONTAINERS.register("press") {
        IForgeContainerType.create(PressContainer.Companion::fromNetwork)
    }
    val blastFurnaceContainer: RegistryObject<ContainerType<BlastFurnaceContainer>> = CONTAINERS.register("blast_furnace") {
        IForgeContainerType.create(BlastFurnaceContainer.Companion::fromNetwork)
    }
    val combustionGeneratorContainer: RegistryObject<ContainerType<CombustionGeneratorContainer>> = CONTAINERS.register("combustion_generator") {
        IForgeContainerType.create(CombustionGeneratorContainer.Companion::fromNetwork)
    }
    val electricFurnaceContainer: RegistryObject<ContainerType<ElectricFurnaceContainer>> = CONTAINERS.register("electric_furnace") {
        IForgeContainerType.create(ElectricFurnaceContainer.Companion::fromNetwork)
    }
}
