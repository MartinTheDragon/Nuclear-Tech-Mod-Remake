package at.martinthedragon.nucleartech.particle

import at.martinthedragon.nucleartech.NuclearTech
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Vector3f
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.util.Mth

class MiniNukeFlashParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, private val spriteSet: SpriteSet) : TextureSheetParticle(level, x, y, z) {
    private val balefire = NuclearTech.polaroidBroken || random.nextInt(100) == 0

    init {
        lifetime = 20
        rCol = 1F
        gCol = .9F
        bCol = .75F

        setSpriteFromAge(spriteSet)
    }

    override fun tick() {
        super.tick()

        setSpriteFromAge(spriteSet)

        if (age == 15) {
            // Stem
            for (motionY in 0..18) level.addParticle(getCloud(), x, y, z, random.nextGaussian() * .05, motionY * .1 + random.nextGaussian() * .02, random.nextGaussian() * .05)

            // Ground
            for (i in 0 until 100) level.addParticle(getCloud(), x, y + .5, z, random.nextGaussian() * .5, if (random.nextInt(5) == 0) 0.02 else 0.0, random.nextGaussian() * .5)

            // Mush
            for (i in 0 until 75) {
                var motionX = random.nextGaussian() * .5
                var motionZ = random.nextGaussian() * .5
                val motionY = 1.8 + (random.nextDouble() * 3 - 1.5) * (.75 - (motionX * motionX + motionZ * motionZ)) * .5

                if (motionX * motionX + motionZ * motionZ > 1.5) {
                    motionX *= .5
                    motionZ *= .5
                }

                level.addParticle(getCloud(), x, y, z, motionX, motionY, motionZ)
            }
        }
    }

    private fun getCloud() = if (balefire) ModParticles.MINI_NUKE_CLOUD_BALEFIRE.get() else ModParticles.MINI_NUKE_CLOUD.get()

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    override fun render(vertexConsumer: VertexConsumer, camera: Camera, partials: Float) {
        alpha = 1 - (age + partials) / lifetime
        alpha *= .5F
        quadSize = (age + partials) * 3F + 1F

        val vec3 = camera.position
        val f = (Mth.lerp(partials.toDouble(), xo, x) - vec3.x()).toFloat()
        val f1 = (Mth.lerp(partials.toDouble(), yo, y) - vec3.y()).toFloat()
        val f2 = (Mth.lerp(partials.toDouble(), zo, z) - vec3.z()).toFloat()
        val quaternion = Vector3f.YP.rotationDegrees(-camera.yRot)

        val f4 = getQuadSize(partials)

        for (i in 0 until 24) {
            val avector3f = arrayOf(Vector3f(-1.0f, -1.0f, 0.0f), Vector3f(-1.0f, 1.0f, 0.0f), Vector3f(1.0f, 1.0f, 0.0f), Vector3f(1.0f, -1.0f, 0.0f))

            for (j in 0..3) {
                val vector3f = avector3f[j]
                vector3f.transform(quaternion)
                vector3f.mul(f4)
                vector3f.add(
                    f + random.nextFloat() * 15F - 7.5F,
                    f1 + random.nextFloat() * 7.5F - 3.75F,
                    f2 + random.nextFloat() * 15F - 7.5F
                )
            }

            val f7 = this.u0
            val f8 = this.u1
            val f5 = this.v0
            val f6 = this.v1
            val j = getLightColor(partials)
            vertexConsumer.vertex(avector3f[0].x().toDouble(), avector3f[0].y().toDouble(), avector3f[0].z().toDouble()).uv(f8, f6).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
            vertexConsumer.vertex(avector3f[1].x().toDouble(), avector3f[1].y().toDouble(), avector3f[1].z().toDouble()).uv(f8, f5).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
            vertexConsumer.vertex(avector3f[2].x().toDouble(), avector3f[2].y().toDouble(), avector3f[2].z().toDouble()).uv(f7, f5).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
            vertexConsumer.vertex(avector3f[3].x().toDouble(), avector3f[3].y().toDouble(), avector3f[3].z().toDouble()).uv(f7, f6).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
        }
    }

    override fun shouldCull() = false

    class Provider(private val spriteSet: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(options: SimpleParticleType, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            MiniNukeFlashParticle(level, x, y, z, spriteSet)
    }
}
