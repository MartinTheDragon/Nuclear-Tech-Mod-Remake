package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.Radiation
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.capabilites.Capabilities
import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import kotlin.random.Random

class GeigerCounterItem(properties: Properties) : Item(properties) {
    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        player.playSound(SoundEvents.randomBoop.get(), 1F, 1F)
        if (!world.isClientSide) {
            printGeigerData(player)
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide)
    }

    override fun inventoryTick(stack: ItemStack, world: Level, entity: Entity, slot: Int, isSelected: Boolean) {
        if (entity !is LivingEntity || world.isClientSide) return

        // TODO check for FSB armor

        val capability = Capabilities.getContamination(entity) ?: return

        val radPerSecond = capability.getRadPerSecond()

        if (world.gameTime % 5L == 0L) if (radPerSecond > 0) {
            var intensity = 0
            val likelihood = if (radPerSecond < 1) 3
            else if (radPerSecond < 5) 2
            else 1
            if (radPerSecond < 10) intensity = 1
            if (radPerSecond > 5 && radPerSecond < 15) intensity = intensity or (1 shl 1)
            if (radPerSecond > 10 && radPerSecond < 20) intensity = intensity or (1 shl 2)
            if (radPerSecond > 15 && radPerSecond < 25) intensity = intensity or (1 shl 3)
            if (radPerSecond > 20 && radPerSecond < 30) intensity = intensity or (1 shl 4)
            if (radPerSecond > 25) intensity = intensity or (1 shl 5)

            // making use of short-circuiting
            if ((likelihood == 1 || world.random.nextInt(likelihood) == 0) && intensity > 0) {
                val sound = when (Random.nextInt(intensity.countTrailingZeroBits(), intensity.countOneBits() + intensity.countTrailingZeroBits()) + 1) {
                    1 -> SoundEvents.geiger1
                    2 -> SoundEvents.geiger2
                    3 -> SoundEvents.geiger3
                    4 -> SoundEvents.geiger4
                    5 -> SoundEvents.geiger5
                    6 -> SoundEvents.geiger6
                    else -> SoundEvents.geiger1
                }

                world.playSound(null, entity, sound.get(), SoundSource.PLAYERS, 1F, 1F)
            }

        } else if (world.random.nextInt(50) == 0)
            world.playSound(null, entity,
                if (world.random.nextInt(2) == 0) SoundEvents.geiger2.get() else SoundEvents.geiger1.get(),
                SoundSource.PLAYERS, 1F, 1F
            )
    }

    companion object {
        fun printGeigerData(player: Player) {
            val world = player.level
            val capability = Capabilities.getContamination(player) ?: return

            // for that hbm approved decimal rounding
            val chunkRadiation = (ChunkRadiation.getRadiation(world, player.blockPosition()) * 10F).toInt() / 10F
            val environmentRadiation = (capability.getRadPerSecond() * 10F).toInt() / 10F
            val playerIrradiation = (Radiation.getEntityIrradiation(player) * 10F).toInt() / 10F
            val playerResistance = (10_000.0 - EntityContaminationEffects.calculateRadiationMod(player) * 10_000.0).toInt() / 100.0
            // TODO coefficient

            player.displayClientMessage(
                TextComponent("===== ☢ ")
                    .append(TranslatableComponent("geiger.title"))
                    .append(TextComponent(" ☢ ====="))
                    .withStyle(ChatFormatting.GOLD), false
            )
            player.displayClientMessage(
                TranslatableComponent("geiger.chunkRadiation")
                    .append(TextComponent(" $chunkRadiation RAD/s").withStyle(getColorForRadValue(chunkRadiation)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
            player.displayClientMessage(
                TranslatableComponent("geiger.totalEnvironmentRadiation")
                    .append(TextComponent(" $environmentRadiation RAD/s").withStyle(getColorForRadValue(environmentRadiation)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
            player.displayClientMessage(
                TranslatableComponent("geiger.playerIrradiation")
                    .append(TextComponent(" $playerIrradiation RAD").withStyle(getColorForPlayerRadValue(playerIrradiation)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
            player.displayClientMessage(
                TranslatableComponent("geiger.playerResistance")
                    .append(TextComponent(" $playerResistance%").withStyle(ChatFormatting.WHITE))
                    .withStyle(ChatFormatting.YELLOW), false
            )
        }

        private fun getColorForRadValue(rad: Float): ChatFormatting = when {
            rad == 0F -> ChatFormatting.GREEN
            rad < 1F -> ChatFormatting.YELLOW
            rad < 10F -> ChatFormatting.GOLD
            rad < 100F -> ChatFormatting.RED
            rad < 1000F -> ChatFormatting.DARK_RED
            else -> ChatFormatting.DARK_GRAY
        }

        private fun getColorForPlayerRadValue(rad: Float): ChatFormatting = when {
            rad < 200 -> ChatFormatting.GREEN
            rad < 400 -> ChatFormatting.YELLOW
            rad < 600 -> ChatFormatting.GOLD
            rad < 800 -> ChatFormatting.RED
            rad < 1000 -> ChatFormatting.DARK_RED
            else -> ChatFormatting.DARK_GRAY
        }
    }
}
