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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.component.AreaBox
import com.highcapable.flexiui.component.AreaColumn
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.DropdownList
import com.highcapable.flexiui.component.DropdownMenuItem
import com.highcapable.flexiui.component.HorizontalItemBox
import com.highcapable.flexiui.component.Icon
import com.highcapable.flexiui.component.IconDefaults
import com.highcapable.flexiui.component.NavigationBarItem
import com.highcapable.flexiui.component.NavigationBarRow
import com.highcapable.flexiui.component.PrimaryAppBar
import com.highcapable.flexiui.component.Scaffold
import com.highcapable.flexiui.component.SwitchItem
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.component.window.FlexiDialog
import com.highcapable.flexiui.demo.Component
import com.highcapable.flexiui.demo.GitHub
import com.highcapable.flexiui.demo.Home
import com.highcapable.flexiui.demo.PROJECT_URL
import com.highcapable.flexiui.demo.Preferences
import com.highcapable.flexiui.demo.PrimarySpacer
import com.highcapable.flexiui.demo.Screen
import com.highcapable.flexiui.demo.SecondarySpacer
import com.highcapable.flexiui.demo.SecondaryText
import com.highcapable.flexiui.demo.colorSchemes
import com.highcapable.flexiui.demo.rememberRouter
import com.highcapable.flexiui.demo.toName
import com.highcapable.flexiui.resources.FlexiIcons
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val pageCount = 2
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current
    Scaffold(
        appBar = {
            var showOpenUriDialog by remember { mutableStateOf(false) }
            FlexiDialog(
                visible = showOpenUriDialog,
                onDismissRequest = { showOpenUriDialog = false },
                title = { Text("Open Link") },
                content = { Text("Open the project URL in the browser?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showOpenUriDialog = false
                            uriHandler.openUri(PROJECT_URL)
                        }
                    ) { Text("Open") }
                },
                cancelButton = {
                    Button(
                        onClick = { showOpenUriDialog = false }
                    ) { Text("Cancel") }
                }
            )
            PrimaryAppBar(
                title = { Text("Flexi UI Demo") },
                actions = {
                    ActionIconButton(
                        onClick = { showOpenUriDialog = true }
                    ) { Icon(FlexiIcons.GitHub) }
                }
            )
        },
        navigationBar = {
            NavigationBarRow(
                arrangement = Arrangement.SpaceAround
            ) {
                NavigationBarItem(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(page = 0) } },
                    icon = { Icon(FlexiIcons.Home, style = IconDefaults.style(size = 24.dp)) },
                    text = { Text("Home") }
                )
                NavigationBarItem(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(page = 1) } },
                    icon = { Icon(FlexiIcons.Component, style = IconDefaults.style(size = 24.dp)) },
                    text = { Text("Component") }
                )
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) { index ->
            val modifier = Modifier.padding(innerPadding)
            when (index) {
                0 -> MainHomePage(modifier)
                1 -> MainComponentPage(modifier)
            }
        }
    }
}

@Composable
fun MainHomePage(modifier: Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.fillMaxSize().verticalScroll(scrollState)) {
        AreaBox(modifier = Modifier.fillMaxWidth()) {
            Text("Flexi UI is a flexible and useful UI component library.")
        }
        PrimarySpacer()
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            var colorScheme by remember { Preferences.colorScheme }
            var darkMode by remember { Preferences.darkMode }
            var followSystemDarkMode by remember { Preferences.followSystemDarkMode }
            SecondaryText("Theme Style")
            PrimarySpacer()
            AnimatedVisibility(visible = !followSystemDarkMode) {
                Column {
                    SwitchItem(
                        checked = darkMode,
                        onCheckedChange = { darkMode = it }
                    ) { Text("Enable night mode") }
                    SecondarySpacer()
                    SecondaryText("Manually switch the current theme to night mode.")
                    PrimarySpacer()
                }
            }
            SwitchItem(
                checked = followSystemDarkMode,
                onCheckedChange = { followSystemDarkMode = it }
            ) { Text("Follow system night mode") }
            SecondarySpacer()
            SecondaryText("Follow the system theme to switch day and night mode.")
            PrimarySpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var expanded by remember { mutableStateOf(false) }
                Text("Current Theme")
                PrimarySpacer()
                DropdownList(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    text = { Text(colorScheme.toName()) },
                ) {
                    colorSchemes().forEach {
                        DropdownMenuItem(
                            actived = colorScheme == it,
                            onClick = {
                                expanded = false
                                colorScheme = it
                            }
                        ) { Text(it.toName()) }
                    }
                }
            }
        }
        PrimarySpacer()
        val router = rememberRouter()
        HorizontalItemBox(
            onClick = { router.navigate(Screen.Secondary) },
            title = { Text("Single Page Demo") },
            subtitle = { Text("Open a single page") }
        )
        PrimarySpacer()
        HorizontalItemBox(
            onClick = { router.navigate(Screen.LazyList) },
            title = { Text("Lazy List Demo") },
            subtitle = { Text("Open a lazy list page") }
        )
    }
}

@Composable
fun MainComponentPage(modifier: Modifier) {
    // TODO: To be implemented.
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { Text("To be implemented.") }
}