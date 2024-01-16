/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019-2024 HighCapable
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
fun Locales.toName() = when (this) {
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
    comfirm = "OK",
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
    listNoData = "No data to show"
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
    comfirm = "确定",
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
    listNoData = "暂无数据"
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
    val comfirm: String,
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
    val listNoData: String
)