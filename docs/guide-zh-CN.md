# Flexi UI 使用文档 (Jetpack Compose)

![Maven Central](https://img.shields.io/maven-central/v/com.highcapable.flexiui/core?logo=apachemaven&logoColor=orange&label=core)
![Maven Central](https://img.shields.io/maven-central/v/com.highcapable.flexiui/resources?logo=apachemaven&logoColor=orange&label=resources)

> ⚠️ 注意
>
> Flexi UI 尚在开发与测试，在 `1.0.0` 版本正式发布前 API 可能会发生变化，欢迎前往 [GitHub Issues](https://github.com/BetterAndroid/FlexiUI/issues)
> 向我们提出建议。
>
> 我们推荐你优先使用下方的 [Dev 版本](#dev-版本)，它正在积极开发中，同步当前分支的最新代码。

在开始使用之前，建议你仔细阅读此文档，以便你能更好地了解它的作用方式与功能。

你可以配合 [BetterAndroid](https://github.com/BetterAndroid/BetterAndroid) 一起使用效果更佳。

## 配置依赖

Flexi UI 分为 `core` 与 `resources` 两个模块，通常情况下你只需要添加 `core` 即可，`resources` 会自动作为 API 引入。

你可以使用如下方式将此模块添加到你的项目中。

这是一个 Kotlin Multiplatform 依赖，你需要 `org.jetbrains.kotlin.multiplatform` 插件来应用相关依赖。

我们推荐使用 Kotlin DSL 作为 Gradle 构建脚本语言并推荐使用 [SweetDependency](https://github.com/HighCapable/SweetDependency) 来管理依赖。

### SweetDependency (推荐)

在你的项目 `SweetDependency` 配置文件中添加依赖。

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

在你的项目 `build.gradle.kts` 中配置依赖。

如果你在普通的项目中使用多平台依赖，你只需要按需部署对应平台后缀的依赖即可。

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

如果你在多平台项目中使用多平台依赖，你需要在 `commonMain` 中添加 `core` 依赖。

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

### 传统方式

在你的项目 `build.gradle.kts` 中配置依赖。

如果你在普通的项目中使用多平台依赖，你只需要按需部署对应平台后缀的依赖即可。

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

如果你在多平台项目中使用多平台依赖，你需要在 `commonMain` 中添加 `core` 依赖。

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

请将 `<version>` 修改为此页面顶部显示的版本。

## Dev 版本

![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Fmain%2Frepository%2Fsnapshots%2Fcom%2Fhighcapable%2Fflexiui%2Fcore%2Fmaven-metadata.xml&logo=apachemaven&logoColor=orange&label=core)
![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fraw.githubusercontent.com%2FHighCapable%2Fmaven-repository%2Fmain%2Frepository%2Fsnapshots%2Fcom%2Fhighcapable%2Fflexiui%2Fresources%2Fmaven-metadata.xml&logo=apachemaven&logoColor=orange&label=resources)

Flexi UI 每个版本都有对应的 Dev 版本 (开发中的版本)，它的更新频率将会非常高，这里的文档**可能不会及时**根据最新的 Dev 版本进行同步。

Dev 版本仅会发布在我们的公共存储库中，不会同步至 **Maven Central**，如果你要使用 Dev 版本，请参考以下方式配置存储库。

### SweetDependency (推荐)

在你的项目 `SweetDependency` 配置文件中添加存储库。

```yaml
repositories:
  # 详情请前往：https://github.com/HighCapable/maven-repository
  highcapable-maven-snapshots:
    # 中国大陆用户请将下方的 "raw.githubusercontent.com" 修改为 "raw.gitmirror.com"
    url: https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/snapshots
```

### 传统方式

在你的项目 `build.gradle.kts` 中配置存储库。

```kotlin
repositories {
    // 详情请前往：https://github.com/HighCapable/maven-repository
    // 中国大陆用户请将下方的 "raw.githubusercontent.com" 修改为 "raw.gitmirror.com"
    maven("https://raw.githubusercontent.com/HighCapable/maven-repository/main/repository/snapshots")
}
```

## Demo

你可以在 [这里](https://github.com/BetterAndroid/FlexiUI/tree/compose/samples) 找到一些示例，通过查看对应的演示项目来更好地了解 Flexi UI 的使用方式。

## 基本用法

你可以通过 `FlexiTheme` 提供自定义的主题来初始化一个简单的应用程序。

它的用法与 `MaterialTheme` 基本保持一致。

> 示例如下

```kotlin
@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    FlexiTheme {
        content()
    }
}
```

然后通过 `Scaffold` 来创建一个基本的页面。

> 示例如下

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

## 样式介绍

Flexi UI 提供了 `Colors`、`Shapes`、`Typography` 以及 `Sizes` (实验性)，你可以通过它们来修改所有组件的全局样式。

> 示例如下

```kotlin
FlexiTheme(
    colors = FlexiTheme.colors.copy(
        themePrimary = Color.Blue
    )
) {
    // ...
}
```

详情请参考 Flexi UI 的设计规范文档 (完善中)。

## 组件介绍

这里介绍了 Flexi UI 中目前提供的所有可用组件，你可以在下方每个组件中找到详细的使用方法。

### Scaffold

该板块完善中，敬请期待。

### Surface

该板块完善中，敬请期待。

### AreaBox

该板块完善中，敬请期待。

### ItemBox

该板块完善中，敬请期待。

### Icon

该板块完善中，敬请期待。

### Text

该板块完善中，敬请期待。

### TextField

该板块完善中，敬请期待。

### Button

该板块完善中，敬请期待。

### CheckBox

该板块完善中，敬请期待。

### RadioButton

该板块完善中，敬请期待。

### Switch

该板块完善中，敬请期待。

### Slider

该板块完善中，敬请期待。

### ProgressIndicator

该板块完善中，敬请期待。

### Dropdown

该板块完善中，敬请期待。

### Tab

该板块完善中，敬请期待。

### NavigationBar

该板块完善中，敬请期待。

### Spacer

该板块完善中，敬请期待。

### StickyHeaderBar

该板块完善中，敬请期待。

### AppBar

该板块完善中，敬请期待。

### Dialog

该板块完善中，敬请期待。

### Interaction

该板块完善中，敬请期待。