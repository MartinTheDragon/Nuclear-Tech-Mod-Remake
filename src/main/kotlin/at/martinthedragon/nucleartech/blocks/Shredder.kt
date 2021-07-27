package at.martinthedragon.nucleartech.blocks

import at.martinthedragon.nucleartech.tileentities.ShredderTileEntity
import at.martinthedragon.nucleartech.world.dropExperience
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.PushReaction
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.container.Container
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.math.vector.Vector3d
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

class Shredder(properties: Properties) : Block(properties) {
    override fun use(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!world.isClientSide) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is ShredderTileEntity) NetworkHooks.openGui(player as ServerPlayerEntity, tileEntity, pos)
        }
        return ActionResultType.sidedSuccess(world.isClientSide)
    }

    override fun getPistonPushReaction(state: BlockState) = PushReaction.BLOCK

    override fun setPlacedBy(world: World, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        setTileEntityCustomName<ShredderTileEntity>(world, pos, stack)
    }

    override fun onRemove(state: BlockState, world: World, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        dropTileEntityContents<ShredderTileEntity>(state, world, pos, newState) {
            world.dropExperience(Vector3d.atCenterOf(pos), it.getExperienceToDrop(null))
        }
        @Suppress("DEPRECATION")
        super.onRemove(state, world, pos, newState, p_196243_5_)
    }

    override fun hasAnalogOutputSignal(state: BlockState) = true

    override fun getAnalogOutputSignal(state: BlockState, world: World, pos: BlockPos): Int =
        Container.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos))

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = ShredderTileEntity()
}
