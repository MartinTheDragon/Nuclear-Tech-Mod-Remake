package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.SOUNDS
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.RegistryObject

object SoundEvents {
    val anvilFall = register("anvil.fall")
    val assemblerOperate = register("assembler.operate")
    val chemPlantOperate = register("chem_plant.operate")
    val centrifugeOperate = register("centrifuge.operate")
    val debris = register("block.debris")
    val emptyIVBag = register("item.use.radaway")
    val geiger1 = register("geiger.geiger1")
    val geiger2 = register("geiger.geiger2")
    val geiger3 = register("geiger.geiger3")
    val geiger4 = register("geiger.geiger4")
    val geiger5 = register("geiger.geiger5")
    val geiger6 = register("geiger.geiger6")
    val inject = register("item.use.inject")
    val installUpgrade = register("item.upgrade.install")
    val miniNukeExplosion = register("weapon.mini_nuke_explosion")
    val missileTakeoff = register("weapon.missile_takeoff")
    val pressOperate = register("press.operate")
    val randomBleep = register("random.bleep")
    val randomBoop = register("random.boop")
    val randomUnpack = register("random.unpack")
    val rbmkAz5Cover = register("block.rbmk.az5_cover")
    val rbmkExplosion = register("block.rbmk.explosion")
    val rbmkShutdown = register("block.rbmk.shutdown")
    val meteorImpact = register("entity.meteor.impact")
    val sirenTrackAMSSiren = register("siren.ams")
    val sirenTrackAPCPass = register("siren.apc_pass")
    val sirenTrackAPCSiren = register("siren.apc")
    val sirenTrackAirRaidSiren = register("siren.air_raid")
    val sirenTrackAutopilotDisconnected = register("siren.autopilot_disconnected")
    val sirenTrackBankAlarm = register("siren.bank")
    val sirenTrackBeepSiren = register("siren.beep")
    val sirenTrackBlastDoorAlarm = register("siren.blast_door")
    val sirenTrackClassicSiren = register("siren.classic")
    val sirenTrackContainerAlarm = register("siren.container")
    val sirenTrackEASAlarmScreech = register("siren.eas")
    val sirenTrackHatchSiren = register("siren.hatch")
    val sirenTrackKlaxon = register("siren.klaxon")
    val sirenTrackMissileSiloSiren = register("siren.missile_silo")
    val sirenTrackNostromoSelfDestruct = register("siren.nostromo_self_destruct")
    val sirenTrackRazortrainHorn = register("siren.razortrain_horn")
    val sirenTrackSecurityAlert = register("siren.security")
    val sirenTrackStandardSiren = register("siren.standard")
    val sirenTrackSweepSiren = register("siren.sweep")
    val sirenTrackVaultDoorAlarm = register("siren.vault_door")

    private fun register(name: String): RegistryObject<SoundEvent> = SOUNDS.register(name) { SoundEvent(ntm(name)) }
}
