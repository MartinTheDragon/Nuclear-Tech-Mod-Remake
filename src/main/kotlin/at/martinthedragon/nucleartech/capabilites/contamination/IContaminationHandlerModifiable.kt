package at.martinthedragon.nucleartech.capabilites.contamination

interface IContaminationHandlerModifiable : IContaminationHandler {
    fun setIrradiation(amount: Float)
    fun setDigamma(amount: Float)
    fun setAsbestos(amount: Int)
    fun setBlacklung(amount: Int)
    fun setBombTimer(value: Int)
    fun setContagion(value: Int)
}

fun IContaminationHandlerModifiable.addIrradiation(amount: Float) =
    setIrradiation(getIrradiation() + amount)

fun IContaminationHandlerModifiable.addDigamma(amount: Float) =
    setDigamma(getDigamma() + amount)
