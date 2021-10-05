package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.Radiation
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.World

class GeigerCounterItem(properties: Properties) : Item(properties) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        if (!world.isClientSide) {
            player.playSound(SoundEvents.randomBoop.get(), 1F, 1F)
            printGeigerData(player)
        }
        return ActionResult.sidedSuccess(player.getItemInHand(hand), world.isClientSide)
    }

    companion object {
        fun printGeigerData(player: PlayerEntity) {
            val world = player.level
            // for that hbm approved decimal rounding
            val chunkRadiation = (ChunkRadiation.getRadiation(world, player.blockPosition()) * 10F).toInt() / 10F
            val playerIrradiation = (Radiation.getEntityIrradiation(player) * 10F).toInt() / 10F

            player.displayClientMessage(StringTextComponent("$chunkRadiation - $playerIrradiation"), false)
        }
    }
}
