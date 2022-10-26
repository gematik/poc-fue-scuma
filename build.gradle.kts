import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("de.gematik.kether.codegen") version "1.0-SNAPSHOT"
}

group = "de.gematik.scuma"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://hyperledger.jfrog.io/artifactory/besu-maven/")
    }

}

dependencies {
    implementation("de.gematik.kether:kether:1.0-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configure<de.gematik.kether.codegen.CodeGeneratorPluginExtension> {
    packageName.set("de.gematik.scuma.contracts")
}