package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.extensions.*
import com.mojang.datafixers.util.Either
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.client.event.RenderTooltipEvent
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.registries.ForgeRegistries
import kotlin.jvm.optionals.getOrNull

object FluidTraitHandler {
    fun addHoverText(event: RenderTooltipEvent.GatherComponents) {
        val stack = event.itemStack
        if (!stack.isEmpty) {
            val capability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).getOrNull() ?: return
            val tooltip = mutableListOf<Component>()

            if (event.tooltipElements.size > 1) // if there are already other tooltips, add some space
                tooltip += TextComponent.EMPTY

            for (fluid in capability.getFluids())
                collectTooltip(fluid, tooltip)

            event.tooltipElements.addAll(tooltip.map { Either.left(it) })
        } else { // if there's no item stack, try to understand whether and what fluid we have as good as we can
            var changed = false
            for (entry in event.tooltipElements.map { it.left() }) {
                val component = entry.getOrNull() as? TranslatableComponent ?: continue
                val fluid = (component.args.flatMap(::flattenArgs) + component.key)
                    .firstNotNullOfOrNull { ForgeRegistries.FLUIDS.firstOrNull { fluid -> fluid.attributes.translationKey == it } } ?: continue
                if (fluid.isSame(Fluids.EMPTY)) continue
                val fakeFluidStack = FluidStack(fluid, 1000)

                val tooltip = mutableListOf<Component>()
                if (!changed && event.tooltipElements.size > 1) tooltip += TextComponent.EMPTY

                collectTooltip(fakeFluidStack, tooltip)

                changed = event.tooltipElements.addAll(tooltip.map { Either.left(it) })
            }
        }
    }

    private fun collectTooltip(fluid: FluidStack, tooltip: MutableList<Component>) {
        val level = Minecraft.getInstance().level
        val flag = if (Minecraft.getInstance().options.advancedItemTooltips || Screen.hasShiftDown()) TooltipFlag.Default.ADVANCED else TooltipFlag.Default.NORMAL

        if (flag.isAdvanced) tooltip += TranslatableComponent(fluid.translationKey).gray()

        val traits = FluidTraitManager.getTraitsForFluidStack(fluid)
        for (trait in traits) {
            trait.appendHoverText(level, fluid, tooltip, flag)
        }
        if (!flag.isAdvanced && traits.any { it.trait.isTooltipFlagReactive })
            tooltip += LangKeys.INFO_HOLD_KEY_FOR_INFO.format(TextComponent("Shift").yellow().italic()).darkGray().italic()
    }

    private fun flattenArgs(arg: Any, passOn: MutableList<String> = mutableListOf()): Collection<String> {
        if (arg is TranslatableComponent) {
            passOn += arg.key
            arg.args.forEach { flattenArgs(it, passOn) }
        } else {
            passOn += arg.toString()
        }
        return passOn
    }
}
