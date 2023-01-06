import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("de.gematik.kether.codegen") version "1.0-SNAPSHOT"
    id("maven-publish")
}

group = "de.gematik.scuma"
version = "1.0-SNAPSHOT"

repositories {
    maven(url="https://repo.labor.gematik.de/repository/maven-public/")
    mavenCentral()
    maven(url ="https://hyperledger.jfrog.io/artifactory/besu-maven/")
}

dependencies {
    implementation("de.gematik.kether:kether:1.0-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")
    implementation("org.hyperledger.besu.internal:crypto:22.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.junit.platform:junit-platform-suite-engine:1.9.1")
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

publishing {
    publications {
        create<MavenPublication>("scumaJar") {
            from(components["java"])
        }
    }
}