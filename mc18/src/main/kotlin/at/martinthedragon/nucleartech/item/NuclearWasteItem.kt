package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.entity.WasteItemEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

open class NuclearWasteItem(properties: Properties) : Item(properties) {
    override fun hasCustomEntity(stack: ItemStack) = true
    override fun getEntityLifespan(itemStack: ItemStack, level: Level) = Int.MAX_VALUE
    override fun createEntity(level: Level, location: Entity, stack: ItemStack): Entity {
        val wasteItemEntity = WasteItemEntity(level, location.x, location.y, location.z, stack, location.deltaMovement.x, location.deltaMovement.y, location.deltaMovement.z)
        wasteItemEntity.setPickUpDelay(10)
        return wasteItemEntity
    }
}
