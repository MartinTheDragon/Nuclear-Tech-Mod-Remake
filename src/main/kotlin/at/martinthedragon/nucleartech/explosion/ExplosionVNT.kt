package at.martinthedragon.nucleartech.explosion

import at.martinthedragon.nucleartech.math.*
import at.martinthedragon.nucleartech.networking.ExplosionVNTMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.item.PrimedTnt
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.item.enchantment.ProtectionEnchantment
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Explosion.BlockInteraction
import net.minecraft.world.level.ExplosionDamageCalculator
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseFireBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.minecraftforge.event.ForgeEventFactory
import net.minecraftforge.network.PacketDistributor
import kotlin.math.floor
import kotlin.math.sqrt

class ExplosionVNT(
    val level: Level,
    val pos: Vec3,
    val size: Float,
    val exploder: Entity? = null,
    val interaction: BlockInteraction = BlockInteraction.DESTROY,
    val damageCalculator: ExplosionDamageCalculator = if (exploder == null) ExplosionDamageCalculator() else EntityBasedExplosionDamageCalculator(exploder)
) {
    var blockAllocator: BlockAllocator? = null
    var blockProcessor: BlockProcessor? = null
    var entityProcessor: EntityProcessor? = null
    var playerProcessor: PlayerProcessor? = null
    val effects: MutableCollection<ExplosionFX> = mutableListOf()
    var syncer: ClientSyncer? = null

    private var hitPlayers = mapOf<Player, Vec3>()

    val compat = object : Explosion(level, exploder, pos.x, pos.y, pos.z, size) {
        override fun getHitPlayers() = this@ExplosionVNT.hitPlayers
    }

    fun explode() {
        if (!level.isClientSide)
            explodeServer()
    }

    private fun explodeServer() {
        if (ForgeEventFactory.onExplosionStart(level, compat)) return

        val processBlocks = blockAllocator != null && blockProcessor != null
        val processEntities = entityProcessor != null && playerProcessor != null

        val affectedBlocks = if (processBlocks) blockAllocator?.allocate(this, level, pos, size) else null
        val affectedPlayers = if (processEntities) entityProcessor?.process(this, level, pos, size) else null

        if (processBlocks) blockProcessor?.process(this, level, pos, affectedBlocks ?: emptySet())
        if (processEntities) playerProcessor?.process(this, level, pos, affectedPlayers ?: emptyMap())

        if (affectedBlocks != null) compat.toBlow.addAll(affectedBlocks)
        if (affectedPlayers != null) hitPlayers = affectedPlayers

        for (effect in effects) {
            effect.doEffect(this, level, pos, size)
        }

        syncer?.sync(this, level as ServerLevel, pos, size, affectedBlocks?.toList() ?: emptyList(), affectedPlayers ?: emptyMap())
    }

    fun interface BlockAllocator {
        fun allocate(explosion: ExplosionVNT, level: Level, pos: Vec3, size: Float): Set<BlockPos>

        class Default(private val resolution: Int = 16) : BlockAllocator {
            override fun allocate(explosion: ExplosionVNT, level: Level, pos: Vec3, size: Float): Set<BlockPos> {
                if (resolution < 2) return emptySet()

                level.gameEvent(explosion.exploder, GameEvent.EXPLODE, pos.toBlockPos())
                val allocated = mutableSetOf<BlockPos>()

                for (i in 0 until resolution) for (j in 0 until resolution) for (k in 0 until resolution) if (i == 0 || j == 0 || k == 0 || i == 15 || j == 15 || k == 15) {
                    var vx = i / (resolution - 1.0) * 2.0 - 1.0
                    var vy = j / (resolution - 1.0) * 2.0 - 1.0
                    var vz = k / (resolution - 1.0) * 2.0 - 1.0
                    val length = sqrt(vx * vx + vy * vy + vz * vz)
                    vx /= length
                    vy /= length
                    vz /= length
                    var force = size * (.7F + level.random.nextFloat() * .6F)

                    var px = pos.x
                    var py = pos.y
                    var pz = pos.z

                    while (force > 0F) {
                        val blockPos = BlockPos(px, py, pz)

                        if (!level.isInWorldBounds(blockPos)) break

                        val blockState = level.getBlockState(blockPos)
                        val fluidState = level.getFluidState(blockPos)

                        val optional = explosion.damageCalculator.getBlockExplosionResistance(explosion.compat, level, blockPos, blockState, fluidState)
                        if (optional.isPresent) {
                            force -= (optional.get() + .3F) * .3F

                            if (force > 0F && explosion.damageCalculator.shouldBlockExplode(explosion.compat, level, blockPos, blockState, force)) {
                                allocated += blockPos
                            }
                        }

                        px += vx * .3
                        py += vy * .3
                        pz += vz * .3

                        force -= .225F
                    }
                }

                return allocated.toSet()
            }
        }
    }

    fun interface BlockProcessor {
        fun process(explosion: ExplosionVNT, level: Level, pos: Vec3, affectedBlocks: Set<BlockPos>)

        class Default(private val dropChanceMutator: DropChanceMutator? = null, private val fortuneMutator: FortuneMutator? = null, private val blockMutator: BlockMutator? = null) : BlockProcessor {
            override fun process(explosion: ExplosionVNT, level: Level, pos: Vec3, affectedBlocks: Set<BlockPos>) {
                if (explosion.interaction == BlockInteraction.NONE) return

                val resources = ObjectArrayList<Pair<ItemStack, BlockPos>>()
                val shuffledPositions = affectedBlocks.shuffled(level.random)
                for (blockPos in shuffledPositions) {
                    val blockState = level.getBlockState(blockPos)
                    if (!blockState.isAir) {
                        val dropChanceMutation = dropChanceMutator?.mutateDropChance(explosion, blockPos, blockState, 1F / explosion.size)
                        if (dropChanceMutation != 0F && level is ServerLevel && blockState.canDropFromExplosion(level, blockPos, explosion.compat)) {
                            val blockEntity = if (blockState.hasBlockEntity()) level.getBlockEntity(blockPos) else null
                            val lootContext = LootContext.Builder(level)
                                .withRandom(level.random)
                                .withParameter(LootContextParams.ORIGIN, blockPos.toVec3Middle())
                                .withParameter(LootContextParams.TOOL, if (fortuneMutator == null) ItemStack.EMPTY else Items.STICK.defaultInstance.apply { enchant(Enchantments.BLOCK_FORTUNE, fortuneMutator.mutateFortune(explosion, blockPos, blockState)) })
                                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity)
                                .withOptionalParameter(LootContextParams.THIS_ENTITY, explosion.exploder)

                            if (explosion.interaction == BlockInteraction.DESTROY) {
                                val virtualSize = if (dropChanceMutation == null) explosion.size else 1F / dropChanceMutation
                                lootContext.withParameter(LootContextParams.EXPLOSION_RADIUS, virtualSize)
                            }

                            dropLoop@for (drop in blockState.getDrops(lootContext)) {
                                for (i in resources.indices) {
                                    val (existingDrop, previousPos) = resources[i]
                                    if (ItemEntity.areMergable(existingDrop, drop)) {
                                        val newDrop = ItemEntity.merge(existingDrop, drop, 16)
                                        resources[i] = newDrop to previousPos
                                        if (drop.isEmpty) continue@dropLoop
                                    }
                                }

                                resources += drop to blockPos
                            }
                        }

                        blockState.onBlockExploded(level, blockPos, explosion.compat)
                    }
                }

                for ((drop, blockPos) in resources) {
                    Block.popResource(level, blockPos, drop)
                }

                if (blockMutator != null) {
                    for (blockPos in shuffledPositions) if (level.getBlockState(blockPos).isAir) {
                        blockMutator.mutateAtPosition(explosion, blockPos)
                    }
                }
            }
        }
    }

    fun interface DropChanceMutator {
        fun mutateDropChance(explosion: ExplosionVNT, pos: BlockPos, blockState: BlockState, chance: Float): Float

        class Default(val chance: Float) : DropChanceMutator {
            override fun mutateDropChance(explosion: ExplosionVNT, pos: BlockPos, blockState: BlockState, chance: Float) = this.chance
        }
    }

    fun interface FortuneMutator {
        fun mutateFortune(explosion: ExplosionVNT, pos: BlockPos, blockState: BlockState): Int

        class Default(val fortune: Int) : FortuneMutator {
            override fun mutateFortune(explosion: ExplosionVNT, pos: BlockPos, blockState: BlockState) = fortune
        }
    }

    fun interface BlockMutator {
        fun mutateAtPosition(explosion: ExplosionVNT, pos: BlockPos)

        private object AlwaysFire : BlockMutator {
            override fun mutateAtPosition(explosion: ExplosionVNT, pos: BlockPos) {
                val firePos = pos.above()
                explosion.level.setBlockAndUpdate(firePos, BaseFireBlock.getState(explosion.level, firePos))
            }
        }

        private class RandomFire(private val randomBound: Int) : BlockMutator {
            override fun mutateAtPosition(explosion: ExplosionVNT, pos: BlockPos) {
                if (explosion.level.random.nextInt(randomBound) == 0) {
                    val firePos = pos.above()
                    explosion.level.setBlockAndUpdate(firePos, BaseFireBlock.getState(explosion.level, firePos))
                }
            }
        }

        companion object {
            fun fire(randomBound: Int = 3) = if (randomBound > 0) RandomFire(randomBound) else AlwaysFire
        }
    }

    fun interface EntityProcessor {
        fun process(explosion: ExplosionVNT, level: Level, pos: Vec3, size: Float): Map<Player, Vec3>

        class Default(private val rangeMutator: EntityRangeMutator? = null, private val damageHandler: DamageHandler? = null) : EntityProcessor {
            override fun process(explosion: ExplosionVNT, level: Level, pos: Vec3, size: Float): Map<Player, Vec3> {
                val affectedPlayers = mutableMapOf<Player, Vec3>()

                var range = size * 2.0

                if (rangeMutator != null)
                    range = rangeMutator.mutateRange(explosion, range)

                val xMin = floor(pos.x - range - 1.0)
                val yMin = floor(pos.y - range - 1.0)
                val zMin = floor(pos.z - range - 1.0)
                val xMax = floor(pos.x + range + 1.0)
                val yMax = floor(pos.y + range + 1.0)
                val zMax = floor(pos.z + range + 1.0)

                val entitiesInRange = level.getEntities(explosion.exploder, AABB(xMin, yMin, zMin, xMax, yMax, zMax))
                ForgeEventFactory.onExplosionDetonate(level, explosion.compat, entitiesInRange, range)

                for (entity in entitiesInRange) {
                    if (entity.ignoreExplosion()) continue

                    val distanceScaled = sqrt(entity.distanceToSqr(pos)) / range
                    if (distanceScaled <= 1.0) {
                        var vx = entity.x - pos.x
                        var vy = (if (entity is PrimedTnt) entity.y else entity.eyeY) - pos.y
                        var vz = entity.z - pos.z
                        val distanceToEyes = sqrt(vx * vx + vy * vy + vz * vz)
                        if (distanceToEyes != 0.0) {
                            vx /= distanceToEyes
                            vy /= distanceToEyes
                            vz /= distanceToEyes
                            val uncovered = Explosion.getSeenPercent(pos, entity)
                            val damageFactor = (1.0 - distanceScaled) * uncovered
                            entity.hurt(explosion.compat.damageSource, ((damageFactor * damageFactor + damageFactor) / 2.0 * 7.0 * range + 1.0).toFloat())
                            var knockBack = damageFactor
                            if (entity is LivingEntity) {
                                knockBack = ProtectionEnchantment.getExplosionKnockbackAfterDampener(entity, knockBack)
                            }
                            val knockBackVector = Vec3(vx * knockBack, vy * knockBack, vz * knockBack)
                            entity.deltaMovement = entity.deltaMovement.add(knockBackVector)
                            if (entity is Player) {
                                if (!entity.isSpectator && (!entity.isCreative || !entity.abilities.flying)) {
                                    affectedPlayers[entity] = knockBackVector
                                }
                            }

                            damageHandler?.handleAttack(explosion, entity, distanceScaled)
                        }
                    }
                }
                return affectedPlayers.toMap()
            }
        }
    }

    fun interface EntityRangeMutator {
        fun mutateRange(explosion: ExplosionVNT, range: Double): Double
    }

    fun interface DamageHandler {
        fun handleAttack(explosion: ExplosionVNT, entity: Entity, distanceScaled: Double)
    }

    fun interface PlayerProcessor {
        fun process(explosion: ExplosionVNT, level: Level, pos: Vec3, players: Map<Player, Vec3>)

        class Default : PlayerProcessor {
            override fun process(explosion: ExplosionVNT, level: Level, pos: Vec3, players: Map<Player, Vec3>) {}
        }
    }

    fun interface ExplosionFX {
        fun doEffect(explosion: ExplosionVNT, level: Level, pos: Vec3, size: Float)

        class Default : ExplosionFX {
            override fun doEffect(explosion: ExplosionVNT, level: Level, pos: Vec3, size: Float) {
                val (x, y, z) = pos
                if (level.isClientSide) {
                    level.playLocalSound(x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4F, (1F + (level.random.nextFloat() - level.random.nextFloat()) * .2F) * .7F, false)

                    if (size >= 2F)
                        level.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1.0, 0.0, 0.0)
                    else
                        level.addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0, 0.0, 0.0)
                }
            }
        }
    }

    fun interface ClientSyncer {
        fun sync(explosion: ExplosionVNT, level: ServerLevel, pos: Vec3, size: Float, affectedBlocks: List<BlockPos>, affectedPlayers: Map<Player, Vec3>)

        class Default : ClientSyncer {
            override fun sync(explosion: ExplosionVNT, level: ServerLevel, pos: Vec3, size: Float, affectedBlocks: List<BlockPos>, affectedPlayers: Map<Player, Vec3>) {
                for (player in level.players()) if (player.distanceToSqr(pos) < 4096.0)
                    NuclearPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with { player }, ExplosionVNTMessage(pos, size, if (explosion.interaction == BlockInteraction.NONE) emptyList() else affectedBlocks.toList(), affectedPlayers.getOrDefault(player, Vec3.ZERO)))
            }
        }
    }

    companion object {
        fun createStandard(level: Level, pos: Vec3, size: Float, exploder: Entity? = null) = ExplosionVNT(level, pos, size, exploder).apply {
            blockAllocator = BlockAllocator.Default()
            blockProcessor = BlockProcessor.Default()
            entityProcessor = EntityProcessor.Default()
            playerProcessor = PlayerProcessor.Default()
            effects += ExplosionFX.Default()
            syncer = ClientSyncer.Default()
        }
    }
}
