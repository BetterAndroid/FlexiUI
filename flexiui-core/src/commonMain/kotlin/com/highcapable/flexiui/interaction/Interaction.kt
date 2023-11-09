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
 * This file is created by fankes on 2023/11/10.
 */
@file:Suppress("unused")

package com.highcapable.flexiui.interaction

import androidx.compose.foundation.Indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.highcapable.flexiui.LocalColors
import androidx.compose.foundation.clickable as foundationClickable
import androidx.compose.foundation.selection.toggleable as foundationToggleable

@Composable
fun Modifier.clickable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = foundationClickable(interactionSource, indication, enabled, onClickLabel, role, onClick)

@Composable
fun Modifier.toggleable(
    value: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onValueChange: (Boolean) -> Unit
) = foundationToggleable(value, interactionSource, indication, enabled, role, onValueChange)

@Composable
fun Modifier.rippleClickable(
    rippleColor: Color = Interaction.rippleColor,
    bounded: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = clickable(
    onClick = onClick,
    interactionSource = interactionSource,
    indication = rememberRipple(bounded = bounded, color = rippleColor),
    enabled = enabled,
    onClickLabel = onClickLabel,
    role = role
)

@Composable
fun Modifier.rippleToggleable(
    value: Boolean,
    rippleColor: Color = Interaction.rippleColor,
    bounded: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    role: Role? = null,
    onValueChange: (Boolean) -> Unit
) = toggleable(
    value = value,
    interactionSource = interactionSource,
    indication = rememberRipple(bounded = bounded, color = rippleColor),
    enabled = enabled,
    role = role,
    onValueChange = onValueChange
)

object Interaction {
    val rippleColor: Color
        @Composable
        @ReadOnlyComposable
        get() = defaultInteractionRippleColor()
}

@Composable
@ReadOnlyComposable
private fun defaultInteractionRippleColor() = LocalColors.current.themeSecondary