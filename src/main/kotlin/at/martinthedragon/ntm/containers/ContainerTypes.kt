package at.martinthedragon.ntm.containers

import at.martinthedragon.ntm.RegistriesAndLifecycle.CONTAINERS
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.fml.RegistryObject

@Suppress("UNCHECKED_CAST")
object ContainerTypes {
    val safeContainer: RegistryObject<ContainerType<SafeContainer>> = CONTAINERS.register("safe") {
        IForgeContainerType.create(SafeContainer.Companion::fromNetwork)
    }
    val sirenContainer: RegistryObject<ContainerType<SirenContainer>> = CONTAINERS.register("siren") {
        IForgeContainerType.create(SirenContainer.Companion::fromNetwork)
    }
}
