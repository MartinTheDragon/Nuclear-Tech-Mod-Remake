package at.martinthedragon.nucleartech.particle

import com.mojang.brigadier.StringReader
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

class RBMKMushParticleOptions(val scale: Float) : ParticleOptions {
    override fun getType() = ModParticles.RBMK_MUSH.get()

    override fun writeToNetwork(packet: FriendlyByteBuf) {
        packet.writeFloat(scale)
    }

    override fun writeToString() = "%s %.2f".format(Locale.ROOT, ForgeRegistries.PARTICLE_TYPES.getKey(this.type), scale)

    companion object {
        @Suppress("DEPRECATION")
        @JvmStatic
        val DESERIALIZER = object : ParticleOptions.Deserializer<RBMKMushParticleOptions> {
            override fun fromCommand(type: ParticleType<RBMKMushParticleOptions>, reader: StringReader): RBMKMushParticleOptions {
                reader.expect(' ')
                return RBMKMushParticleOptions(reader.readFloat())
            }

            override fun fromNetwork(type: ParticleType<RBMKMushParticleOptions>, packet: FriendlyByteBuf) = RBMKMushParticleOptions(packet.readFloat())
        }

        val CODEC: Codec<RBMKMushParticleOptions> = RecordCodecBuilder.create {
            it.group(Codec.FLOAT.fieldOf("scale").forGetter(RBMKMushParticleOptions::scale)).apply(it, ::RBMKMushParticleOptions)
        }
    }
}
