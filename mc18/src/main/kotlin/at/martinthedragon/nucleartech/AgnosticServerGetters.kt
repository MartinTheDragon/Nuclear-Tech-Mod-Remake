// use with DistExecutor

package at.martinthedragon.nucleartech

import net.minecraft.server.MinecraftServer
import net.minecraft.world.item.crafting.RecipeManager
import net.minecraftforge.common.util.LogicalSidedProvider
import net.minecraftforge.fml.LogicalSide

fun getRecipeManagerServer(): RecipeManager? {
    val server = LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER) as? MinecraftServer ?: return null
    return server.serverResources.managers.recipeManager
}
