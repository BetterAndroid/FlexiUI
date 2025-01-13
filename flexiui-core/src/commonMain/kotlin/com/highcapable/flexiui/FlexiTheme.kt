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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Customize Flexi UI theme styles.
 *
 * Use this function to provide your own theme styles to the content.
 * @param colors the colors, default is [FlexiTheme.colors].
 * @param shapes the shapes, default is [FlexiTheme.shapes].
 * @param typography the typography, default is [FlexiTheme.typography].
 * @param content the content.
 */
@Composable
fun FlexiTheme(
    colors: Colors = FlexiTheme.colors,
    shapes: Shapes = FlexiTheme.shapes,
    typography: Typography = FlexiTheme.typography,
    content: @Composable () -> Unit
) {
    FlexiTheme(colors, shapes, typography, FlexiTheme.sizes, content)
}

/**
 * Customize Flexi UI theme styles.
 *
 * Use this function to provide your own theme styles to the content.
 *
 * - Note: The [sizes] is experimental for now, its may be change in the future.
 * @param colors the colors, default is [FlexiTheme.colors].
 * @param shapes the shapes, default is [FlexiTheme.shapes].
 * @param typography the typography, default is [FlexiTheme.typography].
 * @param sizes the sizes.
 * @param content the content.
 */
@Composable
fun FlexiTheme(
    colors: Colors = FlexiTheme.colors,
    shapes: Shapes = FlexiTheme.shapes,
    typography: Typography = FlexiTheme.typography,
    sizes: Sizes,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides shapes,
        LocalTypography provides typography,
        LocalSizes provides sizes
    ) { FlexiThemeContent(content) }
}

/**
 * Defaults of Flexi UI theme styles.
 */
object FlexiTheme {

    /**
     * Retrieves the current [Colors] at the call site's position in the hierarchy.
     * @return [Colors]
     */
    val colors: Colors
        @ReadOnlyComposable
        @Composable
        get() = LocalColors.current

    /**
     * Retrieves the current [Shapes] at the call site's position in the hierarchy.
     * @return [Shapes]
     */
    val shapes: Shapes
        @ReadOnlyComposable
        @Composable
        get() = LocalShapes.current

    /**
     * Retrieves the current [Typography] at the call site's position in the hierarchy.
     * @return [Typography]
     */
    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current

    /**
     * Retrieves the current [Sizes] at the call site's position in the hierarchy.
     * @return [Sizes]
     */
    val sizes: Sizes
        @ReadOnlyComposable
        @Composable
        get() = LocalSizes.current
}

@Composable
internal expect fun FlexiThemeContent(content: @Composable () -> Unit)