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
 * This file is created by fankes on 2024/1/12.
 */
@file:OptIn(ExperimentalFoundationApi::class)

package com.highcapable.flexiui.demo.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.highcapable.betterandroid.compose.multiplatform.backpress.BackHandler
import com.highcapable.flexiui.component.Icon
import com.highcapable.flexiui.component.Scaffold
import com.highcapable.flexiui.component.SecondaryAppBar
import com.highcapable.flexiui.component.Tab
import com.highcapable.flexiui.component.TabRow
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.demo.DeleteForever
import com.highcapable.flexiui.demo.ListAdd
import com.highcapable.flexiui.demo.rememberRouter
import com.highcapable.flexiui.resources.FlexiIcons
import kotlinx.coroutines.launch

@Composable
fun LazyListScreen() {
    val router = rememberRouter()
    val pageCount = 2
    val state = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    Scaffold(
        appBar = {
            SecondaryAppBar(
                title = { Text("Lazy List Demo") },
                subtitle = { Text("0 items of list data", singleLine = true) },
                navigationIcon = {
                    NavigationIconButton(onClick = { router.goHome() })
                },
                actions = {
                    ActionIconButton(onClick = { /* TODO */ }) {
                        Icon(FlexiIcons.ListAdd)
                    }
                    ActionIconButton(onClick = { /* TODO */ }) {
                        Icon(FlexiIcons.DeleteForever)
                    }
                }
            )
        },
        tab = {
            TabRow(
                selectedTabIndex = state.currentPage,
                indicator = {
                    TabIndicator(modifier = Modifier.pagerTabIndicatorOffset(state))
                }
            ) {
                Tab(
                    selected = state.currentPage == 0,
                    onClick = { scope.launch { state.animateScrollToPage(0) } }
                ) { Text("Linear List") }
                Tab(
                    selected = state.currentPage == 1,
                    onClick = { scope.launch { state.animateScrollToPage(1) } }
                ) { Text("Grid List") }
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = state,
        ) { index ->
            // TODO: To be implemented.
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("Page ${index + 1}. To be implemented.") }
        }
        BackHandler { router.goHome() }
    }
}