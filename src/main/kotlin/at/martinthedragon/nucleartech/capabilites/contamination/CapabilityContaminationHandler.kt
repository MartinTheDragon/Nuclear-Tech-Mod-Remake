package at.martinthedragon.nucleartech.capabilites.contamination

import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager

object CapabilityContaminationHandler {
    @CapabilityInject(IContaminationHandler::class)
    lateinit var contaminationHandlerCapability: Capability<IContaminationHandler>

    internal fun register() {
        CapabilityManager.INSTANCE.register(IContaminationHandler::class.java,
            object : Capability.IStorage<IContaminationHandler> {
                override fun writeNBT(
                    capability: Capability<IContaminationHandler>,
                    instance: IContaminationHandler,
                    side: Direction?
                ): INBT {
                    val nbt = CompoundNBT()
                    nbt.putFloat("radiation", instance.getIrradiation())
                    nbt.putFloat("digamma", instance.getDigamma())
                    nbt.putInt("asbestos", instance.getAsbestos())
                    nbt.putInt("blacklung", instance.getBlacklung())
                    nbt.putInt("bomb", instance.getBombTimer())
                    nbt.putInt("contagion", instance.getContagion())
                    return nbt
                }

                override fun readNBT(
                    capability: Capability<IContaminationHandler>?,
                    instance: IContaminationHandler?,
                    side: Direction?,
                    nbt: INBT
                ) {
                    if (instance !is IContaminationHandlerModifiable)
                        throw RuntimeException("IContaminationHandler instance does not implement IContaminationHandlerModifiable")
                    instance.setIrradiation((nbt as CompoundNBT).getFloat("radiation"))
                    instance.setDigamma(nbt.getFloat("digamma"))
                    instance.setAsbestos(nbt.getInt("asbestos"))
                    instance.setBlacklung(nbt.getInt("blacklung"))
                    instance.setBombTimer(nbt.getInt("bomb"))
                    instance.setContagion(nbt.getInt("contagion"))
                }
            }, ::EntityContaminationHandler)
    }

    fun getCapability(entity: LivingEntity) =
        entity.getCapability(contaminationHandlerCapability)
            .takeIf { it.isPresent }
            ?.orElseThrow(::Error) as? IContaminationHandlerModifiable
}
