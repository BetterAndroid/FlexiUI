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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.borderOrNot
import com.highcapable.flexiui.utils.orElse

@Immutable
data class ButtonColors(
    val fillColor: Color,
    val contentColor: Color,
    val backgroundColor: Color
)

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = Dp.Unspecified,
    topPadding: Dp = Button.topPadding,
    startPadding: Dp = Button.startPadding,
    bottomPadding: Dp = Button.bottomPadding,
    endPadding: Dp = Button.endPadding,
    shape: Shape = Button.shape,
    border: BorderStroke = Button.border,
    colors: ButtonColors = Button.colors,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    var sModifier = modifier.clip(shape = shape)
    sModifier = if (enabled) sModifier.clickable(
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        indication = rememberRipple(color = colors.fillColor),
        interactionSource = interactionSource
    ) else sModifier.alpha(0.5f)
    sModifier = sModifier.background(color = colors.backgroundColor, shape = shape)
    sModifier = sModifier.borderOrNot(border = border, shape = shape)
    val localTextStyle = LocalTextStyle.current.copy(color = colors.contentColor)
    val localProgressIndicatorColors = LocalProgressIndicatorColors.current.copy(
        foregroundColor = colors.contentColor,
        backgroundColor = Color.Transparent
    )
    Box(modifier = sModifier) {
        CompositionLocalProvider(
            LocalTextStyle provides localTextStyle,
            LocalProgressIndicatorColors provides localProgressIndicatorColors
        ) {
            Row(
                Modifier.padding(
                    top = topPadding.orElse() ?: padding,
                    start = startPadding.orElse() ?: padding,
                    bottom = bottomPadding.orElse() ?: padding,
                    end = endPadding.orElse() ?: padding
                )
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
    fillColor: Color = Button.colors.fillColor,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            onClick = onClick,
            enabled = enabled,
            role = Role.Button,
            interactionSource = interactionSource,
            indication = rememberRipple(bounded = false, color = fillColor)
        ),
        contentAlignment = Alignment.Center,
    ) { content() }
}

@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    fillColor: Color = Button.colors.fillColor,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.toggleable(
            value = checked,
            onValueChange = onCheckedChange,
            enabled = enabled,
            role = Role.Checkbox,
            interactionSource = interactionSource,
            indication = rememberRipple(bounded = false, color = fillColor)
        ),
        contentAlignment = Alignment.Center
    ) { content() }
}

object Button {
    val topPadding: Dp
        @Composable
        @ReadOnlyComposable
        get() = defalutButtonPaddings()[0]
    val startPadding: Dp
        @Composable
        @ReadOnlyComposable
        get() = defalutButtonPaddings()[1]
    val bottomPadding: Dp
        @Composable
        @ReadOnlyComposable
        get() = defalutButtonPaddings()[2]
    val endPadding: Dp
        @Composable
        @ReadOnlyComposable
        get() = defalutButtonPaddings()[3]
    val shape: Shape
        @Composable
        @ReadOnlyComposable
        get() = when (LocalInAreaBox.current) {
            true -> LocalAreaBoxShape.current
            else -> defaultButtonShape()
        }
    val border: BorderStroke
        @Composable
        @ReadOnlyComposable
        get() = defaultButtonBorder()
    val colors: ButtonColors
        @Composable
        @ReadOnlyComposable
        get() = when (LocalInAreaBox.current) {
            true -> defaultButtonInBoxColors()
            else -> defaultButtonOutBoxColors()
        }
}

@Composable
@ReadOnlyComposable
private fun defalutButtonPaddings() = arrayOf(
    LocalSizes.current.spacingSecondary,
    LocalSizes.current.spacingPrimary,
    LocalSizes.current.spacingSecondary,
    LocalSizes.current.spacingPrimary
)

@Composable
@ReadOnlyComposable
private fun defaultButtonBorder() = BorderStroke(LocalSizes.current.borderSizeTertiary, LocalColors.current.textPrimary)

@Composable
@ReadOnlyComposable
private fun defaultButtonShape() = LocalShapes.current.tertiary

@Composable
@ReadOnlyComposable
private fun defaultButtonInBoxColors() = ButtonColors(
    fillColor = LocalColors.current.themeSecondary,
    contentColor = LocalColors.current.textPrimary,
    backgroundColor = LocalColors.current.foregroundSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultButtonOutBoxColors() = ButtonColors(
    fillColor = LocalColors.current.foregroundSecondary,
    contentColor = Color.White,
    backgroundColor = LocalColors.current.themePrimary
)