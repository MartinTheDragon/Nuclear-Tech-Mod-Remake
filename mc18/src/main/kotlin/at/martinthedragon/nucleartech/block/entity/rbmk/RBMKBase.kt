package at.martinthedragon.nucleartech.block.entity.rbmk

import at.martinthedragon.nucleartech.NTechSoundsCore
import at.martinthedragon.nucleartech.api.block.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.entity.BlockEntityWrapper
import at.martinthedragon.nucleartech.block.rbmk.RBMKBaseBlock
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.entity.RBMKDebris
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.particle.RBMKMushParticleOptions
import at.martinthedragon.nucleartech.particle.sendParticles
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import kotlin.math.min

interface RBMKBase : BlockEntityWrapper, TickingServerBlockEntity {
    var heat: Double
    var water: Int
    var steam: Int

    val valid: Boolean

    val isLidRemovable: Boolean get() = true

    fun hasLid(): Boolean {
        if (!isLidRemovable) return true
        return getLid().isLid()
    }

    fun getLid(): RBMKBaseBlock.LidType {
        val blockState = levelUnchecked.getBlockState(blockPosWrapped)
        return if (blockState.block !is RBMKBaseBlock) RBMKBaseBlock.LidType.NONE else blockState.getValue(RBMKBaseBlock.LID_TYPE)
    }

    fun maxHeat(): Double = 1500.0
    fun passiveCooling(): Double = NuclearConfig.rbmk.passiveCooling.get()

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (level.isClientSide) return

        level.profiler.push("rbmkBase_heat_movement")
        moveHeat()

        if (NuclearConfig.rbmk.reasimBoilers.get()) {
            level.profiler.popPush("rbmkBase_reasim_boilers")
            boilWater()
        }

