rootProject.name = "SS-Components"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://jitpack.io")
    }
}

include("API", "Compat")
