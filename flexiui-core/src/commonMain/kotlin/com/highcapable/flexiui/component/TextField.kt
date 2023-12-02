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
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.extension.borderOrNot
import com.highcapable.flexiui.extension.calculateEnd
import com.highcapable.flexiui.extension.calculateStart
import com.highcapable.flexiui.extension.orElse
import com.highcapable.flexiui.extension.solidColor
import com.highcapable.flexiui.extension.status
import com.highcapable.flexiui.resources.Icons
import com.highcapable.flexiui.resources.icon.Backspace
import com.highcapable.flexiui.resources.icon.ViewerClose
import com.highcapable.flexiui.resources.icon.ViewerOpen

@Immutable
data class TextFieldColors(
    val textColor: Color,
    val cursorColor: Color,
    val selectionColors: TextSelectionColors,
    val completionColors: AutoCompleteBoxColors,
    val placeholderContentColor: Color,
    val decorInactiveTint: Color,
    val decorActiveTint: Color,
    val borderInactiveColor: Color,
    val borderActiveColor: Color,
    val backgroundColor: Color
)

@Immutable
data class AutoCompleteBoxColors(
    val highlightContentColor: Color,
    val menuColors: DropdownMenuColors
)

@Immutable
data class TextFieldStyle(
    val textStyle: TextStyle,
    val padding: PaddingValues,
    val shape: Shape,
    val borderInactive: BorderStroke,
    val borderActive: BorderStroke,
    val completionStyle: DropdownMenuStyle
)

@Immutable
data class AutoCompleteOptions(
    val checkCase: Boolean = true,
    val checkStartSpace: Boolean = true,
    val checkEndSpace: Boolean = true,
    val threshold: Int = 2
)

@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    completionValues: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    completionOptions: AutoCompleteOptions = AutoCompleteOptions(),
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
    footer: @Composable (() -> Unit)? = null
) {
    val focused by interactionSource.collectIsFocusedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val keyEventFactory = TextFieldKeyEventFactory()
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
    val textColor = style.textStyle.color.orElse() ?: colors.textColor
    BoxWithConstraints(
        modifier = Modifier.textField(
            enabled = enabled,
            colors = colors,
            style = style,
            border = border,
            interactionSource = interactionSource,
            then = modifier
        ).pointerHoverState(TextFieldPointerState.Text)
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
                        modifier = Modifier.inflatable()
                            .focusRequester(focusRequester)
                            .onKeyEvent { keyEventFactory.onKeyEvent?.invoke(it) ?: false },
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = style.textStyle.copy(color = textColor),
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
                                placeholderContentColor = colors.placeholderContentColor,
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
        AutoCompleteTextFieldBox(
            value = value,
            onValueChange = onValueChange,
            completionValues = completionValues,
            completionOptions = completionOptions,
            completionColors = colors.completionColors,
            completionStyle = style.completionStyle,
            focusRequester = focusRequester,
            keyEventFactory = keyEventFactory,
            dropdownMenuWidth = if (needInflatable) maxWidth else Dp.Unspecified,
            textFieldAvailable = enabled && !readOnly && focused
        )
    }
}

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    completionValues: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    completionOptions: AutoCompleteOptions = AutoCompleteOptions(),
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
    footer: @Composable (() -> Unit)? = null
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    TextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
        },
        completionValues = completionValues,
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        completionOptions = completionOptions,
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
        footer = footer
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
    placeholder: @Composable () -> Unit = {}
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
                modifier = Modifier.width(TextDecorIconSize),
                contentAlignment = Alignment.Center
            ) {
                val animatedSize by animateDpAsState(if (value.text.isNotEmpty()) TextDecorIconSize else 0.dp)
                val cInteractionSource = remember { MutableInteractionSource() }
                val pressed by cInteractionSource.collectIsPressedAsState()
                if (pressed) focusRequester.requestFocus()
                if (value.text.isEmpty() && animatedSize == 0.dp) passwordVisible = defaultPasswordVisible
                IconToggleButton(
                    modifier = Modifier.size(animatedSize).pointerHoverState(TextFieldPointerState.Common),
                    style = IconButton.style.copy(padding = TextDecorIconPadding),
                    checked = passwordVisible,
                    onCheckedChange = {
                        passwordVisible = it
                        focusRequester.requestFocus()
                    },
                    enabled = enabled,
                    interactionSource = cInteractionSource
                ) { Icon(imageVector = if (passwordVisible) Icons.ViewerOpen else Icons.ViewerClose) }
            }
        }
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
    placeholder: @Composable () -> Unit = {}
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
        placeholder = placeholder
    )
}

