package at.martinthedragon.nucleartech.entity

import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.monster.Creeper
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.SignBlockEntity

class NuclearCreeper(
    entityType: EntityType<out NuclearCreeper>,
    level: Level
) : Creeper(entityType, level) {
    // TODO achievement

    init {
        maxSwell = 75
    }

    override fun explodeCreeper() {
        if (!level.isClientSide) {
            remove(RemovalReason.DISCARDED)

            // TODO actually create explosions lol
            if (isOnGround) {
                val pos = blockPosition()
                level.setBlockAndUpdate(pos, Blocks.OAK_SIGN.defaultBlockState())
                val signTileEntity = level.getBlockEntity(pos)
                if (signTileEntity is SignBlockEntity) {
                    signTileEntity.setMessage(1, TextComponent("Coming soon!"))
                }
            }
        }
    }

    override fun canDropMobsSkull() = false

    companion object {
        fun createAttributes(): AttributeSupplier = Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, .3)
            .build()
    }
}
