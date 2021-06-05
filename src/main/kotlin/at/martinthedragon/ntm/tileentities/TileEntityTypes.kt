package at.martinthedragon.ntm.tileentities

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.RegistriesAndLifecycle.TILE_ENTITIES
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.fml.RegistryObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object TileEntityTypes {
    val safeTileEntityType: RegistryObject<TileEntityType<SafeTileEntity>> = TILE_ENTITIES.register("safe") {
        TileEntityType.Builder.of({ SafeTileEntity() }, ModBlocks.safe.get()).build(null)
    }
    val sirenTileEntityType: RegistryObject<TileEntityType<SirenTileEntity>> = TILE_ENTITIES.register("siren") {
        TileEntityType.Builder.of({ SirenTileEntity() }, ModBlocks.siren.get()).build(null)
    }
    val steamPressHeadTileEntityType: RegistryObject<TileEntityType<SteamPressTopTileEntity>> = TILE_ENTITIES.register("steam_press_top") {
        TileEntityType.Builder.of({ SteamPressTopTileEntity() }, ModBlocks.steamPressTop.get()).build(null)
    }
}
