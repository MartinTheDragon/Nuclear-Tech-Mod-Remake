package at.martinthedragon.nucleartech.coremodules.minecraft.world

import at.martinthedragon.nucleartech.sorcerer.Linkage
import at.martinthedragon.nucleartech.sorcerer.Linkage.Companion.MC12
import at.martinthedragon.nucleartech.sorcerer.Linkage.Companion.MC18

@Linkage(MC12, "net.minecraft.util.EnumActionResult")
@Linkage.Enum(MC12, [0, 0, 0, 1, 2])
@Linkage(MC18, "net.minecraft.world.InteractionResult")
enum class InteractionResult {
    SUCCESS,
    CONSUME,
    CONSUME_PARTIAL,
    PASS,
    FAIL;

    val consumesAction get() = this == SUCCESS || this == CONSUME || this == CONSUME_PARTIAL
    val shouldSwing get() = this == SUCCESS
    val shouldAwardStats get() = this == SUCCESS || this == CONSUME

    companion object {
        fun sidedSuccess(isClient: Boolean) = if (isClient) SUCCESS else CONSUME
    }
}
