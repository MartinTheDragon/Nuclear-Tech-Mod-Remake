package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.extensions.append
import at.martinthedragon.nucleartech.hazard.EntityContaminationEffects
import at.martinthedragon.nucleartech.hazard.HazmatValues
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
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
        player.playSound(NTechSoundsCore.randomBoop.get(), 1F, 1F)
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
            if ((likelihood == 1 || world.random.nextInt(likelihood) == 0)) {
                val sound = when (Random.nextInt(intensity.countTrailingZeroBits(), intensity.countOneBits() + intensity.countTrailingZeroBits()) + 1) {
                    1 -> NTechSoundsCore.geiger1
                    2 -> NTechSoundsCore.geiger2
                    3 -> NTechSoundsCore.geiger3
                    4 -> NTechSoundsCore.geiger4
                    5 -> NTechSoundsCore.geiger5
                    6 -> NTechSoundsCore.geiger6
                    else -> NTechSoundsCore.geiger1
                }

                world.playSound(null, entity, sound.get(), SoundSource.PLAYERS, 1F, 1F)
            }

        } else if (world.random.nextInt(50) == 0)
            world.playSound(null, entity,
                if (world.random.nextInt(2) == 0) NTechSoundsCore.geiger2.get() else NTechSoundsCore.geiger1.get(),
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
            val playerIrradiation = (capability.getIrradiation() * 10F).toInt() / 10F
            val playerResistance = (10_000F - EntityContaminationEffects.calculateRadiationMod(player) * 10_000F).toInt() / 100F
            val playerResistanceCoefficient = (HazmatValues.getPlayerResistance(player) * 10_000F).toInt() / 100F

            player.displayClientMessage(
                TextComponent("===== ☢ ")
                    .append(LangKeys.GEIGER_TITLE)
                    .append(TextComponent(" ☢ ====="))
                    .withStyle(ChatFormatting.GOLD), false
            )
            player.displayClientMessage(
                LangKeys.GEIGER_CHUNK_RADIATION
                    .append(TextComponent(" $chunkRadiation RAD/s").withStyle(getColorForRadValue(chunkRadiation)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
            player.displayClientMessage(
                LangKeys.GEIGER_TOTAL_ENVIRONMENTAL_RADIATION
                    .append(TextComponent(" $environmentRadiation RAD/s").withStyle(getColorForRadValue(environmentRadiation)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
            player.displayClientMessage(
                LangKeys.GEIGER_PLAYER_IRRADIATION
                    .append(TextComponent(" $playerIrradiation RAD").withStyle(getColorForPlayerRadValue(playerIrradiation)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
            player.displayClientMessage(
                LangKeys.GEIGER_PLAYER_RESISTANCE
                    .append(TextComponent(" $playerResistance% ($playerResistanceCoefficient)").withStyle(getColorForResistanceCoefficient(playerResistanceCoefficient)))
                    .withStyle(ChatFormatting.YELLOW), false
            )
        }

        private fun getColorForRadValue(rad: Float): ChatFormatting = when {
            rad < 0 -> ChatFormatting.DARK_GREEN
            rad == 0F -> ChatFormatting.GREEN
            rad < 1 -> ChatFormatting.YELLOW
            rad < 10 -> ChatFormatting.GOLD
            rad < 100 -> ChatFormatting.RED
            rad < 1000 -> ChatFormatting.DARK_RED
            else -> ChatFormatting.DARK_PURPLE
        }

        private fun getColorForPlayerRadValue(rad: Float): ChatFormatting = when {
            rad < 0 -> ChatFormatting.AQUA
            rad < 200 -> ChatFormatting.GREEN
            rad < 400 -> ChatFormatting.YELLOW
            rad < 600 -> ChatFormatting.GOLD
            rad < 800 -> ChatFormatting.RED
            rad < 1000 -> ChatFormatting.DARK_RED
            else -> ChatFormatting.DARK_PURPLE
        }

        private fun getColorForResistanceCoefficient(resistance: Float): ChatFormatting = if (resistance > 0) ChatFormatting.GREEN else ChatFormatting.WHITE
    }
}
