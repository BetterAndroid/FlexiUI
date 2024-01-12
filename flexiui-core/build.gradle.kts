plugins {
    autowire(libs.plugins.kotlin.multiplatform)
    autowire(libs.plugins.android.library)
    autowire(libs.plugins.jetbrains.compose)
    autowire(libs.plugins.maven.publish)
}

group = property.project.groupName
version = property.project.flexiui.core.version

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }
    jvm("desktop")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = property.project.flexiui.core.iosModuleName
            isStatic = true
        }
    }
    jvmToolchain(17)
    sourceSets {
        all {
            languageSettings {
                optIn("com.highcapable.flexiui.component.window.ExperimentalFlexiDialogScrimAnimated")
                optIn("com.highcapable.flexiui.ExperimentalFlexiUISizesApi")
                optIn("androidx.compose.ui.ExperimentalComposeUiApi")
                optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                // Mark foundation with api like material do.
                api(compose.foundation)
                implementation(composeExt.material.ripple)
                api(projects.flexiuiResources)
                api(com.highcapable.betterandroid.compose.extension)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(com.highcapable.betterandroid.ui.extension)
            }
        }
        val desktopMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = property.project.flexiui.core.namespace
    compileSdk = property.project.android.compileSdk

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = property.project.android.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}