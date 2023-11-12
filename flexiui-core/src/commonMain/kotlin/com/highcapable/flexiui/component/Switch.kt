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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.clickable
import com.highcapable.flexiui.utils.borderOrNot
import kotlin.math.roundToInt

@Immutable
data class SwitchColors(
    val thumbColor: Color,
    val trackInactive: Color,
    val trackActive: Color
)

@Immutable
data class SwitchStyle(
    val padding: Dp,
    val thumbDiameter: Dp,
    val thumbGain: Float,
    val thumbShadowSize: Dp,
    val thumbShape: Shape,
    val trackShape: Shape,
    val thumbBorder: BorderStroke,
    val trackBorder: BorderStroke,
    val trackWidth: Dp,
    val trackHeight: Dp
)

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchColors = Switch.colors,
    style: SwitchStyle = Switch.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentSpacing: Dp = Switch.contentSpacing,
    content: @Composable () -> Unit = {}
) {
    val maxOffset = with(LocalDensity.current) { (style.trackWidth - style.thumbDiameter - style.padding * 2).toPx() }
    val halfWidth = maxOffset / 2
    val hovered by interactionSource.collectIsHoveredAsState()
    var dragging by remember { mutableStateOf(false) }
    var absOffsetX by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var distance by remember { mutableStateOf(0f) }
    if (!hovered && !dragging) offsetX = if (checked) maxOffset else 0f
    val animatedOffsetX by animateFloatAsState(offsetX)
    val animatedScale by animateFloatAsState(if (hovered || dragging) style.thumbGain else 1f)
    var trackColor by remember { mutableStateOf(colors.trackInactive) }
    fun updateTrackColor() {
        val fraction = (offsetX / maxOffset).coerceIn(0f, 1f)
        trackColor = lerp(colors.trackInactive, colors.trackActive, fraction)
    }
    updateTrackColor()
    val animatedTrackColor by animateColorAsState(trackColor)
    val efficientDragging = dragging && distance > 5
    val sModifier = if (enabled) modifier else modifier.alpha(0.5f)

    @Composable
    fun Track(content: @Composable RowScope.() -> Unit) {
        Row(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                enabled = enabled,
                role = Role.Switch
            ) {
                distance = maxOffset
                offsetX = if (checked) 0f else maxOffset
                onCheckedChange(!checked)
            }.background(if (efficientDragging) trackColor else animatedTrackColor, style.trackShape)
                .borderOrNot(style.trackBorder, style.trackShape)
                .size(style.trackWidth, style.trackHeight)
                .padding(start = style.padding, end = style.padding),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }

    @Composable
    fun Thumb() {
        Box(
            modifier = Modifier.size(style.thumbDiameter)
                .offset { IntOffset((if (efficientDragging) offsetX else animatedOffsetX).roundToInt(), 0) }
                .scale(animatedScale)
                .shadow(style.thumbShadowSize, style.thumbShape)
                .background(colors.thumbColor, style.thumbShape)
                .borderOrNot(style.thumbBorder, style.thumbShape)
                .draggable(
                    enabled = enabled,
                    orientation = Orientation.Horizontal,
                    interactionSource = interactionSource,
                    state = rememberDraggableState { delta ->
                        val absDelta = delta * animatedScale
                        absOffsetX += absDelta
                        when {
                            absOffsetX in 0f..maxOffset -> offsetX += absDelta
                            absOffsetX < 0f -> offsetX = 0f
                            absOffsetX > maxOffset -> offsetX = maxOffset
                        }
                        updateTrackColor()
                    },
                    onDragStarted = {
                        dragging = true
                        absOffsetX = offsetX
                    },
                    onDragStopped = {
                        dragging = false
                        if (offsetX >= halfWidth) {
                            distance = maxOffset - offsetX
                            offsetX = maxOffset
                            onCheckedChange(true)
                        } else {
                            distance = offsetX
                            offsetX = 0f
                            onCheckedChange(false)
                        }
                    }
                )
        )
    }
    Row(modifier = sModifier) {
        Box(
            modifier = Modifier.padding(end = contentSpacing)
                .clickable(enabled = enabled) { onCheckedChange(!checked) }
        ) { content() }
        Track { Thumb() }
    }
}

object Switch {
    val colors: SwitchColors
        @Composable
        @ReadOnlyComposable
        get() = defaultSwitchColors()
    val style: SwitchStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultSwitchStyle()
    val contentSpacing: Dp
        @Composable
        @ReadOnlyComposable
        get() = defaultSwitchContentSpacing()
}

@Composable
@ReadOnlyComposable
private fun defaultSwitchColors() = SwitchColors(
    thumbColor = Color.White,
    trackInactive = LocalColors.current.themeTertiary,
    trackActive = LocalColors.current.themePrimary
)

@Composable
@ReadOnlyComposable
private fun defaultSwitchStyle() = SwitchStyle(
    padding = DefaultSwitchPadding,
    thumbDiameter = DefaultThumbDiameter,
    thumbGain = DefaultThumbGain,
    thumbShadowSize = DefaultThumbShadowSize,
    thumbShape = CircleShape,
    trackShape = LocalShapes.current.tertiary,
    thumbBorder = defaultSwitchBorder(),
    trackBorder = defaultSwitchBorder(),
    trackWidth = DefaultTrackWidth,
    trackHeight = DefaultTrackHeight
)

@Composable
@ReadOnlyComposable
private fun defaultSwitchBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

@Composable
@ReadOnlyComposable
private fun defaultSwitchContentSpacing() = LocalSizes.current.spacingSecondary

private val DefaultSwitchPadding = 4.dp

private val DefaultThumbDiameter = 12.dp
private const val DefaultThumbGain = 1.2f
private val DefaultThumbShadowSize = 0.5.dp

private val DefaultTrackWidth = 40.dp
private val DefaultTrackHeight = 20.dp