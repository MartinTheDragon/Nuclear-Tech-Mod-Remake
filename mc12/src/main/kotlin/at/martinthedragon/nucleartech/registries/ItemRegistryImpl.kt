package at.martinthedragon.nucleartech.registries

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.entity.NameableBlockEntityMirror
import at.martinthedragon.nucleartech.coremodules.forge.registries.RegistryObjectImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.item.ItemImplItem
import at.martinthedragon.nucleartech.item.NTechItemsCore
import at.martinthedragon.nucleartech.item.TooltippedVanishableItem
import at.martinthedragon.nucleartech.sorcerer.registries.mirroredEntry
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.registries.IRegistryDelegate

@Mod.EventBusSubscriber(modid = NuclearTech.MODID)
class ItemRegistryImpl : ItemRegistry {
    private val constructors = Object2ObjectOpenHashMap<String, () -> Item>()
    private val instances = Object2ObjectOpenHashMap<String, ItemImplItem<Item>>()

    @Suppress("UNCHECKED_CAST")
    override fun <I : Item> register(name: String, sup: () -> I) = RegistryObjectImpl(getItemInstance(name).delegate as IRegistryDelegate<out ItemImplItem<I>>).also { constructors[name] = sup }

    @SubscribeEvent
    fun register(event: RegistryEvent.Register<net.minecraft.item.Item>) {
        val registry = event.registry
        constructors.map { (name, sup) ->
            getItemInstance(name).apply {
                item = sup()
            }.let(registry::register)
        }
        NTechItemsCore.testItem.get().mirroredEntry
    }

    fun getItemInstance(name: String) = instances.getOrPut(name) {
        ItemImplItem<Item>().apply {
            setRegistryName(NuclearTech.MODID, name)
            unlocalizedName = "${NuclearTech.MODID}.$name"
        }
    }
}
