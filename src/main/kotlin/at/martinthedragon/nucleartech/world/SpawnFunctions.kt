package at.martinthedragon.nucleartech.world

import net.minecraft.entity.item.ExperienceOrbEntity
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.World
import kotlin.math.floor
import kotlin.random.Random

fun World.dropExperience(pos: Vector3d, amount: Float) {
    if (amount < 0F) throw IllegalArgumentException("Cannot drop anti-experience")

    var integerValue = floor(amount).toInt()
    val decimalValue = amount - integerValue
    if (decimalValue != 0F && Random.nextFloat() < decimalValue) integerValue++

    while (integerValue > 0) {
        val experienceDropped = ExperienceOrbEntity.getExperienceValue(integerValue)
        integerValue -= experienceDropped
        addFreshEntity(ExperienceOrbEntity(this, pos.x, pos.y, pos.z, experienceDropped))
    }
}
