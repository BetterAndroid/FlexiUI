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
package com.highcapable.flexiui.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/** Simulate a router. */
@Stable
private var CurrentScreen = mutableStateOf(Screen.Main)

@Stable
enum class Screen {
    Main,
    Secondary,
    LazyList
}

@Stable
class Router(private val screen: MutableState<Screen>) {

    fun navigate(screen: Screen) {
        this.screen.value = screen
    }

    fun goHome() = navigate(Screen.Main)
}

@Composable
fun rememberRouter() = Router(remember { CurrentScreen })

@Composable
fun Screen(screen: Screen, content: @Composable () -> Unit) {
    val currentScreen by remember { CurrentScreen }
    AnimatedVisibility(
        visible = currentScreen == screen,
        enter = fadeIn(),
        exit = fadeOut()
    ) { content() }
}