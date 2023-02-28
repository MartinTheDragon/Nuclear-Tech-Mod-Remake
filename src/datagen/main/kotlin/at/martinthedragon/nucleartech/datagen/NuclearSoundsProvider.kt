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
        add(SoundEvents.anvilFall, definition().with(sound(ntm("block/anvil_fall_berserk"))).subtitle(LangKeys.SUBTITLE_ANVIL_FALL))
        add(SoundEvents.assemblerOperate, definition().with(sound(ntm("block/assembler/assembler_operate"))).subtitle(LangKeys.SUBTITLE_ASSEMBLER_OPERATE))
        add(SoundEvents.chemPlantOperate, definition().with(sound(ntm("block/chem_plant/chem_plant_operate"))).subtitle(LangKeys.SUBTITLE_CHEM_PLANT_OPERATE))
        add(SoundEvents.centrifugeOperate, definition().with(sound(ntm("block/centrifuge/operate"))).subtitle(LangKeys.SUBTITLE_CENTRIFUGE_OPERATE))
        add(SoundEvents.debris, definition().with(sound(ntm("block/debris1")), sound(ntm("block/debris2")), sound(ntm("block/debris3"))).subtitle(LangKeys.SUBTITLE_BLOCK_DEBRIS))
        add(SoundEvents.emptyIVBag, definition().with(sound(ntm("item/use/radaway"))).subtitle(LangKeys.SUBTITLE_USE_RADAWAY))
        add(SoundEvents.geiger1, definition().with(sound(ntm("geiger/geiger1"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(SoundEvents.geiger2, definition().with(sound(ntm("geiger/geiger2"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(SoundEvents.geiger3, definition().with(sound(ntm("geiger/geiger3"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(SoundEvents.geiger4, definition().with(sound(ntm("geiger/geiger4"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(SoundEvents.geiger5, definition().with(sound(ntm("geiger/geiger5"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(SoundEvents.geiger6, definition().with(sound(ntm("geiger/geiger6"))).subtitle(LangKeys.SUBTITLE_GEIGER_CLICK))
        add(SoundEvents.inject, definition().with(sound(ntm("item/use/inject"))).subtitle(LangKeys.SUBTITLE_ITEM_INJECT))
        add(SoundEvents.installUpgrade, definition().with(sound(ntm("item/install_upgrade"))).subtitle(LangKeys.SUBTITLE_UPGRADE_INSTALL))
        add(SoundEvents.miniNukeExplosion, definition().with(sound(ntm("weapon/mini_nuke_explosion"))).subtitle(LangKeys.SUBTITLE_MINI_NUKE_EXPLODE))
        add(SoundEvents.missileTakeoff, definition().with(sound(ntm("weapon/missile_takeoff"))).subtitle(LangKeys.SUBTITLE_MISSILE_TAKEOFF))
        add(SoundEvents.pressOperate, definition().with(sound(ntm("block/press/press_operate"))).subtitle(LangKeys.SUBTITLE_PRESS_OPERATE))
        add(SoundEvents.randomBleep, definition().with(sound(ntm("random/bleep"))).subtitle(LangKeys.SUBTITLE_BLEEP))
        add(SoundEvents.randomBoop, definition().with(sound(ntm("random/boop"))).subtitle(LangKeys.SUBTITLE_BOOP))
        add(SoundEvents.randomUnpack, definition().with(sound(ntm("random/unpack1")), sound(ntm("random/unpack2"))).subtitle(LangKeys.SUBTITLE_ITEM_UNPACK))
        add(SoundEvents.rbmkAz5Cover, definition().with(sound(ntm("block/rbmk_az5_cover"))).subtitle(LangKeys.SUBTITLE_RBMK_AZ5_COVER))
        add(SoundEvents.rbmkExplosion, definition().with(sound(ntm("block/rbmk_explosion")).stream()).subtitle(LangKeys.SUBTITLE_RBMK_EXPLODE))
        add(SoundEvents.rbmkShutdown, definition().with(sound(ntm("block/rbmk_shutdown")).stream()).subtitle(LangKeys.SUBTITLE_RBMK_SHUTDOWN))
        add(SoundEvents.meteorImpact, definition().with(sound(ntm("entity/meteor_impact"))).subtitle(LangKeys.SUBTITLE_METEOR_IMPACT))
        add(SoundEvents.sirenTrackAMSSiren, definition().with(sound(ntm("block/siren/ams")).attenuationDistance(NTechItems.sirenTrackAMSSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_AMS))
        add(SoundEvents.sirenTrackAPCPass, definition().with(sound(ntm("block/siren/apc_pass")).attenuationDistance(NTechItems.sirenTrackAPCPass.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_APC_PASS))
        add(SoundEvents.sirenTrackAPCSiren, definition().with(sound(ntm("block/siren/apc")).attenuationDistance(NTechItems.sirenTrackAPCSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_APC))
        add(SoundEvents.sirenTrackAirRaidSiren, definition().with(sound(ntm("block/siren/air_raid")).attenuationDistance(NTechItems.sirenTrackAirRaidSiren.get().range).stream()).subtitle(LangKeys.SUBTITLE_SIREN_AIR_RAID))
        add(SoundEvents.sirenTrackAutopilotDisconnected, definition().with(sound(ntm("block/siren/autopilot_disconnected")).attenuationDistance(NTechItems.sirenTrackAutopilotDisconnected.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_AUTOPILOT_DISCONNECTED))
        add(SoundEvents.sirenTrackBankAlarm, definition().with(sound(ntm("block/siren/bank")).attenuationDistance(NTechItems.sirenTrackBankAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_BANK))
        add(SoundEvents.sirenTrackBeepSiren, definition().with(sound(ntm("block/siren/beep")).attenuationDistance(NTechItems.sirenTrackBeepSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_BEEP))
        add(SoundEvents.sirenTrackBlastDoorAlarm, definition().with(sound(ntm("block/siren/blast_door")).attenuationDistance(NTechItems.sirenTrackBlastDoorAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_BLAST_DOOR))
        add(SoundEvents.sirenTrackClassicSiren, definition().with(sound(ntm("block/siren/classic")).attenuationDistance(NTechItems.sirenTrackClassicSiren.get().range).stream()).subtitle(LangKeys.SUBTITLE_SIREN_CLASSIC))
        add(SoundEvents.sirenTrackContainerAlarm, definition().with(sound(ntm("block/siren/container")).attenuationDistance(NTechItems.sirenTrackContainerAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_CONTAINER))
        add(SoundEvents.sirenTrackEASAlarmScreech, definition().with(sound(ntm("block/siren/eas")).attenuationDistance(NTechItems.sirenTrackEASAlarmScreech.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_EAS))
        add(SoundEvents.sirenTrackHatchSiren, definition().with(sound(ntm("block/siren/hatch")).attenuationDistance(NTechItems.sirenTrackHatchSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_HATCH))
        add(SoundEvents.sirenTrackKlaxon, definition().with(sound(ntm("block/siren/klaxon")).attenuationDistance(NTechItems.sirenTrackKlaxon.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_KLAXON))
        add(SoundEvents.sirenTrackMissileSiloSiren, definition().with(sound(ntm("block/siren/missile_silo")).attenuationDistance(NTechItems.sirenTrackMissileSiloSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_MISSILE_SILO))
        add(SoundEvents.sirenTrackNostromoSelfDestruct, definition().with(sound(ntm("block/siren/nostromo_self_destruct")).attenuationDistance(NTechItems.sirenTrackNostromoSelfDestruct.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_NOSTROMO_SELF_DESTRUCT))
        add(SoundEvents.sirenTrackRazortrainHorn, definition().with(sound(ntm("block/siren/razortrain_horn")).attenuationDistance(NTechItems.sirenTrackRazortrainHorn.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_RAZORTRAIN_HORN))
        add(SoundEvents.sirenTrackSecurityAlert, definition().with(sound(ntm("block/siren/security")).attenuationDistance(NTechItems.sirenTrackSecurityAlert.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_SECURITY))
        add(SoundEvents.sirenTrackStandardSiren, definition().with(sound(ntm("block/siren/standard")).attenuationDistance(NTechItems.sirenTrackStandardSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_STANDARD))
        add(SoundEvents.sirenTrackSweepSiren, definition().with(sound(ntm("block/siren/sweep")).attenuationDistance(NTechItems.sirenTrackSweepSiren.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_SWEEP))
        add(SoundEvents.sirenTrackVaultDoorAlarm, definition().with(sound(ntm("block/siren/vault_door")).attenuationDistance(NTechItems.sirenTrackVaultDoorAlarm.get().range)).subtitle(LangKeys.SUBTITLE_SIREN_VAULT_DOOR))
    }

    private fun SoundDefinition.subtitle(key: TranslationKey) = subtitle(key.key)
}
