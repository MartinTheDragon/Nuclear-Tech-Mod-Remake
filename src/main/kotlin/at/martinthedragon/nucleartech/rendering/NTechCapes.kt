package at.martinthedragon.nucleartech.rendering

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.ntm
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.CapeLayer
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.entity.player.PlayerRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import kotlin.math.PI

object NTechCapes {
    private var vanillaCapeLayer: CapeLayer? = null
    private var capeLayer: NTechCapeLayer? = null

    fun renderCape(player: Player, renderer: PlayerRenderer) {
        if (!NuclearConfig.client.displayCustomCapes.get()) return
        if (!playerCapes.contains(player.stringUUID)) return

        if (player.isInvisible || player.getItemBySlot(EquipmentSlot.CHEST).`is`(Items.ELYTRA)) return

        // intercept vanilla cape rendering
        vanillaCapeLayer = renderer.layers.filterIsInstance<CapeLayer>().firstOrNull()
        if (vanillaCapeLayer != null) renderer.layers.remove(vanillaCapeLayer)

        // replace it with our own
        if (capeLayer == null) {
            capeLayer = NTechCapeLayer(renderer)
        }

        renderer.addLayer(capeLayer!!)
    }

    fun renderCapePost(renderer: PlayerRenderer) {
        if (capeLayer != null) {
            renderer.layers.remove(capeLayer!!)
        }

        // restore vanilla cape rendering
        val vanillaCape = vanillaCapeLayer
        if (vanillaCape != null) {
            renderer.addLayer(vanillaCape)
            vanillaCapeLayer = null
        }
    }

    private class NTechCapeLayer(parent: RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) : RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(parent) {
        override fun render(matrix: PoseStack, buffers: MultiBufferSource, light: Int, player: AbstractClientPlayer, p_117353_: Float, p_117354_: Float, partials: Float, p_117356_: Float, p_117357_: Float, p_117358_: Float) {
            val capeLocation = playerCapes[player.stringUUID] ?: return

            matrix.pushPose()
            matrix.translate(.0, .0, .125)
            val x = Mth.lerp(partials.toDouble(), player.xCloakO, player.xCloak) - Mth.lerp(partials.toDouble(), player.xo, player.x)
            val y = Mth.lerp(partials.toDouble(), player.yCloakO, player.yCloak) - Mth.lerp(partials.toDouble(), player.yo, player.y)
            val z = Mth.lerp(partials.toDouble(), player.zCloakO, player.zCloak) - Mth.lerp(partials.toDouble(), player.zo, player.z)
            val playerRot = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO)
            val playerRotX = Mth.sin(playerRot * (PI.toFloat() / 180F))
            val playerRotZ = -Mth.cos(playerRot * (PI.toFloat() / 180F))
            var yRot = (y.toFloat() * 10F).coerceIn(-6F, 32F)
            val xRot = ((x * playerRotX + z * playerRotZ).toFloat() * 100F).coerceIn(0F, 150F)
            val zRot = ((x * playerRotZ - z * playerRotX).toFloat() * 100F).coerceIn(-20F, 20F)
            val bobbing = Mth.lerp(partials, player.oBob, player.bob)
            yRot += Mth.sin(Mth.lerp(partials, player.walkDistO, player.walkDist) * 6F) * 32F * bobbing
            if (player.isCrouching) yRot += 25F

            matrix.mulPose(Vector3f.XP.rotationDegrees(6F + xRot / 2F + yRot))
            matrix.mulPose(Vector3f.ZP.rotationDegrees(zRot / 2F))
            matrix.mulPose(Vector3f.YP.rotationDegrees(180F - zRot / 2F))

            val sprite = capeTextures.getSprite(if (NuclearTech.polaroidBroken && capeLocation.polaroid != null) capeLocation.polaroid else capeLocation.normal)
            val buffer = sprite.wrap(buffers.getBuffer(if (capeLocation.transparency) RenderType.entityTranslucent(CAPE_TEXTURE_ATLAS_LOCATION) else RenderType.entitySolid(CAPE_TEXTURE_ATLAS_LOCATION)))
            parentModel.renderCloak(matrix, buffer, light, OverlayTexture.NO_OVERLAY)
            matrix.popPose()
        }
    }

    val CAPE_TEXTURE_ATLAS_LOCATION = ntm("textures/atlas/cape.png")

    lateinit var capeTextures: CapeTextureManager

    private val wikiCape = CapeLocation(ntm("wiki"))

    private val playerCapes = with(NuclearTech.SpecialUsers) {
        mapOf(
            JulekJulas to CapeLocation(ntm("julekjulas_gif")),

            HbMinecraft to CapeLocation(ntm("hbminecraft"), ntm("hbminecraft_polaroid")),
            Drillgon to CapeLocation(ntm("drillgon")),
            Dafnik to CapeLocation(ntm("dafnik")),
            LPkukin to CapeLocation(ntm("lpkukin")),
            LordVertice to CapeLocation(ntm("vertice")),
            CodeRed_ to CapeLocation(ntm("red")),
            dxmaster769 to CapeLocation(ntm("dxmaster")),
            Dr_Nostalgia to CapeLocation(ntm("nostalgia"), ntm("nostalgia_polaroid")),
            Samino2 to CapeLocation(ntm("sam")),
            Hoboy03new to CapeLocation(ntm("hoboy")),
            Dragon59MC to CapeLocation(ntm("dragon59")),
            Steelcourage to CapeLocation(ntm("steelcourage")),
            ZippySqrl to CapeLocation(ntm("zippysqrl")),
            Schrabby to CapeLocation(ntm("schrabby")),
            SweatySwiggs to CapeLocation(ntm("sweatyswiggs")),
            Doctor17 to CapeLocation(ntm("doctor17")),
            Doctor17PH to CapeLocation(ntm("doctor17")),
            ShimmeringBlaze to CapeLocation(ntm("shimmeringblaze"), ntm("shimmeringblaze_polaroid")),
            mustang_rudolf to wikiCape,
            JMF781 to wikiCape,
            FifeMiner to CapeLocation(ntm("fifeminer"), transparency = true),
            lag_add to CapeLocation(ntm("lag_add"), transparency = true),
            Tankish to CapeLocation(ntm("tankish")),
            FrizzleFrazzle to CapeLocation(ntm("frizzlefrazzle")),
            Barnaby99_x to CapeLocation(ntm("pheo")),
            Ma118 to CapeLocation(ntm("vaer")),
            Adam29Adam29 to CapeLocation(ntm("adam29")),
        )
    }

    private data class CapeLocation(val normal: ResourceLocation, val polaroid: ResourceLocation? = null, val transparency: Boolean = false)

    // custom texture atlas for animated capes
    class CapeTextureManager(textureManager: TextureManager) : TextureAtlasHolder(textureManager, CAPE_TEXTURE_ATLAS_LOCATION, "cape") {
        override fun getResourcesToLoad() = buildList {
            for (capeLocation in playerCapes.values) {
                add(capeLocation.normal)
                if (capeLocation.polaroid != null)
                    add(capeLocation.polaroid)
            }
        }.stream()

        public override fun getSprite(location: ResourceLocation): TextureAtlasSprite = super.getSprite(location)
    }
}
