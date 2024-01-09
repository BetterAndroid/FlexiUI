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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding

// TODO: re-made it by SubcomposeLayout.

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
 * @param padding the padding of content, default is [SurfaceDefaults.padding].
 * @param verticalArrangement the vertical arrangement of content, default is [Arrangement.Top].
 * @param horizontalAlignment the horizontal alignment of content, default is [Alignment.Start].
 * @param appBar the app bar on top of the screen, should typically be [PrimaryAppBar] or [SecondaryAppBar].
 * @param tab the tab below the app bar, should typically be [TabRow].
 * @param navigationBar the navigation bar on bottom of the screen, should typically be [NavigationBarRow] or [NavigationBarColumn].
 * @param content the content of the screen.
 */
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    colors: SurfaceColors = SurfaceDefaults.colors(),
    padding: ComponentPadding = SurfaceDefaults.padding,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    appBar: @Composable () -> Unit = {},
    tab: @Composable () -> Unit = {},
    navigationBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    // When out of the box, we no need to match the top padding, it should be provided by the action bar.
    val outBoxPadding = padding.copy(top = 0.dp)
    // When in the box, we no need to match the start and end padding, it should be provided by the surface.
    val inBoxPadding = padding.copy(start = 0.dp, end = 0.dp)
    Surface(
        modifier = modifier,
        colors = colors,
        padding = outBoxPadding
    ) {
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            appBar()
            tab()
            Box(modifier = Modifier.fillMaxSize().padding(inBoxPadding).weight(1f)) { content() }
            navigationBar()
        }
    }
}