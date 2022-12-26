package at.martinthedragon.nucleartech.entity.renderer

import at.martinthedragon.nucleartech.entity.Shrapnel
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture

class ShrapnelRenderer(context: EntityRendererProvider.Context) : EntityRenderer<Shrapnel>(context) {
    override fun getTextureLocation(shrapnel: Shrapnel) = ntm("textures/entity/shrapnel/shrapnel.png")

    private val rotationVector = Vector3f(1F, 1F, 1F).apply(Vector3f::normalize)

    private val shrapnelModel = ShrapnelModel(Minecraft.getInstance().entityModels.bakeLayer(NuclearModelLayers.SHRAPNEL))

    override fun render(shrapnel: Shrapnel, yaw: Float, partialTicks: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int) {
        super.render(shrapnel, yaw, partialTicks, matrix, buffers, light)

        matrix.pushPose()
        matrix.mulPose(Vector3f.XP.rotationDegrees(180F))
        matrix.mulPose(rotationVector.rotationDegrees((shrapnel.tickCount % 360) * 10 + partialTicks))

        val consumer = buffers.getBuffer(shrapnelModel.renderType(getTextureLocation(shrapnel)))
        shrapnelModel.renderToBuffer(matrix, consumer, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F)

        matrix.popPose()
    }

    class ShrapnelModel(private val root: ModelPart) : Model(RenderType::entitySolid) {
        override fun renderToBuffer(matrix: PoseStack, consumer: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
            root.render(matrix, consumer, light, overlay, red, green, blue, alpha)
        }

        companion object {
            fun createLayerDefinition(): LayerDefinition {
                val meshDefinition = MeshDefinition().apply {
                    root.addOrReplaceChild("cube1", CubeListBuilder.create().addBox(-2F, -2F, -2F, 2F, 2F, 2F), PartPose.ZERO)
                }
                return LayerDefinition.create(meshDefinition, 16, 8)
            }
        }
    }
}
