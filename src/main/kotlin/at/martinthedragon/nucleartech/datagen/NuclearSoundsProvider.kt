package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.ModItems
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
        add(SoundEvents.sirenTrackHatchSiren, definition().with(sound(ntm("blocks/siren/hatch"), SOUND).attenuationDistance(ModItems.sirenTrackHatchSiren.get().range)).subtitle(ntmSubtitle("siren.hatch")))
        add(SoundEvents.sirenTrackAutopilotDisconnected, definition().with(sound(ntm("blocks/siren/autopilot_disconnected"), SOUND).attenuationDistance(ModItems.sirenTrackAutopilotDisconnected.get().range)).subtitle(ntmSubtitle("siren.autopilot_disconnected")))
        add(SoundEvents.sirenTrackAMSSiren, definition().with(sound(ntm("blocks/siren/ams"), SOUND).attenuationDistance(ModItems.sirenTrackAMSSiren.get().range)).subtitle(ntmSubtitle("siren.ams")))
        add(SoundEvents.sirenTrackBlastDoorAlarm, definition().with(sound(ntm("blocks/siren/blast_door"), SOUND).attenuationDistance(ModItems.sirenTrackBlastDoorAlarm.get().range)).subtitle(ntmSubtitle("siren.blast_door")))
        add(SoundEvents.sirenTrackAPCSiren, definition().with(sound(ntm("blocks/siren/apc"), SOUND).attenuationDistance(ModItems.sirenTrackAPCSiren.get().range)).subtitle(ntmSubtitle("siren.apc")))
        add(SoundEvents.sirenTrackKlaxon, definition().with(sound(ntm("blocks/siren/klaxon"), SOUND).attenuationDistance(ModItems.sirenTrackKlaxon.get().range)).subtitle(ntmSubtitle("siren.klaxon")))
        add(SoundEvents.sirenTrackVaultDoorAlarm, definition().with(sound(ntm("blocks/siren/vault_door"), SOUND).attenuationDistance(ModItems.sirenTrackVaultDoorAlarm.get().range)).subtitle(ntmSubtitle("siren.vault_door")))
        add(SoundEvents.sirenTrackSecurityAlert, definition().with(sound(ntm("blocks/siren/security"), SOUND).attenuationDistance(ModItems.sirenTrackSecurityAlert.get().range)).subtitle(ntmSubtitle("siren.security")))
        add(SoundEvents.sirenTrackStandardSiren, definition().with(sound(ntm("blocks/siren/standard"), SOUND).attenuationDistance(ModItems.sirenTrackStandardSiren.get().range)).subtitle(ntmSubtitle("siren.standard")))
        add(SoundEvents.sirenTrackClassicSiren, definition().with(sound(ntm("blocks/siren/classic"), SOUND).attenuationDistance(ModItems.sirenTrackClassicSiren.get().range).stream()).subtitle(ntmSubtitle("siren.classic")))
        add(SoundEvents.sirenTrackBankAlarm, definition().with(sound(ntm("blocks/siren/bank"), SOUND).attenuationDistance(ModItems.sirenTrackBankAlarm.get().range)).subtitle(ntmSubtitle("siren.bank")))
        add(SoundEvents.sirenTrackBeepSiren, definition().with(sound(ntm("blocks/siren/beep"), SOUND).attenuationDistance(ModItems.sirenTrackBeepSiren.get().range)).subtitle(ntmSubtitle("siren.beep")))
        add(SoundEvents.sirenTrackContainerAlarm, definition().with(sound(ntm("blocks/siren/container"), SOUND).attenuationDistance(ModItems.sirenTrackContainerAlarm.get().range)).subtitle(ntmSubtitle("siren.container")))
        add(SoundEvents.sirenTrackSweepSiren, definition().with(sound(ntm("blocks/siren/sweep"), SOUND).attenuationDistance(ModItems.sirenTrackSweepSiren.get().range)).subtitle(ntmSubtitle("siren.sweep")))
        add(SoundEvents.sirenTrackMissileSiloSiren, definition().with(sound(ntm("blocks/siren/missile_silo"), SOUND).attenuationDistance(ModItems.sirenTrackMissileSiloSiren.get().range)).subtitle(ntmSubtitle("siren.missile_silo")))
        add(SoundEvents.sirenTrackAirRaidSiren, definition().with(sound(ntm("blocks/siren/air_raid"), SOUND).attenuationDistance(ModItems.sirenTrackAirRaidSiren.get().range).stream()).subtitle(ntmSubtitle("siren.air_raid")))
        add(SoundEvents.sirenTrackNostromoSelfDestruct, definition().with(sound(ntm("blocks/siren/nostromo_self_destruct"), SOUND).attenuationDistance(ModItems.sirenTrackNostromoSelfDestruct.get().range)).subtitle(ntmSubtitle("siren.nostromo_self_destruct")))
        add(SoundEvents.sirenTrackEASAlarmScreech, definition().with(sound(ntm("blocks/siren/eas"), SOUND).attenuationDistance(ModItems.sirenTrackEASAlarmScreech.get().range)).subtitle(ntmSubtitle("siren.eas")))
        add(SoundEvents.sirenTrackAPCPass, definition().with(sound(ntm("blocks/siren/apc_pass"), SOUND).attenuationDistance(ModItems.sirenTrackAPCPass.get().range)).subtitle(ntmSubtitle("siren.apc_pass")))
        add(SoundEvents.sirenTrackRazortrainHorn, definition().with(sound(ntm("blocks/siren/razortrain_horn"), SOUND).attenuationDistance(ModItems.sirenTrackRazortrainHorn.get().range)).subtitle(ntmSubtitle("siren.razortrain_horn")))
        add(SoundEvents.pressOperate, definition().with(sound(ntm("blocks/press/press_operate"), SOUND)).subtitle(ntmSubtitle("press.operate")))
        add(SoundEvents.randomBleep, definition().with(sound(ntm("random/bleep"), SOUND)).subtitle(ntmSubtitle("random.bleep")))
    }

    private fun ntm(location: String) = ResourceLocation(NuclearTech.MODID, location)
    private fun ntmSubtitle(subtitle: String) = "subtitle.${NuclearTech.MODID}.$subtitle"
}
