package at.martinthedragon.nucleartech.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class MoltenMeteorBlock(properties: Properties) : Block(properties) {
    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        level.setBlockAndUpdate(pos, NTechBlocks.meteoriteCobblestone.get().defaultBlockState())
        level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, .5F, 2.6F + (random.nextFloat() - random.nextFloat()) * .8F)
    }

    override fun isRandomlyTicking(state: BlockState) = true

    override fun stepOn(level: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!entity.fireImmune() && entity is LivingEntity && !EnchantmentHelper.hasFrostWalker(entity)) {
            entity.hurt(DamageSource.HOT_FLOOR, 1F)
        }
    }

    override fun playerDestroy(level: Level, player: Player, pos: BlockPos, state: BlockState, blockEntity: BlockEntity?, itemInHand: ItemStack) {
        super.playerDestroy(level, player, pos, state, blockEntity, itemInHand)
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemInHand) == 0) {
            level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState())
        }
    }
}
