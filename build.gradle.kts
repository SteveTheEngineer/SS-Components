import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
    kotlin("jvm") version "1.8.0"
    id("java-library")
    id("com.github.SteveTheEngineer.SS-BukkitGradle") version "1.6"
    `maven-publish`
}

group = "me.ste.stevesseries.components2"
version = "0.0.0-mc1.19.3"
description = "Components are functional elements designed with RPG servers in mind. Has all tools needed to create a fancy door or, the main attraction of this mod, an interactive map."

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://jitpack.io")
}

allprojects {
    apply<KotlinPluginWrapper>()
    apply<MavenPublishPlugin>()

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    }

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        compileOnly(kotlin("stdlib"))
    }
}

dependencies {
    softDepend("com.github.SteveTheEngineer:SS-Kotlin:1.8.0")

    compileOnly("com.github.SteveTheEngineer.SS-Base:base-api:0.0.0-mc1.19.3")
    dependRuntimeOnly("com.github.SteveTheEngineer.SS-Base:base:0.0.0-mc1.19.3")

    api(project(":API"))
}

tasks {
    jar {
        from(
            project(":API").sourceSets.main.get().output,
            project(":Compat").sourceSets.main.get().output
        )
    }
}

runServer {
    downloadUri.set("https://api.papermc.io/v2/projects/paper/versions/1.19.3/builds/448/downloads/paper-1.19.3-448.jar")
}

pluginDescription {
    mainClass.set("me.ste.stevesseries.base.Base")
    apiVersion.set("1.16")
    authors.add("SteveTheEngineer")
}

publishing {
    publications {
        create<MavenPublication>("plugin") {
            artifactId = "base"
            group = "com.github.SteveTheEngineer.SS-Base"

            from(components.getByName("java"))
        }
    }
}

//import org.apache.tools.ant.filters.ReplaceTokens
//
//apply plugin: "java"
//
//group = "me.ste.stevesseries"
//version = "0"
//if(System.env.BUILD_NUMBER != null) {
//    version = "" + System.env.BUILD_NUMBER
//}
//
//sourceCompatibility = targetCompatibility = "1.8"
//
//repositories {
//    mavenCentral()
//    maven {
//        name = "spigotmc-repo"
//        url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
//    }
//    maven {
//        name = "sonatype"
//        url = "https://oss.sonatype.org/content/groups/public/"
//    }
//    maven {
//        name = "generic"
//        url = "https://repo.stev.gq/repository/generic/"
//    }
//}
//
//dependencies {
//    compileOnly "org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT"
//    compileOnly "me.ste.stevesseries:base:24"
//    compileOnly "me.ste.stevesseries:inventoryguilibrary:9"
//    compileOnly "org.jetbrains:annotations:16.0.2"
//}
//
//processResources {
//    from(sourceSets.main.resources.srcDirs) {
//        filter ReplaceTokens, tokens: [version: version]
//    }
//}

