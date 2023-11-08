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
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.orElse

// TODO: Linkage BetterAndroid SafeArea (SystemBarsController)

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    padding: Dp = Surface.padding,
    topPadding: Dp = Dp.Unspecified,
    startPadding: Dp = Dp.Unspecified,
    bottomPadding: Dp = Dp.Unspecified,
    endPadding: Dp = Dp.Unspecified,
    color: Color = Surface.color,
    contentColor: Color = Surface.contentColor,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides LocalColors.current.copy(
            backgroundPrimary = color,
            textPrimary = contentColor
        )
    ) { Box(modifier.surface(padding, topPadding, startPadding, bottomPadding, endPadding, color), content = content) }
}

private fun Modifier.surface(
    padding: Dp,
    topPadding: Dp,
    startPadding: Dp,
    bottomPadding: Dp,
    endPadding: Dp,
    color: Color
) = background(color = color)
    .padding(
        top = topPadding.orElse() ?: padding,
        start = startPadding.orElse() ?: padding,
        bottom = bottomPadding.orElse() ?: padding,
        end = endPadding.orElse() ?: padding
    )

object Surface {
    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfaceColor()
    val contentColor: Color
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfaceContentColor()
    val padding: Dp
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfacePadding()
}

@Composable
@ReadOnlyComposable
private fun defaultSurfacePadding() = LocalSizes.current.spacingPrimary

@Composable
@ReadOnlyComposable
private fun defaultSurfaceColor() = LocalColors.current.backgroundPrimary

@Composable
@ReadOnlyComposable
private fun defaultSurfaceContentColor() = LocalColors.current.textPrimary