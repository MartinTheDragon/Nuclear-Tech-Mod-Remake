package at.martinthedragon.nucleartech.items

import at.martinthedragon.nucleartech.CreativeTabs
import at.martinthedragon.nucleartech.screens.UseTemplateFolderScreen
import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class TemplateFolder : AutoTooltippedItem(Properties().stacksTo(1).tab(CreativeTabs.Templates.itemGroup)) {
    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = player.getItemInHand(hand)

        if (world.isClientSide) {
            Minecraft.getInstance().setScreen(UseTemplateFolderScreen())
        }

        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide)
    }
}
