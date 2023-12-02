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
 * This file is created by fankes on 2023/11/29.
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
import com.highcapable.flexiui.resources.Icons
import com.highcapable.flexiui.resources.builder.buildImageVector

val Icons.ArrowForward by lazy {
    buildImageVector(
        name = "arrow_forward",
        defaultWidth = 32.dp,
        defaultHeight = 32.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ) {
        path(
            fill = SolidColor(Color.White),
            fillAlpha = 1.0f,
            stroke = null,
            strokeAlpha = 1.0f,
            strokeLineWidth = 1.0f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Miter,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(7.38f, 21.01f)
            curveToRelative(0.49f, 0.49f, 1.28f, 0.49f, 1.77f, 0f)
            lineToRelative(8.31f, -8.31f)
            curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0f, -1.41f)
            lineTo(9.15f, 2.98f)
            curveToRelative(-0.49f, -0.49f, -1.28f, -0.49f, -1.77f, 0f)
            reflectiveCurveToRelative(-0.49f, 1.28f, 0f, 1.77f)
            lineTo(14.62f, 12f)
            lineToRelative(-7.25f, 7.25f)
            curveToRelative(-0.48f, 0.48f, -0.48f, 1.28f, 0.01f, 1.76f)
            close()
        }
    }
}