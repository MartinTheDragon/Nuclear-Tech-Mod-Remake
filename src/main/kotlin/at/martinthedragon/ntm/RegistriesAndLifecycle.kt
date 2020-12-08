@file:Suppress("unused")

package at.martinthedragon.ntm

import at.martinthedragon.ntm.containers.ContainerTypes
import at.martinthedragon.ntm.tileentities.TileEntityTypes
import net.minecraft.block.Block
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistriesAndLifecycle {
    private val blocksToRegister = mutableListOf<Block>()
    private val itemsToRegister = mutableListOf<Item>()
    private val tileEntitiesToRegister = mutableListOf<TileEntityType<*>>()
    private val containerTypesToRegister = mutableListOf<ContainerType<*>>()

    @SubscribeEvent
    @JvmStatic
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        Main.logger.debug("Registering blocks...")
        ModBlocks
        event.registry.registerAll(*blocksToRegister.toTypedArray())
        Main.logger.debug("Blocks registered")
    }

    @SubscribeEvent
    @JvmStatic
    fun registerItems(event: RegistryEvent.Register<Item>) {
        Main.logger.debug("Registering items...")
        ModBlockItems
        ModItems
        event.registry.registerAll(*itemsToRegister.toTypedArray())
        Main.logger.debug("Items registered")
    }

    @SubscribeEvent
    @JvmStatic
    fun registerTileEntities(event: RegistryEvent.Register<TileEntityType<*>>) {
        Main.logger.debug("Registering TileEntityTypes...")
        TileEntityTypes
        event.registry.registerAll(*tileEntitiesToRegister.toTypedArray())
        Main.logger.debug("TileEntityTypes registered")
    }

    @SubscribeEvent
    @JvmStatic
    fun registerContainers(event: RegistryEvent.Register<ContainerType<*>>) {
        Main.logger.debug("Registering ContainerTypes...")
        ContainerTypes
        event.registry.registerAll(*containerTypesToRegister.toTypedArray())
        Main.logger.debug("ContainerTypes registered")
    }

    @SubscribeEvent
    @JvmStatic
    fun commonSetup(event: FMLCommonSetupEvent) {
        Main.logger.info("Hello World!")
        Config.createConfig()
    }

    fun Block.register(): Block = apply { blocksToRegister += this }.also { Main.logger.debug("Registering Block ${it.registryName}") }
    fun Item.register(): Item = apply { itemsToRegister += this }.also { Main.logger.debug("Registering Item ${it.registryName}") }
    fun <T : TileEntity> TileEntityType<T>.register() = apply { tileEntitiesToRegister += this }.also { Main.logger.debug("Registering TileEntity ${it.registryName}") }
    fun <T : Container> ContainerType<T>.register() = apply { containerTypesToRegister += this }.also { Main.logger.debug("Registering Container ${it.registryName}") }
}
