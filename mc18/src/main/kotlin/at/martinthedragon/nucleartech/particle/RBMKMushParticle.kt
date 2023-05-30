package at.martinthedragon.nucleartech.particle

import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Vector3f
import net.minecraft.client.Camera
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.util.Mth

class RBMKMushParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, options: RBMKMushParticleOptions, private val sprites: SpriteSet) : TextureSheetParticle(level, x, y, z) {
    init {
        lifetime = 50
        setSpriteFromAge(sprites)
        setSize(options.scale, options.scale)
        quadSize = options.scale
    }

    override fun tick() {
        xo = x
        yo = y
        zo = z

        age++
        if (age >= lifetime) remove()
        setSpriteFromAge(sprites)
    }

    override fun render(consumer: VertexConsumer, camera: Camera, partials: Float) {
        val vec3 = camera.position
        val f = (Mth.lerp(partials.toDouble(), xo, x) - vec3.x()).toFloat()
        val f1 = (Mth.lerp(partials.toDouble(), yo, y) - vec3.y()).toFloat()
        val f2 = (Mth.lerp(partials.toDouble(), zo, z) - vec3.z()).toFloat()
        val quaternion = Vector3f.YP.rotationDegrees(-camera.yRot)

        val vector3f1 = Vector3f(-1.0f, -1.0f, 0.0f)
        vector3f1.transform(quaternion)
        val avector3f = arrayOf(Vector3f(-1.0f, -1.0f, 0.0f), Vector3f(-1.0f, 1.0f, 0.0f), Vector3f(1.0f, 1.0f, 0.0f), Vector3f(1.0f, -1.0f, 0.0f))
        val f4 = getQuadSize(partials)

        for (i in 0..3) {
            val vector3f = avector3f[i]
            vector3f.transform(quaternion)
            vector3f.mul(f4)
            vector3f.add(f, f1, f2)
        }

        val f7 = this.u0
        val f8 = this.u1
        val f5 = this.v0
        val f6 = this.v1
        val j = getLightColor(partials)
        consumer.vertex(avector3f[0].x().toDouble(), avector3f[0].y().toDouble(), avector3f[0].z().toDouble()).uv(f8, f6).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
        consumer.vertex(avector3f[1].x().toDouble(), avector3f[1].y().toDouble(), avector3f[1].z().toDouble()).uv(f8, f5).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
        consumer.vertex(avector3f[2].x().toDouble(), avector3f[2].y().toDouble(), avector3f[2].z().toDouble()).uv(f7, f5).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
        consumer.vertex(avector3f[3].x().toDouble(), avector3f[3].y().toDouble(), avector3f[3].z().toDouble()).uv(f7, f6).color(rCol, gCol, bCol, alpha).uv2(j).endVertex()
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    class Provider(private val sprites: SpriteSet) : ParticleProvider<RBMKMushParticleOptions> {
        override fun createParticle(options: RBMKMushParticleOptions, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            RBMKMushParticle(level, x, y, z, options, sprites)
    }
}
