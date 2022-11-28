package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.entity.RBMKDebris
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.item.RBMKRodItem
import at.martinthedragon.nucleartech.menu.NTechContainerMenu
import at.martinthedragon.nucleartech.menu.rbmk.RBMKRodMenu
import at.martinthedragon.nucleartech.world.ChunkRadiation
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

open class RBMKRodBlockEntity(type: BlockEntityType<out RBMKRodBlockEntity>, pos: BlockPos, state: BlockState) : InventoryRBMKBaseBlockEntity(type, pos, state), RBMKFluxReceiver {
    constructor(pos: BlockPos, state: BlockState) : this(BlockEntityTypes.rbmkRodBlockEntityType.get(), pos, state)

    override val shouldPlaySoundLoop = false
    override val soundLoopEvent: SoundEvent get() = throw UnsupportedOperationException("RBMK has no sound loop")
    override val soundLoopStateMachine = SoundLoopBlockEntity.NoopStateMachine(@Suppress("LeakingThis") this)

    override val mainInventory: NonNullList<ItemStack> = NonNullList.withSize(1, ItemStack.EMPTY)

    override fun isItemValid(slot: Int, stack: ItemStack) = slot == 0 && stack.item is RBMKRodItem

    override fun trackContainerMenu(menu: NTechContainerMenu<*>) {}

    override val defaultName = LangKeys.CONTAINER_RBMK_ROD.get()

    override fun createMenu(windowID: Int, inventory: Inventory) = RBMKRodMenu(windowID, inventory, this)

    var fluxFast = 0.0
    var fluxSlow = 0.0
    var hasRod = false

    override fun receiveFlux(type: RBMKFluxReceiver.NeutronType, amount: Double) {
        when (type) {
            RBMKFluxReceiver.NeutronType.FAST -> fluxFast += amount
            RBMKFluxReceiver.NeutronType.SLOW -> fluxSlow += amount
            else -> {}
        }
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        val hadRod = hasRod

        val rod = mainInventory[0]
        val rodItem = rod.item
        if (rod.isEmpty || rodItem !is RBMKRodItem) {
            fluxFast = 0.0
            fluxSlow = 0.0
            hasRod = false
            if (hadRod) sendContinuousUpdatePacket()
            super.serverTick(level, pos, state)
            return
        }

        val fluxIn = fluxFromType(rodItem.neutronType)
        val fluxOut = rodItem.burn(rod, fluxIn, NuclearConfig.rbmk.reactivityMod.get())

        rodItem.updateHeat(rod, 1.0, NuclearConfig.rbmk.fuelDiffusionMod.get())
        heat += rodItem.provideHeat(rod, heat, 1.0, NuclearConfig.rbmk.heatProvision.get())

        if (!hasLid()) {
            ChunkRadiation.incrementRadiation(level, pos, (fluxFast + fluxSlow).toFloat() * 0.05F)
        }

        super.serverTick(level, pos, state)

        if (heat > maxHeat() && !NuclearConfig.rbmk.disableMeltdowns.get()) {
            meltdown()
            return
        }

        val lastFluxFast = fluxFast
        val lastFluxSlow = fluxSlow
        fluxFast = 0.0
        fluxSlow = 0.0

        level.profiler.push("rbmkRod_flux_spread")
        spreadFlux(rodItem.releaseType, fluxOut)
        level.profiler.pop()

        if (fluxFast != lastFluxFast || fluxSlow != lastFluxSlow)
            sendContinuousUpdatePacket()

        hasRod = true
        if (!hadRod) sendContinuousUpdatePacket()
    }

    var clientCherenkovLevel = 0.0
    val clientIsEmittingCherenkov get() = clientCherenkovLevel > 0

