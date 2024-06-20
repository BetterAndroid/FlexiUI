plugins {
    autowire(libs.plugins.kotlin.multiplatform)
    autowire(libs.plugins.jetbrains.compose)
    autowire(libs.plugins.compose.compiler)
}

group = property.project.samples.desktopApp.groupName
version = property.project.samples.desktopApp.version

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