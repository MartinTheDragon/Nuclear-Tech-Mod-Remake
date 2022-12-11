package at.martinthedragon.nucleartech.extensions

import com.google.gson.JsonObject
import net.minecraft.world.item.ItemStack

fun ItemStack.toJson() = JsonObject().apply {
    addProperty("item", item.registryName?.toString() ?: throw IllegalStateException("Unregistered item in ItemStack!"))
    if (count != 1) addProperty("count", count)
    if (hasTag()) addProperty("nbt", tag!!.toString())
}
