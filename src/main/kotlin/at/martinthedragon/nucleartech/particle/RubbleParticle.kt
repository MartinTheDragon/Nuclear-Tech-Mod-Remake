package at.martinthedragon.nucleartech.particle

import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import at.martinthedragon.nucleartech.extensions.surroundPath
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Vector3d
import com.mojang.math.Vector3f
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import kotlin.math.abs

class RubbleParticle(level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double, private val texture: ResourceLocation) : Particle(level, x, y, z) {
    private var xRot = 0.0
    private var yRot = 0.0
    private var zRot = 0.0
    private var xRotO = 0.0
    private var yRotO = 0.0
    private var zRotO = 0.0
    private val deltaRot = Vector3d(random.nextDouble(-5.0, 5.0), random.nextDouble(-5.0, 5.0), random.nextDouble(-5.0, 5.0))
    private val sinkSpeed = random.nextDouble(.0005, .002)
    private var yOffset = 0.0

    init {
        this.xd = xd * .4
        this.yd = yd * .4
        this.zd = zd * .4
        gravity = 1F
        lifetime = 500 + random.nextInt(100)
        setSize(1.1F, 1.1F)
    }

    override fun tick() {
        val wasStopped = stoppedByCollision

        super.tick()

        xRotO = xRot
        yRotO = yRot
        zRotO = zRot

        if (!stoppedByCollision) {
            xRot += deltaRot.x
            yRot += deltaRot.y
            zRot += deltaRot.z
        }

        if (onGround) {
            yOffset -= sinkSpeed
        }

        alpha = ((lifetime - age) / 40F).coerceIn(0F, 1F)

        if (!wasStopped && stoppedByCollision) {
            level.playLocalSound(x, y, z, SoundEvents.debris.get(), SoundSource.BLOCKS, 1.5F, 1F, false)
        }
    }

    private val rubbleModel = RubbleModel(Minecraft.getInstance().entityModels.bakeLayer(NuclearModelLayers.RUBBLE))

    override fun render(consumer: VertexConsumer, camera: Camera, partials: Float) {
        val stack = PoseStack()
        val (cameraX, cameraY, cameraZ) = camera.position
        val partialsD = partials.toDouble()
        val offsetX = Mth.lerp(partialsD, xo, x) - cameraX
        val offsetY = Mth.lerp(partialsD, yo, y) - cameraY + yOffset + .5
        val offsetZ = Mth.lerp(partialsD, zo, z) - cameraZ
        stack.translate(offsetX, offsetY, offsetZ)
        val rotationX = Mth.lerp(partialsD, xRotO, xRot).toFloat()
        val rotationY = Mth.lerp(partialsD, yRotO, yRot).toFloat()
        val rotationZ = Mth.lerp(partialsD, zRotO, zRot).toFloat()
        stack.mulPose(Vector3f.XP.rotationDegrees(rotationX))
        stack.mulPose(Vector3f.YP.rotationDegrees(rotationY))
        stack.mulPose(Vector3f.ZP.rotationDegrees(rotationZ))
        val bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().builder)
        val newConsumer = bufferSource.getBuffer(rubbleModel.renderType(texture))
        rubbleModel.renderToBuffer(stack, newConsumer, getLightColor(partials), OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, alpha)
        bufferSource.endBatch()
    }

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.CUSTOM

    override fun move(deltaX: Double, deltaY: Double, deltaZ: Double) {
        if (!stoppedByCollision) {
            var varX = deltaX
            var varY = deltaY
            var varZ = deltaZ
            if (hasPhysics && (deltaX != 0.0 || deltaY != 0.0 || deltaZ != 0.0) && deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ < 10_000) {
                val newVector = Entity.collideBoundingBox(null, Vec3(deltaX, deltaY, deltaZ), collisionBox, level, emptyList())
                varX = newVector.x
                varY = newVector.y
                varZ = newVector.z
            }
            if (varX != 0.0 || varY != 0.0 || varZ != 0.0) {
                boundingBox = boundingBox.move(varX, varY, varZ)
                setLocationFromBoundingbox()
            }

            if (abs(deltaY) >= 1.0E-5 && abs(varY) < 1.0E-5)
                stoppedByCollision = true

            onGround = deltaY != varY && deltaY < 0
            if (deltaX != varX) xd = 0.0
            if (deltaZ != varZ) xd = 0.0
        }
    }

    // hack to have smaller collision than clipping
    private var collisionBox = AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

    override fun setBoundingBox(aabb: AABB) {
        super.setBoundingBox(aabb)
        collisionBox = aabb.inflate(-.25, 0.0, -.25)
    }

    class RubbleModel(private val root: ModelPart) : Model(if (Minecraft.useShaderTransparency()) RenderType::entityTranslucent else RenderType::entitySolid) {
        override fun renderToBuffer(stack: PoseStack, consumer: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
            root.render(stack, consumer, light, overlay, red, green, blue, alpha)
        }

        companion object {
            fun createLayerDefinition(): LayerDefinition {
                val meshDefinition = MeshDefinition().apply {
                    root.addOrReplaceChild("cube1", CubeListBuilder.create()
                        .addBox(-7F, 1F, 2F, 14F, 6F, 6F)
                        .addBox(-7F, -6F, -5F, 6F, 13F, 5F)
                        .addBox(1F, 1F, -5F, 6F, 6F, 6F)
                        .addBox(0F, -6F, -5F, 6F, 6F, 11F)
                        .addBox(-4F, -4F, -4F, 8F, 8F, 8F)
                        .addBox(-7F, -5F, 1F, 6F, 5F, 7F), PartPose.ZERO
                    ).addOrReplaceChild("cube2", CubeListBuilder.create().addBox(-7F, -7F, 2F, 14F, 7F, 4F), PartPose.rotation(0F, .435F, 0F))
                        .addOrReplaceChild("cube3", CubeListBuilder.create().addBox(-6F, -1F, 3F, 12F, 6F, 4F), PartPose.rotation(0F, 0F, -.35F))
                        .addOrReplaceChild("cube4", CubeListBuilder.create().addBox(-6F, 2F, -3F, 12F, 6F, 6F), PartPose.rotation(0F, -.21F, 0F))
                        .addOrReplaceChild("cube5", CubeListBuilder.create().addBox(-5F, -3F, -6F, 6F, 10F, 4F), PartPose.rotation(0F, 0F, -.35F))
                }
                return LayerDefinition.create(meshDefinition, 64, 32)
            }
        }
    }

    class Provider : ParticleProvider<RubbleParticleOptions> {
        override fun createParticle(options: RubbleParticleOptions, level: ClientLevel, x: Double, y: Double, z: Double, xd: Double, yd: Double, zd: Double) =
            RubbleParticle(level, x, y, z, xd, yd, zd, Minecraft.getInstance().blockRenderer.blockModelShaper.getTexture(options.block.defaultBlockState(), level, BlockPos(x, y, z)).name.surroundPath("textures/", ".png"))
    }
}
