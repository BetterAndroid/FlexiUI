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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.borderOrNot
import com.highcapable.flexiui.utils.orElse
import com.highcapable.flexiui.utils.solidColor
import com.highcapable.flexiui.utils.status

// TODO: Preset text boxes (password text box, text box with delete button, auto complete text box, etc.)

@Immutable
data class TextFieldColors(
    val cursorColor: Color,
    val selectionColors: TextSelectionColors,
    val decorInactiveTint: Color,
    val decorActiveTint: Color,
    val borderInactiveColor: Color,
    val borderActiveColor: Color,
    val backgroundColor: Color
)

@Immutable
data class TextFieldStyle(
    val padding: Dp,
    val topPadding: Dp,
    val startPadding: Dp,
    val bottomPadding: Dp,
    val endPadding: Dp,
    val shape: Shape,
    val borderInactive: BorderStroke,
    val borderActive: BorderStroke
)

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable (() -> Unit)? = null,
    placeholder: @Composable () -> Unit = {},
    footer: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = TextField.textStyle
) {
    val focused by interactionSource.collectIsFocusedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val animatedDecorTint by animateColorAsState(when {
        focused || hovered -> colors.decorActiveTint
        else -> colors.decorInactiveTint
    })
    val animatedBorderColor by animateColorAsState(when {
        focused || hovered -> style.borderActive.solidColor
        else -> style.borderInactive.solidColor
    })
    val animatedBorderWidth by animateDpAsState(when {
        focused -> style.borderActive.width
        else -> style.borderInactive.width
    })
    val border = when {
        focused || hovered -> style.borderInactive
        else -> style.borderInactive
    }.copy(animatedBorderWidth, SolidColor(animatedBorderColor))
    Row(
        modifier = Modifier.textField(
            colors = colors,
            style = style,
            border = border,
            enabled = enabled,
            interactionSource = interactionSource,
            modifier = modifier
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        header?.also {
            InnerDecorationBox(
                decorTint = animatedDecorTint,
                style = style,
                header = true,
                content = it
            )
        }
        Box(modifier = Modifier.weight(1f, fill = false).textFieldPadding(style)) {
            TextFieldStyle(colors) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.focusRequester(focusRequester).then(modifier),
                    enabled = enabled,
                    readOnly = readOnly,
                    textStyle = textStyle,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    minLines = minLines,
                    visualTransformation = visualTransformation,
                    onTextLayout = onTextLayout,
                    interactionSource = interactionSource,
                    cursorBrush = SolidColor(colors.cursorColor),
                    decorationBox = {
                        TextFieldDecorationBox(
                            value = value,
                            placeholder = placeholder,
                            innerTextField = it
                        )
                    }
                )
            }
        }
        footer?.also {
            InnerDecorationBox(
                decorTint = animatedDecorTint,
                style = style,
                footer = true,
                content = it
            )
        }
    }
}

@Composable
private fun InnerDecorationBox(
    decorTint: Color,
    style: TextFieldStyle,
    header: Boolean = false,
    footer: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.textFieldPadding(style, fitStart = header, fitEnd = footer)) {
        CompositionLocalProvider(LocalIconTint provides decorTint) {
            content()
        }
    }
}

@Composable
private fun TextFieldDecorationBox(
    value: String,
    placeholder: @Composable () -> Unit,
    innerTextField: @Composable () -> Unit
) {
    val animatedPlaceholder by animateFloatAsState(if (value.isNotEmpty()) 0f else 1f)
    Box(modifier = Modifier.alpha(animatedPlaceholder)) {
        CompositionLocalProvider(
            LocalTextStyle provides LocalTextStyle.current.default(LocalColors.current.textSecondary)
        ) { placeholder() }
    }
    innerTextField()
}

@Composable
private fun TextFieldStyle(colors: TextFieldColors, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalTextSelectionColors provides colors.selectionColors) {
        content()
    }
}

private fun Modifier.textField(
    colors: TextFieldColors,
    style: TextFieldStyle,
    border: BorderStroke,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier
) = status(enabled)
    .focusable(enabled, interactionSource)
    .hoverable(interactionSource, enabled)
    .clip(style.shape)
    .background(colors.backgroundColor, style.shape)
    .borderOrNot(border, style.shape)
    .then(modifier)

private fun Modifier.textFieldPadding(
    style: TextFieldStyle,
    fitStart: Boolean = false,
    fitEnd: Boolean = false
) = when {
    !fitStart && !fitEnd -> padding(
        top = style.topPadding.orElse() ?: style.padding,
        start = style.startPadding.orElse() ?: style.padding,
        bottom = style.bottomPadding.orElse() ?: style.padding,
        end = style.endPadding.orElse() ?: style.padding
    )
    fitStart -> padding(start = style.startPadding.orElse() ?: style.padding)
    fitEnd -> padding(end = style.endPadding.orElse() ?: style.padding)
    else -> this
}

object TextField {
    val colors: TextFieldColors
        @Composable
        @ReadOnlyComposable
        get() = defaultTextFieldColors()
    val style: TextFieldStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultTextFieldStyle()
    val textStyle: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalTextStyle.current.default(LocalColors.current.textPrimary)
}

@Composable
@ReadOnlyComposable
private fun defaultTextFieldColors() = TextFieldColors(
    cursorColor = LocalColors.current.themePrimary,
    selectionColors = TextSelectionColors(
        handleColor = LocalColors.current.themePrimary,
        backgroundColor = LocalColors.current.themeSecondary
    ),
    decorInactiveTint = LocalColors.current.themeSecondary,
    decorActiveTint = LocalColors.current.themePrimary,
    borderInactiveColor = LocalColors.current.themeSecondary,
    borderActiveColor = LocalColors.current.themePrimary,
    backgroundColor = Color.Transparent
)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldStyle() = TextFieldStyle(
    padding = LocalSizes.current.spacingSecondary,
    topPadding = Dp.Unspecified,
    startPadding = Dp.Unspecified,
    bottomPadding = Dp.Unspecified,
    endPadding = Dp.Unspecified,
    shape = when (LocalInAreaBox.current) {
        true -> LocalAreaBoxShape.current
        else -> LocalShapes.current.secondary
    },
    borderInactive = defaultTextFieldInactiveBorder(),
    borderActive = defaultTextFieldActiveBorder()
)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldInactiveBorder() = BorderStroke(LocalSizes.current.borderSizeSecondary, LocalColors.current.themeSecondary)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldActiveBorder() = BorderStroke(LocalSizes.current.borderSizePrimary, LocalColors.current.themePrimary)