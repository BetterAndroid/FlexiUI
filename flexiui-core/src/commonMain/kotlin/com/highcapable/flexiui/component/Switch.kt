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
@file:Suppress("unused", "ConstPropertyName")

package com.highcapable.flexiui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
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
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import kotlin.math.roundToInt

/**
 * Colors defines for switch.
 * @see SwitchDefaults.colors
 */
@Immutable
data class SwitchColors(
    val thumbColor: Color,
    val thumbBorderColor: Color,
    val trackBorderColor: Color,
    val trackInactiveColor: Color,
    val trackActiveColor: Color
)

/**
 * Style defines for switch.
 * @see SwitchDefaults.style
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
    val trackWidth: Dp,
    val trackHeight: Dp,
    val thumbBorderWidth: Dp,
    val trackBorderWidth: Dp
)

/**
 * Flexi UI switch.
 * @see SwitchItem
 * @param checked the checked state of switch.
 * @param onCheckedChange the callback when switch checked state changed.
 * @param modifier the [Modifier] to be applied to this switch.
 * @param colors the colors of switch, default is [SwitchDefaults.colors].
 * @param style the style of switch, default is [SwitchDefaults.style].
 * @param enabled the enabled state of switch, default is true.
 * @param interactionSource the interaction source of switch.
 */
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors(),
    style: SwitchStyle = SwitchDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
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
    var trackColor by remember { mutableStateOf(colors.trackInactiveColor) }

    /** Update the track color of switch. */
    fun updateTrackColor() {
        val fraction = (offsetX / maxOffsetX).coerceIn(0f, 1f)
        trackColor = lerp(colors.trackInactiveColor, colors.trackActiveColor, fraction)
    }
    updateTrackColor()
    val animatedTrackColor by animateColorAsState(trackColor)
    val efficientDragging = dragging && distance > 5

    /** Build the track of switch. */
    @Composable
    fun Track(content: @Composable RowScope.() -> Unit) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    enabled = enabled,
                    role = Role.Switch
                ) {
                    distance = maxOffsetX
                    offsetX = if (checked) 0f else maxOffsetX
                    onCheckedChange(!checked)
                }
                .background(if (efficientDragging) trackColor else animatedTrackColor, style.trackShape)
                .borderOrElse(style.trackBorderWidth, colors.trackBorderColor, style.trackShape)
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
            modifier = Modifier
                .size(thumbDiameter)
                .offset { IntOffset((if (efficientDragging) offsetX else animatedOffsetX).roundToInt(), 0) }
                .scale(animatedScale)
                .shadow(style.thumbShadowSize, style.thumbShape)
                .background(colors.thumbColor, style.thumbShape)
                .borderOrElse(style.thumbBorderWidth, colors.thumbBorderColor, style.thumbShape)
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
    Box(
        modifier = Modifier
            .componentState(enabled)
            .then(modifier)
    ) { Track { Thumb() } }
}

/**
 * Flexi UI switch item.
 * @see Switch
 * @param checked the checked state of switch.
 * @param onCheckedChange the callback when switch checked state changed.
 * @param modifier the [Modifier] to be applied to this switch.
 * @param colors the colors of switch, default is [SwitchDefaults.colors].
 * @param style the style of switch, default is [SwitchDefaults.style].
 * @param enabled the enabled state of switch, default is true.
 * @param interactionSource the interaction source of switch.
 * @param content the content of the [SwitchItem], should typically be [Icon] or [Text].
 */
@Composable
fun SwitchItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors(),
    style: SwitchStyle = SwitchDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .componentState(enabled)
                .weight(1f)
                .clickable(enabled = enabled) { onCheckedChange(!checked) }
        ) { content() }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            colors = colors,
            style = style,
            enabled = enabled,
            interactionSource = interactionSource
        )
    }
}

/**
 * Defaults of switch.
 */
object SwitchDefaults {

