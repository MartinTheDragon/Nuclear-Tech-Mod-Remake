package at.martinthedragon.nucleartech.capability.contamination

import at.martinthedragon.nucleartech.capability.contamination.effects.ContaminationEffect
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraftforge.common.util.INBTSerializable

/** Gets synced at [at.martinthedragon.nucleartech.hazard.EntityContaminationEffects] */
open class EntityContaminationHandler : ContaminationHandler, INBTSerializable<CompoundTag> {
    protected var irradiationLevel = 0F
    protected var digammaLevel = 0F
    protected var asbestosLevel = 0
    protected var blacklungLevel = 0
    protected var bombTimerValue = 0
    protected var contagionValue = 0

    protected var cumulativeRadiationValue = 0F
    protected var radPerSecondValue = 0F

    protected val contaminationEffectsList = mutableListOf<ContaminationEffect>()

    override fun setIrradiation(amount: Float) {
        irradiationLevel = amount
        onIrradiationLevelChanged()
    }

    override fun setDigamma(amount: Float) {
        digammaLevel = amount
        onDigammaLevelChanged()
    }

    override fun setAsbestos(amount: Int) {
        asbestosLevel = amount
        onAsbestosLevelChanged()
    }

    override fun setBlackLung(amount: Int) {
        blacklungLevel = amount
        onBlacklungLevelChanged()
    }

    override fun setBombTimer(value: Int) {
        bombTimerValue = value
        onBombTimerValueChanged()
    }

    override fun setContagion(value: Int) {
        contagionValue = value
        onContagionValueChanged()
    }

    override fun setCumulativeRadiation(value: Float) { cumulativeRadiationValue = value }
    override fun setRadPerSecond(value: Float) { radPerSecondValue = value }

    override fun getIrradiation(): Float = irradiationLevel
    override fun getDigamma(): Float = digammaLevel
    override fun getAsbestos(): Int = asbestosLevel
    override fun getBlackLung(): Int = blacklungLevel
    override fun getBombTimer(): Int = bombTimerValue
    override fun getContagion(): Int = contagionValue

    override fun getCumulativeRadiation(): Float = cumulativeRadiationValue
    override fun getRadPerSecond(): Float = radPerSecondValue

    override fun getContaminationEffects() = contaminationEffectsList

    override fun serializeNBT(): CompoundTag = CompoundTag().apply {
        putFloat("radiation", irradiationLevel)
        putFloat("digamma", digammaLevel)
        putInt("asbestos", asbestosLevel)
        putInt("blacklung", blacklungLevel)
        putInt("bomb", bombTimerValue)
        putInt("contagion", contagionValue)
        put("effects", ListTag().apply { for (effect in getContaminationEffects()) add(effect.save()) })
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        with(nbt) {
            irradiationLevel = getFloat("radiation")
            digammaLevel = getFloat("digamma")
            asbestosLevel = getInt("asbestos")
            blacklungLevel = getInt("blacklung")
            bombTimerValue = getInt("bomb")
            contagionValue = getInt("contagion")
            val effects = getList("effects", 10)
            for (i in effects.indices) contaminationEffectsList += ContaminationEffect.deserialize(effects.getCompound(i))
        }
        onLoad()
    }

    protected open fun onLoad() {}
    protected open fun onIrradiationLevelChanged() {}
    protected open fun onDigammaLevelChanged() {}
    protected open fun onAsbestosLevelChanged() {}
    protected open fun onBlacklungLevelChanged() {}
    protected open fun onBombTimerValueChanged() {}
    protected open fun onContagionValueChanged() {}
}
