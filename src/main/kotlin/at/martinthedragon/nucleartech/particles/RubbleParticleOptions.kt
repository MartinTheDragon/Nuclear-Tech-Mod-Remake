package at.martinthedragon.nucleartech.particles

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.registries.ForgeRegistries

class RubbleParticleOptions(val block: Block) : ParticleOptions {
    private constructor(resourceLocation: ResourceLocation) : this(ForgeRegistries.BLOCKS.getValue(resourceLocation) ?: Blocks.STONE)

    override fun getType() = ModParticles.RUBBLE.get()

    override fun writeToNetwork(packet: FriendlyByteBuf) {
        packet.writeRegistryId(block)
    }

    override fun writeToString() = block.toString()

    companion object {
        @Suppress("DEPRECATION")
        @JvmStatic
        val DESERIALIZER = object : ParticleOptions.Deserializer<RubbleParticleOptions> {
            override fun fromCommand(type: ParticleType<RubbleParticleOptions>, reader: StringReader): RubbleParticleOptions {
                reader.expect(' ')
                val resourceLocationString = reader.readString()
                if (!ResourceLocation.isValidResourceLocation(resourceLocationString))
                    throw SimpleCommandExceptionType(LiteralMessage("Invalid resource location")).createWithContext(reader)
                val block = ForgeRegistries.BLOCKS.getValue(ResourceLocation(resourceLocationString))
                if (block == null || block.defaultBlockState().isAir) throw SimpleCommandExceptionType(LiteralMessage("Block not found")).createWithContext(reader)
                return RubbleParticleOptions(block)
            }

            override fun fromNetwork(type: ParticleType<RubbleParticleOptions>, packet: FriendlyByteBuf) = RubbleParticleOptions(packet.readRegistryId<Block>())
        }

        @JvmStatic
        val CODEC: Codec<RubbleParticleOptions> = RecordCodecBuilder.create { builder ->
            builder.group(ResourceLocation.CODEC.fieldOf("block").forGetter { it.block.registryName }).apply(builder, ::RubbleParticleOptions)
        }
    }
}
