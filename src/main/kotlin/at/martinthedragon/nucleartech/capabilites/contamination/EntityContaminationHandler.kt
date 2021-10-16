package at.martinthedragon.nucleartech.capabilites.contamination

import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.util.INBTSerializable

/** Gets synced at [at.martinthedragon.nucleartech.hazards.EntityContaminationEffects] */
open class EntityContaminationHandler : IContaminationHandlerModifiable, INBTSerializable<CompoundNBT> {
    protected var irradiationLevel = 0F
    protected var digammaLevel = 0F
    protected var asbestosLevel = 0
    protected var blacklungLevel = 0
    protected var bombTimerValue = 0
    protected var contagionValue = 0

    protected var cumulativeRadiationValue = 0F
    protected var radPerSecondValue = 0F

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

    override fun setBlacklung(amount: Int) {
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
    override fun getBlacklung(): Int = blacklungLevel
    override fun getBombTimer(): Int = bombTimerValue
    override fun getContagion(): Int = contagionValue

    override fun getCumulativeRadiation(): Float = cumulativeRadiationValue
    override fun getRadPerSecond(): Float = radPerSecondValue

    override fun serializeNBT(): CompoundNBT = CompoundNBT().apply {
        putFloat("radiation", irradiationLevel)
        putFloat("digamma", digammaLevel)
        putInt("asbestos", asbestosLevel)
        putInt("blacklung", blacklungLevel)
        putInt("bomb", bombTimerValue)
        putInt("contagion", contagionValue)
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        irradiationLevel = nbt.getFloat("radiation")
        digammaLevel = nbt.getFloat("digamma")
        asbestosLevel = nbt.getInt("asbestos")
        blacklungLevel = nbt.getInt("blacklung")
        bombTimerValue = nbt.getInt("bomb")
        contagionValue = nbt.getInt("contagion")
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
