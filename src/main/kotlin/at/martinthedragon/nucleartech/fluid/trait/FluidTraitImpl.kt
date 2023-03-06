package at.martinthedragon.nucleartech.fluid.trait

import at.martinthedragon.nucleartech.RegistriesAndLifecycle
import at.martinthedragon.nucleartech.api.fluid.trait.AttachedFluidTrait
import at.martinthedragon.nucleartech.api.fluid.trait.FluidTrait
import net.minecraft.Util
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TranslatableComponent
import net.minecraftforge.registries.ForgeRegistryEntry

open class FluidTraitImpl(protected val styleModifier: Style) : ForgeRegistryEntry<FluidTrait>(), FluidTrait {
    private var _descriptionId: String? = null

    override val descriptionId: String
        get() {
            if (_descriptionId == null)
                _descriptionId = Util.makeDescriptionId("fluid_trait", RegistriesAndLifecycle.FLUID_TRAIT_REGISTRY.get().getKey(this))
            return _descriptionId!!
        }

    override fun getName(data: AttachedFluidTrait<*>): MutableComponent = TranslatableComponent(descriptionId).withStyle(styleModifier)
}