@Composable
fun BackspaceTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    completionValues: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    completionOptions: AutoCompleteOptions = AutoCompleteOptions(),
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
    placeholder: @Composable () -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        completionValues = completionValues,
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        completionOptions = completionOptions,
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
                modifier = Modifier.width(TextDecorIconSize),
                contentAlignment = Alignment.Center
            ) {
                val animatedSize by animateDpAsState(if (value.text.isNotEmpty()) TextDecorIconSize else 0.dp)
                val cInteractionSource = remember { MutableInteractionSource() }
                val pressed by cInteractionSource.collectIsPressedAsState()
                if (pressed) focusRequester.requestFocus()
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
                    modifier = Modifier.width(animatedSize).pointerHoverState(TextFieldPointerState.Common),
                    style = IconButton.style.copy(padding = TextDecorIconPadding),
                    enabled = enabled,
                    interactionSource = cInteractionSource
                ) { Icon(imageVector = Icons.Backspace) }
            }
        }
    )
}

@Composable
fun BackspaceTextField(
    value: String,
    onValueChange: (String) -> Unit,
    completionValues: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextField.colors,
    style: TextFieldStyle = TextField.style,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    completionOptions: AutoCompleteOptions = AutoCompleteOptions(),
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
    placeholder: @Composable () -> Unit = {}
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    BackspaceTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange(it.text)
        },
        completionValues = completionValues,
        modifier = modifier,
        colors = colors,
        style = style,
        enabled = enabled,
        readOnly = readOnly,
        completionOptions = completionOptions,
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
        placeholder = placeholder
    )
}

@Composable
private fun AutoCompleteTextFieldBox(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    completionValues: List<String>,
    completionOptions: AutoCompleteOptions,
    completionColors: AutoCompleteBoxColors,
    completionStyle: DropdownMenuStyle,
    focusRequester: FocusRequester,
    keyEventFactory: TextFieldKeyEventFactory,
    dropdownMenuWidth: Dp,
    textFieldAvailable: Boolean
) {
    if (completionValues.isEmpty()) return
    // We need to use some "last" to remember the last using data,
    // because we need to mantain the animation state of the dropdown menu.
    // This allows the animation to finish playing due to the next composable event.
    var lastHandingModified by remember { mutableStateOf(false) }
    var lastMatchedValue by remember { mutableStateOf("") }
    var lastInputLength by remember { mutableStateOf(0) }
    var lastMatchedValues by remember { mutableStateOf(listOf<String>()) }
    var selection by remember { mutableStateOf(-1) }
    val inputText = value.text.let {
        var currentText = it
        when {
            !completionOptions.checkStartSpace -> currentText = currentText.trimStart()
            !completionOptions.checkEndSpace -> currentText = currentText.trimEnd()
        }; currentText
    }
    val hasInput = inputText.isNotEmpty()
    val matchedValues = completionValues.filter {
        if (inputText.length >= completionOptions.threshold)
            if (completionOptions.checkCase)
                it.startsWith(inputText)
            else it.lowercase().startsWith(inputText.lowercase())
        else false
    }.sortedBy { it.length }
    if (matchedValues.isNotEmpty() && !lastHandingModified) {
        lastMatchedValues = matchedValues
        lastInputLength = inputText.length
    }
    val matchText = if (completionOptions.checkCase)
        lastMatchedValue != inputText
    else lastMatchedValue.lowercase() != inputText.lowercase()
    val expanded = hasInput && matchedValues.isNotEmpty() && matchText
    // As long as it is expanded, reset the lastHandingModified, lastMatchedValue.
    if (expanded) {
        lastHandingModified = false
        lastMatchedValue = ""
    } else selection = -1
    fun collapse() {
        lastMatchedValue = inputText
    }
    fun selectAndCollapse(position: Int) {
        if (position < 0) return
        val newValue = TextFieldValue(matchedValues[position], TextRange(matchedValues[position].length))
        lastHandingModified = true
        lastMatchedValue = matchedValues[position]
        onValueChange(newValue)
        focusRequester.requestFocus()
    }
    keyEventFactory.onKeyEvent = {
        var release = true
        if (it.type == KeyEventType.KeyUp) when (it.key) {
            Key.Escape -> collapse()
            Key.DirectionUp -> {
                if (selection > 0)
                    selection--
                else selection = matchedValues.lastIndex
            }
            Key.DirectionDown -> {
                if (selection < matchedValues.lastIndex)
                    selection++
                else selection = 0
            }
            Key.DirectionRight, Key.Enter -> selectAndCollapse(selection)
            else -> release = false
        } else release = false
        release
    }
    // Clearly, if the text field is not available,
    // the dropdown menu should not be displayed when reavailable.
    if (!textFieldAvailable && matchedValues.isNotEmpty()) collapse()
    DropdownMenu(
        expanded = expanded && textFieldAvailable,
        onDismissRequest = {},
        modifier = dropdownMenuWidth.orElse()?.let { Modifier.width(it) } ?: Modifier.width(IntrinsicSize.Max),
        colors = completionColors.menuColors,
        style = completionStyle,
        properties = PopupProperties(focusable = false)
    ) {
        lastMatchedValues.forEachIndexed { index, matchedValue ->
            DropdownMenuItem(
                onClick = { selectAndCollapse(index) },
                actived = selection == index
            ) {
                Text(buildAnnotatedString {
                    append(matchedValue)
                    addStyle(
                        style = SpanStyle(color = completionColors.highlightContentColor, fontWeight = FontWeight.Bold),
                        start = 0,
                        end = lastInputLength
                    )
                })
            }
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
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(tint = decorTint),
            LocalTextStyle provides LocalTextStyle.current.copy(color = decorTint),
            content = content
        )
    }
}

