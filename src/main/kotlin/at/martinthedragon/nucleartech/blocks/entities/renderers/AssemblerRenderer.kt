package at.martinthedragon.nucleartech.blocks.entities.renderers

import at.martinthedragon.nucleartech.blocks.entities.AssemblerBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraftforge.client.model.IModelConfiguration
import net.minecraftforge.client.model.StandaloneModelConfiguration
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.client.model.obj.OBJModel.ModelSettings
import net.minecraftforge.client.model.renderable.MultipartTransforms
import kotlin.math.PI
import kotlin.math.sin

class AssemblerRenderer(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<AssemblerBlockEntity> {
    private val bodyModel = ntm("models/other/assembler/assembler_body.obj")
    private val cogModel = ntm("models/other/assembler/assembler_cog.obj")
    private val sliderModel = ntm("models/other/assembler/assembler_slider.obj")
    private val armModel = ntm("models/other/assembler/assembler_arm.obj")

    init {
        val modelLoadFunction = { id: ResourceLocation ->
            OBJLoader.INSTANCE
                .loadModel(ModelSettings(id, false, false, true, true, null))
                .bakeRenderable(getModelConfigurationFor(id))
        }

        SpecialModels.registerModel(bodyModel, modelLoadFunction)
        SpecialModels.registerModel(cogModel, modelLoadFunction)
        SpecialModels.registerModel(sliderModel, modelLoadFunction)
        SpecialModels.registerModel(armModel, modelLoadFunction)
    }

    private fun getModelConfigurationFor(id: ResourceLocation): IModelConfiguration =
        StandaloneModelConfiguration.create(id, mapOf("#texture" to ResourceLocation(id.namespace, "block/${id.path.removeSuffix(".obj").removePrefix("models/other/")}")))

    override fun render(assembler: AssemblerBlockEntity, partialTicks: Float, matrix: PoseStack, bufferSource: MultiBufferSource, light: Int, overlay: Int) {
        val level = assembler.level ?: return

        val body = SpecialModels.getModel(bodyModel)
        val cog = SpecialModels.getModel(cogModel)
        val slider = SpecialModels.getModel(sliderModel)
        val arm = SpecialModels.getModel(armModel)

        val state = assembler.blockState

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        matrix.mulPose(Vector3f.YN.rotationDegrees(state.getValue(HorizontalDirectionalBlock.FACING).toYRot()))
        matrix.translate(-.5, .0, -.5)
        body.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)

        val recipeID = assembler.recipeID
        if (recipeID != null) try {
            matrix.pushPose()
            matrix.translate(.0, .875, .0)
            val recipe = level.recipeManager.byKey(recipeID).orElseThrow(::RuntimeException)
            val stack = recipe.resultItem
            if (stack.item is BlockItem) {
                matrix.scale(.5F, .5F, .5F)
                matrix.translate(.0, -.875, -2.0)
            } else {
                matrix.mulPose(Vector3f.XP.rotationDegrees(-90F))
            }

            Minecraft.getInstance().itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, light, overlay, matrix, bufferSource, state.getSeed(assembler.blockPos).toInt())
            matrix.popPose()
        } catch (ignored: Exception) {
        }

        matrix.pushPose()
        if (assembler.isProgressing) {
            val offset = ((System.currentTimeMillis() % 5000).toInt() / 5).let { if (it > 500) 500 - (it - 500) else it }
            matrix.translate(offset * .003 - .75, .0, .0)
        }
        slider.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)

        if (assembler.isProgressing) {
            val sway = sin(System.currentTimeMillis() % 2000 * .5 / PI / 50)
            matrix.translate(.0, .0, sway * .3)
        }
        arm.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        val rotation = if (assembler.isProgressing) (System.currentTimeMillis() % (360 * 5)).toInt() / 5F else 0F

        matrix.pushPose()
        matrix.translate(-.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, 1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(-.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.pushPose()
        matrix.translate(.6, .75, -1.0625)
        matrix.mulPose(Vector3f.ZP.rotationDegrees(rotation))
        cog.render(matrix, bufferSource, RenderType::entitySmoothCutout, light, overlay, partialTicks, MultipartTransforms.EMPTY)
        matrix.popPose()

        matrix.popPose()
    }

    override fun shouldRenderOffScreen(assembler: AssemblerBlockEntity) = true
}
