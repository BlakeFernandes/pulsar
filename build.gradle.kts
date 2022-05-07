import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("maven-publish")
    application
}

group = "me.blake.pulsar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { setUrl("https://repo.codemc.org/repository/maven-public/") }
    maven { setUrl("https://libraries.minecraft.net/") }
}

dependencies {
    // ANNOTATION
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // LIBRARIES
    compileOnly("com.mojang:authlib:1.5.21")
    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    implementation("com.zaxxer:HikariCP:2.3.2")
    implementation("me.lucko:sql-streams:1.0.0")

    // HELPER LIBRARIES
    compileOnly("me.lucko:helper:5.6.9")
    implementation("com.github.cryptomorin:XSeries:8.+")
    implementation("de.tr7zw:item-nbt-api-plugin:2.9.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}