package at.martinthedragon.nucleartech.blocks.entities.renderers

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.blocks.entities.SteamPressBlockEntity
import at.martinthedragon.nucleartech.rendering.NuclearModelLayers
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.resources.model.Material
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu

class SteamPressRenderer(context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<SteamPressBlockEntity> {
    private val pressHead: ModelPart

    init {
        val modelPart = context.bakeLayer(NuclearModelLayers.STEAM_PRESS)
        pressHead = modelPart.getChild("press_head")
    }

    override fun render(
        tileEntity: SteamPressBlockEntity,
        partialTicks: Float,
        matrixStack: PoseStack,
        buffer: MultiBufferSource,
        light: Int,
        otherLight: Int
    ) {
        // TODO animation
        val position = 15F
        pressHead.y = position
        val vertexBuilder = PRESS_HEAD_MATERIAL.buffer(buffer, RenderType::entitySolid)
        pressHead.render(matrixStack, vertexBuilder, light, otherLight)
    }

    companion object {
        val PRESS_HEAD_MATERIAL = Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation(NuclearTech.MODID, "block/steam_press/steam_press_head"))

        fun createLayerDefinition(): LayerDefinition {
            val meshDefinition = MeshDefinition().apply {
                root.addOrReplaceChild("press_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -15F, -3.5F, 7F, 15F, 7F), PartPose.offset(8F, 15F, 8F))
                    .addOrReplaceChild("press_plate", CubeListBuilder.create().texOffs(0, 22).addBox(-5.5F, -2F, -5.5F, 11F, 2F, 11F), PartPose.offset(0F, -15F, 0F))
            }
            return LayerDefinition.create(meshDefinition, 64, 64)
        }
    }
}
