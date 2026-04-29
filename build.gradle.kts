plugins {
    kotlin("jvm") version "2.4.0-Beta1"
    id("com.gradleup.shadow") version "9.4.1"
    id("dev.meluhdy.multi-module") version "1.0.0"
    `maven-publish`
}

group = "dev.meluhdy"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    multiModule.tryMultiModule(":Melodia", "dev.meluhdy:melodia:1.0-SNAPSHOT")
    multiModule.tryMultiModule(":ScovilleCore", "dev.meluhdy:scoville-core:1.0-SNAPSHOT")
    multiModule.tryMultiModule(":ScovilleAchievements", "dev.meluhdy:scoville-achievements:1.0-SNAPSHOT")
}

val targetJavaVersion = 25
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
    finalizedBy(tasks.publishToMavenLocal)
}

tasks.processResources {
    val props = mapOf("version" to version, "description" to description)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:.*"))
        exclude(dependency("org.jetbrains.kotlinx:kotlinx-serialization-core"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "scoville-ui"
            version = project.version.toString()

            from(components["shadow"])
        }
    }
}