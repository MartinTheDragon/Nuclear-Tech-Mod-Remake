package at.martinthedragon.ntm.containers

import at.martinthedragon.ntm.RegistriesAndLifecycle.register
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.common.extensions.IForgeContainerType

@Suppress("UNCHECKED_CAST")
object ContainerTypes {
    val safeContainer: ContainerType<SafeContainer> = IForgeContainerType.create(SafeContainer.Companion::fromNetwork).setRegistryName("safe").register() as ContainerType<SafeContainer>
    val sirenContainer: ContainerType<SirenContainer> = IForgeContainerType.create(SirenContainer.Companion::fromNetwork).setRegistryName("siren").register() as ContainerType<SirenContainer>
}
