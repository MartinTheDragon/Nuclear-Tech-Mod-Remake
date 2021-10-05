package at.martinthedragon.nucleartech.capabilites.contamination

interface IContaminationHandler {
    fun getIrradiation(): Float
    fun getDigamma(): Float
    fun getAsbestos(): Int
    fun getBlacklung(): Int
    fun getBombTimer(): Int
    fun getContagion(): Int
}
