package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.capability.contamination.ContaminationHandler
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.entity.NuclearCreeper
import at.martinthedragon.nucleartech.entity.attribute.Attributes
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.hazard.HazardRegistry
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.item.NuclearArmorMaterials
import at.martinthedragon.nucleartech.menu.MenuTypes
import at.martinthedragon.nucleartech.networking.filter.NTechNetFilters
import at.martinthedragon.nucleartech.particle.ModParticles
import at.martinthedragon.nucleartech.plugin.PluginEvents
import at.martinthedragon.nucleartech.recipe.RecipeSerializers
import at.martinthedragon.nucleartech.recipe.RecipeTypes
import at.martinthedragon.nucleartech.recipe.StackedIngredient
import at.martinthedragon.nucleartech.world.ChunkLoadingValidationCallback
import at.martinthedragon.nucleartech.world.gen.WorldGen
import at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers.MeteoritePlacerType
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleType
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.world.ForgeChunkManager
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.EntityAttributeModificationEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.VersionChecker
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.*
import java.util.function.Supplier

@Suppress("unused")
@Mod.EventBusSubscriber(modid = NuclearTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistriesAndLifecycle {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, NuclearTech.MODID)
    val FLUIDS: DeferredRegister<Fluid> = DeferredRegister.create(ForgeRegistries.FLUIDS, NuclearTech.MODID)
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearTech.MODID)
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, NuclearTech.MODID)
    val ENTITIES: DeferredRegister<EntityType<*>> = DeferredRegister.create(ForgeRegistries.ENTITIES, NuclearTech.MODID)
    val MENUS: DeferredRegister<MenuType<*>> = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuclearTech.MODID)
    val RECIPE_SERIALIZERS: DeferredRegister<RecipeSerializer<*>> = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NuclearTech.MODID)
    val ATTRIBUTES: DeferredRegister<Attribute> = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, NuclearTech.MODID)
    val FEATURES: DeferredRegister<Feature<*>> = DeferredRegister.create(ForgeRegistries.FEATURES, NuclearTech.MODID)
    val SOUNDS: DeferredRegister<SoundEvent> = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NuclearTech.MODID)
    val PARTICLES: DeferredRegister<ParticleType<*>> = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, NuclearTech.MODID)

    val METEORITE_PLACERS: DeferredRegister<MeteoritePlacerType<*>> = DeferredRegister.create(ntm("meteorite_placers"), NuclearTech.MODID)
    val METEORITE_PLACER_REGISTRY: Supplier<IForgeRegistry<MeteoritePlacerType<*>>>

    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        BLOCKS.register(modEventBus)
        NTechBlocks
        FLUIDS.register(modEventBus)
        NTechFluids
        ITEMS.register(modEventBus)
        NTechBlockItems
        NTechItems
        NuclearArmorMaterials
        BLOCK_ENTITIES.register(modEventBus)
        BlockEntityTypes
        ENTITIES.register(modEventBus)
        EntityTypes
        MENUS.register(modEventBus)
        MenuTypes
        RECIPE_SERIALIZERS.register(modEventBus)
        RecipeSerializers
        ATTRIBUTES.register(modEventBus)
        Attributes
        METEORITE_PLACER_REGISTRY = METEORITE_PLACERS.makeRegistry(MeteoritePlacerType::class.java, ::RegistryBuilder)
        METEORITE_PLACERS.register(modEventBus)
        MeteoritePlacerType
        FEATURES.register(modEventBus)
        WorldGen.Features
        SOUNDS.register(modEventBus)
        SoundEvents
        PARTICLES.register(modEventBus)
        ModParticles
    }

    @SubscribeEvent @JvmStatic
    fun commonSetup(event: FMLCommonSetupEvent) {
        NuclearTech.LOGGER.info("Hello World!")

        if (NuclearTech.isSnapshot) {
            NuclearTech.LOGGER.warn("Running a bootleg snapshot version!")
        }

        NuclearTech.LOGGER.debug("Initializing plugins...")
        PluginEvents.init()
        HazardRegistry.registerItems()

        event.enqueueWork {
            NuclearTech.LOGGER.debug("Setting forced chunk loading callback...")
            ForgeChunkManager.setForcedChunkLoadingCallback(NuclearTech.MODID, ChunkLoadingValidationCallback)
        }

        NuclearTech.LOGGER.debug("Registering dispenser behaviours...")
        NTechFluids.registerDispenserBehaviour()

        NTechNetFilters.performTheFunny()
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent @JvmStatic
    fun serverSetup(event: FMLDedicatedServerSetupEvent) {
        val versionCheckResult = NuclearTech.versionCheckResult
        when (versionCheckResult?.status) {
            VersionChecker.Status.PENDING, VersionChecker.Status.FAILED, VersionChecker.Status.UP_TO_DATE, null -> {}
            VersionChecker.Status.BETA, VersionChecker.Status.AHEAD -> {
                NuclearTech.LOGGER.info(if (NuclearTech.isSnapshot) "Running on the bleeding edge!" else "Running on the cutting edge of beta!")
            }
            VersionChecker.Status.OUTDATED, VersionChecker.Status.BETA_OUTDATED -> {
                val updateMessage = StringBuilder()
                updateMessage.appendLine("Hello server administrator! There's a newer version of NTM available: ${versionCheckResult.target}")
                if (versionCheckResult.changes.isNotEmpty()) updateMessage.appendLine("List of changes:")
                for (change in versionCheckResult.changes.values.flatMap { it.split("\r\n", "\n", "\r") }) updateMessage.appendLine(change.prependIndent())
                NuclearTech.LOGGER.info(updateMessage.toString())
            }
        }
    }

    @SubscribeEvent @JvmStatic
    fun createAttributes(event: EntityAttributeCreationEvent) {
        event.put(EntityTypes.nuclearCreeper.get(), NuclearCreeper.createAttributes())
    }

    @SubscribeEvent @JvmStatic
    fun modifyAttributes(event: EntityAttributeModificationEvent) {
        for (type in event.types) event.add(type, Attributes.RADIATION_RESISTANCE.get())
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent @JvmStatic
    fun registerRecipeTypes(event: RegistryEvent.Register<RecipeSerializer<*>>) {
        // no forge registry for recipe types currently available
        RecipeTypes.getTypes().forEach { Registry.register(Registry.RECIPE_TYPE, it.toString(), it) }

        CraftingHelper.register(StackedIngredient.NAME, StackedIngredient.Serializer)
    }

    @SubscribeEvent @JvmStatic
    fun registerCapabilities(event: RegisterCapabilitiesEvent) {
        event.register(ContaminationHandler::class.java)
    }
}

fun <T : IForgeRegistryEntry<T>, R : T> DeferredRegister<T>.registerK(name: String, sup: () -> R): RegistryObject<R> = register(name, sup)
