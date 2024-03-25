import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("de.gematik.kether.codegen") version "1.3.1"
    id("maven-publish")
}

group = "de.gematik.scuma"
version = "1.0"
val ktor_version = "2.2.2"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("de.gematik.kether:kether:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("org.junit.platform:junit-platform-suite-engine:1.9.1")
    implementation("io.ktor:ktor-server-core-jvm:2.2.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.2")
    implementation("io.ktor:ktor-server-default-headers:2.2.2")
    implementation("io.ktor:ktor-server-html-builder:2.2.2")
    implementation("io.ktor:ktor-client-core-jvm:2.2.2")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-json:$ktor_version")
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
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}