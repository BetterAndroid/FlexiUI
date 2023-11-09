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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.utils.borderOrNot
import com.highcapable.flexiui.utils.orElse

// TODO: Preset text boxes (password text box, text box with delete button, etc.)

@Immutable
data class TextFieldColors(
    val cursorColor: Color,
    val selectionColors: TextSelectionColors,
    val borderInactiveColor: Color,
    val borderActiveColor: Color,
    val backgroundColor: Color
)

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = TextField.padding,
    topPadding: Dp = Dp.Unspecified,
    startPadding: Dp = Dp.Unspecified,
    bottomPadding: Dp = Dp.Unspecified,
    endPadding: Dp = Dp.Unspecified,
    shape: Shape = TextField.shape,
    borderInactive: BorderStroke = TextField.borderInactive,
    borderActive: BorderStroke = TextField.borderActive,
    colors: TextFieldColors = TextField.colors,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    style: TextStyle = TextField.style
) {
    TextFieldStyle(colors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = style,
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
                    modifier = modifier,
                    padding = padding,
                    topPadding = topPadding,
                    startPadding = startPadding,
                    bottomPadding = bottomPadding,
                    endPadding = endPadding,
                    shape = shape,
                    borderInactive = borderInactive,
                    borderActive = borderActive,
                    colors = colors,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    header = header,
                    placeholder = placeholder,
                    footer = footer,
                    innerTextField = it
                )
            }
        )
    }
}

@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = TextField.padding,
    topPadding: Dp = Dp.Unspecified,
    startPadding: Dp = Dp.Unspecified,
    bottomPadding: Dp = Dp.Unspecified,
    endPadding: Dp = Dp.Unspecified,
    shape: Shape = TextField.shape,
    borderInactive: BorderStroke = TextField.borderInactive,
    borderActive: BorderStroke = TextField.borderActive,
    colors: TextFieldColors = TextField.colors,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    style: TextStyle = TextField.style
) {
    TextFieldStyle(colors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = style,
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
                    value = value.text,
                    modifier = modifier,
                    padding = padding,
                    topPadding = topPadding,
                    startPadding = startPadding,
                    bottomPadding = bottomPadding,
                    endPadding = endPadding,
                    shape = shape,
                    borderInactive = borderInactive,
                    borderActive = borderActive,
                    colors = colors,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    header = header,
                    placeholder = placeholder,
                    footer = footer,
                    innerTextField = it
                )
            }
        )
    }
}

@Composable
private fun TextFieldStyle(colors: TextFieldColors, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalTextSelectionColors provides colors.selectionColors) {
        content()
    }
}

@Composable
private fun TextFieldDecorationBox(
    value: String,
    modifier: Modifier,
    padding: Dp,
    topPadding: Dp,
    startPadding: Dp,
    bottomPadding: Dp,
    endPadding: Dp,
    shape: Shape,
    borderInactive: BorderStroke,
    borderActive: BorderStroke,
    colors: TextFieldColors,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    header: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    footer: @Composable () -> Unit,
    innerTextField: @Composable () -> Unit
) {
    val focused by interactionSource.collectIsFocusedAsState()
    val border = if (focused) borderActive else borderInactive
    Box(
        modifier.textField(
            padding = padding,
            topPadding = topPadding,
            startPadding = startPadding,
            bottomPadding = bottomPadding,
            endPadding = endPadding,
            shape = shape,
            border = border,
            colors = colors,
            enabled = enabled
        )
    ) {
        val placeholderAlpha by animateFloatAsState(if (value.isNotEmpty()) 0f else 1f)
        Row {
            header()
            Box {
                Box(modifier = Modifier.alpha(placeholderAlpha)) {
                    CompositionLocalProvider(
                        LocalTextStyle provides LocalTextStyle.current.copy(color = LocalColors.current.textSecondary)
                    ) { placeholder() }
                }
                innerTextField()
            }
            footer()
        }
    }
}

private fun Modifier.textField(
    padding: Dp,
    topPadding: Dp,
    startPadding: Dp,
    bottomPadding: Dp,
    endPadding: Dp,
    shape: Shape,
    border: BorderStroke,
    colors: TextFieldColors,
    enabled: Boolean
): Modifier {
    var sModifier = clip(shape = shape)
        .background(color = colors.backgroundColor, shape = shape)
        .borderOrNot(border, shape)
        .padding(
            top = topPadding.orElse() ?: padding,
            start = startPadding.orElse() ?: padding,
            bottom = bottomPadding.orElse() ?: padding,
            end = endPadding.orElse() ?: padding
        )
    if (!enabled) sModifier = sModifier.alpha(0.5f)
    return sModifier
}

object TextField {
    val padding: Dp
        @Composable
        @ReadOnlyComposable
        get() = LocalSizes.current.spacingSecondary
    val shape: Shape
        @Composable
        @ReadOnlyComposable
        get() = when (LocalInAreaBox.current) {
            true -> LocalAreaBoxShape.current
            else -> LocalShapes.current.primary
        }
    val borderInactive: BorderStroke
        @Composable
        @ReadOnlyComposable
        get() = defaultTextFieldInActiveBorder()
    val borderActive: BorderStroke
        @Composable
        @ReadOnlyComposable
        get() = defaultTextFieldActiveBorder()
    val colors: TextFieldColors
        @Composable
        @ReadOnlyComposable
        get() = defaultTextFieldColors()
    val style: TextStyle
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
    borderInactiveColor = LocalColors.current.themeSecondary,
    borderActiveColor = LocalColors.current.themePrimary,
    backgroundColor = Color.Transparent
)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldInActiveBorder() = BorderStroke(LocalSizes.current.borderSizeSecondary, LocalColors.current.themeSecondary)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldActiveBorder() = BorderStroke(LocalSizes.current.borderSizePrimary, LocalColors.current.themePrimary)