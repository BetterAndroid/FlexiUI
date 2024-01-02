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

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.DefaultTypography
import com.highcapable.flexiui.LocalColors

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = Text.style,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = !singleLine || maxLines > 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        text = AnnotatedString(text),
        modifier = modifier,
        color = color,
        style = style,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = emptyMap(),
        onTextLayout = onTextLayout
    )
}

@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = Text.style,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = !singleLine || maxLines > 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    val currentColor = color.orNull() ?: style.color.orNull() ?: Text.color
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(color = currentColor),
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent
    )
}

object Text {
    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = defaultTextColor()
    val style: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalTextStyle.current
}

@Composable
@ReadOnlyComposable
internal fun defaultTextColor() = LocalColors.current.textPrimary

internal val LocalTextStyle = compositionLocalOf { DefaultTextStyle }

private val DefaultTextStyle = DefaultTypography.primary