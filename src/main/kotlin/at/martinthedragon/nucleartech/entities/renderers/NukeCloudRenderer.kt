package at.martinthedragon.nucleartech.entities.renderers

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.entities.NukeCloudEntity
import at.martinthedragon.nucleartech.rendering.ClientRenderer
import at.martinthedragon.nucleartech.rendering.CustomRenderTypes
import at.martinthedragon.nucleartech.rendering.renderModelAlpha
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.client.model.data.EmptyModelData
import org.lwjgl.opengl.GL11
import java.util.*
import kotlin.math.*

class NukeCloudRenderer(entityRendererManager: EntityRendererManager) : EntityRenderer<NukeCloudEntity>(entityRendererManager) {
    private val textureFireball = ResourceLocation(NuclearTech.MODID, "textures/entity/nuke_cloud/nuke_cloud_fireball.png")
    private val textureBalefire = ResourceLocation(NuclearTech.MODID, "textures/entity/nuke_cloud/nuke_cloud_balefire.png")
    private val textureCloudlet = ResourceLocation(NuclearTech.MODID, "textures/entity/nuke_cloud/nuke_cloud_cloudlet.png")

    private val mushModel = ResourceLocation(NuclearTech.MODID, "other/nuke_mush")

    private val textureManager = Minecraft.getInstance().textureManager
    private val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer
    private val modelManager = Minecraft.getInstance().modelManager

    private val camera = Minecraft.getInstance().gameRenderer.mainCamera

    override fun getTextureLocation(entity: NukeCloudEntity) =
        if (entity.isBalefire) textureBalefire else textureFireball

    override fun render(
        entity: NukeCloudEntity,
        yaw: Float,
        partialTicks: Float,
        matrix: MatrixStack,
        renderer: IRenderTypeBuffer,
        light: Int
    ) {
        super.render(entity, yaw, partialTicks, matrix, renderer, light)

        matrix.pushPose()

        renderMush(entity, matrix, partialTicks, renderer, light)
        renderCloudlets(entity, matrix, partialTicks)
        renderFlash(entity, matrix, partialTicks, renderer)
        shakeCamera(entity)
        // TODO maybe some fog

        matrix.popPose()
    }

    private fun renderMush(
        entity: NukeCloudEntity,
        matrix: MatrixStack,
        partialTicks: Float,
        renderer: IRenderTypeBuffer,
        light: Int
    ) {
        matrix.pushPose()

        val size = entity.scale * 5
        matrix.scale(size, size, size)

        val progress = entity.age + partialTicks
        val height = max(20 - 30 * 20 / (progress * .5F - 5), 0F) // arbitrary values apparently
        val expansion = 100F
        val width = min(progress, expansion) / expansion * .3F + .7F
        matrix.pushPose()
        matrix.translate(0.0, -26.0 + height, 0.0)
        matrix.scale(width, 1F, width)

        val fade = (progress * .0075F).coerceIn(0F, 1F)
        val red: Float
        val green: Float
        val blue: Float
        if (entity.isBalefire) {
            red = 1F - (1F - .64F) * fade
            green = 1F
            blue = 1F - (1F - .5F) * fade
        } else {
            red = 1F
            green = 1F - (1F - .7F) * fade
            blue = 1F - (1F - .48F) * fade
        }
        val model = modelManager.getModel(mushModel)
        val solidBuffer = renderer.getBuffer(CustomRenderTypes.nukeCloudMushSolid())
        modelRenderer.renderModel(matrix.last(), solidBuffer, null, model, red, green, blue, light, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE)
        val texturedBuffer = renderer.getBuffer(CustomRenderTypes.nukeCloudMushTextured(getTextureLocation(entity), -progress * .035F))
        modelRenderer.renderModelAlpha(matrix.last(), texturedBuffer, null, model, 1F, 1F, 1F, fade * .875F, light, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE)

        matrix.popPose()
        matrix.popPose()
    }

    // FIXME things with transparency show behind the cloudlets (mushroom cloud, entities, ...)
    @Suppress("DEPRECATION")
    private fun renderCloudlets(entity: NukeCloudEntity, matrix: MatrixStack, partialTicks: Float) {
        if (entity.cloudlets.isEmpty()) return

        matrix.pushPose()
        RenderSystem.disableAlphaTest()
        RenderSystem.enableDepthTest()
        RenderSystem.enableFog()
        RenderSystem.pushMatrix()
        RenderSystem.multMatrix(matrix.last().pose())
        RenderSystem.depthMask(false)
        textureManager.bind(textureCloudlet)
        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
        RenderSystem.alphaFunc(GL11.GL_GREATER, .01F)

        val buffer = Tessellator.getInstance().builder
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX)

