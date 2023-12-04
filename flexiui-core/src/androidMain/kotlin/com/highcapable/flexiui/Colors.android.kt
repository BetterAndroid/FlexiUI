/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019-2023 HighCapable
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
 * This file is created by fankes on 2023/11/18.
 */
@file:Suppress("unused")

package com.highcapable.flexiui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.highcapable.betterandroid.ui.extension.component.base.toMixColor
import com.highcapable.betterandroid.ui.extension.component.feature.SystemColors

@Composable
@ReadOnlyComposable
actual fun isDynamicColorsAvailable() = SystemColors.isAvailable

internal actual val DynamicLightColors
    @Composable
    @ReadOnlyComposable
    get() = if (SystemColors.isAvailable) Colors(
        backgroundPrimary = Color(DynamicColors.materialDynamicNeutral(95)),
        backgroundSecondary = Color(DynamicColors.materialDynamicNeutral(95) mix DynamicColors.materialDynamicNeutral(99)),
        foregroundPrimary = Color(DynamicColors.materialDynamicNeutral(99)),
        foregroundSecondary = Color(DynamicColors.materialDynamicNeutral(95)),
        themePrimary = Color(DynamicColors.materialDynamicPrimary(60)),
        themeSecondary = Color(DynamicColors.materialDynamicPrimary(60)).copy(alpha = 0.65f),
        themeTertiary = Color(DynamicColors.materialDynamicPrimary(60)).copy(alpha = 0.15f),
        textPrimary = DefaultLightColors.textPrimary,
        textSecondary = DefaultLightColors.textSecondary,
        isLight = true
    ) else DefaultLightColors

internal actual val DynamicDarkColors
    @Composable
    @ReadOnlyComposable
    get() = if (SystemColors.isAvailable) Colors(
        backgroundPrimary = Color(DynamicColors.materialDynamicNeutral(10)),
        backgroundSecondary = Color(DynamicColors.materialDynamicNeutral(10) mix DynamicColors.materialDynamicNeutral(20)),
        foregroundPrimary = Color(DynamicColors.materialDynamicNeutral(20)),
        foregroundSecondary = Color(DynamicColors.materialDynamicNeutral(30)),
        themePrimary = Color(DynamicColors.materialDynamicSecondary(60)),
        themeSecondary = Color(DynamicColors.materialDynamicSecondary(60)).copy(alpha = 0.65f),
        themeTertiary = Color(DynamicColors.materialDynamicSecondary(60)).copy(alpha = 0.25f),
        textPrimary = DefaultDarkColors.textPrimary,
        textSecondary = DefaultDarkColors.textSecondary,
        isLight = false
    ) else DefaultDarkColors

internal actual val DynamicBlackColors
    @Composable
    @ReadOnlyComposable
    get() = if (SystemColors.isAvailable) Colors(
        backgroundPrimary = Color(DynamicColors.materialDynamicNeutral(0)),
        backgroundSecondary = Color(DynamicColors.materialDynamicNeutral(0) mix DynamicColors.materialDynamicNeutral(10)),
        foregroundPrimary = Color(DynamicColors.materialDynamicNeutral(10)),
        foregroundSecondary = Color(DynamicColors.materialDynamicNeutral(20)),
        themePrimary = Color(DynamicColors.materialDynamicSecondary(60)),
        themeSecondary = Color(DynamicColors.materialDynamicSecondary(60)).copy(alpha = 0.65f),
        themeTertiary = Color(DynamicColors.materialDynamicSecondary(60)).copy(alpha = 0.27f),
        textPrimary = DefaultBlackColors.textPrimary,
        textSecondary = DefaultBlackColors.textSecondary,
        isLight = false
    ) else DefaultBlackColors

private val DynamicColors
    @Composable
    @ReadOnlyComposable
    get() = SystemColors.from(LocalContext.current)

private infix fun Int.mix(other: Int) = toMixColor(other)