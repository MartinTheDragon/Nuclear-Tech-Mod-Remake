package at.martinthedragon.nucleartech.block.entity.transmitters

import at.martinthedragon.nucleartech.block.BlockTints
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.io.fluid.Pipe
import net.minecraft.ResourceLocationException
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler
import net.minecraftforge.registries.ForgeRegistries

class FluidPipeBlockEntity(pos: BlockPos, state: BlockState) : AbstractTransmitterBlockEntity(BlockEntityTypes.fluidPipeBlockEntityType.get(), pos, state) {
    var fluid: Fluid = Fluids.EMPTY
        private set

    fun setFluid(fluid: Fluid) {
        if (this.fluid == fluid) return
        this.fluid = fluid
        if (!isClientSide()) {
            removeFromNetwork() // turn it off and back on again, always works
            addToNetwork()
            refresh(true)
            sendContinuousUpdatePacket()
        }
    }

    override fun getContinuousUpdateTag(): CompoundTag = super.getContinuousUpdateTag().apply {
        putString("Fluid", fluid.registryName?.toString() ?: "minecraft:empty")
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        fluid = try {
            ForgeRegistries.FLUIDS.getValue(ResourceLocation(tag.getString("Fluid"))) ?: Fluids.EMPTY
        } catch (ignored: ResourceLocationException) {
            Fluids.EMPTY
        }
        BlockTints.invalidate(levelUnchecked as ClientLevel, BlockTints.FLUID_DUCT_COLOR_RESOLVER, blockPos, true)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)

        val fluidLocation = fluid.registryName
        if (fluidLocation != null)
            tag.putString("Fluid", fluidLocation.toString())
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        fluid = try {
            ForgeRegistries.FLUIDS.getValue(ResourceLocation(tag.getString("Fluid"))) ?: Fluids.EMPTY
        } catch (ignored: ResourceLocationException) {
            Fluids.EMPTY
        }
    }

    override val transmitter = Pipe(this)

    var capability: LazyOptional<IFluidHandler> = LazyOptional.empty()

    fun createCapability(): LazyOptional<IFluidHandler> = if (transmitter.network == null) LazyOptional.empty() else LazyOptional.of { transmitter.network!! }

    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return capability.cast()
        return super.getCapability(cap, side)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        capability.invalidate()
    }

    override fun reviveCaps() {
        super.reviveCaps()
        capability = createCapability()
    }
}
