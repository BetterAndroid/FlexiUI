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
 * This file is created by fankes on 2023/11/5.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.extension.borderOrNot
import com.highcapable.flexiui.extension.orElse
import com.highcapable.flexiui.extension.status
import com.highcapable.flexiui.interaction.rippleClickable
import com.highcapable.flexiui.interaction.rippleToggleable

@Immutable
data class ButtonColors(
    val rippleColor: Color,
    val contentColor: Color,
    val backgroundColor: Color
)

@Immutable
data class ButtonStyle(
    val padding: PaddingValues,
    val shape: Shape,
    val border: BorderStroke
)

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = Button.colors,
    style: ButtonStyle = Button.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    val localTextStyle = LocalTextStyle.current.default(color = colors.contentColor)
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
            enabled = enabled,
            role = Role.Button,
            rippleColor = colors.rippleColor,
            interactionSource = interactionSource,
            onClick = onClick
        )
    ) {
        CompositionLocalProvider(
            LocalIconTint provides colors.contentColor,
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

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = IconButton.colors,
    style: ButtonStyle = IconButton.style,
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
            rippleColor = colors.rippleColor,
            bounded = false,
            enabled = enabled,
            role = Role.Button,
            interactionSource = interactionSource,
            onClick = onClick
        ).padding(style.padding),
        contentAlignment = Alignment.Center,
    ) { CompositionLocalProvider(LocalIconTint provides colors.contentColor, content = content) }
}

@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = IconButton.colors,
    style: ButtonStyle = IconButton.style,
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
            rippleColor = colors.rippleColor,
            bounded = false,
            onValueChange = onCheckedChange,
            enabled = enabled,
            role = Role.Checkbox,
            interactionSource = interactionSource
        ).padding(style.padding),
        contentAlignment = Alignment.Center
    ) { CompositionLocalProvider(LocalIconTint provides colors.contentColor, content = content) }
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
    status(enabled)
        .clip(style.shape)
        .background(colors.backgroundColor, style.shape)
        .borderOrNot(style.border, style.shape)
        .then(then)
}

object Button {
    val colors: ButtonColors
        @Composable
        @ReadOnlyComposable
        get() = when (LocalInAreaBox.current) {
            true -> defaultButtonInBoxColors()
            else -> defaultButtonOutBoxColors()
        }
    val style: ButtonStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultButtonStyle()
}

object IconButton {
    val colors: ButtonColors
        @Composable
        @ReadOnlyComposable
        get() = defaultIconButtonColors()
    val style: ButtonStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultIconButtonStyle()
}

@Composable
@ReadOnlyComposable
private fun defaultButtonInBoxColors() = ButtonColors(
    rippleColor = LocalColors.current.themeSecondary,
    contentColor = LocalColors.current.textPrimary,
    backgroundColor = LocalColors.current.foregroundSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultButtonOutBoxColors() = ButtonColors(
    rippleColor = LocalColors.current.foregroundSecondary,
    contentColor = Color.White,
    backgroundColor = LocalColors.current.themePrimary
)

@Composable
@ReadOnlyComposable
private fun defaultIconButtonColors() = ButtonColors(
    rippleColor = LocalColors.current.themeSecondary,
    contentColor = LocalIconTint.current.orElse() ?: LocalColors.current.themePrimary,
    backgroundColor = Color.Transparent
)

@Composable
@ReadOnlyComposable
private fun defaultButtonStyle() = ButtonStyle(
    padding = PaddingValues(
        horizontal = LocalSizes.current.spacingPrimary,
        vertical = LocalSizes.current.spacingSecondary
    ),
    shape = when (LocalInAreaBox.current) {
        true -> LocalAreaBoxShape.current
        else -> LocalShapes.current.secondary
    },
    border = defaultButtonBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultIconButtonStyle() = ButtonStyle(
    padding = PaddingValues(),
    shape = LocalShapes.current.tertiary,
    border = defaultButtonBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultButtonBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)