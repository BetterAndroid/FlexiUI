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
 * This file is created by fankes on 2023/11/6.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.orElse

// TODO: Linkage BetterAndroid SafeArea (SystemBarsController)

@Immutable
data class SurfaceColors(
    val contentColor: Color,
    val backgroundColor: Color
)

@Immutable
data class SurfaceStyle(
    val padding: Dp,
    val topPadding: Dp,
    val startPadding: Dp,
    val bottomPadding: Dp,
    val endPadding: Dp
)

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    colors: SurfaceColors = Surface.colors,
    style: SurfaceStyle = Surface.style,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides LocalColors.current.copy(
            backgroundPrimary = colors.backgroundColor,
            textPrimary = colors.contentColor
        )
    ) { Box(modifier.surface(colors, style), content = content) }
}

private fun Modifier.surface(colors: SurfaceColors, style: SurfaceStyle) =
    background(colors.backgroundColor).padding(
        top = style.topPadding.orElse() ?: style.padding,
        start = style.startPadding.orElse() ?: style.padding,
        bottom = style.bottomPadding.orElse() ?: style.padding,
        end = style.endPadding.orElse() ?: style.padding
    )

object Surface {
    val colors: SurfaceColors
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfaceColors()
    val style: SurfaceStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfaceStyle()
}

@Composable
@ReadOnlyComposable
private fun defaultSurfaceColors() = SurfaceColors(
    contentColor = LocalColors.current.textPrimary,
    backgroundColor = LocalColors.current.backgroundPrimary
)

@Composable
@ReadOnlyComposable
private fun defaultSurfaceStyle() = SurfaceStyle(
    padding = LocalSizes.current.spacingPrimary,
    topPadding = Dp.Unspecified,
    startPadding = Dp.Unspecified,
    bottomPadding = Dp.Unspecified,
    endPadding = Dp.Unspecified
)