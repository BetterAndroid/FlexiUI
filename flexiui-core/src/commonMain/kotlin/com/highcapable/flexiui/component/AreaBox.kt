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

package com.highcapable.flexiui.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.highcapable.flexiui.DefaultShapes
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.borderOrNot
import com.highcapable.flexiui.utils.orElse

@Composable
fun AreaBox(
    modifier: Modifier = Modifier,
    padding: Dp = AreaBox.padding,
    topPadding: Dp = Dp.Unspecified,
    startPadding: Dp = Dp.Unspecified,
    bottomPadding: Dp = Dp.Unspecified,
    endPadding: Dp = Dp.Unspecified,
    shape: Shape = AreaBox.shape,
    border: BorderStroke = AreaBox.border,
    color: Color = AreaBox.color,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides shape
    ) {
        Box(
            modifier = modifier.box(padding, topPadding, startPadding, bottomPadding, endPadding, shape, border, color),
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
        )
    }
}

@Composable
fun AreaRow(
    modifier: Modifier = Modifier,
    padding: Dp = AreaBox.padding,
    topPadding: Dp = Dp.Unspecified,
    startPadding: Dp = Dp.Unspecified,
    bottomPadding: Dp = Dp.Unspecified,
    endPadding: Dp = Dp.Unspecified,
    shape: Shape = AreaBox.shape,
    border: BorderStroke = AreaBox.border,
    color: Color = AreaBox.color,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides shape
    ) {
        Row(
            modifier = modifier.box(padding, topPadding, startPadding, bottomPadding, endPadding, shape, border, color),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

@Composable
fun AreaColumn(
    modifier: Modifier = Modifier,
    padding: Dp = AreaBox.padding,
    topPadding: Dp = Dp.Unspecified,
    startPadding: Dp = Dp.Unspecified,
    bottomPadding: Dp = Dp.Unspecified,
    endPadding: Dp = Dp.Unspecified,
    shape: Shape = AreaBox.shape,
    border: BorderStroke = AreaBox.border,
    color: Color = AreaBox.color,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides shape
    ) {
        Column(
            modifier = modifier.box(padding, topPadding, startPadding, bottomPadding, endPadding, shape, border, color),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
}

private fun Modifier.box(
    padding: Dp,
    topPadding: Dp,
    startPadding: Dp,
    bottomPadding: Dp,
    endPadding: Dp,
    shape: Shape,
    border: BorderStroke,
    color: Color
) = clip(shape = shape)
    .background(color = color, shape = shape)
    .borderOrNot(border, shape)
    .padding(
        top = topPadding.orElse() ?: padding,
        start = startPadding.orElse() ?: padding,
        bottom = bottomPadding.orElse() ?: padding,
        end = endPadding.orElse() ?: padding
    )

object AreaBox {
    val padding: Dp
        @Composable
        @ReadOnlyComposable
        get() = defaultAreaBoxPadding()
    val shape: Shape
        @Composable
        @ReadOnlyComposable
        get() = defaultAreaBoxShape()
    val border: BorderStroke
        @Composable
        @ReadOnlyComposable
        get() = defaultAreaBoxBorder()
    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = defaultAreaBoxColor()
}

internal val LocalInAreaBox = compositionLocalOf { false }

internal val LocalAreaBoxShape = compositionLocalOf { DefaultAreaBoxShape }

internal val DefaultAreaBoxShape: Shape = DefaultShapes.primary

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxPadding() = LocalSizes.current.spacingPrimary

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxShape() = LocalShapes.current.primary

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxColor() = LocalColors.current.foregroundPrimary