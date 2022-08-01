package at.martinthedragon.nucleartech.api.sound

import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraftforge.client.ForgeHooksClient
import kotlin.random.Random

// somewhat a reconstruction of mekanism's implementation. it's just so good :p
public class MachineSoundInstance(sound: SoundEvent, source: SoundSource, pos: BlockPos, loop: Boolean) : AbstractTickableSoundInstance(sound, source) {
    init {
        x = pos.x + .5
        y = pos.y + .5
        z = pos.z + .5
        looping = loop
        delay = 0
    }

    private var interval = 20 + Random.nextInt(20)

    override fun tick() {
        val level = Minecraft.getInstance().level ?: return
        if (level.gameTime % interval == 0L) {
            if (!isClientPlayerInRange()) {
                stop()
                return
            }

            val sound = ForgeHooksClient.playSound(Minecraft.getInstance().soundManager.soundEngine, this)
            if (sound == null) stop()
        }
    }

    override fun canStartSilent(): Boolean = true
}
