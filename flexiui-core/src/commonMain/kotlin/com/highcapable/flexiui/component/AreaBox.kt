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

package com.highcapable.flexiui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape

/**
 * Colors defines for area box.
 * @see AreaBoxDefaults.colors
 */
@Immutable
data class AreaBoxColors(
    val backgroundColor: Color,
    val borderColor: Color
)

/**
 * Style defines for area box.
 * @see AreaBoxDefaults.style
 */
@Immutable
data class AreaBoxStyle(
    val padding: ComponentPadding,
    val shape: Shape,
    val borderWidth: Dp,
    val shadowSize: Dp
)

/**
 * Flexi UI area box of [Box].
 * @see AreaRow
 * @see AreaColumn
 * @param modifier the [Modifier] to be applied to this area box.
 * @param initializer the [Modifier] initializer, earlies than [modifier].
 * @param colors the colors of this area box, default is [AreaBoxDefaults.colors].
 * @param style the style of this area box, default is [AreaBoxDefaults.style].
 * @param contentAlignment the alignment of the content inside this area box, default is [Alignment.TopStart].
 * @param propagateMinConstraints whether to propagate the min constraints from the content to this area box.
 * @param content the content of the [AreaBox].
 */
@Composable
fun AreaBox(
    modifier: Modifier = Modifier,
    initializer: @Composable Modifier.() -> Modifier = { Modifier },
    colors: AreaBoxColors = AreaBoxDefaults.colors(),
    style: AreaBoxStyle = AreaBoxDefaults.style(),
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides style.shape
    ) {
        Box(
            modifier = Modifier.areaBox(colors, style, modifier, initializer),
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
        )
    }
}

/**
 * Flexi UI area box of [Row].
 * @see AreaColumn
 * @see AreaBox
 * @param modifier the [Modifier] to be applied to this area row.
 * @param initializer the [Modifier] initializer, earlies than [modifier].
 * @param colors the colors of this area row, default is [AreaBoxDefaults.colors].
 * @param style the style of this area row, default is [AreaBoxDefaults.style].
 * @param horizontalArrangement the horizontal arrangement of the content inside this area row, default is [Arrangement.Start].
 * @param verticalAlignment the vertical alignment of the content inside this area row, default is [Alignment.Top].
 * @param content the content of the [AreaRow].
 */
@Composable
fun AreaRow(
    modifier: Modifier = Modifier,
    initializer: @Composable Modifier.() -> Modifier = { Modifier },
    colors: AreaBoxColors = AreaBoxDefaults.colors(),
    style: AreaBoxStyle = AreaBoxDefaults.style(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides style.shape
    ) {
        Row(
            modifier = Modifier.areaBox(colors, style, modifier, initializer),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

/**
 * Flexi UI area box of [Column].
 * @see AreaRow
 * @see AreaBox
 * @param modifier the [Modifier] to be applied to this area column.
 * @param initializer the [Modifier] initializer, earlies than [modifier].
 * @param colors the colors of this area column, default is [AreaBoxDefaults.colors].
 * @param style the style of this area column, default is [AreaBoxDefaults.style].
 * @param verticalArrangement the vertical arrangement of the content inside this area column, default is [Arrangement.Top].
 * @param horizontalAlignment the horizontal alignment of the content inside this area column, default is [Alignment.Start].
 * @param content the content of the [AreaColumn].
 */
@Composable
fun AreaColumn(
    modifier: Modifier = Modifier,
    initializer: @Composable Modifier.() -> Modifier = { Modifier },
    colors: AreaBoxColors = AreaBoxDefaults.colors(),
    style: AreaBoxStyle = AreaBoxDefaults.style(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides style.shape
    ) {
        Column(
            modifier = Modifier.areaBox(colors, style, modifier, initializer),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
}

private fun Modifier.areaBox(
    colors: AreaBoxColors,
    style: AreaBoxStyle,
    then: Modifier,
    initializer: @Composable Modifier.() -> Modifier
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "areaBox"
        properties["colors"] = colors
        properties["style"] = style
    }
) {
    initializer()
        .shadow(style.shadowSize, style.shape)
        .clip(style.shape)
        .background(colors.backgroundColor, style.shape)
        .borderOrElse(style.borderWidth, colors.borderColor, style.shape)
        .then(then)
        .padding(style.padding)
}

/**
 * Defaults of area box.
 */
object AreaBoxDefaults {

    /**
     * Creates a [AreaBoxColors] with the default values.
     * @param backgroundColor the background color of the box.
     * @param borderColor the border color of the box.
     * @return [AreaBoxColors]
     */
    @Composable
    fun colors(
        backgroundColor: Color = when (LocalInAreaBox.current) {
            true -> AreaBoxProperties.InnerBackgroundColor
            else -> AreaBoxProperties.BackgroundColor
        }.toColor(),
        borderColor: Color = AreaBoxProperties.BorderColor.toColor()
    ) = AreaBoxColors(
        backgroundColor = backgroundColor,
        borderColor = borderColor
    )

    /**
     * Creates a [AreaBoxStyle] with the default values.
     * @param padding the padding of content.
     * @param shape the shape of the box.
     * @param borderWidth the border width of the box.
     * @param shadowSize the shadow size of the box.
     * @return [AreaBoxStyle]
     */
    @Composable
    fun style(
        padding: ComponentPadding = AreaBoxProperties.Padding.toPadding(),
        shape: Shape = AreaBoxProperties.Shape.toShape(),
        borderWidth: Dp = AreaBoxProperties.BorderWidth.toDp(),
        shadowSize: Dp = AreaBoxProperties.ShadowSize
    ) = AreaBoxStyle(
        padding = padding,
        shape = shape,
        borderWidth = borderWidth,
        shadowSize = shadowSize
    )

    /**
     * Returns the child components shape of the current area box.
     *
     * Design specification: The shape of the components inside the area box
     * should be consistent with the shape of the area box.
     * @param inBox the shape of inner area box.
     * @param outBox the shape of outside area box.
     * @return [Shape]
     */
    @Composable
    fun childShape(
        inBox: Shape = LocalAreaBoxShape.current ?: AreaBoxProperties.Shape.toShape(),
        outBox: Shape = AreaBoxProperties.OutBoxShape.toShape()
    ) = when (LocalInAreaBox.current) {
        true -> inBox
        else -> outBox
    }
}

@Stable
internal object AreaBoxProperties {
    val BackgroundColor = ColorsDescriptor.ForegroundPrimary
    val InnerBackgroundColor = ColorsDescriptor.ForegroundSecondary
    val BorderColor = ColorsDescriptor.TextPrimary
    val Padding = PaddingDescriptor(SizesDescriptor.SpacingPrimary)
    val Shape = ShapesDescriptor.Primary
    val OutBoxShape = ShapesDescriptor.Secondary
    val BorderWidth = SizesDescriptor.BorderSizeTertiary
    val ShadowSize = 0.dp
}

internal val LocalInAreaBox = compositionLocalOf { false }

internal val LocalAreaBoxShape = compositionLocalOf<Shape?> { null }