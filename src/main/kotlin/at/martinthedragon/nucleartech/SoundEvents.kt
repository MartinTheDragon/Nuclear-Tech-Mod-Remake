package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.SOUNDS
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.RegistryObject

object SoundEvents {
    val sirenTrackHatchSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.hatch") { SoundEvent(modLoc("siren.hatch")) }
    val sirenTrackAutopilotDisconnected: RegistryObject<SoundEvent> = SOUNDS.register("siren.autopilot_disconnected") { SoundEvent(modLoc("siren.autopilot_disconnected")) }
    val sirenTrackAMSSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.ams") { SoundEvent(modLoc("siren.ams")) }
    val sirenTrackBlastDoorAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.blast_door") { SoundEvent(modLoc("siren.blast_door")) }
    val sirenTrackAPCSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.apc") { SoundEvent(modLoc("siren.apc")) }
    val sirenTrackKlaxon: RegistryObject<SoundEvent> = SOUNDS.register("siren.klaxon") { SoundEvent(modLoc("siren.klaxon")) }
    val sirenTrackVaultDoorAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.vault_door") { SoundEvent(modLoc("siren.vault_door")) }
    val sirenTrackSecurityAlert: RegistryObject<SoundEvent> = SOUNDS.register("siren.security") { SoundEvent(modLoc("siren.security")) }
    val sirenTrackStandardSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.standard") { SoundEvent(modLoc("siren.standard")) }
    val sirenTrackClassicSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.classic") { SoundEvent(modLoc("siren.classic")) }
    val sirenTrackBankAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.bank") { SoundEvent(modLoc("siren.bank")) }
    val sirenTrackBeepSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.beep") { SoundEvent(modLoc("siren.beep")) }
    val sirenTrackContainerAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.container") { SoundEvent(modLoc("siren.container")) }
    val sirenTrackSweepSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.sweep") { SoundEvent(modLoc("siren.sweep")) }
    val sirenTrackMissileSiloSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.missile_silo") { SoundEvent(modLoc("siren.missile_silo")) }
    val sirenTrackAirRaidSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.air_raid") { SoundEvent(modLoc("siren.air_raid")) }
    val sirenTrackNostromoSelfDestruct: RegistryObject<SoundEvent> = SOUNDS.register("siren.nostromo_self_destruct") { SoundEvent(modLoc("siren.nostromo_self_destruct")) }
    val sirenTrackEASAlarmScreech: RegistryObject<SoundEvent> = SOUNDS.register("siren.eas") { SoundEvent(modLoc("siren.eas")) }
    val sirenTrackAPCPass: RegistryObject<SoundEvent> = SOUNDS.register("siren.apc_pass") { SoundEvent(modLoc("siren.apc_pass")) }
    val sirenTrackRazortrainHorn: RegistryObject<SoundEvent> = SOUNDS.register("siren.razortrain_horn") { SoundEvent(modLoc("siren.razortrain_horn")) }
    val anvilFall: RegistryObject<SoundEvent> = SOUNDS.register("anvil.fall") { SoundEvent(modLoc("anvil.fall")) }
    val pressOperate: RegistryObject<SoundEvent> = SOUNDS.register("press.operate") { SoundEvent(modLoc("press.operate")) }
    val inject: RegistryObject<SoundEvent> = SOUNDS.register("item.use.inject") { SoundEvent(modLoc("item.use.inject")) }
    val emptyIVBag: RegistryObject<SoundEvent> = SOUNDS.register("item.use.radaway") { SoundEvent(modLoc("item.use.radaway")) }
    val randomBleep: RegistryObject<SoundEvent> = SOUNDS.register("random.bleep") { SoundEvent(modLoc("random.bleep")) }
    val randomBoop: RegistryObject<SoundEvent> = SOUNDS.register("random.boop") { SoundEvent(modLoc("random.boop")) }
    val randomUnpack: RegistryObject<SoundEvent> = SOUNDS.register("random.unpack") { SoundEvent(modLoc("random.unpack")) }
    val geiger1: RegistryObject<SoundEvent> = SOUNDS.register("geiger.geiger1") { SoundEvent(modLoc("geiger.geiger1")) }
    val geiger2: RegistryObject<SoundEvent> = SOUNDS.register("geiger.geiger2") { SoundEvent(modLoc("geiger.geiger2")) }
    val geiger3: RegistryObject<SoundEvent> = SOUNDS.register("geiger.geiger3") { SoundEvent(modLoc("geiger.geiger3")) }
    val geiger4: RegistryObject<SoundEvent> = SOUNDS.register("geiger.geiger4") { SoundEvent(modLoc("geiger.geiger4")) }
    val geiger5: RegistryObject<SoundEvent> = SOUNDS.register("geiger.geiger5") { SoundEvent(modLoc("geiger.geiger5")) }
    val geiger6: RegistryObject<SoundEvent> = SOUNDS.register("geiger.geiger6") { SoundEvent(modLoc("geiger.geiger6")) }
    val miniNukeExplosion: RegistryObject<SoundEvent> = SOUNDS.register("weapon.mini_nuke_explosion") { SoundEvent(modLoc("weapon.mini_nuke_explosion")) }

    private fun modLoc(location: String) = ntm(location)
}
