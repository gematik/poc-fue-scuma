import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("de.gematik.kether.codegen") version "1.0-SNAPSHOT"
    application
}

group = "de.gematik.scuma"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("de.gematik.kether:kether:1.0-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.4.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

configure<de.gematik.kether.codegen.CodeGeneratorPluginExtension> {
    packageName.set("de.gematik.scuma.contracts")
}