package at.martinthedragon.nucleartech.api.sounds

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.math.max

public object SoundHandler {
    private val soundMap = Long2ObjectOpenHashMap<SoundInstance>()

    public fun playBlockEntitySoundEvent(pos: BlockPos, event: SoundEvent, source: SoundSource, volume: Float, pitch: Float): SoundInstance? {
        var instance: SoundInstance? = soundMap[pos.asLong()]
        if (instance.isActive()) return instance

        instance = MachineSoundInstance(event, source, pos)

        if (!instance.isClientPlayerInRange()) return null
        Minecraft.getInstance().soundManager.play(instance)
        soundMap[pos.asLong()] = instance
        return instance
    }

    public fun stopBlockEntitySound(pos: BlockPos) {
        soundMap.remove(pos.asLong())?.let { Minecraft.getInstance().soundManager.stop(it) }
    }

    public fun stopBlockEntitySound(instance: SoundInstance) {
        if (soundMap.containsValue(instance)) soundMap.long2ObjectEntrySet().find { (_, sound) -> sound === instance }?.longKey?.let {
            soundMap.remove(it)?.let { sound -> Minecraft.getInstance().soundManager.stop(sound) }
        }
    }
}

@OptIn(ExperimentalContracts::class)
public fun SoundInstance?.isActive(): Boolean {
    contract {
        returns(true) implies (this@isActive != null)
    }
    return this != null && Minecraft.getInstance().soundManager.isActive(this)
}

public fun SoundInstance.isClientPlayerInRange(): Boolean {
    if (isRelative || attenuation == SoundInstance.Attenuation.NONE) return true
    val player = Minecraft.getInstance().player ?: return false
    val sound = sound ?: run {
        resolve(Minecraft.getInstance().soundManager)
        sound
    }
    val distance = max(volume, 1F) * sound.attenuationDistance
    return player.position().distanceToSqr(x, y, z) < distance * distance
}
