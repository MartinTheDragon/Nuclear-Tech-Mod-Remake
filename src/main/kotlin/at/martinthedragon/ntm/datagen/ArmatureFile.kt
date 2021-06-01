package at.martinthedragon.ntm.datagen

import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.data.ExistingFileHelper

abstract class ArmatureFile(val location: ResourceLocation) {
    protected abstract fun exists(): Boolean

    fun assertExists() { if (!exists()) throw IllegalStateException("Armature at $location does not exist") }

    class UncheckedArmatureFile(location: ResourceLocation) : ArmatureFile(location) {
        constructor(location: String) : this(ResourceLocation(location))

        override fun exists(): Boolean = true
    }

    class ExistingArmatureFile(
        location: ResourceLocation,
        private val existingFileHelper: ExistingFileHelper
    ) : ArmatureFile(location) {
        override fun exists(): Boolean =
            existingFileHelper.exists(location, ArmatureProvider.ARMATURE)
    }
}
