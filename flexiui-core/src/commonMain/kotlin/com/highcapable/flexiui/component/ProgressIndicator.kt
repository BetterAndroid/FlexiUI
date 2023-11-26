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
 * This file is created by fankes on 2023/11/8.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.extension.orElse
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max

@Stable
internal interface IProgressIndicatorStyle {
    val strokeWidth: Dp
    val strokeCap: StrokeCap
}

@Immutable
data class CircularIndicatorStyle(
    override val strokeWidth: Dp,
    override val strokeCap: StrokeCap,
    val radius: Dp,
    val rotationDuration: Int,
    val rotationsPerCycle: Int,
    val startAngleOffset: Float,
    val baseRotationAngle: Float,
    val jumpRotationAngle: Float,
    val easing: Easing
) : IProgressIndicatorStyle

@Immutable
data class LinearIndicatorStyle(
    override val strokeWidth: Dp,
    override val strokeCap: StrokeCap,
    val width: Dp,
    val animationDuration: Int,
    val firstLineHeadDuration: Int,
    val firstLineTailDuration: Int,
    val secondLineHeadDuration: Int,
    val secondLineTailDuration: Int,
    val firstLineHeadDelay: Int,
    val firstLineTailDelay: Int,
    val secondLineHeadDelay: Int,
    val secondLineTailDelay: Int,
    val firstLineHeadEasing: Easing,
    val firstLineTailEasing: Easing,
    val secondLineHeadEasing: Easing,
    val secondLineTailEasing: Easing
) : IProgressIndicatorStyle

@Immutable
data class ProgressIndicatorColors(
    val foregroundColor: Color,
    val backgroundColor: Color
)

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = -1f,
    min: Float = 0f,
    max: Float = 100f,
    indeterminate: Boolean = progress < min,
    colors: ProgressIndicatorColors = ProgressIndicator.circularColors,
    style: CircularIndicatorStyle = ProgressIndicator.circularStyle
) {
    val diameter = style.radius * 2
    val stroke = with(LocalDensity.current) { Stroke(width = style.strokeWidth.toPx(), cap = style.strokeCap) }

    @Composable
    fun Determinate() {
        val coercedProgress = progress.coerceIn(min, max)
        val normalizedProgress = (coercedProgress - min) / (max - min)
        Canvas(modifier.progressSemantics(normalizedProgress).size(diameter)) {
            val startAngle = 270f
            val sweep = normalizedProgress * 360f
            drawCircularIndicatorBackground(colors.backgroundColor, stroke)
            drawCircularIndicator(startAngle, sweep, colors.foregroundColor, stroke)
        }
    }

    @Composable
    fun Indeterminate() {
        val transition = rememberInfiniteTransition()
        val currentRotation by transition.animateValue(
            initialValue = 0,
            style.rotationsPerCycle,
            Int.VectorConverter,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = style.rotationDuration * style.rotationsPerCycle,
                    easing = LinearEasing
                )
            )
        )
        val baseRotation by transition.animateFloat(
            initialValue = 0f,
            style.baseRotationAngle,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = style.rotationDuration,
                    easing = LinearEasing
                )
            )
        )
        val headAndTailAnimationDuration = caleHeadAndTailAnimationDuration(style.rotationDuration)
        val endAngle by transition.animateFloat(
            initialValue = 0f,
            style.jumpRotationAngle,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = headAndTailAnimationDuration * 2
                    0f at 0 with style.easing
                    style.jumpRotationAngle at headAndTailAnimationDuration
                }
            )
        )
        val startAngle by transition.animateFloat(
            initialValue = 0f,
            style.jumpRotationAngle,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = headAndTailAnimationDuration * 2
                    0f at headAndTailAnimationDuration with style.easing
                    style.jumpRotationAngle at durationMillis
                }
            )
        )
        Canvas(modifier.progressSemantics().size(diameter)) {
            drawCircularIndicatorBackground(colors.backgroundColor, stroke)
            val rotationAngleOffset = caleRotationAngleOffset(style.baseRotationAngle, style.jumpRotationAngle)
            val currentRotationAngleOffset = (currentRotation * rotationAngleOffset) % 360f
            val sweep = abs(endAngle - startAngle)
            val offset = style.startAngleOffset + currentRotationAngleOffset + baseRotation
            drawIndeterminateCircularIndicator(startAngle + offset, style.strokeWidth, diameter, sweep, colors.foregroundColor, stroke)
        }
    }
    if (indeterminate) Indeterminate() else Determinate()
}

