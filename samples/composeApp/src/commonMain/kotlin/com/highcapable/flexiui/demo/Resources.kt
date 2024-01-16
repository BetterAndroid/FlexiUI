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
 * This file is created by fankes on 2024/1/10.
 */
package com.highcapable.flexiui.demo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ImageVector
import com.highcapable.flexiui.resources.FlexiIcons

val FlexiIcons.GitHub by lazy {
    ImageVector(
        name = "github",
        defaultWidth = 150.dp,
        defaultHeight = 150.dp,
        viewportWidth = 1024f,
        viewportHeight = 1024f
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
            moveTo(512f, 12.6f)
            curveToRelative(-282.8f, 0f, -512f, 229.2f, -512f, 512f)
            curveToRelative(0f, 226.2f, 146.7f, 418.1f, 350.1f, 485.8f)
            curveToRelative(25.6f, 4.7f, 35f, -11.1f, 35f, -24.6f)
            curveToRelative(0f, -12.2f, -0.5f, -52.5f, -0.7f, -95.3f)
            curveToRelative(-142.5f, 31f, -172.5f, -60.4f, -172.5f, -60.4f)
            curveToRelative(-23.3f, -59.2f, -56.8f, -74.9f, -56.8f, -74.9f)
            curveToRelative(-46.5f, -31.8f, 3.5f, -31.1f, 3.5f, -31.1f)
            curveToRelative(51.4f, 3.6f, 78.5f, 52.8f, 78.5f, 52.8f)
            curveToRelative(45.7f, 78.3f, 119.8f, 55.6f, 149f, 42.6f)
            curveToRelative(4.6f, -33.1f, 17.9f, -55.7f, 32.5f, -68.5f)
            curveToRelative(-113.7f, -12.9f, -233.3f, -56.9f, -233.3f, -253f)
            curveToRelative(0f, -55.9f, 20f, -101.6f, 52.8f, -137.4f)
            curveToRelative(-5.3f, -12.9f, -22.8f, -65f, 5f, -135.5f)
            curveToRelative(0f, 0f, 43f, -13.8f, 140.8f, 52.5f)
            curveToRelative(40.8f, -11.4f, 84.6f, -17f, 128.2f, -17.2f)
            curveToRelative(43.5f, 0.2f, 87.3f, 5.9f, 128.3f, 17.2f)
            curveToRelative(97.7f, -66.2f, 140.6f, -52.5f, 140.6f, -52.5f)
            curveToRelative(27.9f, 70.5f, 10.3f, 122.6f, 5f, 135.5f)
            curveToRelative(32.8f, 35.8f, 52.7f, 81.5f, 52.7f, 137.4f)
            curveToRelative(0f, 196.6f, -119.8f, 239.9f, -233.8f, 252.6f)
            curveToRelative(18.4f, 15.9f, 34.7f, 47f, 34.7f, 94.8f)
            curveToRelative(0f, 68.5f, -0.6f, 123.6f, -0.6f, 140.5f)
            curveToRelative(0f, 13.6f, 9.2f, 29.6f, 35.2f, 24.6f)
            curveToRelative(203.3f, -67.8f, 349.9f, -259.6f, 349.9f, -485.8f)
            curveToRelative(0f, -282.8f, -229.2f, -512f, -512f, -512f)
            close()
        }
    }
}

val FlexiIcons.Home by lazy {
    ImageVector(
        name = "home",
        defaultWidth = 50.dp,
        defaultHeight = 50.dp,
        viewportWidth = 1024f,
        viewportHeight = 1024f
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
            moveTo(900.7f, 430.9f)
            lineToRelative(-330.4f, -308f)
            curveToRelative(-16.7f, -19f, -40.8f, -30f, -66.4f, -30f)
            curveToRelative(-25.2f, 0f, -49.3f, 10.8f, -65.3f, 28.9f)
            lineTo(120.5f, 430.8f)
            curveToRelative(-15.9f, 16.3f, -20.5f, 39.8f, -11.7f, 60.8f)
            curveToRelative(8.8f, 21f, 29.2f, 34.1f, 51.9f, 34.1f)
            lineToRelative(28.7f, 0f)
            lineToRelative(0f, 314.1f)
            curveToRelative(0f, 48.7f, 39f, 88.3f, 87.7f, 88.3f)
            lineToRelative(136.6f, 0f)
            curveToRelative(17.7f, 0f, 33.5f, -14.3f, 33.5f, -32f)
            lineTo(447.1f, 687f)
            curveToRelative(0f, -8.9f, 5.8f, -16.8f, 14.7f, -16.8f)
            lineToRelative(96.4f, 0f)
            curveToRelative(8.9f, 0f, 15.7f, 8f, 15.7f, 16.8f)
            lineToRelative(0f, 209f)
            curveToRelative(0f, 17.8f, 14.8f, 32f, 32.5f, 32f)
            lineToRelative(136.6f, 0f)
            curveToRelative(48.7f, 0f, 88.6f, -39.5f, 88.6f, -88.3f)
            lineToRelative(0f, -314.1f)
            lineToRelative(28.5f, 0f)
            curveToRelative(22.6f, 0f, 43f, -13.1f, 51.8f, -34f)
            curveTo(921f, 470.8f, 916.5f, 447.2f, 900.7f, 430.9f)
            lineTo(900.7f, 430.9f)
            lineTo(900.7f, 430.9f)
            moveTo(900.7f, 430.9f)
            lineTo(900.7f, 430.9f)
            close()
        }
    }
}

