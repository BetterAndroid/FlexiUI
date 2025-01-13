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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.toColor

/**
 * Colors defines for surface.
 * @see SurfaceDefaults.colors
 */
@Immutable
data class SurfaceColors(
    val contentColor: Color,
    val backgroundColor: Color
)

/**
 * Flexi UI surface.
 *
 * A surface have a background color and default padding.
 * @param modifier the [Modifier] to be applied to this surface.
 * @param initializer the [Modifier] initializer, earlies than [modifier].
 * @param colors the colors of surface, default is [SurfaceDefaults.colors].
 * @param padding the padding of surface, default is [SurfaceDefaults.padding].
 * @param contentAlignment the alignment of the content inside this surface, default is [Alignment.TopStart].
 * @param propagateMinConstraints whether to propagate the min constraints from the content to this surface.
 * @param content the content of the [Surface].
 */
@Composable
fun Surface(
    modifier: Modifier = Modifier,
    initializer: @Composable Modifier.() -> Modifier = { Modifier },
    colors: SurfaceColors = SurfaceDefaults.colors(),
    padding: ComponentPadding = SurfaceDefaults.padding,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInSurface provides true,
        LocalColors provides LocalColors.current.copy(
            backgroundPrimary = colors.backgroundColor,
            textPrimary = colors.contentColor
        )
    ) {
        Box(
            modifier = Modifier.surface(colors, padding, modifier, initializer),
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
        )
    }
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

/**
 * Defaults of surface.
 */
object SurfaceDefaults {

    /**
     * Creates a [SurfaceColors] with the default values.
     * @param contentColor the content color, usually for the text color.
     * @param backgroundColor the background color.
     * @return [SurfaceColors]
     */
    @Composable
    fun colors(
        contentColor: Color = SurfaceProperties.ContentColor.toColor(),
        backgroundColor: Color = SurfaceProperties.BackgroundColor.toColor()
    ) = SurfaceColors(contentColor, backgroundColor)

    /**
     * Returns the default padding of surface.
     * @return [ComponentPadding]
     */
    val padding: ComponentPadding
        @ReadOnlyComposable
        @Composable
        get() = SurfaceProperties.Padding.toPadding()
}

@Stable
internal object SurfaceProperties {
    val ContentColor = ColorsDescriptor.TextPrimary
    val BackgroundColor = ColorsDescriptor.BackgroundPrimary
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingPrimary)
}

internal val LocalInSurface = compositionLocalOf { false }