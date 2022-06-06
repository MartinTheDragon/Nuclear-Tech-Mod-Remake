package at.martinthedragon.nucleartech.explosions

import at.martinthedragon.nucleartech.api.explosions.ExplosionAlgorithmRegistration
import at.martinthedragon.nucleartech.api.explosions.ExplosionFactory
import at.martinthedragon.nucleartech.api.explosions.NuclearExplosionMk4Params
import com.google.common.collect.HashBasedTable
import net.minecraft.resources.ResourceLocation

object Explosions : ExplosionAlgorithmRegistration {
    private val registrations = HashBasedTable.create<ResourceLocation, Class<out ExplosionFactory.ExplosionParams>, ExplosionFactory<*>>()

    override fun <P : ExplosionFactory.ExplosionParams> register(key: ResourceLocation, params: Class<out P>, factory: ExplosionFactory<P>) {
        if (registrations.containsRow(key) || registrations.containsColumn(params)) return // TODO some exception or log message?
        registrations.put(key, params, factory)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <P : ExplosionFactory.ExplosionParams> getFactory(key: ResourceLocation): ExplosionFactory<P>? = registrations.rowMap()[key]?.values?.single() as? ExplosionFactory<P>

    @Suppress("UNCHECKED_CAST")
    override fun <P : ExplosionFactory.ExplosionParams> getFactory(params: Class<out P>): ExplosionFactory<P>? = registrations.columnMap()[params]?.values?.single() as? ExplosionFactory<P>

    override fun getParamsClass(key: ResourceLocation): Class<out ExplosionFactory.ExplosionParams>? {
        if (!registrations.containsRow(key)) return null
        return registrations.row(key).keys.single() // there should only ever be one column for the key
    }

    override fun getParamsClass(factory: ExplosionFactory<*>): Class<out ExplosionFactory.ExplosionParams>? {
        if (!registrations.containsValue(factory)) return null
        return registrations.cellSet().find { it.value === factory }?.columnKey
    }

    override fun getBuiltinDefault(): ExplosionFactory<NuclearExplosionMk4Params> = getFactory(ExplosionAlgorithmRegistration.Defaults.MK4) ?: throw IllegalStateException("No built-in registered?")
}
