enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/releases")
    }
}

plugins {
    id("com.highcapable.gropify") version "1.0.1"
}

gropify {
    global {
        android {
            className = "FlexiUI"
            includeKeys("^project\\..*\$".toRegex())
            isRestrictedAccessEnabled = true
        }
    }

    rootProject {
        common {
            isEnabled = false
        }
    }

    projects(
        ":samples",
        ":samples:androidApp",
        ":samples:desktopApp",
        ":samples:composeApp",
        ":flexiui-core",
        ":flexiui-resources"
    ) {
        android {
            isEnabled = false
        }
        jvm {
            isEnabled = false
        }
        kmp {
            isEnabled = false
        }
    }
}

rootProject.name = "FlexiUI"

include(":samples:androidApp", ":samples:desktopApp", ":samples:composeApp")
include(":flexiui-core", ":flexiui-resources")