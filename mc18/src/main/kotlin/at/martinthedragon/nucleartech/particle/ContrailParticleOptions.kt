package at.martinthedragon.nucleartech.particle

import com.mojang.brigadier.StringReader
import com.mojang.math.Vector3f
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.particles.DustParticleOptionsBase
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf

class ContrailParticleOptions(color: Vector3f, scale: Float) : DustParticleOptionsBase(color, scale) {
    override fun getType() = ModParticles.CONTRAIL.get()

    companion object {
        @Suppress("DEPRECATION")
        @JvmStatic
        val DESERIALIZER = object : ParticleOptions.Deserializer<ContrailParticleOptions> {
            override fun fromCommand(type: ParticleType<ContrailParticleOptions>, reader: StringReader): ContrailParticleOptions {
                val color = readVector3f(reader)
                reader.expect(' ')
                val scale = reader.readFloat()
                return ContrailParticleOptions(color, scale)
            }

            override fun fromNetwork(type: ParticleType<ContrailParticleOptions>, packet: FriendlyByteBuf) = ContrailParticleOptions(readVector3f(packet), packet.readFloat())
        }

        val CODEC: Codec<ContrailParticleOptions> = RecordCodecBuilder.create {
            it.group(
                Vector3f.CODEC.fieldOf("color").forGetter(DustParticleOptionsBase::getColor),
                Codec.FLOAT.fieldOf("scale").forGetter(DustParticleOptionsBase::getScale)
            ).apply(it, ::ContrailParticleOptions)
        }
    }
}
