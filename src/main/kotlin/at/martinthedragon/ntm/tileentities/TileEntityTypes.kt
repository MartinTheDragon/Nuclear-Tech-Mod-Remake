package at.martinthedragon.ntm.tileentities

import at.martinthedragon.ntm.ModBlocks
import at.martinthedragon.ntm.RegistriesAndLifecycle.register
import net.minecraft.tileentity.TileEntityType
import java.util.function.Supplier

@Suppress("unused", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object TileEntityTypes {
    val safeTileEntityType: TileEntityType<*> = TileEntityType.Builder
            .of(Supplier { SafeTileEntity() }, ModBlocks.safe)
            .build(null)
            .setRegistryName("safe")
            .register()
    val sirenTileEntityType: TileEntityType<*> = TileEntityType.Builder
            .of(Supplier { SirenTileEntity() }, ModBlocks.siren)
            .build(null)
            .setRegistryName("siren")
            .register()
//    val steamPressHeadTileEntityType: TileEntityType<*> = TileEntityType.Builder
//            .create(Supplier { SteamPressHeadTileEntity() }, ModBlocks.steamPressTop)
//            .build(null)
//            .setRegistryName("steam_press_head")
//            .register()
}
