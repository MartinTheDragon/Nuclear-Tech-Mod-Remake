package at.martinthedragon.nucleartech.coremodules

import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocation
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.ResourceLocationImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language.I18n
import at.martinthedragon.nucleartech.coremodules.minecraft.client.resources.language.I18nImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPosImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Vec3i
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Vec3iImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTag
import at.martinthedragon.nucleartech.coremodules.minecraft.nbt.CompoundTagImpl
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.ComponentImpl
import at.martinthedragon.nucleartech.registries.ItemRegistry
import at.martinthedragon.nucleartech.registries.ItemRegistryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val minecraftModule = module {
    singleOf(BlockPosImpl::Factory) { bind<BlockPos.Factory>() }
    singleOf(ComponentImpl::Factory) { bind<Component.Factory>() }
    singleOf(CompoundTagImpl::Factory) { bind<CompoundTag.Factory>() }
    singleOf(ResourceLocationImpl::Factory) { bind<ResourceLocation.Factory>() }
    singleOf(Vec3iImpl::Factory) { bind<Vec3i.Factory>() }
}

val minecraftClientModule = module {
    singleOf(::I18nImpl) { bind<I18n>() }
}

val registriesModule = module {
    singleOf(::ItemRegistryImpl) { bind<ItemRegistry>() }
}