    /**
     * Creates a [SwitchColors] with the default values.
     * @param thumbColor the color of thumb.
     * @param thumbBorderColor the border color of thumb.
     * @param trackBorderColor the border color of track.
     * @param trackInactiveColor the color of track when switch is inactive.
     * @param trackActiveColor the color of track when switch is active.
     * @return [SwitchColors]
     */
    @Composable
    fun colors(
        thumbColor: Color = SwitchProperties.ThumbColor,
        thumbBorderColor: Color = SwitchProperties.ThumbBorderColor.toColor(),
        trackBorderColor: Color = SwitchProperties.TrackBorderColor.toColor(),
        trackInactiveColor: Color = SwitchProperties.TrackInactiveColor.toColor(),
        trackActiveColor: Color = SwitchProperties.TrackActiveColor.toColor()
    ) = SwitchColors(
        thumbColor = thumbColor,
        thumbBorderColor = thumbBorderColor,
        trackBorderColor = trackBorderColor,
        trackInactiveColor = trackInactiveColor,
        trackActiveColor = trackActiveColor
    )

    /**
     * Creates a [SwitchStyle] with the default values.
     * @param padding the padding between thumb and track.
     * @param contentSpacing the spacing between content and track.
     * @param thumbRadius the radius of thumb.
     * @param thumbGain the gain of thumb when switch is hovered or dragging.
     * @param thumbShadowSize the shadow size of thumb.
     * @param thumbShape the shape of thumb.
     * @param trackShape the shape of track.
     * @param trackWidth the width of track.
     * @param trackHeight the height of track.
     * @param thumbBorderWidth the border width of thumb.
     * @param trackBorderWidth the border width of track.
     * @return [SwitchStyle]
     */
    @Composable
    fun style(
        padding: ComponentPadding = SwitchProperties.Padding,
        contentSpacing: Dp = SwitchProperties.ContentSpacing.toDp(),
        thumbRadius: Dp = SwitchProperties.ThumbRadius,
        thumbGain: Float = SwitchProperties.ThumbGain,
        thumbShadowSize: Dp = SwitchProperties.ThumbShadowSize,
        thumbShape: Shape = SwitchProperties.ThumbShape.toShape(),
        trackShape: Shape = SwitchProperties.TrackShape.toShape(),
        trackWidth: Dp = SwitchProperties.TrackWidth,
        trackHeight: Dp = SwitchProperties.TrackHeight,
        trackBorderWidth: Dp = SwitchProperties.TrackBorderWidth.toDp(),
        thumbBorderWidth: Dp = SwitchProperties.ThumbBorderWidth.toDp()
    ) = SwitchStyle(
        padding = padding,
        contentSpacing = contentSpacing,
        thumbRadius = thumbRadius,
        thumbGain = thumbGain,
        thumbShadowSize = thumbShadowSize,
        thumbShape = thumbShape,
        trackShape = trackShape,
        trackWidth = trackWidth,
        trackHeight = trackHeight,
        thumbBorderWidth = thumbBorderWidth,
        trackBorderWidth = trackBorderWidth
    )
}

/**
 * Properties for [Switch].
 */
@Stable
internal object SwitchProperties {
    val ThumbColor = Color.White
    val ThumbBorderColor = ColorsDescriptor.TextPrimary
    val TrackBorderColor = ColorsDescriptor.TextPrimary
    val TrackInactiveColor = ColorsDescriptor.ThemeTertiary
    val TrackActiveColor = ColorsDescriptor.ThemePrimary
    val Padding = ComponentPadding(horizontal = 3.5.dp)
    val ContentSpacing = SizesDescriptor.SpacingSecondary
    val ThumbRadius = 6.5.dp
    const val ThumbGain = 1.1f
    val ThumbShadowSize = 0.5.dp
    val ThumbShape = ShapesDescriptor.Tertiary
    val TrackShape = ShapesDescriptor.Tertiary
    val TrackWidth = 40.dp
    val TrackHeight = 20.dp
    val ThumbBorderWidth = SizesDescriptor.BorderSizeTertiary
    val TrackBorderWidth = SizesDescriptor.BorderSizeTertiary
}