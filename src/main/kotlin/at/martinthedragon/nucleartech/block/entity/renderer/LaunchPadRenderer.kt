package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.LaunchPadBlockEntity
import at.martinthedragon.nucleartech.item.MissileItem
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.ForgeModelBakery
import net.minecraftforge.client.model.SimpleModelState
import net.minecraftforge.client.model.StandaloneModelConfiguration
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.client.model.obj.OBJModel
import net.minecraftforge.client.model.renderable.MultipartTransforms
import net.minecraftforge.client.textures.UnitSprite

class LaunchPadRenderer(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<LaunchPadBlockEntity> {
    private val launchPadModel = ntm("models/other/launch_pad/launch_pad.obj")
    private val launchPadTexture = ntm("block/launch_pad")
    private val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer

    init {
        SpecialModels.registerModel(launchPadModel) {
            OBJLoader.INSTANCE
                .loadModel(OBJModel.ModelSettings(it, true, false, true, true, null))
                .bakeRenderable(StandaloneModelConfiguration.create(it, mapOf("#texture" to launchPadTexture)))
        }
    }

    private fun registerMissileModel(id: ResourceLocation) = OBJLoader.INSTANCE
        .loadModel(OBJModel.ModelSettings(id, false, false, true, true, null))
        .bake(StandaloneModelConfiguration.INSTANCE, ForgeModelBakery.instance(), UnitSprite.GETTER, SimpleModelState.IDENTITY, ItemOverrides.EMPTY, id)

    override fun render(launchPad: LaunchPadBlockEntity, partials: Float, stack: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        val model = SpecialModels.getModel(launchPadModel)
        val missileItem = launchPad.missileItem.item as? MissileItem<*>

        stack.pushPose()
        stack.translate(.5, .0, .5)
        model.render(stack, buffers, RenderType::entitySmoothCutout, light, overlay, partials, MultipartTransforms.EMPTY)

        if (missileItem != null) {
            val missileModel = SpecialModels.getOrRegisterBakedModel(missileItem.renderModel, this::registerMissileModel)
            val renderBuffer = buffers.getBuffer(RenderType.entityCutoutNoCull(missileItem.renderTexture))
            val renderScale = missileItem.renderScale
            stack.pushPose()
            stack.translate(0.0, 1.0, 0.0)
            stack.scale(renderScale, renderScale, renderScale)
            modelRenderer.renderModel(stack.last(), renderBuffer, null, missileModel, 1F, 1F, 1F, light, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE)
            stack.popPose()
        }

        stack.popPose()
    }

    override fun shouldRenderOffScreen(launchPad: LaunchPadBlockEntity) = true
    override fun getViewDistance() = 512
}
