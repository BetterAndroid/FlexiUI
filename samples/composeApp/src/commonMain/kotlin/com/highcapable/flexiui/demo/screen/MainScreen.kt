/*
 * Flexi UI - A flexible and useful UI component library.
 * Copyright (C) 2019 HighCapable
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
package com.highcapable.flexiui.demo.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.highcapable.betterandroid.compose.extension.ui.layout.AdaptiveRow
import com.highcapable.flexiui.FlexiTheme
import com.highcapable.flexiui.availableColorSchemes
import com.highcapable.flexiui.component.AreaBox
import com.highcapable.flexiui.component.AreaColumn
import com.highcapable.flexiui.component.AutoCompleteOptions
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.CircularProgressIndicator
import com.highcapable.flexiui.component.DropdownList
import com.highcapable.flexiui.component.DropdownMenuItem
import com.highcapable.flexiui.component.HorizontalItemBox
import com.highcapable.flexiui.component.Icon
import com.highcapable.flexiui.component.IconDefaults
import com.highcapable.flexiui.component.LinearProgressIndicator
import com.highcapable.flexiui.component.NavigationBarItem
import com.highcapable.flexiui.component.NavigationBarRow
import com.highcapable.flexiui.component.PrimaryAppBar
import com.highcapable.flexiui.component.PrimarySpacer
import com.highcapable.flexiui.component.Scaffold
import com.highcapable.flexiui.component.SecondarySpacer
import com.highcapable.flexiui.component.SecondaryText
import com.highcapable.flexiui.component.Slider
import com.highcapable.flexiui.component.StickyHeaderBar
import com.highcapable.flexiui.component.SwitchItem
import com.highcapable.flexiui.component.TertiarySpacer
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.component.TextField
import com.highcapable.flexiui.component.window.FlexiDialog
import com.highcapable.flexiui.demo.AutoList
import com.highcapable.flexiui.demo.Component
import com.highcapable.flexiui.demo.Dialog
import com.highcapable.flexiui.demo.GitHub
import com.highcapable.flexiui.demo.Home
import com.highcapable.flexiui.demo.Locale
import com.highcapable.flexiui.demo.PROJECT_URL
import com.highcapable.flexiui.demo.Preferences
import com.highcapable.flexiui.demo.Screen
import com.highcapable.flexiui.demo.Signpost
import com.highcapable.flexiui.demo.Style
import com.highcapable.flexiui.demo.TextFields
import com.highcapable.flexiui.demo.displayName
import com.highcapable.flexiui.demo.locales
import com.highcapable.flexiui.demo.rememberRouter
import com.highcapable.flexiui.demo.strings
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
            NavigationBarRow(arrangement = Arrangement.SpaceAround) {
                repeat(pageCount) { index ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(page = index) } },
                        icon = {
                            Icon(when (index) {
                                0 -> FlexiIcons.Home
                                else -> FlexiIcons.Component
                            }, style = IconDefaults.style(size = 24.dp))
                        },
                        text = {
                            Text(when (index) {
                                0 -> strings.home
                                else -> strings.component
                            })
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            beyondViewportPageCount = pageCount
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
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
                    text = { Text(locale.displayName) },
                ) {
                    locales.forEach {
                        DropdownMenuItem(
                            actived = locale == it,
                            onClick = {
                                expanded = false
                                locale = it
                            }
                        ) { Text(it.displayName) }
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
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            StickyHeaderBar(
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(FlexiIcons.TextFields) },
                title = { Text(strings.textAndInput) }
            )
            SecondarySpacer()
            val defaultFontSize = FlexiTheme.typography.primary.fontSize.value
            val maxFontSize = 30f
            var inputText by remember { mutableStateOf("") }
            var fontSize by remember { mutableStateOf(defaultFontSize) }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text(strings.enterTextHere) }
            )
            SecondarySpacer()
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(10.dp),
                text = inputText.ifEmpty { strings.enterTextHereDescription },
                fontSize = fontSize.sp,
                textAlign = TextAlign.Center
            )
            SecondarySpacer()
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fontSize,
                onValueChange = { fontSize = it },
                max = maxFontSize
            )
        }
        SecondarySpacer()
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf("") }
            var inputText by remember { mutableStateOf("") }
            StickyHeaderBar(
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(FlexiIcons.AutoList) },
                title = { Text(strings.dropdownAndAutoComplete) }
            )
            SecondarySpacer()
            DropdownList(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = { expanded = it },
                text = { Text(selectedText.ifEmpty { strings.dropdownSimpleDescription }) },
            ) {
                strings.simpleStringData.forEach {
                    DropdownMenuItem(
                        actived = selectedText == it,
                        onClick = {
                            expanded = false
                            selectedText = it
                        }
                    ) { Text(it) }
                }
            }
            SecondarySpacer()
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text(strings.enterTextHere) },
                singleLine = true,
                completionValues = strings.simpleStringData,
                completionOptions = AutoCompleteOptions(
                    checkCase = false,
                    threshold = 1
                )
            )
            SecondarySpacer()
            SecondaryText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = strings.dropdownAndAutoCompleteDescription,
                textAlign = TextAlign.Center
            )
        }
        SecondarySpacer()
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            var useCircularIndicator by remember { mutableStateOf(false) }
            var indeterminate by remember { mutableStateOf(false) }
            var progress by remember { mutableStateOf(30f) }
            StickyHeaderBar(
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(FlexiIcons.Signpost) },
                title = { Text(strings.progressIndicator) }
            )
            SecondarySpacer()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                if (useCircularIndicator)
                    CircularProgressIndicator(
                        progress = progress,
                        max = 100f,
                        indeterminate = indeterminate
                    )
                else
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        progress = progress,
                        max = 100f,
                        indeterminate = indeterminate
                    )
            }
            SecondarySpacer()
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = 10.dp
            ) {
                Button(
                    onClick = { progress = 30f }
                ) { Text(strings.progressTo30, singleLine = true) }
                Button(
                    onClick = { progress = 100f }
                ) { Text(strings.progressTo100, singleLine = true) }
            }
            PrimarySpacer()
            SwitchItem(
                checked = useCircularIndicator,
                onCheckedChange = { useCircularIndicator = it }
            ) { Text(strings.useCircularIndicator) }
            PrimarySpacer()
            SwitchItem(
                checked = indeterminate,
                onCheckedChange = { indeterminate = it }
            ) { Text(strings.switchToIndeterminateProgress) }
        }
        SecondarySpacer()
        AreaColumn(modifier = Modifier.fillMaxWidth()) {
            StickyHeaderBar(
                modifier = Modifier.fillMaxWidth(),
                icon = { Icon(FlexiIcons.Dialog) },
                title = { Text(strings.dialog) }
            )
            SecondarySpacer()
            var showNormalDialog by remember { mutableStateOf(false) }
            var showMultiButtonDialog by remember { mutableStateOf(false) }
            var showDialogWithIcon by remember { mutableStateOf(false) }
            var showLoadingDialog by remember { mutableStateOf(false) }
            var showProgressDialog by remember { mutableStateOf(false) }
            var showSingleInputDialog by remember { mutableStateOf(false) }
            var showMultiInputDialog by remember { mutableStateOf(false) }
            var showDropdownListDialog by remember { mutableStateOf(false) }
            var showPasswordInputDialog by remember { mutableStateOf(false) }
            var showSingleChoiceDialog by remember { mutableStateOf(false) }
            var showMutliChoiceDialog by remember { mutableStateOf(false) }
            NormalDialog(
                visible = showNormalDialog,
                onDismissRequest = { showNormalDialog = false }
            )
            MultiButtonDialog(
                visible = showMultiButtonDialog,
                onDismissRequest = { showMultiButtonDialog = false }
            )
            DialogWithIconDialog(
                visible = showDialogWithIcon,
                onDismissRequest = { showDialogWithIcon = false }
            )
            LoadingDialog(
                visible = showLoadingDialog,
                onDismissRequest = { showLoadingDialog = false }
            )
            ProgressDialog(
                visible = showProgressDialog,
                onDismissRequest = { showProgressDialog = false }
            )
            SingleInputDialog(
                visible = showSingleInputDialog,
                onDismissRequest = { showSingleInputDialog = false }
            )
            MultiInputDialog(
                visible = showMultiInputDialog,
                onDismissRequest = { showMultiInputDialog = false }
            )
            DropdownListDialog(
                visible = showDropdownListDialog,
                onDismissRequest = { showDropdownListDialog = false }
            )
            PasswordInputDialog(
                visible = showPasswordInputDialog,
                onDismissRequest = { showPasswordInputDialog = false }
            )
            SingleChoiceDialog(
                visible = showSingleChoiceDialog,
                onDismissRequest = { showSingleChoiceDialog = false }
            )
            MutliChoiceDialog(
                visible = showMutliChoiceDialog,
                onDismissRequest = { showMutliChoiceDialog = false }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showNormalDialog = true }
            ) { Text(strings.normalDialog, singleLine = true) }
            SecondarySpacer()
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = 10.dp
            ) {
                Button(
                    onClick = { showMultiButtonDialog = true }
                ) { Text(strings.multiButtonDialog, singleLine = true) }
                Button(
                    onClick = { showDialogWithIcon = true }
                ) { Text(strings.dialogWithIcon, singleLine = true) }
            }
            SecondarySpacer()
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = 10.dp
            ) {
                Button(
                    onClick = { showLoadingDialog = true }
                ) { Text(strings.loadingDialog, singleLine = true) }
                Button(
                    onClick = { showProgressDialog = true }
                ) { Text(strings.progressDialog, singleLine = true) }
            }
            SecondarySpacer()
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = 10.dp
            ) {
                Button(
                    onClick = { showSingleInputDialog = true }
                ) { Text(strings.singleInputDialog, singleLine = true) }
                Button(
                    onClick = { showMultiInputDialog = true }
                ) { Text(strings.multiInputDialog, singleLine = true) }
            }
            SecondarySpacer()
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = 10.dp
            ) {
                Button(
                    onClick = { showDropdownListDialog = true }
                ) { Text(strings.dropdownListDialog, singleLine = true) }
                Button(
                    onClick = { showPasswordInputDialog = true }
                ) { Text(strings.passwordInputDialog, singleLine = true) }
            }
            SecondarySpacer()
            AdaptiveRow(
                modifier = Modifier.fillMaxWidth(),
                spacingBetween = 10.dp
            ) {
                Button(
                    onClick = { showSingleChoiceDialog = true }
                ) { Text(strings.singleChoiceDialog, singleLine = true) }
                Button(
                    onClick = { showMutliChoiceDialog = true }
                ) { Text(strings.mutliChoiceDialog, singleLine = true) }
            }
        }
    }
}

@Composable
private fun NormalDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    FlexiDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        title = { Text(strings.normalDialog) },
        content = { Text(strings.normalDialogDescription) },
        confirmButton = {
            Button(
                onClick = onDismissRequest
            ) { Text(strings.confirm) }
        }
    )
}

@Composable
private fun MultiButtonDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    FlexiDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        title = { Text(strings.multiButtonDialog) },
        content = { Text(strings.multiButtonDialogDescription) },
        confirmButton = {
            Button(
                onClick = onDismissRequest
            ) { Text(strings.confirm) }
        },
        cancelButton = {
            Button(
                onClick = onDismissRequest
            ) { Text(strings.cancel) }
        }
    )
}

@Composable
private fun DialogWithIconDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Dialog with icon
}

@Composable
private fun LoadingDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Loading dialog
}

@Composable
private fun ProgressDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Progress dialog
}

@Composable
private fun SingleInputDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Single input dialog
}

@Composable
private fun MultiInputDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Multi input dialog
}

@Composable
private fun DropdownListDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Dropdown list dialog
}

@Composable
private fun PasswordInputDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Password input dialog
}

@Composable
private fun SingleChoiceDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Single choice dialog
}

@Composable
private fun MutliChoiceDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    // TODO: Multi choice dialog
}