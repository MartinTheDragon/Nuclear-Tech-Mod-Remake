package at.martinthedragon.nucleartech.sorcerer

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

internal class TheForbiddenTome : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) =
        BlackMagic(environment.codeGenerator, environment.logger, environment.options["mcVersion"], environment.options["kspCorePath"])
}
