package at.martinthedragon.nucleartech.entity.missile

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.particle.ContrailParticleOptions
import at.martinthedragon.nucleartech.particle.sendParticles
import at.martinthedragon.nucleartech.world.ChunkLoader
import com.mojang.math.Vector3f
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MoverType
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraftforge.common.world.ForgeChunkManager
import net.minecraftforge.network.NetworkHooks
import kotlin.math.PI
import kotlin.math.atan2

abstract class AbstractMissile : Entity, ChunkLoader {
    var startPos: BlockPos protected set
    var targetPos: BlockPos protected set

    init {
        noCulling = true
        noPhysics = true
    }

    constructor(entityType: EntityType<out AbstractMissile>, level: Level) : super(entityType, level) {
        startPos = blockPosition()
        targetPos = blockPosition()
    }

    constructor(entityType: EntityType<out AbstractMissile>, level: Level, startPos: BlockPos, targetPos: BlockPos) : super(entityType, level) {
        this.startPos = startPos
        this.targetPos = targetPos.let { if (it == startPos) it.above(1) else it }
        moveTo(startPos, 0F, 0F)
        deltaMovement = deltaMovement.add(0.0, 2.0, 0.0)
        updateRotation()
        val vector = targetPos.toVec3Middle().subtract(startPos.toVec3Middle()).multiply(1.0, 0.0, 1.0)
        accelerationXZ = 1 / vector.length()
        decelerationY = accelerationXZ * 2
        velocity = 1
        calculateForcedChunks(ChunkPos(startPos), ChunkPos(targetPos))
        if (!level.isClientSide) (level as ServerLevel).sendParticles(ContrailParticleOptions(Vector3f.ZERO, .75F), true, x, y - .5, z, 40, .9, .1, .9, 5.0)
    }

    open val renderTexture: ResourceLocation get() = missileTexture(type.registryName!!.path)
    open val renderModel: ResourceLocation = MODEL_MISSILE_V2
    open val renderScale: Float = 1F
    abstract fun onImpact()

    var health = 50F

    override fun defineSynchedData() {
        entityData.define(DATA_ID_HEALTH, health)
    }

    override fun canBeCollidedWith() = true

    override fun hurt(source: DamageSource, damage: Float): Boolean {
        if (isInvulnerableTo(source)) return false
        if (!isRemoved && !level.isClientSide) {
            health -= damage
            if (health <= 0F) {
                kill()
                killMissile()
            }
        }
        return true
    }

    protected fun killMissile() {
//        unRide()
        ExplosionLarge.createAndStart(level, position(), 5F, ExplosionLargeParams(fire = false, cloud = true, rubble = false, shrapnel = true))
        if (!level.isClientSide && currentSegmentIndex != -1) for (chunk in forcedChunks.toList()) unForceChunk(chunk)
    }

    private var velocity = 0
    private var decelerationY = 0.0
    private var accelerationXZ = 0.0

    override fun tick() {
        super.tick()

        if (velocity < 1) velocity = 1
        if (tickCount > 40) velocity = 3
        else if (tickCount > 20) velocity = 2

        for (i in 0 until velocity) {
            move(MoverType.SELF, deltaMovement)

            updateRotation()

            deltaMovement = deltaMovement.subtract(0.0, decelerationY, 0.0)

            val vector = targetPos.toVec3Middle().subtract(startPos.toVec3Middle()).multiply(1.0, 0.0, 1.0).normalize().multiply(accelerationXZ, 1.0, accelerationXZ)
            if (deltaMovement.y > 0) deltaMovement = deltaMovement.add(vector)
            if (deltaMovement.y < 0) deltaMovement = deltaMovement.subtract(vector)

            if (!level.isClientSide) (level as ServerLevel).sendParticles(ContrailParticleOptions(Vector3f.ZERO, .4F), true, x, y, z, 6, 0.0, 0.0, 0.0, 0.0)

            // TODO actual clipping
            val block = level.getBlockState(blockPosition())
            if (!block.isAir && block.fluidState.isEmpty) {
                if (!level.isClientSide) {
                    onImpact()
                    unForceChunks()
//                    unRide()
                }
                discard()
                return
            }
        }

        if (!level.isClientSide) manageForcedChunks()
    }

