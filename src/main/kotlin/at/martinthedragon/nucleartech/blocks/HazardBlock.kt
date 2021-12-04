package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.hazards.blocks.HazardBlockEffect
import at.martinthedragon.nucleartech.hazards.blocks.RadioactiveBlockEffect
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import java.util.*

open class HazardBlock(properties: Properties, hazardEffects: Set<HazardBlockEffect> = emptySet()) : Block(properties) {
    private val effects = hazardEffects.toMutableSet()

    // TODO particles

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos, random: Random) {
        for (effect in effects) effect.tick(state, world, pos, random)
        world.scheduleTick(pos, this, 20)
    }

    override fun onPlace(oldState: BlockState, world: Level, pos: BlockPos, newState: BlockState, p_220082_5_: Boolean) {
        if (!world.isClientSide && effects.isNotEmpty()) world.scheduleTick(pos, this, 20)
    }

    fun radiation(amount: Float): HazardBlock {
        effects += RadioactiveBlockEffect(amount)
        return this
    }
}
