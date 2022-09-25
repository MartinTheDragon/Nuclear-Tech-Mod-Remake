package at.martinthedragon.nucleartech.fluid

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCKS
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FLUIDS
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ITEMS
import at.martinthedragon.nucleartech.delegate.LateRegistryProperty
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.registerK
import net.minecraft.core.BlockSource
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior
import net.minecraft.world.item.*
import net.minecraft.world.level.block.DispenserBlock
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Material
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fluids.ForgeFlowingFluid.*
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object NTechFluids {
    private val fluids = mutableListOf<FluidObject<*, *, *, *>>()

    val oil = registerFluid("oil", FluidAttributes.builder(ntm("fluid/oil_still"), ntm("fluid/oil_flow")), ::Source, ::Flowing, ::BucketItem, propertiesModifier = { tickRate(30) })
    val gas = registerFluid("gas", FluidAttributes.builder(ntm("fluid/gas_still"), ntm("fluid/gas_still")).gaseous().density(-100).sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(10) })
    val uraniumHexafluoride = registerFluid("uranium_hexafluoride", FluidAttributes.builder(ntm("fluid/uranium_hexafluoride_still"), ntm("fluid/uranium_hexafluoride_flow")).color(0xE6D1CEBEu.toInt()).gaseous().sound(null), GaseousFluid::Source, GaseousFluid::Flowing, ::BucketItem, propertiesModifier = { tickRate(10) })

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
        fluidBlockProperties: BlockBehaviour.Properties = BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100F).noDrops()
    ): FluidObject<S, F, BU, LiquidBlock> {
        val fluidObject = FluidObject<S, F, BU, LiquidBlock>()
        val properties = Properties(fluidObject::getSourceFluid, fluidObject::getFlowingFluid, attributes.translationKey("block.nucleartech.$name")).bucket(fluidObject::getBucket).block(fluidObject::getBlock)
        properties.propertiesModifier()
        fluidObject.source = FLUIDS.registerK(name) { sourceCreator(properties) }
        fluidObject.flowing = FLUIDS.registerK("flowing_$name") { flowingCreator(properties) }
        fluidObject.bucket = ITEMS.registerK("${name}_bucket") { bucketCreator.invoke(fluidObject::getSourceFluid, bucketProperties()) }
        fluidObject.block = BLOCKS.registerK(name) { LiquidBlock(fluidObject::getSourceFluid, fluidBlockProperties) }
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
