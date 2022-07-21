package at.martinthedragon.nucleartech.fluid

import net.minecraft.world.Container
import net.minecraftforge.fluids.capability.IFluidHandler

class SimpleContainerFluidHandler(container: Container, fluidHandler: IFluidHandler) : ContainerFluidHandler, Container by container, IFluidHandler by fluidHandler
