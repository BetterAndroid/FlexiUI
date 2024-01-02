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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes

@Immutable
data class SurfaceColors(
    val contentColor: Color,
    val backgroundColor: Color
)

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    initializer: @Composable Modifier.() -> Modifier = { Modifier },
    colors: SurfaceColors = Surface.colors,
    padding: ComponentPadding = Surface.padding,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInSurface provides true,
        LocalColors provides LocalColors.current.copy(
            backgroundPrimary = colors.backgroundColor,
            textPrimary = colors.contentColor
        )
    ) { Box(Modifier.surface(colors, padding, modifier, initializer), content = content) }
}

private fun Modifier.surface(
    colors: SurfaceColors,
    padding: ComponentPadding,
    then: Modifier,
    initializer: @Composable Modifier.() -> Modifier
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "surface"
        properties["colors"] = colors
        properties["padding"] = padding
    }
) {
    initializer()
        .background(colors.backgroundColor)
        .then(then)
        .padding(padding)
}

object Surface {
    val colors: SurfaceColors
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfaceColors()
    val padding: ComponentPadding
        @Composable
        @ReadOnlyComposable
        get() = defaultSurfacePadding()
}

internal val LocalInSurface = compositionLocalOf { false }

@Composable
@ReadOnlyComposable
private fun defaultSurfaceColors() = SurfaceColors(
    contentColor = LocalColors.current.textPrimary,
    backgroundColor = LocalColors.current.backgroundPrimary
)

@Composable
@ReadOnlyComposable
private fun defaultSurfacePadding() = ComponentPadding(LocalSizes.current.spacingPrimary)