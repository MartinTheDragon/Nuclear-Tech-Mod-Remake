package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.Radiation
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.capabilites.contamination.CapabilityContaminationHandler
import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.SoundCategory
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World
import kotlin.random.Random

class GeigerCounterItem(properties: Properties) : Item(properties) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        player.playSound(SoundEvents.randomBoop.get(), 1F, 1F)
        if (!world.isClientSide) {
            printGeigerData(player)
        }
        return ActionResult.sidedSuccess(player.getItemInHand(hand), world.isClientSide)
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, isSelected: Boolean) {
        if (entity !is LivingEntity || world.isClientSide) return

        // TODO check for FSB armor

        val capability = entity.getCapability(CapabilityContaminationHandler.contaminationHandlerCapability)
            .takeIf { it.isPresent }
            ?.orElseThrow(::Error)
            ?: return

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
            if ((likelihood == 1 || random.nextInt(likelihood) == 0) && intensity > 0) {
                val sound = when (Random.nextInt(intensity.countTrailingZeroBits(), intensity.countOneBits() + intensity.countTrailingZeroBits()) + 1) {
                    1 -> SoundEvents.geiger1
                    2 -> SoundEvents.geiger2
                    3 -> SoundEvents.geiger3
                    4 -> SoundEvents.geiger4
                    5 -> SoundEvents.geiger5
                    6 -> SoundEvents.geiger6
                    else -> SoundEvents.geiger1
                }

                world.playSound(null, entity, sound.get(), SoundCategory.PLAYERS, 1F, 1F)
            }

        } else if (random.nextInt(50) == 0)
            world.playSound(null, entity,
                if (random.nextInt(2) == 0) SoundEvents.geiger2.get() else SoundEvents.geiger1.get(),
                SoundCategory.PLAYERS, 1F, 1F
            )
    }

    companion object {
        fun printGeigerData(player: PlayerEntity) {
            val world = player.level
            val capability = CapabilityContaminationHandler.getCapability(player) ?: return

            // for that hbm approved decimal rounding
            val chunkRadiation = (ChunkRadiation.getRadiation(world, player.blockPosition()) * 10F).toInt() / 10F
            val environmentRadiation = (capability.getRadPerSecond() * 10F).toInt() / 10F
            val playerIrradiation = (Radiation.getEntityIrradiation(player) * 10F).toInt() / 10F
            val playerResistance = (10_000.0 - EntityContaminationEffects.calculateRadiationMod(player) * 10_000.0).toInt() / 100.0
            // TODO coefficient

            player.displayClientMessage(
                StringTextComponent("===== ☢ ")
                    .append(TranslationTextComponent("geiger.title"))
                    .append(StringTextComponent(" ☢ ====="))
                    .withStyle(TextFormatting.GOLD), false
            )
            player.displayClientMessage(
                TranslationTextComponent("geiger.chunkRadiation")
                    .append(StringTextComponent(" $chunkRadiation RAD/s").withStyle(getColorForRadValue(chunkRadiation)))
                    .withStyle(TextFormatting.YELLOW), false
            )
            player.displayClientMessage(
                TranslationTextComponent("geiger.totalEnvironmentRadiation")
                    .append(StringTextComponent(" $environmentRadiation RAD/s").withStyle(getColorForRadValue(environmentRadiation)))
                    .withStyle(TextFormatting.YELLOW), false
            )
            player.displayClientMessage(
                TranslationTextComponent("geiger.playerIrradiation")
                    .append(StringTextComponent(" $playerIrradiation RAD").withStyle(getColorForPlayerRadValue(playerIrradiation)))
                    .withStyle(TextFormatting.YELLOW), false
            )
            player.displayClientMessage(
                TranslationTextComponent("geiger.playerResistance")
                    .append(StringTextComponent(" $playerResistance%").withStyle(TextFormatting.WHITE))
                    .withStyle(TextFormatting.YELLOW), false
            )
        }

        private fun getColorForRadValue(rad: Float): TextFormatting = when {
            rad == 0F -> TextFormatting.GREEN
            rad < 1F -> TextFormatting.YELLOW
            rad < 10F -> TextFormatting.GOLD
            rad < 100F -> TextFormatting.RED
            rad < 1000F -> TextFormatting.DARK_RED
            else -> TextFormatting.DARK_GRAY
        }

        private fun getColorForPlayerRadValue(rad: Float): TextFormatting = when {
            rad < 200 -> TextFormatting.GREEN
            rad < 400 -> TextFormatting.YELLOW
            rad < 600 -> TextFormatting.GOLD
            rad < 800 -> TextFormatting.RED
            rad < 1000 -> TextFormatting.DARK_RED
            else -> TextFormatting.DARK_GRAY
        }
    }
}
