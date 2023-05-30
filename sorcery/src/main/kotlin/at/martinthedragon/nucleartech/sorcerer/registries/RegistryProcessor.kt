package at.martinthedragon.nucleartech.sorcerer.registries

import at.martinthedragon.nucleartech.sorcerer.*
import at.martinthedragon.nucleartech.sorcerer.BlackMagic
import at.martinthedragon.nucleartech.sorcerer.indentationScope
import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*

internal class RegistryProcessor(val resolver: Resolver, val generator: CodeGenerator, val incantations: BlackMagic.Incantations) {
    val candidateInterface = resolver.getClassDeclarationByName<RegistryCandidate<*>>()!!.qualifiedName!!.asString()

    fun collectRegistryEntries(registry: KSClassDeclaration) {

        val mirrorEntryFilePackage = "at.martinthedragon.nucleartech.sorcerer.registries"
        val mirrorEntryFile = generator.createNewFile(Dependencies.ALL_FILES, mirrorEntryFilePackage, "MirroredRegistryEntries")

        indentationScope {
            mirrorEntryFile.writePackage(mirrorEntryFilePackage)
        }

        var baseMirrorPropertyGenerated = false
        for (property in registry.getAllProperties()) {
            val propertyType = property.type.resolve()
            val typeArgument = propertyType.arguments.firstOrNull() ?: continue // TODO
            val resolvedType = typeArgument.type?.resolve() ?: TODO()
            if (resolvedType.nullability == Nullability.NULLABLE) throw TODO()

            val result = buildMirrorTree(resolvedType)

//            if (!baseMirrorPropertyGenerated) {
//                val (baseCandidate, baseCandidateInfo) = findLinkageBaseClass(resolvedType) ?: continue
//                with (mirrorEntryFile) { indentationScope {
//                    writeLine("val RegistryCandidate<${baseCandidateInfo.formatType()}>.mirroredEntry: AutoGenRegistryEntry<${baseCandidateInfo.formatType()}, ${incantations.linkages[baseCandidate]?.qualifiedName?.asString()}>")
//                    indent {
//                        writeLine("get() = ${result.formatType()}(${registry.qualifiedName!!.asString()}.${property.simpleName.asString()}.get())")
//                    }
//                }}
//                baseMirrorPropertyGenerated = true
//            }
        }

        mirrorEntryFile.close()
    }

    private fun buildMirrorTree(type: KSType): ClassInfo {
        val declaration = type.declaration as KSClassDeclaration
        val superTypes = declaration.superTypes.map(KSTypeReference::resolve)
        val classSuperType = superTypes.find { it.declaration.closestClassDeclaration()?.classKind == ClassKind.CLASS } ?: throw IllegalStateException("Class with no class super type: ${declaration.qualifiedName?.asString()}")
        if (classSuperType == resolver.builtIns.anyType) throw IllegalStateException("No valid base candidate for hierarchy found")

        val linkageSuperType = incantations.linkages[classSuperType.declaration]?.asType(emptyList())?.getClassInfo()
        val mirroredClassSuperType = linkageSuperType ?: buildMirrorTree(classSuperType)
        val superTypeIsGenerated = linkageSuperType == null
        val interfaces = (superTypes - classSuperType).mapNotNull { incantations.linkages[it.declaration]?.asType(emptyList())?.getClassInfo() }

        val mirroredSuperTypes = listOf(mirroredClassSuperType.formatHeaderInheritance(constructorArgOffset = if (superTypeIsGenerated) 0 else 1)) + interfaces.map(ClassInfo::formatHeaderInheritance)
            .run { if (linkageSuperType != null) this + "at.martinthedragon.nucleartech.sorcerer.registries.AutoGenRegistryEntry<${classSuperType.getClassInfo()?.formatType()}, ${linkageSuperType.formatType()}>" else this }

        val packageName = declaration.packageName.asString()
        val declarationName = declaration.qualifiedName!!.asString()
        val qualifiedMirrorName = declaration.qualifiedName!!.asString() + "Mirror"
        val mirrorName = declaration.simpleName.asString() + "Mirror"
        val fileOutput = generator.createNewFile(Dependencies.ALL_FILES, packageName, mirrorName)
        val mirrorSupport = MirrorWriter(declaration, incantations.linkages, incantations.links, incantations.injections)

        val constructorArgs = listOf(declarationName) + (mirroredClassSuperType.constructorArgs?.let { if (superTypeIsGenerated) it.drop(1) else it } ?: emptyList())

        fileOutput.use { output -> with (output) { indentationScope {
            writePackage(packageName)
            writeImport("$PACKAGE_NAME.*")
            writeLine()

            writeClass(mirrorName, constructorArgs = constructorArgs.mapIndexed { index, arg -> "arg$index: $arg" }, superTypes = mirroredSuperTypes) {
                writeLine((if (superTypeIsGenerated) "override" else "open") + " val original = arg0")

                mirrorSupport.mirrorProperties(output, this@indentationScope)
                mirrorSupport.mirrorFunctions(output, this@indentationScope)
            }
        }}}

        return ClassInfo(qualifiedMirrorName, emptyList(), constructorArgs, mirroredSuperTypes)
    }

//    private fun findLinkageBaseClass(subType: KSType): Pair<KSClassDeclaration, ClassInfo>? {
//        var currentType = subType.declaration.closestClassDeclaration() ?: return null
//        do {
//            currentType = currentType.superTypes.find { it.resolve().declaration.closestClassDeclaration()?.classKind == ClassKind.CLASS }?.resolve()?.declaration?.closestClassDeclaration() ?: return null
//        } while (currentType.superTypes.none { it.resolve().declaration.closestClassDeclaration()?.qualifiedName?.asString() == RegistryCandidate::class.qualifiedName })
//        return currentType to currentType.asType(emptyList()).getClassInfo()!! // TODO types
//    }
}
