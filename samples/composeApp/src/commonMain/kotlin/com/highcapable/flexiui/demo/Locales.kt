/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019 HighCapable
 * https://github.com/BetterAndroid/FlexiUI
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is created by fankes on 2024/1/16.
 */
package com.highcapable.flexiui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Stable
enum class Locales { EN, ZH_CN }

@Stable
fun locales() = Locales.entries.toTypedArray()

@Stable
val Locales.displayName
    get() = when (this) {
        Locales.EN -> "English"
        Locales.ZH_CN -> "简体中文"
    }

val strings
    @Composable
    get(): Strings {
        val locale by remember { Preferences.locale }
        return when (locale) {
            Locales.EN -> EnString
            Locales.ZH_CN -> ZhCnString
        }
    }

private val EnString = Strings(
    appName = "Flexi UI Demo",
    appDescription = "Flexi UI is a flexible and useful UI component library.",
    uiLanguage = "UI Language",
    selectLanguage = "Select a language",
    themeStyle = "Theme Style",
    enableDarkMode = "Enable night mode",
    enableDarkModeDescription = "Manually switch the current theme to night mode.",
    followSystemDarkMode = "Follow system night mode",
    followSystemDarkModeDescription = "Follow the system theme to switch day and night mode.",
    selectTheme = "Select a theme",
    home = "Home",
    component = "Component",
    openLink = "Open Link",
    openLinkDescription = "Open the project URL in the browser?",
    open = "Open",
    confirm = "OK",
    cancel = "Cancel",
    singlePageDemo = "Single Page Demo",
    singlePageDemoDescription = "Open a single page",
    lazyListDemo = "Lazy List Demo",
    lazyListDemoDescription = "Open a lazy list page",
    singlePageDescription = """
      Now, you open a separate secondary page.
      You can click the button below to back to the homepage.
    """.trimIndent(),
    takeMeHome = "Take Me Home",
    itemsListDataCount = { count -> "$count items of list data" },
    listRemoveAll = "Remove All",
    listRemoveAllDescription = "Are you sure you want to remove all data?",
    linearList = "Linear List",
    gridList = "Grid List",
    listRemoveSingle = "Remove this data",
    listNoData = "No data to show",
    textAndInput = "Text and Input",
    enterTextHere = "Enter text here",
    enterTextHereDescription = "The text you enter will be displayed here",
    dropdownAndAutoComplete = "Dropdown and Auto Complete",
    dropdownSimpleDescription = "Please select a data",
    dropdownAndAutoCompleteDescription = "Enter the beginning of the text from the drop-down list above to experience autocompletion.",
    simpleStringData = listOf(
        "Week",
        "Year",
        "Month",
        "Auto complete this text",
        "Auto complete those text",
        "The weather is nice today",
        "Flexi UI is beautiful and useful"
    ),
    progressIndicator = "Progress Indicator",
    progressTo30 = "Progress to 30%",
    progressTo100 = "Progress to 100%",
    useCircularIndicator = "Use circular progress indicator",
    switchToIndeterminateProgress = "Switch to indeterminate progress",
    dialog = "Dialog",
    normalDialog = "Show a Normal Dialog",
    normalDialogDescription = "This is a normal dialog.",
    multiButtonDialog = "Multi-Button",
    multiButtonDialogDescription = "In this dialog you have two options.",
    dialogWithIcon = "With an Icon",
    progressDialog = "Progress Indicator",
    loadingDialog = "Loading",
    singleInputDialog = "Single Input Box",
    multiInputDialog = "Multi Input Box",
    passwordInputDialog = "Password Input Box",
    dropdownListDialog = "Dropdown List",
    singleChoiceDialog = "Single Choice",
    mutliChoiceDialog = "Multi Choice"
)

