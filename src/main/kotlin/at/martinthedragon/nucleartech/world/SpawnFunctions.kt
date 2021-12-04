package at.martinthedragon.nucleartech.world

import net.minecraft.world.entity.ExperienceOrb
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import kotlin.math.floor
import kotlin.random.Random

fun Level.dropExperience(pos: Vec3, amount: Float) {
    if (amount < 0F) throw IllegalArgumentException("Cannot drop anti-experience")

    var integerValue = floor(amount).toInt()
    val decimalValue = amount - integerValue
    if (decimalValue != 0F && Random.nextFloat() < decimalValue) integerValue++

    while (integerValue > 0) {
        val experienceDropped = ExperienceOrb.getExperienceValue(integerValue)
        integerValue -= experienceDropped
        addFreshEntity(ExperienceOrb(this, pos.x, pos.y, pos.z, experienceDropped))
    }
}
