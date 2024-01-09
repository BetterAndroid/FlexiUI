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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
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
import com.highcapable.betterandroid.compose.extension.ui.clickable
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape

/**
 * Colors defines for radio button.
 * @see RadioButtonDefaults.colors
 */
@Immutable
data class RadioButtonColors(
    val contentColor: Color,
    val inactiveColor: Color,
    val activeColor: Color,
    val borderColor: Color
)

/**
 * Style defines for radio button.
 * @see RadioButtonDefaults.style
 */
@Immutable
data class RadioButtonStyle(
    val contentSpacing: Dp,
    val contentRadius: Dp,
    val contentShadowSize: Dp,
    val strokeRadius: Dp,
    val pressedGain: Float,
    val hoveredGain: Float,
    val shape: Shape,
    val borderWidth: Dp
)

/**
 * Flexi UI radio button.
 * @param selected the selected state of this button.
 * @param onClick the callback when button is clicked.
 * @param modifier the [Modifier] to be applied to this button.
 * @param colors the colors of this button, default is [RadioButtonDefaults.colors].
 * @param style the style of this button, default is [RadioButtonDefaults.style].
 * @param enabled whether this button is enabled, default is true.
 * @param interactionSource the interaction source of this button.
 * @param content the content of the [RowScope] to be applied to the [RadioButton], should typically be [Text].
 */
@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    style: RadioButtonStyle = RadioButtonDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val contentDiameter = style.contentRadius * 2
    val strokeDiameter = style.strokeRadius * 2
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val animatedStrokeScale by animateFloatAsState(if (pressed) style.pressedGain else 1f)
    val animatedColor by animateColorAsState(if (selected) colors.activeColor else colors.inactiveColor)
    val animatedContentScale by animateFloatAsState(if (hovered) style.hoveredGain else 1f)
    val animatedContentShadow by animateDpAsState(if (selected) style.contentShadowSize else 0.dp)
    val animatedContentAlpha by animateFloatAsState(if (selected) 1f else 0f)
    Row(modifier = Modifier.componentState(enabled).then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                enabled = enabled,
                role = Role.RadioButton,
                onClick = onClick
            ).size(strokeDiameter)
                .scale(animatedStrokeScale)
                .background(animatedColor, style.shape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(contentDiameter)
                    .scale(animatedContentScale)
                    .shadow(animatedContentShadow, style.shape)
                    .alpha(animatedContentAlpha)
                    .background(colors.contentColor, style.shape)
            )
        }
        content?.also { content ->
            Row(modifier = Modifier.clickable(enabled = enabled, onClick = onClick)) {
                Spacer(modifier = Modifier.padding(end = style.contentSpacing))
                content()
            }
        }
    }
}

/**
 * Defaults of radio button.
 */
object RadioButtonDefaults {

    /**
     * Creates a [RadioButtonColors] with the default values.
     * @param contentColor the color of the check mark.
     * @param inactiveColor the color of the unchecked box.
     * @param activeColor the color of the checked box.
     * @param borderColor the color of the border.
     * @return [RadioButtonColors]
     */
    @Composable
    fun colors(
        contentColor: Color = RadioButtonProperties.ContentColor,
        inactiveColor: Color = RadioButtonProperties.InactiveColor.toColor(),
        activeColor: Color = RadioButtonProperties.ActiveColor.toColor(),
        borderColor: Color = RadioButtonProperties.BorderColor.toColor()
    ) = RadioButtonColors(
        contentColor = contentColor,
        inactiveColor = inactiveColor,
        activeColor = activeColor,
        borderColor = borderColor
    )

    /**
     * Creates a [RadioButtonStyle] with the default values.
     * @param contentSpacing the spacing between the check mark and the content.
     * @param contentRadius the radius of the check mark.
     * @param strokeRadius the radius of the box.
     * @param pressedGain the gain when pressed.
     * @param hoveredGain the gain when hovered.
     * @param shape the shape.
     * @param borderWidth the border width.
     * @return [RadioButtonStyle]
     */
    @Composable
    fun style(
        contentSpacing: Dp = RadioButtonProperties.ContentSpacing.toDp(),
        contentRadius: Dp = RadioButtonProperties.ContentRadius,
        contentShadowSize: Dp = RadioButtonProperties.ContentShadowSize,
        strokeRadius: Dp = RadioButtonProperties.StrokeRadius,
        pressedGain: Float = RadioButtonProperties.PressedGain,
        hoveredGain: Float = RadioButtonProperties.HoveredGain,
        shape: Shape = RadioButtonProperties.Shape.toShape(),
        borderWidth: Dp = RadioButtonProperties.BorderWidth.toDp()
    ) = RadioButtonStyle(
        contentSpacing = contentSpacing,
        contentRadius = contentRadius,
        contentShadowSize = contentShadowSize,
        strokeRadius = strokeRadius,
        pressedGain = pressedGain,
        hoveredGain = hoveredGain,
        shape = shape,
        borderWidth = borderWidth
    )
}

@Stable
internal object RadioButtonProperties {
    val ContentColor = Color.White
    val InactiveColor = ColorsDescriptor.ThemeTertiary
    val ActiveColor = ColorsDescriptor.ThemePrimary
    val BorderColor = ColorsDescriptor.TextPrimary
    val ContentSpacing = SizesDescriptor.SpacingSecondary
    val ContentRadius = 5.dp
    val ContentShadowSize = 0.5.dp
    val StrokeRadius = 10.dp
    const val PressedGain = 0.9f
    const val HoveredGain = 1.2f
    val Shape = ShapesDescriptor.Tertiary
    val BorderWidth = SizesDescriptor.BorderSizeTertiary
}