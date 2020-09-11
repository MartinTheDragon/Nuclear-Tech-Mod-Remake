package at.martinthedragon.ntm.tileentities
// FIXME
//import com.google.common.collect.ImmutableMap
//import net.minecraft.resources.IResourceManager
//import net.minecraft.tileentity.TileEntity
//import net.minecraft.util.ResourceLocation
//import net.minecraftforge.common.animation.TimeValues
//import net.minecraftforge.common.capabilities.Capability
//import net.minecraftforge.common.model.animation.AnimationStateMachine
//import net.minecraftforge.common.model.animation.CapabilityAnimation
//import net.minecraftforge.common.model.animation.IAnimationStateMachine
//import net.minecraftforge.common.util.LazyOptional
//import net.minecraftforge.fml.DistExecutor
//
//class SteamPressHeadTileEntity : TileEntity(TileEntityTypes.steamPressHeadTileEntityType) {
//    private val animationSateMachine: IAnimationStateMachine? = DistExecutor.safeRunForDist({
//        DistExecutor.SafeSupplier { AnimationStateMachine.load(
//                IResourceManager.Instance.INSTANCE,
//                ResourceLocation("asms/block/steam_press_head.json"),
//                ImmutableMap.of("cycle_length", TimeValues.ConstValue(2f))
//        ) }
//    }) {
//        DistExecutor.SafeSupplier { null }
//    }
//
//    override fun <T : Any?> getCapability(cap: Capability<T>): LazyOptional<T> {
//        if (cap == CapabilityAnimation.ANIMATION_CAPABILITY)
//            return CapabilityAnimation.ANIMATION_CAPABILITY.orEmpty(cap,
//                    // This code is crap, I know. What I don't know is how to make it better
//                    if (animationSateMachine != null)
//                        LazyOptional.of(this::createHandler)
//                    else LazyOptional.of(null))
//        return super.getCapability(cap)
//    }
//
//    private fun createHandler() = animationSateMachine!!
//}
