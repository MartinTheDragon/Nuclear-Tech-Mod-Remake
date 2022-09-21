package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.SOUNDS
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.RegistryObject

object SoundEvents {
    val sirenTrackHatchSiren = register("siren.hatch")
    val sirenTrackAutopilotDisconnected = register("siren.autopilot_disconnected")
    val sirenTrackAMSSiren = register("siren.ams")
    val sirenTrackBlastDoorAlarm = register("siren.blast_door")
    val sirenTrackAPCSiren = register("siren.apc")
    val sirenTrackKlaxon = register("siren.klaxon")
    val sirenTrackVaultDoorAlarm = register("siren.vault_door")
    val sirenTrackSecurityAlert = register("siren.security")
    val sirenTrackStandardSiren = register("siren.standard")
    val sirenTrackClassicSiren = register("siren.classic")
    val sirenTrackBankAlarm = register("siren.bank")
    val sirenTrackBeepSiren = register("siren.beep")
    val sirenTrackContainerAlarm = register("siren.container")
    val sirenTrackSweepSiren = register("siren.sweep")
    val sirenTrackMissileSiloSiren = register("siren.missile_silo")
    val sirenTrackAirRaidSiren = register("siren.air_raid")
    val sirenTrackNostromoSelfDestruct = register("siren.nostromo_self_destruct")
    val sirenTrackEASAlarmScreech = register("siren.eas")
    val sirenTrackAPCPass = register("siren.apc_pass")
    val sirenTrackRazortrainHorn = register("siren.razortrain_horn")
    val anvilFall = register("anvil.fall")
    val pressOperate = register("press.operate")
    val assemblerOperate = register("assembler.operate")
    val chemPlantOperate = register("chem_plant.operate")
    val debris = register("block.debris")
    val installUpgrade = register("item.upgrade.install")
    val inject = register("item.use.inject")
    val emptyIVBag = register("item.use.radaway")
    val randomBleep = register("random.bleep")
    val randomBoop = register("random.boop")
    val randomUnpack = register("random.unpack")
    val geiger1 = register("geiger.geiger1")
    val geiger2 = register("geiger.geiger2")
    val geiger3 = register("geiger.geiger3")
    val geiger4 = register("geiger.geiger4")
    val geiger5 = register("geiger.geiger5")
    val geiger6 = register("geiger.geiger6")
    val missileTakeoff = register("weapon.missile_takeoff")
    val miniNukeExplosion = register("weapon.mini_nuke_explosion")

    private fun register(name: String): RegistryObject<SoundEvent> = SOUNDS.register(name) { SoundEvent(ntm(name)) }
}
