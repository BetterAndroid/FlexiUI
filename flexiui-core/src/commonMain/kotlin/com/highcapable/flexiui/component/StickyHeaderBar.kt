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
 * This file is created by fankes on 2024/1/14.
 */
@file:Suppress("unused")
package com.highcapable.flexiui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.ShapesDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.TypographyDescriptor
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import com.highcapable.flexiui.toTextStyle

/**
 * Colors defines for sticky header bar.
 * @see StickyHeaderBarDefaults.colors
 */
@Immutable
data class StickyHeaderBarColors(
    val iconTint: Color,
    val iconBackgroundColor: Color,
    val textColor: Color
)

/**
 * Style defines for sticky header bar.
 * @see StickyHeaderBarDefaults.style
 */
@Immutable
data class StickyHeaderBarStyle(
    val textStyle: TextStyle,
    val iconSize: Dp,
    val iconPadding: ComponentPadding,
    val iconShape: Shape,
    val contentSpacing: Dp
)

/**
 * Flexi UI sticky header bar.
 * @param modifier the [Modifier] to be applied to this bar.
 * @param colors the colors of this bar, default is [StickyHeaderBarDefaults.colors].
 * @param style the style of this bar, default is [StickyHeaderBarDefaults.style].
 * @param icon the icon of the [StickyHeaderBar], should typically be [Icon].
 * @param title the title of the [StickyHeaderBar], should typically be [Text].
 */
@Composable
fun StickyHeaderBar(
    modifier: Modifier = Modifier,
    colors: StickyHeaderBarColors = StickyHeaderBarDefaults.colors(),
    style: StickyHeaderBarStyle = StickyHeaderBarDefaults.style(),
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconStyle = IconDefaults.style(size = style.iconSize, tint = colors.iconTint)
        val textStyle = style.textStyle.copy(color = colors.textColor)
        Box(
            modifier = Modifier.clip(style.iconShape)
                .background(colors.iconBackgroundColor)
                .padding(style.iconPadding)
        ) {
            CompositionLocalProvider(
                LocalIconStyle provides iconStyle,
                content = icon
            )
        }
        Spacer(modifier = Modifier.width(style.contentSpacing))
        CompositionLocalProvider(
            LocalTextStyle provides textStyle,
            content = title
        )
    }
}

/**
 * Defaults of sticky header bar.
 */
object StickyHeaderBarDefaults {

    /**
     * Creates a [StickyHeaderBarColors] with the default values.
     * @param iconTint the icon tint.
     * @param iconBackgroundColor the icon background color.
     * @param textColor the text color.
     * @return [StickyHeaderBarColors]
     */
    @Composable
    fun colors(
        iconTint: Color = StickyHeaderBarProperties.IconTint,
        iconBackgroundColor: Color = StickyHeaderBarProperties.IconBackgroundColor.toColor(),
        textColor: Color = StickyHeaderBarProperties.TextColor.toColor()
    ) = StickyHeaderBarColors(
        iconTint = iconTint,
        iconBackgroundColor = iconBackgroundColor,
        textColor = textColor
    )

    /**
     * Creates a [StickyHeaderBarStyle] with the default values.
     * @param textStyle the text style.
     * @param iconSize the icon size.
     * @param iconPadding the icon padding.
     * @param iconShape the icon shape.
     * @param contentSpacing the content spacing.
     * @return [StickyHeaderBarStyle]
     */
    @Composable
    fun style(
        textStyle: TextStyle = StickyHeaderBarProperties.TextStyle.toTextStyle(),
        iconSize: Dp = StickyHeaderBarProperties.IconSize,
        iconPadding: ComponentPadding = StickyHeaderBarProperties.IconPadding,
        iconShape: Shape = StickyHeaderBarProperties.IconShape.toShape(),
        contentSpacing: Dp = StickyHeaderBarProperties.ContentSpacing.toDp()
    ) = StickyHeaderBarStyle(
        textStyle = textStyle,
        iconSize = iconSize,
        iconPadding = iconPadding,
        iconShape = iconShape,
        contentSpacing = contentSpacing
    )
}

@Stable
internal object StickyHeaderBarProperties {
    val IconTint = Color.White
    val IconBackgroundColor = ColorsDescriptor.ThemePrimary
    val TextColor = ColorsDescriptor.TextSecondary
    val TextStyle = TypographyDescriptor.Secondary
    val IconSize = 10.dp
    val IconPadding = ComponentPadding(3.dp)
    val IconShape = ShapesDescriptor.Tertiary
    val ContentSpacing = SizesDescriptor.SpacingSecondary
}