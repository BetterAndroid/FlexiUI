/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019-2024 HighCapable
 * https://github.com/BetterAndroid/FlexiUI
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance using the License.
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
@file:Suppress("unused", "ObjectPropertyName", "ktlint:standard:backing-property-naming")

package com.highcapable.flexiui.component

import androidx.compose.animation.core.CubicBezierEasing
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
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.toColor
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max

/**
 * Style interface for progress indicator.
 * @see CircularIndicatorStyle
 * @see LinearIndicatorStyle
 */
@Stable
interface ProgressIndicatorStyle {
    val strokeWidth: Dp
    val strokeCap: StrokeCap
}

/**
 * Animation interface for progress indicator.
 * @see CircularIndicatorAnimation
 * @see LinearIndicatorAnimation
 */
@Stable
interface ProgressIndicatorAnimation {
    val duration: Int
}

/**
 * Colors defines for progress indicator.
 * @see CircularIndicatorDefaults.colors
 * @see LinearIndicatorDefaults.colors
 */
@Immutable
data class ProgressIndicatorColors(
    val foregroundColor: Color,
    val backgroundColor: Color
)

/**
 * Style defines for circular progress indicator.
 * @see CircularIndicatorDefaults.style
 */
@Immutable
data class CircularIndicatorStyle(
    override val strokeWidth: Dp,
    override val strokeCap: StrokeCap,
    val radius: Dp
) : ProgressIndicatorStyle

/**
 * Style defines for linear progress indicator.
 * @see LinearIndicatorDefaults.style
 */
@Immutable
data class LinearIndicatorStyle(
    override val strokeWidth: Dp,
    override val strokeCap: StrokeCap,
    val width: Dp
) : ProgressIndicatorStyle

/**
 * Animation defines for circular progress indicator.
 * @param duration the duration of animation.
 * @param rotationsPerCycle the rotations per cycle of animation.
 * @param startAngleOffset the start angle offset of animation.
 * @param baseRotationAngle the base rotation angle of animation.
 * @param jumpRotationAngle the jump rotation angle of animation.
 */
@Immutable
data class CircularIndicatorAnimation(
    override val duration: Int,
    val rotationsPerCycle: Int,
    val startAngleOffset: Float,
    val baseRotationAngle: Float,
    val jumpRotationAngle: Float
) : ProgressIndicatorAnimation

/**
 * Animation defines for linear progress indicator.
 * @param duration the duration of animation.
 * @param firstLineHeadDuration the first line head duration of animation.
 * @param firstLineTailDuration the first line tail duration of animation.
 * @param secondLineHeadDuration the second line head duration of animation.
 * @param secondLineTailDuration the second line tail duration of animation.
 * @param firstLineHeadDelay the first line head delay of animation.
 * @param firstLineTailDelay the first line tail delay of animation.
 * @param secondLineHeadDelay the second line head delay of animation.
 * @param secondLineTailDelay the second line tail delay of animation.
 */
@Immutable
data class LinearIndicatorAnimation(
    override val duration: Int,
    val firstLineHeadDuration: Int,
    val firstLineTailDuration: Int,
    val secondLineHeadDuration: Int,
    val secondLineTailDuration: Int,
    val firstLineHeadDelay: Int,
    val firstLineTailDelay: Int,
    val secondLineHeadDelay: Int,
    val secondLineTailDelay: Int
) : ProgressIndicatorAnimation

/**
 * Flexi UI circular progress indicator.
 * @see LinearProgressIndicator
 * @param modifier the [Modifier] to be applied to this indicator.
 * @param progress the progress of indicator.
 * @param min the min of indicator, default is 0f.
 * @param max the max of indicator, default is 1f.
 * @param indeterminate the indeterminate of indicator, default is false.
 * @param animation the animation of indicator.
 * @param colors the colors of indicator.
 * @param style the style of indicator.
 */
