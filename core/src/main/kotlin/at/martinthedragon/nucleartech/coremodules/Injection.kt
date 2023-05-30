package at.martinthedragon.nucleartech.coremodules

import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.forge.energy.CapabilityEnergy
import at.martinthedragon.nucleartech.coremodules.forge.network.PacketDistributor
import at.martinthedragon.nucleartech.coremodules.minecraft.client.Minecraft
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language.I18n
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Vec3i
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.util.Mth
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.CreativeModeTab
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.Item
import at.martinthedragon.nucleartech.coremodules.minecraft.world.item.ItemStack
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntity
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties.BlockStateProperties
import at.martinthedragon.nucleartech.net.NTechNetMessages
import org.koin.core.KoinApplication
import org.koin.core.component.get
import org.koin.core.component.inject

lateinit var koinApp: KoinApplication

val STUBS = InjectionStatic.get<ApiStubs>()
val OPTIMIZED = InjectionStatic.get<Optimizations>()

object InjectionFactories : NTechKoinComponent {
    val blockPos: BlockPos.Factory by inject()
    val component: Component.Factory by inject()
    val compoundTag: CompoundTag.Factory by inject()
    val creativeModeTab: CreativeModeTab.Factory by inject()
    val itemStack: ItemStack.Factory by inject()
    val resourceLocation: ResourceLocation.Factory by inject()
    val vec3i: Vec3i.Factory by inject()
}

object InjectionStatic : NTechKoinComponent {
    val blockEntity: BlockEntity.Static by inject()
    val blockStateProperties: BlockStateProperties by inject()
    val capabilityEnergy: CapabilityEnergy by inject()
    val clientI18n: I18n by inject()
    val clientMinecraft: Minecraft by inject()
    val lazyOptional: LazyOptional.Static by inject()
    val mth: Mth by inject()
    val networkMessages: NTechNetMessages by inject()
    val packetDistributor: PacketDistributor.Static by inject()
}
