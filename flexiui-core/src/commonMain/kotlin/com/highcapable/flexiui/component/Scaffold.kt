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

@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    colors: SurfaceColors = Surface.colors,
    padding: ComponentPadding = Surface.padding,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    actionBar: @Composable () -> Unit = {},
    tab: @Composable () -> Unit = {},
    navigation: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val outBoxPadding = padding.copy(top = 0.dp)
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
            actionBar()
            tab()
            Box(modifier = Modifier.fillMaxSize().padding(inBoxPadding).weight(1f)) { content() }
            navigation()
        }
    }
}