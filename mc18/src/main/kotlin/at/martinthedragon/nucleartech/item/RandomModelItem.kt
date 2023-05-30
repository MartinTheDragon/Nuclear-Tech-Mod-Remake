package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.model.RandomModelLoader
import net.minecraft.world.level.ItemLike
import net.minecraftforge.registries.RegistryObject

interface RandomModelItem : ItemLike {
    val id: Int

    fun chooseId(max: Int): Int
}

fun RandomModelLoader.setIdSupplier(randomModelItem: RegistryObject<out RandomModelItem>) {
    setIdSupplier(randomModelItem.id, randomModelItem.get()::chooseId)
}
