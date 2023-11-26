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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.DefaultShapes
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.borderOrNot

@Immutable
data class AreaBoxStyle(
    val padding: PaddingValues,
    val shape: Shape,
    val border: BorderStroke,
    val shadowSize: Dp
)

@Composable
fun AreaBox(
    modifier: Modifier = Modifier,
    initializer: Modifier.() -> Modifier = { Modifier },
    color: Color = AreaBox.color,
    style: AreaBoxStyle = AreaBox.style,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides style.shape
    ) {
        Box(
            modifier = Modifier.areaBox(color, style, modifier, initializer),
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
        )
    }
}

@Composable
fun AreaRow(
    modifier: Modifier = Modifier,
    initializer: Modifier.() -> Modifier = { Modifier },
    color: Color = AreaBox.color,
    style: AreaBoxStyle = AreaBox.style,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides style.shape
    ) {
        Row(
            modifier = Modifier.areaBox(color, style, modifier, initializer),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

@Composable
fun AreaColumn(
    modifier: Modifier = Modifier,
    initializer: Modifier.() -> Modifier = { Modifier },
    color: Color = AreaBox.color,
    style: AreaBoxStyle = AreaBox.style,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalInAreaBox provides true,
        LocalAreaBoxShape provides style.shape
    ) {
        Column(
            modifier = Modifier.areaBox(color, style, modifier, initializer),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
}

private fun Modifier.areaBox(
    color: Color,
    style: AreaBoxStyle,
    then: Modifier,
    initializer: Modifier.() -> Modifier
) = composed {
    initializer()
        .shadow(style.shadowSize, style.shape)
        .clip(style.shape)
        .background(color, style.shape)
        .borderOrNot(style.border, style.shape)
        .then(then)
        .padding(style.padding)
}

object AreaBox {
    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = defaultAreaBoxColor()
    val style: AreaBoxStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultAreaBoxStyle()
}

internal val LocalInAreaBox = compositionLocalOf { false }

internal val LocalAreaBoxShape = compositionLocalOf { DefaultAreaBoxShape }

internal val DefaultAreaBoxShape: Shape = DefaultShapes.primary

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxStyle() = AreaBoxStyle(
    padding = PaddingValues(LocalSizes.current.spacingPrimary),
    shape = LocalShapes.current.primary,
    border = defaultAreaBoxBorder(),
    shadowSize = DefaultAreaBoxShadowSize
)

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxColor() = LocalColors.current.foregroundPrimary

@Composable
@ReadOnlyComposable
private fun defaultAreaBoxBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

private val DefaultAreaBoxShadowSize = 0.dp