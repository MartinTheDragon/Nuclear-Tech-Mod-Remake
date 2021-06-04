package at.martinthedragon.ntm.tileentities

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.RegistriesAndLifecycle.register
import net.minecraft.tileentity.TileEntityType

@Suppress("unused", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNCHECKED_CAST")
object TileEntityTypes {
    val safeTileEntityType: TileEntityType<*> = TileEntityType.Builder
            .of({ SafeTileEntity() }, ModBlocks.safe)
            .build(null)
            .setRegistryName("safe")
            .register()
    val sirenTileEntityType: TileEntityType<*> = TileEntityType.Builder
            .of({ SirenTileEntity() }, ModBlocks.siren)
            .build(null)
            .setRegistryName("siren")
            .register()
    val steamPressHeadTileEntityType: TileEntityType<SteamPressTopTileEntity> = TileEntityType.Builder
        .of({ SteamPressTopTileEntity() }, ModBlocks.steamPressTop)
        .build(null)
        .setRegistryName("steam_press_top")
        .register() as TileEntityType<SteamPressTopTileEntity>
}
