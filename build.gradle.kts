buildscript {
    dependencies {
        classpath("net.minecraftforge.gradle", "ForgeGradle", "3.+")
    }
}

apply(plugin = "net.minecraftforge.gradle")

plugins {
    kotlin("jvm") version "1.3.70"
}

version = "1.14.4-0.0.3.0"
group = "at.martinthedragon"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    "minecraft"("net.minecraftforge:forge:1.14.4-28.1.0")
}
