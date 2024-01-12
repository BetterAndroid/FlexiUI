plugins {
    autowire(libs.plugins.kotlin.multiplatform)
    autowire(libs.plugins.android.application)
    autowire(libs.plugins.jetbrains.compose)
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
    namespace = property.project.samples.androidApp.packageName
    compileSdk = property.project.android.compileSdk

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = property.project.samples.androidApp.packageName
        minSdk = property.project.android.minSdk
        targetSdk = property.project.android.targetSdk
        versionName = property.project.samples.androidApp.versionName
        versionCode = property.project.samples.androidApp.versionCode
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

val composeAndroidVersion = dependencies.androidx.compose.android.version

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group.startsWith("androidx.compose."))
            useVersion(composeAndroidVersion)
    }
}