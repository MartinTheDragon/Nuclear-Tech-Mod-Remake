package at.martinthedragon.nucleartech.sorcerer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration

internal const val ENUM_MAPPING_GETTER_NAME = "magicallyVersionMappedEnum"

internal fun generateEnumMappingGetters(codeGenerator: CodeGenerator, linkages: Map<KSClassDeclaration, KSClassDeclaration>, enums: Map<String, IntArray>) {
    codeGenerator.createNewFile(Dependencies.ALL_FILES, PACKAGE_NAME, "MappedEnums").use { output ->
        indentationScope {
            output.writePackage(PACKAGE_NAME)

            for ((coreEnum, versionEnum) in linkages) {
                if (coreEnum.classKind != ClassKind.ENUM_CLASS) continue
                val coreEnumName = coreEnum.qualifiedName!!.asString()
                val versionEnumName = versionEnum.qualifiedName!!.asString()
                val enumMap = enums[coreEnumName]

                val propertyDeclaration = "val $coreEnumName.$ENUM_MAPPING_GETTER_NAME get() = "
                val propertyDefinition = if (enumMap != null) {
                    val coreEnumMap = "map${coreEnumName.substringAfterLast('.')}"
                    output.writeLine("private val $coreEnumMap = intArrayOf(${enumMap.joinToString()})")
                    "$versionEnumName.values()[$coreEnumMap[ordinal]]"
                } else {
                    "$versionEnumName.values()[ordinal]"
                }

                output.writeLine(propertyDeclaration + propertyDefinition)
            }
        }
    }
}
