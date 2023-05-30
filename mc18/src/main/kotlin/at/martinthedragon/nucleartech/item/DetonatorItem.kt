package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.api.explosion.IgnitableExplosive
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.extensions.darkRed
import at.martinthedragon.nucleartech.extensions.gray
import at.martinthedragon.nucleartech.extensions.green
import at.martinthedragon.nucleartech.extensions.red
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class DetonatorItem(properties: Properties) : Item(properties) {
    override fun useOn(context: UseOnContext): InteractionResult {
        val player = context.player
        val world = context.level

        if (player != null && player.isSecondaryUseActive) {
            val pos = context.clickedPos
            val block = world.getBlockState(pos)
            if (block.block !is IgnitableExplosive) return InteractionResult.FAIL
            if (!world.isClientSide) {
                val detonatorTag = context.itemInHand.orCreateTag
                detonatorTag.putInt("ExplosivePosX", pos.x)
                detonatorTag.putInt("ExplosivePosY", pos.y)
                detonatorTag.putInt("ExplosivePosZ", pos.z)
            }
            player.playSound(NTechSoundsCore.randomBoop.get(), 2F, 1F)
            if (world.isClientSide) player.displayClientMessage(LangKeys.DEVICE_POSITION_SET.green(), true)
            return InteractionResult.sidedSuccess(world.isClientSide)
        }

        return InteractionResult.PASS
    }

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        player.playSound(NTechSoundsCore.randomBleep.get(), 1F, 1F)
        val itemStack = player.getItemInHand(hand)
        if (!world.isClientSide) processUse(itemStack, world, player)
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide)
    }

    private fun processUse(stack: ItemStack, world: Level, player: Player) {
        if (!isPositionSet(stack)) {
            player.displayClientMessage(LangKeys.DEVICE_POSITION_NOT_SET.red(), true)
            return
        }
        val detonatorTag = stack.orCreateTag
        val posX = detonatorTag.getInt("ExplosivePosX")
        val posY = detonatorTag.getInt("ExplosivePosY")
        val posZ = detonatorTag.getInt("ExplosivePosZ")
        val pos = BlockPos(posX, posY, posZ)
        if (!NuclearConfig.explosions.detonateUnloadedBombs.get() && !world.isLoaded(pos)) {
            player.displayClientMessage(LangKeys.DEVICE_POSITION_NOT_LOADED.red(), true)
            return
        }
        val block = world.getBlockState(pos).block
        if (block !is IgnitableExplosive) {
            player.displayClientMessage(LangKeys.DETONATOR_NO_EXPLOSIVE.red(), true)
            return
        }
        val messageToSend = when (block.detonate(world, pos)) {
            IgnitableExplosive.DetonationResult.Success -> LangKeys.DETONATOR_SUCCESS.green()
            IgnitableExplosive.DetonationResult.InvalidPosition -> LangKeys.DETONATOR_NO_EXPLOSIVE.red()
            IgnitableExplosive.DetonationResult.InvalidBlockEntity -> LangKeys.DETONATOR_INVALID_BLOCK_ENTITY.red()
            IgnitableExplosive.DetonationResult.Incomplete -> LangKeys.DETONATOR_MISSING_COMPONENTS.red()
            IgnitableExplosive.DetonationResult.Prohibited -> LangKeys.DETONATOR_PROHIBITED.red()
            IgnitableExplosive.DetonationResult.Unknown -> LangKeys.DETONATOR_UNKNOWN_ERROR.red()
        }
        player.displayClientMessage(messageToSend, true)
    }

    private fun isPositionSet(stack: ItemStack): Boolean = stack.hasTag() && stack.orCreateTag.contains("ExplosivePosX")

    override fun appendHoverText(stack: ItemStack, worldIn: Level?, tooltip: MutableList<Component>, flagIn: TooltipFlag) {
        autoTooltip(stack, tooltip)
        tooltip += if (isPositionSet(stack)) {
            val tag = stack.orCreateTag
            val x = tag.getInt("ExplosivePosX")
            val y = tag.getInt("ExplosivePosY")
            val z = tag.getInt("ExplosivePosZ")
            TextComponent("$x, $y, $z").gray()
        } else LangKeys.DEVICE_POSITION_NOT_SET.darkRed()
    }
}
