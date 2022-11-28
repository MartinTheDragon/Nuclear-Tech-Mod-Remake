package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import net.minecraft.network.chat.Component

interface RBMKFluxReceiver {
    enum class NeutronType(val displayName: Component) {
        FAST(LangKeys.RBMK_NEUTRON_FAST.get()),
        SLOW(LangKeys.RBMK_NEUTRON_SLOW.get()),
        ANY(LangKeys.RBMK_NEUTRON_ANY.get());
    }

    fun receiveFlux(type: NeutronType, amount: Double)
}
