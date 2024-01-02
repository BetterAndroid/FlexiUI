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
 * This file is created by fankes on 2023/11/10.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.LocalSizes

@Immutable
data class IconStyle(
    val size: Dp,
    val tint: Color
)

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    style: IconStyle = Icon.style
) {
    val painter = rememberVectorPainter(imageVector)
    Icon(painter, contentDescription, modifier, style)
}

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    style: IconStyle = Icon.style
) {
    // TODO: b/149735981 semantics for content description
    val colorFilter = if (style.tint.isUnspecified) null else ColorFilter.tint(style.tint)
    val semantics = if (contentDescription != null)
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    else Modifier
    Box(
        modifier = modifier.toolingGraphicsLayer()
            .defaultSizeFor(style, painter)
            .paint(
                painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit
            )
            .then(semantics)
    )
}

private fun Modifier.defaultSizeFor(
    style: IconStyle,
    painter: Painter
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "defaultSizeFor"
        properties["style"] = style
        properties["painter"] = painter
    }
) {
    then(when {
        style.size.isSpecified ||
            painter.intrinsicSize == Size.Unspecified ||
            painter.intrinsicSize.isInfinite() ->
            Modifier.size(style.size.orNull() ?: defaultIconSize())
        else -> Modifier
    })
}

object Icon {
    val style: IconStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalIconStyle.current
}

internal val LocalIconStyle = compositionLocalOf { DefaultIconStyle }

private val DefaultIconStyle = IconStyle(
    size = Dp.Unspecified,
    tint = Color.Unspecified
)

@Composable
@ReadOnlyComposable
private fun defaultIconSize() = LocalSizes.current.iconSizePrimary

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()