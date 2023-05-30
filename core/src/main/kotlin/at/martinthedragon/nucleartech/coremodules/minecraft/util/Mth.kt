package at.martinthedragon.nucleartech.coremodules.minecraft.util

import at.martinthedragon.nucleartech.coremodules.InjectionStatic

interface Mth {
    fun color(r: Int, g: Int, b: Int): Int

    companion object {
        fun color(r: Int, g: Int, b: Int) = InjectionStatic.mth.color(r, g, b)
    }
}
