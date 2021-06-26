package at.martinthedragon.nucleartech.capabilites

import at.martinthedragon.nucleartech.radiation.EntityIrradiationHandler
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object CapabilityIrradiationHandler {
    @CapabilityInject(IIrradiationHandler::class)
    lateinit var irradiationHandlerCapability: Capability<IIrradiationHandler>

    fun register() {
        CapabilityManager.INSTANCE.register(IIrradiationHandler::class.java,
            object : Capability.IStorage<IIrradiationHandler> {
                override fun writeNBT(
                    capability: Capability<IIrradiationHandler>,
                    instance: IIrradiationHandler,
                    side: Direction?
                ): INBT {
                    val nbt = CompoundNBT()
                    nbt.putFloat("HfrRadiation", instance.getIrradiation())
                    return nbt
                }

                override fun readNBT(
                    capability: Capability<IIrradiationHandler>?,
                    instance: IIrradiationHandler?,
                    side: Direction?,
                    nbt: INBT
                ) {
                    if (instance !is IIrradiationHandlerModifiable)
                        throw RuntimeException("IIrradiationHandler instance does not implement IIrradiationHandlerModifiable")
                    instance.setIrradiation((nbt as CompoundNBT).getFloat("HfrRadiation"))
                }
            }, ::EntityIrradiationHandler)
    }
}
