package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.entity.Meteor
import net.minecraft.Util
import net.minecraft.network.chat.ChatType
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class MeteorRemoteItem(properties: Properties) : AutoTooltippedItem(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemInHand = player.getItemInHand(hand)

        itemInHand.hurtAndBreak(1, player) {
            it.broadcastBreakEvent(hand)
        }

        if (!level.isClientSide) {
            Meteor.spawnMeteorAtPlayer(player as ServerPlayer, false)
            player.sendMessage(LangKeys.DEVICE_METEOR_REMOTE_WATCH_OUT.get(), ChatType.GAME_INFO, Util.NIL_UUID)
        }

        level.playSound(player, player.x, player.y, player.z, SoundEvents.randomBleep.get(), SoundSource.PLAYERS, 1F, 1F)

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide)
    }
}
