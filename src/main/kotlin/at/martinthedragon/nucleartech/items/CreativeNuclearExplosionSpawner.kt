package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.NukeExplosionEntity
import at.martinthedragon.nucleartech.screens.UseCreativeNuclearExplosionSpawnerScreen
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.Util
import net.minecraft.world.World
import kotlin.math.ceil

// more like destructive
class CreativeNuclearExplosionSpawner(properties: Properties) : Item(properties) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val item = player.getItemInHand(hand)

        if (player.isShiftKeyDown) { // quick detonation
            if (!world.isClientSide) {
                if (player.canUseGameMasterBlocks()) {
                    world.addFreshEntity(NukeExplosionEntity(EntityTypes.nukeExplosionEntity.get(), world).apply {
                        strength = 200
                        speed = ceil(100000F / strength).toInt()
                        length = strength / 2
                        moveTo(player.position())
                    })
                } else {
                    player.sendMessage(UseCreativeNuclearExplosionSpawnerScreen.ERR_INSUFFICIENT_PERMISSION, Util.NIL_UUID)
                    return ActionResult(ActionResultType.PASS, item)
                }
            }
        } else if (world.isClientSide) { // open advanced GUI
            if (player.canUseGameMasterBlocks())
                Minecraft.getInstance().setScreen(UseCreativeNuclearExplosionSpawnerScreen())
            else player.displayClientMessage(UseCreativeNuclearExplosionSpawnerScreen.ERR_INSUFFICIENT_PERMISSION, false)
        }

        return ActionResult.sidedSuccess(item, world.isClientSide)
    }
}
