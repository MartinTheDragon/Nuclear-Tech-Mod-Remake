package at.martinthedragon.nucleartech.extensions

import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.client.renderer.texture.TextureAtlasSprite

fun TextureAtlasSprite.getAverageColor(frame: Int) = getAverageColor(frame, 0, 0, width, height)

fun TextureAtlasSprite.getAverageColor(frame: Int, u0: Int, v0: Int, u1: Int, v1: Int): Int {
    require(u0 <= u1 && v0 <= v1) { "Illegal UV coordinates: u0=$u0 v0=$v0 u1=$u1 v1=$v1" }

    var alphaSum = 0
    var redSum = 0
    var greenSum = 0
    var blueSum = 0

    for (u in u0..u1) for (v in v0..v1) {
        val pixel = getPixelRGBA(frame, u, v)
        alphaSum += NativeImage.getA(pixel)
        redSum += NativeImage.getR(pixel)
        greenSum += NativeImage.getG(pixel)
        blueSum += NativeImage.getB(pixel)
    }

    val pixelCount = (u1 - u0 + 1) * (v1 - v0 + 1)

    return NativeImage.combine(
        alphaSum / pixelCount,
        redSum / pixelCount,
        greenSum / pixelCount,
        blueSum / pixelCount
    )
}
