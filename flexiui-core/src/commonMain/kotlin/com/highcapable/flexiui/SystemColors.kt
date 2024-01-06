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
 * This file is created by fankes on 2024/1/6.
 */
@file:Suppress("unused")

package com.highcapable.flexiui

import androidx.compose.runtime.Composable

@get:Composable
internal expect val SystemLightColors: Colors

@get:Composable
internal expect val SystemDarkColors: Colors

@get:Composable
internal expect val SystemBlackColors: Colors

/**
 * Whether system color is available.
 * @return [Boolean]
 */
@Composable
expect fun isSystemColorAvailable(): Boolean

/**
 * Returns a color scheme provided by system.
 *
 * You can use [isSystemColorAvailable] check it first, otherwise it will return default colors.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 */
@Composable
fun systemColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) SystemBlackColors else SystemDarkColors
    else -> SystemLightColors
}