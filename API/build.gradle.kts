group = "me.ste.stevesseries.components2"
version = rootProject.version

dependencies {
    api("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("api") {
            artifactId = "components-api"
            group = "com.github.SteveTheEngineer.SS-Components"

            from(components.getByName("java"))
        }
    }
}