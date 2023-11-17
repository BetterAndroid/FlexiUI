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
 * This file is created by fankes on 2023/11/5.
 */
@file:Suppress("unused")

package com.highcapable.flexiui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

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
    var textSecondary: Color,
    var isLight: Boolean
)

@get:Composable
@get:ReadOnlyComposable
internal expect val DynamicLightColors: Colors

@get:Composable
@get:ReadOnlyComposable
internal expect val DynamicDarkColors: Colors

@get:Composable
@get:ReadOnlyComposable
internal expect val DynamicBlackColors: Colors

internal val DefaultLightColors = Colors(
    backgroundPrimary = Color(0xFFF5F5F5),
    backgroundSecondary = Color(0xFFEDEDED),
    foregroundPrimary = Color(0xFFFFFFFF),
    foregroundSecondary = Color(0xFFF5F5F5),
    themePrimary = Color(0xFF777777),
    themeSecondary = Color(0xA6777777),
    themeTertiary = Color(0x27777777),
    textPrimary = Color(0xFF323B42),
    textSecondary = Color(0xFF777777),
    isLight = true
)

internal val DefaultDarkColors = Colors(
    backgroundPrimary = Color(0xFF2D2D2D),
    backgroundSecondary = Color(0xFF484848),
    foregroundPrimary = Color(0xFF474747),
    foregroundSecondary = Color(0xFF646464),
    themePrimary = Color(0xFF888888),
    themeSecondary = Color(0xA6888888),
    themeTertiary = Color(0x40888888),
    textPrimary = Color(0xFFE3E3E3),
    textSecondary = Color(0xFFBBBBBB),
    isLight = false
)

