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
import com.highcapable.flexiui.availableColorSchemes
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
import com.highcapable.flexiui.component.SecondarySpacer
import com.highcapable.flexiui.component.SecondaryText
import com.highcapable.flexiui.component.StickyHeaderBar
import com.highcapable.flexiui.component.SwitchItem
import com.highcapable.flexiui.component.TertiarySpacer
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.component.window.FlexiDialog
import com.highcapable.flexiui.demo.Component
import com.highcapable.flexiui.demo.GitHub
import com.highcapable.flexiui.demo.Home
import com.highcapable.flexiui.demo.Locale
import com.highcapable.flexiui.demo.PROJECT_URL
import com.highcapable.flexiui.demo.Preferences
import com.highcapable.flexiui.demo.Screen
import com.highcapable.flexiui.demo.Style
import com.highcapable.flexiui.demo.locales
import com.highcapable.flexiui.demo.rememberRouter
import com.highcapable.flexiui.demo.strings
import com.highcapable.flexiui.demo.toName
import com.highcapable.flexiui.displayName
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
                title = { Text(strings.openLink) },
                content = { Text(strings.openLinkDescription) },
                confirmButton = {
                    Button(
                        onClick = {
                            showOpenUriDialog = false
                            uriHandler.openUri(PROJECT_URL)
                        }
                    ) { Text(strings.open) }
                },
                cancelButton = {
                    Button(
                        onClick = { showOpenUriDialog = false }
                    ) { Text(strings.cancel) }
                }
            )
            PrimaryAppBar(
                title = { Text(strings.appName) },
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
                    text = { Text(strings.home) }
                )
                NavigationBarItem(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(page = 1) } },
                    icon = { Icon(FlexiIcons.Component, style = IconDefaults.style(size = 24.dp)) },
                    text = { Text(strings.component) }
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
        AreaBox(modifier = Modifier.fillMaxWidth()) { Text(strings.appDescription) }
        SecondarySpacer()
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            val locales = locales()
            var locale by remember { Preferences.locale }
            StickyHeaderBar(
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(FlexiIcons.Locale) },
                title = { Text(strings.uiLanguage) }
            )
            SecondarySpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var expanded by remember { mutableStateOf(false) }
                Text(strings.selectLanguage)
                SecondarySpacer()
                DropdownList(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    text = { Text(locale.toName()) },
                ) {
                    locales.forEach {
                        DropdownMenuItem(
                            actived = locale == it,
                            onClick = {
                                expanded = false
                                locale = it
                            }
                        ) { Text(it.toName()) }
                    }
                }
            }
        }
        SecondarySpacer()
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            val colorSchemes = availableColorSchemes()
            var colorScheme by remember { Preferences.colorScheme }
            var darkMode by remember { Preferences.darkMode }
            var followSystemDarkMode by remember { Preferences.followSystemDarkMode }
            StickyHeaderBar(
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(FlexiIcons.Style) },
                title = { Text(strings.themeStyle) }
            )
            SecondarySpacer()
            AnimatedVisibility(visible = !followSystemDarkMode) {
                Column {
                    SwitchItem(
                        checked = darkMode,
                        onCheckedChange = { darkMode = it }
                    ) { Text(strings.enableDarkMode) }
                    TertiarySpacer()
                    SecondaryText(strings.enableDarkModeDescription)
                    SecondarySpacer()
                }
            }
            SwitchItem(
                checked = followSystemDarkMode,
                onCheckedChange = { followSystemDarkMode = it }
            ) { Text(strings.followSystemDarkMode) }
            TertiarySpacer()
            SecondaryText(strings.followSystemDarkModeDescription)
            SecondarySpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var expanded by remember { mutableStateOf(false) }
                Text(strings.selectTheme)
                SecondarySpacer()
                DropdownList(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    text = { Text(colorScheme.displayName) },
                ) {
                    colorSchemes.forEach {
                        DropdownMenuItem(
                            actived = colorScheme == it,
                            onClick = {
                                expanded = false
                                colorScheme = it
                            }
                        ) { Text(it.displayName) }
                    }
                }
            }
        }
        SecondarySpacer()
        val router = rememberRouter()
        HorizontalItemBox(
            onClick = { router.navigate(Screen.Secondary) },
            title = { Text(strings.singlePageDemo) },
            subtitle = { Text(strings.singlePageDemoDescription) }
        )
        SecondarySpacer()
        HorizontalItemBox(
            onClick = { router.navigate(Screen.LazyList) },
            title = { Text(strings.lazyListDemo) },
            subtitle = { Text(strings.lazyListDemoDescription) }
        )
    }
}

@Composable
fun MainComponentPage(modifier: Modifier) {
    // TODO: To be implemented.
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { SecondaryText("To be implemented.", primaryFontSize = true) }
}