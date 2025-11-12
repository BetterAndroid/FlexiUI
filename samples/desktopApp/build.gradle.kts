plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = gropify.project.samples.desktopApp.groupName
version = gropify.project.samples.desktopApp.version

kotlin {
    jvm("desktop")
    jvmToolchain(17)

    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(projects.samples.composeApp)
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

compose.desktop {
    application {
        mainClass = "$group.MainKt"
    }
}