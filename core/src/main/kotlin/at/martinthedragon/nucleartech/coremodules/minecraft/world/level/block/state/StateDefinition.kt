package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state

import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties.Property

interface StateDefinition<O, S : StateHolder<O, S>> {
    fun any(): S

    interface Builder<O, S : StateHolder<O, S>> {
        fun add(vararg properties: Property<*>): Builder<O, S>
        fun create(defaultGetter: (owner: O) -> S, )
    }
}
