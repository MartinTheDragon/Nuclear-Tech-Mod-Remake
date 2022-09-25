package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.api.block.multi.MultiBlockPlacer
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.Wearable
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PumpjackPlacerItem(block: Block, blockEntityFunc: (BlockPos, BlockState) -> BlockEntity, placerFunc: (MultiBlockPlacer) -> Unit, properties: Properties) : SpecialModelMultiBlockPlacerItem(block, blockEntityFunc, placerFunc, properties), Wearable {
    override fun canEquip(stack: ItemStack, armorType: EquipmentSlot, entity: Entity) = armorType == EquipmentSlot.HEAD

    override fun appendHoverText(stack: ItemStack, worldIn: Level?, tooltip: MutableList<Component>, flagIn: TooltipFlag) {
        if (NuclearTech.polaroidBroken) autoTooltip(stack, tooltip, true)
    }
}
