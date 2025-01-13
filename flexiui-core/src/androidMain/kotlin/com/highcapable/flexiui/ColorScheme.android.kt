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
 * This file is created by fankes on 2024/1/6.
 */
@file:Suppress("unused")

package com.highcapable.flexiui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.highcapable.betterandroid.ui.extension.component.feature.SystemColors
import com.highcapable.betterandroid.ui.extension.graphics.mixColorOf

internal actual val SystemLightColors
    @Composable
    get() = systemLightColors()

internal actual val SystemDarkColors
    @Composable
    get() = systemDarkColors()

internal actual val SystemBlackColors
    @Composable
    get() = systemBlackColors()

/**
 * Whether system color is available.
 * @return [Boolean]
 */
@Composable
actual fun isSystemColorAvailable() = SystemColors.isAvailable

@Composable
private fun systemLightColors(): Colors {
    val systemColors = rememberSystemColors()
    if (!SystemColors.isAvailable) return DefaultLightColors
    return Colors(
        backgroundPrimary = Color(systemColors.materialDynamicNeutral(95)),
        backgroundSecondary = Color(mixColorOf(systemColors.materialDynamicNeutral(95), systemColors.materialDynamicNeutral(99))),
        foregroundPrimary = Color(systemColors.materialDynamicNeutral(99)),
        foregroundSecondary = Color(systemColors.materialDynamicNeutral(95)),
        themePrimary = Color(systemColors.materialDynamicPrimary(60)),
        themeSecondary = Color(systemColors.materialDynamicPrimary(60)).copy(alpha = 0.65f),
        themeTertiary = Color(systemColors.materialDynamicPrimary(60)).copy(alpha = 0.15f),
        textPrimary = DefaultLightColors.textPrimary,
        textSecondary = DefaultLightColors.textSecondary
    )
}

@Composable
private fun systemDarkColors(): Colors {
    val systemColors = rememberSystemColors()
    if (!SystemColors.isAvailable) return DefaultDarkColors
    return Colors(
        backgroundPrimary = Color(systemColors.materialDynamicNeutral(10)),
        backgroundSecondary = Color(mixColorOf(systemColors.materialDynamicNeutral(10), systemColors.materialDynamicNeutral(20))),
        foregroundPrimary = Color(systemColors.materialDynamicNeutral(20)),
        foregroundSecondary = Color(systemColors.materialDynamicNeutral(30)),
        themePrimary = Color(systemColors.materialDynamicSecondary(60)),
        themeSecondary = Color(systemColors.materialDynamicSecondary(60)).copy(alpha = 0.65f),
        themeTertiary = Color(systemColors.materialDynamicSecondary(60)).copy(alpha = 0.25f),
        textPrimary = DefaultDarkColors.textPrimary,
        textSecondary = DefaultDarkColors.textSecondary
    )
}

@Composable
private fun systemBlackColors(): Colors {
    val systemColors = rememberSystemColors()
    if (!SystemColors.isAvailable) return DefaultBlackColors
    return Colors(
        backgroundPrimary = Color(systemColors.materialDynamicNeutral(0)),
        backgroundSecondary = Color(mixColorOf(systemColors.materialDynamicNeutral(0), systemColors.materialDynamicNeutral(10))),
        foregroundPrimary = Color(systemColors.materialDynamicNeutral(10)),
        foregroundSecondary = Color(systemColors.materialDynamicNeutral(20)),
        themePrimary = Color(systemColors.materialDynamicSecondary(60)),
        themeSecondary = Color(systemColors.materialDynamicSecondary(60)).copy(alpha = 0.65f),
        themeTertiary = Color(systemColors.materialDynamicSecondary(60)).copy(alpha = 0.27f),
        textPrimary = DefaultBlackColors.textPrimary,
        textSecondary = DefaultBlackColors.textSecondary
    )
}

@Composable
private fun rememberSystemColors(): SystemColors {
    val context = LocalContext.current
    val systemColors = remember { SystemColors.from(context) }
    return systemColors
}