@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = -1f,
    min: Float = 0f,
    max: Float = 1f,
    indeterminate: Boolean = progress < min,
    animation: CircularIndicatorAnimation = DefaultCircularIndicatorAnimation,
    colors: ProgressIndicatorColors? = null,
    style: CircularIndicatorStyle? = null
) {
    val currentColors = colors ?: LocalProgressIndicatorColors.current ?: CircularIndicatorDefaults.colors()
    val currentStyle = style ?: LocalProgressIndicatorStyle.current as? CircularIndicatorStyle? ?: CircularIndicatorDefaults.style()
    val diameter = currentStyle.radius * 2
    val stroke = with(LocalDensity.current) { Stroke(width = currentStyle.strokeWidth.toPx(), cap = currentStyle.strokeCap) }

    /** Build determinate progress indicator. */
    @Composable
    fun Determinate() {
        val coercedProgress = progress.coerceIn(min, max)
        val normalizedProgress = (coercedProgress - min) / (max - min)
        Canvas(modifier.progressSemantics(normalizedProgress).size(diameter)) {
            val startAngle = 270f
            val sweep = normalizedProgress * 360f
            drawCircularIndicatorBackground(currentColors.backgroundColor, stroke)
            drawCircularIndicator(startAngle, sweep, currentColors.foregroundColor, stroke)
        }
    }

    /** Build indeterminate progress indicator. */
    @Composable
    fun Indeterminate() {
        val transition = rememberInfiniteTransition()
        val currentRotation by transition.animateValue(
            initialValue = 0,
            animation.rotationsPerCycle,
            Int.VectorConverter,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = animation.duration * animation.rotationsPerCycle,
                    easing = LinearEasing
                )
            )
        )
        val baseRotation by transition.animateFloat(
            initialValue = 0f,
            animation.baseRotationAngle,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = animation.duration,
                    easing = LinearEasing
                )
            )
        )
        val headAndTailAnimationDuration = caleHeadAndTailAnimationDuration(animation.duration)
        val endAngle by transition.animateFloat(
            initialValue = 0f,
            animation.jumpRotationAngle,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = headAndTailAnimationDuration * 2
                    0f at 0 using CircularEasing
                    animation.jumpRotationAngle at headAndTailAnimationDuration
                }
            )
        )
        val startAngle by transition.animateFloat(
            initialValue = 0f,
            animation.jumpRotationAngle,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = headAndTailAnimationDuration * 2
                    0f at headAndTailAnimationDuration using CircularEasing
                    animation.jumpRotationAngle at durationMillis
                }
            )
        )
        Canvas(modifier.progressSemantics().size(diameter)) {
            drawCircularIndicatorBackground(currentColors.backgroundColor, stroke)
            val rotationAngleOffset = caleRotationAngleOffset(animation.baseRotationAngle, animation.jumpRotationAngle)
            val currentRotationAngleOffset = (currentRotation * rotationAngleOffset) % 360f
            val sweep = abs(endAngle - startAngle)
            val offset = animation.startAngleOffset + currentRotationAngleOffset + baseRotation
            drawIndeterminateCircularIndicator(startAngle + offset, currentStyle.strokeWidth, diameter, sweep, currentColors.foregroundColor, stroke)
        }
    }
    if (indeterminate) Indeterminate() else Determinate()
}

/**
 * Flexi UI linear progress indicator.
 * @see CircularProgressIndicator
 * @param modifier the [Modifier] to be applied to this indicator.
 * @param progress the progress of indicator.
 * @param min the min of indicator, default is 0f.
 * @param max the max of indicator, default is 1f.
 * @param indeterminate the indeterminate of indicator, default is false.
 * @param animation the animation of indicator.
 * @param colors the colors of indicator.
 * @param style the style of indicator.
 */