    // TODO smooth rotations
    private fun updateRotation() {
        val movement = deltaMovement
        xRot = (atan2(movement.y, movement.horizontalDistance()) * 180 / PI).toFloat()
        while (xRot - xRotO < -180F) xRotO -= 360F
        while (xRot - xRotO >= 180F) xRotO += 360F
        yRot = (atan2(movement.x, movement.z) * 180 / PI).toFloat()
        while (yRot - yRotO < -180F) yRotO -= 360F
        while (yRot - yRotO >= 180F) yRotO += 360F
    }

    private val chunksToForce = mutableSetOf<ChunkPos>()
    override val forcedChunks = mutableSetOf<ChunkPos>()
    override val tickingForcedChunks get() = forcedChunks
    private var segments = listOf<List<ChunkPos>>() // segments in size of 16 chunks
    var currentSegmentIndex = -1

    private fun manageForcedChunks() {
        if (segments.isEmpty()) return

        val currentChunkPos = ChunkPos(blockPosition())
        if (currentSegmentIndex == 0 && currentChunkPos == chunksToForce.first() && forcedChunks.isEmpty()) { // just spawned here
            for (chunk in segments[currentSegmentIndex]) forceChunk(chunk)
            return
        }

        // we are in the second half of the current segment -> unload previous, load next
        if (currentChunkPos in segments[currentSegmentIndex].takeLast(2)) {
            if (currentSegmentIndex < segments.lastIndex) for (chunk in segments[currentSegmentIndex + 1]) forceChunk(chunk)
            if (currentSegmentIndex > 0) for (chunk in segments[currentSegmentIndex - 1]) unForceChunk(chunk)
            if (currentSegmentIndex < segments.lastIndex) currentSegmentIndex++
        }
    }

    private fun forceChunk(chunkPos: ChunkPos) = if (chunkPos !in forcedChunks) (ForgeChunkManager.forceChunk(level as ServerLevel, NuclearTech.MODID, this, chunkPos.x, chunkPos.z, true, false) && ForgeChunkManager.forceChunk(level as ServerLevel, NuclearTech.MODID, this, chunkPos.x, chunkPos.z, true, true)).also { forcedChunks += chunkPos } else false
    private fun unForceChunk(chunkPos: ChunkPos) = if (chunkPos in forcedChunks) (ForgeChunkManager.forceChunk(level as ServerLevel, NuclearTech.MODID, this, chunkPos.x, chunkPos.z, false, false) && ForgeChunkManager.forceChunk(level as ServerLevel, NuclearTech.MODID, this, chunkPos.x, chunkPos.z, false, true)).also { forcedChunks -= chunkPos } else false

    override fun forceChunks(chunks: Set<ChunkPos>) {
        for (chunk in chunks) forceChunk(chunk)
    }

    override fun unForceChunks() {
        for (chunk in forcedChunks.toSet()) unForceChunk(chunk)
    }

    override fun forceTickingChunks(chunks: Set<ChunkPos>) {
        forceChunks(chunks)
    }

    override fun unForceTickingChunks() {
        unForceChunks()
    }

