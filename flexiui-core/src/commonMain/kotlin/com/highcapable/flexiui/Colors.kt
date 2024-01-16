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

internal val DefaultLightColors = Colors(
    backgroundPrimary = Color(0xFFF5F5F5),
    backgroundSecondary = Color(0xFFFAFAFA),
    foregroundPrimary = Color(0xFFFFFFFF),
    foregroundSecondary = Color(0xFFF5F5F5),
    themePrimary = Color(0xFF777777),
    themeSecondary = Color(0xA6777777),
    themeTertiary = Color(0x27777777),
    textPrimary = Color(0xFF323B42),
    textSecondary = Color(0xFF777777)
)

internal val DefaultDarkColors = Colors(
    backgroundPrimary = Color(0xFF2D2D2D),
    backgroundSecondary = Color(0xFF3A3A3A),
    foregroundPrimary = Color(0xFF474747),
    foregroundSecondary = Color(0xFF646464),
    themePrimary = Color(0xFF888888),
    themeSecondary = Color(0xA6888888),
    themeTertiary = Color(0x40888888),
    textPrimary = Color(0xFFE3E3E3),
    textSecondary = Color(0xFFBBBBBB)
)

internal val DefaultBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF0D0D0D),
    foregroundPrimary = Color(0xFF1A1A1A),
    foregroundSecondary = Color(0xFF373737),
    themePrimary = Color(0xFF5B5B5B),
    themeSecondary = Color(0xA65B5B5B),
    themeTertiary = Color(0x455B5B5B),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val RedLightColors = Colors(
    backgroundPrimary = Color(0xFFFBEEEC),
    backgroundSecondary = Color(0xFFFDF4F5),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFFBEEEC),
    themePrimary = Color(0xFFFF5545),
    themeSecondary = Color(0xA6FF5545),
    themeTertiary = Color(0x27FF5545),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val PinkLightColors = Colors(
    backgroundPrimary = Color(0xFFFBEEEE),
    backgroundSecondary = Color(0xFFFDF4F6),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFFBEEEE),
    themePrimary = Color(0xFFFF4E7C),
    themeSecondary = Color(0xA6FF4E7C),
    themeTertiary = Color(0x27FF4E7C),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val PurpleLightColors = Colors(
    backgroundPrimary = Color(0xFFF5EFF4),
    backgroundSecondary = Color(0xFFFAF5F9),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFF5EFF4),
    themePrimary = Color(0xFFA476FF),
    themeSecondary = Color(0xA6A476FF),
    themeTertiary = Color(0x27A476FF),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val OrangeLightColors = Colors(
    backgroundPrimary = Color(0xFFFAEFE7),
    backgroundSecondary = Color(0xFFFCF5F3),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFFAEFE7),
    themePrimary = Color(0xFFD27C00),
    themeSecondary = Color(0xA6D27C00),
    themeTertiary = Color(0x27D27C00),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val YellowLightColors = Colors(
    backgroundPrimary = Color(0xFFF8EFE7),
    backgroundSecondary = Color(0xFFFBF5F3),
    foregroundPrimary = Color(0xFFFFFBFF),
    foregroundSecondary = Color(0xFFF8EFE7),
    themePrimary = Color(0xFFBA8800),
    themeSecondary = Color(0xA6BA8800),
    themeTertiary = Color(0x27BA8800),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val GreenLightColors = Colors(
    backgroundPrimary = Color(0xFFEFF1ED),
    backgroundSecondary = Color(0xFFF5F7F2),
    foregroundPrimary = Color(0xFFFBFDF8),
    foregroundSecondary = Color(0xFFEFF1ED),
    themePrimary = Color(0xFF5B9E7A),
    themeSecondary = Color(0xA65B9E7A),
    themeTertiary = Color(0x275B9E7A),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val BlueLightColors = Colors(
    backgroundPrimary = Color(0xFFF0F0F3),
    backgroundSecondary = Color(0xFFF6F6F9),
    foregroundPrimary = Color(0xFFFCFCFF),
    foregroundSecondary = Color(0xFFF0F0F3),
    themePrimary = Color(0xFF0099DF),
    themeSecondary = Color(0xA60099DF),
    themeTertiary = Color(0x270099DF),
    textPrimary = DefaultLightColors.textPrimary,
    textSecondary = DefaultLightColors.textSecondary
)

private val RedDarkColors = Colors(
    backgroundPrimary = Color(0xFF271816),
    backgroundSecondary = Color(0xFF322220),
    foregroundPrimary = Color(0xFF3D2C2A),
    foregroundSecondary = Color(0xFF554240),
    themePrimary = Color(0xFFB9856D),
    themeSecondary = Color(0xA6B9856D),
    themeTertiary = Color(0x40B9856D),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val PinkDarkColors = Colors(
    backgroundPrimary = Color(0xFF26181A),
    backgroundSecondary = Color(0xFF312224),
    foregroundPrimary = Color(0xFF3D2C2E),
    foregroundSecondary = Color(0xFF544244),
    themePrimary = Color(0xFFBA837B),
    themeSecondary = Color(0xA6BA837B),
    themeTertiary = Color(0x40BA837B),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val PurpleDarkColors = Colors(
    backgroundPrimary = Color(0xFF1E1A24),
    backgroundSecondary = Color(0xFF28242F),
    foregroundPrimary = Color(0xFF332E3A),
    foregroundSecondary = Color(0xFF494550),
    themePrimary = Color(0xFF9F88AD),
    themeSecondary = Color(0xA69F88AD),
    themeTertiary = Color(0x409F88AD),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val OrangeDarkColors = Colors(
    backgroundPrimary = Color(0xFF25190E),
    backgroundSecondary = Color(0xFF302318),
    foregroundPrimary = Color(0xFF3B2E22),
    foregroundSecondary = Color(0xFF534437),
    themePrimary = Color(0xFFAE8B5D),
    themeSecondary = Color(0xA6AE8B5D),
    themeTertiary = Color(0x40AE8B5D),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val YellowDarkColors = Colors(
    backgroundPrimary = Color(0xFF221B0D),
    backgroundSecondary = Color(0xFF2D2516),
    foregroundPrimary = Color(0xFF382F20),
    foregroundSecondary = Color(0xFF4F4535),
    themePrimary = Color(0xFFA18F5C),
    themeSecondary = Color(0xA6A18F5C),
    themeTertiary = Color(0x40A18F5C),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val GreenDarkColors = Colors(
    backgroundPrimary = Color(0xFF191C1A),
    backgroundSecondary = Color(0xFF232624),
    foregroundPrimary = Color(0xFF2E312E),
    foregroundSecondary = Color(0xFF444844),
    themePrimary = Color(0xFF7F9687),
    themeSecondary = Color(0xA67F9687),
    themeTertiary = Color(0x407F9687),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val BlueDarkColors = Colors(
    backgroundPrimary = Color(0xFF141C23),
    backgroundSecondary = Color(0xFF1E262E),
    foregroundPrimary = Color(0xFF293139),
    foregroundSecondary = Color(0xFF3F484F),
    themePrimary = Color(0xFF8091B1),
    themeSecondary = Color(0xA68091B1),
    themeTertiary = Color(0x408091B1),
    textPrimary = DefaultDarkColors.textPrimary,
    textSecondary = DefaultDarkColors.textSecondary
)

private val RedBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF130C0B),
    foregroundPrimary = Color(0xFF271816),
    foregroundSecondary = Color(0xFF3D2C2A),
    themePrimary = Color(0xFFB9856D),
    themeSecondary = Color(0xA6B9856D),
    themeTertiary = Color(0x45B9856D),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

private val PinkBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF130C0D),
    foregroundPrimary = Color(0xFF26181A),
    foregroundSecondary = Color(0xFF3D2C2E),
    themePrimary = Color(0xFFBA837B),
    themeSecondary = Color(0xA6BA837B),
    themeTertiary = Color(0x45BA837B),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

private val PurpleBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF0F0D12),
    foregroundPrimary = Color(0xFF1E1A24),
    foregroundSecondary = Color(0xFF332E3A),
    themePrimary = Color(0xFF9F88AD),
    themeSecondary = Color(0xA69F88AD),
    themeTertiary = Color(0x459F88AD),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

private val OrangeBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF120C07),
    foregroundPrimary = Color(0xFF25190E),
    foregroundSecondary = Color(0xFF3B2E22),
    themePrimary = Color(0xFFAE8B5D),
    themeSecondary = Color(0xA6AE8B5D),
    themeTertiary = Color(0x45AE8B5D),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

private val YellowBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF110D06),
    foregroundPrimary = Color(0xFF221B0D),
    foregroundSecondary = Color(0xFF382F20),
    themePrimary = Color(0xFFA18F5C),
    themeSecondary = Color(0xA6A18F5C),
    themeTertiary = Color(0x45A18F5C),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

private val GreenBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF0C0E0D),
    foregroundPrimary = Color(0xFF191C1A),
    foregroundSecondary = Color(0xFF2E312E),
    themePrimary = Color(0xFF7F9687),
    themeSecondary = Color(0xA67F9687),
    themeTertiary = Color(0x457F9687),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

private val BlueBlackColors = Colors(
    backgroundPrimary = Color(0xFF000000),
    backgroundSecondary = Color(0xFF0A0E11),
    foregroundPrimary = Color(0xFF141C23),
    foregroundSecondary = Color(0xFF293139),
    themePrimary = Color(0xFF8091B1),
    themeSecondary = Color(0xA68091B1),
    themeTertiary = Color(0x458091B1),
    textPrimary = DefaultBlackColors.textPrimary,
    textSecondary = DefaultBlackColors.textSecondary
)

/**
 * Returns a default color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun defaultColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) DefaultBlackColors else DefaultDarkColors
    else -> DefaultLightColors
}

/**
 * Returns a red color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun redColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) RedBlackColors else RedDarkColors
    else -> RedLightColors
}

/**
 * Returns a pink color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun pinkColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) PinkBlackColors else PinkDarkColors
    else -> PinkLightColors
}

/**
 * Returns a purple color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun purpleColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) PurpleBlackColors else PurpleDarkColors
    else -> PurpleLightColors
}

/**
 * Returns a orange color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun orangeColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) OrangeBlackColors else OrangeDarkColors
    else -> OrangeLightColors
}

/**
 * Returns a yellow color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun yellowColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) YellowBlackColors else YellowDarkColors
    else -> YellowLightColors
}

/**
 * Returns a green color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun greenColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) GreenBlackColors else GreenDarkColors
    else -> GreenLightColors
}

/**
 * Returns a blue color scheme.
 * @param darkMode whether to use dark mode color scheme.
 * @param blackDarkMode requires [darkMode] is true, whether to use a pure black mode scheme.
 * @return [Colors]
 */
fun blueColors(darkMode: Boolean = false, blackDarkMode: Boolean = false) = when {
    darkMode -> if (blackDarkMode) BlueBlackColors else BlueDarkColors
    else -> BlueLightColors
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