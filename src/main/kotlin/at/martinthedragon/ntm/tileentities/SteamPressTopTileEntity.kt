package at.martinthedragon.ntm.tileentities

import net.minecraft.tileentity.TileEntity

class SteamPressTopTileEntity : TileEntity(TileEntityTypes.steamPressHeadTileEntityType.get()) {
    var headMoving = true
    var headPosition = 0F
    var headMovingUp = false
}
