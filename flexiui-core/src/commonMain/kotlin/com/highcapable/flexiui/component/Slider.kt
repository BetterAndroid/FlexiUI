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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused", "ConstPropertyName")

package com.highcapable.flexiui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Colors defines for slider.
 * @see SliderDefaults.colors
 */
@Immutable
data class SliderColors(
    val thumbColor: Color,
    val stepColor: Color,
    val thumbBorderColor: Color,
    val stepBorderColor: Color,
    val trackBorderColor: Color,
    val trackInactiveColor: Color,
    val trackActiveColor: Color
)

/**
 * Style defines for slider.
 * @see SliderDefaults.style
 */
@Immutable
data class SliderStyle(
    val thumbRadius: Dp,
    val thumbGain: Float,
    val thumbShadowSize: Dp,
    val thumbShape: Shape,
    val stepShape: Shape,
    val trackShape: Shape,
    val trackWidth: Dp,
    val trackHeight: Dp,
    val thumbBorderWidth: Dp,
    val stepBorderWidth: Dp,
    val trackBorderWidth: Dp
)

/**
 * Flexi UI slider.
 * @param value the value of this slider.
 * @param onValueChange the callback when the value changed.
 * @param modifier the [Modifier] to be applied to this slider.
 * @param colors the colors of this slider, default is [SliderDefaults.colors].
 * @param style the style of this slider, default is [SliderDefaults.style].
 * @param enabled whether this slider is enabled, default is true.
 * @param min the min value of this slider, default is 0f.
 * @param max the max value of this slider, default is 1f.
 * @param steps the steps of this slider, default is 0.
 * @param onValueChangeFinished the callback when the value changed finished.
 * @param interactionSource the interaction source of this slider.
 */
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    colors: SliderColors = SliderDefaults.colors(),
    style: SliderStyle = SliderDefaults.style(),
    enabled: Boolean = true,
    min: Float = 0f,
    max: Float = 1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    BoxWithConstraints {
        SliderLayout(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            colors = colors,
            style = style,
            enabled = enabled,
            min = min,
            max = max,
            steps = steps,
            onValueChangeFinished = onValueChangeFinished,
            interactionSource = interactionSource,
            maxWidth = maxWidth
        )
    }
}

/**
 * Slider layout for internal use.
 */
