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
 * This file is created by fankes on 2023/11/9.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.extension.ui.componentState
import com.highcapable.betterandroid.compose.extension.ui.orNull
import com.highcapable.flexiui.ColorsDescriptor
import com.highcapable.flexiui.PaddingDescriptor
import com.highcapable.flexiui.SizesDescriptor
import com.highcapable.flexiui.component.interaction.InteractionDefaults
import com.highcapable.flexiui.component.interaction.rippleClickable
import com.highcapable.flexiui.toColor
import com.highcapable.flexiui.toDp
import com.highcapable.flexiui.toShape

/**
 * Colors defines for navigation bar.
 * @see NavigationBarDefaults.colors
 */
@Immutable
data class NavigationBarColors(
    val backgroundColor: Color,
    val borderColor: Color,
    val indicatorColor: Color,
    val selectedContentColor: Color,
    val unselectedContentColor: Color
)

/**
 * Style defines for navigation bar.
 * @see NavigationBarDefaults.style
 */
@Immutable
data class NavigationBarStyle(
    val padding: ComponentPadding,
    val shape: Shape,
    val borderWidth: Dp,
    val shadowSize: Dp,
    val contentSpacing: Dp,
    val contentPadding: ComponentPadding,
    val contentShape: Shape
)

/**
 * Flexi UI horizontal navigation bar.
 * @see NavigationBarColumn
 * @see NavigationBarItem
 * @param modifier the [Modifier] to be applied to this navigation bar.
 * @param colors the colors of this navigation bar, default is [NavigationBarDefaults.colors].
 * @param style the style of this navigation bar, default is [NavigationBarDefaults.style].
 * @param arrangement the horizontal arrangement of this navigation bar, default is [Arrangement.SpaceBetween].
 * @param content the content of the [NavigationBarRow], should typically be [NavigationBarItem].
 */
@Composable
fun NavigationBarRow(
    modifier: Modifier = Modifier,
    colors: NavigationBarColors = NavigationBarDefaults.colors(),
    style: NavigationBarStyle = NavigationBarDefaults.style(),
    arrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBarStyleBox(modifier, horizontal = true, colors, style) {
        AreaRow(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            colors = AreaBoxDefaults.colors(
                backgroundColor = colors.backgroundColor,
                borderColor = colors.borderColor
            ),
            style = AreaBoxDefaults.style(
                padding = style.padding,
                shape = style.shape,
                borderWidth = style.borderWidth,
                shadowSize = style.shadowSize
            ),
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

/**
 * Flexi UI vertical navigation bar.
 * @see NavigationBarRow
 * @see NavigationBarItem
 * @param modifier the [Modifier] to be applied to this navigation bar.
 * @param colors the colors of this navigation bar, default is [NavigationBarDefaults.colors].
 * @param style the style of this navigation bar, default is [NavigationBarDefaults.style].
 * @param arrangement the vertical arrangement of this navigation bar, default is [Arrangement.SpaceBetween].
 * @param content the content of the [NavigationBarColumn], should typically be [NavigationBarItem].
 */
@Composable
fun NavigationBarColumn(
    modifier: Modifier = Modifier,
    colors: NavigationBarColors = NavigationBarDefaults.colors(),
    style: NavigationBarStyle = NavigationBarDefaults.style(),
    arrangement: Arrangement.Vertical = Arrangement.SpaceBetween,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationBarStyleBox(modifier, horizontal = false, colors, style) {
        AreaColumn(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            colors = AreaBoxDefaults.colors(
                backgroundColor = colors.backgroundColor,
                borderColor = colors.borderColor
            ),
            style = AreaBoxDefaults.style(
                padding = style.padding,
                shape = style.shape,
                borderWidth = style.borderWidth,
                shadowSize = style.shadowSize
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = arrangement,
            content = content
        )
    }
}

/**
 * Flexi UI navigation bar item.
 * @see NavigationBarRow
 * @see NavigationBarColumn
 * @param selected whether this item is selected.
 * @param onClick the callback when item is clicked.
 * @param horizontal whether this item is horizontal.
 * @param modifier the [Modifier] to be applied to this item.
 * @param enabled whether this item is enabled, default is true.
 * @param colors the colors of this item.
 * @param contentSpacing the spacing between the components of content of this item.
 * @param contentPadding the padding of content of this item.
 * @param contentShape the content shape of this item.
 * @param interactionSource the interaction source of this item.
 * @param icon the icon of the [NavigationBarItem], should typically be [Icon].
 * @param text the text of the [NavigationBarItem], should typically be [Text].
 */
@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    horizontal: Boolean? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: NavigationBarColors? = null,
    contentSpacing: Dp = Dp.Unspecified,
    contentPadding: PaddingValues? = null,
    contentShape: Shape? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable () -> Unit,
    text: @Composable (() -> Unit)? = null
) {
    val currentHorizontal = horizontal ?: LocalHorizontalNavigationBar.current
    val currentColors = colors ?: LocalNavigationBarColors.current ?: NavigationBarDefaults.colors()
    val currentContentSpacing = contentSpacing.orNull()
        ?: LocalNavigationBarContentSpacing.current.orNull()
        ?: NavigationBarDefaults.style().contentSpacing
    val currentContentPadding = contentPadding
        ?: LocalNavigationBarContentPadding.current
        ?: NavigationBarDefaults.style().contentPadding
    val currentContentShape = contentShape
        ?: LocalNavigationBarContentShape.current
        ?: NavigationBarDefaults.style().contentShape
    val animatedIndicatorColor by animateColorAsState(if (selected) currentColors.indicatorColor else Color.Transparent)
    val animatedContentColor by animateColorAsState(if (selected) currentColors.selectedContentColor else currentColors.unselectedContentColor)
    val currentIconStyle = LocalIconStyle.current.copy(tint = animatedContentColor)
    val currentTextStyle = LocalTextStyle.current.copy(color = animatedContentColor)
    Box(
        modifier = Modifier.componentState(enabled)
            .clip(currentContentShape)
            .then(modifier)
            .background(animatedIndicatorColor)
            .rippleClickable(
                rippleStyle = InteractionDefaults.rippleStyle(color = currentColors.indicatorColor),
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                onClick = onClick
            )
            .padding(currentContentPadding)
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides currentIconStyle,
            LocalTextStyle provides currentTextStyle
        ) {
            if (currentHorizontal)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon()
                    text?.also { content ->
                        AnimatedVisibility(
                            visible = selected,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            Row {
                                Box(modifier = Modifier.width(currentContentSpacing))
                                content()
                            }
                        }
                    }
                }
            else
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    icon()
                    text?.also { content ->
                        Box(modifier = Modifier.height(currentContentSpacing / VerticalContentSpacingRatio))
                        content()
                    }
                }
        }
    }
}