    private val clientCherenkovValues = DoubleArray(20)
    private var currentCherenkovValueIndex = 0

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        clientCherenkovValues[currentCherenkovValueIndex] = fluxFast + fluxSlow
        currentCherenkovValueIndex = (currentCherenkovValueIndex + 1) % clientCherenkovValues.size
        clientCherenkovLevel = clientCherenkovValues.average() - 4.0
    }

    private fun fluxFromType(type: RBMKFluxReceiver.NeutronType) = when (type) {
        RBMKFluxReceiver.NeutronType.SLOW -> fluxFast * .5 + fluxSlow
        RBMKFluxReceiver.NeutronType.FAST -> fluxFast + fluxSlow * .3
        RBMKFluxReceiver.NeutronType.ANY -> fluxFast + fluxSlow
    }

    protected open fun spreadFlux(type: RBMKFluxReceiver.NeutronType, fluxOut: Double) {
        val range = NuclearConfig.rbmk.fluxRange.get()

        for (direction in RBMKBase.HEAT_DIRECTIONS) {
            var streamType = type
            var flux = fluxOut

            for (i in 1..range) {
                val (newType, returnedFlux) = runInteraction(blockPos.relative(direction, i), flux, streamType)
                streamType = newType
                flux = returnedFlux

                if (flux <= 0) break
            }
        }
    }

    protected fun runInteraction(pos: BlockPos, flux: Double, type: RBMKFluxReceiver.NeutronType): Pair<RBMKFluxReceiver.NeutronType, Double> {
        val blockEntity = levelUnchecked.getBlockEntity(pos)
        var returnType = type

        if (blockEntity is RBMKBase) {
            if (!blockEntity.hasLid()) ChunkRadiation.incrementRadiation(levelUnchecked, pos, flux.toFloat() * 0.05F)
            if (blockEntity.isModerated()) returnType = RBMKFluxReceiver.NeutronType.SLOW
        }

        if (blockEntity is RBMKRodBlockEntity) {
            return if (blockEntity.hasRod) {
                blockEntity.receiveFlux(type, flux)
                returnType to 0.0
            } else
                returnType to flux
        }

        if (blockEntity is RBMKFluxReceiver) {
            blockEntity.receiveFlux(returnType, flux)
            return returnType to 0.0
        }

        if (blockEntity is RBMKControlBlockEntity) {
            return returnType to flux * blockEntity.fluxMultiplier
        }

        if (blockEntity is RBMKModeratorBlockEntity) {
            returnType = RBMKFluxReceiver.NeutronType.SLOW
            return returnType to flux
        }

        if (blockEntity is RBMKReflectorBlockEntity) {
            receiveFlux(returnType, flux)
            return returnType to 0.0
        }

        if (blockEntity is RBMKAbsorberBlockEntity) {
            return returnType to 0.0
        }

        if (blockEntity is RBMKBase) {
            return returnType to flux
        }

        val limit = getColumnHeight()
        var hits = 0
        for (h in 0..limit) {
            if (!levelUnchecked.getBlockState(pos.offset(0, -h, 0)).isCollisionShapeFullBlock(levelUnchecked, pos))
                hits++
        }

        if (hits > 0)
            ChunkRadiation.incrementRadiation(levelUnchecked, pos, (flux.toFloat() * .05F * hits / limit))

        return returnType to 0.0
    }

    override fun onMelt(reduce: Int) {
        val hadLid = getLid() == RBMKBaseBlock.LidType.CONCRETE
        val height = getColumnHeight()
        var reduceActual = reduce.coerceIn(0, height)
        if (levelUnchecked.random.nextInt(3) == 0) reduceActual++

        val corium = hasRod
        mainInventory[0] = ItemStack.EMPTY

        if (corium) {
            for (i in height downTo 1) {
                if (i <= height + 1 - reduceActual) {
                    levelUnchecked.setBlockAndUpdate(blockPos.relative(Direction.UP, i - height), NTechFluids.corium.block.get().defaultBlockState())
                } else {
                    levelUnchecked.setBlockAndUpdate(blockPos.relative(Direction.UP, i - height), Blocks.AIR.defaultBlockState())
                }
            }

            val count = 1 + levelUnchecked.random.nextInt(getColumnHeight())

            for (i in 0 until count) spawnDebris(RBMKDebris.DebrisType.FUEL)
        } else {
            standardMelt(reduceActual)
        }

        spawnDebris(RBMKDebris.DebrisType.ELEMENT)

        if (hadLid) spawnDebris(RBMKDebris.DebrisType.LID)
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.putDouble("FluxFast", fluxFast)
        tag.putDouble("FluxSlow", fluxSlow)
        tag.putBoolean("HasRod", hasRod)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        fluxFast = tag.getDouble("FluxFast")
        fluxSlow = tag.getDouble("FluxSlow")
        hasRod = tag.getBoolean("HasRod")
    }

    override fun getContinuousUpdateTag() = super.getContinuousUpdateTag().apply {
        putBoolean("HasRod", hasRod)
        putDouble("FluxFast", fluxFast)
        putDouble("FluxSlow", fluxSlow)
    }

    override fun handleContinuousUpdatePacket(tag: CompoundTag) {
        super.handleContinuousUpdatePacket(tag)
        hasRod = tag.getBoolean("HasRod")
        fluxFast = tag.getDouble("FluxFast")
        fluxSlow = tag.getDouble("FluxSlow")
    }

    override val consoleType = RBMKConsoleBlockEntity.Column.Type.FUEL
    override fun getConsoleData() = CompoundTag().apply {
        if (hasRod) {
            val rod = mainInventory[0]
            putDouble("Enrichment", RBMKRodItem.getEnrichment(rod))
            putDouble("Xenon", RBMKRodItem.getPoison(rod))
            putDouble("FuelHeat", RBMKRodItem.getHullHeat(rod))
            putDouble("FuelCoreHeat", RBMKRodItem.getCoreHeat(rod))
            putDouble("FuelMaxHeat", (rod.item as RBMKRodItem).meltingPoint)
        }
    }
}
