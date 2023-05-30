package at.martinthedragon.nucleartech.world.gen.features.meteoriteplacers

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.METEORITE_PLACERS
import at.martinthedragon.nucleartech.registerK
import com.mojang.serialization.Codec
import net.minecraftforge.registries.ForgeRegistryEntry

class MeteoritePlacerType<T : MeteoritePlacer>(val codec: Codec<T>) : ForgeRegistryEntry<MeteoritePlacerType<*>>() {
    companion object {
        val BOX_PLACER = METEORITE_PLACERS.registerK("box_placer") { MeteoritePlacerType(BoxMeteoritePlacer.CODEC) }
        val NOP_PLACER = METEORITE_PLACERS.registerK("nop_placer") { MeteoritePlacerType(Codec.unit(NopMeteoritePlacer)) }
        val SINGLE_BLOCK_PLACER = METEORITE_PLACERS.registerK("single_block_placer") { MeteoritePlacerType(Codec.unit(SingleBlockMeteoritePlacer)) }
        val SPHERE_PLACER = METEORITE_PLACERS.registerK("sphere_placer") { MeteoritePlacerType(SphereMeteoritePlacer.CODEC) }
        val STAR_PLACER = METEORITE_PLACERS.registerK("star_placer") { MeteoritePlacerType(StarMeteoritePlacer.CODEC) }
    }
}
