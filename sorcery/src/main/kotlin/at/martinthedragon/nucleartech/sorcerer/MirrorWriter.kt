package at.martinthedragon.nucleartech.sorcerer

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import java.io.OutputStream

internal class MirrorWriter(
    private val mirrored: KSClassDeclaration,
    private val linkageMap: Map<KSClassDeclaration, KSClassDeclaration?>,
    private val links: Map<String, Pair<String, String>>,
    private val injections: Map<KSDeclaration, KSDeclaration>,
) {
    val outputPackage = mirrored.packageName.asString()
    val mirrorName = "${mirrored.simpleName.asString()}Mirror"
    val qualifiedName = mirrored.qualifiedName!!.asString()
    val qualifiedMirrorName = "$outputPackage.$mirrorName"

    private val mappedSuperTypes = buildMap {
        for (superType in mirrored.superTypes) {
            val resolvedType = superType.resolve()
            val linkage = linkageMap[resolvedType.declaration] ?: continue
            put(linkage, resolvedType)
        }
    }

    fun write(generator: CodeGenerator) {
        val fileOutput = generator.createNewFile(Dependencies.ALL_FILES, outputPackage, mirrorName)

        fileOutput.use { output -> with (output) { indentationScope {
            writePackage(outputPackage)
            writeLine("class $mirrorName(val original: $qualifiedName) : ${mappedSuperTypes.keys.joinToString { it.qualifiedName!!.asString() }} {")

            indent {
                mirrorProperties(output, this@indentationScope)
            }
            writeLine("}")
        }}}
    }

    fun mirrorProperties(output: OutputStream, scope: IndentationScope) {
        val superPropertyMap = mappedSuperTypes.keys.flatMap { it.getAllProperties() }.associateBy { it.simpleName.asString() }
        val superFunctionMap = mappedSuperTypes.keys.flatMap { it.getAllFunctions() }.associateBy { it.simpleName.asString() }
        for (superType in mirrored.getAllSuperTypes()) for (property in superType.declaration.closestClassDeclaration()!!.getAllProperties()) { // TODO error checking
            val qualifiedSuperTypeName = superType.declaration.qualifiedName!!.asString()
            val propertyName = property.simpleName.asString()
            val qualifiedPropertyName = "$qualifiedSuperTypeName.$propertyName"
            val (linkTo, transCode) = links[qualifiedPropertyName] ?: continue
            val resolvedPropertyType = property.type.resolve()
            val coreType = resolvedPropertyType.getClassInfo() ?: unresolved("class info on property type for $qualifiedPropertyName")
            val injectionType = injections[resolvedPropertyType.declaration]?.qualifiedName?.asString()

            val getterName = "get${propertyName.replaceFirstChar(Char::uppercase)}"
            val setterName = "set${propertyName.replaceFirstChar(Char::uppercase)}"

            val nullable = resolvedPropertyType.isMarkedNullable
            val baseFlags = if (nullable) PropertyMember.NULLABLE_FLAG else 0

            val mirroredMember: ClassMember = when {
                propertyName in superPropertyMap -> PropertyMember(propertyName, propertyName, coreType.name, superPropertyMap.getValue(propertyName).type.resolve().declaration.qualifiedName!!.asString(), transCode, injectionType, baseFlags or if (property.isMutable) PropertyMember.LINK_SETTER_FLAG else 0)
                getterName in superFunctionMap && setterName in superFunctionMap -> PropertyMember(propertyName, propertyName, coreType.name, superFunctionMap.getValue(getterName).returnType!!.resolve().declaration.qualifiedName!!.asString(), transCode, injectionType, baseFlags or PropertyMember.FUNCTION_FLAG or PropertyMember.LINK_GETTER_FLAG or PropertyMember.LINK_SETTER_FLAG)
                getterName in superFunctionMap -> PropertyMember(propertyName, getterName, coreType.name, superFunctionMap.getValue(getterName).returnType!!.resolve().declaration.qualifiedName!!.asString(), transCode, injectionType, baseFlags or PropertyMember.FUNCTION_FLAG or PropertyMember.LINK_GETTER_FLAG)
                setterName in superFunctionMap -> PropertyMember(propertyName, setterName, coreType.name, superFunctionMap.getValue(setterName).parameters.first().type.resolve().declaration.qualifiedName!!.asString(), transCode, injectionType, baseFlags or PropertyMember.FUNCTION_FLAG or PropertyMember.LINK_SETTER_FLAG)
                propertyName in superFunctionMap -> PropertyMember(propertyName, propertyName, coreType.name, superFunctionMap.getValue(propertyName).returnType!!.resolve().declaration.qualifiedName!!.asString(), transCode, injectionType, baseFlags or PropertyMember.FUNCTION_FLAG)
                else -> unresolved("any candidate")
            }

            mirroredMember.mirror(output, scope)
        }
    }

    fun mirrorFunctions(output: OutputStream, scope: IndentationScope) {
        val superFunctionMap = mappedSuperTypes.keys.flatMap { it.getAllFunctions() }.associateBy { it.simpleName.asString() }
        for (superType in mirrored.getAllSuperTypes()) for (function in superType.declaration.closestClassDeclaration()!!.getAllFunctions()) {
            val qualifiedSuperTypeName = superType.declaration.qualifiedName!!.asString()
            val functionName = function.simpleName.asString()
            val qualifiedFunctionName = "$qualifiedSuperTypeName.$functionName"
            val (linkTo, transCode) = links[qualifiedFunctionName] ?: continue
            val mirrorFunctionName = linkTo.ifBlank { functionName }
            val mirrorSuperFunction = superFunctionMap[mirrorFunctionName] ?: continue // FIXME weird kotlin bug unresolved("mirror super function '$mirrorFunctionName'")
            val member = FunctionMember(function, mirrorSuperFunction, transCode, injections)
            member.mirror(output, scope)
        }
    }

    sealed interface ClassMember {
        fun mirror(output: OutputStream, scope: IndentationScope)
    }

    class PropertyMember(val core: String, val link: String, val coreType: String, val linkType: String, code: String, val injectionMappedType: String?, val flags: Int) : ClassMember {
        val code = transformCode(code)

        override fun mirror(output: OutputStream, scope: IndentationScope) {
            val mutable = flags and IMMUTABILITY_FLAG == 0
            val linksFunction = flags and FUNCTION_FLAG != 0
            val nullable = flags and NULLABLE_FLAG != 0
            val setter = flags and LINK_SETTER_FLAG != 0
            val getter = flags and LINK_GETTER_FLAG != 0 || !setter
            val directLink = getter != setter

            if (linksFunction) {
                if (getter) {
                    val functionName = if (directLink) link else "get" + link.replaceFirstChar(Char::uppercase)
                    val header = StringBuilder().apply {
                        if (!mutable) append("final ")
                        append("override fun $functionName(): $linkType")
                        if(nullable) append('?')
                        append(" =")
                    }.toString()

                    with(scope) {
                        output.writeLine(header)
                        indent {
                            output.writeLine(code)
                        }
                    }
                }
                if (setter) {
                    val functionName = if (directLink) link else "set" + link.replaceFirstChar(Char::uppercase)
                    val header = StringBuilder().apply {
                        if (!mutable) append("final ")
                        append("override fun $functionName(value: $linkType")
                        if (nullable) append('?')
                        append(") {")
                    }.toString()

                    with(scope) {
                        output.writeLine(header)
                        indent {
                            if (!getter) output.writeLine("super.$functionName(value)")
                            output.writeLine("original.$core = value as $coreType") // TODO casts don't always work, need to instantiate injection types
                        }
                        output.writeLine("}")
                    }
                }
            } else {
                val header = StringBuilder().apply {
                    if (!mutable) append("final ")
                    append("override ")
                    if (setter) append("var ")
                    else append("val ")
                    append("$link: $linkType")
                    if (nullable) append('?')
                }.toString()

                with(scope) {
                    output.writeLine(header)
                    indent {
                        output.writeLine("get() = $code")
                        if (setter)
                            output.writeLine("set(value) { original.$core = value as $coreType }")
                    }
                }
            }
        }

        private fun transformCode(input: String) = input
            .replace("%%", "original.$core")
            .replace("%i", "(original.$core as $injectionMappedType)")
            .replace("%?", "(original.$core as? $injectionMappedType)")

        companion object {
            const val IMMUTABILITY_FLAG = 0b00000001
            const val FUNCTION_FLAG = 0b00000010
            const val NULLABLE_FLAG = 0b00000100
            const val LINK_GETTER_FLAG = 0b00001000
            const val LINK_SETTER_FLAG = 0b00010000
        }
    }

    class FunctionMember(val coreFunction: KSFunctionDeclaration, val mirrorSuperFunction: KSFunctionDeclaration, code: String, val injections: Map<KSDeclaration, KSDeclaration>) : ClassMember {
        private val lineInstructions = code
            .split('|')
            .takeWhile { !it.startsWith('>') }

        private val positionQualifierLength = if (lineInstructions.all { it.length > 1 && it[1].isDigit() }) {
            lineInstructions.minOf { it.drop(1).takeWhile { c -> c.isDigit() }.length }
        } else 0

        private val returnInstructions = code.substringAfter("|>", "")

        override fun mirror(output: OutputStream, scope: IndentationScope) {
            val signature = StringBuilder().apply {
                append("override fun ${mirrorSuperFunction.simpleName.asString()}(")
                for ((index, parameter) in mirrorSuperFunction.parameters.withIndex()) {
                    val parameterType = parameter.type.resolve()
                    if (parameter.isVararg) // TODO proper usage
                        append("vararg ")
                    append("arg$index: ${parameterType.getClassInfo()?.formatType()}")
                    if (parameterType.isMarkedNullable)
                        append('?')
                    append(", ")
                }
                append(')')
                if (mirrorSuperFunction.returnType != null)
                    append(": ${mirrorSuperFunction.returnType!!.resolve().getClassInfo()?.formatType()}")
            }.toString()

            val linesBeforeCall = mutableListOf<String>()
            val linesAfterCall = mutableListOf<String>()
            val callArguments = mutableListOf<String>()

            for ((index, instructions) in lineInstructions.withIndex()) {
                if (instructions.startsWith('%')) {
                    val parameterIndex = if (positionQualifierLength > 0) instructions.substring(1, positionQualifierLength).toInt() else index
                    val coreParameterType = coreFunction.parameters[parameterIndex].type.resolve()
                    val instructionSet = instructions.drop(1 + positionQualifierLength).substringBefore('.')
                    val additionalCode = if ('.' in instructions) instructions.removeRange(0, instructions.indexOf('.')) else ""
                    val listModifier = 'L' in instructionSet

                    val varName = "arg$parameterIndex"

                    for (instruction in instructionSet) when (instruction) {
                        'i' -> when {
                            listModifier -> {
                                val wrappedVar = varName + "Wrapped"
                                val listType = coreParameterType.arguments.first().type?.resolve() ?: unresolved("type in list for ${coreFunction.simpleName.asString()}")
                                linesBeforeCall += "val $wrappedVar = mutableListOf<${listType.getClassInfo()?.formatType()}>()"
                                callArguments += wrappedVar
                                linesAfterCall += "@Suppress(\"UNCHECKED_CAST\")"
                                linesAfterCall += "$wrappedVar as MutableList<${injections[listType.declaration]?.qualifiedName?.asString()}>"
                                linesAfterCall += "$varName.addAll($wrappedVar.map { it.delegate$additionalCode })"
                            }
                            else -> callArguments += injections[coreParameterType.declaration]?.qualifiedName?.asString() + "($varName)$additionalCode"
                        }
                        '?' -> callArguments += "$varName?.let { " + injections[coreParameterType.declaration]?.qualifiedName?.asString() + "(it)$additionalCode }"
                        '%' -> callArguments += varName + additionalCode
                        'e' -> TODO()
                    }
                }
            }

            val returnInstructionSet = returnInstructions.drop(1).substringBefore('.')
            val additionalReturnValueCode = if ('.' in returnInstructions) returnInstructions.removeRange(0, returnInstructions.indexOf('.')) else ""

            val transformedReturnStatement = when {
                'i' in returnInstructionSet -> TODO()
                '?' in returnInstructionSet -> TODO()
                '%' in returnInstructionSet -> TODO()
                'e' in returnInstructionSet -> "returnValue.$ENUM_MAPPING_GETTER_NAME$additionalReturnValueCode"
                else -> ""
            }

            with(scope) {
                output.writeLine("$signature {")
                indent {
                    linesBeforeCall.forEach { output.writeLine(it) }
                    output.writeLine("${if (returnInstructions.isNotBlank()) "val returnValue = " else ""}original.${coreFunction.simpleName.asString()}(${callArguments.joinToString()})")
                    linesAfterCall.forEach { output.writeLine(it) }
                    if (returnInstructionSet.isNotBlank())
                        output.writeLine("return $transformedReturnStatement")
                }
                output.writeLine("}")
            }
        }
    }
}