@Composable
private fun SliderLayout(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier,
    colors: SliderColors,
    style: SliderStyle,
    enabled: Boolean,
    min: Float,
    max: Float,
    steps: Int,
    onValueChangeFinished: (() -> Unit)?,
    interactionSource: MutableInteractionSource,
    maxWidth: Dp
) {
    val thumbDiameter = style.thumbRadius * 2
    val trackWidth = when {
        style.trackWidth > maxWidth && maxWidth > 0.dp -> style.trackWidth
        maxWidth > 0.dp -> maxWidth
        else -> style.trackWidth
    }
    val trackAdoptWidth = trackWidth - thumbDiameter
    val hovered by interactionSource.collectIsHoveredAsState()
    var tapped by remember { mutableStateOf(false) }
    var dragging by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(if (hovered || dragging) style.thumbGain else 1f)
    val maxOffsetX = with(LocalDensity.current) { (trackWidth - thumbDiameter).toPx() }
    var steppedOffsetXs by remember { mutableStateOf(listOf<Float>()) }

    if (steps > 0) {
        val pOffsetX = maxOffsetX / (steps + 1)
        steppedOffsetXs = List(steps + 2) { index -> index * pOffsetX }
    }

    /** Get the stepped offset X. */
    fun Float.withSteps() =
        if (steps > 0)
            steppedOffsetXs.minByOrNull { abs(it - this) } ?: this
        else this

    val offsetXFromValue = (value.coerceIn(min, max) - min) / (max - min) * maxOffsetX
    val steppedOffsetXFromValue = offsetXFromValue.withSteps()
    var absOffsetX by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    // Needs update every time when [value] or [trackWidth] changed.
    offsetX = steppedOffsetXFromValue
    val animatedOffsetX by animateFloatAsState(offsetX)
    val adoptedOffsetX = if (tapped && !dragging) animatedOffsetX else offsetX

    /** Update the value of slider. */
    fun updateValue(offsetX: Float) {
        val newValue = (offsetX / maxOffsetX) * (max - min) + min
        onValueChange(newValue)
    }

    /** Build the track of slider. */
    @Composable
    fun Track(content: @Composable () -> Unit) {
        val cornerSize = (style.trackShape as? CornerBasedShape)?.topStart?.toPx(Size.Zero, LocalDensity.current) ?: 0f
        Box(
            modifier = Modifier.width(trackWidth),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(trackAdoptWidth, style.trackHeight)
                    .background(colors.trackInactiveColor, style.trackShape)
                    .borderOrElse(style.trackBorderWidth, colors.trackBorderColor, style.trackShape)
                    .drawWithContent {
                        drawRoundRect(
                            color = colors.trackActiveColor,
                            size = Size(adoptedOffsetX, size.height),
                            cornerRadius = CornerRadius(cornerSize, cornerSize)
                        )
                    }
            )
            content()
        }
    }

    /** Build the step of slider. */
    @Composable
    fun Step() {
        if (steps > 0) Row(
            modifier = Modifier.size(trackAdoptWidth, style.trackHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until steps + 2) Box(
                modifier = Modifier
                    .size(style.trackHeight)
                    .background(colors.stepColor, style.stepShape)
                    .borderOrElse(style.stepBorderWidth, colors.stepBorderColor, style.stepShape)
            )
        }
    }

    /** Build the thumb of slider. */
    @Composable
    fun Thumb() {
        Box(
            modifier = Modifier
                .size(thumbDiameter)
                .offset { IntOffset(adoptedOffsetX.roundToInt(), 0) }
                .scale(animatedScale)
                .shadow(style.thumbShadowSize, style.thumbShape)
                .background(colors.thumbColor, style.thumbShape)
                .borderOrElse(style.thumbBorderWidth, colors.thumbBorderColor, style.thumbShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val absDelta = delta * animatedScale
                        absOffsetX += absDelta
                        when {
                            absOffsetX in 0f..maxOffsetX -> offsetX += absDelta
                            absOffsetX < 0f -> offsetX = 0f
                            absOffsetX > maxOffsetX -> offsetX = maxOffsetX
                        }
                        updateValue(offsetX.withSteps())
                    },
                    interactionSource = interactionSource,
                    enabled = enabled,
                    onDragStarted = {
                        tapped = false
                        dragging = true
                        absOffsetX = offsetX
                    },
                    onDragStopped = {
                        dragging = false
                        val steppedOffsetX = offsetX.withSteps()
                        if (offsetX != steppedOffsetX) offsetX = steppedOffsetX
                        onValueChangeFinished?.invoke()
                    }
                )
        )
    }
    Box(
        modifier = Modifier
            .componentState(enabled)
            .then(modifier)
            .hoverable(interactionSource, enabled)
            .pointerInput(Unit) {
                if (enabled) detectTapGestures(
                    onTap = { offset ->
                        tapped = true
                        val tapedOffsetX = offset.x - style.thumbRadius.toPx()
                        offsetX = tapedOffsetX.withSteps().coerceIn(0f, maxOffsetX)
                        updateValue(offsetX)
                        onValueChangeFinished?.invoke()
                    }
                )
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Track { Step() }
        Thumb()
    }
}

/**
 * Defaults of slider.
 */
object SliderDefaults {

