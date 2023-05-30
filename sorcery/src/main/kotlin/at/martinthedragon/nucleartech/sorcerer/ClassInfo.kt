package at.martinthedragon.nucleartech.sorcerer

internal data class ClassInfo(val name: String, val typeArguments: List<String>, val constructorArgs: List<String>?, val superTypes: List<String>) {
    fun formatHeaderInheritance(constructorArgOffset: Int = 0) = name + formatTypeArguments() + formatConstructorArguments(constructorArgOffset)
    fun formatType() = name + formatTypeParameters()

    fun formatConstructorArguments(indexOffset: Int = 0) = if (constructorArgs == null) "" else buildList {
        for (i in constructorArgs.indices)
            add("arg${i + indexOffset}")
    }.joinToString(prefix = "(", postfix = ")")

    fun formatTypeArguments() = typeArguments
        .apply { if (isEmpty()) return "" }
        .mapIndexed { index, argument -> getTypeArgumentLabel(argument) + 'T' + index + if (getTypeArgumentNullability(argument)) "?" else "" }
        .joinToString(prefix = "<", postfix = ">")

    fun formatTypeParameters() = typeArguments
        .apply { if (isEmpty()) return "" }
        .joinToString(prefix = "<", postfix = ">")

    private fun getTypeArgumentLabel(typeArgument: String) = when {
        typeArgument.startsWith("out") -> "out "
        typeArgument.startsWith("in") -> "in "
        else -> ""
    }

    private fun getTypeArgumentNullability(typeArgument: String) = typeArgument.endsWith('?')
}
