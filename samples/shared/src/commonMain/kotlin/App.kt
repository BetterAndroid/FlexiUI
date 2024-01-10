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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.FlexiTheme
import com.highcapable.flexiui.component.AreaBox
import com.highcapable.flexiui.component.AreaColumn
import com.highcapable.flexiui.component.Icon
import com.highcapable.flexiui.component.IconDefaults
import com.highcapable.flexiui.component.NavigationBarItem
import com.highcapable.flexiui.component.NavigationBarRow
import com.highcapable.flexiui.component.PrimaryAppBar
import com.highcapable.flexiui.component.Scaffold
import com.highcapable.flexiui.component.SwitchItem
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.resources.FlexiIcons
import kotlinx.coroutines.launch

@Stable
data class AppPreferences(
    var darkMode: MutableState<Boolean> = mutableStateOf(false),
    var followSystemDarkMode: MutableState<Boolean> = mutableStateOf(false)
)

@Stable
private val Preferences = AppPreferences()

@Composable
fun App() {
    FlexiTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    val pageCount = 2
    val state = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    Scaffold(
        appBar = {
            PrimaryAppBar(
                title = { Text("Flexi UI Demo") },
                actions = {
                    ActionIconButton(
                        onClick = { /* TODO */ }
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
            var darkMode by remember { Preferences.darkMode }
            var followSystemDarkMode by remember { Preferences.followSystemDarkMode }
            SecondaryText("Theme Style")
            PrimarySpacer()
            SwitchItem(
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            ) { Text("Enable night mode") }
            SecondarySpacer()
            SecondaryText("Manually switch the current theme to night mode.")
            PrimarySpacer()
            SwitchItem(
                checked = followSystemDarkMode,
                onCheckedChange = { followSystemDarkMode = it }
            ) { Text("Follow system night mode") }
            SecondarySpacer()
            SecondaryText("Follow the system theme to switch day and night mode.")
        }
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
private fun PrimarySpacer() {
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun SecondarySpacer() {
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
private fun SecondaryText(text: String) {
    Text(
        text = text,
        color = FlexiTheme.colors.textSecondary,
        style = FlexiTheme.typography.secondary
    )
}