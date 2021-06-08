@file:Suppress("unused")

package at.martinthedragon.ntm

import at.martinthedragon.ntm.capabilites.CapabilityIrradiationHandler
import at.martinthedragon.ntm.containers.ContainerTypes
import at.martinthedragon.ntm.datagen.*
import at.martinthedragon.ntm.tileentities.TileEntityTypes
import net.minecraft.block.Block
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistryEntry
import net.minecraftforge.registries.RegistryManager

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistriesAndLifecycle {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID)
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID)
    val TILE_ENTITIES: DeferredRegister<TileEntityType<*>> = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.MODID)
    val CONTAINERS: DeferredRegister<ContainerType<*>> = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MODID)

    init {
        BLOCKS.register(FMLJavaModLoadingContext.get().modEventBus)
        ModBlocks
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
        ModBlockItems
        ModItems
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().modEventBus)
        TileEntityTypes
        CONTAINERS.register(FMLJavaModLoadingContext.get().modEventBus)
        ContainerTypes
    }

    // using kotlin's strong type system
    // automatically uses the correct registry for the field
    inline fun <reified T : ForgeRegistryEntry<T>> retrieve(resourceLocation: ResourceLocation): RegistryObject<T> {
        return RegistryObject.of(resourceLocation, RegistryManager.ACTIVE.getRegistry(T::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
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
            dataGenerator.addProvider(NuclearRecipeProvider(dataGenerator))
            dataGenerator.addProvider(NuclearLootProvider(dataGenerator))
        }

        if (event.includeClient()) {
            dataGenerator.addProvider(NuclearBlockStateProvider(dataGenerator, existingFileHelper))
            dataGenerator.addProvider(NuclearItemModelProvider(dataGenerator, existingFileHelper))
        }
    }
}
