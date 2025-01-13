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
 * This file is created by fankes on 2023/11/5.
 */
@file:Suppress("unused", "ConstPropertyName")

package com.highcapable.flexiui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.component.interaction.InteractionDefaults
import com.highcapable.flexiui.component.interaction.RippleStyle
import com.highcapable.flexiui.component.interaction.rippleClickable
import com.highcapable.flexiui.component.interaction.rippleToggleable
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape

/**
 * Colors defines for button.
 * @see ButtonDefaults.colors
 * @see IconButtonDefaults.colors
 */
@Immutable
data class ButtonColors(
    val contentColor: Color,
    val backgroundColor: Color,
    val borderColor: Color
)

/**
 * Style defines for button.
 * @see ButtonDefaults.style
 * @see IconButtonDefaults.style
 */
@Immutable
data class ButtonStyle(
    val rippleStyle: RippleStyle,
    val padding: ComponentPadding,
    val shape: Shape,
    val borderWidth: Dp
)

/**
 * Flexi UI button.
 * @see IconButton
 * @see IconToggleButton
 * @param onClick the callback when button is clicked.
 * @param modifier the [Modifier] to be applied to this button.
 * @param colors the colors of this button, default is [ButtonDefaults.colors].
 * @param style the style of this button, default is [ButtonDefaults.style].
 * @param enabled whether this button is enabled, default is true.
 * @param interactionSource the interaction source of this button.
 * @param header the header content of the [Button], should typically be [Icon] or [Text].
 * @param footer the footer content of the [Button], should typically be [Icon] or [Text].
 * @param content the content of the [Button], should typically be [Icon] or [Text].
 */
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.colors(),
    style: ButtonStyle = ButtonDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    val localTextStyle = LocalTextStyle.current.copy(color = colors.contentColor)
    val localProgressIndicatorColors = LocalProgressIndicatorColors.current?.copy(
        foregroundColor = colors.contentColor,
        backgroundColor = Color.Transparent
    )
    Box(
        modifier = Modifier
            .button(
                enabled = enabled,
                colors = colors,
                style = style,
                then = modifier
            )
            .rippleClickable(
                rippleStyle = style.rippleStyle,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(tint = colors.contentColor),
            LocalTextStyle provides localTextStyle,
            LocalProgressIndicatorColors provides localProgressIndicatorColors
        ) {
            Row(
                modifier = Modifier.padding(style.padding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                header()
                content()
                footer()
            }
        }
    }
}

/**
 * Flexi UI icon button.
 * @see IconToggleButton
 * @see Button
 * @param onClick the callback when button is clicked.
 * @param modifier the [Modifier] to be applied to this button.
 * @param colors the colors of this button, default is [IconButtonDefaults.colors].
 * @param style the style of this button, default is [IconButtonDefaults.style].
 * @param enabled whether this button is enabled, default is true.
 * @param interactionSource the interaction source of this button.
 * @param content the content of the [IconButton], should typically be [Icon].
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = IconButtonDefaults.colors(),
    style: ButtonStyle = IconButtonDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .button(
                enabled = enabled,
                colors = colors,
                style = style,
                then = modifier
            )
            .rippleClickable(
                rippleStyle = style.rippleStyle,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .padding(style.padding),
        contentAlignment = Alignment.Center,
    ) { IconButtonStyle(colors, content) }
}

/**
 * Flexi UI icon toggle button.
 * @see IconButton
 * @see Button
 * @param checked the checked state of this button.
 * @param onCheckedChange the callback when checked state is changed.
 * @param modifier the [Modifier] to be applied to this button.
 * @param colors the colors of this button, default is [IconButtonDefaults.colors].
 * @param style the style of this button, default is [IconButtonDefaults.style].
 * @param enabled whether this button is enabled, default is true.
 * @param interactionSource the interaction source of this button.
 * @param content the content of the [IconToggleButton], should typically be [Icon].
 */
@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = IconButtonDefaults.colors(),
    style: ButtonStyle = IconButtonDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .button(
                enabled = enabled,
                colors = colors,
                style = style,
                then = modifier
            )
            .rippleToggleable(
                value = checked,
                rippleStyle = style.rippleStyle,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource
            )
            .padding(style.padding),
        contentAlignment = Alignment.Center
    ) { IconButtonStyle(colors, content) }
}

@Composable
private fun IconButtonStyle(
    colors: ButtonColors,
    content: @Composable () -> Unit
) {
    // The provided tint should have the highest priority,
    // this will allow the user to override the current style through [LocalIconStyle].
    val iconStyle = LocalIconStyle.current.let { it.copy(tint = it.tint.orNull() ?: colors.contentColor) }
    CompositionLocalProvider(
        LocalIconStyle provides iconStyle,
        content = content
    )
}

