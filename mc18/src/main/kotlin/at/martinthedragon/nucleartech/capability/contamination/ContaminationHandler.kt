package at.martinthedragon.nucleartech.capability.contamination

import at.martinthedragon.nucleartech.capability.contamination.effect.ContaminationEffect
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface ContaminationHandler {
    fun getIrradiation(): Float
    fun getDigamma(): Float
    fun getAsbestos(): Int
    fun getBlackLung(): Int
    fun getBombTimer(): Int
    fun getContagion(): Int

    fun getCumulativeRadiation(): Float
    fun getRadPerSecond(): Float

    fun setIrradiation(amount: Float)
    fun setDigamma(amount: Float)
    fun setAsbestos(amount: Int)
    fun setBlackLung(amount: Int)
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

fun ContaminationHandler.addAsbestos(amount: Int) =
    setAsbestos(getAsbestos() + amount)

fun ContaminationHandler.addBlackLung(amount: Int) =
    setBlackLung(getBlackLung() + amount)

fun ContaminationHandler.addEffect(effect: ContaminationEffect): Boolean = getContaminationEffects().add(effect)

inline fun <reified T : ContaminationEffect> ContaminationHandler.addEffectFromSource(effect: T): Boolean =
    if (effect.source == null || getContaminationEffects().none { it is T && it.source == effect.source }) addEffect(effect) else false

@OptIn(ExperimentalContracts::class)
inline fun <reified T : ContaminationEffect> ContaminationHandler.modifyEffectFromSourceIf(source: String, condition: (T) -> Boolean, action: (T) -> Unit): Boolean {
    contract {
        callsInPlace(condition, InvocationKind.UNKNOWN)
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    val effect = getContaminationEffects().filterIsInstance<T>().filter { it.source == source }.firstOrNull(condition) ?: return false
    action(effect)
    return true
}

inline fun <reified T : ContaminationEffect> ContaminationHandler.replaceEffectFromSourceIf(effect: T, condition: (T) -> Boolean): Boolean {
    if (getContaminationEffects().none { it is T }) return addEffect(effect)
    val oldEffects = getContaminationEffects().filterIsInstance<T>().filter { it.source == effect.source }.filter(condition)
    return if (oldEffects.isNotEmpty()) {
        getContaminationEffects().removeAll(oldEffects.toSet())
        addEffect(effect)
    } else false
}

inline fun <reified T : ContaminationEffect> ContaminationHandler.hasEffectFromSource(source: String): Boolean =
    getContaminationEffects().any { it is T && it.source == source }
