package at.martinthedragon.nucleartech.entities

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks
import java.util.*
import kotlin.math.PI

class MushroomCloud(entityType: EntityType<MushroomCloud>, world: Level) : Entity(entityType, world) {
    var age: Int = 0

    var maxAge: Int
        get() = entityData.get(MAX_AGE)
        set(value) { entityData.set(MAX_AGE, value) }

    var scale: Float
        get() = entityData.get(SCALE)
        set(value) { entityData.set(SCALE, value) }

    var isBalefire: Boolean
        get() = entityData.get(IS_BALEFIRE)
        set(value) { entityData.set(IS_BALEFIRE, value) }

    var isMuted: Boolean
        get() = entityData.get(IS_MUTED)
        set(value) { entityData.set(IS_MUTED, value) }

    val cloudlets = mutableListOf<Cloudlet>()

    init {
        noCulling = true
        noPhysics = true
    }

    // called by the explosion entity
    fun finish() {
        maxAge = age
    }

    override fun tick() {
        if (firstTick && level.isClientSide) age = entityData.get(AGE)

        super.tick()

        age++
        if (!level.isClientSide) {
            if (age >= maxAge) remove(RemovalReason.DISCARDED)
            entityData.set(AGE, age)
        }

        if (!level.isClientSide) return

        if (!isMuted) { // FIXME sound delayed when player rejoins
            level.playLocalSound(x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.AMBIENT, 10000F, .8F + random.nextFloat() * .2F, true)
            if (random.nextInt(5) == 0)
                level.playLocalSound(x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.AMBIENT, 10000F, .8F + random.nextFloat() * .2F, true)
        }

        cloudlets.removeIf { age > it.age + 50 }

        val cloudCount = age * 3
        val vector = Vec3(age * 2.0, 0.0, 0.0)

        if (age < 200) {
            for (i in 0 until cloudCount) {
                val rotatedVector = vector.yRot(PI.toFloat() * 2 * random.nextFloat())
                cloudlets.add(Cloudlet(rotatedVector.x, level.getHeight(Heightmap.Types.WORLD_SURFACE, (rotatedVector.x + x).toInt(), (rotatedVector.z + z).toInt()).toDouble(), rotatedVector.z, age))
            }
        }
    }

    override fun defineSynchedData() {
        entityData.define(AGE, age)
        entityData.define(MAX_AGE, 1000)
        entityData.define(SCALE, 1F)
        entityData.define(IS_BALEFIRE, false)
        entityData.define(IS_MUTED, false)
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        age = nbt.getInt("Age")
        entityData.set(AGE, age)
        maxAge = nbt.getInt("MaxAge")
        scale = nbt.getFloat("Scale")
        isBalefire = nbt.getBoolean("IsBalefire")
        isMuted = nbt.getBoolean("IsMuted")
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        nbt.putInt("Age", age)
        nbt.putInt("MaxAge", maxAge)
        nbt.putFloat("Scale", scale)
        nbt.putBoolean("IsBalefire", isBalefire)
        nbt.putBoolean("IsMuted", isMuted)
    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldRenderAtSqrDistance(distance: Double) = true

    data class Cloudlet(val posX: Double, val posY: Double, val posZ: Double, val age: Int)

    companion object {
        private val AGE = SynchedEntityData.defineId(MushroomCloud::class.java, EntityDataSerializers.INT)
        private val MAX_AGE = SynchedEntityData.defineId(MushroomCloud::class.java, EntityDataSerializers.INT)
        private val SCALE = SynchedEntityData.defineId(MushroomCloud::class.java, EntityDataSerializers.FLOAT)
        private val IS_BALEFIRE = SynchedEntityData.defineId(MushroomCloud::class.java, EntityDataSerializers.BOOLEAN)
        private val IS_MUTED = SynchedEntityData.defineId(MushroomCloud::class.java, EntityDataSerializers.BOOLEAN)

        fun create(world: Level, pos: Vec3, scale: Float, timeout: Int = 20_000, isBalefire: Boolean = false, isMuted: Boolean = false): UUID? = if (world.isClientSide) null else {
            val cloudEntity = MushroomCloud(EntityTypes.mushroomCloud.get(), world).apply {
                this.maxAge = timeout
                this.scale = scale
                this.isBalefire = isBalefire
                this.isMuted = isMuted
                moveTo(pos)
            }
            world.addFreshEntity(cloudEntity)
            cloudEntity.getUUID()
        }
    }
}
