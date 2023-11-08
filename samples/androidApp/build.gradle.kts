plugins {
    autowire(libs.plugins.kotlin.multiplatform)
    autowire(libs.plugins.android.application)
    autowire(libs.plugins.jetbrains.compose)
}

group = property.project.sharedApp.packageName

kotlin {
    androidTarget()
    jvmToolchain(17)
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(projects.samples.shared)
            }
        }
    }
}

android {
    namespace = property.project.sharedApp.packageName
    compileSdk = property.project.android.compileSdk

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = property.project.sharedApp.packageName
        minSdk = property.project.android.minSdk
        targetSdk = property.project.android.targetSdk
        versionName = property.project.androidApp.versionName
        versionCode = property.project.androidApp.versionCode
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}