@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = -1f,
    min: Float = 0f,
    max: Float = 1f,
    indeterminate: Boolean = progress < min,
    animation: LinearIndicatorAnimation = DefaultLinearIndicatorAnimation,
    colors: ProgressIndicatorColors? = null,
    style: LinearIndicatorStyle? = null
) {
    val currentColors = colors ?: LocalProgressIndicatorColors.current ?: LinearIndicatorDefaults.colors()
    val currentStyle = style ?: LocalProgressIndicatorStyle.current as? LinearIndicatorStyle? ?: LinearIndicatorDefaults.style()

    /** Build determinate progress indicator. */
    @Composable
    fun Determinate() {
        val coercedProgress = progress.coerceIn(min, max)
        val normalizedProgress = (coercedProgress - min) / (max - min)
        Canvas(modifier.progressSemantics(normalizedProgress).size(currentStyle.width, currentStyle.strokeWidth)) {
            val strokeWidth = size.height
            drawLinearIndicatorBackground(currentColors.backgroundColor, strokeWidth, currentStyle.strokeCap)
            drawLinearIndicator(startFraction = 0f, normalizedProgress, currentColors.foregroundColor, strokeWidth, currentStyle.strokeCap)
        }
    }

    /** Build indeterminate progress indicator. */
    @Composable
    fun Indeterminate() {
        val infiniteTransition = rememberInfiniteTransition()
        val firstLineHead by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animation.duration
                    0f at animation.firstLineHeadDelay using FirstLineHeadEasing
                    1f at animation.firstLineHeadDuration + animation.firstLineHeadDelay
                }
            )
        )
        val firstLineTail by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animation.duration
                    0f at animation.firstLineTailDelay using FirstLineTailEasing
                    1f at animation.firstLineTailDuration + animation.firstLineTailDelay
                }
            )
        )
        val secondLineHead by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animation.duration
                    0f at animation.secondLineHeadDelay using SecondLineHeadEasing
                    1f at animation.secondLineHeadDuration + animation.secondLineHeadDelay
                }
            )
        )
        val secondLineTail by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animation.duration
                    0f at animation.secondLineTailDelay using SecondLineTailEasing
                    1f at animation.secondLineTailDuration + animation.secondLineTailDelay
                }
            )
        )
        Canvas(modifier.progressSemantics().size(currentStyle.width, currentStyle.strokeWidth)) {
            val strokeWidth = size.height
            drawLinearIndicatorBackground(currentColors.backgroundColor, strokeWidth, currentStyle.strokeCap)
            if (firstLineHead - firstLineTail > 0)
                drawLinearIndicator(firstLineHead, firstLineTail, currentColors.foregroundColor, strokeWidth, currentStyle.strokeCap)
            if (secondLineHead - secondLineTail > 0)
                drawLinearIndicator(secondLineHead, secondLineTail, currentColors.foregroundColor, strokeWidth, currentStyle.strokeCap)
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

/**
 * Defaults of circular progress indicator.
 */
object CircularIndicatorDefaults {

    /**
     * Creates a [ProgressIndicatorColors] with the default values.
     * @param foregroundColor the foreground color of indicator.
     * @param backgroundColor the background color of indicator.
     * @return [ProgressIndicatorColors]
     */
    @Composable
    fun colors(
        foregroundColor: Color = CircularIndicatorProperties.ForegroundColor.toColor(),
        backgroundColor: Color = CircularIndicatorProperties.BackgroundColor
    ) = ProgressIndicatorColors(
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor
    )

    /**
     * Creates a [CircularIndicatorStyle] with the default values.
     * @param strokeWidth the stroke width of indicator.
     * @param strokeCap the stroke cap of indicator.
     * @param radius the radius of indicator.
     * @return [CircularIndicatorStyle]
     */
    @Composable
    fun style(
        strokeWidth: Dp = CircularIndicatorProperties.StrokeWidth,
        strokeCap: StrokeCap = CircularIndicatorProperties._StrokeCap,
        radius: Dp = CircularIndicatorProperties.Radius
    ) = CircularIndicatorStyle(
        strokeWidth = strokeWidth,
        strokeCap = strokeCap,
        radius = radius
    )
}

/**
 * Defaults of linear progress indicator.
 */
object LinearIndicatorDefaults {

    /**
     * Creates a [ProgressIndicatorColors] with the default values.
     * @param foregroundColor the foreground color of indicator.
     * @param backgroundColor the background color of indicator.
     * @return [ProgressIndicatorColors]
     */
    @Composable
    fun colors(
        foregroundColor: Color = LinearIndicatorProperties.ForegroundColor.toColor(),
        backgroundColor: Color = LinearIndicatorProperties.BackgroundColor.toColor()
    ) = ProgressIndicatorColors(
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor
    )

    /**
     * Creates a [LinearIndicatorStyle] with the default values.
     * @param strokeWidth the stroke width of indicator.
     * @param strokeCap the stroke cap of indicator.
     * @param width the width of indicator.
     * @return [LinearIndicatorStyle]
     */
    @Composable
    fun style(
        strokeWidth: Dp = LinearIndicatorProperties.StrokeWidth,
        strokeCap: StrokeCap = LinearIndicatorProperties._StrokeCap,
        width: Dp = LinearIndicatorProperties.Width
    ) = LinearIndicatorStyle(
        strokeWidth = strokeWidth,
        strokeCap = strokeCap,
        width = width
    )
}

@Stable
internal object CircularIndicatorProperties {
    val ForegroundColor = ColorsDescriptor.ThemePrimary
    val BackgroundColor = Color.Transparent
    val StrokeWidth = 4.dp
    val _StrokeCap = StrokeCap.Round
    val Radius = 20.dp
}

@Stable
internal object LinearIndicatorProperties {
    val ForegroundColor = ColorsDescriptor.ThemePrimary
    val BackgroundColor = ColorsDescriptor.ThemeTertiary
    val StrokeWidth = 4.dp
    val _StrokeCap = StrokeCap.Round
    val Width = 240.dp
}

internal val LocalProgressIndicatorColors = compositionLocalOf<ProgressIndicatorColors?> { null }
internal val LocalProgressIndicatorStyle = compositionLocalOf<ProgressIndicatorStyle?> { null }

private fun caleRotationAngleOffset(baseRotationAngle: Float, jumpRotationAngle: Float) = (baseRotationAngle + jumpRotationAngle) % 360f

private fun caleHeadAndTailAnimationDuration(rotationDuration: Int) = (rotationDuration * 0.5).toInt()

private const val DefaultLinearAnimationDuration = 1800
private const val DefaultRotationAnimationDuration = 1332

private const val DefaultFirstLineHeadDuration = 750
private const val DefaultFirstLineTailDuration = 850
private const val DefaultSecondLineHeadDuration = 567
private const val DefaultSecondLineTailDuration = 533

private const val DefaultFirstLineHeadDelay = 0
private const val DefaultFirstLineTailDelay = 333
private const val DefaultSecondLineHeadDelay = 1000
private const val DefaultSecondLineTailDelay = 1267

private const val DefaultRotationsPerCycle = 5
private const val DefaultStartAngleOffset = -90f
private const val DefaultBaseRotationAngle = 286f
private const val DefaultJumpRotationAngle = 290f

private val FirstLineHeadEasing = CubicBezierEasing(0.2f, 0f, 0.8f, 1f)
private val FirstLineTailEasing = CubicBezierEasing(0.4f, 0f, 1f, 1f)
private val SecondLineHeadEasing = CubicBezierEasing(0f, 0f, 0.65f, 1f)
private val SecondLineTailEasing = CubicBezierEasing(0.1f, 0f, 0.45f, 1f)
private val CircularEasing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

private val DefaultLinearIndicatorAnimation = LinearIndicatorAnimation(
    duration = DefaultLinearAnimationDuration,
    firstLineHeadDuration = DefaultFirstLineHeadDuration,
    firstLineTailDuration = DefaultFirstLineTailDuration,
    secondLineHeadDuration = DefaultSecondLineHeadDuration,
    secondLineTailDuration = DefaultSecondLineTailDuration,
    firstLineHeadDelay = DefaultFirstLineHeadDelay,
    firstLineTailDelay = DefaultFirstLineTailDelay,
    secondLineHeadDelay = DefaultSecondLineHeadDelay,
    secondLineTailDelay = DefaultSecondLineTailDelay
)

private val DefaultCircularIndicatorAnimation = CircularIndicatorAnimation(
    duration = DefaultRotationAnimationDuration,
    rotationsPerCycle = DefaultRotationsPerCycle,
    startAngleOffset = DefaultStartAngleOffset,
    baseRotationAngle = DefaultBaseRotationAngle,
    jumpRotationAngle = DefaultJumpRotationAngle
)