package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.rendering.CustomBEWLR
import net.minecraft.core.BlockPos
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.client.IItemRenderProperties
import java.util.function.Consumer

open class SpecialModelBlockItem(block: Block, override val blockEntityFunc: (pos: BlockPos, state: BlockState) -> BlockEntity, properties: Properties) : BlockItem(block, properties), SpecialRenderingBlockEntityItem {
    override val blockStateForRendering: BlockState get() = super.getBlock().defaultBlockState()

    override fun initializeClient(consumer: Consumer<IItemRenderProperties>) {
        consumer.accept(CustomBEWLR.DefaultRenderProperties)
    }
}
