package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCKS
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FLUIDS
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.delegate.LateRegistryProperty
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.registerK
import net.minecraft.core.BlockSource
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.*
import net.minecraft.world.level.block.DispenserBlock
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Material
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fluids.ForgeFlowingFluid.*
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object NTechFluids {
    private val fluids = mutableListOf<FluidObject<*, *, *, *>>()

    private const val ABSOLUTE_ZERO = -273

    val spentSteam = registerFluid("spent_steam", FluidAttributes.builder(ntm("fluid/spent_steam"), ntm("fluid/spent_steam")).gaseous().sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steam = registerFluid("steam", FluidAttributes.builder(ntm("fluid/steam"), ntm("fluid/steam")).density(-100).gaseous().temperature(100 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steamHot = registerFluid("hot_steam", FluidAttributes.builder(ntm("fluid/hot_steam"), ntm("fluid/hot_steam")).density(-10).gaseous().temperature(300 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steamSuperHot = registerFluid("super_hot_steam", FluidAttributes.builder(ntm("fluid/super_hot_steam"), ntm("fluid/super_hot_steam")).density(0).gaseous().temperature(450 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val steamUltraHot = registerFluid("ultra_hot_steam", FluidAttributes.builder(ntm("fluid/ultra_hot_steam"), ntm("fluid/ultra_hot_steam")).density(10).gaseous().temperature(600 - ABSOLUTE_ZERO).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem)
    val oil = registerFluid("oil", FluidAttributes.builder(ntm("fluid/oil_still"), ntm("fluid/oil_flow")), ::Source, ::Flowing, ::BucketItem, propertiesModifier = { tickRate(30).levelDecreasePerBlock(5) })
    val gas = registerFluid("gas", FluidAttributes.builder(ntm("fluid/gas_still"), ntm("fluid/gas_still")).gaseous().density(-100).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(10) })
    val uraniumHexafluoride = registerFluid("uranium_hexafluoride", FluidAttributes.builder(ntm("fluid/uranium_hexafluoride_still"), ntm("fluid/uranium_hexafluoride_flow")).color(0xE6D1CEBEu.toInt()).gaseous().sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(10) })
    val corium = registerFluid("corium_fluid", FluidAttributes.builder(ntm("fluid/corium_still"), ntm("fluid/corium_flow")).density(100_000).temperature(3000 - ABSOLUTE_ZERO), CoriumFluid::Source, CoriumFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(30) }, fluidBlockProperties = BlockBehaviour.Properties.of(Material.WATER).jumpFactor(0.01F).speedFactor(0.01F).noCollission().strength(1000F).noDrops())
    val volcanicLava = registerFluid("volcanic_lava", FluidAttributes.builder(ntm("fluid/volcanic_lava_still"), ntm("fluid/volcanic_lava_flow")).luminosity(15).density(3200).viscosity(7000).temperature(900 - ABSOLUTE_ZERO).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA), VolcanicLavaFluid::Source, VolcanicLavaFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(36).levelDecreasePerBlock(2) }, fluidBlockProperties = BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(1000F).noDrops().lightLevel { 15 }.hasPostProcess { _, _, _ -> true }, liquidBlockCreator = ::VolcanicLavaBlock)

    // specifying a default argument doesn't work because of generics
    fun registerFluid(name: String, attributes: FluidAttributes.Builder) = registerFluid(name, attributes, ::Source, ::Flowing, ::BucketItem)

    // æugh
    fun <S : ForgeFlowingFluid, F : ForgeFlowingFluid, BU : BucketItem> registerFluid(
        name: String,
        attributes: FluidAttributes.Builder,
        sourceCreator: (properties: Properties) -> S,
        flowingCreator: (properties: Properties) -> F,
        bucketCreator: (fluidSupplier: Supplier<out Fluid>, properties: Item.Properties) -> BU,
        propertiesModifier: Properties.() -> Unit = {},
        bucketProperties: () -> Item.Properties = { Item.Properties().tab(CreativeTabs.Items.tab).stacksTo(1).craftRemainder(Items.BUCKET) }, // needs to be a lambda so the CreativeTabs don't get triggered before NTechItems
        fluidBlockProperties: BlockBehaviour.Properties = BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100F).noDrops(),
        liquidBlockCreator: (fluidSupplier: Supplier<out FlowingFluid>, properties: BlockBehaviour.Properties) -> LiquidBlock = ::LiquidBlock
    ): FluidObject<S, F, BU, LiquidBlock> {
        val fluidObject = FluidObject<S, F, BU, LiquidBlock>()
        val properties = Properties(fluidObject::getSourceFluid, fluidObject::getFlowingFluid, attributes.translationKey("block.nucleartech.$name")).bucket(fluidObject::getBucket).block(fluidObject::getBlock)
        properties.propertiesModifier()
        fluidObject.source = FLUIDS.registerK(name) { sourceCreator(properties) }
        fluidObject.flowing = FLUIDS.registerK("flowing_$name") { flowingCreator(properties) }
        fluidObject.bucket = ITEMS.registerK("${name}_bucket") { bucketCreator.invoke(fluidObject::getSourceFluid, bucketProperties()) }
        fluidObject.block = BLOCKS.registerK(name) { liquidBlockCreator(fluidObject::getSourceFluid, fluidBlockProperties) }
        fluids += fluidObject
        return fluidObject
    }

    fun getFluidsList() = fluids.toList()

    private val bucketBehaviour = object : DefaultDispenseItemBehavior() {
        override fun execute(source: BlockSource, stack: ItemStack): ItemStack {
            val bucket = stack.item as DispensibleContainerItem
            val pos = source.pos.relative(source.blockState.getValue(DispenserBlock.FACING))
            val level = source.level
            return if (bucket.emptyContents(null, level, pos, null)) {
                bucket.checkExtraContent(null, level, stack, pos)
                ItemStack(Items.BUCKET)
            } else super.execute(source, stack)
        }
    }

    fun registerDispenserBehaviour() {
        for ((_, _, bucket, _) in getFluidsList()) {
            DispenserBlock.registerBehavior(bucket.get(), bucketBehaviour)
        }
    }

    // some fancy workaround because in order to register a fluid, you need properties, and in order to make properties, you need the fluid, so...
    class FluidObject<S : Fluid, F : Fluid, BU : BucketItem, BL : LiquidBlock> {
        var source by LateRegistryProperty<RegistryObject<S>>()
        var flowing by LateRegistryProperty<RegistryObject<F>>()
        var bucket by LateRegistryProperty<RegistryObject<BU>>()
        var block by LateRegistryProperty<RegistryObject<BL>>()

        operator fun component1() = source
        operator fun component2() = flowing
        operator fun component3() = bucket
        operator fun component4() = block
    }
}

// need this for referencing in registerFluid
private fun <S : Fluid> NTechFluids.FluidObject<S, *, *, *>.getSourceFluid() = source.get()
private fun <F : Fluid> NTechFluids.FluidObject<*, F, *, *>.getFlowingFluid() = flowing.get()
private fun <BU : BucketItem> NTechFluids.FluidObject<*, *, BU, *>.getBucket() = bucket.get()
private fun <BL : LiquidBlock> NTechFluids.FluidObject<*, *, *, BL>.getBlock() = block.get()
