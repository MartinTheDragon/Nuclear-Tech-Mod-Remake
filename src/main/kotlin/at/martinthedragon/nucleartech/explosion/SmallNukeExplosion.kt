package at.martinthedragon.nucleartech.explosion

import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.api.explosion.*
import at.martinthedragon.nucleartech.entity.NukeExplosion
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import at.martinthedragon.nucleartech.networking.HurtAnimationMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import at.martinthedragon.nucleartech.particle.ModParticles
import at.martinthedragon.nucleartech.particle.sendParticles
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.PacketDistributor.TargetPoint
import kotlin.math.abs

object SmallNukeExplosion : ExplosionFactory<SmallNukeExplosionParams> {
    override fun create(level: Level, pos: Vec3, strength: Float, params: SmallNukeExplosionParams): Explosion = SmallNukeExplosionInstance(level, pos, strength, params)

    private class SmallNukeExplosionInstance(val level: Level, val pos: Vec3, val strength: Float, val params: SmallNukeExplosionParams) : Explosion {
        override fun start(): Boolean {
            if (level.isClientSide) return false
            level as ServerLevel
            val (x, y, z) = pos
            level.playSound(null, x, y, z, SoundEvents.miniNukeExplosion.get(), SoundSource.BLOCKS, 15F, 1F)
            level.sendParticles(ModParticles.SHOCKWAVE.get(), true, x, y + .5, z, 1, 0.0, 0.0, 0.0, 0.0)
            level.sendParticles(ModParticles.MINI_NUKE_FLASH.get(), true, x, y + .5, z, 1, 0.0, 0.0, 0.0, 0.0)
            NuclearPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(TargetPoint.p(x, y, z, 250.0, level.dimension())), HurtAnimationMessage(15))

            if (params.shrapnel) ExplosionLarge.spawnShrapnel(level, pos, 25)
            params.actualExplosion?.createAndStart(level, pos, strength, EmptyParams)
            if (params.damageRadius > 0) NukeExplosion.dealDamage(level, pos, params.damageRadius.toDouble())

            if (params.radiationModifier > 0) {
                val middle = BlockPos(pos)
                for (xo in -2..2) for (zo in -2..2) if (abs(xo) + abs(zo) < 4)
                    ChunkRadiation.incrementRadiation(level, middle.offset(xo * 16, 0, zo * 16), 50F / (abs(xo) + abs(zo) + 1) * params.radiationModifier)
            }

            return true
        }
    }

    private val miniNukeAttributes: ExplosionVNT.() -> Unit = {
        blockAllocator = ExplosionVNT.BlockAllocator.Default(64)
        blockProcessor = ExplosionVNT.BlockProcessor.Default(dropChanceMutator = ExplosionVNT.DropChanceMutator.Default(0F), blockMutator = ExplosionVNT.BlockMutator.fire())
        entityProcessor = null
        syncer = null
    }

    val SAFE = SmallNukeExplosionParams(damageRadius = 45, radiationModifier = 0.66F)
    val LOW = SmallNukeExplosionParams(damageRadius = 45, radiationModifier = 0.66F, actualExplosion = { level, pos, _, _ -> Explosion { ExplosionVNT.createStandard(level, pos, 15F).apply(miniNukeAttributes).explode() ; true }})
    val MEDIUM = SmallNukeExplosionParams(damageRadius = 55, radiationModifier = 1F, actualExplosion = { level, pos, _, _ -> Explosion { ExplosionVNT.createStandard(level, pos, 20F).apply(miniNukeAttributes).explode() ; true }})
    val HIGH = SmallNukeExplosionParams(damageRadius = 0, radiationModifier = 1.33F, shrapnel = false, actualExplosion = { level, pos, _, _ -> NukeExplosion.create(level, pos, 350F, NuclearExplosionMk4Params(muted = true)) }) // TODO strength config
}
