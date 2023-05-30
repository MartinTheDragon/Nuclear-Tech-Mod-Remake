package at.martinthedragon.nucleartech.net

import at.martinthedragon.nucleartech.coremodules.forge.fml.LogicalSide

enum class NetDirection(val logicalSide: LogicalSide, val otherWay: Int) {
    PLAY_TO_SERVER(LogicalSide.CLIENT, 1),
    PLAY_TO_CLIENT(LogicalSide.SERVER, 0),
    LOGIN_TO_SERVER(LogicalSide.CLIENT, 3),
    LOGIN_TO_CLIENT(LogicalSide.SERVER, 2);

    val reply: NetDirection get() = NetDirection.values()[otherWay]
    val receptionSide: LogicalSide get() = reply.logicalSide
}
