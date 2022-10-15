package at.martinthedragon.nucleartech.item

import at.martinthedragon.nucleartech.block.entity.transmitters.FluidPipeBlockEntity
import net.minecraft.ResourceLocationException
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.registries.ForgeRegistries

class FluidIdentifierItem(properties: Properties) : AutoTooltippedItem(properties) {
    override fun getName(stack: ItemStack): Component = TranslatableComponent(getDescriptionId(stack), getFluid(stack).let { if (it.isSame(Fluids.EMPTY)) TextComponent("N/A") else TranslatableComponent(it.attributes.translationKey) })

    override fun fillItemCategory(tab: CreativeModeTab, items: NonNullList<ItemStack>) {
        if (allowdedIn(tab)) {
            items.addAll(getAllFluidIdentifiers())
        }
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val pos = context.clickedPos
        val blockEntity = level.getBlockEntity(pos)

        if (blockEntity is FluidPipeBlockEntity) {
            val fluid = getFluid(context.itemInHand)
            if (context.isSecondaryUseActive)
                markDuctsRecursively(level, pos, fluid)
            else
                blockEntity.setFluid(fluid)
            return InteractionResult.sidedSuccess(level.isClientSide)
        }

        return InteractionResult.PASS
    }

    private fun markDuctsRecursively(level: Level, pos: BlockPos, fluid: Fluid, maxRecursion: Int = 64) {
        val start = level.getBlockEntity(pos)
        if (start !is FluidPipeBlockEntity) return

        val oldFluid = start.fluid
        if (oldFluid.isSame(fluid)) return

        start.setFluid(fluid)

        directionLoop@for (direction in start.transmitter.currentTransmitterConnections) for (i in 1..maxRecursion) {
            val nextPos = pos.relative(direction, i)
            val nextDuct = level.getBlockEntity(nextPos)
            if (nextDuct is FluidPipeBlockEntity && nextDuct.fluid.isSame(oldFluid)) {
                if (nextDuct.transmitter.currentTransmitterConnections.count() > 1) {
                    markDuctsRecursively(level, nextPos, fluid, maxRecursion - i)
                    continue@directionLoop
                } else nextDuct.setFluid(fluid)
            } else break
        }
    }

    fun getFluid(stack: ItemStack): Fluid = try {
        val location = stack.tag?.getString("FluidId") ?: "minecraft:empty"
        ForgeRegistries.FLUIDS.getValue(ResourceLocation(location)) ?: Fluids.EMPTY
    } catch (ignored: ResourceLocationException) {
        Fluids.EMPTY
    }

    companion object {
        fun getFluid(stack: ItemStack): Fluid {
            val item = stack.item
            return if (item is FluidIdentifierItem) {
                item.getFluid(stack)
            } else Fluids.EMPTY
        }

        fun getAllFluidIdentifiers(): List<ItemStack> =
            ForgeRegistries.FLUIDS
                .filterNot { it.registryName!!.path.contains("flowing") }
                .map { ItemStack(NTechItems.fluidIdentifier.get()).apply { orCreateTag.putString("FluidId", it.registryName!!.toString()) } }
    }
}
