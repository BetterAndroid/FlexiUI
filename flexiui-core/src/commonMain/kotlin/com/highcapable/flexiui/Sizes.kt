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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalFlexiUISizesApi
@Immutable
data class Sizes(
    val spacingPrimary: Dp,
    val spacingSecondary: Dp,
    val spacingTertiary: Dp,
    val iconSizePrimary: Dp,
    val iconSizeSecondary: Dp,
    val iconSizeTertiary: Dp,
    val zoomSizePrimary: Dp,
    val zoomSizeSecondary: Dp,
    val zoomSizeTertiary: Dp,
    val borderSizePrimary: Dp,
    val borderSizeSecondary: Dp,
    val borderSizeTertiary: Dp
)

internal val LocalSizes = staticCompositionLocalOf { DefaultSizes }

internal val DefaultSizes = Sizes(
    spacingPrimary = 15.dp,
    spacingSecondary = 10.dp,
    spacingTertiary = 5.dp,
    iconSizePrimary = 27.dp,
    iconSizeSecondary = 25.dp,
    iconSizeTertiary = 20.dp,
    zoomSizePrimary = 15.dp,
    zoomSizeSecondary = 10.dp,
    zoomSizeTertiary = 8.dp,
    borderSizePrimary = 2.dp,
    borderSizeSecondary = 1.dp,
    borderSizeTertiary = 0.dp
)

/**
 * The [Sizes] is experimental, the relevant design specifications for size are still being improved,
 * this is the old design plan.
 *
 * Some sizes will modify in the future.
 */
@RequiresOptIn(
    message = "The Sizes is experimental, the relevant design specifications for size are still being improved, this is the old design plan.\n" +
        "Some sizes will modify in the future.",
    level = RequiresOptIn.Level.WARNING
)
@MustBeDocumented
annotation class ExperimentalFlexiUISizesApi