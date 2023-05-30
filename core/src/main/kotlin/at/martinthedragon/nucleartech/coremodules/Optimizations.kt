package at.martinthedragon.nucleartech.coremodules

import java.util.Locale

/**
 * Offers bypasses around the core API for faster code.
 */
interface Optimizations {
    /**
     * Retrieves the currently selected locale as a java Locale. **Client-only.**
     *
     * 1.18 equivalent:
     * `Minecraft.getInstance().languageManager.selected.javaLocale`
     */
    fun getSelectedLocale(): Locale
}
