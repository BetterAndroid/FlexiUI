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
 * This file is created by fankes on 2024/1/3.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun SubcomposeRow(
    modifier: Modifier = Modifier,
    count: Int = 1,
    spacingBetween: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    SubcomposeLayout(modifier = modifier) { constraints ->
        var subcomposeIndex = 0
        val coerceCount = count.coerceAtLeast(1)
        val spaceBetweenComponents = if (coerceCount > 1) with(density) { spacingBetween.roundToPx() } else 0
        var placeables = subcompose(subcomposeIndex++, content).map { it.measure(constraints) }
        var maxWidth = 0
        var maxHeight = 0
        var layoutWidth: Int
        placeables.forEach { placeable: Placeable ->
            maxWidth = placeable.width.coerceAtLeast(maxWidth)
                .coerceAtMost(((constraints.maxWidth - spaceBetweenComponents) / coerceCount))
            maxHeight = placeable.height.coerceAtLeast(maxHeight)
        }
        layoutWidth = maxWidth
        // Remeasure every element using width of longest item using it as min width.
        // Our max width is half of the remaining area after we subtract space between components.
        // and we constraint its maximum width to half width minus space between.
        if (placeables.isNotEmpty() && placeables.size > 1) {
            placeables = subcompose(subcomposeIndex, content).map { measurable ->
                measurable.measure(
                    constraints.copy(
                        minWidth = maxWidth,
                        maxWidth = ((constraints.maxWidth - spaceBetweenComponents) / coerceCount)
                            .coerceAtLeast(maxWidth)
                    )
                )
            }
            layoutWidth = (placeables.sumOf { it.width } + spaceBetweenComponents)
                .coerceAtMost(constraints.maxWidth)
            maxHeight = placeables.maxOf { it.height }
        }
        layout(layoutWidth, maxHeight) {
            var xPos = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(xPos, 0)
                xPos += placeable.width + spaceBetweenComponents
            }
        }
    }
}