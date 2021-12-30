package at.martinthedragon.nucleartech.capabilites.contamination.effects

import at.martinthedragon.nucleartech.capabilites.contamination.ContaminationHandler
import at.martinthedragon.nucleartech.capabilites.contamination.effects.ContaminationEffect.Companion.deserialize
import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

/**
 * For applying continuous effects on the [ContaminationHandler] of a [LivingEntity].
 *
 * The [tick] method gets called from [EntityContaminationEffects] until it [shouldBeRemoved], [save] and [load] are used in the [ContaminationHandler].
 *
 * Implementations need to save a `"Source"` tag of type `String` for use in [deserialize].
 */
sealed interface ContaminationEffect { // maybe make this some fancier serialization system to allow this to be non-sealed?
    /** Effects can optionally have a source to keep track of for avoiding duplicates. Otherwise, always return `null`. */
    val source: String?

    /** Gets called on every [net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent] */
    fun tick(entity: LivingEntity)
    /** Return `true` to remove this effect instance in the next tick */
    fun shouldBeRemoved(entity: LivingEntity): Boolean

    fun save(): CompoundTag
    fun load(nbt: CompoundTag)

    companion object {
        fun deserialize(tag: CompoundTag): ContaminationEffect {
            return when (tag.getString("Type")) {
                "RadiationEffect" -> RadiationEffect().apply { load(tag) }
                else -> throw IllegalArgumentException("Received unknown ContaminationEffect type")
            }
        }
    }
}

/** Continuously adds radiation to the entity of the value [startingRadiation] * 0.005 * [timeLeft] * [maxTime].
 * E.g. [startingRadiation] gets applied every second decayed by the amount time that has passed.
 */
class RadiationEffect : ContaminationEffect {
    var startingRadiation = 0F
    var maxTime = 0
    var timeLeft = 0
    var ignoreArmor = false

    override var source: String? = null
        private set

    constructor()

    constructor(startingRadiation: Float, maxTime: Int, ignoreArmor: Boolean, source: String? = null) {
        this.startingRadiation = startingRadiation
        this.maxTime = maxTime
        this.timeLeft = maxTime
        this.ignoreArmor = ignoreArmor
        this.source = source
    }

    override fun tick(entity: LivingEntity) {
        EntityContaminationEffects.contaminate(entity,
            EntityContaminationEffects.HazardType.Radiation,
            if (ignoreArmor) EntityContaminationEffects.ContaminationType.Bypass else EntityContaminationEffects.ContaminationType.Creative,
            startingRadiation * 0.05F * (timeLeft.toFloat() / maxTime)
        )
        timeLeft--
    }

    override fun shouldBeRemoved(entity: LivingEntity) = timeLeft <= 0

    override fun save() = CompoundTag().apply {
        putString("Type", "RadiationEffect")
        source?.let { putString("Source", it) }
        putFloat("StartingRadiation", startingRadiation)
        putInt("MaxTime", maxTime)
        putInt("TimeLeft", timeLeft)
        putBoolean("IgnoreArmor", ignoreArmor)
    }

    override fun load(nbt: CompoundTag) = with(nbt) {
        source = getString("Source").let { if (it.isNullOrBlank()) null else it }
        startingRadiation = getFloat("StartingRadiation")
        maxTime = getInt("MaxTime")
        timeLeft = getInt("TimeLeft")
        ignoreArmor = getBoolean("IgnoreArmor")
    }
}
