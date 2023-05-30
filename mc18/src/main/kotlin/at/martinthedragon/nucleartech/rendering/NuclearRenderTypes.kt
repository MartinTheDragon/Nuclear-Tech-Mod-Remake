package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.ClientRegistries
import at.martinthedragon.nucleartech.NuclearTech
import com.google.common.collect.ImmutableMap
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormatElement
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation

@Suppress("INACCESSIBLE_TYPE")
object NuclearRenderTypes : RenderType(
    "${NuclearTech.MODID}_render_types",
    DefaultVertexFormat.BLOCK,
    VertexFormat.Mode.LINES, 0, false, false,
    { throw IllegalStateException("Do not use this instance!") },
    { throw IllegalStateException("Do not use this instance!") }
) {
    val mushroomCloudFlash: RenderType = create("${NuclearTech.MODID}_mushroom_cloud_flash",
        DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256,
        false, true, CompositeState.builder()
            .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
            .setTransparencyState(LIGHTNING_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(false)
    )

    val mushroomCloudSolid: RenderType = create("${NuclearTech.MODID}_mushroom_cloud_solid",
        DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 512 * 256, true, true, CompositeState.builder()
            .setTextureState(NO_TEXTURE)
            .setShaderState(POSITION_COLOR_SHADER)
            .setCullState(NO_CULL)
            .setOutputState(ITEM_ENTITY_TARGET)
            .setLightmapState(NO_LIGHTMAP)
            .createCompositeState(false)
    )

    val mushroomCloudTransparent: RenderType = create("${NuclearTech.MODID}_mushroom_cloud_transparent",
        DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 512 * 256, true, true, CompositeState.builder()
            .setTextureState(NO_TEXTURE)
            .setShaderState(POSITION_COLOR_SHADER)
            .setCullState(NO_CULL)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(ITEM_ENTITY_TARGET)
            .setLightmapState(NO_LIGHTMAP)
            .createCompositeState(false)
    )

    val rbmkCherenkov: RenderType = create("${NuclearTech.MODID}_rbmk_cherenkov",
        DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, CompositeState.builder()
            .setLightmapState(NO_LIGHTMAP)
            .setCullState(NO_CULL)
            .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
            .setTransparencyState(LIGHTNING_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(false)
    )

    fun mushroomCloudTextured(texture: ResourceLocation, textureOffsetY: Float): RenderType {
        val state = CompositeState.builder()
            .setTextureState(TextureStateShard(texture, false, false))
            .setTexturingState(OffsetTexturingStateShard(0F, textureOffsetY))
            .setShaderState(RENDERTYPE_MUSHROOM_CLOUD_SHADER)
            .setDepthTestState(EQUAL_DEPTH_TEST)
            .setCullState(NO_CULL)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(ITEM_ENTITY_TARGET)
            .setLightmapState(LIGHTMAP)
            .createCompositeState(false)
        return create("${NuclearTech.MODID}_mushroom_cloud_textured", VertexFormats.POSITION_COLOR_TEX_NORMAL, VertexFormat.Mode.QUADS, 512 * 256, true, true, state)
    }

    private val RENDERTYPE_MUSHROOM_CLOUD_SHADER = ShaderStateShard(ClientRegistries.Shaders::rendertypeMushroomCloudShader)

    object VertexFormats {
        val POSITION_COLOR_TEX_NORMAL = VertexFormat(ImmutableMap.builder<String, VertexFormatElement>().put("Position", DefaultVertexFormat.ELEMENT_POSITION).put("Color", DefaultVertexFormat.ELEMENT_COLOR).put("UV0", DefaultVertexFormat.ELEMENT_UV0).put("Normal", DefaultVertexFormat.ELEMENT_NORMAL).put("Padding", DefaultVertexFormat.ELEMENT_PADDING).build())
    }
}
