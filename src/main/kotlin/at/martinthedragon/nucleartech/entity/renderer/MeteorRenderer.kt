package at.martinthedragon.nucleartech.entity.renderer

import at.martinthedragon.nucleartech.entity.Meteor
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
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture

class MeteorRenderer(context: EntityRendererProvider.Context) : EntityRenderer<Meteor>(context) {
    private val rotationVector = Vector3f(1F, 1F, 1F).apply(Vector3f::normalize)
    private val meteorModel = MeteorModel(Minecraft.getInstance().entityModels.bakeLayer(NuclearModelLayers.METEOR))

    override fun getTextureLocation(meteor: Meteor) = ntm("textures/entity/meteor/meteor.png")

    override fun render(meteor: Meteor, yaw: Float, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int) {
        super.render(meteor, yaw, partials, matrix, buffers, light)

        matrix.pushPose()
        matrix.mulPose(rotationVector.rotationDegrees(meteor.tickCount % 360 * 10F))
        matrix.scale(5F, 5F, 5F)

        val consumer = buffers.getBuffer(meteorModel.renderType(getTextureLocation(meteor)))
        meteorModel.renderToBuffer(matrix, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F)

        matrix.popPose()
    }

    class MeteorModel(private val root: ModelPart) : Model(RenderType::entitySolid) {
        override fun renderToBuffer(matrix: PoseStack, consumer: VertexConsumer, light: Int, overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {
            root.render(matrix, consumer, light, overlay, red, green, blue, alpha)
        }

        companion object {
            fun createLayerDefinition(): LayerDefinition {
                val meshDefinition = MeshDefinition().apply {
                    root.addOrReplaceChild("cube1", CubeListBuilder.create().addBox(-8F, -8F, -8F, 8F, 8F, 8F), PartPose.ZERO)
                }
                return LayerDefinition.create(meshDefinition, 64, 32)
            }
        }
    }
}