val FlexiIcons.Component by lazy {
    ImageVector(
        name = "component",
        defaultWidth = 48.dp,
        defaultHeight = 48.dp,
        viewportWidth = 48f,
        viewportHeight = 48f
    ) {
        path(
            fill = SolidColor(Color.White),
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(18f, 6f)
            horizontalLineTo(8f)
            curveTo(6.895f, 6f, 6f, 6.895f, 6f, 8f)
            verticalLineTo(18f)
            curveTo(6f, 19.105f, 6.895f, 20f, 8f, 20f)
            horizontalLineTo(18f)
            curveTo(19.105f, 20f, 20f, 19.105f, 20f, 18f)
            verticalLineTo(8f)
            curveTo(20f, 6.895f, 19.105f, 6f, 18f, 6f)
            close()
        }
        path(
            fill = SolidColor(Color.White),
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(18f, 28f)
            horizontalLineTo(8f)
            curveTo(6.895f, 28f, 6f, 28.895f, 6f, 30f)
            verticalLineTo(40f)
            curveTo(6f, 41.105f, 6.895f, 42f, 8f, 42f)
            horizontalLineTo(18f)
            curveTo(19.105f, 42f, 20f, 41.105f, 20f, 40f)
            verticalLineTo(30f)
            curveTo(20f, 28.895f, 19.105f, 28f, 18f, 28f)
            close()
        }
        path(
            fill = SolidColor(Color.White),
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(35f, 20f)
            curveTo(38.866f, 20f, 42f, 16.866f, 42f, 13f)
            curveTo(42f, 9.134f, 38.866f, 6f, 35f, 6f)
            curveTo(31.134f, 6f, 28f, 9.134f, 28f, 13f)
            curveTo(28f, 16.866f, 31.134f, 20f, 35f, 20f)
            close()
        }
        path(
            fill = SolidColor(Color.White),
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(40f, 28f)
            horizontalLineTo(30f)
            curveTo(28.895f, 28f, 28f, 28.895f, 28f, 30f)
            verticalLineTo(40f)
            curveTo(28f, 41.105f, 28.895f, 42f, 30f, 42f)
            horizontalLineTo(40f)
            curveTo(41.105f, 42f, 42f, 41.105f, 42f, 40f)
            verticalLineTo(30f)
            curveTo(42f, 28.895f, 41.105f, 28f, 40f, 28f)
            close()
        }
    }
}

val FlexiIcons.Locale by lazy {
    ImageVector(
        name = "locale",
        defaultWidth = 48.dp,
        defaultHeight = 48.dp,
        viewportWidth = 48f,
        viewportHeight = 48f
    ) {
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.EvenOdd
        ) {
            moveTo(24f, 44f)
            curveTo(35.0457f, 44f, 44f, 35.0457f, 44f, 24f)
            curveTo(44f, 12.9543f, 35.0457f, 4f, 24f, 4f)
            curveTo(12.9543f, 4f, 4f, 12.9543f, 4f, 24f)
            curveTo(4f, 35.0457f, 12.9543f, 44f, 24f, 44f)
            close()
        }
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(4f, 24f)
            horizontalLineTo(44f)
        }
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.EvenOdd
        ) {
            moveTo(24f, 44f)
            curveTo(28.4183f, 44f, 32f, 35.0457f, 32f, 24f)
            curveTo(32f, 12.9543f, 28.4183f, 4f, 24f, 4f)
            curveTo(19.5817f, 4f, 16f, 12.9543f, 16f, 24f)
            curveTo(16f, 35.0457f, 19.5817f, 44f, 24f, 44f)
            close()
        }
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(9.85791f, 10.1421f)
            curveTo(13.4772f, 13.7614f, 18.4772f, 16f, 24f, 16f)
            curveTo(29.5229f, 16f, 34.5229f, 13.7614f, 38.1422f, 10.1421f)
        }
        path(
            fill = null,
            fillAlpha = 1.0f,
            stroke = SolidColor(Color.White),
            strokeAlpha = 1.0f,
            strokeLineWidth = 4f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            strokeLineMiter = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(38.1422f, 37.8579f)
            curveTo(34.5229f, 34.2386f, 29.5229f, 32f, 24f, 32f)
            curveTo(18.4772f, 32f, 13.4772f, 34.2386f, 9.8579f, 37.8579f)
        }
    }
}

