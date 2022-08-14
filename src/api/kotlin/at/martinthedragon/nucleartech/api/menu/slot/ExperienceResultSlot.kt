package at.martinthedragon.nucleartech.api.menu.slot

import at.martinthedragon.nucleartech.api.block.entities.ExperienceRecipeResultBlockEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler

public open class ExperienceResultSlot(
    public val tileEntity: ExperienceRecipeResultBlockEntity,
    player: Player,
    inventory: IItemHandler,
    index: Int,
    xPosition: Int, yPosition: Int
) : ResultSlot(player, inventory, index, xPosition, yPosition) {
    override fun checkTakeAchievements(stack: ItemStack) {
        super.checkTakeAchievements(stack)
        if (!player.level.isClientSide)
            tileEntity.dropExperienceAndAwardRecipes(player)
    }
}
