package at.martinthedragon.nucleartech.io.fluid

import at.martinthedragon.nucleartech.block.entity.transmitters.FluidPipeBlockEntity
import at.martinthedragon.nucleartech.extensions.getOrNull
import at.martinthedragon.nucleartech.io.AbstractTransmitter
import net.minecraft.core.Direction
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.IFluidHandler

class Pipe(blockEntity: FluidPipeBlockEntity) : AbstractTransmitter<Pipe, FluidNetwork, IFluidHandler>(blockEntity) {
    val fluid get() = (this.blockEntity as FluidPipeBlockEntity).fluid

    override fun self() = this
    override fun isNeighboringTransmitter(blockEntity: BlockEntity, side: Direction?) = blockEntity is FluidPipeBlockEntity
    override fun isValidTransmitterNeighbour(blockEntity: BlockEntity, side: Direction?) = blockEntity is FluidPipeBlockEntity && blockEntity.fluid.isSame(fluid)
    override fun isValidNetworkMember(blockEntity: BlockEntity, side: Direction) = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.opposite).isPresent
    override fun getMember(blockEntity: BlockEntity, side: Direction) = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.opposite).getOrNull()
    override fun createNetworkByMerging(networks: Collection<FluidNetwork>) = FluidNetwork(networks)
    override fun compatibleWith(network: FluidNetwork) = network.fluid.isSame(fluid)

    override fun onNetworkChanged(oldNetwork: FluidNetwork?, newNetwork: FluidNetwork?) {
        super.onNetworkChanged(oldNetwork, newNetwork)

        blockEntity as FluidPipeBlockEntity
        blockEntity.capability.invalidate()
        blockEntity.capability = blockEntity.createCapability()
    }
}
