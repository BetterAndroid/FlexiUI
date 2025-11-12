plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.maven.publish)
}

group = gropify.project.groupName
version = gropify.project.flexiui.core.version

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
            baseName = gropify.project.flexiui.core.iosModuleName
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
                implementation(libs.composeExt.material.ripple)
                api(projects.flexiuiResources)
                api(libs.betterandroid.compose.extension)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.betterandroid.ui.extension)
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
    namespace = gropify.project.flexiui.core.namespace
    compileSdk = gropify.project.android.compileSdk

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = gropify.project.android.minSdk
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