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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
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
import com.highcapable.flexiui.component.AutoCompleteOptions
import com.highcapable.flexiui.component.BackspaceTextField
import com.highcapable.flexiui.component.Button
import com.highcapable.flexiui.component.CheckBox
import com.highcapable.flexiui.component.DropdownList
import com.highcapable.flexiui.component.DropdownMenuItem
import com.highcapable.flexiui.component.PasswordTextField
import com.highcapable.flexiui.component.RadioButton
import com.highcapable.flexiui.component.Slider
import com.highcapable.flexiui.component.Surface
import com.highcapable.flexiui.component.Switch
import com.highcapable.flexiui.component.Text
import com.highcapable.flexiui.component.TextField
import com.highcapable.flexiui.defaultColors
import com.highcapable.flexiui.dynamicColors
import com.highcapable.flexiui.greenColors
import com.highcapable.flexiui.isDynamicColorsAvailable
import com.highcapable.flexiui.orangeColors
import com.highcapable.flexiui.pinkColors
import com.highcapable.flexiui.purpleColors
import com.highcapable.flexiui.redColors
import com.highcapable.flexiui.yellowColors
import kotlin.math.roundToInt

@Composable
fun App() {
    val themeColor = remember { mutableStateOf(ThemeColors.first().second) }
    FlexiTheme(colors = themeColor.value) {
        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AreaBox(modifier = Modifier.weight(1f)) { ContentView() }
                Spacer(Modifier.padding(10.dp))
                ThemeColorsView(themeColor)
            }
        }
    }
}

@Composable
private fun ContentView() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var greeting by remember { mutableStateOf("Hello World!") }
        Text(text = greeting)
        Spacer(Modifier.padding(15.dp))
        Button(onClick = { greeting = "Hello Jetpack Compose Multiplatform!" }) {
            Text(text = "Greeting")
        }
        Spacer(Modifier.padding(15.dp))
        var input by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier.width(TextFieldWidth),
            value = input,
            onValueChange = { input = it },
            placeholder = { Text(text = "Type something...") }
        )
        Spacer(Modifier.padding(10.dp))
        var completion by remember { mutableStateOf("") }
        TextField(
            modifier = Modifier.width(TextFieldWidth),
            value = completion,
            onValueChange = { completion = it },
            placeholder = { Text(text = "Auto completion...") },
            singleLine = true,
            completionValues = listOf(
                "Compose",
                "Compose World",
                "Composable",
                "Android",
                "Jetpack",
                "Jetpack Compose",
                "Jetpack Compose Multiplatform"
            ),
            completionOptions = AutoCompleteOptions(checkCase = false, threshold = 1)
        )
        Spacer(Modifier.padding(10.dp))
        var backspace by remember { mutableStateOf("") }
        BackspaceTextField(
            modifier = Modifier.width(TextFieldWidth),
            value = backspace,
            onValueChange = { backspace = it },
            placeholder = { Text(text = "Type or delete...") }
        )
        Spacer(Modifier.padding(10.dp))
        var password by remember { mutableStateOf("") }
        PasswordTextField(
            modifier = Modifier.width(TextFieldWidth),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Enter password...") }
        )
        Spacer(Modifier.padding(15.dp))
        var switchChecked by remember { mutableStateOf(true) }
        Switch(checked = switchChecked, onCheckedChange = { switchChecked = it }) {
            Text(modifier = Modifier.width(200.dp), text = "Switch Status: ${if (switchChecked) "Checked" else "Unchecked"}")
        }
        Spacer(Modifier.padding(15.dp))
        var boxChecked by remember { mutableStateOf(true) }
        CheckBox(checked = boxChecked, onCheckedChange = { boxChecked = it }) {
            Text(modifier = Modifier.width(210.dp), text = "CheckBox Status: ${if (boxChecked) "Checked" else "Unchecked"}")
        }
        Spacer(Modifier.padding(15.dp))
        Row {
            var option1Seleted by remember { mutableStateOf(true) }
            var option2Seleted by remember { mutableStateOf(false) }
            fun switchOption(option1: Boolean) {
                option1Seleted = option1
                option2Seleted = !option1
            }
            RadioButton(option1Seleted, onClick = { switchOption(option1 = true) }) {
                Text(text = "Option 1")
            }
            Spacer(Modifier.padding(15.dp))
            RadioButton(option2Seleted, onClick = { switchOption(option1 = false) }) {
                Text(text = "Option 2")
            }
        }
        Spacer(Modifier.padding(15.dp))
        var value by remember { mutableStateOf(50f) }
        Text(text = "Current Value: ${value.roundToInt()}", modifier = Modifier.width(150.dp))
        Spacer(Modifier.padding(10.dp))
        Slider(value = value, onValueChange = { value = it })
        Spacer(Modifier.padding(15.dp))
        var stepValue by remember { mutableStateOf(25f) }
        Text(text = "Current Value: ${stepValue.roundToInt()}", modifier = Modifier.width(150.dp))
        Spacer(Modifier.padding(10.dp))
        Slider(value = stepValue, onValueChange = { stepValue = it }, steps = 3)
        Spacer(Modifier.padding(15.dp))
        val items = listOf("Item 1", "Item 2", "Item 3")
        var expanded by remember { mutableStateOf(false) }
        var curentItem by remember { mutableStateOf(items.first()) }
        Text(text = "Choose an item following.")
        Spacer(Modifier.padding(10.dp))
        DropdownList(
            modifier = Modifier.width(DropdownListWidth),
            expanded = expanded,
            onExpandedChange = { expanded = it },
            text = { Text(text = curentItem) }
        ) {
            items.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        curentItem = it
                    },
                    actived = curentItem == it
                ) { Text(text = it) }
            }
        }
    }
}

