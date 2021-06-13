package at.martinthedragon.ntm.items

import at.martinthedragon.ntm.CreativeTabs
import at.martinthedragon.ntm.screens.UseTemplateFolderScreen
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class TemplateFolder : AutoTooltippedItem(Properties().stacksTo(1).tab(CreativeTabs.Templates.itemGroup)) {
    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val itemStack = player.getItemInHand(hand)

        if (world.isClientSide) {
            Minecraft.getInstance().setScreen(UseTemplateFolderScreen())
        }

        return ActionResult.sidedSuccess(itemStack, world.isClientSide)
    }
}
