package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCK_ENTITIES
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.entity.rbmk.*
import at.martinthedragon.nucleartech.block.entity.transmitters.CableBlockEntity
import at.martinthedragon.nucleartech.block.entity.transmitters.FluidPipeBlockEntity
import at.martinthedragon.nucleartech.block.multi.MultiBlockPart
import at.martinthedragon.nucleartech.block.multi.MultiBlockPort
import at.martinthedragon.nucleartech.registerK
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object BlockEntityTypes {
    val genericMultiBlockPartBlockEntityType = BLOCK_ENTITIES.registerK("generic_multi_block_part") { createType(MultiBlockPart::GenericMultiBlockPartBlockEntity, NTechBlocks.genericMultiBlockPart.get()) }
    val genericMultiBlockPortBlockEntityType = BLOCK_ENTITIES.registerK("generic_multi_block_port") { createType(MultiBlockPort::GenericMultiBlockPortBlockEntity, NTechBlocks.genericMultiBlockPort.get()) }
    val safeBlockEntityType = BLOCK_ENTITIES.registerK("safe") { createType(::SafeBlockEntity, NTechBlocks.safe.get()) }
    val sirenBlockEntityType = BLOCK_ENTITIES.registerK("siren") { createType(::SirenBlockEntity, NTechBlocks.siren.get()) }
    val steamPressHeadBlockEntityType = BLOCK_ENTITIES.registerK("steam_press_top") { createType(::SteamPressBlockEntity, NTechBlocks.steamPressTop.get()) }
    val blastFurnaceBlockEntityType = BLOCK_ENTITIES.registerK("blast_furnace") { createType(::BlastFurnaceBlockEntity, NTechBlocks.blastFurnace.get()) }
    val combustionGeneratorBlockEntityType = BLOCK_ENTITIES.registerK("combustion_generator") { createType(::CombustionGeneratorBlockEntity, NTechBlocks.combustionGenerator.get()) }
    val electricFurnaceBlockEntityType = BLOCK_ENTITIES.registerK("electric_furnace") { createType(::ElectricFurnaceBlockEntity, NTechBlocks.electricFurnace.get()) }
    val shredderBlockEntityType = BLOCK_ENTITIES.registerK("shredder") { createType(::ShredderBlockEntity, NTechBlocks.shredder.get()) }
    val assemblerBlockEntityType = BLOCK_ENTITIES.registerK("assembler") { createType(::AssemblerBlockEntity, NTechBlocks.assembler.get()) }
    val chemPlantBlockEntityType = BLOCK_ENTITIES.registerK("chem_plant") { createType(::ChemPlantBlockEntity, NTechBlocks.chemPlant.get()) }
    val turbineBlockEntityType = BLOCK_ENTITIES.registerK("turbine") { createType(::TurbineBlockEntity, NTechBlocks.turbine.get()) }
    val oilDerrickBlockEntityType = BLOCK_ENTITIES.registerK("oil_derrick") { createType(::OilDerrickBlockEntity, NTechBlocks.oilDerrick.get()) }
    val pumpjackBlockEntityType = BLOCK_ENTITIES.registerK("pumpjack") { createType(::PumpjackBlockEntity, NTechBlocks.pumpjack.get()) }
    val centrifugeBlockEntityType = BLOCK_ENTITIES.registerK("centrifuge") { createType(::CentrifugeBlockEntity, NTechBlocks.centrifuge.get()) }
    val cableBlockEntityType = BLOCK_ENTITIES.registerK("cable") { createType(::CableBlockEntity, NTechBlocks.coatedCable.get()) }
    val fluidPipeBlockEntityType = BLOCK_ENTITIES.registerK("fluid_pipe") { createType(::FluidPipeBlockEntity, NTechBlocks.coatedUniversalFluidDuct.get()) }
    val rbmkBlankBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_blank") { createType(::RBMKBlankBlockEntity, NTechBlocks.rbmkBlank.get()) }
    val rbmkRodBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_rod") { createType(::RBMKRodBlockEntity, NTechBlocks.rbmkRod.get()) }
    val rbmkModeratedRodBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_moderated_rod") { createType(::RBMKModeratedRodBlockEntity, NTechBlocks.rbmkModeratedRod.get()) }
    val rbmkReaSimRodBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_reasim_rod") { createType(::RBMKReaSimRodBlockEntity, NTechBlocks.rbmkReaSimRod.get()) }
    val rbmkModeratedReaSimRodBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_moderated_reasim_rod") { createType(::RBMKModeratedReaSimRodBlockEntity, NTechBlocks.rbmkModeratedReaSimRod.get()) }
    val rbmkAbsorberBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_absorber") { createType(::RBMKAbsorberBlockEntity, NTechBlocks.rbmkAbsorber.get()) }
    val rbmkModeratorBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_moderator") { createType(::RBMKModeratorBlockEntity, NTechBlocks.rbmkModerator.get()) }
    val rbmkReflectorBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_reflector") { createType(::RBMKReflectorBlockEntity, NTechBlocks.rbmkReflector.get()) }
    val rbmkBoilerBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_boiler") { createType(::RBMKBoilerBlockEntity, NTechBlocks.rbmkBoiler.get()) }
    val rbmkBoilerWaterInputBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_boiler_water_input") { createType(RBMKBoilerBlockEntity::WaterInputBlockEntity, NTechBlocks.rbmkBoilerColumn.get()) }
    val rbmkManualControlBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_manual_control") { createType(::RBMKManualControlBlockEntity, NTechBlocks.rbmkManualControlRod.get()) }
    val rbmkModeratedControlBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_moderated_control") { createType(::RBMKModeratedControlBlockEntity, NTechBlocks.rbmkModeratedControlRod.get()) }
    val rbmkAutoControlBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_auto_control") { createType(::RBMKAutoControlBlockEntity, NTechBlocks.rbmkAutoControlRod.get()) }
    val rbmkSteamConnectorBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_steam_connector") { createType(::RBMKSteamConnectorBlockEntity, NTechBlocks.rbmkSteamConnector.get()) }
    val rbmkInletBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_inlet") { createType(::RBMKInletBlockEntity, NTechBlocks.rbmkInlet.get()) }
    val rbmkOutletBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_outlet") { createType(::RBMKOutletBlockEntity, NTechBlocks.rbmkOutlet.get()) }
    val rbmkConsoleBlockEntityType = BLOCK_ENTITIES.registerK("rbmk_console") { createType(::RBMKConsoleBlockEntity, NTechBlocks.rbmkConsole.get()) }
    val littleBoyBlockEntityType = BLOCK_ENTITIES.registerK("little_boy") { createType(::LittleBoyBlockEntity, NTechBlocks.littleBoy.get()) }
    val fatManBlockEntityType = BLOCK_ENTITIES.registerK("fat_man") { createType(::FatManBlockEntity, NTechBlocks.fatMan.get()) }
    val volcanoBlockEntityType = BLOCK_ENTITIES.registerK("volcano") { createType(::VolcanoBlockEntity, NTechBlocks.volcanoCore.get()) }
    val launchPadBlockEntityType = BLOCK_ENTITIES.registerK("launch_pad") { createType(::LaunchPadBlockEntity, NTechBlocks.launchPad.get()) }

    private fun <T : BlockEntity> createType(supplier: BlockEntitySupplier<T>, vararg blocks: Block) = BlockEntityType.Builder.of(supplier, *blocks).build()
    private fun <T : BlockEntity> BlockEntityType.Builder<T>.build() = build(null)
}
