plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget()
    jvmToolchain(17)
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(projects.samples.composeApp)
            }
        }
    }
}

android {
    namespace = gropify.project.samples.androidApp.packageName
    compileSdk = gropify.project.android.compileSdk

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = gropify.project.samples.androidApp.packageName
        minSdk = gropify.project.android.minSdk
        targetSdk = gropify.project.android.targetSdk
        versionName = gropify.project.samples.androidApp.versionName
        versionCode = gropify.project.samples.androidApp.versionCode
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val composeAndroidVersion = libs.androidx.compose.android.get().version ?: error("Unresolved compose android version.")

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group.startsWith("androidx.compose."))
            useVersion(composeAndroidVersion)
    }
}