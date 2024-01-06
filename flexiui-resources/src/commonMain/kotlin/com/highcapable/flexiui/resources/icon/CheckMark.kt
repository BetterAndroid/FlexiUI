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
 * This file is created by fankes on 2023/11/11.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.resources.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ImageVector
import com.highcapable.flexiui.resources.FlexiIcons

val FlexiIcons.CheckMark by lazy {
    ImageVector(
        name = "check_mark",
        defaultWidth = 32.dp,
        defaultHeight = 32.dp,
        viewportWidth = 48f,
        viewportHeight = 48f
    ) {
        path(
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 7f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(43f, 11f)
            lineTo(16.875f, 37f)
            lineTo(5f, 25.1818f)
        }
    }
}