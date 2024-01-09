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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
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
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.betterandroid.compose.extension.ui.clickable
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.resources.FlexiIcons
import com.highcapable.flexiui.resources.icon.CheckMark
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp

/**
 * Colors defines for check box.
 * @see CheckBoxDefaults.colors
 */
@Immutable
data class CheckBoxColors(
    val contentColor: Color,
    val inactiveColor: Color,
    val activeColor: Color,
    val borderColor: Color
)

/**
 * Style defines for check box.
 * @see CheckBoxDefaults.style
 */
@Immutable
data class CheckBoxStyle(
    val contentSpacing: Dp,
    val contentSize: Dp,
    val strokeSize: Dp,
    val pressedGain: Float,
    val hoveredGain: Float,
    val shape: Shape,
    val borderWidth: Dp
)

/**
 * Flexi UI check box.
 * @param checked the checked state of this check box.
 * @param onCheckedChange the callback when checked state changed.
 * @param modifier the [Modifier] to be applied to this check box.
 * @param colors the colors of this check box, default is [CheckBoxDefaults.colors].
 * @param style the style of this check box, default is [CheckBoxDefaults.style].
 * @param enabled whether this check box is enabled, default is true.
 * @param interactionSource the interaction source of this check box.
 * @param content the content of the [RowScope] to be applied to the [CheckBox], should typically be [Text].
 */
@Composable
fun CheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: CheckBoxColors = CheckBoxDefaults.colors(),
    style: CheckBoxStyle = CheckBoxDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable (RowScope.() -> Unit)? = null
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    val animatedStrokeScale by animateFloatAsState(if (pressed) style.pressedGain else 1f)
    val animatedColor by animateColorAsState(if (checked) colors.activeColor else colors.inactiveColor)
    val animatedContentScale by animateFloatAsState(if (hovered) style.hoveredGain else 1f)
    val animatedContentAlpha by animateFloatAsState(if (checked) 1f else 0f)
    val animatedContentLayer by animateFloatAsState(if (checked) 1f else 0f)
    Row(modifier = Modifier.componentState(enabled).then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                enabled = enabled,
                role = Role.Checkbox
            ) { onCheckedChange(!checked) }
                .size(style.strokeSize)
                .scale(animatedStrokeScale)
                .background(animatedColor, style.shape)
                .borderOrElse(style.borderWidth, colors.borderColor, style.shape),
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
                imageVector = FlexiIcons.CheckMark,
                style = IconDefaults.style(tint = colors.contentColor)
            )
        }
        content?.also { content ->
            Row(modifier = Modifier.clickable(enabled = enabled) { onCheckedChange(!checked) }) {
                Spacer(modifier = Modifier.padding(end = style.contentSpacing))
                content()
            }
        }
    }
}

/**
 * Defaults of check box.
 */
object CheckBoxDefaults {

    /**
     * Creates a [CheckBoxColors] with the default values.
     * @param contentColor the color of the check mark.
     * @param inactiveColor the color of the unchecked box.
     * @param activeColor the color of the checked box.
     * @param borderColor the color of the border.
     * @return [CheckBoxColors]
     */
    @Composable
    fun colors(
        contentColor: Color = CheckBoxProperties.ContentColor,
        inactiveColor: Color = CheckBoxProperties.InactiveColor.toColor(),
        activeColor: Color = CheckBoxProperties.ActiveColor.toColor(),
        borderColor: Color = CheckBoxProperties.BorderColor.toColor()
    ) = CheckBoxColors(
        contentColor = contentColor,
        inactiveColor = inactiveColor,
        activeColor = activeColor,
        borderColor = borderColor
    )

    /**
     * Creates a [CheckBoxStyle] with the default values.
     * @param contentSpacing the spacing between the check mark and the content.
     * @param contentSize the size of the check mark.
     * @param strokeSize the stroke size.
     * @param pressedGain the gain when pressed.
     * @param hoveredGain the gain when hovered.
     * @param shape the shape.
     * @param borderWidth the border width.
     * @return [CheckBoxStyle]
     */
    @Composable
    fun style(
        contentSpacing: Dp = CheckBoxProperties.ContentSpacing.toDp(),
        contentSize: Dp = CheckBoxProperties.ContentSize,
        strokeSize: Dp = CheckBoxProperties.StrokeSize,
        pressedGain: Float = CheckBoxProperties.PressedGain,
        hoveredGain: Float = CheckBoxProperties.HoveredGain,
        shape: Shape = CheckBoxProperties.Shape,
        borderWidth: Dp = CheckBoxProperties.BorderWidth.toDp()
    ) = CheckBoxStyle(
        contentSpacing = contentSpacing,
        contentSize = contentSize,
        strokeSize = strokeSize,
        pressedGain = pressedGain,
        hoveredGain = hoveredGain,
        shape = shape,
        borderWidth = borderWidth
    )
}

@Stable
internal object CheckBoxProperties {
    val ContentColor = Color.White
    val InactiveColor = ColorsDescriptor.ThemeTertiary
    val ActiveColor = ColorsDescriptor.ThemePrimary
    val BorderColor = ColorsDescriptor.TextPrimary
    val ContentSpacing = SizesDescriptor.SpacingSecondary
    val ContentSize = 13.dp
    val StrokeSize = 20.dp
    const val PressedGain = 0.9f
    const val HoveredGain = 1.1f
    val Shape: Shape = RoundedCornerShape(4.dp)
    val BorderWidth = SizesDescriptor.BorderSizeTertiary
}