@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = -1f,
    min: Float = 0f,
    max: Float = 100f,
    indeterminate: Boolean = progress < min,
    colors: ProgressIndicatorColors = ProgressIndicator.linearColors,
    style: LinearIndicatorStyle = ProgressIndicator.linearStyle
) {
    @Composable
    fun Determinate() {
        val coercedProgress = progress.coerceIn(min, max)
        val normalizedProgress = (coercedProgress - min) / (max - min)
        Canvas(modifier.progressSemantics(normalizedProgress).size(style.width, style.strokeWidth)) {
            val strokeWidth = size.height
            drawLinearIndicatorBackground(colors.backgroundColor, strokeWidth, style.strokeCap)
            drawLinearIndicator(startFraction = 0f, normalizedProgress, colors.foregroundColor, strokeWidth, style.strokeCap)
        }
    }

    @Composable
    fun Indeterminate() {
        val infiniteTransition = rememberInfiniteTransition()
        val firstLineHead by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = style.animationDuration
                    0f at style.firstLineHeadDelay with style.firstLineHeadEasing
                    1f at style.firstLineHeadDuration + style.firstLineHeadDelay
                }
            )
        )
        val firstLineTail by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = style.animationDuration
                    0f at style.firstLineTailDelay with style.firstLineTailEasing
                    1f at style.firstLineTailDuration + style.firstLineTailDelay
                }
            )
        )
        val secondLineHead by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = style.animationDuration
                    0f at style.secondLineHeadDelay with style.secondLineHeadEasing
                    1f at style.secondLineHeadDuration + style.secondLineHeadDelay
                }
            )
        )
        val secondLineTail by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = style.animationDuration
                    0f at style.secondLineTailDelay with style.secondLineTailEasing
                    1f at style.secondLineTailDuration + style.secondLineTailDelay
                }
            )
        )
        Canvas(modifier.progressSemantics().size(style.width, style.strokeWidth)) {
            val strokeWidth = size.height
            drawLinearIndicatorBackground(colors.backgroundColor, strokeWidth, style.strokeCap)
            if (firstLineHead - firstLineTail > 0)
                drawLinearIndicator(firstLineHead, firstLineTail, colors.foregroundColor, strokeWidth, style.strokeCap)
            if (secondLineHead - secondLineTail > 0)
                drawLinearIndicator(secondLineHead, secondLineTail, colors.foregroundColor, strokeWidth, style.strokeCap)
        }
    }
    if (indeterminate) Indeterminate() else Determinate()
}

private fun DrawScope.drawIndeterminateCircularIndicator(
    startAngle: Float,
    strokeWidth: Dp,
    diameter: Dp,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val strokeCapOffset = if (stroke.cap == StrokeCap.Butt) 0f
    else (180.0 / PI).toFloat() * (strokeWidth / (diameter / 2)) / 2f
    val adjustedStartAngle = startAngle + strokeCapOffset
    val adjustedSweep = max(sweep, 0.1f)
    drawCircularIndicator(adjustedStartAngle, adjustedSweep, color, stroke)
}

private fun DrawScope.drawCircularIndicatorBackground(
    color: Color,
    stroke: Stroke
) = drawCircularIndicator(startAngle = 0f, sweep = 360f, color, stroke)

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

private fun DrawScope.drawLinearIndicatorBackground(
    color: Color,
    strokeWidth: Float,
    strokeCap: StrokeCap,
) = drawLinearIndicator(startFraction = 0f, endFraction = 1f, color, strokeWidth, strokeCap)

private fun DrawScope.drawLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float,
    strokeCap: StrokeCap,
) {
    val width = size.width
    val height = size.height
    val yOffset = height / 2
    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width
    if (strokeCap == StrokeCap.Butt || height > width) {
        drawLine(color, Offset(barStart, yOffset), Offset(barEnd, yOffset), strokeWidth)
    } else {
        val strokeCapOffset = strokeWidth / 2
        val coerceRange = strokeCapOffset..(width - strokeCapOffset)
        val adjustedBarStart = barStart.coerceIn(coerceRange)
        val adjustedBarEnd = barEnd.coerceIn(coerceRange)
        if (abs(endFraction - startFraction) > 0)
            drawLine(color, Offset(adjustedBarStart, yOffset), Offset(adjustedBarEnd, yOffset), strokeWidth, strokeCap)
    }
}

object ProgressIndicator {
    val circularColors: ProgressIndicatorColors
        @Composable
        @ReadOnlyComposable
        get() = LocalProgressIndicatorColors.current.copy(
            foregroundColor = LocalProgressIndicatorColors.current.foregroundColor.orElse()
                ?: defaultCircularIndicatorColors().foregroundColor,
            backgroundColor = LocalProgressIndicatorColors.current.backgroundColor.orElse()
                ?: defaultCircularIndicatorColors().backgroundColor
        )
    val linearColors: ProgressIndicatorColors
        @Composable
        @ReadOnlyComposable
        get() = LocalProgressIndicatorColors.current.copy(
            foregroundColor = LocalProgressIndicatorColors.current.foregroundColor.orElse()
                ?: defaultLinearIndicatorColors().foregroundColor,
            backgroundColor = LocalProgressIndicatorColors.current.backgroundColor.orElse()
                ?: defaultLinearIndicatorColors().backgroundColor
        )
    val circularStyle: CircularIndicatorStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultCircularIndicatorStyle()
    val linearStyle: LinearIndicatorStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultLinearIndicatorStyle()
}

