val orchidVersion = "0.21.0"

version = rootProject.version
group = "at.martinthedragon"

plugins {
    id("com.eden.orchidPlugin") version "0.21.0"
    kotlin("jvm")
}

repositories {
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://jitpack.io")
}

dependencies {
    orchidImplementation("io.github.javaeden.orchid:OrchidCore:$orchidVersion")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidPluginDocs:$orchidVersion")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidSearch:$orchidVersion")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidGithub:$orchidVersion")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidChangelog:$orchidVersion")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidPages:$orchidVersion")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidWiki:$orchidVersion")
}

orchid {
    version = "${project.version}"
    baseUrl = "https://martinthedragon.github.io/Nuclear-Tech-Mod-Remake"
}
