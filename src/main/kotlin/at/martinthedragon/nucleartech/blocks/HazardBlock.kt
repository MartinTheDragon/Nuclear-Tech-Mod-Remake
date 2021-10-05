package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.hazards.blocks.HazardBlockEffect
import at.martinthedragon.nucleartech.hazards.blocks.RadioactiveBlockEffect
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import java.util.*

open class HazardBlock(properties: Properties, hazardEffects: Set<HazardBlockEffect> = emptySet()) : Block(properties) {
    private val effects = hazardEffects.toMutableSet()

    // TODO particles

    override fun tick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        for (effect in effects) effect.tick(state, world, pos, random)
        world.blockTicks.scheduleTick(pos, this, 20)
    }

    override fun onPlace(oldState: BlockState, world: World, pos: BlockPos, newState: BlockState, p_220082_5_: Boolean) {
        if (!world.isClientSide && effects.isNotEmpty()) world.blockTicks.scheduleTick(pos, this, 20)
    }

    fun radiation(amount: Float): HazardBlock {
        effects += RadioactiveBlockEffect(amount)
        return this
    }
}
