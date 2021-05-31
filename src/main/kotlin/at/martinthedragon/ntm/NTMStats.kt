package at.martinthedragon.ntm

import net.minecraft.stats.IStatFormatter
import net.minecraft.stats.Stats
import net.minecraft.util.ResourceLocation
import net.minecraft.util.registry.Registry

object NTMStats {
    private fun makeCustomStat(name: String, formatter: IStatFormatter): ResourceLocation {
        val registryName = ResourceLocation(Main.MODID, name)
        Registry.register(Registry.CUSTOM_STAT, name, registryName)
        Stats.CUSTOM.get(registryName, formatter)
        return registryName
    }
}
