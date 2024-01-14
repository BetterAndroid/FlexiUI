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
package com.highcapable.flexiui.demo.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.em
import com.highcapable.betterandroid.compose.multiplatform.backpress.BackHandler
import com.highcapable.flexiui.FlexiTheme
import com.highcapable.flexiui.component.AreaColumn
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.Scaffold
import com.highcapable.flexiui.component.SecondaryAppBar
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.demo.PrimarySpacer
import com.highcapable.flexiui.demo.rememberRouter

@Composable
fun SecondaryScreen() {
    val router = rememberRouter()
    Scaffold(
        appBar = {
            SecondaryAppBar(
                title = { Text("Single Page Demo") },
                navigationIcon = {
                    NavigationIconButton(onClick = { router.goHome() })
                }
            )
            BackHandler { router.goHome() }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding)
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
                    onClick = { router.goHome() }
                ) { Text("Take Me Home") }
            }
        }
    }
}