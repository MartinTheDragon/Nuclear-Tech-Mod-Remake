package at.martinthedragon.nucleartech.capabilites.contamination

import at.martinthedragon.nucleartech.capabilites.contamination.effects.ContaminationEffect

interface ContaminationHandler {
    fun getIrradiation(): Float
    fun getDigamma(): Float
    fun getAsbestos(): Int
    fun getBlacklung(): Int
    fun getBombTimer(): Int
    fun getContagion(): Int

    fun getCumulativeRadiation(): Float
    fun getRadPerSecond(): Float

    fun setIrradiation(amount: Float)
    fun setDigamma(amount: Float)
    fun setAsbestos(amount: Int)
    fun setBlacklung(amount: Int)
    fun setBombTimer(value: Int)
    fun setContagion(value: Int)

    fun setCumulativeRadiation(value: Float)
    fun setRadPerSecond(value: Float)

    fun getContaminationEffects(): MutableCollection<ContaminationEffect>
}

fun ContaminationHandler.addIrradiation(amount: Float) =
    setIrradiation(getIrradiation() + amount)

fun ContaminationHandler.addDigamma(amount: Float) =
    setDigamma(getDigamma() + amount)
