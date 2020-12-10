package at.martinthedragon.ntm

import at.martinthedragon.ntm.capabilites.CapabilityIrradiationHandler
import at.martinthedragon.ntm.capabilites.IIrradiationHandlerModifiable
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.merchant.villager.VillagerEntity
import net.minecraft.entity.monster.CreeperEntity
import net.minecraft.entity.monster.SkeletonEntity
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.entity.monster.ZombieVillagerEntity
import net.minecraft.entity.passive.CowEntity
import net.minecraft.entity.passive.MooshroomEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.world.World
import net.minecraft.world.server.ChunkHolder
import net.minecraft.world.server.ServerWorld
import java.util.*

object Radiation {
    var irradiatedEntityList = listOf<LivingEntity>()

    fun addEntityIrradiation(entity: LivingEntity, radiation: Float) {
        if (entity.shouldBeDead) return
        @Suppress("ThrowableNotThrown")
        val cap = entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).orElseThrow { RuntimeException() }
        if (cap !is IIrradiationHandlerModifiable) throw RuntimeException("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        val newIrradiation = (cap.getIrradiation() + radiation).coerceAtLeast(0f)
        cap.setIrradiation(newIrradiation)
    }

    fun setEntityIrradiation(entity: LivingEntity, radiation: Float) {
        if (entity.shouldBeDead) return
        @Suppress("ThrowableNotThrown")
        val cap = entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).orElseThrow { RuntimeException() }
        if (cap !is IIrradiationHandlerModifiable) throw RuntimeException("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        val newIrradiation = radiation.coerceAtLeast(0f)
        cap.setIrradiation(newIrradiation)
    }

    fun getEntityIrradiation(entity: LivingEntity): Float {
        if (entity.shouldBeDead) return 0f
        @Suppress("ThrowableNotThrown")
        val cap = entity.getCapability(CapabilityIrradiationHandler.irradiationHandlerCapability).orElseThrow { RuntimeException() }
        if (cap !is IIrradiationHandlerModifiable) throw RuntimeException("LivingEntity ${entity.name} hasn't gotten a modifiable IIrradiationHandler")
        return cap.getIrradiation()
    }

    fun applyRadiationEffects(world: World) {
        if (!world.isRemote) {
            // 3000 IQ strategy to get irradiated entities without causing ConcurrentModificationException follows:
            if (world.gameTime % 20 == 0L) { // polling rate
                val chunkManager = (world as ServerWorld).chunkProvider.chunkManager
                val getLoadedChunksIterableMethod = chunkManager.javaClass.getDeclaredMethod("getLoadedChunksIterable")
                getLoadedChunksIterableMethod.isAccessible = true
                @Suppress("UNCHECKED_CAST")
                irradiatedEntityList = (getLoadedChunksIterableMethod(chunkManager) as Iterable<ChunkHolder>)
                    .mapNotNull { it.chunkIfComplete }
                    .flatMap { chunk -> chunk.entityLists.flatMap { it } }
                    .filterIsInstance<LivingEntity>()
                    .filter { getEntityIrradiation(it) > 0 }
            }
            loop@ for (entity in irradiatedEntityList) {
                val irradiation = getEntityIrradiation(entity)
                when {
                    entity is CreeperEntity && irradiation >= 200 && !entity.shouldBeDead -> {
                        if (world.random.nextInt(3) == 0) {
                            // TODO spawn nuclear creeper
                        } else
                            entity.attackEntityFrom(DamageSources.radiation, 100f)
                        continue@loop
                    }
                    entity is CowEntity && entity !is MooshroomEntity && irradiation >= 50 -> {
                        val creep = MooshroomEntity(EntityType.MOOSHROOM, world)
                        creep.isChild = entity.isChild
                        creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch)
                        if (!entity.shouldBeDead && !world.isRemote)
                            world.addEntity(creep)
                        entity.remove()
                        continue@loop
                    }
                    entity is VillagerEntity && irradiation >= 500 -> {
                        val creep = ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, world)
                        creep.isChild = entity.isChild
                        creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch)
                        if (!entity.shouldBeDead && !world.isRemote)
                            world.addEntity(creep)
                        entity.remove()
                        continue@loop
                    }
                }

                // TODO add nuclear creeper to exclusions
                if (irradiation < 200 || entity is MooshroomEntity || entity is ZombieEntity || entity is SkeletonEntity)
                    continue

                if (irradiation > 2500)
                    setEntityIrradiation(entity, 2500f)

                if (entity is PlayerEntity && entity.isCreative)
                    continue

                when {
                    irradiation >= 1000 -> {
                        entity.attackEntityFrom(DamageSources.radiation, entity.maxHealth * 100)
                        setEntityIrradiation(entity, 0f)
                    }
                    irradiation >= 800 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.NAUSEA, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.SLOWNESS, 10 * 20, 2))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.WEAKNESS, 10 * 20, 2))
                        if (world.random.nextInt(500) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.POISON, 3 * 20, 2))
                        if (world.random.nextInt(700) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.WITHER, 3 * 20, 1))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.HUNGER, 5 * 20, 3))
                    }
                    irradiation >= 600 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.NAUSEA, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.SLOWNESS, 10 * 20, 2))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.WEAKNESS, 10 * 20, 2))
                        if (world.random.nextInt(500) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.POISON, 3 * 20, 1))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.HUNGER, 3 * 20, 3))
                    }
                    irradiation >= 400 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.NAUSEA, 5 * 20))
                        if (world.random.nextInt(500) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.SLOWNESS, 5 * 20))
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.WEAKNESS, 5 * 20,1))
                        if (world.random.nextInt(500) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.HUNGER, 3 * 20, 2))
                    }
                    irradiation >= 200 -> {
                        if (world.random.nextInt(300) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.NAUSEA, 5 * 20))
                        if (world.random.nextInt(500) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.WEAKNESS, 5 * 20))
                        if (world.random.nextInt(700) == 0)
                            entity.addPotionEffect(EffectInstance(Effects.HUNGER, 3 * 20, 2))
                    }
                }
            }
        }
    }
}