internal val DefaultBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF1B1B1B),
    foregroundPrimary = Color(0xFF1A1A1A),
    foregroundSecondary = Color(0xFF373737),
    themePrimary = Color(0xFF5B5B5B),
    themeSecondary = Color(0xA65B5B5B),
    themeTertiary = Color(0x455B5B5B),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val RedLightColors = Colors(
    backgroundPrimary = Color(0xFFFBEEEC),
    backgroundSecondary = Color(0xFFEDE0DE),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFFBEEEC),
    themePrimary = Color(0xFFFF5545),
    themeSecondary = Color(0xA6FF8A7B),
    themeTertiary = Color(0xFFF9DCD8),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val PinkLightColors = Colors(
    backgroundPrimary = Color(0xFFFBEEEE),
    backgroundSecondary = Color(0xFFECE0E0),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFFBEEEE),
    themePrimary = Color(0xFFFF4E7C),
    themeSecondary = Color(0xA6FF869D),
    themeTertiary = Color(0xFFF7DCDF),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val PurpleLightColors = Colors(
    backgroundPrimary = Color(0xFFF5EFF4),
    backgroundSecondary = Color(0xFFE6E1E6),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFF5EFF4),
    themePrimary = Color(0xFFA476FF),
    themeSecondary = Color(0xA6BB99FF),
    themeTertiary = Color(0xFFE8DFEE),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val OrangeLightColors = Colors(
    backgroundPrimary = Color(0xFFFAEFE7),
    backgroundSecondary = Color(0xFFEBE0D9),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFFAEFE7),
    themePrimary = Color(0xFFD27C00),
    themeSecondary = Color(0xA6F89300),
    themeTertiary = Color(0xFFF5DECC),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val YellowLightColors = Colors(
    backgroundPrimary = Color(0xFFF8EFE7),
    backgroundSecondary = Color(0xFFE9E1D9),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFF8EFE7),
    themePrimary = Color(0xFFBA8800),
    themeSecondary = Color(0xA6DCA100),
    themeTertiary = Color(0xFFF0E0CA),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val GreenLightColors = Colors(
    backgroundPrimary = Color(0xFFEFF1ED),
    backgroundSecondary = Color(0xFFE1E3DF),
    foregroundPrimary = Color(0xFFFBFDF8),
    foregroundSecondary = Color(0xFFEFF1ED),
    themePrimary = Color(0xFF5B9E7A),
    themeSecondary = Color(0xA676B993),
    themeTertiary = Color(0xFFE1E3DF),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val BlueLightColors = Colors(
    backgroundPrimary = Color(0xFFF0F0F3),
    backgroundSecondary = Color(0xFFE2E2E5),
    foregroundPrimary = Color(0xFFFCFCFF),
    foregroundSecondary = Color(0xFFF0F0F3),
    themePrimary = Color(0xFF0099DF),
    themeSecondary = Color(0xA633B4FF),
    themeTertiary = Color(0xFFDBE3ED),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary,
    isLight = true
)

private val RedDarkColors = Colors(
    backgroundPrimary = Color(0xFF271816),
    backgroundSecondary = Color(0xFF3D2C2A),
    foregroundPrimary = Color(0xFF3D2C2A),
    foregroundSecondary = Color(0xFF554240),
    themePrimary = Color(0xFFB9856D),
    themeSecondary = Color(0xA69B6B54),
    themeTertiary = Color(0xFF554240),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val PinkDarkColors = Colors(
    backgroundPrimary = Color(0xFF26181A),
    backgroundSecondary = Color(0xFF3D2C2E),
    foregroundPrimary = Color(0xFF3D2C2E),
    foregroundSecondary = Color(0xFF544244),
    themePrimary = Color(0xFFBA837B),
    themeSecondary = Color(0xA69D6962),
    themeTertiary = Color(0xFF544244),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val PurpleDarkColors = Colors(
    backgroundPrimary = Color(0xFF1E1A24),
    backgroundSecondary = Color(0xFF332E3A),
    foregroundPrimary = Color(0xFF332E3A),
    foregroundSecondary = Color(0xFF494550),
    themePrimary = Color(0xFF9F88AD),
    themeSecondary = Color(0xA6846E91),
    themeTertiary = Color(0xFF494550),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val OrangeDarkColors = Colors(
    backgroundPrimary = Color(0xFF25190E),
    backgroundSecondary = Color(0xFF3B2E22),
    foregroundPrimary = Color(0xFF3B2E22),
    foregroundSecondary = Color(0xFF534437),
    themePrimary = Color(0xFFAE8B5D),
    themeSecondary = Color(0xA6917045),
    themeTertiary = Color(0xFF534437),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val YellowDarkColors = Colors(
    backgroundPrimary = Color(0xFF221B0D),
    backgroundSecondary = Color(0xFF382F20),
    foregroundPrimary = Color(0xFF382F20),
    foregroundSecondary = Color(0xFF4F4535),
    themePrimary = Color(0xFFA18F5C),
    themeSecondary = Color(0xA6857544),
    themeTertiary = Color(0xFF4F4535),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val GreenDarkColors = Colors(
    backgroundPrimary = Color(0xFF191C1A),
    backgroundSecondary = Color(0xFF2E312E),
    foregroundPrimary = Color(0xFF2E312E),
    foregroundSecondary = Color(0xFF444844),
    themePrimary = Color(0xFF7F9687),
    themeSecondary = Color(0xA6657B6D),
    themeTertiary = Color(0xFF444844),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val BlueDarkColors = Colors(
    backgroundPrimary = Color(0xFF141C23),
    backgroundSecondary = Color(0xFF293139),
    foregroundPrimary = Color(0xFF293139),
    foregroundSecondary = Color(0xFF3F484F),
    themePrimary = Color(0xFF8091B1),
    themeSecondary = Color(0xA6657795),
    themeTertiary = Color(0xFF3F484F),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary,
    isLight = false
)

private val RedBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF271816),
    foregroundSecondary = Color(0xFF3D2C2A),
    themePrimary = Color(0xFFB9856D),
    themeSecondary = Color(0xA69B6B54),
    themeTertiary = Color(0xFF3D2C2A),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

private val PinkBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF26181A),
    foregroundSecondary = Color(0xFF3D2C2E),
    themePrimary = Color(0xFFBA837B),
    themeSecondary = Color(0xA69D6962),
    themeTertiary = Color(0xFF3D2C2E),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

private val PurpleBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF1E1A24),
    foregroundSecondary = Color(0xFF332E3A),
    themePrimary = Color(0xFF9F88AD),
    themeSecondary = Color(0xA6846E91),
    themeTertiary = Color(0xFF332E3A),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

private val OrangeBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF25190E),
    foregroundSecondary = Color(0xFF3B2E22),
    themePrimary = Color(0xFFAE8B5D),
    themeSecondary = Color(0xA6917045),
    themeTertiary = Color(0xFF3B2E22),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

private val YellowBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF221B0D),
    foregroundSecondary = Color(0xFF382F20),
    themePrimary = Color(0xFFA18F5C),
    themeSecondary = Color(0xA6857544),
    themeTertiary = Color(0xFF382F20),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

private val GreenBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF191C1A),
    foregroundSecondary = Color(0xFF2E312E),
    themePrimary = Color(0xFF7F9687),
    themeSecondary = Color(0xA6657B6D),
    themeTertiary = Color(0xFF2E312E),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

private val BlueBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF000000),
    foregroundPrimary = Color(0xFF141C23),
    foregroundSecondary = Color(0xFF293139),
    themePrimary = Color(0xFF8091B1),
    themeSecondary = Color(0xA6657795),
    themeTertiary = Color(0xFF293139),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary,
    isLight = false
)

@Composable
@ReadOnlyComposable
fun dynamicColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) DynamicBlackColors else DynamicDarkColors
    else -> DynamicLightColors
}

fun defaultColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) DefaultBlackColors else DefaultDarkColors
    else -> DefaultLightColors
}

fun redColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) RedBlackColors else RedDarkColors
    else -> RedLightColors
}

fun pinkColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) PinkBlackColors else PinkDarkColors
    else -> PinkLightColors
}

fun purpleColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) PurpleBlackColors else PurpleDarkColors
    else -> PurpleLightColors
}

fun orangeColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) OrangeBlackColors else OrangeDarkColors
    else -> OrangeLightColors
}

fun yellowColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) YellowBlackColors else YellowDarkColors
    else -> YellowLightColors
}

fun greenColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) GreenBlackColors else GreenDarkColors
    else -> GreenLightColors
}

fun blueColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) BlueBlackColors else BlueDarkColors
    else -> BlueLightColors
}

internal val LocalColors = staticCompositionLocalOf { DefaultLightColors }

@Stable
val Color.Companion.Translucent get() = Color(0x80000000)