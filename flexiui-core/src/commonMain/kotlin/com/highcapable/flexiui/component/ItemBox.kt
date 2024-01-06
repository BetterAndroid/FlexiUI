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
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.LocalTypography
import com.highcapable.flexiui.interaction.rippleClickable
import com.highcapable.flexiui.resources.Icons
import com.highcapable.flexiui.resources.icon.ArrowForward

/**
 * Colors defines for item box.
 * @param backgroundColor the background color.
 * @param titleTextColor the title text color.
 * @param subTextColor the sub text color.
 * @param arrowIconTint the arrow icon tint.
 */
@Immutable
data class ItemBoxColors(
    val backgroundColor: Color,
    val titleTextColor: Color,
    val subTextColor: Color,
    val arrowIconTint: Color
)

/**
 * Style defines for item box.
 * @param boxStyle the style of area box.
 * @param contentSpacing the spacing between the components of content.
 * @param titleTextStyle the title text style.
 * @param subTextStyle the sub text style.
 */
@Immutable
data class ItemBoxStyle(
    val boxStyle: AreaBoxStyle,
    val contentSpacing: Dp,
    val titleTextStyle: TextStyle,
    val subTextStyle: TextStyle
)

/**
 * Flexi UI horizontal item box.
 * @see VerticalItemBox
 * @param onClick the callback when item is clicked.
 * @param modifier the [Modifier] to be applied to this item box.
 * @param colors the colors of item box, default is [ItemBoxDefaults.colors].
 * @param style the style of item box, default is [ItemBoxDefaults.style].
 * @param enabled whether this item box is enabled, default is true.
 * @param showArrowIcon whether show arrow icon, default is true.
 * @param interactionSource the interaction source of this item box.
 * @param logoImage the logo image of the [HorizontalItemBox], should typically be [Icon] or [Image].
 * @param titleText the title text of the [HorizontalItemBox], should typically be [Text].
 * @param subText the sub text of the [HorizontalItemBox], should typically be [Text].
 */
@Composable
fun HorizontalItemBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ItemBoxColors = ItemBoxDefaults.colors,
    style: ItemBoxStyle = ItemBoxDefaults.style,
    enabled: Boolean = true,
    showArrowIcon: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    logoImage: @Composable (() -> Unit)? = null,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)? = null
) {
    AreaBox(
        modifier = modifier.rippleClickable(
            enabled = enabled,
            interactionSource = interactionSource,
            onClick = onClick
        ),
        initializer = { componentState(enabled) },
        color = colors.backgroundColor,
        style = style.boxStyle
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
                    titleText = titleText,
                    subText = subText
                )
            }
            if (showArrowIcon) Icon(
                imageVector = Icons.ArrowForward,
                style = IconDefaults.style.copy(
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
 * @param modifier the [Modifier] to be applied to this item box.
 * @param colors the colors of item box, default is [ItemBoxDefaults.colors].
 * @param style the style of item box, default is [ItemBoxDefaults.style].
 * @param enabled whether this item box is enabled, default is true.
 * @param interactionSource the interaction source of this item box.
 * @param logoImage the logo image of the [VerticalItemBox], should typically be [Icon] or [Image].
 * @param titleText the title text of the [VerticalItemBox], should typically be [Text].
 * @param subText the sub text of the [VerticalItemBox], should typically be [Text].
 */
@Composable
fun VerticalItemBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ItemBoxColors = ItemBoxDefaults.colors,
    style: ItemBoxStyle = ItemBoxDefaults.style,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    logoImage: @Composable (() -> Unit)? = null,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)? = null
) {
    AreaColumn(
        modifier = modifier.rippleClickable(
            enabled = enabled,
            interactionSource = interactionSource,
            onClick = onClick
        ),
        initializer = { componentState(enabled) },
        color = colors.backgroundColor,
        style = style.boxStyle,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(style.contentSpacing / VerticalContentSpacingRatio)
    ) {
        logoImage?.invoke()
        ItemBoxContent(
            colors = colors,
            style = style,
            titleText = titleText,
            subText = subText
        )
    }
}

@Composable
private fun ItemBoxContent(
    colors: ItemBoxColors,
    style: ItemBoxStyle,
    titleText: @Composable () -> Unit,
    subText: @Composable (() -> Unit)?
) {
    CompositionLocalProvider(
        LocalTextStyle provides style.titleTextStyle.copy(color = colors.titleTextColor),
        content = titleText
    )
    subText?.also { content ->
        CompositionLocalProvider(
            LocalTextStyle provides style.subTextStyle.copy(color = colors.subTextColor),
            content = content
        )
    }
}

/**
 * Defaults of item box.
 */
object ItemBoxDefaults {
    val colors: ItemBoxColors
        @Composable
        get() = defaultItemBoxColors()
    val style: ItemBoxStyle
        @Composable
        get() = defaultItemBoxStyle()
}

@Composable
@ReadOnlyComposable
private fun defaultItemBoxColors() = ItemBoxColors(
    backgroundColor = AreaBoxDefaults.color,
    titleTextColor = LocalColors.current.textPrimary,
    subTextColor = LocalColors.current.textSecondary,
    arrowIconTint = LocalColors.current.textSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultItemBoxStyle() = ItemBoxStyle(
    boxStyle = AreaBoxDefaults.style,
    contentSpacing = LocalSizes.current.spacingSecondary,
    titleTextStyle = LocalTypography.current.primary,
    subTextStyle = LocalTypography.current.secondary
)

private val DefaultArrowIconSize = 15.dp

private const val VerticalContentSpacingRatio = 1.6f