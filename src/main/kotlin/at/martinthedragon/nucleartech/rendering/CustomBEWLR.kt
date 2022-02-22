package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.items.AssemblyTemplate
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.world.item.ItemStack

object CustomBEWLR : BlockEntityWithoutLevelRenderer(Minecraft.getInstance().blockEntityRenderDispatcher, Minecraft.getInstance().entityModels) {
    override fun renderByItem(stack: ItemStack, transformType: ItemTransforms.TransformType, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        /*
        Okay so this works using a special model definition in the item's model json using item property overrides. Basically, there is a property that gets checked, and if the condition returns
        true, a different model gets rendered instead. In this case, the property passed to the model json is 1F if the shift button is held down, 0F otherwise (handled in ClientRegistries).
        So if shift is held down, a special model gets rendered which returns true for BakedModel#isCustomRenderer. This makes the ItemRenderer use our CustomBEWLR specified in Item#initializeClient,
        with which we can do all kinds of things with rendering.
         */
        if (stack.item is AssemblyTemplate) {
            val level = Minecraft.getInstance().level
            if (level != null) {
                val recipe = AssemblyTemplate.getRecipeFromStack(stack, level.recipeManager)
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
        }
    }
}