private fun Modifier.button(
    enabled: Boolean,
    colors: ButtonColors,
    style: ButtonStyle,
    then: Modifier
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "button"
        properties["enabled"] = enabled
        properties["colors"] = colors
        properties["style"] = style
    }
) {
    componentState(enabled)
        .clip(style.shape)
        .background(colors.backgroundColor, style.shape)
        .borderOrElse(style.borderWidth, colors.borderColor, style.shape)
        .then(then)
}

/**
 * Defaults of button.
 */
object ButtonDefaults {

    /**
     * Creates a [ButtonColors] with the default values.
     * @param contentColor the content color, usually for icon tint and text color.
     * @param backgroundColor the background color.
     * @param borderColor the border color.
     * @return [ButtonColors]
     */
    @Composable
    fun colors(
        contentColor: Color = when {
            LocalPrimaryButton.current -> ButtonProperties.PrimaryContentColor
            LocalInAreaBox.current -> ButtonProperties.ContentColor.toColor()
            else -> ButtonProperties.PrimaryContentColor
        },
        backgroundColor: Color = when {
            LocalPrimaryButton.current -> ButtonProperties.PrimaryBackgroundColor
            LocalInAreaBox.current -> ButtonProperties.BackgroundColor
            else -> ButtonProperties.PrimaryBackgroundColor
        }.toColor(),
        borderColor: Color = ButtonProperties.BorderColor.toColor()
    ) = ButtonColors(
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor
    )

    /**
     * Creates a [ButtonStyle] with the default values.
     * @param rippleStyle the ripple style of this button.
     * @param padding the padding of content.
     * @param shape the shape.
     * @param borderWidth the border width.
     * @return [ButtonStyle]
     */
    @Composable
    fun style(
        rippleStyle: RippleStyle = InteractionDefaults.rippleStyle(
            color = when {
                LocalPrimaryButton.current -> ButtonProperties.PrimaryRippleColor
                LocalInAreaBox.current -> ButtonProperties.RippleColor
                else -> ButtonProperties.PrimaryRippleColor
            }.toColor()
        ),
        padding: ComponentPadding = ButtonProperties.Padding.toPadding(),
        shape: Shape = AreaBoxDefaults.childShape(),
        borderWidth: Dp = ButtonProperties.BorderWidth.toDp()
    ) = ButtonStyle(
        rippleStyle = rippleStyle,
        padding = padding,
        shape = shape,
        borderWidth = borderWidth
    )
}

/**
 * Defaults of icon button.
 */
object IconButtonDefaults {

    /**
     * Creates a [ButtonColors] with the default values.
     * @param contentColor the content color, usually for icon tint and text color.
     * @param backgroundColor the background color.
     * @param borderColor the border color.
     * @return [ButtonColors]
     */
    @Composable
    fun colors(
        contentColor: Color = IconButtonProperties.ContentColor.toColor(),
        backgroundColor: Color = IconButtonProperties.BackgroundColor,
        borderColor: Color = ButtonProperties.BorderColor.toColor()
    ) = ButtonColors(
        contentColor = contentColor,
        backgroundColor = backgroundColor,
        borderColor = borderColor
    )

    /**
     * Creates a [ButtonStyle] with the default values.
     * @param rippleStyle the ripple style of this button.
     * @param padding the padding of content.
     * @param shape the shape.
     * @param borderWidth the border width.
     * @return [ButtonStyle]
     */
    @Composable
    fun style(
        rippleStyle: RippleStyle = InteractionDefaults.rippleStyle(bounded = IconButtonProperties.RippleBounded),
        padding: ComponentPadding = IconButtonProperties.Padding,
        shape: Shape = IconButtonProperties.Shape.toShape(),
        borderWidth: Dp = ButtonProperties.BorderWidth.toDp()
    ) = ButtonStyle(
        rippleStyle = rippleStyle,
        padding = padding,
        shape = shape,
        borderWidth = borderWidth
    )
}

@Stable
internal object ButtonProperties {
    val PrimaryContentColor = Color.White
    val ContentColor = ColorsDescriptor.TextPrimary
    val PrimaryBackgroundColor = ColorsDescriptor.ThemePrimary
    val BackgroundColor = ColorsDescriptor.ForegroundSecondary
    val BorderColor = ColorsDescriptor.TextPrimary
    val PrimaryRippleColor = ColorsDescriptor.ForegroundSecondary
    val RippleColor = ColorsDescriptor.ThemeSecondary
    val Padding = PaddingDescriptor(
        horizontal = SizesDescriptor.SpacingPrimary,
        vertical = SizesDescriptor.SpacingSecondary
    )
    val BorderWidth = SizesDescriptor.BorderSizeTertiary
}

@Stable
internal object IconButtonProperties {
    val ContentColor = ColorsDescriptor.ThemePrimary
    val BackgroundColor = Color.Transparent
    const val RippleBounded = false
    val Padding = ComponentPadding.None
    val Shape = ShapesDescriptor.Tertiary
}

internal val LocalPrimaryButton = compositionLocalOf { false }