@Composable
private fun TextFieldDecorationBox(
    value: String,
    placeholderContentColor: Color,
    placeholder: @Composable () -> Unit,
    innerTextField: @Composable () -> Unit
) {
    val animatedAlpha by animateFloatAsState(if (value.isNotEmpty()) 0f else 1f)
    Box(modifier = Modifier.alpha(animatedAlpha)) {
        CompositionLocalProvider(
            LocalIconStyle provides LocalIconStyle.current.copy(tint = placeholderContentColor),
            LocalTextStyle provides LocalTextStyle.current.copy(color = placeholderContentColor),
            content = placeholder
        )
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
internal enum class TextFieldPointerState { Common, Text }

@Stable
private class TextFieldKeyEventFactory {
    var onKeyEvent: ((KeyEvent) -> Boolean)? = null
}

private fun Modifier.textField(
    enabled: Boolean,
    colors: TextFieldColors,
    style: TextFieldStyle,
    border: BorderStroke,
    interactionSource: MutableInteractionSource,
    then: Modifier
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "textField"
        properties["enabled"] = enabled
        properties["colors"] = colors
        properties["style"] = style
        properties["border"] = border
    }
) {
    status(enabled)
        .focusable(enabled, interactionSource)
        .hoverable(interactionSource, enabled)
        .clip(style.shape)
        .background(colors.backgroundColor, style.shape)
        .borderOrNot(border, style.shape)
        .then(then)
}

private fun Modifier.textFieldPadding(
    style: TextFieldStyle,
    fitStart: Boolean = false,
    fitEnd: Boolean = false
) = composed {
    when {
        !fitStart && !fitEnd -> padding(style.padding)
        fitStart -> padding(start = style.padding.calculateStart())
        fitEnd -> padding(end = style.padding.calculateEnd())
        else -> this
    }
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
}

@Composable
@ReadOnlyComposable
private fun defaultTextFieldColors() = TextFieldColors(
    textColor = defaultTextColor(),
    cursorColor = LocalColors.current.themePrimary,
    selectionColors = TextSelectionColors(
        handleColor = LocalColors.current.themePrimary,
        backgroundColor = LocalColors.current.themeSecondary
    ),
    completionColors = AutoCompleteBoxColors(
        highlightContentColor = LocalColors.current.themePrimary,
        menuColors = DropdownMenu.colors
    ),
    placeholderContentColor = LocalColors.current.textSecondary,
    decorInactiveTint = LocalColors.current.themeSecondary,
    decorActiveTint = LocalColors.current.themePrimary,
    borderInactiveColor = LocalColors.current.themeSecondary,
    borderActiveColor = LocalColors.current.themePrimary,
    backgroundColor = Color.Transparent
)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldStyle() = TextFieldStyle(
    textStyle = LocalTextStyle.current,
    padding = PaddingValues(LocalSizes.current.spacingSecondary),
    shape = withAreaBoxShape(),
    borderInactive = defaultTextFieldInactiveBorder(),
    borderActive = defaultTextFieldActiveBorder(),
    completionStyle = DropdownMenu.style
)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldInactiveBorder() = BorderStroke(LocalSizes.current.borderSizeSecondary, LocalColors.current.themeSecondary)

@Composable
@ReadOnlyComposable
private fun defaultTextFieldActiveBorder() = BorderStroke(LocalSizes.current.borderSizePrimary, LocalColors.current.themePrimary)

private val TextDecorIconSize = 24.dp
private val TextDecorIconPadding = PaddingValues(2.dp)