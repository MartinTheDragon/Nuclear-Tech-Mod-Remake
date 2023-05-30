package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block

import at.martinthedragon.nucleartech.coremodules.forge.registries.IForgeRegistryEntry
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.BlockPlaceContext
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.Level
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.StateDefinition

open class Block(val properties: Properties) : IForgeRegistryEntry {
    @get:[JvmSynthetic JvmName("registryName_")]
    var registryName: ResourceLocation? = null
    override fun getRegistryName() = registryName

    lateinit var stateDefinition: StateDefinition<Block, BlockState>
    lateinit var defaultBlockState: BlockState
    open fun createDefaultBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {}

    open fun getStateForPlacement(context: BlockPlaceContext): BlockState? = defaultBlockState

    open fun triggerEvent(state: BlockState, level: Level, pos: BlockPos, paramA: Int, paramB: Int): Boolean = false
    open fun appendHoverText(itemStack: ItemStack, level: Level?, tooltip: MutableList<Component>, extended: Boolean) {}

    class Properties {

    }
}


