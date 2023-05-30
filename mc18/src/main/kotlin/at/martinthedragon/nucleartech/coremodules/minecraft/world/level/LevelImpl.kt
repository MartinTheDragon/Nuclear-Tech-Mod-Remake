package at.martinthedragon.nucleartech.coremodules.minecraft.world.level

import at.martinthedragon.nucleartech.sorcerer.Injection

@Injection @JvmInline
value class LevelImpl(val delegate: net.minecraft.world.level.Level) : Level {
}
