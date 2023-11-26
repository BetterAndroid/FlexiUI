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
 * This file is created by fankes on 2023/11/11.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.resources.builder

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultGroupName
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

internal inline fun buildImageVector(
    name: String = DefaultGroupName,
    defaultWidth: Dp,
    defaultHeight: Dp,
    viewportWidth: Float,
    viewportHeight: Float,
    tintColor: Color = Color.Unspecified,
    tintBlendMode: BlendMode = BlendMode.SrcIn,
    autoMirror: Boolean = false,
    builder: ImageVector.Builder.() -> Unit
) = ImageVector.Builder(
    name = name,
    defaultWidth = defaultWidth,
    defaultHeight = defaultHeight,
    viewportWidth = viewportWidth,
    viewportHeight = viewportHeight,
    tintColor = tintColor,
    tintBlendMode = tintBlendMode,
    autoMirror = autoMirror
).apply(builder).build()