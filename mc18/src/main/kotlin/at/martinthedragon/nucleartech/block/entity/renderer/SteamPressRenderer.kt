package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.SteamPressBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.resources.model.Material
import net.minecraft.world.inventory.InventoryMenu

class SteamPressRenderer(context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<SteamPressBlockEntity> {
    private val pressHead: ModelPart = context.bakeLayer(NuclearModelLayers.STEAM_PRESS).getChild("press_head")
    private val itemRenderer: ItemRenderer = Minecraft.getInstance().itemRenderer

    override fun render(press: SteamPressBlockEntity, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        matrix.pushPose()

        if (press.progress > 0) {
            val position = 15F - (press.progress + partials) * 14F / SteamPressBlockEntity.PRESS_TIME
            pressHead.y = position
        } else pressHead.y = 15F
        val vertexBuilder = PRESS_HEAD_MATERIAL.buffer(buffers, RenderType::entitySolid)
        pressHead.render(matrix, vertexBuilder, light, overlay)

        matrix.pushPose()
        matrix.translate(.5, -1.0 + .5 / 16.0, .5)
        matrix.mulPose(Vector3f.XP.rotationDegrees(-90F))
        val recipe = press.recipe
        if (recipe != null)
            itemRenderer.renderStatic(recipe.resultItem, ItemTransforms.TransformType.FIXED, light, overlay, matrix, buffers, 11)
        matrix.popPose()

        matrix.popPose()
    }

    override fun shouldRenderOffScreen(press: SteamPressBlockEntity) = true
    override fun getViewDistance() = 256

    companion object {
        val PRESS_HEAD_MATERIAL = Material(InventoryMenu.BLOCK_ATLAS, ntm("block/steam_press/steam_press_head"))

        fun createLayerDefinition(): LayerDefinition {
            val meshDefinition = MeshDefinition().apply {
                root.addOrReplaceChild("press_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -15F, -3.5F, 7F, 15F, 7F), PartPose.offset(8F, 15F, 8F))
                    .addOrReplaceChild("press_plate", CubeListBuilder.create().texOffs(0, 22).addBox(-5.5F, -2F, -5.5F, 11F, 2F, 11F), PartPose.offset(0F, -15F, 0F))
            }
            return LayerDefinition.create(meshDefinition, 64, 64)
        }
    }
}
