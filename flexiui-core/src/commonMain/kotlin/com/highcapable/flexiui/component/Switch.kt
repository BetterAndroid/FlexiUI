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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.betterandroid.compose.extension.ui.clickable
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import kotlin.math.roundToInt

/**
 * Colors defines for switch.
 * @param thumbColor the color of thumb.
 * @param trackInactive the color of track when switch is inactive.
 * @param trackActive the color of track when switch is active.
 */
@Immutable
data class SwitchColors(
    val thumbColor: Color,
    val trackInactive: Color,
    val trackActive: Color
)

/**
 * Style defines for switch.
 * @param padding the padding between thumb and track.
 * @param contentSpacing the spacing between content and track.
 * @param thumbRadius the radius of thumb.
 * @param thumbGain the gain of thumb when switch is hovered or dragging.
 * @param thumbShadowSize the shadow size of thumb.
 * @param thumbShape the shape of thumb.
 * @param trackShape the shape of track.
 * @param thumbBorder the border of thumb.
 * @param trackBorder the border of track.
 * @param trackWidth the width of track.
 * @param trackHeight the height of track.
 */
@Immutable
data class SwitchStyle(
    val padding: ComponentPadding,
    val contentSpacing: Dp,
    val thumbRadius: Dp,
    val thumbGain: Float,
    val thumbShadowSize: Dp,
    val thumbShape: Shape,
    val trackShape: Shape,
    val thumbBorder: BorderStroke,
    val trackBorder: BorderStroke,
    val trackWidth: Dp,
    val trackHeight: Dp
)

/**
 * Flexi UI switch.
 * @param checked the checked state of switch.
 * @param onCheckedChange the callback when switch checked state changed.
 * @param modifier the [Modifier] to be applied to this switch.
 * @param colors the colors of switch, default is [SwitchDefaults.colors].
 * @param style the style of switch, default is [SwitchDefaults.style].
 * @param enabled the enabled state of switch, default is true.
 * @param interactionSource the interaction source of switch.
 * @param content the content of the [RowScope] to be applied to the [Switch], should typically be [Text].
 */
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors,
    style: SwitchStyle = SwitchDefaults.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val thumbDiameter = style.thumbRadius * 2
    val maxOffsetX = with(LocalDensity.current) { (style.trackWidth - thumbDiameter - style.padding.horizontal).toPx() }
    val halfWidth = maxOffsetX / 2
    val hovered by interactionSource.collectIsHoveredAsState()
    var dragging by remember { mutableStateOf(false) }
    var absOffsetX by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var distance by remember { mutableStateOf(0f) }
    if (!hovered && !dragging) offsetX = if (checked) maxOffsetX else 0f
    val animatedOffsetX by animateFloatAsState(offsetX)
    val animatedScale by animateFloatAsState(if (hovered || dragging) style.thumbGain else 1f)
    var trackColor by remember { mutableStateOf(colors.trackInactive) }

    /** Update the track color of switch. */
    fun updateTrackColor() {
        val fraction = (offsetX / maxOffsetX).coerceIn(0f, 1f)
        trackColor = lerp(colors.trackInactive, colors.trackActive, fraction)
    }
    updateTrackColor()
    val animatedTrackColor by animateColorAsState(trackColor)
    val efficientDragging = dragging && distance > 5

    /** Build the track of switch. */
    @Composable
    fun Track(content: @Composable RowScope.() -> Unit) {
        Row(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                enabled = enabled,
                role = Role.Switch
            ) {
                distance = maxOffsetX
                offsetX = if (checked) 0f else maxOffsetX
                onCheckedChange(!checked)
            }.background(if (efficientDragging) trackColor else animatedTrackColor, style.trackShape)
                .borderOrElse(style.trackBorder, style.trackShape)
                .size(style.trackWidth, style.trackHeight)
                .padding(style.padding),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }

    /** Build the thumb of switch. */
    @Composable
    fun Thumb() {
        Box(
            modifier = Modifier.size(thumbDiameter)
                .offset { IntOffset((if (efficientDragging) offsetX else animatedOffsetX).roundToInt(), 0) }
                .scale(animatedScale)
                .shadow(style.thumbShadowSize, style.thumbShape)
                .background(colors.thumbColor, style.thumbShape)
                .borderOrElse(style.thumbBorder, style.thumbShape)
                .draggable(
                    enabled = enabled,
                    orientation = Orientation.Horizontal,
                    interactionSource = interactionSource,
                    state = rememberDraggableState { delta ->
                        val absDelta = delta * animatedScale
                        absOffsetX += absDelta
                        when {
                            absOffsetX in 0f..maxOffsetX -> offsetX += absDelta
                            absOffsetX < 0f -> offsetX = 0f
                            absOffsetX > maxOffsetX -> offsetX = maxOffsetX
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
                            distance = maxOffsetX - offsetX
                            offsetX = maxOffsetX
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
    Row(modifier = Modifier.componentState(enabled).then(modifier)) {
        content?.also { content ->
            Row(modifier = Modifier.clickable(enabled = enabled) { onCheckedChange(!checked) }) {
                content()
                Spacer(modifier = Modifier.padding(start = style.contentSpacing))
            }
        }
        Track { Thumb() }
    }
}

/**
 * Defaults of switch.
 */
object SwitchDefaults {
    val colors: SwitchColors
        @Composable
        @ReadOnlyComposable
        get() = defaultSwitchColors()
    val style: SwitchStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultSwitchStyle()
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
    padding = ComponentPadding(horizontal = DefaultSwitchPadding),
    contentSpacing = LocalSizes.current.spacingSecondary,
    thumbRadius = DefaultThumbRadius,
    thumbGain = DefaultThumbGain,
    thumbShadowSize = DefaultThumbShadowSize,
    thumbShape = LocalShapes.current.tertiary,
    trackShape = LocalShapes.current.tertiary,
    thumbBorder = defaultSwitchBorder(),
    trackBorder = defaultSwitchBorder(),
    trackWidth = DefaultTrackWidth,
    trackHeight = DefaultTrackHeight
)

@Composable
@ReadOnlyComposable
private fun defaultSwitchBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

private val DefaultSwitchPadding = 3.5.dp

private val DefaultThumbRadius = 6.5.dp
private const val DefaultThumbGain = 1.1f
private val DefaultThumbShadowSize = 0.5.dp

private val DefaultTrackWidth = 40.dp
private val DefaultTrackHeight = 20.dp