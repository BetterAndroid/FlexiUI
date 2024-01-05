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
import androidx.compose.runtime.ReadOnlyComposable
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
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.interaction.Interaction
import com.highcapable.flexiui.interaction.rippleClickable

@Immutable
data class NavigationBarColors(
    val backgroundColor: Color,
    val indicatorColor: Color,
    val selectedContentColor: Color,
    val unselectedContentColor: Color
)

@Immutable
data class NavigationBarStyle(
    val boxStyle: AreaBoxStyle,
    val contentSpacing: Dp,
    val contentPadding: ComponentPadding,
    val contentShape: Shape
)

@Composable
fun NavigationRow(
    modifier: Modifier = Modifier,
    colors: NavigationBarColors = NavigationBar.colors,
    style: NavigationBarStyle = NavigationBar.style,
    arrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBarStyleBox(modifier, horizontal = true, colors, style) {
        AreaRow(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            color = colors.backgroundColor,
            style = style.boxStyle,
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun NavigationColumn(
    modifier: Modifier = Modifier,
    colors: NavigationBarColors = NavigationBar.colors,
    style: NavigationBarStyle = NavigationBar.style,
    arrangement: Arrangement.Vertical = Arrangement.SpaceBetween,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationBarStyleBox(modifier, horizontal = false, colors, style) {
        AreaColumn(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            color = colors.backgroundColor,
            style = style.boxStyle,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = arrangement,
            content = content
        )
    }
}

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
    val currentColors = colors ?: LocalNavigationBarColors.current ?: NavigationBar.colors
    val currentContentSpacing = contentSpacing.orNull() ?: LocalNavigationBarContentSpacing.current.orNull() ?: NavigationBar.style.contentSpacing
    val currentContentPadding = contentPadding ?: LocalNavigationBarContentPadding.current ?: NavigationBar.style.contentPadding
    val currentContentShape = contentShape ?: LocalNavigationBarContentShape.current ?: NavigationBar.style.contentShape
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
                rippleStyle = Interaction.rippleStyle.copy(color = currentColors.indicatorColor),
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

object NavigationBar {
    val colors: NavigationBarColors
        @Composable
        @ReadOnlyComposable
        get() = defaultNavigationBarColors()
    val style: NavigationBarStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultNavigationBarStyle()
}

private val LocalHorizontalNavigationBar = compositionLocalOf { true }

private val LocalNavigationBarColors = compositionLocalOf<NavigationBarColors?> { null }

private val LocalNavigationBarContentSpacing = compositionLocalOf { Dp.Unspecified }

private val LocalNavigationBarContentPadding = compositionLocalOf<PaddingValues?> { null }

private val LocalNavigationBarContentShape = compositionLocalOf<Shape?> { null }

@Composable
@ReadOnlyComposable
private fun defaultNavigationBarColors() = NavigationBarColors(
    backgroundColor = AreaBox.color,
    indicatorColor = LocalColors.current.themeTertiary,
    selectedContentColor = LocalColors.current.themePrimary,
    unselectedContentColor = LocalColors.current.textSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultNavigationBarStyle() = NavigationBarStyle(
    boxStyle = AreaBox.style,
    contentSpacing = LocalSizes.current.spacingSecondary,
    contentPadding = ComponentPadding(
        horizontal = LocalSizes.current.spacingPrimary,
        vertical = LocalSizes.current.spacingSecondary
    ),
    contentShape = withAreaBoxShape()
)

private const val VerticalContentSpacingRatio = 1.6f