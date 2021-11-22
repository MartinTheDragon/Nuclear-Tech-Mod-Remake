package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation

@Suppress("INACCESSIBLE_TYPE")
object CustomRenderTypes : RenderType(
    "${NuclearTech.MODID}_render_types",
    DefaultVertexFormats.BLOCK,
    0, 0, false, false,
    { throw IllegalStateException("Do not use this instance!") },
    { throw IllegalStateException("Do not use this instance!") }
) {
    val nukeCloudFlash: RenderType = create("${NuclearTech.MODID}_nuke_cloud_flash",
        DefaultVertexFormats.POSITION_COLOR, 7, 256,
        false, true, State.builder()
            .setShadeModelState(SMOOTH_SHADE)
            .setTransparencyState(LIGHTNING_TRANSPARENCY)
            .setAlphaState(NO_ALPHA)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(false)
    )

    fun nukeCloudMushTextured(texture: ResourceLocation, textureOffsetY: Float): RenderType {
        val state = State.builder()
            .setTextureState(TextureState(texture, false, false))
            .setTexturingState(OffsetTexturingState(0F, textureOffsetY))
            .setShadeModelState(SMOOTH_SHADE)
            .setDiffuseLightingState(DIFFUSE_LIGHTING)
            .setDepthTestState(EQUAL_DEPTH_TEST)
            .setCullState(NO_CULL)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(TRANSLUCENT_TARGET)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .createCompositeState(true)
        return create("${NuclearTech.MODID}_nuke_cloud_mush_textured", DefaultVertexFormats.BLOCK, 7, 512 * 256, true, true, state)
    }

    fun nukeCloudMushSolid(): RenderType {
        val state = State.builder()
            .setTextureState(NO_TEXTURE)
            .setShadeModelState(SMOOTH_SHADE)
            .setCullState(NO_CULL)
            .setLightmapState(NO_LIGHTMAP)
            .createCompositeState(true)
        return create("${NuclearTech.MODID}_nuke_cloud_mush_solid", DefaultVertexFormats.BLOCK, 7, 512 * 256, true, true, state)
    }
}
