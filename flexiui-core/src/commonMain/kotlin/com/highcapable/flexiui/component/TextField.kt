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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalShapes
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.resources.Icons
import com.highcapable.flexiui.resources.icon.Backspace
import com.highcapable.flexiui.resources.icon.ViewerClose
import com.highcapable.flexiui.resources.icon.ViewerOpen
import com.highcapable.flexiui.utils.borderOrNot
import com.highcapable.flexiui.utils.orElse
import com.highcapable.flexiui.utils.solidColor
import com.highcapable.flexiui.utils.status

// TODO: auto complete text box (possible a few long time later)

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
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
    BoxWithConstraints(
        modifier = Modifier.textField(
            colors = colors,
            style = style,
            border = border,
            enabled = enabled,
            interactionSource = interactionSource,
            modifier = modifier
        ).pointerHoverState(TextFieldPointerState.TEXT)
    ) {
        // Note: If minWidth is not 0, a constant width is currently set.
        //       At this time, the child layout must be completely filled into the parent layout.
        val needInflatable = minWidth > 0.dp
        fun Modifier.inflatable() = if (needInflatable) fillMaxWidth() else this
        Row(verticalAlignment = Alignment.CenterVertically) {
            header?.also {
                InnerDecorationBox(
                    decorTint = animatedDecorTint,
                    style = style,
                    header = true,
                    content = it
                )
            }
            Box(modifier = Modifier.weight(1f, needInflatable).textFieldPadding(style)) {
                TextFieldStyle(colors) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.focusRequester(focusRequester).inflatable(),
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
                                value = value.text,
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
}

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
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    TextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
        },
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        focusRequester = focusRequester,
        interactionSource = interactionSource,
        header = header,
        placeholder = placeholder,
        footer = footer,
        textStyle = textStyle
    )
}

@Composable
fun PasswordTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    defaultPasswordVisible: Boolean = false,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    normalVisualTransformation: VisualTransformation = VisualTransformation.None,
    secretVisualTransformation: VisualTransformation = PasswordVisualTransformation(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable (() -> Unit)? = null,
    placeholder: @Composable () -> Unit = {},
    textStyle: TextStyle = TextField.textStyle
) {
    var passwordVisible by remember { mutableStateOf(defaultPasswordVisible) }
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        visualTransformation = if (passwordVisible)
            normalVisualTransformation
        else secretVisualTransformation,
        onTextLayout = onTextLayout,
        focusRequester = focusRequester,
        interactionSource = interactionSource,
        header = header,
        placeholder = placeholder,
        footer = {
            Box(
                modifier = Modifier.width(DefaultDecorIconSize),
                contentAlignment = Alignment.Center
            ) {
                val animatedSize by animateDpAsState(if (value.text.isNotEmpty()) DefaultDecorIconSize else 0.dp)
                if (value.text.isEmpty() && animatedSize == 0.dp) passwordVisible = defaultPasswordVisible
                IconToggleButton(
                    modifier = Modifier.size(animatedSize).pointerHoverState(TextFieldPointerState.NORMAL),
                    style = IconButton.style.copy(padding = DefaultDecorIconPadding),
                    checked = passwordVisible,
                    onCheckedChange = {
                        passwordVisible = it
                        focusRequester.requestFocus()
                    },
                    enabled = enabled
                ) { Icon(imageVector = if (passwordVisible) Icons.ViewerOpen else Icons.ViewerClose) }
            }
        },
        textStyle = textStyle
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    defaultPasswordVisible: Boolean = false,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    normalVisualTransformation: VisualTransformation = VisualTransformation.None,
    secretVisualTransformation: VisualTransformation = PasswordVisualTransformation(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    header: @Composable (() -> Unit)? = null,
    placeholder: @Composable () -> Unit = {},
    textStyle: TextStyle = TextField.textStyle
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    PasswordTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
        },
        defaultPasswordVisible = defaultPasswordVisible,
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        normalVisualTransformation = normalVisualTransformation,
        secretVisualTransformation = secretVisualTransformation,
        onTextLayout = onTextLayout,
        focusRequester = focusRequester,
        interactionSource = interactionSource,
        header = header,
        placeholder = placeholder,
        textStyle = textStyle
    )
}

@Composable
fun BackspaceTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
    textStyle: TextStyle = TextField.textStyle
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        focusRequester = focusRequester,
        interactionSource = interactionSource,
        header = header,
        placeholder = placeholder,
        footer = {
            Box(
                modifier = Modifier.width(DefaultDecorIconSize),
                contentAlignment = Alignment.Center
            ) {
                val animatedSize by animateDpAsState(if (value.text.isNotEmpty()) DefaultDecorIconSize else 0.dp)
                IconButton(
                    onClick = {
                        val cursorPosition = value.selection.start
                        if (cursorPosition > 0) {
                            val newText = value.text.removeRange(cursorPosition - 1, cursorPosition)
                            val newValue = TextFieldValue(newText, TextRange(cursorPosition - 1))
                            onValueChange(newValue)
                        }
                        focusRequester.requestFocus()
                    },
                    modifier = Modifier.width(animatedSize).pointerHoverState(TextFieldPointerState.NORMAL),
                    style = IconButton.style.copy(padding = DefaultDecorIconPadding),
                    enabled = enabled
                ) { Icon(imageVector = Icons.Backspace) }
            }
        },
        textStyle = textStyle
    )
}

@Composable
fun BackspaceTextField(
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
    textStyle: TextStyle = TextField.textStyle
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    BackspaceTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
        },
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        focusRequester = focusRequester,
        interactionSource = interactionSource,
        header = header,
        placeholder = placeholder,
        textStyle = textStyle
    )
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
    val animatedAlpha by animateFloatAsState(if (value.isNotEmpty()) 0f else 1f)
    Box(modifier = Modifier.alpha(animatedAlpha)) {
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

internal expect fun Modifier.pointerHoverState(state: TextFieldPointerState): Modifier

@Stable
internal enum class TextFieldPointerState { NORMAL, TEXT }

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

private val DefaultDecorIconSize = 24.dp
private val DefaultDecorIconPadding = 2.dp