package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCK_ENTITIES
import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPart
import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPort
import at.martinthedragon.nucleartech.registerK
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.RegistryObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object BlockEntityTypes {
    val genericMultiBlockPartBlockEntityType = BLOCK_ENTITIES.registerK("generic_multi_block_part") { BlockEntityType.Builder.of(MultiBlockPart::GenericMultiBlockPartBlockEntity, ModBlocks.genericMultiBlockPart.get()).build() }
    val genericMultiBlockPortBlockEntityType = BLOCK_ENTITIES.registerK("generic_multi_block_port") { BlockEntityType.Builder.of(MultiBlockPort::GenericMultiBlockPortBlockEntity, ModBlocks.genericMultiBlockPort.get()).build() }
    val safeBlockEntityType = BLOCK_ENTITIES.registerK("safe") { BlockEntityType.Builder.of(::SafeBlockEntity, ModBlocks.safe.get()).build() }
    val sirenBlockEntityType = BLOCK_ENTITIES.registerK("siren") { BlockEntityType.Builder.of(::SirenBlockEntity, ModBlocks.siren.get()).build() }
    val steamPressHeadBlockEntityType = BLOCK_ENTITIES.registerK("steam_press_top") { BlockEntityType.Builder.of(::SteamPressBlockEntity, ModBlocks.steamPressTop.get()).build() }
    val blastFurnaceBlockEntityType = BLOCK_ENTITIES.registerK("blast_furnace") { BlockEntityType.Builder.of(::BlastFurnaceBlockEntity, ModBlocks.blastFurnace.get()).build() }
    val combustionGeneratorBlockEntityType = BLOCK_ENTITIES.registerK("combustion_generator") { BlockEntityType.Builder.of(::CombustionGeneratorBlockEntity, ModBlocks.combustionGenerator.get()).build() }
    val electricFurnaceBlockEntityType = BLOCK_ENTITIES.registerK("electric_furnace") { BlockEntityType.Builder.of(::ElectricFurnaceBlockEntity, ModBlocks.electricFurnace.get()).build() }
    val shredderBlockEntityType = BLOCK_ENTITIES.registerK("shredder") { BlockEntityType.Builder.of(::ShredderBlockEntity, ModBlocks.shredder.get()).build() }
    val assemblerBlockEntityType = BLOCK_ENTITIES.registerK("assembler") { BlockEntityType.Builder.of(::AssemblerBlockEntity, ModBlocks.assembler.get()).build() }
    val littleBoyBlockEntityType = BLOCK_ENTITIES.registerK("little_boy") { BlockEntityType.Builder.of(::LittleBoyBlockEntity, ModBlocks.littleBoy.get()).build() }
    val fatManBlockEntityType = BLOCK_ENTITIES.registerK("fat_man") { BlockEntityType.Builder.of(::FatManBlockEntity, ModBlocks.fatMan.get()).build() }
    val launchPadBlockEntityType = BLOCK_ENTITIES.registerK("launch_pad") { BlockEntityType.Builder.of(::LaunchPadBlockEntity, ModBlocks.launchPad.get()).build() }
    val launchPadPartBlockEntityType: RegistryObject<BlockEntityType<LaunchPadBlockEntity.LaunchPadPartBlockEntity>> = BLOCK_ENTITIES.register("launch_pad_part") { BlockEntityType.Builder.of(LaunchPadBlockEntity::LaunchPadPartBlockEntity, ModBlocks.launchPadPart.get()).build() }

    private fun <T : BlockEntity> BlockEntityType.Builder<T>.build() = build(null)
}