        for (cloudlet in entity.cloudlets) {
            val mushAgeAtCloudletCreation = entity.age + partialTicks - cloudlet.age
            val x = (cloudlet.posX).toFloat()
            val y = (cloudlet.posY - entity.y + 2.0).toFloat()
            val z = (cloudlet.posZ).toFloat()
            var alpha = (1F - max(mushAgeAtCloudletCreation / 50F, 0F)).coerceIn(0F, 1F)
            val originalAlpha = alpha
            val scale = 5F * (alpha * .5F + .5F)
            if (mushAgeAtCloudletCreation < 3) alpha = mushAgeAtCloudletCreation * .333F

            val quaternion = camera.rotation()
            val vertices = arrayOf(
                Vector3f(-1F, -1F, 0F),
                Vector3f(-1F, 1F, 0F),
                Vector3f(1F, 1F, 0F),
                Vector3f(1F, -1F, 0F)
            )

            for (vertex in vertices) {
                vertex.transform(quaternion)
                vertex.mul(scale)
                vertex.add(x, y, z)
            }

            val random = Random(((x * 5F + y * 25F + z * 125F) * 1000F).toLong())
            val brightness = random.nextFloat() * .25F + .25F

            val red: Float
            val green: Float
            val blue: Float

            if (entity.isBalefire) {
                red = .25F * originalAlpha
                green = (originalAlpha - brightness * .5F).coerceIn(0F, 1F)
                blue = .25F * originalAlpha
            } else {
                red = brightness
                green = brightness
                blue = brightness
            }

            buffer.vertex(vertices[0].x().toDouble(), vertices[0].y().toDouble(), vertices[0].z().toDouble()).color(red, green, blue, alpha).uv(1F, 1F).endVertex()
            buffer.vertex(vertices[1].x().toDouble(), vertices[1].y().toDouble(), vertices[1].z().toDouble()).color(red, green, blue, alpha).uv(1F, 0F).endVertex()
            buffer.vertex(vertices[2].x().toDouble(), vertices[2].y().toDouble(), vertices[2].z().toDouble()).color(red, green, blue, alpha).uv(0F, 0F).endVertex()
            buffer.vertex(vertices[3].x().toDouble(), vertices[3].y().toDouble(), vertices[3].z().toDouble()).color(red, green, blue, alpha).uv(0F, 1F).endVertex()
        }

        Tessellator.getInstance().end()
        RenderSystem.popMatrix()
        RenderSystem.enableAlphaTest()
        RenderSystem.disableFog()
        RenderSystem.disableBlend()
        RenderSystem.depthMask(true)
        RenderSystem.defaultAlphaFunc()
        matrix.popPose()
    }

    private fun renderFlash(entity: NukeCloudEntity, matrix: MatrixStack, partialTicks: Float, renderer: IRenderTypeBuffer) {
        if (entity.age >= 60) return

        var intensity = (entity.age + partialTicks) / 60.0
        intensity *= E.pow(-intensity) * E
        val random = Random(432L)
        val builder = renderer.getBuffer(CustomRenderTypes.nukeCloudFlash)

        matrix.pushPose()
        matrix.scale(.2F, .2F, .2F)

        for (i in 0 until 300) {
            matrix.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360F))
            matrix.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360F))
            matrix.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360F))
            matrix.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360F))
            matrix.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360F))
            matrix.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360F))

            val vert1 = (random.nextFloat() * 20F + 15F) * intensity.toFloat() * 100F
            val vert2 = (random.nextFloat() * 2F + 3F) * intensity.toFloat() * 100
            val matrix4f = matrix.last().pose()
            val alpha = 1F - intensity.toFloat()

            builder.vertex(matrix4f, 0F, 0F, 0F).color(1F, 1F, 1F, alpha).endVertex()
            builder.vertex(matrix4f, -0.866F * vert2, vert1, -0.5F * vert2).color(1F, 1F, 1F, 0F).endVertex()
            builder.vertex(matrix4f, 0.866F * vert2, vert1, -0.5F * vert2).color(1F, 1F, 1F, 0F).endVertex()
            builder.vertex(matrix4f, 0F, vert1, vert2).color(1F, 1F, 1F, 0F).endVertex()
            builder.vertex(matrix4f, -0.866F * vert2, vert1, -0.5F * vert2).color(1F, 1F, 1F, 0F).endVertex()
        }

        matrix.popPose()
    }

    private fun shakeCamera(entity: NukeCloudEntity) { // TODO shake intensity depending on distance
        val distance = sqrt(entity.distanceToSqr(camera.position))
        val distanceToShockwave = distance - entity.age * 2
        if (distanceToShockwave > -5 && distanceToShockwave < 5)
            ClientRenderer.CameraShake.setIntensityAtLeast(1.0) // TODO calculate shockwave strength?
        val distanceForGroundShake = distance - entity.age * 5
        if (distanceForGroundShake < 10) {
            if (distanceForGroundShake < 0) ClientRenderer.CameraShake.setIntensityAtLeast(entity.scale * 0.2)
            else ClientRenderer.CameraShake.setIntensityAtLeast(entity.scale * 0.7)
        }
    }
}
