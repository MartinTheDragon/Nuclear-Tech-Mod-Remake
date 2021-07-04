package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.NuclearTags
import at.martinthedragon.nucleartech.SoundEvents
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class OilDetector(properties: Properties) : Item(properties) {
    override fun appendHoverText(stack: ItemStack, world: World?, tooltip: MutableList<ITextComponent>, flag: ITooltipFlag) =
        autoTooltip(stack, tooltip)

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        fun checkColumns(world: World, playerPos: BlockPos, offsetX: Int, offsetZ: Int): Boolean {
            val oilOreTag = NuclearTags.Blocks.ORES_OIL
            for (i in 5..(playerPos.y + 15)) {
                val found = when {
                    offsetX == 0 && offsetZ == 0 -> world.getBlockState(BlockPos(playerPos.x, i, playerPos.z)).`is`(oilOreTag)
                    offsetX == 0 -> world.getBlockState(BlockPos(playerPos.x, i, playerPos.z + offsetZ)).`is`(oilOreTag) ||
                            world.getBlockState(BlockPos(playerPos.x, i, playerPos.z - offsetZ)).`is`(oilOreTag)
                    offsetZ == 0 -> world.getBlockState(BlockPos(playerPos.x + offsetX, i, playerPos.z)).`is`(oilOreTag) ||
                            world.getBlockState(BlockPos(playerPos.x - offsetX, i, playerPos.z)).`is`(oilOreTag)
                    else -> world.getBlockState(BlockPos(playerPos.x + offsetX, i, playerPos.z + offsetZ)).`is`(oilOreTag) ||
                            world.getBlockState(BlockPos(playerPos.x - offsetX, i, playerPos.z + offsetZ)).`is`(oilOreTag) ||
                            world.getBlockState(BlockPos(playerPos.x + offsetX, i, playerPos.z - offsetZ)).`is`(oilOreTag) ||
                            world.getBlockState(BlockPos(playerPos.x - offsetX, i, playerPos.z - offsetZ)).`is`(oilOreTag)
                }

                if (found) return true
            }
            return false
        }

        if (world.isClientSide) {
            val playerPosition = player.blockPosition()
            if (checkColumns(world, playerPosition, 0, 0)) {
                player.displayClientMessage(TranslationTextComponent("$descriptionId.below").withStyle(TextFormatting.DARK_GREEN), true)
            } else if (
                checkColumns(world, playerPosition, 5, 0) ||
                checkColumns(world, playerPosition, 0, 5) ||
                checkColumns(world, playerPosition, 10, 0) ||
                checkColumns(world, playerPosition, 0, 10) ||
                checkColumns(world, playerPosition, 5, 5)
            ) {
                player.displayClientMessage(TranslationTextComponent("$descriptionId.near").withStyle(TextFormatting.GOLD), true)
            } else {
                player.displayClientMessage(TranslationTextComponent("$descriptionId.no_oil").withStyle(TextFormatting.RED), true)
            }
        }

        player.playSound(SoundEvents.itemGenericBleep, 1F, 1F)

        val itemStack = player.getItemInHand(hand)
        return ActionResult.sidedSuccess(itemStack, world.isClientSide)
    }
}
