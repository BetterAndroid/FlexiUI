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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
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
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import kotlin.math.abs
import kotlin.math.roundToInt

@Immutable
data class SliderColors(
    val trackInactiveColor: Color,
    val trackActiveColor: Color,
    val thumbColor: Color,
    val stepColor: Color
)

@Immutable
data class SliderStyle(
    val thumbRadius: Dp,
    val thumbGain: Float,
    val thumbShadowSize: Dp,
    val thumbShape: Shape,
    val stepShape: Shape,
    val trackShape: Shape,
    val thumbBorder: BorderStroke,
    val stepBorder: BorderStroke,
    val trackBorder: BorderStroke,
    val trackWidth: Dp,
    val trackHeight: Dp
)

@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    colors: SliderColors = Slider.colors,
    style: SliderStyle = Slider.style,
    enabled: Boolean = true,
    min: Float = 0f,
    max: Float = 100f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val thumbDiameter = style.thumbRadius * 2
    val trackAdoptWidth = style.trackWidth - thumbDiameter
    val hovered by interactionSource.collectIsHoveredAsState()
    var tapped by remember { mutableStateOf(false) }
    var dragging by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(if (hovered || dragging) style.thumbGain else 1f)
    val maxOffsetX = with(LocalDensity.current) { (style.trackWidth - thumbDiameter).toPx() }
    var steppedOffsetXs by remember { mutableStateOf(listOf<Float>()) }
    if (steps > 0) {
        val pOffsetX = maxOffsetX / (steps + 1)
        steppedOffsetXs = List(steps + 2) { index -> index * pOffsetX }
    }

    fun Float.withSteps() =
        if (steps > 0)
            steppedOffsetXs.minByOrNull { abs(it - this) } ?: this
        else this

    val offsetXFromValue = (value.coerceIn(min, max) - min) / (max - min) * maxOffsetX
    val steppedOffsetXFromValue = offsetXFromValue.withSteps()
    var absOffsetX by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(steppedOffsetXFromValue) }
    val animatedOffsetX by animateFloatAsState(offsetX)
    val adoptedOffsetX = if (tapped && !dragging) animatedOffsetX else offsetX

    fun updateValue(offsetX: Float) {
        val newValue = (offsetX / maxOffsetX) * (max - min) + min
        onValueChange(newValue)
    }

    @Composable
    fun Track(content: @Composable () -> Unit) {
        val cornerSize = (style.trackShape as? CornerBasedShape)?.topStart?.toPx(Size.Zero, LocalDensity.current) ?: 0f
        Box(modifier = Modifier.width(style.trackWidth), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier.size(trackAdoptWidth, style.trackHeight)
                    .background(colors.trackInactiveColor, style.trackShape)
                    .borderOrElse(style.trackBorder, style.trackShape)
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

    @Composable
    fun Step() {
        if (steps > 0) Row(
            modifier = Modifier.size(trackAdoptWidth, style.trackHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until steps + 2)
                Box(
                    modifier = Modifier.size(style.trackHeight)
                        .background(colors.stepColor, style.stepShape)
                        .borderOrElse(style.stepBorder, style.stepShape)
                )
        }
    }

    @Composable
    fun Thumb() {
        Box(
            modifier = Modifier.size(thumbDiameter)
                .offset { IntOffset(adoptedOffsetX.roundToInt(), 0) }
                .scale(animatedScale)
                .shadow(style.thumbShadowSize, style.thumbShape)
                .background(colors.thumbColor, style.thumbShape)
                .borderOrElse(style.thumbBorder, style.thumbShape)
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
        modifier = Modifier.componentState(enabled)
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

object Slider {
    val colors
        @Composable
        @ReadOnlyComposable
        get() = defaultSliderColors()
    val style
        @Composable
        @ReadOnlyComposable
        get() = defaultSliderStyle()
}

@Composable
@ReadOnlyComposable
private fun defaultSliderColors() = SliderColors(
    trackInactiveColor = LocalColors.current.themeTertiary,
    trackActiveColor = LocalColors.current.themePrimary,
    thumbColor = LocalColors.current.themePrimary,
    stepColor = LocalColors.current.themeSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultSliderStyle() = SliderStyle(
    thumbRadius = DefaultThumbRadius,
    thumbGain = DefaultThumbGain,
    thumbShadowSize = DefaultThumbShadowSize,
    thumbShape = LocalShapes.current.tertiary,
    stepShape = LocalShapes.current.tertiary,
    trackShape = LocalShapes.current.primary,
    thumbBorder = defaultSliderBorder(),
    stepBorder = defaultSliderBorder(),
    trackBorder = defaultSliderBorder(),
    trackWidth = DefaultTrackWidth,
    trackHeight = DefaultTrackHeight
)

@Composable
@ReadOnlyComposable
private fun defaultSliderBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

private val DefaultThumbRadius = 10.dp
private const val DefaultThumbGain = 1.1f
private val DefaultThumbShadowSize = 0.5.dp

private val DefaultTrackWidth = 240.dp
private val DefaultTrackHeight = 4.dp