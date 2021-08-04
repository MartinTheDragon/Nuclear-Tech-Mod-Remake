package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.NukeExplosionEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World
import kotlin.math.ceil

// more like destructive
class CreativeNuclearExplosionSpawner(properties: Properties) : Item(properties) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val item = player.getItemInHand(hand)

        if (!world.isClientSide) {
            world.addFreshEntity(NukeExplosionEntity(EntityTypes.nukeExplosionEntity.get(), world).apply {
                strength = 300
                speed = ceil(100000F / strength).toInt()
                length = strength / 2
                moveTo(player.position())
            })
        }

        return ActionResult.sidedSuccess(item, world.isClientSide)
    }
}
