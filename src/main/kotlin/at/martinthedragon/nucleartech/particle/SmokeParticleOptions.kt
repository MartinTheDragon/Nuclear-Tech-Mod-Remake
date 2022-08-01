package at.martinthedragon.nucleartech.particle

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.util.StringRepresentable
import net.minecraftforge.registries.ForgeRegistries
import java.util.*

class SmokeParticleOptions(val mode: Mode, val strength: Float) : ParticleOptions {
    enum class Mode(private val serializedName: String) : StringRepresentable {
        Cloud("cloud"),
        Radial("smoke"),
        Digamma("digamma"),
        Shock("shock"),
        ShockRandom("shockrandom"),
        Wave("wave");

        override fun getSerializedName() = serializedName

        companion object {
            private val BY_NAME = values().associateBy(Mode::serializedName)
            val CODEC: Codec<Mode> = StringRepresentable.fromEnum(Mode::values, Mode::byName)

            fun byName(name: String) = BY_NAME[name]
        }
    }

    override fun getType() = ModParticles.SMOKE.get()

    override fun writeToNetwork(buffer: FriendlyByteBuf) {
        buffer.writeEnum(mode)
        buffer.writeFloat(strength)
    }

    override fun writeToString() = String.format(Locale.ROOT, "%s %s %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(type), mode.serializedName, strength)

    companion object {
        @Suppress("DEPRECATION")
        @JvmStatic
        val DESERIALIZER = object : ParticleOptions.Deserializer<SmokeParticleOptions> {
            override fun fromCommand(type: ParticleType<SmokeParticleOptions>, reader: StringReader): SmokeParticleOptions {
                reader.expect(' ')
                val mode = Mode.byName(reader.readString()) ?: throw SimpleCommandExceptionType(LiteralMessage("Unknown mode")).createWithContext(reader)
                reader.expect(' ')
                val strength = reader.readFloat()
                if (strength <= 0) throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.floatTooLow().createWithContext(reader, 0F, strength)
                return SmokeParticleOptions(mode, strength)
            }

            override fun fromNetwork(type: ParticleType<SmokeParticleOptions>, packet: FriendlyByteBuf) = SmokeParticleOptions(packet.readEnum(Mode::class.java), packet.readFloat())
        }

        @JvmStatic
        val CODEC: Codec<SmokeParticleOptions> = RecordCodecBuilder.create {
            it.group(
                Mode.CODEC.fieldOf("mode").forGetter(SmokeParticleOptions::mode),
                Codec.FLOAT.fieldOf("strength").forGetter(SmokeParticleOptions::strength)
            ).apply(it, ::SmokeParticleOptions)
        }
    }
}
