package at.martinthedragon.nucleartech.sorcerer

import at.martinthedragon.nucleartech.sorcerer.registries.RegistryProcessor
import com.google.devtools.ksp.*
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import kotlin.io.path.Path

internal const val PACKAGE_NAME = "at.martinthedragon.nucleartech.sorcerer"

internal class BlackMagic(val generator: CodeGenerator, val logger: KSPLogger, val mcVersion: String?, val kspCorePath: String?) : SymbolProcessor {
    lateinit var resolver: Resolver
    private var passes = -1

    override fun process(resolver: Resolver): List<KSAnnotated> {
        this.resolver = resolver
        passes++

        if (mcVersion == null) {
            if (passes == 0)
                storeAnnotationInfo(Linkage::class, Linkage.Link::class, Linkage.Enum::class, Injected::class, Mirrored::class, EnchantRegistry::class)
            return emptyList()
        }

        if (kspCorePath == null) {
            throw IllegalStateException("Path to core generated KSP resources directory not specified")
        }

        if (passes > 0) return emptyList()
        if (discoverOutputSourceSet(generator) != "main") return emptyList()

        val annotations = readAnnotationInfo(Path(kspCorePath).resolve("annotations.info"))
        val linkageAnnotations = annotations[Linkage::class]
            ?.filter { it.arguments["mcVersion"] == mcVersion }
            ?: return emptyList()
        val linkAnnotations = annotations[Linkage.Link::class]
            ?.filter { it.arguments["mcVersion"] == mcVersion }
            ?: emptyList()
        val enumAnnotations = annotations[Linkage.Enum::class]
            ?.filter { it.arguments["mcVersion"] == mcVersion }
            ?: emptyList()
        val mirrorAnnotations = annotations[Mirrored::class] ?: return emptyList()
        val injected = annotations[Injected::class]?.map(ProtoAnnotation::annotated) ?: emptyList()

        val linkages = linkageAnnotations.associate { (resolver.getClassDeclarationByName(it.annotated) ?: unresolved(it.annotated)) to it.arguments["to"]!!.let { to -> resolver.getClassDeclarationByName(to) ?: unresolved(to) } }
        val links = linkAnnotations.associate { it.annotated to (it.arguments["to"]!! to it.arguments["transCode"]!!) }
        val enums = enumAnnotations.associate { it.annotated to (it.arguments["mappedEnum"] ?: unresolved("mappedEnum in ${it.annotation} on ${it.annotated}")).split(',').map { token -> token.filter(Char::isDigit).toInt() }.toIntArray() }

        val mirrorCandidates = mirrorAnnotations.map { resolver.getClassDeclarationByName(it.annotated) ?: unresolved(it.annotated) }

        val injections = collectAnnotatedDeclarations(Injection::class).associateBy { (it as KSClassDeclaration).superTypes.map(KSTypeReference::resolve).first { s -> s.declaration.qualifiedName!!.asString() in injected }.declaration }

        generateEnumMappingGetters(generator, linkages, enums)

        for (mirrored in mirrorCandidates) {
            if (mirrored.classKind != ClassKind.CLASS) throw IllegalStateException("Mirror class kind ${mirrored.classKind} is not supported yet")

            MirrorWriter(mirrored, linkages, links, injections).write(generator)
        }

        val incantations = Incantations(annotations, linkages, links, injections)
        val registries = annotations[EnchantRegistry::class] ?: return emptyList()
        for (registry in registries) {
            RegistryProcessor(resolver, generator, incantations).collectRegistryEntries(resolver.getClassDeclarationByName(registry.annotated) ?: unresolved(registry.annotated))
        }

        return emptyList()
    }

    data class Incantations(
        val annotations: AnnotationStorage,
        val linkages: Map<KSClassDeclaration, KSClassDeclaration>,
        val links: Map<String, Pair<String, String>>,
        val injections: Map<KSDeclaration, KSDeclaration>,
    )
}
