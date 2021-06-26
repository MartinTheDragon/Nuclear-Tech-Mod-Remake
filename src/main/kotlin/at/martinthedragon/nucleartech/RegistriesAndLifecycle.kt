package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.capabilites.CapabilityIrradiationHandler
import at.martinthedragon.nucleartech.containers.ContainerTypes
import at.martinthedragon.nucleartech.datagen.*
import at.martinthedragon.nucleartech.recipes.RecipeSerializers
import at.martinthedragon.nucleartech.recipes.RecipeTypes
import at.martinthedragon.nucleartech.tileentities.TileEntityTypes
import net.minecraft.block.Block
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
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

@Suppress("unused")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistriesAndLifecycle {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, NuclearTech.MODID)
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearTech.MODID)
    val TILE_ENTITIES: DeferredRegister<TileEntityType<*>> = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NuclearTech.MODID)
    val CONTAINERS: DeferredRegister<ContainerType<*>> = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuclearTech.MODID)
    val RECIPE_SERIALIZERS: DeferredRegister<IRecipeSerializer<*>> = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NuclearTech.MODID)

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
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().modEventBus)
        RecipeSerializers
    }

    // using kotlin's strong type system
    // automatically uses the correct registry for the field
    inline fun <reified T : ForgeRegistryEntry<T>> retrieve(resourceLocation: ResourceLocation): RegistryObject<T> {
        return RegistryObject.of(resourceLocation, RegistryManager.ACTIVE.getRegistry(T::class.java))
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent @JvmStatic
    fun commonSetup(event: FMLCommonSetupEvent) {
        NuclearTech.LOGGER.info("Hello World!")
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

    // this is currently needed as no forge registry exists for recipe types
    @SubscribeEvent @JvmStatic
    fun registerRecipeTypes(event: RegistryEvent.Register<IRecipeSerializer<*>>) {
        RecipeTypes.registerTypes()
    }
}
