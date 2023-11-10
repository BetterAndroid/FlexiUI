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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.clickable

@Immutable
data class RadioButtonColors(
    val contentColor: Color,
    val inactiveColor: Color,
    val activeColor: Color
)

@Immutable
data class RadioButtonStyle(
    val contentSize: Dp,
    val contentShadowSize: Dp,
    val strokeSize: Dp,
    val pressedGain: Float,
    val hoveredGain: Float,
    val shape: Shape,
    val border: BorderStroke
)

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButton.colors,
    style: RadioButtonStyle = RadioButton.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentSpacing: Dp = RadioButton.contentSpacing,
    content: @Composable () -> Unit = {}
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val animatedStrokeScale by animateFloatAsState(if (pressed) style.pressedGain else 1f)
    val animatedColor by animateColorAsState(if (selected) colors.activeColor else colors.inactiveColor)
    val animatedContentScale by animateFloatAsState(if (hovered) style.hoveredGain else 1f)
    val animatedContentShadow by animateDpAsState(if (selected) style.contentShadowSize else 0.dp)
    val animatedContentAlpha by animateFloatAsState(if (selected) 1f else 0f)
    val sModifier = if (enabled) modifier else modifier.alpha(0.5f)
    Row(modifier = sModifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                enabled = enabled,
                role = Role.RadioButton,
                onClick = onClick
            ).size(style.strokeSize)
                .scale(animatedStrokeScale)
                .background(animatedColor, style.shape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(style.contentSize)
                    .scale(animatedContentScale)
                    .shadow(animatedContentShadow, style.shape)
                    .alpha(animatedContentAlpha)
                    .background(colors.contentColor, style.shape)
            )
        }
        Box(
            modifier = Modifier.padding(start = contentSpacing)
                .clickable(enabled = enabled, onClick = onClick)
        ) { content() }
    }
}

object RadioButton {
    val colors: RadioButtonColors
        @Composable
        @ReadOnlyComposable
        get() = defaultRadioButtonColors()
    val style: RadioButtonStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultRadioButtonStyle()
    val contentSpacing: Dp
        @Composable
        @ReadOnlyComposable
        get() = defaultRadioButtonContentSpacing()
}

@Composable
@ReadOnlyComposable
private fun defaultRadioButtonColors() = RadioButtonColors(
    contentColor = Color.White,
    inactiveColor = LocalColors.current.themeTertiary,
    activeColor = LocalColors.current.themePrimary
)

@Composable
@ReadOnlyComposable
private fun defaultRadioButtonStyle() = RadioButtonStyle(
    contentSize = DefaultContentSize,
    contentShadowSize = DefaultContentShadowSize,
    strokeSize = DefaultStrokeSize,
    pressedGain = DefaultPressedGain,
    hoveredGain = DefaultHoveredGain,
    shape = CircleShape,
    border = defaultRadioButtonBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultRadioButtonBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

@Composable
@ReadOnlyComposable
private fun defaultRadioButtonContentSpacing() = LocalSizes.current.spacingSecondary

private val DefaultContentSize = 10.dp
private val DefaultStrokeSize = 20.dp

private const val DefaultPressedGain = 0.9f
private const val DefaultHoveredGain = 1.2f

private val DefaultContentShadowSize = 0.5.dp