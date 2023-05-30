package at.martinthedragon.nucleartech.sorcerer

import java.io.OutputStream

internal class IndentationScope {
    private var indentation = 0

    inline fun indent(block: IndentationScope.() -> Unit) {
        indentation++
        block()
        indentation--
    }

    fun OutputStream.writeLine() = write('\n'.code)
    fun OutputStream.writeLine(string: String) = write((string.prependIndent("    ".repeat(indentation)) + '\n').toByteArray())

    internal fun OutputStream.writePackage(name: String) = writeLine("package $name\n")

    internal fun OutputStream.writeImport(import: String) = writeLine("import $import")

    internal fun OutputStream.writeClass(className: String, typeArguments: List<String> = emptyList(), constructorArgs: List<String> = emptyList(), superTypes: List<String> = emptyList(), body: IndentationScope.() -> Unit = {}): ClassInfo {
        writeLine("open class $className" + (if (typeArguments.isNotEmpty()) typeArguments.joinToString(prefix = "<", postfix = ">") else "") + constructorArgs.joinToString(prefix = "(", postfix = ")") + (if (superTypes.isNotEmpty()) " : " + superTypes.joinToString() else "") + " {")
        indent(body)
        writeLine("}")
        return ClassInfo(className, typeArguments, constructorArgs, superTypes)
    }
}

internal inline fun indentationScope(block: IndentationScope.() -> Unit) =
    IndentationScope().block()
