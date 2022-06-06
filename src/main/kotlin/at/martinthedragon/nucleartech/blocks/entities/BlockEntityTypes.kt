package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCK_ENTITIES
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.RegistryObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object BlockEntityTypes {
    val safeBlockEntityType: RegistryObject<BlockEntityType<SafeBlockEntity>> = BLOCK_ENTITIES.register("safe") { BlockEntityType.Builder.of(::SafeBlockEntity, ModBlocks.safe.get()).build() }
    val sirenBlockEntityType: RegistryObject<BlockEntityType<SirenBlockEntity>> = BLOCK_ENTITIES.register("siren") { BlockEntityType.Builder.of(::SirenBlockEntity, ModBlocks.siren.get()).build() }
    val steamPressHeadBlockEntityType: RegistryObject<BlockEntityType<SteamPressBlockEntity>> = BLOCK_ENTITIES.register("steam_press_top") { BlockEntityType.Builder.of(::SteamPressBlockEntity, ModBlocks.steamPressTop.get()).build() }
    val blastFurnaceBlockEntityType: RegistryObject<BlockEntityType<BlastFurnaceBlockEntity>> = BLOCK_ENTITIES.register("blast_furnace") { BlockEntityType.Builder.of(::BlastFurnaceBlockEntity, ModBlocks.blastFurnace.get()).build() }
    val combustionGeneratorBlockEntityType: RegistryObject<BlockEntityType<CombustionGeneratorBlockEntity>> = BLOCK_ENTITIES.register("combustion_generator") { BlockEntityType.Builder.of(::CombustionGeneratorBlockEntity, ModBlocks.combustionGenerator.get()).build() }
    val electricFurnaceBlockEntityType: RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> = BLOCK_ENTITIES.register("electric_furnace") { BlockEntityType.Builder.of(::ElectricFurnaceBlockEntity, ModBlocks.electricFurnace.get()).build() }
    val shredderBlockEntityType: RegistryObject<BlockEntityType<ShredderBlockEntity>> = BLOCK_ENTITIES.register("shredder") { BlockEntityType.Builder.of(::ShredderBlockEntity, ModBlocks.shredder.get()).build() }
    val assemblerBlockEntityType: RegistryObject<BlockEntityType<AssemblerBlockEntity>> = BLOCK_ENTITIES.register("assembler") { BlockEntityType.Builder.of(::AssemblerBlockEntity, ModBlocks.assembler.get()).build() }
    val assemblerPartBlockEntityType: RegistryObject<BlockEntityType<AssemblerBlockEntity.AssemblerPartBlockEntity>> = BLOCK_ENTITIES.register("assembler_part") { BlockEntityType.Builder.of(AssemblerBlockEntity::AssemblerPartBlockEntity, ModBlocks.assemblerPart.get()).build() }
    val littleBoyBlockEntityType: RegistryObject<BlockEntityType<LittleBoyBlockEntity>> = BLOCK_ENTITIES.register("little_boy") { BlockEntityType.Builder.of(::LittleBoyBlockEntity, ModBlocks.littleBoy.get()).build() }
    val fatManBlockEntityType: RegistryObject<BlockEntityType<FatManBlockEntity>> = BLOCK_ENTITIES.register("fat_man") { BlockEntityType.Builder.of(::FatManBlockEntity, ModBlocks.fatMan.get()).build() }
    val launchPadBlockEntityType: RegistryObject<BlockEntityType<LaunchPadBlockEntity>> = BLOCK_ENTITIES.register("launch_pad") { BlockEntityType.Builder.of(::LaunchPadBlockEntity, ModBlocks.launchPad.get()).build() }
    val launchPadPartBlockEntityType: RegistryObject<BlockEntityType<LaunchPadBlockEntity.LaunchPadPartBlockEntity>> = BLOCK_ENTITIES.register("launch_pad_part") { BlockEntityType.Builder.of(LaunchPadBlockEntity::LaunchPadPartBlockEntity, ModBlocks.launchPadPart.get()).build() }

    private fun <T : BlockEntity> BlockEntityType.Builder<T>.build() = build(null)
}
