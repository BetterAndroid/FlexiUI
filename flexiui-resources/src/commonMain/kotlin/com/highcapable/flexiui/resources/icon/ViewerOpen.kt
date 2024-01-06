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
 * This file is created by fankes on 2023/11/18.
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

val FlexiIcons.ViewerOpen by lazy {
    ImageVector(
        name = "viewer_open",
        defaultWidth = 32.dp,
        defaultHeight = 32.dp,
        viewportWidth = 48f,
        viewportHeight = 48f
    ) {
        path(
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(24f, 36f)
            curveTo(35.0457f, 36f, 44f, 24f, 44f, 24f)
            curveTo(44f, 24f, 35.0457f, 12f, 24f, 12f)
            curveTo(12.9543f, 12f, 4f, 24f, 4f, 24f)
            curveTo(4f, 24f, 12.9543f, 36f, 24f, 36f)
            close()
        }
        path(
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(24f, 29f)
            curveTo(26.7614f, 29f, 29f, 26.7614f, 29f, 24f)
            curveTo(29f, 21.2386f, 26.7614f, 19f, 24f, 19f)
            curveTo(21.2386f, 19f, 19f, 21.2386f, 19f, 24f)
            curveTo(19f, 26.7614f, 21.2386f, 29f, 24f, 29f)
            close()
        }
    }
}