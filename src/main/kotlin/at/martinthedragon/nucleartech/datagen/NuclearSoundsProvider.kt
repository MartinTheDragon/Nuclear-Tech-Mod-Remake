package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.SoundEvents
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.SoundDefinitionsProvider

class NuclearSoundsProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : SoundDefinitionsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Sounds"

    override fun registerSounds() {
        add(SoundEvents.sirenTrackHatchSiren, definition().with(sound(ntm("blocks/siren/hatch")).attenuationDistance(ModItems.sirenTrackHatchSiren.get().range)).subtitle(ntmSubtitle("siren.hatch")))
        add(SoundEvents.sirenTrackAutopilotDisconnected, definition().with(sound(ntm("blocks/siren/autopilot_disconnected")).attenuationDistance(ModItems.sirenTrackAutopilotDisconnected.get().range)).subtitle(ntmSubtitle("siren.autopilot_disconnected")))
        add(SoundEvents.sirenTrackAMSSiren, definition().with(sound(ntm("blocks/siren/ams")).attenuationDistance(ModItems.sirenTrackAMSSiren.get().range)).subtitle(ntmSubtitle("siren.ams")))
        add(SoundEvents.sirenTrackBlastDoorAlarm, definition().with(sound(ntm("blocks/siren/blast_door")).attenuationDistance(ModItems.sirenTrackBlastDoorAlarm.get().range)).subtitle(ntmSubtitle("siren.blast_door")))
        add(SoundEvents.sirenTrackAPCSiren, definition().with(sound(ntm("blocks/siren/apc")).attenuationDistance(ModItems.sirenTrackAPCSiren.get().range)).subtitle(ntmSubtitle("siren.apc")))
        add(SoundEvents.sirenTrackKlaxon, definition().with(sound(ntm("blocks/siren/klaxon")).attenuationDistance(ModItems.sirenTrackKlaxon.get().range)).subtitle(ntmSubtitle("siren.klaxon")))
        add(SoundEvents.sirenTrackVaultDoorAlarm, definition().with(sound(ntm("blocks/siren/vault_door")).attenuationDistance(ModItems.sirenTrackVaultDoorAlarm.get().range)).subtitle(ntmSubtitle("siren.vault_door")))
        add(SoundEvents.sirenTrackSecurityAlert, definition().with(sound(ntm("blocks/siren/security")).attenuationDistance(ModItems.sirenTrackSecurityAlert.get().range)).subtitle(ntmSubtitle("siren.security")))
        add(SoundEvents.sirenTrackStandardSiren, definition().with(sound(ntm("blocks/siren/standard")).attenuationDistance(ModItems.sirenTrackStandardSiren.get().range)).subtitle(ntmSubtitle("siren.standard")))
        add(SoundEvents.sirenTrackClassicSiren, definition().with(sound(ntm("blocks/siren/classic")).attenuationDistance(ModItems.sirenTrackClassicSiren.get().range).stream()).subtitle(ntmSubtitle("siren.classic")))
        add(SoundEvents.sirenTrackBankAlarm, definition().with(sound(ntm("blocks/siren/bank")).attenuationDistance(ModItems.sirenTrackBankAlarm.get().range)).subtitle(ntmSubtitle("siren.bank")))
        add(SoundEvents.sirenTrackBeepSiren, definition().with(sound(ntm("blocks/siren/beep")).attenuationDistance(ModItems.sirenTrackBeepSiren.get().range)).subtitle(ntmSubtitle("siren.beep")))
        add(SoundEvents.sirenTrackContainerAlarm, definition().with(sound(ntm("blocks/siren/container")).attenuationDistance(ModItems.sirenTrackContainerAlarm.get().range)).subtitle(ntmSubtitle("siren.container")))
        add(SoundEvents.sirenTrackSweepSiren, definition().with(sound(ntm("blocks/siren/sweep")).attenuationDistance(ModItems.sirenTrackSweepSiren.get().range)).subtitle(ntmSubtitle("siren.sweep")))
        add(SoundEvents.sirenTrackMissileSiloSiren, definition().with(sound(ntm("blocks/siren/missile_silo")).attenuationDistance(ModItems.sirenTrackMissileSiloSiren.get().range)).subtitle(ntmSubtitle("siren.missile_silo")))
        add(SoundEvents.sirenTrackAirRaidSiren, definition().with(sound(ntm("blocks/siren/air_raid")).attenuationDistance(ModItems.sirenTrackAirRaidSiren.get().range).stream()).subtitle(ntmSubtitle("siren.air_raid")))
        add(SoundEvents.sirenTrackNostromoSelfDestruct, definition().with(sound(ntm("blocks/siren/nostromo_self_destruct")).attenuationDistance(ModItems.sirenTrackNostromoSelfDestruct.get().range)).subtitle(ntmSubtitle("siren.nostromo_self_destruct")))
        add(SoundEvents.sirenTrackEASAlarmScreech, definition().with(sound(ntm("blocks/siren/eas")).attenuationDistance(ModItems.sirenTrackEASAlarmScreech.get().range)).subtitle(ntmSubtitle("siren.eas")))
        add(SoundEvents.sirenTrackAPCPass, definition().with(sound(ntm("blocks/siren/apc_pass")).attenuationDistance(ModItems.sirenTrackAPCPass.get().range)).subtitle(ntmSubtitle("siren.apc_pass")))
        add(SoundEvents.sirenTrackRazortrainHorn, definition().with(sound(ntm("blocks/siren/razortrain_horn")).attenuationDistance(ModItems.sirenTrackRazortrainHorn.get().range)).subtitle(ntmSubtitle("siren.razortrain_horn")))
        add(SoundEvents.pressOperate, definition().with(sound(ntm("blocks/press/press_operate"))).subtitle(ntmSubtitle("press.operate")))
        add(SoundEvents.randomBleep, definition().with(sound(ntm("random/bleep"))).subtitle(ntmSubtitle("random.bleep")))
        add(SoundEvents.randomBoop, definition().with(sound(ntm("random/boop"))).subtitle(ntmSubtitle("random.boop")))
        add(SoundEvents.randomUnpack, definition().with(sound(ntm("random/unpack1")), sound(ntm("random/unpack2"))).subtitle(ntmSubtitle("random.unpack")))
        add(SoundEvents.geiger1, definition().with(sound(ntm("geiger/geiger1"))).subtitle(ntmSubtitle("geiger.click")))
        add(SoundEvents.geiger2, definition().with(sound(ntm("geiger/geiger2"))).subtitle(ntmSubtitle("geiger.click")))
        add(SoundEvents.geiger3, definition().with(sound(ntm("geiger/geiger3"))).subtitle(ntmSubtitle("geiger.click")))
        add(SoundEvents.geiger4, definition().with(sound(ntm("geiger/geiger4"))).subtitle(ntmSubtitle("geiger.click")))
        add(SoundEvents.geiger5, definition().with(sound(ntm("geiger/geiger5"))).subtitle(ntmSubtitle("geiger.click")))
        add(SoundEvents.geiger6, definition().with(sound(ntm("geiger/geiger6"))).subtitle(ntmSubtitle("geiger.click")))
    }

    private fun ntm(location: String) = ResourceLocation(NuclearTech.MODID, location)
    private fun ntmSubtitle(subtitle: String) = "subtitle.${NuclearTech.MODID}.$subtitle"
}
