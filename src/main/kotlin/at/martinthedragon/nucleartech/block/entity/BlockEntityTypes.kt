package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCK_ENTITIES
import at.martinthedragon.nucleartech.block.NTechBlocks
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
    val oilDerrickBlockEntityType = BLOCK_ENTITIES.registerK("oil_derrick") { createType(::OilDerrickBlockEntity, NTechBlocks.oilDerrick.get()) }
    val pumpjackBlockEntityType = BLOCK_ENTITIES.registerK("pumpjack") { createType(::PumpjackBlockEntity, NTechBlocks.pumpjack.get()) }
    val cableBlockEntity = BLOCK_ENTITIES.registerK("cable") { createType(::CableBlockEntity, NTechBlocks.coatedCable.get()) }
    val littleBoyBlockEntityType = BLOCK_ENTITIES.registerK("little_boy") { createType(::LittleBoyBlockEntity, NTechBlocks.littleBoy.get()) }
    val fatManBlockEntityType = BLOCK_ENTITIES.registerK("fat_man") { createType(::FatManBlockEntity, NTechBlocks.fatMan.get()) }
    val launchPadBlockEntityType = BLOCK_ENTITIES.registerK("launch_pad") { createType(::LaunchPadBlockEntity, NTechBlocks.launchPad.get()) }

    private fun <T : BlockEntity> createType(supplier: BlockEntitySupplier<T>, vararg blocks: Block) = BlockEntityType.Builder.of(supplier, *blocks).build()
    private fun <T : BlockEntity> BlockEntityType.Builder<T>.build() = build(null)
}
