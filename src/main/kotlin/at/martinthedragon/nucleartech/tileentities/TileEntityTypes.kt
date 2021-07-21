package at.martinthedragon.nucleartech.tileentities

import at.martinthedragon.nucleartech.ModBlocks
import at.martinthedragon.nucleartech.RegistriesAndLifecycle.TILE_ENTITIES
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.fml.RegistryObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object TileEntityTypes {
    val safeTileEntityType: RegistryObject<TileEntityType<SafeTileEntity>> = TILE_ENTITIES.register("safe") {
        TileEntityType.Builder.of(::SafeTileEntity, ModBlocks.safe.get()).build(null)
    }
    val sirenTileEntityType: RegistryObject<TileEntityType<SirenTileEntity>> = TILE_ENTITIES.register("siren") {
        TileEntityType.Builder.of(::SirenTileEntity, ModBlocks.siren.get()).build(null)
    }
    val steamPressHeadTileEntityType: RegistryObject<TileEntityType<SteamPressTopTileEntity>> = TILE_ENTITIES.register("steam_press_top") {
        TileEntityType.Builder.of(::SteamPressTopTileEntity, ModBlocks.steamPressTop.get()).build(null)
    }
    val blastFurnaceTileEntityType: RegistryObject<TileEntityType<BlastFurnaceTileEntity>> = TILE_ENTITIES.register("blast_furnace") {
        TileEntityType.Builder.of(::BlastFurnaceTileEntity, ModBlocks.blastFurnace.get()).build(null)
    }
    val combustionGeneratorTileEntityType: RegistryObject<TileEntityType<CombustionGeneratorTileEntity>> = TILE_ENTITIES.register("combustion_generator") {
        TileEntityType.Builder.of(::CombustionGeneratorTileEntity, ModBlocks.combustionGenerator.get()).build(null)
    }
    val electricFurnaceTileEntityType: RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> = TILE_ENTITIES.register("electric_furnace") {
        TileEntityType.Builder.of(::ElectricFurnaceTileEntity, ModBlocks.electricFurnace.get()).build(null)
    }
}
