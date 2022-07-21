package at.martinthedragon.nucleartech.extensions

import at.martinthedragon.nucleartech.fluid.ContainerFluidHandler
import at.martinthedragon.nucleartech.fluid.SimpleContainerFluidHandler
import at.martinthedragon.nucleartech.recipes.getItems
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraftforge.fluids.capability.IFluidHandler

fun Container.subView(fromIndex: Int, toIndex: Int): Container = SimpleContainer(*getItems(false).subList(fromIndex, toIndex).toTypedArray())

fun Container.subView(range: IntRange): Container = subView(range.first, range.last + 1)

fun <T> T.subViewWithFluids(fromIndex: Int, toIndex: Int, fromTank: Int, toTank: Int): ContainerFluidHandler
    where T : Container,
          T : IFluidHandler =
    SimpleContainerFluidHandler((this as Container).subView(fromIndex, toIndex), (this as IFluidHandler).subView(fromTank, toTank))

fun <T> T.subViewWithFluids(containerRange: IntRange, tankRange: IntRange): ContainerFluidHandler
    where T : Container,
          T : IFluidHandler =
    subViewWithFluids(containerRange.first, containerRange.last + 1, tankRange.first, tankRange.last + 1)
