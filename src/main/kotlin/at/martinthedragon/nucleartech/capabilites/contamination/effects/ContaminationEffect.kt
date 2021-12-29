package at.martinthedragon.nucleartech.capabilites.contamination.effects

import at.martinthedragon.nucleartech.hazards.EntityContaminationEffects
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity

sealed interface ContaminationEffect { // maybe make this some fancier serialization system to allow this to be non-sealed?
    fun tick(entity: LivingEntity)
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

class RadiationEffect : ContaminationEffect {
    private var startingRadiation = 0F
    private var maxTime = 0
    private var timeLeft = 0
    private var ignoreArmor = false

    constructor()

    constructor(startingRadiation: Float, maxTime: Int, ignoreArmor: Boolean) {
        this.startingRadiation = startingRadiation
        this.maxTime = maxTime
        this.timeLeft = maxTime
        this.ignoreArmor = ignoreArmor
    }

    override fun tick(entity: LivingEntity) {
        EntityContaminationEffects.contaminate(entity,
            EntityContaminationEffects.HazardType.Radiation,
            if (ignoreArmor) EntityContaminationEffects.ContaminationType.Bypass else EntityContaminationEffects.ContaminationType.Creative,
            startingRadiation * (timeLeft.toFloat() / maxTime)
        )
        timeLeft--
    }

    override fun shouldBeRemoved(entity: LivingEntity) = timeLeft <= 0

    override fun save() = CompoundTag().apply {
        putString("Type", "RadiationEffect")
        putFloat("StartingRadiation", startingRadiation)
        putInt("MaxTime", maxTime)
        putInt("TimeLeft", timeLeft)
        putBoolean("IgnoreArmor", ignoreArmor)
    }

    override fun load(nbt: CompoundTag) = with(nbt) {
        startingRadiation = getFloat("StartingRadiation")
        maxTime = getInt("MaxTime")
        timeLeft = getInt("TimeLeft")
        ignoreArmor = getBoolean("IgnoreArmor")
    }
}
