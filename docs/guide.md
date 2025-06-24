# Flexi UI Documentation (Jetpack Compose)

![Maven Central](https://img.shields.io/maven-central/v/com.highcapable.flexiui/core?logo=apachemaven&logoColor=orange&label=core&style=flat-square)
![Maven Central](https://img.shields.io/maven-central/v/com.highcapable.flexiui/resources?logo=apachemaven&logoColor=orange&label=resources&style=flat-square)

> ⚠️ Notice
>
> Flexi UI is still under development and testing, and the API may change before the `1.0.0` version is officially released.
>
> We are welcome you to make suggestions to us at [GitHub Issues](https://github.com/BetterAndroid/FlexiUI/issues).
>
> We recommend that you use the [Dev Version](#dev-version) first, which is actively under development and synchronizes the latest code from the
> current branch.

Before you start using it, it is recommended that you read this document carefully so that you can better understand how it works and its functions.

You can use it together with [BetterAndroid](https://github.com/BetterAndroid/BetterAndroid) for better results.

## Configure Dependency

Flexi UI is divided into two modules: `core` and `resources`,
normally, you only need to add `core`, and `resources` will be automatically introduced as an API.

You can add this module to your project using the following method.

This is a Kotlin Multiplatform dependency, you need the `org.jetbrains.kotlin.multiplatform` plugin to apply the relevant dependencies.

We recommend using Kotlin DSL as the Gradle build script language and [SweetDependency](https://github.com/HighCapable/SweetDependency)
to manage dependencies.

### SweetDependency (Recommended)

Add dependencies to your project `SweetDependency` configuration file.

```yaml
libraries:
  com.highcapable.flexiui:
    # commonMain
    core:
      version: +
    resources:
      version: +
    # androidMain
    core-android:
      version-ref: <this>::core
    resources-android:
      version-ref: <this>::resources
    # iosArm64Main
    core-iosarm64:
      version-ref: <this>::core
    resources-iosarm64:
      version-ref: <this>::resources
    # iosX64Main
    core-iosx64:
      version-ref: <this>::core
    resources-iosx64:
      version-ref: <this>::resources
    # iosSimulatorArm64Main
    core-iossimulatorarm64:
      version-ref: <this>::core
    resources-iossimulatorarm64:
      version-ref: <this>::resources
    # desktopMain
    core-desktop:
      version-ref: <this>::core
    resources-desktop:
      version-ref: <this>::resources
```

Configure dependencies in your project `build.gradle.kts`.

If you use multi-platform dependencies in a regular project, you only need to deploy the corresponding platform suffix dependencies as needed.

```kotlin
implementation(com.highcapable.flexiui.core.android)
implementation(com.highcapable.flexiui.core.iosarm64)
implementation(com.highcapable.flexiui.core.iosx64)
implementation(com.highcapable.flexiui.core.iossimulatorarm64)
implementation(com.highcapable.flexiui.core.desktop)
```

```kotlin
implementation(com.highcapable.flexiui.resources.android)
implementation(com.highcapable.flexiui.resources.iosarm64)
implementation(com.highcapable.flexiui.resources.iosx64)
implementation(com.highcapable.flexiui.resources.iossimulatorarm64)
implementation(com.highcapable.flexiui.resources.desktop)
```

If you use multi-platform dependencies in a multi-platform project, you need to add the `core` dependency in `commonMain`.

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(com.highcapable.flexiui.core)
                implementation(com.highcapable.flexiui.resources)
            }
        }
    }
}
```

### Traditional Method

Configure dependencies in your project `build.gradle.kts`.

If you use multi-platform dependencies in a regular project, you only need to deploy the corresponding platform suffix dependencies as needed.

```kotlin
implementation("com.highcapable.flexiui:core-android:<version>")
implementation("com.highcapable.flexiui:core-iosarm64:<version>")
implementation("com.highcapable.flexiui:core-iosx64:<version>")
implementation("com.highcapable.flexiui:core-iossimulatorarm64:<version>")
implementation("com.highcapable.flexiui:core-desktop:<version>")
```

```kotlin
implementation("com.highcapable.flexiui:resources-android:<version>")
implementation("com.highcapable.flexiui:resources-iosarm64:<version>")
implementation("com.highcapable.flexiui:resources-iosx64:<version>")
implementation("com.highcapable.flexiui:resources-iossimulatorarm64:<version>")
implementation("com.highcapable.flexiui:resources-desktop:<version>")
```

If you use multi-platform dependencies in a multi-platform project, you need to add the `core` dependency in `commonMain`.

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.highcapable.flexiui:core:<version>")
                implementation("com.highcapable.flexiui:resources:<version>")
            }
        }
    }
}
```

Please change `<version>` to the version displayed at the top of page.

## Dev Version

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Fmain%2Frepository%2Fsnapshots%2Fcom%2Fhighcapable%2Fflexiui%2Fcore%2Fmaven-metadata.xml&logo=apachemaven&logoColor=orange&label=core)
![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Fmain%2Frepository%2Fsnapshots%2Fcom%2Fhighcapable%2Fflexiui%2Fresources%2Fmaven-metadata.xml&logo=apachemaven&logoColor=orange&label=resources)

Each version of Flexi UI has a corresponding dev version (version under development), and its update frequency will be very high.

The documents here **may not be synchronized in time** according to the latest dev version.

The dev version will only be published in our public repository and will not be synchronized to **Maven Central**,
if you want to use the dev version, please refer to the following method to configure the repository.

### SweetDependency (Recommended)

Add the repository in your project's `SweetDependency` configuration file.

```yaml
repositories:
  # For details, please go to: https://github.com/HighCapable/maven-repository
  highcapable-maven-snapshots:
    url: https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/snapshots
```

### Traditional Method

Configure the repository in your project `build.gradle.kts`.

```kotlin
repositories {
    // For details, please go to: https://github.com/HighCapable/maven-repository
    maven("https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/snapshots")
}
```

## Demo

You can find some samples [here](https://github.com/BetterAndroid/FlexiUI/tree/compose/samples) to get a better understanding of how
Flexi UI is used by looking at the corresponding demo projects.

## Basic Usage

You can initialize a simple app by providing a custom theme through `FlexiTheme`.

Its usage is basically consistent with `MaterialTheme`.

> The following example

```kotlin
@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    FlexiTheme {
        content()
    }
}
```

Then use `Scaffold` to create a basic page.

> The following example

```kotlin
MyApplicationTheme {
    Scaffold(
        appBar = {
            PrimaryAppBar(
                title = { Text("My App") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Hello World!")
        }
    }
}
```

## Style Introduction

Flexi UI provides `Colors`, `Shapes`, `Typography` and `Sizes` (experimental) through which you can modify the global styles of all components.

> The following example

```kotlin
FlexiTheme(
    colors = FlexiTheme.colors.copy(
        themePrimary = Color.Blue
    )
) {
    // ...
}
```

For details, please refer to the Flexi UI design specification document (under development).

## Component Introduction

All available components currently provided in Flexi UI are introduced here, you can find detailed usage instructions for each component below.

### Scaffold

This section is under development, stay tuned.

### Surface

This section is under development, stay tuned.

### AreaBox

This section is under development, stay tuned.

### ItemBox

This section is under development, stay tuned.

### Icon

This section is under development, stay tuned.

### Text

This section is under development, stay tuned.

### TextField

This section is under development, stay tuned.

### Button

This section is under development, stay tuned.

### CheckBox

This section is under development, stay tuned.

### RadioButton

This section is under development, stay tuned.

### Switch

This section is under development, stay tuned.

### Slider

This section is under development, stay tuned.

### ProgressIndicator

This section is under development, stay tuned.

### Dropdown

This section is under development, stay tuned.

### Tab

This section is under development, stay tuned.

### NavigationBar

This section is under development, stay tuned.

### Spacer

This section is under development, stay tuned.

### StickyHeaderBar

This section is under development, stay tuned.

### AppBar

This section is under development, stay tuned.

### Dialog

This section is under development, stay tuned.

### Interaction

This section is under development, stay tuned.