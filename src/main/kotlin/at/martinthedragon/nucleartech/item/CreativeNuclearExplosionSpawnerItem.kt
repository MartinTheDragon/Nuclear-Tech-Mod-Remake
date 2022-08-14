package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.api.explosion.NuclearExplosionMk4Params
import at.martinthedragon.nucleartech.entity.NukeExplosion
import at.martinthedragon.nucleartech.screen.UseCreativeNuclearExplosionSpawnerScreen
import net.minecraft.Util
import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

// more like destructive
class CreativeNuclearExplosionSpawnerItem(properties: Properties) : Item(properties) {
    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val item = player.getItemInHand(hand)

        if (player.isShiftKeyDown) { // quick detonation
            if (!world.isClientSide) {
                if (player.canUseGameMasterBlocks()) {
                    NukeExplosion.createAndStart(world, player.position(), 200F, NuclearExplosionMk4Params())
                } else {
                    player.sendMessage(UseCreativeNuclearExplosionSpawnerScreen.ERR_INSUFFICIENT_PERMISSION, Util.NIL_UUID)
                    return InteractionResultHolder.pass(item)
                }
            }
        } else if (world.isClientSide) { // open advanced GUI
            if (player.canUseGameMasterBlocks())
                Minecraft.getInstance().setScreen(UseCreativeNuclearExplosionSpawnerScreen())
            else player.displayClientMessage(UseCreativeNuclearExplosionSpawnerScreen.ERR_INSUFFICIENT_PERMISSION, false)
        }

        return InteractionResultHolder.sidedSuccess(item, world.isClientSide)
    }
}