@Composable
private fun ThemeColorsView(themeColor: MutableState<Colors>) {
    Text(text = "Here are some theme color tests.")
    Spacer(Modifier.padding(10.dp))
    var showChooser by remember { mutableStateOf(false) }
    var choosedColorName by remember { mutableStateOf(ThemeColors.first().first) }
    var choosedColor by remember { mutableStateOf(ThemeColors.first().second) }
    themeColor.value = choosedColor
    Row {
        DropdownList(
            modifier = Modifier.width(DropdownListWidth),
            expanded = showChooser,
            onExpandedChange = { showChooser = it },
            text = { Text(text = choosedColorName) }
        ) {
            ThemeColors.forEachIndexed { index, (name, colors) ->
                @Composable
                fun createItem(name: String, colors: Colors) =
                    DropdownMenuItem(
                        onClick = {
                            showChooser = false
                            choosedColorName = name
                            choosedColor = colors
                        },
                        actived = choosedColorName == name
                    ) { Text(text = name) }
                if (isDynamicColorsAvailable() && index == 3)
                    DynamicColors.forEach { (name, colors) -> createItem(name, colors) }
                else createItem(name, colors)
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Button(onClick = {
            choosedColorName = ThemeColors.first().first
            choosedColor = ThemeColors.first().second
        }) { Text(text = "Reset") }
    }
}

private val DynamicColors
    @Composable
    @ReadOnlyComposable
    get() = listOf(
        "Dynamic" to dynamicColors(),
        "Dynamic (Dark)" to dynamicColors(darkMode = true),
        "Dynamic (Black)" to dynamicColors(darkMode = true, blackDarkMode = true)
    )

private val ThemeColors = listOf(
    "Default" to defaultColors(),
    "Default (Dark)" to defaultColors(darkMode = true),
    "Default (Black)" to defaultColors(darkMode = true, blackDarkMode = true),
    "Red" to redColors(),
    "Red (Dark)" to redColors(darkMode = true),
    "Red (Black)" to redColors(darkMode = true, blackDarkMode = true),
    "Pink" to pinkColors(),
    "Pink (Dark)" to pinkColors(darkMode = true),
    "Pink (Black)" to pinkColors(darkMode = true, blackDarkMode = true),
    "Purple" to purpleColors(),
    "Purple (Dark)" to purpleColors(darkMode = true),
    "Purple (Black)" to purpleColors(darkMode = true, blackDarkMode = true),
    "Green" to greenColors(),
    "Green (Dark)" to greenColors(darkMode = true),
    "Green (Black)" to greenColors(darkMode = true, blackDarkMode = true),
    "Orange" to orangeColors(),
    "Orange (Dark)" to orangeColors(darkMode = true),
    "Orange (Black)" to orangeColors(darkMode = true, blackDarkMode = true),
    "Yellow" to yellowColors(),
    "Yellow (Dark)" to yellowColors(darkMode = true),
    "Yellow (Black)" to yellowColors(darkMode = true, blackDarkMode = true),
    "Blue" to blueColors(),
    "Blue (Dark)" to blueColors(darkMode = true),
    "Blue (Black)" to blueColors(darkMode = true, blackDarkMode = true)
)

private val TextFieldWidth = 180.dp
private val DropdownListWidth = 170.dp