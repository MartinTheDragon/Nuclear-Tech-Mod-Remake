package at.martinthedragon.nucleartech.menus

import at.martinthedragon.nucleartech.blocks.entities.ContainerSyncableBlockEntity
import at.martinthedragon.nucleartech.menus.slots.data.*
import at.martinthedragon.nucleartech.networking.ContainerMenuUpdateMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.DataSlot
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.network.NetworkDirection

// an implementation that doesn't advertise shorts as ints
open class NTechContainerMenu<T : BlockEntity>(type: MenuType<*>, id: Int, val playerInventory: Inventory, val blockEntity: T) : AbstractContainerMenu(type, id) {
    private val trackedSlots = mutableListOf<NTechDataSlot>()

    init {
        @Suppress("LeakingThis")
        if (blockEntity is ContainerSyncableBlockEntity) blockEntity.trackContainerMenu(this)
    }

    fun track(dataSlot: NTechDataSlot) {
        trackedSlots += dataSlot
    }

    fun trackArray(booleans: BooleanArray) { for (i in booleans.indices) track(BooleanDataSlot.create(booleans, i)) }
    fun trackArray(bytes: ByteArray) { for (i in bytes.indices) track(ByteDataSlot.create(bytes, i)) }
    fun trackArray(shorts: ShortArray) { for (i in shorts.indices) track(ShortDataSlot.create(shorts, i)) }
    fun trackArray(ints: IntArray) { for (i in ints.indices) track(IntDataSlot.create(ints, i)) }
    fun trackArray(longs: LongArray) { for (i in longs.indices) track(LongDataSlot.create(longs, i)) }
    fun trackArray(floats: FloatArray) { for (i in floats.indices) track(FloatDataSlot.create(floats, i)) }
    fun trackArray(doubles: DoubleArray) { for (i in doubles.indices) track(DoubleDataSlot.create(doubles, i)) }

    fun getTrackedSlot(slot: Short) = trackedSlots[slot.toInt()]

    final override fun addDataSlot(dataSlot: DataSlot): DataSlot {
        track(IntDataSlot.create(dataSlot::get, dataSlot::set))
        return dataSlot
    }

    fun handleDataUpdate(slot: Short, value: Boolean) {
        val data = getTrackedSlot(slot)
        if (data is BooleanDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: Byte) {
        val data = getTrackedSlot(slot)
        if (data is ByteDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: Short) {
        val data = getTrackedSlot(slot)
        if (data is ShortDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: Int) {
        val data = getTrackedSlot(slot)
        if (data is IntDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: Long) {
        val data = getTrackedSlot(slot)
        if (data is LongDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: Float) {
        val data = getTrackedSlot(slot)
        if (data is FloatDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: Double) {
        val data = getTrackedSlot(slot)
        if (data is DoubleDataSlot)
            data.set(value)
    }

    fun handleDataUpdate(slot: Short, value: FluidStack) {
        val data = getTrackedSlot(slot)
        if (data is FluidStackDataSlot)
            data.set(value)
    }

    override fun broadcastChanges() {
        super.broadcastChanges()
        val player = playerInventory.player
        if (player is ServerPlayer) {
            val dirtyData = buildList {
                for ((slotIndex, slot) in trackedSlots.withIndex()) if (slot.isDirty())
                    add(slot.getData(slotIndex.toShort()))
            }
            if (dirtyData.isNotEmpty()) NuclearPacketHandler.INSTANCE.sendTo(ContainerMenuUpdateMessage(containerId, dirtyData), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT)
        }
    }

    override fun sendAllDataToRemote() {
        super.sendAllDataToRemote()
        val player = playerInventory.player
        if (player is ServerPlayer) {
            val data = trackedSlots.mapIndexed { index, slot -> slot.getData(index.toShort()) }
            if (data.isNotEmpty()) NuclearPacketHandler.INSTANCE.sendTo(ContainerMenuUpdateMessage(containerId, data), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT)
        }
    }

    override fun stillValid(player: Player) = playerInventory.stillValid(player)

    fun isClientSide() = playerInventory.player.level.isClientSide
}
