package at.martinthedragon.nucleartech.entities

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.IPacket
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import net.minecraft.world.gen.Heightmap
import net.minecraftforge.fml.network.NetworkHooks
import java.util.*
import kotlin.math.PI

class NukeCloudEntity(entityType: EntityType<NukeCloudEntity>, world: World) : Entity(entityType, world) {
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
            if (age >= maxAge) remove()
            entityData.set(AGE, age)
        }

        if (!level.isClientSide) return

        if (!isMuted) { // FIXME sound delayed when player rejoins
            level.playLocalSound(x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundCategory.AMBIENT, 10000F, .8F + random.nextFloat() * .2F, true)
            if (random.nextInt(5) == 0)
                level.playLocalSound(x, y, z, SoundEvents.GENERIC_EXPLODE, SoundCategory.AMBIENT, 10000F, .8F + random.nextFloat() * .2F, true)
        }

        cloudlets.removeIf { age > it.age + 50 }

        val cloudCount = age * 3
        val vector = Vector3d(age * 2.0, 0.0, 0.0)

        if (age < 200) {
            for (i in 0 until cloudCount) {
                val rotatedVector = vector.yRot(PI.toFloat() * 2 * random.nextFloat())
                cloudlets.add(Cloudlet(rotatedVector.x, level.getHeight(Heightmap.Type.MOTION_BLOCKING, (rotatedVector.x + x).toInt(), (rotatedVector.z + z).toInt()).toDouble(), rotatedVector.z, age))
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

    override fun readAdditionalSaveData(nbt: CompoundNBT) {
        age = nbt.getInt("Age")
        entityData.set(AGE, age)
        maxAge = nbt.getInt("MaxAge")
        scale = nbt.getFloat("Scale")
        isBalefire = nbt.getBoolean("IsBalefire")
        isMuted = nbt.getBoolean("IsMuted")
    }

    override fun addAdditionalSaveData(nbt: CompoundNBT) {
        nbt.putInt("Age", age)
        nbt.putInt("MaxAge", maxAge)
        nbt.putFloat("Scale", scale)
        nbt.putBoolean("IsBalefire", isBalefire)
        nbt.putBoolean("IsMuted", isMuted)
    }

    override fun getAddEntityPacket(): IPacket<*> = NetworkHooks.getEntitySpawningPacket(this)

    override fun shouldRenderAtSqrDistance(distance: Double) = true

    data class Cloudlet(val posX: Double, val posY: Double, val posZ: Double, val age: Int)

    companion object {
        private val AGE = EntityDataManager.defineId(NukeCloudEntity::class.java, DataSerializers.INT)
        private val MAX_AGE = EntityDataManager.defineId(NukeCloudEntity::class.java, DataSerializers.INT)
        private val SCALE = EntityDataManager.defineId(NukeCloudEntity::class.java, DataSerializers.FLOAT)
        private val IS_BALEFIRE = EntityDataManager.defineId(NukeCloudEntity::class.java, DataSerializers.BOOLEAN)
        private val IS_MUTED = EntityDataManager.defineId(NukeCloudEntity::class.java, DataSerializers.BOOLEAN)

        fun create(world: World, pos: Vector3d, scale: Float, timeout: Int = 20_000, isBalefire: Boolean = false, isMuted: Boolean = false): UUID? = if (world.isClientSide) null else {
            val cloudEntity = NukeCloudEntity(EntityTypes.nukeCloudEntity.get(), world).apply {
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
