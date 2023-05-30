package at.martinthedragon.nucleartech.particle

import com.mojang.brigadier.StringReader
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

class CoolingTowerCloudParticleOptions(val scale: Float, val maxScale: Float, val lift: Float, val lifetime: Int) : ParticleOptions {
    override fun getType() = ModParticles.COOLING_TOWER_CLOUD.get()

    override fun writeToNetwork(packet: FriendlyByteBuf): Unit = with(packet) {
        writeFloat(scale)
        writeFloat(maxScale)
        writeFloat(lift)
        writeInt(lifetime)
    }

    override fun writeToString() = "%s %.2f %.2f %.2f %d".format(Locale.ROOT, ForgeRegistries.PARTICLE_TYPES.getKey(this.type), scale, maxScale, lift, lifetime)

    @Suppress("DEPRECATION")
    object Deserializer : ParticleOptions.Deserializer<CoolingTowerCloudParticleOptions> {
        override fun fromCommand(type: ParticleType<CoolingTowerCloudParticleOptions>, reader: StringReader): CoolingTowerCloudParticleOptions {
            reader.expect(' ')
            val scale = reader.readFloat()
            reader.expect(' ')
            val maxScale = reader.readFloat()
            reader.expect(' ')
            val lift = reader.readFloat()
            reader.expect(' ')
            return CoolingTowerCloudParticleOptions(scale, maxScale, lift, reader.readInt())
        }

        override fun fromNetwork(type: ParticleType<CoolingTowerCloudParticleOptions>, packet: FriendlyByteBuf) =
            CoolingTowerCloudParticleOptions(packet.readFloat(), packet.readFloat(), packet.readFloat(), packet.readInt())

        val CODEC: Codec<CoolingTowerCloudParticleOptions> = RecordCodecBuilder.create {
            it.group(
                Codec.FLOAT.fieldOf("scale").forGetter(CoolingTowerCloudParticleOptions::scale),
                Codec.FLOAT.fieldOf("max_scale").forGetter(CoolingTowerCloudParticleOptions::maxScale),
                Codec.FLOAT.fieldOf("lift").forGetter(CoolingTowerCloudParticleOptions::lift),
                Codec.INT.fieldOf("lifetime").forGetter(CoolingTowerCloudParticleOptions::lifetime)
            ).apply(it, ::CoolingTowerCloudParticleOptions)
        }
    }
}
