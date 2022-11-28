package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.config.NuclearConfig
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import kotlin.math.PI

open class RBMKReaSimRodBlockEntity(type: BlockEntityType<out RBMKReaSimRodBlockEntity>, pos: BlockPos, state: BlockState) : RBMKRodBlockEntity(type, pos, state) {
    constructor(pos: BlockPos, state: BlockState) : this(BlockEntityTypes.rbmkReaSimRodBlockEntityType.get(), pos, state)

    override val defaultName = LangKeys.CONTAINER_RBMK_REASIM_ROD.get()

    override fun spreadFlux(type: RBMKFluxReceiver.NeutronType, fluxOut: Double) {
        val range = NuclearConfig.rbmk.reasimRange.get()
        val count = NuclearConfig.rbmk.reasimCount.get()

        val baseVector = Vec3(1.0, 0.0, 0.0)
        for (i in 0 until count) {
            var streamType = type
            var flux = fluxOut * NuclearConfig.rbmk.reasimMod.get()

            val rotatedVector = baseVector.yRot((PI * 2.0 * levelUnchecked.random.nextDouble()).toFloat())

            for (j in 1..range) {
                val x = (0.5 + rotatedVector.x * j).toInt()
                val z = (0.5 + rotatedVector.z * j).toInt()
                val xLast = (0.5 + rotatedVector.x * (j - 1)).toInt()
                val zLast = (0.5 + rotatedVector.z * (j - 1)).toInt()

                if (x == 0 && z == 0 || x == xLast && z == zLast) continue
                val (newType, returnedFlux) = runInteraction(blockPos.offset(x, 0, z), flux, streamType)
                streamType = newType
                flux = returnedFlux

                if (flux <= 0) break
            }
        }
    }

    override val consoleType = RBMKConsoleBlockEntity.Column.Type.FUEL_REASIM
}
