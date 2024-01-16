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
 * This file is created by fankes on 2023/11/5.
 */
package com.highcapable.flexiui.demo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.multiplatform.systembar.PlatformSystemBarStyle
import com.highcapable.betterandroid.compose.multiplatform.systembar.rememberSystemBarsController
import com.highcapable.betterandroid.compose.multiplatform.systembar.setStyle
import com.highcapable.flexiui.FlexiTheme
import com.highcapable.flexiui.component.Surface
import com.highcapable.flexiui.demo.screen.LazyListScreen
import com.highcapable.flexiui.demo.screen.MainScreen
import com.highcapable.flexiui.demo.screen.SecondaryScreen

const val PROJECT_URL = "https://github.com/BetterAndroid/FlexiUI"

@Composable
private fun FlexiDemoTheme(content: @Composable () -> Unit) {
    val systemBars = rememberSystemBarsController()
    val darkMode by remember { Preferences.darkMode }
    val followSystemDarkMode by remember { Preferences.followSystemDarkMode }
    val currentDarkMode = if (followSystemDarkMode) isSystemInDarkTheme() else darkMode
    val colorScheme by remember { Preferences.colorScheme }
    systemBars.setStyle(
        if (currentDarkMode)
            PlatformSystemBarStyle.DarkTransparent
        else PlatformSystemBarStyle.LightTransparent
    )
    FlexiTheme(
        colors = colorScheme.toColors(currentDarkMode),
        content = content
    )
}

@Composable
fun App() {
    FlexiDemoTheme {
        // Surface will keep the content background color when animation.
        Surface(padding = ComponentPadding.None) {
            Screen(Screen.Main) { MainScreen() }
            Screen(Screen.Secondary) { SecondaryScreen() }
            Screen(Screen.LazyList) { LazyListScreen() }
        }
    }
}