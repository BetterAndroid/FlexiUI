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
 * This file is created by fankes on 2024/1/11.
 */
package com.highcapable.flexiui.demo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.highcapable.flexiui.FlexiTheme
import com.highcapable.flexiui.component.Text

// TODO: Some components can be include to Flexi UI (To be determined)

@Composable
fun PrimarySpacer() {
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
fun SecondarySpacer() {
    Spacer(modifier = Modifier.size(5.dp))
}

@Composable
fun SecondaryText(text: String) {
    Text(
        text = text,
        color = FlexiTheme.colors.textSecondary,
        style = FlexiTheme.typography.secondary
    )
}

@Composable
fun HapticFeedback(
    type: HapticFeedbackType = HapticFeedbackType.LongPress,
    handler: () -> Unit
): () -> Unit {
    val hapticFeedback = LocalHapticFeedback.current
    return {
        handler()
        hapticFeedback.performHapticFeedback(type)
    }
}