internal val LocalProgressIndicatorColors = compositionLocalOf { DefaultProgressIndicatorColors }

internal val DefaultProgressIndicatorColors = ProgressIndicatorColors(Color.Unspecified, Color.Unspecified)

@Composable
@ReadOnlyComposable
private fun defaultCircularIndicatorColors() = ProgressIndicatorColors(
    foregroundColor = LocalColors.current.themePrimary,
    backgroundColor = Color.Transparent
)

@Composable
@ReadOnlyComposable
private fun defaultLinearIndicatorColors() = ProgressIndicatorColors(
    foregroundColor = LocalColors.current.themePrimary,
    backgroundColor = LocalColors.current.themeTertiary
)

@Composable
@ReadOnlyComposable
private fun defaultCircularIndicatorStyle() = CircularIndicatorStyle(
    strokeWidth = DefaultIndicatorStrokeWidth,
    strokeCap = StrokeCap.Round,
    radius = DefaultCircularIndicatorRadius,
    rotationDuration = DefaultRotationDuration,
    rotationsPerCycle = DefaultRotationsPerCycle,
    startAngleOffset = DefaultStartAngleOffset,
    baseRotationAngle = DefaultBaseRotationAngle,
    jumpRotationAngle = DefaultJumpRotationAngle,
    easing = DefaultCircularEasing
)

@Composable
@ReadOnlyComposable
private fun defaultLinearIndicatorStyle() = LinearIndicatorStyle(
    strokeWidth = DefaultIndicatorStrokeWidth,
    strokeCap = StrokeCap.Round,
    width = DefaultLinearIndicatorWidth,
    animationDuration = DefaultLinearAnimationDuration,
    firstLineHeadDuration = DefaultFirstLineHeadDuration,
    firstLineTailDuration = DefaultFirstLineTailDuration,
    secondLineHeadDuration = DefaultSecondLineHeadDuration,
    secondLineTailDuration = DefaultSecondLineTailDuration,
    firstLineHeadDelay = DefaultFirstLineHeadDelay,
    firstLineTailDelay = DefaultFirstLineTailDelay,
    secondLineHeadDelay = DefaultSecondLineHeadDelay,
    secondLineTailDelay = DefaultSecondLineTailDelay,
    firstLineHeadEasing = DefaultFirstLineHeadEasing,
    firstLineTailEasing = DefaultFirstLineTailEasing,
    secondLineHeadEasing = DefaultSecondLineHeadEasing,
    secondLineTailEasing = DefaultSecondLineTailEasing
)

private fun caleRotationAngleOffset(baseRotationAngle: Float, jumpRotationAngle: Float) = (baseRotationAngle + jumpRotationAngle) % 360f

private fun caleHeadAndTailAnimationDuration(rotationDuration: Int) = (rotationDuration * 0.5).toInt()

private val DefaultIndicatorStrokeWidth = 4.dp
private val DefaultLinearIndicatorWidth = 240.dp
private val DefaultCircularIndicatorRadius = 20.dp
private const val DefaultLinearAnimationDuration = 1800

private const val DefaultFirstLineHeadDuration = 750
private const val DefaultFirstLineTailDuration = 850
private const val DefaultSecondLineHeadDuration = 567
private const val DefaultSecondLineTailDuration = 533

private const val DefaultFirstLineHeadDelay = 0
private const val DefaultFirstLineTailDelay = 333
private const val DefaultSecondLineHeadDelay = 1000
private const val DefaultSecondLineTailDelay = 1267

private const val DefaultRotationsPerCycle = 5
private const val DefaultRotationDuration = 1332
private const val DefaultStartAngleOffset = -90f
private const val DefaultBaseRotationAngle = 286f
private const val DefaultJumpRotationAngle = 290f

private val DefaultFirstLineHeadEasing = CubicBezierEasing(0.2f, 0f, 0.8f, 1f)
private val DefaultFirstLineTailEasing = CubicBezierEasing(0.4f, 0f, 1f, 1f)
private val DefaultSecondLineHeadEasing = CubicBezierEasing(0f, 0f, 0.65f, 1f)
private val DefaultSecondLineTailEasing = CubicBezierEasing(0.1f, 0f, 0.45f, 1f)
private val DefaultCircularEasing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)