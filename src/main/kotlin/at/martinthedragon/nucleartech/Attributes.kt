package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ATTRIBUTES
import net.minecraft.world.entity.ai.attributes.RangedAttribute

object Attributes {
    val RADIATION_RESISTANCE = ATTRIBUTES.register("generic.radiation_resistance") { RangedAttribute(ntmAttributeName("generic.radiation_resistance"), 0.0, 0.0, 10.0) }

    private fun ntmAttributeName(name: String) = "nucleartech.attribute.name.$name"
}
