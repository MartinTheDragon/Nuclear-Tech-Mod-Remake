package at.martinthedragon.nucleartech.coremodules.forge.common.capabilities

interface CapabilityProvider<B : CapabilityProvider<B>> : ICapabilityProvider {
    fun invalidateCaps() {}
    fun reviveCaps() {}
}
