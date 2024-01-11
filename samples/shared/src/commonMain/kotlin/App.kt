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
 * This file is created by fankes on 2023/11/5.
 */
@file:OptIn(ExperimentalFoundationApi::class)

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.highcapable.betterandroid.compose.extension.ui.ComponentPadding
import com.highcapable.betterandroid.compose.multiplatform.backpress.BackHandler
import com.highcapable.betterandroid.compose.multiplatform.systembar.PlatformSystemBarStyle
import com.highcapable.betterandroid.compose.multiplatform.systembar.rememberSystemBarsController
import com.highcapable.betterandroid.compose.multiplatform.systembar.setStyle
import com.highcapable.flexiui.FlexiTheme
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
import com.highcapable.flexiui.component.SecondaryAppBar
import com.highcapable.flexiui.component.Surface
import com.highcapable.flexiui.component.SwitchItem
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.resources.FlexiIcons
import kotlinx.coroutines.launch

private const val PROJECT_URL = "https://github.com/BetterAndroid/FlexiUI"

@Composable
private fun FlexiDemoTheme(content: @Composable () -> Unit) {
    val systemBars = rememberSystemBarsController()
    val darkMode by remember { Preferences.darkMode }
    val followSystemDarkMode by remember { Preferences.followSystemDarkMode }
    val currentDarkMode = if (followSystemDarkMode) isSystemInDarkTheme() else darkMode
    val colorScheme by remember { Preferences.colorScheme }
    systemBars.setStyle(
        if (currentDarkMode)
            PlatformSystemBarStyle.DarkTransparent
        else PlatformSystemBarStyle.LightTransparent
    )
    FlexiTheme(
        colors = colorScheme.toColors(currentDarkMode),
        content = content
    )
}

/** Simulate a router. */
@Stable
private var CurrentPage = mutableStateOf(0)

@Composable
private fun rememberCurrentPage() = remember { CurrentPage }

@Composable
private fun Page(page: Int, content: @Composable () -> Unit) {
    val currentPage by remember { CurrentPage }
    AnimatedVisibility(
        visible = currentPage == page,
        enter = fadeIn(),
        exit = fadeOut()
    ) { content() }
}

@Composable
fun App() {
    FlexiDemoTheme {
        // Surface will keep the content background color when animation.
        Surface(padding = ComponentPadding()) {
            Page(0) { MainScreen() }
            Page(1) { SecondaryScreen() }
        }
    }
}

@Composable
fun MainScreen() {
    val pageCount = 2
    val state = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current
    Scaffold(
        appBar = {
            PrimaryAppBar(
                title = { Text("Flexi UI Demo") },
                actions = {
                    ActionIconButton(
                        onClick = { uriHandler.openUri(PROJECT_URL) }
                    ) { Icon(FlexiIcons.GitHub) }
                }
            )
        },
        navigationBar = {
            NavigationBarRow(
                arrangement = Arrangement.SpaceAround
            ) {
                NavigationBarItem(
                    selected = state.currentPage == 0,
                    onClick = { scope.launch { state.animateScrollToPage(page = 0) } },
                    icon = { Icon(FlexiIcons.Home, style = IconDefaults.style(size = 24.dp)) },
                    text = { Text("Home") }
                )
                NavigationBarItem(
                    selected = state.currentPage == 1,
                    onClick = { scope.launch { state.animateScrollToPage(page = 1) } },
                    icon = { Icon(FlexiIcons.Component, style = IconDefaults.style(size = 24.dp)) },
                    text = { Text("Component") }
                )
            }
        }
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = state,
        ) { index ->
            when (index) {
                0 -> MainHomePage()
                1 -> MainComponentPage()
            }
        }
    }
}

@Composable
fun MainHomePage() {
    Column(modifier = Modifier.fillMaxSize()) {
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
        var currentPage by rememberCurrentPage()
        HorizontalItemBox(
            onClick = { currentPage = 1 },
            title = { Text("Single Page Demo") },
            subtitle = { Text("Open a single page.") }
        )
    }
}

@Composable
fun MainComponentPage() {
    // TODO: To be implemented.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { Text("To be implemented.") }
}

@Composable
fun SecondaryScreen() {
    var currentPage by rememberCurrentPage()
    Scaffold(
        appBar = {
            SecondaryAppBar(
                title = { Text("Single Page") },
                navigationIcon = {
                    NavigationIconButton(onClick = { currentPage = 0 })
                }
            )
        }
    ) {
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            Text(
                """
                  Now, you open a separate secondary page.
                  You can click the button below to back to the homepage.
                """.trimIndent(),
                style = FlexiTheme.typography.primary.copy(lineHeight = 2.em)
            )
            PrimarySpacer()
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { currentPage = 0 }
            ) { Text("Take Me Home") }
        }
        BackHandler { currentPage = 0 }
    }
}