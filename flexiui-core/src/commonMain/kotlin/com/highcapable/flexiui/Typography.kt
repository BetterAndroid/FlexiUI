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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(
    val titlePrimary: TextStyle,
    val titleSecondary: TextStyle,
    val subtitle: TextStyle,
    val primary: TextStyle,
    val secondary: TextStyle
)

internal val LocalTypography = staticCompositionLocalOf { DefaultTypography }

internal val DefaultTypography = Typography(
    titlePrimary = TextStyle(
        fontSize = 25.sp,
        lineHeight = 1.5.em
    ),
    titleSecondary = TextStyle(
        fontSize = 18.sp,
        lineHeight = 1.2.em
    ),
    subtitle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 1.em
    ),
    primary = TextStyle(
        fontSize = 15.sp,
        lineHeight = 1.2.em
    ),
    secondary = TextStyle(
        fontSize = 12.sp,
        lineHeight = 1.em
    )
)