package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.capability.contamination.addEffectFromSource
import at.martinthedragon.nucleartech.capability.contamination.effect.RadiationEffect
import at.martinthedragon.nucleartech.capability.contamination.hasEffectFromSource
import at.martinthedragon.nucleartech.capability.contamination.modifyEffectFromSourceIf
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.item.NTechItems
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.*
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.entity.EntityTypeTest
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks
import kotlin.math.abs
import kotlin.math.max

class RBMKDebris : Entity {
    constructor(type: EntityType<*>, level: Level) : super(type, level)
    constructor(level: Level, pos: Vec3, debrisType: DebrisType) : super(EntityTypes.rbmkDebris.get(), level) {
        moveTo(pos)
        setDebrisType(debrisType)
        yRot = random.nextFloat() * 360F
    }

    override fun isPickable() = true

    override fun interact(player: Player, hand: InteractionHand): InteractionResult {
        val item = when (getDebrisType()) {
            DebrisType.BLANK, DebrisType.ELEMENT, DebrisType.ROD -> NTechItems.metalDebris.get()
            DebrisType.FUEL -> NTechItems.fuelDebris.get()
            DebrisType.GRAPHITE -> NTechItems.graphiteDebris.get()
            DebrisType.LID -> NTechItems.rbmkLid.get()
        }

        return if (player.addItem(item.defaultInstance)) {
            discard()
            InteractionResult.sidedSuccess(level.isClientSide)
        } else InteractionResult.FAIL
    }

    private val lifetime get() = getDebrisType().lifetime

    override fun tick() {
        super.tick()
        if (!NuclearConfig.rbmk.permanentScrap.get() && tickCount > lifetime + id % 50)
            discard()

        deltaMovement = deltaMovement.subtract(0.0, 0.04, 0.0)
        move(MoverType.SELF, deltaMovement)

        if (onGround) {
            deltaMovement = deltaMovement.multiply(0.85, -0.5, 0.85)
        } else {
            xRot += 10F
            if (xRot >= 360F) {
                xRot -= 360F
            }
        }

        if (!level.isClientSide) {
            val debrisType = getDebrisType()
            if (debrisType == DebrisType.LID && deltaMovement.y > 0) {
                val result = level.clip(ClipContext(position(), position().add(deltaMovement.scale(2.0)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this))
                if (result.type == HitResult.Type.BLOCK) {
                    val pos = result.blockPos
                    for (i in -1..1) for (j in -1..1) for (k in -1..1) {
                        val distance = abs(i) + abs(j) + abs(k)
                        if (distance <= 1 || random.nextInt(distance) == 0) {
                            val nextPos = pos.offset(i, j, k)
                            if (level.getBlockState(nextPos).block.defaultDestroyTime() >= 0F)
                                level.destroyBlock(nextPos, true)
                        }
                    }
                    discard()
                }
            }
            if (debrisType == DebrisType.FUEL || debrisType == DebrisType.GRAPHITE) {
                val entities = level.getEntities(EntityTypeTest.forClass(LivingEntity::class.java), boundingBox.inflate(2.5), EntitySelector.LIVING_ENTITY_STILL_ALIVE)
                val source = "rbmk_debris"
                val radiationLevel = if (debrisType == DebrisType.FUEL) 9F else 4F
                for (entity in entities) {
                    val capability = Capabilities.getContamination(entity)
                    if (capability != null) {
                        if (capability.hasEffectFromSource<RadiationEffect>(source))
                            capability.modifyEffectFromSourceIf<RadiationEffect>(source, { it.timeLeft < it.maxTime || it.startingRadiation < radiationLevel }) { it.timeLeft = it.maxTime; it.startingRadiation = max(radiationLevel, it.startingRadiation) }
                        else
                            capability.addEffectFromSource(RadiationEffect(radiationLevel, 60 * 20, false, source))
                    }
                }
            }
        }
    }

    override fun getDimensions(pose: Pose) = getDebrisType().dimensions

    fun setDebrisType(debrisType: DebrisType) {
        entityData.set(DATA_ID_DEBRIS_TYPE, debrisType.ordinal.toByte())
    }

    fun getDebrisType() = DebrisType.values()[entityData.get(DATA_ID_DEBRIS_TYPE).toInt()]

    override fun defineSynchedData() {
        entityData.define(DATA_ID_DEBRIS_TYPE, 0)
    }

    override fun onSyncedDataUpdated(dataAccessor: EntityDataAccessor<*>) {
        super.onSyncedDataUpdated(dataAccessor)
        if (dataAccessor == DATA_ID_DEBRIS_TYPE)
            refreshDimensions()
    }

    override fun readAdditionalSaveData(tag: CompoundTag) {
        entityData.set(DATA_ID_DEBRIS_TYPE, tag.getByte("DebrisType"))
    }

    override fun addAdditionalSaveData(tag: CompoundTag) {
        tag.putByte("DebrisType", entityData.get(DATA_ID_DEBRIS_TYPE))
    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

    enum class DebrisType(val dimensions: EntityDimensions, val lifetime: Int) {
        BLANK(BLANK_DIMENSIONS, 3 * 60 * 20),
        ELEMENT(ELEMENT_DIMENSIONS, 3 * 60 * 20),
        FUEL(FUEL_DIMENSIONS, 10 * 60 * 20),
        ROD(ROD_DIMENSIONS, 60 * 20),
        GRAPHITE(GRAPHITE_DIMENSIONS, 15 * 60 * 20),
        LID(LID_DIMENSIONS, 30 * 20)
    }

    companion object {
        private val DATA_ID_DEBRIS_TYPE = SynchedEntityData.defineId(RBMKDebris::class.java, EntityDataSerializers.BYTE)

        private val BLANK_DIMENSIONS = EntityDimensions.fixed(0.75F, 0.5F)
        private val ELEMENT_DIMENSIONS = EntityDimensions.fixed(1F, 1F)
        private val FUEL_DIMENSIONS = EntityDimensions.fixed(0.25F, 0.25F)
        private val ROD_DIMENSIONS = EntityDimensions.fixed(0.75F, 0.5F)
        private val GRAPHITE_DIMENSIONS = EntityDimensions.fixed(0.25F, 0.25F)
        private val LID_DIMENSIONS = EntityDimensions.fixed(1F, 0.5F)
    }
}
