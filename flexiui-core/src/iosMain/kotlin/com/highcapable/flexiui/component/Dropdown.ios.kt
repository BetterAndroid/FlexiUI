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
 * This file is created by fankes on 2023/11/18.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.component

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.toIntRect
import com.highcapable.flexiui.interaction.rippleClickable
import kotlin.math.max

@Composable
internal actual fun DropdownListBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier,
    properties: DropdownListProperties,
    menuHeightPx: (Int) -> Unit,
    content: @Composable @UiComposable BoxWithConstraintsScope.() -> Unit
) {
    val windowInfo = LocalWindowInfo.current
    BoxWithConstraints(
        modifier = Modifier.dropdownList(
            properties = properties,
            modifier = modifier.rippleClickable(
                enabled = properties.enabled,
                role = Role.DropdownList,
                interactionSource = properties.interactionSource
            ) {
                properties.focusRequester.requestFocus()
                onExpandedChange(!expanded)
            }.onGloballyPositioned {
                val boundsInWindow = it.boundsInWindow()
                val visibleWindowBounds = windowInfo.containerSize.toIntRect()
                val heightAbove = boundsInWindow.top - visibleWindowBounds.top
                val heightBelow = visibleWindowBounds.height - boundsInWindow.bottom
                menuHeightPx(max(heightAbove, heightBelow).toInt())
            }
        ),
        content = content
    )
}