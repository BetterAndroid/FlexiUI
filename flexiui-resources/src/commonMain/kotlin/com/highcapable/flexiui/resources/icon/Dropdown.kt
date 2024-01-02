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
 * This file is created by fankes on 2023/11/17.
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
import com.highcapable.flexiui.resources.Icons

val Icons.Dropdown by lazy {
    ImageVector(
        name = "dropdown",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ) {
        path(
            fill = SolidColor(Color.White),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(8.12f, 9.29f)
            lineTo(12f, 13.17f)
            lineToRelative(3.88f, -3.88f)
            curveToRelative(0.39f, -0.39f, 1.02f, -0.39f, 1.41f, 0f)
            curveToRelative(0.39f, 0.39f, 0.39f, 1.02f, 0f, 1.41f)
            lineToRelative(-4.59f, 4.59f)
            curveToRelative(-0.39f, 0.39f, -1.02f, 0.39f, -1.41f, 0f)
            lineTo(6.7f, 10.7f)
            curveToRelative(-0.39f, -0.39f, -0.39f, -1.02f, 0f, -1.41f)
            curveToRelative(0.39f, -0.38f, 1.03f, -0.39f, 1.42f, 0f)
            close()
        }
    }
}