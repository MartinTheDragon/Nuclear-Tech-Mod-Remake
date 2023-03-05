package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.FLUID_TRAITS
import at.martinthedragon.nucleartech.registerK
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Style

object NTechFluidTraits {
    val liquid = FLUID_TRAITS.registerK("liquid") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val gaseous = FLUID_TRAITS.registerK("gaseous") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val gaseousRoomTemp = FLUID_TRAITS.registerK("gaseous_at_room_temperature") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.BLUE)) }
    val plasma = FLUID_TRAITS.registerK("plasma") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)) }
    val antimatter = FLUID_TRAITS.registerK("antimatter") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.DARK_RED)) }
    val delicious = FLUID_TRAITS.registerK("delicious") { FluidTraitImpl(Style.EMPTY.applyFormat(ChatFormatting.DARK_GREEN)) }
    val combustible = FLUID_TRAITS.registerK("combustible") { CombustibleFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.GOLD)) }
    val coolable = FLUID_TRAITS.registerK("coolable") { CoolableFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.AQUA)) }
    val corrosive = FLUID_TRAITS.registerK("corrosive") { CorrosiveFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)) }
    val flammable = FLUID_TRAITS.registerK("flammable") { FlammableFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)) }
    val heatable = FLUID_TRAITS.registerK("heatable") { HeatableFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.AQUA)) }
    val radioactive = FLUID_TRAITS.registerK("radioactive") { RadioactiveFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.YELLOW)) }
    val toxic = FLUID_TRAITS.registerK("toxic") { ToxicFluidTrait(Style.EMPTY.applyFormat(ChatFormatting.GREEN)) }
}
