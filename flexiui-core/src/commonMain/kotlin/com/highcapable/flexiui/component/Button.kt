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
 * This file is created by fankes on 2023/11/5.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
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
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.borderOrElse
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.InteractionDefaults
import com.highcapable.flexiui.interaction.RippleStyle
import com.highcapable.flexiui.interaction.rippleClickable
import com.highcapable.flexiui.interaction.rippleToggleable

/**
 * Colors defines for button.
 * @param contentColor the content color, usually for icon tint and text color.
 * @param backgroundColor the background color.
 */
@Immutable
data class ButtonColors(
    val contentColor: Color,
    val backgroundColor: Color
)

/**
 * Style defines for button.
 * @param rippleStyle the ripple style of this button.
 * @param padding the padding of content.
 * @param shape the shape.
 * @param border the border stroke.
 */
@Immutable
data class ButtonStyle(
    val rippleStyle: RippleStyle,
    val padding: ComponentPadding,
    val shape: Shape,
    val border: BorderStroke
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
    colors: ButtonColors = ButtonDefaults.colors,
    style: ButtonStyle = ButtonDefaults.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    val localTextStyle = LocalTextStyle.current.copy(color = colors.contentColor)
    val localProgressIndicatorColors = LocalProgressIndicatorColors.current.copy(
        foregroundColor = colors.contentColor,
        backgroundColor = Color.Transparent
    )
    Box(
        modifier = Modifier.button(
            enabled = enabled,
            colors = colors,
            style = style,
            then = modifier
        ).rippleClickable(
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
    colors: ButtonColors = IconButtonDefaults.colors,
    style: ButtonStyle = IconButtonDefaults.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.button(
            enabled = enabled,
            colors = colors,
            style = style,
            then = modifier
        ).rippleClickable(
            rippleStyle = style.rippleStyle,
            enabled = enabled,
            role = Role.Button,
            interactionSource = interactionSource,
            onClick = onClick
        ).padding(style.padding),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(tint = colors.contentColor),
            content = content
        )
    }
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
    colors: ButtonColors = IconButtonDefaults.colors,
    style: ButtonStyle = IconButtonDefaults.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.button(
            enabled = enabled,
            colors = colors,
            style = style,
            then = modifier
        ).rippleToggleable(
            value = checked,
            rippleStyle = style.rippleStyle,
            onValueChange = onCheckedChange,
            enabled = enabled,
            role = Role.Checkbox,
            interactionSource = interactionSource
        ).padding(style.padding),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(tint = colors.contentColor),
            content = content
        )
    }
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
        .borderOrElse(style.border, style.shape)
        .then(then)
}

/**
 * Defaults of button.
 */
object ButtonDefaults {
    val colors: ButtonColors
        @Composable
        @ReadOnlyComposable
        get() = defaultButtonColors()
    val style: ButtonStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultButtonStyle()
}

/**
 * Defaults of icon button.
 */
object IconButtonDefaults {
    val colors: ButtonColors
        @Composable
        @ReadOnlyComposable
        get() = defaultIconButtonColors()
    val style: ButtonStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultIconButtonStyle()
}

internal val LocalPrimaryButton = compositionLocalOf { false }

@Composable
@ReadOnlyComposable
private fun defaultPrimaryButtonColors() = ButtonColors(
    contentColor = Color.White,
    backgroundColor = LocalColors.current.themePrimary
)

@Composable
@ReadOnlyComposable
private fun defaultContentButtonColors() = ButtonColors(
    contentColor = LocalColors.current.textPrimary,
    backgroundColor = LocalColors.current.foregroundSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultPrimaryButtonRippleStyle() =
    InteractionDefaults.rippleStyle.copy(color = LocalColors.current.foregroundSecondary)

@Composable
@ReadOnlyComposable
private fun defaultContentButtonRippleStyle() =
    InteractionDefaults.rippleStyle.copy(color = LocalColors.current.themeSecondary)

@Composable
@ReadOnlyComposable
private fun defaultIconButtonRippleStyle() = InteractionDefaults.rippleStyle.copy(bounded = false)

@Composable
@ReadOnlyComposable
private fun defaultButtonColors() = when {
    LocalPrimaryButton.current -> defaultPrimaryButtonColors()
    LocalInAreaBox.current -> defaultContentButtonColors()
    else -> defaultPrimaryButtonColors()
}

@Composable
@ReadOnlyComposable
private fun defaultButtonStyle() = ButtonStyle(
    rippleStyle = defaultButtonRippleStyle(),
    padding = ComponentPadding(
        horizontal = LocalSizes.current.spacingPrimary,
        vertical = LocalSizes.current.spacingSecondary
    ),
    shape = withAreaBoxShape(),
    border = defaultButtonBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultButtonRippleStyle() = when {
    LocalPrimaryButton.current -> defaultPrimaryButtonRippleStyle()
    LocalInAreaBox.current -> defaultContentButtonRippleStyle()
    else -> defaultPrimaryButtonRippleStyle()
}

@Composable
@ReadOnlyComposable
private fun defaultIconButtonColors() = ButtonColors(
    contentColor = LocalIconStyle.current.tint.orNull() ?: LocalColors.current.themePrimary,
    backgroundColor = Color.Transparent
)

@Composable
@ReadOnlyComposable
private fun defaultIconButtonStyle() = ButtonStyle(
    rippleStyle = defaultIconButtonRippleStyle(),
    padding = ComponentPadding(),
    shape = LocalShapes.current.tertiary,
    border = defaultButtonBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultButtonBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)