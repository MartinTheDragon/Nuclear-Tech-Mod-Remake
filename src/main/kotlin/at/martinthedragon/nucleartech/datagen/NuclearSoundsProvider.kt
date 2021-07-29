package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.SoundEvents
import net.minecraft.data.DataGenerator
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.SoundDefinition.SoundType.SOUND
import net.minecraftforge.common.data.SoundDefinitionsProvider

class NuclearSoundsProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : SoundDefinitionsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Sounds"

    override fun registerSounds() {
        add(SoundEvents.sirenTrackHatchSiren, definition().with(sound(ntm("blocks/siren/hatch"), SOUND).attenuationDistance(250)).subtitle(ntmSubtitle("siren.playing_hatch")))
        add(SoundEvents.sirenTrackAutopilotDisconnected, definition().with(sound(ntm("blocks/siren/autopilot_disconnected"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_autopilot_disconnected")))
        add(SoundEvents.sirenTrackAMSSiren, definition().with(sound(ntm("blocks/siren/ams"), SOUND).attenuationDistance(100)).subtitle(ntmSubtitle("siren.playing_ams")))
        add(SoundEvents.sirenTrackBlastDoorAlarm, definition().with(sound(ntm("blocks/siren/blast_door"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_blast_door")))
        add(SoundEvents.sirenTrackAPCSiren, definition().with(sound(ntm("blocks/siren/apc"), SOUND).attenuationDistance(100)).subtitle(ntmSubtitle("siren.playing_apc")))
        add(SoundEvents.sirenTrackKlaxon, definition().with(sound(ntm("blocks/siren/klaxon"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_klaxon")))
        add(SoundEvents.sirenTrackVaultDoorAlarm, definition().with(sound(ntm("blocks/siren/vault_door"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_vault_door")))
        add(SoundEvents.sirenTrackSecurityAlert, definition().with(sound(ntm("blocks/siren/security"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_security")))
        add(SoundEvents.sirenTrackStandardSiren, definition().with(sound(ntm("blocks/siren/standard"), SOUND).attenuationDistance(250)).subtitle(ntmSubtitle("siren.playing_standard")))
        add(SoundEvents.sirenTrackClassicSiren, definition().with(sound(ntm("blocks/siren/classic"), SOUND).attenuationDistance(250).stream()).subtitle(ntmSubtitle("siren.playing_classic")))
        add(SoundEvents.sirenTrackBankAlarm, definition().with(sound(ntm("blocks/siren/bank"), SOUND).attenuationDistance(100)).subtitle(ntmSubtitle("siren.playing_bank")))
        add(SoundEvents.sirenTrackBeepSiren, definition().with(sound(ntm("blocks/siren/beep"), SOUND).attenuationDistance(100)).subtitle(ntmSubtitle("siren.playing_beep")))
        add(SoundEvents.sirenTrackContainerAlarm, definition().with(sound(ntm("blocks/siren/container"), SOUND).attenuationDistance(100)).subtitle(ntmSubtitle("siren.playing_container")))
        add(SoundEvents.sirenTrackSweepSiren, definition().with(sound(ntm("blocks/siren/sweep"), SOUND).attenuationDistance(500)).subtitle(ntmSubtitle("siren.playing_sweep")))
        add(SoundEvents.sirenTrackMissileSiloSiren, definition().with(sound(ntm("blocks/siren/missile_silo"), SOUND).attenuationDistance(500)).subtitle(ntmSubtitle("siren.playing_missile_silo")))
        add(SoundEvents.sirenTrackAirRaidSiren, definition().with(sound(ntm("blocks/siren/air_raid"), SOUND).attenuationDistance(1000).stream()).subtitle(ntmSubtitle("siren.playing_air_raid")))
        add(SoundEvents.sirenTrackNostromoSelfDestruct, definition().with(sound(ntm("blocks/siren/nostromo_self_destruct"), SOUND).attenuationDistance(100)).subtitle(ntmSubtitle("siren.playing_nostromo_self_destruct")))
        add(SoundEvents.sirenTrackEASAlarmScreech, definition().with(sound(ntm("blocks/siren/eas"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_eas")))
        add(SoundEvents.sirenTrackAPCPass, definition().with(sound(ntm("blocks/siren/apc_pass"), SOUND).attenuationDistance(50)).subtitle(ntmSubtitle("siren.playing_apc_pass")))
        add(SoundEvents.sirenTrackRazortrainHorn, definition().with(sound(ntm("blocks/siren/razortrain_horn"), SOUND).attenuationDistance(250)).subtitle(ntmSubtitle("siren.playing_razortrain_horn")))
        add(SoundEvents.pressOperate, definition().with(sound(ntm("blocks/press/press_operate"), SOUND)).subtitle(ntmSubtitle("press.operate")))
        add(SoundEvents.randomBleep, definition().with(sound(ntm("random/bleep"), SOUND)).subtitle(ntmSubtitle("random.bleep")))
    }

    private fun ntm(location: String) = ResourceLocation(NuclearTech.MODID, location)
    private fun ntmSubtitle(subtitle: String) = "subtitle.${NuclearTech.MODID}.$subtitle"
}
