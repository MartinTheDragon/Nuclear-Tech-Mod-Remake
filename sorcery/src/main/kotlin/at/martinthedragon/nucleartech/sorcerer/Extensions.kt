package at.martinthedragon.nucleartech.sorcerer

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Nullability
import java.io.OutputStream
import kotlin.reflect.KClass

internal fun OutputStream.writeLine(string: String) = write((string + '\n').toByteArray())

internal fun KSAnnotation.matchesClass(annotationKClass: KClass<out Annotation>): Boolean =
    shortName.getShortName() == annotationKClass.simpleName && annotationType.resolve().declaration.qualifiedName?.asString() == annotationKClass.qualifiedName

internal fun KSType.getClassInfo(): ClassInfo? {
    val name = declaration.qualifiedName?.asString() ?: return null
    val typeArguments = arguments.map { val type = it.type!!.resolve(); val label = it.variance.label; "${if (label.isNotBlank()) "$label " else ""}${type.declaration.qualifiedName!!.asString()}${if (type.nullability == Nullability.NULLABLE) "?" else ""}" }
    val closestClassDeclaration = declaration.closestClassDeclaration() ?: return null
    val constructorArgs = closestClassDeclaration.getConstructors().firstOrNull()?.parameters?.map { it.type.resolve().declaration.closestClassDeclaration()!!.qualifiedName!!.asString() }
    val superTypes = closestClassDeclaration.superTypes.mapNotNull { it.resolve().getClassInfo()?.formatHeaderInheritance() }.toList()
    return ClassInfo(name, typeArguments, constructorArgs, superTypes)
}
