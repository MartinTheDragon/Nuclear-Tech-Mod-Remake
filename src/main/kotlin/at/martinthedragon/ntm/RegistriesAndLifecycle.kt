@file:Suppress("unused")

package at.martinthedragon.ntm

import at.martinthedragon.ntm.capabilites.CapabilityIrradiationHandler
import at.martinthedragon.ntm.containers.ContainerTypes
import at.martinthedragon.ntm.datagen.BlockTagProvider
import at.martinthedragon.ntm.datagen.ItemTagProvider
import at.martinthedragon.ntm.tileentities.TileEntityTypes
import net.minecraft.block.Block
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.ForgeRegistryEntry
import net.minecraftforge.registries.RegistryManager

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistriesAndLifecycle {
    private val blocksToRegister = mutableListOf<Block>()
    private val itemsToRegister = mutableListOf<Item>()
    private val tileEntitiesToRegister = mutableListOf<TileEntityType<*>>()
    private val containerTypesToRegister = mutableListOf<ContainerType<*>>()

    // using kotlin's strong type system
    // automatically uses the correct registry for the field
    inline fun <reified T : ForgeRegistryEntry<T>> retrieve(resourceLocation: ResourceLocation): RegistryObject<T> {
        return RegistryObject.of(resourceLocation, RegistryManager.ACTIVE.getRegistry(T::class.java))
    }

    @SubscribeEvent @JvmStatic
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        Main.logger.debug("Registering blocks...")
        ModBlocks
        event.registry.registerAll(*blocksToRegister.toTypedArray())
        Main.logger.debug("Blocks registered")
    }

    @SubscribeEvent @JvmStatic
    fun registerItems(event: RegistryEvent.Register<Item>) {
        Main.logger.debug("Registering items...")
        ModBlockItems
        ModItems
        event.registry.registerAll(*itemsToRegister.toTypedArray())
        Main.logger.debug("Items registered")
    }

    @SubscribeEvent @JvmStatic
    fun registerTileEntities(event: RegistryEvent.Register<TileEntityType<*>>) {
        Main.logger.debug("Registering TileEntityTypes...")
        TileEntityTypes
        event.registry.registerAll(*tileEntitiesToRegister.toTypedArray())
        Main.logger.debug("TileEntityTypes registered")
    }

    @SubscribeEvent @JvmStatic
    fun registerContainers(event: RegistryEvent.Register<ContainerType<*>>) {
        Main.logger.debug("Registering ContainerTypes...")
        ContainerTypes
        event.registry.registerAll(*containerTypesToRegister.toTypedArray())
        Main.logger.debug("ContainerTypes registered")
    }

    @SubscribeEvent @JvmStatic
    fun commonSetup(event: FMLCommonSetupEvent) {
        Main.logger.info("Hello World!")
        Config.createConfig()
        CapabilityIrradiationHandler.register()
    }

    @SubscribeEvent @JvmStatic
    fun generateData(event: GatherDataEvent) {
        val dataGenerator = event.generator
        val existingFileHelper = event.existingFileHelper
        if (event.includeServer()) {
            val blockTagProvider = BlockTagProvider(dataGenerator, existingFileHelper)
            dataGenerator.addProvider(blockTagProvider)
            dataGenerator.addProvider(ItemTagProvider(dataGenerator, blockTagProvider, existingFileHelper))
        }
    }

    fun Block.register(): Block = apply { blocksToRegister += this }.also { Main.logger.debug("Registering Block ${it.registryName}") }
    fun Item.register(): Item = apply { itemsToRegister += this }.also { Main.logger.debug("Registering Item ${it.registryName}") }
    fun <T : TileEntity> TileEntityType<T>.register() = apply { tileEntitiesToRegister += this }.also { Main.logger.debug("Registering TileEntity ${it.registryName}") }
    fun <T : Container> ContainerType<T>.register() = apply { containerTypesToRegister += this }.also { Main.logger.debug("Registering Container ${it.registryName}") }
}
