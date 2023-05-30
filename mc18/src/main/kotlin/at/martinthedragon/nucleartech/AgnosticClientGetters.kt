// use with DistExecutor

package at.martinthedragon.nucleartech

import net.minecraft.client.Minecraft

fun getRecipeManagerClient() = Minecraft.getInstance().level?.recipeManager
