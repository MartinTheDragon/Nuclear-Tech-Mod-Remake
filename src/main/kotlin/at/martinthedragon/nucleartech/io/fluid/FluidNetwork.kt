package at.martinthedragon.nucleartech.io.fluid

import at.martinthedragon.nucleartech.io.TransmitterNetwork
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.IFluidHandler
import java.util.*

class FluidNetwork : TransmitterNetwork<Pipe, FluidNetwork, IFluidHandler>, IFluidHandler {
    constructor() : super()
    constructor(networks: Collection<FluidNetwork>, uuid: UUID = UUID.randomUUID()) : super(networks, uuid)

    val fluid: Fluid get() = transmitters.firstOrNull()?.fluid ?: Fluids.EMPTY

    override fun self() = this
    override fun compatibleWith(other: FluidNetwork) = fluid.isSame(other.fluid)

    override fun tick() {
        if (!fluid.isSame(Fluids.EMPTY)) {
            val toFill = fill(FluidStack(fluid, Int.MAX_VALUE), IFluidHandler.FluidAction.SIMULATE)
            fill(drain(FluidStack(fluid, toFill), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE)
        }
    }

    // Flat representation of all tanks somehow connected to the network (one fluid handler can have multiple tanks)
    override fun getTanks() = networkMembers.values().fold(0) { acc, fluidHandler -> acc + fluidHandler.tanks }

    /**
     * Returns where the tanks of the [fluidHandler] start inside the flat representation returned by [getTanks].
     */
    private fun getStartTankForHandler(fluidHandler: IFluidHandler): Int {
        var currentTank = 0
        for (handler in networkMembers.values()) {
            if (handler === fluidHandler) return currentTank
            currentTank += handler.tanks
        }
        return -1
    }

    /**
     * Returns the range of tanks of the [fluidHandler] inside the flat representation returned by [getTanks].
     */
    private fun getTankRangeForHandler(fluidHandler: IFluidHandler): IntRange {
        var currentTank = 0
        for (handler in networkMembers.values()) {
            if (handler === fluidHandler) return currentTank until (currentTank + fluidHandler.tanks)
            currentTank += handler.tanks
        }
        return IntRange.EMPTY
    }

    /**
     * Returns the [IFluidHandler] responsible for the [tank] inside the flat representation returned by [getTanks].
     */
    private fun getHandlerForTank(tank: Int): IFluidHandler? {
        var currentTank = 0
        for (handler in networkMembers.values()) {
            currentTank += handler.tanks
            if (tank < currentTank) return handler
        }
        return null
    }

    override fun getFluidInTank(tank: Int): FluidStack {
        if (tank < 0 || tank >= tanks) return FluidStack.EMPTY
        val handler = getHandlerForTank(tank) ?: return FluidStack.EMPTY
        return handler.getFluidInTank(tank - getStartTankForHandler(handler))
    }

    override fun getTankCapacity(tank: Int): Int {
        if (tank < 0 || tank >= tanks) return 0
        val handler = getHandlerForTank(tank) ?: return 0
        return handler.getTankCapacity(tank - getStartTankForHandler(handler))
    }

    override fun isFluidValid(tank: Int, stack: FluidStack): Boolean {
        if (tank < 0 || tank >= tanks) return false
        val handler = getHandlerForTank(tank) ?: return false
        return handler.isFluidValid(tank - getStartTankForHandler(handler), stack)
    }

    override fun fill(resource: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (networkMembers.isEmpty) return 0

        val fluidAcceptingMembers = networkMembers.values().filter { it.fill(FluidStack(resource, 1), IFluidHandler.FluidAction.SIMULATE) > 0 }
        if (fluidAcceptingMembers.isEmpty()) return 0

        var remaining = resource.amount
        while (true) { // FIXME really long loop
            val lastRemaining = remaining
            val eachFill = if (remaining < fluidAcceptingMembers.size) 1 else remaining / fluidAcceptingMembers.size
            for (handler in fluidAcceptingMembers) {
                val fluidStack =
                    if (remaining - eachFill < 0) FluidStack(resource, remaining)
                    else FluidStack(resource, eachFill)
                remaining -= handler.fill(fluidStack, action)
                if (remaining <= 0) break
            }
            if (lastRemaining == remaining) break
        }
        return resource.amount - remaining
    }

    override fun drain(resource: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        if (networkMembers.isEmpty) return FluidStack.EMPTY

        val fluidSupplyingMembers = networkMembers.values().filter { it.drain(FluidStack(resource, 1), IFluidHandler.FluidAction.SIMULATE).amount > 0 }
        if (fluidSupplyingMembers.isEmpty()) return FluidStack.EMPTY

        val fluid = resource.fluid
        var remaining = resource.amount
        val tag = resource.tag
        while (true) {
            val lastRemaining = remaining
            val eachDrain = if (remaining < fluidSupplyingMembers.size) 1 else remaining / fluidSupplyingMembers.size
            for (handler in fluidSupplyingMembers) {
                val fluidStack =
                    if (remaining - eachDrain < 0) FluidStack(resource, remaining)
                    else FluidStack(resource, eachDrain)
                remaining -= handler.drain(fluidStack, action).amount
                if (remaining <= 0) break
            }
            if (lastRemaining == remaining) break
        }
        return FluidStack(fluid, resource.amount - remaining, tag)
    }

    override fun drain(maxDrain: Int, action: IFluidHandler.FluidAction): FluidStack {
        if (networkMembers.isEmpty) return FluidStack.EMPTY

        val fluidStack = networkMembers.values().firstNotNullOfOrNull {
            val fluidStack = it.drain(maxDrain, IFluidHandler.FluidAction.SIMULATE)
            if (fluidStack.isEmpty) null
            else fluidStack.fluid
        } ?: return FluidStack.EMPTY
        return drain(FluidStack(fluidStack, maxDrain), action)
    }
}