    // http://eugen.dedu.free.fr/projects/bresenham/
    private fun calculateForcedChunks(from: ChunkPos, to: ChunkPos) {
        chunksToForce.clear()

        var x = from.x
        var z = from.z
        var error: Int
        var previousError: Int
        val xStep: Int
        val zStep: Int
        var dx = to.x - from.x
        var dz = to.z - from.z

        chunksToForce += ChunkPos(from.x, from.z)

        if (dx < 0) {
            xStep = -1
            dx = -dx
        } else {
            xStep = 1
        }

        if (dz < 0) {
            zStep = -1
            dz = -dz
        } else {
            zStep = 1
        }

        val ddx = dx * 2
        val ddz = dz * 2

        if (ddx >= ddz) {
            error = dx
            previousError = dx

            for (i in 0 until dx) {
                x += xStep
                error += ddz

                if (error > ddx) {
                    z += zStep
                    error -= ddx

                    if (error + previousError < ddx) chunksToForce += ChunkPos(x, z - zStep)
                    else if (error + previousError > ddx) chunksToForce += ChunkPos(x - xStep, z)
                    else {
                        chunksToForce += ChunkPos(x, z - zStep)
                        chunksToForce += ChunkPos(x - xStep, z)
                    }
                }
                chunksToForce += ChunkPos(x, z)
                previousError = error
            }
        } else {
            error = dz
            previousError = dz

            for (i in 0 until dz) {
                z += zStep
                error += ddx

                if (error > ddz) {
                    x += xStep
                    error -= ddz

                    if (error + previousError < ddz) chunksToForce += ChunkPos(x - xStep, z)
                    else if (error + previousError > ddz) chunksToForce += ChunkPos(x, z - zStep)
                    else {
                        chunksToForce += ChunkPos(x - xStep, z)
                        chunksToForce += ChunkPos(z, z - zStep)
                    }
                }
                chunksToForce += ChunkPos(x, z)
                previousError = error
            }
        }
        assert(ChunkPos(x, z) == to) { "Something went wrong calculating the forced chunks" }

        segments = chunksToForce.chunked(4)
        currentSegmentIndex = 0
    }

    // TODO rideable missiles
//    override fun playerTouch(player: Player) {
//        player.startRiding(this)
//    }
//
//    override fun positionRider(rider: Entity) {
//        if (!hasPassenger(rider)) return
//        val vector = Vec3(2.0, 0.0, 0.0).yRot((yRot * PI / 180F - PI / 2F).toFloat())
//        rider.setPos(x + vector.x, y + passengersRidingOffset + rider.myRidingOffset, z + vector.z)
//    }
//
//    override fun getPassengersRidingOffset() = 1.5

    final override fun moveTo(pos: BlockPos, yRot: Float, xRot: Float) = super.moveTo(pos, yRot, xRot)
    final override fun blockPosition(): BlockPos = super.blockPosition()

    override fun teleportTo(x: Double, y: Double, z: Double) {
        super.teleportTo(x, y, z)
        if (!level.isClientSide) {
            startPos = BlockPos(x, y, z)

            unForceChunks()
            calculateForcedChunks(ChunkPos(startPos), ChunkPos(targetPos))
            manageForcedChunks()
        }
    }

    override fun readAdditionalSaveData(tag: CompoundTag) = with(tag) {
        startPos = BlockPos.of(getLong("StartPos"))
        targetPos = BlockPos.of(getLong("TargetPos"))
        velocity = getInt("MissileVelocity")
        decelerationY = getDouble("MissileDeceleration")
        accelerationXZ = getDouble("MissileAcceleration")
        for (chunk in getLongArray("ForcedChunks")) forcedChunks.add(ChunkPos(chunk))
        calculateForcedChunks(ChunkPos(startPos), ChunkPos(targetPos))
        currentSegmentIndex = getInt("CurrentForcedChunksSegment")
    }

    override fun addAdditionalSaveData(tag: CompoundTag) = with(tag) {
        putLong("StartPos", startPos.asLong())
        putLong("TargetPos", targetPos.asLong())
        putInt("MissileVelocity", velocity)
        putDouble("MissileDeceleration", decelerationY)
        putDouble("MissileAcceleration", accelerationXZ)
        putLongArray("ForcedChunks", forcedChunks.map(ChunkPos::toLong).toLongArray())
        putInt("CurrentForcedChunksSegment", currentSegmentIndex)
    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldRenderAtSqrDistance(distance: Double) = distance < 1024 * 1024

    companion object {
        private val DATA_ID_HEALTH = SynchedEntityData.defineId(AbstractMissile::class.java, EntityDataSerializers.FLOAT)

        fun missileTexture(name: String) = ntm("textures/entity/missiles/$name.png")
        fun missileModel(name: String) = ntm("models/other/missiles/$name.obj")

        val MODEL_MISSILE_V2 = missileModel("missile_v2")
        val MODEL_MISSILE_STRONG = missileModel("missile_strong")
        val MODEL_MISSILE_HUGE = missileModel("missile_huge")
        val MODEL_MISSILE_NUCLEAR = missileModel("missile_nuclear")
    }
}
