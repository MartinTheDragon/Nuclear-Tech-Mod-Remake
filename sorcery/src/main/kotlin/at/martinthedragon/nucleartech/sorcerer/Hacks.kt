package at.martinthedragon.nucleartech.sorcerer

import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSDeclaration
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.reflect.KClass

// hacks to work around some of KSP's limitations

internal fun discoverOutputSourceSet(generator: CodeGenerator): String {
    generator.createNewFile(Dependencies(true), "", "generator", "empty").close()
    return generator.generatedFile.first().toString().substringAfter("/ksp/").substringBefore("/resources/").substringAfterLast('/')
}

internal fun BlackMagic.storeAnnotationInfo(vararg annotations: KClass<out Annotation>) {
    val annotatedDeclarations = annotations.associateWith(::collectAnnotatedDeclarations)
    val dependencies = Dependencies(true, *annotatedDeclarations.values.flatten().mapNotNull(KSAnnotated::containingFile).toTypedArray())
    generator.createNewFile(dependencies, "", "annotations", "info").use { output -> with (output) {
        for ((annotation, annotated) in annotatedDeclarations) {
            writeLine(annotation.qualifiedName!!)
            writeLine(annotated.joinToString("\n") { "|${it.qualifiedName!!.asString()}${
                it.annotations
                    .filter { a -> a.matchesClass(annotation) }
                    .joinToString(prefix = "{", postfix = "}", separator = "") { a ->
                        a.arguments.joinToString(prefix = "(", postfix = ")", separator = ";") { arg ->
                            "${arg.name!!.asString()}=${arg.value}"
                        }
                    }
            }" })
        }
    }}
}

internal fun BlackMagic.collectAnnotatedDeclarations(annotationClass: KClass<out Annotation>): List<KSDeclaration> =
    resolver.getSymbolsWithAnnotation(annotationClass.qualifiedName!!, true).filterIsInstance<KSDeclaration>().toList()

internal fun readAnnotationInfo(infoFilePath: Path): AnnotationStorage {
    val annotations = mutableListOf<ProtoAnnotation>()
    var currentAnnotation: String? = null
    for (line in infoFilePath.readLines()) when {
        !line.startsWith('|') -> currentAnnotation = line
        currentAnnotation == null -> continue
        else -> {
            val annotated = line.substringBefore('{').removePrefix("|")
            val argString = line.substringAfter('{')
            val argsList = argString.removeSurrounding("(", ")}").split(")(")
            if (argsList.first().isBlank()) { // only one annotation instance without arguments
                annotations += ProtoAnnotation(currentAnnotation, annotated, emptyMap())
                continue
            }
            for (args in argsList) { // each entry represents its own annotation instance
                val namedArgs = args.split(';') // all the arguments for this annotation instance
                    .map { val (name, value) = it.split('='); name to value }
                annotations += ProtoAnnotation(currentAnnotation, annotated, namedArgs.toMap())
            }
        }
    }

    // validation
    val groupedAnnotations = annotations.groupBy(ProtoAnnotation::annotation)
    for ((annotation, protoList) in groupedAnnotations) {
        val argumentCount = protoList.first().arguments.size
        for (proto in protoList) if (proto.arguments.size != argumentCount)
            throw Exception("Expected $argumentCount arguments for $annotation annotation on ${proto.annotated}")
    }

    return groupedAnnotations
}

internal operator fun AnnotationStorage.get(annotationKClass: KClass<out Annotation>) =
    get(annotationKClass.qualifiedName)

internal data class ProtoAnnotation(val annotation: String, val annotated: String, val arguments: Map<String, String>)

internal typealias AnnotationStorage = Map<String, List<ProtoAnnotation>>
