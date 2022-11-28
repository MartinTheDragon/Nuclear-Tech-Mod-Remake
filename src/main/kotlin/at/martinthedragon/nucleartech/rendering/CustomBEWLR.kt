package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.extensions.prependToPath
import at.martinthedragon.nucleartech.item.AssemblyTemplateItem
import at.martinthedragon.nucleartech.item.ChemPlantTemplateItem
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.item.SpecialRenderingBlockEntityItem
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.client.IItemRenderProperties

object CustomBEWLR : BlockEntityWithoutLevelRenderer(Minecraft.getInstance().blockEntityRenderDispatcher, Minecraft.getInstance().entityModels) {
    private val blockEntityRenderDispatcher = Minecraft.getInstance().blockEntityRenderDispatcher
    private val defaultBlockEntityCache = mutableMapOf<SpecialRenderingBlockEntityItem, BlockEntity>()

    override fun renderByItem(stack: ItemStack, transformType: ItemTransforms.TransformType, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        /*
        Okay so this works using a special model definition in the item's model json using item property overrides. Basically, there is a property that gets checked, and if the condition returns
        true, a different model gets rendered instead. In this case, the property passed to the model json is 1F if the shift button is held down, 0F otherwise (handled in ClientRegistries).
        So if shift is held down, a special model gets rendered which returns true for BakedModel#isCustomRenderer. This makes the ItemRenderer use our CustomBEWLR specified in Item#initializeClient,
        with which we can do all kinds of things with rendering.
         */
        val item = stack.item
        if (item is AssemblyTemplateItem) {
            val level = Minecraft.getInstance().level
            if (level != null) {
                val recipe = AssemblyTemplateItem.getRecipeFromStack(stack, level.recipeManager)
                if (recipe != null) {
                    if (!stack.isEmpty) {
                        matrix.translate(.5, .5, .5)
                        val itemRenderer = Minecraft.getInstance().itemRenderer
                        val resultItem = recipe.resultItem
                        val model = itemRenderer.getModel(resultItem, null, null, 0)
                        if (transformType == ItemTransforms.TransformType.GUI && !model.usesBlockLight()) {
                            val bufferSource = Minecraft.getInstance().renderBuffers().bufferSource()
                            Lighting.setupForFlatItems()
                            itemRenderer.renderStatic(resultItem, transformType, light, overlay, matrix, bufferSource, 0)
                            bufferSource.endBatch()
                            Lighting.setupFor3DItems()
                        } else {
                            itemRenderer.renderStatic(resultItem, transformType, light, overlay, matrix, buffers, 0)
                        }
                    }
                }
            }
        } else if (item is ChemPlantTemplateItem) {
            val level = Minecraft.getInstance().level
            if (level != null) {
                val recipe = ChemPlantTemplateItem.getRecipeIDFromStack(stack)
                val modelLocation = recipe?.prependToPath("chemistry_icons/") ?: NTechItems.chemTemplate.id
                val model = Minecraft.getInstance().modelManager.getModel(modelLocation)
                val itemRenderer = Minecraft.getInstance().itemRenderer
                matrix.translate(.5, .5, .5)
                if (transformType == ItemTransforms.TransformType.GUI && !model.usesBlockLight()) {
                    val bufferSource = Minecraft.getInstance().renderBuffers().bufferSource()
                    Lighting.setupForFlatItems()
                    itemRenderer.render(stack, transformType, false, matrix, bufferSource, light, overlay, model)
                    bufferSource.endBatch()
                    Lighting.setupFor3DItems()
                } else {
                    itemRenderer.render(stack, transformType, false, matrix, buffers, light, overlay, model)
                }
            }
        } else if (item is SpecialRenderingBlockEntityItem) {
            val blockEntity = defaultBlockEntityCache.computeIfAbsent(item) { it.blockEntityFunc(BlockPos.ZERO, it.blockStateForRendering) }
            blockEntityRenderDispatcher.renderItem(blockEntity, matrix, buffers, light, overlay)
        }
    }

    object DefaultRenderProperties : IItemRenderProperties {
        override fun getItemStackRenderer() = CustomBEWLR
    }
}
