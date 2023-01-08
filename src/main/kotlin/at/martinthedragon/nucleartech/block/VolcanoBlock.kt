package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.LangKeys
import at.martinthedragon.nucleartech.api.block.entities.createServerTickerChecked
import at.martinthedragon.nucleartech.block.entity.BlockEntityTypes
import at.martinthedragon.nucleartech.block.entity.VolcanoBlockEntity
import at.martinthedragon.nucleartech.extensions.darkGray
import at.martinthedragon.nucleartech.extensions.gold
import at.martinthedragon.nucleartech.extensions.red
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.pathfinder.PathComputationType

class VolcanoBlock(properties: Properties) : BaseEntityBlock(properties) {
    init { registerDefaultState(stateDefinition.any().setValue(GROWS, false).setValue(EXTINGUISHES, false).setValue(SMOLDERING, false)) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(GROWS, EXTINGUISHES, SMOLDERING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val tags = context.itemInHand.tag ?: return defaultBlockState()
        return stateDefinition.any()
            .setValue(GROWS, tags.getBoolean("Grows"))
            .setValue(EXTINGUISHES, tags.getBoolean("Extinguishes"))
            .setValue(SMOLDERING, tags.getBoolean("Smoldering"))
    }

    override fun isPathfindable(state: BlockState, level: BlockGetter, pos: BlockPos, path: PathComputationType) = false
    override fun getRenderShape(state: BlockState) = RenderShape.MODEL

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        items += ItemStack(this).apply { orCreateTag.apply { putBoolean("Grows", false); putBoolean("Extinguishes", false) }}
        items += ItemStack(this).apply { orCreateTag.apply { putBoolean("Grows", true); putBoolean("Extinguishes", false) }}
        items += ItemStack(this).apply { orCreateTag.apply { putBoolean("Grows", false); putBoolean("Extinguishes", true) }}
        items += ItemStack(this).apply { orCreateTag.apply { putBoolean("Grows", true); putBoolean("Extinguishes", true) }}
        items += ItemStack(this).apply { orCreateTag.putBoolean("Smoldering", true) }
    }

    override fun appendHoverText(itemStack: ItemStack, level: BlockGetter?, tooltip: MutableList<Component>, flag: TooltipFlag) {
        if (itemStack.tag?.getBoolean("Smoldering") == true) {
            tooltip += LangKeys.VOLCANO_SMOLDERING.gold()
            return
        }

        tooltip += if (itemStack.tag?.getBoolean("Grows") == true) LangKeys.VOLCANO_GROWING.red() else LangKeys.VOLCANO_NOT_GROWING.darkGray()
        tooltip += if (itemStack.tag?.getBoolean("Extinguishes") == true) LangKeys.VOLCANO_EXTINGUISHING.red() else LangKeys.VOLCANO_NOT_EXTINGUISHING.darkGray()
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = VolcanoBlockEntity(pos, state)
    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>) = if (level.isClientSide) null else createServerTickerChecked(type, BlockEntityTypes.volcanoBlockEntityType.get())

    companion object {
        val GROWS: BooleanProperty = BooleanProperty.create("grows")
        val EXTINGUISHES: BooleanProperty = BooleanProperty.create("extinguishes")
        val SMOLDERING: BooleanProperty = BooleanProperty.create("smoldering")
    }
}
