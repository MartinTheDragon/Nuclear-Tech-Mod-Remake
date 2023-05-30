package at.martinthedragon.nucleartech.api.item

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext

public interface ScrewdriverInteractable {
    public fun onScrew(context: UseOnContext): InteractionResult
}