@Composable
private fun NavigationBarStyleBox(
    modifier: Modifier,
    horizontal: Boolean,
    colors: NavigationBarColors,
    style: NavigationBarStyle,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        CompositionLocalProvider(
            LocalHorizontalNavigationBar provides horizontal,
            LocalNavigationBarColors provides colors,
            LocalNavigationBarContentPadding provides style.contentPadding,
            LocalNavigationBarContentShape provides style.contentShape,
            content = content
        )
    }
}

/**
 * Defaults of navigation bar.
 */
object NavigationBarDefaults {

    /**
     * Creates a [NavigationBarColors] with the default values.
     * @param backgroundColor the background color.
     * @param borderColor the border color.
     * @param indicatorColor the indicator color.
     * @param selectedContentColor the selected content color.
     * @param unselectedContentColor the unselected content color.
     * @return [NavigationBarColors]
     */
    @Composable
    fun colors(
        backgroundColor: Color = NavigationBarProperties.BackgroundColor.toColor(),
        borderColor: Color = NavigationBarProperties.BorderColor.toColor(),
        indicatorColor: Color = NavigationBarProperties.IndicatorColor.toColor(),
        selectedContentColor: Color = NavigationBarProperties.SelectedContentColor.toColor(),
        unselectedContentColor: Color = NavigationBarProperties.UnselectedContentColor.toColor()
    ) = NavigationBarColors(
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        indicatorColor = indicatorColor,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor
    )

    /**
     * Creates a [NavigationBarStyle] with the default values.
     * @param padding the padding.
     * @param shape the shape.
     * @param borderWidth the border width.
     * @param shadowSize the shadow size.
     * @param contentSpacing the spacing between the components of content.
     * @param contentPadding the padding of content.
     * @param contentShape the content shape.
     * @return [NavigationBarStyle]
     */
    @Composable
    fun style(
        padding: ComponentPadding = NavigationBarProperties.Padding.toPadding(),
        shape: Shape = NavigationBarProperties.Shape.toShape(),
        borderWidth: Dp = NavigationBarProperties.BorderWidth.toDp(),
        shadowSize: Dp = NavigationBarProperties.ShadowSize,
        contentSpacing: Dp = NavigationBarProperties.ContentSpacing.toDp(),
        contentPadding: ComponentPadding = NavigationBarProperties.ContentPadding.toPadding(),
        contentShape: Shape = NavigationBarProperties.Shape.toShape()
    ) = NavigationBarStyle(
        padding = padding,
        shape = shape,
        borderWidth = borderWidth,
        shadowSize = shadowSize,
        contentSpacing = contentSpacing,
        contentPadding = contentPadding,
        contentShape = contentShape
    )
}

@Stable
internal object NavigationBarProperties {
    val BackgroundColor = AreaBoxProperties.BackgroundColor
    val BorderColor = AreaBoxProperties.BorderColor
    val IndicatorColor = ColorsDescriptor.ThemeTertiary
    val SelectedContentColor = ColorsDescriptor.ThemePrimary
    val UnselectedContentColor = ColorsDescriptor.TextSecondary
    val Padding = AreaBoxProperties.Padding
    val Shape = AreaBoxProperties.Shape
    val BorderWidth = AreaBoxProperties.BorderWidth
    val ShadowSize = AreaBoxProperties.ShadowSize
    val ContentSpacing = SizesDescriptor.SpacingPrimary
    val ContentPadding = PaddingDescriptor(
        horizontal = SizesDescriptor.SpacingPrimary,
        vertical = SizesDescriptor.SpacingSecondary
    )
}

private val LocalHorizontalNavigationBar = compositionLocalOf { true }
private val LocalNavigationBarColors = compositionLocalOf<NavigationBarColors?> { null }

private val LocalNavigationBarContentSpacing = compositionLocalOf { Dp.Unspecified }
private val LocalNavigationBarContentPadding = compositionLocalOf<ComponentPadding?> { null }
private val LocalNavigationBarContentShape = compositionLocalOf<Shape?> { null }

private const val VerticalContentSpacingRatio = 1.6f