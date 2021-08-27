package at.martinthedragon.nucleartech.items

import net.minecraft.block.DispenserBlock
import net.minecraft.dispenser.DefaultDispenseItemBehavior
import net.minecraft.dispenser.IBlockSource
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.item.ItemStack
import net.minecraft.item.SpawnEggItem
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import java.util.function.Supplier

/** An override for `RegistryObject<EntityType<*>>` */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NuclearSpawnEggItem(
    private val entityTypeSupplier: Supplier<out EntityType<*>>,
    primaryColor: Int,
    secondaryColor: Int,
    properties: Properties
) : SpawnEggItem(null, primaryColor, secondaryColor, properties) {
    init {
        toRegister += entityTypeSupplier to this
    }

    override fun getType(nbt: CompoundNBT?): EntityType<*> {
        if (nbt != null && nbt.contains("EntityTag", 10)) {
            val entityTag = nbt.getCompound("EntityTag")
            if (entityTag.contains("id", 8))
                return EntityType.byString(entityTag.getString("id")).orElse(entityTypeSupplier.get())
        }
        return entityTypeSupplier.get()
    }

    companion object {
        private val toRegister = mutableMapOf<Supplier<out EntityType<*>>, NuclearSpawnEggItem>()
        lateinit var resolvedMap: Map<EntityType<*>, NuclearSpawnEggItem>

        fun registerSpawnEggEntities() {
            resolvedMap = toRegister.mapKeys { (typeSupplier, _) -> typeSupplier.get() }
            toRegister.clear()
            BY_ID.putAll(resolvedMap)
            val spawnEntityDispenserBehavior = object : DefaultDispenseItemBehavior() {
                override fun execute(source: IBlockSource, stack: ItemStack): ItemStack {
                    val direction = source.blockState.getValue(DispenserBlock.FACING)
                    (stack.item as SpawnEggItem).getType(stack.tag).spawn(
                        source.level,
                        stack,
                        null,
                        source.pos.relative(direction),
                        SpawnReason.DISPENSER,
                        direction != Direction.UP,
                        false
                    )
                    stack.shrink(1)
                    return stack
                }
            }
            for (spawnEgg in resolvedMap.values)
                DispenserBlock.registerBehavior(spawnEgg, spawnEntityDispenserBehavior)
        }
    }
}
