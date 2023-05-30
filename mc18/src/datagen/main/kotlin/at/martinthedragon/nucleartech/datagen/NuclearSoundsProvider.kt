package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.item.NTechItems
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.SoundDefinition
import net.minecraftforge.common.data.SoundDefinitionsProvider

class NuclearSoundsProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : SoundDefinitionsProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Sounds"

    override fun registerSounds() {
        add(NTechSoundsCore.anvilFall, definition().with(sound(ntm("block/anvil_fall_berserk"))).subtitle(LangKeys.SUBTITLE_ANVIL_FALL))
        add(NTechSoundsCore.assemblerOperate, definition().with(sound(ntm("block/assembler/assembler_operate"))).subtitle(
            LangKeys.SUBTITLE_ASSEMBLER_OPERATE))
        add(NTechSoundsCore.chemPlantOperate, definition().with(sound(ntm("block/chem_plant/chem_plant_operate"))).subtitle(
            LangKeys.SUBTITLE_CHEM_PLANT_OPERATE))
        add(NTechSoundsCore.centrifugeOperate, definition().with(sound(ntm("block/centrifuge/operate"))).subtitle(LangKeys.SUBTITLE_CENTRIFUGE_OPERATE))
        add(NTechSoundsCore.debris, definition().with(sound(ntm("block/debris1")), sound(ntm("block/debris2")), sound(ntm("block/debris3"))).subtitle(
            LangKeys.SUBTITLE_BLOCK_DEBRIS))
        add(NTechSoundsCore.emptyIVBag, definition().with(sound(ntm("item/use/radaway"))).subtitle(LangKeys.SUBTITLE_USE_RADAWAY))
        add(NTechSoundsCore.geiger1, definition().with(sound(ntm("geiger/geiger1"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSoundsCore.geiger2, definition().with(sound(ntm("geiger/geiger2"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSoundsCore.geiger3, definition().with(sound(ntm("geiger/geiger3"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSoundsCore.geiger4, definition().with(sound(ntm("geiger/geiger4"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSoundsCore.geiger5, definition().with(sound(ntm("geiger/geiger5"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSoundsCore.geiger6, definition().with(sound(ntm("geiger/geiger6"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(NTechSoundsCore.inject, definition().with(sound(ntm("item/use/inject"))).subtitle(LangKeys.SUBTITLE_ITEM_INJECT))
        add(NTechSoundsCore.installUpgrade, definition().with(sound(ntm("item/install_upgrade"))).subtitle(LangKeys.SUBTITLE_UPGRADE_INSTALL))
        add(NTechSoundsCore.miniNukeExplosion, definition().with(sound(ntm("weapon/mini_nuke_explosion"))).subtitle(LangKeys.SUBTITLE_MINI_NUKE_EXPLODE))
        add(NTechSoundsCore.missileTakeoff, definition().with(sound(ntm("weapon/missile_takeoff"))).subtitle(LangKeys.SUBTITLE_MISSILE_TAKEOFF))
        add(NTechSoundsCore.pressOperate, definition().with(sound(ntm("block/press/press_operate"))).subtitle(LangKeys.SUBTITLE_PRESS_OPERATE))
        add(NTechSoundsCore.randomBleep, definition().with(sound(ntm("random/bleep"))).subtitle(LangKeys.SUBTITLE_BLEEP))
        add(NTechSoundsCore.randomBoop, definition().with(sound(ntm("random/boop"))).subtitle(LangKeys.SUBTITLE_BOOP))
        add(NTechSoundsCore.randomUnpack, definition().with(sound(ntm("random/unpack1")), sound(ntm("random/unpack2"))).subtitle(
            LangKeys.SUBTITLE_ITEM_UNPACK))
        add(NTechSoundsCore.rbmkAz5Cover, definition().with(sound(ntm("block/rbmk_az5_cover"))).subtitle(LangKeys.SUBTITLE_RBMK_AZ5_COVER))
        add(NTechSoundsCore.rbmkExplosion, definition().with(sound(ntm("block/rbmk_explosion")).stream()).subtitle(LangKeys.SUBTITLE_RBMK_EXPLODE))
        add(NTechSoundsCore.rbmkShutdown, definition().with(sound(ntm("block/rbmk_shutdown")).stream()).subtitle(LangKeys.SUBTITLE_RBMK_SHUTDOWN))
        add(NTechSoundsCore.meteorImpact, definition().with(sound(ntm("entity/meteor_impact"))).subtitle(LangKeys.SUBTITLE_METEOR_IMPACT))
        add(NTechSoundsCore.sirenTrackAMSSiren, definition().with(sound(ntm("block/siren/ams")).attenuationDistance(NTechItems.sirenTrackAMSSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_AMS))
        add(NTechSoundsCore.sirenTrackAPCPass, definition().with(sound(ntm("block/siren/apc_pass")).attenuationDistance(NTechItems.sirenTrackAPCPass.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_APC_PASS))
        add(NTechSoundsCore.sirenTrackAPCSiren, definition().with(sound(ntm("block/siren/apc")).attenuationDistance(NTechItems.sirenTrackAPCSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_APC))
        add(NTechSoundsCore.sirenTrackAirRaidSiren, definition().with(sound(ntm("block/siren/air_raid")).attenuationDistance(NTechItems.sirenTrackAirRaidSiren.get().range).stream()).subtitle(
            LangKeys.SUBTITLE_SIREN_AIR_RAID))
        add(NTechSoundsCore.sirenTrackAutopilotDisconnected, definition().with(sound(ntm("block/siren/autopilot_disconnected")).attenuationDistance(NTechItems.sirenTrackAutopilotDisconnected.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_AUTOPILOT_DISCONNECTED))
        add(NTechSoundsCore.sirenTrackBankAlarm, definition().with(sound(ntm("block/siren/bank")).attenuationDistance(NTechItems.sirenTrackBankAlarm.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_BANK))
        add(NTechSoundsCore.sirenTrackBeepSiren, definition().with(sound(ntm("block/siren/beep")).attenuationDistance(NTechItems.sirenTrackBeepSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_BEEP))
        add(NTechSoundsCore.sirenTrackBlastDoorAlarm, definition().with(sound(ntm("block/siren/blast_door")).attenuationDistance(NTechItems.sirenTrackBlastDoorAlarm.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_BLAST_DOOR))
        add(NTechSoundsCore.sirenTrackClassicSiren, definition().with(sound(ntm("block/siren/classic")).attenuationDistance(NTechItems.sirenTrackClassicSiren.get().range).stream()).subtitle(
            LangKeys.SUBTITLE_SIREN_CLASSIC))
        add(NTechSoundsCore.sirenTrackContainerAlarm, definition().with(sound(ntm("block/siren/container")).attenuationDistance(NTechItems.sirenTrackContainerAlarm.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_CONTAINER))
        add(NTechSoundsCore.sirenTrackEASAlarmScreech, definition().with(sound(ntm("block/siren/eas")).attenuationDistance(NTechItems.sirenTrackEASAlarmScreech.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_EAS))
        add(NTechSoundsCore.sirenTrackHatchSiren, definition().with(sound(ntm("block/siren/hatch")).attenuationDistance(NTechItems.sirenTrackHatchSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_HATCH))
        add(NTechSoundsCore.sirenTrackKlaxon, definition().with(sound(ntm("block/siren/klaxon")).attenuationDistance(NTechItems.sirenTrackKlaxon.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_KLAXON))
        add(NTechSoundsCore.sirenTrackMissileSiloSiren, definition().with(sound(ntm("block/siren/missile_silo")).attenuationDistance(NTechItems.sirenTrackMissileSiloSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_MISSILE_SILO))
        add(NTechSoundsCore.sirenTrackNostromoSelfDestruct, definition().with(sound(ntm("block/siren/nostromo_self_destruct")).attenuationDistance(NTechItems.sirenTrackNostromoSelfDestruct.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_NOSTROMO_SELF_DESTRUCT))
        add(NTechSoundsCore.sirenTrackRazortrainHorn, definition().with(sound(ntm("block/siren/razortrain_horn")).attenuationDistance(NTechItems.sirenTrackRazortrainHorn.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_RAZORTRAIN_HORN))
        add(NTechSoundsCore.sirenTrackSecurityAlert, definition().with(sound(ntm("block/siren/security")).attenuationDistance(NTechItems.sirenTrackSecurityAlert.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_SECURITY))
        add(NTechSoundsCore.sirenTrackStandardSiren, definition().with(sound(ntm("block/siren/standard")).attenuationDistance(NTechItems.sirenTrackStandardSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_STANDARD))
        add(NTechSoundsCore.sirenTrackSweepSiren, definition().with(sound(ntm("block/siren/sweep")).attenuationDistance(NTechItems.sirenTrackSweepSiren.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_SWEEP))
        add(NTechSoundsCore.sirenTrackVaultDoorAlarm, definition().with(sound(ntm("block/siren/vault_door")).attenuationDistance(NTechItems.sirenTrackVaultDoorAlarm.get().range)).subtitle(
            LangKeys.SUBTITLE_SIREN_VAULT_DOOR))
    }

    private fun SoundDefinition.subtitle(key: TranslationKey) = subtitle(key.key)
}
