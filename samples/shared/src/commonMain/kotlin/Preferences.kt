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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.highcapable.flexiui.blueColors
import com.highcapable.flexiui.defaultColors
import com.highcapable.flexiui.greenColors
import com.highcapable.flexiui.isSystemColorAvailable
import com.highcapable.flexiui.orangeColors
import com.highcapable.flexiui.pinkColors
import com.highcapable.flexiui.purpleColors
import com.highcapable.flexiui.redColors
import com.highcapable.flexiui.systemColors
import com.highcapable.flexiui.yellowColors

@Stable
data class AppPreferences(
    var colorScheme: MutableState<ColorScheme> = mutableStateOf(ColorScheme.Default),
    var darkMode: MutableState<Boolean> = mutableStateOf(false),
    var followSystemDarkMode: MutableState<Boolean> = mutableStateOf(false)
)

// TODO: Include ColorScheme to Flexi UI's Colors.kt

@Stable
val Preferences = AppPreferences()

@Stable
enum class ColorScheme {
    Default,
    DefaultBlack,
    System,
    SystemBlack,
    Red,
    RedBlack,
    Pink,
    PinkBlack,
    Purple,
    PurpleBlack,
    Orange,
    OrangeBlack,
    Yellow,
    YellowBlack,
    Green,
    GreenBlack,
    Blue,
    BlueBlack
}

@Composable
fun colorSchemes(): Array<ColorScheme> {
    val defaultColors = arrayOf(
        ColorScheme.Default,
        ColorScheme.DefaultBlack
    )
    val systemColors = arrayOf(
        ColorScheme.System,
        ColorScheme.SystemBlack
    )
    val presetColors = arrayOf(
        ColorScheme.Red,
        ColorScheme.RedBlack,
        ColorScheme.Pink,
        ColorScheme.PinkBlack,
        ColorScheme.Purple,
        ColorScheme.PurpleBlack,
        ColorScheme.Orange,
        ColorScheme.OrangeBlack,
        ColorScheme.Yellow,
        ColorScheme.YellowBlack,
        ColorScheme.Green,
        ColorScheme.GreenBlack,
        ColorScheme.Blue,
        ColorScheme.BlueBlack
    )
    return if (isSystemColorAvailable())
        defaultColors + systemColors + presetColors
    else defaultColors + presetColors
}

@Composable
fun ColorScheme.toColors(darkMode: Boolean) = when (this) {
    ColorScheme.Default -> defaultColors(darkMode)
    ColorScheme.DefaultBlack -> defaultColors(darkMode, blackDarkMode = true)
    ColorScheme.System -> systemColors(darkMode)
    ColorScheme.SystemBlack -> systemColors(darkMode, blackDarkMode = true)
    ColorScheme.Red -> redColors(darkMode)
    ColorScheme.RedBlack -> redColors(darkMode, blackDarkMode = true)
    ColorScheme.Pink -> pinkColors(darkMode)
    ColorScheme.PinkBlack -> pinkColors(darkMode, blackDarkMode = true)
    ColorScheme.Purple -> purpleColors(darkMode)
    ColorScheme.PurpleBlack -> purpleColors(darkMode, blackDarkMode = true)
    ColorScheme.Orange -> orangeColors(darkMode)
    ColorScheme.OrangeBlack -> orangeColors(darkMode, blackDarkMode = true)
    ColorScheme.Yellow -> yellowColors(darkMode)
    ColorScheme.YellowBlack -> yellowColors(darkMode, blackDarkMode = true)
    ColorScheme.Green -> greenColors(darkMode)
    ColorScheme.GreenBlack -> greenColors(darkMode, blackDarkMode = true)
    ColorScheme.Blue -> blueColors(darkMode)
    ColorScheme.BlueBlack -> blueColors(darkMode, blackDarkMode = true)
}

@Stable
fun ColorScheme.toName() = when (this) {
    ColorScheme.Default -> "Default"
    ColorScheme.DefaultBlack -> "Default (Black)"
    ColorScheme.System -> "System"
    ColorScheme.SystemBlack -> "System (Black)"
    ColorScheme.Red -> "Red"
    ColorScheme.RedBlack -> "Red (Black)"
    ColorScheme.Pink -> "Pink"
    ColorScheme.PinkBlack -> "Pink (Black)"
    ColorScheme.Purple -> "Purple"
    ColorScheme.PurpleBlack -> "Purple (Black)"
    ColorScheme.Orange -> "Orange"
    ColorScheme.OrangeBlack -> "Orange (Black)"
    ColorScheme.Yellow -> "Yellow"
    ColorScheme.YellowBlack -> "Yellow (Black)"
    ColorScheme.Green -> "Green"
    ColorScheme.GreenBlack -> "Green (Black)"
    ColorScheme.Blue -> "Blue"
    ColorScheme.BlueBlack -> "Blue (Black)"
}