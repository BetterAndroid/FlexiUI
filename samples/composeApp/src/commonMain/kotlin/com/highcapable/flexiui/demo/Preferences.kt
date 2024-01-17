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
 * This file is created by fankes on 2024/1/11.
 */
package com.highcapable.flexiui.demo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.highcapable.flexiui.ColorScheme

@Stable
data class AppPreferences(
    var locale: MutableState<Locales> = mutableStateOf(Locales.EN),
    var colorScheme: MutableState<ColorScheme> = mutableStateOf(ColorScheme.Default),
    var darkMode: MutableState<Boolean> = mutableStateOf(false),
    var followSystemDarkMode: MutableState<Boolean> = mutableStateOf(false)
)

@Stable
val Preferences = AppPreferences()