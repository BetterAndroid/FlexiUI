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
 * This file is created by fankes on 2023/12/2.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.TypographyDescriptor
import com.highcapable.flexiui.component.interaction.rippleCombinedClickable
import com.highcapable.flexiui.resources.FlexiIcons
import com.highcapable.flexiui.resources.icon.ArrowForward
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape
import com.highcapable.flexiui.toTextStyle

/**
 * Colors defines for item box.
 * @see ItemBoxDefaults.colors
 */
@Immutable
data class ItemBoxColors(
    val backgroundColor: Color,
    val titleTextColor: Color,
    val subtitleTextColor: Color,
    val arrowIconTint: Color,
    val borderColor: Color
)

/**
 * Style defines for item box.
 * @see ItemBoxDefaults.style
 */
@Immutable
data class ItemBoxStyle(
    val contentSpacing: Dp,
    val titleTextStyle: TextStyle,
    val subtitleTextStyle: TextStyle,
    val shape: Shape,
    val borderWidth: Dp,
    val shadowSize: Dp
)

/**
 * Flexi UI horizontal item box.
 * @see VerticalItemBox
 * @param onClick the callback when item is clicked.
 * @param onLongClick the callback when item is long clicked.
 * @param modifier the [Modifier] to be applied to this item box.
 * @param colors the colors of item box, default is [ItemBoxDefaults.colors].
 * @param style the style of item box, default is [ItemBoxDefaults.style].
 * @param enabled whether this item box is enabled, default is true.
 * @param showArrowIcon whether show arrow icon, default is true.
 * @param interactionSource the interaction source of this item box.
 * @param logoImage the logo image of the [HorizontalItemBox], should typically be [Icon] or [Image].
 * @param title the title of the [HorizontalItemBox], should typically be [Text].
 * @param subtitle the subtitle of the [HorizontalItemBox], should typically be [Text].
 */
@Composable
fun HorizontalItemBox(
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    colors: ItemBoxColors = ItemBoxDefaults.colors(),
    style: ItemBoxStyle = ItemBoxDefaults.style(),
    enabled: Boolean = true,
    showArrowIcon: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    logoImage: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null
) {
    AreaBox(
        modifier = modifier.rippleCombinedClickable(
            enabled = enabled,
            interactionSource = interactionSource,
            onLongClick = onLongClick,
            onClick = onClick
        ),
        initializer = { componentState(enabled) },
        colors = AreaBoxDefaults.colors(
            backgroundColor = colors.backgroundColor,
            borderColor = colors.borderColor
        ),
        style = AreaBoxDefaults.style(
            shape = style.shape,
            borderWidth = style.borderWidth,
            shadowSize = style.shadowSize
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(style.contentSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            logoImage?.invoke()
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(style.contentSpacing / VerticalContentSpacingRatio)
            ) {
                ItemBoxContent(
                    colors = colors,
                    style = style,
                    title = title,
                    subtitle = subtitle
                )
            }
            if (showArrowIcon) Icon(
                imageVector = FlexiIcons.ArrowForward,
                style = IconDefaults.style(
                    size = DefaultArrowIconSize,
                    tint = colors.arrowIconTint
                )
            )
        }
    }
}

/**
 * Flexi UI vertical item box.
 * @see HorizontalItemBox
 * @param onClick the callback when item is clicked.
 * @param onLongClick the callback when item is long clicked.
 * @param modifier the [Modifier] to be applied to this item box.
 * @param colors the colors of item box, default is [ItemBoxDefaults.colors].
 * @param style the style of item box, default is [ItemBoxDefaults.style].
 * @param enabled whether this item box is enabled, default is true.
 * @param interactionSource the interaction source of this item box.
 * @param logoImage the logo image of the [VerticalItemBox], should typically be [Icon] or [Image].
 * @param title the title of the [VerticalItemBox], should typically be [Text].
 * @param subtitle the subtitle of the [VerticalItemBox], should typically be [Text].
 */
