package at.martinthedragon.nucleartech.datagen.model

import at.martinthedragon.nucleartech.ntm
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.CustomLoaderBuilder
import net.minecraftforge.client.model.generators.ModelBuilder
import net.minecraftforge.common.data.ExistingFileHelper

class RandomModelBuilder<T : ModelBuilder<T>>(parent: T, existingFileHelper: ExistingFileHelper) : CustomLoaderBuilder<T>(ntm("random"), parent, existingFileHelper, ) {
    private var idSupplier: ResourceLocation? = null
    private val models = mutableListOf<T>()

    fun idSupplier(supplier: ResourceLocation): RandomModelBuilder<T> {
        idSupplier = supplier
        return this
    }

    fun randomModel(modelBuilder: T): RandomModelBuilder<T> {
        models += modelBuilder
        return this
    }

    override fun toJson(json: JsonObject): JsonObject = super.toJson(json).apply {
        if (idSupplier != null) addProperty("id_supplier", idSupplier.toString())
        add("models", JsonArray(models.size).apply {
            for (model in models) add(model.toJson())
        })
    }
}
