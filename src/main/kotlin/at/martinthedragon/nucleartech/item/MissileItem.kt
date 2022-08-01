package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.entity.missile.AbstractMissile
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level

class MissileItem<out M : AbstractMissile>(
    val missileSupplier: (level: Level, startPos: BlockPos, targetPos: BlockPos) -> M,
    properties: Properties,
    private val missileTexture: ResourceLocation? = null,
    val renderModel: ResourceLocation = AbstractMissile.missileModel("missile_v2"),
    val renderScale: Float = 1F
) : Item(properties.stacksTo(1)) {
    val renderTexture: ResourceLocation get() = missileTexture ?: AbstractMissile.missileTexture(registryName!!.path)
}