@Composable
fun VerticalItemBox(
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    colors: ItemBoxColors = ItemBoxDefaults.colors(),
    style: ItemBoxStyle = ItemBoxDefaults.style(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    logoImage: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null
) {
    AreaColumn(
        modifier = modifier.rippleCombinedClickable(
            enabled = enabled,
            interactionSource = interactionSource,
            onLongClick = onLongClick,
            onClick = onClick
        ),
        initializer = { componentState(enabled) },
        colors = AreaBoxDefaults.colors(
            backgroundColor = colors.backgroundColor,
            borderColor = colors.borderColor
        ),
        style = AreaBoxDefaults.style(
            shape = style.shape,
            borderWidth = style.borderWidth,
            shadowSize = style.shadowSize
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(style.contentSpacing / VerticalContentSpacingRatio)
    ) {
        logoImage?.invoke()
        ItemBoxContent(
            colors = colors,
            style = style,
            title = title,
            subtitle = subtitle
        )
    }
}

@Composable
private fun ItemBoxContent(
    colors: ItemBoxColors,
    style: ItemBoxStyle,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)?
) {
    CompositionLocalProvider(
        LocalTextStyle provides style.titleTextStyle.copy(color = colors.titleTextColor),
        content = title
    )
    subtitle?.also { content ->
        CompositionLocalProvider(
            LocalTextStyle provides style.subtitleTextStyle.copy(color = colors.subtitleTextColor),
            content = content
        )
    }
}

/**
 * Defaults of item box.
 */
object ItemBoxDefaults {

    /**
     * Creates a [ItemBoxColors] with the default values.
     * @param backgroundColor the background color.
     * @param titleTextColor the title text color.
     * @param subtitleTextColor the subtitle text color.
     * @param arrowIconTint the arrow icon tint.
     * @param borderColor the border color.
     * @return [ItemBoxColors]
     */
    @Composable
    fun colors(
        backgroundColor: Color = ItemBoxProperties.BackgroundColor.toColor(),
        titleTextColor: Color = ItemBoxProperties.TitleTextColor.toColor(),
        subtitleTextColor: Color = ItemBoxProperties.SubtitleTextColor.toColor(),
        arrowIconTint: Color = ItemBoxProperties.ArrowIconTint.toColor(),
        borderColor: Color = ItemBoxProperties.BorderColor.toColor()
    ) = ItemBoxColors(
        backgroundColor = backgroundColor,
        titleTextColor = titleTextColor,
        subtitleTextColor = subtitleTextColor,
        arrowIconTint = arrowIconTint,
        borderColor = borderColor
    )

    /**
     * Creates a [ItemBoxStyle] with the default values.
     * @param contentSpacing the spacing between the components of content.
     * @param titleTextStyle the title text style.
     * @param subtitleTextStyle the subtitle text style.
     * @param shape the shape.
     * @param borderWidth the border width.
     * @param shadowSize the shadow size.
     * @return [ItemBoxStyle]
     */
    @Composable
    fun style(
        contentSpacing: Dp = ItemBoxProperties.ContentSpacing.toDp(),
        titleTextStyle: TextStyle = ItemBoxProperties.TitleTextStyle.toTextStyle(),
        subtitleTextStyle: TextStyle = ItemBoxProperties.SubtitleTextStyle.toTextStyle(),
        shape: Shape = ItemBoxProperties.Shape.toShape(),
        borderWidth: Dp = ItemBoxProperties.BorderWidth.toDp(),
        shadowSize: Dp = ItemBoxProperties.ShadowSize
    ) = ItemBoxStyle(
        contentSpacing = contentSpacing,
        titleTextStyle = titleTextStyle,
        subtitleTextStyle = subtitleTextStyle,
        shape = shape,
        borderWidth = borderWidth,
        shadowSize = shadowSize
    )
}

@Stable
internal object ItemBoxProperties {
    val BackgroundColor = AreaBoxProperties.BackgroundColor
    val TitleTextColor = ColorsDescriptor.TextPrimary
    val SubtitleTextColor = ColorsDescriptor.TextSecondary
    val ArrowIconTint = ColorsDescriptor.TextSecondary
    val BorderColor = AreaBoxProperties.BorderColor
    val ContentSpacing = SizesDescriptor.SpacingSecondary
    val TitleTextStyle = TypographyDescriptor.Primary
    val SubtitleTextStyle = TypographyDescriptor.Secondary
    val Shape = AreaBoxProperties.Shape
    val BorderWidth = AreaBoxProperties.BorderWidth
    val ShadowSize = AreaBoxProperties.ShadowSize
}

private val DefaultArrowIconSize = 15.dp

private const val VerticalContentSpacingRatio = 1.6f