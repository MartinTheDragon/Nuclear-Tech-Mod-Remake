package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.coremodules.minecraft.world.InteractionResult
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.context.UseOnContext

interface ScrewdriverInteractable {
    fun onScrew(context: UseOnContext): InteractionResult
}
