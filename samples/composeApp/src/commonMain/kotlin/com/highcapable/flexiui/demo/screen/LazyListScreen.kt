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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.highcapable.betterandroid.compose.extension.ui.HapticFeedback
import com.highcapable.betterandroid.compose.multiplatform.backpress.BackHandler
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.DropdownMenu
import com.highcapable.flexiui.component.DropdownMenuItem
import com.highcapable.flexiui.component.HorizontalItemBox
import com.highcapable.flexiui.component.Icon
import com.highcapable.flexiui.component.Scaffold
import com.highcapable.flexiui.component.SecondaryAppBar
import com.highcapable.flexiui.component.Tab
import com.highcapable.flexiui.component.TabRow
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.component.window.FlexiDialog
import com.highcapable.flexiui.demo.Delete
import com.highcapable.flexiui.demo.DeleteForever
import com.highcapable.flexiui.demo.ListAdd
import com.highcapable.flexiui.demo.PrimarySpacer
import com.highcapable.flexiui.demo.bean.SimpleListBean
import com.highcapable.flexiui.demo.rememberRouter
import com.highcapable.flexiui.demo.strings
import com.highcapable.flexiui.resources.FlexiIcons
import kotlinx.coroutines.launch

@Composable
fun LazyListScreen() {
    val router = rememberRouter()
    val pageCount = 2
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val testListData = remember {
        mutableStateListOf<SimpleListBean>().apply {
            for (i in 1..5) add(SimpleListBean.create(i))
        }
    }
    Scaffold(
        appBar = {
            var showRemoveAllDialog by remember { mutableStateOf(false) }
            FlexiDialog(
                visible = showRemoveAllDialog,
                onDismissRequest = { showRemoveAllDialog = false },
                title = { Text(strings.listRemoveAll) },
                content = { Text(strings.listRemoveAllDescription) },
                confirmButton = {
                    Button(
                        onClick = {
                            showRemoveAllDialog = false
                            testListData.clear()
                        }
                    ) { Text(strings.comfirm) }
                },
                cancelButton = {
                    Button(
                        onClick = { showRemoveAllDialog = false }
                    ) { Text(strings.cancel) }
                }
            )
            SecondaryAppBar(
                title = { Text(strings.lazyListDemo) },
                subtitle = { Text(strings.itemsListDataCount(testListData.size), singleLine = true) },
                navigationIcon = {
                    NavigationIconButton(onClick = { router.goHome() })
                },
                actions = {
                    ActionIconButton(
                        onClick = {
                            val lastIndex = if (testListData.isNotEmpty())
                                testListData[testListData.lastIndex].index + 1
                            else 1
                            testListData.add(SimpleListBean.create(lastIndex))
                            scope.launch {
                                lazyListState.animateScrollToItem(testListData.lastIndex)
                                lazyGridState.animateScrollToItem(testListData.lastIndex)
                            }
                        }
                    ) { Icon(FlexiIcons.ListAdd) }
                    ActionIconButton(
                        onClick = { showRemoveAllDialog = testListData.isNotEmpty() }
                    ) { Icon(FlexiIcons.DeleteForever) }
                }
            )
            BackHandler { router.goHome() }
        },
        tab = {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    TabIndicator(modifier = Modifier.pagerTabIndicatorOffset(pagerState))
                }
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                ) { Text(strings.linearList) }
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                ) { Text(strings.gridList) }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            beyondBoundsPageCount = pageCount
        ) { index ->
            @Composable
            fun LazyItem(modifier: Modifier, index: Int) {
                Box(modifier = modifier) {
                    var showDropdownMenu by remember { mutableStateOf(false) }
                    HorizontalItemBox(
                        onLongClick = HapticFeedback { showDropdownMenu = true },
                        title = { Text(testListData[index].title) },
                        subtitle = { Text(testListData[index].subtitle) },
                        showArrowIcon = false
                    )
                    DropdownMenu(
                        expanded = showDropdownMenu,
                        onDismissRequest = { showDropdownMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = { testListData.removeAt(index) }
                        ) {
                            Icon(FlexiIcons.Delete)
                            PrimarySpacer()
                            Text(strings.listRemoveSingle)
                        }
                    }
                }
            }
            if (testListData.isNotEmpty()) when (index) {
                0 -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    state = lazyListState,
                    verticalArrangement = ListSpacing
                ) {
                    items(
                        count = testListData.size,
                        key = { testListData[it].index }
                    ) { index ->
                        LazyItem(
                            modifier = Modifier.animateItemPlacement(),
                            index = index
                        )
                    }
                }
                1 -> LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    state = lazyGridState,
                    columns = GridCells.Adaptive(GridMaxWidth),
                    verticalArrangement = ListSpacing,
                    horizontalArrangement = ListSpacing
                ) {
                    items(
                        count = testListData.size,
                        key = { testListData[it].index }
                    ) { index ->
                        LazyItem(
                            modifier = Modifier.animateItemPlacement(),
                            index = index
                        )
                    }
                }
            } else Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) { Text(strings.listNoData) }
        }
    }
}

private val ListSpacing = Arrangement.spacedBy(10.dp)
private val GridMaxWidth = 100.dp