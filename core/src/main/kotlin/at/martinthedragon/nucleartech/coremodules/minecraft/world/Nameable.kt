package at.martinthedragon.nucleartech.coremodules.minecraft.world

import at.martinthedragon.nucleartech.coremodules.minecraft.network.chat.Component
import at.martinthedragon.nucleartech.sorcerer.Linkage

@Linkage(Linkage.MC12, "net.minecraft.world.IWorldNameable")
@Linkage(Linkage.MC18, "net.minecraft.world.Nameable")
interface Nameable {
    @Linkage.Link(Linkage.MC12, "%i.formattedText")
    @Linkage.Link(Linkage.MC18, "%i.delegate")
    val name: Component

    @Linkage.Link(Linkage.MC12, "%i.delegate")
    @Linkage.Link(Linkage.MC18, "%i.delegate")
    val displayName: Component get() = name

    @Linkage.Link(Linkage.MC18, "%??.delegate")
    val customName: Component? get() = null

    @Linkage.Link(Linkage.MC12, "%%")
    @Linkage.Link(Linkage.MC18, "%%")
    val hasCustomName: Boolean get() = customName != null
}
