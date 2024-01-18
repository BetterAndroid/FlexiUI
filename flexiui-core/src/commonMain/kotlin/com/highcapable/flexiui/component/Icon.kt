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
 * This file is created by fankes on 2023/11/10.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
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
import androidx.compose.ui.unit.isUnspecified
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.toDp

/**
 * Style defines for basic icon.
 * @see IconDefaults.style
 */
@Immutable
data class IconStyle(
    val size: Dp,
    val tint: Color
)

/**
 * Flexi UI basic icon.
 * @param imageVector the vector image to be drawn.
 * @param contentDescription the content description for this icon.
 * @param modifier the [Modifier] to be applied to this icon.
 * @param style the style of this icon.
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    style: IconStyle? = null
) {
    val painter = rememberVectorPainter(imageVector)
    Icon(painter, contentDescription, modifier, style)
}

/**
 * Flexi UI basic icon.
 * @param painter the painter to be drawn.
 * @param contentDescription the content description for this icon.
 * @param modifier the [Modifier] to be applied to this icon.
 * @param style the style of this icon.
 */
@Composable
fun Icon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    style: IconStyle? = null
) {
    val defaultStyle = LocalIconStyle.current.orNull() ?: IconDefaults.style()
    val currentStyle = style?.let {
        it.copy(
            size = it.size.orNull() ?: defaultStyle.size,
            tint = it.tint.orNull() ?: defaultStyle.tint
        )
    } ?: defaultStyle
    val colorFilter = if (currentStyle.tint.isUnspecified) null else ColorFilter.tint(currentStyle.tint)
    val semantics = if (contentDescription != null)
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    else Modifier
    Box(
        modifier = modifier
            .toolingGraphicsLayer()
            .defaultSizeFor(currentStyle, painter)
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
            Modifier.size(style.size.orNull() ?: IconDefaults.style().size)
        else -> Modifier
    })
}

/**
 * Defaults of basic icon.
 */
object IconDefaults {

    /**
     * Creates a [IconStyle] with the default values.
     * @param size the size.
     * @param tint the tint.
     * @return [IconStyle]
     */
    @Composable
    fun style(
        size: Dp = IconProperties.Size.toDp(),
        tint: Color = IconProperties.Tint
    ) = IconStyle(
        size = size,
        tint = tint
    )
}

@Stable
internal object IconProperties {
    val Size = SizesDescriptor.IconSizePrimary
    val Tint = Color.Unspecified
}

/**
 * Composition local containing the preferred [IconStyle]
 * that will be used by [Icon] by default.
 */
val LocalIconStyle = compositionLocalOf { DefaultIconStyle }

private val DefaultIconStyle = IconStyle(
    size = Dp.Unspecified,
    tint = Color.Unspecified
)

private fun IconStyle.orNull() = if (size.isUnspecified && tint.isUnspecified) null else this

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()