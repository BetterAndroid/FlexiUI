enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
plugins {
    id("com.highcapable.sweetdependency") version "1.0.4"
    id("com.highcapable.sweetproperty") version "1.0.5"
}
sweetDependency {
    isUseDependencyResolutionManagement = false
}
sweetProperty {
    global {
        sourcesCode {
            className = "FlexiUI"
            includeKeys("^project\\..*\$".toRegex())
            isEnableRestrictedAccess = true
        }
    }
    rootProject { all { isEnable = false } }
    project(
        ":samples",
        ":samples:androidApp",
        ":samples:desktopApp",
        ":samples:composeApp",
        ":flexiui-core",
        ":flexiui-resources"
    ) { sourcesCode { isEnable = false } }
}
rootProject.name = "FlexiUI"
include(":samples:androidApp", ":samples:desktopApp", ":samples:composeApp")
include(":flexiui-core", ":flexiui-resources")