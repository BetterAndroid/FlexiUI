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

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.highcapable.betterandroid.compose.extension.ui.clickable
import com.highcapable.betterandroid.compose.extension.ui.combinedClickable
import com.highcapable.betterandroid.compose.extension.ui.selectable
import com.highcapable.betterandroid.compose.extension.ui.toggleable
import com.highcapable.flexiui.LocalColors
import androidx.compose.material.ripple.rememberRipple as materialRememberRipple

@Immutable
data class RippleStyle(
    val bounded: Boolean,
    val radius: Dp,
    val color: Color
)

@Composable
fun rememberRipple(style: RippleStyle) = materialRememberRipple(style.bounded, style.radius, style.color)

fun Modifier.rippleClickable(
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleClickable"
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val currentRippleStyle = rippleStyle ?: Interaction.rippleStyle
    val currentIndication = rememberRipple(currentRippleStyle)
    clickable(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role
    )
}

fun Modifier.rippleCombinedClickable(
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onLongClickLabel: String? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleCombinedClickable"
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onLongClickLabel"] = onLongClickLabel
        properties["onLongClick"] = onLongClick
        properties["onDoubleClick"] = onDoubleClick
        properties["onClick"] = onClick
    }
) {
    val currentRippleStyle = rippleStyle ?: Interaction.rippleStyle
    val currentIndication = rememberRipple(currentRippleStyle)
    combinedClickable(
        onClick = onClick,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onLongClickLabel = onLongClickLabel,
        onLongClick = onLongClick,
        onDoubleClick = onDoubleClick
    )
}

fun Modifier.rippleToggleable(
    value: Boolean,
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onValueChange: (Boolean) -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleToggleable"
        properties["value"] = value
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["role"] = role
        properties["onValueChange"] = onValueChange
    }
) {
    val currentRippleStyle = rippleStyle ?: Interaction.rippleStyle
    val currentIndication = rememberRipple(currentRippleStyle)
    toggleable(
        value = value,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        role = role,
        onValueChange = onValueChange
    )
}

fun Modifier.rippleSelectable(
    selected: Boolean,
    rippleStyle: RippleStyle? = null,
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "rippleSelectable"
        properties["selected"] = selected
        properties["rippleStyle"] = rippleStyle
        properties["interactionSource"] = interactionSource
        properties["enabled"] = enabled
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val currentRippleStyle = rippleStyle ?: Interaction.rippleStyle
    val currentIndication = rememberRipple(currentRippleStyle)
    selectable(
        selected = selected,
        interactionSource = interactionSource,
        indication = currentIndication,
        enabled = enabled,
        role = role,
        onClick = onClick
    )
}

object Interaction {
    val rippleStyle: RippleStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalRippleStyle.current ?: defaultRippleStyle()
}

val LocalRippleStyle = compositionLocalOf<RippleStyle?> { null }

@Composable
@ReadOnlyComposable
private fun defaultRippleStyle() = RippleStyle(
    bounded = true,
    radius = Dp.Unspecified,
    color = LocalColors.current.themeSecondary
)