    /**
     * Creates a [SliderColors] with the default values.
     * @param thumbColor the color of thumb.
     * @param stepColor the color of step.
     * @param thumbBorderColor the border color of thumb.
     * @param stepBorderColor the border color of step.
     * @param trackBorderColor the border color of track.
     * @param trackInactiveColor the inactive color of track.
     * @param trackActiveColor the active color of track.
     * @return [SliderColors]
     */
    @Composable
    fun colors(
        thumbColor: Color = SliderProperties.ThumbColor.toColor(),
        stepColor: Color = SliderProperties.StepColor.toColor(),
        thumbBorderColor: Color = SliderProperties.ThumbBorderColor.toColor(),
        stepBorderColor: Color = SliderProperties.StepBorderColor.toColor(),
        trackBorderColor: Color = SliderProperties.TrackBorderColor.toColor(),
        trackInactiveColor: Color = SliderProperties.TrackInactiveColor.toColor(),
        trackActiveColor: Color = SliderProperties.TrackActiveColor.toColor()
    ) = SliderColors(
        thumbColor = thumbColor,
        stepColor = stepColor,
        thumbBorderColor = thumbBorderColor,
        stepBorderColor = stepBorderColor,
        trackBorderColor = trackBorderColor,
        trackInactiveColor = trackInactiveColor,
        trackActiveColor = trackActiveColor
    )

    /**
     * Creates a [SliderStyle] with the default values.
     * @param thumbRadius the radius of thumb.
     * @param thumbGain the gain of thumb.
     * @param thumbShadowSize the shadow size of thumb.
     * @param thumbShape the shape of thumb.
     * @param stepShape the shape of step.
     * @param trackShape the shape of track.
     * @param trackWidth the width of track.
     * @param trackHeight the height of track.
     * @param thumbBorderWidth the border width of thumb.
     * @param stepBorderWidth the border width of step.
     * @param trackBorderWidth the border width of track.
     * @return [SliderStyle]
     */
    @Composable
    fun style(
        thumbRadius: Dp = SliderProperties.ThumbRadius,
        thumbGain: Float = SliderProperties.ThumbGain,
        thumbShadowSize: Dp = SliderProperties.ThumbShadowSize,
        thumbShape: Shape = SliderProperties.ThumbShape.toShape(),
        stepShape: Shape = SliderProperties.StepShape.toShape(),
        trackShape: Shape = SliderProperties.TrackShape.toShape(),
        trackWidth: Dp = SliderProperties.TrackWidth,
        trackHeight: Dp = SliderProperties.TrackHeight,
        thumbBorderWidth: Dp = SliderProperties.ThumbBorderWidth.toDp(),
        stepBorderWidth: Dp = SliderProperties.StepBorderWidth.toDp(),
        trackBorderWidth: Dp = SliderProperties.TrackBorderWidth.toDp()
    ) = SliderStyle(
        thumbRadius = thumbRadius,
        thumbGain = thumbGain,
        thumbShadowSize = thumbShadowSize,
        thumbShape = thumbShape,
        stepShape = stepShape,
        trackShape = trackShape,
        trackWidth = trackWidth,
        trackHeight = trackHeight,
        thumbBorderWidth = thumbBorderWidth,
        stepBorderWidth = stepBorderWidth,
        trackBorderWidth = trackBorderWidth
    )
}

@Stable
internal object SliderProperties {
    val ThumbColor = ColorsDescriptor.ThemePrimary
    val StepColor = ColorsDescriptor.ThemeSecondary
    val ThumbBorderColor = ColorsDescriptor.TextPrimary
    val StepBorderColor = ColorsDescriptor.TextPrimary
    val TrackBorderColor = ColorsDescriptor.TextPrimary
    val TrackInactiveColor = ColorsDescriptor.ThemeTertiary
    val TrackActiveColor = ColorsDescriptor.ThemePrimary
    val ThumbRadius = 10.dp
    const val ThumbGain = 1.1f
    val ThumbShadowSize = 0.5.dp
    val ThumbShape = ShapesDescriptor.Tertiary
    val StepShape = ShapesDescriptor.Tertiary
    val TrackShape = ShapesDescriptor.Primary
    val TrackWidth = 240.dp
    val TrackHeight = 4.dp
    val ThumbBorderWidth = SizesDescriptor.BorderSizeTertiary
    val StepBorderWidth = SizesDescriptor.BorderSizeTertiary
    val TrackBorderWidth = SizesDescriptor.BorderSizeTertiary
}