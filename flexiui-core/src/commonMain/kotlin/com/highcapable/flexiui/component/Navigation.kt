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
import com.highcapable.flexiui.LocalColors
import com.highcapable.flexiui.LocalSizes
import com.highcapable.flexiui.extension.orElse
import com.highcapable.flexiui.extension.status
import com.highcapable.flexiui.interaction.Interaction
import com.highcapable.flexiui.interaction.rippleClickable

@Immutable
data class NavigationColors(
    val indicatorColor: Color,
    val selectedContentColor: Color,
    val unselectedContentColor: Color
)

@Immutable
data class NavigationStyle(
    val padding: PaddingValues,
    val contentSpacing: Dp,
    val contentPadding: PaddingValues,
    val contentShape: Shape
)

@Composable
fun NavigationRow(
    modifier: Modifier = Modifier,
    colors: NavigationColors = Navigation.colors,
    style: NavigationStyle = Navigation.style,
    arrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable RowScope.() -> Unit
) {
    NavigationStyleBox(modifier, horizontal = true, colors, style) {
        Row(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun NavigationColumn(
    modifier: Modifier = Modifier,
    colors: NavigationColors = Navigation.colors,
    style: NavigationStyle = Navigation.style,
    arrangement: Arrangement.Vertical = Arrangement.SpaceBetween,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationStyleBox(modifier, horizontal = false, colors, style) {
        Column(
            modifier = Modifier.fillMaxWidth().selectableGroup(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = arrangement,
            content = content
        )
    }
}

@Composable
fun NavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    horizontal: Boolean? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: NavigationColors? = null,
    contentSpacing: Dp = Dp.Unspecified,
    contentPadding: PaddingValues? = null,
    contentShape: Shape? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: @Composable () -> Unit,
    text: @Composable (() -> Unit)? = null
) {
    val currentHorizontal = horizontal ?: LocalHorizontalNavigation.current
    val currentColors = colors ?: LocalNavigationColors.current ?: Navigation.colors
    val currentContentSpacing = contentSpacing.orElse() ?: LocalNavigationContentSpacing.current.orElse() ?: Navigation.style.contentSpacing
    val currentContentPadding = contentPadding ?: LocalNavigationContentPadding.current ?: Navigation.style.contentPadding
    val currentContentShape = contentShape ?: LocalNavigationContentShape.current ?: Navigation.style.contentShape
    val animatedIndicatorColor by animateColorAsState(if (selected) currentColors.indicatorColor else Color.Transparent)
    val animatedContentColor by animateColorAsState(if (selected) currentColors.selectedContentColor else currentColors.unselectedContentColor)
    val currentContentStyle = LocalTextStyle.current.copy(color = animatedContentColor)
    Box(
        modifier = Modifier.status(enabled)
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
            LocalIconTint provides animatedContentColor,
            LocalTextStyle provides currentContentStyle
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
                        Box(modifier = Modifier.height(currentContentSpacing / 2))
                        content()
                    }
                }
        }
    }
}

@Composable
private fun NavigationStyleBox(
    modifier: Modifier,
    horizontal: Boolean,
    colors: NavigationColors,
    style: NavigationStyle,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.padding(style.padding)) {
        CompositionLocalProvider(
            LocalHorizontalNavigation provides horizontal,
            LocalNavigationColors provides colors,
            LocalNavigationContentPadding provides style.contentPadding,
            LocalNavigationContentShape provides style.contentShape,
            content = content
        )
    }
}

object Navigation {
    val colors: NavigationColors
        @Composable
        @ReadOnlyComposable
        get() = defaultNavigationColors()
    val style: NavigationStyle
        @Composable
        @ReadOnlyComposable
        get() = defaultNavigationStyle()
}

private val LocalHorizontalNavigation = compositionLocalOf { true }

private val LocalNavigationColors = compositionLocalOf<NavigationColors?> { null }

private val LocalNavigationContentSpacing = compositionLocalOf { Dp.Unspecified }

private val LocalNavigationContentPadding = compositionLocalOf<PaddingValues?> { null }

private val LocalNavigationContentShape = compositionLocalOf<Shape?> { null }

@Composable
@ReadOnlyComposable
private fun defaultNavigationColors() = NavigationColors(
    indicatorColor = LocalColors.current.themeTertiary,
    selectedContentColor = LocalColors.current.themePrimary,
    unselectedContentColor = LocalColors.current.textSecondary
)

@Composable
@ReadOnlyComposable
private fun defaultNavigationStyle() = NavigationStyle(
    padding = when {
        LocalInSurface.current || LocalInAreaBox.current -> PaddingValues()
        else -> PaddingValues(
            horizontal = LocalSizes.current.spacingPrimary,
            vertical = LocalSizes.current.spacingSecondary
        )
    },
    contentSpacing = LocalSizes.current.spacingSecondary,
    contentPadding = PaddingValues(
        horizontal = LocalSizes.current.spacingPrimary,
        vertical = LocalSizes.current.spacingSecondary
    ),
    contentShape = withAreaBoxShape()
)