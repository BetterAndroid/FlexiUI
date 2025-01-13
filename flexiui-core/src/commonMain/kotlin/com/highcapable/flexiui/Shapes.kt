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

package com.highcapable.flexiui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Shapes defines for Flexi UI.
 */
@Immutable
data class Shapes(
    val primary: CornerBasedShape,
    val secondary: CornerBasedShape,
    val tertiary: CornerBasedShape
)

/**
 * Descriptor for [Shapes].
 */
@Stable
internal enum class ShapesDescriptor {
    Primary,
    Secondary,
    Tertiary
}

internal val LocalShapes = staticCompositionLocalOf { DefaultShapes }

internal val DefaultShapes = Shapes(
    primary = RoundedCornerShape(15.dp),
    secondary = RoundedCornerShape(10.dp),
    tertiary = CircleShape
)

/**
 * Gets a [Shape] from descriptor.
 * @see ShapesDescriptor.toShape
 * @param value the descriptor.
 * @return [Shape]
 */
internal fun Shapes.fromDescriptor(value: ShapesDescriptor): Shape = when (value) {
    ShapesDescriptor.Primary -> primary
    ShapesDescriptor.Secondary -> secondary
    ShapesDescriptor.Tertiary -> tertiary
}

/**
 * Converts a descriptor to a [Shape].
 * @see Shapes.fromDescriptor
 * @return [Shape]
 */
@ReadOnlyComposable
@Composable
internal fun ShapesDescriptor.toShape(): Shape = LocalShapes.current.fromDescriptor(this)