private val ZhCnString = Strings(
    appName = "Flexi UI 演示",
    appDescription = "Flexi UI 是一个灵活且实用的 UI 组件库。",
    uiLanguage = "界面语言",
    selectLanguage = "选择一个语言",
    themeStyle = "主题风格",
    enableDarkMode = "启用夜间模式",
    enableDarkModeDescription = "手动切换当前主题到夜间模式。",
    followSystemDarkMode = "跟随系统夜间模式",
    followSystemDarkModeDescription = "跟随系统主题切换日夜模式。",
    selectTheme = "选择一个主题",
    home = "主页",
    component = "组件",
    openLink = "打开链接",
    openLinkDescription = "在浏览器中打开项目链接？",
    open = "打开",
    confirm = "确定",
    cancel = "取消",
    singlePageDemo = "独立页面演示",
    singlePageDemoDescription = "打开一个独立页面",
    lazyListDemo = "懒加载列表演示",
    lazyListDemoDescription = "打开一个懒加载列表页面",
    singlePageDescription = """
      现在，你打开了一个独立的二级页面。
      你可以点击下面的按钮回到主页。
    """.trimIndent(),
    takeMeHome = "回到主页",
    itemsListDataCount = { count -> "当前共有 $count 项列表数据" },
    listRemoveAll = "全部移除",
    listRemoveAllDescription = "你确定要移除全部数据吗？",
    linearList = "线性列表",
    gridList = "九宫格列表",
    listRemoveSingle = "移除这条数据",
    listNoData = "暂无数据",
    textAndInput = "文本与输入",
    enterTextHere = "在这里输入文字",
    enterTextHereDescription = "你输入的文字会显示在这里",
    dropdownAndAutoComplete = "下拉列表与自动完成",
    dropdownSimpleDescription = "请选择一项数据",
    dropdownAndAutoCompleteDescription = "输入上方下拉列表中的文字开头部分可体验自动补全功能。",
    simpleStringData = listOf(
        "星期",
        "年份",
        "月份",
        "自动补全这条文本",
        "自动补全这些文本",
        "今天天气很好",
        "Flexi UI 美观且实用"
    ),
    progressIndicator = "进度指示器",
    progressTo30 = "进度到 30%",
    progressTo100 = "进度到 100%",
    useCircularIndicator = "使用圆形进度条",
    switchToIndeterminateProgress = "切换到不确定进度",
    dialog = "对话框",
    normalDialog = "显示一个普通对话框",
    normalDialogDescription = "这是一个普通的对话框。",
    multiButtonDialog = "多个按钮",
    multiButtonDialogDescription = "在这个对话框中你有两个选择。",
    dialogWithIcon = "带个图标",
    progressDialog = "进度指示",
    loadingDialog = "加载中",
    singleInputDialog = "单行输入框",
    multiInputDialog = "多行输入框",
    passwordInputDialog = "密码输入框",
    dropdownListDialog = "下拉列表",
    singleChoiceDialog = "单项选择",
    mutliChoiceDialog = "多项选择"
)

@Immutable
data class Strings(
    val appName: String,
    val appDescription: String,
    val uiLanguage: String,
    val selectLanguage: String,
    val themeStyle: String,
    val enableDarkMode: String,
    val enableDarkModeDescription: String,
    val followSystemDarkMode: String,
    val followSystemDarkModeDescription: String,
    val selectTheme: String,
    val home: String,
    val component: String,
    val openLink: String,
    val openLinkDescription: String,
    val open: String,
    val confirm: String,
    val cancel: String,
    val singlePageDemo: String,
    val singlePageDemoDescription: String,
    val lazyListDemo: String,
    val lazyListDemoDescription: String,
    val singlePageDescription: String,
    val takeMeHome: String,
    val itemsListDataCount: (Number) -> String,
    val listRemoveAll: String,
    val listRemoveAllDescription: String,
    val linearList: String,
    val gridList: String,
    val listRemoveSingle: String,
    val listNoData: String,
    val textAndInput: String,
    val enterTextHere: String,
    val enterTextHereDescription: String,
    val dropdownAndAutoComplete: String,
    val dropdownSimpleDescription: String,
    val dropdownAndAutoCompleteDescription: String,
    val simpleStringData: List<String>,
    val progressIndicator: String,
    val progressTo30: String,
    val progressTo100: String,
    val useCircularIndicator: String,
    val switchToIndeterminateProgress: String,
    val dialog: String,
    val normalDialog: String,
    val normalDialogDescription: String,
    val multiButtonDialog: String,
    val multiButtonDialogDescription: String,
    val dialogWithIcon: String,
    val progressDialog: String,
    val loadingDialog: String,
    val singleInputDialog: String,
    val multiInputDialog: String,
    val passwordInputDialog: String,
    val dropdownListDialog: String,
    val singleChoiceDialog: String,
    val mutliChoiceDialog: String
)