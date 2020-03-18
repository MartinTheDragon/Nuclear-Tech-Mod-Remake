version = rootProject.version
group = "at.martinthedragon"

plugins {
    id("com.eden.orchidPlugin") version "0.19.0"
}

repositories {
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://jitpack.io")
}

dependencies {
    orchidImplementation("io.github.javaeden.orchid:OrchidCore:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidEditorial:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidPluginDocs:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidSearch:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidGithub:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidChangelog:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidPages:0.19.0")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidWiki:0.19.0")
}

orchid {
    theme = "Editorial"

    version = "${project.version}"
    baseUrl = "https://martinthedragon.github.io/Nuclear-Tech-Mod-Remake"
}
