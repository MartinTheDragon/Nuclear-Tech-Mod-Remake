package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.explosions.IgnitableExplosive
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class Detonator(properties: Properties) : Item(properties) {
    override fun useOn(context: ItemUseContext): ActionResultType {
        val player = context.player
        val world = context.level

        if (player != null && player.isSecondaryUseActive) {
            val pos = context.clickedPos
            val block = world.getBlockState(pos)
            if (block.block !is IgnitableExplosive) return ActionResultType.FAIL
            if (!world.isClientSide) {
                val detonatorTag = context.itemInHand.orCreateTag
                detonatorTag.putInt("ExplosivePosX", pos.x)
                detonatorTag.putInt("ExplosivePosY", pos.y)
                detonatorTag.putInt("ExplosivePosZ", pos.z)
            }
            player.playSound(SoundEvents.randomBoop.get(), 2F, 1F)
            if (world.isClientSide) player.displayClientMessage(TranslationTextComponent("$descriptionId.position_set").withStyle(TextFormatting.GREEN), true)
            return ActionResultType.sidedSuccess(world.isClientSide)
        }

        return ActionResultType.PASS
    }

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        player.playSound(SoundEvents.randomBleep.get(), 1F, 1F)
        val itemStack = player.getItemInHand(hand)
        if (!world.isClientSide) processUse(itemStack, world, player)
        return ActionResult.sidedSuccess(itemStack, world.isClientSide)
    }

    private fun processUse(stack: ItemStack, world: World, player: PlayerEntity) {
        if (!isPositionSet(stack)) {
            player.displayClientMessage(TranslationTextComponent("$descriptionId.error_position_not_set").withStyle(TextFormatting.RED), true)
            return
        }
        val detonatorTag = stack.orCreateTag
        val posX = detonatorTag.getInt("ExplosivePosX")
        val posY = detonatorTag.getInt("ExplosivePosY")
        val posZ = detonatorTag.getInt("ExplosivePosZ")
        val pos = BlockPos(posX, posY, posZ)
        if (!world.isLoaded(pos)) { // TODO make configurable
            player.displayClientMessage(TranslationTextComponent("$descriptionId.error_position_not_loaded").withStyle(TextFormatting.RED), true)
            return
        }
        val block = world.getBlockState(pos).block
        if (block !is IgnitableExplosive) {
            player.displayClientMessage(TranslationTextComponent("$descriptionId.error_not_explosive").withStyle(TextFormatting.RED), true)
            return
        }
        val messageToSend = when (block.detonate(world, pos)) {
            IgnitableExplosive.DetonationResult.Success -> TranslationTextComponent("$descriptionId.detonation_successful").withStyle(TextFormatting.GREEN)
            IgnitableExplosive.DetonationResult.InvalidPosition -> TranslationTextComponent("$descriptionId.error_not_explosive").withStyle(TextFormatting.RED)
            IgnitableExplosive.DetonationResult.InvalidTileEntity -> TranslationTextComponent("$descriptionId.error_invalid_tile_entity").withStyle(TextFormatting.RED)
            IgnitableExplosive.DetonationResult.Incomplete -> TranslationTextComponent("$descriptionId.error_missing_components").withStyle(TextFormatting.RED)
            IgnitableExplosive.DetonationResult.Prohibited -> TranslationTextComponent("$descriptionId.error_detonation_prohibited").withStyle(TextFormatting.RED)
            IgnitableExplosive.DetonationResult.Unknown -> TranslationTextComponent("$descriptionId.error_unknown").withStyle(TextFormatting.RED)
        }
        player.displayClientMessage(messageToSend, true)
    }

    private fun isPositionSet(stack: ItemStack): Boolean = stack.hasTag() && stack.orCreateTag.contains("ExplosivePosX")

    override fun appendHoverText(stack: ItemStack, worldIn: World?, tooltip: MutableList<ITextComponent>, flagIn: ITooltipFlag) {
        autoTooltip(stack, tooltip)
        tooltip += if (isPositionSet(stack)) {
            val tag = stack.orCreateTag
            val x = tag.getInt("ExplosivePosX")
            val y = tag.getInt("ExplosivePosY")
            val z = tag.getInt("ExplosivePosZ")
            StringTextComponent("$x, $y, $z").withStyle(TextFormatting.GRAY)
        } else TranslationTextComponent("$descriptionId.tooltip_no_position_set").withStyle(TextFormatting.DARK_RED)
    }
}
