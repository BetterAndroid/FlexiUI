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
 * This file is created by fankes on 2023/11/5.
 */
@file:Suppress("unused")

package com.highcapable.flexiui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Colors defines for Flexi UI.
 */
@Stable
data class Colors(
    var backgroundPrimary: Color,
    var backgroundSecondary: Color,
    var foregroundPrimary: Color,
    var foregroundSecondary: Color,
    var themePrimary: Color,
    var themeSecondary: Color,
    var themeTertiary: Color,
    var textPrimary: Color,
    var textSecondary: Color
)

/**
 * Descriptor for [Colors].
 */
@Stable
internal enum class ColorsDescriptor {
    BackgroundPrimary,
    BackgroundSecondary,
    ForegroundPrimary,
    ForegroundSecondary,
    ThemePrimary,
    ThemeSecondary,
    ThemeTertiary,
    TextPrimary,
    TextSecondary
}

internal val LocalColors = staticCompositionLocalOf { DefaultLightColors }

/**
 * Gets a [Color] from descriptor.
 * @see ColorsDescriptor.toColor
 * @param value the descriptor.
 * @return [Color]
 */
internal fun Colors.fromDescriptor(value: ColorsDescriptor) = when (value) {
    ColorsDescriptor.BackgroundPrimary -> backgroundPrimary
    ColorsDescriptor.BackgroundSecondary -> backgroundSecondary
    ColorsDescriptor.ForegroundPrimary -> foregroundPrimary
    ColorsDescriptor.ForegroundSecondary -> foregroundSecondary
    ColorsDescriptor.ThemePrimary -> themePrimary
    ColorsDescriptor.ThemeSecondary -> themeSecondary
    ColorsDescriptor.ThemeTertiary -> themeTertiary
    ColorsDescriptor.TextPrimary -> textPrimary
    ColorsDescriptor.TextSecondary -> textSecondary
}

/**
 * Converts a descriptor to a [Color].
 * @see Colors.fromDescriptor
 * @return [Color]
 */
@ReadOnlyComposable
@Composable
internal fun ColorsDescriptor.toColor() = LocalColors.current.fromDescriptor(this)