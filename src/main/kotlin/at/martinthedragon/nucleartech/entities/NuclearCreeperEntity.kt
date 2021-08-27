package at.martinthedragon.nucleartech.entities

import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.AttributeModifierMap
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.monster.CreeperEntity
import net.minecraft.entity.monster.MonsterEntity
import net.minecraft.tileentity.SignTileEntity
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.World

class NuclearCreeperEntity(
    entityType: EntityType<out NuclearCreeperEntity>,
    world: World
) : CreeperEntity(entityType, world) {
    // TODO achievement

    init {
        maxSwell = 75
    }

    override fun explodeCreeper() {
        if (!level.isClientSide) {
            remove()

            // TODO actually create explosions lol
            if (isOnGround) {
                val pos = blockPosition()
                level.setBlockAndUpdate(pos, Blocks.OAK_SIGN.defaultBlockState())
                val signTileEntity = level.getBlockEntity(pos)
                if (signTileEntity is SignTileEntity) {
                    signTileEntity.setMessage(1, StringTextComponent("Coming soon!"))
                }
            }
        }
    }

    override fun canDropMobsSkull() = false

    companion object {
        fun createAttributes(): AttributeModifierMap = MonsterEntity.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, .3)
            .build()
    }
}
