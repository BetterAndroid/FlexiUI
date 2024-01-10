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
 * This file is created by fankes on 2023/11/6.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding

/**
 * Scaffold implements the basic Flexi UI visual layout structure.
 *
 * You can add your own components into [appBar], [tab], [navigationBar].
 * @see PrimaryAppBar
 * @see SecondaryAppBar
 * @see TabRow
 * @see NavigationBarRow
 * @see NavigationBarColumn
 * @param modifier the [Modifier] to be applied to content.
 * @param colors the colors of content, default is [SurfaceDefaults.colors].
 * @param padding the padding of content, default is [ScaffoldDefaults.padding].
 * @param appBar the app bar on top of the screen, should typically be [PrimaryAppBar] or [SecondaryAppBar].
 * @param tab the tab below the app bar, should typically be [TabRow].
 * @param navigationBar the navigation bar on bottom of the screen, should typically be [NavigationBarRow] or [NavigationBarColumn].
 * @param contentWindowInsets the window insets of content, default is [ScaffoldDefaults.contentWindowInsets].
 * @param content the content of the screen. The lambda receives a [ComponentPadding] that should be applied to the content root via
 * [Modifier.padding], if using Modifier.verticalScroll, apply this modifier to the child of the scroll, and not on the scroll itself.
 */
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    colors: SurfaceColors = SurfaceDefaults.colors(),
    padding: ComponentPadding = ScaffoldDefaults.padding,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    appBar: @Composable () -> Unit = {},
    tab: @Composable () -> Unit = {},
    navigationBar: @Composable () -> Unit = {},
    content: @Composable (innerPadding: ComponentPadding) -> Unit
) {
    Surface(modifier = modifier, colors = colors, padding = ComponentPadding()) {
        ScaffoldLayout(
            padding = padding,
            contentWindowInsets = contentWindowInsets,
            appBar = appBar,
            tab = tab,
            navigationBar = navigationBar,
            content = content
        )
    }
}

@Composable
private fun ScaffoldLayout(
    padding: ComponentPadding,
    contentWindowInsets: WindowInsets,
    appBar: @Composable () -> Unit,
    tab: @Composable () -> Unit,
    navigationBar: @Composable () -> Unit,
    content: @Composable (innerPadding: ComponentPadding) -> Unit
) {
    val density = LocalDensity.current
    SubcomposeLayout(modifier = Modifier.windowInsetsPadding(contentWindowInsets)) { baseConstraints ->
        var currentY = 0
        var navigationBarHeight = 0
        var appBarPlaceables = subcompose(Unit, appBar).map { it.measure(baseConstraints) }
        // Top insets padding is override by [appBar].
        val topPadding = with(density) { if (appBarPlaceables.isNotEmpty()) 0 else padding.top.roundToPx() }
        val horizontalPadding = with(density) { padding.horizontal.roundToPx() }
        val bottomPadding = with(density) { padding.bottom.roundToPx() }
        val constraints = baseConstraints.copy(
            maxHeight = baseConstraints.maxHeight - topPadding - bottomPadding,
            maxWidth = baseConstraints.maxWidth - horizontalPadding
        )
        // Re-measure [appBar] with new constraints.
        appBarPlaceables = subcompose(ScaffoldSlots.AppBar, appBar).map { it.measure(constraints) }
        val tabPlaceables = subcompose(ScaffoldSlots.Tab, tab).map { it.measure(constraints) }
        val navigationBarPlaceables = subcompose(ScaffoldSlots.NavigationBar, navigationBar).map { it.measure(constraints) }
        // Inner content no need start and end padding.
        val innerPadding = padding.copy(
            start = 0.dp,
            top = if (tabPlaceables.isNotEmpty()) padding.top else 0.dp,
            end = 0.dp,
            bottom = if (navigationBarPlaceables.isNotEmpty()) padding.bottom else 0.dp
        )
        // Measure [appBar], [tab] and [navigationBar] height.
        appBarPlaceables.forEach { currentY += it.height }
        tabPlaceables.forEach { currentY += it.height }
        navigationBarPlaceables.forEach { navigationBarHeight += it.height }
        // Measure content with [navigationBar] height.
        val contentConstraints = constraints.copy(
            // The maxHeight of content must be >= minHeight, if not will coerce to minHeight.
            maxHeight = (constraints.maxHeight - currentY - navigationBarHeight).coerceAtLeast(constraints.minHeight)
        )
        val contentPlaceables = subcompose(ScaffoldSlots.Content) { content(innerPadding) }.map { it.measure(contentConstraints) }
        layout(constraints.maxWidth, constraints.maxHeight) {
            val placementX = horizontalPadding / 2
            var placementY = 0
            appBarPlaceables.forEach {
                it.placeRelative(placementX, placementY)
                placementY += it.height
            }
            tabPlaceables.forEach {
                it.placeRelative(placementX, placementY)
                placementY += it.height
            }
            contentPlaceables.forEach {
                it.placeRelative(placementX, placementY)
                placementY += it.height
            }
            var navigationBarY = constraints.maxHeight
            navigationBarPlaceables.forEach {
                navigationBarY -= it.height
                it.placeRelative(placementX, navigationBarY)
            }
        }
    }
}

@Stable
private enum class ScaffoldSlots { AppBar, Tab, NavigationBar, Content }

/**
 * Defaults of scaffold.
 */
object ScaffoldDefaults {

    /**
     * Returns the default padding of scaffold.
     * @return [ComponentPadding]
     */
    val padding: ComponentPadding
        @Composable
        @ReadOnlyComposable
        get() = SurfaceDefaults.padding

    /**
     * Returns the default content window insets of scaffold.
     * @return [WindowInsets]
     */
    val contentWindowInsets: WindowInsets
        @Composable
        get() = WindowInsets.safeDrawing
}