        level.profiler.popPush("rbmkBase_passive_cooling")
        coolPassively()
        level.profiler.pop()
    }

    fun boilWater() {
        if (heat < 100.0) return

        val heatConsumption = NuclearConfig.rbmk.boilerHeatConsumption.get()
        val availableHeat = (heat - 100.0) / heatConsumption
        val availableWater = water
        val availableSpace = MAX_STEAM - steam

        val processedWater = (min(availableHeat, min(availableWater, availableSpace).toDouble()) * NuclearConfig.rbmk.reasimBoilerSpeed.get()).toInt()

        water -= processedWater
        steam += processedWater
        heat -= processedWater * heatConsumption
    }

    val heatCache: Array<RBMKBase?>

    fun moveHeat() {
        val rec = mutableListOf<RBMKBase>()
        rec += this

        var heatTotal = heat
        var waterTotal = water
        var steamTotal = steam

        for ((index, direction) in HEAT_DIRECTIONS.withIndex()) {
            val other = heatCache[index]
            if (other != null && !other.valid) heatCache[index] = null

            if (other == null) {
                val blockEntity = levelUnchecked.getBlockEntity(blockPosWrapped.relative(direction))
                if (blockEntity is RBMKBase) heatCache[index] = blockEntity
            }
        }

        for (other in heatCache) if (other != null) {
            rec += other
            heatTotal += other.heat
            waterTotal += other.water
            steamTotal += other.steam
        }

        val stepSize = NuclearConfig.rbmk.columnHeatFlow.get()

        val members = rec.size
        if (members > 1) {
            val targetHeat = heatTotal / members
            val tWater = waterTotal / members
            val rWater = waterTotal % members
            val tSteam = steamTotal / members
            val rSteam = steamTotal % members

            for (member in rec) {
                val delta = targetHeat - member.heat
                member.heat += delta * stepSize
                member.water = tWater
                member.steam = tSteam
            }

            water += rWater
            steam += rSteam

            markDirty()
        }
    }

    fun coolPassively() {
        // TODO tom effects

        heat -= passiveCooling()

        if (heat < 20) heat = 20.0
    }

    fun saveAdditional(tag: CompoundTag) {
        tag.putDouble("Heat", heat)
        tag.putInt("Water", water)
        tag.putInt("Steam", steam)
    }

    fun load(tag: CompoundTag) {
        heat = tag.getDouble("Heat")
        water = tag.getInt("Water")
        steam = tag.getInt("Steam")
    }

    fun onOverheat() {
        for (i in 0 until 4) {
            levelUnchecked.setBlockAndUpdate(blockPosWrapped.offset(0, i, 0), Blocks.LAVA.defaultBlockState())
        }
    }

    fun meltdown() {
        val allParts = findAllConnected()

        var minX = blockPosWrapped.x
        var maxX = blockPosWrapped.x
        var minZ = blockPosWrapped.z
        var maxZ = blockPosWrapped.z

        for (part in allParts) {
            if (part.blockPosWrapped.x < minX) minX = part.blockPosWrapped.x
            if (part.blockPosWrapped.x > maxX) maxX = part.blockPosWrapped.x
            if (part.blockPosWrapped.z < minZ) minZ = part.blockPosWrapped.z
            if (part.blockPosWrapped.z > maxZ) maxZ = part.blockPosWrapped.z
        }

        for (part in allParts) {
            val distanceMinX = part.blockPosWrapped.x - minX
            val distanceMaxX = maxX - part.blockPosWrapped.x
            val distanceMinZ = part.blockPosWrapped.z - minZ
            val distanceMaxZ = maxZ - part.blockPosWrapped.z

            val distanceCenter = min(distanceMinX, min(distanceMaxX, min(distanceMinZ, distanceMaxZ)))
            part.onMelt(distanceCenter + 1)
        }

        for (part in allParts) {
            if (part is RBMKRodBlockEntity && levelUnchecked.getBlockState(part.blockPos.offset(0, 1 - getColumnHeight(), 0)).`is`(NTechFluids.corium.block.get())) {
                val middle = part.blockPos.offset(0, 1 - getColumnHeight() / 2, 0)
                for (pos in BlockPos.betweenClosed(middle.offset(-1, -1, -1), middle.offset(1, 1, 1))) {
                    val state = levelUnchecked.getBlockState(pos)
                    if (levelUnchecked.random.nextInt(3) == 0 && (state.`is`(NTechBlocks.rbmkDebris.get()) || state.`is`(NTechBlocks.rbmkBurningDebris.get()))) {
                        levelUnchecked.setBlockAndUpdate(pos, NTechBlocks.rbmkRadioactiveDebris.get().defaultBlockState())
                    }
                }
            }
        }

        val level = levelUnchecked as ServerLevel
        val smallestDimension = min(maxX - minX, maxZ - minZ).coerceAtLeast(1)
        val centerX = minX + (maxX - minX) / 2
        val centerZ = minZ + (maxZ - minZ) / 2

        level.sendParticles(RBMKMushParticleOptions(smallestDimension.toFloat() * 2F), true, centerX + 0.5, blockPosWrapped.y + 3.0, centerZ + 0.5, 1, 0.0, 0.0, 0.0, 0.0)
        level.playSound(null, centerX + 0.5, blockPosWrapped.y + 1.0, centerZ + 0.5, NTechSoundsCore.rbmkExplosion.get(), SoundSource.BLOCKS, 50F, 1F)
    }

    fun findAllConnected(): List<RBMKBase> {
        val result = mutableListOf<RBMKBase>()
        findRecursive(levelUnchecked, blockPosWrapped, result)
        return result.toList()
    }

    fun onMelt(reduce: Int) {
        val hadLid = getLid() == RBMKBaseBlock.LidType.CONCRETE
        standardMelt(reduce)
        if (hadLid) spawnDebris(RBMKDebris.DebrisType.LID)
    }

    fun standardMelt(r: Int) {
        val height = getColumnHeight()
        var reduce = r.coerceIn(0, height)
        if (levelUnchecked.random.nextInt(3) == 0) reduce++

        for (i in height downTo 1) {
            if (i <= height + 1 - reduce) {
                if (reduce > 1 && i == height + 1 - reduce) {
                    levelUnchecked.setBlockAndUpdate(blockPosWrapped.offset(0, i - height, 0), NTechBlocks.rbmkBurningDebris.get().defaultBlockState())
                } else {
                    levelUnchecked.setBlockAndUpdate(blockPosWrapped.offset(0, i - height, 0), NTechBlocks.rbmkDebris.get().defaultBlockState())
                }
            } else {
                levelUnchecked.setBlockAndUpdate(blockPosWrapped.offset(0, i - height, 0), Blocks.AIR.defaultBlockState())
            }
        }
    }

    fun spawnDebris(type: RBMKDebris.DebrisType) {
        val debris = RBMKDebris(levelUnchecked, blockPosWrapped.toVec3Middle(), type)
        val random = levelUnchecked.random
        debris.setDeltaMovement(random.nextGaussian() * 0.25, 0.25 + random.nextDouble() * 1.25, random.nextGaussian() * 0.25)

        if (type == RBMKDebris.DebrisType.LID) {
            debris.deltaMovement = debris.deltaMovement.multiply(0.5, 1.0, 0.5).add(0.0, 0.5, 0.0)
        }
        levelUnchecked.addFreshEntity(debris)
    }

    fun isModerated(): Boolean = false

    fun getColumnHeight(): Int {
        val thisBlock = levelUnchecked.getBlockState(blockPosWrapped).block
        if (thisBlock !is RBMKBaseBlock) return 0
        val blockToLookFor = thisBlock.column.get()

        var height = 1

        for (i in 1..15) {
            if (levelUnchecked.getBlockState(blockPosWrapped.offset(0, -i, 0)).`is`(blockToLookFor))
                height++
            else break
        }

        return height
    }

    fun markDirty()

    fun getRenderBoundingBox() = AABB(blockPosWrapped.offset(0, -getColumnHeight(), 0), blockPosWrapped.offset(1, 2, 1))

    val consoleType: RBMKConsoleBlockEntity.Column.Type
    fun getConsoleData(): CompoundTag

    companion object {
        const val MAX_WATER = 16_000
        const val MAX_STEAM = 16_000

        val HEAT_DIRECTIONS = arrayOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)

        private fun findRecursive(level: Level, pos: BlockPos, existing: MutableList<RBMKBase>) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is RBMKBase && blockEntity !in existing) {
                existing += blockEntity
                findRecursive(level, pos.north(), existing)
                findRecursive(level, pos.south(), existing)
                findRecursive(level, pos.east(), existing)
                findRecursive(level, pos.west(), existing)
            }
        }
    }
}