val FlexiIcons.Style by lazy {
    ImageVector(
        name = "style",
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
            moveTo(2.53f, 19.65f)
            lineToRelative(1.34f, 0.56f)
            verticalLineToRelative(-9.03f)
            lineToRelative(-2.43f, 5.86f)
            curveToRelative(-0.41f, 1.02f, 0.08f, 2.19f, 1.09f, 2.61f)
            close()
            moveTo(22.03f, 15.95f)
            lineTo(17.07f, 3.98f)
            curveToRelative(-0.31f, -0.75f, -1.04f, -1.21f, -1.81f, -1.23f)
            curveToRelative(-0.26f, 0f, -0.53f, 0.04f, -0.79f, 0.15f)
            lineTo(7.1f, 5.95f)
            curveToRelative(-0.75f, 0.31f, -1.21f, 1.03f, -1.23f, 1.8f)
            curveToRelative(-0.01f, 0.27f, 0.04f, 0.54f, 0.15f, 0.8f)
            lineToRelative(4.96f, 11.97f)
            curveToRelative(0.31f, 0.76f, 1.05f, 1.22f, 1.83f, 1.23f)
            curveToRelative(0.26f, 0f, 0.52f, -0.05f, 0.77f, -0.15f)
            lineToRelative(7.36f, -3.05f)
            curveToRelative(1.02f, -0.42f, 1.51f, -1.59f, 1.09f, -2.6f)
            close()
            moveTo(7.88f, 8.75f)
            curveToRelative(-0.55f, 0f, -1f, -0.45f, -1f, -1f)
            reflectiveCurveToRelative(0.45f, -1f, 1f, -1f)
            reflectiveCurveToRelative(1f, 0.45f, 1f, 1f)
            reflectiveCurveToRelative(-0.45f, 1f, -1f, 1f)
            close()
            moveTo(5.88f, 19.75f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(1.45f)
            lineToRelative(-3.45f, -8.34f)
            verticalLineToRelative(6.34f)
            close()
        }
    }
}

val FlexiIcons.ListAdd by lazy {
    ImageVector(
        name = "list_add",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
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
            moveTo(13f, 10f)
            lineTo(3f, 10f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(10f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
            close()
            moveTo(13f, 6f)
            lineTo(3f, 6f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(10f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
            close()
            moveTo(18f, 14f)
            verticalLineToRelative(-3f)
            curveToRelative(0f, -0.55f, -0.45f, -1f, -1f, -1f)
            reflectiveCurveToRelative(-1f, 0.45f, -1f, 1f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(-3f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(3f)
            verticalLineToRelative(3f)
            curveToRelative(0f, 0.55f, 0.45f, 1f, 1f, 1f)
            reflectiveCurveToRelative(1f, -0.45f, 1f, -1f)
            verticalLineToRelative(-3f)
            horizontalLineToRelative(3f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
            horizontalLineToRelative(-3f)
            close()
            moveTo(3f, 16f)
            horizontalLineToRelative(6f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
            lineTo(3f, 14f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
            close()
        }
    }
}

val FlexiIcons.Delete by lazy {
    ImageVector(
        name = "delete",
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
            moveTo(6f, 19f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(8f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineTo(9f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            horizontalLineTo(8f)
            curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
            verticalLineToRelative(10f)
            close()
            moveTo(18f, 4f)
            horizontalLineToRelative(-2.5f)
            lineToRelative(-0.71f, -0.71f)
            curveToRelative(-0.18f, -0.18f, -0.44f, -0.29f, -0.7f, -0.29f)
            horizontalLineTo(9.91f)
            curveToRelative(-0.26f, 0f, -0.52f, 0.11f, -0.7f, 0.29f)
            lineTo(8.5f, 4f)
            horizontalLineTo(6f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(12f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
            close()
        }
    }
}

val FlexiIcons.DeleteForever by lazy {
    ImageVector(
        name = "delete_forever",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
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
            moveTo(6f, 19f)
            curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
            horizontalLineToRelative(8f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            lineTo(18f, 7f)
            lineTo(6f, 7f)
            verticalLineToRelative(12f)
            close()
            moveTo(8.46f, 11.88f)
            lineToRelative(1.41f, -1.41f)
            lineTo(12f, 12.59f)
            lineToRelative(2.12f, -2.12f)
            lineToRelative(1.41f, 1.41f)
            lineTo(13.41f, 14f)
            lineToRelative(2.12f, 2.12f)
            lineToRelative(-1.41f, 1.41f)
            lineTo(12f, 15.41f)
            lineToRelative(-2.12f, 2.12f)
            lineToRelative(-1.41f, -1.41f)
            lineTo(10.59f, 14f)
            lineToRelative(-2.13f, -2.12f)
            close()
            moveTo(15.5f, 4f)
            lineToRelative(-1f, -1f)
            horizontalLineToRelative(-5f)
            lineToRelative(-1f, 1f)
            lineTo(5f, 4f)
            verticalLineToRelative(2f)
            horizontalLineToRelative(14f)
            lineTo(19f, 4f)
            close()
        }
    }
}