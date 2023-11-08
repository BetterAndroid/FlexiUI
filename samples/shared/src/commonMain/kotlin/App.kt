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
 * This file is created by fankes on 2023/11/5.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.Colors
import com.highcapable.flexiui.FlexiTheme
import com.highcapable.flexiui.blueColors
import com.highcapable.flexiui.component.AreaBox
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.Surface
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.component.TextField
import com.highcapable.flexiui.defaultColors
import com.highcapable.flexiui.greenColors
import com.highcapable.flexiui.orangeColors
import com.highcapable.flexiui.pinkColors
import com.highcapable.flexiui.purpleColors
import com.highcapable.flexiui.redColors
import com.highcapable.flexiui.yellowColors

@Composable
fun App() {
    val themeColor = remember { mutableStateOf(greenColors()) }
    FlexiTheme(colors = themeColor.value) {
        Surface {
            var greeting by remember { mutableStateOf("Hello World!") }
            var input by remember { mutableStateOf("") }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AreaBox(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = greeting)
                        Spacer(Modifier.padding(15.dp))
                        Button(onClick = { greeting = "Hello Jetpack Compose Multiplatform!" }) {
                            Text(text = "Greeting")
                        }
                        Spacer(Modifier.padding(15.dp))
                        TextField(
                            value = input,
                            placeholder = { Text(text = "Type something here...") },
                            onValueChange = { input = it }
                        )
                    }
                }
                Spacer(Modifier.padding(10.dp))
                ThemeTestLayout(themeColor)
            }
        }
    }
}

@Composable
private fun ThemeTestLayout(themeColor: MutableState<Colors>) {
    Text(text = "Here are some theme color tests.")
    Spacer(Modifier.padding(10.dp))
    Row {
        Button(onClick = { themeColor.value = defaultColors() }) {
            Text(text = "Default")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = defaultColors(darkMode = true) }) {
            Text(text = "Default (Dark)")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = defaultColors(darkMode = true, blackDarkMode = true) }) {
            Text(text = "Default (Black)")
        }
    }
    Spacer(Modifier.padding(10.dp))
    Row {
        Button(onClick = { themeColor.value = redColors() }) {
            Text(text = "Red")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = pinkColors() }) {
            Text(text = "Pink")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = purpleColors() }) {
            Text(text = "Purple")
        }
    }
    Spacer(Modifier.padding(10.dp))
    Row {
        Button(onClick = { themeColor.value = greenColors() }) {
            Text(text = "Green")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = orangeColors() }) {
            Text(text = "Orange")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = yellowColors() }) {
            Text(text = "Yellow")
        }
    }
    Spacer(Modifier.padding(10.dp))
    Row {
        Button(onClick = { themeColor.value = blueColors() }) {
            Text(text = "Blue")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = greenColors(darkMode = true) }) {
            Text(text = "Green (Dark)")
        }
        Spacer(Modifier.padding(10.dp))
        Button(onClick = { themeColor.value = greenColors(darkMode = true, blackDarkMode = true) }) {
            Text(text = "Green (Black)")
        }
    }
}