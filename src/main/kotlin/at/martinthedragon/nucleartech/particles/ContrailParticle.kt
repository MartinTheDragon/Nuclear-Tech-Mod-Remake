package at.martinthedragon.nucleartech.particles

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.*

class ContrailParticle private constructor(level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double, options: ContrailParticleOptions, private val sprites: SpriteSet) : TextureSheetParticle(level, x, y, z, xd, yd, zd){
    init {
        lifetime = 100 + random.nextInt(40)
        setSpriteFromAge(sprites)
        val colorModifier = random.nextFloat() * .2F + .2F
        setColor(options.color.x() + colorModifier, options.color.y() + colorModifier, options.color.z() + colorModifier)
        this.xd *= .25
        this.yd *= .25
        this.zd *= .25
        quadSize = options.scale
    }

    override fun tick() {
        super.tick()
        alpha = 1 - (age / lifetime.toFloat())
        setSpriteFromAge(sprites)
    }

    override fun getQuadSize(partials: Float) = alpha + quadSize * .5F
    override fun getLightColor(partials: Float) = 240
    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

    class Provider(private val sprites: SpriteSet) : ParticleProvider<ContrailParticleOptions> {
        override fun createParticle(options: ContrailParticleOptions, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            ContrailParticle(level, x, y, z, xd, yd, zd, options, sprites)
    }
}
