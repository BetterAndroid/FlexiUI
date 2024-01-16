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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.DefaultTypography
import com.highcapable.flexiui.toColor

/**
 * Flexi UI basic text.
 * @see BasicText
 * @param text the text to be displayed.
 * @param modifier the [Modifier] to be applied to this text.
 * @param color the color of the text, see [TextStyle.color].
 * @param style the style of the text.
 * @param fontSize the font size of the text, see [TextStyle.fontSize].
 * @param fontStyle the font style of the text, see [TextStyle.fontStyle].
 * @param fontWeight the font weight of the text, see [TextStyle.fontWeight].
 * @param fontFamily the font family of the text, see [TextStyle.fontFamily].
 * @param letterSpacing the letter spacing of the text, see [TextStyle.letterSpacing].
 * @param textDecoration the text decoration of the text, see [TextStyle.textDecoration].
 * @param textAlign the text alignment of the text, see [TextStyle.textAlign].
 * @param lineHeight the line height of the text, see [TextStyle.lineHeight].
 * @param singleLine whether the text should be displayed on a single line, default is false.
 * @param maxLines the maximum number of lines to display, when [singleLine] is false default is [Int.MAX_VALUE].
 * @param minLines the minimum number of lines to display, default is 1.
 * @param overflow the overflow strategy for displaying the text, default is [TextOverflow.Ellipsis].
 * @param softWrap whether the text should break at soft line breaks.
 * @param onTextLayout the callback to be invoked when the text layout is ready.
 */
@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
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
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = emptyMap(),
        onTextLayout = onTextLayout
    )
}

/**
 * Flexi UI basic text.
 * @see BasicText
 * @param text the text to be displayed.
 * @param modifier the [Modifier] to be applied to this text.
 * @param color the color of the text, see [TextStyle.color].
 * @param style the style of the text.
 * @param fontSize the font size of the text, see [TextStyle.fontSize].
 * @param fontStyle the font style of the text, see [TextStyle.fontStyle].
 * @param fontWeight the font weight of the text, see [TextStyle.fontWeight].
 * @param fontFamily the font family of the text, see [TextStyle.fontFamily].
 * @param letterSpacing the letter spacing of the text, see [TextStyle.letterSpacing].
 * @param textDecoration the text decoration of the text, see [TextStyle.textDecoration].
 * @param textAlign the text alignment of the text, see [TextStyle.textAlign].
 * @param lineHeight the line height of the text, see [TextStyle.lineHeight].
 * @param singleLine whether the text should be displayed on a single line, default is false.
 * @param maxLines the maximum number of lines to display, when [singleLine] is false default is [Int.MAX_VALUE].
 * @param minLines the minimum number of lines to display, default is 1.
 * @param overflow the overflow strategy for displaying the text, default is [TextOverflow.Ellipsis].
 * @param softWrap whether the text should break at soft line breaks.
 * @param inlineContent map of tags to [InlineTextContent]s that can be used to add composable content to the text.
 * @param onTextLayout the callback to be invoked when the text layout is ready.
 */
@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = !singleLine || maxLines > 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    val currentStyle = style ?: LocalTextStyle.current
    val currentColor = color.orNull() ?: currentStyle.color.orNull() ?: TextProperties.Color.toColor()
    BasicText(
        text = text,
        modifier = modifier,
        style = currentStyle.copy(color = currentColor).merge(
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight
        ),
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent
    )
}

@Stable
internal object TextProperties {
    val Color = ColorsDescriptor.TextPrimary
}

/**
 * Composition local containing the preferred [TextStyle]
 * that will be used by [Text] by default.
 */
val LocalTextStyle = compositionLocalOf { DefaultTextStyle }

/**
 * This function is used to set the current value of [LocalTextStyle], merging the given style
 * with the current style values for any missing attributes. Any [Text] components included in
 * this component's [content] will be styled with this style unless styled explicitly.
 * @see LocalTextStyle
 * @param value the merged text style to set.
 * @param content the composable content.
 */
@Composable
fun ProvideTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}

private val DefaultTextStyle = DefaultTypography.primary