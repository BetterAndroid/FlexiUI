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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.clickable
import com.highcapable.flexiui.resources.IconRes
import com.highcapable.flexiui.utils.borderOrNot
import org.jetbrains.compose.resources.painterResource

@Immutable
data class CheckBoxColors(
    val contentColor: Color,
    val inactiveColor: Color,
    val activeColor: Color
)

@Immutable
data class CheckBoxStyle(
    val contentSize: Dp,
    val strokeSize: Dp,
    val pressedGain: Float,
    val hoveredGain: Float,
    val shape: Shape,
    val border: BorderStroke
)

@Composable
fun CheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: CheckBoxColors = CheckBox.colors,
    style: CheckBoxStyle = CheckBox.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentSpacing: Dp = CheckBox.contentSpacing,
    content: @Composable () -> Unit = {}
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val animatedStrokeScale by animateFloatAsState(if (pressed) 0.9f else 1f)
    val animatedColor by animateColorAsState(if (checked) colors.activeColor else colors.inactiveColor)
    val animatedContentScale by animateFloatAsState(if (hovered) 1.1f else 1f)
    val animatedContentAlpha by animateFloatAsState(if (checked) 1f else 0f)
    val animatedContentLayer by animateFloatAsState(if (checked) 1f else 0f)
    val sModifier = if (enabled) modifier else modifier.alpha(0.5f)
    Row(modifier = sModifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.clickable(
                enabled = enabled,
                interactionSource = interactionSource
            ) { onCheckedChange(!checked) }
                .size(style.strokeSize)
                .scale(animatedStrokeScale)
                .background(animatedColor, style.shape)
                .borderOrNot(style.border, style.shape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(style.contentSize)
                    .scale(animatedContentScale)
                    .alpha(animatedContentAlpha)
                    .graphicsLayer(
                        clip = true,
                        scaleX = animatedContentLayer,
                        scaleY = animatedContentLayer
                    ),
                painter = painterResource(IconRes.CHECKMARK),
                tint = colors.contentColor
            )
        }
        Box(
            modifier = Modifier.padding(start = contentSpacing)
                .clickable(enabled = enabled) { onCheckedChange(!checked) }
        ) { content() }
    }
}

object CheckBox {
    val colors: CheckBoxColors
        @Composable
        @ReadOnlyComposable
        get() = defaultCheckBoxColors()
    val style: CheckBoxStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultCheckBoxStyle()
    val contentSpacing: Dp
        @Composable
        @ReadOnlyComposable
        get() = defaultCheckBoxContentSpacing()
}

@Composable
@ReadOnlyComposable
private fun defaultCheckBoxColors() = CheckBoxColors(
    contentColor = Color.White,
    inactiveColor = LocalColors.current.themeTertiary,
    activeColor = LocalColors.current.themePrimary
)

@Composable
@ReadOnlyComposable
private fun defaultCheckBoxStyle() = CheckBoxStyle(
    contentSize = DefaultContentSize,
    strokeSize = DefaultStrokeSize,
    pressedGain = DefaultPressedGain,
    hoveredGain = DefaultHoveredGain,
    shape = DefaultCheckBoxShape,
    border = defaultCheckBoxBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultCheckBoxBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

@Composable
@ReadOnlyComposable
private fun defaultCheckBoxContentSpacing() = LocalSizes.current.spacingSecondary

private val DefaultContentSize = 13.dp
private val DefaultStrokeSize = 20.dp

private val DefaultCheckBoxShape = RoundedCornerShape(4.dp)

private const val DefaultPressedGain = 0.9f
private const val DefaultHoveredGain = 1.1f