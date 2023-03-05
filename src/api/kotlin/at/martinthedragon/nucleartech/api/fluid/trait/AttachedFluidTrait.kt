package at.martinthedragon.nucleartech.api.fluid.trait

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentUtils
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraftforge.fluids.FluidStack

public interface AttachedFluidTrait {
    public val trait: FluidTrait
    public val target: FluidTarget
    public val tag: CompoundTag

    public fun getDisplayName(): Component {
        val name = trait.getName(this)
        val style = name.style
        return ComponentUtils.wrapInSquareBrackets(name).withStyle(style)
    }

    public fun appendHoverText(level: BlockGetter?, fluid: FluidStack, tooltip: MutableList<Component>, flag: TooltipFlag) {
        trait.appendHoverText(level, fluid, this, tooltip, flag)
    }

    public fun releaseFluidInWorld(level: Level, pos: BlockPos, fluid: FluidStack) {
        trait.releaseFluidInWorld(level, pos, fluid, this)
    }

    public fun interface FluidTarget {
        public fun test(fluid: FluidStack): Boolean
    }
}
