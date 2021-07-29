package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.SOUNDS
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.fml.RegistryObject

object SoundEvents {
    val sirenTrackHatchSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.hatch") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.hatch")) }
    val sirenTrackAutopilotDisconnected: RegistryObject<SoundEvent> = SOUNDS.register("siren.autopilot_disconnected") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.autopilot_disconnected")) }
    val sirenTrackAMSSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.ams") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.ams")) }
    val sirenTrackBlastDoorAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.blast_door") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.blast_door")) }
    val sirenTrackAPCSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.apc") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.apc")) }
    val sirenTrackKlaxon: RegistryObject<SoundEvent> = SOUNDS.register("siren.klaxon") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.klaxon")) }
    val sirenTrackVaultDoorAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.vault_door") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.vault_door")) }
    val sirenTrackSecurityAlert: RegistryObject<SoundEvent> = SOUNDS.register("siren.security") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.security")) }
    val sirenTrackStandardSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.standard") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.standard")) }
    val sirenTrackClassicSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.classic") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.classic")) }
    val sirenTrackBankAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.bank") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.bank")) }
    val sirenTrackBeepSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.beep") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.beep")) }
    val sirenTrackContainerAlarm: RegistryObject<SoundEvent> = SOUNDS.register("siren.container") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.container")) }
    val sirenTrackSweepSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.sweep") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.sweep")) }
    val sirenTrackMissileSiloSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.missile_silo") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.missile_silo")) }
    val sirenTrackAirRaidSiren: RegistryObject<SoundEvent> = SOUNDS.register("siren.air_raid") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.air_raid")) }
    val sirenTrackNostromoSelfDestruct: RegistryObject<SoundEvent> = SOUNDS.register("siren.nostromo_self_destruct") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.nostromo_self_destruct")) }
    val sirenTrackEASAlarmScreech: RegistryObject<SoundEvent> = SOUNDS.register("siren.eas") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.eas")) }
    val sirenTrackAPCPass: RegistryObject<SoundEvent> = SOUNDS.register("siren.apc_pass") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.apc_pass")) }
    val sirenTrackRazortrainHorn: RegistryObject<SoundEvent> = SOUNDS.register("siren.razortrain_horn") { SoundEvent(ResourceLocation(NuclearTech.MODID, "siren.razortrain_horn")) }
    val pressOperate: RegistryObject<SoundEvent> = SOUNDS.register("press.operate") { SoundEvent(ResourceLocation(NuclearTech.MODID, "press.operate")) }
    val randomBleep: RegistryObject<SoundEvent> = SOUNDS.register("random.bleep") { SoundEvent(ResourceLocation(NuclearTech.MODID, "random.bleep")) }
}
