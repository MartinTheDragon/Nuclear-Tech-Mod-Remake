package at.martinthedragon.nucleartech.coremodules.minecraft.world.entity.player

import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.sounds.SoundEvent
import at.martinthedragon.nucleartech.coremodules.minecraft.world.entity.Entity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.entity.LivingEntity

interface Player : LivingEntity {
    fun displayClientMessage(message: Component, aboveToolbar: Boolean)

    fun playSound(soundEvent: SoundEvent, volume: Float, pitch: Float)
}
