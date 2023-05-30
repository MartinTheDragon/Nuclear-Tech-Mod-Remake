package at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties

import at.martinthedragon.nucleartech.coremodules.minecraft.util.StringRepresentable

interface EnumProperty<T> : Property<T>
    where T : Enum<T>,
          T : StringRepresentable
{
}
