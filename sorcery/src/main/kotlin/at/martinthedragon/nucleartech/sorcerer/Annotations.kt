package at.martinthedragon.nucleartech.sorcerer

import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.*

/**
 * Tells the compiler plugin that this class represents an API stub to a library implementation somewhere else.
 *
 * Grants the compiler plugin the ability to generate code that uses vanilla code.
 *
 * The annotation can be applied multiple times for different [mcVersion]s.
 *
 * @param[mcVersion] Should be one of [CompatibleVersions]
 * @param[to] The fully-qualified name of the class to link to
 *
 * @see Link
 * @see Mirrored
 * @see Enum
 */
@Target(CLASS)
@Retention(SOURCE)
@Repeatable
@MustBeDocumented
annotation class Linkage(val mcVersion: String, val to: String) {
    /**
     * Minecraft versions compatible with the compiler plugin's APIs.
     *
     * When using the compiler plugin in the `build.gradle` file, pass one of these strings as the `mcVersion` argument.
     */
    companion object CompatibleVersions {
        const val MC12 = "1.12.2"
        const val MC18 = "1.18.2"
    }

    /**
     * Tells the compiler plugin what class member in the library implementation to link to, and how to translate usages of this member.
     *
     * This will be used generate so-called class "mirrors" in version-specific code with overrides of these annotated members.
     * The generated overrides will transform parameters of the annotated class member to be compatible with the linked class member.
     *
     * The containing class needs to be annotated with [Linkage] for this to work.
     *
     * Table of value transformation designators:
     *
     * | Designator | Effect |
     * | ---------- | ------ |
     * | `%%` | Passes the parameter along directly |
     * | `%i` | Converts an [Injected] type into the version-specific [Injection] type |
     * | `%?` | Converts a nullable [Injected] type into the version-specific nullable [Injection] type |
     * | `%e` | Converts a [Linkage] [Enum] value into the version-specific mapped enum value |
     *
     * Table of value transformation modifiers, which can be applied to transformation designators by appending them:
     *
     * | Modifier | Effect |
     * | -------- | ------ |
     * | L | Wraps a list parameter in a secondary list, with each member having the transformation applied afterward. |
     *
     * The annotation can be applied multiple times for different [mcVersion]s.
     *
     * @param[mcVersion] Should be one of [CompatibleVersions]
     * @param[transCode] A pipe-separated list of transformation designators for the annotated member's parameters, optionally with code after the designator, with a right angle bracket for the return type, for example `"%i.delegate|%?L|>%%"`
     * @param[to] Needs to be specified when the linked member name doesn't match the annotated member name
     *
     * @see Mirrored
     */
    @Target(FIELD, FUNCTION, PROPERTY, PROPERTY_GETTER, PROPERTY_SETTER)
    @Retention(SOURCE)
    @Repeatable
    @MustBeDocumented
    annotation class Link(val mcVersion: String, val transCode: String, val to: String = "")

    /**
     * Can be applied on enums along with a [Linkage] annotation if the enum values don't match for certain [mcVersion]s.
     *
     * Additional functions for mapping enum values to their corresponding linked versions will be generated and used in generated code.
     *
     * @param[mcVersion] Should be one of [CompatibleVersions]
     * @param[mappedEnum] An int array where each entry maps its index to the linked enum's ordinals
     */
    @Target(CLASS)
    @Retention(SOURCE)
    @Repeatable
    @MustBeDocumented
    annotation class Enum(val mcVersion: String, val mappedEnum: IntArray)

    /**
     * Marks parameters of a [Link]ed function as unused, offering an optimization to reduce class allocation overhead when transforming parameters.
     *
     * The parameter must then either be nullable, the parameter must have a default value or the parameter's type must have a marked default/empty value.
     *
     * It doesn't matter whether an override uses the parameter.
     *
     * **Be very vigilant when using this or things might break!**
     */
    @Target(VALUE_PARAMETER)
    @Retention(SOURCE)
    @MustBeDocumented
    annotation class Unused

    /**
     * Generates API stubs for [Linkage] annotated classes based on [Link]s.
     *
     * Stubs will be generated at [at.martinthedragon.nucleartech.sorcerer.generated.ApiStubs] and will be extensions
     * of the [Linkage] annotated class, with `Stub` appended to each member's name.
     *
     * All stubs will have automatically generated implementations that link to their version-specific implementations,
     * unless annotated with [ManualStub].
     */
    @Target(CLASS)
    @Retention(SOURCE)
    annotation class ConjureStubs {
        /**
         * Can be applied to members of [ConjureStubs] annotated classes to exclude them from API stub generation
         * and handle implementations manually instead.
         *
         * Only required if the member is annotated with [Link].
         *
         * Optionally, [mcVersions] can be specified to only apply this on specific Minecraft versions.
         */
        @Target(FIELD, FUNCTION, PROPERTY, PROPERTY_GETTER, PROPERTY_SETTER)
        @Retention(SOURCE)
        annotation class ManualStub(vararg val mcVersions: String = [])
    }
}

/**
 * Forces generation of a "mirror" class of the annotated class.
 *
 * The mirror class will be subtype of a class hierarchy where the linked class of the [Linkage] annotated class in the original hierarchy is at the top.
 * If no [Linkage] annotated class is present in the super-types of this annotated class, a compile-time error will be thrown.
 *
 * Each [Linkage.Link] annotated class member in the [Linkage] annotated class will be mirrored with the actual, version-specific and linked types, effectively creating a wrapper
 * that makes core classes be compatible with Minecraft ones.
 *
 * @see EnchantRegistry
 */
@Target(CLASS)
@Retention(SOURCE)
@MustBeDocumented
annotation class Mirrored

/**
 * Generates a "mirrored" registry in version-specific code where each entry uses an auto-generated [Mirrored] type.
 *
 * All class declarations that store values of subtypes of [Linkage] annotated classes directly
 * or wrapped in a registry object with type parameters can technically use this annotation.
 */
@Target(CLASS)
@Retention(SOURCE)
@MustBeDocumented
annotation class EnchantRegistry

/**
 * Marks an abstract class declaration in core code as having version-specific [Injection] implementations in version-specific submodules.
 *
 * Can be used to provide APIs from version-specific libraries to core code and have the compiler plugin generate code that uses them.
 *
 * The injection mechanism uses the [Koin](https://insert-koin.io) dependency-injection library.
 *
 * @see Linkage.Link
 */
@Target(CLASS)
@Retention(SOURCE)
@MustBeDocumented
annotation class Injected

/**
 * Marks a class in version-specific code as being an implementation of an [Injected] class in core code.
 */
@Target(CLASS)
@Retention(SOURCE)
@MustBeDocumented
annotation class Injection
